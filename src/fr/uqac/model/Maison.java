package fr.uqac.model;

public class Maison {
    private int[][] grille;
    private int width;
    private int height;

    public Maison(int width, int height) {
        this(width, height, 2);
    }
    public Maison(int width, int height, int nb_dirt) {
        this.width = width;
        this.height = height;
        this.grille = new int[width][height];
        randomDirt(nb_dirt);
    }

    public void randomDirt(int nb_dirt) {
        int i =0;
        while (i<nb_dirt) {
            int posX = (int) (Math.floor(Math.random()*1000)%this.width);
            int posY = (int) (Math.floor(Math.random()*1000)%this.height);
            if (this.grille[posX][posY]!=1) {
                this.grille[posX][posY]=1;
                i++;
            }
        }
    }

    public void showGrille() {
        for (int i=0; i<this.width; i++) {
            for (int j=0; j<this.height; j++) {
                System.out.print(this.grille[i][j]+" ");
            }
            System.out.println();
        }
    }
}
