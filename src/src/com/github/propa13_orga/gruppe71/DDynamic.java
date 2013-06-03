package src.com.github.propa13_orga.gruppe71;

import java.awt.Graphics;

import javax.swing.JOptionPane;



public class DDynamic {
	private DPanel SpielPanel;
	private StaticObject[][] StaticObjects; // private int[][] StaticObjects; 
	private DDynamic[] DynamicObjects; // private int[][] StaticObjects; 
	private int CurrentXPos;  //Die derzeitige X Position
	private int CurrentYPos; //Die derzeitige Y Position
	private int MoveToXPos; //Bewegung in X Richtung
	private int MoveToYPos; //Bewegung in Y Richtung
	private boolean moves; //Entscheidet ob Bewegt oder nicht
	private int Health;
	private int Lives;
	private int Type;
	private int Points;
	private int Money;
	private boolean isBot;
	private boolean[] items;
	private boolean hit;
	
	
	public DDynamic(DPanel pPanel, StaticObject[][] pStaticObjects, DDynamic[] pDynamicObjects, int pCurrentXPos, int pCurrentYPos, int pHealth, int pPunkte, boolean pisBot,int itemnumber){
		this.SpielPanel = pPanel;
		this.StaticObjects = pStaticObjects;
		this.DynamicObjects = pDynamicObjects;
		this.CurrentXPos = pCurrentXPos;
		this.CurrentYPos = pCurrentYPos;
		this.MoveToXPos = -1;
		this.MoveToYPos = -1;
		this.moves = false;
		this.Health = pHealth;
		this.Points = pPunkte;
		this.Money = 0;
		this.isBot = pisBot;
		if(pisBot == true){
			this.Lives = 0;
			this.Type = 1;
		}else{
			this.Type = 0;
			this.Lives = 3;
		}
		this.items=new boolean[itemnumber];
		this.InitItems();
		this.hit=false;
		
	}
	    
	//Bekomme Position des Dynamischen Objekts.
	public int[]getCurrentPosition(){
		  int[] CurrentPosition = new int[2]; //2 Rueckgabewerte: x und y
		  //Index braucht man nicht, weil man ja schon im Objekt ist
		  CurrentPosition[0]=this.CurrentXPos;
		  CurrentPosition[1]=this.CurrentYPos;
		  return CurrentPosition;
		 }
	
	//Setzt die Position des DynamicObjects Players
	public void setCurrentPosition(int pXPos, int pYPos){
		this.CurrentXPos=pXPos;
		this.CurrentYPos=pYPos;
	}

	//Routine
	public boolean IsMoving(){
		return this.moves;
	}
	
	//Routine
	public void setMoves(boolean pMoves){
		this.moves = pMoves;
	}
	
	// Bekomme Wert der Position des dynamischen Objektes
	public int[] getMoveToPosition(){
		int[] PositionMoving=new int[2];
		PositionMoving[0]=this.MoveToXPos;
		PositionMoving[1]=this.MoveToYPos;
		return PositionMoving;
			
	}
	
	//Gibt StaticObjects aus
	public StaticObject[][] getStaticObjects(){
		return this.StaticObjects;
	}
	
	//Setzt StaticObjects
	public void setStaticObjects(StaticObject[][] pStaticObjects){
		this.StaticObjects = pStaticObjects;
	}
	
