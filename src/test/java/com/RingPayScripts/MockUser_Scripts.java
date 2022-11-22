package com.RingPayScripts;

import java.io.IOException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.business.RingPay.URI.RingPay_BaseURL;
import com.business.RingPay.URI.RingPay_Endpoints;
import com.business.RingPay_MerchantQRCode_Journey.*;

public class MockUser_Scripts  {

	private com.business.RingPay_MerchantQRCode_Journey.RegisterUser_Mock_User mockuser;
	private com.business.RingPay_MerchantQRCode_Journey.RegisterUser_Onload registerUser_Onload;
	private com.business.RingPay_MerchantQRCode_Journey.RegisterUser_Get_Details_VPA getvpa;
	private com.business.RingPay_MerchantQRCode_Journey.RegisterUser_OTPSend sendotp;
	private com.business.RingPay_MerchantQRCode_Journey.RegisterUser_UserAuthenticate userauthenticate;
	private com.business.RingPay_MerchantQRCode_Journey.RegisterUser_UpdateUser updateuser;
	private com.business.RingPay_MerchantQRCode_Journey.RegisterUser_Login login;
	private com.business.RingPay_MerchantQRCode_Journey.RegisterUser_Register_User registeruser;
	private com.business.RingPay_MerchantQRCode_Journey.BasicDetailScreen_Get_User_Detail getuserdetails;
	private com.business.RingPay_MerchantQRCode_Journey.BasicDetailScreen_User_Onboarding useronboarding;
	private com.business.RingPay_MerchantQRCode_Journey.BasicDetailScreen_Create_Bnpl_Transaction createbnpl;
	private com.business.RingPay_MerchantQRCode_Journey.BasicDetailScreen_Update_User_Status updateuserstatus;
	private com.business.RingPay_MerchantQRCode_Journey.BasicDetailScreen_Add_Address addaddress;
	private com.business.RingPay_MerchantQRCode_Journey.BasicDetailScreen_Check_Application_Eligibility basic_eligibility;
	private com.business.RingPay_MerchantQRCode_Journey.OfferDetailsScreen_Get_Offer getoffer ;



	
	@Test(priority = 0)
//	@Parameters({"MOCK_USER-URI"})
	public void userDetailsAPI() throws Exception {
		
		mockuser=new com.business.RingPay_MerchantQRCode_Journey.RegisterUser_Mock_User();
		mockuser.mock_User_200();
	}


	@Test(priority = 1)
//	@Parameters({"ONLOAD-URI"})
	public void onloadAndroidVersionCheckAPI() throws Exception {
		registerUser_Onload=new com.business.RingPay_MerchantQRCode_Journey.RegisterUser_Onload();
		registerUser_Onload.onload_Positive();
	}
	

	@Test(priority = 2)
//	@Parameters({"GET_DETAILS_VPN-URI"})
	public void getVPADetails() throws Exception {
		
		getvpa=new com.business.RingPay_MerchantQRCode_Journey.RegisterUser_Get_Details_VPA();
		getvpa.get_Details_Vpa_Positive();
	}


	@Test(priority = 3)
//	@Parameters({"OTP-URI"})
	public void sendOtp_Positive() throws Exception {

		sendotp=new com.business.RingPay_MerchantQRCode_Journey.RegisterUser_OTPSend();
		
		sendotp.validMobileNo_Positive();
		System.out.println("Valid_Otp is Done");
		
		sendotp.mobileNoLessThan10Digit_Negative();
		System.out.println("lessthan10digit_400 is Done");
		sendotp.mobileNoMoreThan10Digit_Negative();
		System.out.println("morethan10digit_400 is Done");
		sendotp.specialCharacterInMobileNoField_Negative();
		System.out.println("specialCharacterInField_400 is Done");
		sendotp.validMobileNo_Positive();
		System.out.println("Valid_Otp Repeated is Done");
	}



