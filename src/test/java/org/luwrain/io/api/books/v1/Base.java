
package org.luwrain.io.api.books.v1;

import java.io.*;
import java.util.*;
import org.junit.*;

class Base extends Assert
{
    static private final File CONFIG_FILE = new File(new File(System.getProperty("user.home")), ".books-api-java.properties");

    private String baseUrl;
    private String mail;
    private String passwd;

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
