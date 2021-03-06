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

package javasoft.sqe.tests.javax.mail.Transport;

/**
 * This class tests the <strong>getURLName()</strong> API.
 * It does this by passing various valid input values and then checking
 * the type of the returned object.     <p>
 *
 *              Return a URLName representing this Transport. <p>
 * api2test: public URLName getURLName()     <p>
 *
 * how2test: Call this API, if it returns non-null object of type URLName,
 *           then this testcase passes otherwise it fails. <p>
 */

import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.javatest.*;
import javasoft.sqe.tests.javax.mail.util.MailTest;

public class getURLName_Test extends MailTest {

    public static void main( String argv[] )
    {
        getURLName_Test test = new getURLName_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run( String argv[], PrintWriter log, PrintWriter out )
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

	out.println("\nTesting Transport class API => getURLName()\n");

	try {
           // Get a Session object
              Session session = Session.getInstance(properties, null);

           // Get a Transport object
              Transport transport = session.getTransport(transport_protocol);

           // Connect
              if( transport_host != null ) {
		  if( auth )
		      transport.connect(transport_host, user, password);
		  else
		      transport.connect(transport_host, null, null);
              } else
                  transport.connect();

	   // BEGIN UNIT TEST 1:
	      out.println("UNIT TEST 1:  close()");
              URLName urlname = transport.getURLName();		// API TEST

	      out.println("URL name for this transport is "+ urlname);
	      out.println("UNIT TEST 1: passed\n");
	   // END UNIT TEST 1:

	   // close connect to transport
	      transport.close();

	      status = Status.passed("OKAY");
        } catch (Exception e) {
              handlException(e);
          }
	  return status;
    }
}
