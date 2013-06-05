package src.com.github.propa13_orga.gruppe71;

import java.awt.Graphics;

import javax.swing.JOptionPane;



public class DProjectile {
	private DPanel SpielPanel;
	private StaticObject[][] StaticObjects;
	private DDynamic[] DynamicObjects;
	private DDynamic Shooter; //Wer hat es abgeschossen?
	
	private int CurrentXPos;  //Die derzeitige X Position
	private int CurrentYPos; //Die derzeitige Y Position
	private int Direction; // Richtung
	
	private int Damage; // Schaden
	private int Speed; // Geschwindigkeit
	private int Type; // Typ des Projektils
	private boolean Enabled; // Objekt ist aktiv
	
	public DProjectile(DDynamic pShooter, DPanel pPanel, StaticObject[][] pStaticObjects, DDynamic[] pDynamicObjects, int pCurrentXPos, int pCurrentYPos, int pType, int pDirection){
		this.SpielPanel = pPanel;
		this.StaticObjects = pStaticObjects;
		this.DynamicObjects = pDynamicObjects;
		this.Shooter = pShooter;

		this.CurrentXPos = pCurrentXPos;
		this.CurrentYPos = pCurrentYPos;
		this.Direction = pDirection;
		
		this.Type = pType;
		this.Enabled = true;
		
		switch(pType){
		case 0: //Zauber
			this.Damage = 1;
			this.Speed = 2;
			break;
			
		default:
			this.Damage = 1;
			this.Speed = 2;
			break;
		}
	}
    
	/**
	 * Gibt Momentane Position des Objekts zurueck
	 * @param NICHTS 
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
	* Gibt Typ zurueck
	* @param NICHTS
	*/
	public int getType(){
	return this.Type;	
	}

	/**
	* Setzt Typ
	* @param pTyp Welcher Typ?
	*/
	public void setType(int pType){
	this.Type = pType;
	}
	
	/**
	* Gibt Richtung zurueck
	* @param NICHTS
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
	* Gibt zurueck ob aktiv oder nicht
	* @param NICHTS
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
	 * @param NICHTS
	 */
	public void AnimateMoving(){
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

		if(this.StaticObjects[(cleanYPos/30)][(cleanXPos/30)].getCollision() == false){ // Keine Kollision also weiter
			
			// Schaden hinzufügen wenn ein DynamicObject beruehrt wird
			for (int i = 0; i < this.DynamicObjects.length; i++){
				 if(this.DynamicObjects[i] != null && this.DynamicObjects[i] != this.Shooter && this.Shooter.IsBot() != this.DynamicObjects[i].IsBot() && this.DynamicObjects[i].getHealth() > 0){
					 // Wenn Objekt nicht der, der es abgeschossen hat ist und nicht beides Spieler/Bots
					 
					 if(((this.DynamicObjects[i].IsMoving() == false && this.DynamicObjects[i].getCurrentXPosition() == cleanXPos && this.DynamicObjects[i].getCurrentYPosition() == cleanYPos) || (this.DynamicObjects[i].IsMoving() == true && this.DynamicObjects[i].getMoveToXPosition() == cleanXPos && this.DynamicObjects[i].getMoveToYPosition() == cleanYPos) ) ) {
						 //WENN auf Position ein DynamicObject ist
						 for(int d = 0; d < this.Damage; d++){
							 // So oft Gesundheit abziehen, bis Schaden erreicht
							 if(this.DynamicObjects[i].getHealth() > 0){
								 this.DynamicObjects[i].LoseHealth(); 
							 }
						 }
						 System.out.println("DO "+i+" getroffen!("+this.DynamicObjects[i].getHealth()+")");

						this.Enabled = false;
						i = this.DynamicObjects.length; //Beende Schleife
					 }
				 }
			}
		}else{
			this.Enabled = false;
		}
		
	}
	}
	
	
