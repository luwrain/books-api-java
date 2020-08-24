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
import org.luwrain.io.api.books.v1.users.*;

public final class UsersTest extends Base
{
        @Test public void register() throws IOException
    {
	if (!isReady())
	    return;
	assertTrue(newBooks().users().register().mail("test@test.org").passwd("0123456789").exec().isOk());
    }

        @Test public void confirmLimitExceeding() throws IOException
    {
	final String mail = "test-confirm-limit-exceeding@luwrain.org";
	if (!isReady())
	    return;
	final RegisterQuery.Response r1 = newBooks().users().register().mail(mail).passwd("123123123").exec();
	assertNotNull(r1);
	assertTrue(r1.isOk());
	for(int i = 0;i < 5;i++)
	{
	    try {
		final ConfirmQuery.Response r = newBooks().users().confirm().mail(mail).confirmationCode("10000").exec();
		assertTrue(false);
	    }
	    catch(BooksException e)
	    {
		final ErrorResponse r = e.getErrorResponse();
		assertNotNull(r);
		assertNotNull(r.getType());
		assertEquals(r.getType(), "INVALID_CONFIRMATION_CODE");
	    }
	}

		    try {
			final ConfirmQuery.Response r = newBooks().users().confirm().mail(mail).confirmationCode("10000").exec();
		assertTrue(false);
	    }
	    catch(BooksException e)
	    {
		final ErrorResponse r = e.getErrorResponse();
		assertNotNull(r);
		assertNotNull(r.getType());
		assertEquals(r.getType(), "TOO_MANY_ATTEMPTS");
	    }

		    
    }

}
