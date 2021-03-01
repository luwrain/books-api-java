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

package org.luwrain.io.api.books.v1.notes;

import java.util.*;
import java.io.*;
import com.google.gson.annotations.*;

import org.luwrain.io.api.books.v1.*;

public final class OrderQuery extends Query
{
    private String[] ids = null;

        OrderQuery(Connection con)
    {
	super(con);
    }

    public OrderQuery accessToken(String atoken)
    {
	return (OrderQuery)addArg("atoken", atoken);
    }

        public OrderQuery ids(String[] ids)
    {
	if (ids == null)
	    throw new NullPointerException("ids can't be null");
	if (ids.length == 0)
	    throw new IllegalArgumentException("ids can't be empty");
	for(String s: ids)
	{
	    if (s == null)
		throw new NullPointerException("ids can't contain null items");
	    if (s.isEmpty())
		throw new IllegalArgumentException("ids can't contain empty items");
	}
	this.ids = ids.clone();
	return this;
    }

    public Response exec() throws IOException
    {
	if (this.ids == null)
	    throw new IllegalStateException("No ids");
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doPost("notes/order/", gson.toJson(this.ids)), CHARSET))){
	    final Response res = gson.fromJson(r, Response.class);
	    return res;
	    	}
    }

    public final class Response extends CommonResponse
    {
    }
}
