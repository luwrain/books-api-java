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

package org.luwrain.io.api.books.v1;

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
public class BooksException extends java.io.IOException
{
    private final int httpCode;
    private final ErrorResponse errorResponse;

    /**
     * The constructor accepting the exception which caused the error. The same message
     * as in the exception being wrapped is used.
     *
     * @param e The exception caused the error
     */
    public BooksException(Exception e)
    {
	super(e.getMessage(), e);
	this.httpCode = -1;
	this.errorResponse = null;
    }

    public BooksException(String message)
    {
	super(message);
		this.httpCode = -1;
	this.errorResponse = null;
    }

    public BooksException(int httpCode, ErrorResponse errorResponse)
    {
	super("HTTP code: " + String.valueOf(httpCode) + ", type: " + ((errorResponse != null && errorResponse.getType() != null)?errorResponse.getType():null));
	this .httpCode = httpCode;
	this.errorResponse = errorResponse;
    }

    public int getHttpCode()
    {
	return this.httpCode;
    }

    public ErrorResponse getErrorResponse()
    {
	return this.errorResponse;
    }
}
