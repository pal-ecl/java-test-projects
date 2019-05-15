package minesweeper;
import java.awt.Color;

import javax.swing.JButton;

public class Box extends JButton {
	
	private int indexBoxes;
	private int indexW;
	private int indexH;
	private int value;
	private boolean mine;
	private boolean flag;
	
	public Box(int iBoxes, int iW, int iH){
		indexBoxes = iBoxes;
		indexW = iW;
		indexH = iH;
		value = 0;
	}
	

	public int getIndexBoxes() {
		return indexBoxes;
	}


	public int getIndexWidth() {
		return indexW;
	}


	public int getIndexHeight() {
		return indexH;
	}


	public int getValue() {
		return value;
	}

	public void changeValue(int val) {
		this.value = val;
		if(value == 0){
			this.setText("");
			this.setBackground(null);
		}else if (value == 1){
			this.setText("M");
			this.setBackground(Color.RED);
		} else if(value == 2) {
			this.setText("");
			this.setBackground(Color.LIGHT_GRAY);
		} else if (value == 3){
			this.setText("F");
			this.setBackground(Color.PINK);
		}
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}	
}
