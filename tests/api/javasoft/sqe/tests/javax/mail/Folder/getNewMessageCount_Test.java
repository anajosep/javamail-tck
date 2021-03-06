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

package javasoft.sqe.tests.javax.mail.Folder;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.javatest.*;
import javasoft.sqe.tests.javax.mail.util.MailTest;

/**
 * This class tests the <strong>getNewMessageCount()</strong> API.
 * It does this by invoking the api under test and then checking
 * the value of the returned object.	<p>
 *
 *		Get the number of new messages in this Folder
 * api2test: public int getNewMessageCount()  <p>
 *
 * how2test: Call this API. Verify it return type to be int. <p>
 *	     This is operation is successfull then testcase passes. <p>
 *
 *	  a) Test this with folder containing some new messages.
 *	  b) Test this with folder containing NO new messages.
 */

public class getNewMessageCount_Test extends MailTest {

    public static void main( String argv[] )
    {
        getNewMessageCount_Test test = new getNewMessageCount_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class Folder: getNewMessageCount()\n");

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
	  // BEGIN UNIT TEST 1:
             out.println("UNIT TEST 1: getNewMessageCount();");
	     
             int new_msg = folder.getNewMessageCount();	    // API TEST
	     out.println("Total new messages in the folder "+ mailbox +" = "+ new_msg);

             if( new_msg >= 0 )
                 out.println("UNIT TEST 1: passed\n");
             else {
                    out.println("UNIT TEST 1: FAILED\n");
                    errors++;
             }
	  // END UNIT TEST 1:
	     folder.close(false);
	     store.close();
	     checkStatus();

        } catch ( Exception e ) {
	     handlException(e);
        }
	return status;
     }
}
