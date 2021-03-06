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

package javasoft.sqe.tests.javax.mail.Flags;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.javatest.*;
import javasoft.sqe.tests.javax.mail.util.MailTest;

/**
 * This class tests the <strong>clone()</strong> API.
 * It does this by invoking the test api and then checking
 * the type of the returned object.	<p>
 *
 *		Returns a clone of this Flags object. <p>
 * api2test: public Flags clone()  <p>
 *
 * how2test: Get a message's Flags object then clone this object by <p>
 *	     using clone() api. Check the type of object cloned.
 */

public class clone_Test extends MailTest {

    public static void main( String argv[] )
    {
        clone_Test test = new clone_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class Flags: clone()\n");

        try {
          // Connect to host server
             Store store = connect2host(protocol, host, user, password);

          // Get a Folder object
	     Folder root = getRootFolder(store);
             Folder folder = root.getFolder(mailbox);

             if ( folder == null ) {
                  return Status.failed("Invalid folder object!");
             }
             folder.open(Folder.READ_ONLY);
	     msgcount = folder.getMessageCount();

	     if ( msgcount < 1 )
		  return Status.failed("Mail folder is empty!");

	     for( int i = 1; i <= msgcount; i++ )
	     {
	     // Get message object
		MimeMessage msg = (MimeMessage)folder.getMessage(i);

		if ( msg == null ) {
		     out.println("WARNING: FAILED TO GET MESSAGE NUMBER: "+ i);
		     continue;
		}
	     // Get Flags object for this message
		Flags flag = msg.getFlags();

                if ( flag == null ) {
                     out.println("WARNING: FAILED TO GET FLAGS OBJECT FOR MESSAGE: "+ i);
                     continue;
                }
	     // BEGIN UNIT TEST:

                out.println("UNIT TEST "+ i +":  clone()");

		Flags cflag = (Flags)flag.clone();		// API TEST

		if( cflag != null )
		    out.println("UNIT TEST " + i + ":  passed\n");
		else {
		      out.println("UNIT TEST "+ i +":  FAILED\n");
		      errors++;
		}
	     // END OF UNIT TEST:
	     }
	     folder.close(false);
	     store.close();
	     checkStatus();

        } catch ( Exception e ) {
             handlException(e);
        }
	return status;
     }
}
