package fr.uqac.model;

public class Environnement {
    private Case[][] grille;
    private int width;
    private int height;


    public Environnement(int width, int height) {
        this(width, height, 2);
    }
    public Environnement(int width, int height, int nb_dirt) {
        this.width = width;
        this.height = height;
        this.grille = new Case[width][height];
        for (int i=0; i<this.width; i++) {
            for (int j=0; j<this.height; j++) {
                this.grille[i][j]=new Case(i, j);
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
        }while(!this.addDirt(posX, posY));
        System.out.println(posX + " : " + posY);
    }

    public boolean addDirt(int posX, int posY) {
        if (this.grille[posX][posY].getDirt()!= Case.Dirt.POUSSIERE) {
            if (this.grille[posX][posY].getDirt()==Case.Dirt.DIAMANT) {
                this.grille[posX][posY].setDirt(Case.Dirt.MIXE);
            } else {
                this.grille[posX][posY].setDirt(Case.Dirt.POUSSIERE);
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
    public void clean(int x, int y) {
        this.grille[x][y].setDirt(Case.Dirt.PROPRE);
    }
    public boolean detect() {
        for (int i=0; i<this.width;i++) {
            for (int j=0;j<this.height; j++) {
                if (this.grille[i][j].getDirt()!= Case.Dirt.PROPRE) return true;
            }
        }
        return false;
    }
    public boolean isDirty(int i, int j) {
        return grille[i][j].getDirt() == Case.Dirt.POUSSIERE;
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
}
