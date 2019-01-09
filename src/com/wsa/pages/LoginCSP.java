package com.wsa.pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.wsa.framework.CommonLib;
import com.wsa.framework.HashMapNew;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginCSP {

	private By editBox_userid = By.xpath("//*[@id='lrrFormId']//input[@name='userid']");
	private By editBox_password = By.xpath("//*[@id='lrrFormId']//input[@name='password']");
	private By button_submit = By.xpath("//*[@id='lrrFormId']//input[@name='btnSubmit']");
	private By button_success = By.xpath("//*[@id='srv_successok']//input[@name='successOK']");
	
	public WebDriver oDriver;
	public ExtentReports oExtentReports;
	public ExtentTest oExtentTest;
	public HashMapNew dictionary;

	public LoginCSP(WebDriver oDriver, ExtentReports oExtentReports, ExtentTest oExtentTest, HashMapNew dictionary) {
		this.oDriver = oDriver;
		this.oExtentReports = oExtentReports;
		this.oExtentTest = oExtentTest;
		this.dictionary = dictionary;
	}
	
	public boolean fLogin()  throws InterruptedException{
		oDriver.switchTo().frame(0);
		
		WebDriverWait wait=new WebDriverWait(oDriver, 20);
		WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(editBox_userid));
		oExtentTest.log(LogStatus.INFO, "Starting Login Activity");
		//Login with email is displayed
		if(e.isDisplayed() && e.isEnabled()){
			oExtentTest.log(LogStatus.INFO, "Entering Credentials");
			CommonLib.sendKeys(oDriver, editBox_userid, dictionary.get("USERID"));
			CommonLib.sendKeys(oDriver, editBox_password, dictionary.get("PASSWORD"));
			oDriver.findElement(button_submit).click();
			Thread.sleep(5000);
		}else{
			oExtentTest.log(LogStatus.FAIL, "Login screen not working properly!");
			return false;
		}

		oDriver.switchTo().defaultContent();
		oDriver.switchTo().frame(0);
		WebElement b = wait.until(ExpectedConditions.visibilityOfElementLocated(button_success));
		if(b.isDisplayed() && b.isEnabled()){
			oExtentTest.log(LogStatus.INFO, "Clicking Success button");
			oDriver.findElement(button_success).click();
			Thread.sleep(5000);
		}else{
			oExtentTest.log(LogStatus.FAIL, "Login screen not working properly!");
			return false;
		}
		
		return true;
	}

}
