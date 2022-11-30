package com.utility;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertNotNull;

import java.util.List;

//import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.Assert;

import io.restassured.module.jsv.JsonSchemaValidator;
//import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;

public class Validation {


	public static void assertSchemaValidation(String key,String Actual,String message) throws Exception {

		try {

			String expectedJsonSchema = key;
			assertThat(Actual, JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
			Utilities.log.info(message);
			ExtentReporter.extentLogger("", "message");
		} 
		catch (Exception e) {
			ExtentReporter.extentLoggerFail(message+" - Failed");	
		}
		catch (AssertionError e) {
			ExtentReporter.extentLoggerFail(message+" - Failed");	
		}

	}	


	public static void validatingStatusCode (int actualrespcode, int expectedresponsecode, String message) throws Exception {
		if(actualrespcode==expectedresponsecode) {
			Assert.assertEquals(actualrespcode, expectedresponsecode);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}		 
		else
		{
			ExtentReporter.extentLoggerFail(message+" - Failed");
		}

	}


	//	public static void validating400StatusCode (int actualrespcode, int expectedresponsecode, String message) throws Exception {
	//		if(actualrespcode==expectedresponsecode) {
	//			Assert.assertEquals(actualrespcode, expectedresponsecode);
	//			Utilities.log.info(message);
	//			ExtentReporter.extentLoggerPass(message+" - Passed");
	//		}		 
	//		else
	//		{
	//			ExtentReporter.extentLoggerFail(message+" - Failed");
	//		}
	//
	//	}
	//	

	public static void assertEquals(String key,String responseValue,String message) throws Exception {


		if(responseValue.equals(key))
		{

			Assert.assertEquals(key,responseValue);
		Utilities.log.info(message);
		ExtentReporter.extentLoggerPass(message+" - Passed");
		}


		else
		{
			ExtentReporter.extentLoggerFail(message+" - Failed");
		}

	}


	public static void assertEqualsStatus(String key,String statusInitiated,String statusCOND_APPROVED,String statusFINAL_APPROVED,String message) throws Exception {

		//	String value=responseValue;

		if(statusInitiated.equals(key))

		{
			Assert.assertEquals(key,statusInitiated);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}


		else if(statusCOND_APPROVED.equals(key))

		{
			Assert.assertEquals(key,statusCOND_APPROVED);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}

		else if(statusFINAL_APPROVED.equals(key))

		{
			Assert.assertEquals(key,statusFINAL_APPROVED);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}

		else
		{
			ExtentReporter.extentLoggerFail(message+" - Failed");
		}


	}

	public static void assertEqualsStage(String key,String statusAddress_details_pending,String statusAcceptance_pending,String statusComplete,String message) throws Exception {

		//	String value=responseValue;

		if(statusAddress_details_pending.equals(key))

		{
			Assert.assertEquals(key,statusAddress_details_pending);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}


		else if(statusAcceptance_pending.equals(key))

		{
			Assert.assertEquals(key,statusAcceptance_pending);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}

		else if(statusComplete.equals(key))

		{
			Assert.assertEquals(key,statusComplete);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}

		else
		{
			ExtentReporter.extentLoggerFail(message+" - Failed");
		}


	}


	//	public static void assertEqualsValidatingActiveValue (String key,String responseValue,String message) throws Exception {
	//
	//		//		String value=responseValue;
	//
	//		if(responseValue.equals(key)) {
	//
	//			Assert.assertEquals(key, responseValue);
	//			Utilities.log.info(message);
	//			ExtentReporter.extentLoggerPass(message+" - Passed");
	//		}		 
	//		else
	//		{
	//			ExtentReporter.extentLoggerFail(message+" - Failed");
	//		}
	//
	//
	//	}


	public static void assertRequest_IdNotNullBodyValidation(String key,String message) throws Exception {
		//		ValidatableResponse responseBody = Utilities.OnloadAPI();

		System.out.println(key);
		//		String request_id=responseBody.extract().body().jsonPath().get("request_id");
		if(key!="") 
		{

			Assert.assertNotNull(key);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}		 
		else
		{
			ExtentReporter.extentLoggerFail(message+" - Failed");
		}


	}


	public static void assertNull(String key,String message) throws Exception {
		//		ValidatableResponse responseBody = Utilities.OnloadAPI();

		System.out.println(key);
		//		String request_id=responseBody.extract().body().jsonPath().get("request_id");
		if(key!="") 
		{

			Assert.assertNull(key);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}		 
		else
		{
			ExtentReporter.extentLoggerFail(message+" - Failed");
		}


	}



	public static void assertIntegerNotNullBodyValidation(Integer key,String message) throws Exception {

		if(Integer.valueOf(key) != null)
		{
			Assert.assertNotNull(key);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}		 
		else
		{
			ExtentReporter.extentLoggerFail(message+" - Failed");
		}


	}

	public static void assertLongNotNullBodyValidation(Long key,String message) throws Exception {

		if(Long.valueOf(key) != null)
		{
			Assert.assertNotNull(key);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}		 
		else
		{
			ExtentReporter.extentLoggerFail(message+" - Failed");
		}


	}

	public static void assertTrue(Boolean key,String message) throws Exception {
		if(key==true) {

			Assert.assertTrue(key);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}		 
		else
		{
			ExtentReporter.extentLoggerFail(message+" - Failed");
		}

	}

	public static void assertFalse(Boolean key,String message) throws Exception {
		if(key==false) {

			Assert.assertFalse(key);
			Utilities.log.info(message);
			ExtentReporter.extentLoggerPass(message+" - Passed");
		}		 
		else
		{
			ExtentReporter.extentLoggerFail(message+" - Failed");
		}

	}



}
