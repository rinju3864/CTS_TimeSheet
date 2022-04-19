package com.cts.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Assert;
import java.util.Set;
import org.openqa.selenium.By;

import com.cts.base.BaseUI;

public class TimeSheet extends BaseUI {
	By email = By.xpath("//input[@type='email']");
	By next = By.xpath("//input[@type='submit']");
	By pass = By.name("passwd");
	By acc = By.id("user-name");
	By call = By.xpath("//div[starts-with(text(),'Call')]");
	By yes = By.xpath("//input[@value='Yes']");
	By search = By.id("searchbox");
	By icon = By.className("icomoon-search2");
	By timesheet = By.xpath("(//span[text()='Timesheet'])[1]");
	By date = By.xpath("//div[@class='dayDetail ng-scope']/div[1]");

	public void login() {
		logger = report.createTest("Login into Becognizant.");
		try {
			wait(20, email);
			driver.findElement(email).sendKeys(prop.getProperty("email"));
			System.out.println("**--Email entered--*");
			Thread.sleep(1000);
			logger.addScreenCaptureFromPath(addScreenShot());
			driver.findElement(next).click();
			wait(20, pass);
			driver.findElement(pass).sendKeys(prop.getProperty("password"));
			System.out.println("**--Password entered--*");
			Thread.sleep(1000);
			logger.addScreenCaptureFromPath(addScreenShot());
			driver.findElement(next).click();
			Thread.sleep(1000);
			driver.findElement(call).click();
			reportPass("Email and Password Verified sucessfully");
			System.out.println("**--Login success--*");
			wait(120, yes);
			driver.findElement(yes).click();
			// Verify Title
			Thread.sleep(1000);
			if (driver.getTitle().contains("Be.Cognizant"))
				// Pass
				System.out.println("*--Page title contains Be.Cognizant--*");
			else
				// Fail
				System.out.println("*--Page title doesn't contains Be.Cognizant--*");
			Thread.sleep(1000);
			Screenshot("BeCognizant");
			reportPass("Be.Cognizant Page is reached sucessfully");
			System.out.println("*--Be.Cognizant Page is reached sucessfully--*");			
			String name = driver.findElement(acc).getText();
			System.out.println("*--The name for the Acoount is: " + name + " --*");
			Thread.sleep(1000);
			logger.addScreenCaptureFromPath(addScreenShot());

			reportPass("Account Details captured ");

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void getData() {
		logger = report.createTest("Search & Navigate to ESA timesheet.");
		try {
			driver.findElement(search).sendKeys(prop.getProperty("search2"));
			System.out.println("*--ESA Timesheet entered in searchbox--*");
			reportPass("ESA Timesheet entered in searchbox");
			driver.findElement(icon).click();
			wait(20, timesheet);
			String currentHandle = driver.getWindowHandle();
			Thread.sleep(3000);
			driver.findElement(timesheet).click();
			System.out.println("*--Search Results Page is reached sucessfully--*");
			reportPass("Search Results Page is reached sucessfully");

			Set<String> handle1 = driver.getWindowHandles();
			for (String actual : handle1) {
				if (!actual.equalsIgnoreCase(currentHandle)) {
					driver.switchTo().window(actual);
				}
			}
			System.out.println("*--TimeSheet is reached sucessfully--*");
			reportPass("TimeSheet is reached sucessfully");

			Screenshot("TimeSheet");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	public void verifyDates() {
		logger = report.createTest("Verifying the dates.");
		try {
			LocalDate today = LocalDate.now();
			LocalDate week1_start, week2_start, week3_start, week1_end, week2_end, week3_end;
			String week1_txt, week2_txt, week3_txt;
			String w1, w2, w3;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			week1_start = today;

			System.out.println("\n\n*--Todays date is : " + today + "--*");
			// get this week start date (previous saturday)
			while (!week1_start.getDayOfWeek().toString().equals("SATURDAY")) {
				week1_start = week1_start.minusDays(1);
				//System.out.println(week1_start.getDayOfWeek());
			}
			week1_end = week1_start.plusDays(6);
			week2_start = week1_start.minusDays(7);
			week2_end = week2_start.plusDays(6);
			week3_start = week2_start.minusDays(7);
			week3_end = week3_start.plusDays(6);

			week1_txt = formatter.format(week1_start).toUpperCase() + " To "
					+ formatter.format(week1_end).toUpperCase();
			week2_txt = formatter.format(week2_start).toUpperCase() + " To "
					+ formatter.format(week2_end).toUpperCase();
			week3_txt = formatter.format(week3_start).toUpperCase() + " To "
					+ formatter.format(week3_end).toUpperCase();
			System.out.println("Expected week 1 :" + week1_txt);
			System.out.println("Expected week 2 :" + week2_txt);
			System.out.println("Expected week 3 :" + week3_txt);

			w1 = driver.findElement(By.id("CTS_TS_LAND_PER_DESCR30$0")).getText();
			w2 = driver.findElement(By.id("CTS_TS_LAND_PER_DESCR30$1")).getText();
			w3 = driver.findElement(By.id("CTS_TS_LAND_PER_DESCR30$2")).getText();
			System.out.println("Actual week 1 :" + w1);
			System.out.println("Actual week 2 :" + w2);
			System.out.println("Actual week 3 :" + w3);

			Assert.assertEquals(week1_txt, w1);
			Assert.assertEquals(week2_txt, w2);
			Assert.assertEquals(week3_txt, w3);

			reportPass("Dates verified successfully!");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
}
