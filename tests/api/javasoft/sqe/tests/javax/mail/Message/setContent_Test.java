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

package javasoft.sqe.tests.javax.mail.Message;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.javatest.*;
import javasoft.sqe.tests.javax.mail.util.MailTest;

/**
 * This class tests the <strong>setContent()</strong> API.
 * It does this by passing various valid input values and then checking
 * the type of the returned object.	<p>
 *
 *		This method provides the mechanism to set a Message's content. <p>
 * api2test: public void setContent(Object obj, String type)  <p>
 *
 * how2test: Call this API with required arguments, then call getContent() to check
 *	     that expected value was returned. If this operation is successful then
 *	     this testcase passes, otherwise it fails.
 */

public class setContent_Test extends MailTest {

    public static void main( String argv[] )
    {
        setContent_Test test = new setContent_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class Message: setContent(Object, String)\n");

        try {
          // Create a MimeMessage object
             Session session = Session.getInstance(properties, null);
             MimeMessage msg = new MimeMessage(session);

             if( msg == null ) {
                 log.println("WARNING: FAILED TO CREATE MESSAGE OBJECT");
                 return Status.failed("Failed to create Message object");
             }
	  // BEGIN UNIT TEST:
	     out.println("UNIT TEST 1:  setContent(Object, text|plain|html)");

	  // set the message's content
	     msg.setContent("Hello World", "text/plain");	// API TEST

	     out.println("setContent(text/plain | text/plain)");
	     Object content = msg.getContent();

	     if(( content != null ) && ( content instanceof String )) {
		  if( ((String)content).equals("Hello World") )
		      out.println("UNIT TEST 1:  passed\n");
		  else {
			out.println("UNIT TEST 1:  FAILED\n");
			errors++;
		  }
	     } else if(( content != null ) && ( content instanceof Multipart )) {
			 out.println("This is a Multipart");
			 Multipart mp = (Multipart)content;
            		 int count = mp.getCount();
		         out.println("UNIT TEST 1:  passed\n");
	     } else {
		      out.println("UNIT TEST 1:  FAILED\n");
		      errors++;
	     }
	  // END UNIT TEST:

	     checkStatus();

        } catch ( Exception e ) {
	     handlException(e);
        }
	return status;
     }
}
