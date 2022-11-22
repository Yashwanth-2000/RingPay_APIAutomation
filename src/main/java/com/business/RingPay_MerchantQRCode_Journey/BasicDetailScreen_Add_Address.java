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

public class BasicDetailScreen_Add_Address {
	
	 RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();

		public ValidatableResponse addAddress() throws Exception {
			Object[][] data = dataProvider.AddAddressAPIData("add_address");
			ValidatableResponse response = Utilities.AddAddressAPI(data);


			//Status Code Validation
//			Assert.assertEquals(response.extract().statusCode(), 200);
//			Utilities.log.info("addAddress , Validating 200 Success Response");
//			ExtentReporter.extentLogger("addAddress", "addAddress,Validating 200 Success Response");

			 int responseBody=response.extract().statusCode();
				Validation.validatingStatusCode(responseBody,200,"addAddress,Validating 200 Success Response");
		        
			
			//Body Validation
				Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"addAddress,Validating request_id is not null");
				Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","addAddress,Validating message should be success");

//			assertNotNull("'request_id' is not null", response.extract().body().jsonPath().get("request_id"));
//			Utilities.log.info("addAddress , Validating request_id is not null");
//			ExtentReporter.extentLogger("addAddress", "Validating request_id is not null");
//			Assert.assertEquals(response.extract().body().jsonPath().get("message"), "Success");
//			Utilities.log.info("addAddress , Validating messsage value");
//			ExtentReporter.extentLogger("addAddress", "addAddress,Validating messsage value");


//			//Schema Validation
//			String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//updateuserstatus_200_schema.json"));
//			assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
//			Utilities.log.info("addAddress , expectedJsonSchema");
//			ExtentReporter.extentLogger("addAddress", "addAddress,expectedJsonSchema");
//				Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//updateuserstatus_200_schema.json")), response.extract().body().asString(), "addAddress,expectedJsonSchema");

				
			return response;

		}

}
