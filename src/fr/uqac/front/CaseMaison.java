package fr.uqac.front;

import fr.uqac.model.Agent;
import fr.uqac.model.Environnement;

import javax.swing.*;
import java.awt.*;

public class CaseMaison extends JPanel {
    private Environnement maison;
    private int posX;
    private Agent robot;
    private int posY;
    public CaseMaison(int posX, int posY, Environnement maison) {
        super();
        this.maison = maison;
        this.posX = posX;
        this.posY = posY;
        this.robot = Agent.getInstance();
        this.setMaximumSize(new Dimension(50,50));
    }

    @Override
    public void paint(Graphics g) {
        Graphics pencil = g.create();
        //Background
        switch (this.maison.getDirt(this.posX, this.posY)) {
            case PROPRE:
                pencil.setColor(Color.WHITE);
                break;
            case POUSSIERE:
                pencil.setColor(new Color(100,100,100));
                break;
            case DIAMANT:
                pencil.setColor(new Color(10,100,130));
                break;
            case MIXE:
                pencil.setColor(new Color(1,11,60));
                break;
        }
        pencil.fillRect(0, 0, this.getWidth(), this.getHeight());
        //Border
        pencil.setColor(Color.BLACK);
        pencil.fillRect(0,0,this.getWidth(),5);
        pencil.fillRect(0,0,5,this.getHeight());
//        pencil.fillRect(this.getWidth()-5,5, this.getWidth(), this.getHeight());
//        pencil.fillRect(0,this.getHeight(),this.getWidth(),this.getHeight()-5);
        //Robot
        if (this.robot.getPosX()==this.posX && this.robot.getPosY() == this.posY) {
            pencil.setColor(Color.RED);
            int width = this.getWidth();
            int height = this.getHeight();
            int rayon = width<height?width/2:height/2;
            pencil.fillOval(width/2-rayon/2, height/2-rayon/2, rayon, rayon);
        }
    }
    public void updateGUI() {
//        this.setBackground();
        this.repaint();
    }

    @Override
    public String toString() {
        return "[CaseMaison]=" + this.posX + " : " + this.posY;
    }
}
