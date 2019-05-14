package Taquin;

import java.awt.Color;

import javax.swing.JButton;

public class Box extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int value;
	private int x;
	private int y;

	public Box(int value, int x, int y){
		super();
		this.setValue(value);
		this.setX(x);
		this.setY(y);
		this.setText(Integer.toString(this.getValue()));
		if (this.getValue() == 16){
			this.setVisible(false);
		}
	}


	public void setValue(int value) {
		this.value = value;
		this.setText(Integer.toString(value));
		if (this.getValue() == 16){
			this.setVisible(false);
		}else {
			this.setVisible(true);
		}
		this.revalidate();
		this.repaint();

	}

	public int getValue() {
		return this.value;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}

}
