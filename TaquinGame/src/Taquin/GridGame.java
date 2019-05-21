package Taquin;

import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.plaf.IconUIResource;


public class GridGame {
	Box[] boxes;
	Box[] modelBoxes;
	int blankPawnIndex;
	int column;
	int row;
	private final Random rand = new Random();

	public GridGame(int column, int row) {
		this.setColumn(column);
		this.setRow(row);
		this.setBoxes(new Box[this.getColumn() * this.getRow()]);
		this.modelBoxes = new Box[this.getColumn() * this.getRow()];
		int count = 1;
		for (int rowCount = 0; rowCount < this.getRow(); rowCount++) {
			for (int columnCount = 0; columnCount < this.getColumn(); columnCount++){
				this.getBoxes()[count-1] = new Box(count, columnCount, rowCount);
				this.modelBoxes[count-1] = new Box(count, columnCount, rowCount);
				count++;
			}
		}
		blankPawnIndex = this.getColumn() * this.getRow() - 1;
		this.displayGame();
	}

	public void shuffle() {
		int pawnToInvert = this.getColumn() * this.getRow() - 1;
		while (pawnToInvert > 1) {
			int randomPawn = rand.nextInt(pawnToInvert--);
			int intermediate = this.getBoxes()[randomPawn].getValue();
			this.getBoxes()[randomPawn].setValue(this.getBoxes()[pawnToInvert].getValue());
			this.getBoxes()[pawnToInvert].setValue(intermediate);
		}
	}

	public void shufflePossible(int numberOfShuffle) {
		for (int n = 0; n < numberOfShuffle; n++){		
			int index16 = -99;
			int i = 0;

			while ( i < this.getBoxes().length && index16 == -99){
				if (this.getBoxes()[i].getValue() == 16){
					index16 = i;
				}
				i++;
			}

			int randomNextPawn = rand.nextInt(4);
			if (randomNextPawn == 0 && index16 < (this.getColumn() * this.getRow()) - 1){
				this.changePlace(index16 + 1);
			} else if (randomNextPawn == 1 && index16 > 0) {
				this.changePlace(index16 - 1);
			} else if (randomNextPawn == 2 && index16 < (this.getColumn() * this.getRow()) - this.getColumn()) {
				this.changePlace(index16 + this.getColumn());
			} else if (randomNextPawn == 3 && index16 >= this.getColumn()) {
				this.changePlace(index16 - this.getColumn());
			}
		}
	}
	
	public Box getBox(int a){
		return boxes[a];
	}

	public void displayGame() {
		String toDisplayShuffled = "";
		int count = 0;
		for (int rowCount = 0; rowCount < this.getRow(); rowCount++) {
			for (int columnCount = 0; columnCount < this.getColumn(); columnCount++) {
				toDisplayShuffled += this.getBoxes()[count].getValue() + "\t";
				count++;
			}
			System.out.println(toDisplayShuffled);
			toDisplayShuffled = "";
		}
	}

