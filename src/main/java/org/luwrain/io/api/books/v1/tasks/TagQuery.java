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

import java.util.*;
import java.io.*;
import com.google.gson.annotations.*;

import org.luwrain.io.api.books.v1.*;

public final class TagQuery extends Query
{
    static public final String
	AUTHORS = "AUTHORS",
	TITLE = "TITLE",
	PUBLISHER
    = "PUBLISHER"	,
	NO_TASK_ID = "NO_TASK_ID",
	INVALID_TASK_ID = "INVALID_TASK_ID",
	NO_TAG = "NO_TAG",
	INVALID_TAG = "INVALID_TAG",
	NO_VALUE = "NO_VALUE",
	MAX_LENGTH_LIMIT_EXCEEDED = "MAX_LENGTH_LIMIT_EXCEEDED";

    TagQuery(Connection con)
    {
	super(con);
    }

        public TagQuery accessToken(String atoken)
    {
	return (TagQuery)addArg("atoken", atoken);
    }

            public TagQuery taskId(String task)
    {
	return (TagQuery)addArg("task", task);
    }

                public TagQuery tag(String tag)
    {
	return (TagQuery)addArg("tag", tag);
    }

                    public TagQuery value(String value)
    {
	return (TagQuery)addArg("value", value);
    }

    public Response exec() throws IOException
    {
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doGet("tasks/tag/", urlArgs), CHARSET))){
	    return gson.fromJson(r, Response.class);
	}
    }

    public final class Response extends CommonResponse
    {
	    }
}
