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

import java.io.*;
import java.net.*;

public class NLobby extends JFrame{
	private static final long serialVersionUID = 1L;

	private ServerSocket HostServerSocket;
	private Socket HostSocket;
	
	private Socket ClientSocket;
	
	private JButton bExit;
	private boolean isServer;
	
	private NSteuerung NetzwerkSteuerung;
	
	/**
	 * Initialisiert die Netzwerk-Lobby
	 */
	public NLobby(){

		NSteuerung NetzwerkSteuerung = new NSteuerung();
		
		/*
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
		
		
		Object[] options = {"Spiel beitreten", "Spiel hosten"};
		
		int HostorJoin = JOptionPane.showOptionDialog(this,"Moechten Sie einem Spiel beitreten oder ein neues hosten?","",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
		
		
		if(HostorJoin == 0){
			this.isServer = false;

			try {
				this.ClientSocket = new Socket("192.168.1.83", 36182);
				DataInputStream dataIn = new DataInputStream(this.ClientSocket.getInputStream());
				DataOutputStream dataOut = new DataOutputStream(this.ClientSocket.getOutputStream());
				
				dataOut.writeUTF("JOIN");
				String HostResponse = dataIn.readUTF();

				System.out.println("Host: " + HostResponse);
				

				if(HostResponse.contains("JOIN OK")){
					//JOptionPane.showMessageDialog(null, "Message("+HostResponse.length()+"): "+HostResponse);
					dispose();

					DSteuerung TestSteuerung = new DSteuerung(2);
					//this.NetzwerkSteuerung = new NSteuerung(true, this.ClientSocket);
				}
				
				} catch (UnknownHostException e) {
				e.printStackTrace();
				} catch (IOException e) {
				e.printStackTrace();
				}
			
		}else{
			this.isServer = true;
			
				try {
				this.HostServerSocket = new ServerSocket(36182);
				
				this.HostSocket = this.HostServerSocket.accept();
				DataInputStream dataIn = new DataInputStream(this.HostSocket.getInputStream());
				DataOutputStream dataOut = new DataOutputStream(this.HostSocket.getOutputStream());
				
				String ClientQuery = dataIn.readUTF();
				System.out.println("Client: "+ClientQuery);
				dataOut.writeUTF(ClientQuery+" OK");
				
				if(ClientQuery.contains("JOIN") == true){
					//JOptionPane.showMessageDialog(null, "Message("+ClientQuery.length()+"): "+ClientQuery);
					dispose();

					DSteuerung TestSteuerung = new DSteuerung(2);
					
					this.NetzwerkSteuerung = new NSteuerung(true, this.HostSocket);
				}
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}*/
		/*
		dispose();
		NSteuerung NetzwerkSteuerung = new NSteuerung(true);
		*/
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
