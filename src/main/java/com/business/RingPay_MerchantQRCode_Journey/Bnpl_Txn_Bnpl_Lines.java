package com.business.RingPay_MerchantQRCode_Journey;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.Datasheet.RingPay_TestData_DataProvider;
import com.utility.Utilities;
import com.utility.Validation;

import io.restassured.response.ValidatableResponse;

public class Bnpl_Txn_Bnpl_Lines {
	
//	RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();


	public void bnpl_Lines() throws Exception {
//		long startTime=System.currentTimeMillis();
//		Object[][] data = dataProvider.Mock_UserAPIData("user_200");
		ValidatableResponse response = Utilities.bnplLinesAPI();


		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"bnpl_Lines,Validating 200 Success Response");
		
//		Body Validation
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"bnpl_Lines,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","bnpl_Lines,Validating message should be success");
		Validation.assertTrue(response.extract().body().jsonPath().get("data.bnpl.is_line_present"), "bnpl_Lines,Validating is_line_present should be true");
		Validation.assertEquals(response.extract().body().jsonPath().get("data.bnpl.line.status"),"ACTIVE","bnpl_Lines,Validating status should be ACTIVE");

		


		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//bnpl_lines_200_schema.json")), response.extract().body().asString(), "bnpl_Lines,expectedJsonSchema");
//		long endTime=System.currentTimeMillis();
//		
//		ExtentReporter.extentLogger("Time Stamp", "Api Time 'mock_User_200'  : "+(endTime-startTime)+" milliseconds");
	}

}
