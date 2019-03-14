package com.github.elasticfantastic.loggenerator.client.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class which performs an HTTP request to the specified host.
 * 
 * @author Daniel Nilsson
 */
public class HttpRequester {

    private String host;
    private String method;

    private HttpURLConnection connection;

    /**
     * Default constructor.
     * 
     * @param host
     *            the host to make requests to
     * @param method
     *            the HTTP method, i.e. GET or POST
     */
    public HttpRequester(String host, String method) {
        this.host = host;
        this.method = method;
    }

    /**
     * Prepare the HTTP request for a future call
     * 
     * @return a HTTP connection to the specified host with the specified HTTP method
     * @throws IOException
     *             if the connection to the host couldn't be opened
     */
    private HttpURLConnection prepareRequest() throws IOException {
        URL url = new URL(host);
        URLConnection conn = url.openConnection();
        HttpURLConnection http = (HttpURLConnection) conn;
        http.setRequestMethod(method);
        http.setDoOutput(true);
        http.setDoInput(true);
        return http;
    }

    /**
     * Returns the response body from the prepared request
     * 
     * @return the response body
     * @throws IOException
     *             if the response body couldn't be read
     */
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

    /**
     * Returns the response code from the prepared request
     * 
     * @return the response code
     * @throws IOException
     *             if the response code couldn't be read
     */
    public int getResponseCode() throws IOException {
        if (this.connection == null) {
            this.connection = prepareRequest();
        }
        return this.connection.getResponseCode();
    }

}
