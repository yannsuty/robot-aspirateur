package recherche;

import java.util.ArrayList;

public class Uninforme {
	
	private ArrayList<Integer> chemin;
	private int posXR;
	private int PosYR;
	private int diffPosX;
	private int diffPosY;
	

	public Uninforme() {
		this.chemin = new ArrayList<Integer>();
	}

	
	public ArrayList<Integer> calculChemin( int posXR, int posYR, int PosXDest, int PosYDest) {
		
		
		this.posXR = posXR;
		PosYR = posYR;
		
		this.diffPosX = PosXDest - posXR;
		this.diffPosY = PosYDest - posYR;
		
		
		
		return chemin;
	}
	
	
	

}
