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
 * This class tests the <strong>setContentMD5()</strong> API.
 * It does by passing various valid input values and then checking
 * the type of the returned object.	<p>
 *
 *	    Set the "Content-MD5" header field of BodyPart. <p>
 * api2test: public void setContentMD5(String)  <p>
 *
 * how2test: Call API with string arguments, then call getContentMD5(), if the
 *	     set/get values are equal then testcase passes, otherwise it fails.
 */

public class setContentMD5_Test extends MailTest {

    public static String[] setmd = { "x-y-z","content-md5","0123456789abc@#$%^&*+=","" };

    public static void main( String argv[] )
    {
        setContentMD5_Test test = new setContentMD5_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class MimeBodyPart: setContentMD5()\n");

        try {
          // Create a MimeBodyPart object
	     MimeBodyPart bp = new MimeBodyPart();

             if( bp == null )
                 return Status.failed("Failed to create MimeBodyPart object!");

	  // BEGIN UNIT TEST:

             for( int i = 0; i < setmd.length; i++ )
             {
                  out.println("UNIT TEST "+i+": setContentMD5("+setmd[i]+")");
		  bp.setContentMD5(setmd[i]); 	// API TEST

                  if( setmd[i].equals(bp.getContentMD5()) )
                      out.println("UNIT TEST "+i+":  passed.\n");
                  else {
                        out.println("UNIT TEST "+i+":  FAILED.\n");
                        errors++;
                  }
             }
          // END UNIT TEST:
	     checkStatus();

        } catch ( Exception e ) {
	     handlException(e);
        }
	return status;
     }
}
