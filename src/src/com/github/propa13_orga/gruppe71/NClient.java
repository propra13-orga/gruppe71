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
* Klasse des Clients beim Netzwerkspiel
*/
public class NClient implements Runnable{
	private static final long serialVersionUID = 1L;
	
	private NPanel SpielPanel;
	private String IsHostName;
	
	public NClient(NPanel pPanel){
		this.SpielPanel = pPanel;
		
		if(this.SpielPanel.isHost() == true)
			this.IsHostName = "HOST";
		else
			this.IsHostName = "CLIENT1";
	}
	
	/**
	 * Initialisiert den Netzwerk-Server
	 */
	public void run(){
		
		while(true){
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		//System.out.println("NClient run: "+this.SpielPanel.getClientMessage());
		
		if(this.SpielPanel.getClientMessage() != ""){ //Wenn Befehl zu senden ist
		try {
			
			Socket ClientSocket = new Socket(this.SpielPanel.getClientIPAddress(), 36182);

			DataInputStream ClientIn = new DataInputStream(ClientSocket.getInputStream());
			DataOutputStream ClientOut = new DataOutputStream(ClientSocket.getOutputStream());
			
			ClientOut.writeUTF(this.IsHostName+" "+this.SpielPanel.getClientMessage());
			
			String HostResponse = ClientIn.readUTF();
			System.out.println(ClientSocket.getInetAddress().toString().replace("/", "")+": "+HostResponse);
			this.SpielPanel.resetClientMessage();
			this.SpielPanel.setClientResponse(HostResponse);
			
			ClientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else{
			//Checke, ob es einen neuen Befehl vom anderen gibt
			try 
			{
				Socket ClientSocket = new Socket(this.SpielPanel.getClientIPAddress(), 36182);

				DataInputStream ClientIn = new DataInputStream(ClientSocket.getInputStream());
				DataOutputStream ClientOut = new DataOutputStream(ClientSocket.getOutputStream());
				
				ClientOut.writeUTF(this.IsHostName+" QUERY");
				
				String HostResponse = ClientIn.readUTF();
				//System.out.println("Server: "+HostResponse);
				
				ClientSocket.close();
					
				if(HostResponse.contains("DO") == true){
					int TmpDynamicObjectIndex = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("DO")+2),HostResponse.indexOf(" ", (HostResponse.indexOf("DO")+2)))));
						
					if(HostResponse.contains("ACTION") == true){
						if(HostResponse.contains("ACTION 0") == true){
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).Action(0,true);
						}else if(HostResponse.contains("ACTION 1") == true){
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).Action(1,true);	
						}else if(HostResponse.contains("ACTION 2") == true){
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).Action(2,true);	
						}else if(HostResponse.contains("ACTION 3") == true){
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).Action(3,true);	
						}else if(HostResponse.contains("ACTION 4") == true){
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).Action(4,true);	
						}
					}else if(HostResponse.contains("CHANGEWEAPON") == true){
						
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).changeWeapon(true);	
						
					}else if(HostResponse.contains("ATTRIBUTES") == true){
						
						int TmpLives = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("L:")+2),HostResponse.indexOf(" ", (HostResponse.indexOf("L:")+2)))));
						int TmpHealth = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("H:")+2),HostResponse.indexOf(" ", (HostResponse.indexOf("H:")+2)))));
						int TmpMoney = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("G:")+2),HostResponse.indexOf(" ", (HostResponse.indexOf("G:")+2)))));
						int TmpMana = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("M:")+2),HostResponse.indexOf(" ", (HostResponse.indexOf("M:")+2)))));
						int TmpActiveItem = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("AI:")+3),HostResponse.length())));

						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setLives2(TmpLives);
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setHealth2(TmpHealth);
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setMoney2(TmpMoney);
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setMana2(TmpMana);
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setActiveItem(TmpActiveItem);
						
					}else if(HostResponse.contains("ITEMS") == true){

						int[] TmpItems = new int[6];
						String[] TmpName = new String[6];

						TmpItems[0] = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("I0:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("I0:")+3)))));
						TmpItems[1] = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("I1:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("I1:")+3)))));
						TmpItems[2] = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("I2:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("I2:")+3)))));
						TmpItems[3] = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("I3:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("I3:")+3)))));
						TmpItems[4] = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("I4:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("I4:")+3)))));
						TmpItems[5] = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("I5:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("I5:")+3)))));
						TmpName[0] = HostResponse.substring((HostResponse.indexOf("N0:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("N0:")+3)));
						TmpName[1] = HostResponse.substring((HostResponse.indexOf("N1:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("N1:")+3)));
						TmpName[2] = HostResponse.substring((HostResponse.indexOf("N2:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("N2:")+3)));
						TmpName[3] = HostResponse.substring((HostResponse.indexOf("N3:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("N3:")+3)));
						TmpName[4] = HostResponse.substring((HostResponse.indexOf("N4:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("N4:")+3)));
						TmpName[5] = HostResponse.substring((HostResponse.indexOf("N5:")+3),HostResponse.length());

						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setItems2(TmpItems);
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setName2(TmpName);
						
					}else{

						int TmpCurrentXPos = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("X:")+2),HostResponse.indexOf(" ", (HostResponse.indexOf("X:")+2)))));
						int TmpCurrentYPos = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("Y:")+2),HostResponse.indexOf(" ", (HostResponse.indexOf("Y:")+2)))));

						int TmpMoveToXPos = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("MX:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("MX:")+3)))));
						int TmpMoveToYPos = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("MY:")+3),HostResponse.length())));
						
						boolean TmpMoves;
						if((TmpCurrentXPos == TmpMoveToXPos && TmpCurrentYPos == TmpMoveToYPos)||(TmpMoveToXPos==-1 && TmpMoveToYPos == -1))
							TmpMoves = false;
						else
							TmpMoves = true;
						
						while(this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).moves == true){
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).AnimateMoving();
						}

						
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setCurrentPosition(TmpCurrentXPos, TmpCurrentYPos);
						
						//System.out.println("FILTERED POSITIONS X:"+TmpCurrentXPos+" Y:"+TmpCurrentYPos+" MX:"+TmpMoveToXPos+" MY:"+TmpMoveToYPos);
						
						if(HostResponse.contains("UP") == true){
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).moveTo("up",true);
							
						}else if(HostResponse.contains("DOWN") == true){
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).moveTo("down",true);
							
						}else if(HostResponse.contains("LEFT") == true){
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).moveTo("left",true);
							
						}else if(HostResponse.contains("RIGHT") == true){
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).moveTo("right",true);
							
						}
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		}

	}
	
}
