package main.java.pages;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import junit.framework.Assert;
import main.java.util.TestUtil;

public class SignUpPage extends TestUtil{
	

	@AndroidFindBy(xpath = "//*[@resource-id='ap_customer_name']")
	private MobileElement Name;
	
	@AndroidFindBy(xpath = "//*[@resource-id='ap_phone_number']")
	private MobileElement PhoneNumber;
	
	@AndroidFindBy(xpath = "//*[@resource-id='ap_email']")
	private MobileElement EmailId;
	
	@AndroidFindBy(xpath = "//*[@resource-id='ap_password']")
	private MobileElement Password;

	@AndroidFindBy(xpath = "//*[@resource-id='continue']")
	private MobileElement Continue;

	@AndroidFindBy(xpath = "//*[@resource-id='auth-pv-enter-code']")
	private MobileElement OTP;
	
	

	
	public SignUpPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
	}
	
	
	/**
	 * This method is used to create a new user
	 * @throws Exception
	 */
	public void createAcc() throws Exception {
		if(checkCurrentPage(Name)) {
			Sendkeys(Name, "Testing", "User Name");
			Sendkeys(PhoneNumber, generatephonenum(), "Phone Number");
			Sendkeys(EmailId, generateEmailId(), "Email Id");
			CreatedPassword=generatephonenum();
			Sendkeys(Password,CreatedPassword, "Password");
			clickElement(Continue, "Continue Button");
			
			Assert.assertEquals(true, checkCurrentPage(OTP));
			System.out.println("User created new account successfully");
			
		}else {
			System.out.println("Error User not navigated to signup page");
			fail=true;
			throw new Exception();
		}
	}
	
	/**
	 * This method is used to create a random mobile number
	 * @return : it returns the mobile number
	 */
	public String  generatephonenum() {
		String phonenum="9959"+(int)(Math.random() * (1000000 - 100000 + 1) + 100000);
		return phonenum;
	}
	
	/**
	 * This method is used to create a random email id
	 * @return : it returns the email id
	 */
	public String  generateEmailId() {
		EmailID="testingteam"+new Random().nextInt(1000000)+"@gmail.com";
		return EmailID;
	}
}
