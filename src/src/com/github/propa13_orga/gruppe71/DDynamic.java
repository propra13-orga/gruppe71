					package src.com.github.propa13_orga.gruppe71;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.JOptionPane;



public class DDynamic {
	protected DSound sound;
	protected DPanel SpielPanel;
	protected StaticObject[][] StaticObjects; // private int[][] StaticObjects; 
	protected DDynamic[] DynamicObjects; // private int[][] StaticObjects; 
	protected DProjectile[] Projectiles; // private int[][] StaticObjects; 
	protected int CurrentXPos;  //Die derzeitige X Position
	protected int CurrentYPos; //Die derzeitige Y Position
	protected int MoveToXPos; //Bewegung in X Richtung
	protected int MoveToYPos; //Bewegung in Y Richtung
	protected boolean moves; //Entscheidet ob Bewegt oder nicht
	protected int Direction;

	protected int counter; //Für mehrere Sounds
	protected int Health; // Gesundheit
	protected int Lives; // Leben
	protected int Money; // Geld
	protected int Mana; // Mana / Zauberpunkte
	protected  int Type; // Typ des Bots(normal, Boss etc.)
	protected boolean isBot; // Ist ein Bot oder Spieler?
	protected int Points; //Punkte
	
	protected int[] quest; // Quest für JOPTIONPANE
	protected int[] qenabled;//für SpielPanel Environment ändern
	protected boolean[] qreceived;//wenn schon bekommen Quest
	protected boolean[] qalready;
	protected boolean[] qdenied;//KEINE QUEST
	protected boolean[] treasure;//Belohnungen
	protected int marke;
	
