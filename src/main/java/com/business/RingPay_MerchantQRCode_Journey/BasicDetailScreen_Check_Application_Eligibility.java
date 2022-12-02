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

public class BasicDetailScreen_Check_Application_Eligibility {

	//	 RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();

	public static ValidatableResponse checkApplicationEligibility_Positive() throws Exception {

		//		Start Time
		long startTime=System.currentTimeMillis();

		//			Object[][] data = dataProvider.UpdateUserStatusAPIData("update_user_200");
		ValidatableResponse response = Utilities.CheckApplicationEligibilityAPI();


		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"checkApplicationEligibility_Positive,Validating 200 Success Response");


		//Body Validation
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"checkApplicationEligibility_Positive,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","checkApplicationEligibility_Positive,Validating message should be success");
		//			public static void assertEqualsStatus(String key,String statusInitiated,String statusCOND_APPROVED,String statusFINAL_APPROVED,String message) throws Exception {

		Validation.assertEqualsStatus(response.extract().body().jsonPath().get("data.status"),"INITIATED","COND_APPROVED","FINAL_APPROVED","checkApplicationEligibility_Positive,Validating status should be COND_APPROVED");
		Validation.assertTrue(response.extract().body().jsonPath().get("data.is_application_allowed"), "checkApplicationEligibility_Positive,Validating is_application_allowed should be true");
		//		public static void assertEqualsStage(String key,String statusAddress_details_pending,String statusAcceptance_pending,String statusComplete,String message) throws Exception {

		Validation.assertEqualsStage(response.extract().body().jsonPath().get("data.stage"),"address_details_pending","acceptance_pending","complete","checkApplicationEligibility_Positive,Validating stage should be acceptance_pending");
		Validation.assertEquals(response.extract().body().jsonPath().get("data.segment"),"SEGMENT_2","checkApplicationEligibility_Positive,Validating segment should be SEGMENT_2");

//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'checkApplicationEligibility_Positive'  : "+(endTime-startTime)+" milliseconds");


		return response;

	}


	public void checkApplicationEligibilitySchemaValidation_Positive() throws Exception {

		//		Start Time
		long startTime=System.currentTimeMillis();


		ValidatableResponse response = Utilities.CheckApplicationEligibilityAPI();


		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"checkApplicationEligibility_Positive,Validating 200 Success Response");


		//Body Validation
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"checkApplicationEligibility_Positive,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","checkApplicationEligibility_Positive,Validating message should be success");
		//			public static void assertEqualsStatus(String key,String statusInitiated,String statusCOND_APPROVED,String statusFINAL_APPROVED,String message) throws Exception {

		Validation.assertEqualsStatus(response.extract().body().jsonPath().get("data.status"),"INITIATED","COND_APPROVED","FINAL_APPROVED","checkApplicationEligibility_Positive,Validating status should be COND_APPROVED");
		Validation.assertTrue(response.extract().body().jsonPath().get("data.is_application_allowed"), "checkApplicationEligibility_Positive,Validating is_application_allowed should be true");
		//		public static void assertEqualsStage(String key,String statusAddress_details_pending,String statusAcceptance_pending,String statusComplete,String message) throws Exception {

		Validation.assertEqualsStage(response.extract().body().jsonPath().get("data.stage"),"address_details_pending","acceptance_pending","complete","checkApplicationEligibility_Positive,Validating stage should be acceptance_pending");
		Validation.assertEquals(response.extract().body().jsonPath().get("data.segment"),"SEGMENT_2","checkApplicationEligibility_Positive,Validating segment should be SEGMENT_2");


		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//check_application_eligibility_200_schema.json")), response.extract().body().asString(), "checkApplicationEligibility_Positive,expectedJsonSchema");

		//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'checkApplicationEligibilitySchemaValidation_Positive'  : "+(endTime-startTime)+" milliseconds");

	}


	public void checkApplicationEligibilityAfterAddAddress_Positive() throws Exception {

		//		Start Time
		long startTime=System.currentTimeMillis();

		//			Object[][] data = dataProvider.UpdateUserStatusAPIData("update_user_200");
		ValidatableResponse response = Utilities.CheckApplicationEligibilityAPI();


		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"checkApplicationEligibility_Positive,Validating 200 Success Response");


		//Body Validation
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"checkApplicationEligibility_Positive,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","checkApplicationEligibility_Positive,Validating message should be success");
		//			public static void assertEqualsStatus(String key,String statusInitiated,String statusCOND_APPROVED,String statusFINAL_APPROVED,String message) throws Exception {

		Validation.assertEqualsStatus(response.extract().body().jsonPath().get("data.status"),"INITIATED","COND_APPROVED","FINAL_APPROVED","checkApplicationEligibility_Positive,Validating status should be COND_APPROVED");
		Validation.assertTrue(response.extract().body().jsonPath().get("data.is_application_allowed"), "checkApplicationEligibility_Positive,Validating is_application_allowed should be true");
		//		public static void assertEqualsStage(String key,String statusAddress_details_pending,String statusAcceptance_pending,String statusComplete,String message) throws Exception {

		Validation.assertEqualsStage(response.extract().body().jsonPath().get("data.stage"),"address_details_pending","acceptance_pending","complete","checkApplicationEligibility_Positive,Validating stage should be acceptance_pending");
		Validation.assertEquals(response.extract().body().jsonPath().get("data.segment"),"SEGMENT_2","checkApplicationEligibility_Positive,Validating segment should be SEGMENT_2");


		//Schema Validation

		//		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//check_application_eligibilityAfterAddAddress_200_schema.json")), response.extract().body().asString(), "checkApplicationEligibility_Positive,expectedJsonSchema");

		//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'checkApplicationEligibilityAfterAddAddress_Positive'  : "+(endTime-startTime)+" milliseconds");


	}


}
