package fr.uqac;
//test
import fr.uqac.front.MainFrame;
import fr.uqac.model.Agent;
import fr.uqac.model.Environnement;
import fr.uqac.model.Noeud;

import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Environnement maison = new Environnement(5,5, 1);
        Agent robot = Agent.getInstance(maison);
        MainFrame mainFrame;
        int i = 0;

        mainFrame = new MainFrame("Robot Aspirateur", new Dimension(400,600), maison, robot);
        mainFrame.setVisible(true);
        new Thread() {
            @Override
            public void run() {
                super.run();
                while(mainFrame.isVisible()) {
                    synchronized (maison) {
                        maison.generateRandomDirt();
                        
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                while(mainFrame.isVisible()) {
                    synchronized (maison) {
                        maison.generateRandomJewel();
                    }
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();
        while (mainFrame.isVisible()) {
            if (maison.detect()) {
                ArrayList<Noeud> noeuds = robot.explorationNonInformee(maison.getCase(robot.getPosX(), robot.getPosY()));
                while(!noeuds.isEmpty()) {
                    synchronized (maison) {
                        robot.doAction(noeuds.get(0).getAction());
                        noeuds.remove(0);
                        mainFrame.updateGUI();
                        System.out.println(noeuds);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
