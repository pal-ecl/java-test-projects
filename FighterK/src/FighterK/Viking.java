package FighterK;

public class Viking implements Fighter {
	private String name;
	private int initialStrength;
	private int strength;
	private int initialHp;
	private int hp;
	
	public Viking(String name, int hp, int strenght) {
		this(name, hp, strenght, false);
	}
	
	public Viking(String name, int hp, int strenght, boolean display) {
		this.name = name;
		this.initialHp = hp + Math.toIntExact(Math.round(Math.random()*25));
		this.hp = this.initialHp;
		this.initialStrength = strenght + Math.toIntExact(Math.round(Math.random()*5));
		this.strength = this.initialStrength;
		if (display){
			System.out.println("The viking " + this.getName() + " (" + this.getInitialHp() + " hp, " + this.getInitialStrength() + " strength) is ready to fight.");
		}
	}
	
	public Viking() {
		this.name = "Unknown";
		this.initialHp = 50 + Math.toIntExact(Math.round(Math.random()*25));
		this.hp = this.initialHp;
		this.initialStrength = 5 + Math.toIntExact(Math.round(Math.random()*5));
		this.strength = this.initialStrength;
	}
		
	
	public int getInitialHp() {
		return initialHp;
	}

	public int getInitialStrength() {
		return initialStrength;
	}

	public int getHp() {
		if (hp < 0){
			hp = 0;
		}
		if (hp > initialHp){
			hp = initialHp;
		}
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getStrength() {
		return strength + Math.toIntExact(Math.round(Math.random()*10));
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public String getName() {
		return name;
	}
	
	public void displayFighterState() {
		System.out.println(this.getName() + " has " + this.getHp() + " health points left.");
	}
	
	public void displayFighterInfo() {
		System.out.println(this.getName() + " has " + this.getHp() + " health points left on " + this.getInitialHp() + ". His strength is " + this.getInitialStrength() + ".");
	}
	
	// the fighter hit the other fighter, or use the lay on hand instead if his hp are low
	public void hit (Fighter fighter2) {
		int damage = this.getStrength();
		if (this.getHp()< 20){
			damage *= 3;
			System.out.println(this.getName() + " become berserk !");
		}
		fighter2.setHp(fighter2.getHp() - damage);
		System.out.println(this.name + " hits " + fighter2.getName() + " for " + damage + " points.");
	}
	
	
}
