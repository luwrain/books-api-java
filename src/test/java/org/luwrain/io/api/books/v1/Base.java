
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

        @Test public void accessTokenInvalidCredentials()
    {
	try {
newBooks().users().accessToken().exec();
assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(r.getType(), "INVALID_CREDENTIALS");
	}
	catch(IOException e)
	{
	    assertTrue(false);
	}
		try {
		    		    newBooks().users().accessToken().mail(getMail()).exec();
assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(r.getType(), "INVALID_CREDENTIALS");
	}
	catch(IOException e)
	{
	    assertTrue(false);
	}
				try {
		    		    newBooks().users().accessToken().mail("none@luwrain.org").passwd(getPasswd()).exec();
assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(r.getType(), "INVALID_CREDENTIALS");
	}
	catch(IOException e)
	{
	    assertTrue(false);
	}



		
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
