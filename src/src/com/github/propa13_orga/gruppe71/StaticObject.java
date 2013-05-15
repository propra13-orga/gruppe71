package src.com.github.propa13_orga.gruppe71;

public class StaticObject {
	
	private int Type;
	private boolean Collision;
	private int[][] StaticObject;
	
	
	public StaticObject (int[][] pStaticObjects){
	this.StaticObject = pStaticObjects;
	}
	
	
	/*public void setCollision(pCollision) {
		this.Collision = pCollision}
	}
	*/
	public void setCollision() {
		if (this.Type == 1 /*|| this.Type == 6*/) {this.Collision = true;}
		else {this.Collision = false;}
	}
	
	public boolean getCollision() {
	return this.Collision;
	}
	
	
	/*public void setType (int pType){
	this.Type = pType;}
	
	oder
	
	public void setType (int[][] StaticObject){
	switch (StaticObject) {
		case "0":
			this.Type = 0;
			break;
		case "1":
			this.Type = 1;
			break;
		case "2":
			this.Type = 2;
			break;
		case "3":
			this.Type = 3;
			break;
		case "4":
			this.Type = 4;
			break;
			default:
			System.out.println("Ist kein StaticObject");
		}
	
	
	*/
	
	
	public int getType() {
		return this.Type;
		}
	
}
