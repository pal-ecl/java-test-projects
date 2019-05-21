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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Font;

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
		frame.getContentPane().setForeground(Color.ORANGE);
		frame.setBounds(100, 100, 630, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnTaquin = new JButton("Shuffle !");
		btnTaquin.setBackground(Color.ORANGE);
		btnTaquin.setForeground(Color.BLACK);
		btnTaquin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				taquin.shufflePossible(150);
			}
		});
		btnTaquin.setBounds(154, 11, 92, 25);
		frame.getContentPane().add(btnTaquin);
		
		JButton btnTaquin_1 = new JButton("Taquin");
		btnTaquin_1.setEnabled(false);
		btnTaquin_1.setBackground(Color.ORANGE);
		btnTaquin_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnTaquin_1.setForeground(Color.RED);
		btnTaquin_1.setBounds(63, 11, 92, 25);
		frame.getContentPane().add(btnTaquin_1);
		
		for (int i = 0; i < taquin.getBoxes().length; i++){
			final int j = i;
			int x = 55 + taquin.getBox(i).getXGrid() * 50;
			int y = 55 + taquin.getBox(i).getYGrid() * 50;	
			taquin.getBox(i).setBounds(x, y, 50, 50);
			taquin.getBox(i).setBackground(Color.ORANGE);
			taquin.getBox(i).setForeground(Color.BLUE);
			taquin.getBox(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					taquin.changePlace(j);
					taquin.winGame();
				}
			});
			frame.getContentPane().add(taquin.getBox(i));
		}
	}
}
