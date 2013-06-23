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

public class DPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected JFrame SpielFenster;
	protected StaticObject[][] StaticObjects;
	protected DDynamic[] DynamicObjects;
	protected DSound sound;
	protected DProjectile[] Projectiles;
	protected int[][][] LevelObjects; //Hier werden die aus der Datei geladenen Levelabschnitte zwischengespeichert
	protected DDynamic CheckpointObject;
	protected boolean CheckpointLoaded;
	protected boolean StaticObjectsLoaded; //Statische Objekte geladen?
	protected boolean StaticObjectsPainted; //Statische Objekte gemalt?
	protected boolean DynamicObjectsLoaded; //Dynamische Objekte geladen?
	protected boolean GegnerLoaded; //Gegner DynamicObjects geladen?
	protected boolean DynamicObjectsPainted; //Dynamische Objekte gemalt?
	protected boolean LevelObjectsLoaded; //Level Objekte zwischengespeichert/geladen aus Datei?
	protected int CurrentLevelSection;
	protected int CurrentLevel;
	protected int AnzahlSpieler;
	protected boolean loaded;
	protected boolean DebugMode;
	
	/**
	 * Initialisiert die Klassenattribute
	 */
	public DPanel(JFrame pJFrame, int pAnzahlSpieler){
		//Konstruktor
		super();

		//Setze alles auf Start-Wert
		this.SpielFenster = pJFrame;
		
		this.StaticObjects = new StaticObject[14][24];
		this.DynamicObjects = new DDynamic[50];
		this.Projectiles = new DProjectile[100];
		this.LevelObjects = new int[3][12][20]; 
		this.CheckpointLoaded = false;
		this.StaticObjectsLoaded = false;
		this.StaticObjectsPainted = false;
		this.DynamicObjectsLoaded = false;
		this.GegnerLoaded = false;
		this.DynamicObjectsPainted = false;
		this.LevelObjectsLoaded = false;
		this.CurrentLevelSection = 0;
		this.CurrentLevel = 0;
		this.AnzahlSpieler = pAnzahlSpieler;
		this.loadLevel(0); //Laed 1. Level
		this.loaded=false;
		
		this.DebugMode = true; //Debug-Mode? Um Bugs zu finden beim Programmieren
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
		int questX=-1;
		int questY=-1;
		int belohnungX=-1;
		int belohnungY=-1;
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
					if(this.StaticObjects[y][x].getType() == 40){ //Geheimgang,Trittstelle
						questX = x; //Speichert wo Geheimstelle
						questY= y; //in 2 Variablen
					}
					if(this.StaticObjects[y][x].getType() == 42){ //Treasure
						belohnungX = x; //Speichert wo Geheimstelle
						belohnungY= y; //in 2 Variablen
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
					this.DynamicObjects[0] = new DDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, (TmpXStart*30), (TmpYStart*30), Health, Points, false,6); //initialisiere, damit Objekt neben Eingang
					this.DynamicObjects[1] = new DDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, (TmpXStart*30), (TmpYStart*30), Health, Points, false,6); //initialisiere, damit Objekt neben Eingang
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
								this.DynamicObjects[2+i] = new DDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, (tmpGegnerXPos*30), (tmpGegnerYPos*30), 1, 0, true, 0); //initialisiere, Gegner
							}else{
								this.DynamicObjects[2+i].setCurrentPosition((tmpGegnerXPos*30), (tmpGegnerYPos*30));
							}
							this.DynamicObjects[2+i].setType(1); //Setzt Type 1 = Normaler Gegner
							this.DynamicObjects[2+i].setHealth(1);
							break;
						case 7: //Starker Gegner (1. Boss)
							if(this.DynamicObjects[2+i] == null){
								this.DynamicObjects[2+i] = new DDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, (tmpGegnerXPos*30), (tmpGegnerYPos*30), 1, 0, true, 0); //initialisiere, Gegner
							}else{
								this.DynamicObjects[2+i].setCurrentPosition((tmpGegnerXPos*30), (tmpGegnerYPos*30));	
							}
							this.DynamicObjects[2+i].setType(2); //Setzt Type 2 = Starker Gegner
							this.DynamicObjects[2+i].setHealth(2);
							break;
						case 8: //Starker Boss Spinne (2.Boss)
							if(this.DynamicObjects[2+i] == null){
								this.DynamicObjects[2+i] = new DDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, (tmpGegnerXPos*30), (tmpGegnerYPos*30), 1, 0, true, 0); //initialisiere, Gegner
							}else{
								this.DynamicObjects[2+i].setCurrentPosition((tmpGegnerXPos*30), (tmpGegnerYPos*30));
							}
							this.DynamicObjects[2+i].setType(3); //Setzt Type 3 = Boss Gegner
							this.DynamicObjects[2+i].setHealth(4);
							break;
						case 9: //End Boss Spinne2? (3.Boss)
							if(this.DynamicObjects[2+i] == null){
								this.DynamicObjects[2+i] = new DDynamic(this, this.StaticObjects, this.DynamicObjects, this.Projectiles, (tmpGegnerXPos*30), (tmpGegnerYPos*30), 1, 0, true, 0); //initialisiere, Gegner
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
						
						// if (this.DynamicObjects[i].getHealth() < 5) {this.DynamicObjects[i].setRType(0);} //Setzt R�stung auf Null 
						
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
						
						else if((this.DynamicObjects[0].getQuestLaufend(0)==true && this.DynamicObjects[0].getMarke()>=5)){//Quest 1
							this.StaticObjects[belohnungY][belohnungX].setType(0);
							this.StaticObjects[belohnungY][belohnungX].setCollision(false);
							this.DynamicObjects[i].setMarke(0);
						}
						
						for(int z=0;z<DynamicObjects[i].QuestLength();z++){
							if(this.DynamicObjects[i].getQuest(z)==true){
								sound=new DSound("src/com/github/propa13_orga/gruppe71/GoodBye.wav");// Good Bye !
								sound.SetVolume(-10);
								sound.Abspielen();
							this.StaticObjects[questY][questX].setType(0);
							this.StaticObjects[questY][questX].setCollision(false);
							this.DynamicObjects[i].setQuest(false,z);
							break;
							
					}
						}	
							
				
						
						int[] TmpDynamicObjectPosition=this.DynamicObjects[i].getCurrentPosition();
							//Hier werden die Burger neu gezeichnet bei Leben Verlust.
						if(i == 0) {
								switch(this.DynamicObjects[i].getHealth()){
									case 8:
										if (DynamicObjects[i].getRType()== 5){
											this.drawImageAtPos(pGraphics,44 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										}else this.drawImageAtPos(pGraphics,10 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 7:
										if (DynamicObjects[i].getRType()== 5){
											this.drawImageAtPos(pGraphics,44 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										}else this.drawImageAtPos(pGraphics,10 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 6:
										if (DynamicObjects[i].getRType()== 5){
											this.drawImageAtPos(pGraphics,44 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										}else this.drawImageAtPos(pGraphics,10 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 5:
										if (DynamicObjects[i].getRType()== 5){
											this.drawImageAtPos(pGraphics,44 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										}else this.drawImageAtPos(pGraphics,10 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
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
										if (DynamicObjects[i].getRType()== 5){
											this.drawImageAtPos(pGraphics,45 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										}else this.drawImageAtPos(pGraphics,34 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 7:
										if (DynamicObjects[i].getRType()== 5){
											this.drawImageAtPos(pGraphics,45 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										}else this.drawImageAtPos(pGraphics,34 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 6:
										if (DynamicObjects[i].getRType()== 5){
											this.drawImageAtPos(pGraphics,45 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										}else this.drawImageAtPos(pGraphics,34 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										break;
									case 5:
										if (DynamicObjects[i].getRType()== 5){
											this.drawImageAtPos(pGraphics,45 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
										}else this.drawImageAtPos(pGraphics,34 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
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
					else if(this.DynamicObjects[i] != null && this.DynamicObjects[i].getHealth() == 0 && this.DynamicObjects[i].getType() == 2 && this.DynamicObjects[i].getQuestLaufend(0)==true){//Quest 1
			
						this.DynamicObjects[0].setMarke(1);//Hier Marke setzen f�r Quest
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
	 * Malt ein Bild an der angegebenen Position
	 * @param pGraphics Java-Graphicsobjekt
	 * @param pIndex Index des zu zeichnenden Bildes
	 * @param pXPos X Position
	 * @param pYPos Y Position
	 */
	protected void drawImageAtPos(Graphics pGraphics, int pIndex, int pXPos, int pYPos){ 
		//Bild Array, enthaelt Bilder von Objekten aus JPEG Dateien, Boden/Mauer etc.
		Image[] bb = {
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_floor01.png"), //0 Boden
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_wall01.png"), //1 Mauer
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_in.png"), //2 Eingang
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_through.png"), //3 Durchgang
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_out.png"), //4 Ausgang
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_enemy.png"), //5 Normaler Gegner
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_trap01.png"), //6 Falle
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_strong_enemy.png"), //7 Starker Gegner(1.Boss)
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_boss_enemy.png"), //8 Starker Boss Spinne(2.Boss)
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_boss_enemy2.png"), //9 End Boss(3.Boss)
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_armor01.png"), //10 Spieler Ruestung
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player01.png"),//11 Spieler 4-Leben01
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player02.png"),//12 Spieler 3-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player03.png"),//13 Spieler 2-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player04.png"),//14 Spieler 1-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player05.png"),//15 Spieler 0-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_wall01.png"), //16 Geheimgang
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_floor01.png"), //17 Geheim
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_floor01.png"), //18 Teleport , Destination
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pBeamer.png"), //19 Secret
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pKaese.png"), //20 Cheese/Kaese
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pLive.png"), //21 Health/ Gesundheit
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pMesser.png"), //22 Knife/Messer
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pLive.png"), //23 Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pMoney.png"), //24 Money / Geld
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pHotdog_npc.png"), //25 NPC
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pArmor01.png"), //26 Ruestung
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_shop.png"), //27 Shop
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pTrank.png"), //28 Zaubertrank
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pKetchup.png"),  //29 Ketchup
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_ketchup.png"),//30 [Proj]Zauber
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/proj_kaese.png"),//31 [Proj]Kaese
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_messer.png"),//32 [Proj]Messer
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/proj_spiderweb.png"),//33 [Proj]Spinnweben(2. und 3. Boss)
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_armor01.png"),//34 2.Spieler Ruestung01
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_2player01.png"),//35 2.Spieler 4-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_2player01.png"),//36 2.Spieler 3-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_2player02.png"),//37 2.Spieler 2-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_2player03.png"),//38 2.Spieler 1-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_2player04.png"),//39 2.Spieler 0-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_wall01.png"),//40 QUEST
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/Treasure.png"),//41 Truhe Belohnung Quests
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_wall01.png"),//Nach Quest Vervollst�ndigung
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pArmor02.png"), //43 Ruestung02
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_armor02.png"),//44 1.Spieler Ruestung
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_armor02.png") //45 2.Spieler Ruestung
				};
	
		//Zeichne das Bild
		pGraphics.drawImage(bb[pIndex], pXPos, pYPos, 30, 30, this);
	}
		
	/**
	 * Laed Level Nummer....
	 * @param Nummer des Levels
	 */
	public void loadLevel(int pLevel){	
		//Lade eine Level Datei in den Zwischenspeicher
		this.loadLevelFromFile("src/src/com/github/propa13_orga/gruppe71/level"+ Integer.toString(pLevel)+".txt");
		this.CurrentLevel = pLevel;
		
		//Lade den 1. Abschnitt(0) des Levels nach Statische Objekte
		this.loadLevelIntoStaticObjects(0);	
	}

	
	/**
	 * Laed den naechsten Level
	 * @param NICHTS
	 */
	public void loadNextLevel(){
		if(this.CurrentLevel < 2) //Max 3 Abschnitte, daher
			this.loadLevel((this.CurrentLevel+1));
	}
	
	/**
	 * Liest eine Datei aus und laed sie als Level
	 * @param Pfad/Dateiname der LevelDatei
	 */
	public void loadLevelFromFile(String pFilename){
		
		//Setze alle Variablen auf Startwert
		this.StaticObjects = new StaticObject[14][24];
		//this.DynamicObjects = new DDynamic[2];
		this.LevelObjects = new int[3][12][20]; 
		this.StaticObjectsLoaded = false;
		this.StaticObjectsPainted = false;
		this.DynamicObjectsLoaded = false;
		this.DynamicObjectsPainted = false;
		this.LevelObjectsLoaded = false;
		this.CurrentLevelSection = 0;
		
		File LevelFile = new File(pFilename); //Dateiname der LevelDatei
		BufferedReader LevelReader = null; //DateiLeser

		try {
		    LevelReader = new BufferedReader(new FileReader(LevelFile)); //Dateileser wird erzeugt
		    
		    String[][] LevelFileContent = new String[3][12];
		    String TmpTextLine = null; //Momentaner Zeileninhalt
		    int LevelFileCurrentLine = 0; //Momentane Zeile
		    int LevelFileCurrentSection = 0; //Momentaner Levelabschnitt
		    
		    while ((TmpTextLine = LevelReader.readLine()) != null) // Momentane Zeile wird gelesen und gespeichert und dann abgefragt, ob es null ist(Datei-Ende)
		    {
		    	if(TmpTextLine.length() < 20){ // Wenn eine Zeile leer ist, faengt neuer Abschnitt an
		    		LevelFileCurrentLine = 0;
		    		LevelFileCurrentSection++;
		    	}else{
		    		LevelFileContent[LevelFileCurrentSection][LevelFileCurrentLine] = TmpTextLine; // Zeile wird gespeichert
		    		LevelFileCurrentLine++; //Zeilennummer um 1 erhoeht
		    	}
		    }
		    
		    //Momentan haben wir nur String[], brauchen aber int
		    int[][][] TmpLevelObjects = new int[3][12][20]; 
		    
		    //Dazu lesen wir jetzt den String Zeile für Zeile auf
		    //und speichern jedes Zeichen(Char) als Nummer(int) in das int[][]
		    for (int z = 0; z < 3; z++){ //Level-Abschnitt
		    	for (int y = 0; y < 12; y++){ //Spalte
	            	for (int x = 0; x < 20; x++) { //Alle Buchstaben der Spalte durchgehen
	            		char tmpBuchstabe = LevelFileContent[z][y].charAt(x);
	            		if(tmpBuchstabe >= '0' && tmpBuchstabe <= '9'){ //Wenn eine Nummer
	            			TmpLevelObjects[z][y][x] = Character.getNumericValue(tmpBuchstabe);
	            		}else{
	            			switch(tmpBuchstabe){
	            			case 'O'://Geheimgang
	            				TmpLevelObjects[z][y][x] = 16;
	            				break;
	            			case 'G': //17 Geheim
	            				TmpLevelObjects[z][y][x] = 17;
	            				break;
	            			case '!'://Teleport
	            				TmpLevelObjects[z][y][x] = 18;
								break;
	            			case 'B'://Belohnung
	            				TmpLevelObjects[z][y][x] = 42;
								break;
	            			case '?': //Secret
	            				TmpLevelObjects[z][y][x] = 19;
								break;
	            			case 'C': // Cheese/Kaese
								TmpLevelObjects[z][y][x] = 20;
								break;
	            			case 'H': // Health/Gesundheit
								TmpLevelObjects[z][y][x] = 21;
								break;
	            			case 'K': // Knife / Messer
								TmpLevelObjects[z][y][x] = 22;
								break;
	            			case 'L': // Leben
								TmpLevelObjects[z][y][x] = 23;
								break;
	            			case 'M': // Money/Geld
								TmpLevelObjects[z][y][x] = 24;
								break;
	            			case 'N': // NPC
								TmpLevelObjects[z][y][x] = 25;
								break;
	            			case 'Q': // NPC
								TmpLevelObjects[z][y][x] = 40;
								break;
	            			case 'T': // Truhe,Treasure
								TmpLevelObjects[z][y][x] = 41;
								break;
	            			case 'R': // Ruestung01
								TmpLevelObjects[z][y][x] = 26;
								break;
	            			case '%': // Ruestung02
								TmpLevelObjects[z][y][x] = 43;
								break;
	            			case 'S': // Shop
								TmpLevelObjects[z][y][x] = 27;
								break;
	            			case 'Z': //Zaubertrank
								TmpLevelObjects[z][y][x] = 28;
								break;
							default:
								TmpLevelObjects[z][y][x] = 24;
								break;
	            			}
	            		}
	            	}
	        	}
		    }

			// Jetzt haben wir das int[][]
			// Der ausgelesene Text sind jetzt Nummern, also laden wir es in LevelObjects
		    // Dort wird es zwischengespeichert und kann bei Bedarf geladen werden
			this.setLevelObjects(TmpLevelObjects);
			
		} 
		catch (FileNotFoundException e) {//DateiName nicht gefunden
		    e.printStackTrace();
		} 
		catch (IOException e) {//Festplattenfehler
		    e.printStackTrace();
		} 
		finally {
		    try {
		        if (LevelReader != null) {//Wenn wir fertig sind
		        	LevelReader.close();//wird der LevelLeser geschlossen
		        }
		    } 
		    catch (IOException e) {
		    
		    }
		}
		
	}
	
	/**
	 * Liest eine Datei aus und laed sie als Level
	 * @param pSection Zu ladender Levelabschnitt
	 */
	public void loadLevelIntoStaticObjects(int pSection){
		// Setze alles zurueck auf Startwert, damit es neu gezeichnet wird
		this.StaticObjects = new StaticObject[14][24];
		//this.DynamicObjects = new DDynamic[2];
		this.StaticObjectsLoaded = false;
		this.StaticObjectsPainted = false;
		
		this.DynamicObjectsLoaded = false;
		this.DynamicObjectsPainted = false;
		this.CurrentLevelSection = pSection;

		this.StaticObjectsLoaded = true;
		
		//Schleife, die die Typen der statischen Objekte gleich den Werten der LevelObjekte setzt
		for (int y = 0; y < 12; y++) {
			for (int x = 0; x < 20; x++) {
				// Es wird ein neues StaticObject mit neuem Typ erzeugt(Typ = was auch immer in LevelObject
				// steht, am Ende ist der StaticObject Array gefüllt mit neuen StatiObject Objekten
				this.StaticObjects[y][x] = new StaticObject(LevelObjects[pSection][y][x]);
			}
		}

		//Schleife, die die Gegner loescht
		for (int i = 2; i < this.DynamicObjects.length; i++) {
			this.DynamicObjects[i] = null;
		}

		//Schleife, die die Projektile loescht
		for (int i = 0; i < this.Projectiles.length; i++) {
			this.Projectiles[i] = null;
		}
	}
	
	
	/**
	 * Laed den naechsten Levelabschnitt
	 * @param NICHTS
	 */
	public void loadNextLevelSection(){
		if(this.CurrentLevelSection < 2){ //Max 3 Abschnitte, daher
		this.CheckpointLoaded = false;
		this.loadLevelIntoStaticObjects((this.CurrentLevelSection+1));
		}
	}
	
	/**
	 * Gibt den Array der Dynamischen Objekte(bewegliche Objekte wie Player/Gegner/Gegenstaende) zurueck
	 * @param NICHTS
	 */
	public DDynamic[] getDynamicObjects(){
		return this.DynamicObjects;
	}
	
	/**
	 * Gibt ein Dynamisches Objekt zurueck an der Stelle pIndex Spieler 0,1,2...
	 * @param pIndex Nummer des Spielers
	 */
	public DDynamic getDynamicObject(int pIndex){
		return this.DynamicObjects[pIndex];
	}

	/**
	 * Setzt den Array der Dynamischen Objekte(bewegliche Objekte wie Player/Gegner etc.)
	 * zu einem uebergebenen Wert
	 * @param int[][] Array von Dynamischen Objekten
	 */
	public void setDynamicObjects(DDynamic[] pDynamicObjects){
		this.DynamicObjects = pDynamicObjects;
	}
	
	/**
	 * Gibt das Checkpointobject zurück
	 */
	public DDynamic getCheckpointObject(){
		return this.CheckpointObject;
	}
	
	/**
	 * Setzt den Wert des Checkpointobjects
	 * @param DDynamic zu speicherndes Checkpointobject
	 */
	public void setCheckpointObject(DDynamic pCheckpointObject){
		this.CheckpointObject = pCheckpointObject;
	}
	
	/**
	 * Gibt den Array der Statischen Objekte(unbewegliche Objekte wie Boden/Mauern/Eingang etc.) zurueck
	 * @param NICHTS
	 */
	public StaticObject[][] getStaticObjects(){
		return this.StaticObjects;
	}
	
	/**
	 * Setzt den Array der Statischen Objekte(unbewegliche Objekte wie Boden/Mauern/Eingang etc.)
	 * zu einem uebergebenen Wert
	 * @param pStaticObjects Array von Statischen Objekten
	 */
	public void setStaticObjects(StaticObject[][] pStaticObjects){
		this.StaticObjects = pStaticObjects;
	}
	
	/**
	 * Gibt den Array der Level-Objekte zurueck
	 * @param NICHTS
	 */
	public int[][][] getLevelObjects(){
		return this.LevelObjects;
	}
	
	/**
	 * Setzt den Array der Statischen Objekte(unbewegliche Objekte wie Boden/Mauern/Eingang/Fallen etc.)
	 * zu einem uebergebenen Wert
	 * @param pLevelObjects Array von Level-Objekten
	 */
	public void setLevelObjects(int[][][] pLevelObjects){
		this.LevelObjects = pLevelObjects;
	}

	/**
	 * Gibt den momentanen Level zurueck
	 * @param NICHTS
	 */
	public int getCurrentLevel(){
		return this.CurrentLevel;
	}
	
	/**
	 * Setzt den momentanen Level
	 * @param pLevel Nummer des zu setzenden Levels
	 */
	public void setCurrentLevel(int pLevel){
		this.CurrentLevel = pLevel;
	}

	/**
	 * Gibt den momentanen Levelabschnitt zurueck
	 * @param NICHTS
	 */
	public int getCurrentLevelSection(){
		return this.CurrentLevelSection;
	}
	
	/**
	 * Setzt den momentanen Levelabschnitt
	 * @param pLevel Nummer des zu setzenden Levelabscnitts
	 */
	public void setCurrentLevelSection(int pLevelSection){
		this.CurrentLevelSection = pLevelSection;
	}
	
	/**
	 * Gibt zurueck, ob ein Checkpoint existiert
	 * @param NICHTS
	 */
	public boolean CheckpointExists(){
		if(this.CheckpointObject != null)
			return true;
		else
			return false;
	}
	
	/**
	 * Gibt zurueck, ob ein Checkpoint geladen wurde
	 * @param NICHTS
	 */
	public boolean CheckpointLoaded(){
		return this.CheckpointLoaded;
	}
	
	/**
	 * Gibt den momentanen Checkpoint zurueck
	 * @param NICHTS
	 */
	public DDynamic getCheckpoint(){
		return this.CheckpointObject;
	}
	
	/**
	 * Setzt den momentanen Checkpoint
	 * @param pCheckpointObject zu setzendes CheckpointObject
	 */
	public void setCheckpoint(DDynamic pCheckpointObject){
		this.CheckpointObject = pCheckpointObject;
	}

	/**
	 * Startet Das Spiel neu, Reset zum ersten Levelabschnitt
	 * @param NICHTS
	 */
	public void neuStart(){
		this.loadLevelIntoStaticObjects(0); // Lade ersten Levelabschnitt
	}
	
	/**
	 * Beendet das Spiel und geht zurueck zum Startmenue
	 * @param NICHTS
	 */
	public void beendeSpiel(){
		this.SpielFenster.dispose(); // Schliesst das Spielfenster
		DStartMenu StartMenu2 = new DStartMenu() ; //Oeffnet neues Startmenue
	}

	
	/**
	 * Gibt Anzahl der Spieler zurueck
	 * @param NICHTS
	 */
	public int SpielerModus(){
		return this.AnzahlSpieler;
	}
	
	/**
	 * Gibt zurueck ob wir gerade im Debug-Modus sind(Fehlersuche)
	 * @param NICHTS
	 */
	public boolean getDebugMode(){
		return this.DebugMode;
	}
	
	/** Messagedialog 
	 * -> Spieler bekommen Spielstand
	 * @param NICHTS
	 */
	public void Spielstand()
	{
		JOptionPane.showMessageDialog(null, "Spieler 1 hat: " + Integer.toString(this.DynamicObjects[0].getPoints()) + " Punkte!\nSpieler 2 hat: " + Integer.toString(this.DynamicObjects[1].getPoints()) + " Punkte!\n");   
	}
	
	/**
	 * Beim Sterben und bei Checkpoint == true kommt ein Messagefenster mit JOptionPane
	 * @param Keine Parameter
	 */
	public int Checkpoint(){//Fragt nach Checkpoint Benutzung wenn moeglich.
		int opt = JOptionPane.showOptionDialog(null, "OH LEIDER GEGESSEN WORDEN!\nCheckpoint:\n"+this.DynamicObjects[0].CheckAussage(),
                  "Checkpoint", JOptionPane.YES_NO_CANCEL_OPTION,
                  JOptionPane.WARNING_MESSAGE, null, 
                  new String[]{"Wiederbeleben!", "Ich geb auf!"}, "Ich geb auf");
		return opt;
	}
	
	/**
	 * Die Positon des Letzten Checkpoints und Das Dynamische Objekt an der Stelle Painten
	 * @param Keine Parameter
	 */
	public void RevivePaint(){
		if(this.CheckpointLoaded == false){
			this.loadLevelIntoStaticObjects(this.CurrentLevelSection);
			this.DynamicObjects[0] = this.CheckpointObject;
			this.CheckpointLoaded = true;
			this.setCheckpointObject(null);
		}
	}


}

