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

package org.luwrain.io.api.books.v1.users;

import java.util.*;
import java.io.*;

import org.luwrain.io.api.books.v1.*;

public final class RegisterQuery extends Query
{
    static public final String
	NO_MAIL = "NO_MAIL",
	NO_PASSWORD = "NO_PASSWORD",
	INVALID_MAIL = "INVALID_MAIL",
	INVALID_PASSWORD = "INVALID_PASSWORD",
	MAIL_ADDRESS_ALREADY_IN_USE = "MAIL_ADDRESS_ALREADY_IN_USE",
	TOO_SHORT_PASSWORD = "TOO_SHORT_PASSWORD";

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

    public Response exec() throws IOException
    {
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doPost("user/register/", urlArgs)))){
	    return gson.fromJson(r, Response.class);
	}
    }

    public final class Response extends CommonResponse
    {
    }
}
