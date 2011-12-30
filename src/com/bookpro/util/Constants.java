package com.bookpro.util;

import android.os.Environment;

public class Constants {
	public static final String URL_ROOT = "http://192.168.1.2/dtui/";
	public static final String URL_LOGIN = URL_ROOT + "login.php";
	public static final String URL_LOGOUT = URL_ROOT + "logout.php";
	public static final String URL_SIGNUP = URL_ROOT + "register.php";
	public static final String URL_PROFILE = URL_ROOT + "view_info.php";
	public static final String URL_CHANGE_PROFILE =URL_ROOT + "update_info.php";
	public static final String URL_RANDOM = URL_ROOT + "ramdom.php";
	
	public static final String USERNAME_PATTERN = "^[a-zA-Z0-9-_]{6,30}$";
	public static final String EMAIL_PATTERN = 
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static final String LIBRARY_PATH = 
		Environment.getExternalStorageDirectory() + "/bookstore";
	
	public static final String LAST_READ_BOOK = "LastReadBook";
	
	//login
	public static final String SEND_LOGIN_USERNAME = "username";
	public static final String SEND_LOGIN_PASSWORD = "password";
	public static final String RECV_LOGIN_USERNAME = "username";
	public static final String RECV_LOGIN_LOG = "log";
	
	//signup
	public static final String SEND_SIGNUP_USERNAME = "username";
	public static final String SEND_SIGNUP_NAME = "name";
	public static final String SEND_SIGNUP_PASSWORD = "password";
	public static final String SEND_SIGNUP_EMAIL = "email";	
	
	//profile
	public static final String SEND_PROFILE_PASSWORD = "password";
	public static final String SEND_PROFILE_NAME = "name";
	public static final String SEND_PROFILE_EMAIL = "email";
	public static final String SEND_PROFILE_ADDRESS = "address";
	
	// Don vi tien
	public static final String UNIT_MONEY = "000 VND";
}
