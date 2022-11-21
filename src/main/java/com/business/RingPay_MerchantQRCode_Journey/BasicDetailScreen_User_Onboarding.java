package com.business.RingPay_MerchantQRCode_Journey;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import com.Datasheet.RingPay_TestData_DataProvider;
import com.utility.ExtentReporter;
import com.utility.Utilities;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;

public class BasicDetailScreen_User_Onboarding {


	RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();


	public void userOnbording_Positive() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("user_onboarding_200");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 200);
		Utilities.log.info("user_onboarding_200 , Validating 200 Success Response");
		ExtentReporter.extentLogger("user_onboarding_200", "user_onboarding_200,Validating 200 Success Response");

		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "Success");
		Utilities.log.info("user_onboarding_200 , Validating messsage value");
		ExtentReporter.extentLogger("user_onboarding_200", "user_onboarding_200,Validating messsage value");

		Assert.assertEquals(response.extract().body().jsonPath().get("data.stage"), "basic_details_pending");
		Utilities.log.info("user_onboarding_200 , Validating stage should be basic_details_pending");
		ExtentReporter.extentLogger("user_onboarding_200", "user_onboarding_200,Validating stage should be basic_details_pending");

		Assert.assertEquals(response.extract().body().jsonPath().get("data.stage_detail"), "apply");
		Utilities.log.info("user_onboarding_200 , Validating stage_detail should be apply");
		ExtentReporter.extentLogger("user_onboarding_200", "user_onboarding_200,Validating stage_detail should be apply");


		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_200_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("user_onboarding_200 , expectedJsonSchema");
		ExtentReporter.extentLogger("user_onboarding_200", "user_onboarding_200,expectedJsonSchema");

		Thread.sleep(2000);

	}

	public void latitudeFieldEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("latitude_field_empty_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 400);
		Utilities.log.info("latitudeFieldEmpty_400 , Validating 400 Bad Request");
		ExtentReporter.extentLogger("latitudeFieldEmpty_400", "latitudeFieldEmpty_400,Validating 400 Bad Request");

		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The latitude field is required.");
		Utilities.log.info("latitudeFieldEmpty_400 , Validating messsage value");
		ExtentReporter.extentLogger("latitudeFieldEmpty_400", "latitudeFieldEmpty_400,Validating messsage");

		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("latitudeFieldEmpty_400 , expectedJsonSchema");
		ExtentReporter.extentLogger("latitudeFieldEmpty_400", "latitudeFieldEmpty_400,expectedJsonSchema");

		Thread.sleep(2000);
	}

	public void longitudeFieldEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("longitude_field_empty_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 400);
		Utilities.log.info("longitudeFieldEmpty_400 , Validating 400 Bad Request");
		ExtentReporter.extentLogger("longitudeFieldEmpty_400", "longitudeFieldEmpty_400,Validating 400 Bad Request");

		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The longitude field is required.");
		Utilities.log.info("longitudeFieldEmpty_400 , Validating messsage value");
		ExtentReporter.extentLogger("longitudeFieldEmpty_400", "longitudeFieldEmpty_400,Validating messsage");

		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("longitudeFieldEmpty_400 , expectedJsonSchema");
		ExtentReporter.extentLogger("longitudeFieldEmpty_400", "longitudeFieldEmpty_400,expectedJsonSchema");

		//		Thread.sleep(2000);
	}

	public void advertisingIdFieldEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("advertising_id_field_empty_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 400);
		Utilities.log.info("longitudeFieldEmpty_400 , Validating 400 Bad Request");
		ExtentReporter.extentLogger("longitudeFieldEmpty_400", "longitudeFieldEmpty_400,Validating 400 Bad Request");

		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The advertising id field is required.");
		Utilities.log.info("longitudeFieldEmpty_400 , Validating messsage value");
		ExtentReporter.extentLogger("longitudeFieldEmpty_400", "longitudeFieldEmpty_400,Validating messsage");

		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("longitudeFieldEmpty_400 , expectedJsonSchema");
		ExtentReporter.extentLogger("longitudeFieldEmpty_400", "longitudeFieldEmpty_400,expectedJsonSchema");

	}

	public void androidIdFieldEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("android_id_field_empty_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 400);
		Utilities.log.info("androidIdFieldEmpty_400 , Validating 400 Bad Request");
		ExtentReporter.extentLogger("androidIdFieldEmpty_400", "androidIdFieldEmpty_400,Validating 400 Bad Request");

		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The android id field is required.");
		Utilities.log.info("androidIdFieldEmpty_400 , Validating messsage value");
		ExtentReporter.extentLogger("androidIdFieldEmpty_400", "androidIdFieldEmpty_400,Validating messsage");

		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("androidIdFieldEmpty_400 , expectedJsonSchema");
		ExtentReporter.extentLogger("androidIdFieldEmpty_400", "androidIdFieldEmpty_400,expectedJsonSchema");

	}

	public void globalDeviceIdFieldEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("global_device_id_field_empty_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 400);
		Utilities.log.info("globalDeviceIdFieldEmpty_400 , Validating 400 Bad Request");
		ExtentReporter.extentLogger("globalDeviceIdFieldEmpty_400", "globalDeviceIdFieldEmpty_400,Validating 400 Bad Request");

		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The global device id field is required.");
		Utilities.log.info("globalDeviceIdFieldEmpty_400 , Validating messsage value");
		ExtentReporter.extentLogger("globalDeviceIdFieldEmpty_400", "globalDeviceIdFieldEmpty_400,Validating messsage");

		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("globalDeviceIdFieldEmpty_400 , expectedJsonSchema");
		ExtentReporter.extentLogger("globalDeviceIdFieldEmpty_400", "globalDeviceIdFieldEmpty_400,expectedJsonSchema");

	}

	public void latitudeAndLongitudeFieldEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("latitude_and_longitude_field_empty_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 400);
		Utilities.log.info("latitudeAndLongitudeFieldEmpty_400 , Validating 400 Bad Request");
		ExtentReporter.extentLogger("latitudeAndLongitudeFieldEmpty_400", "latitudeAndLongitudeFieldEmpty_400,Validating 400 Bad Request");

		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The latitude field is required.,The longitude field is required.");
		Utilities.log.info("latitudeAndLongitudeFieldEmpty_400 , Validating messsage value");
		ExtentReporter.extentLogger("latitudeAndLongitudeFieldEmpty_400", "latitudeAndLongitudeFieldEmpty_400,Validating messsage");

		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("latitudeAndLongitudeFieldEmpty_400 , expectedJsonSchema");
		ExtentReporter.extentLogger("latitudeAndLongitudeFieldEmpty_400", "latitudeAndLongitudeFieldEmpty_400,expectedJsonSchema");

	}

	public void latitudeFieldWithAlphaNumericKeywords_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("latitude_field_with_alphanumeric_keywords_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 400);
		Utilities.log.info("latitudeFieldWithAlphaNumericKeywords_400 , Validating 400 Bad Request");
		ExtentReporter.extentLogger("latitudeFieldWithAlphaNumericKeywords_400", "latitudeFieldWithAlphaNumericKeywords_400,Validating 400 Bad Request");

		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The latitude format is invalid.");
		Utilities.log.info("latitudeFieldWithAlphaNumericKeywords_400 , Validating messsage value");
		ExtentReporter.extentLogger("latitudeFieldWithAlphaNumericKeywords_400", "latitudeFieldWithAlphaNumericKeywords_400,Validating messsage");

		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("latitudeFieldWithAlphaNumericKeywords_400 , expectedJsonSchema");
		ExtentReporter.extentLogger("latitudeFieldWithAlphaNumericKeywords_400", "latitudeFieldWithAlphaNumericKeywords_400,expectedJsonSchema");

	}



}
