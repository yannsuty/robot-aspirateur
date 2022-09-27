package fr.uqac;
//test
import fr.uqac.front.MainFrame;
import fr.uqac.model.Maison;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Maison maison = new Maison(5,5, 1);
        MainFrame mainFrame;
        int i = 0;

        mainFrame = new MainFrame("Robot Aspirateur", new Dimension(400,600), maison);
        mainFrame.setVisible(true);
        while (mainFrame.isVisible()) {
            Thread.sleep(2000);
            maison.generateRandomDirt();
            mainFrame.updateGUI();
            maison.showGrille();
        }
    }
}
