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
	JButton bRules;
	
	/**
	 * Oeffnet das Startmenu des Spiels
	 */
	public DStartMenu() {
	//Fenster-Eigenschaften werden gesetzt
	setSize(600, 400) ;
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
	setTitle("Dungeon Crawler Menu");
	setLocation(350, 150) ;
	setResizable(false);
	setLayout(null) ;
	
	//Single Modi -Knopf
	bStart = new JButton("Single") ;
	bStart.setBounds(50, 50, 100, 100) ;
	bStart.addActionListener(new TestListenerStart());
	add(bStart) ;
	
	//Regel - Knopf
	bRules=new JButton("Rules");
	bRules.setBounds(225,200,100,100);
	bRules.addActionListener(new TestListenerRules());
	add(bRules);
	
	//Multiplayer -Knopf
	bMulti=new JButton("Multi");
	bMulti.setBounds(225,50,100,100);
	bMulti.addActionListener(null);
	bMulti.addActionListener(new TestListenerMulti());
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
	private class TestListenerStart implements ActionListener { //Single
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose(); // schliesst Menu
			DSteuerung fSpiel = new DSteuerung(1);
			
		}
	}
private class TestListenerMulti implements ActionListener { //Multi
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose(); // schliesst Menu
			DSteuerung fSpiel2 = new DSteuerung(2);
			
		}
	}
	private class TestListenerRules implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			//DRules rules=new DRules();
			
			
			
			
			
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