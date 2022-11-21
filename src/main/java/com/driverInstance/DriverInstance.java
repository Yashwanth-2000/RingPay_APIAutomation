package com.driverInstance;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.SkipException;

import com.propertyfilereader.PropertyFileReader;
import com.utility.Utilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverInstance extends Drivertools {
	
	public static ThreadLocal<RemoteWebDriver> tlWebDriver = new ThreadLocal<>();

	@SuppressWarnings("static-access")
	public DriverInstance(String Application) {
		super(Application);
		try {
			switch (getPlatform()) {
			case "Android":
				if (getTestName().equals("Android_ChromeCast")) {
					chromeCastInitDriver();
				}
				else {
					DriverManager.setAppiumDriver((AppiumDriver<WebElement>) new AndroidDriver<WebElement>(new URL(getremoteUrl()),this.generateAndroidCapabilities(Application)));
					//Utilities.waitForElementDisplayed(AMDOnboardingScreen.objWaitForSplashScreenDisapear, 240);				
					Instant endTime = Instant.now();
//					timeElapsed = Duration.between(startTime, endTime);
//					logger.info("Time taken to launch the App (millisec)" + timeElapsed.toMillis());
				}
				break;

			case "MPWA":
				DriverManager.setAppiumDriver((AppiumDriver<WebElement>) new AndroidDriver<WebElement>(new URL(getremoteUrl()),this.generateAndroidCapabilities(Application)));
				DriverManager.getAppiumDriver().get(getURL());
				break;
				
			case "MPWAiOSSafari":
				DriverManager.setAppiumDriver((AppiumDriver<WebElement>) new IOSDriver<WebElement>(new URL(getremoteUrl()),this.generateiOSCapabilities(Application)));
				DriverManager.getAppiumDriver().get(getURL());
				break;	

			case "Web":
				//LaunchBrowser(getBrowserType());
				break;
				
			case "TV":
				tlDriver.set((AppiumDriver<WebElement>) new AndroidDriver<WebElement>(new URL(getremoteUrl()),
						this.generateAndroidCapabilities(Application)));
				break;

			default:
				throw new SkipException("Incorrect Platform...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SkipException("Device not connected OR Appium Studio service is down...");
		}
		Utilities.initDriver();
	}

	/**
	 * @param application
	 * @return Android capabilities
	 * @throws Exception
	 */
	protected DesiredCapabilities generateAndroidCapabilities(String application) {
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("autoAcceptAlerts", true);
		if (getPlatform().equals("MPWA")) {
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
			return capabilities;
		}
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getAppPackage());
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getappActivity());
		if (Utilities.relaunch) {
			removePermisson(getAppPackage());
		}
		startTime = Instant.now();
		return capabilities;
	}
	
	/**
	 * @param application
	 * @return Android capabilities
	 * @throws Exception
	 */
	protected DesiredCapabilities generateiOSCapabilities(String application) {
		DesiredCapabilities capabilities = new DesiredCapabilities();	
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iOS");
		capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.apple.mobilesafari");
		capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
		return capabilities;
	}
	
//	@SuppressWarnings("unused")
//	private void installAPK(String build) {
//		if(build.contains("Latest") || build.contains("BuildVersion")) {
//		//DownloadApp(build);
//		System.out.println("Finished download");
//		System.out.println(dir);
//		File file = new File(dir);
//		file.mkdir();
//		File filesList[] = file.listFiles();
//		 for(File fileName : filesList) {
//			 apkName = fileName.getName();
//		 }
//		 capabilities.setCapability(MobileCapabilityType.APP, dir+apkName);
//		 System.out.println("Install APK");
//		switch(getApk()) {
//		case "CleverTap":
//			capabilities.setCapability(MobileCapabilityType.APP, dir+apkName);
//			break;
//		case "AppsFlyer":
////			capabilities.setCapability(MobileCapabilityType.APP, dir+"");
//			break;
//		case "Conviva":
////			capabilities.setCapability(MobileCapabilityType.APP, dir+"");
//			break;
//		case "DFP":
//			capabilities.setCapability(MobileCapabilityType.APP, dir+"DFP.apk");
//			break;
//		case "Mixpanel":
//			capabilities.setCapability(MobileCapabilityType.APP, dir+"mixpanel.apk");
//			break;
//		}
//	  }
//	}

	/**
	 * To Remove the permission of an application
	 * 
	 * @param packagename
	 */
	public static void removePermisson(String packagename)
	{
		logger.info("****Clearing the App Data****");
		String cmd2 = "adb -s "+getDeviceList()+" shell pm clear " + packagename;
		try {
			Runtime.getRuntime().exec(cmd2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void chromeCastInitDriver() throws MalformedURLException, ParseException {
		tlDriver.set((AppiumDriver<WebElement>) new AndroidDriver<WebElement>(new URL(getremoteUrl()),generateAndroidChromeCastCapabilities("Zee")));		
		driverTV.set((AppiumDriver<WebElement>) new AndroidDriver<WebElement>(new URL(getremoteUrl()),generateAndroidTvChromeCastCapabilities("zeeTV")));
	}
	
	public DesiredCapabilities generateAndroidChromeCastCapabilities(String application) {
		System.out.println("Capability");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		capabilities.setCapability("udid", getDeviceList());
		capabilities.setCapability("platformVersion", "10");
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("autoAcceptAlerts", true);
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getAppPackage());
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getappActivity());
		return capabilities;
	}
	
	public DesiredCapabilities generateAndroidTvChromeCastCapabilities(String application) {
		System.out.println("Capability");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		capabilities.setCapability("udid", getTVDeviceList());
		capabilities.setCapability("platformVersion", "10");
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("autoAcceptAlerts", true);
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.graymatrix.did");
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,"com.zee5.player.activities.SplashActivity");
		return capabilities;
	}
}