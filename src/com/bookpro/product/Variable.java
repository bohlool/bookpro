package com.bookpro.product;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.bookpro.util.Constants;

public class Variable {
	
	public static boolean login = false;
	public static boolean acount = false;
	public static  int next=1;
	public static String root = Constants.URL_ROOT;
	public static String main1 = Constants.URL_ROOT + "product.php";
	public static String main2 = Constants.URL_ROOT + "product.php";
	public static String main3 = Constants.URL_ROOT + "product.php";
	public static String productDetail = Constants.URL_ROOT
			+ "product_view.php";
	public static String search = Constants.URL_ROOT + "search.php";
	public static String buy = Constants.URL_ROOT + "buy.php";
	public static String result = Constants.URL_ROOT + "search.php";
	public static String Login = Constants.URL_ROOT + "login.php";
	public static int numberRecord = 15;

	public static HttpClient client = new DefaultHttpClient();

	public static String id_0 = "id0";
	public static String id_1 = "cat_id";
	public static String id_2 = "subcat_id";
	public static String id_3 = "pro_id";
	public static String id_buy = "product_id";

	public static String value_1;
	public static String value_2;
	public static String value_3;

	public static String value = "value";
	public static String type = "type";

	public static String num_Count = "num_count";
	public static String page = "page";
	public static String strType="1";
}
