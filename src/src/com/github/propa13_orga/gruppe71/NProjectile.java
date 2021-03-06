package src.com.github.propa13_orga.gruppe71;

import java.awt.Graphics;

import javax.swing.JOptionPane;



/**
* Klasse der Projektile, die Spieler und Gegner abschiessen im Netzwerkspiel 
*/
public class NProjectile {
	private NPanel SpielPanel;
	private StaticObject[][] StaticObjects;
	private NDynamic[] DynamicObjects;
	private NDynamic Shooter; //Wer hat es abgeschossen?
	
	private int CurrentXPos;  //Die derzeitige X Position
	private int CurrentYPos; //Die derzeitige Y Position
	private int Direction; // Richtung
	
	private int Damage; // Schaden
	private int Speed; // Geschwindigkeit
	private int Type; // Typ des Projektils
	private boolean Enabled; // Objekt ist aktiv
	private int MaxRange; // Max Schussweite eines Proj
	private int CurrentRange; // Momentanzurueckgelegte Schussweite eines Proj
	private int Preferred; // Bevorzugter Gegnertyp
	
	public NProjectile(NDynamic pShooter, NPanel pPanel, StaticObject[][] pStaticObjects, NDynamic[] pDynamicObjects, int pCurrentXPos, int pCurrentYPos, int pType, int pDirection){
		this.SpielPanel = pPanel;
		this.StaticObjects = pStaticObjects;
		this.DynamicObjects = pDynamicObjects;
		this.Shooter = pShooter;

		this.CurrentXPos = pCurrentXPos;
		this.CurrentYPos = pCurrentYPos;
		this.Direction = pDirection;
		
		this.Type = pType;
		
		this.init();
	}
	
	/**
	 * Initalisiert die Standardwerte des Objekts
	 *   
	 */
	public void init(){
		this.StaticObjects = null;
		this.StaticObjects = new StaticObject[14][24];
		this.StaticObjects = this.SpielPanel.getStaticObjects();
		this.DynamicObjects = null;
		this.DynamicObjects = new NDynamic[50];
		this.DynamicObjects = this.SpielPanel.getDynamicObjects();
		this.Enabled = true;
		this.CurrentRange = 0;
		
		switch(this.Type){
		case 0: //Zauber
			
			this.Damage = 334;
			this.Speed = 2;
			this.MaxRange = 9999;
			break;

		case 1: //Kaese
			this.Damage = 666;
			this.Speed = 3;
			this.MaxRange = 9999;
			this.Preferred = 4;
			break;

		case 2: //Messer
			this.Damage = 999;
			this.Speed = 4;
			this.MaxRange = 9;
			this.Preferred = 1;
			break;
			

		case 3: //Spinnweben
			this.Damage = 2;
			this.Speed = 2;
			this.MaxRange = 9999;
			this.Preferred = 5; // 5 ist nicht zugewiesen
			break;
			
		case 4: //Spinnweben? (Proj fuer 3. Boss)
			this.Damage = 2;
			this.Speed = 2;
			this.MaxRange = 9999;
			this.Preferred = 5;
			break;
			
		default:
			this.Damage = 1;
			this.Speed = 2;
			this.MaxRange = 1;
			this.Preferred = 5;
			break;
		}
	}
    
