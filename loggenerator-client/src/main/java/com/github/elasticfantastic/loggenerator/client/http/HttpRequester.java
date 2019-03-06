package com.github.elasticfantastic.loggenerator.client.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequester {

	private String host;
	private String method;

	private HttpURLConnection connection;

	public HttpRequester(String host, String method) {
		this.host = host;
		this.method = method;
	}

	private HttpURLConnection prepareRequest() throws IOException {
		URL url = new URL(host);
		URLConnection conn = url.openConnection();
		HttpURLConnection http = (HttpURLConnection) conn;
		http.setRequestMethod(method);
		http.setDoOutput(true);
		http.setDoInput(true);
		return http;
	}

	public String getResponseBody() throws IOException {
		if (this.connection == null) {
			this.connection = prepareRequest();
		}

		BufferedReader br;
		if (200 <= this.connection.getResponseCode() && this.connection.getResponseCode() <= 299) {
			br = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(this.connection.getErrorStream()));
		}
		StringBuilder builder = new StringBuilder();
		String inputLine = "";
		while ((inputLine = br.readLine()) != null) {
			builder.append(inputLine);
		}
		return builder.toString();
	}

	public int getResponseCode() throws IOException {
		if (this.connection == null) {
			this.connection = prepareRequest();
		}
		return this.connection.getResponseCode();
	}

}
