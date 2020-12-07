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

package org.luwrain.io.api.books.v1.source;

import java.util.*;
import java.io.*;
import com.google.gson.annotations.*;

import org.luwrain.io.api.books.v1.*;

public final class InfoQuery extends Query
{
    static public final String
	READY = "READY",
	ERROR = "ERROR",
	NO_SOURCE_ID = "NO_SOURCE_ID",
	INVALID_SOURCE_ID = "INVALID_SOURCE_ID";

    InfoQuery(Connection con)
    {
	super(con);
    }

        public InfoQuery accessToken(String atoken)
    {
	return (InfoQuery)addArg("atoken", atoken);
    }

            public InfoQuery sourceId(String source)
    {
	return (InfoQuery)addArg("source", source);
    }

                public InfoQuery waiting()
    {
	return (InfoQuery)addArg("wait", "1");
    }

    public Response exec() throws IOException
    {
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doGet("source/", urlArgs)))){
	    return gson.fromJson(r, Response.class);
	}
    }

    public final class Response extends CommonResponse
    {
	@SerializedName("id")
	private String id = null;
	@SerializedName("name")
	private String name = null;
	@SerializedName("status")
	private String status = null;
	@SerializedName("format")
	private String format = null;
	@SerializedName("size")
	private Long size = null;
	@SerializedName("files")
	private List<File> files = null;
	public String getId()
	{
	    return this.id;
	}
	public String getName()
	{
	    return this.name;
	}
	public String getStatus()
	{
	    return this.status;
	}
	public long getSize()
	{
	    return this.size != null?this.size.longValue():0;
	}
	public String getFormat()
	{
	    return this.format;
	}
	public File[] getFiles()
	{
	    return this.files != null?this.files.toArray(new File[this.files.size()]):new File[0];
	}
		    }

    static public final class File
    {
	@SerializedName("id")
	private Integer id = null;
	@SerializedName("name")
	private String name = null;
	@SerializedName("size")
	private Long size = null;
	@SerializedName("format")
	private String format = null;
	public int getId()
	{
	    return this.id != null?this.id.intValue():0;
	}
	public String getName()
	{
	    return this.name;
	}
	public String getFormat()
	{
	    return this.format;
	}
	public long getSize()
	{
	    return this.size != null?this.size.longValue():0;
	}
    }
}
