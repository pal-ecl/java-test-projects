package floodIt;

import java.awt.Color;

import javax.swing.JButton;

public class FloodItBox extends JButton {
	
	private int indexBoxes;
	private int indexW;
	private int indexH;
	private Color color;
	
	public FloodItBox(int iBoxes, int iW, int iH, Color col){
		indexBoxes = iBoxes;
		indexW = iW;
		indexH = iH;
		color = col;
		this.setColor(col);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color col) {
		this.color = col;
		this.setBackground(color);
	}

	public int getIndexBoxes() {
		return indexBoxes;
	}

	public int getIndexW() {
		return indexW;
	}

	public int getIndexH() {
		return indexH;
	}	
}