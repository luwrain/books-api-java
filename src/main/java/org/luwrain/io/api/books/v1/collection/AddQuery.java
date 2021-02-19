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

package org.luwrain.io.api.books.v1.collection;

import java.util.*;
import java.io.*;

import com.google.gson.annotations.*;

import org.luwrain.io.api.books.v1.*;

public final class AddQuery extends Query
{
static public final String
    NO_BOOK_ID = "NO_BOOK_ID",
    INVALID_BOOK_ID = "INVALID_BOOK_ID";
    
    AddQuery(Connection con)
    {
	super(con);
    }

    public AddQuery accessToken(String atoken)
    {
	return (AddQuery)addArg("atoken", atoken);
    }

        public AddQuery bookId(String book)
    {
	return (AddQuery)addArg("book", book);
    }

            public AddQuery user(String user)
    {
	return (AddQuery)addArg("user", user);
    }

    public Response exec() throws IOException
    {
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doGet("collection/add/", urlArgs)))){
	    	    final Response res = gson.fromJson(r, Response.class);
	    return res;
	}
    }

    static public final class Response extends CommonResponse
    {
    }
}
