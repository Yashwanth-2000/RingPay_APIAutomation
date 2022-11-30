package com.utility;

import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.driverInstance.*;

import com.excel.ExcelWriteData;
//import com.global.GlobalData;
import com.Datasheet.RingPay_TestData_DataProvider;
import com.business.RingPay.URI.RingPay_BaseURL;
import com.business.RingPay.URI.RingPay_Endpoints;
import com.business.RingPay_MerchantQRCode_Journey.*;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import com.google.common.collect.Ordering;
import com.propertyfilereader.PropertyFileReader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import java.time.Duration;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import javassist.bytecode.stackmap.BasicBlock.Catch;

public class Utilities extends ExtentReporter {

	private Utilities() {
	}

	/** Time out */
	private static int timeout;

	/** Retry Count */
	private static int retryCount;

	/** Test data property file**/
	public static PropertyFileReader prop = new PropertyFileReader(".\\properties\\testData.properties");

	// public static ExtentReporter extent = new ExtentReporter();

	@SuppressWarnings("rawtypes")
	public static TouchAction touchAction;

	private static SoftAssert softAssert = new SoftAssert();

	public static boolean relaunch = false;

	public static String CTUserName;

	public static String CTPWD;

	public static String setPlatform = "";

	// logging for extent report
	public static LoggingUtils log = new LoggingUtils();

	/** The Constant logger. */
	// final static Logger logger = Logger.getLogger("rootLogger");
	static LoggingUtils logger = new LoggingUtils();

	/** The Android driver. */
	public AndroidDriver<AndroidElement> androidDriver;

	/** window handler */
	static ArrayList<String> win = new ArrayList<>();

	/** The Android driver. */
	public IOSDriver<WebElement> iOSDriver;

	public static int getTimeout() {
		return 30;
	}

	/*
	 * public static void setTimeout(int timeout) { this.timeout = timeout; }
	 */

	public static int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public static AppiumDriver<WebElement> getDriver() {
		return DriverInstance.tlDriver.get();
	}

	public static String getPlatform() {
		return DriverInstance.getPlatform();
	}

	public void setPlatform(String Platform) {
		DriverInstance.setPlatfrom(Platform);
	}

	public static AppiumDriver<WebElement> getTVDriver() {
		return DriverInstance.driverTV.get();
	}

	static WebDriverWait wait;

	public static JavascriptExecutor js;
	public static Actions action;
	public static String username = "";
	public static String password = "";

