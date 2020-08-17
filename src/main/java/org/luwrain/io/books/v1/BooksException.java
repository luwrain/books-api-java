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

package org.luwrain.io.books.v1;

/**
 * Indicates errors on requests to the server.
 * The instances of this class are thrown on encountering an error of any
 * kind. In most cases it wraps the exception caused the error.
 * <p>
 * It is assumed that, if the particular method of the {@link Books} class
 * returns, the request was successfully send and the server answered
 * with the code 200. If the client is unable to send the request or the
 * server is unable to provide requested data, the method throwes the
 * {@code BooksException} which helps to understand what went wrong.
 *
 * @see Books
 */
public class BooksException extends Exception
{
    /**
     * The constructor accepting the exception which caused the error. The same message
     * as in the exception being wrapped is used.
     *
     * @param e The exception caused the error
     */
    public BooksException(Exception e)
    {
	super(e.getMessage(), e);
    }
}
