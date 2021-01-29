package com.qa.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.User;
/**
 * 
 * @author:- srinivastadakamalla
 * @Date  :- jeudi 28 01 2021
 */
public class POSTAPITest extends TestBase {
	
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
	public void postCreateUserTest() throws JsonGenerationException, JsonMappingException, IOException {
		
		RestClient restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		//jackson API Marshaling from POJO to JSON
		ObjectMapper mapper = new ObjectMapper();
		User user = new User("Srinivas", "QA Automation");
		
		//POJO Object to JSON File
		mapper.writeValue(new File("/Users/srinivastadakamalla/eclipse-workspace/httpClientRestAPI/src/main/java/com/qa/data/user.json"), user);
		
		//Object to json in String
		String usersJsonString = mapper.writeValueAsString(user);
		System.out.println(usersJsonString);
		
		response = restClient.postAPI(url, usersJsonString, headerMap);
		
		//1.StatusCode
		System.out.println("Status Code :-->"+response.getStatusLine().getStatusCode());
		Assert.assertEquals(response.getStatusLine().getStatusCode(), restClient.RESPONSE_STATUS_CODE_201);
		
		//2.StatusCode
		String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON---->"+responseJson);
		
		//3. json to POJO unmarshalling
		User userObj = mapper.readValue(responseString, User.class);
		System.out.println("User Response :---> "+userObj);

		Assert.assertTrue(user.getName().equals(userObj.getName()));
		Assert.assertTrue(user.getJob().equals(userObj.getJob()));
		
	}
	
	
	
	

}
