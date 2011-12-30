package com.bookpro.product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestHttpPost {
	private String page = "http://192.168.1.9/dtui/view_info.php";
	HttpClient client = new DefaultHttpClient();
	HttpPost request = new HttpPost(page);

	public void setPage(String _page) {
		page = _page;
	}

	public String setPage() {
		return page;
	}

	private List<NameValuePair> Post;

	public TestHttpPost() {
		Post = new ArrayList<NameValuePair>();
	}

	public String executeHttpPost() throws Exception {
		BufferedReader in = null;
		try {

			// L//ist<NameValuePair> postParameters = new
			// ArrayList<NameValuePair>();
			// postParameters.add(new BasicNameValuePair("username", "dungb"));
			// postParameters.add(new BasicNameValuePair("password", "abc123"));
			// UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
			// postParameters);

			// request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
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

	public InputStream executeHttpPost2() {
		InputStream in = null;

		return in;
	}
}
