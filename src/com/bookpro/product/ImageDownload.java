package com.bookpro.product;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageDownload {
	public Bitmap bm;

	public Bitmap Download(String url) {
		// TODO Auto-generated method stub
		// bm=new Bitmap[url.length];
		URL myURL = null;
		InputStream input = null;
		// for(int i=0;i<url.size();i++) {
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URLConnection conn = null;
		try {
			conn = myURL.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Day la doan code download anh tu tren mang xuog
		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			// Log.w("", "Da connect duoc!");

			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				input = httpConn.getInputStream();
				bm = (BitmapFactory.decodeStream(input));
				input.close();
			}
		} catch (Exception ex) {
		}
		// isDownload=true;
		return bm;
	}
}
