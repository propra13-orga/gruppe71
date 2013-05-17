package src.com.github.propa13_orga.gruppe71;

public class DDynamic {

	private DPanel SpielPanel;
	private StaticObject[][] StaticObjects; // private int[][] StaticObjects; 
	private int CurrentXPos;  //Die derzeitige X Position
	private int CurrentYPos; //Die derzeitige Y Position
	private int MoveToXPos; //Bewegung in X Richtung
	private int MoveToYPos; //Bewegung in Y Richtung
	private boolean moves; //Entscheidet ob Bewegt oder nicht

	
	public DDynamic(DPanel pPanel, StaticObject[][] pStaticObjects, int pCurrentXPos, int pCurrentYPos){
		this.SpielPanel = pPanel;
		this.StaticObjects = pStaticObjects;
		this.CurrentXPos = pCurrentXPos;
		this.CurrentYPos = pCurrentYPos;
		this.MoveToXPos = -1;
		this.MoveToYPos = -1;
		this.moves = false;
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
	
	// Bekomme Wert der Position des dynamischen Objektes
	public int[] getMoveToPosition(){
		int[] PositionMoving=new int[2];
		PositionMoving[0]=this.MoveToXPos;
		PositionMoving[1]=this.MoveToYPos;
		return PositionMoving;
			
	}
	
	/**
	 * Setzt die neue Position x und y eines dyn. Objektes(z.B. Player), wo es hin gehen soll
	 * @param pXStart Neue X Position
	 * @param pYStart Neue Y Position
	 */
	public void setMoveToPosition(int pXPos, int pYPos){
		if( this.StaticObjects[(pYPos/30)][(pXPos/30)].getCollision() == false){ //Keine Kollision also bewege
			this.moves = true; //Objekt bewegt sich jetzt also = 1
			this.MoveToXPos = pXPos;
			this.MoveToYPos = pYPos;
		}
		// AKTIONEN JE NACH TYP
		switch(this.StaticObjects[(pYPos/30)][(pXPos/30)].getType()){
		
		case 3: //lade neues Level -neuer Level Abschnitt
			this.SpielPanel.loadNextLevel();
			break;
			
		case 4: //Ziel erreicht NeuStart des Spiels
			this.SpielPanel.neuStart();
			break;
		case 6: // Objekt ist ein Gegner/ eine Falle
			this.SpielPanel.beendeSpiel(); // Spiel wird beendet, zurueck zu Startbildschirm
			break;		
		}
			
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
	//Hilfsfunktionen f�r Animate Moving
	public void ErhoeheXUm(int add){
		this.CurrentXPos+=add;
		
	}
	public void ErhoeheYUm(int add){
		this.CurrentYPos+=add;
		
	}
	
	/**
	 * Animiert das Objekt, hiermit wird das dyn. Objekt Stueck fuer Stueck um 2 Pixel bewegt, bis
	 * es sich an der Position befindet wo es hin soll
	 * @param pIndex Index des dyn. Objektes
	 */
	public void AnimateMoving(){
		
		if(this.CurrentXPos < this.MoveToXPos)
			this.ErhoeheXUm(2); //muss noch ein St�ck nach rechts

			if(this.CurrentXPos > this.MoveToXPos)
			this.ErhoeheXUm(-2); //muss noch ein St�ck nach links

			if(this.CurrentYPos < this.MoveToYPos)
			this.ErhoeheYUm(2); //muss noch ein St�ck nach unten

			if(this.CurrentYPos > this.MoveToYPos)
			this.ErhoeheYUm(-2); //muss noch ein St�ck nach oben

			//Wenn wir fertig sind, setzen wir die Variable wieder, dass es sich momentan nicht bewegt
			if(this.CurrentYPos == this.MoveToYPos && this.CurrentXPos == this.MoveToXPos)
			this.moves = false; // bewegt sich nicht mehr, moveTo ist IsMoving
		
	}
	
}
