package com.bookpro.product;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class Parse3 extends DefaultHandler {
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if (qName.equals("mail")) {
			email = false;
		}
		if (qName.equals("address")) {
			address = false;
		}
		if (qName.equals("name")) {
			username = false;
		}
		if (qName.equals("money")) {
			money = false;
		}
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	boolean email = false;
	boolean username = false;
	boolean address = false, money = false;
	String strName, strEmail, strAddress, strmoney;

	@Override
	// funciton: co su kien Text
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String str = new String(ch, start, length);
		str = str.trim();
		if (email) {
			strEmail = str;
			Log.w("", "Mail:" + str);
			// System.out.println("Mail:"+strEmail);
		}
		if (username) {
			strName = str;
			Log.w("", "Name: " + str);
			// System.out.println("Name:" + strName);
		}
		if (address) {
			strAddress = str;
			Log.w("", "Address: " + str);
			// System.out.println("Address:" + strAddress);

		}
		if (money) {
			strmoney = str;
			Log.w("", "Money: " + str);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if (qName.equals("mail")) {
			email = true;
		}
		if (qName.equals("name")) {
			username = true;
		}
		if (qName.equals("address")) {
			address = true;
		}
		if (qName.equals("money")) {
			money = true;
		}
	}

	public String getName() {
		return strName;
	}

	public String getEmail() {
		return strEmail;
	}

	public String getAddress() {
		return strAddress;
	}

	public String getMoney() {
		return strmoney;
	}
}
