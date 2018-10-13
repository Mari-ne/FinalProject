package com.epam.totalizator.util;

import java.util.List;

import com.epam.totalizator.entity.PersonalResult;
import com.epam.totalizator.entity.Result;

/**
 * Service class, that find index of element in lists. 
 * Depending on list content, require different unique parameter
 * with what it compare.
 * All methods return -1, if element wasn't found
 */
public class Finder {

	public static int findPersonalResult(String login, List<PersonalResult> perResults) {
		int index = -1;
		for(int i = 0; i < perResults.size(); i ++) {
			if(perResults.get(i).getUserLogin().equals(login)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public static int findResult(int corrects, List<Result> results) {
		int index = -1;
		for(int i = 0; i < results.size(); i ++) {
			if(results.get(i).getCorrect() == corrects) {
				index = i;
				break;
			}
		}
		return index;
	}
}
