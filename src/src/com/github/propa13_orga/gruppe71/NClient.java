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
				Thread.sleep(50);
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
					

				if(HostResponse.contains("NEW LEVEL RESET") == true){
					this.SpielPanel.resetAllClientMessages();
				}else{
				
				if(HostResponse.contains("DO") == true){
					int TmpDynamicObjectIndex = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("DO")+2),HostResponse.indexOf(" ", (HostResponse.indexOf("DO")+2)))));
						
					if(TmpDynamicObjectIndex >= 0 && TmpDynamicObjectIndex < 50){
					
					if(HostResponse.contains("SETHIDDEN") == true){

						if(HostResponse.contains("SETHIDDEN 1") == true)
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setHidden(1);

						if(HostResponse.contains("SETHIDDEN 0") == true)
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setHidden(0);
					}
					
					if(HostResponse.contains("SETSECRET1") == true){

						if(HostResponse.contains("SETSECRET1 TRUE") == true)
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).SetSecret(true);

						if(HostResponse.contains("SETSECRET1 FALSE") == true)
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).SetSecret(false);
						
					}
					
					if(HostResponse.contains("SETSECRET2") == true){

						if(HostResponse.contains("SETSECRET2 TRUE") == true)
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).SetSecret2(true);

						if(HostResponse.contains("SETSECRET2 FALSE") == true)
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).SetSecret2(false);
					}
					
					
					if(HostResponse.contains("SETQUEST") == true){

						int TmpZ = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("Z:")+2),HostResponse.indexOf(" ", (HostResponse.indexOf("Z:")+2)))));
						
						System.out.println("EXECUTING SETQUEST "+TmpZ);
						
						
						if(HostResponse.contains("SETQUEST TRUE") == true)
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setQuest(true,TmpZ);

						if(HostResponse.contains("SETQUEST FALSE") == true)
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setQuest(false,TmpZ);
					}
					
					
					
					if(HostResponse.contains("SETXPOS") == true){

						int TmpXPos = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("SETXPOS:")+8),HostResponse.indexOf(" ", (HostResponse.indexOf("SETXPOS:")+8)))));
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setPosX(TmpXPos);	
						
					}
					
					
					if(HostResponse.contains("SETYPOS") == true){

						int TmpYPos = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("SETYPOS:")+8),HostResponse.indexOf(" ", (HostResponse.indexOf("SETYPOS:")+8)))));
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setPosY(TmpYPos);	
						
					}
					
					
					if(HostResponse.contains("QUEST1") == true){

						int TmpQUEST = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("QUEST:")+6),HostResponse.indexOf(" ", (HostResponse.indexOf("QUEST:")+6)))));
						int TmpQALREADY = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("QALREADY:")+9),HostResponse.indexOf(" ", (HostResponse.indexOf("QALREADY:")+9)))));
						
						if(TmpQALREADY == 1)
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).Quest1(TmpQUEST, true, true);	
						
					}
					
					if(HostResponse.contains("QUEST2") == true){

						int TmpQUEST = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("QUEST:")+6),HostResponse.indexOf(" ", (HostResponse.indexOf("QUEST:")+6)))));
						int TmpQALREADY = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("QALREADY:")+9),HostResponse.indexOf(" ", (HostResponse.indexOf("QALREADY:")+9)))));
						
						if(TmpQALREADY == 1)
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).Quest2(TmpQUEST, true, true);	
						
					}
					
					if(HostResponse.contains("QUEST3") == true){

						int TmpQUEST = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("QUEST:")+6),HostResponse.indexOf(" ", (HostResponse.indexOf("QUEST:")+6)))));
						int TmpQALREADY = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("QALREADY:")+9),HostResponse.indexOf(" ", (HostResponse.indexOf("QALREADY:")+9)))));
						
						if(TmpQALREADY == 1)
							this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).Quest3(TmpQUEST, true, true);	
						
					}

					
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
						int TmpActiveItem = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("AI:")+3),HostResponse.indexOf(" ", (HostResponse.indexOf("AI:")+3)))));
						int TmpMarke = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("MARKE:")+6),HostResponse.length())));

						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setLives2(TmpLives);
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setHealth2(TmpHealth);
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setMoney2(TmpMoney);
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setMana2(TmpMana);
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setActiveItem(TmpActiveItem);
						this.SpielPanel.getDynamicObject(TmpDynamicObjectIndex).setMarke2(TmpMarke);
						
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
						
					}else if(HostResponse.contains("UP") == true || HostResponse.contains("DOWN") == true || HostResponse.contains("LEFT") == true || HostResponse.contains("RIGHT") == true){

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
				}
				
				if(HostResponse.contains("SO") == true){ //StaticObject
					
					int TmpStaticObjectY = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("Y:")+2),HostResponse.indexOf(" ", (HostResponse.indexOf("Y:")+1))))); // X Pos
					int TmpStaticObjectX = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("X:")+2),HostResponse.indexOf(" ", (HostResponse.indexOf("X:")+1))))); // Y Pos
					int TmpStaticObjectType = Integer.parseInt((HostResponse.substring((HostResponse.indexOf("T:")+2),HostResponse.indexOf(" ", (HostResponse.indexOf("T:")+1))))); // Type

					if(this.SpielPanel.getStaticObject(TmpStaticObjectY,TmpStaticObjectX) != null){
					this.SpielPanel.getStaticObject(TmpStaticObjectY,TmpStaticObjectX).setType(TmpStaticObjectType);
					
					if(TmpStaticObjectType == 0)
						this.SpielPanel.getStaticObject(TmpStaticObjectY,TmpStaticObjectX).setCollision(false);
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
