package com.cts.testSuites;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.cts.base.BaseUI;
import com.cts.pages.TimeSheet;

public class timesheet extends BaseUI{
	TimeSheet ha= new TimeSheet();
	@BeforeTest
	public void invokeBrowser() throws Exception {
		logger = report.createTest("Test Case Execution");
		ha.invokeBrowser();
		reportPass("Browser is opened successfuly");
	}

	@Test(priority = 1)
	public void testCases() throws Exception {

		ha.openURL();
		ha.login();
		ha.getData();
		ha.verifyDates();
		reportPass("Tests passed successfuly");
	}

	@AfterTest
	public void closeBrowser() throws IOException {
		
		reportPass("Browser is closed successfuly");
		ha.closeBrowser();
		ha.endReport();

	}
}
