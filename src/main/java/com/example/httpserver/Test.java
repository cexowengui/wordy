package com.example.httpserver;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @project SimpleHttpServer
 * @author 
 * @vresion 1.0 
 * @description 测试类  
 */
public class Test {
	public static void main(String[] args) {
		
		ExecutorService exec = Executors.newCachedThreadPool();
		// 测试并发20个请求对MyHttpServer的影响
		for (int i = 0; i < 20; i++) {
			Runnable run = new Runnable() {
				public void run() {
					try {
						startWork();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			exec.execute(run);
		}
		exec.shutdown();// 关闭线程池
	}

	public static void startWork() throws IOException {
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String TimeString = time.format(new java.util.Date());
		//System.out.println(TimeString);
		URL url = new URL("http://127.0.0.1:6666/myApp");
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setDoOutput(true);
		urlConn.setDoInput(true);
		urlConn.setRequestMethod("POST");
		// 测试内容包
		String teststr = "this is a test message " + TimeString;
		OutputStream out = urlConn.getOutputStream();
		out.write(teststr.getBytes());
		out.flush();
		while (urlConn.getContentLength() != -1) {
			if (urlConn.getResponseCode() == 200) {
				InputStream in = urlConn.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String temp = "";
				while ((temp = reader.readLine()) != null) {
					System.err.println("server response:" + temp);// 打印收到的信息
				}
				reader.close();
				in.close();
				urlConn.disconnect();
				break;
			}
		}
	}
}
