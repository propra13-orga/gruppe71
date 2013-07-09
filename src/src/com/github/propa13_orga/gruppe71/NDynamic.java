package src.com.github.propa13_orga.gruppe71;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.JOptionPane;


/**
* Klasse der Dynamischen Objekte im Netzwerkspiel 
*/
public class NDynamic {
	
	private static final long serialVersionUID = 6109931997164167600L;
	protected NSound sound;
	protected NPanel SpielPanel;
	protected StaticObject[][] StaticObjects; // private int[][] StaticObjects; 
	protected NDynamic[] DynamicObjects; // private int[][] StaticObjects; 
	protected int DynamicObjectIndex;
	protected NProjectile[] Projectiles; // private int[][] StaticObjects; 
	protected int CurrentXPos;  //Die derzeitige X Position
	protected int CurrentYPos; //Die derzeitige Y Position
	protected int MoveToXPos; //Bewegung in X Richtung
	protected int MoveToYPos; //Bewegung in Y Richtung
	protected boolean moves; //Entscheidet ob Bewegt oder nicht
	protected int Direction;

	protected int counter; //Fuer mehrere Sounds
	protected int Health; // Gesundheit
	protected int Lives; // Leben
	protected int Money; // Geld
	protected int Mana; // Mana / Zauberpunkte
	protected  int Type; // Typ des Bots(normal, Boss etc.)
	protected boolean isBot; // Ist ein Bot oder Spieler?
	protected int Points; //Punkte
	protected int RType; // Ruestungstyp
	
	public int[] quest; // Quest fuer JOPTIONPANE
	protected int[] qenabled;//fuer SpielPanel Environment aendern
	protected boolean[] qreceived;//wenn schon bekommen Quest
	public boolean[] qalready;
	protected boolean[] qdenied;//KEINE QUEST
	protected boolean[] treasure;//Belohnungen
	protected int marke;
	protected boolean info;
	protected boolean key;
	protected int hidden;
	protected int ok;// Fuer Schluessel JOptionPane
	protected int posX;//Spezielle Positionen fuer key
	protected int posY;
	
	protected int[] items;
	protected int ActiveItem;
	protected String[] name,datei;
	protected boolean secret;
	protected boolean secret2;

	protected int exp;//Erfahrungspunkte
	protected int level; //Level des Spielers
	protected int[] grenze;// fuer verschiedene Level Grenzen
	protected int skills;
	protected int[] rank;
	protected int[] wisdom,greed;
	protected int[] crit;//critical strike!
	protected int critted;
	protected int lifesteal;// setHealthh Methode
	protected boolean ultimaready;//Ultimate Lifesteal;
	
