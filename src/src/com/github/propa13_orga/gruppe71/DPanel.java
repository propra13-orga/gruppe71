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
import javax.swing.JFrame;

public class DPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JFrame SpielFenster;
	private StaticObject[][] StaticObjects;
	private DDynamic[] DynamicObjects;
	private int[][][] LevelObjects; //Hier werden die aus der Datei geladenen Levelabschnitte zwischengespeichert
	private boolean StaticObjectsLoaded; //Statische Objekte geladen?
	private boolean StaticObjectsPainted; //Statische Objekte gemalt?
	private boolean DynamicObjectsLoaded; //Dynamische Objekte geladen?
	private boolean DynamicObjectsPainted; //Dynamische Objekte gemalt?
	private boolean LevelObjectsLoaded; //Level Objekte zwischengespeichert/geladen aus Datei?
	private int CurrentLevelSection;
	private int AnzahlSpieler;
	
	/**
	 * Initialisiert die Klassenattribute
	 */
	public DPanel(JFrame pJFrame, int pAnzahlSpieler){
		//Konstruktor
		super();

		//Setze alles auf Start-Wert
		this.SpielFenster = pJFrame;
		this.StaticObjects = new StaticObject[12][20];
		this.DynamicObjects = new DDynamic[2];
		this.LevelObjects = new int[3][12][20]; 
		this.StaticObjectsLoaded = false;
		this.StaticObjectsPainted = false;
		this.DynamicObjectsLoaded = false;
		this.DynamicObjectsPainted = false;
		this.LevelObjectsLoaded = false;
		this.CurrentLevelSection = 0;
		this.AnzahlSpieler = pAnzahlSpieler;
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
		
		if(this.StaticObjectsLoaded == true){//Wenn der Level/Statische Objekte geladen wurde
			
			//Schleife, die durch die statischen Objekte geht
			for (int y = 0; y < 12; y++) {
				for (int x = 0; x < 20; x++) {
					//Dann jedes an der richtigen Stelle malt
					this.drawImageAtPos(pGraphics, this.StaticObjects[y][x].getType(), (x*30), (y*30));
					
					if(this.StaticObjects[y][x].getType() == 2){ //und wenn das Objekt ein Eingang ist
						TmpXStart = x; //Speichert es wo der Eingang ist
						TmpYStart = y; //in 2 Variablen
					}
				}
			}

			this.StaticObjectsPainted = true;
			
			
			if(this.DynamicObjectsLoaded == false){
				
				if(this.DynamicObjects[0] == null && this.DynamicObjects[1] == null)
				{
					//Wenn noch nichts initialisiert wurde, Level Start
					int Life=3;
					int Points = 0;
					
					this.DynamicObjects[0] = new DDynamic(this, StaticObjects, (TmpXStart*30), (TmpYStart*30), Life, Points); //initialisiere, damit Objekt neben Eingang
					this.DynamicObjects[1] = new DDynamic(this, StaticObjects, (TmpXStart*30), (TmpYStart*30), Life, Points); //initialisiere, damit Objekt neben Eingang
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
						
						int[] TmpDynamicObjectPosition = this.DynamicObjects[i].getCurrentPosition();
						
						//Und male das Objekt dann an der (neuen) Position
						this.drawImageAtPos(pGraphics, 5 , TmpDynamicObjectPosition[0], TmpDynamicObjectPosition[1]);
					}
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
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_7.jpg") //7
				};
		
		//Zeichne das Bild
		pGraphics.drawImage(bb[pIndex], pXPos, pYPos, 30, 30, this);
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
	}
	
	
	/**
	 * Laed den naechsten Levelabschnitt
	 * @param NICHTS
	 */
	public void loadNextLevel(){
		if(this.CurrentLevelSection < 2) //Max 3 Abschnitte, daher
		this.loadLevelIntoStaticObjects((CurrentLevelSection+1));
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
	
	/** Messagedialog 
	 * -> Spieler bekommen Spielstand
	 * @param NICHTS
	 */
	public void Spielstand()
	{
		JOptionPane.showMessageDialog(null, "Spieler 1 hat: " + Integer.toString(this.DynamicObjects[0].getPoints()) + " Punkte!\nSpieler 2 hat: " + Integer.toString(this.DynamicObjects[1].getPoints()) + " Punkte!\n");   
	}
}