package com.bookpro.ui;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bookpro.R;
import com.bookpro.product.ParseXMLProfile;
import com.bookpro.product.Variable;
import com.bookpro.util.Constants;
import com.bookpro.util.Utils;

public class ProfileActivity extends Activity {
	private TextView username;
	private TextView name;
	private TextView email;
	private TextView address;
	private TextView money;
	
	private RelativeLayout loading;
	private LinearLayout content;
	
	private ParseXMLProfile pt;
	private InputStream in;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_profile);
		Utils.setTitleFromActivityLabel(this, R.id.title_text);

		username = (TextView) this.findViewById(R.id.profile_username);
		name = (TextView) this.findViewById(R.id.profile_name);
		email = (TextView) this.findViewById(R.id.profile_email);
		address = (TextView) this.findViewById(R.id.profile_address);
		money = (TextView) this.findViewById(R.id.profile_money);
		
		loading = (RelativeLayout)this.findViewById(R.id.profile_loading);
		content = (LinearLayout)this.findViewById(R.id.profile_content);

		new ProfileTask().execute();
	}
	
	public void onHomeClick(View v) {
		Utils.goHome(this);
	}

	public void onSearchClick(View v) {
		Utils.goSearch(this);
	}

	public void onChangeProfileClick(View v) {
		this.finish();
		startActivity(new Intent(getApplicationContext(),
				ChangeProfileActivity.class));
	}
	
	private class ProfileTask extends AsyncTask<Void, Void, InputStream> {
		@Override
		protected InputStream doInBackground(Void... arg0) {
			return getProfileInputStream();
		}

		@Override
		protected void onPostExecute(InputStream in) {
			loading.setVisibility(View.GONE);
			pt = new ParseXMLProfile();			
			try {
				SAXParserFactory sx = SAXParserFactory.newInstance();
				SAXParser parser = sx.newSAXParser();
				XMLReader xmlReader = parser.getXMLReader();
				xmlReader.setContentHandler(pt);
				try {
					InputSource input = new InputSource(in);
					input.setEncoding("utf-8");
					xmlReader.parse(input);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
			
			String u = pt.getUsername()==null?"null":pt.getUsername();
			String n = pt.getName()==null?"null":pt.getName();
			String e = pt.getEmail()==null?"null":pt.getEmail();
			String a = pt.getAddress()==null?"null":pt.getAddress();
			String m = pt.getMoney()==null?"null":pt.getMoney();
			//if(u != null && e != null && a != null){
				content.setVisibility(View.VISIBLE);
				username.setText(u);
				name.setText(n);
				email.setText(e);
				address.setText(a);
				money.setText(m);
			//}else{
				
			//}
		}
	}

	private InputStream getProfileInputStream() {
		HttpPost request = new HttpPost(Constants.URL_PROFILE);
		try {
			HttpResponse response = Variable.client.execute(request);
			in = response.getEntity().getContent();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}
}