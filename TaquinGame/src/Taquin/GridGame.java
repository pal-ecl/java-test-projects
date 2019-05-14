package Taquin;

import java.util.Arrays;
import java.util.Random;

import org.omg.CORBA.PUBLIC_MEMBER;

public class GridGame {
	Box[] boxes;
	int column;
	int row;
	private final Random rand = new Random();

	public GridGame(int column, int row) {
		this.setColumn(column);
		this.setRow(row);
		this.setBoxes(new Box[this.getColumn() * this.getRow()]);
		int count = 1;
		for (int rowCount = 0; rowCount < this.getRow(); rowCount++) {
			for (int columnCount = 0; columnCount < this.getColumn(); columnCount++){
				this.getBoxes()[count-1] = new Box(count, columnCount, rowCount);
				count++;
			}
		}
		this.displayGame();
		this.shufflePossible(100);
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

		if (placeIndex(indexClicked)[0] != true){	
			if (this.getBoxes()[indexClicked+1].getValue() == 16){
				this.getBoxes()[indexClicked].setValue(16);
				this.getBoxes()[indexClicked+1].setValue(temp);
			}
		}
		if (placeIndex(indexClicked)[1] != true){		
			if (this.getBoxes()[indexClicked-1].getValue() == 16){
				this.getBoxes()[indexClicked].setValue(16);
				this.getBoxes()[indexClicked-1].setValue(temp);
			}
		}
		if (placeIndex(indexClicked)[2] != true){
			if (this.getBoxes()[indexClicked+this.getColumn()].getValue() == 16){
				this.getBoxes()[indexClicked].setValue(16);
				this.getBoxes()[indexClicked+this.getColumn()].setValue(temp);
			}
		}
		if (placeIndex(indexClicked)[3] != true){
			if (this.getBoxes()[indexClicked-this.getColumn()].getValue() == 16){
				this.getBoxes()[indexClicked].setValue(16);
				this.getBoxes()[indexClicked-this.getColumn()].setValue(temp);
			}
		}		
	}

	private boolean[] placeIndex(int indexClicked){
		boolean[] placeIndex = new boolean[4];
		if (indexClicked > (this.getColumn() * this.getRow()) - 2){
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

}
