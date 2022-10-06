package fr.uqac;

import fr.uqac.front.MainFrame;
import fr.uqac.model.Agent;
import fr.uqac.model.Environnement;
import fr.uqac.model.Noeud;

import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Environnement manoir = new Environnement(5, 5, 1);
        Agent robot = Agent.getInstance(manoir);
        MainFrame mainFrame;

        mainFrame = new MainFrame("Robot Aspirateur", new Dimension(400, 600), manoir, robot);
        mainFrame.setVisible(true);
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (mainFrame.isVisible()) {
                    synchronized (manoir) {
                        manoir.generateRandomDirt();

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
                while (mainFrame.isVisible()) {
                    synchronized (manoir) {
                        manoir.generateRandomJewel();
                    }
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();

        // Tant que le robot et le manoir sont présents, le programme tourne

        while (mainFrame.isVisible() && robot.AmIAlive()) {
            // On effecture un test de désir afin de savoir si le manoir, auquel cas, le
            // robot agit dans le but de le nettoyer
            if (manoir.desires()) {
                // Les séries d'actions constituent les intentions du robot
                ArrayList<Noeud> intentions = new ArrayList<>();
                // Le robot observe son environnement afin de décider de ses intentions
                if (robot.Croyances() != null) {
                    // Si le robot n'a pas connaissance de son environnement, l'exploration sera
                    // alors non informée
                    intentions = robot.explorationNonInformee(manoir.getCase(robot.getPosX(), robot.getPosY()));
                } else {
                    // Si il a connaissance de son environnement, alors elle sera informée
                    intentions = robot.explorationInformee(manoir.getCase(robot.getPosX(), robot.getPosY()));
                }

                System.out.println(intentions);
                while (!intentions.isEmpty()) {
                    synchronized (manoir) {
                        // Le robot agit afin d'effectuer ses intentions
                        robot.doAction(intentions.get(0).getAction());
                        intentions.remove(0);
                        mainFrame.updateGUI();
                        System.out.println(intentions);
                    }
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
