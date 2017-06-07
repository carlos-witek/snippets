package org.carlos.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public abstract class Starter {

	public static void jmxremote() throws Exception {

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		mbs.registerMBean( new Hello(), new ObjectName( "com.example:type=Hello" ) );

		System.out.println( getProperty( "com.sun.management.jmxremote" ) );
		System.out.println( getProperty( "com.sun.management.jmxremote.port" ) );
		System.out.println( getProperty( "com.sun.management.jmxremote.authenticate" ) );
		System.out.println( getProperty( "com.sun.management.jmxremote.ssl" ) );

	}

	public static void loop() throws Exception {
		System.out.println( "Waiting forever..." );
		//		Thread.sleep( Long.MAX_VALUE );
	}

	private static String getProperty( String key ) {
		return key + ": " + System.getProperty( key );
	}

}
