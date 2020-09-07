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

public final class RepoTest extends Base
{
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
	final UploadQuery.Response r2 = newBooks().repo().upload().accessToken(getAccessToken()).format(UploadQuery.FORMAT_MP3_ZIP).bookId(bookId).exec();
	assertNotNull(r2);
	assertTrue(r2.isOk());
	final String uploadId = r2.getUploadId();
	assertNotNull(uploadId);
	assertFalse(uploadId.isEmpty());
    }
}
