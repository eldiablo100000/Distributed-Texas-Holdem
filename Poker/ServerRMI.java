import java.awt.Color;
import java.rmi.Naming;
import java.util.Random;
import java.util.*;
import java.rmi.registry.*;
import java.util.concurrent.*;
import java.util.Timer;
import java.util.TimerTask;

// client poker texas holdem

public class ServerRMI
extends java.rmi.server.UnicastRemoteObject  
	implements TexasGame
	
{
	
	public static List <Integer> Carte = new ArrayList <Integer>();
	public static ArrayList <Integer> MieCarte = new ArrayList <Integer>();
	public static List <String> IpGiocatori = new ArrayList<String>();   // IL MIO IP È A MYCONNECTIONID -1
	public static List <String> UtentiAttivi = new ArrayList <String>();
	public static ArrayList <Integer> CarteComuni = new ArrayList <Integer>();
	public static ArrayList <ArrayList<Integer>> CarteValutazione = new ArrayList <ArrayList<Integer>>();
	public static ArrayList <Integer> vincitori = new ArrayList <Integer>();
	public static ArrayList <Integer> vincitoriAllin = new ArrayList <Integer>();

	public static boolean dealer = false;
	public static boolean piccoloBuio = false;
	public static boolean grandeBuio = false;
	public static boolean attivaBottoni = false;
	public static boolean allIn = false;
	public static boolean giroBui = true;
	public static boolean Sconfitto = false;
	public static boolean Debug = false;
	public static boolean PossoGiocare = true;


	public static int MyConnectionID;
	public static int utentiTavolo;
	public static int IdGrandeBuio;
	public static int IdGrandeBuioCorrente;
	public static int OldGrandeBuio;
	public static int ultimoGiocatore;
	public static int nUtentiAttivi;
	public static int k = 0; 
	public static int piBuio = 20;
	public static int graBuio = 40;
	public static int turno = 0;
	public static int turniAumento = 10;
	public static int mia_puntata = 0;
	public static int toCall = 0;
	public static int piatto = 0;
	public static int piattoTavolo = 0;
	public static int statoMano = 0;
	public static int OperazioneEseguita = 0;
	public static int GiocatoreCorrente;

	public static int [] stack;
	public static int [] puntate;
	public static int [] puntateMano;
	public static int [] AllinGiocatori;
	public static int [] piatti;

	public static Random rand = new Random();

	public static String cartaDaDisegnare;
	public static String MyIp;
	public static String statoGiocatore = "Turno Avversario";
	
	
	public static GUI Gui = new GUI(); 
	public static Evaluate Ev = new Evaluate();
	public static Utility Ut = new Utility();


	Timer timer = new Timer();
	



/**------------------------------- costruttore ---------------------------------------**/
	public ServerRMI()
		throws java.rmi.RemoteException { 
		super(); 
		}


/*----------------------- ricevo gli ip e l'id dal server e inizializzo le variabili del client-------------------------------*/
	public void Ricezione(List <String> ips, int id){

		timer.schedule(Ut.controllaGiocatoreSucessivo, 100, 100);
		timer.schedule(Gui.update, 100, 100);
		IpGiocatori = ips;
		for(String x: ips) UtentiAttivi.add(x);
		MyConnectionID = id;
		MyIp = IpGiocatori.get(MyConnectionID-1);
		utentiTavolo = IpGiocatori.size();
		Ut.inizializza();
		Gui.canvas.setCoord();
		Gui.canvas.resetCoord();
		Debug = TexasHoldem.debug;
		
		if (IpGiocatori.size() > 2){	
			if (MyConnectionID == IpGiocatori.size()-2) dealer = true;
	    	if (MyConnectionID == IpGiocatori.size()-1) piccoloBuio = true;
	    }

	    else{
	    	if (MyConnectionID == 1) piccoloBuio = true;
	    	if (MyConnectionID == 2) dealer = true;
	    }

	    IdGrandeBuio = utentiTavolo;
	    ultimoGiocatore = IdGrandeBuio;
		IdGrandeBuioCorrente = IdGrandeBuio;

		if (MyConnectionID == utentiTavolo) {
			grandeBuio = true;
			inizia();
		}	
	}

	public void bloccaTimer(){
		timer.cancel();
		}
	/*---------------------------inizia partita */
	public void inizia(){						/* chiamo la pescaCarte del giocatore sucessivo (il primo dopo il grande buio )*/
		if (Debug) System.out.println("INIZIA");
		OperazioneEseguita = 0;
		MioTurno(MyConnectionID, OperazioneEseguita);
		

		Carte.clear();
		for(int i = 1; i <= 52 ; i++) Carte.add(i);

		int nsize = Carte.size();
		int index;
		int cartaEstratta;
		while (nsize -5 != Carte.size()){
			index = rand.nextInt(Carte.size());
			cartaEstratta = Carte.get(index);
			CarteComuni.add(cartaEstratta);
			Carte.remove(index);
			}

		try{
			TexasGame ClientRMI = (TexasGame)
			java.rmi.Naming.lookup("rmi://"+IpGiocatori.get(MyConnectionID % utentiTavolo)+"/Game"); 
			ClientRMI.pescaCarte(Carte, CarteComuni);
		
		}

		catch(Exception e) {

		}

	}




	/*------------------------ pesco le mie carte----------------------------------*/
	public void pescaCarte(List <Integer> carte, ArrayList <Integer> carteComuni){

		if (Debug) System.out.println("Pesca carte");
		if (Debug) System.out.println("Sono l'utente " + MyConnectionID + ", dealer: "+dealer+ ",piccoloBuio: " + piccoloBuio + ", grandeBuio: "+grandeBuio);

		OperazioneEseguita = 1;

		MioTurno(MyConnectionID, OperazioneEseguita);

		Carte = carte;
		UtentiAttivi.clear();
		for(String x : IpGiocatori) UtentiAttivi.add(x);
		CarteComuni = carteComuni;
		MieCarte.clear();
		MieCarte.add(MyConnectionID);

		int nCarte = Carte.size();
		int cartaEstratta = 0;
		int index;

		while (nCarte -2 != Carte.size()){

			index = rand.nextInt(Carte.size());

			cartaEstratta = Carte.get(index);
			MieCarte.add(cartaEstratta);
			Carte.remove(index);


			if (cartaEstratta >39 ) cartaDaDisegnare = Integer.toString(cartaEstratta-39) + "P";

			else if (cartaEstratta > 26) cartaDaDisegnare = Integer.toString(cartaEstratta-26) + "F";

			else if (cartaEstratta > 13) cartaDaDisegnare = Integer.toString(cartaEstratta-13) + "Q";

			else cartaDaDisegnare = Integer.toString(cartaEstratta) + "C";

			Gui.DisegnaCarte(cartaDaDisegnare);

		}


		try{					/*se sono il grande buio chiamo la iniziaMano del giocatore sucessivo, altrimenti la pesca carte */ 

			TexasGame ClientRMI = (TexasGame)
			java.rmi.Naming.lookup("rmi://"+UtentiAttivi.get(MyConnectionID % utentiTavolo)+"/Game");
			if (! (MyConnectionID == IdGrandeBuio)) ClientRMI.pescaCarte(Carte, CarteComuni); 
			else ClientRMI.iniziaMano();
		 	}


		catch(Exception e) {

			} 

	 	} //fine pescaCarte



	/*---------------------------- metto i bui e aggiorno i turni------------------------ */
	public void iniziaMano(){

		if (Debug) System.out.println("INIZIA MANO");
		OperazioneEseguita =2;
		MioTurno(MyConnectionID, OperazioneEseguita);

		turno = turno +1;
		if (turno == turniAumento ){
			piBuio = piBuio*2;
			graBuio= graBuio*2;
			turniAumento = turniAumento+turniAumento;
		}

		if (piccoloBuio) punta(piBuio, MyConnectionID);
		else if (grandeBuio) punta(graBuio, MyConnectionID);
		else { 
			try{
				OperazioneEseguita = 3;
				TexasGame ClientRMI = (TexasGame)
				java.rmi.Naming.lookup("rmi://"+UtentiAttivi.get(MyConnectionID % utentiTavolo)+"/Game");
				ClientRMI.iniziaMano();
			}

		 	catch(Exception e) { 
 
		 	} 

		}


		giroBui =false;

		if (MyConnectionID == IdGrandeBuio){
	 		try{
	 		OperazioneEseguita = 3;
			TexasGame ClientRMI = (TexasGame)
			java.rmi.Naming.lookup("rmi://"+UtentiAttivi.get(MyConnectionID % utentiTavolo)+"/Game");
			ClientRMI.gioca();
			 	}

		 	catch(Exception e) { 
 
		 	} 
		}
	}


	/*------------------eseguo una puntata e la notifico agli altri giocatori oppure la ricevo da un altro giocatore-------------------------*/

	public void punta(int bet, int id){
		if (Debug) System.out.println("PUNTA");
		Gui.DisegnaPuntata();

		if (id == MyConnectionID){

			if(bet >= stack[id-1]) {
				allIn = true;
				Gui.canvas.varStato = Color.RED;
				statoGiocatore = "Sei in AllIn";
				bet = stack[id-1];
				}

				mia_puntata = mia_puntata +bet;


			for (String x:IpGiocatori ){
				if ( !x.equals(MyIp) ){
					try{
						TexasGame ClientRMI = (TexasGame)
						java.rmi.Naming.lookup("rmi://"+ x +"/Game");
						if (Debug) System.out.println("invoca punta remota di: " + x + " MyConnectionID: " + MyConnectionID + "bet: "+bet+"\n");
						ClientRMI.punta(bet,id);
					}

				 	catch(Exception e) { 
 
				 	} 
				}
			 	
		 	}
		}

	 	piatto = piatto + bet;

	 	stack[id-1] = stack[id-1] - bet;
		puntate[id-1] = puntate[id-1] + bet;
		puntateMano[id -1] = puntateMano[id-1]+bet;

		if(stack[id-1] == 0) AllinGiocatori[id-1] = puntate[id-1];

 		int max= 0;
 		for (int i =0; i< utentiTavolo; i++){
 			if(puntate[i] > max) max = puntate[i]; 
 		}

 		toCall= max - mia_puntata;

	 	if(giroBui) OperazioneEseguita = 3;
	 	
	 	if (id == MyConnectionID && MyConnectionID != ultimoGiocatore && giroBui == false){

	 		try{        
				int i = MyConnectionID % utentiTavolo;
				while(UtentiAttivi.get(i) == null) i = (i+1) % utentiTavolo; 
				TexasGame ClientRMI = (TexasGame)
				java.rmi.Naming.lookup("rmi://"+ UtentiAttivi.get(i)+"/Game");
				ClientRMI.gioca();
			}

		 	catch(Exception e) { 

		 	} 

	 	}


	 	if (id == MyConnectionID && MyConnectionID != IdGrandeBuio && giroBui== true){
	 	 	chiamaGiocatoreSuccessivo();
	 	}
	}


	/*-------------------- passo il turno al giocatore successivo------------------------ */

	public void chiamaGiocatoreSuccessivo(){				/*chiamo l' inizia mano del giocatore sucessivo */
		try{
			TexasGame ClientRMI = (TexasGame) 
			java.rmi.Naming.lookup("rmi://"+ UtentiAttivi.get(MyConnectionID % utentiTavolo) +"/Game");
			ClientRMI.iniziaMano();

		}

		catch(Exception e) { 
   
		} 
	}


	/* ---------------------------qui inizia a giocare l'utente----------------------------------*/ 
	public void gioca(){									/* e' il mio turno e attivo i bottoni*/

		MioTurno(MyConnectionID, OperazioneEseguita);
		if (Debug) System.out.println("GIOCA!!!!!!!");
		attivaBottoni = true;
		Gui.coloraBottoni();
		if (allIn == false){
			Gui.canvas.varStato =new Color(0, 153, 51);
			statoGiocatore = "E' il tuo Turno, call: " + toCall;
		}
	}



	public void flop(int flag){									/*funzione che estrae il flop */

		OperazioneEseguita = 4;
		MioTurno(MyConnectionID, OperazioneEseguita);
		if (Debug) System.out.println("FLOP!!!");
		
		int nsize = CarteComuni.size();
		int cartaEstratta;
		int i=0;

		while(i<3){

			cartaEstratta = CarteComuni.get(i);

			if (cartaEstratta >39 ) cartaDaDisegnare = Integer.toString(cartaEstratta-39) + "P";
			else if (cartaEstratta > 26) cartaDaDisegnare = Integer.toString(cartaEstratta-26) + "F";
			else if (cartaEstratta > 13) cartaDaDisegnare = Integer.toString(cartaEstratta-13) + "Q";
			else cartaDaDisegnare = Integer.toString(cartaEstratta) + "C";
				System.out.println("chiama disegna carte tavolo");
			DisegnaCarteTavolo(cartaDaDisegnare, MyConnectionID);
			System.out.println("dopo disegna carte tavolo");
			i =i+1;

		}
		
		if (flag ==1)gioca();
	}


	public void turn(int flag){							/*funzione che estrae il turn */
		OperazioneEseguita = 5;
		MioTurno(MyConnectionID, OperazioneEseguita);

		if (Debug) System.out.println("TURN!!!");
		
		int cartaEstratta = CarteComuni.get(3);
		if (cartaEstratta >39 ) cartaDaDisegnare = Integer.toString(cartaEstratta-39) + "P";
		else if (cartaEstratta > 26) cartaDaDisegnare = Integer.toString(cartaEstratta-26) + "F";
		else if (cartaEstratta > 13) cartaDaDisegnare = Integer.toString(cartaEstratta-13) + "Q";
		else cartaDaDisegnare = Integer.toString(cartaEstratta) + "C";

		DisegnaCarteTavolo(cartaDaDisegnare, MyConnectionID);
					
		if (flag == 1)gioca();
	}



	public void river(int flag){							/*funzione che estrae il river */
		OperazioneEseguita = 6;
		MioTurno(MyConnectionID, OperazioneEseguita);

		if (Debug) System.out.println("RIVER!!!");
		
		int cartaEstratta = CarteComuni.get(4);
		if (cartaEstratta >39 ) cartaDaDisegnare = Integer.toString(cartaEstratta-39) + "P";
		else if (cartaEstratta > 26) cartaDaDisegnare = Integer.toString(cartaEstratta-26) + "F";
		else if (cartaEstratta > 13) cartaDaDisegnare = Integer.toString(cartaEstratta-13) + "Q";
		else cartaDaDisegnare = Integer.toString(cartaEstratta) + "C";

		DisegnaCarteTavolo(cartaDaDisegnare, MyConnectionID);
					
		if(flag ==1)gioca();
	}



	public void continuaMano(){				/*funzione che continua la mano nel caso in cui i giocatori siano tutti o tutti meno uno in allin */

		if (Debug) System.out.println("CONTINUA MANO");

		try{
		    TimeUnit.SECONDS.sleep(2);
		}
		catch(Exception e){
		    System.exit(1);
		}


		if (statoMano == 0){

		  	flop(0);
			
		  	try{
		    	TimeUnit.SECONDS.sleep(2);
		    }
		    catch(Exception e){
		    	System.exit(1);
		    }

		    turn(0);

			try{
		    	TimeUnit.SECONDS.sleep(2);

		  	}
		  	catch(Exception e){
		    	System.exit(1);
		  	}

		  	river(0);
		}

		else if ( statoMano == 1){

		 	turn(0);

		 	try{
		    	TimeUnit.SECONDS.sleep(2);
		  	}
		  	catch(Exception e){
		    	System.exit(1);
		  	}

		  	river(0);

			}

		else if (statoMano == 2) {

			river(0);
		}

		valuta_punteggio(CarteValutazione);
	}


	public void valuta_punteggio( ArrayList<ArrayList<Integer>> carteValutazione){			/*con k = 0 aggiungo le mie carte ad una lista (CarteValutazione), con k=1 faccio la valutazione delle carte in CarteValutazione e ricevo i vincitori */
		PossoGiocare = false;
		OperazioneEseguita = 7;
		MioTurno(MyConnectionID, OperazioneEseguita);

		for ( int j =0 ; j < utentiTavolo; j++){ 
			piattoTavolo = piattoTavolo + puntateMano[j];	
			if (puntateMano[j]>0){
				 Gui.canvas.firstSet[((MyConnectionID-1)+j)%utentiTavolo]=1;
				}
			 puntateMano[j] = 0;
		}

		if (Debug) System.out.println("VALUTA PUNTEGGIO !!!" +k);

		if (k == 0){
			k = k+1;
			Gui.canvas.Animation = true;
			carteValutazione.add(MieCarte);
			int i = MyConnectionID % utentiTavolo;
	        while(UtentiAttivi.get(i) == null) i = (i+1) % utentiTavolo; 
			
			try{
			TexasGame ClientRMI = (TexasGame) 
			java.rmi.Naming.lookup("rmi://"+ UtentiAttivi.get(i) +"/Game");
			ClientRMI.valuta_punteggio(carteValutazione);
			

			}

			catch(Exception e) { 

			}
		}  

		else { 

			vincitori = Ev.showdown(carteValutazione, CarteComuni);
			mostraRisultato(MyConnectionID, Ev.playersResults);
			try{
				TexasGame ClientRMI = (TexasGame) 
				java.rmi.Naming.lookup("rmi://"+ IpGiocatori.get(IdGrandeBuio % utentiTavolo) +"/Game");
				ClientRMI.terminaMano(vincitori, carteValutazione);
			}

			catch(Exception e) { 
  
		 	} 

		}
		
	}


	public void mostraRisultato(int id , ArrayList<ArrayList<String>> ris){     /*mostra i punteggi di tutti i giocatori ancora attivi*/
		
		if (Debug) System.out.println("mostra Risultato");
		Gui.mostraPunteggi(ris);
		if (id == MyConnectionID){
			for (String x : IpGiocatori){
				if (!x.equals(MyIp)){
					try{
						TexasGame ClientRMI = (TexasGame) 
						java.rmi.Naming.lookup("rmi://"+ x +"/Game");
						ClientRMI.mostraRisultato(id, ris);
					}

					catch(Exception e) { 
		  
				 	}
					
				}

				try{
				    TimeUnit.SECONDS.sleep(2);
				}
				catch(Exception e){
				    System.exit(1);
				}
			}
		}
	}


	public void DisegnaCarteTavolo(String carta, int id){			/* funzione chiamata dalla flop, turn e river che disegna le carte e chiama in broadcast la DisegnaCarteTavolo di tutti gli altri giocatori */

		for ( int i =0 ; i < utentiTavolo; i++){
		 	piattoTavolo = piattoTavolo + puntateMano[i];
			if (puntateMano[i]>0){
				 Gui.canvas.firstSet[((MyConnectionID-1)+i)%utentiTavolo]=1;	
			}
			puntateMano[i] = 0;
		}	
		Gui.DisegnaCarte(carta);
		if (id == MyConnectionID){
			for (String x : IpGiocatori){
				if (!x.equals(MyIp)){
					try{
						TexasGame ClientRMI = (TexasGame) 
						java.rmi.Naming.lookup("rmi://"+ x +"/Game");
						ClientRMI.DisegnaCarteTavolo(carta, id);

					}

					catch(Exception e) { 
    
				 	}
				}

			} 

		}
	}



	public void mostraC(int i){						/*se i e' < utenti attivi chiama la mostra carte, altrimenti la continuaMano */

		int nUtenti = Ut.contaUtentiAttivi();

		if ( i < nUtentiAttivi) mostraCarte(MieCarte,MyConnectionID, i);
		else {
			continuaMano();
		}
	}



	public void mostraCarte(ArrayList <Integer> Carte,int id,int i){	/*mostro le mie carte agli altri giocatori */

		if (Debug) System.out.println("mostraCarte");

		if (id != MyConnectionID) Gui.mostraCarteGiocatore(Carte,id);

		else {
			for (String x : IpGiocatori){
				if (!x.equals(MyIp)){
					try{
						TexasGame ClientRMI = (TexasGame) 
						java.rmi.Naming.lookup("rmi://"+ x +"/Game");
						ClientRMI.mostraCarte(Carte, id, i);

					}

					catch(Exception e) { 
 
				 	}
				}
			}

			try{
				int l = MyConnectionID % utentiTavolo;
				while(UtentiAttivi.get(l) == null || UtentiAttivi.get(l).equals(MyIp) ) l = (l+1) %utentiTavolo;
				TexasGame ClientRMI = (TexasGame) 
				java.rmi.Naming.lookup("rmi://"+ UtentiAttivi.get(l) +"/Game");
				ClientRMI.mostraC(i+1);

				}

			catch(Exception e) { 

		 	}

		}

	}




	public void ImpostaUltimoGiocatore(int id){				/*imposto l'ultimo giocatore che deve parlare in questo giro (prima del flop , prima del turn, ecc....)*/
		ultimoGiocatore = id -1;
		if(ultimoGiocatore == 0) ultimoGiocatore = utentiTavolo;
		while(UtentiAttivi.get(ultimoGiocatore - 1) == null){
			ultimoGiocatore = ultimoGiocatore - 1;
			if(ultimoGiocatore == 0) ultimoGiocatore = utentiTavolo;
		 }

		if (id == MyConnectionID){
			for(String x : IpGiocatori){
				if (!x.equals(MyIp)){
					try{
					TexasGame ClientRMI = (TexasGame) 
					java.rmi.Naming.lookup("rmi://"+ x +"/Game");
					ClientRMI.ImpostaUltimoGiocatore( id);

					}

					catch(Exception e) { 
 
					} 
				}
			}
		}
	}



	public void foldGiocatore(int id, int flag){					/*gestisco il fold di un giocatore, se ho eseguito io il fold lo notifico a tutti gli altri giocatori in broadcast */

		Gui.canvas.foldGiocatori.set(id-1, true);
		UtentiAttivi.set(id-1, null);
		int last = Ut.contaUtentiAttivi();
		if (flag == 1){
			while(UtentiAttivi.get(IdGrandeBuioCorrente-1) == null){
				IdGrandeBuioCorrente = IdGrandeBuioCorrente -1;
				if(IdGrandeBuioCorrente == 0) IdGrandeBuioCorrente = last;
			}
		} 

		if(IdGrandeBuioCorrente == 0) IdGrandeBuioCorrente = last;

		if (id == MyConnectionID){
			for(String x : IpGiocatori){
				if (!x.equals(MyIp)){
					try{
					TexasGame ClientRMI = (TexasGame) 
					java.rmi.Naming.lookup("rmi://"+ x +"/Game");
					ClientRMI.foldGiocatore(id, flag);

					}

					catch(Exception e) { 
 
					} 
				}
			}

		}

	}



	public void terminaMano(ArrayList<Integer> vincitori, ArrayList<ArrayList<Integer>> carteVal){		/*termino la Mano e assegno i piatti corrispondenti a tutti i vincitori */

		if (Debug) System.out.println("TERMINA MANO");
		OperazioneEseguita = 8;
		
		MioTurno(MyConnectionID, OperazioneEseguita);
		
		CarteValutazione.clear();
		for(ArrayList <Integer> x : carteVal ) CarteValutazione.add(x);
		
		ArrayList <Integer> vincitoriTemp = new ArrayList <Integer>();
		ArrayList <Integer> vincitoriTemp2 = new ArrayList <Integer>();
		
		if (piatto >utentiTavolo){
			Gui.canvas.varStato = Color.red;
			if (Gui.canvas.foldGiocatori.get(MyConnectionID -1) == false) statoGiocatore = "Hai perso";
			for(int y : vincitori){ 
				vincitoriTemp2.add(y);
				if (y == MyConnectionID){
					if (vincitori.size()==1){
						Gui.canvas.varStato =new Color(0, 153, 51);
						statoGiocatore = "Hai Vinto";
					}
					else{
						Gui.canvas.varStato =new Color(255, 153, 0);
						statoGiocatore = "Hai Pareggiato";
					}
							
				}
			}
	
		}

		try{
		    TimeUnit.MILLISECONDS.sleep(8000/utentiTavolo);
		}
		catch(Exception e){
		    System.exit(1);
		}



		for(int i = 0; i < utentiTavolo; i++){												/* calcolo i piatti di tutti i giocatori*/
			for(int j =0; j< utentiTavolo; j++){
				if(AllinGiocatori[i] == 0) piatti[i]= piatti[i] + puntate[j];
				else{
					if(puntate[j] <= AllinGiocatori[i]) piatti[i] = piatti[i] + puntate[j];
					else piatti[i] = piatti[i] + AllinGiocatori[i];

				}
			}
		}

		while(piatto > utentiTavolo && vincitori.size()>0){ 							/* finche' c'e' qualcosa nel piatto da distribuire lo assegno a chi spetta */

			while (vincitori.size() > 0){						/*ordino i vincitori con i giocatori con allin minore all'inizio, poi gli altri in allin e infine gli altri */
				int min= 9;
				int minAllIn=10000;
				int index = 0;
				for(int i=0; i<vincitori.size(); i++){
					if(AllinGiocatori[vincitori.get(i)-1] != 0 && AllinGiocatori[vincitori.get(i)-1] < minAllIn){
						minAllIn = AllinGiocatori[vincitori.get(i)-1];
						min = vincitori.get(i);
						index = i;
						break;

					}

					else {
						min = vincitori.get(i);
						index = i;
					}

				}

				vincitoriTemp.add(min);
				vincitori.remove(index);


			}

			while (vincitoriTemp.size()> 0){							/*assegno le vincite */

				int dividendo = vincitoriTemp.size();
				int vincita =  (piatti[vincitoriTemp.get(0) -1] / dividendo);
				stack[vincitoriTemp.get(0) -1] = stack[vincitoriTemp.get(0) -1]  + vincita;
				piatto = piatto - vincita;
				for(int i = 0; i < utentiTavolo ; i ++) piatti[i] = piatti[i] - vincita;


				for (int i = 0 ; i < CarteValutazione.size(); i++){								/*rimuovo dalla lista delle carte da valutare le carte dei giocatori a cui e gia stato assegnato il proprio piatto*/
					if((int)CarteValutazione.get(i).get(0) == (int)vincitoriTemp.get(0)){
						CarteValutazione.remove(i);
					} 
				}

				for (int i = 0 ; i <CarteValutazione.size(); i++){								/*rimuvo dalla lista delle carte da valutare le carte dei giocatori che erano in allin con una somma inferiore al vincitore */
					for (int j = 0 ; j < utentiTavolo; j++){
						if (AllinGiocatori[j] <= AllinGiocatori[vincitoriTemp.get(0)-1] && AllinGiocatori[j] != 0 ){
							if((int)CarteValutazione.get(i).get(0) == (int)(j+1)){
								CarteValutazione.remove(i);
								break;
							}
						}
					}
				}

				vincitoriTemp.remove(0);
			}
		
			if(piatto > utentiTavolo){

				vincitori = Ev.showdown(CarteValutazione, CarteComuni); 
				if (Debug) System.out.println("e rimasto da dividere " + piatto +" e va a: " +vincitori);
			}

		}


		for(int x : vincitoriTemp2) vincitori.add(x); 
		
		if(piatto<0) System.out.println("ERRORE");

		if(MyConnectionID == IdGrandeBuio){

			int l = 0;
			while (l< utentiTavolo){				/*elimino i giocatori sconfitto(il loro stack = 0) (chiamo la termina partita) */
				if (stack[l]== 0) {
					try{
						TexasGame ClientRMI = (TexasGame) 
						java.rmi.Naming.lookup("rmi://"+ IpGiocatori.get(l) +"/Game");
						ClientRMI.Sconfitta();

					}

					catch(Exception e) { 

		 			} 

					terminaPartita(l+1);
				}

				else l = l+1;
			}

			if (IpGiocatori.size() > 2){
				try{
					TexasGame ClientRMI = (TexasGame) 
					java.rmi.Naming.lookup("rmi://"+ IpGiocatori.get((IdGrandeBuio+1) % utentiTavolo) +"/Game");
					ClientRMI.reinizializzaPartita();

				}

				catch(Exception e) { 

			 	} 
			}

			else {
				try{
					TexasGame ClientRMI = (TexasGame) 
					java.rmi.Naming.lookup("rmi://"+ IpGiocatori.get((IdGrandeBuio-1) % utentiTavolo) +"/Game");
					ClientRMI.reinizializzaPartita();

				}

				catch(Exception e) { 
  
			 	} 
			}
		}


		else {

			int l = 0;
			while (l< utentiTavolo){				/*elimino i giocatori sconfitto(il loro stack = 0) (chiamo la termina partita) */
				if (stack[l]== 0) {
					try{
						TexasGame ClientRMI = (TexasGame) 
						java.rmi.Naming.lookup("rmi://"+ IpGiocatori.get(l) +"/Game");
						ClientRMI.Sconfitta();

					}

					catch(Exception e) { 

		 			} 

					terminaPartita(l+1);
				}

				else l = l+1;
			}

			try{
				TexasGame ClientRMI = (TexasGame) 
				java.rmi.Naming.lookup("rmi://"+ IpGiocatori.get(MyConnectionID % utentiTavolo) +"/Game");
				ClientRMI.terminaMano(vincitori, carteVal);
			}

			catch(Exception e) { 

			} 
			
		}

		if (Sconfitto) System.exit(1);

	}



	public void reinizializzaPartita(){			/*reinizializzo tutte le variabili per la mano sucessivo */

		OperazioneEseguita = 9;
		MioTurno(MyConnectionID, OperazioneEseguita);

		if (Debug) System.out.println("reinizializzaPartita utente "+ MyConnectionID);
		Gui.canvas.setCoord();
		Gui.canvas.resetCoord();
		piatto = 0;
		piattoTavolo = piatto;
		mia_puntata = 0;
		statoMano = 0;
		allIn = false;
		attivaBottoni = false;
		Gui.coloraBottoni();
		PossoGiocare = true;
		k = 0;
		
		Gui.canvas.varStato = Color.RED;
		statoGiocatore = "Turno Avversario";

		CarteComuni.clear();
		CarteValutazione.clear();
		vincitoriAllin.clear();
		vincitori.clear();
		
		System.out.println(Ev.playersResults);
		Ev.playersResults.clear();
		System.out.println(Ev.playersResults);
		
		for (int i =0;i<utentiTavolo; i++) piatti[i]=0;
		for ( int i =0 ; i < utentiTavolo; i++) puntateMano[i] = 0;
		
		if (utentiTavolo == 2){
			if (dealer){
				dealer = false;
				piccoloBuio = true;
				grandeBuio = false;
			}
			else{
				dealer = true;
				piccoloBuio = false;
				grandeBuio = true;
			}
		}


		else{

			if (dealer) dealer = false;

			if (piccoloBuio){

				dealer = true;
				piccoloBuio = false;		
			}

			if(grandeBuio){
				piccoloBuio = true;
				grandeBuio = false;
			}
		}

		giroBui = true;

		for (int i= 0; i < utentiTavolo; i++){
			puntate[i]=0;
			AllinGiocatori[i] = 0;
		}

		IdGrandeBuio = IdGrandeBuio +1;
		if (IdGrandeBuio > IpGiocatori.size()) IdGrandeBuio = 1;
		ultimoGiocatore = IdGrandeBuio;
		IdGrandeBuioCorrente = IdGrandeBuio;
		if (MyConnectionID == IdGrandeBuio) grandeBuio = true;

		if (Debug) System.out.println("Sono l'utente " + MyConnectionID + ", dealer: "+dealer+ ",piccoloBuio: " + piccoloBuio + ", grandeBuio: "+grandeBuio + " ultimoGiocatore: " + ultimoGiocatore + "IdGrandeBuioCorrente: " + IdGrandeBuioCorrente);
		Gui.reinizializzaTavolo();
		if (grandeBuio) {
			inizia();
		}

		else{
			try{
			TexasGame ClientRMI = (TexasGame) 
			java.rmi.Naming.lookup("rmi://"+ IpGiocatori.get(MyConnectionID % utentiTavolo) +"/Game");
			ClientRMI.reinizializzaPartita();
			}

		catch(Exception e) { 
  
			 	} 
			}
		}



	public void terminaPartita(int id){					/*funzione chiamata quando un giocatore finisce la partita (Stack =0) o esce dal gioco e lo rimuove dalla lista dei giocatori e aggiorna tutte le variabili interessate*/
		
		if (Debug) System.out.println("Termina partita per il giocatore :" +id );

		IpGiocatori.remove(id -1);
		UtentiAttivi.remove(id-1);
		
		int [] tempStack = new int[utentiTavolo-1];
		int [] tempPuntate = new int[utentiTavolo-1];
		int [] tempPuntateMano = new int[utentiTavolo-1];
		int [] tempPiatti = new int[utentiTavolo-1];
		int [] tempAllinGiocatori = new int[utentiTavolo-1];

		int MyOldConnectionID = MyConnectionID;
		OldGrandeBuio = IdGrandeBuio;

		for (int i =0 ; i<utentiTavolo; i++) piatti[i] = piatti[i] + puntate[id-1];
		
		for(int i=0; i< utentiTavolo; i++){
			
			if (i < id-1) {
				tempStack[i]= stack[i];
				tempPuntate[i] = puntate[i];
				tempPuntateMano[i] = puntateMano[i];
				tempPiatti[i] = piatti[i];
				tempAllinGiocatori[i] = AllinGiocatori[i];
			}

			if (i > id -1){
				tempStack[i-1] = stack[i];
				tempPuntate[i-1] = puntate[i];
				tempPuntateMano[i-1] = puntateMano[i];
				tempPiatti[i-1] = piatti[i];
				tempAllinGiocatori[i-1] = AllinGiocatori[i];
			}
		}


		utentiTavolo = utentiTavolo -1;
		stack = new int[utentiTavolo];
		puntate = new int[utentiTavolo];
		AllinGiocatori = new int[utentiTavolo];
		piatti = new int[utentiTavolo];
		puntateMano = new int[utentiTavolo];


		for(int i =0; i < utentiTavolo; i++){
			stack[i]=tempStack[i];
			puntate[i]=tempPuntate[i];
			AllinGiocatori[i] = tempAllinGiocatori[i];
			piatti[i] = tempPiatti[i];
			puntateMano[i] = tempPuntateMano[i];
		}


		if (id <= IdGrandeBuio) IdGrandeBuio = IdGrandeBuio -1;
		if (id <= MyConnectionID) MyConnectionID = MyConnectionID -1;
		if (id <= IdGrandeBuioCorrente) IdGrandeBuioCorrente = IdGrandeBuioCorrente -1;
		if (id <= ultimoGiocatore) ultimoGiocatore = ultimoGiocatore -1;
		if (id < GiocatoreCorrente) GiocatoreCorrente = GiocatoreCorrente -1;
		
		if(GiocatoreCorrente == 0) GiocatoreCorrente = utentiTavolo;
		if (ultimoGiocatore == 0) ultimoGiocatore = utentiTavolo;
		if (IdGrandeBuio == 0) IdGrandeBuio = utentiTavolo;
		if (IdGrandeBuioCorrente == 0) IdGrandeBuioCorrente = Ut.contaUtentiAttivi();
		MieCarte.set(0,MyConnectionID);
		Gui.canvas.foldGiocatori.remove(id-1);
		Gui.canvas.carteGiocatori.remove(id-1);
		Gui.canvas.risultati.remove(id-1);
		Gui.canvas.setCoord();
		Gui.canvas.resetCoord();

		if(IpGiocatori.size() == 2){
			
			if (IdGrandeBuio == MyConnectionID){
				dealer = true;
				grandeBuio = true;
				piccoloBuio = false;
			}
			else{
				dealer = false;
				piccoloBuio = true;
				grandeBuio = false;
			}
		}
		else{

			if (dealer) dealer = false;


			if (piccoloBuio){

				dealer = true;
				piccoloBuio = false;
			
				}

			if(grandeBuio){
				piccoloBuio = true;
				grandeBuio = false;
			
			}
			if (MyConnectionID == IdGrandeBuio) grandeBuio = true;
		}


		if (IpGiocatori.size()==1 && IpGiocatori.get(0).equals(MyIp) && Sconfitto == false){
			Gui.canvas.varStato =new Color(0, 153, 51);
			statoGiocatore = "Grande! Hai Vinto!";
			Gui.canvas.paintImmediately(Gui.canvas.getBounds());
			if (Debug) System.out.println("Grande! Hai Vinto!");

			try{
	        TimeUnit.SECONDS.sleep(2);

	     	}
	      	catch(Exception e){
	        	System.exit(1);
	      	}

	      	System.exit(0);
		}
	}




	public void MioTurno(int id, int op){				/* dico a tutti i giocatori che sto giocando io e che operazione sto eseguendo */

		GiocatoreCorrente = id;
		OperazioneEseguita = op;

		if (id == MyConnectionID ){
			for ( String x : IpGiocatori){
				if(!x.equals(MyIp)){
					try{
						TexasGame ClientRMI = (TexasGame) 
						java.rmi.Naming.lookup("rmi://"+ x +"/Game");
						ClientRMI.MioTurno(id, op);
					}
					catch(Exception e){

					}
				}
			}
		}
	}



	public void Sconfitta(){
		Gui.canvas.varStato = Color.RED;
		statoGiocatore = "Mi Spiace, hai Perso";
		if (Debug) System.out.println("Mi Spiace, hai Perso");
		
		Sconfitto = true;
	}



	public void sonoVivo(){						/* per controllare se sono ancora in gioco */

	}



	/* reimposto il mio stato nel caso in cui sia avvenuto il crash di un giocatore */
	public void reimpostaStato(int giocatoreCorr, int opEs, int [] stackG, int [] puntateG, int [] puntateManoG, int [] piattiG, int [] AllinGiocatoriG, int ultimoG, int Piatto, String Flop1, String Flop2, String Flop3, String CartaTurn, String CartaRiver, ArrayList<ArrayList<String>> CarteGiocatori){

		if (Debug) System.out.println("Reimposta Stato");

		GiocatoreCorrente = giocatoreCorr;
		OperazioneEseguita = opEs;
		for (int i = 0; i < utentiTavolo; i ++) {
			stack[i] = stackG[i];
			puntate[i] = puntateG[i];
		 	puntateMano[i] = puntateManoG[i];
			piatti[i] = piattiG[i];
			stack[i] = stackG[i];
			AllinGiocatori[i] = AllinGiocatoriG[i];
		}

		ultimoGiocatore = ultimoG;
		piatto = Piatto;

		Gui.canvas.flop1 = Flop1;
		Gui.canvas.flop2 = Flop2;
		Gui.canvas.flop3 = Flop3;
		Gui.canvas.cartaTurn = CartaTurn;
		Gui.canvas.cartaRiver = CartaRiver;
		Gui.canvas.carteGiocatori = CarteGiocatori;

	}
}//fine classe
