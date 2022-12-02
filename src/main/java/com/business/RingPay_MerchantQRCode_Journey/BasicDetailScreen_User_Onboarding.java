package com.business.RingPay_MerchantQRCode_Journey;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import com.Datasheet.RingPay_TestData_DataProvider;
import com.utility.ExtentReporter;
import com.utility.Utilities;
import com.utility.Validation;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;

public class BasicDetailScreen_User_Onboarding {


	RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();


	public void userOnbording_Positive() throws Exception {

		//		Start Time
		long startTime=System.currentTimeMillis();


		Object[][] data = dataProvider.UserOnboardingAPIData("user_onboarding_200");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);

		String line_application_reference_number = response.extract().body().jsonPath()
				.get("data.details.line_application_reference_number");
		System.out.println("line_application_reference_number: " + line_application_reference_number);


		//		Thread.sleep(5000);
		//		
		//		String dataBase =Utilities.executeQuery("SELECT * FROM db_tradofina.line_application where line_application_reference_number='"+ line_application_reference_number+"';",2);
		//		System.out.println("DataBase  :======================= "+ dataBase);
		//		Validation.assertEquals(line_application_reference_number,dataBase,"userOnbording_Positive,Validating DataBase");



		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"userAuthenticate,Validating 200 Success Response");


		//Body Validation

		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","userOnbording_Positive,Validating message should be success");
		Validation.assertEquals(response.extract().body().jsonPath().get("data.stage"),"basic_details_pending","userOnbording_Positive,Validating message should be success");
		Validation.assertEquals(response.extract().body().jsonPath().get("data.stage_detail"),"apply","userOnbording_Positive,Validating message should be success");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_200_schema.json")), response.extract().body().asString(), "userOnbording_Positive,expectedJsonSchema");

		//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'userOnbording_Positive'  : "+(endTime-startTime)+" milliseconds");


	}

