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
	NAME = "Евгений Онегин";

@Test public void main() throws IOException
    {
	if (!isReady())
	    return;
	final CreateQuery.Response r1 = newBooks().repo().create().accessToken(getAccessToken()).name(NAME).exec();
	assertNotNull(r1);
	assertTrue(r1.isOk());
	final String bookId = r1.getNewBookId();
	assertNotNull(bookId);
	assertEquals(ID_LEN, bookId.length());
	//	title(bookId);
	tagNoBookId();
	tagInvalidBookId();
	tagNoTag(bookId);
	//FIXME:tagInvalidTag
	tagNoValue(bookId);
	//FIXME:tagTooLongValue
	//		collectionAdd(bookId);
	//	collectionIncluded(bookId);

	final RemoveQuery.Response rr = newBooks().repo().remove().accessToken(getAccessToken()).bookId(bookId).exec();
	assertNotNull(rr);
	assertTrue(rr.isOk());
    }

    private void title(String bookId) throws IOException
    {
	final TagQuery.Response r = newBooks().repo().tag().accessToken(getAccessToken()).tag(TagQuery.TAG_TITLE).value(NAME).bookId(bookId).exec();
	assertNotNull(r);
	assertTrue(r.isOk());
    }

    private void tagNoBookId() throws IOException
    {
	try {
	    newBooks().repo().tag().accessToken(getAccessToken()).tag(TagQuery.TAG_TITLE).value(NAME).exec();
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
	    newBooks().repo().tag().accessToken(getAccessToken()).bookId("zzz").tag(TagQuery.TAG_TITLE).value(NAME).exec();
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
	    newBooks().repo().tag().accessToken(getAccessToken()).bookId(bookId).value(NAME).exec();
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

    private void collectionIncluded(String bookId) throws IOException
    {
	final CollectionQuery.Response r = newBooks().collection().collection().accessToken(getAccessToken()).exec();
	assertNotNull(r);
	assertTrue(r.isOk());
	final Book[] books = r.getBooks();
	assertNotNull(books);
	assertTrue(books.length > 0);
	Book book = null;
	System.out.println("need " + bookId);
	for(Book b: books)
	{
	    assertNotNull(b.getId());
	    System.out.println("booked " + b.getId());
	    if (b.getId().equals(bookId))
		book = b;
	}
	assertNotNull(book);
	assertNotNull(book.getName());
	assertEquals(NAME, book.getName());
    }

        private void collectionAdd(String bookId) throws IOException
    {
	final AddQuery.Response r = newBooks().collection().add().accessToken(getAccessToken()).bookId(bookId).exec();
	assertNotNull(r);
	assertTrue(r.isOk());
    }


    @Test public void repo() throws IOException
    {
	final ListQuery.Response r = newBooks().repo().list().accessToken(getAccessToken()).exec();
	assertNotNull(r);
	assertTrue(r.isOk());
	final Book[] books = r.getBooks();
	assertNotNull(books);
    }
}
