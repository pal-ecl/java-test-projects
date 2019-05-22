package lifeGame;

import java.awt.Color;

import javax.swing.JButton;

public class LifeGameBox extends JButton implements Cloneable {
	private boolean inLife;
	private boolean colored;
	private int boxIndex;
	private int indexW;
	private int indexH;
	
	public LifeGameBox(int index, int w, int h){
		boxIndex = index;
		indexW = w;
		indexH = h;
	}

	public boolean isInLife() {
		return inLife;
	}

	public void setInLife(boolean inLife) {
		this.inLife = inLife;
		setColored(inLife);
	}

	public boolean isColored() {
		return colored;
	}

	public void setColored(boolean colored) {
		this.colored = colored;
		if (colored){
			this.setBackground(Color.BLACK);
		}
		else {
			this.setBackground(Color.LIGHT_GRAY);
		}
	}

	public int getBoxIndex() {
		return boxIndex;
	}
	
	
	public int getIndexW() {
		return indexW;
	}

	public int getIndexH() {
		return indexH;
	}

	@Override
    protected LifeGameBox clone() throws CloneNotSupportedException {
        LifeGameBox clone = null;
        try
        {
            clone = (LifeGameBox) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new RuntimeException(e);
        }
        return clone;
    }
}
