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

import io.restassured.response.Response;
import io.restassured.RestAssured;

public class RegisterUser_Get_Details_VPA {

	RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();


	public void get_Details_Vpa_Positive() throws Exception {

//		Start Time
		long startTime=System.currentTimeMillis();

		Object[][] data = dataProvider.Get_Details_VPA_APIData("get_vpn");
		ValidatableResponse response = Utilities.Get_Details_VPA_API(data);

		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"Mock_User,Validating 200 Success Response");


		//Body Validation
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"Get_Details_VPN,Validating request_id is not null");

		Validation.assertTrue(response.extract().body().jsonPath().get("success"), "Get_Details_VPN,Validating success is true");

		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","Get_Details_VPN,Validating messsage value");

		Validation.assertEquals(response.extract().body().jsonPath().get("data.merchant_details.status"),"ACTIVE","Get_Details_VPN,Validating status is Active");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//getdetailsvpn_200_schema.json")), response.extract().body().asString(), "Mock_User,expectedJsonSchema");

//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'get_Details_Vpa_Positive'  : "+(endTime-startTime)+" milliseconds");

	}



}
