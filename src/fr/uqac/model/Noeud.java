package fr.uqac.model;

import fr.uqac.front.CaseManoir;

/**
 * Afin d'établir une liste de mouvements à faire par le robot afin d'atteindre
 * les saletés et poussières,
 * on utilise un heuristique sans boucle à l'aide de noeuds
 */

public class Noeud {
    public enum Action {
        START, RIGHT, LEFT, UP, DOWN, VACUUM, PICKUP
    }

    private Case c;
    private Noeud parent;
    private Action action;
    private int cout;
    private boolean isVisited;

    public Noeud(Case ca, Noeud parent) {
        c = ca;
        this.parent = parent;

        if (parent == null) {
            int cout = 0;
        } else {
            cout = parent.getCout();
        }
        action = Action.START;
        isVisited = false;
    }

    public Noeud(Case ca, Noeud parent, Action action) {
        c = ca;
        this.parent = parent;
        this.action = action;
        isVisited = false;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void setParent(Noeud parent) {
        this.parent = parent;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }

    public int getCout() {
        return cout;
    }

    public Noeud getParent() {
        return parent;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public Case getC() {
        return c;
    }

    public void setC(Case c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Noeud{" + action + "}";
    }
}
