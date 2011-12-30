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

public class Main3 extends Activity implements OnItemClickListener,
		OnClickListener {
	/** Called when the acSubProductIDtivity is first created. */
	public static int next = 1;
	Intent intent, intent2;
	ImageView search;
	String num_count = Variable.num_Count;
	String page = Variable.page;
	InputStream in;
	// So san pham cua moi man hinh la 15 san pham
	int numberRecord = Variable.numberRecord;
	// Nut de load san pham moi
	Button more;
	// nut de quay lai
	Button back;
	// Tong so trang
	int numberPage;
	// Adaper de ket not ArayList voi ListView
	private ProductAdapter2 productAdapter;
	// ListView de chua san pham layout tren manh hinh
	ListView lv;
	// Trinh phan tich XML
	public ParseXML pt;
	// Tong so san pham
	int size;
	// public int subListSize;
	String url = Variable.main3;
	private ArrayList<Product2> productList = new ArrayList<Product2>();
	TextView tv_page;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);
		
		Utils.setTitleFromActivityLabel(this, R.id.title_text);
		
		tv_page = (TextView) findViewById(R.id.main3_page);
		lv = (ListView) findViewById(R.id.main3_listview);
		more = (Button) findViewById(R.id.main3_more);
		more.setOnClickListener(this);
		back = (Button) findViewById(R.id.main3_back);
		back.setOnClickListener(this);
		//search = (ImageView) findViewById(R.id.main3_search);
		//search.setOnClickListener(this);
		// HttpClient client = new DefaultHttpClient();
		pt = new ParseXML();
		HttpPost request = new HttpPost(url);
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("cat_id", Variable.value_1));
		// postParameters.add(new BasicNameValuePair("subcat_id","2"));
		postParameters
				.add(new BasicNameValuePair("subcat_id", Variable.value_2));
		Log.w("", "cat_id" + Variable.value_1);
		Log.w("", "subcat_id" + Variable.value_2);
		postParameters.add(new BasicNameValuePair("page", "1"));
		postParameters.add(new BasicNameValuePair("num_count", "25"));

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
				// Log.w("","OK2");
				xmlReader.parse(input);
				size = pt.ID.size();
				Log.w("", "SIZE Of 3:" + pt.ID.size());
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
		// size=pt.ID.size();
		numberPage = (size % this.numberRecord == 0) ? size / numberRecord
				: size / numberRecord + 1;
		setPage();
		setState();
		productAdapter = new ProductAdapter2(this, R.layout.list2, productList);
		addProduct();
		lv.setAdapter(productAdapter);
		lv.setOnItemClickListener(this);
	}

	public void addProduct() {
		Product2 product;
		int i = 0;
		if (next == numberPage && size % numberRecord != 0) {
			numberRecord = size % numberRecord;
		} else if (size < numberRecord) {
			numberRecord = size;
		} else {
			numberRecord = Variable.numberRecord;
		}
		Log.w("", "NumberRecord Of 3:" + numberRecord);
		Log.w("", "Next Of 3: " + next);
		for (i = 0; i < numberRecord; i++) {
			/*
			 * if(pt.ID.get(i).equals("0")) { continue; } else {
			 */
			product = new Product2(pt.name.get(i), pt.ID.get(i), pt.uri.get(i),
					pt.Author.get(i));
			productList.add(product);
			// }

		}
		productAdapter.notifyDataSetChanged();
	}

	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		// TODO Auto-generated method stub
		intent = new Intent(this, Product_Detail.class);
		Product p = this.productList.get(position);
		Variable.value_3 = p.getID();
		Log.i("", "ID3: " + Variable.value_3);
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
			Log.w("", "MORE3");
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
				postParameters.add(new BasicNameValuePair(Variable.id_2,
						Variable.value_2));
				postParameters.add(new BasicNameValuePair(num_count, ""
						+ Variable.numberRecord));
				postParameters.add(new BasicNameValuePair(page, "" + next));
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
				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair(Variable.id_1,
						Variable.value_1));
				postParameters.add(new BasicNameValuePair(Variable.id_2,
						Variable.value_2));
				postParameters.add(new BasicNameValuePair(num_count, ""
						+ Variable.numberRecord));
				postParameters.add(new BasicNameValuePair(page, "" + next));
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
