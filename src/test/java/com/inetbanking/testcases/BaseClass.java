package com.inetbanking.testcases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.inetbanking.utilities.ReadConfig;

public class BaseClass {
	public ReadConfig readConfig = new ReadConfig();
	String baseUrl = readConfig.getApplicationURL();
	String username =readConfig.getUsername();
	String password = readConfig.getPassword();
	public static WebDriver driver = null;
	public static Logger logger;


	@Parameters("browser")
	@BeforeClass
	public void setup(String br) {		
		logger= Logger.getLogger("ebanking");
		PropertyConfigurator.configure("log4j.properties");

		if(br.equalsIgnoreCase("chrome")) 
		{
			System.setProperty("webdriver.chrome.driver",readConfig.getChromePath());
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");
			driver = new ChromeDriver(options);
		}
		else if(br.equalsIgnoreCase("ie")) 
		{
			System.setProperty("webdriver.ie.driver",readConfig.getIEPath());
			driver = new InternetExplorerDriver();
		}
		else if(br.equalsIgnoreCase("firefox")) 
		{
			System.setProperty("webdriver.gecko.driver",readConfig.getFirefoxPath());
			driver = new FirefoxDriver();
		}
		if(driver==null) {
			//			exit code
		}
		logger.info("logging the site");
		driver.get(baseUrl);
		driver.manage().window().maximize();

	}

	@AfterClass
	public void tearDown() {
		if(driver!=null) {
			driver.close();
			driver.quit();
		}
	}
	
	public static void captureScreen(WebDriver driver,String tname) {
		try {
			TakesScreenshot screenshot = (TakesScreenshot)driver;
			File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
			File destFile = new File(System.getProperty("user.dir")+"/Screenshots/"+tname+".png");		
			FileUtils.copyFile(srcFile, destFile);
			logger.info("screenshot captured");
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
	public static boolean isAlertPresent() //user defined method created to check alert is presetn or not
	{
		try
		{
		driver.switchTo().alert();
		return true;
		}
		catch(NoAlertPresentException e)
		{
			return false;
		}
		
	}
	
	public String randomestring()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(8);
		return(generatedstring);
	}
	
	public static String randomeNum() {
		String generatedString2 = RandomStringUtils.randomNumeric(4);
		return (generatedString2);
	}

}
