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

import java.util.*;

import com.google.gson.*;
import com.google.gson.annotations.*;

public class Query
{
    static public final String
	INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR",
	INVALID_QUERY = "INVALID_QUERY";

    protected final Connection con;
    protected final Gson gson = new Gson();
    protected final Map<String, String> urlArgs = new HashMap();

    public Query(Connection con)
    {
	if (con == null)
	    throw new NullPointerException("con can't be null");
	this.con = con;
    }

    protected Query addArg(String argName, String argValue)
    {
	if (argValue == null)
	    throw new NullPointerException(argName + " can't be null");
	if (argValue.isEmpty())
	    throw new IllegalArgumentException(argName + " can't be empty");
	urlArgs.put(argName, argValue);
	return this;
    }

    static public class CommonResponse
    {
	@SerializedName("type")
	private final String type = null;
	public String getType()
	{
	    return this.type;
	}
	public boolean isOk()
	{
	    return this.type != null && this.type.equals("OK");
	}
    }
}
