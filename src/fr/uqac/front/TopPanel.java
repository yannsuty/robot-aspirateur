package fr.uqac.front;

import javax.swing.*;
import java.awt.*;

/**
 * Cette classe permet de nommer notre interface graphique "Le manoir" que
 * représente la grille de cases
 */

public class TopPanel extends JPanel {
    public TopPanel() {
        super();
        this.setLayout(new BorderLayout());
        this.add(new JLabel("Le manoir"), BorderLayout.CENTER);
    }
}
