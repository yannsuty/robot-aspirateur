package fr.uqac.model;

public class Maison {
    private Dirt[][] grille;
    private int width;
    private int height;

    public enum Dirt {
        PROPRE(0), POUSSIERE(1), DIAMANT(2), MIXE(3);

        private int value;
        Dirt(int value) {
            this.value = value;
        }
        @Override
        public String toString() {
            return value+"";
        }
    }


    public Maison(int width, int height) {
        this(width, height, 2);
    }
    public Maison(int width, int height, int nb_dirt) {
        this.width = width;
        this.height = height;
        this.grille = new Dirt[width][height];
        for (int i=0; i<this.width; i++) {
            for (int j=0; j<this.height; j++) {
                this.grille[i][j]=Dirt.PROPRE;
            }
        }
    }

    public void generateRandomDirt(int nb_dirt) {
        int i =0;
        while (i<nb_dirt) {
            int posX = (int) (Math.floor(Math.random()*1000)%this.width);
            int posY = (int) (Math.floor(Math.random()*1000)%this.height);
            if (addDirt(posX, posY)) {
                i++;
            }
        }
    }
    public void generateRandomDirt() {
        int i=0;
        int posX, posY;
        do {
            posX = (int) (Math.floor(Math.random()*1000)%this.width);
            posY = (int) (Math.floor(Math.random()*1000)%this.height);
            i++;
            System.out.println(posX + " : " + posY);
        }while(!this.addDirt(posX, posY));
        System.out.println(i);
    }

    public boolean addDirt(int posX, int posY) {
        if (this.grille[posX][posY]!= Dirt.POUSSIERE) {
            if (this.grille[posX][posY]==Dirt.DIAMANT) {
                this.grille[posX][posY]=Dirt.MIXE;
            } else {
                this.grille[posX][posY]=Dirt.POUSSIERE;
            }
            return true;
        } else {
            return false;
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

    public boolean isDirty(int i, int j) {
        return grille[i][j] == Dirt.POUSSIERE;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Dirt getDirt(int posX, int posY) {
        return this.grille[posX][posY];
    }
}
