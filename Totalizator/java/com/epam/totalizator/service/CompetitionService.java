package com.epam.totalizator.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import com.epam.totalizator.dao.CompetitionDao;
import com.epam.totalizator.entity.Competition;
import com.epam.totalizator.util.ProjectException;
import com.epam.totalizator.util.ServiceThread;

public class CompetitionService {

	private static CompetitionDao compDao = new CompetitionDao();
	
	public static List<Competition> constructMainTable(Locale lang) throws ProjectException{
		return compDao.findForLastMounth(lang.getLanguage().toUpperCase());
	}
	
	public static boolean addCompetition(int sport, int team1, int team2, Timestamp start, Timestamp finish) throws ProjectException{
		Competition compet = new Competition();
		compet.setTeam1Id(team1);
		compet.setTeam2Id(team2);
		compet.setSportId(sport);
		compet.setStart(start);
		compet.setFinish(finish);
		if(compDao.findBettable().size() == ServiceThread.number.get()) {
			ResultService.refreshResult();
		}
		return compDao.create(compet);
	}
	
	public static List<Competition> getBettable(Locale lang) throws ProjectException{
		return compDao.findBettableWithName(lang.getLanguage().toUpperCase());
	}
}
