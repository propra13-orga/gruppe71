package src.com.github.propa13_orga.gruppe71;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;

public class DLevelEditor extends JFrame{
	

    private javax.swing.Timer timer;
    private int xPos, yPos;
	
	/**
	 * Initialisiert das LevelFenster Fenster
	 */
	public DLevelEditor(){
		
		//Fenster-Eigenschaften werden gesetzt
		this.setSize(610, 520);
		this.setLocation(350, 150) ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("DungeonCrawler");
		this.setResizable(false);
		this.setLayout(null);
		
		//Panel zum Fenster hinzugefuegt, hier wird das LevelFenster gemalt
		final DLevelPanel LevelPanel = new DLevelPanel(this);
		LevelPanel.setBounds(0, 0, 610, 360);
		LevelPanel.setLayout(null);
		LevelPanel.addMouseListener(new MouseListener() {
	            @Override
	            public void mouseReleased(MouseEvent e) {
	                System.out.println(":MOUSE_RELEASED_EVENT:");
	            }
	            @Override
	            public void mousePressed(MouseEvent e) {
	                xPos = e.getX();
	                yPos = e.getY();
	                
	                xPos = ((xPos - (xPos % 30))/30);
	                yPos = ((yPos - (yPos % 30))/30);
	                
	                String TmpName = LevelPanel.DigittoName(LevelPanel.getStaticObject(xPos, yPos).getType());
	                
	                System.out.println("----------------------------------\n:MOUSE_PRESSED_EVENT X:"+xPos+" Y:"+yPos+" Name: "+TmpName);

	            }
	            @Override
	            public void mouseExited(MouseEvent e) {
	                System.out.println(":MOUSE_EXITED_EVENT:");
	            }
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                System.out.println(":MOUSE_ENTER_EVENT:");
	            }
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                System.out.println(":MOUSE_CLICK_EVENT:");
	            }
	        });

		this.setContentPane(LevelPanel);
		
		
		//Gebe dem Panel Focus, so dass es Tasteneingaben erkennt
		LevelPanel.setFocusable( true );
	   
		// Alles fertig also kann das Fenster gemalt werden
		this.setVisible(true);
	}
	
}
