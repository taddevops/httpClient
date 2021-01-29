package com.qa.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

/**
 * 
 * @author:- srinivastadakamalla
 * @Date  :- mercredi 27 01 2021
 */
		
public class GetAPITest extends TestBase {
	
	TestBase testBase;
	String url;
	String serviceUrl;
	String apiUrl;
	
	CloseableHttpResponse response;
	String responseString;

	@BeforeMethod
	public void setUp() {
		
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		
		url = serviceUrl+apiUrl;
		
	}
	
	@Test
	public void getUserTest() throws ClientProtocolException, IOException {
		RestClient restClient = new RestClient();
		response = restClient.get(url);
		//a. statusCode
		int statusCode = response.getStatusLine().getStatusCode();
		 System.out.println("Status Code------> " +statusCode);
		 Assert.assertEquals(statusCode,restClient.RESPONSE_STATUS_CODE_200, "Status Code is not 200");
		 
		//b.  Json String
		try {
			responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}	
		
		JSONObject jsonString = new JSONObject(responseString);
		System.out.println("Response JSON ---> "+jsonString);
		
		//PerPage value validation using JSON Util file
		String perPageValue = TestUtil.getValueByJPath(jsonString, "/per_page");
		System.out.println("Per Page Value is ---> "+perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);
		
		//
		String total = TestUtil.getValueByJPath(jsonString, "/total");
		System.out.println("Per Page Value is ---> "+total);
		Assert.assertEquals(Integer.parseInt(total), 12);
		
		//Get The value from JSON ARRAY
		
		
		//c.  All Headers
		Header[] headersArray = response.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		
		System.out.println("Headers" +allHeaders);
	
	}
	
	
	
	@Test
	public void getAPIWithHeadersTest() throws ClientProtocolException, IOException {
		RestClient restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		response = restClient.get(url, headerMap);
		//a. statusCode
		int statusCode = response.getStatusLine().getStatusCode();
		 System.out.println("Status Code------> " +statusCode);
		 Assert.assertEquals(statusCode,restClient.RESPONSE_STATUS_CODE_200, "Status Code is not 200");
		 
		//b.  Json String
		try {
			responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}	
		
		JSONObject jsonString = new JSONObject(responseString);
		System.out.println("Response JSON ---> "+jsonString);
		
		//PerPage value validation using JSON Util file
		String perPageValue = TestUtil.getValueByJPath(jsonString, "/per_page");
		System.out.println("Per Page Value is ---> "+perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);
		
		//
		String total = TestUtil.getValueByJPath(jsonString, "/total");
		System.out.println("Per Page Value is ---> "+total);
		Assert.assertEquals(Integer.parseInt(total), 12);
		
		//Get The value from JSON ARRAY
		String lastName = TestUtil.getValueByJPath(jsonString, "/data[0]/last_name");
		String firstName = TestUtil.getValueByJPath(jsonString, "/data[0]/first_name");
		
		//c.  All Headers
		Header[] headersArray = response.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		
		System.out.println("Headers" +allHeaders);
	
	}

}
