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

package javasoft.sqe.tests.javax.mail.event.StoreEvent;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.event.*;
import com.sun.javatest.*;
import javasoft.sqe.tests.javax.mail.util.MailTest;

/**
 * This class tests the <strong>addStoreListener()</strong> API.
 * It does this by passing various valid input values and then checking
 * the type/value of the returned object.	<p>
 *
 *              Listen and notifies of open/close events to Store. <p>
 * api2test: public addStoreListener(StoreListener)  <p>
 *
 * how2test: Call this API. Then verify that that the notification occur when
 *           open/close events occur to a Store. If this happens then this test
 *           passed otherwise it fails.
 */

public class addStoreListener_Test extends MailTest implements StoreListener {

    boolean return_code;
    boolean notified = false;

    public static void main( String argv[] )
    {
        addStoreListener_Test test = new addStoreListener_Test();
        Status s = test.run(argv, System.err, System.out);
	s.exit();
    }

    public void notification(StoreEvent e)
    {
	out.println("\nStore connect/close event occurred successfully!");
	notified = true;
    }

    public Status run(String argv[], PrintWriter log, PrintWriter out)
    {
	super.run(argv, log, out);
	parseArgs(argv);	// parse command-line options

        out.println("\nTesting class StoreEvent: addStoreListener(StoreListener)");

        try {
           // Get a Session object
              Session session = Session.getDefaultInstance(properties, null);

              if( session == null ) {
                  return Status.failed("Warning: Failed to create a Session object!");
              }
           // Get a Store object
              Store store = session.getStore(protocol);

              if( store == null ) {
                  return Status.failed("Warning: Failed to create a Store object!");
              }
	   // BEGIN UNIT TEST:
	      out.println("UNIT TEST 1: addStoreListener(StoreListener)\n");

	      store.addStoreListener(this);	// API TEST

           // Connect to server
              if( host != null || user != null || password != null )
		  if (portnum > 0)
		      store.connect(host, portnum, user, password);
		  else
		      store.connect(host, user, password);
              else
                  store.connect();

	   // Get folder object
	      Folder root = getRootFolder(store);
	      Folder folder = root.getFolder(mailbox);

	      if( folder == null ) {
		  return Status.failed("Invalid folder object!");
	      }
	      folder.open(Folder.READ_ONLY);
	      out.println("This folder has "+ folder.getMessageCount() +" messages.\n");

	      folder.close(false);
	      store.close();
	      store.removeStoreListener(this);

	      out.println("UNIT TEST 1:  passed\n");
	   // END UNIT TEST:

	      checkStatus();
        } catch ( Exception e ) {
              handlException(e);
        }
	return status;
     }
}
