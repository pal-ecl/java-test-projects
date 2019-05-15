package FighterK;

public interface Fighter {
	
	
	public int getStrength();
	public String getName();
	public int getHp();
	public void setHp(int hp);
	public void hit (Fighter fighter2);
	public void displayFighterInfo();
	public void displayFighterState();

}
