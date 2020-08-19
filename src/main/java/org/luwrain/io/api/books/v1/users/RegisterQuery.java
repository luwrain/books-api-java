/*
   Copyright 2020 Michael Pozhidaev <msp@luwrain.org>

   This file is part of LUWRAIN.

   LUWRAIN is free software; you can redistribute it and/or
   modify it under the terms of the GNU General Public
   License as published by the Free Software Foundation; either
   version 3 of the License, or (at your option) any later version.

   LUWRAIN is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   General Public License for more details.
*/

package org.luwrain.io.api.books.v1.users;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;

import com.google.gson.reflect.*;

import org.luwrain.io.api.books.v1.*;

public final class RegisterQuery extends Query
{
    RegisterQuery(Connection con)
    {
	super(con);
    }

    public RegisterQuery mail(String mail)
    {
	return (RegisterQuery)addArg("mail", mail);
    }

        public RegisterQuery passwd(String passwd)
    {
	return (RegisterQuery)addArg("passwd", passwd);
    }

    public void exec() throws IOException
    {
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doPost("/user/register/", urlArgs)))){
	    String line = r.readLine();
	    while (line != null)
	    {
		System.out.println(line);
		line = r.readLine();
	    }
	}
    }
}
