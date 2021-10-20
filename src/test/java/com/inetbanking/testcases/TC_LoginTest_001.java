package com.inetbanking.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.inetbanking.pageobjects.LoginPage;

public class TC_LoginTest_001 extends BaseClass{
	
	
	@Test
	public void loginTest() throws InterruptedException {
		
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setUserName(username);
		logger.info("entered username");
		loginPage.setPassword(password);
		logger.info("entered password");
		loginPage.clickSubmit();
		logger.info("clicked submit button");
		if(driver.getTitle().trim().equalsIgnoreCase("Guru99 Bank Manager HomePage")){
			logger.info("test got passed");
			Assert.assertTrue(true);			
		}else {
			captureScreen(driver, "loginTest");
			logger.info("test got failed");
			Assert.assertTrue(false);
		}
	}

}
