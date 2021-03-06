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
 * This class tests the <strong>getFullName()</strong> API.
 * It does this by invoking this api and then checking
 * the type/value of the returned object.	<p>
 *
 *		Returns the full name of this Folder
 * api2test: public String getFullName()  <p>
 *
 * how2test: Call this API. Check that it returns a string.
 *
 *        a) If the folder resides under the root hierarchy of this Store,
 *	     the returned name is relative to the root. <p>
 *
 *	  b) Otherwise an absolute name, starting with the hierarchy delimiter,
 *	     is returned.  <p>
 *
 *	  c) This method can be invoked on a closed Folder. <p>
 *	  d) Try it on open folder.
 */

public class getFullName_Test extends MailTest {

    public static void main( String argv[] )
    {
        getFullName_Test test = new getFullName_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class Folder: getFullName()\n");

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

	  // BEGIN UNIT TEST 1:
	     // Invoke on open folder
	     out.println("UNIT TEST 1: getFullName()");

	     String fullName = folder.getFullName();	// API TEST
	     out.println("Full folder name is " + fullName);

	     if ( fullName != null && ( fullName instanceof String ) )
                  out.println("UNIT TEST 1: passed\n");
             else {
                    out.println("UNIT TEST 1: FAILED\n");
                    errors++;
	     }
	     folder.close(false);

	  // END UNIT TEST 1:
          // BEGIN UNIT TEST 2:
		// Invoke on closed folder
             out.println("UNIT TEST 2: getFullName()");

             fullName = folder.getFullName();	// API TEST
             out.println("Full folder name is " + fullName);

             if ( fullName != null && ( fullName instanceof String ) )
                  out.println("UNIT TEST 2: passed\n");
             else {
                    out.println("UNIT TEST 2: FAILED\n");
                    errors++;
             }
          // END UNIT TEST 2:
	     store.close();
             checkStatus();

        } catch ( Exception e ) {
             handlException(e);
        }
	return status;
     }
}
