package com.epam.totalizator.util;

import org.testng.annotations.Test;

import com.epam.totalizator.entity.PersonalResult;
import com.epam.totalizator.entity.Result;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;

public class FinderTest {

  @DataProvider(name = "perResult")
  public Object[][] resultProvider() {
	  List<PersonalResult> list = new ArrayList<>();
	  list.add(new PersonalResult("mari", BigDecimal.valueOf(200.0), BigDecimal.ZERO, BigDecimal.valueOf(400.0), BigDecimal.ZERO));
	  list.add(new PersonalResult("alex", BigDecimal.valueOf(90.0), BigDecimal.ZERO, BigDecimal.valueOf(400.0), BigDecimal.ZERO));
	  list.add(new PersonalResult("blacky", BigDecimal.valueOf(200.0), BigDecimal.ZERO, BigDecimal.valueOf(400.0), BigDecimal.ZERO));
	  list.add(new PersonalResult("noir", BigDecimal.valueOf(200.0), BigDecimal.ZERO, BigDecimal.valueOf(400.0), BigDecimal.ZERO));
	  list.add(new PersonalResult("irvin", BigDecimal.valueOf(200.0), BigDecimal.ZERO, BigDecimal.valueOf(400.0), BigDecimal.ZERO));
	  list.add(new PersonalResult("raijin", BigDecimal.valueOf(200.0), BigDecimal.ZERO, BigDecimal.valueOf(400.0), BigDecimal.ZERO));
	  
	  return new Object[][] {
		  new Object[] { 0, list, "mari" },
		  new Object[] { 5, list, "raijin" },
		  new Object[] {-1, list, "waiss"}
	  };
  }

  @DataProvider(name = "result")
  public Object[][] perResultProvider() {
	  List<Result> list = new ArrayList<>();
	  list.add(new Result(8, 0, BigDecimal.ZERO, 25, BigDecimal.valueOf(200.0), BigDecimal.ZERO));
	  list.add(new Result(9, 0, BigDecimal.ZERO, 20, BigDecimal.valueOf(200.0), BigDecimal.ZERO));
	  list.add(new Result(10, 0, BigDecimal.ZERO, 10, BigDecimal.valueOf(200.0), BigDecimal.ZERO));
	  list.add(new Result(11, 0, BigDecimal.ZERO, 10, BigDecimal.valueOf(200.0), BigDecimal.ZERO));
	  list.add(new Result(12, 0, BigDecimal.ZERO, 10, BigDecimal.valueOf(200.0), BigDecimal.ZERO));
	  list.add(new Result(13, 0, BigDecimal.ZERO, 10, BigDecimal.valueOf(200.0), BigDecimal.ZERO));
	  list.add(new Result(14, 0, BigDecimal.ZERO, 10, BigDecimal.valueOf(200.0), BigDecimal.ZERO));
	  list.add(new Result(15, 0, BigDecimal.ZERO, 5, BigDecimal.valueOf(200.0), BigDecimal.ZERO));
	  
	  return new Object[][] {
		  new Object[] { 0, list, 8 },
		  new Object[] { 5, list, 13 },
		  new Object[] {-1, list, 7}
	  };
  }
  
  @Test(dataProvider = "perResult")
  public void findPersonalResult(Integer index, List<PersonalResult> list, String login) {
	  org.apache.log4j.Logger.getRootLogger().info(index.intValue() + "-" + login + "\n");
	  Assert.assertEquals(Finder.findPersonalResult(login, list), index.intValue());
  }

  @Test(dataProvider = "result")
  public void findResult(Integer index, List<Result> list, Integer correct) {
	  Assert.assertEquals(Finder.findResult(correct.intValue(), list), index.intValue());
  }
}
