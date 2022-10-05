package fr.uqac.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Agent {
	private static Agent INSTANCE;
	private int uniteElec;
	private int etatBDI;
	private int posX;
	private int posY;
	private int previousPosX;
	private int previousPosY;
	private Environnement maison;

	public static Agent getInstance(Environnement maison) {
		if (INSTANCE==null) {
			INSTANCE = new Agent(maison);
		}
		return INSTANCE;
	}
	public static Agent getInstance() {
		return INSTANCE;
	}
	private Agent(Environnement maison) {
		this.uniteElec = 0;
		this.etatBDI = 0;
		this.posX = 0;
		this.posY = 0;
		this.maison = maison;
	}
	public ArrayList<Noeud> explorationNonInformee(Case agent) {

		Noeud racine = new Noeud(agent, null);
		Stack<Noeud> stack = new Stack<Noeud>();
		ArrayList<Point> visit = new ArrayList<Point>();
		stack.push(racine);

		while(!stack.isEmpty()){
			Noeud n = stack.pop();
			if(visit.contains(new Point(n.getC().getPosX(), n.getC().getPosY()))){
				n.setVisited(true);
			}
			if(!n.isVisited()){
				n.setVisited(true);
				visit.add(new Point(n.getC().getPosX(), n.getC().getPosY()));
				int posX = n.getC().getPosX();
				int posY = n.getC().getPosY();

				/* Robot sur case non vide */
				if(this.maison.getDirt(posX,posY).getValue()>0){
					ArrayList<Noeud> path = new ArrayList<Noeud>();
					if(this.maison.getDirt(posX,posY)== Case.Dirt.DIAMANT){
						path.add(new Noeud(this.maison.getCase(posX,posY),n, Noeud.Action.PICKUP));
					}
					if(this.maison.getDirt(posX,posY)== Case.Dirt.POUSSIERE || this.maison.getDirt(posX,posY)== Case.Dirt.MIXE){
						path.add(new Noeud(this.maison.getCase(posX,posY),n,Noeud.Action.VACUUM));
					}
					while(n.getParent() != null){
						path.add(n);
						n = n.getParent();
					}
					Collections.reverse(path);

					return path;
				}

				/* Mouvement Haut */
				if(posX - 1 >= 0){
					stack.push(new Noeud(this.maison.getCase(posX-1,posY),n, Noeud.Action.UP));
				}

				/* Mouvement Bas */
				if(posX + 1 < 5){
					stack.push(new Noeud(this.maison.getCase(posX+1,posY),n, Noeud.Action.DOWN));
				}

				/* Mouvement Gauche */
				if(posY - 1 >= 0){
					stack.push(new Noeud(this.maison.getCase(posX,posY-1),n, Noeud.Action.LEFT));
				}

				/* Mouvement Droite */
				if(posY + 1 < 5){
					stack.push(new Noeud(this.maison.getCase(posX,posY+1),n, Noeud.Action.RIGHT));
				}
			}
		}
		return null;
	}
	public void randomMove() {
		switch((int)(Math.ceil(Math.random()*4))) {
			case 1:
				moveUp();
				break;
			case 2:
				moveDown();
				break;
			case 3:
				moveLeft();
				break;
			case 4:
				moveRight();
				break;
		}
	}
	public void doAction(Noeud.Action action) {
		switch (action) {
			case UP -> moveUp();
			case DOWN -> moveDown();
			case LEFT -> moveLeft();
			case RIGHT -> moveRight();
			case PICKUP -> pickup();
			case VACUUM -> vacuum();
		}
	}

	public void pickup() {
		this.maison.clean(this.posX, this.posY);
	}
	public void vacuum() {
		this.maison.clean(this.posX, this.posY);
	}
	public void moveLeft() {
		if (this.posY -1 >=0) {
			this.previousPosY=this.posY;
			this.posY--;
		}
		else System.out.println("erreur left");
	}
	
	public void moveRight() {
		if (this.posY +1 <maison.getHeight()) {
			this.previousPosY=this.posY;
			this.posY++;
		}
		else System.out.println("erreur right");
	}
	
	public void moveDown() {
		if (this.posX +1 <maison.getWidth()) {
			this.previousPosX=this.posX;
			this.posX++;
		}
		else System.out.println("erreur down");
	}
	
	public void moveUp() {
		if (this.posX -1 >=0) {
			this.previousPosX=this.posX;
			this.posX--;
		}
		else System.out.println("erreur up");
	}
	int distanceManhanttan(int x1, int x2, int y1, int y2) {
		return Math.abs(x2 - x1) + Math.abs(y2 - y1);
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

	public int getPreviousPosX() {
		return previousPosX;
	}

	public int getPreviousPosY() {
		return previousPosY;
	}

	public int getUniteElec() {
		return this.uniteElec;
	}
	
	public int getEtatBDI() {
		return this.etatBDI;
	}

}
