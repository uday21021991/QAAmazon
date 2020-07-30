package main.java.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import main.java.util.TestUtil;


public class HomePage extends TestUtil {
	
	//private static Logger logger=LogManager.getLogger(HomePage.class);
	
	@AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc=\"Navigation panel, button, double tap to open side panel\"]")
	private MobileElement MenuButton;

	@AndroidFindBy(xpath = "//*[@text='Settings']")
	private MobileElement Settings;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/drawer_item_title")
	private MobileElement ChangeCountryLink;

	@AndroidFindBy(xpath = "//*[@resource-id='landing-countryButton']")
	private MobileElement CountryButton;

	@AndroidFindBy(xpath = "//*[contains(@text,'Australia ')]")
	private MobileElement Australia;

	@AndroidFindBy(xpath = "//*[@resource-id='landing-doneButton']")
	private MobileElement DoneButton;

	@AndroidFindBy(xpath = "//*[@text='Skip sign in']")
	private MobileElement Skip;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/sign_in_button")
	private MobileElement LoginButton;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/new_user")	
	private MobileElement CreateAccount;
	
	@AndroidFindBy(xpath = "//*[@class='android.widget.EditText']")
	private MobileElement UserName;

	@AndroidFindBy(xpath = "//*[@text='Continue']")
	private MobileElement Continue;

	@AndroidFindBy(xpath = "//*[@resource-id='auth-fpp-link-bottom']")
	private MobileElement ForgotPasswordLink;

	@AndroidFindBy(className = "android.widget.Button")
	private MobileElement SignInButton;
	
	
	public HomePage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
	}
	
	/**
	 * This Method is used to change the country in settings
	 * @throws Exception
	 */
	public void changeCuntry() throws Exception {

		clickElement(Skip, "Skip button");
		logger.info("Skip button is clicked");
		clickElement(MenuButton, "Menu button");
		Thread.sleep(2000);
		swipe();
		clickElement(Settings, "Setting button");
		clickElement(ChangeCountryLink, "Change Country Link");
		clickElement(CountryButton, "Country Button");
		clickElement(Australia, "Australia");
		Thread.sleep(1500);
		clickElement(DoneButton, "Done Button");

	}
	
	/**
	 * This Method is used to click the login link
	 * @throws Exception
	 */
	public void clickLoginButton() throws Exception {
		clickElement(MenuButton, "Menu button");
		Thread.sleep(2000);
		clickElement(LoginButton, "Login Button");
	}

	/**
	 * This Method is used to pass the login credentials and clik on login 
	 * @param user : We have to pass the valid login username
	 * @param pass : We have to pass the valid login password
	 * @throws Exception
	 */
	public void login(String user,String pass) throws Exception {
		
		Sendkeys(UserName, user, "User Name");
		clickElement(Continue, "Continue Button");
		checkCurrentPage(ForgotPasswordLink);
		Sendkeys(UserName, pass, "Password Name");
		clickElement(SignInButton, "Continue Button");
		
	}
	
	public void createaccountButton() throws Exception {
		clickElement(CreateAccount, "Create Account Button");
	}

}
