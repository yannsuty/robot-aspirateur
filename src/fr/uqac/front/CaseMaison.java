package fr.uqac.front;

import javax.swing.*;
import java.awt.*;

public class CaseMaison extends JPanel {
    private boolean dirt;
    public CaseMaison(int posX, int posY, boolean dirt) {
        super();
        this.dirt = dirt;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(new JLabel(posX + " : " + posY), BorderLayout.CENTER);

        if (dirt) {
            this.setBackground(new Color(100,100,100));
        }
    }
}
