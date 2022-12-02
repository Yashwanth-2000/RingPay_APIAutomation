package com.business.RingPay_MerchantQRCode_Journey;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.Datasheet.RingPay_TestData_DataProvider;
import com.excel.ExcelWriteData;
import com.utility.ExtentReporter;
import com.utility.Utilities;
import com.utility.Validation;

import io.restassured.response.ValidatableResponse;

public class Bnpl_Txn_Transaction_Initiate {

	static RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();


	public static ValidatableResponse transactionInitiate_Positive() throws Exception {

		//		Start Time
		long startTime=System.currentTimeMillis();

		String filePath = System.getProperty("user.dir")+ "\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";

		//		long startTime=System.currentTimeMillis();
		Object[][] data = dataProvider.TxnInitiateAPIData("txn_initiate");
		ValidatableResponse response = Utilities.TransactionInitiateAPI(data);

		// Data Txn_Initiated
		String transaction_reference_number = response.extract().body().jsonPath().get("data.transaction.transaction_reference_number");
		System.out.println("transaction_reference_number: " + transaction_reference_number);

		// Data to transaction_reference_number
		ExcelWriteData.excelWrite(filePath, "Txn_Complete", transaction_reference_number, 1, 1);


		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"transactionInitiate_Positive,Validating 200 Success Response");


