package manage;

import bean.Message;

public class Messages
{
	public Messages()
	{}
	
	
	//
	//  INFORMATION
	//
	public static final String MESSAGE_CAMPUS_ADDED = "Campus has been added";
	public static final String MESSAGE_CAMPUS_DELETED = "Campus has been deleted";
	public static final String MESSAGE_CAMPUS_SAVE = "Campus has been saved";
	
	public static final String MESSAGE_STORE_ADDED = "Store has been added";
	public static final String MESSAGE_STORE_DELETED = "Store has been deleted";
	public static final String MESSAGE_STORE_SAVE = "Store has been saved";
	
	public static final String MESSAGE_BRAND_ADDED = "Brand has been added";
	public static final String MESSAGE_BRAND_DELETED = "Brand has been deleted";
	public static final String MESSAGE_BRAND_SAVE = "Brand has been saved";
	
	public static final String MESSAGE_PRODUCT_ADDED = "Product has been added";
	public static final String MESSAGE_PRODUCT_DELETED = "Product has been deleted";
	public static final String MESSAGE_PRODUCT_SAVE = "Product has been saved";
	
	public static final String MESSAGE_ITEM_ADDED = "Item has been added";
	public static final String MESSAGE_ITEM_DELETED = "Item has been deleted";
	public static final String MESSAGE_ITEM_SAVE = "Item has been saved";
	
	public static final String MESSAGE_STORE_ITEM_SAVE = "The store's items have been saved";
	
	public static final String MESSAGE_LOGIN_SUCCESSFUL = "Login Successful";
	public static final String MESSAGE_LOGOUT_SUCCESSFUL = "Logout Successful";
	public static final String MESSAGE_EMAIL_SENT = "An authentication email has been sent to the email address";
	public static final String MESSAGE_EMAIL_RESENT = "This email address is awaiting authentication, the email has been resent";
	public static final String MESSAGE_SUBMITTER_ADDED = "Your email has been added, and you are logged in";
	
	//
	//  VALIDATION
	//
	public static final String VALIDATE_USER_MISSING = "The email address was not found in our database";
	public static final String VALIDATE_INCORRECT_PASSWORD = "Incorrect password";
	public static final String VALIDATE_USER_ID = "You must provide a valid email address";
	public static final String VALIDATE_PASSWORD = "You must enter a password";
	public static final String VALIDATE_USER_ID_TAKEN = "That email address has already has a login";
	public static final String VALIDATE_AUTHENTICATION_FAILED = "The authentication has failed, please try again";
	//
	public static final String VALIDATE_STATE = "You must select a state";
	public static final String VALIDATE_NAME = "Requires a name";
	public static final String VALIDATE_CITY = "You must fill in a city";
	public static final String VALIDATE_ADDRESS = "Address cannot be blank";
	public static final String VALIDATE_PHONE = "Phone cannot be blank";
	public static final String VALIDATE_ZIP = "Zip cannot be blank";
	public static final String VALIDATE_LIST_PRICE = "One or more of the items has an invalid price";
	public static final String VALIDATE_BRAND = "You must select a brand";
	public static final String VALIDATE_CONTAINER = "You must select a container";
	public static final String VALIDATE_PRICE = "The price must be valid";
	public static final String VALIDATE_QUANTITY = "Please enter a quantity";
	public static final String VALIDATE_TYPE = "You must select a type";
	public static final String VALIDATE_SPECIAL_START = "Special Start Date must be valid";
	public static final String VALIDATE_SPECIAL_STOP = "Special Stop Date must be valid, and cannot be in the past";
	public static final String VALIDATE_SPECIAL_PRICE = "Special Price must be valid";
	public static final String VALIDATE_SPECIAL_DATES = "The Special Start Date cannot be after the Special Stop Date";
	public static final String VALIDATE_SPECIAL_PRICE_HIGHER = "The Special Price cannot be higher than the normal Price";
	
	public static final String VALIDATE_NO_RESULTS = "No results were found for your search";
	
	
	//
	public static final String INFORMATION = "information";
	public static final String VALIDATION = "validation";
	
	public static Message createValidationMessage(String value)
	{
		return new Message(value, Messages.VALIDATION);
	}
	
	public static Message createInformationMessage(String value)
	{
		return new Message(value, Messages.INFORMATION);
	}
}
