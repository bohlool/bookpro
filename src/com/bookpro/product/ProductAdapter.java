package com.bookpro.product;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends ArrayAdapter<Product> {
	protected ArrayList<Product> array;
	protected ImageDownload id = new ImageDownload();
	protected int resource;
	protected Context context;

	public ProductAdapter(Context context, int textViewResourceId,
			ArrayList<Product> objects) {
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
			view = new ProductView(getContext());
		}
		final Product product = this.array.get(position);
		if (product != null) {
			ImageView im = ((ProductView) view).pv_Image;
			TextView tv = ((ProductView) view).pv_TextView;
			tv.setText(product.getName());
			String url = product.getImageURL();
			Bitmap bm;
			bm = id.Download(url);
			im.setImageBitmap(bm);
		}
		return view;
	}

}
