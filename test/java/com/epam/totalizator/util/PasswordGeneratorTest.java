package com.epam.totalizator.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PasswordGeneratorTest {

  @Test
  public void generate() {
	  String pass = PasswordGenerator.generate();
	  Assert.assertTrue(Validator.isAcceptablePassword(pass));
  }
}
