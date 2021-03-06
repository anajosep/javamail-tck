/*
 * Copyright (c) 2002, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package javasoft.sqe.tests.javax.mail.exception;

import java.io.*;
import javax.mail.*;
import com.sun.javatest.*;
import javasoft.sqe.tests.javax.mail.util.MailTest;

/**
 * This class tests the <strong>AuthenticationFailedException()</strong> API.
 * It does this by passing various valid input values and then checking
 * the type of the returned object.	<p>
 *
 * api2test: public AuthenticationFailedException(void | String) <p>
 *
 * how2test: Call the connect() API with invalid password and if this results
 *	     in an AuthenticationFailed exception, then this testcase passes.
 */

public class authenFailExp_Test extends MailTest {

    private Session session;
    private Store   store;

    public static void main( String argv[] )
    {
        authenFailExp_Test test = new authenFailExp_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options
	password="xyz0123";

        out.println("\nTesting class AuthenticationFailedException: AuthenticationFailedException()\n");

        try {
           // Get a Session object
              session = Session.getInstance(properties, null);
	      session.setDebug(debug);

              if( session == null ) {
                  return Status.failed("Warning: Failed to create a Session object!");
              }
           // Get a Store object
              store = session.getStore(protocol);

              if( store == null ) {
                  return Status.failed("Warning: Failed to create a Store object!");
              }
	   // BEGIN UNIT TEST 1:
	      out.println("UNIT TEST 1: AuthenticationFailedException(void|String)");

           // Connect
	      out.println("Connecting to " + host + " with user = " + user + " and password = " + password);
              if (host != null || user != null || password != null)
		  if (portnum > 0)
		      store.connect(host, portnum, user, password);
		  else
		      store.connect(host, user, password);
              else		    // API TEST
                  store.connect();

              out.println("UNIT TEST 1: FAILED.\n");
           // END UNIT TEST 1:

	      status = Status.failed("Failed to catch AuthenticationFailedException");
        } catch ( AuthenticationFailedException afe ) {
		out.println("UNIT TEST 1: passed.\n");
		ExceptionTest(afe);
        } catch ( NoSuchProviderException nspe ) {
		handlException(nspe);
	} catch ( MessagingException me ) {
		handlException(me);
	}
	return status;
     }
}
