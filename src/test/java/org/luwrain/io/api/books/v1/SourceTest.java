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
    @Test public void main() throws IOException
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

	final InfoQuery.Response r3 = newBooks().source().info().accessToken(getAccessToken()).sourceId(sourceId).waiting().exec();
	assertNotNull(r3);
	assertTrue(r3.isOk());
	assertNotNull(r3.getId());
	assertEquals(ID_LEN, r3.getId().length());
	assertNotNull(r3.getName());
	assertEquals(getUploadDocX().substring(getUploadDocX().lastIndexOf("/") + 1), r3.getName());
	assertNotNull(r3.getStatus());
	assertEquals(InfoQuery.READY, r3.getStatus());
	assertNotNull(r3.getFormat());
	assertEquals("DOCX", r3.getFormat());
	assertTrue(r3.getSize() > 0);

	final InfoQuery.File[] files = r3.getFiles();
	assertNotNull(files);
	assertEquals(1, files.length);
	final InfoQuery.File file = files[0];
	assertNotNull(file);
	assertEquals(0, file.getId());
	assertNotNull(file.getName());
	assertEquals(getUploadDocX().substring(getUploadDocX().lastIndexOf("/") + 1), file.getName());
	assertTrue(file.getSize() > 0);
	assertNotNull(file.getFormat());
	assertEquals("DOCX", file.getFormat());

	final RemoveQuery.Response rr = newBooks().source().remove().accessToken(getAccessToken()).sourceId(sourceId).waiting().exec();
	assertNotNull(rr);
	assertTrue(rr.isOk());
    }

        @Test public void errorMp3() throws IOException
    {
	if (!isReady() && getUploadMp3() != null)
	    return;
	final UploadQuery.Response r1 = newBooks().source().upload().accessToken(getAccessToken()).exec();
	assertNotNull(r1);
	assertTrue(r1.isOk());
	final String uploadUrl = r1.getUploadUrl();
	assertNotNull(uploadUrl);
	assertFalse(uploadUrl.isEmpty());
	final SourceUploadQuery.Response r2 = newBooks().upload().source().exec(uploadUrl, new File(getUploadMp3()));
	assertNotNull(r2);
	assertTrue(r2.isOk());
	final String sourceId = r2.getSourceId();
	assertNotNull(sourceId);
	assertEquals(ID_LEN, sourceId.length());

	final InfoQuery.Response r3 = newBooks().source().info().accessToken(getAccessToken()).sourceId(sourceId).waiting().exec();
	assertNotNull(r3);
	assertTrue(r3.isOk());
	assertNotNull(r3.getId());
	assertEquals(ID_LEN, r3.getId().length());
	assertNotNull(r3.getName());
	assertEquals(getUploadMp3().substring(getUploadMp3().lastIndexOf("/") + 1), r3.getName());
	assertNotNull(r3.getStatus());
	assertEquals(InfoQuery.ERROR, r3.getStatus());
	assertNotNull(r3.getFormat());
	assertEquals("", r3.getFormat());
	assertTrue(r3.getSize() > 0);

	final InfoQuery.File[] files = r3.getFiles();
	assertNotNull(files);
	assertEquals(0, files.length);

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

    @Test public void sourceNoSourceId() throws IOException
    {
	if (!isReady())
	    return;
	try {
	    newBooks().source().info().accessToken(getAccessToken()).exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(InfoQuery.NO_SOURCE_ID, r.getType());
	}
    }

    @Test public void sourceInvalidSourceId() throws IOException
    {
	if (!isReady())
	    return;
	try {
	    newBooks().source().info().accessToken(getAccessToken()).sourceId("123").exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(InfoQuery.INVALID_SOURCE_ID, r.getType());
	}
    }
}
