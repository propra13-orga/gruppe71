package src.com.github.propa13_orga.gruppe71;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;

public class NPanel extends DPanel {
	private static final long serialVersionUID = 1L;
	
	private NDynamic[] DynamicObjects;

	
	/**
	 * Initialisiert die Klassenattribute
	 */
	public NPanel(JFrame pJFrame, int pAnzahlSpieler){
		//Konstruktor
		super(pJFrame, pAnzahlSpieler);
	}
	
	/**
	 * Zeichnet den gesamten Bildschirm neu und tut das mehrmals in der Sekunde
	 * @param pGraphics Java-Graphicsobjekt
	 */
	public void paintComponent(Graphics pGraphics) {

		super.paintComponent(pGraphics);
		
		// Variablen um zu Speichern, wo der Eingang ist
		int TmpXStart = -1;
		int TmpYStart = -1;
		int landungX=-1;
		int landungY=-1;
		int geheimX=-1;
		int geheimY=-1;
		int openX=-1;
		int openY=-1;
		if(this.StaticObjectsLoaded == true){//Wenn der Level/Statische Objekte geladen wurde
			
			int[][] tmpGegnerArray = new int[48][3];
			int tmpAnzahlGegner = 0;
			//Schleife, die durch die statischen Objekte geht
			for (int y = 0; y < 12; y++) {
				for (int x = 0; x < 20; x++) {
					//Dann jedes an der richtigen Stelle malt
					this.drawImageAtPos(pGraphics, this.StaticObjects[y][x].getType(), (x*30), (y*30));
					
					if(this.StaticObjects[y][x].getType() == 2){ //und wenn das Objekt ein Eingang ist
						TmpXStart = x; //Speichert es wo der Eingang ist
						TmpYStart = y; //in 2 Variablen
					}
					if(this.StaticObjects[y][x].getType() == 18){ //Landung
						landungX = x; //Speichert wo Landung
						landungY = y; //in 2 Variablen
					}
					
					if(this.StaticObjects[y][x].getType() == 16){ //Geheimgang,Trittstelle
						openX = x; //Speichert wo Geheimstelle
						openY= y; //in 2 Variablen
					}
					
					
					if(this.StaticObjects[y][x].getType() == 5 || this.StaticObjects[y][x].getType() == 7 || this.StaticObjects[y][x].getType() == 8  || this.StaticObjects[y][x].getType() == 9 ){ //und wenn das Objekt ein Gegner ist
						tmpGegnerArray[tmpAnzahlGegner][0] = x; //Gegner X Pos zwischenspeichern
						tmpGegnerArray[tmpAnzahlGegner][1] = y; //Gegner Y Pos zwischenspeichern
						tmpGegnerArray[tmpAnzahlGegner][2] = this.StaticObjects[y][x].getType(); //Gegner Typ zwischenspeichern
						tmpAnzahlGegner++;
						
						this.StaticObjects[y][x].setType(0); //Gegner geladen, also loeschen
					}				
						
					}
					
				
				
			}

			this.StaticObjectsPainted = true;
			
			
			if(this.DynamicObjectsLoaded == false){
				
				if(this.DynamicObjects[0] == null && this.DynamicObjects[1] == null)
				{
					//Wenn noch nichts initialisiert wurde, Level Start
					int Health=4; //Gesundheit
					int Points = 0; //Punkte Marke
					
					//Spieler initialisieren
					this.DynamicObjects[0] = new NDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, (TmpXStart*30), (TmpYStart*30), Health, Points, false,6); //initialisiere, damit Objekt neben Eingang
					this.DynamicObjects[1] = new NDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, (TmpXStart*30), (TmpYStart*30), Health, Points, false,6); //initialisiere, damit Objekt neben Eingang
				}	
				else
				{
					//Wenn schon einmal etwas initialisiert wurde, z.B. Naechster Levelabschnitt
					this.DynamicObjects[0].setCurrentPosition((TmpXStart*30), (TmpYStart*30)); //initialisiere, damit Objekt neben Eingang
					this.DynamicObjects[1].setCurrentPosition((TmpXStart*30), (TmpYStart*30)); //initialisiere, damit Objekt neben Eingang	
					this.DynamicObjects[0].setMoves(false); //Objekt bewegt sich nicht
					this.DynamicObjects[1].setMoves(false); //Objekt bewegt sich nicht	
					this.DynamicObjects[0].setStaticObjects(this.StaticObjects); //StaticObjects vom neuen Levelabschnitt
					this.DynamicObjects[1].setStaticObjects(this.StaticObjects); //StaticObjects vom neuen Levelabschnitt	
					
					// Neuer Levelabschnitt, also Checkpoint speichern
					//this.CheckpointObject = new DDynamic(this, this.StaticObjects, this.DynamicObjects, 0, 0, 4, 0);
					int[] tmpPos = DynamicObjects[0].getCurrentPosition();
					
					this.CheckpointObject = new DDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, tmpPos[0], tmpPos[1],DynamicObjects[0].getHealth(), DynamicObjects[0].getPoints(), false,3);
				} 
				

