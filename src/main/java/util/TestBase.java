package main.java.util;


import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import test.java.Orderproducts;


public class TestBase {
	
	@SuppressWarnings("rawtypes")
	public static AppiumDriver driver;
	public static int count = -1;
	public static int searchCount = 0;
	public static boolean skip = false;
	public static boolean fail = false;

	public static XlsReader AAxls = null;
	public static String sheetName = "Amazon AUS";
	public static boolean isInitialized = false;
	public static String Excelpath = "";
	public static XlsReader inputdata;
	public static String Errormessage = "";
	public static String EmailID = "";
	public static String CreatedPassword = "";
	
	public static Logger logger=LogManager.getLogger(Orderproducts.class);
	
	public static AppiumDriverLocalService appiumService=null;
	private static String appiumServiceUrl;
			
			
	public void initialize() throws Exception {

		if (!isInitialized) {

				AAxls = new XlsReader(System.getProperty("user.dir") + "\\src\\main\\java\\excels\\TestData.xlsx");
				Excelpath = System.getProperty("user.dir") + "//src//main//java//excels//TestData.xlsx";	
			} else {
				System.out.println("Excel is not Configured");
			}
			isInitialized = true;
			try {
				FileUtils.cleanDirectory(new File(System.getProperty("user.dir")+"//src//testsnaps//"));
			}catch(Exception e) {
	
			}

		}
	

	public static void startAppiumServer() {
			
		try {
			appiumService = AppiumDriverLocalService.buildDefaultService();
			appiumService.start();
	        appiumServiceUrl = appiumService.getUrl().toString();
	        System.out.println("Appium Service Address : - "+ appiumServiceUrl);
		}catch(Exception e) {
			e.printStackTrace();

		}
		
    }

	
	 public WebDriver getDriver() {
	        return driver;
	    }

}
