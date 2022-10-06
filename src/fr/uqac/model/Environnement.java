package fr.uqac.model;

/**
 * Cette classe permet de mettre en plcae le manoir. On le constitue en faisant
 * apparaitre de manière aléatoire des saleté ou des diamants
 * 
 */
public class Environnement {
    private Case[][] grille;
    private static Case[][] environnement;
    private int width;
    private int height;

    public Environnement(int width, int height) {
        this(width, height, 2);
    }

    public Environnement(int width, int height, int nb_dirt) {
        this.width = width;
        this.height = height;
        this.grille = new Case[width][height];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.grille[i][j] = new Case(i, j);
            }
        }
    }

    /**
     * Saletés
     * 
     * @param nb_dirt
     */

    public void generateRandomDirt(int nb_dirt) {
        int i = 0;
        while (i < nb_dirt) {
            int posX = (int) (Math.floor(Math.random() * 1000) % this.width);
            int posY = (int) (Math.floor(Math.random() * 1000) % this.height);
            if (addDirt(posX, posY)) {
                i++;
            }
        }
    }

    public void generateRandomDirt() {
        int i = 0;
        int posX, posY;
        do {
            posX = (int) (Math.floor(Math.random() * 1000) % this.width);
            posY = (int) (Math.floor(Math.random() * 1000) % this.height);
            i++;
        } while (!this.addDirt(posX, posY));
        System.out.println(posX + " : " + posY);
    }

    public boolean addDirt(int posX, int posY) {
        if (this.grille[posX][posY].getDirt() != Case.Dirt.POUSSIERE) {
            if (this.grille[posX][posY].getDirt() == Case.Dirt.DIAMANT) {
                this.grille[posX][posY].setDirt(Case.Dirt.MIXE);
            } else {
                this.grille[posX][posY].setDirt(Case.Dirt.POUSSIERE);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Diamants
     * 
     * @param nb_jewel
     */

    public void generateRandomJewel(int nb_jewel) {
        int i = 0;
        while (i < nb_jewel) {
            int posX = (int) (Math.floor(Math.random() * 1000) % this.width);
            int posY = (int) (Math.floor(Math.random() * 1000) % this.height);
            if (addJewel(posX, posY)) {
                i++;
            }
        }
    }

    public void generateRandomJewel() {
        int i = 0;
        int posX, posY;
        do {
            posX = (int) (Math.floor(Math.random() * 1000) % this.width);
            posY = (int) (Math.floor(Math.random() * 1000) % this.height);
            i++;
        } while (!this.addJewel(posX, posY));
        System.out.println(posX + " : " + posY);
    }

    public boolean addJewel(int posX, int posY) {
        if (this.grille[posX][posY].getDirt() != Case.Dirt.DIAMANT) {
            if (this.grille[posX][posY].getDirt() == Case.Dirt.POUSSIERE) {
                this.grille[posX][posY].setDirt(Case.Dirt.MIXE);
            } else {
                this.grille[posX][posY].setDirt(Case.Dirt.DIAMANT);
            }
            return true;
        } else {
            return false;
        }
    }

    public void clean(int x, int y) {
        this.grille[x][y].setDirt(Case.Dirt.PROPRE);
    }

    /**
     * Cette fonction permet d'établir le but du robot (desire) : tant que le manoir
     * n'est pas propre, il continue d'agir
     */

    public boolean desires() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (this.grille[i][j].getDirt() != Case.Dirt.PROPRE)
                    return true;
            }
        }
        return false;
    }

    public boolean isDirty(int i, int j) {
        return grille[i][j].getDirt() == Case.Dirt.POUSSIERE;
    }

    public boolean isJewel(int i, int j) {
        return grille[i][j].getDirt() == Case.Dirt.DIAMANT;
    }

    public void showGrille() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                System.out.print(this.grille[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Case.Dirt getDirt(int posX, int posY) {
        return this.grille[posX][posY].getDirt();
    }

    public Case getCase(int posX, int posY) {
        return this.grille[posX][posY];
    }

    /**
     * Permet au robot r'observer l'environnement
     */
    public static synchronized Case[][] getEnvironnement() {
        return environnement;
    }
}
