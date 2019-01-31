
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.lang.*;
import java.util.concurrent.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.util.Timer;
import java.util.TimerTask;

public class GUI extends JFrame {

  public static final int CANVAS_WIDTH = 1000;
  public static final int CANVAS_HEIGHT = 700;
  public static TavoloPoker canvas;
  public static JButton btnCheck, btnCall, btnBet, btnFold;
  public  ActionListener check, call, bet, fold;

  public GUI() {
      
    JPanel btnPanel = new JPanel(new FlowLayout());

    btnCheck = new JButton("Check");
    btnPanel.add(btnCheck);
   
    btnCall = new JButton("Call");
    btnPanel.add(btnCall);

    btnBet = new JButton("Bet");
    btnPanel.add(btnBet);

    btnFold = new JButton("Fold");
    btnPanel.add(btnFold);
      
    coloraBottoni();

    check = new ActionListener() {                                                      /* operazione bottone check*/
      public void actionPerformed(ActionEvent evt) {

        if (ServerRMI.attivaBottoni == true && (ServerRMI.toCall == 0 || ServerRMI.allIn)){ //se e' il mio turno e o sono in allin o non devo aggiungere niente per poter vedere 

          ServerRMI.attivaBottoni = false;

          if (ServerRMI.allIn == false){
            canvas.varStato = Color.RED;
            ServerRMI.statoGiocatore = "Turno Avversario";
          }

          if (ServerRMI.MyConnectionID == ServerRMI.ultimoGiocatore){

            int utAttivi = ServerRMI.Ut.contaUtentiAttivi();
            int utAllin = ServerRMI.Ut.contaUtentiAllin();

            if ((ServerRMI.nUtentiAttivi == utAllin || ServerRMI.nUtentiAttivi == utAllin+1) ) {
            try{
              TexasGame ClientRMI = (TexasGame)
              java.rmi.Naming.lookup("rmi://"+ServerRMI.MyIp +"/Game");
              ClientRMI.mostraC(0);
              }
              catch(Exception e) { 

              } 
            }

            else{
              try{
                coloraBottoni();
  		          int i = ServerRMI.IdGrandeBuioCorrente % ServerRMI.utentiTavolo;
  		          while(ServerRMI.UtentiAttivi.get(i) == null) i = (i+1) % ServerRMI.utentiTavolo; 
                TexasGame ClientRMI = (TexasGame)
                java.rmi.Naming.lookup("rmi://"+ServerRMI.UtentiAttivi.get(i) +"/Game");

                if(ServerRMI.statoMano == 0) ClientRMI.flop(1);
            
                else if(ServerRMI.statoMano == 1) ClientRMI.turn(1);
            
                else if(ServerRMI.statoMano == 2) ClientRMI.river(1);
                        
                else  ClientRMI.mostraC(0);
              }

              catch(Exception e) { 
    
              } 
            }
          }

          else{
            try{
          		int i = ServerRMI.MyConnectionID % ServerRMI.utentiTavolo;
          		while(ServerRMI.UtentiAttivi.get(i) == null) i = (i+1) % ServerRMI.utentiTavolo; 
              TexasGame ClientRMI = (TexasGame)
              java.rmi.Naming.lookup("rmi://"+ServerRMI.UtentiAttivi.get(i) +"/Game");
              ClientRMI.gioca();
              }

            catch(Exception e) { 

              } 

            }
             coloraBottoni();
        }     
      }
    };

    btnCheck.addActionListener(check);




    call = new ActionListener() {                                                      /* operazione bottone call*/
       public void actionPerformed(ActionEvent evt) {
        if (ServerRMI.attivaBottoni == true && ServerRMI.allIn == false && ServerRMI.toCall > 0){        //se e' il mio turno e non sono in allin e devo aggiungere qualcosa per poter vedere

          ServerRMI.attivaBottoni = false;
          canvas.varStato = Color.RED;
          ServerRMI.statoGiocatore = "Turno Avversario";

          try{
  	
            TexasGame ClientRMI = (TexasGame)
            java.rmi.Naming.lookup("rmi://"+ServerRMI.MyIp +"/Game");
            ClientRMI.punta(ServerRMI.toCall, ServerRMI.MyConnectionID);
            }

          catch(Exception e) { 
   
          }          

          if (ServerRMI.MyConnectionID == ServerRMI.ultimoGiocatore){
            int utAttivi = ServerRMI.Ut.contaUtentiAttivi();
            int utAllin = ServerRMI.Ut.contaUtentiAllin();

            if ((ServerRMI.nUtentiAttivi == utAllin || ServerRMI.nUtentiAttivi == utAllin+1)) {
              try{
                TexasGame ClientRMI = (TexasGame)
                java.rmi.Naming.lookup("rmi://"+ServerRMI.MyIp +"/Game");
                ClientRMI.mostraC(0);
              }

              catch(Exception e) { 
      
              }     
            }

            else{
              try{
      	       	int i = ServerRMI.IdGrandeBuioCorrente % ServerRMI.utentiTavolo;
      		      while(ServerRMI.UtentiAttivi.get(i) == null) i = (i+1) % ServerRMI.utentiTavolo; 
                TexasGame ClientRMI = (TexasGame)
                java.rmi.Naming.lookup("rmi://"+ServerRMI.UtentiAttivi.get(i) +"/Game");
                if(ServerRMI.statoMano == 0) ClientRMI.flop(1);
                else if(ServerRMI.statoMano == 1) ClientRMI.turn(1);
                else if(ServerRMI.statoMano == 2) ClientRMI.river(1);
                else ClientRMI.mostraC(0);
              }

              catch(Exception e) { 
     
              } 
            }
          }
          coloraBottoni();
        }  
      }
    };
    btnCall.addActionListener(call);
     



    bet = new ActionListener() {                            /*operazione bottone bet */                    
       public void actionPerformed(ActionEvent evt) {
        if (ServerRMI.attivaBottoni && !ServerRMI.allIn){       // se e' il mio turno e non sono in allin


          JFrame frame = new JFrame("Scegli quanto puntare");
  		    int puntataMin = ServerRMI.toCall +1;
          int puntata =  Integer.parseInt(JOptionPane.showInputDialog(frame, "min: "+ puntataMin + ", max: " + ServerRMI.stack[ServerRMI.MyConnectionID -1]));
          ServerRMI.attivaBottoni= false;
          canvas.varStato = Color.RED;
          ServerRMI.statoGiocatore = "Turno Avversario";
          
          if (puntata >= ServerRMI.stack[ServerRMI.MyConnectionID -1]){
            puntata = ServerRMI.stack[ServerRMI.MyConnectionID -1];

          }

          else if (puntata < ServerRMI.toCall) puntata = ServerRMI.toCall +1;

          try{

            TexasGame ClientRMI = (TexasGame)
            java.rmi.Naming.lookup("rmi://"+ServerRMI.MyIp +"/Game");
            ClientRMI.ImpostaUltimoGiocatore(ServerRMI.MyConnectionID);
          }

          catch(Exception e) { 
     
          } 

          try{

            TexasGame ClientRMI = (TexasGame)
            java.rmi.Naming.lookup("rmi://"+ServerRMI.MyIp +"/Game");
            ClientRMI.punta(puntata, ServerRMI.MyConnectionID);
          }

          catch(Exception e) { 
    
          } 

          if (ServerRMI.MyConnectionID == ServerRMI.IdGrandeBuio){

            try{
           	  int i = ServerRMI.MyConnectionID % ServerRMI.utentiTavolo;
              while(ServerRMI.UtentiAttivi.get(i) == null) i = (i+1) % ServerRMI.utentiTavolo; 
              TexasGame ClientRMI = (TexasGame)
              java.rmi.Naming.lookup("rmi://"+ServerRMI.UtentiAttivi.get(i) +"/Game");
              ClientRMI.gioca();
              }

            catch(Exception e) { 
      
            } 

          }
        
        coloraBottoni();
        }          
      }
    };

    btnBet.addActionListener(bet);




    fold = new ActionListener() {                                                      /* operazione bottone fold*/
       public void actionPerformed(ActionEvent evt) {
        int z = ServerRMI.Ut.contaUtentiAttivi();
        int x = ServerRMI.Ut.contaUtentiAllin();
        if (ServerRMI.attivaBottoni == true && ServerRMI.allIn == false && !(ServerRMI.toCall == 0 && (ServerRMI.nUtentiAttivi-x) < 2)){  // se e' il mio turno e non sono in allin e se non sono l'unico giocatore non in allin in gioco e non devo aggiungere niente per vedere

          ServerRMI.attivaBottoni= false;
          canvas.varStato = Color.RED;
          ServerRMI.statoGiocatore = "Hai fatto Fold";
          
          canvas.foldGiocatori.set(ServerRMI.MyConnectionID-1, true);       

          if ((ServerRMI.nUtentiAttivi-1) > 1){
            try{
              TexasGame ClientRMI = (TexasGame)
              java.rmi.Naming.lookup("rmi://"+ServerRMI.MyIp +"/Game");
              if (ServerRMI.IdGrandeBuioCorrente == ServerRMI.MyConnectionID) ClientRMI.foldGiocatore(ServerRMI.MyConnectionID, 1);
              else ClientRMI.foldGiocatore(ServerRMI.MyConnectionID, 0);    
            }

            catch(Exception e) { 
    
            }

            if (ServerRMI.MyConnectionID == ServerRMI.ultimoGiocatore){

              try{
              	int i = ServerRMI.IdGrandeBuioCorrente % ServerRMI.utentiTavolo;
              	while(ServerRMI.UtentiAttivi.get(i) == null) i = (i+1) % ServerRMI.utentiTavolo; 
                TexasGame ClientRMI = (TexasGame)
                java.rmi.Naming.lookup("rmi://"+ServerRMI.UtentiAttivi.get(i) +"/Game");

                int utAttivi = ServerRMI.Ut.contaUtentiAttivi();
                int utAllin = ServerRMI.Ut.contaUtentiAllin();

                if ((ServerRMI.nUtentiAttivi == utAllin || ServerRMI.nUtentiAttivi == utAllin+1) ) {
                  ClientRMI.mostraC(0);
                }

                else{
                  if(ServerRMI.statoMano == 0)  ClientRMI.flop(1);
                  else if(ServerRMI.statoMano == 1) ClientRMI.turn(1);       
                  else if(ServerRMI.statoMano == 2) ClientRMI.river(1);         
                  else ClientRMI.mostraC(0);
                }
              }
              catch(Exception e) { 
    
              } 
            }

            else{
              try{
            		int i = ServerRMI.MyConnectionID % ServerRMI.utentiTavolo;
            		while(ServerRMI.UtentiAttivi.get(i) == null) i = (i+1) % ServerRMI.utentiTavolo; 
                TexasGame ClientRMI = (TexasGame)
                java.rmi.Naming.lookup("rmi://"+ServerRMI.UtentiAttivi.get(i) +"/Game");

                ClientRMI.gioca();
               }
              catch(Exception e) { 
    
              } 
            }
          }

          else{
            try{
              int i = ServerRMI.MyConnectionID % ServerRMI.utentiTavolo;
              while(ServerRMI.UtentiAttivi.get(i) == null) i = (i+1) % ServerRMI.utentiTavolo; 
              TexasGame ClientRMI = (TexasGame)
              java.rmi.Naming.lookup("rmi://"+ServerRMI.IpGiocatori.get(ServerRMI.IdGrandeBuio % ServerRMI.utentiTavolo) +"/Game");
    	        ServerRMI.vincitori.add(i+1);
              ClientRMI.terminaMano(ServerRMI.vincitori, ServerRMI.CarteValutazione);
            }

            catch(Exception e) { 
      
              }                  
            }
          coloraBottoni();
        }     
      } 
    };

    btnFold.addActionListener(fold);




    canvas = new TavoloPoker();
    canvas.addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent componentEvent) {
            canvas.setCoord();
  	    canvas.resetCoord();
  	    canvas.paintImmediately(canvas.getBounds());
      }
    });


    canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

    
    Container cp = getContentPane();
    cp.setLayout(new BorderLayout());
    cp.add(canvas, BorderLayout.CENTER);
    cp.add(btnPanel, BorderLayout.SOUTH);


    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Poker Stars");
    pack();          
    setVisible(true); 


  }


