package com.bookpro.product;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
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

public class Main2 extends Activity implements OnItemClickListener,
		OnClickListener {
	/** Called when the acSubProductIDtivity is first created. */
	String num_count = Variable.num_Count;
	String page = Variable.page;
	public static int next = 1;
	// intent de khoi tao Activity tiep theo
	Intent intent;
	// Intent de khoi tao Acitivity search
	Intent intent2;
	// Luong de doc du lieu
	InputStream in;
	// So san pham cua moi man hinh la 7 san pham
	int numberRecord = Variable.numberRecord;
	// Nut de load san pham moi
	Button more;
	// Nut de load lai san pham
	Button back;
	// Nut de search
	Button search;
	// So trang san pham
	int numberPage;
	// Adapter de ket noi ListView va mang
	private ProductAdapter productAdapter;
	ListView lv;
	// Trinh phan tich XML = SAX
	public ParseXML pt;
	// tong so san pham
	int size;
	// Dia chi URL cua website can connect toi
	String url = (Variable.main2);
	// Mang chua san pham
	private ArrayList<Product> productList = new ArrayList<Product>();
	TextView tv_page;
	ImageView search2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		
		Utils.setTitleFromActivityLabel(this, R.id.title_text);
		
		
		tv_page = (TextView) findViewById(R.id.main2_page);
		lv = (ListView) findViewById(R.id.main2_listView);
		more = (Button) findViewById(R.id.main2_more);
		more.setOnClickListener(this);
		back = (Button) findViewById(R.id.main2_back);
		back.setOnClickListener(this);
		// search2 = (ImageView) findViewById(R.id.main2_search);
		// search2.setOnClickListener(this);
		// HttpClient client = new DefaultHttpClient();
		pt = new ParseXML();
		HttpPost request = new HttpPost(url);
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair(Variable.id_1,
				Variable.value_1));
		// Log.w("","");
		UrlEncodedFormEntity formEntity = null;
		try {
			formEntity = new UrlEncodedFormEntity(postParameters);
			request.setEntity(formEntity);
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
				Log.w("", "SIZE of 2:" + pt.ID.size());
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
		Log.w("", "NumberRecord Of 2:" + numberRecord);
		Log.w("", "Next2: " + next);
		for (i = 0; i < numberRecord; i++) {
			/*
			 * if(pt.ID.get(i).equals("0")) continue; else {
			 */
			product = new Product(pt.name.get(i), pt.ID.get(i), pt.uri.get(i));
			productList.add(product);
			// }

		}
		productAdapter.notifyDataSetChanged();
	}

	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		// TODO Auto-generated method stub
		intent = new Intent(this, Main3.class);
		Product p = this.productList.get(position);
		Variable.value_2 = p.getID();
		Log.i("", "ID2: " + Variable.value_2);
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
			Log.w("", "MORE2");
			if (next < numberPage) {
				next++;
				setPage();
				InputStream in = null;
				// HttpClient client = new DefaultHttpClient();
				pt = new ParseXML();
				HttpPost request = new HttpPost(url);
				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair(Variable.id_1,
						Variable.value_1));
				UrlEncodedFormEntity formEntity = null;
				try {
					formEntity = new UrlEncodedFormEntity(postParameters);
					request.setEntity(formEntity);
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
		if (arg0 == back) {
			Log.w("", "BACK2");
			if (next > 1) {
				next--;
				setPage();
				InputStream in = null;
				// HttpClient client = new DefaultHttpClient();
				pt = new ParseXML();
				HttpPost request = new HttpPost(url);
				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair(Variable.id_1,
						Variable.value_1));
				UrlEncodedFormEntity formEntity = null;
				try {
					formEntity = new UrlEncodedFormEntity(postParameters);
					request.setEntity(formEntity);
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
		if (arg0 == search2) {
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

	// function to set State for button back and button more
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