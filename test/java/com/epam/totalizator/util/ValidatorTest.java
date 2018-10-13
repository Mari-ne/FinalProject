package com.epam.totalizator.util;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

public class ValidatorTest {

  @DataProvider(name = "password")
  public Object[][] passwordProvider() {
    return new Object[][] {
      new Object[] { "as", Boolean.FALSE },
      new Object[] { "ashdgashdgahd", Boolean.FALSE },
      new Object[] { "מûגנןאûגנא",  Boolean.FALSE},
      new Object[] { "As1", Boolean.FALSE},
      new Object[] { "Oplsa123", Boolean.TRUE},
      new Object[] { "Ghas87hshd9ckns8anHsXIJ87", Boolean.FALSE}
    };
  }

  @DataProvider(name = "card")
  public Object[][] cardProvider() {
    return new Object[][] {
      new Object[] { "1234123412341234", Boolean.FALSE },
      new Object[] { "1234-1234-1234-1234", Boolean.TRUE }
    };
  }
  
  @DataProvider(name = "email")
  public Object[][] emailProvider() {
    return new Object[][] {
      new Object[] { "@dnail.com", Boolean.FALSE },
      new Object[] { "assssd.com", Boolean.FALSE },
      new Object[] { "asdd@gamil", Boolean.FALSE},
      new Object[] { "iusd@mikl.in", Boolean.TRUE}
    };
  }
  
  @DataProvider(name = "login")
  public Object[][] loginProvider() {
    return new Object[][] {
      new Object[] { "login", Boolean.TRUE },
      new Object[] { "nan", Boolean.FALSE },
      new Object[] { "nan1", Boolean.TRUE},
      new Object[] { "ui-lon", Boolean.TRUE},
      new Object[] { "qwertyuioplkjhgfdsazx", Boolean.FALSE}
    };
  }
  
  @Test(dataProvider = "card")
  public void isAcceptableCard(String card, Boolean expected) {
	  Assert.assertEquals(Validator.isAcceptableCard(card), expected.booleanValue());
  }

  @Test(dataProvider = "email")
  public void isAcceptableEmail(String email, Boolean expected) {
	  Assert.assertEquals(Validator.isAcceptableEmail(email), expected.booleanValue());
  }

  @Test(dataProvider = "login")
  public void isAcceptableLogin(String login, Boolean expected) {
	  Assert.assertEquals(Validator.isAcceptableLogin(login), expected.booleanValue());
  }

  @Test(dataProvider = "password")
  public void isAcceptablePassword(String password, Boolean expected) {
	  Assert.assertEquals(Validator.isAcceptablePassword(password), expected.booleanValue());
  }
}
