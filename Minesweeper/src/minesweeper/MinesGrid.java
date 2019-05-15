package minesweeper;

import java.text.DateFormat;
import java.util.Date;

import javax.swing.JOptionPane;


public class MinesGrid {
	Box[] boxes;
	private int width;
	private int height;
	private int nbMines;
	private int nbFlags;
	private boolean firstMove = true;
	private Date dateStart;
	private Date dateStop;

	public MinesGrid(int w, int h, int nM){
		width = w;
		height = h;
		nbMines = nM;
		boxes = new Box[width * height];
		int indexCount = 0;
		for (int indexH = 0; indexH < height; indexH++){
			for (int indexW = 0; indexW < width; indexW++){
				boxes[indexCount]= new Box(indexCount, indexW, indexH);
				indexCount++;
			}
		}
		nbFlags = nbMines;
	}
	
	public void shuffleMines(int indexClicked){
		int minesToSet = nbMines;
		while (minesToSet > 0){
//			int randomN = Math.toIntExact(Math.round(Math.random() * (boxes.length-1)));
			int randomN = (int)(Math.random() * boxes.length);
			if (!getBox(randomN).isMine() && indexClicked != randomN){
				getBox(randomN).setMine(true);
				System.out.println(getBox(randomN).getIndexBoxes());
				minesToSet--;
			}
		}
	}

	public void win(){
		int index = 0;
		boolean win = true;
		if (nbFlags == 0){
			while (index < boxes.length && win){
				if (getBox(index).isMine() && getBox(index).getValue() != 3){
					win = false;
				}
				index++;
			}
			if (win){
				JOptionPane.showMessageDialog(null,"You win ! in " + ((dateStop.getTime() - dateStart.getTime())/1000) + " seconds.");
			}
		}
	}

	public void leftPlay(int indexClicked){
		if(getBox(indexClicked).getValue() != 3){
			if (getBox(indexClicked).isMine()){
				getBox(indexClicked).changeValue(1);
				dateStop = new Date();
				JOptionPane.showMessageDialog(null,"You lose ! in " + ((dateStop.getTime() - dateStart.getTime())/1000) + " seconds.");
			}else{
				if (isFirstMove()){
					shuffleMines(indexClicked);
					setNbFlags(getNbMines());
					Minesweeper.createTimer();
					dateStart = new Date();
					setFirstMove(false);
				}
				if (getBox(indexClicked).getValue() == 0){
					getBox(indexClicked).changeValue(2);
					displayNextMines(indexClicked);
				}
			}
		}
	}
	
	public void rightPlay(int indexClicked){
		if(getBox(indexClicked).getValue() != 2){
			if(getBox(indexClicked).getValue() != 3){
				if (getNbFlags() > 0){
					getBox(indexClicked).changeValue(3);
					setNbFlags(getNbFlags() - 1);
					win();
				}
			} else {
				getBox(indexClicked).changeValue(0);
				setNbFlags(getNbFlags() + 1);						
			}
		}
	}
	
	public void displayNextMines(int indexClicked){
		boolean firstLine = false;
		boolean lastLine = false;
		boolean firstColumn = false;
		boolean lastColumn = false;
		int NextMines = 0;

		if(indexClicked < width){
			firstLine = true;
		}

		if(indexClicked > (boxes.length -1) - width ){
			lastLine = true;
		}

		if(indexClicked % width == 0){
			firstColumn = true;
		}

		if((indexClicked + 1) % width == 0){
			lastColumn = true;
		}

		if (!lastColumn){
			if (getBox(indexClicked + 1).isMine()){
				NextMines++;
			}
		}
		if (!firstColumn){
			if (getBox(indexClicked - 1).isMine()){
				NextMines++;
			}
		}
		if (!lastLine){
			if (getBox(indexClicked + width).isMine()){
				NextMines++;
			}
		}
		if (!lastLine && !lastColumn){
			if (getBox(indexClicked + width + 1).isMine()){
				NextMines++;
			}
		}
		if (!lastLine && !firstColumn){		
			if (getBox(indexClicked + width - 1).isMine()){
				NextMines++;
			}
		}
		if (!firstLine){
			if (getBox(indexClicked - width).isMine()){
				NextMines++;
			}
		}
		if (!firstLine && !firstColumn){
			if (getBox(indexClicked - width - 1).isMine()){
				NextMines++;
			}
		}
		if (!firstLine && !lastColumn){
			if (getBox(indexClicked - width + 1).isMine()){
				NextMines++;
			}
		}
		getBox(indexClicked).setText(Integer.toString(NextMines));

		if (NextMines == 0){
			if (!lastColumn){
				leftPlay(indexClicked + 1);
			}
			if (!firstColumn){
				leftPlay(indexClicked - 1);
			}
			if (!lastLine){
				leftPlay(indexClicked + width);
			}
			if (!firstLine){
				leftPlay(indexClicked - width);
			}
			if (!lastLine && !lastColumn){
				leftPlay(indexClicked + width + 1);
			}
			if (!lastLine && !firstColumn){	
				leftPlay(indexClicked + width - 1);
			}
			if (!firstLine && !firstColumn){
				leftPlay(indexClicked - width -1);
			}
			if (!firstLine && !lastColumn){
				leftPlay(indexClicked - width + 1);
			}		
		}
	}



	public int getNbMines() {
		return nbMines;
	}

	public Box[] getBoxes() {
		return boxes;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Box getBox(int box){
		return boxes[box];
	}

	public int getNbFlags() {
		return nbFlags;
	}

	public void setNbFlags(int nbFlags) {
		this.nbFlags = nbFlags;
	}

	public boolean isFirstMove() {
		return firstMove;
	}

	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
	

}
