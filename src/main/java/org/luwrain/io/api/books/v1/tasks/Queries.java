/*
 * Copyright 2020-2021 Michael Pozhidaev <msp@luwrain.org>
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

package org.luwrain.io.api.books.v1.tasks;

//import java.net.*;
//import java.util.*;
import java.io.*;

import org.luwrain.io.api.books.v1.*;

public final class Queries
{
    private final Connection con;

    public Queries(Connection con)
    {
	if (con == null)
	    throw new NullPointerException("con can't be null");
	this.con = con;
    }

    public CreateQuery create()
    {
	return new CreateQuery(con);
    }

    public RemoveQuery remove()
    {
	return new RemoveQuery(con);
    }

    public TagQuery tag()
    {
	return new TagQuery(con);
    }
}
