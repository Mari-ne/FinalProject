package com.epam.totalizator.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.totalizator.entity.Competition;
import com.epam.totalizator.entity.Forecast;

public class ServiceThreadTest {

	List<Competition> completed;
	HashMap<String, Integer> correct;
	
	@BeforeClass
	public void fillCompleted() {
		completed = new ArrayList<>();
		completed.add(new Competition(10, 2, 8, 10, null, null, "Completed", "20:80"));
		completed.add(new Competition(11, 3, 15, 16, null, null, "Completed", "1:3"));
		completed.add(new Competition(12, 1, 1, 2, null, null, "Completed", "0:0"));
		completed.add(new Competition(13, 2, 9, 7, null, null, "Completed", "56:87"));
		completed.add(new Competition(14, 2, 8, 7, null, null, "Completed", "97:89"));
		completed.add(new Competition(15, 1, 4, 2, null, null, "Completed", "2:0"));
		completed.add(new Competition(16, 2, 8, 12, null, null, "Completed", "90:90"));
		completed.add(new Competition(17, 3, 14, 17, null, null, "Completed", "2:4"));
		completed.add(new Competition(18, 1, 1, 4, null, null, "Completed", "1:1"));
		completed.add(new Competition(19, 1, 3, 5, null, null, "Completed", "2:3"));
		completed.add(new Competition(20, 1, 1, 5, null, null, "Completed", "1:0"));
		completed.add(new Competition(21, 3, 15, 17, null, null, "Completed", "2:0"));
		completed.add(new Competition(22, 2, 10, 11, null, null, "Completed", "73:81"));
		completed.add(new Competition(23, 1, 3, 2, null, null, "Completed", "2:2"));
		completed.add(new Competition(24, 1, 2, 5, null, null, "Completed", "1:2"));
	}
	
	@DataProvider(name = "forecasts")
	public Object[][] Provider(){	
		List<Forecast> list = new ArrayList<>();
		list.add(new Forecast("mari", 10, "2"));
		list.add(new Forecast("mari", 11, "2"));
		list.add(new Forecast("mari", 12, "2"));
		list.add(new Forecast("mari", 13, "2"));
		list.add(new Forecast("mari", 14, "2"));
		list.add(new Forecast("mari", 15, "2"));
		list.add(new Forecast("mari", 16, "2"));
		list.add(new Forecast("mari", 17, "2"));
		list.add(new Forecast("mari", 18, "2"));
		list.add(new Forecast("mari", 19, "2"));
		list.add(new Forecast("mari", 20, "2"));
		list.add(new Forecast("mari", 21, "2"));
		list.add(new Forecast("mari", 22, "2"));
		list.add(new Forecast("mari", 23, "2"));
		list.add(new Forecast("mari", 24, "2"));
		
		List<Forecast> list2 = new ArrayList<>();
		list2.add(new Forecast("mari", 10, "1"));
		list2.add(new Forecast("mari", 11, "1"));
		list2.add(new Forecast("mari", 12, "1"));
		list2.add(new Forecast("mari", 13, "1"));
		list2.add(new Forecast("mari", 14, "1"));
		list2.add(new Forecast("mari", 15, "1"));
		list2.add(new Forecast("mari", 16, "1"));
		list2.add(new Forecast("mari", 17, "1"));
		list2.add(new Forecast("mari", 18, "1"));
		list2.add(new Forecast("mari", 19, "1"));
		list2.add(new Forecast("mari", 20, "1"));
		list2.add(new Forecast("mari", 21, "1"));
		list2.add(new Forecast("mari", 22, "1"));
		list2.add(new Forecast("mari", 23, "1"));
		list2.add(new Forecast("mari", 24, "1"));
		
		List<Forecast> list3 = new ArrayList<>();
		list3.add(new Forecast("mari", 10, "1"));
		list3.add(new Forecast("mari", 11, "2"));
		list3.add(new Forecast("mari", 12, "2"));
		list3.add(new Forecast("mari", 13, "x"));
		list3.add(new Forecast("mari", 14, "x"));
		list3.add(new Forecast("mari", 15, "x"));
		list3.add(new Forecast("mari", 16, "1"));
		list3.add(new Forecast("mari", 17, "1"));
		list3.add(new Forecast("mari", 18, "2"));
		list3.add(new Forecast("mari", 19, "1"));
		list3.add(new Forecast("mari", 20, "x"));
		list3.add(new Forecast("mari", 21, "x"));
		list3.add(new Forecast("mari", 22, "1"));
		list3.add(new Forecast("mari", 23, "2"));
		list3.add(new Forecast("mari", 24, "1"));
		
		return new Object[][] {
			new Object[] { list, 7},
			new Object[] { list2, 4},
			new Object[] { list3, 1}
		};
	}
	
	@BeforeMethod
	public void fillCorrect() {
		correct = new HashMap<>();
		correct.put("mari", 0);
	}
	
  @Test(dataProvider = "forecasts")
  public void countCorrect(List<Forecast> list, Integer expected) {
	  ServiceThread.countCorrect(completed, list, correct);
	  int actual = correct.get("mari").intValue();
	  Assert.assertEquals(actual, expected.intValue());
  }
}
