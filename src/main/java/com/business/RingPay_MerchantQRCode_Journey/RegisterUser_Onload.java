package com.business.RingPay_MerchantQRCode_Journey;

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
import org.testng.Assert;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertNotNull;
import com.utility.Utilities;

import io.restassured.response.ValidatableResponse;


public class RegisterUser_Onload {

	public void onload_Positive() throws Exception {
		
		//			Start Time
		long startTime=System.currentTimeMillis();

		ValidatableResponse response = Utilities.OnloadAPI();

		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"Onload,Validating 200 Success Response");



		//Body Validation
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"Onload,Request_Id value should not be null");

		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","Onload Validating messsage value");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//onload_200_schema.json")), response.extract().body().asString(), "Onload,expectedJsonSchema");


		//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'onload_Positive'  : "+(endTime-startTime)+" milliseconds");

	}

}
