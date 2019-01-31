import java.util.*;




public interface TexasGame  
						extends java.rmi.Remote
{ 

void inizia()
			throws java.rmi.RemoteException;


 void Ricezione(List <String> ips, int id)
            throws java.rmi.RemoteException;
 
 void pescaCarte(List <Integer> carte, ArrayList <Integer> carteComuni)
 			throws java.rmi.RemoteException;


 void iniziaMano() 
 			throws java.rmi.RemoteException;


 void punta(int bet, int id)
 			throws java.rmi.RemoteException;


 void gioca()
 			throws java.rmi.RemoteException;

 void terminaMano(ArrayList <Integer> vincitori,ArrayList<ArrayList<Integer>> carteVal)
 			throws java.rmi.RemoteException;

 void reinizializzaPartita()
 			throws java.rmi.RemoteException;

 void flop(int flag)
 			throws java.rmi.RemoteException;

 void turn(int flag)
 			throws java.rmi.RemoteException;

 void river(int flag)
 			throws java.rmi.RemoteException;

 void valuta_punteggio(ArrayList <ArrayList<Integer>> CarteValutazione)
 			throws java.rmi.RemoteException;

 void DisegnaCarteTavolo(String carta, int id)
 			throws java.rmi.RemoteException;

void mostraRisultato(int id, ArrayList <ArrayList<String>> risultati)
			throws java.rmi.RemoteException;

 void ImpostaUltimoGiocatore(int id)
 			throws java.rmi.RemoteException;

 void foldGiocatore(int id, int flag)
 			throws java.rmi.RemoteException;

void terminaPartita(int id)
			throws java.rmi.RemoteException;

void Sconfitta()
			throws java.rmi.RemoteException;

void mostraC(int i)
			throws java.rmi.RemoteException;

void mostraCarte(ArrayList <Integer> Carte, int id,int i)
			throws java.rmi.RemoteException;

void sonoVivo()
			throws java.rmi.RemoteException;

void MioTurno(int id, int op)
			throws java.rmi.RemoteException;

void reimpostaStato(int giocatoreCorr, int opEs, int [] stackG, int [] puntateG, int [] puntateManoG, int [] piattiG, int [] AllinGiocatoriG, int ultimoG, int Piatto, String Flop1, String Flop2, String Flop3, String CartaTurn, String CartaRiver, ArrayList<ArrayList<String>> CarteGiocatori)
			throws java.rmi.RemoteException;

} 
