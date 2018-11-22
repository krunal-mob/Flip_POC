package com.Flipkart.Pages;

	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.FindBy;
	import org.openqa.selenium.support.PageFactory;
	import com.Flipkart.TestBase.FilpTestBase;
	import com.Reports.Log;

public class LoginPage extends FilpTestBase{
	
	@FindBy(css="._2zrpKA")
	private WebElement user_Name;
	
	@FindBy(css="._2zrpKA._3v41xv")
	private WebElement user_password;
	
	@FindBy(css="body > div.mCRfo9 > div > div > div > div > div.Km0IJL.col.col-3-5 > div > form > div._1avdGP > button")
	private WebElement submit;
	
	@FindBy(css="#container > div > header > div._1tz-RS > div > div > div > div._1Wr4v5 > div:nth-child(1) > div > div > div > span > div")
	private WebElement my_Account;
	
	 public LoginPage() {
		PageFactory.initElements(driver,this);
	}
	 
	public void flipLogin(String username , String password, String TestCaseName) throws Exception{
		user_Name.click();
		user_Name.sendKeys(username);
		Log.info("Username is entered for test case " + TestCaseName);
		user_password.click();
		user_password.sendKeys(password);
		Log.info("Password is entered for test case " + TestCaseName);
		submit.click();
		assertElement(my_Account, TestCaseName);
		Log.info("User is logged in for testcase " +TestCaseName);
	}
}