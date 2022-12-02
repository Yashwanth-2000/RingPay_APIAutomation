package com.business.RingPay_MerchantQRCode_Journey;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.Datasheet.RingPay_TestData_DataProvider;
import com.utility.ExtentReporter;
import com.utility.Utilities;
import com.utility.Validation;

import io.restassured.response.ValidatableResponse;

public class Bnpl_Txn_Transaction_Complete {

	RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();


	public void transactionComplete() throws Exception {
		
//		Start Time
		long startTime=System.currentTimeMillis();

		Object[][] data = dataProvider.TxnCompleteAPIData("txn_complete");
		ValidatableResponse response = Utilities.TransactionCompleteAPI(data);


		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"transactionComplete,Validating 200 Success Response");

		
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","transactionComplete,Validating message should be success");
		Validation.assertEquals(response.extract().body().jsonPath().get("data.transaction.status"),"FINAL_APPROVED","transactionComplete,Validating status should be FINAL_APPROVED");
		Validation.assertEquals(response.extract().body().jsonPath().get("data.transaction.settlement_status"),"DISBURSED","transactionComplete,Validating status should be DISBURSED");
		Validation.assertTrue(response.extract().body().jsonPath().get("data.is_txn_allowed"), "transactionComplete,Validating is_line_present should be true");


		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//transaction_complete_200_schema.json")), response.extract().body().asString(), "transactionComplete,expectedJsonSchema");
		

		//		End Time
			long endTime=System.currentTimeMillis();
			ExtentReporter.extentLogger("Time Stamp", "API RunTime 'transactionComplete'  : "+(endTime-startTime)+" milliseconds");

		
	}
	
	
}
