package com.qa.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {
	
	public int RESPONSE_STATUS_CODE_200 = 200;
	public int RESPONSE_STATUS_CODE_201 = 201;
	public int RESPONSE_STATUS_CODE_400 = 400;
	public int RESPONSE_STATUS_CODE_401 = 401;
	public int RESPONSE_STATUS_CODE_500 = 500;
	
	//1.GET Method
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpResponse response = null;
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet (url);  //Create Get connection with this UR		
		response = httpClient.execute(httpGet);
		
		return response;	
	}
	
	//2.GET Method with Headers
		public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
			CloseableHttpResponse response = null;
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet (url);  //Create Get connection with this URL
			for (Map.Entry<String, String> entry: headerMap.entrySet()) {
				httpGet.addHeader(entry.getKey(), entry.getValue());
			}
			response = httpClient.execute(httpGet);
			
			return response;	
		}
	
		
		//3. POST Method:
		
		public CloseableHttpResponse postAPI(String url, String entityString,HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
			CloseableHttpResponse response = null;
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost (url);  //Create POST connection with this URL
			httpPost.setEntity(new StringEntity(entityString));
			
			//for Headers:
			for (Map.Entry<String, String> entry: headerMap.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
			response = httpClient.execute(httpPost);  // Execute POST Request
			return response;
		}
	
		

}
