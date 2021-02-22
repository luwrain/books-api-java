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

package org.luwrain.io.api.books.v1.upload;

import java.util.*;
import java.io.*;
import com.google.gson.annotations.*;

import org.luwrain.io.api.books.v1.*;

public final class SourceUploadQuery extends Query
{
    SourceUploadQuery(Connection con)
    {
	super(con);
    }

    public Response exec(String uploadUrl, File file) throws IOException
    {
	if (uploadUrl == null)
	    throw new NullPointerException("uploadUrl can't be null");
	if (uploadUrl.trim().isEmpty())
	    throw new IllegalArgumentException("uploadUrl can't be empty");
	if (file == null)
	    throw new NullPointerException("file can't be null");
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doUpload(uploadUrl, file), CHARSET))){
	    return gson.fromJson(r, Response.class);
	}
    }

    public final class Response extends CommonResponse
    {
	@SerializedName("source")
	private String source = null;
	public String getSourceId()
	{
	    return source;
	}
	    }
}
