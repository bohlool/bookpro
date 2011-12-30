package com.bookpro.util;

import com.bookpro.product.Variable;

public class SharedVariables {
	public static boolean getLoginStatus() {
		return Variable.login;
	}

	public static void setLoginStatus(boolean b) {
		Variable.login = b;
	}
}
