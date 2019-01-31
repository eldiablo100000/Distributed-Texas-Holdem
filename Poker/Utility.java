import java.rmi.Naming;
import java.util.*;
import java.rmi.registry.*;
import java.util.concurrent.*;
import java.util.Timer;
import java.util.TimerTask;
import java.net.Socket;
import java.io.IOException;
import java.lang.reflect.Method;


public class Utility {

	public static String IpGiocatoreSeguente;
	public static int MyOldConnectionID;
	public static boolean risPing = false;

	/*-----------------------------------------FAULT TOLLERANCE-----------------------------------------*/


	public static boolean ping(String server,int port) {

		/*System.out.println("ping: " +server + ", porta: " + port);				//con i socket 
	    try (Socket ignored = new Socket(server, port)) {
	       	System.out.println("up");
		 return false;
	    } 
	    catch (IOException ignored) {
	       	System.out.println("down");
        	return true;
    	}*/

    	boolean ris;															// con rmi
    	try{
			TexasGame ClientRMI = (TexasGame) 
			java.rmi.Naming.lookup("rmi://"+server +"/Game");
			Class<?> c = Class.forName("TexasGame");
			Method method =c.getDeclaredMethod("sonoVivo"+ServerRMI.MyConnectionID);
			method.invoke(ClientRMI);
			ris = false;
		}
		catch(Exception exce){
			ris = true;
		}

		return ris;
	}




	TimerTask controllaGiocatoreSucessivo = new TimerTask(){													/* funzione che pinga tutti i giocatori e se uno e uscito dal gioco lo rimuove*/
    @Override
    public void run() {

    	for(int i =0; i < ServerRMI.utentiTavolo; i++){
    		risPing = ping(ServerRMI.IpGiocatori.get(i),1099);
    		if(risPing == true) faultTolerance(i+1);
    		}
    	}
  	};
  	





