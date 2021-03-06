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
 * This class tests the <strong>NoSuchProviderException()</strong> API.
 * It does this by passing various valid input values and then checking
 * the type of the returned object.	<p>
 *
 * api2test: public NoSuchProviderException(void|String) <p>
 *
 * how2test: Try to getting non-existing Provider and if this results in
 *	     an NoSuchProvider exception, then this testcase passes.
 */

public class noSuchProvExp_Test extends MailTest {

    public static void main( String argv[] )
    {
        noSuchProvExp_Test test = new noSuchProvExp_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class NoSuchProviderException: NoSuchProviderException()\n");

        try {
           // Get Session object
              Session session = Session.getInstance(properties, null);

           // BEGIN UNIT TEST 1:
	      out.println("UNIT TEST 1: NoSuchProviderException(void | String)");

	      session.getProvider("foo");	// API TEST

	      out.println("UNIT TEST 1: FAILED.\n");
           // END UNIT TEST 1:

	      status = Status.failed(" Failed to catch NoSuchProviderException ");
        } catch ( NoSuchProviderException nspe ) {
		out.println("UNIT TEST 1: passed.\n");
		ExceptionTest(nspe);
        }
	return status;
     }
}