//	public void userOnbordingWithValidField_Positive() throws Exception {
//
//		Object[][] data = dataProvider.UserOnboardingAPIData("user_onboarding_200");
//		ValidatableResponse response = Utilities.User_OnboardingAPI(data);
//
//		//Status Code Validation
//		int responseBody=response.extract().statusCode();
//		Validation.validatingStatusCode(responseBody,200,"userAuthenticate,Validating 200 Success Response");
//
//
//		//Body Validation
//
//		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","userOnbording_Positive,Validating message should be success");
//		Validation.assertEquals(response.extract().body().jsonPath().get("data.stage"),"basic_details_pending","userOnbording_Positive,Validating message should be success");
//		Validation.assertEquals(response.extract().body().jsonPath().get("data.stage_detail"),"apply","userOnbording_Positive,Validating message should be success");
//
//
//		//Schema Validation
//
//		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_200_schema.json")), response.extract().body().asString(), "userOnbording_Positive,expectedJsonSchema");
//
//
//	}
	
	

	public void latitudeFieldEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("latitude_field_empty_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"userAuthenticate,Validating 400 Bad Request");

		//Body Validation

		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The latitude field is required.","latitudeFieldEmpty_Negative,Validating message value");

		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json")), response.extract().body().asString(), "latitudeFieldEmpty_Negative,expectedJsonSchema");

	}

	public void longitudeFieldEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("longitude_field_empty_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"longitudeFieldEmpty_Negative,Validating 400 Bad Request");

		//Body Validation

		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The longitude field is required.","longitudeFieldEmpty_Negative,Validating message value");

		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json")), response.extract().body().asString(), "longitudeFieldEmpty_Negative,expectedJsonSchema");


	}

	public void advertisingIdFieldEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("advertising_id_field_empty_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"advertisingIdFieldEmpty_Negative,Validating 400 Bad Request");

		//Body Validation

		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The advertising id field is required.","advertisingIdFieldEmpty_Negative,Validating message value");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json")), response.extract().body().asString(), "advertisingIdFieldEmpty_Negative,expectedJsonSchema");


	}

	public void androidIdFieldEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("android_id_field_empty_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		//		Assert.assertEquals(response.extract().statusCode(), 400);
		//		Utilities.log.info("androidIdFieldEmpty_400 , Validating 400 Bad Request");
		//		ExtentReporter.extentLogger("androidIdFieldEmpty_400", "androidIdFieldEmpty_400,Validating 400 Bad Request");
		//
		//		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The android id field is required.");
		//		Utilities.log.info("androidIdFieldEmpty_400 , Validating messsage value");
		//		ExtentReporter.extentLogger("androidIdFieldEmpty_400", "androidIdFieldEmpty_400,Validating messsage");

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"androidIdFieldEmpty_Negative,Validating 400 Bad Request");

		//Body Validation

		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The android id field is required.","androidIdFieldEmpty_Negative,Validating message value");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json")), response.extract().body().asString(), "androidIdFieldEmpty_Negative,expectedJsonSchema");


	}

	public void globalDeviceIdFieldEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("global_device_id_field_empty_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		//		Assert.assertEquals(response.extract().statusCode(), 400);
		//		Utilities.log.info("globalDeviceIdFieldEmpty_400 , Validating 400 Bad Request");
		//		ExtentReporter.extentLogger("globalDeviceIdFieldEmpty_400", "globalDeviceIdFieldEmpty_400,Validating 400 Bad Request");
		//
		//		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The global device id field is required.");
		//		Utilities.log.info("globalDeviceIdFieldEmpty_400 , Validating messsage value");
		//		ExtentReporter.extentLogger("globalDeviceIdFieldEmpty_400", "globalDeviceIdFieldEmpty_400,Validating messsage");

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"globalDeviceIdFieldEmpty_Negative,Validating 400 Bad Request");

		//Body Validation

		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The global device id field is required.","globalDeviceIdFieldEmpty_Negative,Validating message value");


		//Schema Validation
		//		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json"));
		//		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		//		Utilities.log.info("globalDeviceIdFieldEmpty_400 , expectedJsonSchema");
		//		ExtentReporter.extentLogger("globalDeviceIdFieldEmpty_400", "globalDeviceIdFieldEmpty_400,expectedJsonSchema");

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json")), response.extract().body().asString(), "globalDeviceIdFieldEmpty_Negative,expectedJsonSchema");


	}

	public void latitudeAndLongitudeFieldEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("latitude_and_longitude_field_empty_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		//		Assert.assertEquals(response.extract().statusCode(), 400);
		//		Utilities.log.info("latitudeAndLongitudeFieldEmpty_400 , Validating 400 Bad Request");
		//		ExtentReporter.extentLogger("latitudeAndLongitudeFieldEmpty_400", "latitudeAndLongitudeFieldEmpty_400,Validating 400 Bad Request");
		//
		//		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The latitude field is required.,The longitude field is required.");
		//		Utilities.log.info("latitudeAndLongitudeFieldEmpty_400 , Validating messsage value");
		//		ExtentReporter.extentLogger("latitudeAndLongitudeFieldEmpty_400", "latitudeAndLongitudeFieldEmpty_400,Validating messsage");

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"latitudeAndLongitudeFieldEmpty_Negative,Validating 400 Bad Request");

		//Body Validation

		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The latitude field is required.,The longitude field is required.","latitudeAndLongitudeFieldEmpty_Negative,Validating message value");




		//Schema Validation
		//		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json"));
		//		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		//		Utilities.log.info("latitudeAndLongitudeFieldEmpty_400 , expectedJsonSchema");
		//		ExtentReporter.extentLogger("latitudeAndLongitudeFieldEmpty_400", "latitudeAndLongitudeFieldEmpty_400,expectedJsonSchema");

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json")), response.extract().body().asString(), "latitudeAndLongitudeFieldEmpty_Negative,expectedJsonSchema");


	}

	public void latitudeFieldWithAlphaNumericKeywords_Negative() throws Exception {

		Object[][] data = dataProvider.UserOnboardingAPIData("latitude_field_with_alphanumeric_keywords_400");
		ValidatableResponse response = Utilities.User_OnboardingAPI(data);


		//Status Code Validation
		//		Assert.assertEquals(response.extract().statusCode(), 400);
		//		Utilities.log.info("latitudeFieldWithAlphaNumericKeywords_400 , Validating 400 Bad Request");
		//		ExtentReporter.extentLogger("latitudeFieldWithAlphaNumericKeywords_400", "latitudeFieldWithAlphaNumericKeywords_400,Validating 400 Bad Request");
		//
		//		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The latitude format is invalid.");
		//		Utilities.log.info("latitudeFieldWithAlphaNumericKeywords_400 , Validating messsage value");
		//		ExtentReporter.extentLogger("latitudeFieldWithAlphaNumericKeywords_400", "latitudeFieldWithAlphaNumericKeywords_400,Validating messsage");

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"latitudeFieldWithAlphaNumericKeywords_Negative,Validating 400 Bad Request");

		//Body Validation

		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The latitude format is invalid.","latitudeFieldWithAlphaNumericKeywords_Negative,Validating message value");


		//Schema Validation
		//		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json"));
		//		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		//		Utilities.log.info("latitudeFieldWithAlphaNumericKeywords_400 , expectedJsonSchema");
		//		ExtentReporter.extentLogger("latitudeFieldWithAlphaNumericKeywords_400", "latitudeFieldWithAlphaNumericKeywords_400,expectedJsonSchema");

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//useronboarding_400_schema.json")), response.extract().body().asString(), "latitudeFieldWithAlphaNumericKeywords_Negative,expectedJsonSchema");


	}



}
