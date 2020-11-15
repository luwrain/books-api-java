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

import org.luwrain.io.api.books.v1.tasks.*;

public final class TasksTest extends Base
{
    @Test public void cycle() throws IOException
    {
	if (!isReady())
	    return;
	final CreateQuery.Response r = newBooks().tasks().create().accessToken(getAccessToken()).exec();
	assertNotNull(r);
	assertTrue(r.isOk());
	final String taskId = r.getNewTaskId();
	assertNotNull(taskId);
	assertFalse(taskId.isEmpty());

	final RemoveQuery.Response rr = newBooks().tasks().remove().accessToken(getAccessToken()).taskId(taskId).exec();
	assertNotNull(rr);
	assertTrue(rr.isOk());
    }

@Test public void removeNoTaskId() throws IOException
    {
	if (!isReady())
	    return;
	try {
	    newBooks().tasks().remove().accessToken(getAccessToken()).exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(RemoveQuery.NO_TASK_ID, r.getType());
	}
    }

    @Test public void removeInvalidTaskId() throws IOException
    {
	if (!isReady())
	    return;
	try {
	    newBooks().tasks().remove().accessToken(getAccessToken()).taskId("123").exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(RemoveQuery.INVALID_TASK_ID, r.getType());
	}
    }


}
