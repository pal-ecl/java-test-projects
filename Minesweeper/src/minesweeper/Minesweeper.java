package minesweeper;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import javax.swing.JLabel;

public class Minesweeper {

	private JFrame frame;
	private MinesGrid demineur;
	private JTextField textField;
	private JTextField textField_1;
	private static int timePassingBy;
	private static JLabel timerDisplay;
	private static Timer timer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Minesweeper window = new Minesweeper();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void createTimer(){
		  int delay = 1000; //milliseconds
		  ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				timePassingBy++;
				timerSetText(timePassingBy);
			}
		  };
		  timer = new Timer(delay, taskPerformer);
		  timer.start();	
	}

	/**
	 * Create the application.
	 */
	public Minesweeper() {
		demineur = new MinesGrid(8, 8, 10);
		frame = new JFrame();
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.getContentPane().setForeground(Color.ORANGE);
		frame.setBounds(100, 100, 630, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Minesweeper !");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int gameSize = 8;
				int gameMines = 10;
				try{
					if (!Minesweeper.isInteger(textField_1.getText()) || !Minesweeper.isInteger(textField.getText())){
						throw new NotNumberException("Not a number");
					}
					gameSize = Integer.parseInt(textField.getText());
					gameMines = Integer.parseInt(textField_1.getText());
				}
				catch(NotNumberException message){
					JOptionPane.showMessageDialog(null,"Please use Number only");				
				}
				frame.getContentPane().removeAll();				
				if (timePassingBy > 0){
					timer.stop();
				}
				timePassingBy = 0;
				demineur = new MinesGrid(gameSize, gameSize, gameMines);
				initialize();
				frame.revalidate();
				frame.repaint();
			}
		});
		btnNewButton.setBackground(Color.PINK);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(0, 69, 199, 34);

		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton(Integer.toString(demineur.getNbFlags()));
		btnNewButton_1.setForeground(Color.RED);
		btnNewButton_1.setBounds(209, 0, 117, 34);
		frame.getContentPane().add(btnNewButton_1);

		textField = new JTextField();
		textField.setBounds(74, 0, 125, 34);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(113, 34, 86, 34);
		frame.getContentPane().add(textField_1);

		JLabel lblNewLabel = new JLabel("Game size :");
		lblNewLabel.setBounds(0, 0, 75, 34);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNumberOfMines = new JLabel("Number of mines : ");
		lblNumberOfMines.setBounds(0, 34, 117, 24);
		frame.getContentPane().add(lblNumberOfMines);
		
		timerDisplay = new JLabel("0");
		timerDisplay.setBounds(336, 5, 86, 24);
		frame.getContentPane().add(timerDisplay);


		for (int i = 0; i < demineur.getBoxes().length; i++){
			final int j = i;
			int x = 200 + demineur.getBox(i).getIndexWidth() * 40;
			int y = 45 + demineur.getBox(i).getIndexHeight() * 40;	
			demineur.getBox(i).setBounds(x, y, 40, 40);
			demineur.getBox(i).setForeground(Color.BLUE);
			demineur.getBox(i).setFont(new Font("Tahoma", Font.PLAIN, 8));
			frame.getContentPane().add(demineur.getBox(i));
			demineur.getBox(i).addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent event) {
					if (SwingUtilities.isLeftMouseButton(event)){
						demineur.leftPlay(j);
					}
					else if (SwingUtilities.isRightMouseButton(event)){
						demineur.rightPlay(j);
						btnNewButton_1.setText(Integer.toString(demineur.getNbFlags()));
					}
				}
			});

		}

	}
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
	
	public static void timerSetText(int time){
		timerDisplay.setText(Integer.toString(time));
	}
}
