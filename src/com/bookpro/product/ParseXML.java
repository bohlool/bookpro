package com.bookpro.product;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParseXML extends DefaultHandler {
	public static int count;

	public ParseXML() {
		ID = new ArrayList<String>();
		count =0;
		name = new ArrayList<String>();
		uri = new ArrayList<String>();
		Author = new ArrayList<String>();
	}

	public ArrayList<String> ID;
	public ArrayList<String> name;
	public static ArrayList<String> uri;
	public ArrayList<String> Author;
	//public int size;
	
	public void setID(ArrayList<String> _id) {
		ID = _id;
	}

	public void setName(ArrayList<String> _name) {
		name = _name;
	}

	public void setURL(ArrayList<String> _url) {
		uri = _url;
	}
	public int getCount(){
		return count;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if (qName.equals("id")) {
			IDFound = false;
		}
		if(qName.equals("size")) {
			sizeFound=false;
		}
		if (qName.equals("name")) {
			nameFound = false;
		}
		if (qName.equals("image")) {
			imageFound = false;
		}
		if (qName.equals("author")) {
			authorFound = false;
		}
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	static boolean IDFound = false;
	static boolean sizeFound=false;
	static boolean nameFound = false;
	static boolean imageFound = false;
	static boolean authorFound = false;

	@Override
	// funciton: co su kien Text
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String str = new String(ch, start, length);
		str = str.trim();
		if (IDFound) {
			ID.add(str);
		}
		if (nameFound) {
			name.add(str);
		}
		if (imageFound) {
			str = Variable.root + str;
			uri.add(str);
		}
		if (authorFound) {
			Author.add(str);
		}
		if(sizeFound) {
			count=Integer.parseInt(str);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if (qName.equals("id")) {
			IDFound = true;

		}
		if (qName.equals("name")) {
			nameFound = true;
		}
		if (qName.equals("image")) {
			imageFound = true;
		}
		if (qName.equals("author")) {
			authorFound = true;
		}
		if(qName.equals("size")) {
			sizeFound=true;
		}
	}
}
