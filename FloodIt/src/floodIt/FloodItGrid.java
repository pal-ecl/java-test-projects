package floodIt;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;


public class FloodItGrid {
	
		private FloodItBox[] floodItBoxes;
		private int width;
		private int height;
		private int nbOfColors;
		private int playCount;
		private long maxMoves;
		private ArrayList<Color> colorsArray; 

		public FloodItGrid(int w, int h, int nC){
			nbOfColors = nC;
			width = w;
			height = h;
			
			//We create the array of possible colors
			colorsArray = new ArrayList<Color>();
			colorsArray.add(Color.GREEN);
			colorsArray.add(Color.ORANGE);
			colorsArray.add(Color.BLUE);
			colorsArray.add(Color.MAGENTA);
			colorsArray.add(Color.PINK);
			colorsArray.add(Color.CYAN);
			colorsArray.add(Color.YELLOW);
			colorsArray.add(Color.BLACK);
			
			//we initialize the number of play at 0
			playCount = 0;
			
			maxMoves = Math.round(width * 2.375);
			System.out.println(maxMoves);
			
			//we create the boxes
			floodItBoxes = new FloodItBox[width * height];
			int indexCount = 0;
			for (int indexH = 0; indexH < height; indexH++){
				for (int indexW = 0; indexW < width; indexW++){
					//we shuffle the colors in the boxes for the begining of the game
					int randomN = (int)(Math.random() * nbOfColors);
					
					floodItBoxes[indexCount]= new FloodItBox(indexCount, indexW, indexH, colorsArray.get(randomN));
					indexCount++;					
				}
			}
		}
		
		public void chooseColor(int indexClicked){
			//we store the new color to apply
			Color newColor;
			//we store the color of the first box as a reference
			Color firstColor;
			
			
			newColor = floodItBoxes[indexClicked].getColor();
			firstColor = floodItBoxes[0].getColor();
			//we call the switchColor method on the first box if they are different
			if (newColor != firstColor){
				switchColor(0, newColor, firstColor);
				//we increment the play counter
				playCount++;
				//we check if game is win or not
				win();
				lose();
			}
		}
		
		public void switchColor(int indexToChange, Color newColor, Color firstColor){
				if (floodItBoxes[indexToChange].getColor() == firstColor){
					floodItBoxes[indexToChange].setColor(newColor);
					//if the current box was changed, we check the boxes around
					if((indexToChange + 1) % width != 0){
					trySwitchColor(indexToChange + 1, newColor, firstColor);
					}
					if (indexToChange % width != 0){
					trySwitchColor(indexToChange - 1, newColor, firstColor);
					}
					trySwitchColor(indexToChange + getWidth(), newColor, firstColor);
					trySwitchColor(indexToChange - getWidth(), newColor, firstColor);
				}
		}
		
		public void trySwitchColor(int indexToChange, Color newColor, Color firstColor){
			try{
				switchColor(indexToChange, newColor, firstColor);
			}
			catch (Exception e){				
			}			
		}
		
		public void lose(){
			if (playCount > maxMoves){
				JOptionPane.showMessageDialog(null,"Sorry, you lose :'(");
			}
		}
				
		public void win(){
			Color firstColor;
			int index;
			boolean win;
			
			win = true;
			index = 1;
			firstColor = floodItBoxes[0].getColor();
			while (index < floodItBoxes.length && win){
				if (!(floodItBoxes[index].getColor() == firstColor)){
					win = false;
				}
			index++;
			}
			if (win == true){
				JOptionPane.showMessageDialog(null,"You Win in " + playCount + " moves.");
			}
		}
		

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getNbOfColors() {
			return nbOfColors;
		}

		public void setNbOfColors(int nbOfColors) {
			this.nbOfColors = nbOfColors;
		}

		public int getPlayCount() {
			return playCount;
		}

		public void setPlayCount(int playCount) {
			this.playCount = playCount;
		}

		public FloodItBox[] getFloodItBoxes() {
			return floodItBoxes;
		}
		
		public FloodItBox getFloodItBox(int floodItBox){
			return floodItBoxes[floodItBox];
		}

		public long getMaxMoves() {
			return maxMoves;
		}

		public ArrayList<Color> getColorsArray() {
			return colorsArray;
		}

}
