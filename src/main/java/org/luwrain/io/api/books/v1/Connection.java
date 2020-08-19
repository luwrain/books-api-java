/*
   Copyright 2020 Michael Pozhidaev <msp@luwrain.org>

   This file is part of LUWRAIN.

   LUWRAIN is free software; you can redistribute it and/or
   modify it under the terms of the GNU General Public
   License as published by the Free Software Foundation; either
   version 3 of the License, or (at your option) any later version.

   LUWRAIN is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   General Public License for more details.
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
	    b.append(URLEncoder.encode(e.getKey().toString()) + "=" + URLEncoder.encode(e.getValue().toString()));
	    first = false;
	}
	final URL url = new URL(getBaseUrl(), new String(b));
	final HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
	httpCon.connect();
	switch(httpCon.getResponseCode())
	{
	case 200:
	    return httpCon.getInputStream();
	case 400:
	case 500:
	    httpCon.getInputStream().close();
	    throw new IOException(makeExceptionMessage(httpCon));
	default:
	    httpCon.getInputStream().close();
	    throw new IOException("Response code " + httpCon.getResponseCode());
	}
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
	switch(httpCon.getResponseCode())
	{
	case 200:
	    return httpCon.getInputStream();
	case 400:
	case 500:
	    httpCon.getInputStream().close();
	    throw new IOException(makeExceptionMessage(httpCon));
	default:
	    httpCon.getInputStream().close();
	    throw new IOException("Response code " + httpCon.getResponseCode());
	}
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
	final StringBuilder b = new StringBuilder();
	boolean first = true;
	for(Map.Entry<String, String> e: args.entrySet())
	{
	    b.append(first?prefix:"&");
	    first = false;
	    b.append(URLEncoder.encode(e.getKey())).append("=").append(e.getValue());
	}
	return new String(b);
    }

    private URL getBaseUrl() throws IOException
    {
	return new URL(baseUrl);
    }

    private String makeExceptionMessage(HttpURLConnection con) throws IOException
    {
	final InputStream is = con.getErrorStream();
	try {
	    final BufferedReader r = new BufferedReader(new InputStreamReader(is));
	    final Gson gson = new Gson();
	    final ErrorResult errorRes = gson.fromJson(r, ErrorResult.class);
	    return "Response code " + con.getResponseCode() + " " + errorRes.getMessage();
	}
	finally {
	    if (is != null)
		is.close();
	}
    }
}
