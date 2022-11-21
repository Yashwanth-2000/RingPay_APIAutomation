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

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;

public class BasicDetailScreen_Check_Application_Eligibility {

	//	 RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();

	public void checkApplicationEligibility_Positive() throws Exception {

		
		//			Object[][] data = dataProvider.UpdateUserStatusAPIData("update_user_200");
		ValidatableResponse response = Utilities.CheckApplicationEligibilityAPI();

		
		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 200);
		Utilities.log.info("checkApplicationEligibility_200 , Validating 200 Success Response");
		ExtentReporter.extentLogger("checkApplicationEligibility_200", "checkApplicationEligibility_200,Validating 200 Success Response");

		//Body Validation
		assertNotNull("'request_id' is not null", response.extract().body().jsonPath().get("request_id"));
		Utilities.log.info("checkApplicationEligibility_200 , Validating request_id is not null");
		ExtentReporter.extentLogger("checkApplicationEligibility_200", "Validating request_id is not null");
		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "Success");
		Utilities.log.info("checkApplicationEligibility_200 , Validating messsage value");
		ExtentReporter.extentLogger("checkApplicationEligibility_200", "checkApplicationEligibility_200,Validating messsage value");
		Assert.assertEquals(response.extract().body().jsonPath().get("data.status"), "COND_APPROVED");
		Utilities.log.info("checkApplicationEligibility_200 , Validating Status");
		ExtentReporter.extentLogger("checkApplicationEligibility_200", "checkApplicationEligibility_200,Validating Status");
		Assert.assertTrue(response.extract().body().jsonPath().get("data.is_application_allowed"));
		Utilities.log.info("checkApplicationEligibility_200 , Validating Status");
		ExtentReporter.extentLogger("checkApplicationEligibility_200", "checkApplicationEligibility_200,Validating is_application_allowed should be true");
		Assert.assertEquals(response.extract().body().jsonPath().get("data.stage"), "acceptance_pending");
		Utilities.log.info("checkApplicationEligibility_200 , Validating Status");
		ExtentReporter.extentLogger("checkApplicationEligibility_200", "checkApplicationEligibility_200,Validating Stage Should be acceptance_pending");
//		Segment 2
		Assert.assertEquals(response.extract().body().jsonPath().get("data.segment"), "SEGMENT_2");
		Utilities.log.info("checkApplicationEligibility_200 , Validating Status");
		ExtentReporter.extentLogger("checkApplicationEligibility_200", "checkApplicationEligibility_200,Validating Segment Should be SEGMENT_2");
		
		
		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//check_application_eligibility_200_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("checkApplicationEligibility_200 , expectedJsonSchema");
		ExtentReporter.extentLogger("checkApplicationEligibility_200", "checkApplicationEligibility_200,expectedJsonSchema");


	}

}
