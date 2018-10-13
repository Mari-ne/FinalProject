package com.epam.totalizator.service;

import java.math.BigDecimal;
import java.util.List;

import com.epam.totalizator.dao.ResultDao;
import com.epam.totalizator.entity.Result;
import com.epam.totalizator.exception.ProjectException;

/**
 * Class of logic layer of application.
 * 
 * Process all logic connected with result of bets.
 */
public class ResultService {

	private static ResultDao resDao = new ResultDao();
	
	public static void refreshResult() throws ProjectException{
		List<Result> result = resDao.findAll();
		result.forEach((r)->{
			r.setBets(BigDecimal.ZERO);
			r.setBetters(0);
			r.setCoefficient(BigDecimal.ZERO);
			r.setPool(BigDecimal.ZERO);			
		});
		for(Result res : result)
			resDao.update(res);
	}
	
	public static void changePool(String[] parts) throws ProjectException{
		List<Result> result = resDao.findAll();
		BigDecimal pool = BigDecimal.ZERO;
		result.forEach((r)->pool.add(r.getPool()));
		for(int i = 0; i < parts.length; i ++) {
			int percent = Integer.parseInt(parts[i]);
			BigDecimal newPool = pool.multiply(BigDecimal.valueOf(percent)).divide(BigDecimal.valueOf(100));
			result.get(i).setPool(newPool);
			result.get(i).setPoolPart(percent);
			resDao.updatePoolPart(result.get(i));
		}
	}
	
	public static List<Result> getResult() throws ProjectException{
		return resDao.findAll();
	}
}
