package com.epam.totalizator.service;

import java.util.List;
import java.util.function.Predicate;

import com.epam.totalizator.dao.SportTeamDao;
import com.epam.totalizator.dao.TeamVSDao;
import com.epam.totalizator.entity.Sport;
import com.epam.totalizator.entity.SportTeam;
import com.epam.totalizator.entity.TeamVs;
import com.epam.totalizator.exception.ProjectException;

/**
 * Class of logic layer of application.
 * 
 * Process all logic connected with information about teams.
 */
public class TeamService {
	
	private static TeamVSDao vsDao = new TeamVSDao();
	private static SportTeamDao teamDao = new SportTeamDao();
	
	public static List<TeamVs> getStatistic(String lang) throws ProjectException{
		return vsDao.findAllWithNames(lang.toUpperCase());
	}
	
	public static List<SportTeam> getTeams(String lang) throws ProjectException{
		return teamDao.findAllWithName(lang.toUpperCase());
	}
	
	public static List<Sport> getSports(String lang) throws ProjectException{
		return teamDao.findSport(lang.toUpperCase());
	}
	
	public static boolean addTeam(int sport, String[] names) throws ProjectException{
		SportTeam team = new SportTeam();
		team.setSportId(sport);
		boolean result = teamDao.create(team);
		if(result) {
			result = result && teamDao.createLocalNames(team.getId(), names);
			if(result) {
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
