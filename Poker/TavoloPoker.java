
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.*;

 
public class TavoloPoker extends JPanel {

  BufferedImage img;
  String pescata1, pescata2;
  String flop1 = "Dietro";
  String flop2 = "Dietro";
  String flop3 = "Dietro";
  String cartaTurn = "Dietro";
  String cartaRiver = "Dietro";
  String test1 = "Dietro";
  String test2 = "Dietro";
  Image imgnew;
  Boolean Animation = false;
     
	ArrayList<ArrayList<String>> carteGiocatori= new ArrayList<ArrayList<String>>();
  ArrayList<Boolean> foldGiocatori = new ArrayList <Boolean>();
  ArrayList<String> risultati = new ArrayList<String>();

	 Graphics2D g2d;
	 boolean cartaPescata1 = false;
	 boolean cartaPescata2 = false;
	 boolean flop = false;
	 boolean turn = false;
	 boolean river = false;
	 boolean Piatto = false;
     int i = 0;
     Color varStato = Color.RED;
     
     int[] xC = new int[8];
     int[] yC = new int[8];
     int[] sxC = new int[8];
     int[] syC = new int[8];
     int dxp;
     int dyp;
     int[] firstSet = new int[8];
     Random rand = new Random();
     String dir;
	File f;
	Font currentFont;
	Font newFont;

     float alpha = 1.0f;
     AlphaComposite ac;

	 public BufferedImage LoadImage(String str){
		 try {
		  f = new File(dir+str);
			
	          return ImageIO.read(f);
	       } catch (IOException e) {
	    	   //System.out.print("File non presente"+ str);

				try{
					 TimeUnit.MILLISECONDS.sleep(100+rand.nextInt(500));
					}
					catch(Exception ex){
					 System.exit(1);
					}
					System.out.println("load file failed");
					e.printStackTrace();
	    	   return LoadImage("PokerChips.jpg");
	       }
	 }


	 

	 public void disegna(Graphics2D g2d, int w1, int h1, int w2,int h2, BufferedImage imageee){
		  imgnew = imageee.getScaledInstance(w1, h1, Image.SCALE_DEFAULT);
		    g2d.drawImage(imgnew, w2, h2, null);
	 }


