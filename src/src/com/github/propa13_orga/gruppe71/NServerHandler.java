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
import java.util.Arrays;

/**
* Klasse des Netzwerkhandlers fuer den Server, jede Verbindung ein Handler
*/
public class NServerHandler implements Runnable{
	private static final long serialVersionUID = 1L;

	private Socket HostSocket;
	private NPanel SpielPanel;

	private String[][] ClientQueues;
	private int CurrentClient;
	
	public NServerHandler(String[][] pClientQueues, Socket pHostSocket, NPanel pSpielPanel){
		this.ClientQueues = pClientQueues;
		this.HostSocket = pHostSocket;
		this.CurrentClient = -1;
		
		this.SpielPanel = pSpielPanel;
	}
	
	  @Override
	  public void run()
	  {

		try {
				DataInputStream HostIn = new DataInputStream(this.HostSocket.getInputStream());
				DataOutputStream HostOut = new DataOutputStream(this.HostSocket.getOutputStream());
						
				
				String ClientMessage = HostIn.readUTF();
				System.out.println("Server Received: "+ClientMessage);


				if(ClientMessage.contains("NEW LEVEL RESET") == true){
					this.resetQueues();
					HostOut.writeUTF("NEW LEVEL RESET OK");

				}else{
				
				if(ClientMessage.contains("QUERY") == true){
					if(ClientMessage.contains("CLIENT1") == true){
						this.CurrentClient = 1;
					}else{
						this.CurrentClient = 0;
					}
					
					if(this.ClientQueues[this.CurrentClient][0] == null){
						HostOut.writeUTF("NOTHING");
					}else{
						System.out.println("CLIENTQUEUES BEFORE OUTPUT:\n"+Arrays.deepToString(this.ClientQueues[this.CurrentClient]));
						
						HostOut.writeUTF(this.ClientQueues[this.CurrentClient][0]);

						
						this.ClientQueues[this.CurrentClient][0] = null;
						for(int i = 0; i < 98; i++){
							if(this.ClientQueues[this.CurrentClient][(i+1)] != null){
								this.ClientQueues[this.CurrentClient][i] = this.ClientQueues[this.CurrentClient][(i+1)];
								this.ClientQueues[this.CurrentClient][(i+1)] = null;
							}else{
								i = 98;
							}
						}
						
						System.out.println("CLIENTQUEUES AFTER OUTPUT:\n"+Arrays.deepToString(this.ClientQueues[this.CurrentClient]));

					}
				}else{

					

					if(ClientMessage.contains("JOIN") == true){
						this.SpielPanel.setConnected(true);
					}else if(ClientMessage.contains("HOST HOST") == true){
						this.SpielPanel.resetClientMessage();
					}else if(ClientMessage.contains("HOST HOST") == false){
					
						
						
					if(ClientMessage.contains("CLIENT1") == true){
						this.CurrentClient = 0;
					}else{
						this.CurrentClient = 1;
					}
					
					System.out.println("CLIENTQUEUES BEFORE INPUT:\n"+Arrays.deepToString(this.ClientQueues[this.CurrentClient]));

					boolean AlreadyInQueue = false;
					
					for(int i = 0; i < 100; i++){
						if(this.ClientQueues[this.CurrentClient][i] != null){
							if(this.ClientQueues[this.CurrentClient][i] == ClientMessage){
								AlreadyInQueue = true;
								i = 100;
							}
						}else{
							i=100;
						}
					}
					
					if(AlreadyInQueue == false){
						for(int i = 0; i < 98; i++){
							if(this.ClientQueues[this.CurrentClient][i] == null){
								this.ClientQueues[this.CurrentClient][i] = ClientMessage;
								i = 98;
							}
						}
					}
					System.out.println("CLIENTQUEUES AFTER INPUT:\n"+Arrays.deepToString(this.ClientQueues[this.CurrentClient]));

					
					}
					HostOut.writeUTF(ClientMessage+" OK");
					
					
				}
				}
				this.HostSocket.close();
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	  /**
	   * Resettet die ClientQueues
	   */
	  public void resetQueues(){
		  for(int a = 0; a < 2; a++){
			for(int i = 0; i < 100; i++){
			  	this.ClientQueues[a][i] = null;
		  	}
		  }
	  }
}
