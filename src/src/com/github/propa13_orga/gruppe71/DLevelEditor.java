package src.com.github.propa13_orga.gruppe71;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;

public class DLevelEditor {

	private JFrame LevelFenster;
	
	
	//private DPanel LevelPanel;
	/**
	 * Initialisiert das LevelFenster Fenster
	 */
	public DLevelEditor(){
		
		
		this.LevelFenster = new JFrame();
		
		//Fenster-Eigenschaften werden gesetzt
		this.LevelFenster.setSize(610, 520);
		this.LevelFenster.setLocation(350, 150) ;
		this.LevelFenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.LevelFenster.setTitle("DungeonCrawler");
		this.LevelFenster.setResizable(false);
		this.LevelFenster.setLayout(null);
		
		//Panel zum Fenster hinzugefuegt, hier wird das LevelFenster gemalt
		final DLevelPanel LevelPanel = new DLevelPanel(this.LevelFenster);
		LevelPanel.setBounds(0, 0, 610, 360);
		LevelPanel.setLayout(null);
		this.LevelFenster.setContentPane(LevelPanel);
		
		
		//Gebe dem Panel Focus, so dass es Tasteneingaben erkennt
		LevelPanel.setFocusable( true );
	   
		// Alles fertig also kann das Fenster gemalt werden
		LevelFenster.setVisible(true);
	}
	
}
