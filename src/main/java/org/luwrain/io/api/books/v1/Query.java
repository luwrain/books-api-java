/*
   Copyright 2020 Michael Pozhidaev <msp@luwrain.org>

   This file is part of LUWRAIN.

   LUWRAIN is free software; you can redistribute it and/or
   modify it under the terms of the GNU General Public
   License as published by the Free Software Foundation; either
   version 3 of the License, or (at your option) any later version.

   LUWRAIN is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   General Public License for more details.
*/

package org.luwrain.io.api.books.v1;

import java.util.*;

import com.google.gson.*;
import com.google.gson.annotations.*;

public class Query
{
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
