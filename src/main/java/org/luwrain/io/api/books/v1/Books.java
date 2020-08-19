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

import java.net.*;
import java.util.*;
import java.io.*;

/**
 * The central class providing access to theAPI. This class creates
 * requests objects which encode the necessary arguments, execute
 * a request and return a result. The instance of this class may not be
 * created directly, please use the {@link Factory} class instead.
 * <p>
 * It is supposed that the general lifecycle of a session looks as
 * follows: first of all, the {@code Books} instance must be created through
 * the {@link Factory} class. This instance provides necessary requests
 * objects, these objects take all arguments, perform requests and give a
 * result back through objects of the result classes. We encourage to
 * call the {@code close()} method to indicate that you don't have any intention to
 * make any further requests.
 *
 * @sa Factory
 */
public final class Books
{
    private final Connection con;
private final CookieManager cookieMan = new CookieManager();

    /** 
     * The main constructor, may not be invoked directly.
     *
     * @param An instance of the {@link Connection} class
     */
    Books(Connection con)
    {
	if (con == null)
	    throw new NullPointerException("con may not be null");
	this.con = con;
    }

    /**
     * Notifies that this instance is not needed any more.
     */
    public void close()
    {
	//Actually nothing here.
    }

        InputStream doGet(String resource, Map args) throws IOException
    {
	return con.doGet(resource, args);
    }
}
