package com.yx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParserException;

public class HttpDownloader {
	private URL url = null;
	//һ��Ҫ���ı�
		public String download(String urlStr) {
			StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader buffer = null;
			// ����һ��url����
			try {
				url = new URL(urlStr);
				// ����һ��Http����
				HttpURLConnection urlConn = (HttpURLConnection) url
						.openConnection();
				// ʹ��IO����ȡ����
				
				buffer = new BufferedReader(new InputStreamReader(
						urlConn.getInputStream()));
				System.out.println("wwwww--->downloadXMLover");
				while ((line = buffer.readLine()) != null) {
					sb.append(line);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					buffer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return sb.toString();
		}
		
		/**
		 * �ú�����������-1�����������ļ����� 0�����������ļ��ɹ� 1�������ļ��Ѿ�����
		 * */
		public int downFile(String urlStr, String path, String fileName){
			InputStream inputStream = null;
			try{
				FileUtils fileUtils = new FileUtils();
				if(fileUtils.isFileExist(fileName, path)){
					return 1;
				}else{
					inputStream = getInputStreamFromUrl(urlStr);
					File resultFile = fileUtils.writeIOFromInput(path, fileName, inputStream);
					if(resultFile == null){
						return -1;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				return -1;
			}finally{
				try{
					inputStream.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return 0;
		}
		
		/**
		 *����url�õ�������
		 * 
		 * */
		public InputStream getInputStreamFromUrl(String urlStr) throws XmlPullParserException, IOException{
			url = new URL(urlStr);
			HttpURLConnection urlconn = (HttpURLConnection) url.openConnection();
			InputStream inputStream = urlconn.getInputStream();
			return inputStream;
		}
}
