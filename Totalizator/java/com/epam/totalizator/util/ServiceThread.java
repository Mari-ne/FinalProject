package com.epam.totalizator.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.apache.log4j.Logger;

import com.epam.totalizator.dao.CompetitionDao;
import com.epam.totalizator.dao.ForecastDao;
import com.epam.totalizator.dao.PersonalResultDao;
import com.epam.totalizator.dao.ResultDao;
import com.epam.totalizator.dao.UserDao;
import com.epam.totalizator.entity.Competition;
import com.epam.totalizator.entity.Forecast;
import com.epam.totalizator.entity.PersonalResult;
import com.epam.totalizator.entity.Result;
import com.epam.totalizator.entity.User;
import com.epam.totalizator.exception.ProjectException;

/**
 * 
 * Service class, that work as daemon thread.
 * It monitors competition and their state.
 * If competition with state 'Acceptance of bet' is starting now, all competition with the same state change
 * it on state 'Completion of bets'.
 * After that, thread wait till the finish of each competition, randomly generate result and set state 'Completed'.
 * Next, it find all user and their forecasts on this list of competition and count quantity of correct predictions.
 * If this quantity is less then 8, user get regrettable email. Otherwise, program count how much this user get
 * and send congratulatory email.
 * After that, thread return to monitoring.
 *
 */
public class ServiceThread extends Thread {

	private PersonalResultDao personDao = new PersonalResultDao();
	private ResultDao resultDao = new ResultDao();
	private UserDao userDao = new UserDao();
	private CompetitionDao dao = new CompetitionDao();
	private ForecastDao forecastDao = new ForecastDao();
	public static final AtomicInteger number = new AtomicInteger(15);
	private static final Logger LOGGER = Logger.getRootLogger();
	
	private static AtomicInteger count = new AtomicInteger(0);
	
	/**
	 * Method to monitor competitions.
	 * 		Find all 'bettable' (that has state 'Acceptance of bets') competition.
	 * 		If some of them is started, method waiting() is launching.
	 * 		Otherwise, thread sleep 10 seconds.
	 */
	@Override
	public void run() {
		LOGGER.debug("start work!");
        while(true) {
        	try {
        		int i = 0;
				List<Competition> list = dao.findBettable();
				Timestamp now = new Timestamp(System.currentTimeMillis());
				//should check only first one because other will be later
				if(!list.isEmpty() && list.get(0).getStart().before(now)) {
					for(i = 0; i < list.size(); i ++){
						list.get(i).setState("Completion of bets");
						dao.update(list.get(i));
						count.incrementAndGet();
					}
					if(count.get() == number.get()) {
						waiting();
						count.set(0);
					}
				} else {
					TimeUnit.SECONDS.sleep(10);
				}				
			} catch (ProjectException e) {
				LOGGER.error(e.getMessage());
			} catch (InterruptedException e) {
				LOGGER.fatal(e.getMessage());
				Thread.currentThread().interrupt();
			}
        }
    }
	
	/**
	 * Method, that 'complete' all competition (set them result and set state 'Completed').
	 * 		If all competition end, method launch method processing().
	 * 		Otherwise, sleep 1 second.
	 */	
	private void waiting() {
		try {
			List<Forecast> forecasts = forecastDao.findActualForecast();
			HashMap<String, Integer> correct = new HashMap<>();
			forecasts.forEach((f)->correct.put(f.getUserLogin(), 0));
			int c = 0;
			List<Competition> completed = new ArrayList<>();
			while(c < number.get()) {
				try {
					List<Competition> list = dao.findExpected();
					if(!list.isEmpty()) {
						for(int i = 0; i < list.size(); i ++) {
							c ++;
							list.get(i).setState("Completed");
							list.get(i).setResult(makeResult(list.get(i).getSportId()));
							LOGGER.debug("id: " + list.get(i).getId() + " - result: " + list.get(i).getResult());
							completed.add(list.get(i));
							dao.update(list.get(i));
						}
					}
					TimeUnit.SECONDS.sleep(1);
				} catch (ProjectException e) {
					LOGGER.error(e.getMessage());
				} catch (InterruptedException e) {
					LOGGER.error(e.getMessage());
					Thread.currentThread().interrupt();
				}
			}			
			LOGGER.debug("start result processing");
			resultProcessing(completed, forecasts, correct);
		} catch (ProjectException e) {
			LOGGER.error(e.getMessage());
		}
		
	}
	
