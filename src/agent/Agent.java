package agent;

public class Agent {
	
	private int uniteElec;
	private int etatBDI;
	private int posX;
	private int posY;
	
	public Agent() {
		this.uniteElec = 0;
		this.etatBDI = 0;
		this.posX = 0;
		this.posY = 0;
	}
	
	public void moveUp() {
		if (this.posY -1 >0) {
			this.posY--;
		}
		else System.out.println("erreur up");
	}
	
	public void moveDown() {
		if (this.posY +1 <10) {
			this.posY++;
		}
		else System.out.println("erreur down");
	}
	
	public void moveRight() {
		if (this.posX +1 <10) {
			this.posX++;
		}
		else System.out.println("erreur right");
	}
	
	public void moveLeft() {
		if (this.posX -1 >0) {
			this.posX--;
		}
		else System.out.println("erreur left");
	}
	
	public void useElec() {
		this.uniteElec++;
	}
		
	
	public void goodAction() {
		this.etatBDI++;
	}
	
	public void badAction() {
		this.etatBDI--;
	}

//Getters
	
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}
	
	public int getUniteElec() {
		return this.uniteElec;
	}
	
	public int getEtatBDI() {
		return this.etatBDI;
	}

}
