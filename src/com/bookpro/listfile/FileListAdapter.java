
// Phan control cua cac item trong list cac file

package com.bookpro.listfile;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FileListAdapter extends BaseAdapter{
	private Context mContext;
	private List<FileItem> mFileList;
	
	public FileListAdapter(Context context, List<FileItem> fileList){
		mContext = context;
		mFileList = fileList;
	}

	public int getCount() {
		return mFileList.size();
	}

	public Object getItem(int arg0) {
		return mFileList.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if(arg1 == null){
			return (new FileView(mContext, mFileList.get(arg0)));
		}else{
			((FileView)arg1).setFile(mFileList.get(arg0));
			return arg1;
		}
	}
}