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

package javasoft.sqe.tests.javax.mail.internet.MimeBodyPart;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.javatest.*;
import javasoft.sqe.tests.javax.mail.util.MailTest;

/**
 * This class tests the <strong>getDescription()</strong> API.
 * It does by passing various valid input values and then checking
 * the type of the returned object.	<p>
 *
 *		Returns the "Content-Description" header field of BodyPart. <p>
 * api2test: public String getDescription()  <p>
 *
 * how2test: Call API, check that it returns 'content-description' string. If
 *           operation is successfull then this testcase passes else it fails.
 */

public class getDescription_Test extends MailTest {

    public static void main( String argv[] )
    {
        getDescription_Test test = new getDescription_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class MimeBodyPart: getDescription()\n");

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

	     if( msgcount == -1 ) {
                 msgcount = folder.getMessageCount();
                 if( msgcount < 1 )
                     return Status.failed("Mail folder is empty!");
             }

             for( int i = 1; i <= msgcount; i++ )
             {
             // Get the message
                MimeMessage msg = (MimeMessage)folder.getMessage(i);

	        if( msg == null ) {
		    log.println("WARNING: FAILED TO GET MESSAGE NUMBER: "+ i);
		    continue;
	        }
	     // BEGIN UNIT TEST:
	        out.println("UNIT TEST "+ i +":  getDescription()");

		// Get the "type" of content
	        Object content = msg.getContent();

		if ( content instanceof Multipart ) {
		     int bodycount = ((MimeMultipart)content).getCount();
		     for( int k = 0; k < bodycount; k++ )
		     {
		     	  BodyPart bp = ((MimeMultipart)content).getBodyPart(k);
			 // get "Content-Description" header field for bodypart
			  String desc = ((MimeBodyPart)bp).getDescription();  // API TEST

			  if ( desc != null )
			       out.println("UNIT TEST "+ i +":  passed\n");
			  else {
				out.println("Warning: getDescription() returned null");
				out.println("UNIT TEST "+ i +":  passed\n");
			  }
		       }
		}
             // END UNIT TEST:
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