	public void changePlace(int indexClicked){
		int temp = this.getBoxes()[indexClicked].getValue();
		System.out.println("1: " + this.getBoxes()[indexClicked].getValue());

		//reverse row
		for (int iRr = getColumn() - 2; iRr >= 0; iRr-- ){
			boolean flagException = false;
			if (placeIndex(indexClicked + iRr)[0] != true){
				if (this.getBoxes()[indexClicked + (iRr + 1)].getValue() == 16) {
					System.out.println("cas 1");
					int vIRr = 0;
					while (vIRr <= iRr && flagException != true){
						if ((getBlankPawn() - vIRr) % this.getColumn() == 0){
							flagException = true;
						}
						vIRr++;
					}if (flagException != true){
					this.reverseRow(indexClicked + iRr);
					}
				}
			}
		}
//		if (placeIndex(indexClicked)[0] != true){	
//			if (this.getBoxes()[indexClicked+1].getValue() == 16 && getBlankPawn() % this.getColumn() != 0){
//				this.reverseRow(indexClicked, temp);
//			}
//		}
//		if (placeIndex(indexClicked + 1)[0] != true){	
//			if (this.getBoxes()[indexClicked + 2].getValue() == 16 && (getBlankPawn() % this.getColumn() != 0) && ((getBlankPawn() - 1) % this.getColumn() != 0)){
//				this.reverseRow(indexClicked + 1, temp + 1);
//				this.reverseRow(indexClicked, temp);
//			}
//		}
//		
//		if (placeIndex(indexClicked + 2)[0] != true){	
//			if (this.getBoxes()[indexClicked + 3].getValue() == 16 && (getBlankPawn() % this.getColumn() != 0) && ((getBlankPawn() - 1) % this.getColumn() != 0) && ((getBlankPawn() - 2) % this.getColumn() != 0)){
//				this.reverseRow(indexClicked + 2, temp + 2);
//				this.reverseRow(indexClicked + 1, temp + 1);
//				this.reverseRow(indexClicked, temp);
//			}
//		}
		
		//forward row
		for (int iFR = getColumn() - 2; iFR >= 0; iFR-- ){
			boolean flagException = false;
			if (placeIndex(indexClicked - iFR)[1] != true){
				if (this.getBoxes()[indexClicked - (iFR + 1)].getValue() == 16) {
					System.out.println("cas 2");
					int vIFR = 0;
					while (vIFR <= iFR && flagException != true){
						if ((indexClicked - vIFR) % this.getColumn() == 0){
							flagException = true;
						}
						vIFR++;
					}if (flagException != true){
					this.forwardRow(indexClicked - iFR);
					}
				}
			}
		}
		
//		for (int iFr = getColumn() - 1; iFr >= 0; iFr-- ){
//			boolean flagException = false;
//			if (placeIndex(indexClicked - iFr)[1] != true){
//				if (this.getBoxes()[indexClicked - (iFr + 1)].getValue() == 16) {
//					int vIFr = 0;
//					while (vIFr < iFr && flagException != true){
//						if ((indexClicked - vIFr) % this.getColumn() == 0){
//							flagException =true;
//						}
//						vIFr++;
//					}if (flagException != true){
//					this.forwardRow(indexClicked - (iFr), temp - (iFr));
//					}
//				}
//			}
//		}
//		if (placeIndex(indexClicked)[1] != true){		
//			if (this.getBoxes()[indexClicked-1].getValue() == 16 && (indexClicked) % this.getColumn() != 0){
//				this.forwardRow(indexClicked, temp);
//			}
//		}
//		if (placeIndex(indexClicked - 1)[1] != true){		
//			if (this.getBoxes()[indexClicked - 2].getValue() == 16 && ((indexClicked) % this.getColumn() != 0) && ((indexClicked - 1) % this.getColumn() != 0)){
//				this.forwardRow(indexClicked -1 , temp - 1);
//				this.forwardRow(indexClicked, temp);
//			}
//		}
//		if (placeIndex(indexClicked - 2)[1] != true){		
//			if (this.getBoxes()[indexClicked - 3].getValue() == 16 && ((indexClicked) % this.getColumn() != 0) && ((indexClicked - 2) % this.getColumn() != 0)){
//				this.forwardRow(indexClicked -2 , temp - 2);
//				this.forwardRow(indexClicked -1 , temp - 1);
//				this.forwardRow(indexClicked, temp);
//			}
//		}
		
		//forward column
		for (int iFc = getColumn() - 2; iFc >= 0; iFc-- ){
			if (placeIndex(indexClicked + (getColumn() * iFc))[2] != true){
				if (this.getBoxes()[indexClicked + this.getColumn() * (iFc + 1)].getValue() == 16){
					System.out.println("cas 3");
					this.forwardColumn(indexClicked + this.getColumn() * (iFc));
				}
			}
		}
		
//		if (placeIndex(indexClicked)[2] != true){
//			if (this.getBoxes()[indexClicked+this.getColumn()].getValue() == 16){
//				this.forwardColumn(indexClicked, temp);
//			}
//		}
//		if (placeIndex(indexClicked + getColumn())[2] != true){
//			if (this.getBoxes()[indexClicked + this.getColumn() * 2].getValue() == 16){
//				this.forwardColumn(indexClicked + getColumn(), temp + getColumn());
//				this.forwardColumn(indexClicked, temp);
//			}
//		}
//		if (placeIndex(indexClicked + getColumn() * 2)[2] != true){
//			if (this.getBoxes()[indexClicked + this.getColumn() * 3].getValue() == 16){
//				this.forwardColumn(indexClicked + getColumn() * 2, temp + getColumn() * 2);
//				this.forwardColumn(indexClicked + getColumn(), temp + getColumn());
//				this.forwardColumn(indexClicked, temp);
//			}
//		}
		

		//reverse column
		for (int iRc = getColumn() - 2; iRc >= 0; iRc-- ){
			if (placeIndex(indexClicked - (getColumn() * iRc))[3] != true){
				if (this.getBoxes()[indexClicked - this.getColumn() * (iRc + 1)].getValue() == 16){
					System.out.println("cas 4");
					this.reverseColumn(indexClicked - this.getColumn() * iRc);
				}
			}
		}
//		for (int iRc = getColumn() - 1; iRc >= 0; iRc-- ){
//			if (placeIndex(indexClicked - (getColumn() * iRc))[3] != true){
//				if (this.getBoxes()[indexClicked - this.getColumn() * (iRc + 1)].getValue() == 16){
//					System.out.println("cas 4");
//					this.reverseColumn(indexClicked - this.getColumn() * (iRc));
//				}
//			}
//		}
//		if (placeIndex(indexClicked)[3] != true){
//			if (this.getBoxes()[indexClicked-this.getColumn()].getValue() == 16){
//				this.reverseColumn(indexClicked, temp);
//			}
//		}
//		if (placeIndex(indexClicked - getColumn())[3] != true){
//			if (this.getBoxes()[indexClicked - this.getColumn() * 2 ].getValue() == 16){
//				this.reverseColumn(indexClicked  - getColumn(), temp - getColumn());
//				this.reverseColumn(indexClicked, temp);
//			}
//		}
//		if (placeIndex(indexClicked - getColumn() * 2)[3] != true){
//			if (this.getBoxes()[indexClicked - this.getColumn() * 3 ].getValue() == 16){
//				this.reverseColumn(indexClicked  - getColumn() * 2, temp - getColumn() * 2);
//				this.reverseColumn(indexClicked  - getColumn(), temp - getColumn());
//				this.reverseColumn(indexClicked, temp);
//			}
//		}
		System.out.println("2: " + this.getBoxes()[indexClicked].getValue());
	}
	
