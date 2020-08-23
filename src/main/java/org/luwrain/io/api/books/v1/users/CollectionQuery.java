/*
 * Copyright 2020 Michael Pozhidaev <msp@luwrain.org>
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

    public CollectionQuery atoken(String atoken)
    {
	return (CollectionQuery)addArg("atoken", atoken);
    }

    public Book[] exec() throws IOException
    {
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doGet("user/collection/", urlArgs)))){
	    final List<Book> res = gson.fromJson(r, BOOK_LIST_TYPE);
	    if (res == null)
		return new Book[0];
	    return res.toArray(new Book[res.size()]);
	}
    }
}
