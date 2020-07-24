package org.owasp.psafix.devsec.cryptodemo;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClassLoader;
import java.security.Permission;

import static java.rmi.server.RMIClassLoader.getDefaultProviderInstance;

/**
 * Display names bound to RMI registry on provided host and port.
 */
public class RMITest
{
    private final static String NEW_LINE = System.getProperty("line.separator");

    static class Sec extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
        }

        @Override
        public void checkPermission(Permission perm, Object context) {
        }

        @Override
        public void checkCreateClassLoader() {
        }

        @Override
        public void checkAccess(Thread t) {
        }

        @Override
        public void checkAccess(ThreadGroup g) {
        }

        @Override
        public void checkExit(int status) {
        }

        @Override
        public void checkExec(String cmd) {
        }

        @Override
        public void checkLink(String lib) {
        }

        @Override
        public void checkRead(FileDescriptor fd) {
        }

        @Override
        public void checkRead(String file) {
        }

        @Override
        public void checkRead(String file, Object context) {
        }

        @Override
        public void checkWrite(FileDescriptor fd) {
        }

        @Override
        public void checkWrite(String file) {
        }

        @Override
        public void checkDelete(String file) {
        }

        @Override
        public void checkConnect(String host, int port) {
        }

        @Override
        public void checkConnect(String host, int port, Object context) {
        }

        @Override
        public void checkListen(int port) {
        }

        @Override
        public void checkAccept(String host, int port) {
        }

        @Override
        public void checkMulticast(InetAddress maddr) {
        }

        @Override
        public void checkMulticast(InetAddress maddr, byte ttl) {
        }

        @Override
        public void checkPropertiesAccess() {
        }

        @Override
        public void checkPropertyAccess(String key) {
        }

        @Override
        public boolean checkTopLevelWindow(Object window) {
            return true;
        }

        @Override
        public void checkPrintJobAccess() {
        }

        @Override
        public void checkSystemClipboardAccess() {
        }

        @Override
        public void checkAwtEventQueueAccess() {
        }

        @Override
        public void checkPackageAccess(String pkg) {
        }

        @Override
        public void checkPackageDefinition(String pkg) {
        }

        @Override
        public void checkSetFactory() {
        }

        @Override
        public void checkMemberAccess(Class<?> clazz, int which) {
        }

        @Override
        public void checkSecurityAccess(String target) {
        }
    }
    /**
     * Main executable function for printing out RMI registry names on provided
     * host and port.
     *
     * @param arguments Command-line arguments; Two expected: first is a String
     *    representing a host name ('localhost' works) and the second is an
     *    integer representing the port.
     */
    public static void main(String[] arguments) throws Exception
    {

        System.setSecurityManager(new Sec());
        Thread.currentThread().setContextClassLoader(getDefaultProviderInstance().getClassLoader(null));


        arguments=new String[]{"10.170.2.10", "49194"};
        if (arguments.length < 2)
        {
            System.err.println(
                    "A host name (String) and a port (Integer) must be provided.");
            System.err.println(
                    "\tExample: java dustin.examples.rmi.RmiPortNamesDisplay localhost 1099");
            System.exit(-2);
        }

        final String host = arguments[0];
        int port = 1099;
        try
        {
            port = Integer.valueOf(arguments[1]);
        }
        catch (NumberFormatException numericFormatEx)
        {
            System.err.println(
                    "The provided port value [" + arguments[1] + "] is not an integer."
                            + NEW_LINE + numericFormatEx.toString());
        }

        try
        {
            final Registry registry = LocateRegistry.getRegistry(host, port);
            final String[] boundNames = registry.list();
            System.out.println(
                    "Names bound to RMI registry at host " + host + " and port " + port + ":");
            for (final String name : boundNames)
            {
                System.out.println("\t" + name);

                try {
                    registry.lookup(name);
                } catch (Exception e) {
                    System.out.println("==>"+e.getMessage());
                }
            }
        }
        catch (ConnectException connectEx)
        {
            System.err.println(
                    "ConnectionException - Are you certain an RMI registry is available at port "
                            + port + "?" + NEW_LINE + connectEx.toString());
        }
        catch (RemoteException remoteEx)
        {
            System.err.println("RemoteException encountered: " + remoteEx.toString());
        }
    }
}