	private void forwardRow(int indexClicked){
		this.getBoxes()[indexClicked-1].setValue(this.getBoxes()[indexClicked].getValue());
		this.getBoxes()[indexClicked].setValue(16);
		this.setBlankPawn(indexClicked);

	}
	
	private void reverseRow(int indexClicked){
		this.getBoxes()[indexClicked+1].setValue(this.getBoxes()[indexClicked].getValue());
		this.getBoxes()[indexClicked].setValue(16);
		this.setBlankPawn(indexClicked);
		
	}
	
	private void forwardColumn(int indexClicked){
		this.getBoxes()[indexClicked + this.getColumn()].setValue(this.getBoxes()[indexClicked].getValue());
		this.getBoxes()[indexClicked].setValue(16);
		this.setBlankPawn(indexClicked);
		
	}
	
	private void reverseColumn(int indexClicked){
		this.getBoxes()[indexClicked-this.getColumn()].setValue(this.getBoxes()[indexClicked].getValue());
		this.getBoxes()[indexClicked].setValue(16);
		this.setBlankPawn(indexClicked);
		
	}
	
	public void winGame(){
		boolean flagWin = true;
		for (int pawn = 0; pawn < this.getColumn() * this.getRow(); pawn++){
			if (this.getBoxes()[pawn].getValue() != this.getModelBoxes()[pawn].getValue()){
				
				flagWin = false;
			}
		}
		if (flagWin == true) {
			JOptionPane.showMessageDialog(null,"You win !");
		}
	}

	private boolean[] placeIndex(int indexClicked){
		boolean[] placeIndex = new boolean[4];
		if (indexClicked > (this.getColumn() * this.getRow()) - 2) {
			placeIndex[0] = true;
		}
		if (indexClicked < 1){
			placeIndex[1] = true;
		}
		if (indexClicked > (this.getColumn() * this.getRow()) - this.getColumn() - 1){
			placeIndex[2] = true;
		}
		if (indexClicked < 4){
			placeIndex[3] = true;
		}
		return placeIndex;
	}


	public Box[] getBoxes() {
		return boxes;
	}

	public void setBoxes(Box[] boxes) {
		this.boxes = boxes;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public Box[] getModelBoxes() {
		return modelBoxes;
	}

	public int getBlankPawn() {
		return blankPawnIndex;
	}

	public void setBlankPawn(int blankPawnIndex) {
		this.blankPawnIndex = blankPawnIndex;
	}

}
