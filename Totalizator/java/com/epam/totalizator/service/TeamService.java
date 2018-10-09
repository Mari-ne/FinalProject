package com.epam.totalizator.service;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import com.epam.totalizator.dao.SportTeamDao;
import com.epam.totalizator.dao.TeamVSDao;
import com.epam.totalizator.entity.Sport;
import com.epam.totalizator.entity.SportTeam;
import com.epam.totalizator.entity.TeamVs;
import com.epam.totalizator.util.ProjectException;

public class TeamService {
	
	private static TeamVSDao vsDao = new TeamVSDao();
	private static SportTeamDao teamDao = new SportTeamDao();
	
	public static List<TeamVs> getStatistic(Locale lang) throws ProjectException{
		return vsDao.findAllWithNames(lang.getLanguage().toUpperCase());
	}
	
	public static List<SportTeam> getTeams(Locale lang) throws ProjectException{
		return teamDao.findAllWithName(lang.getLanguage().toUpperCase());
	}
	
	public static List<Sport> getSports(Locale lang) throws ProjectException{
		return teamDao.findSport(lang.getLanguage().toUpperCase());
	}
	
	public static boolean addTeam(int sport, String[] names) throws ProjectException{
		SportTeam team = new SportTeam();
		team.setSportId(sport);
		boolean result = teamDao.create(team);
		if(result) {
			result = result && teamDao.createLocalNames(team.getId(), names);
			if(result) {
				org.apache.log4j.Logger.getRootLogger().info("hi\n");
				List<SportTeam> teams = teamDao.findBySportId(sport);
				Predicate<SportTeam> predicate = t-> t.getId() == team.getId();
				teams.removeIf(predicate);
				for(SportTeam t : teams) {
					TeamVs vs = new TeamVs();
					vs.setTeam2Id(team.getId());
					vs.setTeam1Id(t.getId());
					result = result && vsDao.create(vs);
				}
			}
		}
		return result;
		
	}
}
