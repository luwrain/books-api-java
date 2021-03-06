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

    public BookQuery book()
    {
	return new BookQuery(con);
    }

    public Download download(Book book)
    {
	return new Download(con, book);
    }

    public org.luwrain.io.api.books.v1.users.Queries users()
    {
	return new org.luwrain.io.api.books.v1.users.Queries(con);
    }

        public org.luwrain.io.api.books.v1.notes.Queries notes()
    {
	return new org.luwrain.io.api.books.v1.notes.Queries(con);
    }

            public org.luwrain.io.api.books.v1.source.Queries source()
    {
	return new org.luwrain.io.api.books.v1.source.Queries(con);
    }

        public org.luwrain.io.api.books.v1.tasks.Queries tasks()
    {
	return new org.luwrain.io.api.books.v1.tasks.Queries(con);
    }

            public org.luwrain.io.api.books.v1.upload.Queries upload()
    {
	return new org.luwrain.io.api.books.v1.upload.Queries(con);
    }


        public org.luwrain.io.api.books.v1.repo.Queries repo()
    {
	return new org.luwrain.io.api.books.v1.repo.Queries(con);
    }

            public org.luwrain.io.api.books.v1.collection.Queries collection()
    {
	return new org.luwrain.io.api.books.v1.collection.Queries(con);
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
