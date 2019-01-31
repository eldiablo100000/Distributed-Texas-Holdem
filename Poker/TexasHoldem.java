import java.rmi.Naming;
import java.util.Random;
import java.util.*;
import java.rmi.registry.*;
import java.rmi.RemoteException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.net.*;
import java.util.*;
import java.nio.channels.*;
import java.io.*;

                                                                                            // client pocker texas holdem

public class TexasHoldem{

public static ServerRMI serverRMI;
public static String MyIp;
public static List <Integer> Carte = new ArrayList <Integer>();
public static boolean debug = false;


   public static String TrovaIp(){
    List <String> Indirizzo = new ArrayList <String>();
    try{
        InetAddress addr = Inet4Address.getLocalHost();

        // Get IP Address
        String ipAddr = addr.getAddress().toString();

        // Get hostname
        String hostname = addr.getHostName();
        
        Enumeration  e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                if(i instanceof Inet4Address && i.getHostAddress().equals("127.0.0.1")==false && (i.getHostAddress().contains("130.136") || i.getHostAddress().contains("192.168"))){
                    return i.getHostAddress();
                }
            }
        }
        }catch(UnknownHostException ex){
            return "error";
        }catch(SocketException err){
            return "error";
        }
    return "error";
    }



    public static void main(String[] args) throws Exception{

    	MyIp = TrovaIp();
    	System.out.println("MY IP: " +MyIp);
    	String user=System.getProperty("user.dir");
        String RegistrationServerAddress = "";
    	
    	System.setProperty("java.rmi.server.hostname", MyIp);
    	System.setProperty("java.security.policy",user+"/security.policy");
    	  System.setSecurityManager(new SecurityManager());
    	  try {
    	   LocateRegistry.createRegistry(1099);
    	  } catch (RemoteException e) {
    	   LocateRegistry.getRegistry(1099).list();
    	   System.out.println("rmiregistry already started");
    	  }

        if(args.length == 0){
            System.out.println("ERROR: You don't specified any Registration Server.");
            System.out.println("Usage: java TexasHoldem IPADDRESS");
            System.exit(1);
        }
        else{
                RegistrationServerAddress = args[0];
                if (args.length == 2) {
                    if (args[1].equals("-d")) debug = true;
                    else{
                        System.out.println("ERROR: option "+args[1]+" not exist.");
                        System.out.println("Usage: '-d' for debug");
                        System.exit(1);
                    }
                }
        }
    	
            //inizializzazione del client---------------------------------------------------------------------------------------
    	try
     		{ 
    		serverRMI = new ServerRMI(); 
     		Naming.rebind("rmi://"+MyIp+"/Game", serverRMI);

     	} 
    	catch
     		(Exception e) 
    		{e.printStackTrace(); System.exit(1); }
    		
            //Connessione con il server per ricevere gli indirizzi ip dei giocatori---------------------------------------------
             try{
    			SConnInterface SStartRMI = (SConnInterface)java.rmi.Naming.lookup("rmi://"+RegistrationServerAddress+"/SConn");
    			SStartRMI.Connect("poker");
    			System.out.println("client ready");
    			System.out.println("inviata richiesta");
    			}catch
    				(Exception e) 
    	 		{ System.out.println("error in communication with server" +e);    
    	 		}
    	}
}
