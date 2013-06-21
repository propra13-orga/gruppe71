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

public class NServer implements Runnable{
	private static final long serialVersionUID = 1L;

	private ServerSocket HostServerSocket;
	private NPanel SpielPanel;

	private String[][] ClientQueues;
	
	public NServer(NPanel pSpielPanel){
		this.ClientQueues = new String[2][100];
		this.SpielPanel = pSpielPanel;
	}
	

	  @Override
	  public void run()
	  {
	    this.HostServerSocket = null;

	    //An Port 13000 binden:
	    try
	    {
	    	this.HostServerSocket = new ServerSocket(36182);
	    }
	    catch (IOException e)
	    {
	      System.out.println("Server couldnt be started, another Server running already?: " + e.getMessage());
	      this.SpielPanel.SpielFenster.dispose();
	      new DStartMenu();
	    }
	    
	    //In einer Endlosschleife auf eingehende Anfragen warten.
	    while (true)
	    {
	      try
	      {
	        //Blocken, bis eine Anfrage kommt:
	        System.out.println ("Server: Waiting for Connect");
	        Socket NewSocket = this.HostServerSocket.accept();
	        
	        //Wenn die Anfrage da ist, dann wird ein Thread gestartet, der 
	        //die weitere Verarbeitung übernimmt.
	        System.out.println ("Server: Client Connected");
	        Thread SocketThread = new Thread(new NServerHandler(this.ClientQueues,NewSocket, this.SpielPanel) );
	        SocketThread.start();
			
			System.out.println ("Redirected Client to SocketThread...");
	      }
	      catch (IOException e)
	      {
		      System.out.println("Socket couldnt be started: " + e.getMessage());
		      this.SpielPanel.SpielFenster.dispose();
		      new DStartMenu();
	      }
	    }
	}
}

