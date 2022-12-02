package com.business.RingPay_MerchantQRCode_Journey;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.Datasheet.RingPay_TestData_DataProvider;
import com.utility.ExtentReporter;
import com.utility.Utilities;
import com.utility.Validation;

import io.restassured.response.ValidatableResponse;

public class Bnpl_Txn_Payment_Option {

	static RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();


	public static ValidatableResponse paymentOption_Positive() throws Exception {

		//		Start Time
		long startTime=System.currentTimeMillis();


		Object[][] data = dataProvider.PaymentOptionAPIData("paymentoption");
		ValidatableResponse response = Utilities.PaymentOptionAPI(data);


		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"paymentOption_Positive,Validating 200 Success Response");


		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"paymentOption_Positive,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","paymentOption_PositivepaymentOption_Positive,Validating message should be success");
		Validation.assertTrue(response.extract().body().jsonPath().get("success"), "paymentOption_Positive,Validating success Should be true");

		String bnpl = response.extract().body().jsonPath().get("data.allow_modes.mode[0]");
		System.out.println("bnpl " +bnpl);
		Validation.assertEquals(bnpl,"BNPL_CREDIT","paymentOption_PositivepaymentOption_Positive,Validating message should be success");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//payment_option_200_schema.json")), response.extract().body().asString(), "paymentOption_Positive,expectedJsonSchema");

		//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'paymentOption_Positive'  : "+(endTime-startTime)+" milliseconds");


		return response;

	}

	public static ValidatableResponse reasonFieldIsEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.PaymentOptionAPIData("reasonfieldempty");
		ValidatableResponse response = Utilities.PaymentOptionAPI(data);


		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"reasonFieldIsEmpty_Negative,Validating 400 Bad Request");


		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"reasonFieldIsEmpty_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The reason field is required.","reasonFieldIsEmpty_Negative,Validating message should be The reason field is required.");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//payment_option_400_schema.json")), response.extract().body().asString(), "reasonFieldIsEmpty_Negative,expectedJsonSchema");

		return response;

	}

	public static ValidatableResponse actualAmountFieldIsEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.PaymentOptionAPIData("actualamountfieldempty");
		ValidatableResponse response = Utilities.PaymentOptionAPI(data);


		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"actualAmountFieldIsEmpty_Negative,Validating 400 Bad Request");


		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"actualAmountFieldIsEmpty_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The actual amount field is required.","actualAmountFieldIsEmpty_Negative,Validating message should be The actual amount field is required.");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//payment_option_400_schema.json")), response.extract().body().asString(), "actualAmountFieldIsEmpty_Negative,expectedJsonSchema");

		return response;

	}

	public static ValidatableResponse qr_CodeFieldIsEmpty_Negative() throws Exception {

		Object[][] data = dataProvider.PaymentOptionAPIData("qr_codefieldempty");
		ValidatableResponse response = Utilities.PaymentOptionAPI(data);


		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"qr_CodeFieldIsEmpty_Negative,Validating 400 Bad Request");


		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"qr_CodeFieldIsEmpty_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"The qr code field is required when reason is SCAN_AND_PAY.","qr_CodeFieldIsEmpty_Negative,Validating message should be The qr code field is required when reason is SCAN_AND_PAY.");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//payment_option_400_schema.json")), response.extract().body().asString(), "qr_CodeFieldIsEmpty_Negative,expectedJsonSchema");

		return response;

	}


	public static ValidatableResponse qr_CodeFieldWithIncorrectVPA_Negative() throws Exception {

		Object[][] data = dataProvider.PaymentOptionAPIData("qr_codefieldwithinvalidvpa");
		ValidatableResponse response = Utilities.PaymentOptionAPI(data);


		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"qr_CodeFieldWithIncorrectVPA_Negative,Validating 400 Bad Request");


		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"qr_CodeFieldWithIncorrectVPA_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Incorrect VPA","qr_CodeFieldWithIncorrectVPA_Negative,Validating message should be Incorrect VPA");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//payment_option_400_schema.json")), response.extract().body().asString(), "qr_CodeFieldWithIncorrectVPA_Negative,expectedJsonSchema");

		return response;

	}

	public static ValidatableResponse qr_CodeFieldWithInvalidCode_Negative() throws Exception {

		Object[][] data = dataProvider.PaymentOptionAPIData("qr_codefieldwithinvalidcode");
		ValidatableResponse response = Utilities.PaymentOptionAPI(data);


		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,400,"qr_CodeFieldWithInvalidCode_Negative,Validating 400 Bad Request");


		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"qr_CodeFieldWithInvalidCode_Negative,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Invalid qr code","qr_CodeFieldWithInvalidCode_Negative,Validating message should be Invalid qr code");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//payment_option_400_schema.json")), response.extract().body().asString(), "qr_CodeFieldWithInvalidCode_Negative,expectedJsonSchema");

		return response;

	}


}
