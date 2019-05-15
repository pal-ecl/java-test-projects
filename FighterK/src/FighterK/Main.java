package FighterK;

public class Main {
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Knight perceval = new Knight("Perceval", 75, 10, true);
		Knight yvain = new Knight("Yvain", 75, 10, true);
		Knight gauvain = new Knight("Gauvain", 75, 10, true);
		Knight cliges = new Knight("Cligès", 75, 10, true);
		Knight erec = new Knight("Erec", 75, 10, true);
		System.out.println("\n");
		Samourai musashi = new Samourai("Musashi", 65, 12, true);
		Samourai miyamoto = new Samourai("Miyamoto", 65, 12, true);
		System.out.println("\n");
		Viking eric = new Viking ("Eric", 85, 8, true);
		Viking olaf = new Viking ("Olaf", 85, 8, true);
		

		Fighter[] fighters = {perceval,yvain, gauvain, cliges, erec, musashi, miyamoto, eric, olaf};
		Fighter fighter1 = fighters[Math.toIntExact(Math.round(Math.random()*8))];
		Fighter fighter2 = fighters[Math.toIntExact(Math.round(Math.random()*8))];
		
		try {
		    Fight fight1 = new Fight(fighter1, fighter2);
//			Fight fight2 = new Fight(musashi, miyamoto);
		}catch (Exception e) {
		      System.out.println("Fight aborted.");
		    }
	}

}