	public static void initDriver() {
		if (getPlatform().equals("Web")) {
			// wait = new WebDriverWait(DriverManager.getDriver(), getTimeout());
			js = (JavascriptExecutor) DriverManager.getDriver();
			action = new Actions(DriverManager.getDriver());
			DriverManager.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		} else if (getPlatform().equals("Android") || getPlatform().equals("MPWA") || getPlatform().equals("TV")) {
			js = (JavascriptExecutor) DriverManager.getAppiumDriver();
			action = new Actions(DriverManager.getAppiumDriver());
			DriverManager.getAppiumDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}

	public static void javaScriptExecutor() {
		if (getPlatform().equals("Web")) {
			JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
		} else if (getPlatform().equals("Android") || getPlatform().equals("MPWA") || getPlatform().equals("TV")) {
		}
	}

	public static void setScreenshotSource() {
		if (getPlatformFromtools().equalsIgnoreCase("Web")) {
			src = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
		} else if (getPlatformFromtools().equals("Android") || getPlatformFromtools().equals("PWA")
				|| getPlatformFromtools().equals("TV")) {
			src = ((TakesScreenshot) getDriver()).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
		} else if (getPlatformFromtools().equalsIgnoreCase("MPWA")) {
			src = ((TakesScreenshot) DriverManager.getAppiumDriver())
					.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
		}
	}

	// public static boolean JSClick(By byLocator, String text) throws Exception {
	// try {
	// js = (JavascriptExecutor) DriverManager.getDriver();
	// js.executeScript("arguments[0].click();",
	// DriverManager.getDriver().findElement(byLocator));
	// logger.info("" + text + " " + " is clicked");
	// ExtentReporter.extentLogger("checkElementPresent", "" + text + " is
	// clicked");
	// return true;
	// } catch (Exception e) {
	// logger.error(text + " " + " is not clicked");
	// ExtentReporter.extentLoggerFail("checkElementNotPresent", "" + text + " is
	// not clicked");
	// ExtentReporter.screencapture();
	// // e.printStackTrace();
	// return false;
	// }
	// }

	// public static WebElement findElement(By byLocator) throws Exception {
	// //WebElement element =
	// wait.until(ExpectedConditions.presenceOfElementLocated(byLocator));
	// //return element; +
	// WebElement element = null;
	// if (getPlatform().equals("Web")) {
	// element = DriverManager.getDriver().findElement(byLocator);
	// return element;
	// }
	// else if (getPlatform().equals("MPWA")) {
	// element = DriverManager.getAppiumDriver().findElement(byLocator);
	// return element;
	// }
	// return element;
	// }
	//
	//
	// public void setWifiConnectionToONOFF(String Value) {
	// try {
	// if (Value.equalsIgnoreCase("On")) {
	// System.out.println("Switching On Wifi");
	// String cmd = "adb shell svc wifi enable";
	// Runtime.getRuntime().exec(cmd);
	// waitTime(5000);
	// logger.info("Wifi Data toggle is Switched On");
	// ExtentReporter.extentLogger("Wifi Toggle", "Wifi Data toggle is Switched
	// On");
	// } else if (Value.equalsIgnoreCase("Off")) {
	// System.out.println("Switching Off Wifi");
	// String cmd = "adb shell svc wifi disable";
	// Runtime.getRuntime().exec(cmd);
	// waitTime(3000);
	// logger.info("Wifi Data toggle is Switched Off");
	// ExtentReporter.extentLogger("Wifi Toggle", "Wifi Data toggle is Switched
	// Off");
	// }
	// } catch (Exception e) {
	// // logger.error(e);
	// }
	// }
	//
	// /**
	// * wait until element is displayed.
	// *
	// * @param
	// * @return true, if successful
	// */
	// public static boolean waitForElementDisplayed(By byLocator, int iTimeOut) {
	// try {
	// wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
	// return false;
	// } catch (Exception e) {
	// return true;
	// }
	// }
	//
	// /**
	// * Check element not present.
	// *
	// * @param byLocator the by locator
	// * @return true, if successful
	// */
	// public static boolean verifyElementNotPresent(By byLocator, int iTimeOut) {
	// try {
	// WebDriverWait wait = new WebDriverWait(getDriver(), iTimeOut);
	// wait.until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
	// return false;
	// } catch (NoSuchElementException e) {
	// return true;
	// }
	// }
	//
	// /**
	// * Check element present.
	// * @param byLocator the by locator
	// * @return true, if successful
	// */
	// public static boolean verifyElementPresentOld(By byLocator, String
	// validationtext) throws Exception {
	// try {
	// isPresentWithWait(byLocator);
	// logger.info(validationtext + " is displayed");
	// ExtentReporter.extentLogger("checkElementPresent", validationtext + " is
	// displayed");
	// return true;
	// } catch (Exception e) {
	// logger.error(validationtext + " is not displayed");
	// ExtentReporter.extentLoggerFail("checkElementPresent", validationtext + " is
	// not displayed");
	// return false;
	// }
	// }
	//
	// public static boolean verifyElementPresent(By byLocator, String
	// validationtext) throws Exception {
	// if(isPresentWithWait(byLocator)) {
	// logger.info(validationtext + " is displayed");
	// ExtentReporter.extentLogger("checkElementPresent", validationtext + " is
	// displayed");
	// return true;
	// }
	// else {
	// logger.error(validationtext + " is not displayed");
	// ExtentReporter.extentLoggerFail("checkElementPresent", validationtext + " is
	// not displayed");
	// return false;
	// }
	// }
	//
	// public static boolean verifyElementExist(By byLocator, String str) throws
	// Exception {
	// try {
	// WebElement element = DriverManager.getDriver().findElement(byLocator);
	// if (element.isDisplayed()) {
	// ExtentReporter.extentLogger("checkElementPresent", "" + str + " is
	// displayed");
	// logger.info("" + str + " is displayed");
	// return true;
	// }
	// } catch (Exception e) {
	// ExtentReporter.extentLoggerFail("checkElementPresent", "" + str + " is not
	// displayed");
	// logger.error(str + " is not displayed");
	// return false;
	// }
	// return false;
	// }
	//
	// /**
	// * boolean return type for conditions
	// *
	// * @param byLocator
	// * @return
	// * @throws Exception
	// */
	// public static boolean verifyElementDisplayed(By byLocator) throws Exception {
	// String platform =
	// Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
	// if(platform.equalsIgnoreCase("web")) {
	// try {
	// WebElement element = DriverManager.getDriver().findElement(byLocator);
	// if (element.isDisplayed()) {
	// return true;
	// }
	// } catch (Exception e) {}
	// }
	// else if(platform.equalsIgnoreCase("mpwa")) {
	// try {
	// WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
	// if (element.isDisplayed()) {
	// return true;
	// }
	// } catch (Exception e) {}
	// }
	// return false;
	// }
	//
	//
	// public static boolean checkElementExist(By byLocator, String str) throws
	// Exception {
	//
	// try {
	// getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	// List<WebElement> list = getDriver().findElements(byLocator);
	// getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	// if (list.size() == 0) {
	// ExtentReporter.extentLogger("checkElementPresent", "" + str + " is not
	// displayed");
	// logger.info("" + str + " is not displayed");
	// return false;
	// } else {
	// ExtentReporter.extentLogger("checkElementPresent", "" + str + " is
	// displayed");
	// logger.info("" + str + " is displayed");
	// return list.get(0).isDisplayed();
	// }
	// } catch (Exception e) {
	// return false;
	// }
	// }
	//
	// /**
	// * Check element present and click.
	// *
	// * @param byLocator the by locator
	// * @return true, if successful
	// */
	// public static boolean verifyElementPresentAndClick(By byLocator, String
	// validationtext) throws Exception {
	// String platform =
	// Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
	// WebElement element=null;
	// try {
	// if(platform.equalsIgnoreCase("web")) {
	// element = DriverManager.getDriver().findElement(byLocator);
	// }
	// else if (platform.equalsIgnoreCase("mpwa")) {
	// element = DriverManager.getAppiumDriver().findElement(byLocator);
	// }
	// softAssert.assertEquals(element.isDisplayed(), true, "" + validationtext + "
	// " + " is displayed");
	// logger.info("" + validationtext + " " + "is displayed");
	// ExtentReporter.extentLogger("checkElementPresent", "" + validationtext + " is
	// displayed");
	// if(platform.equalsIgnoreCase("web")) {
	// DriverManager.getDriver().findElement(byLocator).click();
	// }
	// else if (platform.equalsIgnoreCase("mpwa")) {
	// DriverManager.getAppiumDriver().findElement(byLocator).click();
	// }
	// logger.info("Clicked on " + validationtext);
	// ExtentReporter.extentLogger("click", "Clicked on " + validationtext);
	// return true;
	// } catch (Exception e) {
	// softAssert.assertEquals(false, true, "Element" + validationtext + " " + " is
	// not visible");
	// logger.error("Element " + validationtext + " " + " is not visible");
	// ExtentReporter.extentLoggerFail("checkElementPresent", "" + validationtext +
	// " is not displayed");
	// ExtentReporter.screencapture();
	// return false;
	// }
	// }

	public static String getAdId() throws IOException {
		String cmd = "adb shell grep adid_key /data/data/com.google.android.gms/shared_prefs/adid_settings.xml";
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String strBuffer = br.readLine().trim();
		String[] getadid = strBuffer.split(">")[1].split("<");
		System.out.println("\nAdID: " + getadid[0]);
		return getadid[0];
	}

	/**
	 * @param byLocator
	 * @return true or false
	 */
	public static boolean checkcondition(By byLocator) throws Exception {
		boolean iselementPresent = false;
		try {
			iselementPresent = getDriver().findElement(byLocator).isDisplayed();
			iselementPresent = true;
		} catch (Exception e) {
			iselementPresent = false;
		}
		return iselementPresent;
	}

	/**
	 * Click on a web element.
	 *
	 * @param byLocator the by locator
	 *
	 */
	public static void click(By byLocator, String validationtext) throws Exception {
		String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		try {
			if (platform.equalsIgnoreCase("web")) {
				WebElement element = DriverManager.getDriver().findElement(byLocator);
				element.click();
			} else if (platform.equalsIgnoreCase("mpwa")) {
				WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
				element.click();
			}
			logger.info("Clicked on " + validationtext);
			ExtentReporter.extentLogger("click", "Clicked on " + validationtext);
		} catch (Exception e) {
			logger.info("Did not click on " + validationtext);
			// ExtentReporter.screencapture();
		}
	}

	public static boolean verifyElementNotPresentForWeb(By byLocator, int iTimeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), iTimeOut);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
			return false;
		} catch (NoSuchElementException e) {
			return true;
		}
	}

	/**
	 * clear the text field
	 *
	 * @param byLocator
	 */
	public static void clearField(By byLocator, String text) {
		try {
			DriverManager.getDriver().findElement(byLocator).clear();
			logger.info("Cleared the text in : " + text);
			ExtentReporter.extentLogger("clear text", "Cleared the text in : " + text);
		} catch (Exception e) {
			// logger.error(e);
		}
	}

	/**
	 * Get Text from an object
	 *
	 * @param byLocator
	 * @return
	 * @throws Exception
	 */
	public static String getText(By byLocator) throws Exception {
		String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		String value = null;
		if (platform.equalsIgnoreCase("web")) {
			WebElement element = DriverManager.getDriver().findElement(byLocator);
			value = element.getText();
		} else if (platform.equalsIgnoreCase("mpwa")) {
			WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
			value = element.getText();
		}
		return value;
	}

	@SuppressWarnings({ "rawtypes" })
	public String OTPNotification() throws Exception {
		ExtentReporter.HeaderChildNode("Fetching Otp From Notification");
		waitTime(2000);
		getDriver().context("NATIVE_APP");
		// touchAction = new TouchAction(getDriver());
		// touchAction.press(PointOption.point(500, 0))
		// .waitAction(WaitOptions.waitOptions(Duration.ofMillis(4000)))
		// .moveTo(PointOption.point(1500, 500)).release().perform();
		// waitTime(8000);
		AndroidDriver driver = (AndroidDriver) getDriver();
		driver.openNotifications();
		waitTime(3000);
		List<WebElement> allnotifications = getDriver()
				.findElements(By.xpath("(//*[@resource-id='android:id/message_text'])[1]"));
		System.out.println("Size : " + allnotifications.size());
		String Otp = null;
		for (WebElement webElement : allnotifications) {
			Otp = webElement.getText();
			System.out.println("Get Text : " + webElement.getText());
			if (webElement.getText().contains("something")) {
				System.out.println("Notification found");
				break;
			}
		}
		Back(1);
		getDriver().context("WEBVIEW_1");
		return Otp;
	}

	public boolean verifyElementIsNotDisplayed(By by) {
		try {
			getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			return getDriver().findElements(by).isEmpty();
		} catch (Exception e) {
			getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			return false;
		}
	}

	public static boolean verifyIsElementDisplayed(By by) {
		List<WebElement> list = null;
		if (getPlatform().equals("Web")) {
			DriverManager.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			list = DriverManager.getDriver().findElements(by);
			DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else {
			DriverManager.getAppiumDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			list = DriverManager.getAppiumDriver().findElements(by);
			DriverManager.getAppiumDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		if (list.size() == 0) {
			return false;
		} else {
			return list.get(0).isDisplayed();
		}
	}

	public static boolean verifyIsElementDisplayed(By by, String validationtext) {
		List<WebElement> list = null;
		if (getPlatform().equals("Web")) {
			DriverManager.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			list = DriverManager.getDriver().findElements(by);
			DriverManager.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} else {
			getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			list = getDriver().findElements(by);
			getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		if (list.size() == 0) {
			logger.info("Element " + validationtext + " " + " is not displayed");
			ExtentReporter.extentLogger("checkElementPresent", "" + validationtext + " is not displayed");
			return false;
		} else {
			logger.info("" + validationtext + " " + "is displayed");
			ExtentReporter.extentLogger("checkElementPresent", "" + validationtext + " is displayed");
			return list.get(0).isDisplayed();
		}
	}

	public static boolean checkElementExist(By byLocator) throws Exception {
		try {
			WebElement element = DriverManager.getDriver().findElement(byLocator);
			if (element.isDisplayed()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * Kill or start an application using activity
	 *
	 * @param command  to START or KILL an application
	 * @param activity Start an application by passing the activity
	 */
	public void adbStartKill(String command, String activity) {
		String cmd;
		try {
			if (command.equalsIgnoreCase("START")) {
				cmd = "adb shell am start -n" + " " + activity;
				Runtime.getRuntime().exec(cmd);
				logger.info("Started the activity" + cmd);
				ExtentReporter.extentLogger("adbStart", "Started the activity" + cmd);
			} else if (command.equalsIgnoreCase("KILL")) {
				cmd = "adb shell am force-stop" + " " + activity;
				Runtime.getRuntime().exec(cmd);
				logger.info("Executed the App switch");
				ExtentReporter.extentLogger("adbKill", "Executed the App switch");
			}
		} catch (Exception e) {
			// logger.error(e);
		}
	}

	/**
	 * @return true if keyboard is displayed
	 * @throws IOException
	 */
	public boolean checkKeyboardDisplayed() throws IOException {
		boolean mInputShown = false;
		try {
			String cmd = "adb shell dumpsys input_method | grep mInputShown";
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String outputText = "";
			while ((outputText = br.readLine()) != null) {
				if (!outputText.trim().equals("")) {
					String[] output = outputText.split(" ");
					String[] value = output[output.length - 1].split("=");
					String keyFlag = value[1];
					if (keyFlag.equalsIgnoreCase("True")) {
						mInputShown = true;
					}
				}
			}
			br.close();
			p.waitFor();
		} catch (Exception e) {
			System.out.println(e);
		}
		return mInputShown;
	}

	/**
	 * Closes the Keyboard
	 */
	public static void hideKeyboard() {
		try {
			DriverManager.getAppiumDriver().hideKeyboard();
			logger.info("Hiding keyboard was Successfull");
			ExtentReporter.extentLogger("hideKeyboard", "Hiding keyboard was Successfull");
		} catch (Exception e) {
		}
	}

	/**
	 * Type on a web element.
	 *
	 * @param byLocator the by locator
	 * @param text      the text
	 */
	public static void type(By byLocator, String input, String FieldName) {
		try {
			String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
			if (platform.equalsIgnoreCase("web")) {
				WebElement ele = DriverManager.getDriver().findElement(byLocator);
				ele.sendKeys(input);
			} else if (platform.equalsIgnoreCase("mpwa")) {
				Actions a = new Actions(DriverManager.getAppiumDriver());
				a.sendKeys(input);
				a.perform();
			}
			input = input.split("\n")[0];
			logger.info("Typed the value " + input + " into " + FieldName);
			ExtentReporter.extentLogger("", "Typed the value " + input + " into " + FieldName);
		} catch (Exception e) {
			// logger.error(e);
		}
	}

	public void enter(By byLocator, String input, String FieldName) {
		try {
			waitTime(1000);
			if (!getPlatform().equals("Web")) {
				Actions a = new Actions(getDriver());
				a.sendKeys(input);
				a.perform();
			} else {
				WebElement element = DriverManager.getDriver().findElement(byLocator);
				element.sendKeys(input);
			}
			input = input.split("\n")[0];
			logger.info("Typed the value into " + FieldName);
		} catch (Exception e) {
			// logger.error(e);
		}
	}

	/**
	 * Wait
	 *
	 * @param x seconds to lock
	 */
	public static void Wait(int x) {
		try {
			getDriver().manage().timeouts().implicitlyWait(x, TimeUnit.SECONDS);
		} catch (Exception e) {
			// logger.error(e);
		}
	}

	public static void waitTime(int x) {
		try {
			Thread.sleep(x);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * @param keyevent pass the android key event value to perform specific action
	 *
	 */
	public void adbKeyevents(int keyevent) {
		try {
			String cmd = "adb shell input keyevent" + " " + keyevent;
			Runtime.getRuntime().exec(cmd);
			logger.info("Performed the Keyevent" + keyevent);
			ExtentReporter.extentLogger("adbKeyevent", "Performed the Keyevent" + keyevent);
		} catch (Exception e) {
			// logger.error(e);
		}
	}

	/**
	 * @param byLocator
	 * @returns the list count of the element
	 */
	public int getCount(By byLocator) {

		int count = 0;
		try {
			count = getDriver().findElements(byLocator).size();
			logger.info("List count for" + " " + byLocator + " " + "is" + " " + count);
			ExtentReporter.extentLogger("getCount", "List count for" + " " + byLocator + " " + "is" + " " + count);
		} catch (Exception e) {
			// logger.error(e);
		}
		return count;
	}

	public static List<WebElement> findElements(By byLocator) {
		String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		if (platform.equalsIgnoreCase("web")) {
			return DriverManager.getDriver().findElements(byLocator);
		} else if (platform.equalsIgnoreCase("mpwa")) {
			return DriverManager.getAppiumDriver().findElements(byLocator);
		}
		return null;
	}

	/**
	 * @param i
	 * @param byLocator
	 * @returns the By locator
	 */
	public String iterativeXpathtoStringGenerator(int temp, By byLocator, String property) {

		WebElement element = null;
		String drug = null;
		try {

			String xpath = byLocator.toString();
			String var = "'" + temp + "'";
			xpath = xpath.replaceAll("__placeholder", var);
			String[] test = xpath.split(": ");
			xpath = test[1];
			element = getDriver().findElement(By.xpath(xpath));
			drug = element.getAttribute(property);
		} catch (Exception e) {
			// System.out.println(e);
		}
		return drug;
	}

	/**
	 * Back
	 * 
	 * @throws Exception
	 */
	public static void Back(int x) throws Exception {

		try {
			if (getPlatform().equals("Web")) {
				for (int i = 0; i < x; i++) {
					DriverManager.getDriver().navigate().back();
					logger.info("Back button is tapped");
					ExtentReporter.extentLogger("Back", "Back button is tapped");
				}
			} else if (getPlatform().equals("Android") || getPlatform().equals("MPWA")) {
				for (int i = 0; i < x; i++) {
					getDriver().navigate().back();
					logger.info("Back button is tapped");
					ExtentReporter.extentLogger("Back", "Back button is tapped");
					waitTime(6000);
				}
			}
		} catch (Exception e) {
			// logger.error(e);
			ExtentReporter.screencapture();
		}
	}

	/**
	 * Finding the duplicate elements in the list
	 *
	 * @param mono
	 * @param content
	 * @param dosechang
	 * @param enteral
	 */
	public List<String> findDuplicateElements(List<String> mono) {

		List<String> duplicate = new ArrayList<String>();
		Set<String> s = new HashSet<String>();
		try {
			if (mono.size() > 0) {
				for (String content : mono) {
					if (s.add(content) == false) {
						int i = 1;
						duplicate.add(content);
						System.out.println("List of duplicate elements is" + i + content);
						i++;
					}
				}
			}
		} catch (Exception e) {
			// System.out.println(e);
		}
		return duplicate;
	}

	/**
	 * @param contents
	 * @return the list without duplicate elements
	 */
	public List<String> removeDuplicateElements(List<String> contents) {

		LinkedHashSet<String> set = new LinkedHashSet<String>(contents);
		ArrayList<String> listWithoutDuplicateElements = new ArrayList<String>();
		try {

			if (contents.size() > 0) {
				listWithoutDuplicateElements = new ArrayList<String>(set);
			}

		} catch (Exception e) {
			// System.out.println(e);
		}
		return listWithoutDuplicateElements;
	}

	/**
	 * @param i
	 * @param byLocator
	 */
	public void iteratorClick(int temp, By byLocator) {

		try {
			String xpath = byLocator.toString();
			String var = "'" + temp + "'";
			xpath = xpath.replaceAll("__placeholder", var);
			String[] test = xpath.split(": ");
			xpath = test[1];
			getDriver().findElement(By.xpath(xpath)).click();
		} catch (Exception e) {
			// System.out.println(e);
		}
	}

	/**
	 * Get specific property value of a web element and stores to string variable.
	 * 
	 * @param property  the property of the element.
	 * @param byLocator the by locator
	 * @return value of the property.
	 */
	public static String getElementPropertyToString(String property, By byLocator, String object) {
		String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		String propertyValue = null;
		if (platform.equalsIgnoreCase("web")) {
			try {
				WebElement element = DriverManager.getDriver().findElement(byLocator);
				propertyValue = element.getAttribute(property);
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (platform.equalsIgnoreCase("mpwa")) {
			try {
				WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
				propertyValue = element.getAttribute(property);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return propertyValue;
	}

	/**
	 * @param sorted
	 * @return true if the list is sorted
	 * @return false if the list is not sorted
	 */
	public boolean checkListIsSorted(List<String> ListToSort) {

		boolean isSorted = false;

		if (ListToSort.size() > 0) {
			try {
				if (Ordering.natural().isOrdered(ListToSort)) {
					ExtentReporter.extentLogger("Check sorting", "List is sorted");
					logger.info("List is sorted");
					isSorted = true;
					return isSorted;
				} else {
					ExtentReporter.extentLogger("Check sorting", ListToSort + " " + "List is not sorted");
					logger.info(ListToSort + "List is notsorted");
					isSorted = false;
				}
			} catch (Exception e) {
				// System.out.println(e);
			}
		} else {
			logger.info("The size of the list is zero");
			ExtentReporter.extentLogger("",
					ListToSort + " " + "There are no elements in the list to check the sort order");
		}
		return isSorted;
	}

	/**
	 * @param byLocator
	 * @returns the list count of the element
	 */
	public int iterativeGetCount(int temp, By byLocator) {

		int count = 0;
		try {

			String xpath = byLocator.toString();
			String var = "'" + temp + "'";
			xpath = xpath.replaceAll("__placeholder", var);
			String[] test = xpath.split(": ");
			xpath = test[1];
			count = getDriver().findElements(By.xpath(xpath)).size();
			logger.info("List count for" + " " + xpath + " " + "is" + " " + count);
			ExtentReporter.extentLogger("getCount", "List count for" + " " + xpath + " " + "is" + " " + count);
		} catch (Exception e) {
			// logger.error(e);
		}
		return count;
	}

	/**
	 * @param temp
	 * @param byLocator
	 * @return
	 */
	public By iterativeXpathText(String temp, By byLocator) {

		By searchResultList = null;

		try {

			String xpath = byLocator.toString();
			String var = "'" + temp + "'";
			xpath = xpath.replaceAll("__placeholder", var);
			String[] test = xpath.split(": ");
			xpath = test[1];
			searchResultList = By.xpath(xpath);
		} catch (Exception e) {
			// System.out.println(e);
		}
		return searchResultList;
	}

	/**
	 * @param byLocator Checks whether element is not displayed
	 * @throws Exception
	 */
	public void checkElementNotPresent(By byLocator) throws Exception {
		boolean isElementNotPresent = true;
		try {
			isElementNotPresent = checkcondition(byLocator);
			softAssert.assertEquals(isElementNotPresent, false);
			logger.info("" + byLocator + " " + "is not displayed");
			ExtentReporter.extentLogger("checkElementNotPresent", "" + byLocator + "is not displayed");
		} catch (Exception e) {
			softAssert.assertEquals(isElementNotPresent, true, "Element" + byLocator + " " + "is visible");
			// softAssert.assertAll();
			logger.error(byLocator + " " + "is visible");
			ExtentReporter.extentLogger("checkElementNotPresent", "" + byLocator + "is displayed");
			ExtentReporter.screencapture();
		}
	}

	/**
	 * Swipes the screen in left or right or Up or Down or direction
	 *
	 * @param direction to swipe Left or Right or Up or Down
	 * @param count     to swipe
	 */
	@SuppressWarnings("rawtypes")
	public static void Swipe(String direction, int count) {
		String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		TouchAction touchAction = null;
		if (platform.equalsIgnoreCase("web")) {
			touchAction = new TouchAction(getDriver());
		} else if (platform.equalsIgnoreCase("mpwa")) {
			touchAction = new TouchAction(DriverManager.getAppiumDriver());
		}
		String dire = direction;
		try {
			if (dire.equalsIgnoreCase("LEFT")) {

				for (int i = 0; i < count; i++) {
					Dimension size = getDriver().manage().window().getSize();
					int startx = (int) (size.width * 0.5);
					int endx = (int) (size.width * 0.1);
					int starty = size.height / 2;
					touchAction.press(PointOption.point(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(PointOption.point(endx, starty)).release().perform();
					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (i + 1) + " times");
					ExtentReporter.extentLogger("SwipeLeft",
							"Swiping screen in " + " " + dire + " direction" + " " + (i + 1) + " times");
				}
			} else if (dire.equalsIgnoreCase("RIGHT")) {

				for (int j = 0; j < count; j++) {
					Dimension size = getDriver().manage().window().getSize();
					int endx = (int) (size.width * 0.8);
					int startx = (int) (size.width * 0.20);
					if (size.height > 2000) {
						int starty = (int) (size.height / 2);
						touchAction.press(PointOption.point(startx, starty))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
						.moveTo(PointOption.point(endx, starty)).release().perform();
					} else {
						int starty = (int) (size.height / 1.5);
						touchAction.press(PointOption.point(startx, starty))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
						.moveTo(PointOption.point(endx, starty)).release().perform();
					}

					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeRight",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
				}
			} else if (dire.equalsIgnoreCase("UP")) {

				for (int j = 0; j < count; j++) {
					Dimension size = getDriver().manage().window().getSize();
					System.out.println("size : " + size);
					int starty = (int) (size.height * 0.70);// 0.8
					int endy = (int) (size.height * 0.30);// 0.2
					int startx = size.width / 2;
					touchAction.press(PointOption.point(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
					.moveTo(PointOption.point(startx, endy)).release().perform();
					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeUp",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");

				}
			} else if (dire.equalsIgnoreCase("DOWN")) {
				for (int j = 0; j < count; j++) {

					Dimension size = getDriver().manage().window().getSize();
					int starty = (int) (size.height * 0.70);
					int endy = (int) (size.height * 0.30);
					int startx = size.width / 2;
					touchAction.press(PointOption.point(startx, endy))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(PointOption.point(startx, starty)).release().perform();
					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeDown",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
				}
			}
		} catch (Exception e) {
			// logger.error(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public void SwipeRail(By From) throws Exception {

		WebElement element = DriverManager.getDriver().findElement(From);
		Dimension size = getDriver().manage().window().getSize();
		int startx = (int) (size.width * 0.8);
		int endx = (int) (size.width * 0.1);

		String eleY = element.getAttribute("y");
		int currentPosY = Integer.parseInt(eleY);
		System.out.println(currentPosY);
		currentPosY = Integer.parseInt(eleY) + 200;
		System.out.println(currentPosY);
		touchAction = new TouchAction(getDriver());
		touchAction.press(PointOption.point(startx, currentPosY))
		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
		.moveTo(PointOption.point(endx, currentPosY)).release().perform();
	}

	/**
	 * Swipes the screen in left or right or Up or Down or direction
	 *
	 * @param direction to swipe Left or Right or Up or Down
	 * @param count     to swipe
	 */
	@SuppressWarnings("rawtypes")
	public void PartialSwipe(String direction, int count) {
		touchAction = new TouchAction(getDriver());
		String dire = direction;

		try {

			if (dire.equalsIgnoreCase("LEFT")) {

				for (int i = 0; i < count; i++) {
					Dimension size = getDriver().manage().window().getSize();
					int startx = (int) (size.width * 0.4);
					int endx = (int) (size.width * 0.1);
					int starty = size.height / 2;
					// getDriver().swipe(startx, starty, endx, starty, 1000);
					touchAction.press(PointOption.point(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(PointOption.point(endx, starty)).release().perform();
					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (i + 1) + " times");
					ExtentReporter.extentLogger("SwipeLeft",
							"Swiping screen in " + " " + dire + " direction" + " " + (i + 1) + " times");
				}
			} else if (dire.equalsIgnoreCase("RIGHT")) {

				for (int j = 0; j < count; j++) {
					Dimension size = getDriver().manage().window().getSize();
					int endx = (int) (size.width * 0.4);
					int startx = (int) (size.width * 0.1);
					int starty = size.height / 2;
					touchAction.press(PointOption.point(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(PointOption.point(endx, starty)).release().perform();
					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeRight",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
				}
			} else if (dire.equalsIgnoreCase("UP")) {

				for (int j = 0; j < count; j++) {
					Dimension size = getDriver().manage().window().getSize();
					int starty = (int) (size.height * 0.40);
					int endy = (int) (size.height * 0.1);
					int startx = size.width / 2;
					touchAction.press(PointOption.point(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(PointOption.point(startx, endy)).release().perform();
					logger.info("Swiping screen in " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeUp",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");

				}
			} else if (dire.equalsIgnoreCase("DOWN")) {
				for (int j = 0; j < count; j++) {
					Dimension size = getDriver().manage().window().getSize();
					int starty = (int) (size.height * 0.4);
					int endy = (int) (size.height * 0.1);
					int startx = size.width / 2;
					touchAction.press(PointOption.point(startx, endy))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(PointOption.point(startx, starty)).release().perform();
					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeDown",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");

				}
			}

		} catch (Exception e) {
			// logger.error(e);

		}
	}

	@SuppressWarnings("rawtypes")
	public void SwipeRailContentCards(By From) throws Exception {

		Dimension size = getDriver().manage().window().getSize();
		int screenWidth = (int) (size.width * 0.8);

		WebElement element = DriverManager.getDriver().findElement(From);
		String eleX = element.getAttribute("x");
		String eleY = element.getAttribute("y");
		int currentPosX = Integer.parseInt(eleX);
		int currentPosY = Integer.parseInt(eleY);

		currentPosX = currentPosX + screenWidth;
		currentPosY = currentPosY + 150;

		touchAction = new TouchAction(getDriver());
		touchAction.press(PointOption.point(currentPosX, currentPosY))
		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(0, currentPosY))
		.release().perform();
	}

	/**
	 * Drag window
	 *
	 * @param byLocator, byTimer
	 */
	@SuppressWarnings("rawtypes")
	public void DragWindow(By byLocator, String direction) throws Exception {
		WebElement element = getDriver().findElement(byLocator);
		touchAction = new TouchAction(getDriver());
		if (direction.equalsIgnoreCase("LEFT")) {
			Dimension size = element.getSize();
			int startx = (int) (size.width * 0.4);
			int endx = (int) (size.width * 0.1);
			int starty = size.height / 2;
			touchAction.longPress(PointOption.point(startx, starty))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
			.moveTo(PointOption.point(endx, starty)).release().perform();
			logger.info("Swiping " + " " + direction + " direction");
			ExtentReporter.extentLogger("SwipeLeft", "Swiping " + " " + direction + " direction");
		} else if (direction.equalsIgnoreCase("DOWN")) {
			Dimension size = getDriver().manage().window().getSize();
			int starty = (int) (size.height * 0.80);
			int endy = (int) (size.height * 0.20);
			int startx = size.width / 2;
			touchAction.longPress(PointOption.point(startx, endy))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
			.moveTo(PointOption.point((int) startx, (int) starty)).release().perform();
			logger.info("Swiping " + " " + direction + " direction");
			ExtentReporter.extentLogger("SwipeLeft", "Swiping " + " " + direction + " direction");
		}
	}

	/**
	 * Seek by dimensions
	 *
	 * @param byLocator
	 */

	@SuppressWarnings({ "rawtypes", "unused" })
	public void TapToSeekChromecast(By byLocator) throws Exception {
		WebElement element = getDriver().findElement(byLocator);
		Dimension size = element.getSize();
		int startx = (int) (size.width);
		TouchAction action = new TouchAction(getDriver());
		SwipeAnElement(element, startx, 0);
		logger.info("Scrolling the seek bar");
	}

	/**
	 * @param bundleID
	 */
	public void launchiOSApp(String bundleID) {

		try {
			iOSDriver = (IOSDriver<WebElement>) getDriver();
			logger.info("Started the bundle id" + " " + bundleID);
			ExtentReporter.extentLogger("Started the bundle id" + " " + bundleID,
					"Started the bundle id" + " " + bundleID);
		} catch (Exception e) {
			logger.info("Unable to Start the bundle id" + " " + bundleID);
			ExtentReporter.extentLogger("Unable to Start the bundle id" + " " + bundleID,
					"Unable to Start the bundle id" + " " + bundleID);
		}
	}

	/**
	 * Get the Mobile Orientation
	 * 
	 * @throws Exception
	 */
	public void GetAndVerifyOrientation(String Value) throws Exception {
		ScreenOrientation orientation = getDriver().getOrientation();
		String ScreenOrientation = orientation.toString();
		try {
			softAssert.assertEquals(ScreenOrientation.equalsIgnoreCase(Value), true,
					"The screen Orientation is " + ScreenOrientation);
			logger.info("The screen Orientation is " + ScreenOrientation);
			ExtentReporter.extentLogger("Screen Orientation", "The screen Orientation is " + ScreenOrientation);
		} catch (Exception e) {
			softAssert.assertEquals(false, true, "The screen Orientation is not " + ScreenOrientation);
			// softAssert.assertAll();
			logger.error("The screen Orientation is not " + ScreenOrientation);
			ExtentReporter.extentLogger("Screen Orientation", "The screen Orientation is not " + ScreenOrientation);
			ExtentReporter.screencapture();
		}
	}

	/**
	 * Closes the iOS keyboard
	 */
	public void closeIosKeyboard() {

		try {
			iOSDriver = (IOSDriver<WebElement>) getDriver();
			ExtentReporter.extentLogger("Hiding keyboard successful", "Hiding keyboard successful");
		} catch (Exception e) {
			ExtentReporter.extentLogger("Hiding keyboard not successful", "Hiding keyboard not successful");
		}
	}

	/**
	 * closes the application
	 */
	public void closeiOSApp() {
		try {
			iOSDriver = (IOSDriver<WebElement>) getDriver();
			iOSDriver.closeApp();
			ExtentReporter.extentLogger("Killed the appliaction successfully", "Killed the appliaction successfully");
		} catch (Exception e) {
			ExtentReporter.extentLogger("Unable to Kill the application", "Unable to Kill the application");

		}
	}

	/**
	 * closes the Android application
	 */
	public void closeAndroidApp() {
		try {
			getDriver().resetApp();
			ExtentReporter.extentLogger("Killed the application successfully", "Killed the application successfully");
		} catch (Exception e) {
			ExtentReporter.extentLogger("Unable to Kill the application", "Unable to Kill the application");

		}
	}

	/**
	 * Verifies if a new page or app is open
	 */

	public boolean newPageOrNt() {
		boolean app = false;
		try {
			String cmd = "adb shell \"dumpsys window windows | grep -E 'mCurrentFocus|mFocusedApp'\"";
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String outputText = "";
			while ((outputText = br.readLine()) != null) {
				if (!outputText.trim().contains("com.tv.v18.viola")) {
					app = true;
					logger.info("The app is navigated to ad page");
					ExtentReporter.extentLogger("Navigated to ad page or not", "The app is navigated to ad page");
				} else {
					logger.error("The app is not navigated to ad page");
					ExtentReporter.extentLogger("Navigated to ad page or not", "The app is not navigated to ad page");
				}
			}
			br.close();
			p.waitFor();
		} catch (Exception e) {
			// System.out.println(e);
		}
		return app;
	}

	public void IosDragWindow(By byLocator, String direction) throws Exception {
		WebElement element = getDriver().findElement(byLocator);
		@SuppressWarnings({ "rawtypes", "unused" })
		TouchAction action = new TouchAction(getDriver());
		if (direction.equalsIgnoreCase("LEFT")) {
			Dimension size = element.getSize();
			int startx = (int) (size.width * 0.4);
			System.out.println(startx);
			int endx = 0;
			System.out.println(endx);
			int starty = 1250;
			System.out.println(starty);
			SwipeAnElement(element, startx, starty);
			logger.info("Swiping " + " " + direction + " direction");
			ExtentReporter.extentLogger("SwipeLeft", "Swiping " + " " + direction + " direction");
		}
	}

	public static String getAttributValue(String property, By byLocator) throws Exception {
		String Value = null;
		WebElement element = DriverManager.getDriver().findElement(byLocator);
		Value = element.getAttribute(property);
		return Value;
	}

	/*
	 * public void captureScreenshotAndCompare(String SSName) throws
	 * InterruptedException { Thread.sleep(10000); File src =
	 * getDriver().getScreenshotAs(OutputType.FILE); String dir =
	 * System.getProperty("user.dir"); String fileName = dir +
	 * "/Applitool/baseLine/" + SSName + ".png"; System.out.println(fileName); try {
	 * FileUtils.copyFile(src, new File(fileName)); } catch (IOException e) {
	 * System.out.println(e.getMessage()); } BufferedImage img; try { img =
	 * ImageIO.read(new File(fileName)); getEye().checkImage(img, SSName);
	 * ExtentReporter.extentLogger("UI Validation", "UI for " + SSName +
	 * " is validated"); } catch (IOException e) {
	 * System.out.println(e.getMessage()); } }
	 */

	public void SwipeAnElement(WebElement element, int posx, int posy) {
		AndroidTouchAction touch = new AndroidTouchAction(getDriver());
		touch.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(element)))
		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(posx, posy))
		.release().perform();
	}

	public void longPressContent(By element) throws Exception {
		AndroidTouchAction touch = new AndroidTouchAction(getDriver());
		touch.longPress(LongPressOptions.longPressOptions()
				.withElement(ElementOption.element(getDriver().findElement(element)))).release().perform();

		TouchActions action = new TouchActions(getDriver());
		action.singleTap(getDriver().findElement(element));
		action.click();

	}

	public static boolean verifyElementExist(WebElement ele, String str) throws Exception {
		try {
			WebElement element = ele;
			if (element.isDisplayed()) {
				ExtentReporter.extentLogger("checkElementPresent", "<b>" + str + "</b> is displayed");
				logger.info("" + str + " is displayed");
				return true;
			}
		} catch (Exception e) {
			ExtentReporter.extentLogger("checkElementPresent", "<b>" + str + "</b> is not displayed");
			logger.info(str + " is not displayed");
			return false;
		}
		return false;
	}

	public boolean checkcondition(String s) throws Exception {
		boolean iselementPresent = false;
		try {
			String element = "//*[@text='[" + s + "]']";
			iselementPresent = ((WebElement) getDriver().findElementsByXPath(element)).isDisplayed();
		} catch (Exception e) {
			iselementPresent = false;
		}
		return iselementPresent;
	}

	public void switchtoLandscapeMode() throws IOException {
		Runtime.getRuntime().exec(
				"adb shell content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:1");
	}

	public void switchtoPortraitMode() throws IOException {
		Runtime.getRuntime().exec(
				"adb shell content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:0");
	}

	@SuppressWarnings("rawtypes")
	public void PartialSwipeInConsumptionScreen(String direction, int count) {
		touchAction = new TouchAction(getDriver());
		String dire = direction;

		try {

			if (dire.equalsIgnoreCase("UP")) {

				for (int j = 0; j < count; j++) {
					Dimension size = getDriver().manage().window().getSize();
					int starty = (int) (size.height * 0.8);
					int endy = (int) (size.height * 0.5);
					int startx = size.width / 2;
					touchAction.press(PointOption.point(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(PointOption.point(startx, endy)).release().perform();
					logger.info("Swiping screen in " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeUp",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");

				}
			} else if (dire.equalsIgnoreCase("DOWN")) {
				for (int j = 0; j < count; j++) {
					Dimension size = getDriver().manage().window().getSize();
					int starty = (int) (size.height * 0.8);
					int endy = (int) (size.height * 0.5);
					int startx = size.width / 2;
					touchAction.press(PointOption.point(startx, endy))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(PointOption.point(startx, starty)).release().perform();
					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeDown",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");

				}
			}

		} catch (Exception e) {
			// logger.error(e);

		}
	}

	// ====================================================================================================================================
	/** ::::::::::::::::Web Utilities:::::::::::: */

	/**
	 * Function to ExplicitWait Visibility
	 *
	 * @param element
	 * @param time
	 * @throws Exception
	 */
	public static void explicitWaitVisibility(By element, int time) throws Exception {
		wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(element)));
	}

	/**
	 * Function to ExplicitWait for Clickable
	 *
	 * @param element
	 * @param time
	 * @throws Exception
	 */
	public void explicitWaitClickable(By element, int time) throws Exception {
		wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(element)));
	}

	/**
	 * Function to ExplicitWait for windows
	 *
	 * @param noOfWindows
	 */
	public static void explicitWaitForWindows(int noOfWindows) {
		wait.until(ExpectedConditions.numberOfWindowsToBe(noOfWindows));
	}

	/**
	 * Function for ExplicitWait of Element Refresh
	 *
	 * @throws Exception
	 */
	public void explicitWaitForElementRefresh(By element) throws Exception {
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(getDriver().findElement(element))));
	}

	/**
	 * Function for implicitWait
	 */
	public void implicitWait() {
		DriverManager.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Function to select by visible text from drop down
	 *
	 * @param element
	 * @param value
	 * @throws Exception
	 */
	public void selectByVisibleTextFromDD(By element, String value) throws Exception {
		explicitWaitVisibility(element, 20);
		Select select = new Select(getDriver().findElement(element));
		select.selectByVisibleText(value);
	}

	/**
	 * Function to select by value from drop down
	 *
	 * @param element
	 * @param value
	 * @throws Exception
	 */
	public void selectByValueFromDD(By element, String value) throws Exception {
		explicitWaitVisibility(element, 20);
		Select select = new Select(getDriver().findElement(element));
		select.selectByValue(value);
	}

	/**
	 * Function to select By index From Drop down
	 *
	 * @param element
	 * @param value
	 * @throws Exception
	 */
	public void selectByIndexFromDD(By element, String value) throws Exception {
		explicitWaitVisibility(element, 20);
		Select select = new Select(getDriver().findElement(element));
		select.selectByValue(value);
	}

	/**
	 * Function to get First Element from Drop down
	 *
	 * @param element
	 * @return
	 * @throws Exception
	 */
	public String getFirstElementFromDD(By element) throws Exception {
		Select select = new Select(getDriver().findElement(element));
		return select.getFirstSelectedOption().getText();
	}

	/**
	 * Function to scroll down
	 */
	public static void scrollDownWEB() {
		js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("window.scrollBy(0,250)", "");
	}

	/**
	 * Function to Scroll By
	 */
	public static void scrollByWEB() {
		js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("window.scrollBy(0,250)", "");
	}

	/**
	 * Function to scroll bottom of page
	 */
	public static void scrollToBottomOfPageWEB() {
		js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void scrollToBottomOfPage() {
		js = (JavascriptExecutor) DriverManager.getAppiumDriver();
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/**
	 * Function to scroll to top of the page
	 */
	public static void scrollToTopOfPageWEB() {
		js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("window.scrollBy(0,-250)", "");
	}

	public static void scrollToTopOfPage() {
		js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("window.scrollBy(0,-250)", "");
	}

	/**
	 * Function Scroll to Element
	 *
	 * @param element
	 * @throws Exception
	 */
	public static void ScrollToTheElement(By element) throws Exception {
		js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", DriverManager.getDriver().findElement(element));
		js.executeScript("window.scrollBy(0,-50)", "");
	}

	/**
	 * Function to switch to window
	 *
	 * @param noOfWindows
	 */
	/*
	 * public void switchToWindow(int noOfWindows) { try {
	 * wait.until(ExpectedConditions.numberOfWindowsToBe(noOfWindows)); for (String
	 * winHandle : DriverManager.getDriver().getWindowHandles()) {
	 * win.add(winHandle); DriverManager.getDriver().switchTo().window(winHandle);
	 * DriverManager.getDriver().manage().window().maximize(); } } catch (Exception
	 * e) { System.out.println("\n No window is displayed!"); } }
	 */

	/**
	 * Function to switch to parent Window
	 */
	/*
	 * public void switchToParentWindow() { try {
	 * DriverManager.getDriver().switchTo().window(win.get(0)); } catch (Exception
	 * e) { System.out.println("\n No window is displayed!"); } }
	 */

	/**
	 * Function for hard sleep
	 *
	 * @param seconds
	 */
	public void sleep(int seconds) {
		try {
			int ms = 1000 * seconds;
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
	}

	/**
	 * Function to switch the tab
	 *
	 * @param tab
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void switchTab(int tab) {
		ArrayList<String> window = new ArrayList(DriverManager.getDriver().getWindowHandles());
		DriverManager.getDriver().switchTo().window(window.get(tab));
	}

	/**
	 * Function to generate random integer of specified maxValue
	 *
	 * @param maxValue
	 * @return
	 */
	public String generateRandomInt(int maxValue) {
		Random rand = new Random();
		int x = rand.nextInt(maxValue);
		String randomInt = Integer.toString(x);
		return randomInt;
	}

	public String RandomIntegerGenerator(int n) {
		String number = "0123456789";
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (number.length() * Math.random());

			sb.append(number.charAt(index));
		}
		return sb.toString();
	}

	/**
	 * Function to generate Random String of length 4
	 *
	 * @return
	 */
	public String generateRandomString(int size) {
		String strNumbers = "abcdefghijklmnopqrstuvwxyzacvbe";
		Random rnd = new Random();
		StringBuilder strRandomNumber = new StringBuilder(9);
		strRandomNumber.append(strNumbers.charAt(rnd.nextInt(strNumbers.length())));
		String s1 = strRandomNumber.toString().toUpperCase();
		for (int i = 1; i < size; i++) {
			strRandomNumber.append(strNumbers.charAt(rnd.nextInt(strNumbers.length())));
		}
		return s1 + strRandomNumber.toString();
	}

	/**
	 * Function to generate Random characters of specified length
	 *
	 * @param candidateChars
	 * @param length
	 * @return
	 */
	public String generateRandomChars(String candidateChars, int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}
		return sb.toString();
	}

	/**
	 * Function to generate Random Integer between range
	 *
	 * @param maxValue
	 * @param minValue
	 * @return
	 * @throws InterruptedException
	 */
	public String generateRandomIntwithrange(int maxValue, int minValue) throws InterruptedException {
		Thread.sleep(2000);
		Random rand = new Random();
		int x = rand.nextInt(maxValue - minValue) + 1;
		String randomInt = Integer.toString(x);
		System.out.println("Auto generate of number from generic method : " + randomInt);
		return randomInt;
	}

	/**
	 * Function to drag and drop an object
	 *
	 * @param From
	 * @param To
	 * @throws Exception
	 */
	public void dragnddrop(By From, By To) throws Exception {
		WebElement Drag = getDriver().findElement(From);
		WebElement Drop = getDriver().findElement(To);
		Thread.sleep(1000);
		Actions builder = new Actions(getDriver());
		builder.clickAndHold(Drag).moveToElement(Drop).release(Drop).build().perform();
	}

	/**
	 * Function Convert from String to Integer @param(String)
	 */
	public int convertToInt(String string) {
		int i = Integer.parseInt(string);
		return i;
	}

	/**
	 * Function Convert from Integer to String @param(integer)
	 */
	public String convertToString(int i) {
		String string = Integer.toString(i);
		return string;
	}

	/**
	 * Click On element without waiting or verifying
	 *
	 * @param byLocator the by locator
	 *
	 */
	public static void clickDirectly(By byLocator, String validationtext) throws Exception {
		try {
			getDriver().findElement(byLocator).click();
			logger.info("Clicked on the " + validationtext);
			ExtentReporter.extentLogger("click", "Clicked on the <b> " + validationtext);
		} catch (Exception e) {
			// logger.error(e);
			ExtentReporter.screencapture();
		}
	}

	public static void verifyAlert() {
		try {
			DriverManager.getDriver().switchTo().alert().dismiss();
			logger.info("Dismissed the alert Pop Up");
			ExtentReporter.extentLogger("Alert PopUp", "Dismissed the alert Pop Up");
		} catch (Exception e) {

		}
	}

	public void acceptAlert() {
		try {
			DriverManager.getDriver().switchTo().alert().accept();
			logger.info("Dismissed the alert Pop Up");
			ExtentReporter.extentLogger("Alert PopUp", "Dismissed the alert Pop Up");
		} catch (Exception e) {

		}
	}

	public static boolean clickElementWithLocator(By locator) throws Exception {
		String url = Utilities.getParameterFromXML("url");
		String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		if (platform.equalsIgnoreCase("web")) {
			try {
				DriverManager.getDriver().findElement(locator).click();
				return true;
			} catch (Exception e) {
			}
		} else if (platform.equalsIgnoreCase("mpwa")) {
			try {
				DriverManager.getAppiumDriver().findElement(locator).click();
				return true;
			} catch (Exception e) {
			}
		}
		return false;
	}

	public static boolean clickElementWithWebElement(WebElement element) throws Exception {
		try {
			element.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static int timeToSec(String s) {
		String[] t = s.split(":");
		int num = 0;
		System.out.println(t.length);

		if (t.length == 2) {
			num = Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]); // minutes since 00:00
		}
		if (t.length == 3) {
			num = ((Integer.parseInt(t[0]) * 60) * 60) + Integer.parseInt(t[1]) * 60 + Integer.parseInt(t[2]);
		}

		return num;
	}

	public static void partialScrollDown() {
		JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
		jse.executeScript("window.scrollBy(0,500)", "");
	}

	public static void clickByElement(WebElement ele, String validationtext) throws Exception {
		try {
			WebElement element = ele;
			element.click();
			logger.info("Clicked on the " + validationtext);
			ExtentReporter.extentLogger("click", "Clicked on the <b> " + validationtext);
		} catch (Exception e) {
			logger.error(e);
			ExtentReporter.screencapture();
		}
	}

	public static boolean verifyElementEnabled(By byLocator, String str) throws Exception {

		try {
			WebElement element = DriverManager.getDriver().findElement(byLocator);
			if (element.isEnabled()) {
				ExtentReporter.extentLogger("checkElementPresent", "" + str + " is displayed");
				logger.info("" + str + " is displayed");
				return true;
			}
		} catch (Exception e) {
			ExtentReporter.extentLogger("checkElementPresent", "" + str + " is not displayed");
			logger.info(str + " is not displayed");
			return false;
		}
		return false;
	}

	public static int getCountweb(By byLocator) {
		int count = 0;
		try {
			count = DriverManager.getDriver().findElements(byLocator).size();
			logger.info("List count for" + " " + byLocator + " " + "is" + " " + count);
			ExtentReporter.extentLogger("getCount", "List count for" + " " + byLocator + " " + "is" + " " + count);
		} catch (Exception e) {
			// logger.error(e);
		}
		return count;
	}

	public static boolean waitForElementAndClickIfPresent(By locator, int seconds, String message) throws Exception {
		try {
			if (getPlatform().equals("Web")) {
				for (int time = 0; time <= seconds; time++) {
					try {
						DriverManager.getDriver().findElement(locator).click();
						logger.info("Clicked element " + message);
						ExtentReporter.extentLogger("clickedElement", "Clicked element " + message);
						return true;
					} catch (Exception e) {
						Thread.sleep(1000);
					}
				}
			} else if (getPlatform().equals("Android") || getPlatform().equals("MPWA")) {
				for (int time = 0; time <= seconds; time++) {
					try {
						getDriver().findElement(locator).click();
						logger.info("Clicked on " + message);
						ExtentReporter.extentLogger("clickedElement", "Clicked on " + message);
						return true;
					} catch (Exception e) {
						Thread.sleep(1000);
					}
				}
			}
		} catch (Exception e) {
			// logger.error(e);
			ExtentReporter.screencapture();
		}
		return false;
	}

	public static String RandomStringGenerator(int n) {
		{

			String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
			StringBuilder sb = new StringBuilder(n);
			for (int i = 0; i < n; i++) {
				int index = (int) (AlphaNumericString.length() * Math.random());

				sb.append(AlphaNumericString.charAt(index));
			}
			return sb.toString();
		}
	}

	/**
	 * Method to swipe bottom of page
	 * 
	 * @throws Exception
	 */
	public static void swipeToBottomOfPage() throws Exception {
		for (int i = 0; i < 5; i++) {
			scrollToBottomOfPage();
			waitTime(4000);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void androidSwitchTab() {
		ArrayList<String> window = new ArrayList(getDriver().getWindowHandles());
		getDriver().switchTo().window(window.get(window.size() - 1));
	}

	/**
	 * Function to switch to parent Window
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void AndroidSwitchToParentWindow() {
		try {
			ArrayList<String> window = new ArrayList(getDriver().getWindowHandles());
			getDriver().switchTo().window(window.get(0));
		} catch (Exception e) {
			System.out.println("\n No window is displayed!");
		}
	}

	public static String getTheOSVersion() {
		String version = null;
		try {
			String cmd1 = "adb shell getprop ro.build.version.release";
			Process p1 = Runtime.getRuntime().exec(cmd1);
			BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			// outputText1 ="";
			while ((version = br.readLine()) != null) {
				logger.info("Version :: " + version.toString());
				Thread.sleep(3000);
				break;
			}

		} catch (Exception e) {
			// logger.error(e);
		}
		return version;
	}

	public void TurnOFFWifi() throws IOException {
		String Deviceversion = getTheOSVersion();
		System.out.println("Turn off wifi");
		if (Deviceversion.contains("6")) {
			Runtime.getRuntime().exec("adb shell am broadcast -a io.appium.settings.wifi --es setstatus disable");
			logger.info("Turning off wifi");
			ExtentReporter.extentLogger("Turning off wifi", "Turning off wifi");
		} else {
			Runtime.getRuntime().exec("adb shell svc wifi disable");
			logger.info("Turning off wifi");
			ExtentReporter.extentLogger("Turning off wifi", "Turning off wifi");
		}
	}

	// public void TurnONWifi() throws IOException {
	// String Deviceversion = getTheOSVersion();
	// System.out.println("Turn on wifi");
	// if (Deviceversion.contains("6")) {
	// Runtime.getRuntime().exec("adb shell am broadcast -a io.appium.settings.wifi
	// --es setstatus enable");
	// logger.info("Turning ON wifi");
	// ExtentReporter.extentLogger("Turning ON wifi", "Turning ON wifi");
	// } else {
	// Runtime.getRuntime().exec("adb shell svc wifi enable");
	// logger.info("Turning ON wifi");
	// ExtentReporter.extentLogger("Turning ON wifi", "Turning ON wifi");
	// }
	// }

	@SuppressWarnings("rawtypes")
	public void carouselSwipe(String direction, int count) {
		touchAction = new TouchAction(getDriver());
		String dire = direction;
		try {
			if (dire.equalsIgnoreCase("LEFT")) {

				for (int i = 0; i < count; i++) {
					Dimension size = getDriver().manage().window().getSize();

					int startx = (int) (size.width * 0.9);
					int endx = (int) (size.width * 0.20);
					int starty = size.height / 2;
					touchAction.press(PointOption.point(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(PointOption.point(endx, starty)).release().perform();
					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (i + 1) + " times");
					ExtentReporter.extentLogger("SwipeLeft",
							"Swiping screen in " + " " + dire + " direction" + " " + (i + 1) + " times");
				}
			} else if (dire.equalsIgnoreCase("RIGHT")) {

				for (int j = 0; j < count; j++) {
					Dimension size = getDriver().manage().window().getSize();
					int endx = (int) (size.width * 0.9);
					int startx = (int) (size.width * 0.20);
					if (size.height > 2000) {
						int starty = (int) (size.height / 2);
						touchAction.press(PointOption.point(startx, starty))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
						.moveTo(PointOption.point(endx, starty)).release().perform();
					} else {
						int starty = (int) (size.height / 1.5);
						touchAction.press(PointOption.point(startx, starty))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
						.moveTo(PointOption.point(endx, starty)).release().perform();
					}

					logger.info("Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeRight",
							"Swiping screen in " + " " + dire + " direction" + " " + (j + 1) + " times");
				}
			}
		} catch (Exception e) {
			// logger.error(e);

		}
	}

	public static void ScrollToTheElementWEB(By element) throws Exception {
		js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", DriverManager.getDriver().findElement(element));
		js.executeScript("window.scrollBy(0,-250)", "");
	}

	/**
	 * Function to Initialize mandatoryRegistrationPopUp count to one
	 *
	 * @param userType
	 */
	public static void mandatoryRegistrationPopUp(String userType) {
		String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		if (userType.contains("Guest")) {
			if (platform.equalsIgnoreCase("web")) {
				JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
				jse.executeScript("window.localStorage.setItem('mandatoryRegistrationVideoCount','1')");
			} else if (platform.equalsIgnoreCase("mpwa")) {
				JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getAppiumDriver();
				jse.executeScript("window.localStorage.setItem('mandatoryRegistrationVideoCount','1')");
			}
		}
	}

	public static boolean checkElementDisplayed(By byLocator, String str) throws Exception {
		String platform = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
		if (platform.equalsIgnoreCase("web")) {
			try {
				WebElement element = DriverManager.getDriver().findElement(byLocator);
				if (element.isDisplayed()) {
					ExtentReporter.extentLogger("checkElementPresent", "" + str + " is displayed");
					logger.info("" + str + " is displayed");
					return true;
				}
			} catch (Exception e) {
				ExtentReporter.extentLogger("checkElementPresent", "" + str + " is not displayed");
				logger.info("" + str + " is not displayed");
				return false;
			}
		} else if (platform.equalsIgnoreCase("mpwa")) {
			try {
				WebElement element = DriverManager.getAppiumDriver().findElement(byLocator);
				if (element.isDisplayed()) {
					ExtentReporter.extentLogger("checkElementPresent", "" + str + " is displayed");
					logger.info("" + str + " is displayed");
					return true;
				}
			} catch (Exception e) {
				ExtentReporter.extentLogger("checkElementPresent", "" + str + " is not displayed");
				logger.info("" + str + " is not displayed");
				return false;
			}
		}
		return false;
	}

	public static String getParameterFromXML(String param) {
		return ExtentReporter.testContext.getCurrentXmlTest().getParameter(param);
	}

	@SuppressWarnings("rawtypes")
	public void SwipeInLandscapeMode(String direction, int count) {
		touchAction = new TouchAction(getDriver());
		String dire = direction;
		try {
			if (dire.equalsIgnoreCase("DOWN") | dire.equalsIgnoreCase("LEFT")) {

				for (int i = 0; i < count; i++) {
					Dimension size = getDriver().manage().window().getSize();
					int xCor = (int) (size.height / 2);
					int startY = (int) (size.width * 0.20);
					int endY = (int) (size.width * 0.85);
					System.out.println(startY + " X " + endY);
					touchAction.press(PointOption.point(xCor, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(PointOption.point(xCor, endY)).release().perform();
					logger.info("Swiping screen in " + dire + " direction" + (i + 1) + " times");
					ExtentReporter.extentLogger("SwipeLeft",
							"Swiping screen in " + dire + " direction" + (i + 1) + " times");
				}
			} else if (dire.equalsIgnoreCase("UP") | dire.equalsIgnoreCase("RIGHT")) {

				for (int j = 0; j < count; j++) {
					Dimension size = getDriver().manage().window().getSize();
					int xCor = (int) (size.height / 2);
					int startY = (int) (size.width * 0.85);
					int endY = (int) (size.width * 0.20);
					System.out.println(startY + " X " + endY);
					System.out.println(xCor);
					touchAction.press(PointOption.point(xCor, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(PointOption.point(xCor, endY)).release().perform();

					logger.info("Swiping screen in " + dire + " direction " + (j + 1) + " times");
					ExtentReporter.extentLogger("SwipeRight",
							"Swiping screen in " + dire + " direction " + (j + 1) + " times");
				}
			}

		} catch (Exception e) {
			// logger.error(e);
		}
	}

	public void clearBackgroundApps() throws IOException {
		String adbRecentApp = "adb shell input keyevent KEYCODE_APP_SWITCH";
		String adbSelectApp = "adb shell input keyevent KEYCODE_DPAD_DOWN";
		String adbClearApp = "adb shell input keyevent KEYCODE_DEL";
		String adbHomeScreen = "adb shell input keyevent KEYCODE_HOME";

		Runtime.getRuntime().exec(adbRecentApp);

		for (int iterator = 1; iterator <= 7; iterator++) {
			waitTime(1000);
			Runtime.getRuntime().exec(adbClearApp);
			Runtime.getRuntime().exec(adbSelectApp);
		}

		waitTime(1000);
		Runtime.getRuntime().exec(adbHomeScreen);
		System.out.println("Cleared all background Apps");
	}

	public boolean findElementInRefreshingConvivaPage(WebDriver webdriver, By locator, String displayText)
			throws Exception {
		js = (JavascriptExecutor) DriverManager.getDriver();
		for (int i = 1; i <= 500; i++) {
			DriverManager.getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			try {
				DriverManager.getDriver().findElement(locator);
				logger.info(displayText + " is displayed");
				ExtentReporter.extentLogger("", displayText + " is displayed");
				return true;
			} catch (Exception e) {
				try {
					js.executeScript("window.scrollBy(0,100)", "");
					waitTime(2000);
					System.out.println("Waiting ..");
				} catch (Exception e1) {
				}
			}
		}
		return false;
	}

	// ====================================================TV=================================================
	// public boolean verifyElementExistTv(By byLocator, String str) throws
	// Exception {
	//
	// try {
	//
	// if (getDriver().findElement(byLocator).isDisplayed()) {
	// ExtentReporter.extentLogger("checkElementPresent", str + " is displayed");
	// logger.info("" + str + " is displayed");
	// return true;
	// }
	// } catch (Exception e) {
	// ExtentReporter.extentLogger("checkElementPresent", str + " is not
	// displayed");
	// logger.info(str + " is not displayed");
	// return false;
	// }
	// return false;
	// }
	//
	// public void TVclick(By byLocator, String validationtext) throws Exception {
	//
	// try {
	// getDriver().findElement(byLocator).click();
	// logger.info("Clicked on " + validationtext);
	// ExtentReporter.extentLogger("click", "Clicked on " + validationtext);
	// } catch (Exception e) {
	// // logger.error(e);
	// }
	// }
	//
	// public String TVgetText(By byLocator) throws Exception {
	// String Value = null;
	// Value = getDriver().findElement(byLocator).getText();
	// return Value;
	// }
	//
	// public boolean TVVerifyElementNotPresent(By byLocator, int waitTime)
	// {
	// try{
	// WebDriverWait wait = new WebDriverWait(getDriver(), waitTime);
	// wait.until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
	// return false;
	// }catch(Exception e)
	// {
	// return true;
	// }
	// }
	//
	//
	// public String TVgetAttributValue(String property, By byLocator) throws
	// Exception {
	// String Value = null;
	// Value = getDriver().findElement(byLocator).getAttribute(property);
	// return Value;
	// }
	//
	// public void BrowsertearDown() {
	// DriverManager.getDriver().quit();
	// }
	//
	// public void decode() {
	// CTUserName = new
	// String(Base64.getDecoder().decode(getParameterFromXML("CTUser")));
	// CTPWD = new String(Base64.getDecoder().decode(getParameterFromXML("CTPwd")));
	// }
	//
	// /**
	// * The method will wait for the element to not be located for a maximum of
	// given
	// * seconds. The method terminates immediately once the element is located and
	// * throws error.
	// */
	// public static void waitForElementAbsence(By locator, int seconds, String
	// message) throws InterruptedException {
	// main: for (int time = 0; time <= seconds; time++) {
	// try {
	// Utilities.findElement(locator);
	// logger.error("Located element " + message);
	// ExtentReporter.extentLoggerFail("locatedElement", "Located element " +
	// message);
	// break main;
	// } catch (Exception e) {
	// Thread.sleep(1000);
	// if (time == seconds) {
	// logger.info("Expected behavior: " + message + " is not displayed");
	// ExtentReporter.extentLogger("failedLocateElement", "Expected behavior: " +
	// message + " is not displayed");
	// }
	// }
	// }
	// }
	//
	// /**
	// * This method will wait for element presence till the given time
	// * @param locator
	// * @param seconds
	// * @param message
	// * @return
	// * @throws Exception
	// */
	// public static boolean waitForElementPresence(By locator, int seconds, String
	// message) throws Exception {
	// try {
	// String platform =
	// Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
	// if(platform.equalsIgnoreCase("web")) {
	// WebDriverWait w = new WebDriverWait(DriverManager.getDriver(), seconds);
	// w.until(ExpectedConditions.presenceOfElementLocated(locator));
	// logger.info(message + " is displayed");
	// ExtentReporter.extentLogger("element is displayed", message + " is
	// displayed");
	// return true;
	// }else if(platform.equalsIgnoreCase("mpwa")) {
	// WebDriverWait w = new WebDriverWait(DriverManager.getAppiumDriver(),
	// seconds);
	// w.until(ExpectedConditions.presenceOfElementLocated(locator));
	// logger.info(message + " is displayed");
	// ExtentReporter.extentLogger("element is displayed", message + " is
	// displayed");
	// return true;
	// }
	// }
	// catch (Exception e) {}
	// return false;
	// }
	//
	// /**
	// * This method will wait for element presence till the given time
	// * @param locator
	// * @param seconds
	// * @param message
	// * @return
	// * @throws Exception
	// */
	// public static boolean waitForElementVisible(By locator, int seconds, String
	// message) throws Exception {
	// try {
	// WebDriverWait w = new WebDriverWait(DriverManager.getDriver(), seconds);
	// w.until(ExpectedConditions.visibilityOfElementLocated(locator));
	// logger.info(message + " is displayed");
	// ExtentReporter.extentLogger("element is displayed", message + " is
	// displayed");
	// return true;
	// } catch (Exception e) {
	// return false;
	// }
	// }
	//
	// /**
	// * Generic method to return browser current url
	// * @return
	// * @throws Exception
	// */
	// public static String getBrowserCurrentUrl() throws Exception {
	// try {
	// return (DriverManager.getDriver().getCurrentUrl());
	// } catch (Exception e) {
	// return "";
	// }
	// }
	//
	// /**
	// * Function to switch to window
	// * @param noOfWindows
	// */
	// public static void switchToWindow(int noOfWindows) {
	// try {
	// wait.until(ExpectedConditions.numberOfWindowsToBe(noOfWindows));
	// for (String winHandle : DriverManager.getDriver().getWindowHandles()) {
	// win.add(winHandle);
	// DriverManager.getDriver().switchTo().window(winHandle);
	// DriverManager.getDriver().manage().window().maximize();
	// }
	// } catch (Exception e) {
	// System.out.println("\n No window is displayed!");
	// }
	// }
	//
	// /**
	// * Method to Move to Element using Actions
	// * @param title
	// * @throws Exception
	// */
	// public static void moveToElementAction(WebElement element, String message)
	// throws Exception {
	// try {
	// Actions a = new Actions(DriverManager.getDriver());
	// a.moveToElement(element).build().perform();
	// logger.info("Moved to element "+message);
	// ExtentReporter.extentLogger("", "Moved to element "+message);
	// }
	// catch(Exception e) {
	// logger.error("Failed to move to element "+message);
	// ExtentReporter.extentLoggerFail("", "Failed to move to element "+message);
	// }
	// }
	//
	// public static void waitUntilElementVisible_NoCustomMessage(By by) {
	// String platform =
	// Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
	// try {
	// if(platform.equalsIgnoreCase("web")) {
	// WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), 20);
	// wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	// }
	// if(platform.equalsIgnoreCase("mpwa")) {
	// WebDriverWait wait = new WebDriverWait(DriverManager.getAppiumDriver(), 20);
	// wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	// }
	//
	// } catch (Exception e) {
	// throw new ElementNotVisibleException(getCustomErrorMessageForReport(by),
	// e.getCause());
	// } finally {}
	// }
	//
	// public static boolean isPresentWithWait(By by) {
	// boolean flag = true;
	// try {
	// waitUntilElementVisible_NoCustomMessage(by);
	// } catch (Exception e) {
	// flag = false;
	// }
	// return flag;
	// }
	//
	// public static String getCustomErrorMessageForReport(By by) {
	// String className = by.getClass().getSimpleName();
	// String value = "";
	// try {
	// Field field = by.getClass().getDeclaredFields()[1];
	// field.setAccessible(true);
	// value = field.get(by).toString();
	// } catch (Exception ignored) {
	// }
	// return "Timed out waiting for visibility of element located = " + className +
	// "(" + value + ")";
	//
	// }
	//
	// public static void waitForElementAndClick(By locator, int seconds, String
	// message) throws Exception {
	// String platform =
	// Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName();
	// main: for (int time = 0; time <= seconds; time++) {
	// try {
	// if(platform.equalsIgnoreCase("web")) {
	// DriverManager.getDriver().findElement(locator).click();
	// }
	// else if(platform.equalsIgnoreCase("mpwa")) {
	// DriverManager.getAppiumDriver().findElement(locator).click();
	// }
	// logger.info("Clicked element " + message);
	// ExtentReporter.extentLogger("clickedElement", "Clicked element " + message);
	// break main;
	// } catch (Exception e) {
	// Thread.sleep(1000);
	// if (time == seconds) {
	// logger.error("Failed to click element " + message);
	// ExtentReporter.extentLoggerFail("failedClickElement", "Failed to click
	// element " + message);
	// }
	// }
	// }
	// }
	//
	// /***********
	// * Method Name : Text Field Clear in Android
	// *
	// * @Param by Locator
	// */
	//
	// public static void androidClearText(By byLocator, String text) {
	// try {
	// AndroidElement ele = (AndroidElement) getDriver().findElement(byLocator);
	// TouchAction t = new TouchAction(getDriver());
	// t.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(ele))
	// .withDuration(Duration.ofSeconds(2))).release().perform();
	// DriverManager.getAppiumDriver().findElement(byLocator).clear();
	// logger.info("Cleared the text in : " + text);
	// ExtentReporter.extentLogger("clear text", "Cleared the text in : " + text);
	// } catch (Exception e) {
	// logger.error(e);
	// }
	// }
	//
	// public static void suggestionTapMidScreen(By locator) throws Exception {
	// int guideSuggestion = 1;
	// for (int i = 0; i < guideSuggestion; i++) {
	// tapOnFirstSuggestion(locator);
	// waitTime(2000);
	// if (verifyElementDisplayed(locator)) {
	// guideSuggestion++;
	// } else {
	// break;
	// }
	// }
	// }
	//
	// /**
	// * @tap on the center of center for tour dashboard
	// * @param locator
	// * @return
	// * @throws Exception
	// */
	// public static int tapOnFirstSuggestion(By locator) throws Exception {
	// WebElement element = getDriver().findElement(locator);
	// String tourText = getText(locator);
	// Point point = element.getLocation();
	// // capturing height & length of the element
	// int length = element.getSize().getWidth();
	// int height = element.getSize().getHeight();
	// // capturing the stating Y co-ordinate
	// int getY = point.getY();
	// // moving to the middle of the suggestion from the Y co-ordinate
	// int middleY = (int) (getY + height * 1.5);
	// TouchAction ta = new TouchAction(getDriver());
	// waitTime(2000);
	// ta.tap(PointOption.point(length / 2, middleY)).perform();
	// logger.info(tourText + " dashboard tour is displayed Tapped at the center of
	// Screen");
	// ExtentReporter.extentLogger("Tour", tourText + " dashboard tour is displayed
	// Tapped at the center of Screen");
	// return middleY;
	// }

	// ----------------------------------------------------------------------------------------------------

	// execute query from DB
	public static String executeQuery(String dbTable,int column_no) throws SQLException, ClassNotFoundException {
		// Setting the driver
		String ref_no = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			// Open a connection to the database

			java.sql.Connection con = java.sql.DriverManager.getConnection(prop.getproperty("dbHostUrl"),prop.getproperty("dbUserName"),prop.getproperty("dbPassword"));
			java.sql.Statement st = con.createStatement();



			java.sql.ResultSet rs = st.executeQuery(dbTable);



			while (rs.next()) {
				System.out.println("=========================================");
				ref_no = rs.getString(column_no);
				System.out.println("Database Value: " + rs.getString(column_no));
				System.out.println("=========================================");
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ref_no;
	}



	public void executeUpdate(String updateExecutionQuery , String dbTable) throws ClassNotFoundException, SQLException {



		// Setting the driver
		Class.forName("com.mysql.cj.jdbc.Driver");



		// Open a connection to the database
		java.sql.Connection con = java.sql.DriverManager.getConnection(prop.getproperty("dbHostUrl"),prop.getproperty("dbUserName"),prop.getproperty("dbPassword"));
		java.sql.Statement st = con.createStatement();



		// Executing the update query
		st.executeUpdate(updateExecutionQuery);



		java.sql.ResultSet rs = st.executeQuery(dbTable);
		System.out.println("=================================================================================================");
		while (rs.next()) {
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4)
			+ " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " "
			+ rs.getString(8));
		}
		con.close();
	}

	public void executeInsert(String dbTable/*String refNo, String eligibilityType, String approved_amount,String offer_id,String repeat_offer_id*/) throws ClassNotFoundException, SQLException {



		// Setting the driver
		Class.forName("com.mysql.cj.jdbc.Driver");



		// Open a connection to the database
		java.sql.Connection con = java.sql.DriverManager.getConnection(prop.getproperty("dbHostUrl"),prop.getproperty("dbUserName"),prop.getproperty("dbPassword"));
		java.sql.Statement st = con.createStatement();



		java.sql.PreparedStatement ps = con.prepareStatement(dbTable /*"Insert into db_tradofina.instaloan_whitelisted_users(user_reference_number,eligible_type,approved_amount,offer_id,repeat_offer_id)VALUES('" +refNo+ "','" +eligibilityType+ "','" +approved_amount+ "','" +offer_id+ "','" +repeat_offer_id+ "')"*/);
		ps.executeUpdate();
		con.close();
	}

	/**
	 * 
	 * @param data
	 * @param url
	 * @return
	 * @throws Exception 
	 */

	public static ValidatableResponse SendOtpAPI(Object[][] data) throws Exception {

		try {
			Random rand = new Random();

			String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.sendOtpEndPoint);
			logger.info("Url :" + url);

			ExtentReporter.extentLogger("url", url);

			HashMap<String, String> req_body = new HashMap<>();
			req_body.put("source_app", (String) data[0][0]);
			req_body.put("mobile_number", (String) data[0][1]);

			JSONObject Myrequestbody = new JSONObject();

			Myrequestbody.put("source_app", req_body.get("source_app"));
			Myrequestbody.put("mobile_number", req_body.get("mobile_number"));


			String req=String.valueOf(Myrequestbody);
			ExtentReporter.extentLogger("req_body", "Request :"+req);


			HashMap<String, Object> headers = new HashMap<>();
			headers.put("x-request-id", rand.nextInt(1001));
			headers.put("X-Client-App", "android");
			headers.put("X-Client-Version", 4.9);
			headers.put("X-Client-OS-Type", "android");
			headers.put("X-Client-OS-Version", 10);
			headers.put("x-login-token",
					"eyJhbGciOiJSUzI1NiIsIng1YyI6WyJNSUlGWVRDQ0JFbWdBd0lCQWdJUkFQaEtkUXdrSUFNRENRQUFBQUM4QzZvd0RRWUpLb1pJaHZjTkFRRUxCUUF3UmpFTE1Ba0dBMVVFQmhNQ1ZWTXhJakFnQmdOVkJBb1RHVWR2YjJkc1pTQlVjblZ6ZENCVFpYSjJhV05sY3lCTVRFTXhFekFSQmdOVkJBTVRDa2RVVXlCRFFTQXhSRFF3SGhjTk1qRXhNREUzTVRjd05qQTNXaGNOTWpJd01URTFNVGN3TmpBMldqQWRNUnN3R1FZRFZRUURFeEpoZEhSbGMzUXVZVzVrY205cFpDNWpiMjB3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRQ3ZnU2VHM3JTVlcwSVBpWkJGVmJoMktjYjNoTnl3R2VJOUZmaVgyUXZRQnBmUkIvT0xiUUFwZGdDWTZJL1dqNEw0aHVNQzRMVHA3OFZXbmhtZGJ3Y1NxbXJzNkpDM3kwWnVmVm4ydzhsV0NYODNsYytFUmdRVHhmaGUwTVNIakhlWk9mWGROQ3dqejZrTXJkZEVPUlJ5T3V3SWdjcXcrNGoycS9mSktHbkUyNXQ5NndOTDgrUDg1V294ZXhaZEROR1pzMmkzNmRvZkdVTGR1YTZaWFI1YjFlODJkd0dra0Rkd3RFMjZCeDRhTTl4VDEwK3A0S3FKNXZ0MWpvY1N0K2tTWHFRaEowQlJjS082OWhGUTRDSUdKYk5EYlRIMENGYlMvanJsNThGWnhVTUVwaUNHbG9JdmJyZ20xSlFzRDE2UmtIZlQ0NVM5UERNc3k5WFI4bjVqQWdNQkFBR2pnZ0p4TUlJQ2JUQU9CZ05WSFE4QkFmOEVCQU1DQmFBd0V3WURWUjBsQkF3d0NnWUlLd1lCQlFVSEF3RXdEQVlEVlIwVEFRSC9CQUl3QURBZEJnTlZIUTRFRmdRVUJ0M1lUWkFYZ3pGYXdpV2FXN3hmaStYRDhnZ3dId1lEVlIwakJCZ3dGb0FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd2JRWUlLd1lCQlFVSEFRRUVZVEJmTUNvR0NDc0dBUVVGQnpBQmhoNW9kSFJ3T2k4dmIyTnpjQzV3YTJrdVoyOXZaeTluZEhNeFpEUnBiblF3TVFZSUt3WUJCUVVITUFLR0pXaDBkSEE2THk5d2Eya3VaMjl2Wnk5eVpYQnZMMk5sY25SekwyZDBjekZrTkM1a1pYSXdIUVlEVlIwUkJCWXdGSUlTWVhSMFpYTjBMbUZ1WkhKdmFXUXVZMjl0TUNFR0ExVWRJQVFhTUJnd0NBWUdaNEVNQVFJQk1Bd0dDaXNHQVFRQjFua0NCUU13UHdZRFZSMGZCRGd3TmpBMG9ES2dNSVl1YUhSMGNEb3ZMMk55YkhNdWNHdHBMbWR2YjJjdlozUnpNV1EwYVc1MEwxZ3lTakpJY2w4M1VHbE5MbU55YkRDQ0FRUUdDaXNHQVFRQjFua0NCQUlFZ2ZVRWdmSUE4QUIxQUZHanNQWDlBWG1jVm0yNE4zaVBES1I2ekJzbnkvZWVpRUthRGY3VWl3WGxBQUFCZkk5dXVqSUFBQVFEQUVZd1JBSWdYd3JxbEEvV21IRFVySVpSWDIrS24raldjRVlsQjliVCtsRk9HT3RaTEtNQ0lGUzRXYU14Q09GaVAxTnhVN3hMcVBQVGlwR2dlaFgwS0IwTFgrTXhkdEl0QUhjQUtYbSs4SjQ1T1NId1ZuT2ZZNlYzNWI1WGZaeGdDdmo1VFYwbVhDVmR4NFFBQUFGOGoyNjZLUUFBQkFNQVNEQkdBaUVBNDdRNldJYmVnQUZuL0liUUM5OEFoR0dlY0xGVWowcjRCMnlrSkFlN2tzd0NJUURiQ2RNNFdzQ2JVUHJsSDhIV3M1ZGpqQWluKy9jWDZPNHpDTldMbzJxakhEQU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FRRUFMWHlhOUhVVm5rZURkUFgyd0tzQ2QybDhNcGpTeW5iVWVKWGI5Um04dXRsczRjRzkvdXEzRzZ3clRGWkNhdldJMnE5SmxlUnA1Q21DeCtrcElPVVh3T0dPQUZ3SVFrUFhCRnFrOGJscmE1MmhGTTluMUROYzY1bmNVRHkybXFYbjNXaVByN0crZEdSNlkzRnFKMjQ3K0VySlllbTZnM28rR3ZVcERxbWpkZ01SdHFFTXlmTVZIa0xoN3ZucWlXdnYzQ2VlU1ViRjkvMFdxUklNdTdPSFZyTkVET1ZUUEZuWENVczgyUk1OVVd0dVJTS1Njelh3QXFNN0JFWGR4TjNYcXE1Z1dOUDdUeFowczZzRTZGOHovWmN0OFVLdHRkNVBidGhrdGdFMmVvUmFaYTB1alNWVmtUeTVGb1pvMWJ1ZXhjbnM5WjlEWDFCUy9RU1JXbjNBUHc9PSIsIk1JSUZqRENDQTNTZ0F3SUJBZ0lOQWdDT3NnSXpObVdMWk0zYm16QU5CZ2txaGtpRzl3MEJBUXNGQURCSE1Rc3dDUVlEVlFRR0V3SlZVekVpTUNBR0ExVUVDaE1aUjI5dloyeGxJRlJ5ZFhOMElGTmxjblpwWTJWeklFeE1RekVVTUJJR0ExVUVBeE1MUjFSVElGSnZiM1FnVWpFd0hoY05NakF3T0RFek1EQXdNRFF5V2hjTk1qY3dPVE13TURBd01EUXlXakJHTVFzd0NRWURWUVFHRXdKVlV6RWlNQ0FHQTFVRUNoTVpSMjl2WjJ4bElGUnlkWE4wSUZObGNuWnBZMlZ6SUV4TVF6RVRNQkVHQTFVRUF4TUtSMVJUSUVOQklERkVORENDQVNJd0RRWUpLb1pJaHZjTkFRRUJCUUFEZ2dFUEFEQ0NBUW9DZ2dFQkFLdkFxcVBDRTI3bDB3OXpDOGRUUElFODliQSt4VG1EYUc3eTdWZlE0YyttT1dobFVlYlVRcEsweXYycjY3OFJKRXhLMEhXRGplcStuTElITjFFbTVqNnJBUlppeG15UlNqaElSMEtPUVBHQk1VbGRzYXp0SUlKN08wZy84MnFqL3ZHRGwvLzN0NHRUcXhpUmhMUW5UTFhKZGVCKzJEaGtkVTZJSWd4NndON0U1TmNVSDNSY3NlamNxajhwNVNqMTl2Qm02aTFGaHFMR3ltaE1Gcm9XVlVHTzN4dElIOTFkc2d5NGVGS2NmS1ZMV0szbzIxOTBRMExtL1NpS21MYlJKNUF1NHkxZXVGSm0ySk05ZUI4NEZrcWEzaXZyWFdVZVZ0eWUwQ1FkS3ZzWTJGa2F6dnh0eHZ1c0xKekxXWUhrNTV6Y1JBYWNEQTJTZUV0QmJRZkQxcXNDQXdFQUFhT0NBWFl3Z2dGeU1BNEdBMVVkRHdFQi93UUVBd0lCaGpBZEJnTlZIU1VFRmpBVUJnZ3JCZ0VGQlFjREFRWUlLd1lCQlFVSEF3SXdFZ1lEVlIwVEFRSC9CQWd3QmdFQi93SUJBREFkQmdOVkhRNEVGZ1FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd0h3WURWUjBqQkJnd0ZvQVU1SzhySm5FYUswZ25oUzlTWml6djhJa1RjVDR3YUFZSUt3WUJCUVVIQVFFRVhEQmFNQ1lHQ0NzR0FRVUZCekFCaGhwb2RIUndPaTh2YjJOemNDNXdhMmt1WjI5dlp5OW5kSE55TVRBd0JnZ3JCZ0VGQlFjd0FvWWthSFIwY0RvdkwzQnJhUzVuYjI5bkwzSmxjRzh2WTJWeWRITXZaM1J6Y2pFdVpHVnlNRFFHQTFVZEh3UXRNQ3N3S2FBbm9DV0dJMmgwZEhBNkx5OWpjbXd1Y0d0cExtZHZiMmN2WjNSemNqRXZaM1J6Y2pFdVkzSnNNRTBHQTFVZElBUkdNRVF3Q0FZR1o0RU1BUUlCTURnR0Npc0dBUVFCMW5rQ0JRTXdLakFvQmdnckJnRUZCUWNDQVJZY2FIUjBjSE02THk5d2Eya3VaMjl2Wnk5eVpYQnZjMmwwYjNKNUx6QU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FnRUFJVlRveTI0andYVXIwckFQYzkyNHZ1U1ZiS1F1WXczbkxmbExmTGg1QVlXRWVWbC9EdTE4UUFXVU1kY0o2by9xRlpiaFhrQkgwUE5jdzk3dGhhZjJCZW9EWVk5Q2svYitVR2x1aHgwNnpkNEVCZjdIOVA4NG5ucndwUis0R0JEWksrWGgzSTB0cUp5MnJnT3FORGZscjVJTVE4WlRXQTN5bHRha3pTQktaNlhwRjBQcHF5Q1J2cC9OQ0d2MktYMlR1UENKdnNjcDEvbTJwVlR0eUJqWVBSUStRdUNRR0FKS2p0TjdSNURGcmZUcU1XdllnVmxwQ0pCa3dsdTcrN0tZM2NUSWZ6RTdjbUFMc2tNS05MdUR6K1J6Q2NzWVRzVmFVN1ZwM3hMNjBPWWhxRmt1QU9PeERaNnBIT2o5K09KbVlnUG1PVDRYMys3TDUxZlhKeVJIOUtmTFJQNm5UMzFENW5tc0dBT2daMjYvOFQ5aHNCVzF1bzlqdTVmWkxaWFZWUzVIMEh5SUJNRUt5R01JUGhGV3JsdC9oRlMyOE4xemFLSTBaQkdEM2dZZ0RMYmlEVDlmR1hzdHBrK0ZtYzRvbFZsV1B6WGU4MXZkb0VuRmJyNU0yNzJIZGdKV28rV2hUOUJZTTBKaSt3ZFZtblJmZlhnbG9Fb2x1VE5jV3pjNDFkRnBnSnU4ZkYzTEcwZ2wyaWJTWWlDaTlhNmh2VTBUcHBqSnlJV1hoa0pUY01KbFByV3gxVnl0RVVHclgybDBKRHdSalcvNjU2cjBLVkIwMnhIUkt2bTJaS0kwM1RnbExJcG1WQ0sza0JLa0tOcEJOa0Z0OHJoYWZjQ0tPYjlKeC85dHBORmxRVGw3QjM5ckpsSldrUjE3UW5acVZwdEZlUEZPUm9abUZ6TT0iLCJNSUlGWWpDQ0JFcWdBd0lCQWdJUWQ3ME5iTnMyK1JycUlRL0U4RmpURFRBTkJna3Foa2lHOXcwQkFRc0ZBREJYTVFzd0NRWURWUVFHRXdKQ1JURVpNQmNHQTFVRUNoTVFSMnh2WW1Gc1UybG5iaUJ1ZGkxellURVFNQTRHQTFVRUN4TUhVbTl2ZENCRFFURWJNQmtHQTFVRUF4TVNSMnh2WW1Gc1UybG5iaUJTYjI5MElFTkJNQjRYRFRJd01EWXhPVEF3TURBME1sb1hEVEk0TURFeU9EQXdNREEwTWxvd1J6RUxNQWtHQTFVRUJoTUNWVk14SWpBZ0JnTlZCQW9UR1VkdmIyZHNaU0JVY25WemRDQlRaWEoyYVdObGN5Qk1URU14RkRBU0JnTlZCQU1UQzBkVVV5QlNiMjkwSUZJeE1JSUNJakFOQmdrcWhraUc5dzBCQVFFRkFBT0NBZzhBTUlJQ0NnS0NBZ0VBdGhFQ2l4N2pvWGViTzl5L2xENjNsYWRBUEtIOWd2bDlNZ2FDY2ZiMmpILzc2TnU4YWk2WGw2T01TL2tyOXJINXpvUWRzZm5GbDk3dnVmS2o2YndTaVY2bnFsS3IrQ01ueTZTeG5HUGIxNWwrOEFwZTYyaW05TVphUncxTkVEUGpUckVUbzhnWWJFdnMvQW1RMzUxa0tTVWpCNkcwMGowdVlPRFAwZ21IdTgxSThFM0N3bnFJaXJ1Nnoxa1oxcStQc0Fld25qSHhnc0hBM3k2bWJXd1pEclhZZmlZYVJRTTlzSG1rbENpdEQzOG01YWdJL3Bib1BHaVVVKzZET29nckZaWUpzdUI2akM1MTFwenJwMVprajVaUGFLNDlsOEtFajhDOFFNQUxYTDMyaDdNMWJLd1lVSCtFNEV6Tmt0TWc2VE84VXBtdk1yVXBzeVVxdEVqNWN1SEtaUGZtZ2hDTjZKM0Npb2o2T0dhSy9HUDVBZmw0L1h0Y2QvcDJoL3JzMzdFT2VaVlh0TDBtNzlZQjBlc1dDcnVPQzdYRnhZcFZxOU9zNnBGTEtjd1pwRElsVGlyeFpVVFFBczZxemttMDZwOThnN0JBZStkRHE2ZHNvNDk5aVlINlRLWC8xWTdEemt2Z3RkaXpqa1hQZHNEdFFDdjlVdyt3cDlVN0RiR0tvZ1BlTWEzTWQrcHZlejdXMzVFaUV1YSsrdGd5L0JCakZGRnkzbDNXRnBPOUtXZ3o3enBtN0FlS0p0OFQxMWRsZUNmZVhra1VBS0lBZjVxb0liYXBzWld3cGJrTkZoSGF4MnhJUEVEZ2ZnMWF6Vlk4MFpjRnVjdEw3VGxMbk1RLzBsVVRiaVN3MW5INjlNRzZ6TzBiOWY2QlFkZ0FtRDA2eUs1Nm1EY1lCWlVDQXdFQUFhT0NBVGd3Z2dFME1BNEdBMVVkRHdFQi93UUVBd0lCaGpBUEJnTlZIUk1CQWY4RUJUQURBUUgvTUIwR0ExVWREZ1FXQkJUa3J5c21jUm9yU0NlRkwxSm1MTy93aVJOeFBqQWZCZ05WSFNNRUdEQVdnQlJnZTJZYVJRMlh5b2xRTDMwRXpUU28vL3o5U3pCZ0JnZ3JCZ0VGQlFjQkFRUlVNRkl3SlFZSUt3WUJCUVVITUFHR0dXaDBkSEE2THk5dlkzTndMbkJyYVM1bmIyOW5MMmR6Y2pFd0tRWUlLd1lCQlFVSE1BS0dIV2gwZEhBNkx5OXdhMmt1WjI5dlp5OW5jM0l4TDJkemNqRXVZM0owTURJR0ExVWRId1FyTUNrd0o2QWxvQ09HSVdoMGRIQTZMeTlqY213dWNHdHBMbWR2YjJjdlozTnlNUzluYzNJeExtTnliREE3QmdOVkhTQUVOREF5TUFnR0JtZUJEQUVDQVRBSUJnWm5nUXdCQWdJd0RRWUxLd1lCQkFIV2VRSUZBd0l3RFFZTEt3WUJCQUhXZVFJRkF3TXdEUVlKS29aSWh2Y05BUUVMQlFBRGdnRUJBRFNrSHJFb285QzBkaGVtTVhvaDZkRlNQc2piZEJaQmlMZzlOUjN0NVArVDRWeGZxN3ZxZk0vYjVBM1JpMWZ5Sm05YnZoZEdhSlEzYjJ0NnlNQVlOL29sVWF6c2FMK3l5RW45V3ByS0FTT3NoSUFyQW95WmwrdEphb3gxMThmZXNzbVhuMWhJVnc0MW9lUWExdjF2ZzRGdjc0elBsNi9BaFNydzlVNXBDWkV0NFdpNHdTdHo2ZFRaL0NMQU54OExaaDFKN1FKVmoyZmhNdGZUSnI5dzR6MzBaMjA5Zk9VMGlPTXkrcWR1Qm1wdnZZdVI3aFpMNkR1cHN6Zm53MFNrZnRoczE4ZEc5WktiNTlVaHZtYVNHWlJWYk5RcHNnM0JabHZpZDBsSUtPMmQxeG96Y2xPemdqWFBZb3ZKSkl1bHR6a011MzRxUWI5U3oveWlscmJDZ2o4PSJdfQ.eyJub25jZSI6IlBvSEJNR1FXVTZMTHZuQ21tQUlqUkt4dTJ4ND0iLCJ0aW1lc3RhbXBNcyI6MTYzNzc1MTY1NTE2OSwiYXBrUGFja2FnZU5hbWUiOiJjb20uZmFzdGJhbmtpbmcuZGVidWciLCJhcGtEaWdlc3RTaGEyNTYiOiJsRHF1bDJxejdyd2owRDFJSzBkcTZwTnNaUmR0QW9BbUNNOVh5MGg2bkNjPSIsImN0c1Byb2ZpbGVNYXRjaCI6dHJ1ZSwiYXBrQ2VydGlmaWNhdGVEaWdlc3RTaGEyNTYiOlsiR3k3N1doNFRkR0ZXd3NoaS9VVXdDdUJIL0NBZ2V4VFFLdmJzbW5pWHFpTT0iXSwiYmFzaWNJbnRlZ3JpdHkiOnRydWUsImV2YWx1YXRpb25UeXBlIjoiQkFTSUMsSEFSRFdBUkVfQkFDS0VEIn0.ShOvWqQ_5i-T1ixx59sbk0-6LMo8oKiC5PfZCt9dVJrnfeap8JMQ9x8v19-Yh-M07y54BjQPXFGU-Y602uFc_V7TKHonDqjaEOsx6VfRwiQeZmtaO-Hhmlr2g-xRHFoDOnXy2wHYGfDkMbir50EraIyny3xfs-guIDMwg5qAzQaN999KRsrbHXX-a6wwoQ0qyUSVKGN57T_qOcXaq9X5bI1B3nD1m5Inu7TW0xrCb0sfUn8GDimAtnXELKf048S4iaXBObbgtiNyVQtTEfqHA8WdfhANIZWcV4XQDHbv69wcvrmUTDeZJienIfkmesfYnFDngW2NfR9A9m_Q5sorig");
			headers.put("x-login-nonce", "B6B667EB514890789F56F9B78BFA509AB41B673B");
			headers.put("x-login-timestamp", "1636960116339");


			String header=String.valueOf(headers);
			ExtentReporter.extentLogger("header","Headers :"+ header);



			ValidatableResponse response = Utilities.postMethodAPI(headers, Myrequestbody, url);

			logger.info("Request :" + Myrequestbody);
			ExtentReporter.extentLogger("", "Request :" + Myrequestbody);
			String Resp = response.extract().body().asString();
			logger.info("Response Body= " + Resp);
			ExtentReporter.extentLogger("", "Response Body= " + Resp);

			return response;

		}

		catch (Exception e) {
			String message="SendOtpAPI";
			ExtentReporter.extentLoggerFail(message+" - Failed");	
			return null;
		}

	}


	//	public static ValidatableResponse LessThan10DigitAPI(Object[][] data) {
	//
	//		Random rand = new Random();
	//
	//		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.sendOtpEndPoint);
	//		System.out.println("Url :" + url);
	//
	//		HashMap<String, String> req_body = new HashMap<>();
	//		// System.out.println((String) data[0][3]);
	//		req_body.put("source_app", (String) data[0][0]);
	//		req_body.put("mobile_number", (String) data[0][1]);
	//
	//		JSONObject Myrequestbody = new JSONObject();
	//
	//		Myrequestbody.put("source_app", req_body.get("source_app"));
	//		Myrequestbody.put("mobile_number", req_body.get("mobile_number"));
	//
	//		HashMap<String, Object> headers = new HashMap<>();
	//		headers.put("x-request-id", rand.nextInt(1001));
	//		headers.put("X-Client-App", "android");
	//		headers.put("X-Client-Version", 4.9);
	//		headers.put("X-Client-OS-Type", "android");
	//		headers.put("X-Client-OS-Version", 10);
	//		headers.put("x-login-token",
	//				"eyJhbGciOiJSUzI1NiIsIng1YyI6WyJNSUlGWVRDQ0JFbWdBd0lCQWdJUkFQaEtkUXdrSUFNRENRQUFBQUM4QzZvd0RRWUpLb1pJaHZjTkFRRUxCUUF3UmpFTE1Ba0dBMVVFQmhNQ1ZWTXhJakFnQmdOVkJBb1RHVWR2YjJkc1pTQlVjblZ6ZENCVFpYSjJhV05sY3lCTVRFTXhFekFSQmdOVkJBTVRDa2RVVXlCRFFTQXhSRFF3SGhjTk1qRXhNREUzTVRjd05qQTNXaGNOTWpJd01URTFNVGN3TmpBMldqQWRNUnN3R1FZRFZRUURFeEpoZEhSbGMzUXVZVzVrY205cFpDNWpiMjB3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRQ3ZnU2VHM3JTVlcwSVBpWkJGVmJoMktjYjNoTnl3R2VJOUZmaVgyUXZRQnBmUkIvT0xiUUFwZGdDWTZJL1dqNEw0aHVNQzRMVHA3OFZXbmhtZGJ3Y1NxbXJzNkpDM3kwWnVmVm4ydzhsV0NYODNsYytFUmdRVHhmaGUwTVNIakhlWk9mWGROQ3dqejZrTXJkZEVPUlJ5T3V3SWdjcXcrNGoycS9mSktHbkUyNXQ5NndOTDgrUDg1V294ZXhaZEROR1pzMmkzNmRvZkdVTGR1YTZaWFI1YjFlODJkd0dra0Rkd3RFMjZCeDRhTTl4VDEwK3A0S3FKNXZ0MWpvY1N0K2tTWHFRaEowQlJjS082OWhGUTRDSUdKYk5EYlRIMENGYlMvanJsNThGWnhVTUVwaUNHbG9JdmJyZ20xSlFzRDE2UmtIZlQ0NVM5UERNc3k5WFI4bjVqQWdNQkFBR2pnZ0p4TUlJQ2JUQU9CZ05WSFE4QkFmOEVCQU1DQmFBd0V3WURWUjBsQkF3d0NnWUlLd1lCQlFVSEF3RXdEQVlEVlIwVEFRSC9CQUl3QURBZEJnTlZIUTRFRmdRVUJ0M1lUWkFYZ3pGYXdpV2FXN3hmaStYRDhnZ3dId1lEVlIwakJCZ3dGb0FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd2JRWUlLd1lCQlFVSEFRRUVZVEJmTUNvR0NDc0dBUVVGQnpBQmhoNW9kSFJ3T2k4dmIyTnpjQzV3YTJrdVoyOXZaeTluZEhNeFpEUnBiblF3TVFZSUt3WUJCUVVITUFLR0pXaDBkSEE2THk5d2Eya3VaMjl2Wnk5eVpYQnZMMk5sY25SekwyZDBjekZrTkM1a1pYSXdIUVlEVlIwUkJCWXdGSUlTWVhSMFpYTjBMbUZ1WkhKdmFXUXVZMjl0TUNFR0ExVWRJQVFhTUJnd0NBWUdaNEVNQVFJQk1Bd0dDaXNHQVFRQjFua0NCUU13UHdZRFZSMGZCRGd3TmpBMG9ES2dNSVl1YUhSMGNEb3ZMMk55YkhNdWNHdHBMbWR2YjJjdlozUnpNV1EwYVc1MEwxZ3lTakpJY2w4M1VHbE5MbU55YkRDQ0FRUUdDaXNHQVFRQjFua0NCQUlFZ2ZVRWdmSUE4QUIxQUZHanNQWDlBWG1jVm0yNE4zaVBES1I2ekJzbnkvZWVpRUthRGY3VWl3WGxBQUFCZkk5dXVqSUFBQVFEQUVZd1JBSWdYd3JxbEEvV21IRFVySVpSWDIrS24raldjRVlsQjliVCtsRk9HT3RaTEtNQ0lGUzRXYU14Q09GaVAxTnhVN3hMcVBQVGlwR2dlaFgwS0IwTFgrTXhkdEl0QUhjQUtYbSs4SjQ1T1NId1ZuT2ZZNlYzNWI1WGZaeGdDdmo1VFYwbVhDVmR4NFFBQUFGOGoyNjZLUUFBQkFNQVNEQkdBaUVBNDdRNldJYmVnQUZuL0liUUM5OEFoR0dlY0xGVWowcjRCMnlrSkFlN2tzd0NJUURiQ2RNNFdzQ2JVUHJsSDhIV3M1ZGpqQWluKy9jWDZPNHpDTldMbzJxakhEQU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FRRUFMWHlhOUhVVm5rZURkUFgyd0tzQ2QybDhNcGpTeW5iVWVKWGI5Um04dXRsczRjRzkvdXEzRzZ3clRGWkNhdldJMnE5SmxlUnA1Q21DeCtrcElPVVh3T0dPQUZ3SVFrUFhCRnFrOGJscmE1MmhGTTluMUROYzY1bmNVRHkybXFYbjNXaVByN0crZEdSNlkzRnFKMjQ3K0VySlllbTZnM28rR3ZVcERxbWpkZ01SdHFFTXlmTVZIa0xoN3ZucWlXdnYzQ2VlU1ViRjkvMFdxUklNdTdPSFZyTkVET1ZUUEZuWENVczgyUk1OVVd0dVJTS1Njelh3QXFNN0JFWGR4TjNYcXE1Z1dOUDdUeFowczZzRTZGOHovWmN0OFVLdHRkNVBidGhrdGdFMmVvUmFaYTB1alNWVmtUeTVGb1pvMWJ1ZXhjbnM5WjlEWDFCUy9RU1JXbjNBUHc9PSIsIk1JSUZqRENDQTNTZ0F3SUJBZ0lOQWdDT3NnSXpObVdMWk0zYm16QU5CZ2txaGtpRzl3MEJBUXNGQURCSE1Rc3dDUVlEVlFRR0V3SlZVekVpTUNBR0ExVUVDaE1aUjI5dloyeGxJRlJ5ZFhOMElGTmxjblpwWTJWeklFeE1RekVVTUJJR0ExVUVBeE1MUjFSVElGSnZiM1FnVWpFd0hoY05NakF3T0RFek1EQXdNRFF5V2hjTk1qY3dPVE13TURBd01EUXlXakJHTVFzd0NRWURWUVFHRXdKVlV6RWlNQ0FHQTFVRUNoTVpSMjl2WjJ4bElGUnlkWE4wSUZObGNuWnBZMlZ6SUV4TVF6RVRNQkVHQTFVRUF4TUtSMVJUSUVOQklERkVORENDQVNJd0RRWUpLb1pJaHZjTkFRRUJCUUFEZ2dFUEFEQ0NBUW9DZ2dFQkFLdkFxcVBDRTI3bDB3OXpDOGRUUElFODliQSt4VG1EYUc3eTdWZlE0YyttT1dobFVlYlVRcEsweXYycjY3OFJKRXhLMEhXRGplcStuTElITjFFbTVqNnJBUlppeG15UlNqaElSMEtPUVBHQk1VbGRzYXp0SUlKN08wZy84MnFqL3ZHRGwvLzN0NHRUcXhpUmhMUW5UTFhKZGVCKzJEaGtkVTZJSWd4NndON0U1TmNVSDNSY3NlamNxajhwNVNqMTl2Qm02aTFGaHFMR3ltaE1Gcm9XVlVHTzN4dElIOTFkc2d5NGVGS2NmS1ZMV0szbzIxOTBRMExtL1NpS21MYlJKNUF1NHkxZXVGSm0ySk05ZUI4NEZrcWEzaXZyWFdVZVZ0eWUwQ1FkS3ZzWTJGa2F6dnh0eHZ1c0xKekxXWUhrNTV6Y1JBYWNEQTJTZUV0QmJRZkQxcXNDQXdFQUFhT0NBWFl3Z2dGeU1BNEdBMVVkRHdFQi93UUVBd0lCaGpBZEJnTlZIU1VFRmpBVUJnZ3JCZ0VGQlFjREFRWUlLd1lCQlFVSEF3SXdFZ1lEVlIwVEFRSC9CQWd3QmdFQi93SUJBREFkQmdOVkhRNEVGZ1FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd0h3WURWUjBqQkJnd0ZvQVU1SzhySm5FYUswZ25oUzlTWml6djhJa1RjVDR3YUFZSUt3WUJCUVVIQVFFRVhEQmFNQ1lHQ0NzR0FRVUZCekFCaGhwb2RIUndPaTh2YjJOemNDNXdhMmt1WjI5dlp5OW5kSE55TVRBd0JnZ3JCZ0VGQlFjd0FvWWthSFIwY0RvdkwzQnJhUzVuYjI5bkwzSmxjRzh2WTJWeWRITXZaM1J6Y2pFdVpHVnlNRFFHQTFVZEh3UXRNQ3N3S2FBbm9DV0dJMmgwZEhBNkx5OWpjbXd1Y0d0cExtZHZiMmN2WjNSemNqRXZaM1J6Y2pFdVkzSnNNRTBHQTFVZElBUkdNRVF3Q0FZR1o0RU1BUUlCTURnR0Npc0dBUVFCMW5rQ0JRTXdLakFvQmdnckJnRUZCUWNDQVJZY2FIUjBjSE02THk5d2Eya3VaMjl2Wnk5eVpYQnZjMmwwYjNKNUx6QU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FnRUFJVlRveTI0andYVXIwckFQYzkyNHZ1U1ZiS1F1WXczbkxmbExmTGg1QVlXRWVWbC9EdTE4UUFXVU1kY0o2by9xRlpiaFhrQkgwUE5jdzk3dGhhZjJCZW9EWVk5Q2svYitVR2x1aHgwNnpkNEVCZjdIOVA4NG5ucndwUis0R0JEWksrWGgzSTB0cUp5MnJnT3FORGZscjVJTVE4WlRXQTN5bHRha3pTQktaNlhwRjBQcHF5Q1J2cC9OQ0d2MktYMlR1UENKdnNjcDEvbTJwVlR0eUJqWVBSUStRdUNRR0FKS2p0TjdSNURGcmZUcU1XdllnVmxwQ0pCa3dsdTcrN0tZM2NUSWZ6RTdjbUFMc2tNS05MdUR6K1J6Q2NzWVRzVmFVN1ZwM3hMNjBPWWhxRmt1QU9PeERaNnBIT2o5K09KbVlnUG1PVDRYMys3TDUxZlhKeVJIOUtmTFJQNm5UMzFENW5tc0dBT2daMjYvOFQ5aHNCVzF1bzlqdTVmWkxaWFZWUzVIMEh5SUJNRUt5R01JUGhGV3JsdC9oRlMyOE4xemFLSTBaQkdEM2dZZ0RMYmlEVDlmR1hzdHBrK0ZtYzRvbFZsV1B6WGU4MXZkb0VuRmJyNU0yNzJIZGdKV28rV2hUOUJZTTBKaSt3ZFZtblJmZlhnbG9Fb2x1VE5jV3pjNDFkRnBnSnU4ZkYzTEcwZ2wyaWJTWWlDaTlhNmh2VTBUcHBqSnlJV1hoa0pUY01KbFByV3gxVnl0RVVHclgybDBKRHdSalcvNjU2cjBLVkIwMnhIUkt2bTJaS0kwM1RnbExJcG1WQ0sza0JLa0tOcEJOa0Z0OHJoYWZjQ0tPYjlKeC85dHBORmxRVGw3QjM5ckpsSldrUjE3UW5acVZwdEZlUEZPUm9abUZ6TT0iLCJNSUlGWWpDQ0JFcWdBd0lCQWdJUWQ3ME5iTnMyK1JycUlRL0U4RmpURFRBTkJna3Foa2lHOXcwQkFRc0ZBREJYTVFzd0NRWURWUVFHRXdKQ1JURVpNQmNHQTFVRUNoTVFSMnh2WW1Gc1UybG5iaUJ1ZGkxellURVFNQTRHQTFVRUN4TUhVbTl2ZENCRFFURWJNQmtHQTFVRUF4TVNSMnh2WW1Gc1UybG5iaUJTYjI5MElFTkJNQjRYRFRJd01EWXhPVEF3TURBME1sb1hEVEk0TURFeU9EQXdNREEwTWxvd1J6RUxNQWtHQTFVRUJoTUNWVk14SWpBZ0JnTlZCQW9UR1VkdmIyZHNaU0JVY25WemRDQlRaWEoyYVdObGN5Qk1URU14RkRBU0JnTlZCQU1UQzBkVVV5QlNiMjkwSUZJeE1JSUNJakFOQmdrcWhraUc5dzBCQVFFRkFBT0NBZzhBTUlJQ0NnS0NBZ0VBdGhFQ2l4N2pvWGViTzl5L2xENjNsYWRBUEtIOWd2bDlNZ2FDY2ZiMmpILzc2TnU4YWk2WGw2T01TL2tyOXJINXpvUWRzZm5GbDk3dnVmS2o2YndTaVY2bnFsS3IrQ01ueTZTeG5HUGIxNWwrOEFwZTYyaW05TVphUncxTkVEUGpUckVUbzhnWWJFdnMvQW1RMzUxa0tTVWpCNkcwMGowdVlPRFAwZ21IdTgxSThFM0N3bnFJaXJ1Nnoxa1oxcStQc0Fld25qSHhnc0hBM3k2bWJXd1pEclhZZmlZYVJRTTlzSG1rbENpdEQzOG01YWdJL3Bib1BHaVVVKzZET29nckZaWUpzdUI2akM1MTFwenJwMVprajVaUGFLNDlsOEtFajhDOFFNQUxYTDMyaDdNMWJLd1lVSCtFNEV6Tmt0TWc2VE84VXBtdk1yVXBzeVVxdEVqNWN1SEtaUGZtZ2hDTjZKM0Npb2o2T0dhSy9HUDVBZmw0L1h0Y2QvcDJoL3JzMzdFT2VaVlh0TDBtNzlZQjBlc1dDcnVPQzdYRnhZcFZxOU9zNnBGTEtjd1pwRElsVGlyeFpVVFFBczZxemttMDZwOThnN0JBZStkRHE2ZHNvNDk5aVlINlRLWC8xWTdEemt2Z3RkaXpqa1hQZHNEdFFDdjlVdyt3cDlVN0RiR0tvZ1BlTWEzTWQrcHZlejdXMzVFaUV1YSsrdGd5L0JCakZGRnkzbDNXRnBPOUtXZ3o3enBtN0FlS0p0OFQxMWRsZUNmZVhra1VBS0lBZjVxb0liYXBzWld3cGJrTkZoSGF4MnhJUEVEZ2ZnMWF6Vlk4MFpjRnVjdEw3VGxMbk1RLzBsVVRiaVN3MW5INjlNRzZ6TzBiOWY2QlFkZ0FtRDA2eUs1Nm1EY1lCWlVDQXdFQUFhT0NBVGd3Z2dFME1BNEdBMVVkRHdFQi93UUVBd0lCaGpBUEJnTlZIUk1CQWY4RUJUQURBUUgvTUIwR0ExVWREZ1FXQkJUa3J5c21jUm9yU0NlRkwxSm1MTy93aVJOeFBqQWZCZ05WSFNNRUdEQVdnQlJnZTJZYVJRMlh5b2xRTDMwRXpUU28vL3o5U3pCZ0JnZ3JCZ0VGQlFjQkFRUlVNRkl3SlFZSUt3WUJCUVVITUFHR0dXaDBkSEE2THk5dlkzTndMbkJyYVM1bmIyOW5MMmR6Y2pFd0tRWUlLd1lCQlFVSE1BS0dIV2gwZEhBNkx5OXdhMmt1WjI5dlp5OW5jM0l4TDJkemNqRXVZM0owTURJR0ExVWRId1FyTUNrd0o2QWxvQ09HSVdoMGRIQTZMeTlqY213dWNHdHBMbWR2YjJjdlozTnlNUzluYzNJeExtTnliREE3QmdOVkhTQUVOREF5TUFnR0JtZUJEQUVDQVRBSUJnWm5nUXdCQWdJd0RRWUxLd1lCQkFIV2VRSUZBd0l3RFFZTEt3WUJCQUhXZVFJRkF3TXdEUVlKS29aSWh2Y05BUUVMQlFBRGdnRUJBRFNrSHJFb285QzBkaGVtTVhvaDZkRlNQc2piZEJaQmlMZzlOUjN0NVArVDRWeGZxN3ZxZk0vYjVBM1JpMWZ5Sm05YnZoZEdhSlEzYjJ0NnlNQVlOL29sVWF6c2FMK3l5RW45V3ByS0FTT3NoSUFyQW95WmwrdEphb3gxMThmZXNzbVhuMWhJVnc0MW9lUWExdjF2ZzRGdjc0elBsNi9BaFNydzlVNXBDWkV0NFdpNHdTdHo2ZFRaL0NMQU54OExaaDFKN1FKVmoyZmhNdGZUSnI5dzR6MzBaMjA5Zk9VMGlPTXkrcWR1Qm1wdnZZdVI3aFpMNkR1cHN6Zm53MFNrZnRoczE4ZEc5WktiNTlVaHZtYVNHWlJWYk5RcHNnM0JabHZpZDBsSUtPMmQxeG96Y2xPemdqWFBZb3ZKSkl1bHR6a011MzRxUWI5U3oveWlscmJDZ2o4PSJdfQ.eyJub25jZSI6IlBvSEJNR1FXVTZMTHZuQ21tQUlqUkt4dTJ4ND0iLCJ0aW1lc3RhbXBNcyI6MTYzNzc1MTY1NTE2OSwiYXBrUGFja2FnZU5hbWUiOiJjb20uZmFzdGJhbmtpbmcuZGVidWciLCJhcGtEaWdlc3RTaGEyNTYiOiJsRHF1bDJxejdyd2owRDFJSzBkcTZwTnNaUmR0QW9BbUNNOVh5MGg2bkNjPSIsImN0c1Byb2ZpbGVNYXRjaCI6dHJ1ZSwiYXBrQ2VydGlmaWNhdGVEaWdlc3RTaGEyNTYiOlsiR3k3N1doNFRkR0ZXd3NoaS9VVXdDdUJIL0NBZ2V4VFFLdmJzbW5pWHFpTT0iXSwiYmFzaWNJbnRlZ3JpdHkiOnRydWUsImV2YWx1YXRpb25UeXBlIjoiQkFTSUMsSEFSRFdBUkVfQkFDS0VEIn0.ShOvWqQ_5i-T1ixx59sbk0-6LMo8oKiC5PfZCt9dVJrnfeap8JMQ9x8v19-Yh-M07y54BjQPXFGU-Y602uFc_V7TKHonDqjaEOsx6VfRwiQeZmtaO-Hhmlr2g-xRHFoDOnXy2wHYGfDkMbir50EraIyny3xfs-guIDMwg5qAzQaN999KRsrbHXX-a6wwoQ0qyUSVKGN57T_qOcXaq9X5bI1B3nD1m5Inu7TW0xrCb0sfUn8GDimAtnXELKf048S4iaXBObbgtiNyVQtTEfqHA8WdfhANIZWcV4XQDHbv69wcvrmUTDeZJienIfkmesfYnFDngW2NfR9A9m_Q5sorig");
	//		headers.put("x-login-nonce", "B6B667EB514890789F56F9B78BFA509AB41B673B");
	//		headers.put("x-login-timestamp", "1636960116339");
	//
	//		ValidatableResponse response = RestAssured.given().baseUri(url).contentType(ContentType.JSON).headers(headers)
	//				.body(Myrequestbody.toJSONString()).when().post().then();
	//
	//		System.out.println("Request Url -->" + url);
	//		ExtentReporter.extentLogger("", "Request Url -->" + url);
	//		System.out.println("Request :" + Myrequestbody);
	//		ExtentReporter.extentLogger("", "Request :" + Myrequestbody);
	//		String Resp = response.extract().body().asString();
	//		System.out.println("Response Body= " + Resp);
	//		ExtentReporter.extentLogger("", "Response Body= " + Resp);
	//
	//		return response;
	//	}
	//
	//	public static ValidatableResponse MoreThan10DigitAPI(Object[][] data) {
	//
	//		Random rand = new Random();
	//
	//		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.sendOtpEndPoint);
	//		System.out.println("Url :" + url);
	//
	//		HashMap<String, String> req_body = new HashMap<>();
	//		// System.out.println((String) data[0][3]);
	//		req_body.put("source_app", (String) data[0][0]);
	//		req_body.put("mobile_number", (String) data[0][1]);
	//
	//		JSONObject Myrequestbody = new JSONObject();
	//
	//		Myrequestbody.put("source_app", req_body.get("source_app"));
	//		Myrequestbody.put("mobile_number", req_body.get("mobile_number"));
	//
	//		HashMap<String, Object> headers = new HashMap<>();
	//		headers.put("x-request-id", rand.nextInt(1001));
	//		headers.put("X-Client-App", "android");
	//		headers.put("X-Client-Version", 4.9);
	//		headers.put("X-Client-OS-Type", "android");
	//		headers.put("X-Client-OS-Version", 10);
	//		headers.put("x-login-token",
	//				"eyJhbGciOiJSUzI1NiIsIng1YyI6WyJNSUlGWVRDQ0JFbWdBd0lCQWdJUkFQaEtkUXdrSUFNRENRQUFBQUM4QzZvd0RRWUpLb1pJaHZjTkFRRUxCUUF3UmpFTE1Ba0dBMVVFQmhNQ1ZWTXhJakFnQmdOVkJBb1RHVWR2YjJkc1pTQlVjblZ6ZENCVFpYSjJhV05sY3lCTVRFTXhFekFSQmdOVkJBTVRDa2RVVXlCRFFTQXhSRFF3SGhjTk1qRXhNREUzTVRjd05qQTNXaGNOTWpJd01URTFNVGN3TmpBMldqQWRNUnN3R1FZRFZRUURFeEpoZEhSbGMzUXVZVzVrY205cFpDNWpiMjB3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRQ3ZnU2VHM3JTVlcwSVBpWkJGVmJoMktjYjNoTnl3R2VJOUZmaVgyUXZRQnBmUkIvT0xiUUFwZGdDWTZJL1dqNEw0aHVNQzRMVHA3OFZXbmhtZGJ3Y1NxbXJzNkpDM3kwWnVmVm4ydzhsV0NYODNsYytFUmdRVHhmaGUwTVNIakhlWk9mWGROQ3dqejZrTXJkZEVPUlJ5T3V3SWdjcXcrNGoycS9mSktHbkUyNXQ5NndOTDgrUDg1V294ZXhaZEROR1pzMmkzNmRvZkdVTGR1YTZaWFI1YjFlODJkd0dra0Rkd3RFMjZCeDRhTTl4VDEwK3A0S3FKNXZ0MWpvY1N0K2tTWHFRaEowQlJjS082OWhGUTRDSUdKYk5EYlRIMENGYlMvanJsNThGWnhVTUVwaUNHbG9JdmJyZ20xSlFzRDE2UmtIZlQ0NVM5UERNc3k5WFI4bjVqQWdNQkFBR2pnZ0p4TUlJQ2JUQU9CZ05WSFE4QkFmOEVCQU1DQmFBd0V3WURWUjBsQkF3d0NnWUlLd1lCQlFVSEF3RXdEQVlEVlIwVEFRSC9CQUl3QURBZEJnTlZIUTRFRmdRVUJ0M1lUWkFYZ3pGYXdpV2FXN3hmaStYRDhnZ3dId1lEVlIwakJCZ3dGb0FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd2JRWUlLd1lCQlFVSEFRRUVZVEJmTUNvR0NDc0dBUVVGQnpBQmhoNW9kSFJ3T2k4dmIyTnpjQzV3YTJrdVoyOXZaeTluZEhNeFpEUnBiblF3TVFZSUt3WUJCUVVITUFLR0pXaDBkSEE2THk5d2Eya3VaMjl2Wnk5eVpYQnZMMk5sY25SekwyZDBjekZrTkM1a1pYSXdIUVlEVlIwUkJCWXdGSUlTWVhSMFpYTjBMbUZ1WkhKdmFXUXVZMjl0TUNFR0ExVWRJQVFhTUJnd0NBWUdaNEVNQVFJQk1Bd0dDaXNHQVFRQjFua0NCUU13UHdZRFZSMGZCRGd3TmpBMG9ES2dNSVl1YUhSMGNEb3ZMMk55YkhNdWNHdHBMbWR2YjJjdlozUnpNV1EwYVc1MEwxZ3lTakpJY2w4M1VHbE5MbU55YkRDQ0FRUUdDaXNHQVFRQjFua0NCQUlFZ2ZVRWdmSUE4QUIxQUZHanNQWDlBWG1jVm0yNE4zaVBES1I2ekJzbnkvZWVpRUthRGY3VWl3WGxBQUFCZkk5dXVqSUFBQVFEQUVZd1JBSWdYd3JxbEEvV21IRFVySVpSWDIrS24raldjRVlsQjliVCtsRk9HT3RaTEtNQ0lGUzRXYU14Q09GaVAxTnhVN3hMcVBQVGlwR2dlaFgwS0IwTFgrTXhkdEl0QUhjQUtYbSs4SjQ1T1NId1ZuT2ZZNlYzNWI1WGZaeGdDdmo1VFYwbVhDVmR4NFFBQUFGOGoyNjZLUUFBQkFNQVNEQkdBaUVBNDdRNldJYmVnQUZuL0liUUM5OEFoR0dlY0xGVWowcjRCMnlrSkFlN2tzd0NJUURiQ2RNNFdzQ2JVUHJsSDhIV3M1ZGpqQWluKy9jWDZPNHpDTldMbzJxakhEQU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FRRUFMWHlhOUhVVm5rZURkUFgyd0tzQ2QybDhNcGpTeW5iVWVKWGI5Um04dXRsczRjRzkvdXEzRzZ3clRGWkNhdldJMnE5SmxlUnA1Q21DeCtrcElPVVh3T0dPQUZ3SVFrUFhCRnFrOGJscmE1MmhGTTluMUROYzY1bmNVRHkybXFYbjNXaVByN0crZEdSNlkzRnFKMjQ3K0VySlllbTZnM28rR3ZVcERxbWpkZ01SdHFFTXlmTVZIa0xoN3ZucWlXdnYzQ2VlU1ViRjkvMFdxUklNdTdPSFZyTkVET1ZUUEZuWENVczgyUk1OVVd0dVJTS1Njelh3QXFNN0JFWGR4TjNYcXE1Z1dOUDdUeFowczZzRTZGOHovWmN0OFVLdHRkNVBidGhrdGdFMmVvUmFaYTB1alNWVmtUeTVGb1pvMWJ1ZXhjbnM5WjlEWDFCUy9RU1JXbjNBUHc9PSIsIk1JSUZqRENDQTNTZ0F3SUJBZ0lOQWdDT3NnSXpObVdMWk0zYm16QU5CZ2txaGtpRzl3MEJBUXNGQURCSE1Rc3dDUVlEVlFRR0V3SlZVekVpTUNBR0ExVUVDaE1aUjI5dloyeGxJRlJ5ZFhOMElGTmxjblpwWTJWeklFeE1RekVVTUJJR0ExVUVBeE1MUjFSVElGSnZiM1FnVWpFd0hoY05NakF3T0RFek1EQXdNRFF5V2hjTk1qY3dPVE13TURBd01EUXlXakJHTVFzd0NRWURWUVFHRXdKVlV6RWlNQ0FHQTFVRUNoTVpSMjl2WjJ4bElGUnlkWE4wSUZObGNuWnBZMlZ6SUV4TVF6RVRNQkVHQTFVRUF4TUtSMVJUSUVOQklERkVORENDQVNJd0RRWUpLb1pJaHZjTkFRRUJCUUFEZ2dFUEFEQ0NBUW9DZ2dFQkFLdkFxcVBDRTI3bDB3OXpDOGRUUElFODliQSt4VG1EYUc3eTdWZlE0YyttT1dobFVlYlVRcEsweXYycjY3OFJKRXhLMEhXRGplcStuTElITjFFbTVqNnJBUlppeG15UlNqaElSMEtPUVBHQk1VbGRzYXp0SUlKN08wZy84MnFqL3ZHRGwvLzN0NHRUcXhpUmhMUW5UTFhKZGVCKzJEaGtkVTZJSWd4NndON0U1TmNVSDNSY3NlamNxajhwNVNqMTl2Qm02aTFGaHFMR3ltaE1Gcm9XVlVHTzN4dElIOTFkc2d5NGVGS2NmS1ZMV0szbzIxOTBRMExtL1NpS21MYlJKNUF1NHkxZXVGSm0ySk05ZUI4NEZrcWEzaXZyWFdVZVZ0eWUwQ1FkS3ZzWTJGa2F6dnh0eHZ1c0xKekxXWUhrNTV6Y1JBYWNEQTJTZUV0QmJRZkQxcXNDQXdFQUFhT0NBWFl3Z2dGeU1BNEdBMVVkRHdFQi93UUVBd0lCaGpBZEJnTlZIU1VFRmpBVUJnZ3JCZ0VGQlFjREFRWUlLd1lCQlFVSEF3SXdFZ1lEVlIwVEFRSC9CQWd3QmdFQi93SUJBREFkQmdOVkhRNEVGZ1FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd0h3WURWUjBqQkJnd0ZvQVU1SzhySm5FYUswZ25oUzlTWml6djhJa1RjVDR3YUFZSUt3WUJCUVVIQVFFRVhEQmFNQ1lHQ0NzR0FRVUZCekFCaGhwb2RIUndPaTh2YjJOemNDNXdhMmt1WjI5dlp5OW5kSE55TVRBd0JnZ3JCZ0VGQlFjd0FvWWthSFIwY0RvdkwzQnJhUzVuYjI5bkwzSmxjRzh2WTJWeWRITXZaM1J6Y2pFdVpHVnlNRFFHQTFVZEh3UXRNQ3N3S2FBbm9DV0dJMmgwZEhBNkx5OWpjbXd1Y0d0cExtZHZiMmN2WjNSemNqRXZaM1J6Y2pFdVkzSnNNRTBHQTFVZElBUkdNRVF3Q0FZR1o0RU1BUUlCTURnR0Npc0dBUVFCMW5rQ0JRTXdLakFvQmdnckJnRUZCUWNDQVJZY2FIUjBjSE02THk5d2Eya3VaMjl2Wnk5eVpYQnZjMmwwYjNKNUx6QU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FnRUFJVlRveTI0andYVXIwckFQYzkyNHZ1U1ZiS1F1WXczbkxmbExmTGg1QVlXRWVWbC9EdTE4UUFXVU1kY0o2by9xRlpiaFhrQkgwUE5jdzk3dGhhZjJCZW9EWVk5Q2svYitVR2x1aHgwNnpkNEVCZjdIOVA4NG5ucndwUis0R0JEWksrWGgzSTB0cUp5MnJnT3FORGZscjVJTVE4WlRXQTN5bHRha3pTQktaNlhwRjBQcHF5Q1J2cC9OQ0d2MktYMlR1UENKdnNjcDEvbTJwVlR0eUJqWVBSUStRdUNRR0FKS2p0TjdSNURGcmZUcU1XdllnVmxwQ0pCa3dsdTcrN0tZM2NUSWZ6RTdjbUFMc2tNS05MdUR6K1J6Q2NzWVRzVmFVN1ZwM3hMNjBPWWhxRmt1QU9PeERaNnBIT2o5K09KbVlnUG1PVDRYMys3TDUxZlhKeVJIOUtmTFJQNm5UMzFENW5tc0dBT2daMjYvOFQ5aHNCVzF1bzlqdTVmWkxaWFZWUzVIMEh5SUJNRUt5R01JUGhGV3JsdC9oRlMyOE4xemFLSTBaQkdEM2dZZ0RMYmlEVDlmR1hzdHBrK0ZtYzRvbFZsV1B6WGU4MXZkb0VuRmJyNU0yNzJIZGdKV28rV2hUOUJZTTBKaSt3ZFZtblJmZlhnbG9Fb2x1VE5jV3pjNDFkRnBnSnU4ZkYzTEcwZ2wyaWJTWWlDaTlhNmh2VTBUcHBqSnlJV1hoa0pUY01KbFByV3gxVnl0RVVHclgybDBKRHdSalcvNjU2cjBLVkIwMnhIUkt2bTJaS0kwM1RnbExJcG1WQ0sza0JLa0tOcEJOa0Z0OHJoYWZjQ0tPYjlKeC85dHBORmxRVGw3QjM5ckpsSldrUjE3UW5acVZwdEZlUEZPUm9abUZ6TT0iLCJNSUlGWWpDQ0JFcWdBd0lCQWdJUWQ3ME5iTnMyK1JycUlRL0U4RmpURFRBTkJna3Foa2lHOXcwQkFRc0ZBREJYTVFzd0NRWURWUVFHRXdKQ1JURVpNQmNHQTFVRUNoTVFSMnh2WW1Gc1UybG5iaUJ1ZGkxellURVFNQTRHQTFVRUN4TUhVbTl2ZENCRFFURWJNQmtHQTFVRUF4TVNSMnh2WW1Gc1UybG5iaUJTYjI5MElFTkJNQjRYRFRJd01EWXhPVEF3TURBME1sb1hEVEk0TURFeU9EQXdNREEwTWxvd1J6RUxNQWtHQTFVRUJoTUNWVk14SWpBZ0JnTlZCQW9UR1VkdmIyZHNaU0JVY25WemRDQlRaWEoyYVdObGN5Qk1URU14RkRBU0JnTlZCQU1UQzBkVVV5QlNiMjkwSUZJeE1JSUNJakFOQmdrcWhraUc5dzBCQVFFRkFBT0NBZzhBTUlJQ0NnS0NBZ0VBdGhFQ2l4N2pvWGViTzl5L2xENjNsYWRBUEtIOWd2bDlNZ2FDY2ZiMmpILzc2TnU4YWk2WGw2T01TL2tyOXJINXpvUWRzZm5GbDk3dnVmS2o2YndTaVY2bnFsS3IrQ01ueTZTeG5HUGIxNWwrOEFwZTYyaW05TVphUncxTkVEUGpUckVUbzhnWWJFdnMvQW1RMzUxa0tTVWpCNkcwMGowdVlPRFAwZ21IdTgxSThFM0N3bnFJaXJ1Nnoxa1oxcStQc0Fld25qSHhnc0hBM3k2bWJXd1pEclhZZmlZYVJRTTlzSG1rbENpdEQzOG01YWdJL3Bib1BHaVVVKzZET29nckZaWUpzdUI2akM1MTFwenJwMVprajVaUGFLNDlsOEtFajhDOFFNQUxYTDMyaDdNMWJLd1lVSCtFNEV6Tmt0TWc2VE84VXBtdk1yVXBzeVVxdEVqNWN1SEtaUGZtZ2hDTjZKM0Npb2o2T0dhSy9HUDVBZmw0L1h0Y2QvcDJoL3JzMzdFT2VaVlh0TDBtNzlZQjBlc1dDcnVPQzdYRnhZcFZxOU9zNnBGTEtjd1pwRElsVGlyeFpVVFFBczZxemttMDZwOThnN0JBZStkRHE2ZHNvNDk5aVlINlRLWC8xWTdEemt2Z3RkaXpqa1hQZHNEdFFDdjlVdyt3cDlVN0RiR0tvZ1BlTWEzTWQrcHZlejdXMzVFaUV1YSsrdGd5L0JCakZGRnkzbDNXRnBPOUtXZ3o3enBtN0FlS0p0OFQxMWRsZUNmZVhra1VBS0lBZjVxb0liYXBzWld3cGJrTkZoSGF4MnhJUEVEZ2ZnMWF6Vlk4MFpjRnVjdEw3VGxMbk1RLzBsVVRiaVN3MW5INjlNRzZ6TzBiOWY2QlFkZ0FtRDA2eUs1Nm1EY1lCWlVDQXdFQUFhT0NBVGd3Z2dFME1BNEdBMVVkRHdFQi93UUVBd0lCaGpBUEJnTlZIUk1CQWY4RUJUQURBUUgvTUIwR0ExVWREZ1FXQkJUa3J5c21jUm9yU0NlRkwxSm1MTy93aVJOeFBqQWZCZ05WSFNNRUdEQVdnQlJnZTJZYVJRMlh5b2xRTDMwRXpUU28vL3o5U3pCZ0JnZ3JCZ0VGQlFjQkFRUlVNRkl3SlFZSUt3WUJCUVVITUFHR0dXaDBkSEE2THk5dlkzTndMbkJyYVM1bmIyOW5MMmR6Y2pFd0tRWUlLd1lCQlFVSE1BS0dIV2gwZEhBNkx5OXdhMmt1WjI5dlp5OW5jM0l4TDJkemNqRXVZM0owTURJR0ExVWRId1FyTUNrd0o2QWxvQ09HSVdoMGRIQTZMeTlqY213dWNHdHBMbWR2YjJjdlozTnlNUzluYzNJeExtTnliREE3QmdOVkhTQUVOREF5TUFnR0JtZUJEQUVDQVRBSUJnWm5nUXdCQWdJd0RRWUxLd1lCQkFIV2VRSUZBd0l3RFFZTEt3WUJCQUhXZVFJRkF3TXdEUVlKS29aSWh2Y05BUUVMQlFBRGdnRUJBRFNrSHJFb285QzBkaGVtTVhvaDZkRlNQc2piZEJaQmlMZzlOUjN0NVArVDRWeGZxN3ZxZk0vYjVBM1JpMWZ5Sm05YnZoZEdhSlEzYjJ0NnlNQVlOL29sVWF6c2FMK3l5RW45V3ByS0FTT3NoSUFyQW95WmwrdEphb3gxMThmZXNzbVhuMWhJVnc0MW9lUWExdjF2ZzRGdjc0elBsNi9BaFNydzlVNXBDWkV0NFdpNHdTdHo2ZFRaL0NMQU54OExaaDFKN1FKVmoyZmhNdGZUSnI5dzR6MzBaMjA5Zk9VMGlPTXkrcWR1Qm1wdnZZdVI3aFpMNkR1cHN6Zm53MFNrZnRoczE4ZEc5WktiNTlVaHZtYVNHWlJWYk5RcHNnM0JabHZpZDBsSUtPMmQxeG96Y2xPemdqWFBZb3ZKSkl1bHR6a011MzRxUWI5U3oveWlscmJDZ2o4PSJdfQ.eyJub25jZSI6IlBvSEJNR1FXVTZMTHZuQ21tQUlqUkt4dTJ4ND0iLCJ0aW1lc3RhbXBNcyI6MTYzNzc1MTY1NTE2OSwiYXBrUGFja2FnZU5hbWUiOiJjb20uZmFzdGJhbmtpbmcuZGVidWciLCJhcGtEaWdlc3RTaGEyNTYiOiJsRHF1bDJxejdyd2owRDFJSzBkcTZwTnNaUmR0QW9BbUNNOVh5MGg2bkNjPSIsImN0c1Byb2ZpbGVNYXRjaCI6dHJ1ZSwiYXBrQ2VydGlmaWNhdGVEaWdlc3RTaGEyNTYiOlsiR3k3N1doNFRkR0ZXd3NoaS9VVXdDdUJIL0NBZ2V4VFFLdmJzbW5pWHFpTT0iXSwiYmFzaWNJbnRlZ3JpdHkiOnRydWUsImV2YWx1YXRpb25UeXBlIjoiQkFTSUMsSEFSRFdBUkVfQkFDS0VEIn0.ShOvWqQ_5i-T1ixx59sbk0-6LMo8oKiC5PfZCt9dVJrnfeap8JMQ9x8v19-Yh-M07y54BjQPXFGU-Y602uFc_V7TKHonDqjaEOsx6VfRwiQeZmtaO-Hhmlr2g-xRHFoDOnXy2wHYGfDkMbir50EraIyny3xfs-guIDMwg5qAzQaN999KRsrbHXX-a6wwoQ0qyUSVKGN57T_qOcXaq9X5bI1B3nD1m5Inu7TW0xrCb0sfUn8GDimAtnXELKf048S4iaXBObbgtiNyVQtTEfqHA8WdfhANIZWcV4XQDHbv69wcvrmUTDeZJienIfkmesfYnFDngW2NfR9A9m_Q5sorig");
	//		headers.put("x-login-nonce", "B6B667EB514890789F56F9B78BFA509AB41B673B");
	//		headers.put("x-login-timestamp", "1636960116339");
	//
	//		ValidatableResponse response = RestAssured.given().baseUri(url).contentType(ContentType.JSON).headers(headers)
	//				.body(Myrequestbody.toJSONString()).when().post().then();
	//
	//		System.out.println("Request Url -->" + url);
	//		ExtentReporter.extentLogger("", "Request Url -->" + url);
	//		System.out.println("Request :" + Myrequestbody);
	//		ExtentReporter.extentLogger("", "Request :" + Myrequestbody);
	//		String Resp = response.extract().body().asString();
	//		System.out.println("Response Body= " + Resp);
	//		ExtentReporter.extentLogger("", "Response Body= " + Resp);
	//
	//		return response;
	//	}
	//
	//	public static ValidatableResponse SpecialCharacterInFieldAPI(Object[][] data) {
	//
	//		Random rand = new Random();
	//
	//		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.sendOtpEndPoint);
	//		System.out.println("Url :" + url);
	//
	//		HashMap<String, String> req_body = new HashMap<>();
	//		// System.out.println((String) data[0][3]);
	//		req_body.put("source_app", (String) data[0][0]);
	//		req_body.put("mobile_number", (String) data[0][1]);
	//
	//		JSONObject Myrequestbody = new JSONObject();
	//
	//		Myrequestbody.put("source_app", req_body.get("source_app"));
	//		Myrequestbody.put("mobile_number", req_body.get("mobile_number"));
	//
	//		HashMap<String, Object> headers = new HashMap<>();
	//		headers.put("x-request-id", rand.nextInt(1001));
	//		headers.put("X-Client-App", "android");
	//		headers.put("X-Client-Version", 4.9);
	//		headers.put("X-Client-OS-Type", "android");
	//		headers.put("X-Client-OS-Version", 10);
	//		headers.put("x-login-token",
	//				"eyJhbGciOiJSUzI1NiIsIng1YyI6WyJNSUlGWVRDQ0JFbWdBd0lCQWdJUkFQaEtkUXdrSUFNRENRQUFBQUM4QzZvd0RRWUpLb1pJaHZjTkFRRUxCUUF3UmpFTE1Ba0dBMVVFQmhNQ1ZWTXhJakFnQmdOVkJBb1RHVWR2YjJkc1pTQlVjblZ6ZENCVFpYSjJhV05sY3lCTVRFTXhFekFSQmdOVkJBTVRDa2RVVXlCRFFTQXhSRFF3SGhjTk1qRXhNREUzTVRjd05qQTNXaGNOTWpJd01URTFNVGN3TmpBMldqQWRNUnN3R1FZRFZRUURFeEpoZEhSbGMzUXVZVzVrY205cFpDNWpiMjB3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRQ3ZnU2VHM3JTVlcwSVBpWkJGVmJoMktjYjNoTnl3R2VJOUZmaVgyUXZRQnBmUkIvT0xiUUFwZGdDWTZJL1dqNEw0aHVNQzRMVHA3OFZXbmhtZGJ3Y1NxbXJzNkpDM3kwWnVmVm4ydzhsV0NYODNsYytFUmdRVHhmaGUwTVNIakhlWk9mWGROQ3dqejZrTXJkZEVPUlJ5T3V3SWdjcXcrNGoycS9mSktHbkUyNXQ5NndOTDgrUDg1V294ZXhaZEROR1pzMmkzNmRvZkdVTGR1YTZaWFI1YjFlODJkd0dra0Rkd3RFMjZCeDRhTTl4VDEwK3A0S3FKNXZ0MWpvY1N0K2tTWHFRaEowQlJjS082OWhGUTRDSUdKYk5EYlRIMENGYlMvanJsNThGWnhVTUVwaUNHbG9JdmJyZ20xSlFzRDE2UmtIZlQ0NVM5UERNc3k5WFI4bjVqQWdNQkFBR2pnZ0p4TUlJQ2JUQU9CZ05WSFE4QkFmOEVCQU1DQmFBd0V3WURWUjBsQkF3d0NnWUlLd1lCQlFVSEF3RXdEQVlEVlIwVEFRSC9CQUl3QURBZEJnTlZIUTRFRmdRVUJ0M1lUWkFYZ3pGYXdpV2FXN3hmaStYRDhnZ3dId1lEVlIwakJCZ3dGb0FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd2JRWUlLd1lCQlFVSEFRRUVZVEJmTUNvR0NDc0dBUVVGQnpBQmhoNW9kSFJ3T2k4dmIyTnpjQzV3YTJrdVoyOXZaeTluZEhNeFpEUnBiblF3TVFZSUt3WUJCUVVITUFLR0pXaDBkSEE2THk5d2Eya3VaMjl2Wnk5eVpYQnZMMk5sY25SekwyZDBjekZrTkM1a1pYSXdIUVlEVlIwUkJCWXdGSUlTWVhSMFpYTjBMbUZ1WkhKdmFXUXVZMjl0TUNFR0ExVWRJQVFhTUJnd0NBWUdaNEVNQVFJQk1Bd0dDaXNHQVFRQjFua0NCUU13UHdZRFZSMGZCRGd3TmpBMG9ES2dNSVl1YUhSMGNEb3ZMMk55YkhNdWNHdHBMbWR2YjJjdlozUnpNV1EwYVc1MEwxZ3lTakpJY2w4M1VHbE5MbU55YkRDQ0FRUUdDaXNHQVFRQjFua0NCQUlFZ2ZVRWdmSUE4QUIxQUZHanNQWDlBWG1jVm0yNE4zaVBES1I2ekJzbnkvZWVpRUthRGY3VWl3WGxBQUFCZkk5dXVqSUFBQVFEQUVZd1JBSWdYd3JxbEEvV21IRFVySVpSWDIrS24raldjRVlsQjliVCtsRk9HT3RaTEtNQ0lGUzRXYU14Q09GaVAxTnhVN3hMcVBQVGlwR2dlaFgwS0IwTFgrTXhkdEl0QUhjQUtYbSs4SjQ1T1NId1ZuT2ZZNlYzNWI1WGZaeGdDdmo1VFYwbVhDVmR4NFFBQUFGOGoyNjZLUUFBQkFNQVNEQkdBaUVBNDdRNldJYmVnQUZuL0liUUM5OEFoR0dlY0xGVWowcjRCMnlrSkFlN2tzd0NJUURiQ2RNNFdzQ2JVUHJsSDhIV3M1ZGpqQWluKy9jWDZPNHpDTldMbzJxakhEQU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FRRUFMWHlhOUhVVm5rZURkUFgyd0tzQ2QybDhNcGpTeW5iVWVKWGI5Um04dXRsczRjRzkvdXEzRzZ3clRGWkNhdldJMnE5SmxlUnA1Q21DeCtrcElPVVh3T0dPQUZ3SVFrUFhCRnFrOGJscmE1MmhGTTluMUROYzY1bmNVRHkybXFYbjNXaVByN0crZEdSNlkzRnFKMjQ3K0VySlllbTZnM28rR3ZVcERxbWpkZ01SdHFFTXlmTVZIa0xoN3ZucWlXdnYzQ2VlU1ViRjkvMFdxUklNdTdPSFZyTkVET1ZUUEZuWENVczgyUk1OVVd0dVJTS1Njelh3QXFNN0JFWGR4TjNYcXE1Z1dOUDdUeFowczZzRTZGOHovWmN0OFVLdHRkNVBidGhrdGdFMmVvUmFaYTB1alNWVmtUeTVGb1pvMWJ1ZXhjbnM5WjlEWDFCUy9RU1JXbjNBUHc9PSIsIk1JSUZqRENDQTNTZ0F3SUJBZ0lOQWdDT3NnSXpObVdMWk0zYm16QU5CZ2txaGtpRzl3MEJBUXNGQURCSE1Rc3dDUVlEVlFRR0V3SlZVekVpTUNBR0ExVUVDaE1aUjI5dloyeGxJRlJ5ZFhOMElGTmxjblpwWTJWeklFeE1RekVVTUJJR0ExVUVBeE1MUjFSVElGSnZiM1FnVWpFd0hoY05NakF3T0RFek1EQXdNRFF5V2hjTk1qY3dPVE13TURBd01EUXlXakJHTVFzd0NRWURWUVFHRXdKVlV6RWlNQ0FHQTFVRUNoTVpSMjl2WjJ4bElGUnlkWE4wSUZObGNuWnBZMlZ6SUV4TVF6RVRNQkVHQTFVRUF4TUtSMVJUSUVOQklERkVORENDQVNJd0RRWUpLb1pJaHZjTkFRRUJCUUFEZ2dFUEFEQ0NBUW9DZ2dFQkFLdkFxcVBDRTI3bDB3OXpDOGRUUElFODliQSt4VG1EYUc3eTdWZlE0YyttT1dobFVlYlVRcEsweXYycjY3OFJKRXhLMEhXRGplcStuTElITjFFbTVqNnJBUlppeG15UlNqaElSMEtPUVBHQk1VbGRzYXp0SUlKN08wZy84MnFqL3ZHRGwvLzN0NHRUcXhpUmhMUW5UTFhKZGVCKzJEaGtkVTZJSWd4NndON0U1TmNVSDNSY3NlamNxajhwNVNqMTl2Qm02aTFGaHFMR3ltaE1Gcm9XVlVHTzN4dElIOTFkc2d5NGVGS2NmS1ZMV0szbzIxOTBRMExtL1NpS21MYlJKNUF1NHkxZXVGSm0ySk05ZUI4NEZrcWEzaXZyWFdVZVZ0eWUwQ1FkS3ZzWTJGa2F6dnh0eHZ1c0xKekxXWUhrNTV6Y1JBYWNEQTJTZUV0QmJRZkQxcXNDQXdFQUFhT0NBWFl3Z2dGeU1BNEdBMVVkRHdFQi93UUVBd0lCaGpBZEJnTlZIU1VFRmpBVUJnZ3JCZ0VGQlFjREFRWUlLd1lCQlFVSEF3SXdFZ1lEVlIwVEFRSC9CQWd3QmdFQi93SUJBREFkQmdOVkhRNEVGZ1FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd0h3WURWUjBqQkJnd0ZvQVU1SzhySm5FYUswZ25oUzlTWml6djhJa1RjVDR3YUFZSUt3WUJCUVVIQVFFRVhEQmFNQ1lHQ0NzR0FRVUZCekFCaGhwb2RIUndPaTh2YjJOemNDNXdhMmt1WjI5dlp5OW5kSE55TVRBd0JnZ3JCZ0VGQlFjd0FvWWthSFIwY0RvdkwzQnJhUzVuYjI5bkwzSmxjRzh2WTJWeWRITXZaM1J6Y2pFdVpHVnlNRFFHQTFVZEh3UXRNQ3N3S2FBbm9DV0dJMmgwZEhBNkx5OWpjbXd1Y0d0cExtZHZiMmN2WjNSemNqRXZaM1J6Y2pFdVkzSnNNRTBHQTFVZElBUkdNRVF3Q0FZR1o0RU1BUUlCTURnR0Npc0dBUVFCMW5rQ0JRTXdLakFvQmdnckJnRUZCUWNDQVJZY2FIUjBjSE02THk5d2Eya3VaMjl2Wnk5eVpYQnZjMmwwYjNKNUx6QU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FnRUFJVlRveTI0andYVXIwckFQYzkyNHZ1U1ZiS1F1WXczbkxmbExmTGg1QVlXRWVWbC9EdTE4UUFXVU1kY0o2by9xRlpiaFhrQkgwUE5jdzk3dGhhZjJCZW9EWVk5Q2svYitVR2x1aHgwNnpkNEVCZjdIOVA4NG5ucndwUis0R0JEWksrWGgzSTB0cUp5MnJnT3FORGZscjVJTVE4WlRXQTN5bHRha3pTQktaNlhwRjBQcHF5Q1J2cC9OQ0d2MktYMlR1UENKdnNjcDEvbTJwVlR0eUJqWVBSUStRdUNRR0FKS2p0TjdSNURGcmZUcU1XdllnVmxwQ0pCa3dsdTcrN0tZM2NUSWZ6RTdjbUFMc2tNS05MdUR6K1J6Q2NzWVRzVmFVN1ZwM3hMNjBPWWhxRmt1QU9PeERaNnBIT2o5K09KbVlnUG1PVDRYMys3TDUxZlhKeVJIOUtmTFJQNm5UMzFENW5tc0dBT2daMjYvOFQ5aHNCVzF1bzlqdTVmWkxaWFZWUzVIMEh5SUJNRUt5R01JUGhGV3JsdC9oRlMyOE4xemFLSTBaQkdEM2dZZ0RMYmlEVDlmR1hzdHBrK0ZtYzRvbFZsV1B6WGU4MXZkb0VuRmJyNU0yNzJIZGdKV28rV2hUOUJZTTBKaSt3ZFZtblJmZlhnbG9Fb2x1VE5jV3pjNDFkRnBnSnU4ZkYzTEcwZ2wyaWJTWWlDaTlhNmh2VTBUcHBqSnlJV1hoa0pUY01KbFByV3gxVnl0RVVHclgybDBKRHdSalcvNjU2cjBLVkIwMnhIUkt2bTJaS0kwM1RnbExJcG1WQ0sza0JLa0tOcEJOa0Z0OHJoYWZjQ0tPYjlKeC85dHBORmxRVGw3QjM5ckpsSldrUjE3UW5acVZwdEZlUEZPUm9abUZ6TT0iLCJNSUlGWWpDQ0JFcWdBd0lCQWdJUWQ3ME5iTnMyK1JycUlRL0U4RmpURFRBTkJna3Foa2lHOXcwQkFRc0ZBREJYTVFzd0NRWURWUVFHRXdKQ1JURVpNQmNHQTFVRUNoTVFSMnh2WW1Gc1UybG5iaUJ1ZGkxellURVFNQTRHQTFVRUN4TUhVbTl2ZENCRFFURWJNQmtHQTFVRUF4TVNSMnh2WW1Gc1UybG5iaUJTYjI5MElFTkJNQjRYRFRJd01EWXhPVEF3TURBME1sb1hEVEk0TURFeU9EQXdNREEwTWxvd1J6RUxNQWtHQTFVRUJoTUNWVk14SWpBZ0JnTlZCQW9UR1VkdmIyZHNaU0JVY25WemRDQlRaWEoyYVdObGN5Qk1URU14RkRBU0JnTlZCQU1UQzBkVVV5QlNiMjkwSUZJeE1JSUNJakFOQmdrcWhraUc5dzBCQVFFRkFBT0NBZzhBTUlJQ0NnS0NBZ0VBdGhFQ2l4N2pvWGViTzl5L2xENjNsYWRBUEtIOWd2bDlNZ2FDY2ZiMmpILzc2TnU4YWk2WGw2T01TL2tyOXJINXpvUWRzZm5GbDk3dnVmS2o2YndTaVY2bnFsS3IrQ01ueTZTeG5HUGIxNWwrOEFwZTYyaW05TVphUncxTkVEUGpUckVUbzhnWWJFdnMvQW1RMzUxa0tTVWpCNkcwMGowdVlPRFAwZ21IdTgxSThFM0N3bnFJaXJ1Nnoxa1oxcStQc0Fld25qSHhnc0hBM3k2bWJXd1pEclhZZmlZYVJRTTlzSG1rbENpdEQzOG01YWdJL3Bib1BHaVVVKzZET29nckZaWUpzdUI2akM1MTFwenJwMVprajVaUGFLNDlsOEtFajhDOFFNQUxYTDMyaDdNMWJLd1lVSCtFNEV6Tmt0TWc2VE84VXBtdk1yVXBzeVVxdEVqNWN1SEtaUGZtZ2hDTjZKM0Npb2o2T0dhSy9HUDVBZmw0L1h0Y2QvcDJoL3JzMzdFT2VaVlh0TDBtNzlZQjBlc1dDcnVPQzdYRnhZcFZxOU9zNnBGTEtjd1pwRElsVGlyeFpVVFFBczZxemttMDZwOThnN0JBZStkRHE2ZHNvNDk5aVlINlRLWC8xWTdEemt2Z3RkaXpqa1hQZHNEdFFDdjlVdyt3cDlVN0RiR0tvZ1BlTWEzTWQrcHZlejdXMzVFaUV1YSsrdGd5L0JCakZGRnkzbDNXRnBPOUtXZ3o3enBtN0FlS0p0OFQxMWRsZUNmZVhra1VBS0lBZjVxb0liYXBzWld3cGJrTkZoSGF4MnhJUEVEZ2ZnMWF6Vlk4MFpjRnVjdEw3VGxMbk1RLzBsVVRiaVN3MW5INjlNRzZ6TzBiOWY2QlFkZ0FtRDA2eUs1Nm1EY1lCWlVDQXdFQUFhT0NBVGd3Z2dFME1BNEdBMVVkRHdFQi93UUVBd0lCaGpBUEJnTlZIUk1CQWY4RUJUQURBUUgvTUIwR0ExVWREZ1FXQkJUa3J5c21jUm9yU0NlRkwxSm1MTy93aVJOeFBqQWZCZ05WSFNNRUdEQVdnQlJnZTJZYVJRMlh5b2xRTDMwRXpUU28vL3o5U3pCZ0JnZ3JCZ0VGQlFjQkFRUlVNRkl3SlFZSUt3WUJCUVVITUFHR0dXaDBkSEE2THk5dlkzTndMbkJyYVM1bmIyOW5MMmR6Y2pFd0tRWUlLd1lCQlFVSE1BS0dIV2gwZEhBNkx5OXdhMmt1WjI5dlp5OW5jM0l4TDJkemNqRXVZM0owTURJR0ExVWRId1FyTUNrd0o2QWxvQ09HSVdoMGRIQTZMeTlqY213dWNHdHBMbWR2YjJjdlozTnlNUzluYzNJeExtTnliREE3QmdOVkhTQUVOREF5TUFnR0JtZUJEQUVDQVRBSUJnWm5nUXdCQWdJd0RRWUxLd1lCQkFIV2VRSUZBd0l3RFFZTEt3WUJCQUhXZVFJRkF3TXdEUVlKS29aSWh2Y05BUUVMQlFBRGdnRUJBRFNrSHJFb285QzBkaGVtTVhvaDZkRlNQc2piZEJaQmlMZzlOUjN0NVArVDRWeGZxN3ZxZk0vYjVBM1JpMWZ5Sm05YnZoZEdhSlEzYjJ0NnlNQVlOL29sVWF6c2FMK3l5RW45V3ByS0FTT3NoSUFyQW95WmwrdEphb3gxMThmZXNzbVhuMWhJVnc0MW9lUWExdjF2ZzRGdjc0elBsNi9BaFNydzlVNXBDWkV0NFdpNHdTdHo2ZFRaL0NMQU54OExaaDFKN1FKVmoyZmhNdGZUSnI5dzR6MzBaMjA5Zk9VMGlPTXkrcWR1Qm1wdnZZdVI3aFpMNkR1cHN6Zm53MFNrZnRoczE4ZEc5WktiNTlVaHZtYVNHWlJWYk5RcHNnM0JabHZpZDBsSUtPMmQxeG96Y2xPemdqWFBZb3ZKSkl1bHR6a011MzRxUWI5U3oveWlscmJDZ2o4PSJdfQ.eyJub25jZSI6IlBvSEJNR1FXVTZMTHZuQ21tQUlqUkt4dTJ4ND0iLCJ0aW1lc3RhbXBNcyI6MTYzNzc1MTY1NTE2OSwiYXBrUGFja2FnZU5hbWUiOiJjb20uZmFzdGJhbmtpbmcuZGVidWciLCJhcGtEaWdlc3RTaGEyNTYiOiJsRHF1bDJxejdyd2owRDFJSzBkcTZwTnNaUmR0QW9BbUNNOVh5MGg2bkNjPSIsImN0c1Byb2ZpbGVNYXRjaCI6dHJ1ZSwiYXBrQ2VydGlmaWNhdGVEaWdlc3RTaGEyNTYiOlsiR3k3N1doNFRkR0ZXd3NoaS9VVXdDdUJIL0NBZ2V4VFFLdmJzbW5pWHFpTT0iXSwiYmFzaWNJbnRlZ3JpdHkiOnRydWUsImV2YWx1YXRpb25UeXBlIjoiQkFTSUMsSEFSRFdBUkVfQkFDS0VEIn0.ShOvWqQ_5i-T1ixx59sbk0-6LMo8oKiC5PfZCt9dVJrnfeap8JMQ9x8v19-Yh-M07y54BjQPXFGU-Y602uFc_V7TKHonDqjaEOsx6VfRwiQeZmtaO-Hhmlr2g-xRHFoDOnXy2wHYGfDkMbir50EraIyny3xfs-guIDMwg5qAzQaN999KRsrbHXX-a6wwoQ0qyUSVKGN57T_qOcXaq9X5bI1B3nD1m5Inu7TW0xrCb0sfUn8GDimAtnXELKf048S4iaXBObbgtiNyVQtTEfqHA8WdfhANIZWcV4XQDHbv69wcvrmUTDeZJienIfkmesfYnFDngW2NfR9A9m_Q5sorig");
	//		headers.put("x-login-nonce", "B6B667EB514890789F56F9B78BFA509AB41B673B");
	//		headers.put("x-login-timestamp", "1636960116339");
	//
	//		ValidatableResponse response = RestAssured.given().baseUri(url).contentType(ContentType.JSON).headers(headers)
	//				.body(Myrequestbody.toJSONString()).when().post().then();
	//
	//		System.out.println("Request Url -->" + url);
	//		ExtentReporter.extentLogger("", "Request Url -->" + url);
	//		System.out.println("Request :" + Myrequestbody);
	//		ExtentReporter.extentLogger("", "Request :" + Myrequestbody);
	//		String Resp = response.extract().body().asString();
	//		System.out.println("Response Body= " + Resp);
	//		ExtentReporter.extentLogger("", "Response Body= " + Resp);
	//
	//		return response;
	//	}

	/**
	 * 
	 * @param data
	 * @param url
	 * @return
	 */

	public static ValidatableResponse API(Object[][] data, String url) {
		Random rand = new Random();

		HashMap<String, String> req_body = new HashMap<>();
		// System.out.println((String) data[0][3]);
		req_body.put("source_app", (String) data[0][0]);
		req_body.put("mobile_number", (String) data[0][1]);
		// try{
		// req_body.put("otp", (String) data[0][2]);
		// req_body.put("client_id", (String) data[0][3]);
		// } catch (Exception e) {
		// System.out.println(e.getMessage());
		// }

		JSONObject Myrequestbody = new JSONObject();

		Myrequestbody.put("source_app", req_body.get("source_app"));
		Myrequestbody.put("mobile_number", req_body.get("mobile_number"));
		// Myrequestbody.put("otp", req_body.get("otp"));
		// Myrequestbody.put("client_id", req_body.get("client_id"));

		HashMap<String, Object> headers = new HashMap<>();
		headers.put("x-request-id", rand.nextInt(1001));
		headers.put("X-Client-App", "android");
		headers.put("X-Client-Version", 4.9);
		headers.put("X-Client-OS-Type", "android");
		headers.put("X-Client-OS-Version", 10);
		headers.put("x-login-token",
				"eyJhbGciOiJSUzI1NiIsIng1YyI6WyJNSUlGWVRDQ0JFbWdBd0lCQWdJUkFQaEtkUXdrSUFNRENRQUFBQUM4QzZvd0RRWUpLb1pJaHZjTkFRRUxCUUF3UmpFTE1Ba0dBMVVFQmhNQ1ZWTXhJakFnQmdOVkJBb1RHVWR2YjJkc1pTQlVjblZ6ZENCVFpYSjJhV05sY3lCTVRFTXhFekFSQmdOVkJBTVRDa2RVVXlCRFFTQXhSRFF3SGhjTk1qRXhNREUzTVRjd05qQTNXaGNOTWpJd01URTFNVGN3TmpBMldqQWRNUnN3R1FZRFZRUURFeEpoZEhSbGMzUXVZVzVrY205cFpDNWpiMjB3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRQ3ZnU2VHM3JTVlcwSVBpWkJGVmJoMktjYjNoTnl3R2VJOUZmaVgyUXZRQnBmUkIvT0xiUUFwZGdDWTZJL1dqNEw0aHVNQzRMVHA3OFZXbmhtZGJ3Y1NxbXJzNkpDM3kwWnVmVm4ydzhsV0NYODNsYytFUmdRVHhmaGUwTVNIakhlWk9mWGROQ3dqejZrTXJkZEVPUlJ5T3V3SWdjcXcrNGoycS9mSktHbkUyNXQ5NndOTDgrUDg1V294ZXhaZEROR1pzMmkzNmRvZkdVTGR1YTZaWFI1YjFlODJkd0dra0Rkd3RFMjZCeDRhTTl4VDEwK3A0S3FKNXZ0MWpvY1N0K2tTWHFRaEowQlJjS082OWhGUTRDSUdKYk5EYlRIMENGYlMvanJsNThGWnhVTUVwaUNHbG9JdmJyZ20xSlFzRDE2UmtIZlQ0NVM5UERNc3k5WFI4bjVqQWdNQkFBR2pnZ0p4TUlJQ2JUQU9CZ05WSFE4QkFmOEVCQU1DQmFBd0V3WURWUjBsQkF3d0NnWUlLd1lCQlFVSEF3RXdEQVlEVlIwVEFRSC9CQUl3QURBZEJnTlZIUTRFRmdRVUJ0M1lUWkFYZ3pGYXdpV2FXN3hmaStYRDhnZ3dId1lEVlIwakJCZ3dGb0FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd2JRWUlLd1lCQlFVSEFRRUVZVEJmTUNvR0NDc0dBUVVGQnpBQmhoNW9kSFJ3T2k4dmIyTnpjQzV3YTJrdVoyOXZaeTluZEhNeFpEUnBiblF3TVFZSUt3WUJCUVVITUFLR0pXaDBkSEE2THk5d2Eya3VaMjl2Wnk5eVpYQnZMMk5sY25SekwyZDBjekZrTkM1a1pYSXdIUVlEVlIwUkJCWXdGSUlTWVhSMFpYTjBMbUZ1WkhKdmFXUXVZMjl0TUNFR0ExVWRJQVFhTUJnd0NBWUdaNEVNQVFJQk1Bd0dDaXNHQVFRQjFua0NCUU13UHdZRFZSMGZCRGd3TmpBMG9ES2dNSVl1YUhSMGNEb3ZMMk55YkhNdWNHdHBMbWR2YjJjdlozUnpNV1EwYVc1MEwxZ3lTakpJY2w4M1VHbE5MbU55YkRDQ0FRUUdDaXNHQVFRQjFua0NCQUlFZ2ZVRWdmSUE4QUIxQUZHanNQWDlBWG1jVm0yNE4zaVBES1I2ekJzbnkvZWVpRUthRGY3VWl3WGxBQUFCZkk5dXVqSUFBQVFEQUVZd1JBSWdYd3JxbEEvV21IRFVySVpSWDIrS24raldjRVlsQjliVCtsRk9HT3RaTEtNQ0lGUzRXYU14Q09GaVAxTnhVN3hMcVBQVGlwR2dlaFgwS0IwTFgrTXhkdEl0QUhjQUtYbSs4SjQ1T1NId1ZuT2ZZNlYzNWI1WGZaeGdDdmo1VFYwbVhDVmR4NFFBQUFGOGoyNjZLUUFBQkFNQVNEQkdBaUVBNDdRNldJYmVnQUZuL0liUUM5OEFoR0dlY0xGVWowcjRCMnlrSkFlN2tzd0NJUURiQ2RNNFdzQ2JVUHJsSDhIV3M1ZGpqQWluKy9jWDZPNHpDTldMbzJxakhEQU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FRRUFMWHlhOUhVVm5rZURkUFgyd0tzQ2QybDhNcGpTeW5iVWVKWGI5Um04dXRsczRjRzkvdXEzRzZ3clRGWkNhdldJMnE5SmxlUnA1Q21DeCtrcElPVVh3T0dPQUZ3SVFrUFhCRnFrOGJscmE1MmhGTTluMUROYzY1bmNVRHkybXFYbjNXaVByN0crZEdSNlkzRnFKMjQ3K0VySlllbTZnM28rR3ZVcERxbWpkZ01SdHFFTXlmTVZIa0xoN3ZucWlXdnYzQ2VlU1ViRjkvMFdxUklNdTdPSFZyTkVET1ZUUEZuWENVczgyUk1OVVd0dVJTS1Njelh3QXFNN0JFWGR4TjNYcXE1Z1dOUDdUeFowczZzRTZGOHovWmN0OFVLdHRkNVBidGhrdGdFMmVvUmFaYTB1alNWVmtUeTVGb1pvMWJ1ZXhjbnM5WjlEWDFCUy9RU1JXbjNBUHc9PSIsIk1JSUZqRENDQTNTZ0F3SUJBZ0lOQWdDT3NnSXpObVdMWk0zYm16QU5CZ2txaGtpRzl3MEJBUXNGQURCSE1Rc3dDUVlEVlFRR0V3SlZVekVpTUNBR0ExVUVDaE1aUjI5dloyeGxJRlJ5ZFhOMElGTmxjblpwWTJWeklFeE1RekVVTUJJR0ExVUVBeE1MUjFSVElGSnZiM1FnVWpFd0hoY05NakF3T0RFek1EQXdNRFF5V2hjTk1qY3dPVE13TURBd01EUXlXakJHTVFzd0NRWURWUVFHRXdKVlV6RWlNQ0FHQTFVRUNoTVpSMjl2WjJ4bElGUnlkWE4wSUZObGNuWnBZMlZ6SUV4TVF6RVRNQkVHQTFVRUF4TUtSMVJUSUVOQklERkVORENDQVNJd0RRWUpLb1pJaHZjTkFRRUJCUUFEZ2dFUEFEQ0NBUW9DZ2dFQkFLdkFxcVBDRTI3bDB3OXpDOGRUUElFODliQSt4VG1EYUc3eTdWZlE0YyttT1dobFVlYlVRcEsweXYycjY3OFJKRXhLMEhXRGplcStuTElITjFFbTVqNnJBUlppeG15UlNqaElSMEtPUVBHQk1VbGRzYXp0SUlKN08wZy84MnFqL3ZHRGwvLzN0NHRUcXhpUmhMUW5UTFhKZGVCKzJEaGtkVTZJSWd4NndON0U1TmNVSDNSY3NlamNxajhwNVNqMTl2Qm02aTFGaHFMR3ltaE1Gcm9XVlVHTzN4dElIOTFkc2d5NGVGS2NmS1ZMV0szbzIxOTBRMExtL1NpS21MYlJKNUF1NHkxZXVGSm0ySk05ZUI4NEZrcWEzaXZyWFdVZVZ0eWUwQ1FkS3ZzWTJGa2F6dnh0eHZ1c0xKekxXWUhrNTV6Y1JBYWNEQTJTZUV0QmJRZkQxcXNDQXdFQUFhT0NBWFl3Z2dGeU1BNEdBMVVkRHdFQi93UUVBd0lCaGpBZEJnTlZIU1VFRmpBVUJnZ3JCZ0VGQlFjREFRWUlLd1lCQlFVSEF3SXdFZ1lEVlIwVEFRSC9CQWd3QmdFQi93SUJBREFkQmdOVkhRNEVGZ1FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd0h3WURWUjBqQkJnd0ZvQVU1SzhySm5FYUswZ25oUzlTWml6djhJa1RjVDR3YUFZSUt3WUJCUVVIQVFFRVhEQmFNQ1lHQ0NzR0FRVUZCekFCaGhwb2RIUndPaTh2YjJOemNDNXdhMmt1WjI5dlp5OW5kSE55TVRBd0JnZ3JCZ0VGQlFjd0FvWWthSFIwY0RvdkwzQnJhUzVuYjI5bkwzSmxjRzh2WTJWeWRITXZaM1J6Y2pFdVpHVnlNRFFHQTFVZEh3UXRNQ3N3S2FBbm9DV0dJMmgwZEhBNkx5OWpjbXd1Y0d0cExtZHZiMmN2WjNSemNqRXZaM1J6Y2pFdVkzSnNNRTBHQTFVZElBUkdNRVF3Q0FZR1o0RU1BUUlCTURnR0Npc0dBUVFCMW5rQ0JRTXdLakFvQmdnckJnRUZCUWNDQVJZY2FIUjBjSE02THk5d2Eya3VaMjl2Wnk5eVpYQnZjMmwwYjNKNUx6QU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FnRUFJVlRveTI0andYVXIwckFQYzkyNHZ1U1ZiS1F1WXczbkxmbExmTGg1QVlXRWVWbC9EdTE4UUFXVU1kY0o2by9xRlpiaFhrQkgwUE5jdzk3dGhhZjJCZW9EWVk5Q2svYitVR2x1aHgwNnpkNEVCZjdIOVA4NG5ucndwUis0R0JEWksrWGgzSTB0cUp5MnJnT3FORGZscjVJTVE4WlRXQTN5bHRha3pTQktaNlhwRjBQcHF5Q1J2cC9OQ0d2MktYMlR1UENKdnNjcDEvbTJwVlR0eUJqWVBSUStRdUNRR0FKS2p0TjdSNURGcmZUcU1XdllnVmxwQ0pCa3dsdTcrN0tZM2NUSWZ6RTdjbUFMc2tNS05MdUR6K1J6Q2NzWVRzVmFVN1ZwM3hMNjBPWWhxRmt1QU9PeERaNnBIT2o5K09KbVlnUG1PVDRYMys3TDUxZlhKeVJIOUtmTFJQNm5UMzFENW5tc0dBT2daMjYvOFQ5aHNCVzF1bzlqdTVmWkxaWFZWUzVIMEh5SUJNRUt5R01JUGhGV3JsdC9oRlMyOE4xemFLSTBaQkdEM2dZZ0RMYmlEVDlmR1hzdHBrK0ZtYzRvbFZsV1B6WGU4MXZkb0VuRmJyNU0yNzJIZGdKV28rV2hUOUJZTTBKaSt3ZFZtblJmZlhnbG9Fb2x1VE5jV3pjNDFkRnBnSnU4ZkYzTEcwZ2wyaWJTWWlDaTlhNmh2VTBUcHBqSnlJV1hoa0pUY01KbFByV3gxVnl0RVVHclgybDBKRHdSalcvNjU2cjBLVkIwMnhIUkt2bTJaS0kwM1RnbExJcG1WQ0sza0JLa0tOcEJOa0Z0OHJoYWZjQ0tPYjlKeC85dHBORmxRVGw3QjM5ckpsSldrUjE3UW5acVZwdEZlUEZPUm9abUZ6TT0iLCJNSUlGWWpDQ0JFcWdBd0lCQWdJUWQ3ME5iTnMyK1JycUlRL0U4RmpURFRBTkJna3Foa2lHOXcwQkFRc0ZBREJYTVFzd0NRWURWUVFHRXdKQ1JURVpNQmNHQTFVRUNoTVFSMnh2WW1Gc1UybG5iaUJ1ZGkxellURVFNQTRHQTFVRUN4TUhVbTl2ZENCRFFURWJNQmtHQTFVRUF4TVNSMnh2WW1Gc1UybG5iaUJTYjI5MElFTkJNQjRYRFRJd01EWXhPVEF3TURBME1sb1hEVEk0TURFeU9EQXdNREEwTWxvd1J6RUxNQWtHQTFVRUJoTUNWVk14SWpBZ0JnTlZCQW9UR1VkdmIyZHNaU0JVY25WemRDQlRaWEoyYVdObGN5Qk1URU14RkRBU0JnTlZCQU1UQzBkVVV5QlNiMjkwSUZJeE1JSUNJakFOQmdrcWhraUc5dzBCQVFFRkFBT0NBZzhBTUlJQ0NnS0NBZ0VBdGhFQ2l4N2pvWGViTzl5L2xENjNsYWRBUEtIOWd2bDlNZ2FDY2ZiMmpILzc2TnU4YWk2WGw2T01TL2tyOXJINXpvUWRzZm5GbDk3dnVmS2o2YndTaVY2bnFsS3IrQ01ueTZTeG5HUGIxNWwrOEFwZTYyaW05TVphUncxTkVEUGpUckVUbzhnWWJFdnMvQW1RMzUxa0tTVWpCNkcwMGowdVlPRFAwZ21IdTgxSThFM0N3bnFJaXJ1Nnoxa1oxcStQc0Fld25qSHhnc0hBM3k2bWJXd1pEclhZZmlZYVJRTTlzSG1rbENpdEQzOG01YWdJL3Bib1BHaVVVKzZET29nckZaWUpzdUI2akM1MTFwenJwMVprajVaUGFLNDlsOEtFajhDOFFNQUxYTDMyaDdNMWJLd1lVSCtFNEV6Tmt0TWc2VE84VXBtdk1yVXBzeVVxdEVqNWN1SEtaUGZtZ2hDTjZKM0Npb2o2T0dhSy9HUDVBZmw0L1h0Y2QvcDJoL3JzMzdFT2VaVlh0TDBtNzlZQjBlc1dDcnVPQzdYRnhZcFZxOU9zNnBGTEtjd1pwRElsVGlyeFpVVFFBczZxemttMDZwOThnN0JBZStkRHE2ZHNvNDk5aVlINlRLWC8xWTdEemt2Z3RkaXpqa1hQZHNEdFFDdjlVdyt3cDlVN0RiR0tvZ1BlTWEzTWQrcHZlejdXMzVFaUV1YSsrdGd5L0JCakZGRnkzbDNXRnBPOUtXZ3o3enBtN0FlS0p0OFQxMWRsZUNmZVhra1VBS0lBZjVxb0liYXBzWld3cGJrTkZoSGF4MnhJUEVEZ2ZnMWF6Vlk4MFpjRnVjdEw3VGxMbk1RLzBsVVRiaVN3MW5INjlNRzZ6TzBiOWY2QlFkZ0FtRDA2eUs1Nm1EY1lCWlVDQXdFQUFhT0NBVGd3Z2dFME1BNEdBMVVkRHdFQi93UUVBd0lCaGpBUEJnTlZIUk1CQWY4RUJUQURBUUgvTUIwR0ExVWREZ1FXQkJUa3J5c21jUm9yU0NlRkwxSm1MTy93aVJOeFBqQWZCZ05WSFNNRUdEQVdnQlJnZTJZYVJRMlh5b2xRTDMwRXpUU28vL3o5U3pCZ0JnZ3JCZ0VGQlFjQkFRUlVNRkl3SlFZSUt3WUJCUVVITUFHR0dXaDBkSEE2THk5dlkzTndMbkJyYVM1bmIyOW5MMmR6Y2pFd0tRWUlLd1lCQlFVSE1BS0dIV2gwZEhBNkx5OXdhMmt1WjI5dlp5OW5jM0l4TDJkemNqRXVZM0owTURJR0ExVWRId1FyTUNrd0o2QWxvQ09HSVdoMGRIQTZMeTlqY213dWNHdHBMbWR2YjJjdlozTnlNUzluYzNJeExtTnliREE3QmdOVkhTQUVOREF5TUFnR0JtZUJEQUVDQVRBSUJnWm5nUXdCQWdJd0RRWUxLd1lCQkFIV2VRSUZBd0l3RFFZTEt3WUJCQUhXZVFJRkF3TXdEUVlKS29aSWh2Y05BUUVMQlFBRGdnRUJBRFNrSHJFb285QzBkaGVtTVhvaDZkRlNQc2piZEJaQmlMZzlOUjN0NVArVDRWeGZxN3ZxZk0vYjVBM1JpMWZ5Sm05YnZoZEdhSlEzYjJ0NnlNQVlOL29sVWF6c2FMK3l5RW45V3ByS0FTT3NoSUFyQW95WmwrdEphb3gxMThmZXNzbVhuMWhJVnc0MW9lUWExdjF2ZzRGdjc0elBsNi9BaFNydzlVNXBDWkV0NFdpNHdTdHo2ZFRaL0NMQU54OExaaDFKN1FKVmoyZmhNdGZUSnI5dzR6MzBaMjA5Zk9VMGlPTXkrcWR1Qm1wdnZZdVI3aFpMNkR1cHN6Zm53MFNrZnRoczE4ZEc5WktiNTlVaHZtYVNHWlJWYk5RcHNnM0JabHZpZDBsSUtPMmQxeG96Y2xPemdqWFBZb3ZKSkl1bHR6a011MzRxUWI5U3oveWlscmJDZ2o4PSJdfQ.eyJub25jZSI6IlBvSEJNR1FXVTZMTHZuQ21tQUlqUkt4dTJ4ND0iLCJ0aW1lc3RhbXBNcyI6MTYzNzc1MTY1NTE2OSwiYXBrUGFja2FnZU5hbWUiOiJjb20uZmFzdGJhbmtpbmcuZGVidWciLCJhcGtEaWdlc3RTaGEyNTYiOiJsRHF1bDJxejdyd2owRDFJSzBkcTZwTnNaUmR0QW9BbUNNOVh5MGg2bkNjPSIsImN0c1Byb2ZpbGVNYXRjaCI6dHJ1ZSwiYXBrQ2VydGlmaWNhdGVEaWdlc3RTaGEyNTYiOlsiR3k3N1doNFRkR0ZXd3NoaS9VVXdDdUJIL0NBZ2V4VFFLdmJzbW5pWHFpTT0iXSwiYmFzaWNJbnRlZ3JpdHkiOnRydWUsImV2YWx1YXRpb25UeXBlIjoiQkFTSUMsSEFSRFdBUkVfQkFDS0VEIn0.ShOvWqQ_5i-T1ixx59sbk0-6LMo8oKiC5PfZCt9dVJrnfeap8JMQ9x8v19-Yh-M07y54BjQPXFGU-Y602uFc_V7TKHonDqjaEOsx6VfRwiQeZmtaO-Hhmlr2g-xRHFoDOnXy2wHYGfDkMbir50EraIyny3xfs-guIDMwg5qAzQaN999KRsrbHXX-a6wwoQ0qyUSVKGN57T_qOcXaq9X5bI1B3nD1m5Inu7TW0xrCb0sfUn8GDimAtnXELKf048S4iaXBObbgtiNyVQtTEfqHA8WdfhANIZWcV4XQDHbv69wcvrmUTDeZJienIfkmesfYnFDngW2NfR9A9m_Q5sorig");
		headers.put("x-login-nonce", "B6B667EB514890789F56F9B78BFA509AB41B673B");
		headers.put("x-login-timestamp", "1636960116339");

		ValidatableResponse response = RestAssured.given().baseUri(url).contentType(ContentType.JSON).headers(headers)
				.body(Myrequestbody.toJSONString()).when().post().then();

		System.out.println("Request Url -->" + url);
		ExtentReporter.extentLogger("", "Request Url -->" + url);
		System.out.println("Request :" + Myrequestbody);
		ExtentReporter.extentLogger("", "Request :" + Myrequestbody);
		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body= " + Resp);
		return response;
	}

	// New ====================================

	/**
	 * 
	 * @param data
	 * @param url
	 * @return
	 * @throws IOException
	 */

	// ============== Post Response ======================

	public static ValidatableResponse postMethodAPI(HashMap<String, Object> headers, JSONObject Myrequestbody,
			String url) throws IOException {

		ValidatableResponse response = RestAssured.given()
				.baseUri(url)
				.contentType(ContentType.JSON)
				.headers(headers)
				.body(Myrequestbody)
				.when()
				.post()
				.then();

		System.out.println("Request Url -->" + url);
		ExtentReporter.extentLogger("", "Request Url -->" + url);

		return response;

	}

	// ============== Post Response ======================

	public static ValidatableResponse postMethodAuthAPI(HashMap<String, Object> headers, String request,
			String url) throws IOException {

		ValidatableResponse response = RestAssured.given()
				.auth().basic("tslG1aMU12HlAuBY8PiS6vkVtxyWKDk5", "26E1foPXLUtbiFwkizn09LnnCL9ZQS5f")
				.baseUri(url)
				.contentType(ContentType.JSON)
				.headers(headers)
				.body(request)
				.when()
				.post()
				.then();

		System.out.println("Request Url -->" + url);
		ExtentReporter.extentLogger("", "Request Url -->" + url);

		return response;

	}

	public static ValidatableResponse postMethodWithHeadersAPI(HashMap<String, Object> headers, String url)
			throws IOException {

		ValidatableResponse response = RestAssured.given().baseUri(url).contentType(ContentType.JSON).headers(headers)
				.when().post().then();

		System.out.println("Request Url -->" + url);
		ExtentReporter.extentLogger("", "Request Url -->" + url);

		return response;

	}

	// ============== Get Response ======================

	public static ValidatableResponse getMethodAPI(String url) throws IOException {

		ValidatableResponse response = RestAssured.given().baseUri(url).contentType(ContentType.JSON).when().get()
				.then();

		System.out.println("Request Url -->" + url);
		ExtentReporter.extentLogger("", "Request Url -->" + url);

		return response;

	}

	public static ValidatableResponse getMethodWithHeaderAPI(HashMap<String, Object> headers, String url)
			throws IOException {

		ValidatableResponse response = RestAssured.given().baseUri(url).contentType(ContentType.JSON).headers(headers)
				.when().get().then();

		System.out.println("Request Url -->" + url);
		ExtentReporter.extentLogger("", "Request Url -->" + url);

		return response;

	}

	public static ValidatableResponse patchMethodAPI(HashMap<String, Object> headers, JSONObject Myrequestbody,
			String url) throws IOException {

		ValidatableResponse response = RestAssured.given().baseUri(url).contentType(ContentType.JSON).headers(headers)
				.body(Myrequestbody.toJSONString()).when().patch().then();

		System.out.println("Request Url -->" + url);
		ExtentReporter.extentLogger("", "Request Url -->" + url);

		return response;

	}

	public static ValidatableResponse patchMethodWithHeaderAPI(HashMap<String, Object> headers, String url)
			throws IOException {

		ValidatableResponse response = RestAssured.given().baseUri(url).contentType(ContentType.JSON).headers(headers)
				.when().patch().then();

		System.out.println("Request Url -->" + url);
		ExtentReporter.extentLogger("", "Request Url -->" + url);

		return response;

	}


	public static ValidatableResponse MockuserAPI(Object[][] data) throws Exception {

		try
		{

			String filePath = System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

			String url = RingPay_BaseURL.testingServiceURL.concat(RingPay_Endpoints.mockUserEndPoint);
			logger.info("Url :" + url);

			ExtentReporter.extentLogger("url", url);

			Random rand = new Random();

			HashMap<String, String> req_body = new HashMap<>();
			// System.out.println((String) data[0][3]);
			req_body.put("gender", (String) data[0][0]);
			req_body.put("encrypted_name", (String) data[0][1]);


			JSONObject Myrequestbody = new JSONObject();

			Myrequestbody.put("gender", req_body.get("gender"));
			Myrequestbody.put("encrypted_name", req_body.get("encrypted_name"));

			String req=String.valueOf(Myrequestbody);
			ExtentReporter.extentLogger("req_body", "Request :"+req);

			HashMap<String, Object> headers = new HashMap<>();
			headers.put("client-id", "zx2789");

			String header=String.valueOf(headers);
			ExtentReporter.extentLogger("headers","Headers :"+ header);


			ValidatableResponse response = Utilities.postMethodAPI(headers, Myrequestbody, url);

			logger.info("Request :" + Myrequestbody);

			ExtentReporter.extentLogger("", "Request :" + Myrequestbody);

			String Resp = response.extract().body().asString();

			logger.info("Response Body= " + Resp);

			ExtentReporter.extentLogger("", "Response Body= " + Resp);

			// fetching Mobileno
			String mobileNumber = response.extract().body().jsonPath().get("data.data.mobile_number");
			logger.info("MobileNumber : " + mobileNumber);

			// fetching Otp
			Integer Otp = response.extract().body().jsonPath().get("data.data.otp");
			logger.info("OTP : " + Otp);


			// fetching motherName
			String motherName = response.extract().body().jsonPath().get("data.data.mother_name");
			logger.info("MotherName : " + motherName);

			// fetching Email
			String email = response.extract().body().jsonPath().get("data.data.email");
			logger.info("Email : " + email);

			// fetching Dob
			String dob = response.extract().body().jsonPath().get("data.data.dob");
			logger.info("Dob : " + dob);

			// fetching imei
			String imei = response.extract().body().jsonPath().get("data.data.imei");
			logger.info("imei : " + imei);


			// fetching motherName
			String mother_name = response.extract().body().jsonPath().get("data.data.mother_name");
			logger.info("motherName : " + mother_name);


			// fetching androidId
			String androidId = response.extract().body().jsonPath().get("data.data.android_id");
			logger.info("androidId : " + androidId);


			// fetching advertisingId
			String advertisingId = response.extract().body().jsonPath().get("data.data.advertising_id");
			logger.info("advertisingId : " + advertisingId);


			// ================== Write Excel =======================

			// MobileNo to SentOtp
			ExcelWriteData.excelWrite(filePath, "SendOtp", mobileNumber, 1, 2);


			// MobileNo to User_Authenticate
			ExcelWriteData.excelWrite(filePath, "User_Authenticate", mobileNumber, 1, 2);
			ExcelWriteData.excelWrite(filePath, "User_Authenticate", mobileNumber, 2, 2);
			ExcelWriteData.excelWrite(filePath, "User_Authenticate", mobileNumber, 3, 2);
			ExcelWriteData.excelWrite(filePath, "User_Authenticate", mobileNumber, 4, 2);
			ExcelWriteData.excelWrite(filePath, "User_Authenticate", mobileNumber, 5, 2);

			// Data to UpdateUser
			// ExcelWriteData.excelWrite(filePath," UpdateUser",firstName,1,1);
			// ExcelWriteData.excelWrite(filePath," UpdateUser",middleName,1,2);
			// ExcelWriteData.excelWrite(filePath," UpdateUser",lastName,1,3);
			ExcelWriteData.excelWrite(filePath, " UpdateUser", motherName, 1, 4);
			ExcelWriteData.excelWrite(filePath, " UpdateUser", email, 1, 5);
			ExcelWriteData.excelWrite(filePath, " UpdateUser", dob, 1, 6);

			// Data to Register_user
			ExcelWriteData.excelWrite(filePath, "RegisterUser", mobileNumber, 1, 3);
			ExcelWriteData.excelWrite(filePath, "RegisterUser", email, 1, 4);
			ExcelWriteData.excelWrite(filePath, "RegisterUser", imei, 1, 8);

			// Data to User_Onboarding
			ExcelWriteData.excelWrite(filePath, "UserOnboarding", imei, 1, 4);

			// Data to Create_Bnpl_transaction
			ExcelWriteData.excelWrite(filePath, "Create_Bnpl_Transaction", imei, 1, 6);

			// Data to Home_Screen_for_Current_Spends

			ExcelWriteData.excelWrite(filePath, "Current_Spend", androidId, 1, 1);
			ExcelWriteData.excelWrite(filePath, "Current_Spend", advertisingId, 1, 2);
			ExcelWriteData.excelWrite(filePath, "Current_Spend", imei, 1, 4);


			// Data to Update User
			ExcelWriteData.excelWrite(filePath, "UpdateUserStatus", mother_name, 1, 2);

			//		ExtentReporter.extentLoggerPass(message+" - Passed");

			return response;


		}
		catch(Exception e)
		{
			String message="MockuserAPI";
			ExtentReporter.extentLogger("",message);
			ExtentReporter.extentLoggerFail(e.getMessage());
			return null;
		}

	}


	/**
	 * 
	 * @param data
	 * @param url
	 * @return
	 * @throws Exception 
	 */

	public static ValidatableResponse OnloadAPI() throws Exception {


		try {
			String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.onloadEndPoint);
			logger.info("Url :" + url);

			ExtentReporter.extentLogger("url", url);
			Random rand = new Random();


			ValidatableResponse response = Utilities.getMethodAPI(url);

			String Resp = response.extract().body().asString();
			logger.info("Response Body= " + Resp);
			ExtentReporter.extentLogger("", "Response Body= " + Resp);

			return response;

		}

		catch (Exception e) {
			String message="OnloadAPI";
			ExtentReporter.extentLogger("",message);
			ExtentReporter.extentLoggerFail(e.getMessage());
			return null;
		}

	} 



	public static ValidatableResponse Get_Details_VPA_API(Object[][] data) throws Exception {

		try {
			String filePath = System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

			String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.getdetailsVPAEndPoint);
			logger.info("Url :" + url);

			ExtentReporter.extentLogger("url", url);

			Random rand = new Random();

			HashMap<String, String> req_queryparam = new HashMap<>();
			req_queryparam.put("vpa", (String) data[0][0]);

			JSONObject Myrequestqueryparam = new JSONObject();

			Myrequestqueryparam.put("vpa", req_queryparam.get("vpa"));
			System.out.println("hellloo: " + Myrequestqueryparam);


			String req=String.valueOf(Myrequestqueryparam);
			ExtentReporter.extentLogger("req_body", "Request :"+req);


			ValidatableResponse response = RestAssured.given().baseUri(url).contentType(ContentType.JSON).when()
					.body(Myrequestqueryparam.toJSONString()).get().then();


			logger.info("Request :" + Myrequestqueryparam);
			ExtentReporter.extentLogger("", "Request :" + Myrequestqueryparam);
			String Resp = response.extract().body().asString();
			logger.info("Response Body= " + Resp);
			ExtentReporter.extentLogger("", "Response Body= " + Resp);

			//  fetching merchant_reference_number

			String merchant_Reference_Number = response.extract().body().jsonPath().get("data.merchant_details.merchant_reference_number");
			logger.info("Request :" + Myrequestqueryparam);


			//  Write Excel

			//  Data to UpdateUser
			ExcelWriteData.excelWrite(filePath, " UpdateUser", merchant_Reference_Number, 1, 7);

			return response;

		}
		catch (Exception e) {
			String message="Get_Details_VPA_API";
			ExtentReporter.extentLogger("",message);
			ExtentReporter.extentLoggerFail(e.getMessage());
			return null;
		}

	}


	public static ValidatableResponse userTokenAPI(Object[][] data) throws Exception {
		try {
			Random rand = new Random();
			String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.userAuthenticateEndPoint);

			logger.info("Url :" + url);
			ExtentReporter.extentLogger("url", url);


			HashMap<String, String> req_body = new HashMap<>();
			req_body.put("otp", (String) data[0][0]);
			req_body.put("mobile_number", (String) data[0][1]);
			req_body.put("client_id", (String) data[0][2]);
			req_body.put("source_app", (String) data[0][3]);

			JSONObject Myrequestbody = new JSONObject();

			Myrequestbody.put("otp", req_body.get("otp"));
			Myrequestbody.put("mobile_number", req_body.get("mobile_number"));
			Myrequestbody.put("client_id", req_body.get("client_id"));
			Myrequestbody.put("source_app", req_body.get("source_app"));

			String req=String.valueOf(Myrequestbody);
			ExtentReporter.extentLogger("req_body", "Request :"+req);


			HashMap<String, Object> headers = new HashMap<>();
			headers.put("x-request-id", rand.nextInt(1001));
			headers.put("X-Client-App", "android");
			headers.put("X-Client-Version", 4.9);
			headers.put("X-Client-OS-Type", "android");
			headers.put("X-Client-OS-Version", 10);
			headers.put("x-login-token",
					"eyJhbGciOiJSUzI1NiIsIng1YyI6WyJNSUlGWVRDQ0JFbWdBd0lCQWdJUkFQaEtkUXdrSUFNRENRQUFBQUM4QzZvd0RRWUpLb1pJaHZjTkFRRUxCUUF3UmpFTE1Ba0dBMVVFQmhNQ1ZWTXhJakFnQmdOVkJBb1RHVWR2YjJkc1pTQlVjblZ6ZENCVFpYSjJhV05sY3lCTVRFTXhFekFSQmdOVkJBTVRDa2RVVXlCRFFTQXhSRFF3SGhjTk1qRXhNREUzTVRjd05qQTNXaGNOTWpJd01URTFNVGN3TmpBMldqQWRNUnN3R1FZRFZRUURFeEpoZEhSbGMzUXVZVzVrY205cFpDNWpiMjB3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRQ3ZnU2VHM3JTVlcwSVBpWkJGVmJoMktjYjNoTnl3R2VJOUZmaVgyUXZRQnBmUkIvT0xiUUFwZGdDWTZJL1dqNEw0aHVNQzRMVHA3OFZXbmhtZGJ3Y1NxbXJzNkpDM3kwWnVmVm4ydzhsV0NYODNsYytFUmdRVHhmaGUwTVNIakhlWk9mWGROQ3dqejZrTXJkZEVPUlJ5T3V3SWdjcXcrNGoycS9mSktHbkUyNXQ5NndOTDgrUDg1V294ZXhaZEROR1pzMmkzNmRvZkdVTGR1YTZaWFI1YjFlODJkd0dra0Rkd3RFMjZCeDRhTTl4VDEwK3A0S3FKNXZ0MWpvY1N0K2tTWHFRaEowQlJjS082OWhGUTRDSUdKYk5EYlRIMENGYlMvanJsNThGWnhVTUVwaUNHbG9JdmJyZ20xSlFzRDE2UmtIZlQ0NVM5UERNc3k5WFI4bjVqQWdNQkFBR2pnZ0p4TUlJQ2JUQU9CZ05WSFE4QkFmOEVCQU1DQmFBd0V3WURWUjBsQkF3d0NnWUlLd1lCQlFVSEF3RXdEQVlEVlIwVEFRSC9CQUl3QURBZEJnTlZIUTRFRmdRVUJ0M1lUWkFYZ3pGYXdpV2FXN3hmaStYRDhnZ3dId1lEVlIwakJCZ3dGb0FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd2JRWUlLd1lCQlFVSEFRRUVZVEJmTUNvR0NDc0dBUVVGQnpBQmhoNW9kSFJ3T2k4dmIyTnpjQzV3YTJrdVoyOXZaeTluZEhNeFpEUnBiblF3TVFZSUt3WUJCUVVITUFLR0pXaDBkSEE2THk5d2Eya3VaMjl2Wnk5eVpYQnZMMk5sY25SekwyZDBjekZrTkM1a1pYSXdIUVlEVlIwUkJCWXdGSUlTWVhSMFpYTjBMbUZ1WkhKdmFXUXVZMjl0TUNFR0ExVWRJQVFhTUJnd0NBWUdaNEVNQVFJQk1Bd0dDaXNHQVFRQjFua0NCUU13UHdZRFZSMGZCRGd3TmpBMG9ES2dNSVl1YUhSMGNEb3ZMMk55YkhNdWNHdHBMbWR2YjJjdlozUnpNV1EwYVc1MEwxZ3lTakpJY2w4M1VHbE5MbU55YkRDQ0FRUUdDaXNHQVFRQjFua0NCQUlFZ2ZVRWdmSUE4QUIxQUZHanNQWDlBWG1jVm0yNE4zaVBES1I2ekJzbnkvZWVpRUthRGY3VWl3WGxBQUFCZkk5dXVqSUFBQVFEQUVZd1JBSWdYd3JxbEEvV21IRFVySVpSWDIrS24raldjRVlsQjliVCtsRk9HT3RaTEtNQ0lGUzRXYU14Q09GaVAxTnhVN3hMcVBQVGlwR2dlaFgwS0IwTFgrTXhkdEl0QUhjQUtYbSs4SjQ1T1NId1ZuT2ZZNlYzNWI1WGZaeGdDdmo1VFYwbVhDVmR4NFFBQUFGOGoyNjZLUUFBQkFNQVNEQkdBaUVBNDdRNldJYmVnQUZuL0liUUM5OEFoR0dlY0xGVWowcjRCMnlrSkFlN2tzd0NJUURiQ2RNNFdzQ2JVUHJsSDhIV3M1ZGpqQWluKy9jWDZPNHpDTldMbzJxakhEQU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FRRUFMWHlhOUhVVm5rZURkUFgyd0tzQ2QybDhNcGpTeW5iVWVKWGI5Um04dXRsczRjRzkvdXEzRzZ3clRGWkNhdldJMnE5SmxlUnA1Q21DeCtrcElPVVh3T0dPQUZ3SVFrUFhCRnFrOGJscmE1MmhGTTluMUROYzY1bmNVRHkybXFYbjNXaVByN0crZEdSNlkzRnFKMjQ3K0VySlllbTZnM28rR3ZVcERxbWpkZ01SdHFFTXlmTVZIa0xoN3ZucWlXdnYzQ2VlU1ViRjkvMFdxUklNdTdPSFZyTkVET1ZUUEZuWENVczgyUk1OVVd0dVJTS1Njelh3QXFNN0JFWGR4TjNYcXE1Z1dOUDdUeFowczZzRTZGOHovWmN0OFVLdHRkNVBidGhrdGdFMmVvUmFaYTB1alNWVmtUeTVGb1pvMWJ1ZXhjbnM5WjlEWDFCUy9RU1JXbjNBUHc9PSIsIk1JSUZqRENDQTNTZ0F3SUJBZ0lOQWdDT3NnSXpObVdMWk0zYm16QU5CZ2txaGtpRzl3MEJBUXNGQURCSE1Rc3dDUVlEVlFRR0V3SlZVekVpTUNBR0ExVUVDaE1aUjI5dloyeGxJRlJ5ZFhOMElGTmxjblpwWTJWeklFeE1RekVVTUJJR0ExVUVBeE1MUjFSVElGSnZiM1FnVWpFd0hoY05NakF3T0RFek1EQXdNRFF5V2hjTk1qY3dPVE13TURBd01EUXlXakJHTVFzd0NRWURWUVFHRXdKVlV6RWlNQ0FHQTFVRUNoTVpSMjl2WjJ4bElGUnlkWE4wSUZObGNuWnBZMlZ6SUV4TVF6RVRNQkVHQTFVRUF4TUtSMVJUSUVOQklERkVORENDQVNJd0RRWUpLb1pJaHZjTkFRRUJCUUFEZ2dFUEFEQ0NBUW9DZ2dFQkFLdkFxcVBDRTI3bDB3OXpDOGRUUElFODliQSt4VG1EYUc3eTdWZlE0YyttT1dobFVlYlVRcEsweXYycjY3OFJKRXhLMEhXRGplcStuTElITjFFbTVqNnJBUlppeG15UlNqaElSMEtPUVBHQk1VbGRzYXp0SUlKN08wZy84MnFqL3ZHRGwvLzN0NHRUcXhpUmhMUW5UTFhKZGVCKzJEaGtkVTZJSWd4NndON0U1TmNVSDNSY3NlamNxajhwNVNqMTl2Qm02aTFGaHFMR3ltaE1Gcm9XVlVHTzN4dElIOTFkc2d5NGVGS2NmS1ZMV0szbzIxOTBRMExtL1NpS21MYlJKNUF1NHkxZXVGSm0ySk05ZUI4NEZrcWEzaXZyWFdVZVZ0eWUwQ1FkS3ZzWTJGa2F6dnh0eHZ1c0xKekxXWUhrNTV6Y1JBYWNEQTJTZUV0QmJRZkQxcXNDQXdFQUFhT0NBWFl3Z2dGeU1BNEdBMVVkRHdFQi93UUVBd0lCaGpBZEJnTlZIU1VFRmpBVUJnZ3JCZ0VGQlFjREFRWUlLd1lCQlFVSEF3SXdFZ1lEVlIwVEFRSC9CQWd3QmdFQi93SUJBREFkQmdOVkhRNEVGZ1FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd0h3WURWUjBqQkJnd0ZvQVU1SzhySm5FYUswZ25oUzlTWml6djhJa1RjVDR3YUFZSUt3WUJCUVVIQVFFRVhEQmFNQ1lHQ0NzR0FRVUZCekFCaGhwb2RIUndPaTh2YjJOemNDNXdhMmt1WjI5dlp5OW5kSE55TVRBd0JnZ3JCZ0VGQlFjd0FvWWthSFIwY0RvdkwzQnJhUzVuYjI5bkwzSmxjRzh2WTJWeWRITXZaM1J6Y2pFdVpHVnlNRFFHQTFVZEh3UXRNQ3N3S2FBbm9DV0dJMmgwZEhBNkx5OWpjbXd1Y0d0cExtZHZiMmN2WjNSemNqRXZaM1J6Y2pFdVkzSnNNRTBHQTFVZElBUkdNRVF3Q0FZR1o0RU1BUUlCTURnR0Npc0dBUVFCMW5rQ0JRTXdLakFvQmdnckJnRUZCUWNDQVJZY2FIUjBjSE02THk5d2Eya3VaMjl2Wnk5eVpYQnZjMmwwYjNKNUx6QU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FnRUFJVlRveTI0andYVXIwckFQYzkyNHZ1U1ZiS1F1WXczbkxmbExmTGg1QVlXRWVWbC9EdTE4UUFXVU1kY0o2by9xRlpiaFhrQkgwUE5jdzk3dGhhZjJCZW9EWVk5Q2svYitVR2x1aHgwNnpkNEVCZjdIOVA4NG5ucndwUis0R0JEWksrWGgzSTB0cUp5MnJnT3FORGZscjVJTVE4WlRXQTN5bHRha3pTQktaNlhwRjBQcHF5Q1J2cC9OQ0d2MktYMlR1UENKdnNjcDEvbTJwVlR0eUJqWVBSUStRdUNRR0FKS2p0TjdSNURGcmZUcU1XdllnVmxwQ0pCa3dsdTcrN0tZM2NUSWZ6RTdjbUFMc2tNS05MdUR6K1J6Q2NzWVRzVmFVN1ZwM3hMNjBPWWhxRmt1QU9PeERaNnBIT2o5K09KbVlnUG1PVDRYMys3TDUxZlhKeVJIOUtmTFJQNm5UMzFENW5tc0dBT2daMjYvOFQ5aHNCVzF1bzlqdTVmWkxaWFZWUzVIMEh5SUJNRUt5R01JUGhGV3JsdC9oRlMyOE4xemFLSTBaQkdEM2dZZ0RMYmlEVDlmR1hzdHBrK0ZtYzRvbFZsV1B6WGU4MXZkb0VuRmJyNU0yNzJIZGdKV28rV2hUOUJZTTBKaSt3ZFZtblJmZlhnbG9Fb2x1VE5jV3pjNDFkRnBnSnU4ZkYzTEcwZ2wyaWJTWWlDaTlhNmh2VTBUcHBqSnlJV1hoa0pUY01KbFByV3gxVnl0RVVHclgybDBKRHdSalcvNjU2cjBLVkIwMnhIUkt2bTJaS0kwM1RnbExJcG1WQ0sza0JLa0tOcEJOa0Z0OHJoYWZjQ0tPYjlKeC85dHBORmxRVGw3QjM5ckpsSldrUjE3UW5acVZwdEZlUEZPUm9abUZ6TT0iLCJNSUlGWWpDQ0JFcWdBd0lCQWdJUWQ3ME5iTnMyK1JycUlRL0U4RmpURFRBTkJna3Foa2lHOXcwQkFRc0ZBREJYTVFzd0NRWURWUVFHRXdKQ1JURVpNQmNHQTFVRUNoTVFSMnh2WW1Gc1UybG5iaUJ1ZGkxellURVFNQTRHQTFVRUN4TUhVbTl2ZENCRFFURWJNQmtHQTFVRUF4TVNSMnh2WW1Gc1UybG5iaUJTYjI5MElFTkJNQjRYRFRJd01EWXhPVEF3TURBME1sb1hEVEk0TURFeU9EQXdNREEwTWxvd1J6RUxNQWtHQTFVRUJoTUNWVk14SWpBZ0JnTlZCQW9UR1VkdmIyZHNaU0JVY25WemRDQlRaWEoyYVdObGN5Qk1URU14RkRBU0JnTlZCQU1UQzBkVVV5QlNiMjkwSUZJeE1JSUNJakFOQmdrcWhraUc5dzBCQVFFRkFBT0NBZzhBTUlJQ0NnS0NBZ0VBdGhFQ2l4N2pvWGViTzl5L2xENjNsYWRBUEtIOWd2bDlNZ2FDY2ZiMmpILzc2TnU4YWk2WGw2T01TL2tyOXJINXpvUWRzZm5GbDk3dnVmS2o2YndTaVY2bnFsS3IrQ01ueTZTeG5HUGIxNWwrOEFwZTYyaW05TVphUncxTkVEUGpUckVUbzhnWWJFdnMvQW1RMzUxa0tTVWpCNkcwMGowdVlPRFAwZ21IdTgxSThFM0N3bnFJaXJ1Nnoxa1oxcStQc0Fld25qSHhnc0hBM3k2bWJXd1pEclhZZmlZYVJRTTlzSG1rbENpdEQzOG01YWdJL3Bib1BHaVVVKzZET29nckZaWUpzdUI2akM1MTFwenJwMVprajVaUGFLNDlsOEtFajhDOFFNQUxYTDMyaDdNMWJLd1lVSCtFNEV6Tmt0TWc2VE84VXBtdk1yVXBzeVVxdEVqNWN1SEtaUGZtZ2hDTjZKM0Npb2o2T0dhSy9HUDVBZmw0L1h0Y2QvcDJoL3JzMzdFT2VaVlh0TDBtNzlZQjBlc1dDcnVPQzdYRnhZcFZxOU9zNnBGTEtjd1pwRElsVGlyeFpVVFFBczZxemttMDZwOThnN0JBZStkRHE2ZHNvNDk5aVlINlRLWC8xWTdEemt2Z3RkaXpqa1hQZHNEdFFDdjlVdyt3cDlVN0RiR0tvZ1BlTWEzTWQrcHZlejdXMzVFaUV1YSsrdGd5L0JCakZGRnkzbDNXRnBPOUtXZ3o3enBtN0FlS0p0OFQxMWRsZUNmZVhra1VBS0lBZjVxb0liYXBzWld3cGJrTkZoSGF4MnhJUEVEZ2ZnMWF6Vlk4MFpjRnVjdEw3VGxMbk1RLzBsVVRiaVN3MW5INjlNRzZ6TzBiOWY2QlFkZ0FtRDA2eUs1Nm1EY1lCWlVDQXdFQUFhT0NBVGd3Z2dFME1BNEdBMVVkRHdFQi93UUVBd0lCaGpBUEJnTlZIUk1CQWY4RUJUQURBUUgvTUIwR0ExVWREZ1FXQkJUa3J5c21jUm9yU0NlRkwxSm1MTy93aVJOeFBqQWZCZ05WSFNNRUdEQVdnQlJnZTJZYVJRMlh5b2xRTDMwRXpUU28vL3o5U3pCZ0JnZ3JCZ0VGQlFjQkFRUlVNRkl3SlFZSUt3WUJCUVVITUFHR0dXaDBkSEE2THk5dlkzTndMbkJyYVM1bmIyOW5MMmR6Y2pFd0tRWUlLd1lCQlFVSE1BS0dIV2gwZEhBNkx5OXdhMmt1WjI5dlp5OW5jM0l4TDJkemNqRXVZM0owTURJR0ExVWRId1FyTUNrd0o2QWxvQ09HSVdoMGRIQTZMeTlqY213dWNHdHBMbWR2YjJjdlozTnlNUzluYzNJeExtTnliREE3QmdOVkhTQUVOREF5TUFnR0JtZUJEQUVDQVRBSUJnWm5nUXdCQWdJd0RRWUxLd1lCQkFIV2VRSUZBd0l3RFFZTEt3WUJCQUhXZVFJRkF3TXdEUVlKS29aSWh2Y05BUUVMQlFBRGdnRUJBRFNrSHJFb285QzBkaGVtTVhvaDZkRlNQc2piZEJaQmlMZzlOUjN0NVArVDRWeGZxN3ZxZk0vYjVBM1JpMWZ5Sm05YnZoZEdhSlEzYjJ0NnlNQVlOL29sVWF6c2FMK3l5RW45V3ByS0FTT3NoSUFyQW95WmwrdEphb3gxMThmZXNzbVhuMWhJVnc0MW9lUWExdjF2ZzRGdjc0elBsNi9BaFNydzlVNXBDWkV0NFdpNHdTdHo2ZFRaL0NMQU54OExaaDFKN1FKVmoyZmhNdGZUSnI5dzR6MzBaMjA5Zk9VMGlPTXkrcWR1Qm1wdnZZdVI3aFpMNkR1cHN6Zm53MFNrZnRoczE4ZEc5WktiNTlVaHZtYVNHWlJWYk5RcHNnM0JabHZpZDBsSUtPMmQxeG96Y2xPemdqWFBZb3ZKSkl1bHR6a011MzRxUWI5U3oveWlscmJDZ2o4PSJdfQ.eyJub25jZSI6IlBvSEJNR1FXVTZMTHZuQ21tQUlqUkt4dTJ4ND0iLCJ0aW1lc3RhbXBNcyI6MTYzNzc1MTY1NTE2OSwiYXBrUGFja2FnZU5hbWUiOiJjb20uZmFzdGJhbmtpbmcuZGVidWciLCJhcGtEaWdlc3RTaGEyNTYiOiJsRHF1bDJxejdyd2owRDFJSzBkcTZwTnNaUmR0QW9BbUNNOVh5MGg2bkNjPSIsImN0c1Byb2ZpbGVNYXRjaCI6dHJ1ZSwiYXBrQ2VydGlmaWNhdGVEaWdlc3RTaGEyNTYiOlsiR3k3N1doNFRkR0ZXd3NoaS9VVXdDdUJIL0NBZ2V4VFFLdmJzbW5pWHFpTT0iXSwiYmFzaWNJbnRlZ3JpdHkiOnRydWUsImV2YWx1YXRpb25UeXBlIjoiQkFTSUMsSEFSRFdBUkVfQkFDS0VEIn0.ShOvWqQ_5i-T1ixx59sbk0-6LMo8oKiC5PfZCt9dVJrnfeap8JMQ9x8v19-Yh-M07y54BjQPXFGU-Y602uFc_V7TKHonDqjaEOsx6VfRwiQeZmtaO-Hhmlr2g-xRHFoDOnXy2wHYGfDkMbir50EraIyny3xfs-guIDMwg5qAzQaN999KRsrbHXX-a6wwoQ0qyUSVKGN57T_qOcXaq9X5bI1B3nD1m5Inu7TW0xrCb0sfUn8GDimAtnXELKf048S4iaXBObbgtiNyVQtTEfqHA8WdfhANIZWcV4XQDHbv69wcvrmUTDeZJienIfkmesfYnFDngW2NfR9A9m_Q5sorig");
			headers.put("x-login-nonce", "B6B667EB514890789F56F9B78BFA509AB41B673B");
			headers.put("x-login-timestamp", "1636960116339");


			String header=String.valueOf(headers);
			ExtentReporter.extentLogger("header", header);

			ValidatableResponse response = Utilities.postMethodAPI(headers, Myrequestbody, url);

			String Resp = response.extract().body().asString();
			logger.info("Response Body= " + Resp);
			ExtentReporter.extentLogger("", "Response Body= " + Resp);


			return response;
		}
		catch (Exception e) {
			String message="userTokenAPI";
			ExtentReporter.extentLoggerFail(message+" - Failed");	
		}
		return null;

	}

	//	public static ValidatableResponse invalidOtpAPI(Object[][] data) throws IOException {
	//
	//		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.userAuthenticateEndPoint);
	//		System.out.println("Url :" + url);
	//
	//		Random rand = new Random();
	//
	//		HashMap<String, String> req_body = new HashMap<>();
	//
	//		req_body.put("otp", (String) data[0][0]);
	//		req_body.put("mobile_number", (String) data[0][1]);
	//		req_body.put("client_id", (String) data[0][2]);
	//		req_body.put("source_app", (String) data[0][3]);
	//
	//		JSONObject Myrequestbody = new JSONObject();
	//
	//		Myrequestbody.put("otp", req_body.get("otp"));
	//		Myrequestbody.put("mobile_number", req_body.get("mobile_number"));
	//		Myrequestbody.put("client_id", req_body.get("client_id"));
	//		Myrequestbody.put("source_app", req_body.get("source_app"));
	//
	//		HashMap<String, Object> headers = new HashMap<>();
	//		headers.put("x-request-id", rand.nextInt(1001));
	//		headers.put("X-Client-App", "android");
	//		headers.put("X-Client-Version", 4.9);
	//		headers.put("X-Client-OS-Type", "android");
	//		headers.put("X-Client-OS-Version", 10);
	//		headers.put("x-login-token",
	//				"eyJhbGciOiJSUzI1NiIsIng1YyI6WyJNSUlGWVRDQ0JFbWdBd0lCQWdJUkFQaEtkUXdrSUFNRENRQUFBQUM4QzZvd0RRWUpLb1pJaHZjTkFRRUxCUUF3UmpFTE1Ba0dBMVVFQmhNQ1ZWTXhJakFnQmdOVkJBb1RHVWR2YjJkc1pTQlVjblZ6ZENCVFpYSjJhV05sY3lCTVRFTXhFekFSQmdOVkJBTVRDa2RVVXlCRFFTQXhSRFF3SGhjTk1qRXhNREUzTVRjd05qQTNXaGNOTWpJd01URTFNVGN3TmpBMldqQWRNUnN3R1FZRFZRUURFeEpoZEhSbGMzUXVZVzVrY205cFpDNWpiMjB3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRQ3ZnU2VHM3JTVlcwSVBpWkJGVmJoMktjYjNoTnl3R2VJOUZmaVgyUXZRQnBmUkIvT0xiUUFwZGdDWTZJL1dqNEw0aHVNQzRMVHA3OFZXbmhtZGJ3Y1NxbXJzNkpDM3kwWnVmVm4ydzhsV0NYODNsYytFUmdRVHhmaGUwTVNIakhlWk9mWGROQ3dqejZrTXJkZEVPUlJ5T3V3SWdjcXcrNGoycS9mSktHbkUyNXQ5NndOTDgrUDg1V294ZXhaZEROR1pzMmkzNmRvZkdVTGR1YTZaWFI1YjFlODJkd0dra0Rkd3RFMjZCeDRhTTl4VDEwK3A0S3FKNXZ0MWpvY1N0K2tTWHFRaEowQlJjS082OWhGUTRDSUdKYk5EYlRIMENGYlMvanJsNThGWnhVTUVwaUNHbG9JdmJyZ20xSlFzRDE2UmtIZlQ0NVM5UERNc3k5WFI4bjVqQWdNQkFBR2pnZ0p4TUlJQ2JUQU9CZ05WSFE4QkFmOEVCQU1DQmFBd0V3WURWUjBsQkF3d0NnWUlLd1lCQlFVSEF3RXdEQVlEVlIwVEFRSC9CQUl3QURBZEJnTlZIUTRFRmdRVUJ0M1lUWkFYZ3pGYXdpV2FXN3hmaStYRDhnZ3dId1lEVlIwakJCZ3dGb0FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd2JRWUlLd1lCQlFVSEFRRUVZVEJmTUNvR0NDc0dBUVVGQnpBQmhoNW9kSFJ3T2k4dmIyTnpjQzV3YTJrdVoyOXZaeTluZEhNeFpEUnBiblF3TVFZSUt3WUJCUVVITUFLR0pXaDBkSEE2THk5d2Eya3VaMjl2Wnk5eVpYQnZMMk5sY25SekwyZDBjekZrTkM1a1pYSXdIUVlEVlIwUkJCWXdGSUlTWVhSMFpYTjBMbUZ1WkhKdmFXUXVZMjl0TUNFR0ExVWRJQVFhTUJnd0NBWUdaNEVNQVFJQk1Bd0dDaXNHQVFRQjFua0NCUU13UHdZRFZSMGZCRGd3TmpBMG9ES2dNSVl1YUhSMGNEb3ZMMk55YkhNdWNHdHBMbWR2YjJjdlozUnpNV1EwYVc1MEwxZ3lTakpJY2w4M1VHbE5MbU55YkRDQ0FRUUdDaXNHQVFRQjFua0NCQUlFZ2ZVRWdmSUE4QUIxQUZHanNQWDlBWG1jVm0yNE4zaVBES1I2ekJzbnkvZWVpRUthRGY3VWl3WGxBQUFCZkk5dXVqSUFBQVFEQUVZd1JBSWdYd3JxbEEvV21IRFVySVpSWDIrS24raldjRVlsQjliVCtsRk9HT3RaTEtNQ0lGUzRXYU14Q09GaVAxTnhVN3hMcVBQVGlwR2dlaFgwS0IwTFgrTXhkdEl0QUhjQUtYbSs4SjQ1T1NId1ZuT2ZZNlYzNWI1WGZaeGdDdmo1VFYwbVhDVmR4NFFBQUFGOGoyNjZLUUFBQkFNQVNEQkdBaUVBNDdRNldJYmVnQUZuL0liUUM5OEFoR0dlY0xGVWowcjRCMnlrSkFlN2tzd0NJUURiQ2RNNFdzQ2JVUHJsSDhIV3M1ZGpqQWluKy9jWDZPNHpDTldMbzJxakhEQU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FRRUFMWHlhOUhVVm5rZURkUFgyd0tzQ2QybDhNcGpTeW5iVWVKWGI5Um04dXRsczRjRzkvdXEzRzZ3clRGWkNhdldJMnE5SmxlUnA1Q21DeCtrcElPVVh3T0dPQUZ3SVFrUFhCRnFrOGJscmE1MmhGTTluMUROYzY1bmNVRHkybXFYbjNXaVByN0crZEdSNlkzRnFKMjQ3K0VySlllbTZnM28rR3ZVcERxbWpkZ01SdHFFTXlmTVZIa0xoN3ZucWlXdnYzQ2VlU1ViRjkvMFdxUklNdTdPSFZyTkVET1ZUUEZuWENVczgyUk1OVVd0dVJTS1Njelh3QXFNN0JFWGR4TjNYcXE1Z1dOUDdUeFowczZzRTZGOHovWmN0OFVLdHRkNVBidGhrdGdFMmVvUmFaYTB1alNWVmtUeTVGb1pvMWJ1ZXhjbnM5WjlEWDFCUy9RU1JXbjNBUHc9PSIsIk1JSUZqRENDQTNTZ0F3SUJBZ0lOQWdDT3NnSXpObVdMWk0zYm16QU5CZ2txaGtpRzl3MEJBUXNGQURCSE1Rc3dDUVlEVlFRR0V3SlZVekVpTUNBR0ExVUVDaE1aUjI5dloyeGxJRlJ5ZFhOMElGTmxjblpwWTJWeklFeE1RekVVTUJJR0ExVUVBeE1MUjFSVElGSnZiM1FnVWpFd0hoY05NakF3T0RFek1EQXdNRFF5V2hjTk1qY3dPVE13TURBd01EUXlXakJHTVFzd0NRWURWUVFHRXdKVlV6RWlNQ0FHQTFVRUNoTVpSMjl2WjJ4bElGUnlkWE4wSUZObGNuWnBZMlZ6SUV4TVF6RVRNQkVHQTFVRUF4TUtSMVJUSUVOQklERkVORENDQVNJd0RRWUpLb1pJaHZjTkFRRUJCUUFEZ2dFUEFEQ0NBUW9DZ2dFQkFLdkFxcVBDRTI3bDB3OXpDOGRUUElFODliQSt4VG1EYUc3eTdWZlE0YyttT1dobFVlYlVRcEsweXYycjY3OFJKRXhLMEhXRGplcStuTElITjFFbTVqNnJBUlppeG15UlNqaElSMEtPUVBHQk1VbGRzYXp0SUlKN08wZy84MnFqL3ZHRGwvLzN0NHRUcXhpUmhMUW5UTFhKZGVCKzJEaGtkVTZJSWd4NndON0U1TmNVSDNSY3NlamNxajhwNVNqMTl2Qm02aTFGaHFMR3ltaE1Gcm9XVlVHTzN4dElIOTFkc2d5NGVGS2NmS1ZMV0szbzIxOTBRMExtL1NpS21MYlJKNUF1NHkxZXVGSm0ySk05ZUI4NEZrcWEzaXZyWFdVZVZ0eWUwQ1FkS3ZzWTJGa2F6dnh0eHZ1c0xKekxXWUhrNTV6Y1JBYWNEQTJTZUV0QmJRZkQxcXNDQXdFQUFhT0NBWFl3Z2dGeU1BNEdBMVVkRHdFQi93UUVBd0lCaGpBZEJnTlZIU1VFRmpBVUJnZ3JCZ0VGQlFjREFRWUlLd1lCQlFVSEF3SXdFZ1lEVlIwVEFRSC9CQWd3QmdFQi93SUJBREFkQmdOVkhRNEVGZ1FVSmVJWURySlhrWlFxNWRSZGhwQ0QzbE96dUpJd0h3WURWUjBqQkJnd0ZvQVU1SzhySm5FYUswZ25oUzlTWml6djhJa1RjVDR3YUFZSUt3WUJCUVVIQVFFRVhEQmFNQ1lHQ0NzR0FRVUZCekFCaGhwb2RIUndPaTh2YjJOemNDNXdhMmt1WjI5dlp5OW5kSE55TVRBd0JnZ3JCZ0VGQlFjd0FvWWthSFIwY0RvdkwzQnJhUzVuYjI5bkwzSmxjRzh2WTJWeWRITXZaM1J6Y2pFdVpHVnlNRFFHQTFVZEh3UXRNQ3N3S2FBbm9DV0dJMmgwZEhBNkx5OWpjbXd1Y0d0cExtZHZiMmN2WjNSemNqRXZaM1J6Y2pFdVkzSnNNRTBHQTFVZElBUkdNRVF3Q0FZR1o0RU1BUUlCTURnR0Npc0dBUVFCMW5rQ0JRTXdLakFvQmdnckJnRUZCUWNDQVJZY2FIUjBjSE02THk5d2Eya3VaMjl2Wnk5eVpYQnZjMmwwYjNKNUx6QU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FnRUFJVlRveTI0andYVXIwckFQYzkyNHZ1U1ZiS1F1WXczbkxmbExmTGg1QVlXRWVWbC9EdTE4UUFXVU1kY0o2by9xRlpiaFhrQkgwUE5jdzk3dGhhZjJCZW9EWVk5Q2svYitVR2x1aHgwNnpkNEVCZjdIOVA4NG5ucndwUis0R0JEWksrWGgzSTB0cUp5MnJnT3FORGZscjVJTVE4WlRXQTN5bHRha3pTQktaNlhwRjBQcHF5Q1J2cC9OQ0d2MktYMlR1UENKdnNjcDEvbTJwVlR0eUJqWVBSUStRdUNRR0FKS2p0TjdSNURGcmZUcU1XdllnVmxwQ0pCa3dsdTcrN0tZM2NUSWZ6RTdjbUFMc2tNS05MdUR6K1J6Q2NzWVRzVmFVN1ZwM3hMNjBPWWhxRmt1QU9PeERaNnBIT2o5K09KbVlnUG1PVDRYMys3TDUxZlhKeVJIOUtmTFJQNm5UMzFENW5tc0dBT2daMjYvOFQ5aHNCVzF1bzlqdTVmWkxaWFZWUzVIMEh5SUJNRUt5R01JUGhGV3JsdC9oRlMyOE4xemFLSTBaQkdEM2dZZ0RMYmlEVDlmR1hzdHBrK0ZtYzRvbFZsV1B6WGU4MXZkb0VuRmJyNU0yNzJIZGdKV28rV2hUOUJZTTBKaSt3ZFZtblJmZlhnbG9Fb2x1VE5jV3pjNDFkRnBnSnU4ZkYzTEcwZ2wyaWJTWWlDaTlhNmh2VTBUcHBqSnlJV1hoa0pUY01KbFByV3gxVnl0RVVHclgybDBKRHdSalcvNjU2cjBLVkIwMnhIUkt2bTJaS0kwM1RnbExJcG1WQ0sza0JLa0tOcEJOa0Z0OHJoYWZjQ0tPYjlKeC85dHBORmxRVGw3QjM5ckpsSldrUjE3UW5acVZwdEZlUEZPUm9abUZ6TT0iLCJNSUlGWWpDQ0JFcWdBd0lCQWdJUWQ3ME5iTnMyK1JycUlRL0U4RmpURFRBTkJna3Foa2lHOXcwQkFRc0ZBREJYTVFzd0NRWURWUVFHRXdKQ1JURVpNQmNHQTFVRUNoTVFSMnh2WW1Gc1UybG5iaUJ1ZGkxellURVFNQTRHQTFVRUN4TUhVbTl2ZENCRFFURWJNQmtHQTFVRUF4TVNSMnh2WW1Gc1UybG5iaUJTYjI5MElFTkJNQjRYRFRJd01EWXhPVEF3TURBME1sb1hEVEk0TURFeU9EQXdNREEwTWxvd1J6RUxNQWtHQTFVRUJoTUNWVk14SWpBZ0JnTlZCQW9UR1VkdmIyZHNaU0JVY25WemRDQlRaWEoyYVdObGN5Qk1URU14RkRBU0JnTlZCQU1UQzBkVVV5QlNiMjkwSUZJeE1JSUNJakFOQmdrcWhraUc5dzBCQVFFRkFBT0NBZzhBTUlJQ0NnS0NBZ0VBdGhFQ2l4N2pvWGViTzl5L2xENjNsYWRBUEtIOWd2bDlNZ2FDY2ZiMmpILzc2TnU4YWk2WGw2T01TL2tyOXJINXpvUWRzZm5GbDk3dnVmS2o2YndTaVY2bnFsS3IrQ01ueTZTeG5HUGIxNWwrOEFwZTYyaW05TVphUncxTkVEUGpUckVUbzhnWWJFdnMvQW1RMzUxa0tTVWpCNkcwMGowdVlPRFAwZ21IdTgxSThFM0N3bnFJaXJ1Nnoxa1oxcStQc0Fld25qSHhnc0hBM3k2bWJXd1pEclhZZmlZYVJRTTlzSG1rbENpdEQzOG01YWdJL3Bib1BHaVVVKzZET29nckZaWUpzdUI2akM1MTFwenJwMVprajVaUGFLNDlsOEtFajhDOFFNQUxYTDMyaDdNMWJLd1lVSCtFNEV6Tmt0TWc2VE84VXBtdk1yVXBzeVVxdEVqNWN1SEtaUGZtZ2hDTjZKM0Npb2o2T0dhSy9HUDVBZmw0L1h0Y2QvcDJoL3JzMzdFT2VaVlh0TDBtNzlZQjBlc1dDcnVPQzdYRnhZcFZxOU9zNnBGTEtjd1pwRElsVGlyeFpVVFFBczZxemttMDZwOThnN0JBZStkRHE2ZHNvNDk5aVlINlRLWC8xWTdEemt2Z3RkaXpqa1hQZHNEdFFDdjlVdyt3cDlVN0RiR0tvZ1BlTWEzTWQrcHZlejdXMzVFaUV1YSsrdGd5L0JCakZGRnkzbDNXRnBPOUtXZ3o3enBtN0FlS0p0OFQxMWRsZUNmZVhra1VBS0lBZjVxb0liYXBzWld3cGJrTkZoSGF4MnhJUEVEZ2ZnMWF6Vlk4MFpjRnVjdEw3VGxMbk1RLzBsVVRiaVN3MW5INjlNRzZ6TzBiOWY2QlFkZ0FtRDA2eUs1Nm1EY1lCWlVDQXdFQUFhT0NBVGd3Z2dFME1BNEdBMVVkRHdFQi93UUVBd0lCaGpBUEJnTlZIUk1CQWY4RUJUQURBUUgvTUIwR0ExVWREZ1FXQkJUa3J5c21jUm9yU0NlRkwxSm1MTy93aVJOeFBqQWZCZ05WSFNNRUdEQVdnQlJnZTJZYVJRMlh5b2xRTDMwRXpUU28vL3o5U3pCZ0JnZ3JCZ0VGQlFjQkFRUlVNRkl3SlFZSUt3WUJCUVVITUFHR0dXaDBkSEE2THk5dlkzTndMbkJyYVM1bmIyOW5MMmR6Y2pFd0tRWUlLd1lCQlFVSE1BS0dIV2gwZEhBNkx5OXdhMmt1WjI5dlp5OW5jM0l4TDJkemNqRXVZM0owTURJR0ExVWRId1FyTUNrd0o2QWxvQ09HSVdoMGRIQTZMeTlqY213dWNHdHBMbWR2YjJjdlozTnlNUzluYzNJeExtTnliREE3QmdOVkhTQUVOREF5TUFnR0JtZUJEQUVDQVRBSUJnWm5nUXdCQWdJd0RRWUxLd1lCQkFIV2VRSUZBd0l3RFFZTEt3WUJCQUhXZVFJRkF3TXdEUVlKS29aSWh2Y05BUUVMQlFBRGdnRUJBRFNrSHJFb285QzBkaGVtTVhvaDZkRlNQc2piZEJaQmlMZzlOUjN0NVArVDRWeGZxN3ZxZk0vYjVBM1JpMWZ5Sm05YnZoZEdhSlEzYjJ0NnlNQVlOL29sVWF6c2FMK3l5RW45V3ByS0FTT3NoSUFyQW95WmwrdEphb3gxMThmZXNzbVhuMWhJVnc0MW9lUWExdjF2ZzRGdjc0elBsNi9BaFNydzlVNXBDWkV0NFdpNHdTdHo2ZFRaL0NMQU54OExaaDFKN1FKVmoyZmhNdGZUSnI5dzR6MzBaMjA5Zk9VMGlPTXkrcWR1Qm1wdnZZdVI3aFpMNkR1cHN6Zm53MFNrZnRoczE4ZEc5WktiNTlVaHZtYVNHWlJWYk5RcHNnM0JabHZpZDBsSUtPMmQxeG96Y2xPemdqWFBZb3ZKSkl1bHR6a011MzRxUWI5U3oveWlscmJDZ2o4PSJdfQ.eyJub25jZSI6IlBvSEJNR1FXVTZMTHZuQ21tQUlqUkt4dTJ4ND0iLCJ0aW1lc3RhbXBNcyI6MTYzNzc1MTY1NTE2OSwiYXBrUGFja2FnZU5hbWUiOiJjb20uZmFzdGJhbmtpbmcuZGVidWciLCJhcGtEaWdlc3RTaGEyNTYiOiJsRHF1bDJxejdyd2owRDFJSzBkcTZwTnNaUmR0QW9BbUNNOVh5MGg2bkNjPSIsImN0c1Byb2ZpbGVNYXRjaCI6dHJ1ZSwiYXBrQ2VydGlmaWNhdGVEaWdlc3RTaGEyNTYiOlsiR3k3N1doNFRkR0ZXd3NoaS9VVXdDdUJIL0NBZ2V4VFFLdmJzbW5pWHFpTT0iXSwiYmFzaWNJbnRlZ3JpdHkiOnRydWUsImV2YWx1YXRpb25UeXBlIjoiQkFTSUMsSEFSRFdBUkVfQkFDS0VEIn0.ShOvWqQ_5i-T1ixx59sbk0-6LMo8oKiC5PfZCt9dVJrnfeap8JMQ9x8v19-Yh-M07y54BjQPXFGU-Y602uFc_V7TKHonDqjaEOsx6VfRwiQeZmtaO-Hhmlr2g-xRHFoDOnXy2wHYGfDkMbir50EraIyny3xfs-guIDMwg5qAzQaN999KRsrbHXX-a6wwoQ0qyUSVKGN57T_qOcXaq9X5bI1B3nD1m5Inu7TW0xrCb0sfUn8GDimAtnXELKf048S4iaXBObbgtiNyVQtTEfqHA8WdfhANIZWcV4XQDHbv69wcvrmUTDeZJienIfkmesfYnFDngW2NfR9A9m_Q5sorig");
	//		headers.put("x-login-nonce", "B6B667EB514890789F56F9B78BFA509AB41B673B");
	//		headers.put("x-login-timestamp", "1636960116339");
	//
	//		ValidatableResponse response = Utilities.postMethodAPI(headers, Myrequestbody, url);
	//
	//		System.out.println("Request :" + Myrequestbody);
	//		ExtentReporter.extentLogger("", "Request :" + Myrequestbody);
	//		String Resp = response.extract().body().asString();
	//		System.out.println("Response Body= " + Resp);
	//		ExtentReporter.extentLogger("", "Response Body= " + Resp);
	//		return response;
	//	}

	public static ValidatableResponse updateUserAPI(Object[][] data) throws Exception {
		try {
			String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.updateUserEndPoint);
			logger.info("Url :" + url);
			ExtentReporter.extentLogger("url", url);

			ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

			Random rand = new Random();

			HashMap<String, String> req_body = new HashMap<>();
			req_body.put("first_name", (String) data[0][0]);
			req_body.put("middle_name", (String) data[0][1]);
			req_body.put("last_name", (String) data[0][2]);
			req_body.put("mother_name", (String) data[0][3]);
			req_body.put("email", (String) data[0][4]);
			req_body.put("dob", (String) data[0][5]);
			req_body.put("merchant_reference_number", (String) data[0][6]);
			req_body.put("is_native_merchant", (String) data[0][7]);

			JSONObject Myrequestbody = new JSONObject();

			Myrequestbody.put("first_name", req_body.get("first_name"));
			Myrequestbody.put("middle_name", req_body.get("middle_name"));
			Myrequestbody.put("last_name", req_body.get("last_name"));
			Myrequestbody.put("mother_name", req_body.get("mother_name"));
			Myrequestbody.put("email", req_body.get("email"));
			Myrequestbody.put("dob", req_body.get("dob"));
			Myrequestbody.put("merchant_reference_number", req_body.get("merchant_reference_number"));
			Myrequestbody.put("is_native_merchant", req_body.get("is_native_merchant"));

			String req=String.valueOf(Myrequestbody);
			ExtentReporter.extentLogger("req_body", "Request :"+req);


			String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
			logger.info("UserToken: " + user_token);
			ExtentReporter.extentLogger("UserToken: ",user_token);

			HashMap<String, Object> headers = new HashMap<>();
			headers.put("x-request-id", rand.nextInt(1001));
			headers.put("Authorization", "Bearer " + user_token);

			String header=String.valueOf(headers);
			ExtentReporter.extentLogger("header","Headers :"+ header);


			ValidatableResponse response = Utilities.patchMethodAPI(headers, Myrequestbody, url);

			String Resp = response.extract().body().asString();
			logger.info("Response Body= " + Resp);
			ExtentReporter.extentLogger("", "Response Body= " + Resp);

			return response;

		}
		catch (Exception e) {
			String message="updateUserAPI";
			ExtentReporter.extentLoggerFail(message+" - Failed");	
		}
		return null;

	}

	public static ValidatableResponse loginAPI() throws Exception {
		try {
			String filePath = System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

			Random rand = new Random();

			String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.loginEndPoint);
			logger.info("Url :" + url);
			ExtentReporter.extentLogger("url", url);


			ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

			String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
			logger.info("user_token :" + user_token);
			ExtentReporter.extentLogger("user_token", user_token);


			HashMap<String, Object> headers = new HashMap<>();
			headers.put("x-request-id", rand.nextInt(1001));
			headers.put("Authorization", "Bearer " + user_token);

			String header=String.valueOf(headers);
			ExtentReporter.extentLogger("header","Headers :"+ header);


			ValidatableResponse response = Utilities.getMethodWithHeaderAPI(headers, url);

			String Resp = response.extract().body().asString();
			logger.info("Resp :" + Resp);
			ExtentReporter.extentLogger("", "Response Body= " + Resp);

			// fetch user_reference_number
			String user_reference_number = response.extract().body().jsonPath().get("data.user_reference_number");
			//		System.out.println("user_reference_number : " + user_reference_number);
			logger.info("user_reference_number : " + user_reference_number);
			ExtentReporter.extentLogger("user_reference_number ",user_reference_number);

			// fetch user_id
			Integer user_id = response.extract().body().jsonPath().get("data.user_id");
			logger.info("user_id : " + user_id);

			String User_id=String.valueOf(user_id);
			ExtentReporter.extentLogger("User_id", User_id);


			// Write Excel
			ExcelWriteData.excelWrite(filePath, "RegisterUser", user_reference_number, 1, 11);
			ExcelWriteData.excelWrite(filePath, "Txn_Initiate", user_reference_number, 1, 1);

			ExcelWriteData.IntegerExcelWrite(filePath, "RegisterUser", user_id, 1, 12);

			return response;
		}
		catch (Exception e) {
			String message="loginAPI";
			ExtentReporter.extentLoggerFail(message+" - Failed");	
		}
		return null;

	}



	public static ValidatableResponse RegisterUserAPI(Object[][] data) throws Exception {

		try {

			String filePath = System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

			String url = RingPay_BaseURL.bigDataPythonURL.concat(RingPay_Endpoints.registerUserEndPoint);
			logger.info("Url :" + url);
			ExtentReporter.extentLogger("url", url);

			ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();


			String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
			System.out.println("UserToken: " + user_token);
			logger.info("UserToken: " + user_token);
			ExtentReporter.extentLogger("user_token", user_token);


			Random rand = new Random();

			HashMap<String, String> req_body = new HashMap<>();
			// System.out.println((String) data[0][3]);
			req_body.put("first_name", (String) data[0][0]);
			req_body.put("last_name", (String) data[0][1]);
			req_body.put("mobile_number", (String) data[0][2]);
			req_body.put("email", (String) data[0][3]);
			req_body.put("os_info", (String) data[0][4]);
			req_body.put("device_model", (String) data[0][5]);
			req_body.put("device_brand", (String) data[0][6]);
			req_body.put("imei", (String) data[0][7]);
			req_body.put("android_id", (String) data[0][8]);
			req_body.put("advertising_id", (String) data[0][9]);
			req_body.put("user_reference_number", (String) data[0][10]);
			req_body.put("user_id", (String) data[0][11]);
			req_body.put("request_id", (String) data[0][12]);
			req_body.put("approximate_contacts", (String) data[0][13]);
			req_body.put("approximate_sms", (String) data[0][14]);

			JSONObject Myrequestbody = new JSONObject();

			Myrequestbody.put("first_name", req_body.get("first_name"));
			Myrequestbody.put("last_name", req_body.get("last_name"));
			Myrequestbody.put("mobile_number", req_body.get("mobile_number"));
			Myrequestbody.put("email", req_body.get("email"));
			Myrequestbody.put("os_info", req_body.get("os_info"));
			Myrequestbody.put("device_model", req_body.get("device_model"));
			Myrequestbody.put("device_brand", req_body.get("device_brand"));
			Myrequestbody.put("imei", req_body.get("imei"));
			Myrequestbody.put("android_id", req_body.get("android_id"));
			Myrequestbody.put("advertising_id", req_body.get("advertising_id"));
			Myrequestbody.put("user_reference_number", req_body.get("user_reference_number"));
			Myrequestbody.put("user_id", req_body.get("user_id"));
			Myrequestbody.put("request_id", req_body.get("request_id"));
			Myrequestbody.put("approximate_contacts", req_body.get("approximate_contacts"));
			Myrequestbody.put("approximate_sms", req_body.get("approximate_sms"));


			String req=String.valueOf(Myrequestbody);
			ExtentReporter.extentLogger("req_body", "Request :"+req);

			HashMap<String, Object> headers = new HashMap<>();
			headers.put("x-request-id", rand.nextInt(1001));
			headers.put("X-Client-Version", "1.1.7");
			headers.put("Authorization", "client_id=MCUMMlBD7gs98HlHL3Py9Syk3xRsvvU5,token=" + user_token);

			String header=String.valueOf(headers);
			ExtentReporter.extentLogger("header","Headers :"+ header);

			ValidatableResponse response = Utilities.postMethodAPI(headers, Myrequestbody, url);

			logger.info("Request :" + Myrequestbody);
			ExtentReporter.extentLogger("", "Request :" + Myrequestbody);
			String Resp = response.extract().body().asString();
			logger.info("Response Body= " + Resp);
			ExtentReporter.extentLogger("", "Response Body= " + Resp);

			// fetching data
			String global_device_id = response.extract().body().jsonPath().get("data.global_device_id");
			logger.info("Global_Device_id: " + global_device_id);
			ExtentReporter.extentLogger("global_device_id", global_device_id);


			// Data to User_Onboarding
			ExcelWriteData.excelWrite(filePath, "UserOnboarding", global_device_id, 1, 3);

			// Data to Create_Bnpl_transaction
			ExcelWriteData.excelWrite(filePath, "Create_Bnpl_Transaction", global_device_id, 1, 5);

			// Data to Txn_Initiated
			ExcelWriteData.excelWrite(filePath, "Txn_Initiate", global_device_id, 1, 9);

			// Data to Txn_Initiated
			ExcelWriteData.excelWrite(filePath, "Current_Spend", global_device_id, 1, 3);

			return response;
			
		}
		catch (Exception e) {
			String message="RegisterUserAPI";
			ExtentReporter.extentLoggerFail(message+" - Failed");	
		}
		return null;

	}



	public static ValidatableResponse Get_User_DetailsAPI() throws Exception {

		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.getDetailsEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
		System.out.println("UserToken: " + user_token);

		Random rand = new Random();

		HashMap<String, Object> headers = new HashMap<>();
		headers.put("x-request-id", rand.nextInt(1001));
		headers.put("Authorization", "Bearer " + user_token);
		// headers.put("Authorization",
		// "client_id=MCUMMlBD7gs98HlHL3Py9Syk3xRsvvU5,token="+UserToken);

		ValidatableResponse response = Utilities.getMethodWithHeaderAPI(headers, url);

		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body= " + Resp);

		return response;
	}

	public static ValidatableResponse User_OnboardingAPI(Object[][] data) throws Exception {

		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.userOnbordingEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
		System.out.println("UserToken: " + user_token);

		// Random rand = new Random();

		HashMap<String, String> req_body = new HashMap<>();
		req_body.put("advertising_id", (String) data[0][0]);
		req_body.put("android_id", (String) data[0][1]);
		req_body.put("global_device_id", (String) data[0][2]);
		req_body.put("imei_number", (String) data[0][3]);
		req_body.put("latitude", (String) data[0][4]);
		req_body.put("longitude", (String) data[0][5]);

		JSONObject Myrequestbody = new JSONObject();

		Myrequestbody.put("advertising_id", req_body.get("advertising_id"));
		Myrequestbody.put("android_id", req_body.get("android_id"));
		Myrequestbody.put("global_device_id", req_body.get("global_device_id"));
		Myrequestbody.put("imei_number", req_body.get("imei_number"));
		Myrequestbody.put("latitude", req_body.get("latitude"));
		Myrequestbody.put("longitude", req_body.get("longitude"));

		HashMap<String, Object> headers = new HashMap<>();
		// headers.put("x-request-id", rand.nextInt(1001));
		headers.put("Authorization", "Bearer " + user_token);

		ValidatableResponse response = Utilities.postMethodAPI(headers, Myrequestbody, url);

		System.out.println("Request :" + Myrequestbody);
		ExtentReporter.extentLogger("", "Request :" + Myrequestbody);
		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body= " + Resp);

		//		String line_application_reference_number = userTokenResponse.extract().body().jsonPath()
		//				.get("data.details.line_application_reference_number");
		//		System.out.println("line_application_reference_number: " + line_application_reference_number);





		return response;

	}

	public static ValidatableResponse Create_Bnpl_TransactionAPI(Object[][] data) throws Exception {

		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.createBnplTransactionEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
		System.out.println("UserToken: " + user_token);

		// Random rand = new Random();

		HashMap<String, String> req_body = new HashMap<>();
		req_body.put("source", (String) data[0][0]);
		req_body.put("advertising_id", (String) data[0][1]);
		req_body.put("latitude", (String) data[0][2]);
		req_body.put("longitude", (String) data[0][3]);
		req_body.put("global_device_id", (String) data[0][4]);
		req_body.put("imei_number", (String) data[0][5]);
		req_body.put("android_id", (String) data[0][6]);
		req_body.put("product_name", (String) data[0][7]);

		JSONObject Myrequestbody = new JSONObject();

		Myrequestbody.put("source", req_body.get("source"));
		Myrequestbody.put("advertising_id", req_body.get("advertising_id"));
		Myrequestbody.put("latitude", req_body.get("latitude"));
		Myrequestbody.put("longitude", req_body.get("longitude"));
		Myrequestbody.put("global_device_id", req_body.get("global_device_id"));
		Myrequestbody.put("imei_number", req_body.get("imei_number"));
		Myrequestbody.put("android_id", req_body.get("android_id"));
		Myrequestbody.put("product_name", req_body.get("product_name"));

		HashMap<String, Object> headers = new HashMap<>();
		// headers.put("x-request-id", rand.nextInt(1001));
		headers.put("Authorization", "Bearer " + user_token);

		ValidatableResponse response = Utilities.postMethodAPI(headers, Myrequestbody, url);

		System.out.println("Request :" + Myrequestbody);
		ExtentReporter.extentLogger("", "Request :" + Myrequestbody);
		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body= " + Resp);

		return response;

	}

	public static ValidatableResponse UpdateUserStatusAPI(Object[][] data) throws Exception {

		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.updateUserStatusEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
		System.out.println("UserToken: " + user_token);

		Random rand = new Random();

		HashMap<String, String> req_body = new HashMap<>();
		req_body.put("Gender", (String) data[0][0]);
		req_body.put("mother_name", (String) data[0][1]);
		req_body.put("name_verification_status", (String) data[0][2]);
		req_body.put("dob_verification_status", (String) data[0][3]);
		req_body.put("pan_verification_status", (String) data[0][4]);
		req_body.put("mobile_verification_status", (String) data[0][5]);
		req_body.put("gender_verification_status", (String) data[0][6]);
		req_body.put("yob_verification_status", (String) data[0][7]);
		req_body.put("onboarding_stage", (String) data[0][8]);

		JSONObject Myrequestbody = new JSONObject();

		Myrequestbody.put("Gender", req_body.get("Gender"));
		Myrequestbody.put("mother_name", req_body.get("mother_name"));
		Myrequestbody.put("name_verification_status", req_body.get("name_verification_status"));
		Myrequestbody.put("dob_verification_status", req_body.get("dob_verification_status"));
		Myrequestbody.put("pan_verification_status", req_body.get("pan_verification_status"));
		Myrequestbody.put("mobile_verification_status", req_body.get("mobile_verification_status"));
		Myrequestbody.put("gender_verification_status", req_body.get("gender_verification_status"));
		Myrequestbody.put("yob_verification_status", req_body.get("yob_verification_status"));
		Myrequestbody.put("onboarding_stage", req_body.get("onboarding_stage"));

		HashMap<String, Object> headers = new HashMap<>();
		headers.put("x-request-id", rand.nextInt(1001));
		headers.put("Authorization", "Bearer " + user_token);

		ValidatableResponse response = Utilities.patchMethodAPI(headers, Myrequestbody, url);

		System.out.println("Request Url -->" + url);
		ExtentReporter.extentLogger("", "Request Url -->" + url);
		System.out.println("Request :" + Myrequestbody);
		ExtentReporter.extentLogger("", "Request :" + Myrequestbody);
		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body= " + Resp);

		return response;

	}

	public static ValidatableResponse CheckApplicationEligibilityAPI() throws Exception {

		String url = RingPay_BaseURL.txnGatewayURL.concat(RingPay_Endpoints.checkApplicationEligibilityEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
		System.out.println("UserToken: " + user_token);

		// Create_Bnpl_Transaction for Application_Token
		ValidatableResponse bnplResponse = BasicDetailScreen_Create_Bnpl_Transaction.getApplicationToken_Positive();

		String applicationToken = bnplResponse.extract().body().jsonPath().get("data.application_token");
		System.out.println("ApplicationToken: " + applicationToken);

		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Bearer " + applicationToken);
		headers.put("X-Client-Version", "1.1.7");

		ValidatableResponse response = Utilities.postMethodWithHeadersAPI(headers, url);

		// System.out.println("Request Url -->" + url);
		// ExtentReporter.extentLogger("", "Request Url -->" + url);
		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body= " + Resp);

		return response;

	}

	public static ValidatableResponse CheckApplicationEligibilityAfterAddAddressAPI() throws Exception {

		String url = RingPay_BaseURL.txnGatewayURL.concat(RingPay_Endpoints.checkApplicationEligibilityEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
		System.out.println("UserToken: " + user_token);

		// Create_Bnpl_Transaction for Application_Token
		ValidatableResponse bnplResponse = BasicDetailScreen_Create_Bnpl_Transaction.getApplicationToken_Positive();

		String applicationToken = bnplResponse.extract().body().jsonPath().get("data.application_token");
		System.out.println("ApplicationToken: " + applicationToken);

		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Bearer " + applicationToken);
		headers.put("X-Client-Version", "1.1.7");

		ValidatableResponse response = Utilities.postMethodWithHeadersAPI(headers, url);

		// System.out.println("Request Url -->" + url);
		// ExtentReporter.extentLogger("", "Request Url -->" + url);
		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body= " + Resp);

		return response;

	}

	// ========================= PreConditon for Check Application Eligibility
	// ===============

	public static ValidatableResponse AddAddressAPI(Object[][] data) throws Exception {
		try {

			String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.addAddressEndPoint);
			System.out.println("Url :" + url);

			ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

			String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
			System.out.println("UserToken: " + user_token);

			// Random rand = new Random();

			HashMap<String, String> req_body = new HashMap<>();
			req_body.put("room_number", (String) data[0][0]);
			req_body.put("line_1", (String) data[0][1]);
			req_body.put("line_2", (String) data[0][2]);
			req_body.put("landmark", (String) data[0][3]);
			req_body.put("pincode", (String) data[0][4]);
			req_body.put("label", (String) data[0][5]);
			req_body.put("tag", (String) data[0][6]);
			req_body.put("source", (String) data[0][7]);
			req_body.put("residence_type", (String) data[0][8]);
			req_body.put("product_name", (String) data[0][9]);

			JSONObject Myrequestbody = new JSONObject();

			Myrequestbody.put("room_number", req_body.get("room_number"));
			Myrequestbody.put("line_1", req_body.get("line_1"));
			Myrequestbody.put("line_2", req_body.get("line_2"));
			Myrequestbody.put("landmark", req_body.get("landmark"));
			Myrequestbody.put("pincode", req_body.get("pincode"));
			Myrequestbody.put("label", req_body.get("label"));
			Myrequestbody.put("tag", req_body.get("tag"));
			Myrequestbody.put("source", req_body.get("source"));
			Myrequestbody.put("residence_type", req_body.get("residence_type"));
			Myrequestbody.put("product_name", req_body.get("product_name"));

			HashMap<String, Object> headers = new HashMap<>();
			// headers.put("x-request-id", rand.nextInt(1001));
			headers.put("Authorization", "Bearer " + user_token);

			ValidatableResponse response = Utilities.postMethodAPI(headers, Myrequestbody, url);

			String Resp = response.extract().body().asString();
			System.out.println("Response Body= " + Resp);
			ExtentReporter.extentLogger("", "Response Body= " + Resp);

			//		ExtentReporter.extentLogger("", message);

			return response;
		}

		catch (Exception e) {
			String message="AddAddressAPI";
			ExtentReporter.extentLoggerFail(message+" - Failed");	
		}
		return null;


	}

	// ========================= OFFER_DETAILS_SCREEN
	// ===================================

	public static ValidatableResponse getOfferAPI() throws Exception {

		// String filePath=
		// System.getProperty("user.dir")+"\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

		String url = RingPay_BaseURL.txnGatewayURL.concat(RingPay_Endpoints.getOffersEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse bnplResponse = BasicDetailScreen_Create_Bnpl_Transaction.getApplicationToken_Positive();

		// ValidatableResponse bnplResponse =
		// Utilities.CheckApplicationEligibilityAfterAddAddressAPI();

		String applicationToken = bnplResponse.extract().body().jsonPath().get("data.application_token");
		System.out.println("ApplicationToken: " + applicationToken);

		Random rand = new Random();

		// HashMap<String, String> req_body = new HashMap<>();
		// // System.out.println((String) data[0][3]);
		// req_body.put("gender", (String) data[0][0]);
		// req_body.put("encrypted_name", (String) data[0][1]);
		//
		//
		// JSONObject Myrequestbody = new JSONObject();
		//
		// Myrequestbody.put("gender", req_body.get("gender"));
		// Myrequestbody.put("encrypted_name", req_body.get("encrypted_name"));

		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Bearer " + applicationToken);

		ValidatableResponse response = Utilities.getMethodWithHeaderAPI(headers, url);

		// System.out.println("Request Url -->" + url);
		// ExtentReporter.extentLogger("", "Request Url -->" + url);
		// System.out.println("Request :"+Myrequestbody);
		// ExtentReporter.extentLogger("", "Request :"+Myrequestbody);
		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body_getoffer= " + Resp);

		return response;

	}

	public static ValidatableResponse User_ConcentAPI() throws Exception {

		String url = RingPay_BaseURL.txnGatewayURL.concat(RingPay_Endpoints.userConcentEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse bnplResponse = BasicDetailScreen_Create_Bnpl_Transaction.getApplicationToken_Positive();

		String applicationToken = bnplResponse.extract().body().jsonPath().get("data.application_token");
		System.out.println("ApplicationToken: " + applicationToken);

		Random rand = new Random();

		// HashMap<String, String> req_body = new HashMap<>();
		// // System.out.println((String) data[0][3]);
		// req_body.put("gender", (String) data[0][0]);
		// req_body.put("encrypted_name", (String) data[0][1]);
		//
		//
		// JSONObject Myrequestbody = new JSONObject();
		//
		// Myrequestbody.put("gender", req_body.get("gender"));
		// Myrequestbody.put("encrypted_name", req_body.get("encrypted_name"));

		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Bearer " + applicationToken);

		ValidatableResponse response = Utilities.patchMethodWithHeaderAPI(headers, url);

		// System.out.println("Request Url -->" + url);
		// ExtentReporter.extentLogger("", "Request Url -->" + url);
		// System.out.println("Request :"+Myrequestbody);
		// ExtentReporter.extentLogger("", "Request :"+Myrequestbody);
		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body_getoffer= " + Resp);

		return response;

	}

	public static ValidatableResponse bnplLinesAPI() throws Exception {

		// String filePath=
		// System.getProperty("user.dir")+"\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.bnplLinesEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
		System.out.println("UserToken: " + user_token);

		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Bearer " + user_token);

		ValidatableResponse response = Utilities.postMethodWithHeadersAPI(headers, url);

		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body_getoffer= " + Resp);

		return response;

	}

	// Random Product Value

	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	public static ValidatableResponse PaymentOptionAPI(Object[][] data) throws Exception {

		String filePath = System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.paymentOptionEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
		System.out.println("UserToken: " + user_token);

		// ValidatableResponse
		// updateUserResponse=RegisterUser_UpdateUser.updateUser_Positive();
		// System.out.println("updateUser_Positive :"+updateUserResponse);
		//
		// String userToken=
		// updateUserResponse.extract().body().jsonPath().get("data.user_token");
		// System.out.println("UserToken : "+userToken);

		// Random rand = new Random();

		int nProductValue = getRandomNumber(100, 200);
		String sProductValue = String.valueOf(nProductValue);
		System.out.println("productValue: " + sProductValue);

		// String productValue=sProductValue;
		// System.out.println("productValue: "+ productValue);

		// Data to User_Onboarding
		ExcelWriteData.excelWrite(filePath, "PaymentOption", sProductValue, 1, 2);
		ExcelWriteData.excelWrite(filePath, "PaymentOption", sProductValue, 2, 2);

		// Data to Txn_Initiated
		ExcelWriteData.excelWrite(filePath, "Txn_Initiate", sProductValue, 1, 2);

		HashMap<String, String> req_body = new HashMap<>();
		// System.out.println((String) data[0][3]);
		req_body.put("reason", (String) data[0][0]);
		req_body.put("actual_amount", (String) data[0][1]);
		req_body.put("qr_code", (String) data[0][2]);

		// req_body.put("product_name", (String) data[0][9]);

		JSONObject Myrequestbody = new JSONObject();

		Myrequestbody.put("reason", req_body.get("reason"));
		Myrequestbody.put("actual_amount", req_body.get("actual_amount"));
		Myrequestbody.put("qr_code", req_body.get("qr_code"));

		// Myrequestbody.put("otp", req_body.get("otp"));
		// Myrequestbody.put("client_id", req_body.get("client_id"));

		HashMap<String, Object> headers = new HashMap<>();
		// headers.put("x-request-id", rand.nextInt(1001));
		// headers.put("X-Client-Version", "1.1.7");
		headers.put("Authorization", "Bearer " + user_token);

		ValidatableResponse response = Utilities.postMethodAPI(headers, Myrequestbody, url);

		System.out.println("Request :" + Myrequestbody);
		ExtentReporter.extentLogger("", "Request :" + Myrequestbody);
		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body= " + Resp);

		// fetching data
		String upi_handle_reference_number = response.extract().body().jsonPath()
				.get("data.upi_details.upi_handle_reference_number");
		System.out.println("upi_handle_reference_number: " + upi_handle_reference_number);

		// Data to Txn_Initiated
		ExcelWriteData.excelWrite(filePath, "Txn_Initiate", upi_handle_reference_number, 1, 5);

		return response;

	}


	// Random merchant Order

	public static long merchant_order_id(long min, long max) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		return random.nextLong(min, max);
	}

	public static String sku_description(int len) {
		String chars = "ABCDEFGHIJKLMNOPQRSTU";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}

	public static ValidatableResponse TransactionInitiateAPI(Object[][] data) throws Exception {

		String filePath = System.getProperty("user.dir")+ "\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.txnInitiateEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
		System.out.println("UserToken: " + user_token);

		// ValidatableResponse
		// updateUserResponse=RegisterUser_UpdateUser.updateUser_Positive();
		// System.out.println("updateUser_Positive :"+updateUserResponse);
		//
		// String userToken=
		// updateUserResponse.extract().body().jsonPath().get("data.user_token");
		// System.out.println("UserToken : "+userToken);

		// Random rand = new Random();

		long id = merchant_order_id(59999000001L, 59999999999L);
		String sMerchantOrder = String.valueOf(id);
		System.out.println("MerchantOrder: " + sMerchantOrder);

		String sku_Description = sku_description(6);
		System.out.println("sku_Description :" + sku_Description);

		// fetching data
		// String merchant_order=sMerchantOrder;
		// System.out.println("merchant_order: "+ merchant_order);

		// Data to Txn_Initiated
		ExcelWriteData.excelWrite(filePath, "Txn_Initiate", sMerchantOrder, 1, 4);

		// String productValue=sProductValue;
		// System.out.println("productValue: "+ productValue);

		// Data to Txn_Initiated
		ExcelWriteData.excelWrite(filePath, "Txn_Initiate", sku_Description, 1, 6);

		HashMap<String, String> req_body = new HashMap<>();
		// System.out.println((String) data[0][3]);
		req_body.put("user_reference_number", (String) data[0][0]);
		req_body.put("product_value", (String) data[0][1]);
		req_body.put("transaction_type", (String) data[0][2]);
		req_body.put("merchant_order_id", (String) data[0][3]);
		req_body.put("upi_handle_reference_number", (String) data[0][4]);
		req_body.put("sku_description", (String) data[0][5]);
		req_body.put("latitude", (String) data[0][6]);
		req_body.put("longitude", (String) data[0][7]);
		req_body.put("global_device_id", (String) data[0][8]);

		JSONObject Myrequestbody = new JSONObject();

		Myrequestbody.put("user_reference_number", req_body.get("user_reference_number"));
		Myrequestbody.put("product_value", req_body.get("product_value"));
		Myrequestbody.put("transaction_type", req_body.get("transaction_type"));
		Myrequestbody.put("merchant_order_id", req_body.get("merchant_order_id"));
		Myrequestbody.put("upi_handle_reference_number", req_body.get("upi_handle_reference_number"));
		Myrequestbody.put("sku_description", req_body.get("sku_description"));
		Myrequestbody.put("latitude", req_body.get("latitude"));
		Myrequestbody.put("longitude", req_body.get("longitude"));
		Myrequestbody.put("global_device_id", req_body.get("global_device_id"));

		// Myrequestbody.put("otp", req_body.get("otp"));
		// Myrequestbody.put("client_id", req_body.get("client_id"));

		HashMap<String, Object> headers = new HashMap<>();
		// headers.put("x-request-id", rand.nextInt(1001));
		// headers.put("X-Client-Version", "1.1.7");
		headers.put("Authorization", "Bearer "+user_token);

		ValidatableResponse response = Utilities.postMethodAPI(headers, Myrequestbody, url);

		System.out.println("Request :" + Myrequestbody);
		ExtentReporter.extentLogger("", "Request :" + Myrequestbody);
		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body= " + Resp);

		//		// fetching data
		//		String upi_handle_reference_number = response.extract().body().jsonPath()
		//				.get("data.upi_details.upi_handle_reference_number");
		//		System.out.println("upi_handle_reference_number: " + upi_handle_reference_number);


		return response;

	}

	public static ValidatableResponse TransactionCompleteAPI(Object[][] data) throws Exception {

		// String filePath=
		// System.getProperty("user.dir")+"\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.txnCompleteEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
		System.out.println("UserToken: " + user_token);

		HashMap<String, String> req_body = new HashMap<>();
		req_body.put("transaction_reference_number", (String) data[0][0]);
		req_body.put("transaction_pin_hash", (String) data[0][1]);

		JSONObject Myrequestbody = new JSONObject();

		Myrequestbody.put("transaction_reference_number", req_body.get("transaction_reference_number"));
		Myrequestbody.put("transaction_pin_hash", req_body.get("transaction_pin_hash"));

		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Bearer " + user_token);


		ValidatableResponse response = Utilities.postMethodAPI(headers, Myrequestbody, url);

		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body_getoffer= " + Resp);

		return response;

	}


	public static ValidatableResponse CurrentSpendsAPI(Object[][] data) throws Exception {

		// String filePath=
		// System.getProperty("user.dir")+"\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.currentSpendsEndPoint);
		System.out.println("Url :" + url);

		ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
		System.out.println("UserToken: " + user_token);

		HashMap<String, String> req_body = new HashMap<>();
		req_body.put("advertising_id", (String) data[0][0]);
		req_body.put("android_id", (String) data[0][1]);
		req_body.put("global_device_id", (String) data[0][2]);
		req_body.put("imei_number", (String) data[0][3]);
		req_body.put("latitude", (String) data[0][4]);
		req_body.put("longitude", (String) data[0][5]);
		req_body.put("client_id", (String) data[0][6]);



		JSONObject Myrequestbody = new JSONObject();


		Myrequestbody.put("advertising_id", req_body.get("advertising_id"));
		Myrequestbody.put("android_id", req_body.get("android_id"));
		Myrequestbody.put("global_device_id", req_body.get("global_device_id"));
		Myrequestbody.put("imei_number", req_body.get("imei_number"));
		Myrequestbody.put("latitude", req_body.get("latitude"));
		Myrequestbody.put("longitude", req_body.get("longitude"));
		Myrequestbody.put("client_id", req_body.get("client_id"));



		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Bearer " + user_token);

		ValidatableResponse response = Utilities.postMethodAPI(headers, Myrequestbody, url);

		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body_getoffer= " + Resp);

		return response;

	}



	public static ValidatableResponse ValidateAPI(Object[][] data) throws Exception {

		String filePath=System.getProperty("user.dir")+"\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

		String url = RingPay_BaseURL.externalURL.concat(RingPay_Endpoints.validateEndPoint);
		System.out.println("Url :" + url);


		ValidatableResponse mockUserResponse= RegisterUser_Mock_User.mock_User_Positive();

		// fetching Mobileno
		String mobileNumber = mockUserResponse.extract().body().jsonPath().get("data.data.mobile_number");
		System.out.println("MobileNumber : " + mobileNumber);

		String bene_account_no="33557701"+mobileNumber;

		// Data to bene_account_no
		ExcelWriteData.excelWrite(filePath, "Validate", bene_account_no, 1, 2);


		long id = merchant_order_id(80000000001L, 99999999999L);
		String stransfer_unique_no = String.valueOf(id);
		System.out.println("MerchantOrder: " + stransfer_unique_no);

		String transfer_unique_no="ICIC"+stransfer_unique_no;


		// Data to transfer_unique_no
		ExcelWriteData.excelWrite(filePath, "Validate", transfer_unique_no, 1, 6);


		HashMap<String, String> req_body = new HashMap<>();

		req_body.put("customer_code", (String) data[0][0]);
		req_body.put("bene_account_no", (String) data[0][1]);
		req_body.put("bene_account_ifsc", (String) data[0][2]);
		req_body.put("bene_full_name", (String) data[0][3]);
		req_body.put("transfer_type", (String) data[0][4]);
		req_body.put("transfer_unique_no", (String) data[0][5]);
		req_body.put("transfer_timestamp", (String) data[0][6]);
		req_body.put("transfer_ccy", (String) data[0][7]);
		req_body.put("transfer_amt", (String) data[0][8]);
		req_body.put("rmtr_account_no", (String) data[0][9]);
		req_body.put("rmtr_account_ifsc", (String) data[0][10]);
		req_body.put("rmtr_full_name", (String) data[0][11]);
		req_body.put("rmtr_address", (String) data[0][12]);
		req_body.put("rmtr_to_bene_note", (String) data[0][13]);
		req_body.put("attempt_no", (String) data[0][14]);



		JSONObject Myrequestbody = new JSONObject();


		Myrequestbody.put("customer_code", req_body.get("customer_code"));
		Myrequestbody.put("bene_account_no", req_body.get("bene_account_no"));
		Myrequestbody.put("bene_account_ifsc", req_body.get("bene_account_ifsc"));
		Myrequestbody.put("bene_full_name", req_body.get("bene_full_name"));
		Myrequestbody.put("transfer_type", req_body.get("transfer_type"));
		Myrequestbody.put("transfer_unique_no", req_body.get("transfer_unique_no"));
		Myrequestbody.put("transfer_timestamp", "2022-08-25 15:08:24");
		Myrequestbody.put("transfer_ccy", req_body.get("transfer_ccy"));
		Myrequestbody.put("transfer_amt", req_body.get("transfer_amt"));
		Myrequestbody.put("rmtr_account_no", req_body.get("rmtr_account_no"));
		Myrequestbody.put("rmtr_account_ifsc", req_body.get("rmtr_account_ifsc"));
		Myrequestbody.put("rmtr_full_name", req_body.get("rmtr_full_name"));
		Myrequestbody.put("rmtr_address", req_body.get("rmtr_address"));
		Myrequestbody.put("rmtr_to_bene_note", req_body.get("rmtr_to_bene_note"));
		Myrequestbody.put("attempt_no", req_body.get("attempt_no"));



		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Basic dHNsRzFhTVUxMkhsQXVCWThQaVM2dmtWdHh5V0tEazU6MjZFMWZvUFhMVXRiaUZ3a2l6bjA5TG5uQ0w5WlFTNWY=");

		//		System.out.println("\"validate\"");

		String requestValidate="{"+"\"validate\""+":"+Myrequestbody+"}";
		System.out.println("request :"+requestValidate);


		ValidatableResponse response = Utilities.postMethodAuthAPI(headers, requestValidate, url);


		System.out.println("Request :" + requestValidate);
		ExtentReporter.extentLogger("", "Request :" + requestValidate);
		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body " + Resp);

		return response;

	}

	public static ValidatableResponse NotifyAPI(Object[][] data) throws Exception {

		String filePath=System.getProperty("user.dir")+"\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

		String url = RingPay_BaseURL.externalURL.concat(RingPay_Endpoints.notifyEndPoint);
		System.out.println("Url :" + url);


		ValidatableResponse mockUserResponse= RegisterUser_Mock_User.mock_User_Positive();

		// fetching Mobileno
		String mobileNumber = mockUserResponse.extract().body().jsonPath().get("data.data.mobile_number");
		System.out.println("MobileNumber : " + mobileNumber);

		String bene_account_no="33557701"+mobileNumber;

		// Data to bene_account_no
		ExcelWriteData.excelWrite(filePath, "Notify", bene_account_no, 1, 2);


		long id = merchant_order_id(80000000001L, 99999999999L);
		String stransfer_unique_no = String.valueOf(id);
		System.out.println("MerchantOrder: " + stransfer_unique_no);

		String transfer_unique_no="ICIC"+stransfer_unique_no;


		// Data to transfer_unique_no
		ExcelWriteData.excelWrite(filePath, "Notify", transfer_unique_no, 1, 6);


		HashMap<String, String> req_body = new HashMap<>();

		req_body.put("customer_code", (String) data[0][0]);
		req_body.put("bene_account_no", (String) data[0][1]);
		req_body.put("bene_account_ifsc", (String) data[0][2]);
		req_body.put("bene_full_name", (String) data[0][3]);
		req_body.put("transfer_type", (String) data[0][4]);
		req_body.put("transfer_unique_no", (String) data[0][5]);
		req_body.put("transfer_timestamp", (String) data[0][6]);
		req_body.put("transfer_ccy", (String) data[0][7]);
		req_body.put("transfer_amt", (String) data[0][8]);
		req_body.put("rmtr_account_no", (String) data[0][9]);
		req_body.put("rmtr_account_ifsc", (String) data[0][10]);
		req_body.put("rmtr_account_type", (String) data[0][11]);
		req_body.put("rmtr_full_name", (String) data[0][12]);
		req_body.put("rmtr_to_bene_note", (String) data[0][13]);
		req_body.put("attempt_no", (String) data[0][14]);
		req_body.put("status", (String) data[0][15]);
		req_body.put("credit_acct_no", (String) data[0][16]);
		req_body.put("credited_at", (String) data[0][17]);


		JSONObject Myrequestbody = new JSONObject();


		Myrequestbody.put("customer_code", req_body.get("customer_code"));
		Myrequestbody.put("bene_account_no", req_body.get("bene_account_no"));
		Myrequestbody.put("bene_account_ifsc", req_body.get("bene_account_ifsc"));
		Myrequestbody.put("bene_full_name", req_body.get("bene_full_name"));
		Myrequestbody.put("transfer_type", req_body.get("transfer_type"));
		Myrequestbody.put("transfer_unique_no", req_body.get("transfer_unique_no"));
		Myrequestbody.put("transfer_timestamp", "2022-08-25 15:08:24");
		Myrequestbody.put("transfer_ccy", req_body.get("transfer_ccy"));
		Myrequestbody.put("transfer_amt", req_body.get("transfer_amt"));
		Myrequestbody.put("rmtr_account_no", req_body.get("rmtr_account_no"));
		Myrequestbody.put("rmtr_account_ifsc", req_body.get("rmtr_account_ifsc"));
		Myrequestbody.put("rmtr_account_type", req_body.get("rmtr_account_type"));
		Myrequestbody.put("rmtr_full_name", req_body.get("rmtr_full_name"));
		Myrequestbody.put("rmtr_to_bene_note", req_body.get("rmtr_to_bene_note"));
		Myrequestbody.put("attempt_no", req_body.get("attempt_no"));
		Myrequestbody.put("status", req_body.get("status"));
		Myrequestbody.put("credit_acct_no", req_body.get("credit_acct_no"));
		Myrequestbody.put("credited_at", "2022-07-14 18:30:25");



		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Basic dHNsRzFhTVUxMkhsQXVCWThQaVM2dmtWdHh5V0tEazU6MjZFMWZvUFhMVXRiaUZ3a2l6bjA5TG5uQ0w5WlFTNWY=");

		//		System.out.println("\"validate\"");

		String requestValidate="{"+"\"notify\""+":"+Myrequestbody+"}";
		System.out.println("request :"+requestValidate);


		ValidatableResponse response = Utilities.postMethodAuthAPI(headers, requestValidate, url);


		System.out.println("Request :" + requestValidate);
		ExtentReporter.extentLogger("", "Request :" + requestValidate);
		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body " + Resp);

		return response;

	}

	public static ValidatableResponse GetSettlementAPI() throws Exception {

		String filePath = System.getProperty("user.dir")+ "\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";


		ValidatableResponse userTokenResponse = RegisterUser_UserAuthenticate.userToken_Positive();

		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");
		System.out.println("UserToken: " + user_token);

		Thread.sleep(3000);

		ValidatableResponse Response = Bnpl_Txn_Transaction_Initiate.transactionInitiate_Positive();

		System.out.println("gggggggggggg ====="+Response);

		//		String transaction_reference_number = Response.extract().body().jsonPath().get("data.transaction.transaction_reference_number");
		//		System.out.println("transaction_reference_number: " + transaction_reference_number);

		String url = RingPay_BaseURL.userGatewayURL.concat(RingPay_Endpoints.getSettlementEndPoint+Response+"/settlement-status");
		System.out.println("Url :" + url);


		//		Random rand = new Random();

		//		HashMap<String, String> req_body = new HashMap<>();
		//		req_body.put("gender", (String) data[0][0]);
		//		req_body.put("encrypted_name", (String) data[0][1]);
		//
		//		JSONObject Myrequestbody = new JSONObject();
		//
		//		Myrequestbody.put("gender", req_body.get("gender"));
		//		Myrequestbody.put("encrypted_name", req_body.get("encrypted_name"));

		//		System.out.println("hhhhhhhhhhhhhhh :"+Myrequestbody);

		//		String d= "validate";

		//		 System.out.println("\"validate\"");
		//		
		//		String yy="{"+"\"validate\""+":"+Myrequestbody;
		//		System.out.println("ggggggggggg :"+yy);

		HashMap<String, Object> headers = new HashMap<>();
		headers.put("Authorization", "Bearer "+user_token);


		ValidatableResponse response = Utilities.getMethodWithHeaderAPI(headers, url);

		//		System.out.println("Request :" + Myrequestbody);
		//		ExtentReporter.extentLogger("", "Request :" + Myrequestbody);

		String Resp = response.extract().body().asString();
		System.out.println("Response Body= " + Resp);
		ExtentReporter.extentLogger("", "Response Body= " + Resp);


		return response;

	}


}