	/**
	 * Setzt die neue Position x und y eines dyn. Objektes(z.B. Player), wo es hin gehen soll
	 * @param pXStart Neue X Position
	 * @param pYStart Neue Y Position
	 */
	public void setMoveToPosition(int pXPos, int pYPos){
		if(this.StaticObjects[(pYPos/30)][(pXPos/30)].getCollision() == false){ //Keine Kollision also bewege
			this.moves = true; //Objekt bewegt sich jetzt also = 1
			this.MoveToXPos = pXPos;
			this.MoveToYPos = pYPos;
		}
		
		 for (int i = 0; i < this.DynamicObjects.length; i++){ // Wenn der Gegner an der gleichen Position ist verlieren beide ein Leben
			 if(this.DynamicObjects[i] != null){
				 if(this.DynamicObjects[i].CurrentXPos == pXPos && this.DynamicObjects[i].CurrentYPos == pYPos && this.DynamicObjects[i].isBot != this.isBot ) {
				 	this.LoseHealth();
				 	this.DynamicObjects[i].LoseHealth();
			 	}
			 }
		 }
		// AKTIONEN JE NACH TYP
		switch(this.StaticObjects[(pYPos/30)][(pXPos/30)].getType()){
		
		case 3: //lade neues Level -neuer Level Abschnitt und bekomme Punkte
			if(SpielPanel.SpielerModus() == 2){ //2 Spieler Modus
			this.Points += 1;
			SpielPanel.Spielstand();
			}
			this.SpielPanel.loadNextLevelSection();
			break;
			
		case 4: //Ziel erreicht NeuStart des Spiels
			if(this.SpielPanel.getCurrentLevel() < 2){ //Wenn noch nicht letzter Level
				this.SpielPanel.loadNextLevel(); //Lade naechsten Level
			}else{
				this.SpielPanel.beendeSpiel(); //Sonst beende Spiel
			}
			break;
		case 6: // Objekt ist ein Mensch!
			if (this.isBot == false){this.LoseHealth();} // Nur wenn es ein Spieler ist
			break;
		
		case 8:
			this.ItemSetHit(true);
			break;
				
		}
		
		/*
		 * switch(this.DDynamic[(pYPos/30)][(pXPos/30)].isBot()){
		case 11: // Objekt ist ein Gegener
			this.LoseHealth();
			break;
		default:
			System.out.println("Fehler in Schleife zur Gegner Erkenneung");
				
		}
			*/
		}
	
	/**
	 * Bewegt ein dynamisches Objekt rauf, runter, rechts oder links
	 * @param pWhere Wohin es bewegt werden soll
	 */
	
	public void moveTo(String pWhere){

		if(this.moves == false){ //Wird das Objekt momentan schon bewegt?
			
			int[] tmpCurrentPosition = new int[2];
			tmpCurrentPosition = this.getCurrentPosition(); //die momentaneCurr X, Y Position des dyn. Objektes
			
			if(pWhere == "up" && tmpCurrentPosition[1] > 0) //bewege nach oben
				this.setMoveToPosition(tmpCurrentPosition[0], (tmpCurrentPosition[1]-30));
			
			if(pWhere == "right" && tmpCurrentPosition[0] < 570) //bewege nach rechts
				this.setMoveToPosition((tmpCurrentPosition[0]+30), tmpCurrentPosition[1]);
			
			if(pWhere == "left" && tmpCurrentPosition[0] > 0) //bewege nach links
				this.setMoveToPosition((tmpCurrentPosition[0]-30), tmpCurrentPosition[1]);
			
			if(pWhere == "down" && tmpCurrentPosition[1] < 330) //bewege nach unten
				this.setMoveToPosition(tmpCurrentPosition[0], (tmpCurrentPosition[1]+30));
		}
	}
	
	/**
	 * Animiert das Objekt, hiermit wird das dyn. Objekt Stueck fuer Stueck um 2 Pixel bewegt, bis
	 * es sich an der Position befindet wo es hin soll
	 * @param pIndex Index des dyn. Objektes
	 */
	public void AnimateMoving(){
		
		if(this.CurrentXPos < this.MoveToXPos)
			this.CurrentXPos += 3; //muss noch ein St�ck nach rechts

			if(this.CurrentXPos > this.MoveToXPos)
			this.CurrentXPos -= 3; //muss noch ein St�ck nach links

			if(this.CurrentYPos < this.MoveToYPos)
			this.CurrentYPos += 3; //muss noch ein St�ck nach unten

			if(this.CurrentYPos > this.MoveToYPos)
			this.CurrentYPos -= 3; //muss noch ein St�ck nach oben

			//Wenn wir fertig sind, setzen wir die Variable wieder, dass es sich momentan nicht bewegt
			if(this.CurrentYPos == this.MoveToYPos && this.CurrentXPos == this.MoveToXPos)
			this.moves = false; // bewegt sich nicht mehr, moveTo ist IsMoving
		
	}

	/**
	 * Gibt Gesundheit zurueck
	 * @param NICHTS 
	 */
	public int getHealth(){
		return this.Health;	
	}
	
	/**
	 * Setzt Gesundheit
	 * @param pHealth Wieviel Gesundheit? 
	 */
	public void setHealth(int pHealth){
		this.Health += pHealth;
	}

	/**
	 * Gibt Leben zurueck
	 * @param NICHTS 
	 */
	public int getLives(){
		return this.Lives;	
	}
	
