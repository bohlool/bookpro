package com.bookpro.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.bookpro.R;
import com.bookpro.util.Utils;

public class Search extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	Button ok, cacel;
	Intent intent;
	EditText text;
	RadioButton name, author;
	public static String type ;
	public static String value;

	RadioGroup r;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		Utils.setTitleFromActivityLabel(this, R.id.title_text);
		
		ok = (Button) findViewById(R.id.seach_ok);
		ok.setOnClickListener(this);
		//cacel = (Button) findViewById(R.id.seach_cancel);
		//cacel.setOnClickListener(this);
		name = (RadioButton) findViewById(R.id.search_name);
		author = (RadioButton) findViewById(R.id.search_author);
		text = (EditText) findViewById(R.id.seach_text);
		r = (RadioGroup) findViewById(R.id.search_radioGroup);
		r.setOnCheckedChangeListener(this);
		// a=new AlertDialog(this);
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		// Nhan nut tim kiem
		if (arg0 == ok) {
			value = text.getText().toString();
			Log.w("", "Key : " + value);
			// Neu o du lieu la trong, show ra thong bao bat nhap vao
			if (value.equals("")) {
				Log.w("", "No data to search");
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						Search.this);
				alertDialog.setCancelable(false);
				alertDialog.setTitle("Notification");
				alertDialog.setMessage("You have to enter key to search!");

				alertDialog.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Action for 'Yes' Button
								dialog.dismiss();
							}
						});
				alertDialog.create();
				alertDialog.show();
			}
			// Neu dien du lieu day du
			else {
				 Variable.next=1;
				//Variable.strType="2";
				//type=Variable.strType;
				 if(name.isChecked()) {
					 type="1";
				 }
				 if(author.isChecked()) {
					 type="2";
				 }
				intent = new Intent(this, ResultOfSearch.class);
				startActivity(intent);
			}
		}
		// Nhan nut huy bo
		if (arg0 == cacel) {
			finish();
		}
	}

	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg0 == r) {
			if (name.isChecked()) {
				// neu la name
				Variable.strType="1";
				type = Variable.strType;
				Log.w("", "Type: "+type);
				Log.w("","Name vua duoc check!");
			}
			// Neu la author
			else if (author.isChecked()) {
				Variable.strType="2";
				type = Variable.strType;
				Log.w("", "Type: "+type);
				Log.w("","Author vua duoc check!");
			}
		}
	}

	public String getValue() {
		 //Log.w("","Value ne:"+);
		return value;

	}

	public String getType() {
		return type;
	}
}
