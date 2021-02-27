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

package org.luwrain.io.api.books.v1;

import com.google.gson.annotations.*;

public class Note
{
    static public final String
	BOOKMARK = "BOOKMARK",
	NOTE = "NOTE";

    @SerializedName("id")
    private String id = null;

    @SerializedName("bookId")
    private String bookId = null;

    @SerializedName("type")
    private String type = null;

    @SerializedName("doc")
    private String doc = null;

    @SerializedName("pos")
    private String pos = null;

    public String getId()
    {
	return this.id;
    }

    public void setId(String id)
    {
	this.id = id;
    }

    public String getBookId()
    {
	return this.bookId;
    }

    public void setBookId(String bookId)
    {
	this.bookId = bookId;
    }

    public String getType()
    {
	return type;
    }

    public void setType(String type)
    {
	this.type = type;
    }

    public String getDoc()
    {
	return doc;
    }

    public void setDoc(String doc)
    {
	this.doc = doc;
    }

    public String getPos()
    {
	return pos;
    }

    public void setPos(String pos)
    {
	this.pos = pos;
    }
}