  	public void faultTolerance(int id){

			if (ServerRMI.Debug) System.out.println("il giocatore "+ (( ServerRMI.MyConnectionID % ServerRMI.utentiTavolo)+1) + " e uscito dal gioco");
			

			MyOldConnectionID = ServerRMI.MyConnectionID;
			int OldutentiTavolo = ServerRMI.utentiTavolo;
			try{
				TexasGame ClientRMI = (TexasGame) 
				java.rmi.Naming.lookup("rmi://"+ServerRMI.MyIp +"/Game");
				ClientRMI.terminaPartita(id);
			}
			catch(Exception exce){

			}
			




				if ( ServerRMI.GiocatoreCorrente == ServerRMI.MyConnectionID+1 || ServerRMI.GiocatoreCorrente == ((ServerRMI.MyConnectionID +1) % ServerRMI.utentiTavolo)){

					int x = contaUtentiAttivi();							

					if (ServerRMI.nUtentiAttivi == 0){								/*caso in cui gli unici utenti in gioco sono in fold*/
						ServerRMI.Gui.canvas.Animation = false;
						try{
							TexasGame ClientRMI = (TexasGame) 
							java.rmi.Naming.lookup("rmi://"+ServerRMI.IpGiocatori.get(ServerRMI.ultimoGiocatore %ServerRMI.utentiTavolo) +"/Game");
							ClientRMI.terminaMano(ServerRMI.vincitori, ServerRMI.CarteValutazione);
						}
						catch(Exception e0){

						}

					}



					if(ServerRMI.nUtentiAttivi == 1){								/* caso un solo giocatore rimasto attivo */
						if (ServerRMI.Debug) System.out.println("UN SOLO UTENTE ATTIVO");

						try{
							int i = ServerRMI.MyConnectionID % ServerRMI.utentiTavolo;
			            	while (ServerRMI.UtentiAttivi.get(i) == null) i = (i+1) % ServerRMI.utentiTavolo; 
			              	TexasGame ClientRMI = (TexasGame)
			              	java.rmi.Naming.lookup("rmi://" + ServerRMI.UtentiAttivi.get(i)  +"/Game");
			              	ServerRMI.vincitori.add(i+1);
							ClientRMI.terminaMano(ServerRMI.vincitori, ServerRMI.CarteValutazione);
						}
						catch(Exception e1) {

						} 
					}
					

					if (ServerRMI.GiocatoreCorrente > ServerRMI.utentiTavolo) ServerRMI.GiocatoreCorrente = ServerRMI.utentiTavolo;

					switch (ServerRMI.OperazioneEseguita){

						case 0: if (ServerRMI.Debug) System.out.println("Case 0");   /*crash prima che venga eseguita la inizia() */
								try{
									TexasGame ClientRMI = (TexasGame) 
									java.rmi.Naming.lookup("rmi://"+ServerRMI.MyIp +"/Game");
									ClientRMI.inizia();
								}
								catch(Exception e0){

								}
								break;

				
						case 1: if (ServerRMI.Debug) System.out.println("Case 1"); /* crash durante la pescaCarte */

								try{
									TexasGame ClientRMI = (TexasGame)
									java.rmi.Naming.lookup("rmi://"+ServerRMI.UtentiAttivi.get(ServerRMI.MyConnectionID % ServerRMI.utentiTavolo)+"/Game");
									if (! (ServerRMI.MyConnectionID == ServerRMI.IdGrandeBuio)) ClientRMI.pescaCarte(ServerRMI.Carte, ServerRMI.CarteComuni); 
									else ClientRMI.iniziaMano();
								}
								catch(Exception e1) {

								} 
								break;


						case 2: if (ServerRMI.Debug) System.out.println("Case 2");			/*crash durante la iniziaMano */

								try{
									int i = ServerRMI.MyConnectionID % ServerRMI.utentiTavolo;
									while(ServerRMI.UtentiAttivi.get(i) == null) i = (i+1) % ServerRMI.utentiTavolo; 
									TexasGame ClientRMI = (TexasGame)
									java.rmi.Naming.lookup("rmi://"+ ServerRMI.UtentiAttivi.get(i)+"/Game");
									if (ServerRMI.MyConnectionID == ServerRMI.IdGrandeBuio) ClientRMI.gioca();
									else ClientRMI.iniziaMano();
									 	}

							 	catch(Exception e2) { 

							 		}
								break;


						case 3: if (ServerRMI.Debug) System.out.println("Case 3");			  /*crash prima della flop() */
								System.out.println(ServerRMI.GiocatoreCorrente+ " " + ServerRMI.MyConnectionID + " " + ServerRMI.OldGrandeBuio );
								int j3 = (ServerRMI.GiocatoreCorrente -1) % ServerRMI.utentiTavolo;
								while(ServerRMI.UtentiAttivi.get(j3) == null) j3 = (j3+1) % ServerRMI.utentiTavolo;

								if (ServerRMI.GiocatoreCorrente == ServerRMI.MyConnectionID || (ServerRMI.GiocatoreCorrente == 1 && ServerRMI.MyConnectionID == ServerRMI.utentiTavolo && ServerRMI.OldGrandeBuio == 1) ){

								 	int l = ServerRMI.MyConnectionID % ServerRMI.utentiTavolo;
									while(ServerRMI.UtentiAttivi.get(l) == null) l = (l+1) % ServerRMI.utentiTavolo;
									try{ 
										TexasGame ClientRMI = (TexasGame)
										java.rmi.Naming.lookup("rmi://"+ ServerRMI.UtentiAttivi.get(l)+"/Game");
										if (ServerRMI.MyConnectionID == ServerRMI.ultimoGiocatore) ClientRMI.flop(1);
										else ClientRMI.gioca();
									}
									catch (Exception e3){

									}
								}

								else{
									try{
									 	TexasGame ClientRMI = (TexasGame)
										java.rmi.Naming.lookup("rmi://"+ ServerRMI.UtentiAttivi.get(j3)+"/Game");
										ClientRMI.gioca();
									}
									catch(Exception e3){

									}
								}			
								break;


						case 4: if (ServerRMI.Debug) System.out.println("Case 4");			/* crash dopo la flop() e prima della turn() */
								int j4 = (ServerRMI.GiocatoreCorrente -1) % ServerRMI.utentiTavolo;
								while(ServerRMI.UtentiAttivi.get(j4) == null) j4 = (j4+1) % ServerRMI.utentiTavolo;

								if (ServerRMI.GiocatoreCorrente == ServerRMI.MyConnectionID || (ServerRMI.GiocatoreCorrente == 1 && ServerRMI.MyConnectionID == ServerRMI.utentiTavolo && ServerRMI.OldGrandeBuio == 1) ){
								 	int l = ServerRMI.MyConnectionID % ServerRMI.utentiTavolo;
									while(ServerRMI.UtentiAttivi.get(l) == null) l = (l+1) % ServerRMI.utentiTavolo;
									try{ 
										TexasGame ClientRMI = (TexasGame)
										java.rmi.Naming.lookup("rmi://"+ ServerRMI.UtentiAttivi.get(l)+"/Game");
										if (ServerRMI.MyConnectionID == ServerRMI.ultimoGiocatore ) ClientRMI.turn(1);
										else ClientRMI.gioca();
									}
									catch (Exception e4){

									}
								}

								else{
									try{
									 	TexasGame ClientRMI = (TexasGame)
										java.rmi.Naming.lookup("rmi://"+ ServerRMI.UtentiAttivi.get(j4)+"/Game");
										ClientRMI.gioca();
									}
									catch(Exception e4){

									}
								}		
								break;


						case 5: if (ServerRMI.Debug) System.out.println("Case 5");    		/* crash dopo la turn() ma prima della river() */
								int j5 = (ServerRMI.GiocatoreCorrente -1) % ServerRMI.utentiTavolo;
								while(ServerRMI.UtentiAttivi.get(j5) == null) j5 = (j5+1) % ServerRMI.utentiTavolo;

								if (ServerRMI.GiocatoreCorrente == ServerRMI.MyConnectionID || (ServerRMI.GiocatoreCorrente == 1 && ServerRMI.MyConnectionID == ServerRMI.utentiTavolo && ServerRMI.OldGrandeBuio == 1)){
								 	int l = ServerRMI.MyConnectionID % ServerRMI.utentiTavolo;
									while(ServerRMI.UtentiAttivi.get(l) == null) l = (l+1) % ServerRMI.utentiTavolo;
									try{ 
										TexasGame ClientRMI = (TexasGame)
										java.rmi.Naming.lookup("rmi://"+ ServerRMI.UtentiAttivi.get(l)+"/Game");
										if (ServerRMI.MyConnectionID == ServerRMI.ultimoGiocatore ) ClientRMI.river(1);
										else ClientRMI.gioca();
									}
									catch (Exception e5){

									}
								}

								else{
									try{
									 	TexasGame ClientRMI = (TexasGame)
										java.rmi.Naming.lookup("rmi://"+ ServerRMI.UtentiAttivi.get(j5)+"/Game");
										ClientRMI.gioca();
									}
									catch(Exception e5){

									}
								}			
								break;


						case 6: if (ServerRMI.Debug) System.out.println("Case 6");				/*crash dopo la river() ma prima della valuta_punteggio() */
								int j6 = (ServerRMI.GiocatoreCorrente -1) % ServerRMI.utentiTavolo;
								while(ServerRMI.UtentiAttivi.get(j6) == null) j6 = (j6+1) % ServerRMI.utentiTavolo;

								if (ServerRMI.GiocatoreCorrente == ServerRMI.MyConnectionID || (ServerRMI.GiocatoreCorrente == 1 && ServerRMI.MyConnectionID == ServerRMI.utentiTavolo && ServerRMI.OldGrandeBuio == 1)){
								 	int l = ServerRMI.MyConnectionID % ServerRMI.utentiTavolo;
									while(ServerRMI.UtentiAttivi.get(l) == null) l = (l+1) % ServerRMI.utentiTavolo;
									try{ 
										TexasGame ClientRMI = (TexasGame)
										java.rmi.Naming.lookup("rmi://"+ ServerRMI.UtentiAttivi.get(l)+"/Game");
										if (ServerRMI.MyConnectionID == ServerRMI.ultimoGiocatore ) ClientRMI.valuta_punteggio(ServerRMI.CarteValutazione);
										else ClientRMI.gioca();
									}
									catch (Exception e6){

									}
								}

								else{
									try{
									 	TexasGame ClientRMI = (TexasGame)
										java.rmi.Naming.lookup("rmi://"+ ServerRMI.UtentiAttivi.get(j6)+"/Game");
										ClientRMI.gioca();
									}
									catch(Exception e6){

									}
								}			
								break;


						case 7: if (ServerRMI.Debug) System.out.println("Case 7");          /* crash durante la valuta_punteggio()*/
								ServerRMI.CarteValutazione.clear();
								ServerRMI.k = 0;
								try{
									TexasGame ClientRMI = (TexasGame) 
									java.rmi.Naming.lookup("rmi://"+ServerRMI.MyIp +"/Game");
									ClientRMI.valuta_punteggio(ServerRMI.CarteValutazione);
								}
								catch(Exception e7){

								}
								break;


						case 8: if (ServerRMI.Debug) System.out.println("Case 8" );  /* crash durante la terminaMano() */
								ServerRMI.CarteValutazione.clear();
								ServerRMI.k = 0;
								try{
									TexasGame ClientRMI = (TexasGame) 
									java.rmi.Naming.lookup("rmi://"+ServerRMI.MyIp +"/Game");
									ClientRMI.valuta_punteggio(ServerRMI.CarteValutazione);
								}
								catch(Exception e8){
	
								}
								break;


						case 9: if (ServerRMI.Debug) System.out.println("Case 9");		/* crash durante la reinizializzaPartita() */
								if (ServerRMI.grandeBuio) {
									try{
									TexasGame ClientRMI = (TexasGame) 
									java.rmi.Naming.lookup("rmi://"+ServerRMI.MyIp +"/Game");
									ClientRMI.inizia();
									}
									catch(Exception e9){

									}
								}

								else{
									try{
									TexasGame ClientRMI = (TexasGame) 
									java.rmi.Naming.lookup("rmi://"+ ServerRMI.IpGiocatori.get(ServerRMI.MyConnectionID % ServerRMI.utentiTavolo) +"/Game");
									ClientRMI.reinizializzaPartita();
									}

									catch(Exception e9) { 
						
									} 
								}
								break;
						}
					}
				}
			
		
/*--------------------------------------------------- FINE FAULT TOLLERANCE--------------------------------------------*/


