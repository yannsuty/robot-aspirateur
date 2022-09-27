package fr.uqac.front;

import fr.uqac.model.Maison;

import javax.swing.*;
import java.awt.*;

public class MaisonPanel extends JPanel {
    private Maison maison;
    private CaseMaison[][] casesMaison;
    public MaisonPanel(Maison maison) {
        super();
        this.maison = maison;
        this.casesMaison = new CaseMaison[maison.getWidth()][maison.getHeight()];
        this.setLayout(new GridLayout(maison.getWidth(), maison.getHeight()));
        for (int i = 0; i< maison.getWidth(); i++) {
            for (int j=0; j< maison.getHeight(); j++) {
                this.casesMaison[i][j] = new CaseMaison(i,j, this.maison.isDirty(i,j));
                this.add(this.casesMaison[i][j]);
            }
        }
    }
}
