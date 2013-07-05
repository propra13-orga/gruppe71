package src.com.github.propa13_orga.gruppe71;

/**
* Klasse der Statischen Objekte, nicht bewegliche Objekte wir Mauern, Fallen etc.
*/
public class StaticObject {
	
	private int Type;
	private boolean Collision;
	
	
	
	public StaticObject (int pType){
	this.Type = pType;

	if (this.Type == 1 || this.Type == 6 || this.Type==16 || this.Type==25 ||this.Type==40 || this.Type==42) 
	{
		this.Collision = true;
	}
	else {this.Collision = false;}
	
	}
	
	
	/**
	 * Setzt Collision
	 * @param pCollision Collision?
	 */
	public void setCollision(boolean pCollision) {
		this.Collision = pCollision;
	}
	
	
	/**
	 * Gibt die Collision zurueck
	 */
	public boolean getCollision() {
	return this.Collision;
	}
	
	
	/**
	 * Setzt den Typ
	 * @param pType Typ?
	 */
	public void setType (int pType){
	this.Type = pType;
	}
		
	
	/**
	 * Gibt den Typ zurueck
	 */
	public int getType() {
		return this.Type;
	}
	
	
	
	
	
}
