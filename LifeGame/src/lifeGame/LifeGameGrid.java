package lifeGame;

import java.util.ArrayList;
import java.util.Iterator;


public class LifeGameGrid {
	private int width;
	private int height;
	private int generationCount;
	private ArrayList<LifeGameBox> lifeGameBoxes;
	private ArrayList<LifeGameBox> cloneLifeGameBoxes;
	int boxesAlive;
	

	public LifeGameGrid(int w, int h){
		width = w;
		height = h;
		
		int indexCount;
		
		lifeGameBoxes = new ArrayList<LifeGameBox>();
		indexCount = 0;
		
		for (int indexH = 0; indexH < height; indexH++){
			for (int indexW = 0; indexW < width; indexW++){
				lifeGameBoxes.add(new LifeGameBox(indexCount, indexW, indexH));
				indexCount++;					
			}
		}		
	}
	
	public void displayConsoleGrid(){
		int countIndex;
		
		countIndex = 0;
		
		for (int indexH = 0; indexH < height; indexH++){
			System.out.print("|");
			for (int indexW = 0; indexW < width; indexW++){
				if (lifeGameBoxes.get(countIndex).isInLife()){
					System.out.print(" * ");
				}
				else {
					System.out.print(" _ ");
				}
				countIndex++;
			}
			System.out.println("|");
		}
	}
	
	public void forwardTime(){
		int gridSize;
		
		gridSize = lifeGameBoxes.size();
		
		try {
			cloneGrid();
		}
		catch(Exception e){
		}
			
		for (int gridIndex = 0; gridIndex < gridSize; gridIndex++){
			boolean boxIsAlive;
			
			boxIsAlive = cloneLifeGameBoxes.get(gridIndex).isInLife();
			boxesAlive = 0;
			
			if((gridIndex + 1) % width != 0){
				tryCheck(gridIndex + 1);
			}
			if(gridIndex % width != 0){ 
				tryCheck(gridIndex - 1);
			}
			tryCheck(gridIndex - width);
			tryCheck(gridIndex - width - 1);
			tryCheck(gridIndex - width + 1);
			tryCheck(gridIndex + width);
			tryCheck(gridIndex + width + 1);
			tryCheck(gridIndex + width - 1);
			
			if (boxIsAlive){
				if(boxesAlive > 3 || boxesAlive < 2){
					lifeGameBoxes.get(gridIndex).setInLife(false);					
				}
			}
			else {
				if (boxesAlive == 3){
					lifeGameBoxes.get(gridIndex).setInLife(true);
				}
			}
		}
		generationCount++;
	}
	
	private void cloneGrid() throws CloneNotSupportedException{
		int gridSize;
		
		gridSize = lifeGameBoxes.size();
		cloneLifeGameBoxes = new ArrayList<LifeGameBox>();
		
		for ( int gridIndex = 0; gridIndex < gridSize; gridIndex++ ){
			cloneLifeGameBoxes.add(lifeGameBoxes.get(gridIndex).clone());
		}
	}
	
	private void tryCheck(int boxToCheck){
		try{
			if (cloneLifeGameBoxes.get(boxToCheck).isInLife()){
				boxesAlive++;
			}
		}
		catch(Exception e){
			
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getGenerationCount() {
		return generationCount;
	}

	public ArrayList<LifeGameBox> getLifeGameBoxes() {
		return lifeGameBoxes;
	}
	
	
	
}
