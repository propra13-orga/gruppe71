					package src.com.github.propa13_orga.gruppe71;

import java.awt.Graphics;

import javax.swing.JOptionPane;



public class DDynamic {
	
	
	private DPanel SpielPanel;
	private StaticObject[][] StaticObjects; // private int[][] StaticObjects; 
	private DDynamic[] DynamicObjects; // private int[][] StaticObjects; 
	private DProjectile[] Projectiles; // private int[][] StaticObjects; 
	private int CurrentXPos;  //Die derzeitige X Position
	private int CurrentYPos; //Die derzeitige Y Position
	private int MoveToXPos; //Bewegung in X Richtung
	private int MoveToYPos; //Bewegung in Y Richtung
	private boolean moves; //Entscheidet ob Bewegt oder nicht
	private int Direction;

	private int Health; // Gesundheit
	private int Lives; // Leben
	private int Money; // Geld
	private int Mana; // Mana / Zauberpunkte
	private  int Type; // Typ des Bots(normal, Boss etc.)
	private boolean isBot; // Ist ein Bot oder Spieler?
	private int Points; //Punkte
	
	private int[] items;
	private int ActiveItem;
	private String[] name;
	private boolean secret;
	private boolean secret2;
	
	public DDynamic(DPanel pPanel, StaticObject[][] pStaticObjects, DDynamic[] pDynamicObjects, DProjectile[] pProjectiles, int pCurrentXPos, int pCurrentYPos, int pHealth, int pPunkte, boolean pisBot, int itemnumber){
		this.SpielPanel = pPanel;
		this.StaticObjects = pStaticObjects;
		this.DynamicObjects = pDynamicObjects;
		this.Projectiles = pProjectiles;
		this.CurrentXPos = pCurrentXPos;
		this.CurrentYPos = pCurrentYPos;
		this.MoveToXPos = -1;
		this.MoveToYPos = -1;
		this.moves = false;
		this.Direction = 0;
		
		this.Health = pHealth;
		this.Points = pPunkte;
		this.Money = 100;
		this.Mana = 50;
		this.isBot = pisBot;
		if(pisBot == true){
			this.Type = 1;
			this.Lives = 0;
		}else{
			this.Type = 0;
			this.Lives = 3;
		}
		this.items=new int[itemnumber];
		this.InitItems();
		this.name=new String[itemnumber];
		this.InitName();
		this.ActiveItem = -1;
		this.secret=false;
		this.secret2=false;
		
	}
    
	/**
	 * Gibt Momentane Position des Objekts zurueck
	 * @param NICHTS 
	 */
	public int[]getCurrentPosition(){
		  int[] CurrentPosition = new int[2]; //2 Rueckgabewerte: x und y
		  //Index braucht man nicht, weil man ja schon im Objekt ist
		  CurrentPosition[0]=this.CurrentXPos;
		  CurrentPosition[1]=this.CurrentYPos;
		  return CurrentPosition;
	}
	

	/**
	 * Gibt Momentane X-Position des Objekts zurueck
	 * @param NICHTS 
	 */
	public int getCurrentXPosition(){
		  return this.CurrentXPos;
	}
	
	/**
	 * Gibt Momentane Y-Position des Objekts zurueck
	 * @param NICHTS 
	 */
	public int getCurrentYPosition(){
		  return this.CurrentYPos;
	}
	

	/**
	 * Setzt die Momentane Position des Objekts
	 * @param pXPos neue X Position
	 * @param pYPos neue Y Position
	 */
	public void setCurrentPosition(int pXPos, int pYPos){
		this.CurrentXPos=pXPos;
		this.CurrentYPos=pYPos;
	}

	/**
	 * Gibt zurueck, ob das Objekt sich gerade bewegt
	 * @param NICHTS 
	 */
	public boolean IsMoving(){
		return this.moves;
	}
	
	/**
	* Gibt zurueck, ob das Objekt ein Bot ist
	* @param NICHTS
	*/
	public boolean IsBot(){
	return this.isBot;
	}
	
	/**
	* Gibt Type des Bots ist
	* @param NICHTS
	*/
	public int getType() {
		return this.Type;
	}

