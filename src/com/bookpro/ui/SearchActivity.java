package com.bookpro.ui;

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
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bookpro.R;
import com.bookpro.product.ParseXML;
import com.bookpro.product.Product;
import com.bookpro.product.Product2;
import com.bookpro.product.ProductAdapter2;
import com.bookpro.product.Product_Detail;
import com.bookpro.product.Variable;
import com.bookpro.util.Utils;

public class SearchActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	EditText searchEditText;
	/** Called when the acSubProductIDtivity is first created. */
	public static int next = 1;
	
	//Search search = new Search();
	String value = "";
	String type = "1";
	
	public boolean kq = false;
	Intent intent;
	String num_count = Variable.num_Count;
	String pages = Variable.page;
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
	int size = 0;
	// public int subListSize;
	String url = Variable.search;
	private ArrayList<Product2> productList = new ArrayList<Product2>();
	TextView tv_page;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_search);
		Utils.setTitleFromActivityLabel(this, R.id.title_text);
		
		searchEditText = (EditText)this.findViewById(R.id.search_tv_search);

		Spinner spinner = (Spinner) findViewById(R.id.search_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.search_type_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		
		tv_page = (TextView) findViewById(R.id.result_page);
		lv = (ListView) findViewById(R.id.result_listview);
		more = (Button) findViewById(R.id.result_more);
		more.setOnClickListener(this);
		back = (Button) findViewById(R.id.result_back);
		back.setOnClickListener(this);
		
		productAdapter = new ProductAdapter2(this, R.layout.list2, productList);
		lv.setAdapter(productAdapter);
		lv.setOnItemClickListener(this);
	}

	public void onHomeClick(View v) {
		Utils.goHome(this);
	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			if(parent.getItemAtPosition(pos).toString().equals("Book")){
				searchEditText.setHint("Search by name");
			}else{
				searchEditText.setHint("Search by author");
			}
		}
		public void onNothingSelected(AdapterView parent) {
			// Do nothing.
		}
	}
	
	public void onSearchClick(View v){
		value = searchEditText.getText().toString();
		
		HttpClient client = new DefaultHttpClient();
		pt = new ParseXML();
		HttpPost request = new HttpPost(url);
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters
				.add(new BasicNameValuePair(Variable.value, /*search.value*/value));
		postParameters.add(new BasicNameValuePair(Variable.type, /*search.type*/type));
		/*
		 * postParameters.add(new BasicNameValuePair("type", "1"));
		 * postParameters.add(new BasicNameValuePair("value", "sach"));
		 */
		Log.w("", "Value ne: " + /*search.value*/value);
		Log.w("", "Type:" + /*search.type*/type);
		UrlEncodedFormEntity formEntity = null;
		try {
			formEntity = new UrlEncodedFormEntity(postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
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
				size = pt.name.size();
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
		// size=pt.ID.size();
		numberRecord = Variable.numberRecord;
		numberPage = (size % this.numberRecord == 0) ? size / numberRecord
				: size / numberRecord + 1;
		setPage();
		setState();
		
		addProduct();
		
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
		Log.w("", "NumberRecord:" + numberRecord);
		Log.w("", "Next Result: " + next);
		for (i = 0; i < numberRecord; i++) {
			if (pt.ID.get(i).equals("0"))
				continue;
			else {
				product = new Product2(pt.name.get(i), "kk", "ha van phuong",
						"Ha Van Phuong");
				productList.add(product);
			}

		}
		productAdapter.notifyDataSetChanged();
	}

	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		// TODO Auto-generated method stub
		intent = new Intent(this, Product_Detail.class);
		Product p = this.productList.get(position);
		if (p.getID().equals("No ID")) {
			Log.w("", "Khong co ket qua");
		} else {
			Variable.value_3 = p.getID();
			Log.i("", "ID3: " + Variable.value_3);
			startActivity(intent);
		}

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
		value = searchEditText.getText().toString();
		
		// TODO Auto-generated method stub
		setState();
		if (arg0 == more) {
			Log.w("", "MORE3");
			if (next < numberPage) {
				next++;
				setPage();
				InputStream in = null;
				HttpClient client = new DefaultHttpClient();
				pt = new ParseXML();
				HttpPost request = new HttpPost(url);
				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair(Variable.value,
						/*search.getValue()*/value));

				postParameters.add(new BasicNameValuePair(Variable.type, /*search
						.getType()*/type));

				// postParameters.add(new BasicNameValuePair(pages,
				// ""+Variable.numberRecord));
				// postParameters.add(new BasicNameValuePair(nexts, ""+next));
				UrlEncodedFormEntity formEntity = null;
				try {
					formEntity = new UrlEncodedFormEntity(postParameters);
					request.setEntity(formEntity);
					HttpResponse response = client.execute(request);
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
				HttpClient client = new DefaultHttpClient();
				pt = new ParseXML();
				HttpPost request = new HttpPost(url);
				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair(Variable.value,
						/*search.getValue()*/value));
				Log.w("", "Value ne: " + /*search.getValue()*/value);
				postParameters.add(new BasicNameValuePair(Variable.type, /*search
						.getType()*/type));

				// //postParameters.add(new BasicNameValuePair(pages, "25"));
				// postParameters.add(new BasicNameValuePair(nexts, ""+next));
				UrlEncodedFormEntity formEntity = null;
				try {
					formEntity = new UrlEncodedFormEntity(postParameters);
					request.setEntity(formEntity);
					HttpResponse response = client.execute(request);
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
			back.setEnabled(true);
		}
		if (next == numberPage) {

			more.setEnabled(false);
			back.setEnabled(true);
		}
		if (next == 1) {
			back.setEnabled(false);
			more.setEnabled(true);
		}
		if (size < Variable.numberRecord) {
			more.setEnabled(false);
		}
	}

	// Ham kiem tra xem ket qua cua viec tiem kiem co tra lai ban ghi nao ko
	// Neu co tra lai false
	// Nguoc lai tra lai true
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
}