package com.business.RingPay_MerchantQRCode_Journey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import com.Datasheet.RingPay_TestData_DataProvider;
import com.excel.ExcelFunctions;
import com.excel.ExcelWriteData;
import com.utility.ExtentReporter;
import com.utility.LoggingUtils;
import com.utility.Utilities;
import com.utility.Validation;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;

public class RegisterUser_Mock_User {


	static RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();


	public static ValidatableResponse mock_User_200() throws Exception {
//		long startTime=System.currentTimeMillis();
		Object[][] data = dataProvider.Mock_UserAPIData("user_200");
		ValidatableResponse response = Utilities.MockuserAPI(data);


		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"Mock_User,Validating 200 Success Response");


		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//mock_user_200_schema.json")), response.extract().body().asString(), "Mock_User,expectedJsonSchema");
//		long endTime=System.currentTimeMillis();
//		
//		ExtentReporter.extentLogger("Time Stamp", "Api Time 'mock_User_200'  : "+(endTime-startTime)+" milliseconds");
	
	return	response;
	
	}

	}
	

