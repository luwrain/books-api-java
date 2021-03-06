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
import org.junit.*;
import org.luwrain.io.api.books.v1.users.*;

public final class UsersTest extends Base
{
    @Test public void register() throws IOException
    {
	if (!isReady())
	    return;
	assertTrue(newBooks().users().register().mail("test@test.org").passwd("0123456789").exec().isOk());
    }

        @Test public void registerNoMail() throws IOException
    {
		if (!isReady())
	    return;
		try {
	    newBooks().users().register().exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(RegisterQuery.NO_MAIL, r.getType());
	}
    }

            @Test public void registerInvalidMail() throws IOException
    {
		if (!isReady())
	    return;
		try {
		    newBooks().users().register().mail("lala").exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(RegisterQuery.INVALID_MAIL, r.getType());
	}
    }

                @Test public void registerNoPassword() throws IOException
    {
		if (!isReady())
	    return;
		try {
		    newBooks().users().register().mail("test@luwrain.org").exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(RegisterQuery.NO_PASSWORD, r.getType());
	}
    }

                    @Test public void registerINvalidPassword() throws IOException
    {
		if (!isReady())
	    return;
		final StringBuilder b = new StringBuilder();
		for(int i = 0;i < 256; i++)
		    b.append("1");
		try {
		    newBooks().users().register().mail("test@luwrain.org").passwd(new String(b)).exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(RegisterQuery.INVALID_PASSWORD, r.getType());
	}
    }

                        @Test public void registerMailbAlreadyInUse() throws IOException
    {
		if (!isReady())
	    return;
		try {
		    newBooks().users().register().mail(getMail()).passwd(getPasswd()).exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(RegisterQuery.MAIL_ADDRESS_ALREADY_IN_USE, r.getType());
	}
    }

    @Test public void accessTokenNotRegistered() throws IOException
    {
		if (!isReady())
	    return;
		try {
	    newBooks().users().accessToken().mail("none@luwrain.org").passwd(getPasswd()).exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals("NOT_REGISTERED", r.getType());
	}
    }

        @Test public void accessTokenInvalidCredentials() throws IOException
    {
	if (!isReady())
	    return;
	//no neither mail nor password
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
	//no password
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
    }

    @Test public void confirmLimitExceeding() throws IOException
    {
	final String mail = "junit-confirm-limit-exceeding@luwrain.org";
	if (!isReady())
	    return;
	final RegisterQuery.Response r1 = newBooks().users().register().mail(mail).passwd("123123123").exec();
	assertNotNull(r1);
	assertTrue(r1.isOk());
	for(int i = 0;i < 5;i++)
	{
	    try {
		final ConfirmQuery.Response r = newBooks().users().confirm().mail(mail).confirmationCode("10000").exec();
		assertTrue(false);
	    }
	    catch(BooksException e)
	    {
		final ErrorResponse r = e.getErrorResponse();
		assertNotNull(r);
		assertNotNull(r.getType());
		assertEquals(r.getType(), "INVALID_CONFIRMATION_CODE");
	    }
	}
	try {
	    final ConfirmQuery.Response r = newBooks().users().confirm().mail(mail).confirmationCode("10000").exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(r.getType(), "TOO_MANY_ATTEMPTS");
	}
    }

    @Test public void confirmNoMail() throws IOException
    {
	try {
	    final ConfirmQuery.Response r = newBooks().users().confirm().confirmationCode("10000").exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(r.getType(), "NO_MAIL");
	}
    }

    @Test public void confirmNoConfirmationCode() throws IOException
    {
	try {
	    final ConfirmQuery.Response r = newBooks().users().confirm().mail("junit-none@luwrain.org").exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(r.getType(), "NO_CONFIRMATION_CODE");
	}
    }

    @Test public void confirmInvalidMail() throws IOException
    {
	try {
	    final ConfirmQuery.Response r = newBooks().users().confirm().mail(getMail()).confirmationCode("10000").exec();
	    assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertNotNull(r.getType());
	    assertEquals(r.getType(), "INVALID_MAIL");
	}
    }

        @Test public void verifyAccessToken() throws IOException
    {
	if (!isReady())
	    return;
	final VerifyAccessTokenQuery.Response r = newBooks().users().verifyAccessToken().accessToken(getAccessToken()).exec();
assertNotNull(r);
assertNotNull(r.getType());
assertEquals(VerifyAccessTokenQuery.OK, r.getType());
assertNotNull(r.getMail());
assertEquals(getMail(), r.getMail());
    }

            @Test public void verifyAccessToken1() throws IOException
    {
	if (!isReady())
	    return;
	try {
newBooks().users().verifyAccessToken().exec();
assertTrue(false);
	}
	catch(BooksException e)
	{
	    final ErrorResponse r = e.getErrorResponse();
	    assertNotNull(r);
	    assertEquals(VerifyAccessTokenQuery.NO_VALID_ACCESS_TOKEN, r.getType());
	}
	}
}