	@Test(priority = 4)
//	@Parameters({"OTP-URI","AuthenticationURI"})
	public void userToken() throws Exception {

		userauthenticate=new com.business.RingPay_MerchantQRCode_Journey.RegisterUser_UserAuthenticate();

		userauthenticate.userToken_Positive();
		System.out.println("Valid_Otp and Auth is Done");

		userauthenticate.invalidOtp_Negative();
		System.out.println("invalidotp_auth_400 is Done");

		userauthenticate.expiredOtp_Negative();
		System.out.println("expiredotp_auth_400 is Done");
		userauthenticate.alphabetInOtpField_Negative();
		System.out.println("alphabetinotpfield_auth_400 is Done");
		userauthenticate.lessThan6DigitsNoInOtpField_Negative();
		System.out.println("lessthan6digitotp_auth_400 is Done");
		userauthenticate.userToken_Positive();
		System.out.println("Valid_Otp and Auth is Done");
	}
	
	

	@Test(priority = 5)
//	@Parameters({"OTP-URI","AuthenticationURI","UpdateUser-URI"})
	public void updateUserDetils_200() throws Exception {

		updateuser=new com.business.RingPay_MerchantQRCode_Journey.RegisterUser_UpdateUser();


		updateuser.updateUser_Positive_SchemaValiadtion();
		System.out.println("UpdateUser_200,Schema validation");
		updateuser.alphaNumericInFirstNameField_Negative();
		System.out.println("alphaNumericInFirstNameField_400,validation is Done");
		updateuser.specialCharacterInFirstNameField_Negative();
		System.out.println("specialCharacterInFirstNameField_400,validation is Done");

		updateuser.spaceInFirstNameField_Negative();
		System.out.println("spaceInFirstNameField_400,validation is Done");

		updateuser.updateUser_Positive();
		System.out.println("UpdateUser_200,validation is Done");

		updateuser.alphaNumericInLastNameField_Negative();
		System.out.println("alphaNumericInLastNameField_400,validation is Done");

		updateuser.specialCharacterInLastNameField_Negative();
		System.out.println("specialCharacterInLastNameField_400,validation is Done");

		updateuser.spaceInLastNameField_Negative();
		System.out.println("spaceInLastNameField_400,validation is Done");

		updateuser.updateUser_Positive();
		System.out.println("UpdateUser_200,validation is Done");

		updateuser.invalidEmailId_Negative();
		System.out.println("invalidEmailField_400,validation is Done");

		updateuser.spaceInEmailIdField_Negative();
		System.out.println("spaceInEmailField_400,validation is Done");

		updateuser.updateUser_Positive();
		System.out.println("UpdateUser_200,validation is Done");


	}

	@Test(priority = 6)
//	@Parameters({"OTP-URI","AuthenticationURI","UpdateUser-URI","Login-URI"})
	public void loginUser() throws Exception{
		login=new com.business.RingPay_MerchantQRCode_Journey.RegisterUser_Login();

		login.login_Positive();
		System.out.println("login_200,validation is Done");
	}


	@Test(priority = 7)
//	@Parameters({"OTP-URI","AuthenticationURI","UpdateUser-URI","Register_User-URI"})
	public void registeruser() throws Exception {

		registeruser=new com.business.RingPay_MerchantQRCode_Journey.RegisterUser_Register_User();
		System.out.println("registeruser,validation is Done");

		registeruser.registerUserAfterLogin_Positive();
		System.out.println("registeruser,validation is Done");

	}

////	////	 ====================== Basic_Detail_Screen =================================
 
	@Test(priority = 8)
//	@Parameters({"OTP-URI","AuthenticationURI","UpdateUser-URI","Register_User-URI"})
	public void getUserDetails_Positive() throws Exception {

		getuserdetails=new com.business.RingPay_MerchantQRCode_Journey.BasicDetailScreen_Get_User_Detail();
		System.out.println("getUserDetails_Positive,validation is Done");

		getuserdetails.getUserDetails_Positive();
		System.out.println("getUserDetails_Positive,validation is Done");

	}
	
	
	
	

