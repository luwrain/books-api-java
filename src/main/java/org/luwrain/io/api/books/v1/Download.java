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

import java.util.*;
import java.io.*;

public final class Download
{
public interface Listener
{
    void processed(int chunkBytes, long totalBytes);
    boolean interrupting();
}

    private final Connection con;
    private final Book book;

    public Download(Connection con, Book book)
    {
	if (con == null)
	    throw new NullPointerException("con can't be null");
	if (book == null)
	    throw new NullPointerException("book can't be null");
	this.con = con;
	this.book = book;
    }

    public void downloadDaisy(OutputStream os, Listener listener, String accessToken) throws IOException
    {
	if (os == null)
	    throw new NullPointerException("os canb't be null");
	if (listener == null)
	    throw new NullPointerException("listener can't be null");
	if (book.getFiles() == null || book.getFiles().getDaisyZip() == null || book.getFiles().getDaisyZip().isEmpty())
	    throw new IllegalStateException("The book doesn't have the DAISY ZIP downloading URL");
	final Map<String, String> args = new HashMap();
	if (accessToken != null && !accessToken.trim().isEmpty())
	    args.put("atoken", accessToken);
	try (final InputStream is = con.doGet(book.getFiles().getDaisyZip(), args, false)){
	    copyAllBytes(is, os, listener);
	}
    }

    static private long copyAllBytes(InputStream is, OutputStream os, Listener listener) throws IOException
    {
	long totalBytes = 0;
	final byte[] buf = new byte[2048];
	while(true)
	{
	    if (listener.interrupting())
		return totalBytes;
	    final int length = is.read(buf);
	    if (length < 0)
		return totalBytes;
	    writeAllBytes(os, buf, length);
	    totalBytes += length;
		listener.processed(length, totalBytes);
	}
    }

        static private void writeAllBytes(OutputStream os, byte[] bytes, int numBytes) throws IOException
    {
	if (numBytes < 0)
	    throw new IllegalArgumentException("numBytes (" + String.valueOf(numBytes) + ") can't be negative");
	if (numBytes == 0)
	    return;
	int pos = 0;
	do {
	    final int remaining = numBytes - pos;
	    final int numToWrite = remaining > 2048?2048:remaining;
	    os.write(bytes, pos, numToWrite);
	    pos += numToWrite;
	} while(pos < numBytes);
    }

}


    
