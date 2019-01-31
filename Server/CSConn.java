import java.rmi.Naming;
import java.util.Random;
import java.util.*;
import java.rmi.registry.*;
import java.io.*;
import java.net.*;
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;

public class CSConn
extends java.rmi.server.UnicastRemoteObject implements SConnInterface

{

 int nc = 0;
 Boolean conn = false;
 List < String > table = new ArrayList < String > ();
 String HN = "";
 //int UtentiTavolo = 3;
 int nsize;
 int hosts;
 TexasGame serverRMI;
 String element;

 public CSConn(int numberH) throws java.rmi.RemoteException {
  super();
  nsize = numberH;
  hosts = numberH;

 }



 public void Connect(String a) {
  if (a.equals("poker")) {
   System.out.println("Connect ricevuta");
   conn = true;
   try {
    HN = RemoteServer.getClientHost();
    System.out.println(HN);

   } catch (ServerNotActiveException e) {
    System.out.println("ip reading failed");
   }
   table.add(HN);
   System.out.println("dopo try");
   nsize = nsize - 1;
   if (nsize == 0) {
    for (int i = 0; i < table.size(); i++) {
     try {
      System.out.println("dentro ciclo");
      element = table.get(i);
      System.out.println(element);
      serverRMI = (TexasGame) java.rmi.Naming.lookup("rmi://" + element + "/Game");
      serverRMI.Ricezione(table, i + 1);
      System.out.println(element);
      System.out.println(i + 1);
      System.out.println(table.size());
      if (i == table.size() - 1) {
       nsize = hosts;
       table.clear();
      }


     } catch (Exception e) {
      System.out.println("error in communication with client");

      e.printStackTrace();
      nsize = hosts;
      table.clear();
     }
    }
   }
  }
 }

}