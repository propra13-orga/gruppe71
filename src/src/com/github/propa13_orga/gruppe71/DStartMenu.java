package src.com.github.propa13_orga.gruppe71;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class DStartMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	
	JButton bStart;
	JButton bExit;
	
	/**
	 * Oeffnet das Startmenu des Spiels
	 */
	public DStartMenu() {
	//Fenster-Eigenschaften werden gesetzt
	setSize(600, 400) ;
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
	setLocation(200, 200) ;
	setResizable(false);
	setLayout(null) ;
	
	//Start-Knopf
	bStart = new JButton("Start") ;
	bStart.setBounds(50, 50, 100, 100) ;
	bStart.addActionListener(new TestListenerStart());
	
	add(bStart) ;
	
	//Exit-Knopf
	bExit = new JButton("Exit") ;
	bExit.setBounds(200, 50, 100, 100) ;
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