	/**
	 * Gibt Momentane Position des Objekts zurueck
	 *   
	 */
	public int[] getCurrentPosition(){
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
	* Gibt Typ zurueck
	*  
	*/
	public int getType(){
	return this.Type;	
	}

	/**
	* Setzt Typ
	* @param pType Welcher Typ?
	*/
	public void setType(int pType){
	this.Type = pType;
	this.init();
	}
	
	/**
	* Gibt Richtung zurueck
	*  
	*/
	public int getDirection(){
	return this.Direction;	
	}

	/**
	* Setzt Richtung
	* @param pDirection Welche Richtung?
	*/
	public void setDirection(int pDirection){
	this.Direction = pDirection;
	}

	/**
	* Gibt Momentane Weite zurueck
	*  
	*/
	public int getCurrentRange(){
	return this.CurrentRange;
	}

	/**
	* Setzt Momentane Weite
	* @param pCurrentRange Wie weit bereits?
	*/
	public void setCurrentRange(int pCurrentRange){
	this.CurrentRange = pCurrentRange;
	}
	
	/**
	* Gibt Max Weite zurueck
	*  
	*/
	public int getMaxRange(){
	return this.MaxRange;
	}

	/**
	* Setzt Max Weite
	* @param pMaxRange Wie weit maximal?
	*/
	public void setMaxRange(int pMaxRange){
	this.MaxRange = pMaxRange;
	}
	
	/**
	* Gibt Schuetzen zurueck
	*  
	*/
	public NDynamic getShooter(){
		return this.Shooter;
	}

	/**
	* Setzt Schuetzen
	* @param pShooter Wer ist Schuetze?
	*/
	public void setShooter(NDynamic pShooter){
		this.Shooter = pShooter;
	}
	
	/**
	* Gibt zurueck ob aktiv oder nicht
	*  
	*/
	public boolean IsEnabled(){
	return this.Enabled;	
	}
	

	/**
	* Setzt ob aktiv oder nicht
	* @param pEnabled Aktiv?
	*/
	public void setEnabled(boolean pEnabled){
		this.Enabled = pEnabled;
	}
	
	/**
	 * Animiert das Projektil, bewegt es um ein Stueck bei jedem Aufruf
	 *  
	 */
	public void AnimateMoving(){
		int x=1;
		if(this.MaxRange > this.CurrentRange){
		
		if(this.Direction == 0){ // Oben
			if(this.CurrentYPos > 0){
				this.CurrentYPos -= this.Speed; //Ein Stueck nach oben
			}else{
				this.Enabled = false;
			}
		}
		if(this.Direction == 1){ // Rechts
			if(this.CurrentXPos < 570){
				this.CurrentXPos += this.Speed; //Ein Stueck nach rechts
			}else{
				this.Enabled = false;
			}
		}
		if(this.Direction == 2){ // Unten
			if(this.CurrentYPos < 330){
				this.CurrentYPos += this.Speed; //Ein Stueck nach unten
			}else{
				this.Enabled = false;
			}
		}
		if(this.Direction == 3){ // Links
			if(this.CurrentXPos > 0){
				this.CurrentXPos -= this.Speed; //Ein Stueck nach unten
			}else{
				this.Enabled = false;
			}
		}

		if(this.CurrentXPos < 0)
			this.CurrentXPos = 0;
		
		if(this.CurrentYPos < 0)
			this.CurrentYPos = 0;

		// Position bereinigen, so dass sie restlos durch 30 teilbar ist
		int cleanXPos = this.CurrentXPos - (this.CurrentXPos % 30);
		int cleanYPos = this.CurrentYPos - (this.CurrentYPos % 30);
		
		/*if(this.SpielPanel.getDebugMode() == true)
			System.out.println("this.StaticObjects["+(cleanYPos/30)+"]["+(cleanXPos/30)+"].getCollision()");*/
		
		if(this.StaticObjects[(cleanYPos/30)][(cleanXPos/30)].getCollision() == false){ // Keine Kollision also weiter
			
			// Schaden hinzufuegen wenn ein DynamicObject beruehrt wird
			for (int i = 0; i < this.DynamicObjects.length; i++){
				 if(this.DynamicObjects[i] != null && this.DynamicObjects[i] != this.Shooter && this.Shooter.IsBot() != this.DynamicObjects[i].IsBot() && this.DynamicObjects[i].getHealth() > 0){
					 // Wenn Objekt nicht der, der es abgeschossen hat ist und nicht beides Spieler/Bots
					 
					 if(((this.DynamicObjects[i].IsMoving() == false && this.DynamicObjects[i].getCurrentXPosition() == cleanXPos && this.DynamicObjects[i].getCurrentYPosition() == cleanYPos) || (this.DynamicObjects[i].IsMoving() == true && this.DynamicObjects[i].getMoveToXPosition() == cleanXPos && this.DynamicObjects[i].getMoveToYPosition() == cleanYPos) ) ) {
						//WENN auf Position ein DynamicObject ist
						  if (this.Preferred == DynamicObjects[i].getType())// Erhoeht Damage bei bevorzugtem Gegner
						  {
							  this.Damage = this.Damage + 150;
						  }
						  
						 for(int d = 0; d < this.Damage; d++){
							 
							 if(d==0){
								 x= this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).CritSystem(this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentCrit(this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(2)));
								 
							 }
							 // So oft Gesundheit abziehen, bis Schaden erreicht
							 if(this.DynamicObjects[i].getHealth() > 0){
								 this.DynamicObjects[i].LoseHealth(x);
											 
							 }
						
							
						 }
						 
						 if(this.DynamicObjects[i].getHealth()==0){ // Marken setzen und EXP kriegen Rank 0
						 this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setMarke(1);
						 if(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getUltima()==true && SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getHealth()<=2 ){
							 SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).Lifesteal(2);//50%
						 }
						 if(DynamicObjects[i].getType()==1){
						 this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setExp(10+this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentWisdom(this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(0)));
						 this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).LevelUp();
						 }
						 else if(DynamicObjects[i].getType()==2){
							this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setExp(18+this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentWisdom(this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(0)));
							this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).LevelUp();
						 }
						 else if(DynamicObjects[i].getType()==3){
								this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setExp(24+this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentWisdom(this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(0)));
								this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).LevelUp();
							 }
						 else if(DynamicObjects[i].getType()==4){
								this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setExp(33+this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentWisdom(this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(0)));
								this.SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).LevelUp();
							 }
						 System.out.println(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getExp());
						 System.out.println(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).SpielerLevel());
						 System.out.println("UND?");
						 } 
						if(this.SpielPanel.getDebugMode() == true)
							System.out.println("DO "+i+" getroffen! von "+this.Shooter.getType());
						
						this.Enabled = false;
						
						i = this.DynamicObjects.length; //Beende Schleife
					 }
					
				 }
				
			}
		}else{

			if(this.SpielPanel.getDebugMode() == true)
				System.out.println("Proj Collision: Pos X: "+this.getCurrentXPosition()+" Y: "+this.getCurrentYPosition()+ " Dir: "+this.getDirection()+" T: "+this.getType()+" CR: "+this.getCurrentRange()+" MR: "+this.getMaxRange());
			this.Enabled = false;
		}
		
		}else{	

			if(this.SpielPanel.getDebugMode() == true)
				System.out.println("Proj Range Exceeded: Pos X: "+this.getCurrentXPosition()+" Y: "+this.getCurrentYPosition()+ " Dir: "+this.getDirection()+" T: "+this.getType()+" CR: "+this.getCurrentRange()+" MR: "+this.getMaxRange());
			this.Enabled = false;
		}
		
		this.CurrentRange++;
		
	}
	}
	
	
