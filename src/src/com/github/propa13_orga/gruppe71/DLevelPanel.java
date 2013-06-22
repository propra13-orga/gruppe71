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

public class DLevelPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected JFrame SpielFenster;
	protected StaticObject[][] StaticObjects;
	protected int[][][] LevelObjects; //Hier werden die aus der Datei geladenen Levelabschnitte zwischengespeichert
	protected boolean StaticObjectsLoaded; //Statische Objekte geladen?
	protected boolean StaticObjectsPainted; //Statische Objekte gemalt?
	protected boolean LevelObjectsLoaded; //Level Objekte zwischengespeichert/geladen aus Datei?
	protected int CurrentLevelSection;
	protected int CurrentLevel;
	protected boolean DebugMode;
	
	/**
	 * Initialisiert die Klassenattribute
	 */
	public DLevelPanel(JFrame pJFrame){
		//Konstruktor
		super();

		//Setze alles auf Start-Wert
		this.SpielFenster = pJFrame;
		
		this.StaticObjects = new StaticObject[14][24];
		this.LevelObjects = new int[3][12][20]; 
		this.StaticObjectsLoaded = false;
		this.StaticObjectsPainted = false;
		this.LevelObjectsLoaded = false;
		this.CurrentLevelSection = 0;
		this.CurrentLevel = 0;
		this.loadLevel(0); //Laed 1. Level
		
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
		}
					
				
			

			this.StaticObjectsPainted = true;
		
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
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_armor.png"), //10 Spieler Ruestung
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player01.png"),//11 Spieler 4-Leben
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
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pArmor.png"), //26 Ruestung
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_shop.png"), //27 Shop
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pTrank.png"), //28 Zaubertrank
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pKetchup.png"),  //29 Ketchup
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_ketchup.png"),//30 [Proj]Zauber
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/proj_kaese.png"),//31 [Proj]Kaese
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_messer.png"),//32 [Proj]Messer
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/proj_spiderweb.png"),//33 [Proj]Spinnweben(2. und 3. Boss)
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_armor.png"),//34 2.Spieler Ruestung
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_2player01.png"),//35 2.Spieler 4-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_2player01.png"),//36 2.Spieler 3-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_2player02.png"),//37 2.Spieler 2-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_2player03.png"),//38 2.Spieler 1-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_2player04.png"),//39 2.Spieler 0-Leben
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_wall01.png"),//40 QUEST
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/Treasure.png"),//41 Truhe Belohnung Quests
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_wall01.png")//Nach Quest Vervollst�ndigung
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
	            			case 'R': // Ruestung
								TmpLevelObjects[z][y][x] = 26;
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
	}
	
	
	/**
	 * Laed den naechsten Levelabschnitt
	 * @param NICHTS
	 */
	public void loadNextLevelSection(){
		if(this.CurrentLevelSection < 2){ //Max 3 Abschnitte, daher
		this.loadLevelIntoStaticObjects((this.CurrentLevelSection+1));
		}
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
	 * Gibt zurueck ob wir gerade im Debug-Modus sind(Fehlersuche)
	 * @param NICHTS
	 */
	public boolean getDebugMode(){
		return this.DebugMode;
	}

}

