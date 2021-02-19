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

package org.luwrain.io.api.books.v1.repo;

import java.util.*;
import java.io.*;
import com.google.gson.annotations.*;

import org.luwrain.io.api.books.v1.*;

public final class ListQuery extends Query
{
        ListQuery(Connection con)
    {
	super(con);
    }

    public ListQuery accessToken(String atoken)
    {
	return (ListQuery)addArg("atoken", atoken);
    }

    public Response exec() throws IOException
    {
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doGet("repo/list/", urlArgs)))){
	    final Response res = gson.fromJson(r, Response.class);
	    return res;
	    	}
    }

    public final class Response extends CommonResponse
    {
	@SerializedName("books")
	private List<Book> books = null;
	public Book[] getBooks()
	{
	    if (this.books == null)
		return null;
	    return this.books.toArray(new Book[this.books.size()]);
	}
    }
}
