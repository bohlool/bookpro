package com.bookpro.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bookpro.R;

public class ProductView extends LinearLayout {
	public ImageView pv_Image;
	public TextView pv_TextView;

	public ProductView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.list_product, this, true);
		pv_Image = (ImageView) findViewById(R.id.list_category_image);
		pv_TextView = (TextView) findViewById(R.id.list_category_textview);
	}
}
