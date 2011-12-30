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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bookpro.R;
import com.bookpro.util.Constants;
import com.bookpro.util.Utils;

public class SignupActivity extends Activity {
	private EditText username;
	private EditText name;
	private EditText password;
	private EditText re_password;
	private EditText email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_signup);
		Utils.setTitleFromActivityLabel(this, R.id.title_text);

		username = (EditText) this.findViewById(R.id.signup_et_username);
		name = (EditText) this.findViewById(R.id.signup_et_name);
		password = (EditText) this.findViewById(R.id.signup_et_password);
		re_password = (EditText) this.findViewById(R.id.signup_et_repassword);
		email = (EditText) this.findViewById(R.id.signup_et_email);
	}

	public void onHomeClick(View v) {
		Utils.goHome(this);
	}

	public void onSearchClick(View v) {
		Utils.goSearch(this);
	}

	public void onCancelClick(View v) {
		this.finish();
	}

	public void onCreateClick(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(email.getWindowToken(), 0);

		String u = username.getText().toString();
		String n = name.getText().toString();
		String p = password.getText().toString();
		String rp = re_password.getText().toString();
		String e = email.getText().toString();
		
		if(u.equals("") == false){
			if (Utils.validate(u, Constants.USERNAME_PATTERN) == true) {
				if (p.equals("") == false) {
					if (p.equals(rp)) {
						if (Utils.validate(e, Constants.EMAIL_PATTERN)) {
							new SignupTask().execute(u, n, p, e);
						} else {
							Utils.createDialog(this, this.getString(R.string.title_error), this.getString(R.string.error_format_email));
							email.requestFocus();
						}
					} else {
						Utils.createDialog(this, this.getString(R.string.title_error), this.getString(R.string.error_password_not_match));
						password.requestFocus();
					}
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

	private class SignupTask extends AsyncTask<String, Void, String> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(SignupActivity.this);
			dialog.setMessage("Creating...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			return doSignup(arg0[0], arg0[1], arg0[2], arg0[3]);
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			
			Log.e("e_signup", "signup = "+result);
			if (result.equals("1")) {
				Utils.toastLong(SignupActivity.this, "Registration Completed!");
				SignupActivity.this.finish();
			} else {
				if (result.equals("2")) {
					Utils.createDialog(SignupActivity.this, SignupActivity.this.getString(R.string.title_error), 
							SignupActivity.this.getString(R.string.error_signup_existed));
				} else {
					Utils.createDialog(SignupActivity.this, SignupActivity.this.getString(R.string.title_error), 
							SignupActivity.this.getString(R.string.error_server));
				}
			}
		}
	}

	private String doSignup(String u, String n, String p, String e) {
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair(
				Constants.SEND_SIGNUP_USERNAME, u));
		postParameters.add(new BasicNameValuePair(
				Constants.SEND_SIGNUP_NAME, n));
		postParameters.add(new BasicNameValuePair(
				Constants.SEND_SIGNUP_PASSWORD, p));
		postParameters.add(new BasicNameValuePair(Constants.SEND_SIGNUP_EMAIL,
				e));

		InputStream in = Utils.getResponese(Constants.URL_SIGNUP,
				postParameters);
		String response = "";
		try {
			response = Utils.convertStreamToString(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return response;
	}
}