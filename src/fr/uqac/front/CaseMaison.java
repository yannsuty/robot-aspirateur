package fr.uqac.front;

import fr.uqac.model.Maison;

import javax.swing.*;
import java.awt.*;

public class CaseMaison extends JPanel {
    private Maison maison;
    private int posX;
    private int posY;
    public CaseMaison(int posX, int posY, Maison maison) {
        super();
        this.maison = maison;
        this.posX = posX;
        this.posY = posY;

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(new JLabel(posX + " : " + posY), BorderLayout.CENTER);

        this.setBackground();
    }

    public void setBackground() {
        switch (this.maison.getDirt(this.posX, this.posY)) {
            case POUSSIERE:
                this.setBackground(new Color(100,100,100));
                break;
            case DIAMANT:
                this.setBackground(new Color(135,206,235));
                break;
            case MIXE:
                this.setBackground(new Color(1,11,60));
                break;
        }
    }
    public void updateGUI() {
        this.setBackground();
        this.revalidate();
    }

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
    
    
}
