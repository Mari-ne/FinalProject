package com.epam.totalizator.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.epam.totalizator.dao.CompetitionDao;
import com.epam.totalizator.dao.ForecastDao;
import com.epam.totalizator.entity.Competition;
import com.epam.totalizator.entity.Forecast;
import com.epam.totalizator.exception.ProjectException;
import com.epam.totalizator.util.ServiceThread;

/**
 * Class of logic layer of application.
 * 
 * Process all logic connected with information about competitions.
 */
public class CompetitionService {

	private static CompetitionDao compDao = new CompetitionDao();
	private static ForecastDao forecastDao = new ForecastDao();
	
	public static List<Competition> constructMainTable(String lang) throws ProjectException{
		return compDao.findForLastMounth(lang.toUpperCase());
	}
	
	public static boolean addCompetition(int sport, int team1, int team2, Timestamp start, Timestamp finish) throws ProjectException{
		Competition compet = new Competition();
		compet.setTeam1Id(team1);
		compet.setTeam2Id(team2);
		compet.setSportId(sport);
		compet.setStart(start);
		compet.setFinish(finish);
		boolean result = compDao.create(compet);
		if(compDao.findBettable().size() == ServiceThread.number.get()) {
			ResultService.refreshResult();
		}
		return result;
	}
	
	public static List<Competition> getBettable(String lang) throws ProjectException{
		return compDao.findBettableWithName(lang.toUpperCase());
	}
	
	public static List<Forecast> getForecastById(int competitionId, String lang) throws ProjectException{
		return forecastDao.findByCompetitionID(competitionId, lang.toUpperCase());
	}
	

	
	public static boolean hasMadeBet(String login) throws ProjectException {
		boolean result = false;
		List<Forecast> list = forecastDao.findByLogin(login);
		Optional<Competition> comp = compDao.findById(list.get(list.size() - 1).getCompetitionId());
		if(comp.isPresent() && comp.get().getState().equals("Acceptance of bets")) {
			result = true;
		}
		return result;
	}
}
