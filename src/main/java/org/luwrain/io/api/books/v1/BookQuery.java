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

import java.util.*;
import java.io.*;

import com.google.gson.annotations.*;

public final class BookQuery extends Query
{
    private String id = "";

    BookQuery(Connection con)
    {
	super(con);
    }

        public BookQuery id(String id)
    {
	if (id == null)
	    throw new NullPointerException("id can't be null");
	if (id.isEmpty())
	    throw new IllegalStateException("id can't be empty");
	this.id = id;
	return this;
	    }

    public BookQuery accessToken(String atoken)
    {
	return (BookQuery)addArg("atoken", atoken);
    }

    public Response exec() throws IOException
    {
	if (id.trim().isEmpty())
	    throw new IllegalStateException("No id provided");
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doGet("books/" + id + "/", urlArgs)))){
	    	    final Response res = gson.fromJson(r, Response.class);
	    return res;
	}
    }

    static public final class Response extends Book
    {
		@SerializedName("type")
	private final String type = null;
	public String getType()
	{
	    return this.type;
	}
	public boolean isOk()
	{
	    return this.type != null && this.type.equals("OK");
	}
    }
}
