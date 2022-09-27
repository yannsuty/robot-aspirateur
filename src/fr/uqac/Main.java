package fr.uqac;
//test
import fr.uqac.front.MainFrame;
import fr.uqac.model.Maison;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Maison maison = new Maison(10,10, 20);
        MainFrame mainFrame = new MainFrame("Robot Aspirateur", new Dimension(400,600), maison);
        mainFrame.setVisible(true);
    }
}
