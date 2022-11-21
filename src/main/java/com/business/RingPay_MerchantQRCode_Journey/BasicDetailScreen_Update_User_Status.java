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
import com.utility.Validation;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;

public class BasicDetailScreen_Update_User_Status {


	RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();

	public void updateUserStatus_Positive() throws Exception {
		Object[][] data = dataProvider.UpdateUserStatusAPIData("update_user_200");
		ValidatableResponse response = Utilities.UpdateUserStatusAPI(data);


		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"updateUserStatus_Positive,Validating 200 Success Response");

		
		//Body Validation

		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"updateUserStatus_Positive,Validating request_id is not null");
		Validation.assertEqualsValidatingSuccessValue(response.extract().body().jsonPath().get("message"),"Success","updateUserStatus_Positive,Validating message should be success");
		Validation.assertEqualsValidatingSuccessValue(response.extract().body().jsonPath().get("data.user.onboarding_stage"),"VERIFIED","updateUserStatus_Positive,Validating message should be success");

		

		//Schema Validation
		//		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//update_user_status_200_schema.json"));
		//		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		//		Utilities.log.info("updateUserStatus_200 , expectedJsonSchema");
		//		ExtentReporter.extentLogger("updateUserStatus_200", "updateUserStatus_200,expectedJsonSchema");



	}


}
