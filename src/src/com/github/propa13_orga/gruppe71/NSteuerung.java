package src.com.github.propa13_orga.gruppe71;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;

import java.io.*;
import java.net.*;

/**
* Klasse der Steuerung im Netzwerkspiel 
*/
public class NSteuerung {

	public boolean IsHost;

	private NClient SpielServer;
	public JFrame SpielFenster;
	
	/**
	 * Initialisiert das SpielFenster Fenster
	 */
	public NSteuerung(){
				
		Object[] options = {"Spiel beitreten", "Spiel hosten"};
		
		int HostorJoin = JOptionPane.showOptionDialog(null,"Moechten Sie einem Spiel beitreten oder ein neues hosten?","",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
		
		final NPanel SpielPanel;
		
		this.SpielFenster = new JFrame();
		
		//Fenster-Eigenschaften werden gesetzt
		this.SpielFenster.setSize(610, 580);
		this.SpielFenster.setLocation(350, 150) ;
		this.SpielFenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.SpielFenster.setTitle("DungeonCrawler");
		this.SpielFenster.setResizable(false);
		this.SpielFenster.setLayout(null);
		

		if(HostorJoin == 0){
			this.IsHost = false;
		}else{
			this.IsHost = true;
		}
		
		//Panel zum Fenster hinzugefuegt, hier wird das SpielFenster gemalt
		SpielPanel = new NPanel(this.SpielFenster,this.IsHost);
		SpielPanel.setBounds(0, 0, 610, 360);
		SpielPanel.setLayout(null);
		this.SpielFenster.setContentPane(SpielPanel);
		
		
		if(this.IsHost == true){
			Thread ServerThread = new Thread(new NServer(SpielPanel));
			ServerThread.start();
		}
		
		
		final NHUDPanel InfoLeiste = new NHUDPanel(this.SpielFenster, SpielPanel);
		InfoLeiste.setBounds(0, 360, 610, 220);
		InfoLeiste.setLayout(null);
		this.SpielFenster.add(InfoLeiste);
		
		
		//Gebe dem Panel Focus, so dass es Tasteneingaben erkennt
		SpielPanel.setFocusable( true );
		
		//Setze Listener damit die gedrueckte Taste erkannt werden kann
		SpielPanel.addKeyListener(new KeyAdapter(){
	    	/*Methode fuer Taste herunter gedrueckt
    		 * (non-Javadoc)
    		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
    		 */
    		public void keyPressed(KeyEvent e){

	    		if(e.getKeyCode()==KeyEvent.VK_UP)
	    		{
	    			if(SpielPanel.isHost() == true){
	    				SpielPanel.getDynamicObject(0).moveTo("up",false); //Bewege Spieler 1 runter
	    			}else{
	    				SpielPanel.getDynamicObject(1).moveTo("up",false); //Bewege Spieler 2 runter
	    			}	
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
	    		{
	    			if(SpielPanel.isHost() == true){
	    				SpielPanel.getDynamicObject(0).moveTo("down",false); //Bewege Spieler 1 runter
	    			}else{
	    				SpielPanel.getDynamicObject(1).moveTo("down",false); //Bewege Spieler 2 runter
	    			}	
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_LEFT)
	    		{
	    			if(SpielPanel.isHost() == true){
	    				SpielPanel.getDynamicObject(0).moveTo("left",false); //Bewege Spieler 1 nach links
	    			}else{
	    				SpielPanel.getDynamicObject(1).moveTo("left",false); //Bewege Spieler 2 nach links
	    			}
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
	    		{
	    			if(SpielPanel.isHost() == true){
	    				SpielPanel.getDynamicObject(0).moveTo("right",false); //Bewege Spieler 1 nach rechts
	    			}else{
	    				SpielPanel.getDynamicObject(1).moveTo("right",false); //Bewege Spieler 2 nach rechts
	    			}
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_M)
	    		{
	    			if(SpielPanel.isHost() == true){
		    			SpielPanel.getDynamicObject(0).Action(0,false); //Zaubern Spieler 1
	    			}else{
		    			SpielPanel.getDynamicObject(1).Action(0,false); //Zaubern Spieler 2
	    			}
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_N)
	    		{
	    			if(SpielPanel.isHost() == true){
		    			SpielPanel.getDynamicObject(0).Action(1,false); //Waffe benutzen Spieler 1
	    			}else{
		    			SpielPanel.getDynamicObject(1).Action(1,false); //Waffe benutzen Spieler 2
	    			}
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_B)
	    		{
	    			if(SpielPanel.isHost() == true){
		    			SpielPanel.getDynamicObject(0).changeWeapon(false); //Waffe wechseln Spieler 1
	    			}else{
		    			SpielPanel.getDynamicObject(1).changeWeapon(false); //Waffe wechseln Spieler 2
	    			}
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_T) //TEST KNOPF T
	    		{
	    			// Wenn man T drueckt wird was hier steht ausgefuehrt
    				SpielPanel.loadNextLevel();
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_I) //TEST KNOPF I
	    		{
	    			// Wenn man T drueckt wird was hier steht ausgefuehrt
    				SpielPanel.getDynamicObject(0).ItemBag();
	    		}
	else if(e.getKeyCode()==KeyEvent.VK_F) //Spiel laden
    	    	{
				if(SpielPanel.isHost() == true){
    	    		// Wenn man F drueckt wird was hier steht ausgefuehrt
        			SpielPanel.getDynamicObject(0).SkillBaum();
        			
	    		}
				else{
				SpielPanel.getDynamicObject(0).SkillBaum();
				}	
    	    	}
	    			else 
	    			{	
	    				System.out.println("Keine Lauftaste 1"); // tue nichts	
	    			}
	    	}
	       });
		
		// Alles fertig also kann das Fenster gemalt werden
		SpielFenster.setVisible(true);
		
	}
	
	/**
	 * Gibt zurueck, ob Host oder Client?
	 *   
	 */
	public boolean isHost(){
		  return this.IsHost;
	}

	
}