TimerTask update = new TimerTask(){                          /* funzione che aggiorna la grafica */
    @Override
    public void run() {
      canvas.paintImmediately(canvas.getBounds());
	if (canvas.Animation == true){								/* funzione per le animazioni */
					for (int i = 0; i<ServerRMI.utentiTavolo; i++){
						  if (canvas.syC[i]<=canvas.dyp){
						      if (canvas.yC[i]< canvas.dyp) canvas.yC[i]+=(canvas.dyp-canvas.syC[i])/(5+(8-ServerRMI.utentiTavolo));
							else {
							canvas.Animation=false;
							canvas.resettaVal();
							}
						  }else if (canvas.syC[i]>canvas.dyp){
						      if (canvas.yC[i] > canvas.dyp) canvas.yC[i]-=(canvas.syC[i]-canvas.dyp)/(5+(8-ServerRMI.utentiTavolo));
							else {
							canvas.Animation=false;
							canvas.resettaVal();
							}
						  }
						  if (canvas.sxC[i]<=canvas.dxp){
						    if (canvas.xC[i]<canvas.dxp) canvas.xC[i]+=(canvas.dxp-canvas.sxC[i])/(5+(8-ServerRMI.utentiTavolo));
							else {
							canvas.Animation=false;
							canvas.resettaVal();
							}
						  }else if (canvas.sxC[i]>canvas.dxp){
						    if (canvas.xC[i]>canvas.dxp) canvas.xC[i]-=(canvas.sxC[i]-canvas.dxp)/(5+(8-ServerRMI.utentiTavolo));
							else {
							canvas.Animation=false;
							canvas.resettaVal();
							}
						  }
						}
				}

  }
};



  public void coloraBottoni(){
	System.out.println("attivaBottoni: "+ServerRMI.attivaBottoni);

    if (ServerRMI.attivaBottoni == false){
      btnCheck.setBackground(Color.RED);
      btnCall.setBackground(Color.RED);
      btnBet.setBackground(Color.RED);
      btnFold.setBackground(Color.RED);
      btnCheck.setEnabled(false);
      btnCall.setEnabled(false);
      btnBet.setEnabled(false);
      btnFold.setEnabled(false);
    } 

    else{
      int z = ServerRMI.Ut.contaUtentiAttivi();
      int x = ServerRMI.Ut.contaUtentiAllin();

      if (ServerRMI.toCall == 0 || ServerRMI.allIn){
        btnCheck.setBackground(Color.GREEN);
        btnCheck.setEnabled(true); 
      }
      else{
        btnCheck.setBackground(Color.RED);
        btnCheck.setEnabled(false);
      }
      if (ServerRMI.allIn == false && ServerRMI.toCall > 0){
        btnCall.setBackground(Color.GREEN);
        btnCall.setEnabled(true); 
      }
      else{
       btnCall.setBackground(Color.RED);
       btnCall.setEnabled(false);
      }

      if (!ServerRMI.allIn){
        btnBet.setBackground(Color.GREEN);
        btnBet.setEnabled(true); 
      }
      else{
        btnBet.setBackground(Color.RED);
        btnBet.setEnabled(false);
      }

      if (!ServerRMI.allIn && !(ServerRMI.toCall == 0 && (ServerRMI.nUtentiAttivi-x) < 2)){
        btnFold.setBackground(Color.GREEN); 
        btnFold.setEnabled(true); 
      }
      else{
        btnFold.setBackground(Color.RED);
        btnFold.setEnabled(false);
      }
    }
  }



  public void DisegnaCarte(String carta){

    canvas.i = canvas.i+1;

    if (canvas.i ==1){
        canvas.cartaPescata1 = true;
        canvas.pescata1 = carta;
    } 
    if (canvas.i ==2){
        canvas.cartaPescata2 = true;
        canvas.pescata2 = carta;
    }

    if(canvas.i == 3)canvas.flop1 = carta;
    if(canvas.i == 4)canvas.flop2 = carta;
    if (canvas.i==5){
      ServerRMI.statoMano = 1;
      canvas.flop3 = carta;
      canvas.flop = true;
      canvas.Animation = true;
    }

    if(canvas.i == 6){
      ServerRMI.statoMano = 2;
      canvas.cartaTurn = carta;
      canvas.turn = true;
	canvas.Animation=true;
    }

    if(canvas.i == 7){
      ServerRMI.statoMano = 3;
      canvas.cartaRiver = carta;
      canvas.river = true;
	 canvas.Animation=true;
    }
    ServerRMI.ultimoGiocatore = ServerRMI.IdGrandeBuioCorrente;
  }


  public void mostraPunteggi(  ArrayList<ArrayList<String>> ris){
    for(ArrayList <String> s : ris){
        canvas.risultati.set(Integer.parseInt(s.get(0))-1, s.get(1));
    }
  }

  public void DisegnaPuntata(){
    canvas.Piatto = true;
  }


  public void reinizializzaTavolo(){
    canvas.i = 0;
    canvas.cartaPescata1 = false;
    canvas.cartaPescata2 = false;
    canvas.Piatto = false;
    canvas.flop = false;
    canvas.turn = false;
    canvas.river = false;
    canvas.flop1 = "Dietro";
    canvas.flop2 = "Dietro";
    canvas.flop3 = "Dietro";
    canvas.cartaTurn = "Dietro";
    canvas.cartaRiver = "Dietro";
    canvas.setCoord();
    canvas.resetCoord();
    canvas.resettaVal();
    

    ArrayList <String> carte = new ArrayList <String>();
    carte.add("Dietro");
    carte.add("Dietro");
    canvas.carteGiocatori.clear();
    canvas.foldGiocatori.clear();
    canvas.risultati.clear();

    while(canvas.carteGiocatori.size()<ServerRMI.utentiTavolo){
      canvas.carteGiocatori.add(carte);
      canvas.foldGiocatori.add(false);
      canvas.risultati.add(null);
    }
  }



  public void mostraCarteGiocatore(ArrayList <Integer> Carte, int id){

    int cartaEstratta = 0;
    ArrayList<String> carteDaDisegnare = new ArrayList <String>();


    for (int i=1; i<3; i++){

      cartaEstratta = Carte.get(i);

      if (cartaEstratta >39 ) carteDaDisegnare.add(Integer.toString(cartaEstratta-39) + "P");
      else if (cartaEstratta > 26) carteDaDisegnare.add(Integer.toString(cartaEstratta-26) + "F");
      else if (cartaEstratta > 13) carteDaDisegnare.add(Integer.toString(cartaEstratta-13) + "Q");
      else carteDaDisegnare.add(Integer.toString(cartaEstratta) + "C");
    }

    canvas.carteGiocatori.set((Carte.get(0)-1), carteDaDisegnare);
  } 
}
