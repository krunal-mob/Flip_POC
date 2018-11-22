package flipTest;

	import org.testng.annotations.AfterMethod;
	import org.testng.annotations.DataProvider;
	import org.testng.annotations.Test;
	import com.Flipkart.Pages.LoginPage;
	import com.Flipkart.Pages.Logout;
	import com.Flipkart.TestBase.FilpTestBase;

public class TestCases extends FilpTestBase{
	
	LoginPage loginPage;
	Logout logoutPage;
	@Test(dataProvider= "flipcart")
	
	 public void Testcase(String TestCaseName,String UserName,String Password, String Browser,String Url,String Results) throws Exception{
		fn_LaunchBrowser(TestCaseName,Browser,Url);
		loginPage =new LoginPage(); 
		loginPage.flipLogin(UserName, Password, TestCaseName);
		logoutPage = new Logout();
		logoutPage.flipLogout(TestCaseName);
	}
	
	@AfterMethod 
	public void quit() throws InterruptedException{
		driver.close();
		// driver.quit();
	}
	
	@DataProvider(name="flipcart")
    public static Object[][] loginData(){
     
     Object[][] arrayObject=getExcelData("FlipExcel", "FlipSheet");
     return arrayObject;
     }	
}