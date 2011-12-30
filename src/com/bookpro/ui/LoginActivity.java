package com.bookpro.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.bookpro.R;
import com.bookpro.util.Constants;
import com.bookpro.util.SharedVariables;
import com.bookpro.util.Utils;

public class LoginActivity extends Activity{
	private TextView username, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		Utils.setTitleFromActivityLabel(this, R.id.title_text);

		username = (TextView) this.findViewById(R.id.username);
		password = (TextView) this.findViewById(R.id.password);
	}

	public void onHomeClick(View v) {
		Utils.goHome(this);
	}

	public void onSearchClick(View v) {
		Utils.goSearch(this);
	}
	
	public void onSignupClick(View v) {
		startActivity(new Intent(getApplicationContext(), SignupActivity.class));
	}

	public void onLoginClick(View v) {
		// Hide the Soft Keyboard that appears when the EditText has focus
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
		String u = username.getText().toString();
		String p = password.getText().toString();

		if(u.equals("") == false){
			if (Utils.validate(u, Constants.USERNAME_PATTERN) == true) {
				if (p.equals("") == false) {
					new LoginTask().execute(u, p);
				} else {
					Utils.createDialog(this, this.getString(R.string.title_error), this.getString(R.string.error_empty_password));
					password.requestFocus();
				}
			} else {
				Utils.createDialog(this, this.getString(R.string.title_error), this.getString(R.string.error_format_username));
				username.requestFocus();
			}
		}else{
			Utils.createDialog(this, this.getString(R.string.title_error), this.getString(R.string.error_empty_username));
			username.requestFocus();
		}
	}
	
	private class LoginTask extends AsyncTask<String, Void, String>{
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(LoginActivity.this);
	        dialog.setMessage("Authenticating...");
	        dialog.setIndeterminate(true);
	        dialog.setCancelable(false);
	        dialog.show();
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			return doLogin(arg0[0], arg0[1]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			
			Log.e("e_login", "login = "+result);
			if(result.equals("1")){
				// set login status
				SharedVariables.setLoginStatus(true);
				Utils.toast(LoginActivity.this, "Login Successful!");
				// change icon
				HomeActivity.changeStatus();
				LoginActivity.this.finish();
			}else{
				if(result.equals("2")){
					Utils.createDialog(LoginActivity.this, LoginActivity.this.getString(R.string.title_error),
							LoginActivity.this.getString(R.string.error_username_locked));
				}else if(result.equals("3")){
					Utils.createDialog(LoginActivity.this, LoginActivity.this.getString(R.string.title_error),
							LoginActivity.this.getString(R.string.error_username_password_incorrect));
				}else{
					Utils.createDialog(LoginActivity.this, LoginActivity.this.getString(R.string.title_error),
							LoginActivity.this.getString(R.string.error_server));
				}
			}
		}
	}

	public String doLogin(final String u, final String p) {
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair(
				Constants.SEND_LOGIN_USERNAME, u));
		postParameters.add(new BasicNameValuePair(
				Constants.SEND_LOGIN_PASSWORD, p));

		InputStream in = Utils
				.getResponese(Constants.URL_LOGIN, postParameters);
		String response = "";
		try {
			response = Utils.convertStreamToString(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return response;
	}
}