	@Test(priority = 9)
//	@Parameters({"OTP-URI","AuthenticationURI","UpdateUser-URI","UserOnboarding-URI"})
	public void user_onboarding_200() throws Exception {

		useronboarding=new com.business.RingPay_MerchantQRCode_Journey.BasicDetailScreen_User_Onboarding();

		useronboarding.userOnbording_Positive();
		System.out.println("user_Onboarding_200,validation is Done");

		useronboarding.latitudeFieldEmpty_Negative();
		System.out.println("latitudeFieldEmpty_400,validation is Done");

		useronboarding.longitudeFieldEmpty_Negative();
		System.out.println("longitudeFieldEmpty_400,validation is Done");

		useronboarding.advertisingIdFieldEmpty_Negative();
		System.out.println("advertisingIdFieldEmpty_400,validation is Done");

		useronboarding.androidIdFieldEmpty_Negative();
		System.out.println("androidIdFieldEmpty_400,validation is Done");

		useronboarding.globalDeviceIdFieldEmpty_Negative();
		System.out.println("globalDeviceIdFieldEmpty_400,validation is Done");

		useronboarding.latitudeAndLongitudeFieldEmpty_Negative();
		System.out.println("latitudeAndLongitudeFieldEmpty_400,validation is Done");

		useronboarding.latitudeFieldWithAlphaNumericKeywords_Negative();
		System.out.println("latitudeFieldWithAlphaNumericKeywords_400,validation is Done");

		useronboarding.userOnbording_Positive();
		System.out.println("user_Onboarding_200,validation is Done");

	}

	@Test(priority = 10)
//	@Parameters({"OTP-URI","AuthenticationURI","UpdateUser-URI","Basic_CreateBnplTransaction-URI"})
	public void create_bnpl_transaction() throws Exception {

		createbnpl=new com.business.RingPay_MerchantQRCode_Journey.BasicDetailScreen_Create_Bnpl_Transaction();

		createbnpl.getApplicationToken_Positive();
		System.out.println("bnpl_200,validation is Done");
		createbnpl.sourceFieldEmptyBnpl_Negative();
		System.out.println("sourceFieldEmptyBnpl_400,validation is Done");
		createbnpl.globalDeviceIdFieldEmptyBnpl_Negative();
		System.out.println("globalDeviceIdFieldEmptyBnpl_400,validation is Done");
		createbnpl.productNameFieldEmptyBnpl_Negative();
		System.out.println("productNameFieldEmptyBnpl_400,validation is Done");
		createbnpl.getApplicationToken_Positive();
		System.out.println("bnpl_200,validation is Done");
		
	}

	@Test(priority = 11)
//	@Parameters({"OTP-URI","AuthenticationURI","UpdateUser-URI","UpdateUserStatus-URI"})
	public void upadte_user_status_200() throws Exception {

		updateuserstatus=new com.business.RingPay_MerchantQRCode_Journey.BasicDetailScreen_Update_User_Status();
		
		updateuserstatus.updateUserStatus_Positive();
		System.out.println("updateuserstatus,validation is Done");

	}

//	 PreCondition for  Basic_Details_Screen - Check Application Eligibility 
	
	@Test(priority = 12)
//	@Parameters({"OTP-URI","AuthenticationURI","UpdateUser-URI","AddAddress-URI"})
	public void add_address() throws Exception {

		addaddress=new com.business.RingPay_MerchantQRCode_Journey.BasicDetailScreen_Add_Address();
		
		addaddress.addAddress();
		System.out.println("add_address,validation is Done");


	}
	
	
	@Test(priority = 13)
//	@Parameters({"OTP-URI","AuthenticationURI","UpdateUser-URI","Basic_CreateBnplTransaction-URI","Check_Application_Eligibility-URI"})
	public void check_Application_Eligibility() throws Exception {

		basic_eligibility=new com.business.RingPay_MerchantQRCode_Journey.BasicDetailScreen_Check_Application_Eligibility();
		
		basic_eligibility.checkApplicationEligibility_Positive();
		System.out.println("basic_check_eligibility,validation is Done");

	}
	
	@Test(priority = 14)
//	@Parameters({"OTP-URI","AuthenticationURI","UpdateUser-URI","Basic_CreateBnplTransaction-URI","Check_Application_Eligibility-URI"})
	public void get_Offer_Details() throws Exception {

		getoffer=new com.business.RingPay_MerchantQRCode_Journey.OfferDetailsScreen_Get_Offer();
		
		getoffer.get_Offer();
		System.out.println("basic_get_Offer_Details,validation is Done");

	}
	
	
	
}


