package com.yx.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {

	private String SDCardRoot;
	
	public FileUtils(){
		//�õ���ǰ�ⲿ�洢�豸��Ŀ¼
		SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
	}
	
	/**
	 * ��SD���ϴ����ļ�
	 * */
	public File createFileInSDCard(String fileName,String dir) throws IOException{
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		System.out.println(file);
		return file;
	}
	
	/**
	 * ��SD���ϴ���Ŀ¼
	 * 
	 * */
	public File createSDCar(String dir){
		File dirFile = new File(SDCardRoot + dir + File.separator);
		System.out.println(dirFile.mkdir());
		return dirFile;
	}
	
	/**
	 * �ж�SD���ϵ��ļ����Ƿ����
	 * */
	public boolean isFileExist(String fileName, String path){
		File file = new File(SDCardRoot + path + File.separator + fileName);
		return file.exists();
	}
	
	/**
	 * ��һ��InputStream���������д�뵽SD����
	 * 
	 * */
	public File writeIOFromInput(String path, String fileName, InputStream inputStream){
		File file = null;
		OutputStream output = null;
		try{
			createSDCar(path);
			file = createFileInSDCard(fileName, path);
			output = new FileOutputStream(file);
			byte buffer [] = new byte[4 * 1024];
			int temp;
			while((temp = inputStream.read(buffer)) != -1){
				output.write(buffer, 0, temp);
			}
			output.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				output.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return file;
	}

	
	
	
}
