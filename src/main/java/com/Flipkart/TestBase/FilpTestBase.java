package com.Flipkart.TestBase;

	import java.io.BufferedInputStream;
	import java.io.BufferedOutputStream;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.net.URL;
	import java.net.URLConnection;
	import java.text.SimpleDateFormat;
	import java.util.Date;
	import java.util.List;
	import java.util.NoSuchElementException;
	import java.util.concurrent.TimeUnit;
	import org.apache.poi.hssf.usermodel.HSSFCell;
	import org.apache.poi.hssf.usermodel.HSSFRow;
	import org.apache.poi.hssf.usermodel.HSSFSheet;
	import org.apache.poi.hssf.usermodel.HSSFWorkbook;
	import org.apache.poi.ss.usermodel.Cell;
	import org.apache.poi.ss.util.NumberToTextConverter;
	import org.openqa.selenium.Alert;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.openqa.selenium.ie.InternetExplorerDriver;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.annotations.DataProvider;
	import org.testng.annotations.Test;
	import com.Reports.Log;
	import com.google.common.io.Files;
	import junit.framework.Assert;
	import jxl.Sheet;
	import jxl.Workbook;
	import jxl.read.biff.BiffException;
	

public class FilpTestBase {
	public static int totalNoofCols;
	public static int totalNoofRows;
	public static String status ;
	public static WebDriver driver;
	@Test(dataProvider= "flipcart")
	
	
	public static WebDriver fn_LaunchBrowser(String TestCaseName,String Browser,String URL) throws Exception{
		try { 
		if(Browser.equalsIgnoreCase("CH")){
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\swapnilband\\driver\\chromedriver.exe");
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	else if(Browser.equalsIgnoreCase("FF")){
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\swapnilband\\driver\\geckodriver.exe");
		driver= new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	 	  driver.navigate().to(URL);
	 	  
	  } catch (Exception e) { 
		  Log.error("URL is not valid for " + TestCaseName);
		  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	       System.out.println("FAILURE: URL did not load: " + URL); 
	       status = "Failed";
	       WriteToExcelPoi.writeExcel("FlipExcel.xls","FlipSheet",TestCaseName,status);
	       takeScreenshot(TestCaseName);
	       Assert.fail();
	       throw new Exception("URL did not load");  
	       
	     }  
	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	return driver;
}

	public boolean isElementPresentCheckUsingJavaScriptExecutor(WebElement element, String TestCaseName) {
        JavascriptExecutor jse=(JavascriptExecutor) driver;
        try {
            Object obj = jse.executeScript("return typeof(arguments[0]) != 'undefined' && arguments[0] != null;",
                    element);
            if (obj.toString().contains("true")) {
                System.out.println("isElementPresentCheckUsingJavaScriptExecutor: SUCCESS");
                return true;
            } else {
                System.out.println("isElementPresentCheckUsingJavaScriptExecutor: FAIL");
            }

        } catch (NoSuchElementException e) {
        	Log.error("No element present " + TestCaseName);
            System.out.println("isElementPresentCheckUsingJavaScriptExecutor: FAIL");
        }catch (Exception e) {
        	Log.error("No element present " + TestCaseName);
            System.out.println("isElementPresentCheckUsingJavaScriptExecutor: FAIL");
        }
        return false;
    }
	
	public void assertElement(WebElement selector, String TestCaseName) throws Exception{
		try{
			status = "Passed";
			 Assert.assertEquals(true, selector.isDisplayed());
			 System.out.println("TC is " +TestCaseName + " and satus is :"+ status);
			 WriteToExcelPoi.writeExcel("FlipExcel.xls","FlipSheet",TestCaseName,status);
		 
		}
		catch (Exception e) {
			Log.error("No element present assert fail " + TestCaseName);
			status = "Failed";
			takeScreenshot(TestCaseName);
			System.out.println("TC is " +TestCaseName + " and satus is :"+ status);
			WriteToExcelPoi.writeExcel("FlipExcel.xls","FlipSheet",TestCaseName,status);
            Assert.fail();
        }
	}
	
	public void mouseHover(WebElement selector, String TestCaseName) throws Exception{
		try{
		Actions action = new Actions(driver);
		action.moveToElement(selector).perform();
		} catch (Exception e) {
			Log.error("Element is not enabled " + TestCaseName);
			status = "Failed";
			takeScreenshot(TestCaseName);
            System.out.println("Exception " + status);
        }
	}
	
	
	protected static String isEnabled(WebElement selector, String TestCaseName) throws Exception {
		try{
			boolean data = selector.isEnabled();
			System.out.println("TC is " + data);
			if(data){
					status = "Passed";
					System.out.println("TC is " + status);
				} else{
					Log.error("Element is not enabled " + TestCaseName);
					takeScreenshot(TestCaseName);
					System.out.println("TC is " + status);
				}
			Assert.assertEquals(true, selector.isDisplayed());
		}
		catch(NoSuchElementException e){
			Log.error("Element is not enabled " + TestCaseName);
			status = "Failed";
			takeScreenshot(TestCaseName);
			System.out.println("Element not present " + status);
		}
		catch (Exception e) {
			Log.error("Element is not enabled " + TestCaseName);
			status = "Failed";
			takeScreenshot(TestCaseName);
            System.out.println("Exception " + status);
        }
		return status;
	}

	// Write excel code
	  
	public static class WriteToExcelPoi {
	   
	   public static void writeExcel(String fileName,String sheetName,String Tc_ID,String status){
	    try{
	     HSSFWorkbook wb;
	     HSSFSheet sheet;
	     HSSFRow row;
	     HSSFCell cell;
	     
	     File file= new File("D://"+fileName);
	     
	     if(file.exists()==false){
	      try{
	       file.createNewFile();
	      }
	      catch(IOException e){
	       e.printStackTrace();
	      }
	     }
	     
	     FileInputStream fis=new FileInputStream(file.getAbsolutePath());
	     wb=new HSSFWorkbook(fis);
	     sheet=wb.getSheet(sheetName);
	     int rownos=sheet.getPhysicalNumberOfRows();
	     
	     for (int i = 0; i < rownos; i++) {
	      row=sheet.getRow(i);
	      
	      int cellnos=row.getPhysicalNumberOfCells();
	      for (int j = 0; j <cellnos; j++) {
	       String tc_id="";
	       cell=row.getCell(j);
	       if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK){
	        if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
	         tc_id = cell.getStringCellValue();
	        }
	        else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
	         tc_id = NumberToTextConverter.toText(cell.getNumericCellValue());
	        }
	       }
	       if(tc_id.equals(Tc_ID)){
	        cell=row.createCell(5);
	        cell.setCellType(Cell.CELL_TYPE_STRING);
	        cell.setCellValue(status);
	        
	        FileOutputStream fileOut=new FileOutputStream(file.getAbsoluteFile());
	        wb.write(fileOut);
	        fileOut.flush();
	        fileOut.close();
	     }
	  }
	}   
}
	    catch(Exception ex){
	    Log.error("Excel read error " );
	     ex.printStackTrace();
	  }
	}
}	
		// Read excel code.
		public static String[][] getExcelData(String fileName,String sheetName){
			String[][] arrayExcelData = null;
			
			try{
				FileInputStream fs=new FileInputStream("D://FlipExcel.xls");
				
				Workbook wb=Workbook.getWorkbook(fs);
				Sheet sh=wb.getSheet(sheetName);
				
				
				System.out.println("Current col and row from the sheet: " +sheetName);
				totalNoofCols=sh.getColumns();
				totalNoofRows=sh.getRows();
				
				System.out.println("the current col abd row number: col "+totalNoofCols+ "Row:"+totalNoofRows);
				arrayExcelData= new String[totalNoofRows-1][totalNoofCols];
				
				for (int i = 1; i < totalNoofRows; i++) {
					for (int j = 0; j < totalNoofCols; j++) {
						arrayExcelData[i-1][j]=sh.getCell(j, i).getContents();
						System.out.println(sh.getCell(j, i).getContents());
					}
				}
			}
			
			catch(FileNotFoundException e){
				Log.error("File not found ");
				e.printStackTrace();
			}
			
			catch(IOException e){
				Log.error("IO expection found");
				e.printStackTrace();
				e.printStackTrace();
			}
			
			catch(BiffException e){
				Log.error("IO expection found");
				e.printStackTrace();
			}
			return arrayExcelData;
		}
		public void waitForElementToBeVisible(By selector) throws Exception {  
		    try {  
		       WebDriverWait wait = new WebDriverWait(driver, 5);  
		       wait.until(ExpectedConditions.presenceOfElementLocated(selector));  
	     } catch (Exception e) {  
	    	 Log.error("Element is not visible");
	    	 throw new NoSuchElementException(String.format("The following element was not visible: %s", selector));  
     }  
   }
		public static String getPopupMessage(final WebDriver driver) throws Exception {
			 String message = null;
			 try {
				 Alert alert = driver.switchTo().alert();
				 message = alert.getText();
				 alert.accept();
			 } catch (Exception e) {
				 Log.error("Pop up error.");
				 message = null;
			 }
			 System.out.println("message"+message);
			 return message;
		}
			
			//It will cancel pop-up message.
			public static String cancelPopupMessageBox(final WebDriver driver) throws Exception {
			 String message = null;
			 try {
				 Alert alert = driver.switchTo().alert();
				 message = alert.getText();
				 alert.dismiss();
			 } catch (Exception e) {
				 Log.error("Cancel pop up error.");
				 message = null;
			 }
			 return message;
		}
			//Reading ToolTip text in in Selenium-WebDriver
			public static String tooltipText(WebDriver driver, By locator) throws Exception{
				try {
					String tooltip = driver.findElement(locator).getAttribute("title");
					return tooltip;
			} catch (Exception e) {
				Log.error("Tooltip error.");
					 return null;
				 }
			}
	 
			public static void selectRadioButton(WebDriver driver, By locator, String value) throws Exception{ 
				try {
					List<WebElement> select = driver.findElements(locator);
					for (WebElement element : select)
					{
						if (element.getAttribute("value").equalsIgnoreCase(value)){
						element.click();
						}
					}
				} catch (Exception e) {
					Log.error("Radio button error.");
					 return;
				}
			}
			
			//Selecting searched dropdown in Selenium-WebDriver
			public static void selectSearchDropdown(WebDriver driver, By locator, String value) throws Exception{
				try {
				driver.findElement(locator).click();
				driver.findElement(locator).sendKeys(value);
				driver.findElement(locator).sendKeys(Keys.TAB);
				} catch (Exception e) {
					Log.error("select dropdown error.");
			}
		}
			
			//Uploading file using  Selenium-WebDriver
			public static void uploadFile(WebDriver driver, By locator, String path) throws Exception{
				try {
					driver.findElement(locator).sendKeys(path);
				} catch (Exception e) {
					Log.error("UploadFile error.");
			}
		}
			
			//Downloading file in Selenium-WebDriver
			public static void dragAndDrop(WebElement source, WebElement target, WebDriver driver ) throws Exception{
				try {
					WebElement element = driver.findElement(By.name("source"));
					WebElement newTarget = driver.findElement(By.name("target"));
					(new Actions(driver)).dragAndDrop(element, newTarget).perform();
			} catch (Exception e) {
				Log.error("dragAndDrop error.");
				
			}
		}	
	
			public static void takeScreenshot(String TestCaseName) throws Exception {
		       try{ Date date = new Date();
		        SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		        String date_to_string = dateformatyyyyMMdd.format(date);
		        File dir = new File(date_to_string);
		        dir.mkdir( );
		        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		        Files.copy(scrFile, new File("C:\\Users\\swapnilband\\Documents\\workspace-sts-3.7.0.RELEASE\\flipTest\\"+ dir +"\\" + TestCaseName  + ".png"));
		       } catch (Exception e) {
					Log.error("takeScreenshot error.");
		    }
    }
	
	public static void downloadFile(String href, String fileName) throws Exception{
		URL url = null;
		URLConnection con = null;
		int i;
		try {
			url = new URL(href);
			con = url.openConnection();
			// Here we are specifying the location where we really want to save the file.
			File file = new File(".//OutputData//" + fileName);
			BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(
			new FileOutputStream(file));
			while ((i = bis.read()) != -1) {
			bos.write(i);
			}
			bos.flush();
			bis.close();
		} catch (Exception e) {
			return;
		}
	}	
	
	@DataProvider(name="flipcart")
    public static Object[][] loginData(){
     
     Object[][] arrayObject=getExcelData("FlipExcel", "FlipSheet");
     return arrayObject;
     }	
	 protected static void waitForElementToBeClickable(WebElement selector) {
	 WebDriverWait wait = new WebDriverWait(driver, 15);
	    wait.until(ExpectedConditions.elementToBeClickable(selector));
}
	 
	 private static void forwardNavigation(String selector) throws InterruptedException {
	 WebDriver driver =new FirefoxDriver();
	 driver.get("http://seleniumhq.org/");
	 driver.findElement(By.linkText(selector)).click();
	 Thread.sleep(3000);            //delay
	
	 driver.navigate().forward();
}
	 
	 private static void backNavigation(String selector) throws InterruptedException {
		 WebDriver driver =new FirefoxDriver();
		 driver.get("http://seleniumhq.org/");
		 driver.findElement(By.linkText(selector)).click();
		 Thread.sleep(3000);            //delay
		driver.navigate().back();
	 }	
}