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

public class Book
{
    static public final String
	AUDIO = "AUDIO",
	READY = "READY";

    @SerializedName("id")
    private String id = null;

    @SerializedName("bookType")
    private String bookType = null;

    @SerializedName("name")
    private String name = null;

    @SerializedName("status")
    private String status = null;

    @SerializedName("locked")
    private Boolean locked = null;

    @SerializedName("daisy")
    private Boolean daisy = null;

    @SerializedName("mp3")
    private Boolean mp3 = null;

    @SerializedName("tags")
    private Tags tags = null;

    @SerializedName("files")
    private Files files = null;

    public String getId()
    {
	return id;
    }

    public String getBookType()
    {
	return bookType;
    }

    public String getName()
    {
	return name;
    }

    public String getStatus()
    {
	return status;
    }

    public Boolean getLocked()
    {
	return locked;
    }

    public Boolean getDaisy()
    {
	return daisy;
    }

    public Boolean getMp3()
    {
	return mp3;
    }

    public Tags getTags()
    {
	return tags;
    }

    public Files getFiles()
    {
	return files;
    }

    @Override public String toString()
    {
	final StringBuilder b = new StringBuilder();
	if (tags != null && tags.title != null && !tags.title.trim().isEmpty())
	    b.append(tags.title.trim()); else
	    if (name != null && !name.trim().isEmpty())
		b.append(name.trim());
	if (tags != null && tags.authors != null && tags.authors.str != null && !tags.authors.str.trim().isEmpty())
	{
	    if (b.length() > 0)
		b.append(", ");
	    b.append(tags.authors.str.trim());
	}
	if (tags != null && tags.year != null && !tags.year.isEmpty())
	{
	    if (b.length() > 0)
		b.append(", ");
	    b.append(tags.year.trim());
	}
	return new String(b);
    }

    static public final class Authors
    {
	@SerializedName("str")
	private String str = null;
	public String getStr()
	{
	    return str;
	}
    }

    static public final class Tags
    {
	@SerializedName("title")
	private String title = null;
	@SerializedName("authors")
	private Authors authors = null;
	@SerializedName("year")
	private String year = null;
	@SerializedName("isbn")
	private String isbn = null;
	@SerializedName("publisher")
	private String publisher = null;
	@SerializedName("editor")
	private String editor = null;
	public String getTitle()
	{
	    return title;
	}
	public Authors getAuthors()
	{
	    return authors;
	}
	public String getYear()
	{
	    return year;
	}
	public String getIsbn()
	{
	    return isbn;
	}
	public String getPublisher()
	{
	    return publisher;
	}
	public String getEditor()
	{
	    return editor;
	}
    }

    static public final class Files
    {
	@SerializedName("daisyZip")
	private String daisyZip = null;
	@SerializedName("mp3Zip")
	private String mp3Zip = null;
	public String getDaisyZip()
	{
	    return daisyZip;
	}
	public String getMp3Zip()
	{
	    return mp3Zip;
	}
    }
}
