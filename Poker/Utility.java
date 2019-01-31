import java.rmi.Naming;
import java.util.*;
import java.rmi.registry.*;
import java.util.concurrent.*;
import java.util.Timer;
import java.util.TimerTask;


public class Utility {

	public static String IpGiocatoreSeguente;
	public static int MyOldConnectionID;


	/*-----------------------------------------FAULT TOLLERANCE-----------------------------------------*/

	TimerTask controllaGiocatoreSucessivo = new TimerTask(){													/* funzione che pinga il giocatore sucessivo ogni 0.5 secondi e se e uscito dal gioco lo toglie e avvisa gli altri giocatori */
    @Override
    public void run() {
	    try{
	    	IpGiocatoreSeguente = ServerRMI.IpGiocatori.get(ServerRMI.MyConnectionID % ServerRMI.utentiTavolo);
			TexasGame ClientRMI = (TexasGame)
			java.rmi.Naming.lookup("rmi://"+IpGiocatoreSeguente+"/Game"); 
			ClientRMI.sonoVivo();
			}

		catch(Exception e) {

			if (ServerRMI.Debug) System.out.println("il giocatore "+ (( ServerRMI.MyConnectionID % ServerRMI.utentiTavolo)+1) + " e uscito dal gioco");
			if ( ServerRMI.Sconfitto) System.exit(1);
			if (ServerRMI.GiocatoreCorrente == ServerRMI.MyConnectionID +1){
				System.out.println(" reimpostaStato");
				for (String x : ServerRMI.IpGiocatori){ //	for (int i =0; i < ServerRMI.utentiTavolo; i++){//
					if (!x.equals(ServerRMI.MyIp)){	//if(!ServerRMI.IpGiocatori.get(i).equals(ServerRMI.MyIp)){ //
						try{
							TexasGame ClientRMI = (TexasGame) 
							java.rmi.Naming.lookup("rmi://"+ x +"/Game"); //java.rmi.Naming.lookup("rmi://"+ServerRMI.IpGiocatori.get((ServerRMI.MyConnectionID+i) % (ServerRMI.utentiTavolo-1))+"/Game"); //
							ClientRMI.reimpostaStato(ServerRMI.GiocatoreCorrente, ServerRMI.OperazioneEseguita, ServerRMI.stack, ServerRMI.puntate, ServerRMI.puntateMano, ServerRMI.piatti, ServerRMI.AllinGiocatori, ServerRMI.ultimoGiocatore, ServerRMI.piatto, ServerRMI.Gui.canvas.flop1, ServerRMI.Gui.canvas.flop2, ServerRMI.Gui.canvas.flop3, ServerRMI.Gui.canvas.cartaTurn, ServerRMI.Gui.canvas.cartaRiver, ServerRMI.Gui.canvas.carteGiocatori);

						}

						catch(Exception exc) { 
							System.out.println("eccezione reimpostaStato");
  
					 	}
					}
				}
			}

			MyOldConnectionID = ServerRMI.MyConnectionID;
			int OldutentiTavolo = ServerRMI.utentiTavolo;
			try{
				TexasGame ClientRMI = (TexasGame) 
				java.rmi.Naming.lookup("rmi://"+ServerRMI.MyIp +"/Game");
				ClientRMI.terminaPartita( ((ServerRMI.MyConnectionID % ServerRMI.utentiTavolo) +1));
			}
			catch(Exception exce){
				System.out.println("eccezione terminaPartita su di me");

			}
			//boolean tryComplete = false;
			//while(tryComplete == false){

				for (String x : ServerRMI.IpGiocatori){ //for (int i =0; i < ServerRMI.utentiTavolo; i++){
					if (!x.equals(ServerRMI.MyIp)){		//if(!ServerRMI.IpGiocatori.get(i).equals(ServerRMI.MyIp)){ //
						try{
							TexasGame ClientRMI = (TexasGame) 
							java.rmi.Naming.lookup("rmi://"+ x +"/Game"); //java.rmi.Naming.lookup("rmi://"+ServerRMI.IpGiocatori.get((ServerRMI.MyConnectionID+i)% (ServerRMI.utentiTavolo-1))+"/Game"); //
							ClientRMI.terminaPartita((MyOldConnectionID % OldutentiTavolo) +1);

						}
				
						catch(Exception exc) { 
							System.out.println("eccezione terminaPartita sugli altri"+ ServerRMI.MyConnectionID + "  "+ ServerRMI.utentiTavolo);
							exc.printStackTrace();
					 	}
						//tryComplete = true;
					//}
				}
			}
		
			int x = contaUtentiAttivi();				/*caso in cui gli unici utenti in gioco sono in fold*/

			System.out.println("!!!!!!!!" +ServerRMI.nUtentiAttivi);

			if (ServerRMI.nUtentiAttivi == 0){
				ServerRMI.Gui.canvas.Animation = false;
				try{
					TexasGame ClientRMI = (TexasGame) 
					java.rmi.Naming.lookup("rmi://"+ServerRMI.IpGiocatori.get(ServerRMI.ultimoGiocatore %ServerRMI.utentiTavolo) +"/Game");
					ClientRMI.terminaMano(ServerRMI.vincitori, ServerRMI.CarteValutazione);
				}
				catch(Exception e0){

				}
				//System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ"+ ServerRMI.IpGiocatori.get(ServerRMI.ultimoGiocatore %ServerRMI.utentiTavolo));

			}



			if(ServerRMI.nUtentiAttivi == 1){
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

			else{
				
		//int utAttivi = contaUtentiAttivi();
              /*	int utAllin = contaUtentiAllin();

             	if ((ServerRMI.nUtentiAttivi == utAllin || ServerRMI.nUtentiAttivi == utAllin+1)) {
             		try{
						int i = ServerRMI.MyConnectionID % ServerRMI.utentiTavolo;
		            	while (ServerRMI.UtentiAttivi.get(i) == null) i = (i+1) % ServerRMI.utentiTavolo; 
		              	TexasGame ClientRMI = (TexasGame)
		              	java.rmi.Naming.lookup("rmi://" + ServerRMI.UtentiAttivi.get(i)  +"/Game");
						ClientRMI.mostraC(0);
					}
					catch(Exception e1) {

					}
              	}*/

					



				System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+ServerRMI.GiocatoreCorrente+ ServerRMI.MyConnectionID+((ServerRMI.MyConnectionID +1) % ServerRMI.utentiTavolo)+ ServerRMI.OperazioneEseguita);


				if ( (ServerRMI.GiocatoreCorrente == ServerRMI.MyConnectionID+1 || ServerRMI.GiocatoreCorrente == ((ServerRMI.MyConnectionID +1) % ServerRMI.utentiTavolo) || (ServerRMI.GiocatoreCorrente == ServerRMI.MyConnectionID && ServerRMI.OperazioneEseguita ==0) ||/* (ServerRMI.MyConnectionID == ServerRMI.GiocatoreCorrente && ServerRMI.attivaBottoni == false && ServerRMI.OperazioneEseguita <7 && ServerRMI.PossoGiocare == true)||*/ ServerRMI.GiocatoreCorrente == 0) && ServerRMI.nUtentiAttivi>1){			
					
						
					System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+ ServerRMI.nUtentiAttivi);

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
			}
		}
  	};

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
