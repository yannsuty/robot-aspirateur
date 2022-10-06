package fr.uqac.front;

import fr.uqac.model.Agent;
import fr.uqac.model.Environnement;

import javax.swing.*;
import java.awt.*;

/**
 * Cette classe permet de mettre en place la configuration de base de
 * l'interface graphique
 */

public class MainFrame extends JFrame {
    private ManoirPanel manoirPanel;

    public MainFrame(String title, Dimension dimension, Environnement manoir, Agent robot) {
        super();
        this.setSize(dimension);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setTitle(title);
        this.add(new TopPanel(), BorderLayout.NORTH);
        this.manoirPanel = new ManoirPanel(manoir, robot);
        this.add(this.manoirPanel, BorderLayout.CENTER);
    }

    public void updateGUI() {
        this.manoirPanel.updateGUI();
    }
}
