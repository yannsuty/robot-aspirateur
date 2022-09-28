package recherche;

import java.util.ArrayList;

import fr.uqac.model.Maison;

public class Analyse {
	
	private Maison m;
	private ArrayList<Element> elems ;
	
	public Analyse(Maison m) {
		this.m = m;
		this.elems = new  ArrayList<Element>();
	}
	
	public ArrayList<Element> searchDirtJewel(){
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				if(m.isDirty(i, j)) {
					elems.add(new Element(i,j,1));
				}/*else if(m.isJewel(i, j)) {
					elems.add(new Element(i,j,2));
				}*/
			}
		}
		return elems;
		
	}
	

}