	/**
	* Setzt Type des Bots
	* @param pType neuer Type des Bots
	*/
	public void setType(int pType){
		this.Type = pType;
	}
	/**
	 * Setzt, ob das Objekt sich bewegt
	 * @param pMoves bewegt es sich?
	 */
	public void setMoves(boolean pMoves){
		this.moves = pMoves;
	}
	
	/**
	 * Gibt MoveTo Position zurueck
	 * @param NICHTS 
	 */
	public int[] getMoveToPosition(){
		int[] PositionMoving=new int[2];
		PositionMoving[0]=this.MoveToXPos;
		PositionMoving[1]=this.MoveToYPos;
		return PositionMoving;
			
	}
	
	/**
	 * Gibt MoveTo X-Position des Objekts zurueck
	 * @param NICHTS 
	 */
	public int getMoveToXPosition(){
		  return this.MoveToXPos;
	}
	
	/**
	 * Gibt MoveTo Y-Position des Objekts zurueck
	 * @param NICHTS 
	 */
	public int getMoveToYPosition(){
		  return this.MoveToYPos;
	}
	
	/**
	 * Gibt StaticObjects zurueck
	 * @param NICHTS 
	 */
	public StaticObject[][] getStaticObjects(){
		return this.StaticObjects;
	}
	
	/**
	 * Setzt StaticObjects einem Wert
	 * @param pStaticObjects Statisches Objekt
	 */
	public void setStaticObjects(StaticObject[][] pStaticObjects){
		this.StaticObjects = pStaticObjects;
	}
	
	/**
	 * Setzt die neue Position x und y eines dyn. Objektes(z.B. Player), wo es hin gehen soll
	 * @param pXStart Neue X Position
	 * @param pYStart Neue Y Position
	 */
	public void setMoveToPosition(int pXPos, int pYPos){
		
		boolean tmpAndererBotAnPos = false; // Collision zwischen Bots
		
		// Schaden hinzufügen wenn Gegner und Spieler sich beruehren
		 for (int i = 0; i < this.DynamicObjects.length; i++){ // Wenn der Gegner an der gleichen Position ist verlieren beide ein Leben
			 if(this.DynamicObjects[i] != null){
				 if(this.DynamicObjects[i].isBot != this.isBot && ((this.DynamicObjects[i].IsMoving() == false && this.DynamicObjects[i].getCurrentXPosition() == pXPos && this.DynamicObjects[i].getCurrentYPosition() == pYPos) || (this.DynamicObjects[i].IsMoving() == true && this.DynamicObjects[i].getMoveToXPosition() == pXPos && this.DynamicObjects[i].getMoveToYPosition() == pYPos) ) ) {
					 //WENN nicht beide Bots sind & beide auf die gleiche Position wollen(Unterscheidung IsMoving)
					 this.LoseHealth();
					 this.DynamicObjects[i].LoseHealth();
				 }else if(((this.DynamicObjects[i].IsMoving() == false && this.DynamicObjects[i].getCurrentXPosition() == pXPos && this.DynamicObjects[i].getCurrentYPosition() == pYPos) || (this.DynamicObjects[i].IsMoving() == true && this.DynamicObjects[i].getMoveToXPosition() == pXPos && this.DynamicObjects[i].getMoveToYPosition() == pYPos) ) ) {
					 //WENN Beides Bots und gleiche Position
					 tmpAndererBotAnPos = true; //
				 }
			 }
		 }
		 
		 if(this.StaticObjects[(pYPos/30)][(pXPos/30)].getCollision() == false && tmpAndererBotAnPos == false){ //Keine Kollision also bewege
				this.moves = true; //Objekt bewegt sich jetzt also = 1
				this.MoveToXPos = pXPos;
				this.MoveToYPos = pYPos;
		}
		 
		// AKTIONEN JE NACH TYP
		if(this.isBot == false){
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
				
			case 6: // Objekt ist eine Falle!
				this.LoseHealth();
				break;
			
			case 16:
				if(this.getSecret2()==true){
					this.SetSecret2(false);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0);
				}
				break;
			
				
			case 17://Trittstelle
				System.out.println("Was passiert jetzt???");
				this.SetSecret2(true);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0);
				break;
			
				
			
