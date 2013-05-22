package src.com.github.propa13_orga.gruppe71;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class DStartMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	
	JButton bStart;
	JButton bExit;
	JButton bMulti;
	
	/**
	 * Oeffnet das Startmenu des Spiels
	 */
	public DStartMenu() {
	//Fenster-Eigenschaften werden gesetzt
	setSize(600, 400) ;
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
	setTitle("Dungeon Crawler Startmenü");
	setLocation(350, 150) ;
	setResizable(false);
	setLayout(null) ;
	
	//Single Modi -Knopf
	bStart = new JButton("Single") ;
	bStart.setBounds(50, 50, 100, 100) ;
	bStart.addActionListener(new TestListenerStart());
	add(bStart) ;
	
	//Multiplayer -Knopf
	bMulti=new JButton("Multi");
	bMulti.setBounds(225,50,100,100);
	bMulti.addActionListener(null);
	add(bMulti);
	
	//Exit-Knopf
	bExit = new JButton("EXIT") ;
	bExit.setBounds(400, 50, 100, 100) ;
	bExit.addActionListener(new TestListenerExit());
	add(bExit);
	
	//Alles ist fertig, Fenster kann gemalt werden
	setVisible(true) ;
	}
	
	/**
	 * Oeffnet Spielfeld
	 */
	private class TestListenerStart implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose(); // schliesst Menu
			DSteuerung fSpiel = new DSteuerung();
			
		}
	}
	
	//Schliesst Menu
	private class TestListenerExit implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);          //Schliesst komplettes Programm
			
		}
	}
	
	}