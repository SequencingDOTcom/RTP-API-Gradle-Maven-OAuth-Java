package com.sequencing.oauth.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sequencing.oauth.config.AuthenticationParameters;
import com.sequencing.oauth.core.Token;

/**
 * Helper for common HTTP request processing routines
 */
public class HttpHelper
{
	private static final Logger log = LoggerFactory.getLogger(HttpHelper.class);

	/**
	 * Adds basic authentication headers and executes GET request
	 * @param uri GET request URL
	 * @param token object with access and refresh token values present 
	 * @return String server reply
	 */
	public static String doOauthSecureGet(String uri, Token token)
	{
		Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", String.format("Bearer %s", token.getAccessToken()));
		return doGet(uri, headers);
	}

	/**
	 * Adds oAuth authentication headers headers executes POST request
	 * @param uri request URL
	 * @param authParams configuration parameters needed for authentication
	 * @param params POST request parameters
	 * @return String server reply
	 */
	public static String doBasicSecurePost(String uri, AuthenticationParameters authParams, Map<String, String> params)
	{
		Map<String, String> headers = getBasicAuthenticationHeader(
				authParams.getClientId(),
				authParams.getClientSecret());
		
		return doPost(uri, headers, params);
	}

	/**
	 * Executes GET request
	 * @param uri request URL
	 * @param headers additional request headers
	 * @return String server reply
	 */
	private static String doGet(String uri, Map<String, String> headers)
	{
		try {
			return executeRequest(new HttpGet(uri), headers);
		}
		catch (IOException e) {
			log.debug("Error executing HTTP GET request to " + uri, e);
		}
		catch (ParseException e) {
			log.debug("Error executing HTTP GET request to " + uri, e);
		}
		
		return null;
	}

	/**
	 * Basic method for executing HTTP request
	 * @param request request object
	 * @param headers additional request headers
	 * @return String server reply
	 * @throws IOException
	 */
	private static String executeRequest(HttpRequestBase request, Map<String, String> headers) throws IOException
	{
		if (headers != null) {
			for (Map.Entry<String, String> h : headers.entrySet())
				request.addHeader(h.getKey(), h.getValue());
		}
		
		HttpResponse response = getHttpClient().execute(request);
		
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200)
			throw new RuntimeException(request.getURI() + " returned code " + statusCode);

		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}
	
	/**
	 * Returns result of POST request
	 * @param uri request URL
	 * @param headers additional request headers
	 * @param params additional request parameters
	 * @return String server reply
	 */
	private static String doPost(String uri, Map<String, String> headers, Map<String, String> params)
	{
		try {
			HttpPost post = new HttpPost(uri);

			if (params != null) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> p : params.entrySet())
					pairs.add(new BasicNameValuePair(p.getKey(), p.getValue()));

				post.setEntity(new UrlEncodedFormEntity(pairs));
			}

			return executeRequest(post, headers);
		} 
		catch (IOException e) {
			log.debug("Error executing HTTP POST request to " + uri, e);
		}
		catch (ParseException e) {
			log.debug("Error executing HTTP POST request to " + uri, e);
		}
		return null;
	}

	/**
	 * Returns headers for basic authentication
	 * @param username
	 * @param password
	 */
	private static Map<String, String> getBasicAuthenticationHeader(String username, String password)
	{
		byte[] encodedBytes = Base64.encodeBase64((username + ":" + password).getBytes());
		String encoded = new String(encodedBytes);
		Map<String, String> header = new HashMap<String, String>(1);
		header.put("Authorization", "Basic " + encoded);
		return header;
	}
	
	private static HttpClient getHttpClient()
	{
		return HttpClientBuilder.create().build();
	}
}