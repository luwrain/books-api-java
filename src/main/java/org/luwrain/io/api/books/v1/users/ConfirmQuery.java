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

package org.luwrain.io.api.books.v1.users;

import java.util.*;
import java.io.*;

import org.luwrain.io.api.books.v1.*;

public final class ConfirmQuery extends Query
{
    static public final String 
    	noMail = "noMail",
	noConfirmationCode = "noConfirmationCode",
	invalidMail = "invalidMail",
	invalidCode = "invalidCode",
	tooManyAttempts = "tooManyAttempts";

    
    ConfirmQuery(Connection con)
    {
	super(con);
    }

    public ConfirmQuery mail(String mail)
    {
	return (ConfirmQuery)addArg("mail", mail);
    }

        public ConfirmQuery confirmationCode(String code)
    {
	return (ConfirmQuery)addArg("code", code);
    }

    public Response exec() throws IOException
    {
	try (final BufferedReader r = new BufferedReader(new InputStreamReader(con.doGet("user/confirm/", urlArgs)))){
	    return gson.fromJson(r, Response.class);
	}
    }

    public final class Response extends CommonResponse
    {
    }
}
