package fr.uqac;

import fr.uqac.front.MainFrame;
import fr.uqac.model.Agent;
import fr.uqac.model.Environnement;
import fr.uqac.model.Noeud;

import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // On peut personnaliser la taille du manoir (width, height), l'apparition des
        // saletés et diamants

        int width = 5;
        int height = 5;
        int nb_dirt = 1; // le nombre de saleté apparaissant à la fois

        int temp_apparition_dirt = 4000;
        int temp_apparition_jewel = 8000;

        Environnement manoir = new Environnement(width, height, nb_dirt);
        Agent robot = Agent.getInstance(manoir);
        MainFrame mainFrame;

        mainFrame = new MainFrame("IA TP1 : Robot Aspirateur", new Dimension(400, 600), manoir, robot);
        mainFrame.setVisible(true);

        // Génère l'apparition aléatoire de la saleté tous les intervalles de temps
        // temp_apparition_dirt (en ms)
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (mainFrame.isVisible()) {
                    synchronized (manoir) {
                        manoir.generateRandomDirt();

                    }
                    try {
                        Thread.sleep(temp_apparition_dirt);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();

        // Génère l'apparition aléatoire des diamants tous les intervalles de temps
        // temp_apparition_jewel (en ms)
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (mainFrame.isVisible()) {
                    synchronized (manoir) {
                        manoir.generateRandomJewel();
                    }
                    try {
                        Thread.sleep(temp_apparition_jewel);
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
