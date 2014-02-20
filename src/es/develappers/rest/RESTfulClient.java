package es.develappers.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RESTfulClient {

	protected String baseURL;
	protected ResponseHandler<Response> handler;
	
	public RESTfulClient(String baseURL) 
	{
		this.baseURL = baseURL;
		this.handler = new ResponseHandler<Response>() {
			@Override
			public Response handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
				int responseCode = httpResponse.getStatusLine().getStatusCode();
				
				Object responseJSON = null;
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					try{ 
						String responseBody = RESTfulClient.readResponseBody(httpEntity.getContent());
						JSONParser parser = new JSONParser();
						responseJSON = parser.parse(responseBody);
					} catch (Exception e) {
						throw new ClientProtocolException(e);
					}
				}
				
				Response response = new Response();
				response.setCode(responseCode);
				response.setJSON(responseJSON);
				return response;
			}
		};
	}
	
	protected static String readResponseBody(InputStream is) throws Exception
	{
	    String line = "";
	    StringBuilder total = new StringBuilder();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader rd = new BufferedReader(isr);
	    while ((line = rd.readLine()) != null) { 
	        total.append(line); 
	    }
	    isr.close();
	    rd.close();
	    is.close();
	    return total.toString();
	}	

	public Response post(String endPoint) throws Exception
	{
		String endPointURL = this.baseURL + endPoint;
		HttpPost httpPost = new HttpPost(endPointURL);
		Response response = new DefaultHttpClient().execute(httpPost, this.handler);
		return response;
	}
	
	public Response post(String endPoint, JSONObject json) throws Exception
	{
		String endPointURL = this.baseURL + endPoint;
		HttpPost httpPost = new HttpPost(endPointURL);
		StringEntity input = new StringEntity(json.toJSONString());
		input.setContentType("application/json");
		httpPost.setEntity(input);		
		Response response = new DefaultHttpClient().execute(httpPost, this.handler);
		return response;
	}
	
	public Response put(String endPoint, JSONObject json) throws Exception
	{
		String endPointURL = this.baseURL + endPoint;		
		HttpPut httpPut = new HttpPut(endPointURL);
		StringEntity input = new StringEntity(json.toJSONString());
		input.setContentType("application/json");
		httpPut.setEntity(input);		
		Response response = new DefaultHttpClient().execute(httpPut, this.handler);
		return response;
	}
	
	public Response get(String endPoint) throws Exception
	{
		String endPointURL = this.baseURL + endPoint;
		
		HttpGet httpGet = new HttpGet(endPointURL);
		Response response = new DefaultHttpClient().execute(httpGet, this.handler);
		
		return response;
	}
	
}