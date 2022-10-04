package fr.uqac.model;

public class Robot extends Agent {
    private int previousPosX;
    private int previousPosY;
    public Robot(Environnement maison) {
        super(maison);
    }

    public void randomMove() {
        this.saveCurrentPosition();
        int rand = (int) (Math.floor(Math.random()*4));
        switch (rand) {
            case 0:
                this.moveUp();
                break;
            case 1:
                this.moveDown();
                break;
            case 2:
                this.moveLeft();
                break;
            case 3:
                this.moveRight();
        }
    }
    public void saveCurrentPosition() {
        this.previousPosX=this.getPosX();
        this.previousPosY=this.getPosY();
    }
    public int getPreviousPosX() {
        return previousPosX;
    }

    public void setPreviousPosX(int previousPosX) {
        this.previousPosX = previousPosX;
    }

    public int getPreviousPosY() {
        return previousPosY;
    }

    public void setPreviousPosY(int previousPosY) {
        this.previousPosY = previousPosY;
    }
}
