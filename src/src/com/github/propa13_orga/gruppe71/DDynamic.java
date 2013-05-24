package src.com.github.propa13_orga.gruppe71;

import javax.swing.JOptionPane;



public class DDynamic {
	private DPanel SpielPanel;
	private StaticObject[][] StaticObjects; // private int[][] StaticObjects; 
	private int CurrentXPos;  //Die derzeitige X Position
	private int CurrentYPos; //Die derzeitige Y Position
	private int MoveToXPos; //Bewegung in X Richtung
	private int MoveToYPos; //Bewegung in Y Richtung
	private boolean moves; //Entscheidet ob Bewegt oder nicht
	private int Lives;
	private int Points;
	private boolean checkpoint;
	
	public DDynamic(DPanel pPanel, StaticObject[][] pStaticObjects, int pCurrentXPos, int pCurrentYPos, int pLeben, int pPunkte,boolean check){
		this.SpielPanel = pPanel;
		this.StaticObjects = pStaticObjects;
		this.CurrentXPos = pCurrentXPos;
		this.CurrentYPos = pCurrentYPos;
		this.MoveToXPos = -1;
		this.MoveToYPos = -1;
		this.moves = false;
		this.Lives = pLeben; 
		this.Points = pPunkte;
		this.checkpoint=check;
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
		if( this.StaticObjects[(pYPos/30)][(pXPos/30)].getCollision() == false){ //Keine Kollision also bewege
			this.moves = true; //Objekt bewegt sich jetzt also = 1
			this.MoveToXPos = pXPos;
			this.MoveToYPos = pYPos;
		}
		// AKTIONEN JE NACH TYP
		switch(this.StaticObjects[(pYPos/30)][(pXPos/30)].getType()){
		
		case 3: //lade neues Level -neuer Level Abschnitt und bekomme Punkte
			if(SpielPanel.Modus2Spieler()==2){ //2 Spieler Modus
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
			this.LoseLife();
			break;
		case 7: //Checkpoint
			this.setCheck(true); //Hier noch Position speichern und des Checkpoints
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
	//Hilfsfunktionen fï¿½r Animate Moving
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
			this.ErhoeheXUm(2); //muss noch ein Stï¿½ck nach rechts

			if(this.CurrentXPos > this.MoveToXPos)
			this.ErhoeheXUm(-2); //muss noch ein Stï¿½ck nach links

			if(this.CurrentYPos < this.MoveToYPos)
			this.ErhoeheYUm(2); //muss noch ein Stï¿½ck nach unten

			if(this.CurrentYPos > this.MoveToYPos)
			this.ErhoeheYUm(-2); //muss noch ein Stï¿½ck nach oben

			//Wenn wir fertig sind, setzen wir die Variable wieder, dass es sich momentan nicht bewegt
			if(this.CurrentYPos == this.MoveToYPos && this.CurrentXPos == this.MoveToXPos)
			this.moves = false; // bewegt sich nicht mehr, moveTo ist IsMoving
		
	}
	
	public int getLives(){//Setzt Leben
		return this.Lives;	
	}
	public void setLives(int lp){ //lp=Lifepoints
		this.Lives += lp;
	}
	
	public int getPoints(){ //Bekomme Punkte
		return this.Points;
	}
	public void setPoints(int pkt) //Erhoeht Punkte des Spielers bei Erreichen eines Levelabschnitts
	{
		this.Points = pkt;
	}
	
	/*Methode Leben verlieren kann in
	 *  Verknüpfung mit Schadenssystem
	 *   benutzt werden
	 * @param Nichts
	 */
	public void LoseLife(){ 
		int p;
			if(this.getLives()!=0){
					this.setLives(-1);	
				}
			else if(this.getLives()==0){
						p=this.SpielPanel.Revive();
							if(p!=JOptionPane.YES_OPTION){
								this.SpielPanel.beendeSpiel();
							}
								else if(p==JOptionPane.YES_OPTION){//Hier Wiederbeleben
								}	
							}
			else this.SpielPanel.beendeSpiel();
		}
	
	public boolean getCheck(){//Ist CheckPoint vorhanden?
		return checkpoint;
	}
	public void setCheck(boolean c){//Setzt CheckPoint
		this.checkpoint=c;
	}
	public String CheckAussage(){
		if(getCheck()==true)
			return "Sie haben einen Checkpoint, wenn Sie ihn Nutzen wollen... Dann nur zu!";
			else 
				return "Sie haben sowieso keinen Checkpoint! Pech gehabt :)!";
		}

	
	public void Items(){
		
	}
	
}
