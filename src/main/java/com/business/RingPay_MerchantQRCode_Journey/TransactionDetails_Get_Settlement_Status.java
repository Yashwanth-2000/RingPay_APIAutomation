package com.business.RingPay_MerchantQRCode_Journey;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.utility.Utilities;
import com.utility.Validation;

import io.restassured.response.ValidatableResponse;

public class TransactionDetails_Get_Settlement_Status {
	
	
	
	public void getSettlementStatus() throws Exception {
		ValidatableResponse response = Utilities.GetSettlementAPI();


		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"getSettlementStatus,Validating 200 Success Response");
		
		//		Body Validation
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","getSettlementStatus,getSettlementStatus message should be Success");


		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//get_settlement_status_200_schema.json")), response.extract().body().asString(), "getSettlementStatus,expectedJsonSchema");
	

}
	
}
