package com.Reports;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class SendFileEmail{
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner kb = new Scanner(System.in);
        System.out.println("enter ur id: ");
        String id ="bandgar48@gmail.com"; 
        		//kb.nextLine();
        System.out.println("enter ur pass: ");
        String password = "Swapy@2017";
        		//kb.nextLine();
        System.out.println("enter email-id to whom u want to send the mail: ");
        String toId = "swapnilband@cybage.com";
        		//kb.nextLine();
        System.out.println("enter subject for mail: ");
        String subject ="test"; 
        		//kb.nextLine();
        System.out.println("enter the content of the mail: ");
        String content = "hi"; 
        		//kb.nextLine();
        
        //open gamil
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\swapnilband\\driver\\chromedriver.exe");
        WebDriver driver= new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        
        driver.get("https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&hl=en&emr=1&elo=1");
        
        //login to gmail
        driver.findElement(By.cssSelector("#identifierId")).sendKeys(id);
        driver.findElement(By.cssSelector("#identifierNext > content > span")).click();
        driver.findElement(By.cssSelector("#password > div.aCsJod.oJeWuf > div > div.Xb9hP > input")).sendKeys(password);
        driver.findElement(By.linkText("Next")).click();
        
        //click on compose and add the to mail id, and subject
        driver.findElement(By.xpath("//div[text()='COMPOSE']")).click();
        driver.findElement(By.xpath("//form[1]//textarea[1]")).sendKeys(toId);
        driver.findElement(By.xpath("//div[@class='aoD az6']//input[@class='aoT']")).sendKeys(subject);
        
        //wirte the mail body, that mail body is under frame so 1st switch the control to frame then write
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@tabindex='1']")));
        driver.findElement(By.xpath("//body[@class='editable LW-avf']")).sendKeys(content);
        driver.switchTo().defaultContent(); // again switch back to main page
        
        //click on attachment
        driver.findElement(By.xpath("//div[@class='a1 aaA aMZ']")).click();
        //use autoit tool to attach a file
        Runtime.getRuntime().exec("C:\\selenium\\AutoIT\\fileUpload.exe");
        Thread.sleep(10000); //wait for 10sec to upload file
        
        //click on send 
        driver.findElement(By.xpath("//div[text()='Send']")).click();
        String msg = driver.findElement(By.xpath("//div[contains(text(),'Your message has been sent.')]")).getText();
        String exp = "Your message has been sent. View message";
        Assert.assertEquals(msg, exp);
        System.out.println("pass");
        driver.close();
    }
}


