package com.bookpro.product;

public class Product2 extends Product {
	String author;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return super.getID();
	}

	@Override
	public String getImageURL() {
		// TODO Auto-generated method stub
		return super.getImageURL();
	}

	@Override
	public void setName(String _name) {
		// TODO Auto-generated method stub
		super.setName(_name);
	}

	@Override
	public void setID(String _id) {
		// TODO Auto-generated method stub
		super.setID(_id);
	}

	@Override
	public void setImageURL(String url) {
		// TODO Auto-generated method stub
		super.setImageURL(url);
	}

	public Product2(String _name, String iD, String Image_url, String Author) {
		super(_name, iD, Image_url);
		this.author = Author;
	}

	public Product2() {
		name = "";
		cat_id = "";
		ImageURL = "";
		author = "";
	}

	public String getAuthor() {
		return this.author;
	}
}
