package com.bookpro.product;

public class Product {
	protected String name;
	protected String cat_id;
	protected String ImageURL;

	public String getName() {
		return name;
	}

	public String getID() {
		return cat_id;
	}

	public String getImageURL() {
		return ImageURL;
	}

	public void setName(String _name) {
		name = _name;
	}

	public void setID(String _id) {
		cat_id = _id;
	}

	public void setImageURL(String url) {
		ImageURL = url;
	}

	public Product(String _name, String iD, String Image_url) {
		name = _name;
		cat_id = iD;
		ImageURL = Image_url;
	}

	public Product() {
		name = "";
		cat_id = "";
		ImageURL = "";
	}
}
