package Taquin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

public class Main {

	private JFrame frame;
	private GridGame taquin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		taquin = new GridGame(4, 4);
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 630, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		for (int i = 0; i < taquin.getBoxes().length; i++){
			final int j = i;
			int x = 55 + taquin.getBox(i).getX() * 50;
			int y = 55 + taquin.getBox(i).getY() * 50;	
			taquin.getBox(i).setBounds(x, y, 50, 50);
			taquin.getBox(i).setBackground(Color.ORANGE);
			taquin.getBox(i).setForeground(Color.BLUE);
			taquin.getBox(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					taquin.changePlace(j);
				}
			});
			frame.getContentPane().add(taquin.getBox(i));
		}
	}
}
