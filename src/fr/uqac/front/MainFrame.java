package fr.uqac.front;

import fr.uqac.model.Agent;
import fr.uqac.model.Environnement;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame  {
    private MaisonPanel maisonPanel;
    public MainFrame(String title, Dimension dimension, Environnement maison, Agent robot) {
        super();
        this.setSize(dimension);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setTitle(title);
        this.add(new TopPanel(), BorderLayout.NORTH);
        this.maisonPanel = new MaisonPanel(maison, robot);
        this.add(this.maisonPanel, BorderLayout.CENTER);
        this.maisonPanel.addRobot();
    }
    public void updateGUI() {
        this.maisonPanel.updateGUI();
    }
}
