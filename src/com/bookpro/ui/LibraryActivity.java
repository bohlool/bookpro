package com.bookpro.ui;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.bookpro.R;
import com.bookpro.listfile.FileItem;
import com.bookpro.listfile.FileListAdapter;
import com.bookpro.util.Constants;
import com.bookpro.util.Utils;

public class LibraryActivity extends Activity{
	private ArrayList<FileItem> listFile;
	private FileListAdapter fileListAdapter;
	private ListView fileListView;
	
	private File[] files;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		listFile = new ArrayList<FileItem>();
		fileListAdapter = new FileListAdapter(this, listFile);
		
		this.setContentView(R.layout.activity_library);
		Utils.setTitleFromActivityLabel(this, R.id.title_text);
		
		fileListView = (ListView)findViewById(R.id.fileListView);
		
		if(fileListView != null)
			fileListView.setAdapter(fileListAdapter);
		else
			Utils.toast(this, "ERROR: list view is null!");
		
		if(fileListView != null)
			fileListView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {				
				File file = ((FileItem)fileListAdapter.getItem(arg2)).getFile();
				if (file.exists()) {
					Uri path = Uri.fromFile(file);
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(path, "application/pdf");
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					try {
						Utils.createPreference(LibraryActivity.this, Constants.LAST_READ_BOOK, file.getAbsolutePath());
						startActivity(intent);
					} catch (ActivityNotFoundException e) {
						Toast.makeText(LibraryActivity.this, "No application available to view PDF.",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(LibraryActivity.this, "File not found", Toast.LENGTH_SHORT).show();
				}
			}
			
		});

		this.getListFile();
	}
	
	public void getListFile(){
		File directory = new File(Constants.LIBRARY_PATH);
		if (directory.exists() == false) {
			directory.mkdirs();
		} else {
			files = directory.listFiles(new Filter());
			for (int i = 0; i < files.length; i++) {
				this.addFile(new FileItem(files[i]));
			}
		}
	}
	
	public void addFile(FileItem f){
		this.listFile.add(f);
		this.fileListAdapter.notifyDataSetChanged();
	}

	class Filter implements FileFilter {
		public boolean accept(File file) {
			return file.getName().endsWith("pdf");
		}
	}

	public void onHomeClick(View v){
		Utils.goHome(this);
	}
	
	public void onSearchClick(View v){
		Utils.goSearch(this);
	}	
}