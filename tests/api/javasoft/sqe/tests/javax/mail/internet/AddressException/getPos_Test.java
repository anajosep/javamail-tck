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

package javasoft.sqe.tests.javax.mail.internet.AddressException;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.javatest.*;
import javasoft.sqe.tests.javax.mail.util.MailTest;

/**
 * This class tests the <strong>getPos()</strong> API.
 * It does this by passing various valid input values and then checking
 * the type of the returned object.	<p>
 *
 *	       Get the position with the reference string where the error was detected .<p>
 * api2test: public int getPos()  <p>
 *
 * how2test: Call this API and if it successfully return int value,
 *	     then this testcase passes.
 */

public class getPos_Test extends MailTest {

    public AddressException ae;

    public static void main( String argv[] )
    {
        getPos_Test test = new getPos_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class AddressException: getPos(void)\n");

        try {
           // Connect to host server
              Store store = connect2host(protocol, host, user, password);

	   // Create AddressException object
              ae = new AddressException(subject, pattern, 13);

              if( ae == null )
                  return Status.failed("Invalid/null AddressException object!");

           // BEGIN UNIT TEST 1:
              out.println("UNIT TEST 1: getPos()");

              int position = ae.getPos();       // API TEST

              if( position > 0 )
                  out.println("UNIT TEST 1: passed.\n");
              else {
                    out.println("UNIT TEST 1: FAILED.\n");
                    errors++;
              }
           // END UNIT TEST 1:
	      store.close();
              checkStatus();

        } catch ( Exception e ) {
              handlException(e);
        }
	return status;
     }
}
