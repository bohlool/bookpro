package com.bookpro.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bookpro.R;

public class ProductView2 extends LinearLayout {
	public ImageView pv_Image;
	public TextView pv_TextView;
	public TextView pv_TextView2;

	public ProductView2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.list2, this, true);
		pv_Image = (ImageView) findViewById(R.id.list2_image);
		pv_TextView = (TextView) findViewById(R.id.list2_textview1);
		pv_TextView2 = (TextView) findViewById(R.id.list2_textview2);
	}

}