	public NDynamic(NPanel pPanel, StaticObject[][] pStaticObjects, NDynamic[] pDynamicObjects, int pDynamicObjectIndex, NProjectile[] pProjectiles, int pCurrentXPos, int pCurrentYPos, int pHealth, int pPunkte, boolean pisBot,int itemnumber,int e, int l,int sk,int money){
		this.SpielPanel = pPanel;
		this.StaticObjects = pStaticObjects;
		this.DynamicObjects = pDynamicObjects;
		this.DynamicObjectIndex = pDynamicObjectIndex;
		this.Projectiles = pProjectiles;
		this.CurrentXPos = pCurrentXPos;
		this.CurrentYPos = pCurrentYPos;
		this.MoveToXPos = -1;
		this.MoveToYPos = -1;
		this.moves = false;
		this.Direction = 0;
		
		this.Health = pHealth;
		this.Points = pPunkte;
		this.Money = money;
		this.Mana = 50;
		this.isBot = pisBot;
		if(pisBot == true){
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
		this.SoundDateien();
		
		//Quest Inits
		this.quest=new int[3];
		this.QuestReceived();
		this.InitBelohnungen();
		this.QuestAlready();
		this.InitDenied();
		this.qenabled=new int[3];
		this.marke=0;
		this.info=false;
		this.key=false;
		this.hidden=0;
		
		this.posX=0;
		this.posY=0;
		
		this.exp=e;
		this.level=l;
		this.InitLevelGrenze();
		this.skills=sk;
		
		this.critted=1;
		
		this.InitRank();
		this.InitWisdom();
		this.InitGreed();
		this.InitCrit();
		this.ultimaready=false;
	}
    
	/**
	 * Gibt Momentane Position des Objekts zurueck
	 *   
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
	 *   
	 */
	public int getCurrentXPosition(){
		  return this.CurrentXPos;
	}
	
	/**
	 * Gibt Momentane Y-Position des Objekts zurueck
	 *   
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
	 *   
	 */
	public boolean IsMoving(){
		return this.moves;
	}
	
	/**
	* Gibt zurueck, ob das Objekt ein Bot ist
	*  
	*/
	public boolean IsBot(){
	return this.isBot;
	}
	
	/**
	* Gibt Type des Bots ist
	*  
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
	* Gibt Type der Ruestung
	*  
	*/
	public int getRType() {
		return this.RType;
	}

	/**
	* Setzt Type der Ruestung
	* @param pRType neuer RType der Ruestung
	*/
	public void setRType(int pRType){
		this.RType = pRType;
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
	 *   
	 */
	public int[] getMoveToPosition(){
		int[] PositionMoving=new int[2];
		PositionMoving[0]=this.MoveToXPos;
		PositionMoving[1]=this.MoveToYPos;
		return PositionMoving;
			
	}
	
	/**
	 * Gibt MoveTo X-Position des Objekts zurueck
	 *   
	 */
	public int getMoveToXPosition(){
		  return this.MoveToXPos;
	}
	
	/**
	 * Gibt MoveTo Y-Position des Objekts zurueck
	 *   
	 */
	public int getMoveToYPosition(){
		  return this.MoveToYPos;
	}
	
	/**
	 * Gibt StaticObjects zurueck
	 *   
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
	 * @param pXPos Neue X Position
	 * @param pYPos Neue Y Position
	 */
	public void setMoveToPosition(int pXPos, int pYPos){
		int maeuse=0;
		int kaese=0;
		int messer=0;
		boolean tmpAndererBotAnPos = false; // Collision zwischen Bots
		
		// Schaden hinzufuegen wenn Gegner und Spieler sich beruehren
		 for (int i = 0; i < this.DynamicObjects.length; i++){ // Wenn der Gegner an der gleichen Position ist verlieren beide ein Leben
			 if(this.DynamicObjects[i] != null && (this.DynamicObjects[i].getHealth() > 0 && this.getHealth() > 0)){ //Wenn existieren und beide Gesundheit haben
				 if(this.DynamicObjects[i].isBot != this.isBot && ((this.DynamicObjects[i].IsMoving() == false && this.DynamicObjects[i].getCurrentXPosition() == pXPos && this.DynamicObjects[i].getCurrentYPosition() == pYPos) || (this.DynamicObjects[i].IsMoving() == true && this.DynamicObjects[i].getMoveToXPosition() == pXPos && this.DynamicObjects[i].getMoveToYPosition() == pYPos) ) ) {
					 //WENN nicht beide Bots sind & beide auf die gleiche Position wollen(Unterscheidung IsMoving)
					 sound=new NSound(datei[0]); //Schmatzen
					 sound.SetVolume(-10);//
					 sound.Abspielen();
					 //Ende
					 if(this.isBot == false && ((this.getRType() == 3 && this.DynamicObjects[i].getType() < 3) || (this.getRType() == 5 && 2 < this.DynamicObjects[i].getType())) ){ // Ueberprueft auf Ruestung 
						 this.setHealth(-1);
						 if (this.getHealth() <5 ){this.setRType(0);} //WENN Health <5 Setzte Ruestung auf Null
					 }
					 else if((this.isBot == true &&(this.DynamicObjects[i].getRType() == 3 && this.getType() < 3) || (this.DynamicObjects[i].getRType() == 5 && 2 < this.getType()) )){ // Ueberprueft auf Ruestung 
						 this.DynamicObjects[i].setHealth(-1);
						 if (this.DynamicObjects[i].getHealth() <5 ){this.DynamicObjects[i].setRType(0);//WENN Health <5 Setzte Ruestung auf Null
						 } 
					 }
					
					 else {
					 this.LoseHealth(1);
					 this.DynamicObjects[i].LoseHealth(1); 
					 }
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
					sound=new NSound(datei[10]); //Item aufsammeln
					sound.SetVolume(-10);//
					sound.Abspielen();
					this.SpielPanel.loadNextLevel(); //Lade naechsten Level
					
				}else{
					this.SpielPanel.beendeSpiel(); //Sonst beende Spiel
				}
				break;
				
			case 6: // Objekt ist eine Falle!
				this.LoseHealth(1);
				break;
			
			case 16:
				if(this.getSecret2()==true){
					this.SetSecret2(false);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setCollision(false);
				}
				break;
			
				
			case 17://Trittstelle
				sound=new NSound(datei[4]); //Trittstelle
				 sound.SetVolume(-5);//
				 sound.Abspielen();
				this.SetSecret2(true);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setCollision(false);
				break;
			
				
			
			case 19://Secret
				 sound=new NSound(datei[3]); //Teleport
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
				if(this.ActiveItem == -1)
				this.ActiveItem = this.StaticObjects[(pYPos/30)][(pXPos/30)].getType(); //Aktives Item merken 
				this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" ITEMS I0:"+this.items[0]+" N0:"+this.name[0]+" I1:"+this.items[1]+" N1:"+this.name[1]+" I2:"+this.items[2]+" N2:"+this.name[2]+" I3:"+this.items[3]+" N3:"+this.name[3]+" I4:"+this.items[4]+" N4:"+this.name[4]+" I5:"+this.items[5]+" N5:"+this.name[5]);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 21: // Healthpack
				if(this.Health < 4) //Wenn keine Ruestung
				this.Health = 4; // Gesundheit wieder voll machen
				this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" ATTRIBUTES L:"+this.Lives+" H:"+this.Health+" G:"+this.Money+" M:"+this.Mana+" AI:"+this.ActiveItem+" MARKE:"+this.marke);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 22: // Messer
				this.name[0]="Messer";
				this.items[0]+=1;
				 sound=new NSound(datei[1]); //Item aufsammeln
				 sound.SetVolume(0);//
				 sound.Abspielen();
				if(this.ActiveItem == -1)
				this.ActiveItem = this.StaticObjects[(pYPos/30)][(pXPos/30)].getType(); //Aktives Item merken 
				this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" ITEMS I0:"+this.items[0]+" N0:"+this.name[0]+" I1:"+this.items[1]+" N1:"+this.name[1]+" I2:"+this.items[2]+" N2:"+this.name[2]+" I3:"+this.items[3]+" N3:"+this.name[3]+" I4:"+this.items[4]+" N4:"+this.name[4]+" I5:"+this.items[5]+" N5:"+this.name[5]);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 23: // Leben
				this.Lives++;
				this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" ATTRIBUTES L:"+this.Lives+" H:"+this.Health+" G:"+this.Money+" M:"+this.Mana+" AI:"+this.ActiveItem+" MARKE:"+this.marke);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 24: // Money / Geld
				 sound=new NSound(datei[2]); //Geld aufsammeln
				 sound.SetVolume(0);//
				 sound.Abspielen();
				Random zufallsZahl = new Random();				
				this.Money += (zufallsZahl.nextInt(4)+1)+this.getCurrentGreed(this.DynamicObjects[0].getCurrentRank(1)); //Zahl zwischen 1 und 5
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 25: // NPC
				//NPC1
				if(this.SpielPanel.getCurrentLevel() == 0 && this.SpielPanel.getCurrentLevelSection() == 0 && this.qalready[0]==false){//Quest 1
					if((this.SpielPanel.isHost() == true && this.DynamicObjectIndex == 0)||(this.SpielPanel.isHost() == false && this.DynamicObjectIndex == 1)){
						this.quest[0]=JOptionPane.showOptionDialog(null, "Hoert mich an EDLER BURGER!",
			                  "Quest 1", JOptionPane.YES_NO_CANCEL_OPTION,
			                  JOptionPane.WARNING_MESSAGE, null, 
			                  new String[]{"Schiesst los heisser Hund!", "ICH AUF EINE NIEDERE SPEISE HOEREN?????!"}, "ICH AUF EINE NIEDERE SPEISE HOEREN?????!");
				   this.Quest1(quest[0],false,false);
					}
				}
				//NPC 2
				else if(this.SpielPanel.getCurrentLevel() == 1 && this.SpielPanel.getCurrentLevelSection() == 0 && this.qalready[1]==false ){
					if((this.SpielPanel.isHost() == true && this.DynamicObjectIndex == 0)||(this.SpielPanel.isHost() == false && this.DynamicObjectIndex == 1)){
						 this.quest[1]=JOptionPane.showOptionDialog(null, "Das Unheil...",
			                  "Quest 2", JOptionPane.YES_NO_CANCEL_OPTION,
			                  JOptionPane.WARNING_MESSAGE, null, 
			                  new String[]{"Hoere ich recht?", "Schon wieder ein Hund..."}, "Schon wieder ein Hund...");
				   this.Quest2(quest[1],false,false);
				   this.marke=0;
					}
				}
				//NPC 2 nach Schluesel Abgabe
				else if(this.SpielPanel.getCurrentLevel() == 1 && this.SpielPanel.getCurrentLevelSection() == 0 && this.qalready[1]==true && this.getKey()==true ){
					if((this.SpielPanel.isHost() == true && this.DynamicObjectIndex == 0)||(this.SpielPanel.isHost() == false && this.DynamicObjectIndex == 1)){
						this.ok=JOptionPane.showOptionDialog(null, "Danke..."+"\n Ihr seid doch ein Held?",
			                  "Quest 2", JOptionPane.OK_OPTION,
			                  JOptionPane.INFORMATION_MESSAGE, null, 
			                  new String[]{"ICH BEKOMME NICHTS?"}, "ICH BEKOMME NICHTS?");
					}
					 this.key=false;
					 this.hidden+=1;
					 this.marke=0;
				   
				}
				else if(this.SpielPanel.getCurrentLevel() == 1 && this.SpielPanel.getCurrentLevelSection() == 0 && this.key==false && this.hidden==2 ){
					if((this.SpielPanel.isHost() == true && this.DynamicObjectIndex == 0)||(this.SpielPanel.isHost() == false && this.DynamicObjectIndex == 1)){
						 JOptionPane.showOptionDialog(null, "Ahso...",
			                  "Quest 2 FERTIG", JOptionPane.OK_OPTION,
			                  JOptionPane.INFORMATION_MESSAGE, null, 
			                  new String[]{"MANIEREN HABT IHR KEINE!"}, "MANIEREN HABT IHR KEINE!");
					}
					 maeuse=1000;
					 kaese=15;
					 messer=1;
					 this.setMoney(maeuse);
					 this.items[0]+=messer;
					 this.items[1]+=kaese;
					this.Belohnung2(maeuse, kaese, messer);
					this.marke=0;
					this.hidden+=1;
					this.setExp(100+this.DynamicObjects[0].getCurrentGreed(this.DynamicObjects[0].getCurrentRank(0)));
					this.LevelUp();
				   
				}
				//NPC 3
				else if(this.SpielPanel.getCurrentLevel() == 2 && this.SpielPanel.getCurrentLevelSection() == 0 && this.qalready[2]==false ){
					if((this.SpielPanel.isHost() == true && this.DynamicObjectIndex == 0)||(this.SpielPanel.isHost() == false && this.DynamicObjectIndex == 1)){
						this.quest[2]=JOptionPane.showOptionDialog(null, "Euch schicken die 3 Sterne Koeche!",
			                  "Quest 3", JOptionPane.YES_NO_CANCEL_OPTION,
			                  JOptionPane.WARNING_MESSAGE, null, 
			                  new String[]{"Hoffentlich das letzte Mal...", "Ich bin nicht zu haben."}, "Ich bin nicht zu haben.");
						this.Quest3(quest[2], false, false);
						this.marke=0;
					}
				}
				break;

			case 26: // Ruestung
				System.out.println("Ruestung aufgenommen");
				this.Health = 8; // Ruestung = Doppelte Gesundheit
				this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" ATTRIBUTES L:"+this.Lives+" H:"+this.Health+" G:"+this.Money+" M:"+this.Mana+" AI:"+this.ActiveItem+" MARKE:"+this.marke);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				this.setRType(3); // RType setzen
				break;

