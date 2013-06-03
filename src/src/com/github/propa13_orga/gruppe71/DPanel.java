package src.com.github.propa13_orga.gruppe71;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
	
	private JFrame SpielFenster;
	private StaticObject[][] StaticObjects;
	private DDynamic[] DynamicObjects;
	private int[][][] LevelObjects; //Hier werden die aus der Datei geladenen Levelabschnitte zwischengespeichert
	private DDynamic CheckpointObject;
	private boolean CheckpointLoaded;
	private boolean StaticObjectsLoaded; //Statische Objekte geladen?
	private boolean StaticObjectsPainted; //Statische Objekte gemalt?
	private boolean DynamicObjectsLoaded; //Dynamische Objekte geladen?
	private boolean GegnerLoaded; //Gegner DynamicObjects geladen?
	private boolean DynamicObjectsPainted; //Dynamische Objekte gemalt?
	private boolean LevelObjectsLoaded; //Level Objekte zwischengespeichert/geladen aus Datei?
	private int CurrentLevelSection;
	private int CurrentLevel;
	private int AnzahlSpieler;
	private boolean loaded;
	
	/**
	 * Initialisiert die Klassenattribute
	 */
	public DPanel(JFrame pJFrame, int pAnzahlSpieler){
		//Konstruktor
		super();

		//Setze alles auf Start-Wert
		this.SpielFenster = pJFrame;
		this.StaticObjects = new StaticObject[12][20];
		this.DynamicObjects = new DDynamic[50];
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
		int TmpXObject=-1;
		int TmpYObject=-1;
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
					
					if(this.StaticObjects[y][x].getType() == 5){ //und wenn das Objekt ein Gegner ist
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
					this.DynamicObjects[0] = new DDynamic(this, this.StaticObjects, this.DynamicObjects, (TmpXStart*30), (TmpYStart*30), Health, Points, false,3); //initialisiere, damit Objekt neben Eingang
					this.DynamicObjects[1] = new DDynamic(this, this.StaticObjects, this.DynamicObjects, (TmpXStart*30), (TmpYStart*30), Health, Points, false,3); //initialisiere, damit Objekt neben Eingang
					
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
								this.DynamicObjects[2+i] = new DDynamic(this, this.StaticObjects, this.DynamicObjects, (tmpGegnerXPos*30), (tmpGegnerYPos*30), 1, 0, true, 0); //initialisiere, Gegner
								break;
							case 99: //Staerkerer Gegner
								this.DynamicObjects[2+i] = new DDynamic(this, this.StaticObjects, this.DynamicObjects, (tmpGegnerXPos*30), (tmpGegnerYPos*30), 1, 0, true, 0); //initialisiere, Gegner
								break;
							}
						}
					}
				
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
					
					this.CheckpointObject = new DDynamic(this, this.StaticObjects, this.DynamicObjects, tmpPos[0], tmpPos[1],DynamicObjects[0].getHealth(), DynamicObjects[0].getPoints(), false,3);
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
						
						for (int y = 0; y < 12; y++) {
							for (int x = 0; x < 20; x++) {
								 if(this.DynamicObjects[0].ItemHit()==true && this.StaticObjects[y][x].getType() == 8){
											this.StaticObjects[y][x].setType(0);
											this.drawImageAtPos(pGraphics, this.StaticObjects[y][x].getType(),(x*30), (y*30));
											this.Geladen(true);
											this.DynamicObjects[0].ItemBag();
											
									}
								}
						
						//Hier werden die Burger neu gezeichnet bei Leben Verlust.
						int[] TmpDynamicObjectPosition = this.DynamicObjects[i].getCurrentPosition();
						
						
						
						switch(this.DynamicObjects[i].getHealth()){
						case 4:
							this.drawImageAtPos(pGraphics,10 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
							break;
						case 3:
							this.drawImageAtPos(pGraphics,11 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
							break;
						case 2:
							this.drawImageAtPos(pGraphics, 12 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
							break;
						case 1:
							this.drawImageAtPos(pGraphics, 13 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
							break;
						case 0:
							this.drawImageAtPos(pGraphics, 14 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
							break;
						}
						
								
								
							
				
					}
				}
				
					
				}
				//Schleife, die durch die dynamischen Objekte der Gegner geht
				for (int i = 2; i < this.DynamicObjects.length; i++) {
					
					if(this.DynamicObjects[i] != null && this.DynamicObjects[i].getHealth() != 0){ //Wenn Objekt aktiv und Health vorhanden sind
						
						if(this.DynamicObjects[i].IsMoving() == true){ //Soll es bewegt werden?
							this.DynamicObjects[i].AnimateMoving(); //Bewege es ein Stückchen
						} else {
							System.out.println("DO "+i+" soll sich bewegen...");
							Random zufallsZahl = new Random();						// ZufallsZahl
							int randomnumber = zufallsZahl.nextInt(4);
							int delaycounter = zufallsZahl.nextInt(100);			//Verzoegerung von Zufalls-Bewegung
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
							}
						}
						
						int[] TmpDynamicObjectPosition = this.DynamicObjects[i].getCurrentPosition();
						
						//Und male das Objekt dann an der (neuen) Position
						this.drawImageAtPos(pGraphics, 5 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
					}
				} 
			}
			
		
		this.DynamicObjectsPainted = true;

		//Jetzt kann alles was wir gerade gemalt haben neu gezeichnet werden
		this.repaint();
	}
	
	}
	/**
	 * Malt ein Bild an der angegebenen Position
	 * @param pGraphics Java-Graphicsobjekt
	 * @param pIndex Index des zu zeichnenden Bildes
	 * @param pXPos X Position
	 * @param pYPos Y Position
	 */
	private void drawImageAtPos(Graphics pGraphics, int pIndex, int pXPos, int pYPos){ 
		//Bild Array, enthaelt Bilder von Objekten aus JPEG Dateien, Boden/Mauer etc.
		Image[] bb = {
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_floor.jpg"), //0
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_wall.jpg"), //1
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_in.jpg"), //2
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_through.jpg"), //3
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_out.jpg"), //4
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player.jpg"), //5
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_trap.jpg"), //6
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_7.jpg"), //7
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/messer.jpg"), //8
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player.jpg"), //9
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player.jpg"),//10
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player_1.jpg"),//11
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player_2.jpg"),//12
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player_3.jpg"),//13
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player_4.jpg") //14
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
		this.StaticObjects = new StaticObject[12][20];
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
						TmpLevelObjects[z][y][x] = Character.getNumericValue(LevelFileContent[z][y].charAt(x));
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
		this.StaticObjects = new StaticObject[12][20];
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
	
	/** Messagedialog 
	 * -> Spieler bekommen Spielstand
	 * @param NICHTS
	 */
	public void Spielstand()
	{
		JOptionPane.showMessageDialog(null, "Spieler 1 hat: " + Integer.toString(this.DynamicObjects[0].getPoints()) + " Punkte!\nSpieler 2 hat: " + Integer.toString(this.DynamicObjects[1].getPoints()) + " Punkte!\n");   
	}
	
	public int Checkpoint(){//Fragt nach Checkpoint Benutzung wenn moeglich.
		int opt = JOptionPane.showOptionDialog(null, "OH LEIDER GEGESSEN WORDEN!\nCheckpoint:\n"+this.DynamicObjects[0].CheckAussage(),
                  "Checkpoint", JOptionPane.YES_NO_CANCEL_OPTION,
                  JOptionPane.WARNING_MESSAGE, null, 
                  new String[]{"Wiederbeleben!", "Ich geb auf!"}, "Ich geb auf");
		return opt;
	}
	
	public void RevivePaint(){//Die Positon des Letzten Checkpoints und Das Dynamische Objekt an der Stelle Painten
		if(this.CheckpointLoaded == false){
			this.loadLevelIntoStaticObjects(this.CurrentLevelSection);
			this.DynamicObjects[0] = this.CheckpointObject;
			this.CheckpointLoaded = true;
			this.setCheckpointObject(null);
		}
	}
	
	public boolean Setladen(){
		return this.loaded;
	}

	public boolean Geladen(boolean p){
		return this.loaded=p;
	}
	
}

