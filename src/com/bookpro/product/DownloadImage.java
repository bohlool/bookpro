package com.bookpro.product;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DownloadImage {
	public static Bitmap bm;

	public Bitmap download(final String url) {
		// TODO Auto-generated method stub
		Thread t = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				URL myURL = null;
				InputStream input = null;
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
					Log.w("", "Da connect duoc!");

					if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						input = httpConn.getInputStream();
						bm = BitmapFactory.decodeStream(input);
						// Message ms=handler.obtainMessage(1, bm);
						// handler.sendMessage(ms);
						Log.i("", "Da download thanh cong!");
						input.close();
					}
				} catch (Exception ex) {
				}
			}

		};

		return bm;
	}
}
