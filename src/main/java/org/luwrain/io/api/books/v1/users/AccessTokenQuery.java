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

package org.luwrain.io.api.books.v1.users;

import java.util.*;
import java.io.*;

import com.google.gson.annotations.*;

import org.luwrain.io.api.books.v1.*;

public final class AccessTokenQuery extends Query
{
    AccessTokenQuery(Connection con)
    {
	super(con);
    }

    public AccessTokenQuery mail(String mail)
    {
	return (AccessTokenQuery)addArg("mail", mail);
    }

        public AccessTokenQuery passwd(String passwd)
    {
	return (AccessTokenQuery)addArg("passwd", passwd);
    }

    public Response exec() throws IOException
    {
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doPost("user/atoken/", urlArgs)))){
	    return gson.fromJson(r, Response.class);
	}
    }

    public final class Response extends CommonResponse
    {
	@SerializedName("atoken")
	private String accessToken = null;
	public String getAccessToken()
	{
	    return this.accessToken;
	}
    }
}
