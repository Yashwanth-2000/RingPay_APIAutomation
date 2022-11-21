package com.business.RingPay_MerchantQRCode_Journey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import com.Datasheet.RingPay_TestData_DataProvider;
import com.utility.ExtentReporter;
import com.utility.Utilities;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;

public class BasicDetailScreen_Get_User_Detail {
	
//	RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();


	public void getUserDetails_Positive() throws Exception {
		
//		Object[][] data = dataProvider.RegisterUserAPIData("registeruser_200");
		ValidatableResponse response = Utilities.Get_User_DetailsAPI();
		

		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 200);
		Utilities.log.info("get_user_details_200 , Validating 200 Success Response");
		ExtentReporter.extentLogger("get_user_details_200", "get_user_details_200,Validating 200 Success Response");
		
		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "Success");
		Utilities.log.info("get_user_details_200 , Validating messsage value");
		ExtentReporter.extentLogger("get_user_details_200", "get_user_details_200,Validating messsage value");
		
		Assert.assertEquals(response.extract().body().jsonPath().get("data.onboarding_stage"), "PENDING");
		Utilities.log.info("get_user_details_200 , Validating onboarding_stage value should be PENDING");
		ExtentReporter.extentLogger("get_user_details_200", "get_user_details_200,Validating onboarding_stage value should be PENDING");
		
	      //Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//getuserdetails_200.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("get_user_details_200 , expectedJsonSchema");
		ExtentReporter.extentLogger("get_user_details_200", "get_user_details_200,expectedJsonSchema");
		
		Thread.sleep(2000);
	
	}

}