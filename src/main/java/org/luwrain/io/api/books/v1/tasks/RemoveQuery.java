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

package org.luwrain.io.api.books.v1.tasks;

import java.util.*;
import java.io.*;
import com.google.gson.annotations.*;

import org.luwrain.io.api.books.v1.*;

public final class RemoveQuery extends Query
{
    static public final String
	INVALID_TASK_ID = "INVALID_TASK_ID",
	NO_TASK_ID = "NO_TASK_ID";

    RemoveQuery(Connection con)
    {
	super(con);
    }

        public RemoveQuery accessToken(String atoken)
    {
	return (RemoveQuery)addArg("atoken", atoken);
    }

            public RemoveQuery taskId(String task)
    {
	return (RemoveQuery)addArg("task", task);
    }

    public Response exec() throws IOException
    {
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doGet("tasks/remove/", urlArgs)))){
	    return gson.fromJson(r, Response.class);
	}
    }

    public final class Response extends CommonResponse
    {
	    }
}