			case 27: // Shop
				if((this.SpielPanel.isHost() == true && this.DynamicObjectIndex == 0)||(this.SpielPanel.isHost() == false && this.DynamicObjectIndex == 1)){
				System.out.println("Shop beruehrt");
				this.Shop();
				System.out.println(this.getMoney());
				}
				break;

			case 28: // Zaubertrank
				System.out.println("Zaubertrank aufgenommen");
				this.Mana += 10;
				this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" ATTRIBUTES L:"+this.Lives+" H:"+this.Health+" G:"+this.Money+" M:"+this.Mana+" AI:"+this.ActiveItem+" MARKE:"+this.marke);
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				
			case 41: // Truhe
				if(this.SpielPanel.getCurrentLevel() == 0 && this.SpielPanel.getCurrentLevelSection() == 0){
					maeuse+=150+this.DynamicObjects[0].getCurrentGreed(this.DynamicObjects[0].getCurrentRank(1));
					kaese+=10;
					this.setMoney(maeuse);
					this.items[1]+=kaese;
					this.Belohnung1(maeuse, kaese);
					this.setTreasure(true,0);
					this.marke=0;
					this.setExp(120+this.DynamicObjects[0].getCurrentWisdom(this.DynamicObjects[0].getCurrentRank(0)));
					this.LevelUp();
					
				}
				else if(this.SpielPanel.getCurrentLevel() == 2 && this.SpielPanel.getCurrentLevelSection() == 0){
					maeuse+=3000+this.DynamicObjects[0].getCurrentGreed(this.DynamicObjects[0].getCurrentRank(1));
					kaese+=30;
					messer+=5;
					this.setMoney(maeuse);
					this.items[1]+=kaese;
					this.items[0]+=messer;
					this.Belohnung3(maeuse, kaese,messer);
					this.setTreasure(true,2);
					this.marke=0;
					this.setExp(140+this.DynamicObjects[0].getCurrentWisdom(this.DynamicObjects[0].getCurrentRank(0)));
					this.LevelUp();
				}
				break;

			case 43: // Ruestung02
				System.out.println("Ruestung02 aufgenommen");
				this.Health = 8; // Ruestung = Doppelte Gesundheit
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				this.setRType(5); // RType setzen
				break;
				
			case 46: // Information
				this.Information();
				this.info=true;
				break;
				
			case 47: // Schluessel
				this.key=true;
				break;
			}
		}
		/*
		 * switch(this.DDynamic[(pYPos/30)][(pXPos/30)].isBot()){
		case 11: // Objekt ist ein Gegener
			this.LoseHealth(1);
			break;
		default:
			System.out.println("Fehler in Schleife zur Gegner Erkenneung");
				
		}
			*/
		}
	
	/**
	 * Bewegt ein dynamisches Objekt rauf, runter, rechts oder links
	 * @param pWhere Wohin es bewegt werden soll
	 * @param pIsNetwork Wird es gerade vom Netzwerk bewegt?
	 */
	public void moveTo(String pWhere, boolean pIsNetwork){
		
		if(this.SpielPanel.isConnected() == true){

		if(pIsNetwork == false){
			this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" "+pWhere.toUpperCase()+" X:"+this.CurrentXPos+" Y:"+this.CurrentYPos+" MX:"+this.MoveToXPos+" MY:"+this.MoveToYPos);
		}			
			
		if(this.moves == false||pIsNetwork == true){ //Wird das Objekt momentan schon bewegt?
			
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
	}
	
	/**
	 * Animiert das Objekt, hiermit wird das dyn. Objekt Stueck fuer Stueck um 2 Pixel bewegt, bis
	 * es sich an der Position befindet wo es hin soll
	 */
	public void AnimateMoving(){
		
		if(this.CurrentXPos < this.MoveToXPos)
			this.CurrentXPos += 3; //muss noch ein Stueck nach rechts

			if(this.CurrentXPos > this.MoveToXPos)
			this.CurrentXPos -= 3; //muss noch ein Stueck nach links

			if(this.CurrentYPos < this.MoveToYPos)
			this.CurrentYPos += 3; //muss noch ein Stueck nach unten

			if(this.CurrentYPos > this.MoveToYPos)
			this.CurrentYPos -= 3; //muss noch ein Stueck nach oben

			//Wenn wir fertig sind, setzen wir die Variable wieder, dass es sich momentan nicht bewegt
			if(this.CurrentYPos == this.MoveToYPos && this.CurrentXPos == this.MoveToXPos)
			this.moves = false; // bewegt sich nicht mehr, moveTo ist IsMoving
		
	}
	
	/**
	 * Fuehrt Aktion aus
	 * @param pType Art der Aktion
	 * @param pIsNetwork Wird die Aktion vom Netzwerk ausgefuehrt?
	 */
	public void Action(int pType, boolean pIsNetwork){
		
		if(this.SpielPanel.isConnected() == true){

			if(pIsNetwork == false)
				this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" ACTION "+pType);

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
					this.Projectiles[i] = new NProjectile(this, SpielPanel, StaticObjects, DynamicObjects, this.CurrentXPos, this.CurrentXPos, tmpType, this.Direction);
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
	}
	
	/**
	 * Wechselt Waffe, wenn vorhanden
	 * @param pIsNetwork Wird die Aktion vom Netzwerk ausgefuehrt?
	 */
	public void changeWeapon(boolean pIsNetwork){
		if(this.SpielPanel.isConnected() == true){
		
		if(pIsNetwork == false)
			this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" CHANGEWEAPON");
			
			
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
	}

	/**
	 * Gibt Gesundheit zurueck
	 *   
	 */
	public int getHealth(){
		return this.Health;	
	}

	/**
	 * Erhoeht Gesundheit
	 * @param pHealth Wieviel Gesundheit? 
	 */
	public void setHealth(int pHealth){
		this.Health += pHealth;
	}
	

	/**
	 * Setzt Gesundheit
	 * @param pHealth Wieviel Gesundheit? 
	 */
	public void setHealth2(int pHealth){
		this.Health = pHealth;
	}

	/**
	 * Gibt Leben zurueck
	 *   
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
	 * Setzt Leben Absolut
	 * @param pLives Wieviele Leben? 
	 */
	public void setLives2(int pLives){
		this.Lives = pLives;
	}
	
	/**
	* Gibt Money zurueck
	*  
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
	* Setzt Money Absolut
	* @param pMoney Wieviel Geld?
	*/
	public void setMoney2(int pMoney){
		this.Money = pMoney;
	}	

	/**
	* Gibt Mana zurueck
	*  
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
	* Setzt Mana Absolut
	* @param pMana Wieviel Mana?
	*/
	public void setMana2(int pMana){
		this.Mana = pMana;
	}	
	
	/**
	 * Gibt Punkte zurueck
	 *   
	 */
	public int getPoints(){ //Bekomme Punkte
		return this.Points;
	}
	
	/**
	 * Setzt Punkte
	 * @param pPoints Wieviele Punkte? 
	 */
	public void setPoints(int pPoints) //Erhoeht Punkte des Spielers bei Erreichen eines Levelabschnitts
	{
		this.Points = pPoints;
	}
	
	/**
	* Gibt Aktives Item zurueck
	*  
	*/
	public int getActiveItem(){
		return this.ActiveItem;	
	}

	/**
	* Setzt Aktives Item
	* @param pActiveItem Aktives Item?
	*/
	public void setActiveItem(int pActiveItem){
		this.ActiveItem = pActiveItem;
	}	
	/** 
	 * Methode Gesundheit verlieren kann in Verknuepfung mit Schadenssystem benutzt werden
	 * @param crit
	 */
	public void LoseHealth(int crit){ 
		int p;
		
		if(this.SpielPanel.getDebugMode() == true)
			System.out.println("Verliere Leben Typ: "+ this.getType());
		
		if(this.SpielPanel.SpielerModus() == 1){ // gucke nach Modi
			
			if (getHealth() > 4) {
				this.setHealth(-2*crit);// 2 Gesundheit weniger
				
				
				}
			else { 
				this.setHealth(-1);// 1 Gesundheit weniger
				
			
				if(this.getHealth() == 0 && this.getLives() > 0 && this.isBot == false){ 
					//Gesundheit des Spielers 0?
					this.Lives--; // Leben weniger
					this.Health = 4; //Gesundheit wieder voll
				}
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
			if (getHealth() > 4) { this.setHealth(-2);}// 2 Gesundheit weniger
			else{this.setHealth(-1);	// 1 Gesundheit weniger

				if(this.getHealth() == 0 && this.getLives() > 0 && this.isBot == false){ 
					//Gesundheit einer der Spieler 0?
					this.Lives--; // Leben weniger
					this.Health = 4; //Gesundheit wieder voll
				}
			}
			
			if(this.getHealth() == 0 && this.getLives() == 0 && this.isBot == false){ 
				//Gesundheit &Leben einer der Spieler 0?
				this.SpielPanel.beendeSpiel();
			}
		}
			
	}
	
	
	/** Gibt die Aussage fuer das Checkpoint Fenster zurueck
	 *  
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
	 *  
	 */
	public int NumberItems(){
		return this.items.length;
	}
	

	/**
	 * Initialisiert Items (bis jetzt ITEMS =!  7  ! im DPanel init)
	 *  
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
	 * @param p Parameter
	 */
	public int AnzahlItems(int p){
	 
		return this.items[p];
	}
	
	/**
	 * 	Addiere die neuen Items vom Shop
	 * @param i Parameter
	 * @param p Parameter
	 */
	public int SetItems(int i,int p){
		this.items[i]+=p;
		return this.items[i];
	}
	
	/**
	 * Setze die neuen Items vom Shop
	 * @param p Parameter
	 */
	public int[] setItems(int p){
		for(int i=0;i<=NumberItems()-1;i++){
			this.items[i]=p;
		}
		return this.items;
	}
	
	/**
	* Gibt Item zurueck
	* @param pIndex Itemnummer
	*/
	public int getItem(int pIndex){
		return this.items[pIndex];
	}
	
	/**
	* Setzt Items
	* @param pItems ItemsArray
	*/
	public void setItems2(int[] pItems){
		this.items = pItems;
	}
	
	
	/**
	* Setzt Namen von Items
	* @param pName ItemsNamenArray
	*/
	public void setName2(String[] pName){
		this.name = pName;
	}
	
	/**
	* Gibt Itemnamen zurueck
	* @param p Itemnummer
	*/
	public String getName(int p){
		return this.name[p];
	}
	
	/**
	 * Initialisiert Teleporter
	 * @param p Index
	 */
	public boolean SetSecret(boolean p){
		return this.secret=p;	
	}

	/**
	 * Gibt Teleporter zurueck
	 */
	public boolean getSecret(){
		return this.secret;
	}
	
	/**
	 * Initialisiert Geheimtuer
	 * @param p Index
	 */
	public boolean SetSecret2(boolean p){
		return this.secret2=p;	
	}
	
	/**
	 * Initialisiert Geheimtuer2
	 *  
	 */
	public boolean getSecret2(){
		return this.secret2;
	}

	
	/**
	 * Initialisiert Namensliste alles zuerst auf "Leer"
	 *  
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
	 *  
	 */
	public void ItemBag(){
		NItems it = new NItems(this.SpielPanel);
		}
	
	/**
	 * Shop Interaktion, beim Betretten des Shops
	 *  
	 */
	public void Shop(){
		NShop shop=new NShop(SpielPanel);
	}
	
	/**
	 * Alle SoundDateien fuer DDynamic
	 * @return String[]
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
		datei[10]="src/com/github/propa13_orga/gruppe71/NextLevel.wav";//Naechster Level
		datei[11]="src/com/github/propa13_orga/gruppe71/LevelUp.wav";//Level UP!
		datei[12]="src/com/github/propa13_orga/gruppe71/Crit.wav";//Critical Strike
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
		 sound=new NSound(datei[5]);
		 sound.SetVolume(-10);
		 sound.Abspielen();
		 this.setCounter(1);
		 break;
		}
		else if((this.counter%2)==1){
		sound=new NSound(datei[6]);//Magie_2
		 sound.SetVolume(-10);
		 sound.Abspielen();
		 this.setCounter(1);
		break;
		}
	case 2:
		sound=new NSound(datei[7]); //Messer
		 sound.SetVolume(-10);
		 sound.Abspielen();
		 break;
	case 1:
		sound=new NSound(datei[8]); //Kaese
		 sound.SetVolume(-10);
		 sound.Abspielen();
		 break;
		
	}
	
	}
	
	/**
	 * ein counter fuer NSound Magie etc.
	 * @param p Integer
	 */
	public int setCounter(int p){
		this.counter+=p;
		return this.counter;
}	
	
	/**
	 * Quest Dialog 1
	 * @param p Integer
	 */
	public void Quest1(int p, boolean pIsNetwork, boolean pAlready){
		if(pIsNetwork == false){
		if(p==JOptionPane.YES_OPTION){
		 this.qenabled[0]=JOptionPane.showOptionDialog(null, "\nENDLICH EIN EDLER SEINER ZUNFT! DIE SOSSEN SOLLEN DIR IMMER HOLD SEIN..." +
		 		"\nDIE KAEFER SIND WIEDER IM ANMARSCH! WENN IHR --5-- von IHNEN TOETEN KOENNTET, WUERDET IHR \nDEN HEISSEN HUNDEN CLAN FUER IMMER IN ERRINNERUNG BLEIBEN\n" +
		 		"BITTE BEEILT EUCH!\n(fluestern: Natuerlich gibt es auch eine grosse Belohnung...)",
                 "Quest 1 ANGENOMMEN", JOptionPane.OK_OPTION,
                 JOptionPane.INFORMATION_MESSAGE, null, 
                 new String[]{"Ich tue was ich kann!"}, "Ich tue was ich kann!");
		 if(this.qalready[0]==false){
		 this.qreceived[0]=true;
		 }
		 this.qalready[0]=true;

			this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" QUEST1 QUEST:"+p+" QALREADY:1 ");
		 
		}
		else if(p!=JOptionPane.YES_OPTION){
			 JOptionPane.showMessageDialog(null, "VERFLUCHET SEID IHR!!!");
			 this.qreceived[0]=true;
			 this.qdenied[0]=true;
			 
			this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" QUEST1 QUEST:"+p+" ");

		}
		}else{
			this.quest[0] = p;
			this.qalready[0] = pAlready;
			
			if(pAlready == false)
			this.qreceived[0]=true;
		}
	}
	
	/**
	 * Quest Dialog 2
	 * @param p Integer
	 */
	public void Quest2(int p, boolean pIsNetwork, boolean pAlready){
		System.out.println("EXECUTE QUEST2");
		
		if(pIsNetwork == false){
		if(p==JOptionPane.YES_OPTION){
		 this.qenabled[1]=JOptionPane.showOptionDialog(null, "\n SCHLUESSEL...VERSCHWUNDEN...ZU SPAET...\n" +
		 		"\nZU VIELE...EIN BURGER?... RETTUNG?..." +
		 		"\nWas hab ich gesagt?\n WIR BRAUCHEN KEINE HILFE!\n(QUEST: FINDET DEN SCHLUESSEL IN DIESEM RAUM!)",
                 "Quest 2 ANGENOMMEN", JOptionPane.OK_OPTION,
                 JOptionPane.INFORMATION_MESSAGE, null, 
                 new String[]{"Ich werde sie nie verstehen."}, "Ich werde sie nie verstehen.");
		 if(this.qalready[1]==false){
		 this.qreceived[1]=true;
		 }
		 this.qalready[1]=true;
			this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" QUEST2 QUEST:"+p+" QALREADY:1 ");
		}
		else if(p!=JOptionPane.YES_OPTION){
			 JOptionPane.showMessageDialog(null, "SCHOENEN TAG...");
			 this.qreceived[1]=true;
			 this.qdenied[1]=true;
			
			 this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" QUEST2 QUEST:"+p+" ");
		 }
		
		}else{
			this.quest[1] = p;
			this.qalready[1] = pAlready;
			
			if(pAlready == false)
			this.qreceived[1]=true;
		}
		}
	
	/**
	 * Quest Dialog 3
	 * @param p Integer
	 */
	public void Quest3(int p, boolean pIsNetwork, boolean pAlready){
		if(pIsNetwork == false){
		if(p==JOptionPane.YES_OPTION){
		 this.qenabled[2]=JOptionPane.showOptionDialog(null, "\nWIE IHR HIER?" +
		 		"\nHmm... BEWEIST EUREN MUT\n TOETET 12 BESTIEN..." +
		 		"\n SONST ... KEINE BELOHNUNG!",
                 "Quest 1 ANGENOMMEN", JOptionPane.OK_OPTION,
                 JOptionPane.INFORMATION_MESSAGE, null, 
                 new String[]{"Warum habe ich Angenommen?"}, "Warum hab ich Angenommen?");
		 if(this.qalready[2]==false){
		 this.qreceived[2]=true;
		 }
		 this.qalready[2]=true;
		 this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" QUEST3 QUEST:"+p+" QALREADY:1 ");
			
		}
		else if(p!=JOptionPane.YES_OPTION){
			 JOptionPane.showMessageDialog(null, "Hier gibts kein Entkommen...");
			 this.qreceived[2]=true;
			 this.qdenied[2]=true;
			 this.SpielPanel.setClientMessage("DO"+this.DynamicObjectIndex+" QUEST3 QUEST:"+p+" ");
		 }
		}else{
			this.quest[2] = p;
			this.qalready[2] = pAlready;
			
			if(pAlready == false)
			this.qreceived[2]=true;
		}
		}
	
	
	
	/**
	 * Informatio am Anfang des Spiels
	 *  
	 */
	public void Information(){
		JOptionPane.showMessageDialog(null, "Aufgepasst edler Burger.\nAuf eurer Reise werdet ihr viele Gefahren entdecken"
				+"\n Deshalb Ruestet euch gut aus und vergesst nicht Coins aufzusammeln"+"\n Sie koennen das Spiel nur beenden,wenn sie den Endboss besiegt haben!"
				+"\n Viel Glueck!");
	}
	/**
	 * Belohnung 1
	 * @param p
	 * @param y
	 */
	public void Belohnung1(int p,int y){//Belohnung
			JOptionPane.showOptionDialog(null, "Belohnung:" +
		 		"\n Coins: " +p+
		 		"\n Kaese: "+y,
                 "SCHATZ GEFUNDEN", JOptionPane.OK_OPTION,
                 JOptionPane.INFORMATION_MESSAGE, null, 
                 new String[]{"GENAU WAS ICH BRAUCHE!"}, "GENAU WAS ICH BRAUCHE!");
		
	}
	/**
	 * Belohnung 2
	 * @param p Index
	 * @param y Index
	 * @param z Index
	 */
	public void Belohnung2(int p,int y,int z){//Belohnung
		if(this.ok==JOptionPane.OK_OPTION){
			JOptionPane.showOptionDialog(null, "Belohnung:" +
		 		"\n Coins: " +p+
		 		"\n Kaese: "+y+
		 		"\n Messer: "+z,
                 "SCHATZ GEFUNDEN", JOptionPane.OK_OPTION,
                 JOptionPane.INFORMATION_MESSAGE, null, 
                 new String[]{"GENAU WAS ICH BRAUCHE!"}, "GENAU WAS ICH BRAUCHE!");
		}
	}
	
	/**
	 * Belohnung 3
	 * @param p Index
	 * @param y Index
	 * @param z Index
	 */
	public void Belohnung3(int p,int y,int z){//Belohnung
		if(this.ok==JOptionPane.OK_OPTION){
			JOptionPane.showOptionDialog(null, "Belohnung:" +
		 		"\n Coins: " +p+
		 		"\n Kaese: "+y+
		 		"\n Messer: "+z,
                 "SCHATZ GEFUNDEN", JOptionPane.OK_OPTION,
                 JOptionPane.INFORMATION_MESSAGE, null, 
                 new String[]{"NICHT SCHLECHT"}, "NICHT SCHLECHT!");
		}
	}
	
	/**
	 * Initialisierung Quest bekommen
	 * @return boolean[]
	 */
	public boolean[] QuestReceived(){
		this.qreceived=new boolean[3];
		for(int i=0; i<this.qreceived.length;i++){
			this.qreceived[i]=false;
		}
		return this.qreceived;
	}
		
	/**
	 * Initialisierung Quest Already
	 * @return boolean[]
	 */
	public boolean[] QuestAlready(){
		this.qalready=new boolean[3];
		for(int i=0; i<this.qalready.length;i++){
			this.qalready[i]=false;
		}
		return this.qalready;
	}
	
	/**
	 * Bekomme Quest Already
	 * @param p Index
	 * @return boolean
	 */
	public boolean getQuestLaufend(int p){
		return this.qalready[p];
	}
	
	/**
	 * Bekomme Quest
	 * @param p
	 * @return boolean
	 */
	public boolean getQuest(int p){
		return this.qreceived[p];
	}
	
	/**
	 * Quest  bekommen setzen
	 * @param p
	 * @param x
	 * @return boolean
	 */
		public boolean setQuest(boolean p,int x){
			return this.qreceived[x]=p;
		}
		
	/**
	 * Bekomme Quest Luenge
	 * @return int
	 */
	public int QuestLength(){
		return this.qreceived.length;
	}
	
	/**
	 * Bekomme Quest Marken
	 * @return int
	 */
	public int getMarke(){
		return this.marke;
	}

	/**
	 *  Quest Marken erhoehen
	 * @param p Index
	 * @return int
	 */
	public int setMarke(int p){
		return this.marke+=p;
	}
	

	/**
	 *  Quest Marken setzen
	 * @param p Index
	 * @return int
	 */
	public int setMarke2(int p){
		return this.marke=p;
	}

	/**
	 * Initialisiereung Belohnungen
	 * @return boolean[]
	 */
	public boolean[] InitBelohnungen(){
		this.treasure=new boolean[3];
		for(int i=0; i<this.treasure.length;i++){
			this.treasure[i]=false;
		}
		return this.treasure;
	}
	
	/**
	 * Initialisierung QuestDenied
	 * @return boolean[]
	 */
	public boolean[] InitDenied(){
		this.qdenied=new boolean[3];
		for(int i=0; i<this.qdenied.length;i++){
			this.qdenied[i]=false;
		}
		return this.qdenied;
	}
	
	/**
	 * Aufgabe,Quest wurde nicht angenommen.
	 * @param p Index
	 */
	public boolean QuestDenied(int p){
		return this.qdenied[p];
	}
	
	/**
	 * bekomme Schatz Verhalten
	 * @param p
	 * @return boolean
	 */
	public boolean getTreasure(int p){
		return this.treasure[p];
	}
	
	/**
	 * Schatz wird auf true oder false gesetzt.
	 * @param m Index
	 * @param p Index
	 */
	public boolean setTreasure(boolean m,int p){
		return this.treasure[p]=m;
	}
	
	/**
	 * Bekomme die Informatinsvariable( Am Anfang des Spiels =false gesetzt)
	 * @return boolean
	 */
	public boolean getInfo(){
		return this.info;
	}
	/**
	 * Bekomme Schluessel Wert
	 */
	public boolean getKey(){
		return this.key;
	}
	
	/**
	 * Schuessel setzen
	 * @param p
	 * @return boolean
	 */
	public boolean setKey(boolean p){
		return this.key=p;
	}
	
	/**
	 * Setze Hidden Variable fuer versteckte Gegenstuende
	 * @return int
	 */
	public int getHidden(){
		return this.hidden;
	}
	
	/**
	 * Setze Hidden Variable siehe get
	 * @param p Index
	 * @return int
	 */
	public int setHidden(int p){
		return this.hidden+=p;
	}
	
	/**
	 * Spezielle Position des Schluessels X
	 * @return int
	 */
	public int getPosX(){
		return this.posX;
	}
	
	/**
	 * Spezielle Position des Schluessels Y
	 * @return int
	 */
	public int getPosY(){
		return this.posY;
	}
	
	/**
	 *  Speichert key Position Y
	 * @param p Index
	 * @return int
	 */
	public int setPosY(int p){
		return this.posY+=p;
	}
	
	
	/**
	 *  Speichert key Position X
	 * @param p Index
	 * @return int
	 */
	public int setPosX(int p){
		return this.posX+=p;
	}
	
	/**
	 * Ab hier LEVEL , EXP ETC.
	 */
	
	/**
	 * Bekomme Erfahrungspunkte
	 * @return int
	 */
	public int getExp(){
		return this.exp;
	}
	
	/**
	 * Setze Erfahrungspunkte
	 * @param p Index
	 * @return int
	 */
	public int setExp(int p){
		return this.exp+=p;
	}
	
	/**
	 * Spieler Level
	 * @return int
	 */
	public int SpielerLevel(){
		return this.level;
	}
	
	/**
	 * Setze Level
	 * @param p Index
	 * @return int
	 */
	public int setLevel(int p){
		return this.level+=p;
	}
	
	/**
	 * Die Level Grenzen
	 * @return int[]
	 */
	public int[] InitLevelGrenze(){
		this.grenze=new int[10];
		//Benuetigte EXP
		this.grenze[0]=100; //Grenze zu Level 2
		this.grenze[1]=150; //Grenze zu Level 3
		this.grenze[2]=200; //Grenze zu Level 4
		this.grenze[4]=250; //Grenze zu Level 5
		this.grenze[5]=300; //Grenze zu Level 6
		this.grenze[6]=350; //Grenze zu Level 7
		this.grenze[7]=400; //Grenze zu Level 8
		this.grenze[8]=750; //Grenze zu Level 9
		this.grenze[9]=1000; //Grenze zu Level 10
		
		
		return this.grenze;
	}
	
	/**
	 * LevelUp Algorithmus
	 * 
	 */
	public void LevelUp(){
		for(int i=0;i<this.grenze.length;i++){
		if(this.exp>=this.grenze[i] && this.level==i+1 && this.level<this.grenze.length){
			sound=new NSound(datei[11]); //Level UP
			 sound.SetVolume(0);//
			 sound.Abspielen();
			this.setLevel(1);
			this.setSkills(2);
			this.exp-=this.grenze[i];
			
		}
	}
	}
	
	/**
	 * Bekomme Skillpunkte
	 * @return int
	 */
	public int getSkills(){
		return this.skills;
	}
	
	/**
	 * Setze Skillpunkte
	 * @param p Index
	 * @return int
	 */
	public int setSkills(int p){
		return this.skills+=p;
	}
	
	/**
	 * Skill Baum per F Taste aufrufbar
	 */
	public void SkillBaum(){
		NSkill skill=new NSkill(SpielPanel);
	}
	

	
	/**
	 * Initialisiert EXP Zunahme
	 * @return int[]
	 */
	public int[] InitWisdom(){
		this.wisdom=new int[6];
		
		this.wisdom[0]=0; //EXP 3 etc.
		this.wisdom[1]=6;
		this.wisdom[2]=9;
		this.wisdom[3]=12;
		this.wisdom[4]=18;
		this.wisdom[5]=30;
		
		return this.wisdom;
	}
	
	
	/**
	 * Bekomme Wisdom
	 * @param p Index
	 * @return int
	 */
	public int getCurrentWisdom(int p){
		return this.wisdom[p];
	}
	
	/**
	 * Setze Wisdom hoch
	 * @param p Index
	 * @param a Index
	 * @return int
	 */
	public int setCurrentWisdom(int p,int a){
		if(this.wisdom[p+a]<this.wisdom.length-1){
			
			return this.wisdom[p+a];
			}
			return this.wisdom[p+a];
	}
	
	
	
	
	/**
	 * Bekomme Greed
	 * @param p Index
	 * @return int
	 */
	public int getCurrentGreed(int p){
		return this.greed[p];
	}
	
	/**
	 * Setze Greed hoch
	 * @param p Index
	 * @param a Index
	 * @return int
	 */
	public int setCurrentGreed(int p,int a){
		
		if(this.greed[p+a]<this.greed.length-1){
			
		return this.greed[p+a];
		}
		return this.greed[p+a];
	}
	
	/**
	 * Initialisiert Coins Zunahme
	 * @return int[]
	 */
	public int[] InitGreed(){
		this.greed=new int[6];
		
		this.greed[0]=0;
		this.greed[1]=8;
		this.greed[2]=12;
		this.greed[3]=16;
		this.greed[4]=20;
		this.greed[5]=30;
		
		return this.wisdom;
	}
	
	/**
	 * Ranks der einzelnen Fuehigkeiten
	 * @return int[]
	 */
	public int[] InitRank(){
		this.rank=new int[6];
		for(int i=0;i<this.rank.length;i++){
			this.rank[i]=0;
		}
		return this.rank;
	}
	
	/**
	 * Bekomme Rank
	 * @return int
	 */
	public int getCurrentRank(int p){
		return this.rank[p];
	}
	
	/**
	 * Setze Rank
	 * @return int
	 */
	public int setCurrentRank(int p,int a){
		return this.rank[p]+=a;
	}
	
	
	
	/**
	 * Initialisiert Crit 
	 * @return int[]
	 */
	public int[] InitCrit(){
		this.crit=new int[6];
		
		this.crit[0]=1; //Crit Raten
		this.crit[1]=5;
		this.crit[2]=10;
		this.crit[3]=15;
		this.crit[4]=20;
		this.crit[5]=25;
		
		return this.crit;
	}
	
	
	/**
	 * Bekomme Crit Rate
	 * @param p Index
	 * @return int
	 */
	public int getCurrentCrit(int p){
		return this.crit[p];
	}
	
	/**
	 * Setze Crit hoch
	 * @param p Index
	 * @param a Index
	 * @return int
	 */
	public int setCurrentCrit(int p,int a){
		if(this.crit[p+a]<this.crit.length-1){
			
			return this.crit[p+a];
			}
			return this.crit[p+a];
	}
	
	
	/**
	 *  Bekomme critted -> wirkliche Chance auf Crit
	 * @return int
	 */
	public int getCritted(){
		return this.critted;
	}
	
	/**
	 * Setze critted 
	 * @param p Index
	 * @return int
	 */
	public int setCritted(int p){
		return this.critted=p;
	}
	/**
	 *  Das Crit System arbeitet mit Random
	 * @param p Index
	 * @return int
	 */
	public int CritSystem(int p){
		this.critted=1;
		int x=1;
		
		Random zufallsZahl=new Random();
		int fest = zufallsZahl.nextInt(99);// 0-99 100 Zahlen
		int chance = zufallsZahl.nextInt(99);
		
		System.out.println(fest+ "Fest");
		System.out.println(chance+"Chance");
		for(int i=0;i<p;i++){
		if(chance==fest){
			System.out.println("Gecritted");
			sound=new NSound(datei[12]); //Item aufsammeln
			 sound.SetVolume(0);//
			 sound.Abspielen();
			 x=2;
			 break;
		}
			else if(fest>chance){
			chance+=1;
			System.out.println(chance+"Chance 2");
			}
			else if(fest<chance){
				chance-=1;
				System.out.println(chance+"Chance3");
			}
		
		
		
	}
		System.out.println(x);
		return this.critted=x;
	}
	
	/**
	 * Methode Lifesteal benutzt Methode setHealth
	 * @param p Index
	 */
	
	public void Lifesteal(int p){
		
		this.setHealth(p);
		
	}
	
	/**
	 * setUltima
	 * @param p Index
	 * @return boolean
	 */
	public boolean setUltima(boolean p){
		return this.ultimaready=p;
	}
	
	/**
	 * Methode getUltima
	 * @return boolean
	 */
	public boolean getUltima(){
		return this.ultimaready;
	}
}
	
