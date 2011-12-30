package com.bookpro.product;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter2 extends ArrayAdapter<Product2> {
	protected ArrayList<Product2> array;
	protected ImageDownload id = new ImageDownload();
	protected int resource;
	protected Context context;

	public ProductAdapter2(Context context, int textViewResourceId,
			ArrayList<Product2> objects) {
		super(context, textViewResourceId, objects);
		this.array = objects;
		this.resource = textViewResourceId;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		if (view == null) {
			view = new ProductView2(getContext());
		}
		final Product2 product = this.array.get(position);
		if (product != null) {
			ImageView im = ((ProductView2) view).pv_Image;
			TextView tv_name = ((ProductView2) view).pv_TextView;
			TextView tv_author = ((ProductView2) view).pv_TextView2;
			tv_name.setText(product.getName());
			// tv_name.setText("Huong");
			tv_author.setText(product.getAuthor());
			// tv_author.setText("phuong");
			String url = product.getImageURL();
			Bitmap bm;
			bm = id.Download(url);
			im.setImageBitmap(bm);
		}
		return view;
	}

}
