package org.carlos.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;
import java.util.Collections;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;

public class Starter2 extends Starter {
	private static String bindHost = "localhost";

	private static class SingleHostSocketFactory extends RMISocketFactory {
		@Override
		public ServerSocket createServerSocket( int port ) throws IOException {
			InetAddress localAddress = InetAddress.getByName( bindHost );
			return ServerSocketFactory.getDefault().createServerSocket( port, 0, localAddress );
		}

		@Override
		public Socket createSocket( String host, int port ) throws IOException {
			return SocketFactory.getDefault().createSocket( bindHost, port );
		}
	}

	public static void main( String[] args ) throws Exception {

		jmxremote();

		{
			System.setProperty( "java.rmi.server.hostname", bindHost );

			SingleHostSocketFactory socketFactory = new SingleHostSocketFactory();

			LocateRegistry.createRegistry( 8888, socketFactory, socketFactory );
			RMISocketFactory.setSocketFactory( socketFactory );

			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

			JMXServiceURL url = new JMXServiceURL(
					"service:jmx:rmi:///jndi/rmi://localhost:8888/jmxrmi" );

			JMXConnectorServer server = JMXConnectorServerFactory.newJMXConnectorServer( url,
					Collections.<String, Object> emptyMap(), mbs );
			server.start();

		}
		System.out.println( "--------------------------" );
		{

			// Create an RMI connector client 
			// 
			JMXServiceURL url = new JMXServiceURL(
					"service:jmx:rmi:///jndi/rmi://localhost:8888/jmxrmi" );
			JMXConnector jmxc = JMXConnectorFactory.connect( url, null );
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

			// Get domains from MBeanServer 
			// 
			String domains[] = mbsc.getDomains();
			for ( int i = 0; i < domains.length; i++ ) {
				System.out.println( "Domain[" + i + "] = " + domains[i] );
			}

		}
		System.out.println( "--------------------------" );

		loop();
	}

}
