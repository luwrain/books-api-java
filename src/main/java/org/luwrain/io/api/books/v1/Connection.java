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

    InputStream doGet(String resource, Map args) throws IOException
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
	    throw new IOException(makeExceptionMessage(httpCon));
	default:
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
