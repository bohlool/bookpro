package com.bookpro.product;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookpro.R;
import com.bookpro.ui.LoginActivity;
import com.bookpro.util.Constants;
import com.bookpro.util.Utils;

public class Product_Detail extends Activity implements OnClickListener {
	TextView name, price, number_Download, detail, author, nxb, page, format,
			type;
	Intent i2;
	ImageView image;
	Button download;
	ParseXML2 pt;
	InputStream in;
	AlertDialog a;
	String urls = Variable.productDetail;
	AlertDialog.Builder b, downloadSucess;
	ImageDownload id = new ImageDownload();
	ProgressDialog mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail);

		Utils.setTitleFromActivityLabel(this, R.id.title_text);
		mProgressDialog = new ProgressDialog(Product_Detail.this);
		mProgressDialog.setMessage("Downloading...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMax(100);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

		name = (TextView) findViewById(R.id.detail_name);
		price = (TextView) findViewById(R.id.detail_price);
		number_Download = (TextView) findViewById(R.id.detail_numberDownload);
		detail = (TextView) findViewById(R.id.detail_detail);
		author = (TextView) findViewById(R.id.detail_author);
		nxb = (TextView) findViewById(R.id.detail_nxb);
		// page = (TextView) findViewById(R.id.detail_page);
		// format = (TextView) findViewById(R.id.detail_fomat);
		image = (ImageView) findViewById(R.id.detail_image);
		type = (TextView) findViewById(R.id.detail_type);
		a = new AlertDialog.Builder(this).create();
		a.setTitle("Downloading...");
		a.setMessage("Please wait!");

		downloadSucess = new AlertDialog.Builder(this);
		downloadSucess.setTitle("Download Sucessfull");
		downloadSucess.setMessage("Download Sucessfull");

		downloadSucess.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'Yes' Button
						dialog.dismiss();
					}
				});
		download = (Button) findViewById(R.id.detail_download);
		download.setOnClickListener(this);
		// Bundle receiveBundle = this.getIntent().getExtras();
		// final String infor =receiveBundle.getString("id");
		pt = new ParseXML2();
		// HttpClient client = new DefaultHttpClient();

		HttpPost request = new HttpPost(urls);
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		// more.setText(infor);
		postParameters.add(new BasicNameValuePair(Variable.id_3,
				Variable.value_3));
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

		name.setText(pt.getName());
		price.setText("Price: " + pt.getPrice());
		number_Download.setText("Number Download: " + pt.getNumberDownload());
		detail.setText("Descripsion: " + pt.getDeail());
		author.setText(pt.getAuthor());
		nxb.setText("Published: " + pt.getNXB());
		// page.setText("Number of page: " + pt.getPage());
		type.setText("Type: " + pt.getType());
		// format.setText("Format: " + pt.getFormat());
		Bitmap bm = id.Download(pt.getImage());
		image.setImageBitmap(Bitmap.createScaledBitmap(bm, 100, 150, false));
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == download) {
			// Kiem tra xem trang thai cua user da dang nhap hay chua
			if (Variable.login == true) {
				// Neu tai so du tai khoan la lon hon so du cua cuon sach
				if (testAccount()) {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							Product_Detail.this);
					alertDialog.setCancelable(false);
					alertDialog.setTitle("Are you sure?");
					alertDialog
							.setMessage("Do you want to buy this e-book with "
									+ price.getText().toString() + "?");

					alertDialog.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// Action for 'Yes' Button
									dialog.dismiss();
									// a.show();
									Log.w("", "Starting download!");
									// DownloadFile();
									new DownloadFile().execute(pt.getLink());
								}
							});
					alertDialog.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// Action for 'No' Button
									dialog.dismiss();
								}
							});
					alertDialog.create();
					alertDialog.show();
					Log.w("", "Starting download!");

					Log.w("", "Download sucessfull!");
				} else {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							Product_Detail.this);
					alertDialog.setCancelable(false);
					alertDialog.setTitle("Message from admin");
					alertDialog
							.setMessage("Your account is not enough to buy this e-book");
					alertDialog.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// Action for 'Yes' Button
									dialog.dismiss();
								}
							});

					alertDialog.create();
					alertDialog.show();
				}
			}
			// Neu chua dang nhap thi user phai dang nhap
			if (Variable.login == false) {

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						Product_Detail.this);
				alertDialog.setCancelable(false);
				alertDialog.setTitle("You must have login to buy");
				alertDialog.setMessage("Do you want to login?");
				alertDialog.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Action for 'Yes' Button
								dialog.dismiss();
								Intent i = new Intent(Product_Detail.this,
										LoginActivity.class);
								startActivity(i);
							}
						});
				alertDialog.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Action for 'No' Button
								dialog.dismiss();
							}
						});
				alertDialog.create();
				alertDialog.show();

			}

		}
	}

	// function to Download File use Thread
	public void DownloadFile() {
		Thread t = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					URL url = new URL(pt.getLink());
					// URL url = new URL("http://192.168.116.1/download/1.pdf");
					HttpURLConnection c = (HttpURLConnection) url
							.openConnection();
					c.setRequestMethod("GET");
					c.setDoOutput(true);
					c.connect();
					String fileName = getNamOfBook();
					// String fileName="Triger in SQL.pdf";
					String PATH = Constants.LIBRARY_PATH;// Environment.getExternalStorageDirectory()+
															// "/download";
					Log.v("log_tag", "PATH: " + PATH);
					File file = new File(PATH);
					file.mkdirs();
					File outputFile = new File(file, fileName);
					FileOutputStream fos = new FileOutputStream(outputFile);

					InputStream is = c.getInputStream();

					byte[] buffer = new byte[1024];
					int len1 = 0;
					while ((len1 = is.read(buffer)) != -1) {
						fos.write(buffer, 0, len1);
					}
					fos.close();
					is.close();
					a.dismiss();
				} catch (IOException e) {
					Log.d("log_tag", "Error: " + e);

				}
				Log.v("log_tag", "Check: ");

			}
		};
		t.start();

	}

	// Ham lay ten cua cuon sach tu url cua cuon sach
	public String getNamOfBook() {
		String name = "";
		String link = pt.getLink();
		Log.w("", "Link ne:" + pt.getLink());
		;
		char ch[] = link.toCharArray();
		int k = link.length() - 1;
		while (ch[k] != '/') {
			k--;
		}
		Log.i("", "K1 " + k);
		for (int m = k + 1; m < link.length(); m++) {
			name += ch[m];
		}
		Log.i("", "Name: " + name);
		return name;

	}

	// Ham de kiem tra xem tai khoan cua nguoi dung co
	// du de mua cuon sach nay khong
	public boolean testAccount() {
		String result;
		boolean ok = false;
		Log.i("", "ha van phuong" + " ha thu huong");
		try {
			result = getInfor();
			int h = Integer.parseInt(result.trim());
			Log.i("", h + "b");
			if (h == 1) {
				ok = true;
				Log.i("", "kaka: " + ok);
				Log.i("", "Duoc phep mua cuon sach nay:");
			} else {
				ok = false;
				Log.i("", "kaka: " + ok);
				Log.i("", "Khong duoc phep mua cuon sach nay:");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ok;
	}

	// ham de lay du lieu tu trang buy.php
	public String getInfor() throws Exception {
		BufferedReader in = null;
		try {
			// HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost(Variable.buy);

			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair(Variable.id_buy,
					Variable.value_3));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);

			request.setEntity(formEntity);
			HttpResponse response = Variable.client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();

			String result = sb.toString();

			return result;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void onHomeClick(View v) {
		Utils.goHome(this);
	}

	public void onSearchClick(View v) {
		Utils.goSearch(this);
	}

	private class DownloadFile extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mProgressDialog.show();
		}

		@Override
		protected String doInBackground(String... url) {
			int count;
			try {
				URL url2 = new URL(url[0]);
				URLConnection conexion = url2.openConnection();
				conexion.connect();
				// this will be useful so that you can show a tipical 0-100%
				// progress bar
				int lenghtOfFile = conexion.getContentLength();

				// downlod the file
				InputStream input = new BufferedInputStream(url2.openStream());

				String fileName = getNamOfBook();
				String PATH = Constants.LIBRARY_PATH;
				OutputStream output = new FileOutputStream(PATH + "/"
						+ fileName);

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					// publishing the progress....
					publishProgress((int) (total * 100 / lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		public void onProgressUpdate(Integer... args) {
			// here you will have to update the progressbar
			// with something like
			mProgressDialog.setProgress(args[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			mProgressDialog.dismiss();

			AlertDialog.Builder builder = new AlertDialog.Builder(
					Product_Detail.this);
			builder.setTitle("Download completed");
			builder.setMessage("Dow you want to read this e-book now?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									String fileName = getNamOfBook();
									String PATH = Constants.LIBRARY_PATH;
									Utils.openPdf(Product_Detail.this, PATH
											+ "/" + fileName);
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
		}
	}
}