			case 19://Secret
				System.out.println("Was passiert jetzt???");
				this.SetSecret(true);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0);
				break;
				

			case 20: // Kaese
				System.out.println("Kaese aufgenommen");
				this.name[1]="Kaese";
				this.items[1]+=1;
				if(this.ActiveItem == -1)
				this.ActiveItem = this.StaticObjects[(pYPos/30)][(pXPos/30)].getType(); //Aktives Item merken 
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 21: // Healthpack
				if(this.Health < 4) //Wenn keine Ruestung
				this.Health = 4; // Gesundheit wieder voll machen
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 22: // Messer
				this.name[0]="Messer";
				this.items[0]+=1;
				if(this.ActiveItem == -1)
				this.ActiveItem = this.StaticObjects[(pYPos/30)][(pXPos/30)].getType(); //Aktives Item merken 
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 23: // Leben
				this.Lives++;
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 24: // Money / Geld
				this.Money++;
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 25: // NPC
				if(this.SpielPanel.getCurrentLevel() == 0 && this.SpielPanel.getCurrentLevelSection() == 0 ){
					JOptionPane.showMessageDialog(null, " Hallo BURGER NR. 1, \n versuch so schnell wie m�glich an das \n Ziel zu gelangen ohne dabei von Ungezif-\nfer gefressen zu werden. Auf dem Weg ver-\n streute Items k�nnten dir dabei n�tzlich sein. \n Sammel genug Geld um dich im Shop auszur�sten.");
				}
				else if(this.SpielPanel.getCurrentLevel() == 1 && this.SpielPanel.getCurrentLevelSection() == 0 ){
					JOptionPane.showMessageDialog(null, "Es war einmal 1...");
				}
				else if(this.SpielPanel.getCurrentLevel() == 2 && this.SpielPanel.getCurrentLevelSection() == 0 ){
					JOptionPane.showMessageDialog(null, "Es war einmal 2...");
				}
				break;

			case 26: // Ruestung
				System.out.println("Ruestung aufgenommen");
				this.Health = 8; // Ruestung = Doppelte Gesundheit
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 27: // Shop
				System.out.println("Shop beruehrt");
				this.Shop();
				System.out.println(this.getMoney());
				break;

			case 28: // Zaubertrank
				System.out.println("Zaubertrank aufgenommen");
				this.Mana += 10;
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

				
			}
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
			
			if(pWhere == "up" && tmpCurrentPosition[1] > 0){ //bewege nach oben
				this.setMoveToPosition(tmpCurrentPosition[0], (tmpCurrentPosition[1]-30));
				this.Direction = 0;
			}
			
			if(pWhere == "right" && tmpCurrentPosition[0] < 570){ //bewege nach rechts
				this.setMoveToPosition((tmpCurrentPosition[0]+30), tmpCurrentPosition[1]);
				this.Direction = 1;
			}

			if(pWhere == "down" && tmpCurrentPosition[1] < 330){ //bewege nach unten
				this.Direction = 2;
				this.setMoveToPosition(tmpCurrentPosition[0], (tmpCurrentPosition[1]+30));
			}
			
			if(pWhere == "left" && tmpCurrentPosition[0] > 0){ //bewege nach links
				this.setMoveToPosition((tmpCurrentPosition[0]-30), tmpCurrentPosition[1]);
				this.Direction = 3;
			}
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
	 * Fuehrt Aktion aus
	 * @param pType Art der Aktion
	 */
	public void Action(int pType){
		
		if((pType == 0 && this.Mana > 0) || (pType == 1 && this.ActiveItem > -1)){
			
			int tmpType = pType; // Setzt = 0 oder 1
			
			if(tmpType == 1){
				if(this.ActiveItem != 20){ // Wenn kein Kaese
					tmpType = 2; //Setze = 2 -> Messer
				}else{ //Wenn Kaese
					if(this.items[1] == 1){
						this.name[1] = "Leer";
						this.items[1] = 0;
						
						if(this.items[0] > 0) //Wenn Messer
							this.ActiveItem = 22; //Setze Messer als Waffe
						else
							this.ActiveItem = -1; //Sonst Keine Waffe mehr
						
					}else{
						this.name[1] = "Kaese";
						this.items[1]--;
					}
				}
			}else{
				this.Mana--;
			}
			
			for(int i = 0; i < this.Projectiles.length; i++){
				if(this.Projectiles[i] == null){
					// Wenn kein Projektil an der Steller oder ein inaktives
					this.Projectiles[i] = new DProjectile(this, SpielPanel, StaticObjects, DynamicObjects, this.CurrentXPos, this.CurrentXPos, tmpType, this.Direction);
					i = this.Projectiles.length; //Schleife beenden
				}else if(this.Projectiles[i].IsEnabled() == false){
					// Wenn kein Projektil an der Steller oder ein inaktives
					this.Projectiles[i].setCurrentPosition(this.CurrentXPos, this.CurrentYPos);
					this.Projectiles[i].setDirection(this.Direction);
					this.Projectiles[i].setType(tmpType);
					
					i = this.Projectiles.length; //Schleife beenden
				}
			}
		}
		
	}
	
	/**
	 * Wechselt Waffe, wenn vorhanden
	 * @param pType Art der Aktion
	 */
	public void changeWeapon(){
		if(this.items[0] > 0 && this.ActiveItem == 20){
			this.ActiveItem = 22;
		}else if(this.items[1] > 0 && this.ActiveItem == 22){
			this.ActiveItem = 20;
		}else{
			if(this.items[0] > 0)
				this.ActiveItem = 22;
			else if(this.items[1] > 0)
				this.ActiveItem = 20;
			else
				this.ActiveItem = -1;
		}
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
	* Gibt Money zurueck
	* @param NICHTS
	*/
	public int getMoney(){
		return this.Money;	
	}

	/**
	* Setzt Money
	* @param pMoney Wieviel Geld?
	*/
	public void setMoney(int pMoney){
		this.Money += pMoney;
	}	

	/**
	* Gibt Mana zurueck
	* @param NICHTS
	*/
	public int getMana(){
		return this.Mana;	
	}

	/**
	* Setzt Mana
	* @param pMana Wieviel Mana?
	*/
	public void setMana(int pMana){
		this.Mana += pMana;
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
	* Gibt Aktives Item zurueck
	* @param NICHTS
	*/
	public int getActiveItem(){
		return this.ActiveItem;	
	}

	/**
	* Setzt Aktives Item
	* @param pActiveItem Aktives Item?
	*/
	public void setActiveItem(int pActiveItem){
		this.ActiveItem += pActiveItem;
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
	
	/**
	 * Guckt wie viele Items gesetzt wurden
	 * @param Keine Parameter
	 */
	public int NumberItems(){
		return this.items.length;
	}
	

	/**
	 * Initialisiert Items 
	 * @param Keine Parameter
	 */

	
	/**
	 * Initialisiert Items (bis jetzt ITEMS =!  7  ! im DPanel init)
	 * @param Keine Parameter
	 */
	
	public int[] InitItems(){
		this.items=new int[NumberItems()];
		for(int i=0;i<=NumberItems()-1;i++){
			this.items[i]=0;
		}
		return this.items;
	}
	
	/**
	 * 	Fuer DItems und DShop
	 */
	public int AnzahlItems(int p){
	 
		return this.items[p];
	}
	
	//Addiere die neuen Items vom DShop
	public int SetItems(int i,int p){
		this.items[i]+=p;
		return this.items[i];
	}
	
	public int[] setItems(int p){
		for(int i=0;i<=NumberItems()-1;i++){
			this.items[i]=p;
		}
		return this.items;
	}
	
	/**
	 * Fuer DItems
	 * @param p
	 * @return
	 */
	public String getName(int p){
		return this.name[p];
	}
	//Teleport, Initializer
	public boolean SetSecret(boolean p){
		return this.secret=p;	
	}
	public boolean getSecret(){
		return this.secret;
	}
	
	//Geheimtuer
	public boolean SetSecret2(boolean p){
		return this.secret2=p;	
	}
	public boolean getSecret2(){
		return this.secret2;
	}
	
	/**
	 * Initialisiert Namensliste alles zuerst auf "Leer"
	 * @param Keine Parameter
	 */
	public String[] InitName(){
		
		this.name=new String[NumberItems()];
		for(int i=0;i<=NumberItems()-1;i++){
			this.name[i]="Leer";
		}
		return this.name;
	}
	
	/**
	 * Kann per Taste i im Spiel aufgerufen werden
	 * @param Keine Parameter
	 */
	public void ItemBag(){
		DItems it = new DItems(SpielPanel);
		}
	
	public void Shop(){
		DShop shop=new DShop(SpielPanel);
	}
	}
	

	
	
	