     public void disegna2(Graphics2D g2d, int w1, int h1, int w2,int h2){

            Composite original = g2d.getComposite();
            float a = 0.7f;
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a);
            g2d.setComposite(ac);
            Image imgnew = img.getScaledInstance(w1, h1, Image.SCALE_DEFAULT);
            g2d.drawImage(imgnew, w2, h2, null);
            g2d.setComposite(original);
        }

	

	 public void inizializza(){
        ArrayList <String> carte = new ArrayList <String>();
        carte.add("Dietro");
        carte.add("Dietro");
	//setCoord();
	//resetCoord();
	//resettaVal();
	dir =System.getProperty("user.dir") + System.getProperty("file.separator")+"images"+ System.getProperty("file.separator");
        while(carteGiocatori.size()<8){
            carteGiocatori.add(carte);
            foldGiocatori.add(false);
            risultati.add(null);
        }

	 }

	public void setCoord(){
    	
	int w = getSize().width; 
	int h = getSize().height;
	int offset = w/40;
      if (ServerRMI.utentiTavolo == 2){
          sxC[0] = w/2-offset;//giocatore
          syC[0] = h/2+h*3/8-w/15-h/30-h/10;
          sxC[1] = w/2-offset;//centro alto
          syC[1] = h/2-h*3/8+w/15+h/40+h/20;
        
      }else if (ServerRMI.utentiTavolo == 3){
          sxC[0] = w/2-offset;//giocatore
          syC[0] = h/2+h*3/8-w/15-h/30-h/10;
          sxC[1] = w/2-offset;//centro alto
          syC[1] = h/2-h*3/8+w/15+h/40+h/20;
          sxC[2] = w/2+w*3/8-h/40-w/30-w/15-h/20;//destra
          syC[2] = h/2- w/40;
          
      }else if (ServerRMI.utentiTavolo == 4){
          sxC[0] = w/2-offset;//giocatore
          syC[0] = h/2+h*3/8-w/15-h/30-h/10;
          sxC[2] = w/2-offset;//centro alto
          syC[2] = h/2-h*3/8+w/15+h/40+h/20;
          sxC[3] = w/2+w*3/8-h/40-w/30-w/15-h/20;//destra
          syC[3] = h/2- w/40;
          sxC[1] = w/2-w*3/8+h/40+h/20+w/15;//sinistra
          syC[1] = h/2 - w/32;
          
      }else if (ServerRMI.utentiTavolo == 5){
          sxC[0] = w/2-offset;//giocatore
          syC[0] = h/2+h*3/8-w/15-h/30-h/10;
          sxC[2] = w/2- w*3/16 - w/18 + w/20; //alto sinistra 5
          syC[2] = h/2-h*3/8+w/15+h/40+h/20;
          sxC[4] = w/2+w*3/8-h/40-w/30-w/15-h/20;//destra
          syC[4] = h/2- w/40;
          sxC[1] = w/2-w*3/8+h/40+h/20+w/15;//sinistra
          syC[1] = h/2 - w/32;
          sxC[3] = w/2+ w*3/16 - w/18; //alto destra 5
          syC[3] = h/2-h*3/8+w/15+h/40+h/20;            

      }else if (ServerRMI.utentiTavolo == 6){
          sxC[0] = w/2-offset;//giocatore
          syC[0] = h/2+h*3/8-w/15-h/30-h/10;
          sxC[3] = w/2-offset;//centro alto
          syC[3] = h/2-h*3/8+w/15+h/40+h/20;
          sxC[5] = w/2+w*3/8-h/40-w/30-w/15-h/20;//destra
          syC[5] = h/2- w/40;
          sxC[1] = w/2-w*3/8+h/40+h/20+w/15;//sinistra
          syC[1] = h/2 - w/32;
          sxC[2] = w/2- w*3/(4*3) +w/20-offset;//alto sinistra
          syC[2] = h/2-h*3/8+w/15+h/40+h/20;
          sxC[4] = w/2+ w*3/(4*3) +w/20 - w/18-offset;//alto destra
          syC[4] = h/2-h*3/8+w/15+h/40+h/20;

      }else if (ServerRMI.utentiTavolo == 7){
          sxC[0] = w/2-offset;//giocatore
          syC[0] = h/2+h*3/8-w/15-h/30-h/10;
          sxC[4] = w/2-offset;//centro alto
          syC[4] = h/2-h*3/8+w/15+h/40+h/20;
          sxC[6] = w/2+w*3/8-h/40-w/30-w/15-h/20;//destra
          syC[6] = h/2- w/40;
          sxC[2] = w/2-w*3/8+h/40+h/20+w/15;//sinistra
          syC[2] = h/2 - w/32;
          sxC[3] = w/2- w*3/(4*3) +w/20-offset;//alto sinistra
          syC[3] = h/2-h*3/8+w/15+h/40+h/20;
          sxC[5] = w/2+ w*3/(4*3) +w/20 - w/18-offset;//alto destra
          syC[5] = h/2-h*3/8+w/15+h/40+h/20;
          sxC[1] = w/2-w*3/16-offset;//sotto sinistra
          syC[1] = h/2+h*3/8-w/15-h/40-h/10;            

      }else if (ServerRMI.utentiTavolo == 8){
          sxC[0] = w/2-offset;//giocatore
          syC[0] = h/2+h*3/8-w/15-h/30-h/10;
          sxC[4] = w/2-offset;//centro alto
          syC[4] = h/2-h*3/8+w/15+h/40+h/20;
          sxC[6] = w/2+w*3/8-h/40-w/30-w/15-h/20;//destra
          syC[6] = h/2- w/40;
          sxC[2] = w/2-w*3/8+h/40+h/20+w/15;//sinistra
          syC[2] = h/2 - w/32;
          sxC[3] = w/2- w*3/(4*3) +w/20-offset;//alto sinistra
          syC[3] = h/2-h*3/8+w/15+h/40+h/20;
          sxC[5] = w/2+ w*3/(4*3) +w/20 - w/18-offset;//alto destra
          syC[5] = h/2-h*3/8+w/15+h/40+h/20;
          sxC[1] = w/2-w*3/16-offset;//sotto sinistra
          syC[1] = h/2+h*3/8-w/15-h/40-h/10;
          sxC[7] = w/2+ w*3/(4*3) -offset;//sotto destra
          syC[7] = h/2+h*3/8-w/15-h/40-h/10;
          
      }
  }

  public void resetCoord(){
    for (int i=0; i< ServerRMI.utentiTavolo; i++){
          xC[i] = sxC[i];
          yC[i] = syC[i];
    }
  }
 	

 public void resettaVal(){
		for (int j=0; j<ServerRMI.utentiTavolo; j++){
			firstSet[j] = 0;
			}
	}

    public void DisegnaChips(Graphics2D g2, AffineTransform trans, int n, int w, int h){

            Composite original = g2d.getComposite();
            int offset = w/40;
             if(n == 2){
                    disegna(g2, w/25, w/15, w/2+w/16-offset, h/2-h*3/8+h/40, LoadImage("chip.png"));
                   g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]),  w/2+w/16-offset+w/23, h/2-h*3/8+h/40+w/30);
         		
			 if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0 || firstSet[1] == 1){ 
                    	disegna(g2, w/30, w/30, xC[1], yC[1], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]), sxC[1]+w/26, syC[1]+w/60);
			}
			
			 currentFont = g2.getFont();
        		 newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
        		g2.setFont(newFont);
			 if (risultati.get(ServerRMI.MyConnectionID%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo), sxC[1], syC[1]+w/30);
						
			g2.setFont(currentFont);

                if (foldGiocatori.get((ServerRMI.MyConnectionID)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(0)+".png");
                Image imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
   		
		        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);

                trans.setToTranslation(w/2-offset, h/2-h*3/8+w/15+h/40);
                trans.rotate( Math.toRadians(180) );
                g2.drawImage(imgnew, trans, null);

                img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(1)+".png");
                imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                

                ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);


                trans.setToTranslation(w/2+ w/18-offset,  h/2-h*3/8+w/15+h/40);
                trans.rotate( Math.toRadians(180) );
                g2.drawImage(imgnew, trans, null);

                alpha = 1.0f;
                
			
            }

             else if(n == 3){
                      disegna(g2, w/25, w/15, w/2+w/16-offset, h/2-h*3/8+h/40, LoadImage("chip.png"));
                    disegna(g2, w/25, w/15, w/2+w*3/8-h/40-w/25-w/60,  h/2- w/40 - w/14, LoadImage("chip.png"));
                     g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]),  w/2+w/16-offset+w/23, h/2-h*3/8+h/40+w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 1)% ServerRMI.utentiTavolo]), w/2+w*3/8-w/25-h/40-w/60+w/100, h/2 -w/40 - w/30 - h/80-w/30);
                                                   
			 if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]>0 || firstSet[2] == 1){
                     	disegna(g2, w/30, w/30, xC[2], yC[2], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]), sxC[2]+w/120, syC[2]-w/120);
			}
                
		   if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0 || firstSet[1] == 1){ 
                    	disegna(g2, w/30, w/30, xC[1], yC[1], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]), sxC[1]+w/26, syC[1]+w/60);
			}
         
			 currentFont = g2.getFont();
        		 newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
        		g2.setFont(newFont);
			 if (risultati.get(ServerRMI.MyConnectionID%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo), sxC[1], syC[1]+w/30);
			 if (risultati.get((ServerRMI.MyConnectionID+1)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo), sxC[2]-h/10, syC[2]-w/60);
			
						
			g2.setFont(currentFont);


                if (foldGiocatori.get((ServerRMI.MyConnectionID) %ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(0)+".png");

                ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                Image imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                                      
                trans.setToTranslation(w/2-offset, h/2-h*3/8+w/15+h/40);
                trans.rotate( Math.toRadians(180) );
                g2.drawImage(imgnew, trans, null);

                img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(1)+".png");
                imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                
                trans.setToTranslation(w/2+ w/18-offset,  h/2-h*3/8+w/15+h/40);
                trans.rotate( Math.toRadians(180) );
                g2.drawImage(imgnew, trans, null);

                alpha = 1.0f;

                if (foldGiocatori.get((ServerRMI.MyConnectionID +1)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+1) % ServerRMI.utentiTavolo).get(0)+".png");
                imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                trans.setToTranslation(w/2+w*3/8-h/40, h/2- w/40);
                trans.rotate( Math.toRadians(90) );
                g2.drawImage(imgnew, trans, null);

                img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+1) % ServerRMI.utentiTavolo).get(1)+".png");
                imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                
                trans.setToTranslation(w/2+w*3/8-h/40, h/2- w/40 +w/18);
                trans.rotate( Math.toRadians(90) );
                g2.drawImage(imgnew, trans, null);
                
                alpha = 1.0f;
                    
             }
             else if(n == 4){
                      disegna(g2, w/25, w/15, w/2+w/16-offset, h/2-h*3/8+h/40, LoadImage("chip.png"));
                    disegna(g2, w/25, w/15, w/2+w*3/8-h/40-w/25-w/60,  h/2- w/40 - w/14, LoadImage("chip.png"));
                    disegna(g2, w/25, w/15, w/2-w*3/8+h/40+w/60, h/2+w/33, LoadImage("chip.png"));
                    
                   
                   g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]),  w/2+w/16-offset+w/23, h/2-h*3/8+h/40+w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 2)% ServerRMI.utentiTavolo]), w/2+w*3/8-w/25-h/40-w/60+w/100, h/2 -w/35 - w/30 - h/80-w/30);                   
                     g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]),  w/2-w*3/8+h/40+w/60+w/100, h/2+w/30 + w*2/30 + h/80);                    

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]>0 || firstSet[3] == 1){
                     	disegna(g2, w/30, w/30, xC[3], yC[3], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]), sxC[3]+w/120, syC[3]-w/120);
			}
                
		   if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]>0 || firstSet[2] == 1){ 
                    	disegna(g2, w/30, w/30, xC[2], yC[2], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]), sxC[2]+w/26, syC[2]+w/60);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0 || firstSet[1] == 1){
                     	disegna(g2, w/30, w/30, xC[1], yC[1], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]), sxC[1]+w/120, syC[1]-w/120);
			}
         
			 currentFont = g2.getFont();
        		 newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
        		g2.setFont(newFont);
			 if (risultati.get(ServerRMI.MyConnectionID%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo), sxC[1], syC[1]-w/60);
			 if (risultati.get((ServerRMI.MyConnectionID+1)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo), sxC[2], syC[2]+w/30);
			if (risultati.get((ServerRMI.MyConnectionID+2)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+2)%ServerRMI.utentiTavolo), sxC[3]-h/10, syC[3]-w/60);
						
			g2.setFont(currentFont);
            
                if (foldGiocatori.get((ServerRMI.MyConnectionID +1)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                      img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+1) % ServerRMI.utentiTavolo).get(0)+".png");
                     Image imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                     
	                ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                    g2d.setComposite(ac);
                    trans.setToTranslation(w/2-offset, h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);

			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+1) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                     
                    ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                    g2d.setComposite(ac);
                    
                    trans.setToTranslation(w/2+ w/18-offset,  h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);

                alpha = 1.0f;

                if (foldGiocatori.get((ServerRMI.MyConnectionID +2)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+2) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                     
                    ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                     trans.setToTranslation(w/2+w*3/8-h/40, h/2- w/40);
                    trans.rotate( Math.toRadians(90) );
                    g2.drawImage(imgnew, trans, null);

			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+2) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                     
                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                    
                     trans.setToTranslation(w/2+w*3/8-h/40, h/2- w/40 +w/18);
                      trans.rotate( Math.toRadians(90) );
                     g2.drawImage(imgnew, trans, null);

                alpha = 1.0f;

                if (foldGiocatori.get((ServerRMI.MyConnectionID)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                     
                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                      trans.setToTranslation(w/2-w*3/8+h/40, h/2 - w/32);
                      trans.rotate( Math.toRadians(-90) );
                     g2.drawImage(imgnew, trans, null);

			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                     
                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                     trans.setToTranslation(w/2-w*3/8+h/40, h/2 + w/40);
                      trans.rotate( Math.toRadians(-90) );
                     g2.drawImage(imgnew, trans, null);

                alpha = 1.0f;
                                                              
             }
             else if(n == 5){
                     disegna(g2, w/25, w/15, w/2-w*3/16+w/200+w/20, h/2-h*3/8+h/40, LoadImage("chip.png"));
                      disegna(g2, w/25, w/15, w/2+w*3/16 + w/200, h/2-h*3/8+h/40, LoadImage("chip.png"));
                     disegna(g2, w/25, w/15, w/2+w*3/8-h/40-w/25-w/60,  h/2- w/40 - w/14, LoadImage("chip.png"));
                    disegna(g2, w/25, w/15, w/2-w*3/8+h/40+w/60, h/2+w/33, LoadImage("chip.png"));
                    			 
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 2)% ServerRMI.utentiTavolo]),  w/2+w*3/16 + w/200+w/23, h/2-h*3/8+h/40+w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 3)% ServerRMI.utentiTavolo]), w/2+w*3/8-w/25-h/40-w/60+w/100, h/2 -w/35 - w/30 - h/80-w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 1)% ServerRMI.utentiTavolo]), w/2-w*3/16+w/200+w/20+w/23, h/2-h*3/8+h/40+w/30);
                      g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]),  w/2-w*3/8+h/40+w/60+w/100, h/2+w/30 + w*2/30 + h/80);
                    
                     // img = LoadImage("/singlechip.png");

		    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo]>0 || firstSet[4] == 1){
                     	disegna(g2, w/30, w/30, xC[4], yC[4], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo]), sxC[4]+w/120, syC[4]-w/120);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]>0 || firstSet[3] == 1){
                     	disegna(g2, w/30, w/30, xC[3], yC[3], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]), sxC[3]+w/26, syC[3]+w/60);
			}                    
		   
                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]>0 || firstSet[2] == 1){ 
                    	disegna(g2, w/30, w/30, xC[2], yC[2], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]), sxC[2]+w/26, syC[2]+w/60);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0 || firstSet[1] == 1){
                     	disegna(g2, w/30, w/30, xC[1], yC[1], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]), sxC[1]+w/120, syC[1]-w/120);
			}
         
			currentFont = g2.getFont();
        		newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
        		g2.setFont(newFont);
			if (risultati.get(ServerRMI.MyConnectionID%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo), sxC[1], syC[1]-w/60);
			 if (risultati.get((ServerRMI.MyConnectionID+1)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo), sxC[2], syC[2]-w/120);
			
			if (risultati.get((ServerRMI.MyConnectionID+2)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo), sxC[3], syC[3]-w/120);
			if (risultati.get((ServerRMI.MyConnectionID+3)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+3)%ServerRMI.utentiTavolo), sxC[4]-h/10, syC[4]-w/60);
						
			g2.setFont(currentFont);  
                    
            if (foldGiocatori.get((ServerRMI.MyConnectionID +1)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                       img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+1) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                     
                    ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                     trans.setToTranslation(w/2- w*3/16 +w/20, h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);

			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+1) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                     
                    ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                    trans.setToTranslation(w/2- w*3/16 - w/18 + w/20,  h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);

            alpha = 1.0f;

            if (foldGiocatori.get((ServerRMI.MyConnectionID +2)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+2) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                     
                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
	   
                    trans.setToTranslation(w/2+ w*3/16 , h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);

			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+2) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);
                     
                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                    
                    trans.setToTranslation(w/2+ w*3/16 - w/18,  h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);


            alpha = 1.0f;



            if (foldGiocatori.get((ServerRMI.MyConnectionID +3)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;


			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+3) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);                    
                    
                     trans.setToTranslation(w/2+w*3/8-h/40, h/2- w/40);
                    trans.rotate( Math.toRadians(90) );
                    g2.drawImage(imgnew, trans, null);

			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+3) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                     trans.setToTranslation(w/2+w*3/8-h/40, h/2- w/40 +w/18);
                      trans.rotate( Math.toRadians(90) );
                     g2.drawImage(imgnew, trans, null);

            alpha = 1.0f;



            if (foldGiocatori.get((ServerRMI.MyConnectionID)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;


			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                      trans.setToTranslation(w/2-w*3/8+h/40, h/2 - w/32);
                      trans.rotate( Math.toRadians(-90) );
                     g2.drawImage(imgnew, trans, null);
                     
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                     trans.setToTranslation(w/2-w*3/8+h/40, h/2 + w/40);
                      trans.rotate( Math.toRadians(-90) );
                     g2.drawImage(imgnew, trans, null);


            alpha = 1.0f;

             }
              else if(n == 6){
                    disegna(g2, w/25, w/15, w/2+w/16-offset, h/2-h*3/8+h/40, LoadImage("chip.png"));
                     disegna(g2, w/25, w/15, w/2-w*3/(4*4)-offset, h/2-h*3/8+h/40, LoadImage("chip.png"));
                      disegna(g2, w/25, w/15, w/2+w*3/(4*4)+w*2/16-offset, h/2-h*3/8+h/40, LoadImage("chip.png"));
                     disegna(g2, w/25, w/15, w/2+w*3/8-h/40-w/25-w/60,  h/2- w/40 - w/14, LoadImage("chip.png"));
                    disegna(g2, w/25, w/15, w/2-w*3/8+h/40+w/60, h/2+w/33, LoadImage("chip.png"));
                    
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 3)% ServerRMI.utentiTavolo]),   w/2+w*3/(4*4)+w*2/16-offset+ w/23, h/2-h*3/8+h/40+w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 2)% ServerRMI.utentiTavolo]),  w/2+w/16-offset+w/23, h/2-h*3/8+h/40+w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 4)% ServerRMI.utentiTavolo]), w/2+w*3/8-w/25-h/40-w/60+w/100, h/2 -w/35 - w/30 - h/80-w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 1)% ServerRMI.utentiTavolo]), w/2-w*3/(4*4)-offset+ w/23, h/2-h*3/8+h/40+w/30);
                     g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]),  w/2-w*3/8+h/40+w/60+w/100, h/2+w/30 + w*2/30 + h/80);
                    

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo]>0 || firstSet[4] == 1){
			disegna(g2, w/30, w/30, xC[4], yC[4], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo]), sxC[4]+w/26, syC[4]+w/60);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]>0 || firstSet[3] == 1){
                     	disegna(g2, w/30, w/30, xC[3], yC[3], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]), sxC[3]+w/26, syC[3]+w/60);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+4)% ServerRMI.utentiTavolo]>0 || firstSet[5] == 1){
                     	disegna(g2, w/30, w/30, xC[5], yC[5], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+4)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+4)% ServerRMI.utentiTavolo]), sxC[5]+w/60, syC[5]-w/120);
			}
                    
		   
                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]>0 || firstSet[2] == 1){ 
                    	disegna(g2, w/30, w/30, xC[2], yC[2], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]), sxC[2]+w/26, syC[2]+w/60);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0 || firstSet[1] == 1){
                     	disegna(g2, w/30, w/30, xC[1], yC[1], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]), sxC[1]+w/60, syC[1]-w/120);
			}

         

			currentFont = g2.getFont();
        		newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
        		g2.setFont(newFont);
			 if (risultati.get(ServerRMI.MyConnectionID%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo), sxC[1], syC[1]-w/60);
			 if (risultati.get((ServerRMI.MyConnectionID+1)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo), sxC[2], syC[2]-w/120);
			if (risultati.get((ServerRMI.MyConnectionID+2)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+2)%ServerRMI.utentiTavolo), sxC[3], syC[3]+w/120);
			if (risultati.get((ServerRMI.MyConnectionID+3)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo), sxC[4], syC[4]-w/120);
			if (risultati.get((ServerRMI.MyConnectionID+4)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+4)% ServerRMI.utentiTavolo), sxC[5]-h/10, syC[5]-w/60);
			
			
			g2.setFont(currentFont);

            if (foldGiocatori.get((ServerRMI.MyConnectionID+2)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                    
                    img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+2) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                    
                      trans.setToTranslation(w/2-offset, h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+2) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2+ w/18-offset,  h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);
                    

                    alpha = 1.0f;
            if (foldGiocatori.get((ServerRMI.MyConnectionID +1 )%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                    img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+1) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                       trans.setToTranslation(w/2- w*3/(4*3) +w/20-offset, h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+1) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2- w*3/(4*3) - w/18 + w/20-offset,  h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);

            alpha = 1.0f;


            if (foldGiocatori.get((ServerRMI.MyConnectionID +3)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

			
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+3) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     	   
                    trans.setToTranslation(w/2+ w*3/(4*3) +w/20-offset, h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+3) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2+ w*3/(4*3) +w/20 - w/18-offset,  h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);

            alpha = 1.0f;



            if (foldGiocatori.get((ServerRMI.MyConnectionID +4 )%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                    
                    img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+4) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                      trans.setToTranslation(w/2+w*3/8-h/40, h/2- w/40);
                    trans.rotate( Math.toRadians(90) );
                    g2.drawImage(imgnew, trans, null);
			
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+4) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                                         
                     trans.setToTranslation(w/2+w*3/8-h/40, h/2- w/40 +w/18);
                      trans.rotate( Math.toRadians(90) );
                     g2.drawImage(imgnew, trans, null);

            alpha = 1.0f;



            if (foldGiocatori.get((ServerRMI.MyConnectionID)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;


			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                      trans.setToTranslation(w/2-w*3/8+h/40, h/2 - w/32);
                      trans.rotate( Math.toRadians(-90) );
                     g2.drawImage(imgnew, trans, null);

			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                                          
                     trans.setToTranslation(w/2-w*3/8+h/40, h/2 + w/40);
                      trans.rotate( Math.toRadians(-90) );
                     g2.drawImage(imgnew, trans, null);
                    
            alpha = 1.0f;
                    
                   
             }
             else if(n == 7){
                        
                     disegna(g2, w/25, w/15, w/2+w/16-offset, h/2-h*3/8+h/40, LoadImage("chip.png"));
                     disegna(g2, w/25, w/15, w/2-w*3/(4*4)-offset, h/2-h*3/8+h/40, LoadImage("chip.png"));
                      disegna(g2, w/25, w/15, w/2+w*3/(4*4)+w*2/16-offset, h/2-h*3/8+h/40, LoadImage("chip.png"));
                     disegna(g2, w/25, w/15, w/2+w*3/8-h/40-w/25-w/60,  h/2- w/40 - w/14, LoadImage("chip.png"));
                    disegna(g2, w/25, w/15, w/2-w*3/8+h/40+w/60, h/2+w/33, LoadImage("chip.png"));
                    disegna(g2, w/25, w/15, w/2-w*3/16-offset, h/2+h*3/8-w/15-h/40, LoadImage("chip.png"));
                    
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 3)% ServerRMI.utentiTavolo]),   w/2+w/16-offset+w/23, h/2-h*3/8+h/40+w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 2)% ServerRMI.utentiTavolo]),  w/2-w*3/(4*4)-offset+ w/23, h/2-h*3/8+h/40+w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 4)% ServerRMI.utentiTavolo]), w/2+w*3/(4*4)+w*2/16-offset+ w/23, h/2-h*3/8+h/40+w/30);
                     g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 5)% ServerRMI.utentiTavolo]), w/2+w*3/8-w/25-h/40-w/60+w/100, h/2 -w/35 - w/30 - h/80-w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 1)% ServerRMI.utentiTavolo]), w/2-w*3/8+h/40+w/60+w/100, h/2+w/30 + w*2/30 + h/80);
                     g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]),  w/2-w*3/16-offset+w/23, h/2+h*3/8-w/30-h/40);                                        

                   if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo]>0 || firstSet[4] == 1){
			disegna(g2, w/30, w/30, xC[4], yC[4], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo]), sxC[4]+w/26, syC[4]+w/60);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]>0 || firstSet[3] == 1){
                     	disegna(g2, w/30, w/30, xC[3], yC[3], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]), sxC[3]+w/26, syC[3]+w/60);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+4)% ServerRMI.utentiTavolo]>0 || firstSet[5] == 1){
                     	disegna(g2, w/30, w/30, xC[5], yC[5], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+4)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+4)% ServerRMI.utentiTavolo]), sxC[5]+w/26, syC[5]+w/60);
			}
                    
		    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+5)% ServerRMI.utentiTavolo]>0 || firstSet[6] == 1){
                    	disegna(g2, w/30, w/30, xC[6], yC[6], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+5)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+5)% ServerRMI.utentiTavolo]), sxC[6]+w/60, syC[6]-w/120);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]>0 || firstSet[2] == 1){ 
                    	disegna(g2, w/30, w/30, xC[2], yC[2], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]), sxC[2]+w/60, syC[2]-w/120);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0 || firstSet[1] == 1){
                     	disegna(g2, w/30, w/30, xC[1], yC[1], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]), sxC[1]+w/26, syC[1]+w/60);
			}         
			currentFont = g2.getFont();
        		newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
        		g2.setFont(newFont);
			 if (risultati.get(ServerRMI.MyConnectionID%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo), sxC[1], syC[1]+w/120);
			 if (risultati.get((ServerRMI.MyConnectionID+1)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo), sxC[2], syC[2]-w/60);
			if (risultati.get((ServerRMI.MyConnectionID+2)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+2)%ServerRMI.utentiTavolo), sxC[3], syC[3]-w/120);
			if (risultati.get((ServerRMI.MyConnectionID+3)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo), sxC[4], syC[4]);
			if (risultati.get((ServerRMI.MyConnectionID+4)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+4)% ServerRMI.utentiTavolo), sxC[5], syC[5]-w/120);
			if (risultati.get((ServerRMI.MyConnectionID+5)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+5)% ServerRMI.utentiTavolo), sxC[6]-h/10, syC[6]-w/60);
			
			g2.setFont(currentFont);

            if (foldGiocatori.get((ServerRMI.MyConnectionID + 3)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                     img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+3) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);                    
                    
                     trans.setToTranslation(w/2-offset, h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+3) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2+ w/18-offset,  h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);
                    


                   alpha = 1.0f;



            if (foldGiocatori.get((ServerRMI.MyConnectionID + 2)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                    img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+2) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                       trans.setToTranslation(w/2- w*3/(4*3) +w/20-offset, h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+2) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2- w*3/(4*3) - w/18 + w/20-offset,  h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);


            alpha = 1.0f;

            if (foldGiocatori.get((ServerRMI.MyConnectionID + 4)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

	   
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+4) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2+ w*3/(4*3) +w/20-offset, h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+4) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2+ w*3/(4*3) +w/20 - w/18-offset,  h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);


            alpha = 1.0f;



            if (foldGiocatori.get((ServerRMI.MyConnectionID + 5)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                    
                    img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+5) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                           
                      trans.setToTranslation(w/2+w*3/8-h/40, h/2- w/40);
                    trans.rotate( Math.toRadians(90) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+5) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                     trans.setToTranslation(w/2+w*3/8-h/40, h/2- w/40 +w/18);
                      trans.rotate( Math.toRadians(90) );
                     g2.drawImage(imgnew, trans, null);


            alpha = 1.0f;



            if (foldGiocatori.get((ServerRMI.MyConnectionID + 1)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                     
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+1) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                      trans.setToTranslation(w/2-w*3/8+h/40, h/2 - w/32);
                      trans.rotate( Math.toRadians(-90) );
                     g2.drawImage(imgnew, trans, null);
                     
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+1) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                     trans.setToTranslation(w/2-w*3/8+h/40, h/2 + w/40);
                      trans.rotate( Math.toRadians(-90) );
                     g2.drawImage(imgnew, trans, null);

            alpha = 1.0f;


            if (foldGiocatori.get((ServerRMI.MyConnectionID)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                     
                     img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                       trans.setToTranslation(w/2- w*3/(4*3) -offset, h/2+h*3/8-w/15-h/40);
                    trans.rotate( Math.toRadians(0) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2- w*3/(4*3) - w/18 -offset,  h/2+h*3/8-w/15-h/40);
                    trans.rotate( Math.toRadians(0) );
                    g2.drawImage(imgnew, trans, null);
                    
                alpha = 1.0f;
             }
             else if(n==8) {
                     disegna(g2, w/25, w/15, w/2+w/16-offset, h/2-h*3/8+h/40, LoadImage("chip.png"));
                     disegna(g2, w/25, w/15, w/2-w*3/(4*4)-offset, h/2-h*3/8+h/40, LoadImage("chip.png"));
                      disegna(g2, w/25, w/15, w/2+w*3/(4*4)+w*2/16-offset, h/2-h*3/8+h/40, LoadImage("chip.png"));
                     disegna(g2, w/25, w/15, w/2+w*3/8-h/40-w/25-w/60,  h/2- w/40 - w/14, LoadImage("chip.png"));
                    disegna(g2, w/25, w/15, w/2-w*3/8+h/40+w/60, h/2+w/33, LoadImage("chip.png"));
                    disegna(g2, w/25, w/15, w/2-w*3/16-offset, h/2+h*3/8-w/15-h/40, LoadImage("chip.png"));
                     disegna(g2, w/25, w/15, w/2+w*3/(4*4)+w*2/16-offset, h/2+h*3/8-w/15-h/40, LoadImage("chip.png"));
                     
                     g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 3)% ServerRMI.utentiTavolo]),   w/2+w/16-offset+w/23, h/2-h*3/8+h/40+w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 2)% ServerRMI.utentiTavolo]),  w/2-w*3/(4*4)-offset+ w/23, h/2-h*3/8+h/40+w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 4)% ServerRMI.utentiTavolo]), w/2+w*3/(4*4)+w*2/16-offset+ w/23, h/2-h*3/8+h/40+w/30);
                     g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 5)% ServerRMI.utentiTavolo]), w/2+w*3/8-w/25-h/40-w/60+w/100, h/2 -w/35 - w/30 - h/80-w/30);
                    g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 1)% ServerRMI.utentiTavolo]), w/2-w*3/8+h/40+w/60+w/100, h/2+w/30 + w*2/30 + h/80);
                     g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]),  w/2-w*3/16-offset+w/23, h/2+h*3/8-w/30-h/40);
                     g2.drawString(Integer.toString(ServerRMI.stack[(ServerRMI.MyConnectionID + 6)% ServerRMI.utentiTavolo]),   w/2+w*3/(4*4)+w*2/16-offset+w/23, h/2+h*3/8-w/30-h/40);
                                          
                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo]>0 || firstSet[4] == 1){
			disegna(g2, w/30, w/30, xC[4], yC[4], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo]), sxC[4]+w/26, syC[4]+w/60);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]>0 || firstSet[3] == 1){
                     	disegna(g2, w/30, w/30, xC[3], yC[3], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+2)% ServerRMI.utentiTavolo]), sxC[3]+w/26, syC[3]+w/60);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+4)% ServerRMI.utentiTavolo]>0 || firstSet[5] == 1){
                     	disegna(g2, w/30, w/30, xC[5], yC[5], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+4)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+4)% ServerRMI.utentiTavolo]), sxC[5]+w/26, syC[5]+w/60);
			}
                    
		    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+5)% ServerRMI.utentiTavolo]>0 || firstSet[6] == 1){
                    	disegna(g2, w/30, w/30, xC[6], yC[6], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+5)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+5)% ServerRMI.utentiTavolo]), sxC[6]+w/60, syC[6]-w/120);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]>0 || firstSet[2] == 1){ 
                    	disegna(g2, w/30, w/30, xC[2], yC[2], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo]), sxC[2]+w/60, syC[2]-w/120);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0 || firstSet[1] == 1){
                     	disegna(g2, w/30, w/30, xC[1], yC[1], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo]), sxC[1]+w/26, syC[1]+w/60);
			}

                    if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+6)% ServerRMI.utentiTavolo]>0 || firstSet[7] == 1){
                    	disegna(g2, w/30, w/30, xC[7], yC[7], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.puntateMano[(ServerRMI.MyConnectionID+6)% ServerRMI.utentiTavolo]>0)
			g2d.drawString(Integer.toString(ServerRMI.puntateMano[(ServerRMI.MyConnectionID+6)% ServerRMI.utentiTavolo]), sxC[7]+w/26, syC[7]+w/60);
			}

			currentFont = g2.getFont();
        		newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
        		g2.setFont(newFont);
			 if (risultati.get(ServerRMI.MyConnectionID%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID)% ServerRMI.utentiTavolo), sxC[1], syC[1]+w/120);
			 if (risultati.get((ServerRMI.MyConnectionID+1)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+1)% ServerRMI.utentiTavolo), sxC[2], syC[2]-w/60);
			if (risultati.get((ServerRMI.MyConnectionID+2)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+2)%ServerRMI.utentiTavolo), sxC[3], syC[3]-w/120);
			if (risultati.get((ServerRMI.MyConnectionID+3)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+3)% ServerRMI.utentiTavolo), sxC[4], syC[4]+w/120);
			if (risultati.get((ServerRMI.MyConnectionID+4)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+4)% ServerRMI.utentiTavolo), sxC[5], syC[5]-w/120);
			if (risultati.get((ServerRMI.MyConnectionID+5)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+5)% ServerRMI.utentiTavolo), sxC[6]-h/10, syC[6]-w/60);
			if (risultati.get((ServerRMI.MyConnectionID+6)%ServerRMI.utentiTavolo) != null) g2d.drawString(risultati.get((ServerRMI.MyConnectionID+6)% ServerRMI.utentiTavolo), sxC[7], syC[7]+w/120);
			g2.setFont(currentFont);



            if (foldGiocatori.get((ServerRMI.MyConnectionID + 3)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                    
                      img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+3) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                                         
                     trans.setToTranslation(w/2-offset, h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+3) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2+ w/18-offset,  h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);

           alpha = 1.0f;



            if (foldGiocatori.get((ServerRMI.MyConnectionID + 2)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                    
                    img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+2) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                       trans.setToTranslation(w/2- w*3/(4*3) +w/20-offset, h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+2) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2- w*3/(4*3) - w/18 + w/20-offset,  h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);


           alpha = 1.0f;



            if (foldGiocatori.get((ServerRMI.MyConnectionID + 4)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

	   
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+4) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2+ w*3/(4*3) +w/20-offset, h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+4) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2+ w*3/(4*3) +w/20 - w/18-offset,  h/2-h*3/8+w/15+h/40);
                    trans.rotate( Math.toRadians(180) );
                    g2.drawImage(imgnew, trans, null);
                    


           alpha = 1.0f;



            if (foldGiocatori.get((ServerRMI.MyConnectionID + 5)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                    img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+5) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                      trans.setToTranslation(w/2+w*3/8-h/40, h/2- w/40);
                    trans.rotate( Math.toRadians(90) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+5) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                     trans.setToTranslation(w/2+w*3/8-h/40, h/2- w/40 +w/18);
                      trans.rotate( Math.toRadians(90) );
                     g2.drawImage(imgnew, trans, null);

                 alpha = 1.0f;

            if (foldGiocatori.get((ServerRMI.MyConnectionID + 1)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                     
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+1) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                      trans.setToTranslation(w/2-w*3/8+h/40, h/2 - w/32);
                      trans.rotate( Math.toRadians(-90) );
                     g2.drawImage(imgnew, trans, null);
                     
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+1) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                     trans.setToTranslation(w/2-w*3/8+h/40, h/2 + w/40);
                      trans.rotate( Math.toRadians(-90) );
                     g2.drawImage(imgnew, trans, null);


                alpha = 1.0f;


            if (foldGiocatori.get((ServerRMI.MyConnectionID)%ServerRMI.utentiTavolo) == true) alpha = 0.7f;

                     
                     img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                       trans.setToTranslation(w/2- w*3/(4*3) -offset, h/2+h*3/8-w/15-h/40);
                    trans.rotate( Math.toRadians(0) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2- w*3/(4*3) - w/18 -offset,  h/2+h*3/8-w/15-h/40);
                    trans.rotate( Math.toRadians(0) );
                    g2.drawImage(imgnew, trans, null);

               alpha = 1.0f;


            if (foldGiocatori.get((ServerRMI.MyConnectionID +6 )%ServerRMI.utentiTavolo) == true) alpha = 0.7f;


			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+6) % ServerRMI.utentiTavolo).get(0)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                                         
                       trans.setToTranslation(w/2+ w*3/(4*3) -offset, h/2+h*3/8-w/15-h/40);
                    trans.rotate( Math.toRadians(0) );
                    g2.drawImage(imgnew, trans, null);
                    
			img = LoadImage(carteGiocatori.get((ServerRMI.MyConnectionID+6) % ServerRMI.utentiTavolo).get(1)+".png");
                     imgnew = img.getScaledInstance(w/20, w/15, Image.SCALE_DEFAULT);

                     ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                     g2d.setComposite(ac);
                     
                    trans.setToTranslation(w/2+ w*3/(4*3) - w/18 -offset,  h/2+h*3/8-w/15-h/40);
                    trans.rotate( Math.toRadians(0) );
                    g2.drawImage(imgnew, trans, null);

                alpha = 1.0f;
                    
                    
             }

        g2d.setComposite(original);

    }


   public TavoloPoker() {
     setVisible(true);
      
     inizializza();
   }


   public void paintComponent(Graphics g) {
     try{	
        
	   g2d = (Graphics2D)g;
	   g2d.setStroke(new BasicStroke(8));
	   int w = getSize().width;
        	int h = getSize().height;
		int delta = 10;
		dxp = w/2+w/9+w/30;
        	dyp = h/2-w/60;
		if (Animation == false) resetCoord();
       		
   	    g2d.clearRect(0,0, getWidth(),getHeight());
	    g2d.setColor(Color.black);
	    float thickness = 3;
            Stroke oldStroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(thickness));
	    g2d.draw(new RoundRectangle2D.Double(w/2-w*3/8, h/2-h*3/8, w*3/4, h*3/4, 20, 20));
	    g2d.setStroke(oldStroke);
	    
	    Color myColor = new Color (0, 204, 68);
	    g2d.setColor(myColor);
	    g2d.fill(new RoundRectangle2D.Double(w/2-w*3/8+1, h/2-h*3/8+1, w*3/4-2, h*3/4-2, 10, 10));
	    
   	    
   	     AffineTransform identity = new AffineTransform();
	     AffineTransform trans = new AffineTransform();
            trans.setTransform(identity);
            int offset = w/40;

		g2d.setColor(varStato);
        	currentFont = g2d.getFont();
		newFont = currentFont.deriveFont(currentFont.getSize() * 2.2F);
		g2d.setFont(newFont);
		g2d.drawString(ServerRMI.statoGiocatore, w/2 - w/11, h-h/18);
		g2d.setFont(currentFont);


	if(ServerRMI.utentiTavolo>1 && ServerRMI.Sconfitto == false){
	    if(cartaPescata1 == true){ 
              
            if(ServerRMI.MyConnectionID >0 && foldGiocatori.get(ServerRMI.MyConnectionID -1)== true) disegna2(g2d, w/20, w/15, w/2-w/18 -offset, h/2+h*3/8-w/15-h/40);
            else disegna(g2d, w/20, w/15, w/2-w/18 -offset, h/2+h*3/8-w/15-h/40, LoadImage(pescata1+".png"));

	    }

	    if (cartaPescata2 == true){

            if(ServerRMI.MyConnectionID >0 && foldGiocatori.get(ServerRMI.MyConnectionID -1)== true) disegna2(g2d, w/20, w/15, w/2-offset, h/2+h*3/8-w/15-h/40);
	        else disegna(g2d, w/20, w/15, w/2-offset, h/2+h*3/8-w/15-h/40, LoadImage(pescata2+".png"));

	       disegna(g2d, w/25, w/15, w/2+w/18-offset, h/2+h*3/8-w/15-h/40, LoadImage("chip.png"));

        g2d.setColor(Color.BLACK);
        if (ServerRMI.Sconfitto == false){
    	    g2d.drawString(Integer.toString(ServerRMI.stack[ServerRMI.MyConnectionID -1 ]), w/2+w/18-offset+w/23 , h/2+h*3/8-w/30-h/40);
        }
       

	g2d.setColor(Color.BLACK);

	   DisegnaChips(g2d, trans, ServerRMI.utentiTavolo, w, h);
            
	    disegna(g2d, w/20, w/15, w/2-w/9-offset, h/2-w/30, LoadImage(flop1+".png"));
	    disegna(g2d, w/20, w/15, w/2-w/18-offset, h/2-w/30, LoadImage(flop2+".png"));
	    disegna(g2d, w/20, w/15, w/2-offset, h/2-w/30, LoadImage(flop3+".png"));
	    disegna(g2d, w/20, w/15, w/2+w/18-offset, h/2-w/30, LoadImage(cartaTurn+".png"));
	    disegna(g2d, w/20, w/15, w/2+w/9-offset, h/2-w/30, LoadImage(cartaRiver+".png"));


     }
	   if (ServerRMI.MyConnectionID != 0 && ServerRMI.puntateMano[(ServerRMI.MyConnectionID -1 )]>0 || firstSet[0] == 1){
			disegna(g2d, w/30, w/30, xC[0], yC[0], LoadImage("singlechip.png"));
			g2d.setColor(Color.BLACK);
			if (ServerRMI.MyConnectionID > 0 && ServerRMI.puntateMano[(ServerRMI.MyConnectionID -1 )]>0)
				g2d.drawString(Integer.toString(ServerRMI.puntateMano[ServerRMI.MyConnectionID -1 ]), sxC[0]+w/26, syC[0]+w/60);		
		}
		if (ServerRMI.MyConnectionID>0 && risultati.get((ServerRMI.MyConnectionID-1)) != null){
			 currentFont = g2d.getFont();
			 newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
       			 g2d.setFont(newFont);
			 g2d.drawString(risultati.get((ServerRMI.MyConnectionID-1)), sxC[0], syC[0]+w/30);
			 g2d.setFont(currentFont);
			}

	 if (Piatto == true && ServerRMI.statoMano != 0){
	    disegna(g2d, w/30, w/30, w/2+w/9+w/30, h/2-w/60, LoadImage("piatto.png"));
		g2d.setColor(Color.BLACK);
		g2d.drawString(Integer.toString(ServerRMI.piattoTavolo), w/2+w/9+w/28, h/2-w/60-h/80);

	   }
	}
	}catch(Exception exeption){
		exeption.printStackTrace();
		System.out.println("what happened?");
		try{
					 TimeUnit.MILLISECONDS.sleep(100+rand.nextInt(500));
					}
					catch(Exception ex){
					 System.exit(1);
					}
	}	    
   }
   
 }
