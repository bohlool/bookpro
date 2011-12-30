package com.bookpro.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bookpro.R;
import com.bookpro.product.Search;
import com.bookpro.product.Variable;
import com.bookpro.ui.HomeActivity;

public class Utils {
	public static void goHome(Context context) {
		final Intent intent = new Intent(context, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public static void goSearch(Activity activity) {
		activity.startActivity(new Intent(activity.getApplicationContext(),
				Search.class));
	}

	public static void setTitleFromActivityLabel(Activity activity,
			int textViewId) {
		TextView tv = (TextView) activity.findViewById(textViewId);
		if (tv != null) {
			tv.setText(activity.getTitle());
		}
	}

	public static void toast(Activity activity, String msg) {
		Toast.makeText(activity.getApplicationContext(), msg,
				Toast.LENGTH_SHORT).show();
	}

	public static void toastLong(Activity activity, String msg) {
		Toast.makeText(activity.getApplicationContext(), msg, Toast.LENGTH_LONG)
				.show();
	}

	public static void createPreference(Activity activity, String key,
			String value) {
		SharedPreferences settings = activity.getSharedPreferences(
				Constants.LAST_READ_BOOK, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getPreference(Activity activity, String key) {
		SharedPreferences settings = activity.getSharedPreferences(
				Constants.LAST_READ_BOOK, Context.MODE_PRIVATE);
		return settings.getString(key, "");
	}

	public static boolean validate(final String username, final String regex) {
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(username);
		return matcher.matches();
	}

	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"MM/dd/yyyy HH:mm:ss");

	public static InputStream getResponese(String url, List<NameValuePair> list) {
		InputStream in = null;
		HttpPost request = new HttpPost(url);
		UrlEncodedFormEntity formEntity = null;
		try {
			formEntity = new UrlEncodedFormEntity(list);
			request.setEntity(formEntity);
			HttpResponse response = Variable.client.execute(request);
			in = response.getEntity().getContent();
		} catch (Exception e) {
			Log.e("e_utils", e.toString());
		}
		return in;
	}

	public static InputStream getResponese(String url) {
		InputStream in = null;
		HttpPost request = new HttpPost(url);
		try {
			HttpResponse response = Variable.client.execute(request);
			in = response.getEntity().getContent();
		} catch (Exception e) {
			Log.e("e_utils", e.toString());
		}
		return in;
	}

	public static String convertStreamToString(InputStream is)
			throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				// is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	public static void createDialog(Activity activity, String title, String text) {
		AlertDialog ad = new AlertDialog.Builder(activity)
				.setPositiveButton("OK", null).setTitle(title).setMessage(text)
				.create();
		ad.show();
	}

	public static boolean isNetworkAvailable(Activity activity) {
		Context context = activity.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			createDialog(activity, activity.getString(R.string.title_error),
					"getSystemService rend null");
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String standardizeMoney(String money) {
		String result = "";
		if (money != null && money.equals("") == false) {
			try {
				int m = Integer.parseInt(money);
				int n;
				while (m != 0) {
					// Lay phan du
					n = m % 1000;
					// Them vao sau chuan hoa
					result = n + "," + result;
					// Gan m bang thuong cua no voi 1000
					m = m / 1000;
				}
				result += Constants.UNIT_MONEY;
			} catch (Exception e) {
				// Khong lam gi
			}
		} else {
			result = "0," + Constants.UNIT_MONEY;
		}
		return result;
	}

	public static void openPdf(Activity activity, String pathFile) {
		if (pathFile.equals("")) {
			Utils.toast(activity, "You didn't have read any book yet!");
		} else {
			File file = new File(pathFile);

			if (file.exists()) {
				Uri path = Uri.fromFile(file);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(path, "application/pdf");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				try {
					activity.startActivity(intent);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(activity,
							"No Application Available to View PDF",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
