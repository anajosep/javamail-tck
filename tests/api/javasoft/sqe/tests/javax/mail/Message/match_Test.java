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
import javax.mail.search.*;
import com.sun.javatest.*;
import javasoft.sqe.tests.javax.mail.util.MailTest;

/**
 * This class tests the <strong>match()</strong> API.
 * It does this by passing various valid input values and then checking
 * the type of the returned object.	<p>
 *
 *		Apply the specified Search criterion to this message. <p>
 * api2test: public boolean match(Term term)  <p>
 *
 * how2test: Call this API for any message object, verify that it returns a boolean
 *	     value. Regardless of the value returned this testcase passes.
 */

public class match_Test extends MailTest {

    static String SUBJECT = "Subject";

    public static void main( String argv[] )
    {
        match_Test test = new match_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class Message: match(Term)\n");

        try {
          // Connect to host server
             Store store = connect2host(protocol, host, user, password);

          // Get a Folder object
	     Folder root = getRootFolder(store);
             Folder folder = root.getFolder(mailbox);

             if( folder == null ) {
                 return Status.failed("Invalid folder object!");
             }
             folder.open(Folder.READ_ONLY);
	     BodyTerm freeStr = new BodyTerm(pattern);
	     HeaderTerm caseTerm  = new HeaderTerm(SUBJECT, pattern);

	     if ( freeStr == null || caseTerm == null ) {
		  return Status.failed("null Body/Header Term object returned!");
	     }

	     if( msgcount == -1 ) {
                 msgcount = folder.getMessageCount();
                 if( msgcount < 1 )
                     return Status.failed("Mail folder is empty!");
             }
	     boolean foundit;

             for ( int i = 1; i <= msgcount; i++ )
             {
             // Get the message
                Message msg =  folder.getMessage(i);

	        if( msg == null ) {
		    log.println("WARNING: FAILED TO GET MESSAGE NUMBER: "+ i);
		    continue;
	        }
	     // BEGIN UNIT TEST 1:
		// find the pattern in message body
	        out.println("UNIT TEST "+ i +":  match(Term)");

	        if ( freeStr != null )
	             foundit = msg.match(freeStr);	// API TEST
		else
		     return null;

	        if ( foundit ) {
	             out.println("Pattern "+ pattern +" found in message body.");
                     out.println("UNIT TEST "+ i +":  passed\n");
	        } else {
		        out.println("Caution: Pattern "+ pattern +" not found in message body!");
	        }
	     // END UNIT TEST 1:
             // BEGIN UNIT TEST 2:
                // find the pattern in message header
                out.println("UNIT TEST "+ i +":  match(Term)");

		if ( caseTerm != null )
                     foundit = msg.match(caseTerm);    // API TEST
		else
		     return null;

                if ( foundit ) {
                    out.println("Pattern "+ pattern +" found in message header.");
                    out.println("UNIT TEST "+ i +":  passed\n");
                } else {
                        out.println("Caution: Pattern "+ pattern +" not found in message header!");
                }
             // END UNIT TEST 2:
	     }
	     folder.close(false);
	     store.close();
	     status = Status.passed("OKAY");

        } catch ( Exception e ) {
	     handlException(e);
        }
	return status;
     }
}
