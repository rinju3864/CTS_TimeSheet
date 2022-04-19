package com.cts.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import com.aventstack.extentreports.Status;
import com.cts.utils.ExtentReportManager;
import com.google.common.io.Files;

public class BaseUI {

	public static WebDriver driver;
	public static Properties prop;
	JavascriptExecutor js;
	public static WebDriverWait wait;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;
	public File file;
	public XSSFWorkbook workbook;
	public XSSFSheet sh;
	public String userDir = System.getProperty("user.dir");
	public FileOutputStream fos;
	public static int ssNo = 1;
	public String imgPath;

// To call different browsers
	public void invokeBrowser() throws Exception {
		prop = new Properties();

		try {
			prop.load(new FileInputStream("src/main/java/com/cts/config/config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// To Open Chrome Browser
		if (prop.getProperty("browserName").matches("chrome")) {
			System.setProperty("webdriver.chrome.driver", userDir + prop.getProperty("ChromeDriverPath"));
			driver = new ChromeDriver();
			// driver = new ChromeDriver();
		}

		// To Open Firefox Browser
		if (prop.getProperty("browserName").matches("firefox")) {
			System.setProperty("webdriver.gecko.driver", userDir + prop.getProperty("firefoxDriverPath"));
			driver = new FirefoxDriver();
		}

		// To maximize the Browser Window
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

	}

	// To open the Main Page URL
	public void openURL() {
		driver.get(prop.getProperty("websiteURLKey"));
	}

	// Function to show the failed test cases in the report
	public void reportFail(String report) {
		logger.log(Status.FAIL, report);
	}

	// Function to show the passed test cases in the report
	public void reportPass(String report) throws IOException {
		// logger.addScreenCaptureFromPath(addScreenShot());
		logger.log(Status.PASS, report, MediaEntityBuilder.createScreenCaptureFromPath(addScreenShot()).build());

	}

	/* * This method is used to add screenshot in Extent Report */
	public String addScreenShot() {
		imgPath = captureScreen();
		return (imgPath);

	}

	/* This method is used to capture screen */
	public String captureScreen() {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Date date = new Date();

		String d = date.toString();
		d = d.replaceAll(" ", "");
		d = d.replaceAll(":", "");
		String imgPath = userDir + "\\ExtentReports\\ExScreenshots\\scrnSht_" + ssNo + "_" + d + ".png";
		try {
			FileHandler.copy(screenshot, new File(imgPath));
			// System.out.println("Screen Captured");
		} catch (IOException e) {
			e.printStackTrace();
		}
		ssNo++;
		return imgPath;

	}

	// Function to take Screenshot of screen
	public void Screenshot(String fileName) throws IOException {
		TakesScreenshot capture = (TakesScreenshot) driver;
		File srcFile = capture.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir") + "/Screenshot/" + fileName + ".png");
		Files.copy(srcFile, destFile);
	}

	public void wait(int sec, By locator) {
		wait = new WebDriverWait(driver, sec);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// To input all data to the report
	public void endReport() {
		report.flush();
	}

	// To close the Browser
	public void closeBrowser() throws IOException {
		driver.quit();
	}

}
