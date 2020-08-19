
package org.luwrain.io.api.books.v1;

import java.io.*;
import org.junit.*;

public final class UsersTest extends Base
{
    @Test public void collection() throws IOException
    {
	final Book[] books = newBooks().users().collection().exec();

    }
}
