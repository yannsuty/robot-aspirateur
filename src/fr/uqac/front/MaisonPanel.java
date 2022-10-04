package fr.uqac.front;

import fr.uqac.model.Agent;
import fr.uqac.model.Environnement;

import javax.swing.*;
import java.awt.*;

public class MaisonPanel extends JPanel {
    private Environnement maison;
    private Agent robot;
    private CaseMaison[][] casesMaison;
    public MaisonPanel(Environnement maison, Agent robot) {
        super();
        this.maison = maison;
        this.robot = robot;
        this.casesMaison = new CaseMaison[maison.getWidth()][maison.getHeight()];
        this.setLayout(new GridLayout(maison.getWidth(), maison.getHeight()));
        for (int i = 0; i< maison.getWidth(); i++) {
            for (int j=0; j< maison.getHeight(); j++) {
                this.casesMaison[i][j] = new CaseMaison(i,j, this.maison);
                this.add(this.casesMaison[i][j]);
            }
        }
    }
    public void addRobot() {
        this.casesMaison[this.robot.getPosX()][this.robot.getPosY()].enableRobot();
    }
    public void moveRobot() {
        this.casesMaison[this.robot.getPreviousPosX()][this.robot.getPreviousPosY()].disableRobot();
        this.casesMaison[this.robot.getPosX()][this.robot.getPosY()].enableRobot();
    }

    public void updateGUI() {
        for (int i = 0; i< this.maison.getWidth(); i++) {
            for (int j=0; j< this.maison.getHeight(); j++) {
                this.casesMaison[i][j].updateGUI();
            }
        }
        moveRobot();
    }
}
