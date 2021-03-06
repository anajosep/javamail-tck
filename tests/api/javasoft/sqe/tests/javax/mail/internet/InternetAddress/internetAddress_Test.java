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

package javasoft.sqe.tests.javax.mail.internet.InternetAddress;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.javatest.*;
import javasoft.sqe.tests.javax.mail.util.MailTest;

/**
 * This class tests the <strong>InternetAddress()</strong> API.
 * It does by invoking the test API and then checking
 * the type of the returned object.	<p>
 *
 *		a default contructor <p>
 * api2test: public InternetAddress() <p>
 *		yet another constructor <p>
 * api2test: public InternetAddress(String) <p>
 *
 * how2test: Invoke InternetAddress() APIs, if these methods create a non-null object
 *	     of type InternetAddress, then the testcase passes, otherwise it fails.
 */

public class internetAddress_Test extends MailTest {

    public static void main ( String argv[] )
    {
        internetAddress_Test test = new internetAddress_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class InternetAddress: InternetAddress(void | String)\n");

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
	     out.println("\nUNIT TEST 1:  InternetAddress()");
	     InternetAddress addr1 = new InternetAddress();	// API TEST
     
             if(( addr1 != null ) && ( addr1 instanceof InternetAddress ))
                  out.println("UNIT TEST 1: passed\n");
	     else {
		   out.println("UNIT TEST 1: FAILED\n");
		   errors++;
	     }
	  // END UNIT TEST 1:
	  // BEGIN UNIT TEST:

	     if( msgcount == -1 ) {
                 msgcount = folder.getMessageCount();
                 if( msgcount < 1 )
                     return Status.failed("Mail folder is empty!");
             }
	     
             for( int i = 1; i <= msgcount; i++ )
             {
             // Get a Message object
                Message msg = folder.getMessage(i);

                if( msg == null ) {
                    log.println("WARNING: FAILED TO GET MESSAGE NUMBER: "+ i);
                    continue;
                }
                // Get a From address object(s)
                Address[] addrs = msg.getFrom();

                if( addrs == null || addrs.length == 0 ) {
                    log.println("WARNING: FAILED TO GET FROM ADDRESS FOR MESSAGE NUMBER: "+ i);
                    continue;
                }
                // Convert From address object to a string
                String addstr = addrs[0].toString();

                if( addstr != null )
		{
		    out.println("UNIT TEST "+ (i+1) +":  InternetAddress("+ addstr +")");

                    // Create new InternetAddress object
                    InternetAddress addr;
		    try {
			addr = new InternetAddress(addstr);	    // API TEST
		    } catch (AddressException aex) {
			/*
			 * Since we're using addresses from headers, they
			 * might not obey RFC 822.  If we get an exception,
			 * try reparsing the address as a non-strict header.
			 */
			addr = InternetAddress.parseHeader(addstr, false)[0];  // API TEST
		    }

                    if (( addr != null ) && ( addr instanceof InternetAddress )) {
			  out.println("Address = "+ addr.getAddress());
			  out.println("UNIT TEST "+ (i+1) +": passed\n");
		    } else {
			    out.println("UNIT TEST "+ (i+1) +": FAILED\n");
			    errors++;
                          }
                } else
			continue;
	     }
	     // END UNIT TEST:
	     folder.close(false);
	     store.close();
	     checkStatus();

        } catch ( Exception e ) {
	     handlException(e);
        }
	return status;
     }
}
