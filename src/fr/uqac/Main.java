package fr.uqac;
//test
import fr.uqac.front.MainFrame;
import fr.uqac.model.Agent;
import fr.uqac.model.Environnement;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Environnement maison = new Environnement(5,5, 1);
        Agent robot = new Agent(maison);
        MainFrame mainFrame;
        int i = 0;

        mainFrame = new MainFrame("Robot Aspirateur", new Dimension(400,600), maison, robot);
        mainFrame.setVisible(true);
        while (mainFrame.isVisible()) {
            Thread.sleep(1000);
            maison.generateRandomDirt();
            robot.randomMove();
            System.out.println(robot.explorationNonInformee(maison.getCase(robot.getPosX(), robot.getPosY())));
            mainFrame.updateGUI();
        }
    }
}
