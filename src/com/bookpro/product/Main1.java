package com.bookpro.product;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bookpro.R;
import com.bookpro.util.Utils;

// Acitivity thu 2
public class Main1 extends Activity implements OnItemClickListener,
		OnClickListener {
	/** Called when the acSubProductIDtivity is first created. */
	public static int next = 1;
	Intent intent;
	Intent intent2;
	String num_count = Variable.num_Count;
	String page = Variable.page;
	InputStream in;
	// So san pham cua moi man hinh la 15 san pham
	int numberRecord = Variable.numberRecord;
	// Nut de load san pham moi
	Button more;
	// nut de quay lai
	Button back;
	// Text hien thi so trang
	TextView tv_page;
	// Image de serch san pham
	ImageView search;
	// Tong so trang
	int numberPage;
	// Adaper de ket not ArayList voi ListView
	private ProductAdapter productAdapter;
	// ListView de chua san pham layout tren manh hinh
	ListView lv;
	// Trinh phan tich XML
	public ParseXML pt;
	// Tong so san pham
	int size;
	// public int subListSize;
	String url = Variable.main1;
	private ArrayList<Product> productList = new ArrayList<Product>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		
		Utils.setTitleFromActivityLabel(this, R.id.title_text);
		
		tv_page = (TextView) findViewById(R.id.main1_page);
		lv = (ListView) findViewById(R.id.main1_listview);
		more = (Button) findViewById(R.id.main1_more);
		more.setOnClickListener(this);
		back = (Button) findViewById(R.id.main1_back);
		back.setOnClickListener(this);
		// search = (ImageView) findViewById(R.id.main1_search);
		// search.setOnClickListener(this);
		// HttpClient client = new DefaultHttpClient();
		pt = new ParseXML();
		HttpPost request = new HttpPost(url);
		try {
			HttpResponse response = Variable.client.execute(request);
			in = response.getEntity().getContent();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			SAXParserFactory sx = SAXParserFactory.newInstance();
			SAXParser parser = sx.newSAXParser();
			XMLReader xmlReader = parser.getXMLReader();
			xmlReader.setContentHandler(pt);
			try {
				InputSource input = new InputSource(in);
				xmlReader.parse(input);
				size = pt.ID.size();
				Log.w("", "SIZE Of 1:" + pt.ID.size());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		numberPage = (size % this.numberRecord == 0) ? size / numberRecord
				: size / numberRecord + 1;
		setPage();
		setState();
		productAdapter = new ProductAdapter(this, R.layout.list_product,
				productList);
		addProduct();
		lv.setAdapter(productAdapter);
		lv.setOnItemClickListener(this);
	}

	public void addProduct() {
		Product product;
		int i = 0;
		if (next == numberPage && size % numberRecord != 0) {
			numberRecord = size % numberRecord;
		} else if (size < numberRecord) {
			numberRecord = size;
		} else {
			numberRecord = Variable.numberRecord;
		}
		Log.w("", "NumberRecord of 1:" + numberRecord);
		Log.w("", "Next1: " + next);
		for (i = 0; i < numberRecord; i++) {
			/*
			 * if(pt.ID.get(i).equals("0")) continue; else {
			 */
			product = new Product(pt.name.get(i), pt.ID.get(i), pt.uri.get(i));
			Log.w("", "Name ne: " + pt.name.get(i));
			productList.add(product);
			// }

		}
		productAdapter.notifyDataSetChanged();
	}

	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		// TODO Auto-generated method stub
		Product p = this.productList.get(position);
		Variable.value_1 = p.getID();
		Log.i("", "ID1: " + Variable.value_1);
		intent = new Intent(this, Main2.class);
		startActivity(intent);
	}

	public void deleteAll() {
		// Log.w("","size cua array: "+size);
		int k = 0;
		while (k < productList.size()) {
			productList.remove(k);
		}
		productAdapter.notifyDataSetChanged();
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		setState();
		if (arg0 == more) {
			Log.w("", "MORE1");
			if (next < numberPage) {
				next++;
				setPage();
				InputStream in = null;
				// HttpClient client = new DefaultHttpClient();
				pt = new ParseXML();
				HttpPost request = new HttpPost(url);
				try {
					HttpResponse response = Variable.client.execute(request);
					in = response.getEntity().getContent();

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					SAXParserFactory sx = SAXParserFactory.newInstance();
					SAXParser parser = sx.newSAXParser();
					XMLReader xmlReader = parser.getXMLReader();
					xmlReader.setContentHandler(pt);
					try {
						InputSource input = new InputSource(in);
						xmlReader.parse(input);
						size = pt.ID.size();
						Log.w("", "SIZE:" + pt.ID.size());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				deleteAll();
				addProduct();
				setState();
			}// end if
		}// end event
			// }
			// Xu ly su kien cho phim back
		if (arg0 == back) {
			Log.w("", "BACK3");
			if (next > 1) {
				next--;
				setPage();
				InputStream in = null;
				// HttpClient client = new DefaultHttpClient();
				pt = new ParseXML();
				HttpPost request = new HttpPost(url);
				try {
					HttpResponse response = Variable.client.execute(request);
					in = response.getEntity().getContent();

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					SAXParserFactory sx = SAXParserFactory.newInstance();
					SAXParser parser = sx.newSAXParser();
					XMLReader xmlReader = parser.getXMLReader();
					xmlReader.setContentHandler(pt);
					try {
						InputSource input = new InputSource(in);
						xmlReader.parse(input);
						size = pt.ID.size();
						Log.w("", "SIZE:" + pt.ID.size());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				deleteAll();
				addProduct();
				setState();
			}
		}
		// cat dat cho nut search
		if (arg0 == search) {
			intent2 = new Intent(this, Search.class);
			startActivity(intent2);
		}
	}

	public void deleteAll(ArrayList<String> a) {
		int k = 0;
		while (k < a.size()) {
			a.remove(k);
		}
	}

	// Ham de set trang thai cho nut back va nut more
	public void setState() {
		// Neu so trang
		if (next > 1 && next < numberPage) {
			// more.setVisibility(0);
			more.setEnabled(true);
			more.setFocusable(true);
			back.setEnabled(true);
			back.setFocusable(true);
		}
		if (next == numberPage) {

			more.setEnabled(false);
			more.setFocusable(false);
			back.setEnabled(true);
			back.setFocusable(true);
		}
		if (next == 1) {
			back.setEnabled(false);
			back.setFocusable(false);
			more.setEnabled(true);
		}
		if (size < Variable.numberRecord) {
			more.setEnabled(false);
			more.setFocusable(false);
			back.setFocusable(false);
		}
	}

	public boolean kq = false;

	public boolean result() {
		if (pt.ID.size() == 1 && pt.ID.get(0).equals("0")) {
			kq = true;
		} else
			kq = false;
		return kq;
	}

	public void setPage() {

		if (result() || size == 0) {
			tv_page.setText("Page 0 of 0");
		} else
			tv_page.setText("Page " + next + " of " + this.numberPage);
	}
	
	public void onHomeClick(View v){
		Utils.goHome(this);
	}
	
	public void onSearchClick(View v){
		Utils.goSearch(this);
	}
}