	public void inizializza(){

		ServerRMI.stack = new int[ServerRMI.utentiTavolo];
		ServerRMI.puntate  = new int [ServerRMI.utentiTavolo];
		ServerRMI.AllinGiocatori = new int[ServerRMI.utentiTavolo];
		ServerRMI.piatti = new int[ServerRMI.utentiTavolo];
		ServerRMI.puntateMano = new int[ServerRMI.utentiTavolo];

		for (int i = 0; i < ServerRMI.utentiTavolo; i++) ServerRMI.stack[i]= 1000;
		for (int i = 0; i < ServerRMI.utentiTavolo; i++) ServerRMI.puntate[i] = 0;
		for (int i = 0; i < ServerRMI.utentiTavolo; i++) ServerRMI.puntateMano[i] = 0;
		for (int i = 0; i < ServerRMI.utentiTavolo; i++) ServerRMI.AllinGiocatori[i] = 0;
		for (int i = 0; i < ServerRMI.utentiTavolo; i++) ServerRMI.piatti[i] = 0;
	}



	public static int contaUtentiAttivi(){
	int count = 0;
	int last = 0;
	int i =0;
	for (String x: ServerRMI.UtentiAttivi){
		i = i+1;

		if( x != null){
			last = i;
			count = count +1;

		}   
	}
	ServerRMI.nUtentiAttivi = count;	
	return last;
	}

	public static int contaUtentiAllin(){
		int count = 0;
		for ( int x : ServerRMI.AllinGiocatori) if(x != 0)count = count +1;
		return count;
	}
}
