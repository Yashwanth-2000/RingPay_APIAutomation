package com.business.RingPay_MerchantQRCode_Journey;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.Datasheet.RingPay_TestData_DataProvider;
import com.utility.Utilities;
import com.utility.Validation;

import io.restassured.response.ValidatableResponse;

public class TransactionDetails_Get_Settlement_Status {
	
	RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();
	
	public void getSettlementStatus() throws Exception {
		
		Object[][] data = dataProvider.TxnCompleteAPIData("txn_complete");
		ValidatableResponse response = Utilities.GetSettlementAPI(data);


		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"getSettlementStatus,Validating 200 Success Response");
		
		//		Body Validation
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","getSettlementStatus,getSettlementStatus message should be Success");


		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//get_settlement_status_200_schema.json")), response.extract().body().asString(), "getSettlementStatus,expectedJsonSchema");
	

}
	
}
