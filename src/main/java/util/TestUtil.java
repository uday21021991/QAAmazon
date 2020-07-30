package main.java.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class TestUtil extends TestBase {


	/**
	 * This method is used to click the MobileElement 
	 * 
	 * @param webele : We have to pass the valid mobile element
	 * @param Ele_name : we have o pass the name of the element to print the logs
	 * @throws Exception
	 */
	public static void clickElement(MobileElement webele, String Ele_name) throws Exception {
		try {
			new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(webele));
			webele.click();
			System.out.println("The " + Ele_name + " is clicked");
			logger.debug("The " + Ele_name + " is clicked");
		} catch (Exception e) {
			Errormessage= Ele_name +" is not avilable pls refer the screenshot";
			logger.error(Errormessage);
			takeSnap(Ele_name);fail = true;
			e.printStackTrace();
			throw new Exception();
		}
	}

	/**
	 * This method is used to Sending the values to the MobileElement
	 * 
	 * @throws Exception
	 **/
	public static void Sendkeys(MobileElement webele, String value, String Ele_name) throws Exception {
		try {
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(webele)).click();;
			webele.sendKeys(value);
			System.out.println(value + " is entered into the " + Ele_name + " textbox");
			logger.debug(value + " is entered into the " + Ele_name + " textbox");
		} catch (Exception e) {
			Errormessage= Ele_name +" is not avilable pls refer the screenshot";
			logger.error(Errormessage);
			takeSnap(Ele_name);fail = true;
			e.printStackTrace();
			throw new Exception();
		}
	}

	/**
	 * This method is used to get the text of webelement
	 * @param mobileElement
	 * @return
	 * @throws Exception
	 */
	public String gettext(MobileElement mobileElement) throws Exception {
		String text="";
		try {
			text=mobileElement.getText();
		}catch(Exception e) {
			fail = true;
			throw new Exception();
		}
		
		return text;
		
	}
	/**
	 * This method for checking the page is displayed or Not
	 * 
	 * @param webele
	 *            We have to pass the web element to check that is Clickable or not
	 * @return
	 */
	public static boolean checkCurrentPage(MobileElement webele) {
		try {
			new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(webele));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This method for getting the data from the hash map and returns the value
	 * 
	 * @param Name
	 *            It is the name of the column
	 * @return
	 * @throws Exception
	 */
	public static String getdata(String Name) throws Exception {

		String data = "";
		if (inputdata.hashmap().containsKey(Name)) {
			data = inputdata.hashmap().get(Name);
		} else {
			System.err.println("Given Column name is not availale in the Excel " + Name);
		}
		return data;

	}

	/**
	 * This method for write the report to the excel sheet in test case wise.
	 * 
	 * @param xls
	 *            excel sheet name what we are reading
	 * @param testCase
	 *            we have to pass the excel sheet name to writing the report
	 * @param rowNum
	 *            This is for update the report in row wise
	 * @param result
	 *            This is the columns name in this updated the report like pass,
	 *            fail, skip
	 * @param Comments
	 *            This is the columns name in this updated the comments if the test
	 *            is failed.
	 * 
	 */
	public static void reportDataSetResult(XlsReader xls, String testCaseName, int rowNum, String result,String Comments) {

		xls.setCellData(testCaseName, "Results", rowNum, result);
		xls.setCellData(testCaseName, "Comments", rowNum, Comments);
	}
	
	public static void updatelogindetails(XlsReader xls, String testCaseName, int rowNum, String emailid,String pass) {

		xls.setCellData(testCaseName, "username", rowNum, emailid);
		xls.setCellData(testCaseName, "password", rowNum, pass);
	}
	
	

	/**
	 * This method for checking the Test case run mode is Yes or No
	 * @param xls
	 *            This is the excel sheet which we have to read
	 * @param testCaseName
	 *            This is to check the test case is runnable or not
	 * @return
	 */
	public static boolean isTestCaseRunnable(XlsReader xls, String testCaseName) {
		boolean isExecutable = false;
		for (int i = 2; i <= xls.getRowCount("TestNames"); i++) {
			if (xls.getCellData("TestNames", "Test Name", i).equalsIgnoreCase(testCaseName)) {
				if (xls.getCellData("TestNames", "Run Mode", i).equalsIgnoreCase("Y")) {
					isExecutable = true;
				} else {
					isExecutable = false;
				}
			}
		}
		return isExecutable;
	}

	/**
	 * This method is used to swipe the screen using the coordinates
	 * 
	 * @param startx
	 *            : We have to pass the start X example: x:150
	 * @param starty
	 *            : We have to pass the start Y example: x:1200
	 * @param endx
	 *            : We have to pass the end X
	 * @param endy
	 *            : We have to pass the end Y
	 * @throws InterruptedException
	 */
	public static void swipe() throws InterruptedException {

		 Dimension size = driver.manage().window().getSize();
		    int x = (int) (size.width / 2);
		    // Swipe up to scroll down
		    int y = (int) (size.height - 20);
		    int end = 10;
		
		new TouchAction(driver).press(x, y).moveTo(x, end).release().perform();
		Thread.sleep(2000);
	}


	/**
	 * This method will take the screenshot for failed test cases *
	 * 
	 * @param msg
	 *            it is for storing the screenshot with the error message
	 * @throws IOException
	 * @throws InterruptedException
	 */

	public static void takeSnap(String msg) throws IOException, InterruptedException {
		DateFormat dateFormat = new SimpleDateFormat("hh_mm_ss");
		Date date = new Date();
		if (msg.length() > 199) {
			msg = msg.substring(0, 100);
		}
		msg = msg.trim() + dateFormat.format(date);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "//src//testsnaps//" + msg + ".png"));
		Thread.sleep(2000);

	}
}
