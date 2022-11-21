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

public class BasicDetailScreen_Create_Bnpl_Transaction {

	static RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();

	public static ValidatableResponse getApplicationToken_Positive() throws Exception {
		Object[][] data = dataProvider.CreateBnplTransactionAPIData("bnpl_200");
		ValidatableResponse response = Utilities.Create_Bnpl_TransactionAPI(data);


		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 200);
		Utilities.log.info("create_bnpl_200 , Validating 200 Success Response");
		ExtentReporter.extentLogger("create_bnpl_200", "create_bnpl_200,Validating 200 Success Response");

		//Body Validation
		assertNotNull("'request_id' is not null", response.extract().body().jsonPath().get("request_id"));
		Utilities.log.info("create_bnpl_200 , Validating request_id is not null");
		ExtentReporter.extentLogger("create_bnpl_200", "Validating request_id is not null");
		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "Success");
		Utilities.log.info("create_bnpl_200 , Validating messsage value");
		ExtentReporter.extentLogger("create_bnpl_200", "create_bnpl_200,Validating messsage value");
		Assert.assertEquals(response.extract().body().jsonPath().get("data.application.status"), "INITIATED");
		Utilities.log.info("create_bnpl_200 , Validating Status");
		ExtentReporter.extentLogger("create_bnpl_200", "create_bnpl_200,Validating Status");


		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//create_bnpl_transaction_200_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("create_bnpl_200 , expectedJsonSchema");
		ExtentReporter.extentLogger("create_bnpl_200", "create_bnpl_200,expectedJsonSchema");

		Thread.sleep(2000);

		return response;

	}

	public void sourceFieldEmptyBnpl_Negative() throws Exception {
		Object[][] data = dataProvider.CreateBnplTransactionAPIData("source_field_empty_bnpl_400");
		ValidatableResponse response = Utilities.Create_Bnpl_TransactionAPI(data);


		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 400);
		Utilities.log.info("sourceFieldEmptyBnpl_400 , Validating 400 Bad Request");
		ExtentReporter.extentLogger("sourceFieldEmptyBnpl_400", "sourceFieldEmptyBnpl_400,Validating 400 Bad Request");

		//Body Validation
		assertNotNull("'request_id' is not null", response.extract().body().jsonPath().get("request_id"));
		Utilities.log.info("sourceFieldEmptyBnpl_400 , Validating request_id is not null");
		ExtentReporter.extentLogger("sourceFieldEmptyBnpl_400", "Validating request_id is not null");
		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The source field is required.");
		Utilities.log.info("sourceFieldEmptyBnpl_400 , Validating messsage value");
		ExtentReporter.extentLogger("sourceFieldEmptyBnpl_400", "sourceFieldEmptyBnpl_400,Validating messsage");


		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//create_bnpl_transaction_400_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("sourceFieldEmptyBnpl_400 , expectedJsonSchema");
		ExtentReporter.extentLogger("sourceFieldEmptyBnpl_400", "sourceFieldEmptyBnpl_400,expectedJsonSchema");

		//		Thread.sleep(2000);


	}

	public void globalDeviceIdFieldEmptyBnpl_Negative() throws Exception {
		Object[][] data = dataProvider.CreateBnplTransactionAPIData("global_device_id_field_empty_bnpl_400");
		ValidatableResponse response = Utilities.Create_Bnpl_TransactionAPI(data);


		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 400);
		Utilities.log.info("globalDeviceIdFieldEmptyBnpl_400 , Validating 400 Bad Request");
		ExtentReporter.extentLogger("globalDeviceIdFieldEmptyBnpl_400", "globalDeviceIdFieldEmptyBnpl_400,Validating 400 Bad Request");

		//Body Validation
		assertNotNull("'request_id' is not null", response.extract().body().jsonPath().get("request_id"));
		Utilities.log.info("globalDeviceIdFieldEmptyBnpl_400 , Validating request_id is not null");
		ExtentReporter.extentLogger("globalDeviceIdFieldEmptyBnpl_400", "Validating request_id is not null");
		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The global device id field is required.");
		Utilities.log.info("globalDeviceIdFieldEmptyBnpl_400 , Validating messsage value");
		ExtentReporter.extentLogger("globalDeviceIdFieldEmptyBnpl_400", "globalDeviceIdFieldEmptyBnpl_400,Validating messsage");


		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//create_bnpl_transaction_400_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("globalDeviceIdFieldEmptyBnpl_400 , expectedJsonSchema");
		ExtentReporter.extentLogger("globalDeviceIdFieldEmptyBnpl_400", "globalDeviceIdFieldEmptyBnpl_400,expectedJsonSchema");

		//		Thread.sleep(2000);

	}

	public void productNameFieldEmptyBnpl_Negative() throws Exception {
		Object[][] data = dataProvider.CreateBnplTransactionAPIData("product_name_field_empty_bnpl_400");
		ValidatableResponse response = Utilities.Create_Bnpl_TransactionAPI(data);


		//Status Code Validation
		Assert.assertEquals(response.extract().statusCode(), 400);
		Utilities.log.info("productNameFieldEmptyBnpl_400 , Validating 400 Bad Request");
		ExtentReporter.extentLogger("productNameFieldEmptyBnpl_400", "productNameFieldEmptyBnpl_400,Validating 400 Bad Request");

		//Body Validation
		assertNotNull("'request_id' is not null", response.extract().body().jsonPath().get("request_id"));
		Utilities.log.info("productNameFieldEmptyBnpl_400 , Validating request_id is not null");
		ExtentReporter.extentLogger("productNameFieldEmptyBnpl_400", "Validating request_id is not null");
		Assert.assertEquals(response.extract().body().jsonPath().get("message"), "The merchant reference number field is required.,The site reference number field is required.,The line type reference number field is required.");
		Utilities.log.info("productNameFieldEmptyBnpl_400 , Validating messsage value");
		ExtentReporter.extentLogger("productNameFieldEmptyBnpl_400", "productNameFieldEmptyBnpl_400,Validating messsage");


		//Schema Validation
		String expectedJsonSchema = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//create_bnpl_transaction_400_schema.json"));
		assertThat(response.extract().body().asString(), JsonSchemaValidator.matchesJsonSchema(expectedJsonSchema));
		Utilities.log.info("productNameFieldEmptyBnpl_400 , expectedJsonSchema");
		ExtentReporter.extentLogger("productNameFieldEmptyBnpl_400", "productNameFieldEmptyBnpl_400,expectedJsonSchema");

		//		Thread.sleep(2000);

	}



}