		//		Body Validation

		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"transactionInitiate_Positive,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","transactionInitiate_Positive,Validating message should be success");
		Validation.assertEquals(response.extract().body().jsonPath().get("data.transaction.status"),"CUST_ABORT","transactionInitiate_Positive,Validating status should be CUST_ABORT");
		Validation.assertTrue(response.extract().body().jsonPath().get("data.is_txn_allowed"), "transactionInitiate_Positive,Validating is_line_present should be true");
		Validation.assertNull(response.extract().body().jsonPath().get("data.transaction.settlement_status"),"transactionInitiate_Positive,Validating settlement_status should be null");


		//Schema Validation 
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//transaction_initiated_200_schema.json")), response.extract().body().asString(), "transactionInitiate_Positive,expectedJsonSchema");

		//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'transactionInitiate_Positive'  : "+(endTime-startTime)+" milliseconds");


		return response;

	}


	public void productValueEmptyField_Negative() throws Exception {
		//		long startTime=System.currentTimeMillis();
		Object[][] data = dataProvider.TxnInitiateAPIData("productvaluefieldempty");
		ValidatableResponse response = Utilities.TransactionInitiateAPI(data);

		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"productValueEmptyField_Negative,Validating 400 Bad Request");

		//		Body Validation

		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"productValueEmptyField_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The product value field is required.","productValueEmptyField_Negative,Validating productValue should be empty");

		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//transaction_initiated_400_schema.json")), response.extract().body().asString(), "productValueEmptyField_Negative,expectedJsonSchema");

	}

	public void productValueFieldWithAlphaNumericCharacters_Negative() throws Exception {
		//		long startTime=System.currentTimeMillis();
		Object[][] data = dataProvider.TxnInitiateAPIData("productvaluefieldwithalphanumeric_character");
		ValidatableResponse response = Utilities.TransactionInitiateAPI(data);

		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"productValueFieldWithAlphaNumericCharacters_Negative,Validating 400 Bad Request");

		//		Body Validation

		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"productValueFieldWithAlphaNumericCharacters_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The product value format is invalid.","productValueFieldWithAlphaNumericCharacters_Negative,Validating productValue should be AlphaNumeric");

		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//transaction_initiated_400_schema.json")), response.extract().body().asString(), "productValueFieldWithAlphaNumericCharacters_Negative,expectedJsonSchema");

	}


	public void transactionTypeFieldEmpty_Negative() throws Exception {
		//		long startTime=System.currentTimeMillis();
		Object[][] data = dataProvider.TxnInitiateAPIData("transactiontypefieldempty");
		ValidatableResponse response = Utilities.TransactionInitiateAPI(data);

		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"transactionTypeFieldEmpty_Negative,Validating 400 Bad Request");

		//		Body Validation

		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"transactionTypeFieldEmpty_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The transaction type field is required.","transactionTypeFieldEmpty_Negative,Validating transactionType should be empty");

		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//transaction_initiated_400_schema.json")), response.extract().body().asString(), "productValueFieldWithAlphaNumericCharacters_Negative,expectedJsonSchema");

	}

	public void transactionTypeFieldWithInvalidValue_Negative() throws Exception {
		//		long startTime=System.currentTimeMillis();
		Object[][] data = dataProvider.TxnInitiateAPIData("transactiontypefieldinvalid");
		ValidatableResponse response = Utilities.TransactionInitiateAPI(data);

		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"transactionTypeFieldWithInvalidValue_Negative,Validating 400 Bad Request");

		//		Body Validation

		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"transactionTypeFieldWithInvalidValue_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The selected transaction type is invalid.","transactionTypeFieldWithInvalidValue_Negative,Validating transactionTypeField should be invalid");

		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//transaction_initiated_400_schema.json")), response.extract().body().asString(), "transactionTypeFieldWithInvalidValue_Negative,expectedJsonSchema");

	}

	public void merchantOrder_Id_FieldEmpty_Negative() throws Exception {
		//		long startTime=System.currentTimeMillis();
		Object[][] data = dataProvider.TxnInitiateAPIData("merchantorderidfieldempty");
		ValidatableResponse response = Utilities.TransactionInitiateAPI(data);

		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"merchantOrder_Id_FieldEmpty_Negative,Validating 400 Bad Request");

		//		Body Validation

		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"merchantOrder_Id_FieldEmpty_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The merchant order id field is required.","merchantOrder_Id_FieldEmpty_Negative,Validating merchantOrder_Id should be empty");

		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//transaction_initiated_400_schema.json")), response.extract().body().asString(), "merchantOrder_Id_FieldEmpty_Negative,expectedJsonSchema");

	}


	public void upiHandleReferenceNumberFieldEmpty_Negative() throws Exception {
		//		long startTime=System.currentTimeMillis();
		Object[][] data = dataProvider.TxnInitiateAPIData("upihandlereferencenumberfieldempty");
		ValidatableResponse response = Utilities.TransactionInitiateAPI(data);

		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"upiHandleReferenceNumberFieldEmpty_Negative,Validating 400 Bad Request");

		//		Body Validation

		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"upiHandleReferenceNumberFieldEmpty_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The upi handle reference number field is required when transaction type is IDEOPAY_APP_SCAN_AND_PAY.","upiHandleReferenceNumberFieldEmpty_Negative,Validating upi_handle_reference_number should be empty");

		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//transaction_initiated_400_schema.json")), response.extract().body().asString(), "upiHandleReferenceNumberFieldEmpty_Negative,expectedJsonSchema");

	}

	public void latitudeField_Empty_Negative() throws Exception {
		//		long startTime=System.currentTimeMillis();
		Object[][] data = dataProvider.TxnInitiateAPIData("latitudefieldempty");
		ValidatableResponse response = Utilities.TransactionInitiateAPI(data);

		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"latitudeField_Empty_Negative,Validating 400 Bad Request");

		//		Body Validation

		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"latitudeField_Empty_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The latitude field is required.","latitudeField_Empty_Negative,Validating latitude should be empty");

		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//transaction_initiated_400_schema.json")), response.extract().body().asString(), "latitudeField_Empty_Negative,expectedJsonSchema");

	}

	public void longitudeField_Empty_Negative() throws Exception {
		//		long startTime=System.currentTimeMillis();
		Object[][] data = dataProvider.TxnInitiateAPIData("longitudefieldempty");
		ValidatableResponse response = Utilities.TransactionInitiateAPI(data);

		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"longitudeField_Empty_Negative,Validating 400 Bad Request");

		//		Body Validation

		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"longitudeField_Empty_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The longitude field is required.","longitudeField_Empty_Negative,Validating longitude should be empty");

		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//transaction_initiated_400_schema.json")), response.extract().body().asString(), "longitudeField_Empty_Negative,expectedJsonSchema");

	}


	public void latitudeAndLongitudeField_Empty_Negative() throws Exception {
		//		long startTime=System.currentTimeMillis();
		Object[][] data = dataProvider.TxnInitiateAPIData("latitudeandlongitudefieldempty");
		ValidatableResponse response = Utilities.TransactionInitiateAPI(data);

		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"upiHandleReferenceNumberField_Empty_Negative,Validating 400 Bad Request");

		//		Body Validation

		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"upiHandleReferenceNumberField_Empty_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The latitude field is required.,The longitude field is required.","upiHandleReferenceNumberField_Empty_Negative,Validating upiHandleReferenceNumber should be empty");

		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//transaction_initiated_400_schema.json")), response.extract().body().asString(), "upiHandleReferenceNumberField_Empty_Negative,expectedJsonSchema");


	}



}
