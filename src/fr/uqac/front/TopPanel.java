package fr.uqac.front;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {
    public TopPanel() {
        super();
        this.setLayout(new BorderLayout());
        this.add(new JLabel("La maison"), BorderLayout.CENTER);
    }
}