				//Gegner initialisieren
				for (int i = 0; i < tmpAnzahlGegner; i++) {
					if(tmpGegnerArray[i] != null){ //Wenn Gegner existiert
						
						//Werte laden aus dem Array
						int tmpGegnerXPos = tmpGegnerArray[i][0];
						int tmpGegnerYPos = tmpGegnerArray[i][1];
						int tmpGegnerType = tmpGegnerArray[i][2];
						
						//Gegner Typ bestimmen und initialisieren
						switch(tmpGegnerType){
						case 5: //Standard Gegner
							if(this.DynamicObjects[2+i] == null){
								this.DynamicObjects[2+i] = new NDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, (tmpGegnerXPos*30), (tmpGegnerYPos*30), 1, 0, true, 0); //initialisiere, Gegner
							}else{
								this.DynamicObjects[2+i].setCurrentPosition((tmpGegnerXPos*30), (tmpGegnerYPos*30));
							}
							this.DynamicObjects[2+i].setType(1); //Setzt Type 1 = Normaler Gegner
							this.DynamicObjects[2+i].setHealth(1);
							break;
						case 7: //Starker Gegner (1. Boss)
							if(this.DynamicObjects[2+i] == null){
								this.DynamicObjects[2+i] = new NDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, (tmpGegnerXPos*30), (tmpGegnerYPos*30), 1, 0, true, 0); //initialisiere, Gegner
							}else{
								this.DynamicObjects[2+i].setCurrentPosition((tmpGegnerXPos*30), (tmpGegnerYPos*30));	
							}
							this.DynamicObjects[2+i].setType(2); //Setzt Type 2 = Starker Gegner
							this.DynamicObjects[2+i].setHealth(2);
							break;
						case 8: //Starker Boss Spinne (2.Boss)
							if(this.DynamicObjects[2+i] == null){
								this.DynamicObjects[2+i] = new NDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, (tmpGegnerXPos*30), (tmpGegnerYPos*30), 1, 0, true, 0); //initialisiere, Gegner
							}else{
								this.DynamicObjects[2+i].setCurrentPosition((tmpGegnerXPos*30), (tmpGegnerYPos*30));
							}
							this.DynamicObjects[2+i].setType(3); //Setzt Type 3 = Boss Gegner
							this.DynamicObjects[2+i].setHealth(4);
							break;
						case 9: //End Boss Spinne2? (3.Boss)
							if(this.DynamicObjects[2+i] == null){
								this.DynamicObjects[2+i] = new NDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, (tmpGegnerXPos*30), (tmpGegnerYPos*30), 1, 0, true, 0); //initialisiere, Gegner
							}else{
								this.DynamicObjects[2+i].setCurrentPosition((tmpGegnerXPos*30), (tmpGegnerYPos*30));
							}
							this.DynamicObjects[2+i].setType(4); //Setzt Type 4 = EndBoss Gegner
							this.DynamicObjects[2+i].setHealth(12);
							break;
						}
					}
				}

				this.DynamicObjectsLoaded = true;
			}
			

			if(this.StaticObjectsPainted == true){

				//Schleife, die durch die dynamischen Objekte geht
				for (int i = 0; i < this.AnzahlSpieler; i++) {
					
					if(this.DynamicObjects[i] != null){ //Wenn Objekt aktiv
						
						if(this.DynamicObjects[i].IsMoving() == true){ //Soll es bewegt werden?
							this.DynamicObjects[i].AnimateMoving(); //Bewege es ein Stückchen
						}
						
						
			
						
						else if(this.DynamicObjects[i].getSecret()==true){
							this.DynamicObjects[i].setCurrentPosition((landungX*30),(landungY*30));
							this.DynamicObjects[i].SetSecret(false);
						}
						
				
						else if(this.DynamicObjects[i].getSecret2()==true ){
										this.StaticObjects[openY][openX].setType(0);
										this.StaticObjects[openY][openX].setCollision(false);
										this.DynamicObjects[i].SetSecret2(false);
										
								}
						 
					
							
				
						
						int[] TmpDynamicObjectPosition=this.DynamicObjects[i].getCurrentPosition();
							//Hier werden die Burger neu gezeichnet bei Leben Verlust.
						if(i == 0) {
								switch(this.DynamicObjects[i].getHealth()){
									case 8:
										this.drawImageAtPos(pGraphics,10 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 7:
										this.drawImageAtPos(pGraphics,10 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 6:
										this.drawImageAtPos(pGraphics,10 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 5:
										this.drawImageAtPos(pGraphics,10 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 4:
										this.drawImageAtPos(pGraphics,11 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 3:
										this.drawImageAtPos(pGraphics,12 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 2:
										this.drawImageAtPos(pGraphics,13 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 1:
										this.drawImageAtPos(pGraphics,14 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 0:
										this.drawImageAtPos(pGraphics, 15 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
								}
							}
							else if(i == 1) {
								switch(this.DynamicObjects[i].getHealth()){
									case 8:
										this.drawImageAtPos(pGraphics,34 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 7:
										this.drawImageAtPos(pGraphics,34 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 6:
										this.drawImageAtPos(pGraphics,34 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 5:
										this.drawImageAtPos(pGraphics,34 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 4:
										this.drawImageAtPos(pGraphics,35 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 3:
										this.drawImageAtPos(pGraphics,36 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 2:
										this.drawImageAtPos(pGraphics,37 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 1:
										this.drawImageAtPos(pGraphics,38 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 0:
										this.drawImageAtPos(pGraphics, 39 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
						
								}
						}
				}
				}	
					
				}
				//Schleife, die durch die dynamischen Objekte der Gegner geht
				for (int i = 2; i < this.DynamicObjects.length; i++) {
					
					if(this.DynamicObjects[i] != null && this.DynamicObjects[i].getHealth() > 0){ //Wenn Objekt aktiv und Health vorhanden sind	
						//Und male das Objekt dann an der (neuen) Position
						switch(this.DynamicObjects[i].getType()){						// Waehlt Bild nach Type aus		
						case 1: this.drawImageAtPos(pGraphics, 5 , this.DynamicObjects[i].getCurrentXPosition(), this.DynamicObjects[i].getCurrentYPosition());
								break;
						case 2: this.drawImageAtPos(pGraphics, 7 , this.DynamicObjects[i].getCurrentXPosition(), this.DynamicObjects[i].getCurrentYPosition());
								break;
						case 3: this.drawImageAtPos(pGraphics, 8 , this.DynamicObjects[i].getCurrentXPosition(), this.DynamicObjects[i].getCurrentYPosition());
							break;
						case 4: this.drawImageAtPos(pGraphics, 9 , this.DynamicObjects[i].getCurrentXPosition(), this.DynamicObjects[i].getCurrentYPosition());
							break;
						}
							if(this.DynamicObjects[i].IsMoving() == true){ //Soll es bewegt werden? Zufalls-Bewegung
							this.DynamicObjects[i].AnimateMoving(); //Bewege es ein Stückchen
							} else {
							Random zufallsZahl = new Random();						// ZufallsZahl
							int randomnumber = zufallsZahl.nextInt(4);
							int delaycounter = zufallsZahl.nextInt(50);			//Verzoegerung von Zufalls-Bewegung
							
							if(delaycounter == 0){								
								switch(randomnumber){								// Zufalls-Bewegung
								case 0: this.DynamicObjects[i].moveTo("right");
										break;
								case 1: this.DynamicObjects[i].moveTo("left");
										break;
								case 2: this.DynamicObjects[i].moveTo("down");
										break;
								case 3: this.DynamicObjects[i].moveTo("up");
										break;
								default: System.out.println("Fehler bei den Zufallszahlen");
										break;
							
								}
								
								if(this.DynamicObjects[i].getType() > 2){ //Wenn Starker Boss(3) oder Endboss(4)

									int randomshot = zufallsZahl.nextInt(3);
									
									if(randomshot == 0){
										this.DynamicObjects[i].Action(this.DynamicObjects[i].getType());
									}
								}
							}
						}
					}
					else if(this.DynamicObjects[i] != null && this.DynamicObjects[i].getHealth() == 0 && this.DynamicObjects[i].getType() == 4){
							this.beendeSpiel();
					} //Beende das Spiel wenn letzter Endgegner stirbt */
				} 
				
				//Schleife, die durch die Projektile geht
				for (int i = 0; i < this.Projectiles.length; i++) {
					
					if(this.Projectiles[i] != null && this.Projectiles[i].IsEnabled() == true){
						//Wenn Proj aktiv	
						
						//Male das Proj an der (neuen) Position
						if(this.Projectiles[i].getType() < 4)
							this.drawImageAtPos(pGraphics, (30+this.Projectiles[i].getType()) , this.Projectiles[i].getCurrentXPosition(), this.Projectiles[i].getCurrentYPosition());						
						else
							this.drawImageAtPos(pGraphics, (33) , this.Projectiles[i].getCurrentXPosition(), this.Projectiles[i].getCurrentYPosition());						

						this.Projectiles[i].AnimateMoving(); //Bewege es ein Stückchen
					}
				} 
			}
			
		
		this.DynamicObjectsPainted = true;

		
		//Jetzt kann alles was wir gerade gemalt haben neu gezeichnet werden
		this.repaint();
	}
	
	
	/**
	 * Gibt den Array der Dynamischen Objekte(bewegliche Objekte wie Player/Gegner/Gegenstaende) zurueck
	 * @param NICHTS
	 */
	public NDynamic[] getDynamicObjects(){
		return this.DynamicObjects;
	}
	
	/**
	 * Gibt ein Dynamisches Objekt zurueck an der Stelle pIndex Spieler 0,1,2...
	 * @param pIndex Nummer des Spielers
	 */
	public NDynamic getDynamicObject(int pIndex){
		return this.DynamicObjects[pIndex];
	}

	/**
	 * Setzt den Array der Dynamischen Objekte(bewegliche Objekte wie Player/Gegner etc.)
	 * zu einem uebergebenen Wert
	 * @param int[][] Array von Dynamischen Objekten
	 */
	public void setDynamicObjects(NDynamic[] pDynamicObjects){
		this.DynamicObjects = pDynamicObjects;
	}	
}

