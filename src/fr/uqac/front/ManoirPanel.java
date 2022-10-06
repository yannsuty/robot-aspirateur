package fr.uqac.front;

import fr.uqac.model.Agent;
import fr.uqac.model.Environnement;

import javax.swing.*;
import java.awt.*;

/**
 * Cette classe permet de mettre en place la configuration de base de
 * l'interface graphique avec les bonnes dimensions
 */

public class ManoirPanel extends JPanel {
    private Environnement manoir;
    private Agent robot;
    private CaseManoir[][] casesManoir;

    public ManoirPanel(Environnement manoir, Agent robot) {
        super();
        this.manoir = manoir;
        this.robot = robot;
        this.casesManoir = new CaseManoir[manoir.getWidth()][manoir.getHeight()];
        this.setLayout(new GridLayout(manoir.getWidth(), manoir.getHeight()));
        for (int i = 0; i < manoir.getWidth(); i++) {
            for (int j = 0; j < manoir.getHeight(); j++) {
                this.casesManoir[i][j] = new CaseManoir(i, j, this.manoir);
                this.add(this.casesManoir[i][j]);
            }
        }
    }

    public void updateGUI() {
        for (int i = 0; i < this.manoir.getWidth(); i++) {
            for (int j = 0; j < this.manoir.getHeight(); j++) {
                this.casesManoir[i][j].updateGUI();
            }
        }
    }
}
