package com.cts.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class ExtentReportManager {
	public static ExtentReports report;
public static ExtentReports getReportInstance(){
		
		if(report== null){
			ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"/Report/Report.html");
			htmlReporter.config().setTheme(Theme.DARK);
			report=new ExtentReports();
			report.attachReporter(htmlReporter);
			
		}
		
		return report;
	}

}
