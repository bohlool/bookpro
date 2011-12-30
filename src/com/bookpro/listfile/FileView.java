
// Phan view cua cac doi tuong trong list cac file

package com.bookpro.listfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bookpro.R;

public class FileView extends LinearLayout{
	private TextView tv, 	// Ten file
					tv1;	// Thong tin ve file
	private FileItem fileItem;

	public FileView(Context context, FileItem f) {
		super(context);
		// Tao layout tu file xml
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(
				  Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.file_view, this, true);
		
		tv = (TextView)this.findViewById(R.id.fileTextView);
		tv1 = (TextView)this.findViewById(R.id.fileInfoTextView);
		
		this.setFile(f);
	}

	public void setFile(FileItem f) {
		fileItem = f;
		tv.setText(fileItem.getName());
		tv1.setText(fileItem.getInfo());
	}
}
