
package org.luwrain.io.api.books.v1;

import java.io.*;
import org.junit.*;

public final class UsersTest extends Base
{
    @Test public void collection() throws IOException
    {
	if (!isReady())
	    return;
	getAccessToken();
	//	final Book[] books = newBooks().users().collection().exec();
    }

        @Test public void register() throws IOException
    {
	if (!isReady())
	    return;
	assertTrue(newBooks().users().register().mail("test@test.org").passwd("0123456789").exec().isOk());
    }
}
