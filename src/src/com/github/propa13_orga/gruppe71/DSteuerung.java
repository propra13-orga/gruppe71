package src.com.github.propa13_orga.gruppe71;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.awt.event.KeyAdapter;

public class DSteuerung {

	/**
	 * Initialisiert das Spielfeld Fenster
	 */
	public DSteuerung(){
		JFrame jf = new JFrame();
		
		//Fenster-Eigenschaften werden gesetzt
		jf.setSize(610, 390);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setTitle("DungeonCrawler");
		jf.setResizable(false);
		
		//Panel zum Fenster hinzugefuegt, hier wird das Spielfeld gemalt
		final DPanel panel = new DPanel();
		jf.setContentPane(panel);
		
		//Lade einen Level in das Spielfeld
		panel.loadStaticObjects("src/src/com/github/propa13_orga/gruppe71/level.txt");
		
		//Gebe dem Panel Focus, so dass es Tasteneingaben erkennt
		panel.setFocusable( true );
	    
		//Setze Listener damit die gedrueckte Taste erkannt werden kann
	    panel.addKeyListener(new KeyAdapter(){
	    
	    	/*Methode für Taste herunter gedrueckt
    		 * (non-Javadoc)
    		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
    		 */
    		public void keyPressed(KeyEvent e){

	    		if(e.getKeyCode()==KeyEvent.VK_UP)
	    		{
	    			panel.dynamicObjectMove(0, "up"); //Bewege Spieler 1 hoch
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
	    		{
	    			panel.dynamicObjectMove(0, "down"); //Bewege Spieler 1 runter	
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_LEFT)
	    		{
	    			panel.dynamicObjectMove(0, "left"); //Bewege Spieler 1 nach links
	    		}
	    		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
	    		{
	    			panel.dynamicObjectMove(0, "right"); //Bewege Spieler 1 nach rechts
	    		}
	    			else 
	    			{	
	    				System.out.println("Keine Lauftaste 1"); // tue nichts	
	    			}
	    	}
	    		
	    		/*Methode für Taste loslassen
	    		 * (non-Javadoc)
	    		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	    		 */
	    		
	    		public void keyReleased(KeyEvent e) {  
	    			
	    			if(e.getKeyCode()==KeyEvent.VK_UP)
	    			{	
	    				System.out.println("Release Hoch");
	    			}
	    			else if(e.getKeyCode()==KeyEvent.VK_DOWN)
	    			{
	    				System.out.println("Release Unten");
	    			}
	    			else if(e.getKeyCode()==KeyEvent.VK_LEFT)
	    			{
	    				System.out.println("Release Links");
	    			}
	    			else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
	    			{
	    				System.out.println("Release Rechts");
	    			}
	    				else
	    				{
	    					System.out.println("Keine Lauftaste");
	    				}
	    			
	    		}
	       });

		// Alles fertig also kann das Fenster gemalt werden
		jf.setVisible(true);
	}

}
