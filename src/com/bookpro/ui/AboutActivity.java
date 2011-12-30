
// Giao dien About tuong ung voi About tren giao dien chinh

package com.bookpro.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import com.bookpro.R;
import com.bookpro.util.Utils;

public class AboutActivity extends Activity{
	private TextView email;	// Email ho tro khach hang

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_info);
		// Dat tieu de tren action bar
		Utils.setTitleFromActivityLabel(this, R.id.title_text);
		// Tao doi tuong email tu xml va dat thuoc tinh EMAIL_ADDRESSES
		email = (TextView)this.findViewById(R.id.about_support_content);
		email.setText(this.getString(R.string.about_support_content));
		Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);
	}

	// Tro ve giao dien Home khi bam nut Home tren action bar
	public void onHomeClick(View v){
		Utils.goHome(this);
	}
	
	// Chuyen den giao dien Search khi bam nut Search tren action bar
	public void onSearchClick(View v){
		Utils.goSearch(this);
	}
	
}
