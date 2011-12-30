package com.bookpro.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bookpro.R;
import com.bookpro.product.Main1;
import com.bookpro.product.ParseXML2;
import com.bookpro.product.Product_Detail;
import com.bookpro.product.Search;
import com.bookpro.product.Variable;
import com.bookpro.util.Constants;
import com.bookpro.util.SharedVariables;
import com.bookpro.util.Utils;

public class HomeActivity extends Activity {
	private LinearLayout loading;
	private LinearLayout random;
	private TextView nameBookTv, authorBookTv, descBookTv;
	private static ImageButton loginIB;
	private static ImageButton logoutIB;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		loginIB = (ImageButton) this.findViewById(R.id.home_ib_login);
		logoutIB = (ImageButton) this.findViewById(R.id.home_ib_logout);

		loading = (LinearLayout) this.findViewById(R.id.now_playing_loading);
		random = (LinearLayout) this.findViewById(R.id.home_ll_random);
		nameBookTv = (TextView) this.findViewById(R.id.home_tv_namebook);
		authorBookTv = (TextView) this.findViewById(R.id.home_tv_authorbook);
		descBookTv = (TextView) this.findViewById(R.id.home_tv_descriptionbook);

		new RandomBookTask().execute();

		if (SharedVariables.getLoginStatus() == true) {
			loginIB.setVisibility(View.GONE);
			logoutIB.setVisibility(View.VISIBLE);
		} else {
			loginIB.setVisibility(View.VISIBLE);
			logoutIB.setVisibility(View.GONE);
		}
	}

	public void onLibraryClick(View v) {
		startActivity(new Intent(getApplicationContext(), LibraryActivity.class));
	}

	public void onStoreClick(View v) {
		startActivity(new Intent(getApplicationContext(), Main1.class));
	}

	public void onAboutClick(View v) {
		startActivity(new Intent(getApplicationContext(), AboutActivity.class));
	}

	public void onProfileClick(View v) {
		if (SharedVariables.getLoginStatus() == true) {
			startActivity(new Intent(getApplicationContext(),
					ProfileActivity.class));
		} else {
			startActivity(new Intent(getApplicationContext(),
					LoginActivity.class));
		}
	}

	public void onSearchClick(View v) {
		// startActivity (new Intent(getApplicationContext(),
		// SearchActivity.class));
		startActivity(new Intent(getApplicationContext(), Search.class));
	}

	public void onReadClick(View v) {
		String pathFile = Utils.getPreference(HomeActivity.this,
				Constants.LAST_READ_BOOK);
		Utils.openPdf(this, pathFile);
	}

	public void onRefreshClick(View v) {
		new RandomBookTask().execute();
	}

	public void onLoginClick(View v) {
		startActivity(new Intent(getApplicationContext(), LoginActivity.class));
	}

	public void onLogoutClick(View v) {
		new LogoutTask().execute();
	}

	private class RandomBookTask extends AsyncTask<Void, Void, InputStream> {
		@Override
		protected void onPreExecute() {
			loading.setVisibility(View.VISIBLE);
			random.setVisibility(View.GONE);
		}

		@Override
		protected InputStream doInBackground(Void... arg0) {
			// Get id(s) of all book
			InputStream in = Utils.getResponese(Constants.URL_RANDOM);
			String response = "";
			try {
				response = Utils.convertStreamToString(in);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if (response.equals("") == false) {
				String[] tokens = response.split(" ");
				Log.e("e_random", "tokens = " + Arrays.toString(tokens));
				// Get random id of book
				int rd = (new Random()).nextInt(tokens.length);
				String randomId = tokens[rd];
				Variable.value_3 = randomId;
				Log.e("e_random", "randomId = " + randomId);

				HttpPost request = new HttpPost(Variable.productDetail);
				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair(Variable.id_3,
						randomId));
				UrlEncodedFormEntity formEntity = null;
				InputStream in2 = null;
				try {
					formEntity = new UrlEncodedFormEntity(postParameters);
					request.setEntity(formEntity);
					HttpResponse response2 = Variable.client.execute(request);
					in2 = response2.getEntity().getContent();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return in2;
			} else {
				return null;
			}
		}

		@Override
		protected void onPostExecute(InputStream result) {
			ParseXML2 pt = new ParseXML2();
			if (result != null) {
				String nameBook = null, authorBook = null, descriptionBook = null;
				try {
					SAXParserFactory sx = SAXParserFactory.newInstance();
					SAXParser parser = sx.newSAXParser();
					XMLReader xmlReader = parser.getXMLReader();
					xmlReader.setContentHandler(pt);
					try {
						InputSource input = new InputSource(result);
						xmlReader.parse(input);
						nameBook = pt.getName();
						Log.w("", "Name: " + nameBook);
						authorBook = pt.getAuthor();
						Log.w("", "AunameBook: " + authorBook);
						descriptionBook = pt.getDeail();
						Log.w("", "Description : " + descriptionBook);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				}
				nameBookTv.setText(nameBook);
				authorBookTv.setText(authorBook);
				descBookTv.setText(descriptionBook);
			} else {
				Log.e("e_random", "is of random book null");
				nameBookTv.setText("Sorry");
				authorBookTv.setText("404");
				descBookTv.setText("Not Found");
			}
			loading.setVisibility(View.GONE);
			random.setVisibility(View.VISIBLE);
			random.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					startProductDetail();
				}
			});
		}
	}

	private void startProductDetail() {
		startActivity(new Intent(HomeActivity.this.getApplicationContext(),
				Product_Detail.class));
	}

	private class LogoutTask extends AsyncTask<Void, Void, String> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(HomeActivity.this);
			dialog.setMessage("Logouting...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected String doInBackground(Void... arg0) {
			return doLogout();
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();

			Log.e("e_home", "logout = " + result);
			if (result.equals("1")) {
				// set login status is false
				SharedVariables.setLoginStatus(false);
				Utils.toast(HomeActivity.this, "Logout Successful!");
				// change icon
				changeStatus();
			} else {
				Utils.createDialog(HomeActivity.this,
						HomeActivity.this.getString(R.string.title_error),
						HomeActivity.this.getString(R.string.error_server));
			}
		}
	}

	public String doLogout() {
		InputStream in = Utils.getResponese(Constants.URL_LOGOUT);
		String response = "";
		try {
			response = Utils.convertStreamToString(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return response;
	}

	public static void changeStatus() {
		if (loginIB.getVisibility() == View.VISIBLE) {
			loginIB.setVisibility(View.GONE);
			logoutIB.setVisibility(View.VISIBLE);
		} else {
			loginIB.setVisibility(View.VISIBLE);
			logoutIB.setVisibility(View.GONE);
		}
	}
}
