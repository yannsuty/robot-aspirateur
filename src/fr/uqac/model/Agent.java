package fr.uqac.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Agent {
	
	private int uniteElec;
	private int etatBDI;
	private int posX;
	private int posY;
	private Environnement maison;
	
	public Agent(Environnement maison) {
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
					if(this.maison.getDirt(posX,posY)== Case.Dirt.POUSSIERE){
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
	public void moveUp() {
		if (this.posY -1 >0) {
			this.posY--;
		}
		else System.out.println("erreur up");
	}
	
	public void moveDown() {
		if (this.posY +1 <maison.getHeight()) {
			this.posY++;
		}
		else System.out.println("erreur down");
	}
	
	public void moveRight() {
		if (this.posX +1 <maison.getWidth()) {
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
	
	public int getUniteElec() {
		return this.uniteElec;
	}
	
	public int getEtatBDI() {
		return this.etatBDI;
	}

}
