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

import org.luwrain.io.api.books.v1.source.*;
import org.luwrain.io.api.books.v1.upload.*;

public final class SourceTest extends Base
{
    @Test public void cycle() throws IOException
    {
	if (!isReady() && getUploadDocX() != null)
	    return;
	final UploadQuery.Response r1 = newBooks().source().upload().accessToken(getAccessToken()).exec();
	assertNotNull(r1);
	assertTrue(r1.isOk());
	final String uploadUrl = r1.getUploadUrl();
	assertNotNull(uploadUrl);
	assertFalse(uploadUrl.isEmpty());
	final SourceUploadQuery.Response r2 = newBooks().upload().source().exec(uploadUrl, new File(getUploadDocX()));
	assertNotNull(r2);
	assertTrue(r2.isOk());
	final String sourceId = r2.getSourceId();
	assertNotNull(sourceId);
	assertEquals(ID_LEN, sourceId.length());

	final RemoveQuery.Response rr = newBooks().source().remove().accessToken(getAccessToken()).sourceId(sourceId).waiting().exec();
	assertNotNull(rr);
	assertTrue(rr.isOk());
    }

@Test public void removeNoSourceId() throws IOException
    {
	if (!isReady())
	    return;
	try {
	    newBooks().source().remove().accessToken(getAccessToken()).exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(RemoveQuery.NO_SOURCE_ID, r.getType());
	}
    }

    @Test public void removeInvalidSourceId() throws IOException
    {
	if (!isReady())
	    return;
	try {
	    newBooks().source().remove().accessToken(getAccessToken()).sourceId("123").exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(RemoveQuery.INVALID_SOURCE_ID, r.getType());
	}
    }



    
}
