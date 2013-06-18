package src.com.github.propa13_orga.gruppe71;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.KeyAdapter;

public class NLobby extends JFrame{
	private static final long serialVersionUID = 1L;

	private JButton bExit;
	
	/**
	 * Initialisiert die Netzwerk-Lobby
	 */
	public NLobby(){
		
		//Fenster-Eigenschaften werden gesetzt
		this.setSize(300, 200);
		this.setLocation(350, 150) ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Netzwerk Lobby");
		this.setResizable(false);
		this.setLayout(null);
		

		//Exit-Knopf
		bExit = new JButton("EXIT") ;
		bExit.setBounds(90,100, 100, 50) ;
		bExit.addActionListener(new TestListenerExit());
		add(bExit);
		
		this.setVisible(true);
		
		JOptionPane.showMessageDialog(null, "Spiel erstellen oder Spiel beitreten?");
		
		//Spiel gefunden?
		
		dispose();
		NSteuerung NetzwerkSteuerung = new NSteuerung(true);
		
	}
	
	/**
	 * Listener fuer den ExitKnopf
	 */
	private class TestListenerExit implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			DStartMenu StartMenu = new DStartMenu() ;
		}
	}
}
