package com.bookpro.product;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.bookpro.R;

public class Test extends Activity {
	TestHttpPost h = new TestHttpPost();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		try {
			String k = h.executeHttpPost();
			Log.w("", "" + k);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
