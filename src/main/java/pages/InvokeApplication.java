package main.java.pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import main.java.util.TestUtil;

public class InvokeApplication extends TestUtil {

	@SuppressWarnings("rawtypes")
	public static void invokeapp() throws Exception {
		try {
			logger.info("Setupig the desired capabilities");
			String applocation=System.getProperty("user.dir")+"\\src\\main\\java\\excels\\Amazon_shopping.apk";
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("deviceName", "HNB2QS8W");
			caps.setCapability("platformName", "Android");
			caps.setCapability("platformVersion", "8.0");
			caps.setCapability("app", applocation);
			// caps.setCapability("appPackage", "com.amazon.mShop.android.shopping");
			// caps.setCapability("appActivity", "com.amazon.mShop.home.web.MShopWebGatewayActivity");
			//caps.setCapability("fullReset", true);

			try {
				driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), caps);
			} catch (MalformedURLException e) {
				System.out.println(e.getMessage());
			}
			logger.info("Server invoked successfully");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(3000);
		} catch (Exception e) {
			Errormessage= "Error New Session is not created";
			logger.error("Error New Session is not created");
			fail = true;
			throw new Exception();
		}

	}

}
