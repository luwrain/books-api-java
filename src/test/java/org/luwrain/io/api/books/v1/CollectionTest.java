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

import org.luwrain.io.api.books.v1.collection.*;

public final class CollectionTest extends Base
{
@Test public void addNoValidAccessToken() throws IOException
    {
	try {
newBooks().collection().add().exec();
assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(AddQuery.NO_VALID_ACCESS_TOKEN, r.getType());
	}
    }

    @Test public void addNoBookId() throws IOException
    {
	try {
	    newBooks().collection().add().accessToken(getAccessToken()).exec();
assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(AddQuery.NO_BOOK_ID, r.getType());
	}
    }

        @Test public void addInvalidBookId() throws IOException
    {
	try {
	    newBooks().collection().add().accessToken(getAccessToken()).bookId("zzz").exec();
assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(AddQuery.INVALID_BOOK_ID, r.getType());
	}
    }

    

    
    }
