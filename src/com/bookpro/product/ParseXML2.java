package com.bookpro.product;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.bookpro.util.Utils;

public class ParseXML2 extends DefaultHandler {
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if (qName.equals("price")) {
			price = false;
		}
		if (qName.equals("link")) {
			link = false;
		}
		if (qName.equals("name")) {
			name = false;
		}
		if (qName.equals("image")) {
			image = false;
		}
		if (qName.equals("type")) {
			type = false;
		}
		if (qName.equals("num_download")) {
			numberDownload = false;
		}
		if (qName.equals("description")) {
			detail = false;
		}
		if (qName.equals("author")) {
			author = false;
		}
		if (qName.equals("name_published")) {
			nxb = false;
		}
		if (qName.equals("page")) {
			page = false;
		}
		if (qName.equals("format")) {
			format = false;
		}
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	boolean price = false;
	boolean type = false;
	boolean name = false;
	boolean image = false;
	boolean numberDownload = false;
	boolean detail = false;
	boolean author = false;
	boolean nxb = false;
	boolean page = false;
	boolean format = false;
	boolean link = false;
	String strPrice, strType, strName, strImage, strNumberDownload, strDetail,
			strAuthor, strnxb, strPage, strFormat, strLink;

	@Override
	// funciton: co su kien Text
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String str = new String(ch, start, length);
		str = str.trim();
		if (price) {
			//strPrice = str;
			strPrice = Utils.standardizeMoney(str);
		}
		if (name) {
			strName = str;
		}
		if (type) {
			strType = str;
		}
		if (image) {
			str = Variable.root + str;
			strImage = str;
		}
		if (numberDownload) {
			strNumberDownload = str;
			Log.w("", "so la dow" + str);
		}
		if (detail) {
			strDetail = str;
		}
		if (author) {
			strAuthor = str;
		}
		if (nxb) {
			strnxb = str;
		}
		if (page) {
			strPage = str;
		}
		if (format) {
			strFormat = str;
		}
		if (link) {
			str = Variable.root + str;
			strLink = str;
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if (qName.equals("price")) {
			price = true;
		}
		if (qName.equals("type")) {
			type = true;
		}
		if (qName.equals("name")) {
			name = true;
		}
		if (qName.equals("image")) {
			image = true;
		}
		if (qName.equals("num_download")) {
			numberDownload = true;
		}
		if (qName.equals("description")) {
			detail = true;
		}
		if (qName.equals("author")) {
			author = true;
		}
		if (qName.equals("name_published")) {
			nxb = true;
		}
		if (qName.equals("page")) {
			page = true;
		}
		if (qName.equals("format")) {
			format = true;
		}
		if (qName.equals("link")) {
			link = true;
		}
	}

	public String getPrice() {
		return strPrice;
	}

	public String getName() {
		return strName;
	}

	public String getImage() {
		return strImage;
	}

	public String getNumberDownload() {
		return strNumberDownload;
	}

	public String getDeail() {
		return strDetail;
	}

	public String getAuthor() {
		return strAuthor;
	}

	public String getNXB() {
		return strnxb;
	}

	public String getPage() {
		return strPage;
	}

	public String getFormat() {
		return strFormat;
	}

	public String getType() {
		return strType;
	}

	public String getLink() {
		return strLink;
	}
}
