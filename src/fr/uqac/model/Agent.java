package fr.uqac.model;

import fr.uqac.model.Environnement;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Cette classe permet d'implémenter notre agent avec un état mental BDI, des
 * capteurs et effecteurs
 * et quelques outils de mesure de performance et de score
 */
public class Agent {
	private static Agent INSTANCE;
	private Case[][] croyance;
	private int uniteElec;
	private int deplacements;
	private int score;
	private int nb_pv;
	private double mesure_performance_1;
	private double mesure_performance_2;
	private int posX;
	private int posY;
	private int previousPosX;
	private int previousPosY;
	private Environnement manoir;

	public static Agent getInstance(Environnement manoir) {
		if (INSTANCE == null) {
			INSTANCE = new Agent(manoir);
		}
		return INSTANCE;
	}

	public static Agent getInstance() {
		return INSTANCE;
	}

	private Agent(Environnement manoir) {
		this.uniteElec = 0;
		this.deplacements = 0;
		this.score = 0;
		this.mesure_performance_1 = 0.0;
		this.mesure_performance_2 = 0.0;
		this.nb_pv = 0;
		this.posX = 0;
		this.posY = 0;
		this.manoir = manoir;
	}

	// Tant qu'il y a un robot, on estime qu'il est en état de marche
	public boolean AmIAlive() {
		if (getInstance() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Capteurs
	 */
	// Le robot prend connaissance de son environnement afin d'établir ses croyances
	// (beliefs)
	public synchronized Case[][] Croyances() {
		croyance = Environnement.getEnvironnement();
		return croyance;

	}

	/**
	 * Effecteurs
	 */

	/**
	 * On implémente 2 types d'explorations: non-informée et informée qui
	 * constituent ses mouvements à faire
	 * (intentions) afin d'atteindre son but qui est de nettoyer la manoir (desire)
	 */

	/**
	 * Il s'agit d'une Best-First Search avec la vréation d'une file FIFO à l'aide
	 * des noeuds voisins
	 */

	public ArrayList<Noeud> explorationNonInformee(Case agent) {

		Noeud racine = new Noeud(agent, null);
		Stack<Noeud> intentions = new Stack<Noeud>();
		ArrayList<Point> visit = new ArrayList<Point>();
		intentions.push(racine);

		while (!intentions.isEmpty()) {
			Noeud n = intentions.pop();
			if (visit.contains(new Point(n.getC().getPosX(), n.getC().getPosY()))) {
				n.setVisited(true);
			}
			if (!n.isVisited()) {
				n.setVisited(true);
				visit.add(new Point(n.getC().getPosX(), n.getC().getPosY()));
				int posX = n.getC().getPosX();
				int posY = n.getC().getPosY();

				/* Robot sur case non vide */
				if (this.manoir.getDirt(posX, posY).getValue() > 0) {
					ArrayList<Noeud> path = new ArrayList<Noeud>();
					if (this.manoir.getDirt(posX, posY) == Case.Dirt.DIAMANT) {
						path.add(new Noeud(this.manoir.getCase(posX, posY), n, Noeud.Action.PICKUP));
					}
					if (this.manoir.getDirt(posX, posY) == Case.Dirt.POUSSIERE
							|| this.manoir.getDirt(posX, posY) == Case.Dirt.MIXE) {
						path.add(new Noeud(this.manoir.getCase(posX, posY), n, Noeud.Action.VACUUM));
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
					intentions.push(new Noeud(this.manoir.getCase(posX - 1, posY), n, Noeud.Action.UP));
				}

				/* Mouvement Bas */
				if (posX + 1 < this.manoir.getWidth()) {
					intentions.push(new Noeud(this.manoir.getCase(posX + 1, posY), n, Noeud.Action.DOWN));
				}

				/* Mouvement Gauche */
				if (posY - 1 >= 0) {
					intentions.push(new Noeud(this.manoir.getCase(posX, posY - 1), n, Noeud.Action.LEFT));
				}

				/* Mouvement Droite */
				if (posY + 1 < this.manoir.getHeight()) {
					intentions.push(new Noeud(this.manoir.getCase(posX, posY + 1), n, Noeud.Action.RIGHT));
				}
			}
		}
		return null;
	}

	/**
	 * Nous utilisons encore une Best-First Search avec l'ajout de la distance de
	 * Manhattan afin d'évaluer les meilleures options (noeuds) à prendre pour
	 * atteindre le but
	 */

	public ArrayList<Noeud> explorationInformee(Case agent) {
		Stack<Noeud> intentions = new Stack<Noeud>();
		Noeud currentNode = new Noeud(agent, null);
		intentions.push(currentNode);

		Case goalCase = findNearestDirt(agent);
		// Si rien n'est trouvé on quitte l'exploration
		if (goalCase == null)
			return null;
		Noeud goalNode = new Noeud(goalCase, null);
		int goalPosX = goalNode.getC().getPosX();
		int goalPosY = goalNode.getC().getPosY();

		int miniumManhattanDistance = distanceManhanttan(currentNode.getC().getPosX(),
				currentNode.getC().getPosY(),
				goalPosX, goalPosY);
		int distanceToGoal = miniumManhattanDistance;
		System.out.println("Distance Manhattan :" + miniumManhattanDistance);

		while (!intentions.isEmpty()) {
			currentNode = intentions.pop();
			// Si la case est occupée
			if (this.manoir.getDirt(currentNode.getC().getPosX(), currentNode.getC().getPosY()).getValue() > 0) {
				ArrayList<Noeud> path = new ArrayList<Noeud>();
				if (this.manoir.getDirt(currentNode.getC().getPosX(),
						currentNode.getC().getPosY()) == Case.Dirt.DIAMANT) {
					path.add(new Noeud(this.manoir.getCase(currentNode.getC().getPosX(), currentNode.getC().getPosY()),
							currentNode, Noeud.Action.PICKUP));
				}
				if (this.manoir.getDirt(currentNode.getC().getPosX(),
						currentNode.getC().getPosY()) == Case.Dirt.POUSSIERE
						|| this.manoir.getDirt(currentNode.getC().getPosX(),
								currentNode.getC().getPosY()) == Case.Dirt.MIXE) {
					path.add(new Noeud(this.manoir.getCase(currentNode.getC().getPosX(), currentNode.getC().getPosY()),
							currentNode, Noeud.Action.VACUUM));
				}
				while (currentNode.getParent() != null) {
					path.add(currentNode);
					currentNode = currentNode.getParent();
				}
				Collections.reverse(path);

				return path;
			}
			// Haut
			if (currentNode.getC().getPosX() - 1 >= 0) {
				int posHaut = currentNode.getC().getPosX() - 1;
				int hypotheticalDistanceToGoal = distanceManhanttan(posHaut, currentNode.getC().getPosY(), goalPosX,
						goalPosY);
				if (hypotheticalDistanceToGoal < distanceToGoal) {
					intentions.push(new Noeud(this.manoir.getCase(posHaut, currentNode.getC().getPosY()), currentNode,
							Noeud.Action.UP));
					distanceToGoal = hypotheticalDistanceToGoal;
				}
			}
			// Bas
			if (currentNode.getC().getPosX() + 1 < this.manoir.getWidth()) {
				int posBas = currentNode.getC().getPosX() + 1;
				int hypotheticalDistanceToGoal = distanceManhanttan(posBas, currentNode.getC().getPosY(), goalPosX,
						goalPosY);
				if (hypotheticalDistanceToGoal < distanceToGoal) {
					intentions.push(new Noeud(this.manoir.getCase(posBas, currentNode.getC().getPosY()), currentNode,
							Noeud.Action.DOWN));
					distanceToGoal = hypotheticalDistanceToGoal;
				}
			}
			// Gauche
			if (currentNode.getC().getPosY() - 1 >= 0) {
				int posGauche = currentNode.getC().getPosY() - 1;
				int hypotheticalDistanceToGoal = distanceManhanttan(currentNode.getC().getPosX(), posGauche, goalPosX,
						goalPosY);
				if (hypotheticalDistanceToGoal < distanceToGoal) {
					intentions.push(new Noeud(this.manoir.getCase(currentNode.getC().getPosX(), posGauche), currentNode,
							Noeud.Action.LEFT));
					distanceToGoal = hypotheticalDistanceToGoal;
				}
			}
			// Droite
			if (currentNode.getC().getPosY() + 1 < this.manoir.getHeight()) {
				int posDroite = currentNode.getC().getPosY() + 1;
				int hypotheticalDistanceToGoal = distanceManhanttan(currentNode.getC().getPosX(), posDroite, goalPosX,
						goalPosY);
				if (hypotheticalDistanceToGoal < distanceToGoal) {
					intentions.push(new Noeud(this.manoir.getCase(currentNode.getC().getPosX(), posDroite), currentNode,
							Noeud.Action.RIGHT));
					distanceToGoal = hypotheticalDistanceToGoal;
				}
			}
		}
		return null;
	}

	public Case findNearestDirt(Case depart) {
		Case goal = null;
		int distanceM = this.manoir.getWidth() * this.manoir.getHeight();
		for (int i = 0; i < this.manoir.getWidth(); i++) {
			for (int j = 0; j < this.manoir.getHeight(); j++) {
				if (this.manoir.getDirt(i, j).getValue() > 0) {
					int dM = distanceManhanttan(depart.getPosX(), depart.getPosY(), i, j);
					if (dM < distanceM) {
						goal = this.manoir.getCase(i, j);
						distanceM = dM;
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

	/**
	 * Cette fonction nous sert d'effecteur pour permettre au robot d'agir et se
	 * déplacer
	 */

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
		double d_nb_pv = this.nb_pv;
		double d_uniteElec = this.uniteElec;
		double d_deplacement = this.deplacements;
		this.mesure_performance_1 = d_nb_pv / d_deplacement;
		this.mesure_performance_2 = d_nb_pv / d_uniteElec;
		System.out.println("Unite elec : " + this.uniteElec
				+ "\n" + "Nombre de pickup & vacuum : " + this.nb_pv
				+ "\n" + "Score : " + this.score
				+ "\n"
				+ "Mesure de performance 1 (nombres saletés et diamants traités / nombre de déplacements total) : "
				+ this.mesure_performance_1 * 100 + "%"
				+ "\n" + "Mesure de performance 2 (unités élec des saletés et diamants / unités élec totales) : "
				+ this.mesure_performance_2 * 100 + "%");
	}

	/**
	 * Mesure de performance 1 : on effectue un pourcentage du nombre de saleté et
	 * diamants traités (ramassés ou aspirés)
	 * en rapport au nombre de déplacements. Cela permet de mesurer l'efficacité du
	 * robot dans ses déplacements.
	 * Il faut qu'il traite les saletés et diamants en le minimum de déplacements
	 * possibles
	 * 
	 * Mesure de performance 2 : il s'agit du même principe mais concernant les
	 * unités d'électricités utilisés.
	 * Il faut utiliser le moins d'unités électriques possible pour nettoyer le
	 * manoir
	 * 
	 * Score : le robot gagne 10 par saleté aspirée ou diamant ramassé et perd 20
	 * pour chauque diamant aspiré
	 * 
	 * Unité elec : le robot dépense une unité d'éléctricité par action : gauche,
	 * droite, haut, bas, ramasser, aspirer
	 */

	public void pickup() {
		this.goodAction();
		this.nb_pv += 1;
		this.manoir.clean(this.posX, this.posY);
	}

	public void vacuum() {
		this.nb_pv += 1;
		if (this.manoir.getDirt(this.posX, this.posY) == Case.Dirt.MIXE) {
			this.badAction();
		} else
			this.goodAction();
		this.manoir.clean(this.posX, this.posY);
	}

	public void moveLeft() {
		if (this.posY - 1 >= 0) {
			this.previousPosY = this.posY;
			this.deplacements++;
			this.posY--;
		} else
			System.out.println("erreur left");
	}

	public void moveRight() {
		if (this.posY + 1 < manoir.getHeight()) {
			this.previousPosY = this.posY;
			this.deplacements++;
			this.posY++;
		} else
			System.out.println("erreur right");
	}

	public void moveDown() {
		if (this.posX + 1 < manoir.getWidth()) {
			this.previousPosX = this.posX;
			this.deplacements++;
			this.posX++;
		} else
			System.out.println("erreur down");
	}

	public void moveUp() {
		if (this.posX - 1 >= 0) {
			this.previousPosX = this.posX;
			this.deplacements++;
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
		this.score += 10;
	}

	public void badAction() {
		this.score -= 20;
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

	public int getNb_pv() {
		return this.nb_pv;
	}

	public int getDeplacements() {
		return this.deplacements;
	}

	public int getScore() {
		return this.score;
	}

	public double getMesurePerformance1() {
		return this.mesure_performance_1;
	}

	public double getMesurePerformance2() {
		return this.mesure_performance_2;
	}

}
