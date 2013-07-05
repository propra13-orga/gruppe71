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

/**
* Klasse der Netzwerklobby
*/
public class NLobby extends JFrame{
	private static final long serialVersionUID = 1L;

	private boolean isServer;
	
	private NSteuerung NetzwerkSteuerung;
	
	/**
	 * Initialisiert die Netzwerk-Lobby
	 */
	public NLobby(){

		NSteuerung NetzwerkSteuerung = new NSteuerung();
	}
}