	/**
	 * Method to process result of users bets.
	 * 		Firstly, found quantity of correct predictions for each user (method {@linkplain ServiceThread#countCorrect})
	 * 		Than, depend on this information deside, who will get nothing (they will get email with regrets) and
	 * 		who will get profit (their bets is added to appropriate row in database).
	 * 		Next step: count coefficient for each quantity of correct predictions on base of users bets.
	 * 		Last step: count gain of each winner and notify them with email.
	 * @param completed List of completed competitions
	 * @param forecasts List of all forecast, that made to completed competitions
	 * @param correct Map of login and integer
	 */
	private void resultProcessing(List<Competition> completed, List<Forecast> forecasts, HashMap<String, Integer> correct) {
		try {
			List<PersonalResult> perResult = personDao.findAll();
			List<Result> results = resultDao.findAll();
			//find amount of correct forecast for each of user
			countCorrect(completed, forecasts, correct);
			//find quantity of betters and amount of bets for each quantity of correct forecast
			for(Map.Entry<String, Integer> elem : correct.entrySet()) {
				if(elem.getValue().intValue() > Math.ceil(number.get() / 2)) {
					LOGGER.debug("Have enough: " + elem.getKey() + " - correct " + elem.getValue());
					//better can get gain only if he has more then half of correct forecasts
					BigDecimal bet = perResult.get(Finder.findPersonalResult(elem.getKey(), perResult)).getLastBet();
					int index = Finder.findResult(elem.getValue().intValue(), results);
					results.get(index).addBet(bet);
					results.get(index).addBetter();
				}else {
					Predicate<PersonalResult> filter = p -> p.getUserLogin().equals(elem.getKey());
					perResult.removeIf(filter);
					sendResults(false, null, userDao.findById(elem.getKey()).get());
				}
			}
			//find coefficient 
			for(int i = 0; i < results.size(); i ++) {
				Result r = results.get(i);
				if(r.getBets().doubleValue() == 0.0)
					continue;
				r.setCoefficient(r.getPool().divide(r.getBets(), RoundingMode.DOWN).setScale(2));
				resultDao.update(r);
			}
			//find amount of gain for each user, who gain something
			for(int i = 0; i < perResult.size(); i ++) {
				if(correct.get(perResult.get(i).getUserLogin()) == null)
					continue;			
				BigDecimal coef = results.get(Finder.findResult(correct.get(perResult.get(i).getUserLogin()), results)).getCoefficient();
				BigDecimal lastBet = perResult.get(i).getLastBet();			
				perResult.get(i).setLastGain(lastBet.multiply(coef).setScale(2, RoundingMode.FLOOR));
				personDao.updateGain(perResult.get(i));
				sendResults(true, perResult.get(i).getLastGain(), userDao.findById(perResult.get(i).getUserLogin()).get());
			}
		} catch (ProjectException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	/**
	 * Generate random result for competition.
	 * @param sportId Type of sport of competition for what result is generated 
	 * @return Generated result of competition: 'first team score':'second team score'
	 */
	private String makeResult(int sportId) {
		String result = "";
		Random rand = new Random();
		switch(sportId) {
		case 2:{
			//score in basketball is differ from score in football and hockey
			result += Integer.toString(rand.nextInt(100));
			result += ":";
			result += Integer.toString(rand.nextInt(100));
			break;
		}
		default:{
			result += Integer.toString(rand.nextInt(5));
			result += ":";
			result += Integer.toString(rand.nextInt(5));
		}
		}
		return result;
	}
	
	/**
	 * Count quantity of correct prediction for each competition and user. 
	 * 		Count quantity of correct prediction on base of competition result and user's prediction.
	 * 		Result is saved in correct
	 * @param completed List of completed competition
	 * @param forecasts List of users forecasts
	 * @param correct Map of logins and integers
	 */
	static void countCorrect(List<Competition> completed, List<Forecast> forecasts, HashMap<String, Integer> correct) {
		//method was made package to have access in test
		for(Competition comp : completed) {
			String res = comp.getResult();
			int first = Integer.parseInt(res.substring(0, res.indexOf(":")));
			int second = Integer.parseInt(res.substring(res.indexOf(":") + 1, res.length()));
			for(Forecast forecast : forecasts) {
				if(forecast.getCompetitionId() == comp.getId()) {
					String login = forecast.getUserLogin();
					if(first > second && forecast.getResult().equals("1"))
						correct.put(login, correct.get(login).intValue() + 1);
					else if(first == second && forecast.getResult().equals("x")) 
						correct.put(login, correct.get(login).intValue() + 1);
					else if(first < second && forecast.getResult().equals("2"))
						correct.put(login, correct.get(login).intValue() + 1);
				}
			}
		}
	}
	
	/**
	 * Generate and send email to users.
	 * @param isWinMessage Show, what type of email to send
	 * @param gain Amount of gain
	 * @param user User to whom email will be send
	 */
	private void sendResults(boolean isWinMessage, BigDecimal gain, User user) {
		Mailer mailer = new Mailer();
		String subject = "";
		String text = "";
		if(isWinMessage) {
			subject = "Totalizator congratulate you";
			text = "Congradulation, " + user.getLogin() + "!\nYou won ";
			text += gain.setScale(2, RoundingMode.HALF_UP).toString();
			text += " with your last bet!";
		} else {
			subject = "Bad information from Totalizator";
			text = "Sorry, " + user.getLogin() + ", but you got nothing from your last bet. Good luck next time!";
		}
		mailer.send(subject, text, user.getEmail());
	}
}
