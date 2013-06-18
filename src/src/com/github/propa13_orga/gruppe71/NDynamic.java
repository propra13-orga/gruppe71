					package src.com.github.propa13_orga.gruppe71;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.JOptionPane;



public class NDynamic extends DDynamic{
	
	
	private NDynamic[] DynamicObjects;
	
	public NDynamic(DPanel pPanel, StaticObject[][] pStaticObjects, NDynamic[] pDynamicObjects, DProjectile[] pProjectiles, int pCurrentXPos, int pCurrentYPos, int pHealth, int pPunkte, boolean pisBot, int itemnumber){
		super(pPanel, pStaticObjects, pDynamicObjects, pProjectiles, pCurrentXPos, pCurrentYPos, pHealth, pPunkte, pisBot, itemnumber);	
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
			 if(this.DynamicObjects[i] != null && (this.DynamicObjects[i].getHealth() > 0 && this.getHealth() > 0)){ //Wenn existieren und beide Gesundheit haben
				 if(this.DynamicObjects[i].isBot != this.isBot && ((this.DynamicObjects[i].IsMoving() == false && this.DynamicObjects[i].getCurrentXPosition() == pXPos && this.DynamicObjects[i].getCurrentYPosition() == pYPos) || (this.DynamicObjects[i].IsMoving() == true && this.DynamicObjects[i].getMoveToXPosition() == pXPos && this.DynamicObjects[i].getMoveToYPosition() == pYPos) ) ) {
					 //WENN nicht beide Bots sind & beide auf die gleiche Position wollen(Unterscheidung IsMoving)
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
				this.items[1]+=5;
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
				Random zufallsZahl = new Random();				
				this.Money += (zufallsZahl.nextInt(4)+1); //Zahl zwischen 1 und 5
				this.StaticObjects[(pYPos/30)][(pXPos/30)].setType(0); // Entferne Gegenstand
				break;

			case 25: // NPC
				if(this.SpielPanel.getCurrentLevel() == 0 && this.SpielPanel.getCurrentLevelSection() == 0 ){
					JOptionPane.showMessageDialog(null, " Hallo BURGER NR. 1, \n versuch so schnell wie moeglich an das \n Ziel zu gelangen ohne dabei von Ungezif-\nfer gefressen zu werden. Auf dem Weg ver-\n streute Items koennten dir dabei nuetzlich sein. \n Sammel genug Geld um dich im Shop auszuruesten.");
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
	 * Fuehrt Aktion aus
	 * @param pType Art der Aktion
	 */
	public void Action(int pType){
		
		
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
	
	
}
	

	
	
	