	/**
	 * Setzt Leben
	 * @param pLives Wieviele Leben? 
	 */
	public void setLives(int pLives){
		this.Lives += pLives;
	}
	
	/**
	 * Gibt Punkte zurueck
	 * @param NICHTS 
	 */
	public int getPoints(){ //Bekomme Punkte
		return this.Points;
	}
	
	/**
	 * Setzt Punkte
	 * @param pHealth Wieviele Punkte? 
	 */
	public void setPoints(int pPoints) //Erhoeht Punkte des Spielers bei Erreichen eines Levelabschnitts
	{
		this.Points = pPoints;
	}
	
	/** 
	 * Methode Gesundheit verlieren kann in Verknuepfung mit Schadenssystem benutzt werden
	 * @param Nichts
	 */
	public void LoseHealth(){ 
		int p;
		if(this.SpielPanel.SpielerModus() == 1){ // gucke nach Modi
			this.setHealth(-1);// 1 Gesundheit weniger
			
			if(this.getHealth() == 0 && this.getLives() > 0 && this.isBot == false){ 
				//Gesundheit des Spielers 0?
				this.Lives--; // Leben weniger
				this.Health = 4; //Gesundheit wieder voll
			}
			
			if(this.getHealth() == 0 && this.getLives() == 0 && this.isBot == false){ //wenn Leben 0 ist!
				if(this.SpielPanel.CheckpointExists() == false || (this.SpielPanel.CheckpointExists() == true && this.SpielPanel.CheckpointLoaded() == true)){
				// Wenn kein Checkpoint existiert oder schon mal ein Checkpoint geladen wurde	
					this.SpielPanel.beendeSpiel(); // Beende Spiel
				}else{
				
				p=this.SpielPanel.Checkpoint();//Fuehre Aktion Message durch
				if(p!=JOptionPane.YES_OPTION){// Ist ja ,nicht gedrueckt
					this.SpielPanel.beendeSpiel();// Beende Spiel
				}
				else if((this.SpielPanel.CheckpointExists() == true && this.SpielPanel.CheckpointLoaded() == false) && (p == JOptionPane.YES_OPTION)){
					SpielPanel.RevivePaint();
				}
				else 
					this.SpielPanel.beendeSpiel();// Beende Spiel
				}
			}	
		}else if(this.SpielPanel.SpielerModus() == 2){ //Modus 2 Spieler
			this.setHealth(-1);	// 1 Gesundheit weniger

			if(this.getHealth() == 0 && this.getLives() > 0 && this.isBot == false){ 
				//Gesundheit einer der Spieler 0?
				this.Lives--; // Leben weniger
				this.Health = 4; //Gesundheit wieder voll
			}
			
			if(this.getHealth() == 0 && this.getLives() == 0 && this.isBot == false){ 
				//Gesundheit &Leben einer der Spieler 0?
				this.SpielPanel.beendeSpiel();
			}
		}
			
	}
	
	
	/** Gibt die Aussage für das Checkpoint Fenster zurueck
	 * @param NICHTS
	 */
	public String CheckAussage(){
		if(this.SpielPanel.CheckpointExists() == true && this.SpielPanel.CheckpointLoaded() == false)
				return "Sie haben einen Checkpoint, wenn Sie ihn Nutzen wollen... Dann nur zu!";
			else{
				return "Sie haben sowieso keinen Checkpoint! Pech gehabt :)!";
			}
		}
	
	/*
	 * Guckt wie viele Items gesetzt wurden
	 */
	public int NumberItems(){
		return this.items.length;
	}
	//Initialisiert Items 
	public boolean[] InitItems(){
		this.items=new boolean[NumberItems()];
		for(int i=0;i<=NumberItems()-1;i++){
			this.items[i]=false;
		}
		return this.items;
	}
	
	public boolean ItemSetHit(boolean q){
		return this.hit=q;
	}
	
	public boolean ItemHit(){
		return this.hit;
	}
		
	
	public boolean[] ItemBag(){
		
		for(int i=0;i<=NumberItems()-1;i++){
		if(this.hit==true && this.items[i]==false){
			System.out.println("YES!");
			this.items[i]=this.hit;
			if(this.items[i]==true && SpielPanel.Setladen()==true){
				this.ItemSetHit(false);
			}
			
		}
		else if(this.hit==true && this.items[i]==true){
			System.out.println("Already picked up!");
			this.hit=false;
		}
		}
		
		return this.items;

	}
	
	
}