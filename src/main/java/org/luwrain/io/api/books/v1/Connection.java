/*
 * Copyright 2020-2021 Michael Pozhidaev <msp@luwrain.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.luwrain.io.api.books.v1;

import java.io.*;
import java.net.*;
import java.util.*;

import com.google.gson.*;

public final class Connection
{
    private final String baseUrl;

    Connection(String baseUrl)
    {
	if (baseUrl == null)
	    throw new NullPointerException("baseUrl can't be null");
	if (baseUrl.isEmpty())
	    throw new IllegalArgumentException("baseUrl can't be empty");
	this.baseUrl = baseUrl;
    }

        public InputStream doGet(String resource, Map<String, String> args) throws IOException
    {
	return doGet(resource, args, true);
    }

    public InputStream doGet(String resource, Map<String, String> args, boolean useBaseUrl) throws IOException
    {
	if (resource == null)
	    throw new NullPointerException("resource may not be null");
	if (args == null)
	    throw new NullPointerException("args may not be null");
	if (resource.isEmpty())
	    throw new IllegalArgumentException("resource may not be null");
	final StringBuilder b = new StringBuilder();
	b.append(resource);
	boolean first = true;
	for(Object o: args.entrySet())
	{
	    final Map.Entry e = (Map.Entry)o;
	    if (first)
		b.append("?"); else
		b.append("&");
	    b.append(URLEncoder.encode(e.getKey().toString(), Query.CHARSET) + "=" + URLEncoder.encode(e.getValue().toString(), Query.CHARSET));
	    first = false;
	}
	final URL url;
	if (useBaseUrl)
	url = new URL(getBaseUrl(), new String(b)); else
	    	url = new URL(new String(b));
	final HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
	httpCon.connect();
	if (httpCon.getResponseCode() == 200)
	    return httpCon.getInputStream();
	throw buildException(url, httpCon);
    }

    public InputStream doUpload(String resource, File file) throws IOException
    {
	final URL url = new URL(resource);
	String charset = "UTF-8";
	final String boundary = Long.toHexString(System.currentTimeMillis());
	final String CRLF = "\r\n";
	final HttpURLConnection con = (HttpURLConnection)url.openConnection();
	con.setDoOutput(true);
	con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
	try (
	     final OutputStream output = con.getOutputStream();
	     final PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
	     ) {
	    writer.append("--" + boundary).append(CRLF);
	    writer.append("Content-Disposition: form-data; name=\"data\"; filename=\"" + file.getName() + "\"").append(CRLF);
	    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(CRLF);
	    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
	    writer.append(CRLF).flush();
	    java.nio.file.Files.copy(file.toPath(), output);
	    output.flush();
	    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
	    writer.append("--" + boundary + "--").append(CRLF).flush();
	}
	if (con.getResponseCode() == 200)
	    return con.getInputStream();
	throw buildException(url, con);
    }

    public InputStream doPost(String resource, Map<String, String> args) throws IOException
    {
	final URL url = new URL(getBaseUrl(), resource);
	final HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
	httpCon.setDoOutput(true);
	httpCon.setInstanceFollowRedirects( false );
	httpCon.setRequestMethod("POST");
	final byte[] postData       = encodeArgs(args, "").getBytes( java.nio.charset.StandardCharsets.UTF_8 );
	final int    postDataLength = postData.length;
	httpCon.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
	httpCon.setRequestProperty( "charset", "utf-8");
	httpCon.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
	httpCon.setUseCaches( false );
	try( DataOutputStream w = new DataOutputStream( httpCon.getOutputStream())) {
	    w.write( postData );
	    w.flush();
	}
	if (httpCon.getResponseCode() == 200)
	    return httpCon.getInputStream();
	throw buildException(url, httpCon);
    }

    void doPut(String resource) throws IOException
    {
	final URL url = new URL(getBaseUrl(), resource);
	final HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
	httpCon.setDoOutput(true);
	httpCon.setRequestMethod("PUT");
	final OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
	out.write("FIXME");
	out.close();
	httpCon.getInputStream();
    }

    private String encodeArgs(Map<String, String> args, String prefix)
    {
	if (args.isEmpty())
	    return "";
	try {
	    final StringBuilder b = new StringBuilder();
	    boolean first = true;
	    for(Map.Entry<String, String> e: args.entrySet())
	    {
		b.append(first?prefix:"&");
		first = false;
		b.append(URLEncoder.encode(e.getKey(), Query.CHARSET)).append("=").append(URLEncoder.encode(e.getValue(), Query.CHARSET));
	    }
	    return new String(b);
	}
	catch(UnsupportedEncodingException e)
	{
	    throw new RuntimeException(e);
	}
	}

    private URL getBaseUrl() throws IOException
    {
	return new URL(baseUrl);
    }

private BooksException buildException(URL url, HttpURLConnection con) throws IOException
    {
	final int code = con.getResponseCode();
	if (code != 400 && code != 500)
	    return new BooksException("HTTP code: " + String.valueOf(code) + ", URL: " + url.toString());
	final Gson gson = new Gson();
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.getErrorStream())))
	{
	    final ErrorResponse resp = (ErrorResponse)gson.fromJson(r, ErrorResponse.class);
	    if (resp == null)
			    return new BooksException("HTTP code: " + String.valueOf(code) + ", URL: " + url.toString());
	    return new BooksException(code, resp);
	}
	    }
}
