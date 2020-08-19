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
import java.lang.reflect.*;

import com.google.gson.reflect.*;

import org.luwrain.io.api.books.v1.*;

public final class CollectionQuery extends Query
{
    static final Type BOOK_LIST_TYPE = new TypeToken<List<Book>>(){}.getType();

    CollectionQuery(Connection con)
    {
	super(con);
    }

    public CollectionQuery accessToken(String token)
    {
	if (token == null)
	    throw new NullPointerException("token can't be null");
	if (token.isEmpty())
	    throw new IllegalArgumentException("token can't be empty");
	urlArgs.put("atoken", "token");
	return this;
    }

    public Book[] exec() throws IOException
    {
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doGet("/user/collection/", urlArgs)))){
	    final List<Book> res = gson.fromJson(r, BOOK_LIST_TYPE);
	    if (res == null)
		return new Book[0];
	    return res.toArray(new Book[res.size()]);
	}
    }
}
