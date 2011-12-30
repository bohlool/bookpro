
// Phan model cua cac item trong list cac file

package com.bookpro.listfile;

import java.io.File;

import com.bookpro.util.Utils;

public class FileItem {
	private File file;		// Doi tuong file
	private String name;	// Ten file
	private String info;	// Thong tin cua file: dung luong, ngay sua cuoi

	public FileItem(File f) {
		file = f;
		name = file.getName();
		info = file.length() / 1024 + " KB | " + Utils.sdf.format(file.lastModified());
	}

	public File getFile() {
		return file;
	}

	public void setFile(File f) {
		file = f;
	}

	public String getName() {
		return name;
	}

	public String getInfo() {
		return info;
	}

	// Tra ve duong dan tuyet doi cua file
	public String toString() {
		return file.getAbsolutePath();
	}

	// So sanh 2 doi tuolng FileItem
	public boolean equals(Object obj) {
		if (obj != null && obj.getClass().isInstance(this)
				&& ((FileItem) obj).toString().equals(this.toString())) {
			return true;
		} else {
			return false;
		}
	}

	// Tao ban copy cua doi tuong FileItem
	public FileItem copy() {
		return new FileItem(new File(this.toString()));
	}
}
