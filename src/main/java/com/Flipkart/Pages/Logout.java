package com.Flipkart.Pages;

	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.FindBy;
	import org.openqa.selenium.support.PageFactory;
	import com.Reports.Log;
	import com.Flipkart.TestBase.FilpTestBase;

public class Logout extends FilpTestBase {
	@FindBy(css="._2cyQi_")
	private WebElement my_Account;
	
	@FindBy(linkText="Logout")
	private WebElement logout;
	

	@FindBy(linkText="Login & Signup")
	private WebElement login; 
	
	 public Logout() {
			PageFactory.initElements(driver,this);
		}
	
	public void flipLogout(String TestCaseName) throws Exception{
		mouseHover(my_Account, TestCaseName);
		Log.info("User is able to hover on my account " + TestCaseName);
		logout.click();
		Log.info("User is able to click on logout " + TestCaseName);
		System.out.println("user is logout");
		assertElement(login, TestCaseName);
		Log.info("User is logout successfully " + TestCaseName);
	}
}