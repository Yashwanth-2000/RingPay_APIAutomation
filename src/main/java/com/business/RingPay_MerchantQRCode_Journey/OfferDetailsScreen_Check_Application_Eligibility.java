package com.business.RingPay_MerchantQRCode_Journey;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.utility.ExtentReporter;
import com.utility.Utilities;
import com.utility.Validation;

import io.restassured.response.ValidatableResponse;

public class OfferDetailsScreen_Check_Application_Eligibility {

	public void OfferDetailsScreen_CheckApplicationEligibility_Positive() throws Exception {

		//		Start Time
		long startTime=System.currentTimeMillis();

		//			Object[][] data = dataProvider.UpdateUserStatusAPIData("update_user_200");
		ValidatableResponse response = Utilities.CheckApplicationEligibilityAfterAddAddressAPI();

		//Status Code Validation

		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"checkApplicationEligibility_Positive,Validating 200 Success Response");


		//Body Validation
		Validation.assertRequest_IdNotNullBodyValidation(response.extract().body().jsonPath().get("request_id"),"OfferDetailsScreen_CheckApplicationEligibility_Positive,Validating request_id is not null");
		Validation.assertEquals(response.extract().body().jsonPath().get("message"),"Success","OfferDetailsScreen_CheckApplicationEligibility_Positive,Validating message should be success");
		Validation.assertEquals(response.extract().body().jsonPath().get("data.status"),"FINAL_APPROVED","OfferDetailsScreen_CheckApplicationEligibility_Positive,Validating status should be FINAL_APPROVED");
		Validation.assertTrue(response.extract().body().jsonPath().get("data.is_application_allowed"), "OfferDetailsScreen_CheckApplicationEligibility_Positive,Validating is_application_allowed should be true");
		Validation.assertEquals(response.extract().body().jsonPath().get("data.stage"),"complete","OfferDetailsScreen_CheckApplicationEligibility_Positive,Validating stage should be complete");
		Validation.assertEquals(response.extract().body().jsonPath().get("data.segment"),"SEGMENT_2","OfferDetailsScreen_CheckApplicationEligibility_Positive,Validating segment should be SEGMENT_2");


		//Schema Validation

		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//offer_details_screen_check_application_eligibility_200_schema.json")), response.extract().body().asString(), "OfferDetailsScreen_CheckApplicationEligibility_Positive,expectedJsonSchema");

		//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'OfferDetailsScreen_CheckApplicationEligibility_Positive'  : "+(endTime-startTime)+" milliseconds");


	}

}
