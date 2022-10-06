package fr.uqac.front;

import javax.swing.*;
import java.awt.*;

/**
 * Cette classe permet de configurer laffichage du robot que l'on a choisi
 * commme étant un point rouge
 */

public class RobotView extends JComponent {
    public RobotView() {
        super();
    }

    @Override
    public void paintComponents(Graphics g) {
        Graphics pencil = g.create();
        if (this.isOpaque()) {
            pencil.setColor(this.getBackground());
            pencil.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        pencil.setColor(Color.RED);
        int width = this.getWidth();
        int height = this.getHeight();
        int rayon = width < height ? width / 2 : height / 2;
        pencil.drawOval(width / 2, height / 2, rayon, rayon);
    }

    public void updateGUI() {
        this.revalidate();
    }
}
