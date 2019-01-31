import java.rmi.Naming;
import java.util.Random;
import java.util.*;
import java.rmi.registry.*;
import java.io.*;
import java.net.*;
import java.io.IOException;
import java.net.*;
import java.rmi.*;
import java.rmi.server.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.*;
import java.util.Enumeration;
import java.nio.channels.*;


public class ServerForStart {

 public static int NumHosts;
 public static CSConn SStartRMI;

 public static String TrovaIp() throws Exception {
  List < String > Indirizzo = new ArrayList < String > ();
  InetAddress addr = Inet4Address.getLocalHost();

  // Get IP Address
  String ipAddr = addr.getAddress().toString();

  // Get hostname
  String hostname = addr.getHostName();

  Enumeration e = NetworkInterface.getNetworkInterfaces();
  while (e.hasMoreElements()) {
   NetworkInterface n = (NetworkInterface) e.nextElement();
   Enumeration ee = n.getInetAddresses();
   while (ee.hasMoreElements()) {
    InetAddress i = (InetAddress) ee.nextElement();
    if (i instanceof Inet4Address && i.getHostAddress().equals("127.0.0.1") == false && (i.getHostAddress().contains("130.136") || i.getHostAddress().contains("192.168"))) {
     return i.getHostAddress();
    }
   }
  }
  return "error";

 }

 public static void main(String[] args) throws Exception {

  String IP = TrovaIp();

  String user = System.getProperty("user.dir");
  System.setProperty("java.rmi.server.hostname", IP);
  System.setProperty("java.security.policy", user + "/security.policy");
  System.setSecurityManager(new SecurityManager());
  try {
   LocateRegistry.createRegistry(1099);
  } catch (RemoteException e) {
   LocateRegistry.getRegistry(1099).list();
   System.out.println("rmiregistry already started");
  }

  if (args.length == 0) {
   System.out.println("ERROR: You don't specified any hosts number.");
   System.out.println("Usage: java ServerForStart #Hosts");
   System.exit(1);
  } else {
   try {
    Integer tmp = Integer.parseInt(args[0]);
    if (tmp > 8 || tmp < 1) {
     System.out.println("ERROR: You can manage 8 hosts max.");
     System.out.println("Usage: java ServerForStart 8");
     System.exit(1);
    } else {
     NumHosts = tmp;
    }

   } catch (NumberFormatException ex) {
    System.out.println("ERROR: You don't specified any Integer value.");
    System.out.println("Usage: java ServerForStart #Hosts");
    System.exit(1);
   }

  }

  try {
   SStartRMI = new CSConn(NumHosts);

   //LocateRegistry.createRegistry(1999 + MyConnectionID);
   Naming.rebind("rmi://" + IP + "/SConn", SStartRMI);
   System.out.println("server ready");
   NumHosts = SStartRMI.nsize;
   System.out.println(NumHosts);
   //List <String> prov = new ArrayList<String>();
   //serverRMI.Ricezione(prov, 1);

  } catch (Exception e) {
   e.printStackTrace();
   System.exit(1);
  }



 }

}