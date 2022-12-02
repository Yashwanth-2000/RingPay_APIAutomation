package com.business.RingPay_MerchantQRCode_Journey;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.Datasheet.RingPay_TestData_DataProvider;
import com.excel.ExcelWriteData;
import com.utility.ExtentReporter;
import com.utility.Utilities;
import com.utility.Validation;

import io.restassured.response.ValidatableResponse;

public class Repayment_Home_Screen_For_Current_Spends {

	//	Start Time
	long startTime=System.currentTimeMillis();



	RingPay_TestData_DataProvider dataProvider = new RingPay_TestData_DataProvider();

	String filePath=System.getProperty("user.dir")+"\\src\\main\\java\\com\\Datasheet\\RingPayAPI_TestData_stage.xlsx";


	public void current_Spent() throws Exception {
		//		long startTime=System.currentTimeMillis();
		Object[][] data = dataProvider.CurrentSpendAPIData("current_spend");
		ValidatableResponse response = Utilities.CurrentSpendsAPI(data);


		//		String user_token = userTokenResponse.extract().body().jsonPath().get("data.user_token");

		// fetching Mobileno
		Integer Current_Spend = response.extract().body().jsonPath().get("data.cards.bnpl.details.credit_used");
		System.out.println("Current_Spend : " + Current_Spend);


		// Data to validate
		ExcelWriteData.IntegerExcelWrite(filePath, "Validate", Current_Spend, 1, 9);

		// Data to Notify
		ExcelWriteData.IntegerExcelWrite(filePath, "Notify", Current_Spend, 1, 9);


		//Status Code Validation
		int responseBody=response.extract().statusCode();
		Validation.validatingStatusCode(responseBody,200,"Mock_User,Validating 200 Success Response");


		//Schema Validation
		Validation.assertSchemaValidation(FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//TestData//current_spends_200_schema.json")), response.extract().body().asString(), "current_Spent,expectedJsonSchema");

		//		End Time
		long endTime=System.currentTimeMillis();
		ExtentReporter.extentLogger("Time Stamp", "API RunTime 'Repayment_Home_Screen_For_Current_SpendsAPI'  : "+(endTime-startTime)+" milliseconds");

	}

}
