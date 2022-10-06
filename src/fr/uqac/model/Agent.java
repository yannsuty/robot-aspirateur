package fr.uqac.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Agent {
	private static Agent INSTANCE;
	private int uniteElec;
	private int etatBDI;
	private int score;
	private int posX;
	private int posY;
	private int previousPosX;
	private int previousPosY;
	private Environnement maison;

	public static Agent getInstance(Environnement maison) {
		if (INSTANCE == null) {
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
		this.score = 0;
		this.posX = 0;
		this.posY = 0;
		this.maison = maison;
	}

	public ArrayList<Noeud> explorationNonInformee(Case agent) {

		Noeud racine = new Noeud(agent, null);
		Stack<Noeud> stack = new Stack<Noeud>();
		ArrayList<Point> visit = new ArrayList<Point>();
		stack.push(racine);

		while (!stack.isEmpty()) {
			Noeud n = stack.pop();
			if (visit.contains(new Point(n.getC().getPosX(), n.getC().getPosY()))) {
				n.setVisited(true);
			}
			if (!n.isVisited()) {
				n.setVisited(true);
				visit.add(new Point(n.getC().getPosX(), n.getC().getPosY()));
				int posX = n.getC().getPosX();
				int posY = n.getC().getPosY();

				/* Robot sur case non vide */
				if (this.maison.getDirt(posX, posY).getValue() > 0) {
					ArrayList<Noeud> path = new ArrayList<Noeud>();
					if (this.maison.getDirt(posX, posY) == Case.Dirt.DIAMANT) {
						path.add(new Noeud(this.maison.getCase(posX, posY), n, Noeud.Action.PICKUP));
					}
					if(this.maison.getDirt(posX,posY)== Case.Dirt.POUSSIERE || this.maison.getDirt(posX,posY)== Case.Dirt.MIXE){
						path.add(new Noeud(this.maison.getCase(posX,posY),n,Noeud.Action.VACUUM));
					}
					while (n.getParent() != null) {
						path.add(n);
						n = n.getParent();
					}
					Collections.reverse(path);

					return path;
				}

				/* Mouvement Haut */
				if (posX - 1 >= 0) {
					stack.push(new Noeud(this.maison.getCase(posX - 1, posY), n, Noeud.Action.UP));
				}

				/* Mouvement Bas */
				if(posX + 1 < this.maison.getWidth()){
					stack.push(new Noeud(this.maison.getCase(posX+1,posY),n, Noeud.Action.DOWN));
				}

				/* Mouvement Gauche */
				if (posY - 1 >= 0) {
					stack.push(new Noeud(this.maison.getCase(posX, posY - 1), n, Noeud.Action.LEFT));
				}

				/* Mouvement Droite */
				if(posY + 1 < this.maison.getHeight()){
					stack.push(new Noeud(this.maison.getCase(posX,posY+1),n, Noeud.Action.RIGHT));
				}
			}
		}
		return null;
	}
	public ArrayList<Noeud> explorationInformee(Case agent) {
		Stack<Noeud> stack = new Stack<Noeud>();
		Noeud currentNode = new Noeud(agent, null);
		stack.push(currentNode);

		Case goalCase = findNearestDirt(agent);
		//Si rien n'est trouvé on quitte l'exploration
		if (goalCase == null) return null;
		Noeud goalNode = new Noeud(goalCase,null);
		int goalPosX = goalNode.getC().getPosX();
		int goalPosY = goalNode.getC().getPosY();

		int miniumManhattanDistance = distanceManhanttan(currentNode.getC().getPosX(),
				currentNode.getC().getPosY(),
				goalPosX, goalPosY);
		int distanceToGoal = miniumManhattanDistance;
		System.out.println("Distance Manhattan :" + miniumManhattanDistance);

		while (!stack.isEmpty()) {
			currentNode = stack.pop();
			//Si la case est occupée
			if(this.maison.getDirt(currentNode.getC().getPosX(),currentNode.getC().getPosY()).getValue()>0){
				ArrayList<Noeud> path = new ArrayList<Noeud>();
				if(this.maison.getDirt(currentNode.getC().getPosX(),currentNode.getC().getPosY())== Case.Dirt.DIAMANT){
					path.add(new Noeud(this.maison.getCase(currentNode.getC().getPosX(),currentNode.getC().getPosY()),
							currentNode, Noeud.Action.PICKUP));
				}
				if(this.maison.getDirt(currentNode.getC().getPosX(),currentNode.getC().getPosY())== Case.Dirt.POUSSIERE
						|| this.maison.getDirt(currentNode.getC().getPosX(),currentNode.getC().getPosY())== Case.Dirt.MIXE){
					path.add(new Noeud(this.maison.getCase(currentNode.getC().getPosX(),currentNode.getC().getPosY()),
							currentNode,Noeud.Action.VACUUM));
				}
				while(currentNode.getParent() != null){
					path.add(currentNode);
					currentNode = currentNode.getParent();
				}
				Collections.reverse(path);

				return path;
			}
			//Haut
			if (currentNode.getC().getPosX()-1>=0) {
				int posHaut = currentNode.getC().getPosX()-1;
				int hypotheticalDistanceToGoal = distanceManhanttan(posHaut, currentNode.getC().getPosY(), goalPosX, goalPosY);
				if (hypotheticalDistanceToGoal<distanceToGoal) {
					stack.push(new Noeud(this.maison.getCase(posHaut, currentNode.getC().getPosY()), currentNode, Noeud.Action.UP));
					distanceToGoal=hypotheticalDistanceToGoal;
				}
			}
			//Bas
			if (currentNode.getC().getPosX()+1<this.maison.getWidth()) {
				int posBas = currentNode.getC().getPosX()+1;
				int hypotheticalDistanceToGoal = distanceManhanttan(posBas, currentNode.getC().getPosY(), goalPosX, goalPosY);
				if (hypotheticalDistanceToGoal<distanceToGoal) {
					stack.push(new Noeud(this.maison.getCase(posBas, currentNode.getC().getPosY()), currentNode, Noeud.Action.DOWN));
					distanceToGoal=hypotheticalDistanceToGoal;
				}
			}
			//Gauche
			if (currentNode.getC().getPosY()-1>=0) {
				int posGauche = currentNode.getC().getPosY()-1;
				int hypotheticalDistanceToGoal = distanceManhanttan(currentNode.getC().getPosX(), posGauche, goalPosX, goalPosY);
				if (hypotheticalDistanceToGoal<distanceToGoal) {
					stack.push(new Noeud(this.maison.getCase(currentNode.getC().getPosX(), posGauche), currentNode, Noeud.Action.LEFT));
					distanceToGoal=hypotheticalDistanceToGoal;
				}
			}
			//Droite
			if (currentNode.getC().getPosY()+1<this.maison.getHeight()) {
				int posDroite = currentNode.getC().getPosY()+1;
				int hypotheticalDistanceToGoal = distanceManhanttan(currentNode.getC().getPosX(), posDroite, goalPosX, goalPosY);
				if (hypotheticalDistanceToGoal<distanceToGoal) {
					stack.push(new Noeud(this.maison.getCase(currentNode.getC().getPosX(), posDroite), currentNode, Noeud.Action.RIGHT));
					distanceToGoal=hypotheticalDistanceToGoal;
				}
			}
		}
		return null;
	}
	public Case findNearestDirt(Case depart) {
		Case goal = null;
		int distanceM = this.maison.getWidth()*this.maison.getHeight();
		for (int i=0; i<this.maison.getWidth(); i++) {
			for (int j=0; j<this.maison.getHeight(); j++) {
				if (this.maison.getDirt(i,j).getValue()>0) {
					int dM = distanceManhanttan(depart.getPosX(), depart.getPosY(), i,j);
					if (dM<distanceM) {
						goal = this.maison.getCase(i,j);
						distanceM=dM;
					}
				}
			}
		}
		return goal;
	}
	public void randomMove() {
		switch ((int) (Math.ceil(Math.random() * 4))) {
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
			case UP:
				moveUp();
				break;
			case DOWN:
				moveDown();
				break;
			case LEFT:
				moveLeft();
				break;
			case RIGHT:
				moveRight();
				break;
			case PICKUP:
				pickup();
				break;
			case VACUUM:
				vacuum();
				break;
		}
		this.useElec();
		System.out.println("Unite elec : " + this.uniteElec + " score : " + this.score);
	}

	public void pickup() {
		this.score += 1;
		this.maison.clean(this.posX, this.posY);
	}

	public void vacuum() {
		if (this.maison.getDirt(this.posX, this.posY)== Case.Dirt.MIXE) {
			this.score -= 1;
		} else this.score += 1;
		this.maison.clean(this.posX, this.posY);
	}

	public void moveLeft() {
		if (this.posY - 1 >= 0) {
			this.previousPosY = this.posY;
			this.posY--;
		} else
			System.out.println("erreur left");
	}

	public void moveRight() {
		if (this.posY + 1 < maison.getHeight()) {
			this.previousPosY = this.posY;
			this.posY++;
		} else
			System.out.println("erreur right");
	}

	public void moveDown() {
		if (this.posX + 1 < maison.getWidth()) {
			this.previousPosX = this.posX;
			this.posX++;
		} else
			System.out.println("erreur down");
	}

	public void moveUp() {
		if (this.posX - 1 >= 0) {
			this.previousPosX = this.posX;
			this.posX--;
		} else
			System.out.println("erreur up");
	}
	int distanceManhanttan(int x1, int y1, int x2, int y2) {
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

	// Getters

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
