package fr.uqac.front;

import fr.uqac.model.Maison;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame  {
    public MainFrame(String title, Dimension dimension, Maison maison) {
        super();
        this.setSize(dimension);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setTitle(title);
        this.add(new TopPanel(), BorderLayout.NORTH);
        this.add(new MaisonPanel(maison), BorderLayout.CENTER);
    }
}
