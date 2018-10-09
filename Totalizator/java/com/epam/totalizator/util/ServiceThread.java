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
import com.epam.totalizator.entity.Competition;
import com.epam.totalizator.entity.Forecast;
import com.epam.totalizator.entity.PersonalResult;
import com.epam.totalizator.entity.Result;


public class ServiceThread extends Thread {

	public static final AtomicInteger number = new AtomicInteger(15);
	private static final Logger LOGGER = Logger.getRootLogger();
	
	private static AtomicInteger count = new AtomicInteger(0);
	
	@Override
	public void run() {
		CompetitionDao compDao = new CompetitionDao();
        while(true) {
        	try {
        		int i = 0;
				List<Competition> list = compDao.findBettable();
				Timestamp now = new Timestamp(System.currentTimeMillis());
				//should check only first one because other will be later
				if(!list.isEmpty() && list.get(0).getStart().before(now)) {
					for(i = 0; i < list.size(); i ++){
						list.get(i).setState("Completion of bets");
						compDao.update(list.get(i));
						count.incrementAndGet();
					}
					if(count.equals(number)) {
						waiting();
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
	
	private void waiting() {
		CompetitionDao dao = new CompetitionDao();
		ForecastDao forecastDao = new ForecastDao();
		try {
			List<Forecast> forecasts = forecastDao.findActualForecast();
			HashMap<String, Integer> correct = new HashMap<>();
			forecasts.forEach((f)->correct.put(f.getUserLogin(), 0));
			int count = 0;
			List<Competition> completed = new ArrayList<>();
			while(count < number.get()) {
				try {
					List<Competition> list = dao.findExpected();
					if(!list.isEmpty()) {
						count ++;
						for(int i = 0; i < list.size(); i ++) {
							list.get(i).setState("Completed");
							list.get(i).setResult(makeResult(list.get(i).getSportId()));
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
			PersonalResultDao personDao = new PersonalResultDao();
			ResultDao resultDao = new ResultDao();
			List<PersonalResult> perResult = personDao.findAll();
			List<Result> results = resultDao.findAll();
			//find amount of correct forecast for each of user
			countCorrect(completed, forecasts, correct);
			//find quantity of betters and amount of bets for each quantity of correct forecast
			for(Map.Entry<String, Integer> elem : correct.entrySet()) {
				if(elem.getValue().intValue() >= Math.ceil(number.get() / 2)) {
					//better can get gain only if he has more then half of correct forecasts
					BigDecimal bet = perResult.get(Finder.findPersonalResult(elem.getKey(), perResult)).getLastBet();	
					int index = Finder.findResult(elem.getValue().intValue(), results);
					results.get(index).addBet(bet);
					results.get(index).addBetter();
				}else {
					Predicate<PersonalResult> filter = p -> p.getUserLogin().equals(elem.getKey());
					perResult.removeIf(filter);
				}
			}
			//find coefficient 
			for(int i = 0; i < results.size(); i ++) {
				Result r = results.get(i);
				r.setCoefficient(BigDecimal.valueOf(r.getPool().doubleValue() / r.getBets().doubleValue()));
			}
			//find amount of gain for each user, who gain something
			for(int i = 0; i < perResult.size(); i ++) {
				BigDecimal coef = results.get(Finder.findResult(correct.get(perResult.get(i).getUserLogin()), results)).getCoefficient();
				BigDecimal lastBet = perResult.get(i).getLastBet();				
				perResult.get(i).setLastGain(lastBet.multiply(coef).setScale(2, RoundingMode.FLOOR));
			}
		} catch (ProjectException e) {
			LOGGER.error(e.getMessage());
		}
		
	}
	
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
	
	private void countCorrect(List<Competition> completed, List<Forecast> forecasts, HashMap<String, Integer> correct) {
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
}
