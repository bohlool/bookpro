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
import android.widget.TextView;

import com.bookpro.R;
import com.bookpro.util.Constants;
import com.bookpro.util.Utils;

public class ChangeProfileActivity extends Activity{
	private TextView name;
	private TextView password;
	private TextView re_password;
	private TextView email;
	private TextView address;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_change_profile);
		// Dat tieu de cho action bar
		Utils.setTitleFromActivityLabel(this, R.id.title_text);
		// Khoi tao cac doi tuong tu xml
		name = (TextView)this.findViewById(R.id.changeprofile_et_name);
		password = (TextView)this.findViewById(R.id.change_profile_et_password);
		re_password = (TextView)this.findViewById(R.id.change_profile_et_re_password);
		email = (TextView)this.findViewById(R.id.change_profile_et_email);
		address = (TextView)this.findViewById(R.id.change_profile_et_address);
	}

	public void onHomeClick(View v){
		Utils.goHome(this);
	}
	
	public void onSearchClick(View v){
		Utils.goSearch(this);
	}
	
	public void onCancelClick(View v){
		this.finish();
	}
	
	public void onSaveChangeClick(View v){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(address.getWindowToken(), 0);
		
		String n = name.getText().toString();
		String p = password.getText().toString();
		String rp = re_password.getText().toString();
		String e = email.getText().toString();
		String a = address.getText().toString();
		
		if(p.equals("") == false){
			if (p.equals(rp)) {
				if (Utils.validate(e, Constants.EMAIL_PATTERN)) {
					new ChangeProfileTask().execute(n, p, e, a);
				} else {
					Utils.createDialog(this, this.getString(R.string.title_error), this.getString(R.string.error_format_email));
					email.requestFocus();
				}
			} else {
				Utils.createDialog(this, this.getString(R.string.title_error), this.getString(R.string.error_password_not_match));
				password.requestFocus();
			}
		}else{
			Utils.createDialog(this, this.getString(R.string.title_error), this.getString(R.string.error_empty_password));
			password.requestFocus();
		}
	}
	
	private class ChangeProfileTask extends AsyncTask<String, Void, String> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(ChangeProfileActivity.this);
			dialog.setMessage("Submiting...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			return doChangeProfile(arg0[0], arg0[1], arg0[2], arg0[3]);
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();

			if (result.equals("1")) {
				Utils.toast(ChangeProfileActivity.this, "ChangeProfile successful!");
				ChangeProfileActivity.this.finish();
			} else {
				if (result.equals("2")) {
					Utils.toast(ChangeProfileActivity.this, "ChangeProfile fail!");
				} else {
					Utils.toast(ChangeProfileActivity.this,
							"Could not connect to server!");
				}
			}
		}
	}
	
	private String doChangeProfile(String n, String p, String e, String a) {
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair(
				Constants.SEND_PROFILE_NAME, n));
		postParameters.add(new BasicNameValuePair(
				Constants.SEND_PROFILE_PASSWORD, p));
		postParameters.add(new BasicNameValuePair(
				Constants.SEND_PROFILE_EMAIL, e));
		postParameters.add(new BasicNameValuePair(
				Constants.SEND_PROFILE_ADDRESS, a));

		InputStream in = Utils.getResponese(Constants.URL_CHANGE_PROFILE,
				postParameters);
		String response = "";
		try {
			response = Utils.convertStreamToString(in);
			Log.w("", "Change Profile:" + response);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return response;
	}
}