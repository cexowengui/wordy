package core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {

	private static HttpClient httpClient = new DefaultHttpClient();
	//HttpClient client = new HttpClient();	

	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws ParseException, IOException {
		String sn = "xxxxx"; 
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("sn", sn);

		httpGet("https://selfsolve.apple.com/agreementWarrantyDynamic.do",
				requestParams);
		httpClient.getConnectionManager().shutdown();
	}

	public static String httpGet(String url, Map<String, String> requestParams) throws ParseException, IOException {

		HttpGet httpGet = null;
		HttpEntity httpEntity = null;

		try {
			// 参数设置
			StringBuilder builder = new StringBuilder(url);
			builder.append("?");
			for (Map.Entry<String, String> entry : requestParams.entrySet()) {
				builder.append((String) entry.getKey());
				builder.append("=");
				builder.append((String) entry.getValue());
				builder.append("&");
			}

			String tmpUrl = builder.toString();
			tmpUrl = tmpUrl.substring(0, tmpUrl.length() - 1);

			httpGet = new HttpGet(tmpUrl);

			//System.out.println("executing request " + httpGet.getURI());
			//System.out.println("-------------------------------------");

			HttpResponse response = httpClient.execute(httpGet);

			// reponse header
			System.out.println(response.getStatusLine().getStatusCode());

			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				System.out.println(header.getName() + ": " + header.getValue());
			}

			System.out.println();

			// 网页内容
			httpEntity = response.getEntity();
			

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpGet != null) {
				httpGet.abort();
			}
		}
		return EntityUtils.toString(httpEntity);
	}

	public static void httpPost(String url, Map<String, String> requestParams, String urlEncode) {

		HttpPost httpPost = null;

		try {
			// 参数设置
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : requestParams.entrySet()) {
				params.add(new BasicNameValuePair((String) entry.getKey(),
						(String) entry.getValue()));
			}

			httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params, urlEncode));

			System.out.println("executing request " + httpPost.getURI());
			System.out.println("-------------------------------------");

			// reponse header
			HttpResponse response = httpClient.execute(httpPost);
			System.out.println(response.getStatusLine().getStatusCode());

			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				System.out.println(header.getName() + ": " + header.getValue());
			}

			System.out.println();

			// 网页内容
			HttpEntity httpEntity = response.getEntity();
			System.out.println(EntityUtils.toString(httpEntity));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpPost != null) {
				httpPost.abort();
			}
		}
	}
}

