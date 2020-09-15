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

import java.io.*;
import org.junit.*;

import org.luwrain.io.api.books.v1.repo.*;
import org.luwrain.io.api.books.v1.collection.*;

public final class RepoTest extends Base
{
    static public final String
	TITLE = "TITLE";

    @Test public void cycle() throws IOException
    {
	if (!isReady())
	    return;
	final CreateQuery.Response r1 = newBooks().repo().create().accessToken(getAccessToken()).exec();
	assertNotNull(r1);
	assertTrue(r1.isOk());
	final String bookId = r1.getNewBookId();
	assertNotNull(bookId);
	assertFalse(bookId.isEmpty());
	upload(bookId);
	title(bookId);
	tagNoBookId();
	tagInvalidBookId();
	tagNoTag(bookId);
	//FIXME:tagInvalidTag
	tagNoValue(bookId);
	//FIXME:tagTooLongValue
	collection();
    }

    private void upload(String bookId) throws IOException
    {
	final UploadQuery.Response r = newBooks().repo().upload().accessToken(getAccessToken()).format(UploadQuery.FORMAT_MP3_ZIP).bookId(bookId).exec();
	assertNotNull(r);
	assertTrue(r.isOk());
	final String uploadId = r.getUploadId();
	assertNotNull(uploadId);
	assertFalse(uploadId.isEmpty());
    }

    private void title(String bookId) throws IOException
    {
	final TagQuery.Response r = newBooks().repo().tag().accessToken(getAccessToken()).tag(TagQuery.TAG_TITLE).value(TITLE).bookId(bookId).exec();
	assertNotNull(r);
	assertTrue(r.isOk());
    }

    private void tagNoBookId() throws IOException
    {
	try {
	    newBooks().repo().tag().accessToken(getAccessToken()).tag(TagQuery.TAG_TITLE).value(TITLE).exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(TagQuery.NO_BOOK_ID, r.getType());
	}
    }

    private void tagInvalidBookId() throws IOException
    {
	try {
	    newBooks().repo().tag().accessToken(getAccessToken()).bookId("zzz").tag(TagQuery.TAG_TITLE).value(TITLE).exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(TagQuery.INVALID_BOOK_ID, r.getType());
	}
    }

    private void tagNoTag(String bookId) throws IOException
    {
	try {
	    newBooks().repo().tag().accessToken(getAccessToken()).bookId(bookId).value(TITLE).exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(TagQuery.NO_TAG, r.getType());
	}
    }

    private void tagNoValue(String bookId) throws IOException
    {
	try {
	    newBooks().repo().tag().accessToken(getAccessToken()).bookId(bookId).tag(TagQuery.TAG_TITLE).exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(TagQuery.NO_VALUE, r.getType());
	}
    }

    private void collection() throws IOException
    {
	final CollectionQuery.Response r = newBooks().collection().collection().accessToken(getAccessToken()).exec();
	assertNotNull(r);
	assertTrue(r.isOk());
	final Book[] books = r.getBooks();
	assertNotNull(books);
    }

    @Test public void repo() throws IOException
    {
	final RepoQuery.Response r = newBooks().repo().repo().accessToken(getAccessToken()).exec();
	assertNotNull(r);
	assertTrue(r.isOk());
	final Book[] books = r.getBooks();
	assertNotNull(books);
    }
}
