package com.business.RingPay_MerchantQRCode_Journey;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.Datasheet.RingPay_TestData_DataProvider;
import com.utility.Utilities;
import com.utility.Validation;

import io.restassured.response.ValidatableResponse;
import com.utility.ExtentReporter;
public class OfferDetailsScreen_Get_Offer {

	RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();


	public void get_Offer() throws Exception {

		//		Start Time
		long startTime=System.currentTimeMillis();


		//		Object[][] data = dataProvider.Mock_UserAPIData("user_200");
		ValidatableResponse response = Utilities.getOfferAPI();


		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"get_Offer,Validating 200 Success Response");


		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//getoffers_200_schema.json")), response.extract().body().asString(), "get_Offer,expectedJsonSchema");

		//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'get_OfferAPI'  : "+(endTime-startTime)+" milliseconds");

	}

}
