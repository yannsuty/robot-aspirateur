package agent;

public class Agent {
	
	private int uniteElec;
	
	public Agent() {
		uniteElec = 0;
	}
	
	public void consomElec() {
		this.uniteElec++;
	}
	
	public int getUniteElec() {
		return this.uniteElec;
	}

}
