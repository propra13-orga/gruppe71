package src.com.github.propa13_orga.gruppe71;

public class StaticObject {
	
	private int Type;
	private boolean Collision;
	
	
	
	public StaticObject (int pType){
	this.Type = pType;

	if (this.Type == 1 || this.Type == 6 || this.Type==16) {this.Collision = true;}
	else {this.Collision = false;}
	
	}
	
	
	public void setCollision(boolean pCollision) {
		this.Collision = pCollision;
	}
	
	
	public boolean getCollision() {
	return this.Collision;
	}
	
	
	public void setType (int pType){
	this.Type = pType;
	}
		
	
	public int getType() {
		return this.Type;
	}
	
	
	
	
	
}
