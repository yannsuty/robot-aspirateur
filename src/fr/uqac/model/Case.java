package fr.uqac.model;

public class Case {

    public enum Dirt {
        PROPRE(0), POUSSIERE(1), DIAMANT(2), MIXE(3);

        private int value;
        Dirt(int value) {
            this.value = value;
        }
        public int getValue() {
            return this.value;
        }
        @Override
        public String toString() {
            return value+"";
        }
    }
    private int posX;
    private int posY;

    private Dirt dirt;
    private boolean robot;

    public Case(int x, int y) {
        this.robot = false;
        this.dirt = Dirt.PROPRE;
        this.posX=x;
        this.posY=y;
    }

    public Dirt getDirt() {
        return this.dirt;
    }

    public void setDirt(Dirt dirt) {
        this.dirt =dirt;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
