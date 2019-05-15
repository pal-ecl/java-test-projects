package FighterK;

public class Fight {

	private Fighter fighter1;
	private Fighter fighter2;
	
	public Fight (Fighter fighter1, Fighter fighter2)throws SameFighterException{
		setFighter1(fighter1);
		setFighter2(fighter2);
		System.out.println("\n" + fighter1.getName() + " fights " + fighter2.getName() + " !");
		if (fighter1 == fighter2){
			throw new SameFighterException("A fighter can NOT fight versus himself.");
		}
		fight(getFighter1(), getFighter2());
	}
	
	public Fighter getFighter1() {
		return fighter1;
	}

	public void setFighter1(Fighter fighter1) {
		this.fighter1 = fighter1;
	}

	public Fighter getFighter2() {
		return fighter2;
	}

	public void setFighter2(Fighter fighter2) {
		this.fighter2 = fighter2;
	}

	public void fight(Fighter fighter1, Fighter fighter2){
		int fightRound =1;
		while (fighter1.getHp()>0 && fighter2.getHp()>0){
			System.out.println("\n Round " + fightRound + ":");
			if (this.firstToHit(fighter1, fighter2)==fighter2){
				fightingRound(fighter2, fighter1, fightRound);
			}else {
				fightingRound(fighter1, fighter2, fightRound);
			}
			fightRound++;
		}
		if (fighter1.getHp()== 0){
			System.out.println("\n" + fighter2.getName() + " wins the fight!\n");
		}else {
			System.out.println("\n" + fighter1.getName() + " wins the fight!\n");
		}
		fighter1.displayFighterInfo();
		fighter2.displayFighterInfo();
	}
	
	// for each round, who hit first
	private Fighter firstToHit(Fighter fighter1, Fighter fighter2) {
		
		if ( Math.round(Math.random()*10) < 5 ){
			return fighter2;
		}
		return fighter1;		
	}
	
	//each round of the fight
	private void fightingRound(Fighter fighter1, Fighter fighter2, int fightRound) {
		if (fighter1 instanceof Knight && fightRound != 1){
			if (fighter1.getHp() < 30 && ((Knight) fighter1).getLayOnHand() > 0){
				((Knight) fighter1).useLayOnHand();					
			} else {
			fighter1.hit(fighter2);
			}
			if (fighter2.getHp()>0) {
				fighter2.hit(fighter1);
			}
		}else if (fightRound == 1){
			if (fighter2 instanceof Samourai) {
				System.out.println(fighter2.getName() + " dodges !");
			} else {
				fighter1.hit(fighter2);	
			}
			if (fighter1 instanceof Samourai) {
				System.out.println(fighter1.getName() + " dodges !");
			} else {
				if (fighter2.getHp()>0) {
					fighter2.hit(fighter1);
				}
			}
		} else {
			fighter1.hit(fighter2);
			if (fighter2.getHp()>0) {
				fighter2.hit(fighter1);
			}
		}
		fighter1.displayFighterState();
		fighter2.displayFighterState();
	}
}
