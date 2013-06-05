package src.com.github.propa13_orga.gruppe71;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.awt.event.KeyAdapter;

public class DSteuerung {

	private JFrame SpielFenster;
	
	
	//private DPanel SpielPanel;
	/**
	 * Initialisiert das SpielFenster Fenster
	 */
	public DSteuerung(int pAnzahlSpieler){
		
		this.SpielFenster = new JFrame();
		
		//Fenster-Eigenschaften werden gesetzt
		this.SpielFenster.setSize(610, 390);
		this.SpielFenster.setLocation(350, 150) ;
		this.SpielFenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.SpielFenster.setTitle("DungeonCrawler");
		this.SpielFenster.setResizable(false);
		
		//Panel zum Fenster hinzugefuegt, hier wird das SpielFenster gemalt
		final DPanel SpielPanel = new DPanel(SpielFenster, pAnzahlSpieler);
		this.SpielFenster.setContentPane(SpielPanel);
		
		//Gebe dem Panel Focus, so dass es Tasteneingaben erkennt
		SpielPanel.setFocusable( true );
		
		//final DDynamic[] Spieler = SpielPanel.getDynamicObjects();
	    
		//Setze Listener damit die gedrueckte Taste erkannt werden kann
	    SpielPanel.addKeyListener(new KeyAdapter(){
	    
	    	/*Methode für Taste herunter gedrueckt
    		 * (non-Javadoc)
    		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
    		 */
    		public void keyPressed(KeyEvent e){

	    		if(e.getKeyCode()==KeyEvent.VK_UP)
	    		{
	    			SpielPanel.getDynamicObject(0).moveTo("up"); //Bewege Spieler 1 hoch
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
	    		{
	    			SpielPanel.getDynamicObject(0).moveTo("down"); //Bewege Spieler 1 runter	
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_LEFT)
	    		{
	    			SpielPanel.getDynamicObject(0).moveTo("left"); //Bewege Spieler 1 nach links
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
	    		{
	    			SpielPanel.getDynamicObject(0).moveTo("right"); //Bewege Spieler 1 nach rechts
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_M)
	    		{
	    			SpielPanel.getDynamicObject(0).Action(0); //Zaubern
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_W)
	    		{
	    			SpielPanel.getDynamicObject(1).moveTo("up"); //Bewege Spieler 1 hoch
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_S)
	    		{
	    			SpielPanel.getDynamicObject(1).moveTo("down"); //Bewege Spieler 1 runter	
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_A)
	    		{
	    			SpielPanel.getDynamicObject(1).moveTo("left"); //Bewege Spieler 1 nach links
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_D)
	    		{
	    			SpielPanel.getDynamicObject(1).moveTo("right"); //Bewege Spieler 1 nach rechts
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_T) //TEST KNOPF T
	    		{
	    			// Wenn man T drueckt wird was hier steht ausgefuehrt
    				SpielPanel.loadNextLevel();
	    		}
	    			else 
	    			{	
	    				System.out.println("Keine Lauftaste 1"); // tue nichts	
	    			}
	    	}
	    		
	    		/**Methode für Taste loslassen
	    		 * (non-Javadoc)
	    		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	    		 */
	    		
	    		public void keyReleased(KeyEvent e) {  
	    			
	    			if(e.getKeyCode()==KeyEvent.VK_UP)
	    			{	
	    				
	    			}
	    			else if(e.getKeyCode()==KeyEvent.VK_DOWN)
	    			{
	    			
	    			}
	    			else if(e.getKeyCode()==KeyEvent.VK_LEFT)
	    			{
	    			
	    			}
	    			else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
	    			{
	    			
	    			}
	    				else
	    				{
	    			
	    				}
	    			
	    		}
	       });

		// Alles fertig also kann das Fenster gemalt werden
		SpielFenster.setVisible(true);
	}
	
}
