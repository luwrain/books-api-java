
package org.luwrain.io.api.books.v1;

import java.io.*;
import org.junit.*;

public final class UsersTest extends Base
{
    @Ignore @Test public void collection() throws IOException
    {
	if (!isReady())
	    return;
	final Book[] books = newBooks().users().collection().exec();
    }

        @Test public void register() throws IOException
    {
	if (!isReady())
	    return;
newBooks().users().register().mail("test@test.org").passwd("0123456789").exec();
    }
}
