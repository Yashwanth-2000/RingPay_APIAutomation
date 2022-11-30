package com.business.RingPay_MerchantQRCode_Journey;

import static org.testng.AssertJUnit.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import com.Datasheet.RingPay_TestData_DataProvider;
import com.excel.ExcelWriteData;
import com.utility.ExtentReporter;
import com.utility.Utilities;
import com.utility.Validation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegisterUser_Login {

	static RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();

	public static ValidatableResponse login_Positive() throws Exception {

//		String filePath=  System.getProperty("user.dir")+"\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

		//		Start Time
		long startTime=System.currentTimeMillis();

		Object[][] data = dataProvider.UpdateUserAPIData("update_user_200");
        ValidatableResponse response = Utilities.loginAPI();
		

		//Status Code Validation

        int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"login_Positive,Validating 200 Success Response");
        
        
		//Body Validation
		
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"login_Positive,Validating request_id is not null");
		Validation.assertTrue(response.extract().body().jsonPath().get("success"), "login_Positive,Validating success should be true");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","login_Positive,Validating message should be success");
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("data.user_reference_number"),"login_Positive,Validating user_reference_number is not null");
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("data.first_name"),"login_Positive,Validating first_name is not null");
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("data.last_name"),"login_Positive,Validating last_name is not null");
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("data.email"),"login_Positive,Validating email is not null");
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("data.merchant_reference_number"),"login_Positive,Validating merchant_reference_number is not null");
		
        Long mobileno=response.extract().body().jsonPath().get("data.mobile_number");
		Validation.assertLongNotNullBodyValidation(mobileno,"login_Positive,Validating mobile_number is not null");


		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//login_200_schema.json")), response.extract().body().asString(), "login_Positive,expectedJsonSchema");

//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'login_Positive'  : "+(endTime-startTime)+" milliseconds");

		
		return response;
	}
}
