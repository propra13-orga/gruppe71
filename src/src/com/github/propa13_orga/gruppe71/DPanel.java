package src.com.github.propa13_orga.gruppe71;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Arrays;

public class DPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private int[][] StaticObjects;
	private int[][] DynamicObjects;
	private int[][][] LevelObjects; //Hier werden die aus der Datei geladenen Levelabschnitte zwischengespeichert
	private boolean StaticObjectsLoaded; //Statische Objekte geladen?
	private boolean StaticObjectsPainted; //Statische Objekte gemalt?
	private boolean DynamicObjectsLoaded; //Dynamische Objekte geladen?
	private boolean DynamicObjectsPainted; //Dynamische Objekte gemalt?
	private boolean LevelObjectsLoaded; //Level Objekte zwischengespeichert/geladen aus Datei?
	private int CurrentLevelSection;
	
	/**
	 * Initialisiert die Klassenattribute
	 */
	public DPanel(){
		//Konstruktor
		super();

		//Setze alles auf Start-Wert
		this.StaticObjects = new int[12][20];
		this.DynamicObjects = new int[2][6];
		this.LevelObjects = new int[3][12][20]; 
		this.StaticObjectsLoaded = false;
		this.StaticObjectsPainted = false;
		this.DynamicObjectsLoaded = false;
		this.DynamicObjectsPainted = false;
		this.LevelObjectsLoaded = false;
		this.CurrentLevelSection = 0;
	}
	
	/**
	 * Zeichnet den gesamten Bildschirm neu und tut das mehrmals in der Sekunde
	 * @param pGraphics Java-Graphicsobjekt
	 */
	public void paintComponent(Graphics pGraphics) {

		super.paintComponent(pGraphics);
		
		this.DynamicObjectsLoaded = false; //Dynamische Objekte wurden noch nicht geladen
		// Variablen um zu Speichern, wo der Eingang ist
		int TmpXStart = -1;
		int TmpYStart = -1;
		
		if(this.StaticObjectsLoaded == true){//Wenn der Level/Statische Objekte geladen wurde
			
			//Schleife, die durch die statischen Objekte geht
			for (int y = 0; y < 12; y++) {
				for (int x = 0; x < 20; x++) {
					//Dann jedes an der richtigen Stelle malt
					this.drawImageAtPos(pGraphics, this.StaticObjects[y][x], (x*30), (y*30));
					
					if(this.StaticObjects[y][x] == 2){ //und wenn das Objekt ein Eingang ist
						TmpXStart = x; //Speichert es wo der Eingang ist
						TmpYStart = y; //in 2 Variablen
					}
				}
			}

			this.StaticObjectsPainted = true;
			
			
			if(this.DynamicObjectsLoaded == false && DynamicObjects[0][5] <= 0){
				//Wenn noch nichts initialisiert wurde
				
				this.initDynamicObjects(TmpXStart,TmpYStart); //initialisiere, damit Objekt neben Eingang
			
				this.DynamicObjectsLoaded = true;
			}
			

			if(this.StaticObjectsPainted == true){

				//Schleife, die durch die dynamischen Objekte geht
				for (int i = 0; i < 2; i++) {
					
					if(this.getDynamicObjectIsEnabled(i)){ //Wenn Objekt aktiv
						
						if(this.getDynamicObjectIsMoving(i)){ //Soll es bewegt werden?
							this.animateMovingDynamicObject(i); //Bewege es ein Stückchen
						}
						
						//Und male das Objekt dann an der (neuen) Position
						this.drawImageAtPos(pGraphics, 5 , this.DynamicObjects[i][0], this.DynamicObjects[i][1]);
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
				Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_player.jpg") //5
				};
		
		//Zeichne das Bild
		pGraphics.drawImage(bb[pIndex], pXPos, pYPos, 30, 30, this);
	}
	
	
	/**
	 * Animiert das Objekt, hiermit wird das dyn. Objekt Stück für Stück um 2 Pixel bewegt, bis
	 * es sich an der Position befindet wo es hin soll
	 * @param pIndex Index des dyn. Objektes
	 */
	private void animateMovingDynamicObject(int pIndex){
		if(this.DynamicObjects[pIndex][0] < this.DynamicObjects[pIndex][3])
			this.DynamicObjects[pIndex][0] += 2;  //muss noch ein Stück nach rechts
		
		if(this.DynamicObjects[pIndex][0] > this.DynamicObjects[pIndex][3])
			this.DynamicObjects[pIndex][0] -= 2; //muss noch ein Stück nach links
		
		if(this.DynamicObjects[pIndex][1] < this.DynamicObjects[pIndex][4])
			this.DynamicObjects[pIndex][1] += 2; //muss noch ein Stück nach unten
		
		if(this.DynamicObjects[pIndex][1] > this.DynamicObjects[pIndex][4])
			this.DynamicObjects[pIndex][1] -= 2; //muss noch ein Stück nach oben
		
		//Wenn wir fertig sind, setzen wir die Variable wieder, dass es sich momentan nicht bewegt
		if(this.DynamicObjects[pIndex][1] == this.DynamicObjects[pIndex][4] && this.DynamicObjects[pIndex][0] == this.DynamicObjects[pIndex][3])
			this.DynamicObjects[pIndex][2] = 0; // bewegt sich nicht mehr
	}
		
	/**
	 * Bewegt ein dynamisches Objekt rauf, runter, rechts oder links
	 * @param pIndex Index des dyn. Objektes
	 * @param pWhere Wohin es bewegt werden soll
	 */
	public void dynamicObjectMove(int pIndex, String pWhere){
		
		if(this.getDynamicObjectIsMoving(pIndex) == false){ //Wird das Objekt momentan schon bewegt?
			
			int[] tmpCurrentPosition = new int[2];
			tmpCurrentPosition = getDynamicObjectCurrentPosition(pIndex); //die momentane X, Y Position des dyn. Objektes
			
			if(pWhere == "up" && tmpCurrentPosition[1] > 0) //bewege nach oben
				this.setDynamicObjectMoveToPosition(pIndex, tmpCurrentPosition[0], (tmpCurrentPosition[1]-30));
			
			if(pWhere == "right" && tmpCurrentPosition[0] < 570) //bewege nach rechts
				this.setDynamicObjectMoveToPosition(pIndex, (tmpCurrentPosition[0]+30), tmpCurrentPosition[1]);
			
			if(pWhere == "left" && tmpCurrentPosition[0] > 0) //bewege nach links
				this.setDynamicObjectMoveToPosition(pIndex, (tmpCurrentPosition[0]-30), tmpCurrentPosition[1]);
			
			if(pWhere == "down" && tmpCurrentPosition[1] < 330) //bewege nach unten
				this.setDynamicObjectMoveToPosition(pIndex, tmpCurrentPosition[0], (tmpCurrentPosition[1]+30));
		}
	}
	
	/**
	 * Liest eine Datei aus und laed sie als Level
	 * @param Pfad/Dateiname der LevelDatei
	 */
	public void loadLevelFromFile(String pFilename){
		
		//Setze alle Variablen auf Startwert
		this.StaticObjects = new int[12][20];
		this.DynamicObjects = new int[2][6];
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
		this.StaticObjects = new int[12][20];
		this.DynamicObjects = new int[2][6]; 
		this.StaticObjectsLoaded = false;
		this.StaticObjectsPainted = false;
		this.DynamicObjectsLoaded = false;
		this.DynamicObjectsPainted = false;
		this.CurrentLevelSection = pSection;

		this.StaticObjectsLoaded = true;
		this.setStaticObjects(LevelObjects[pSection]);
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
	public int[][] getDynamicObjects(){
		return this.DynamicObjects;
	}

	/**
	 * Setzt den Array der Dynamischen Objekte(bewegliche Objekte wie Player/Gegner etc.)
	 * zu einem uebergebenen Wert
	 * @param int[][] Array von Dynamischen Objekten
	 */
	public void setDynamicObjects(int[][] pDynamicObjects){
		this.DynamicObjects = pDynamicObjects;
	}
	
	/**
	 * Gibt den Array der Statischen Objekte(unbewegliche Objekte wie Boden/Mauern/Eingang etc.) zurueck
	 * @param NICHTS
	 */
	public int[][] getStaticObjects(){
		return this.StaticObjects;
	}
	
	/**
	 * Setzt den Array der Statischen Objekte(unbewegliche Objekte wie Boden/Mauern/Eingang etc.)
	 * zu einem uebergebenen Wert
	 * @param pStaticObjects Array von Statischen Objekten
	 */
	public void setStaticObjects(int[][] pStaticObjects){
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
	 * Setzt den Array der Statischen Objekte(unbewegliche Objekte wie Boden/Mauern/Eingang etc.)
	 * zu einem uebergebenen Wert
	 * @param pStaticObjects Array von Statischen Objekten
	 */
	public void setLevelObjects(int[][][] pLevelObjects){
		this.LevelObjects = pLevelObjects;
	}
	
	
	/**
	 * Initialisiert die Dynamischen Objekte(bewegliche Objekte wie Player/Gegner etc.)
	 * @param pXStart X Position des Eingangs
	 * @param pYStart Y Position des Eingangs
	 */
	public void initDynamicObjects(int pXStart, int pYStart){
		this.DynamicObjects[0][0] = pXStart*30; //Setzt X erst einmal der X Pos des Eingangs 30
		this.DynamicObjects[0][1] = pYStart*30; //Setzt Y erst einmal der Y Pos des Eingangs 30
		this.DynamicObjects[0][2] = 0; //Objekt bewegt sich nicht
		this.DynamicObjects[0][3] = -1; //Daher auch keine X Pos wo es hin gehen soll 60
		this.DynamicObjects[0][4] = -1; //Und auch keine Y Pos wo es hin gehen soll 30 
		this.DynamicObjects[0][5] = 1; //Das Objekt ist aber aktiv
		
		this.DynamicObjects[1][0] = -1; //2. Objekt, alles so wie oben, aber nicht aktiv
		this.DynamicObjects[1][1] = -1;
		this.DynamicObjects[1][2] = 0;
		this.DynamicObjects[1][3] = -1;
		this.DynamicObjects[1][4] = -1;
		this.DynamicObjects[1][5] = 0;
		
		//Start Position vom Objekt
		if(pXStart == 0) //Wenn Eingang am linken Rand ist
			this.DynamicObjects[0][0] += 30; //Player rechts daneben
		if(pXStart == 19) //Wenn Eingang am rechten Rand ist
			this.DynamicObjects[0][0] -= 30;  //Player links daneben
		if(pYStart == 0) //Wenn Eingang am oberen Rand ist
			this.DynamicObjects[0][1] += 30;  //Player darunter
		if(pYStart == 11) //Wenn Eingang am unteren Rand ist
			this.DynamicObjects[0][1] -= 30;  //Player darüber
	}
	
	/**
	 * Gibt die momentane Position x und y eines dyn. Objektes(z.B. Player) zurueck
	 * @param Index des dyn. Objekts
	 */
	public int[] getDynamicObjectCurrentPosition(int pIndex){
		return new int[] {this.DynamicObjects[pIndex][0],this.DynamicObjects[pIndex][1]};
	}

	/**
	 * Setzt die momentane Position x und y eines dyn. Objektes(z.B. Player)
	 * @param pIndex Index des dyn. Objektes
	 * @param pXStart Neue X Position
	 * @param pYStart Neue Y Position
	 */
	public void setDynamicObjectCurrentPosition(int pIndex, int pXPos, int pYPos){
		this.DynamicObjects[pIndex][0] = pXPos;
		this.DynamicObjects[pIndex][1] = pYPos;
	}
	
	/**
	 * Gibt zurueck, ob das dyn. Objekt(z.B. Player), momentan eine Bewegung durchführt
	 * @param Index des dyn. Objekts
	 */
	public boolean getDynamicObjectIsMoving(int pIndex){
		if(this.DynamicObjects[pIndex][2] == 1)
			return true;
		else
			return false;	
	}
	

	/**
	 * Gibt die neue Position x und y eines dyn. Objektes(z.B. Player) zurueck, zu der 
	 * es momentan bewegt wird
	 * @param Index des dyn. Objekts
	 */
	public int[] getDynamicObjectMoveToPosition(int pIndex){
		return new int[] {this.DynamicObjects[pIndex][3],this.DynamicObjects[pIndex][4]};	
	}
	
	/**
	 * Setzt die neue Position x und y eines dyn. Objektes(z.B. Player), wo es hin gehen soll
	 * @param pIndex Index des dyn. Objektes
	 * @param pXStart Neue X Position
	 * @param pYStart Neue Y Position
	 */
	public void setDynamicObjectMoveToPosition(int pIndex, int pXPos, int pYPos){
		this.DynamicObjects[pIndex][2] = 1; //Objekt bewegt sich jetzt also = 1
		this.DynamicObjects[pIndex][3] = pXPos;
		this.DynamicObjects[pIndex][4] = pYPos;
	}
	
	/**
	 * Gibt zurueck, ob das dyn. Objekt(z.B. Player), existiert/aktiviert ist
	 * @param Index des dyn. Objekts
	 */	
	public boolean getDynamicObjectIsEnabled(int pIndex){
		if(this.DynamicObjects[pIndex][5] == 1)
			return true;
		else
			return false;	
	}

}
