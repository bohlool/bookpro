package com.bookpro.product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.bookpro.R;

public class Login extends Activity implements OnClickListener {
	EditText user, pass;
	Button login;
	String url = Variable.Login;
	String strUserName, strPassWord;
	Parse3 pt = new Parse3();
	HttpPost request;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_2);
		user = (EditText) findViewById(R.id.login_userName);
		pass = (EditText) findViewById(R.id.login_password);
		login = (Button) findViewById(R.id.login_bt_login);
		login.setOnClickListener(this);
	}

	public String getInfor() throws Exception {
		BufferedReader in = null;
		try {
			// HttpClient client = new DefaultHttpClient();
			request = new HttpPost(url);

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("username", strUserName));
			postParameters.add(new BasicNameValuePair("password", strPassWord));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);

			request.setEntity(formEntity);
			HttpResponse response = Variable.client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();

			String result = sb.toString();

			return result;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == login) {
			AlertDialog.Builder alertDialog = null;
			strUserName = user.getText().toString();
			strPassWord = pass.getText().toString();
			try {
				String result = this.getInfor();
				int re = Integer.parseInt(result.trim());
				Log.w("", "Ha" + result + "phuong");
				if (re == 1) {
					Log.w("", "Sucessfull!");
					Variable.login = true;
					alertDialog = new AlertDialog.Builder(Login.this);
					alertDialog.setMessage("Login Sucessfull!");
					alertDialog.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// Action for 'Yes' Button
									dialog.dismiss();
									finish();
									/*
									 * InputStream in=null; request = new
									 * HttpPost
									 * ("http://192.168.116.1/dtui/view_info.php"
									 * ); try { HttpResponse response =
									 * Variable.client.execute(request);
									 * in=response.getEntity().getContent(); }
									 * catch (ClientProtocolException e) { //
									 * TODO Auto-generated catch block
									 * e.printStackTrace(); } catch (IOException
									 * e) { // TODO Auto-generated catch block
									 * e.printStackTrace(); } try {
									 * SAXParserFactory
									 * sx=SAXParserFactory.newInstance();
									 * SAXParser parser=sx.newSAXParser();
									 * XMLReader xmlReader =
									 * parser.getXMLReader();
									 * xmlReader.setContentHandler(pt); try {
									 * InputSource input=new InputSource(in);
									 * xmlReader.parse(input); Log.w("",
									 * "Name: " + pt.getName()); Log.w("",
									 * "Address" + pt.getAddress());
									 * Log.w("","Email: " + pt.getEmail());
									 * Log.w("","Money: " + pt.getMoney()); }
									 * catch (IOException e) { // TODO
									 * Auto-generated catch block
									 * e.printStackTrace(); }
									 * 
									 * } catch (ParserConfigurationException e)
									 * { // TODO Auto-generated catch block
									 * e.printStackTrace(); } catch
									 * (SAXException e) { // TODO Auto-generated
									 * catch block e.printStackTrace(); }
									 */
									/*
									 * BufferedReader in = null; request = new
									 * HttpPost
									 * ("http://192.168.116.1/view_infor.php");
									 * 
									 * HttpResponse response = null; try {
									 * response =
									 * Variable.client.execute(request); } catch
									 * (ClientProtocolException e) { // TODO
									 * Auto-generated catch block
									 * e.printStackTrace(); } catch (IOException
									 * e) { // TODO Auto-generated catch block
									 * e.printStackTrace(); } try { in = new
									 * BufferedReader(new
									 * InputStreamReader(response.getEntity()
									 * .getContent())); } catch
									 * (IllegalStateException e) { // TODO
									 * Auto-generated catch block
									 * e.printStackTrace(); } catch (IOException
									 * e) { // TODO Auto-generated catch block
									 * e.printStackTrace(); } StringBuffer sb =
									 * new StringBuffer(""); String line = "";
									 * String NL =
									 * System.getProperty("line.separator"); try
									 * { while ((line = in.readLine()) != null)
									 * { sb.append(line + NL); } } catch
									 * (IOException e) { // TODO Auto-generated
									 * catch block e.printStackTrace(); } try {
									 * in.close(); } catch (IOException e) { //
									 * TODO Auto-generated catch block
									 * e.printStackTrace(); }
									 * 
									 * String result = sb.toString();
									 * Log.w("",result);
									 */

								}
							});
					alertDialog.create();
					alertDialog.show();
				}

				// Log.w("","Ket qua" + result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
