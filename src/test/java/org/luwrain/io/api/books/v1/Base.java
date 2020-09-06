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

import java.io.*;
import java.util.*;
import org.junit.*;

class Base extends Assert
{
    static private final File CONFIG_FILE = new File(new File(System.getProperty("user.home")), ".books-api-java.properties");

    private String baseUrl = null;
    private String mail = null;
    private String passwd = null;
    private String accessToken = null;

    protected Base()
    {
	try {
	    final Properties props = new Properties();
	    try (final BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(CONFIG_FILE)))) {
		props.load(r);
	    }
	    this.baseUrl = props.getProperty("url");
	    this.mail = props.getProperty("mail");
	    this.passwd = props.getProperty("passwd");
	}
	catch(IOException e)
	{
	    System.out.println("No config file " + CONFIG_FILE.getAbsolutePath() + " (" + e.getMessage() + ")");
	}
	if (this.baseUrl == null || this.baseUrl.isEmpty())
	    this.baseUrl = "http://books.luwrain.org";
    }

    Books newBooks()
    {
	return new Factory().newInstance(baseUrl);
    }

    protected String getAccessToken() throws IOException
    {
	if (this.accessToken != null)
	    return this.accessToken;
	final org.luwrain.io.api.books.v1.users.AccessTokenQuery.Response r = newBooks().users().accessToken().mail(getMail()).passwd(getPasswd()).exec();
	assertNotNull(r);
	assertTrue(r.isOk());
	this.accessToken = r.getAccessToken();
	assertNotNull(accessToken);
	assertFalse(accessToken.isEmpty());
	return this.accessToken;
    }

    protected String getMail()
    {
	return this.mail;
    }

    protected String getPasswd()
    {
	return this.passwd;
    }

    protected boolean isReady()
    {
	return this.mail != null && this.passwd != null;
    }
}
