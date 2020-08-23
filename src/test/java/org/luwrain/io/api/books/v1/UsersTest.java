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

public final class UsersTest extends Base
{
    @Test public void collection() throws IOException
    {
	if (!isReady())
	    return;
	getAccessToken();
	//	final Book[] books = newBooks().users().collection().exec();
    }

        @Test public void register() throws IOException
    {
	if (!isReady())
	    return;
	assertTrue(newBooks().users().register().mail("test@test.org").passwd("0123456789").exec().isOk());
    }
}