	protected int[] items;
	protected int ActiveItem;
	protected String[] name,datei;
	protected boolean secret;
	protected boolean secret2;
	
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
			this.Lives = 0;
		}else{
			this.Type = 0;
			this.Lives = 3;
		}
		
		this.counter=2;
		this.items=new int[itemnumber];
		this.InitItems();
		this.name=new String[itemnumber];
		this.InitName();
		this.ActiveItem = -1;
		this.secret=false;
		this.secret2=false;
		this.SoundDateien();
		
		//Quest Inits
		this.quest=new int[3];
		this.QuestReceived();
		this.InitBelohnungen();
		this.QuestAlready();
		this.InitDenied();
		this.qenabled=new int[3];
		this.marke=0;

		
		
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
		
		// Schaden hinzufÃ¼gen wenn Gegner und Spieler sich beruehren
		 for (int i = 0; i < this.DynamicObjects.length; i++){ // Wenn der Gegner an der gleichen Position ist verlieren beide ein Leben
			 if(this.DynamicObjects[i] != null && (this.DynamicObjects[i].getHealth() > 0 && this.getHealth() > 0)){ //Wenn existieren und beide Gesundheit haben
				 if(this.DynamicObjects[i].isBot != this.isBot && ((this.DynamicObjects[i].IsMoving() == false && this.DynamicObjects[i].getCurrentXPosition() == pXPos && this.DynamicObjects[i].getCurrentYPosition() == pYPos) || (this.DynamicObjects[i].IsMoving() == true && this.DynamicObjects[i].getMoveToXPosition() == pXPos && this.DynamicObjects[i].getMoveToYPosition() == pYPos) ) ) {
					 //WENN nicht beide Bots sind & beide auf die gleiche Position wollen(Unterscheidung IsMoving)
					 sound=new DSound(datei[0]); //Schmatzen
					 sound.SetVolume(-10);//
					 sound.Abspielen();
					 //Ende
					 this.LoseHealth();
					 this.DynamicObjects[i].LoseHealth(); 
					 System.out.println("SCHADEN: "+this.getType()+" -> "+this.DynamicObjects[i].getType());
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
		if(this.isBot == false && this.getType() < 1){
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
					sound=new DSound(datei[10]); //Item aufsammeln
					sound.SetVolume(-10);//
					sound.Abspielen();
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
				sound=new DSound(datei[4]); //Trittstelle
				 sound.SetVolume(-5);//
				 sound.Abspielen();
				this.SetSecret2(true);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0);
				break;
			
				
			
			case 19://Secret
				 sound=new DSound(datei[3]); //Teleport
				 sound.SetVolume(-5);//
				 sound.Abspielen();
				System.out.println("Was passiert jetzt???");
				this.SetSecret(true);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0);
				break;
				

			case 20: // Kaese
				System.out.println("Kaese aufgenommen");
				this.name[1]="Kaese";
				this.items[1]+=5;
				sound=new DSound(datei[1]); //Item aufsammeln
				sound.SetVolume(0);//
				sound.Abspielen();
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
				 sound=new DSound(datei[1]); //Item aufsammeln
				 sound.SetVolume(0);//
				 sound.Abspielen();
				if(this.ActiveItem == -1)
				this.ActiveItem = this.StaticObjects[(pYPos/30)][(pXPos/30)].getType(); //Aktives Item merken 
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 23: // Leben
				this.Lives++;
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 24: // Money / Geld
				 sound=new DSound(datei[2]); //Geld aufsammeln
				 sound.SetVolume(0);//
				 sound.Abspielen();
				Random zufallsZahl = new Random();				
				this.Money += (zufallsZahl.nextInt(4)+1); //Zahl zwischen 1 und 5
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 25: // NPC
				if(this.SpielPanel.getCurrentLevel() == 0 && this.SpielPanel.getCurrentLevelSection() == 0 && this.qalready[0]==false){//Quest 1
				   this.quest[0]=JOptionPane.showOptionDialog(null, "Hört mich an EDLER BURGER!",
			                  "Quest 1", JOptionPane.YES_NO_CANCEL_OPTION,
			                  JOptionPane.WARNING_MESSAGE, null, 
			                  new String[]{"Schießt los heißer Hund!", "ICH AUF EINE NIEDERE SPEISE HÖREN?????!"}, "ICH AUF EINE NIEDERE SPEISE HÖREN?????!");
				   this.Quest1(quest[0]);
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
			this.CurrentXPos += 3; //muss noch ein Stï¿½ck nach rechts

			if(this.CurrentXPos > this.MoveToXPos)
			this.CurrentXPos -= 3; //muss noch ein Stï¿½ck nach links

			if(this.CurrentYPos < this.MoveToYPos)
			this.CurrentYPos += 3; //muss noch ein Stï¿½ck nach unten

			if(this.CurrentYPos > this.MoveToYPos)
			this.CurrentYPos -= 3; //muss noch ein Stï¿½ck nach oben

			//Wenn wir fertig sind, setzen wir die Variable wieder, dass es sich momentan nicht bewegt
			if(this.CurrentYPos == this.MoveToYPos && this.CurrentXPos == this.MoveToXPos)
			this.moves = false; // bewegt sich nicht mehr, moveTo ist IsMoving
		
	}
	
	/**
	 * Fuehrt Aktion aus
	 * @param pType Art der Aktion
	 */
	public void Action(int pType){
		
		System.out.println(pType);
		if((pType == 0 && this.Mana > 0) || (pType == 1 && this.ActiveItem > -1) || pType > 2){
			
			int tmpType = pType; // Setzt = 0 oder 1
		
			if(tmpType < 3){
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
			}
			this.WeaponSound(tmpType);//Sound Weapon
			for(int i = 0; i < this.Projectiles.length; i++){
				if(this.Projectiles[i] == null){
					// Wenn kein Projektil an der Steller oder ein inaktives
					this.Projectiles[i] = new DProjectile(this, SpielPanel, StaticObjects, DynamicObjects, this.CurrentXPos, this.CurrentXPos, tmpType, this.Direction);
					this.Projectiles[i].setCurrentPosition(this.CurrentXPos, this.CurrentYPos);
					this.Projectiles[i].setDirection(this.Direction);
					this.Projectiles[i].setType(tmpType);
					System.out.println("Neues Proj(von "+this.getType()+"): Pos X: "+this.Projectiles[i].getCurrentXPosition()+" Y: "+this.Projectiles[i].getCurrentYPosition()+ " Dir: "+this.Projectiles[i].getDirection()+" T: "+this.Projectiles[i].getType()+" CR: "+this.Projectiles[i].getCurrentRange()+" MR: "+this.Projectiles[i].getMaxRange());
					i = this.Projectiles.length; //Schleife beenden
				}else if(this.Projectiles[i].IsEnabled() == false){
					// Wenn kein Projektil an der Steller oder ein inaktives
					this.Projectiles[i].setCurrentPosition(this.CurrentXPos, this.CurrentYPos);
					this.Projectiles[i].setDirection(this.Direction);
					this.Projectiles[i].setType(tmpType);
					this.Projectiles[i].setShooter(this);				
					System.out.println("Neues Proj(von "+this.getType()+"): Pos X: "+this.Projectiles[i].getCurrentXPosition()+" Y: "+this.Projectiles[i].getCurrentYPosition()+ " Dir: "+this.Projectiles[i].getDirection()+" T: "+this.Projectiles[i].getType()+" CR: "+this.Projectiles[i].getCurrentRange()+" MR: "+this.Projectiles[i].getMaxRange());
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
		
		if(this.SpielPanel.getDebugMode() == true)
			System.out.println("Verliere Leben Typ: "+ this.getType());
		
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
	
	
	/** Gibt die Aussage fÃ¼r das Checkpoint Fenster zurueck
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
	
	/**
	 * Alle SoundDateien für DDynamic
	 * @return
	 */
	public String[] SoundDateien(){
		datei=new String[99];
		
		datei[0]="src/com/github/propa13_orga/gruppe71/Schmatz.wav"; //Kauen
		datei[1]="src/com/github/propa13_orga/gruppe71/Collect.wav"; //Item aufsammeln
		datei[2]="src/com/github/propa13_orga/gruppe71/Geld.wav"; //Geld
		datei[3]="src/com/github/propa13_orga/gruppe71/Teleport.wav"; //Teleport
		datei[4]="src/com/github/propa13_orga/gruppe71/Tuer.wav";//Tuer
		datei[5]="src/com/github/propa13_orga/gruppe71/Magie_1.wav";//Magie 1
		datei[6]="src/com/github/propa13_orga/gruppe71/Magie_2.wav";//Magie_2
		datei[7]="src/com/github/propa13_orga/gruppe71/Slash.wav";//Messer
		datei[8]="src/com/github/propa13_orga/gruppe71/KaeseBazooka.wav";//Kaese
		datei[9]="src/com/github/propa13_orga/gruppe71/GoodBye.wav";//Bye
		datei[10]="src/com/github/propa13_orga/gruppe71/NextLevel.wav";//Nächster Level
		return this.datei;
		
	}
	/**
	 * Sound wird hier abgespielt -> Waffensound!
	 * @param p ist der Typ der Projektile
	 */
	public void WeaponSound(int p){
		
	switch(p){
	case 0:
		if((this.counter%2)==0){//Magie_1
		 sound=new DSound(datei[5]);
		 sound.SetVolume(-10);
		 sound.Abspielen();
		 this.setCounter(1);
		 break;
		}
		else if((this.counter%2)==1){
		sound=new DSound(datei[6]);//Magie_2
		 sound.SetVolume(-10);
		 sound.Abspielen();
		 this.setCounter(1);
		break;
		}
	case 2:
		sound=new DSound(datei[7]); //Messer
		 sound.SetVolume(-10);
		 sound.Abspielen();
		 break;
	case 1:
		sound=new DSound(datei[8]); //Kaese
		 sound.SetVolume(-10);
		 sound.Abspielen();
		 break;
		
	}
	
	}
	public int setCounter(int p){
		this.counter+=p;
		return this.counter;
}	
	
	public void Quest1(int p){
		if(p==JOptionPane.YES_OPTION){
		 this.qenabled[0]=JOptionPane.showOptionDialog(null, "\nENDLICH EIN EDLER SEINER ZUNFT! DIE SOßEN SOLLEN DIR IMMER HOLD SEIN..." +
		 		"\nDIE KÄFER SIND WIEDER IM ANMARSCH! WENN IHR --5-- von IHNEN TÖTEN KÖNNTET, WÜRDET IHR \nDEN HEISSEN HUNDEN CLAN FÜR IMMER IN ERRINNERUNG BLEIBEN\n" +
		 		"BITTE BEEILT EUCH!\n(flüstern: Natürlich gibt es auch eine große Belohnung...)",
                 "Quest 1 ANGENOMMEN", JOptionPane.OK_OPTION,
                 JOptionPane.INFORMATION_MESSAGE, null, 
                 new String[]{"Ich tue was ich kann!"}, "Ich tue was ich kann!");
		 if(this.qalready[0]==false){
		 this.qreceived[0]=true;
		 }
		 this.qalready[0]=true;
		}
		
		
		
		else if(p!=JOptionPane.YES_OPTION){
			 JOptionPane.showMessageDialog(null, "VERFLUCHET SEID IHR!!!");
			 this.qreceived[0]=true;
			 this.qdenied[0]=true;
		 }
		}
	
	
	public void Information(){
		JOptionPane.showMessageDialog(null, "Aufgepasst edler Burger.\nAuf eurer Reise werdet ihr viele Gefahren entdecken"
				+"\n Deshalb Rüstet euch gut aus und vergesst nicht Coins aufzusammeln"+"\n Sie können das Spiel nur beenden,wenn sie den Endboss besiegt haben!"
				+"\n Viel Glück!");
	}
	
	
	public boolean[] QuestReceived(){
		this.qreceived=new boolean[3];
		for(int i=0; i<this.qreceived.length;i++){
			this.qreceived[i]=false;
		}
		return this.qreceived;
	}
	
	//Checkt ob schon der NPC berührt wurde
	public boolean[] QuestAlready(){
		this.qalready=new boolean[3];
		for(int i=0; i<this.qalready.length;i++){
			this.qalready[i]=false;
		}
		return this.qalready;
	}
	public boolean getQuestLaufend(int p){
		return this.qalready[p];
	}
	//Quest im SpielPanel Boden
	public boolean getQuest(int p){
		return this.qreceived[p];
	}
	
	//Quest im SpielPanel Boden
		public boolean setQuest(boolean p,int x){
			return this.qreceived[x]=p;
		}
	//Wie viele Quests?
	public int QuestLength(){
		return this.qreceived.length;
	}
	public int getMarke(){
		return this.marke;
	}
	public int setMarke(int p){
		return this.marke+=p;
	}
	//Für die einzelnen Quests die Belohnungen
	public boolean[] InitBelohnungen(){
		this.treasure=new boolean[3];
		for(int i=0; i<this.treasure.length;i++){
			this.treasure[i]=false;
		}
		return this.treasure;
	}
	public boolean[] InitDenied(){
		this.qdenied=new boolean[3];
		for(int i=0; i<this.qdenied.length;i++){
			this.qdenied[i]=false;
		}
		return this.qdenied;
	}
	
	public boolean QuestDenied(int p){
		return this.qdenied[p];
	}
	public boolean setTreasure(boolean m,int p){
		return this.treasure[p]=m;
	}
}
	
