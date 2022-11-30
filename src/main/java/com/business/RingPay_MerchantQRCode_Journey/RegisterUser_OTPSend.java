package com.business.RingPay_MerchantQRCode_Journey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

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

public class RegisterUser_OTPSend {


	static RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();

	public static void validMobileNo_Positive() throws Exception {

		//		Start Time
		long startTime=System.currentTimeMillis();

		Object[][] data = dataProvider.SendOtpAPIData("otp_200");
		ValidatableResponse response = Utilities.SendOtpAPI(data);

		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"OTPSend,Validating 200 Success Response");


		//Body Validation
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"OTPSend,Validating request_id is not null");
		Validation.assertTrue(response.extract().body().jsonPath().get("success"), "OTPSend,Validating success is true");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","OTPSend,Validating status should be success");
		Validation.assertFalse(response.extract().body().jsonPath().get("data.user_exists"), "OTPSend,Validating user_exists should be false");



		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//otp_200_schema.json")), response.extract().body().asString(), "validMobileNo_Positive,expectedJsonSchema");

		//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'SendOTP_Positive'  : "+(endTime-startTime)+" milliseconds");


	}

	//	    ====================================================

	public static void valid_MobileNo_UserExist_True_Positive() throws Exception {

		//		Start Time
		long startTime=System.currentTimeMillis();


		Object[][] data = dataProvider.SendOtpAPIData("otp_200");
		ValidatableResponse response = Utilities.SendOtpAPI(data);

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"valid_MobileNo_UserExist_True_Positive,Validating 200 Success Response");


		//Body Validation

		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"valid_MobileNo_UserExist_True_Positive,Validating request_id is not null");
		Validation.assertTrue(response.extract().body().jsonPath().get("success"), "valid_MobileNo_UserExist_True_Positive,Validating success is true");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","valid_MobileNo_UserExist_True_Positive,Validating status should be success");


		//		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//otp_200_schema.json")), response.extract().body().asString(), "valid_MobileNo_UserExist_True_Positive,expectedJsonSchema");
		
		//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'valid_MobileNo_UserExist_True_Positive'  : "+(endTime-startTime)+" milliseconds");

		
	}


	//	    /RingPay/TestData/otp_200_schema.json


	public void mobileNoLessThan10Digit_Negative() throws Exception {
		
//		Start Time
		long startTime=System.currentTimeMillis();

		Object[][] data = dataProvider.SendOtpAPIData("mobile_no.lessthan10digits_400");
		ValidatableResponse response = Utilities.SendOtpAPI(data);

		//Status Code Validation

		int responseBody=response.extract().statusCode();

		Validation.validatingStatusCode(responseBody,400,"mobileNoLessThan10Digit_Negative,Validating 400 Bad Request");


		//Body Validation
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The mobile number must be at least 10 characters.","mobileNoLessThan10Digit_Negative,Validating message value");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//otp_400_schema.json")), response.extract().body().asString(), "mobileNoLessThan10Digit_Negative,expectedJsonSchema");
		
		//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'mobileNoLessThan10Digit_Negative'  : "+(endTime-startTime)+" milliseconds");

	}


	public void mobileNoMoreThan10Digit_Negative() throws Exception {
		
//		Start Time
		long startTime=System.currentTimeMillis();

		Object[][] data = dataProvider.SendOtpAPIData("mobile_no.morethan10digits_400");
		ValidatableResponse response = Utilities.SendOtpAPI(data);

		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"mobileNoMoreThan10Digit_Negative,Validating 400 Bad Request");


		//Body Validation
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The mobile number may not be greater than 10 characters.","mobileNoMoreThan10Digit_Negative,Validating message value");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//otp_400_schema.json")), response.extract().body().asString(), "mobileNoMoreThan10Digit_Negative,expectedJsonSchema");

//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'mobileNoMoreThan10Digit_Negative'  : "+(endTime-startTime)+" milliseconds");

	}
	

	public void specialCharacterInMobileNoField_Negative() throws Exception {
		
//		Start Time
		long startTime=System.currentTimeMillis();

		Object[][] data = dataProvider.SendOtpAPIData("specialcharacterinafield_400");
		ValidatableResponse response = Utilities.SendOtpAPI(data);

		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"specialCharacterInMobileNoField_Negative,Validating 400 Bad Request");


		//Body Validation
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The mobile number format is invalid.","specialCharacterInMobileNoField_Negative,Validating message value");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//otp_400_schema.json")), response.extract().body().asString(), "specialCharacterInMobileNoField_Negative,expectedJsonSchema");

//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'specialCharacterInMobileNoField_Negative'  : "+(endTime-startTime)+" milliseconds");

	}



}
