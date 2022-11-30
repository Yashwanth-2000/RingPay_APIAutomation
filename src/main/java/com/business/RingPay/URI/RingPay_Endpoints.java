package com.business.RingPay.URI;

public class RingPay_Endpoints {

	public static String mockUserEndPoint="/api/v1/mock-data/user";
	public static String onloadEndPoint="/api/v1/onload/data";
	public static String getdetailsVPAEndPoint="/api/v1/merchants/details/get-by-vpa?vpa=rpy.ipmerchant1234193162@icici";
	public static String sendOtpEndPoint="/api/v1/users/android/send-otp";
	public static String userAuthenticateEndPoint="/api/v1/users/android/authentication";
	public static String updateUserEndPoint="/api/v1/users";
	public static String loginEndPoint="/api/v1/users/login";
	public static String registerUserEndPoint="/api/v2/customer/register";
	public static String getDetailsEndPoint="/api/v1/users/account";
	public static String userOnbordingEndPoint="/api/v1/users/onboarding";
	public static String createBnplTransactionEndPoint="/api/v1/applications/create";
	public static String updateUserStatusEndPoint="/api/v1/users";
	public static String checkApplicationEligibilityEndPoint="/api/v1/applications/check-application-eligibility";
	public static String addAddressEndPoint="/api/v1/users/applications/address";
	public static String getOffersEndPoint="/api/v1/applications/line/offer";
	public static String userConcentEndPoint="/api/v1/applications/user-consent";

	public static String bnplLinesEndPoint="/api/v1/bnpl/lines";
	public static String paymentOptionEndPoint="/api/v1/users/get-payment-options";
	public static String txnInitiateEndPoint="/api/v1/bnpl/transactions/initiate";
	public static String txnCompleteEndPoint="/api/v1/bnpl/transactions/complete";

	public static String currentSpendsEndPoint="/api/v1/users/home-screen";
	public static String validateEndPoint="/api/v1/yesbank/vpa-webhook/validate";
	public static String notifyEndPoint="/api/v1/yesbank/vpa-webhook/notify";
	public static String getSettlementEndPoint="/api/v1/bnpl/transactions/";

	
	
	
	
	
}
