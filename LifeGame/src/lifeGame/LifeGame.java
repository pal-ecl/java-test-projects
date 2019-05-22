package lifeGame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextPane;


import java.awt.Color;
import javax.swing.JTextField;


public class LifeGame {

	private JFrame frame;
	private LifeGameGrid lifeGame;
	private int lifeGameSize;
	JTextPane jTPCounter;
	private JTextField CustomG;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LifeGame window = new LifeGame();
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
	public LifeGame() {
		lifeGame = new LifeGameGrid(50, 30);
		frame = new JFrame();
		frame.setBounds(100, 100, 1300, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		lifeGameSize = lifeGame.getLifeGameBoxes().size();
		
		for (int i = 0; i < lifeGameSize; i++){
			final int j = i;
			int x = 200 + lifeGame.getLifeGameBoxes().get(i).getIndexW() * 20;
			int y = 45 + lifeGame.getLifeGameBoxes().get(i).getIndexH() * 20;	
			lifeGame.getLifeGameBoxes().get(i).setBounds(x, y, 20, 20);
			lifeGame.getLifeGameBoxes().get(i).setColored(false);
			lifeGame.getLifeGameBoxes().get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(lifeGame.getLifeGameBoxes().get(j).isColored()){
						lifeGame.getLifeGameBoxes().get(j).setInLife(false);
					}else {
						lifeGame.getLifeGameBoxes().get(j).setInLife(true);
					}
				}
			});
			frame.getContentPane().add(lifeGame.getLifeGameBoxes().get(i));
		}
		
		JButton btnLifeGame = new JButton("Life Game !");
		btnLifeGame.setBackground(Color.ORANGE);
		btnLifeGame.setBounds(10, 10, 120, 25);
		btnLifeGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();				
				lifeGame = new LifeGameGrid(50, 30);
				initialize();
				frame.revalidate();
				frame.repaint();
			}
		});
		frame.getContentPane().add(btnLifeGame);
		
		JTextPane txtpnGenerations = new JTextPane();
		txtpnGenerations.setText("Generations :");
		txtpnGenerations.setBounds(10, 35, 80, 20);
		frame.getContentPane().add(txtpnGenerations);
		
		jTPCounter = new JTextPane();
		jTPCounter.setBounds(90, 35, 40, 20);
		frame.getContentPane().add(jTPCounter);
		
		JButton btn1Generation = new JButton("1 generation");
		btn1Generation.setBackground(Color.PINK);
		btn1Generation.setBounds(10, 65, 120, 25);
		btn1Generation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lifeGame.forwardTime();
				jTPCounter.setText(Integer.toString(lifeGame.getGenerationCount()));
			}
		});
		frame.getContentPane().add(btn1Generation);
		
		JButton btn10Generations = new JButton("10 generations");
		btn10Generations.setBackground(Color.PINK);
		btn10Generations.setBounds(10, 90, 120, 25);
		btn10Generations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delayLifeGame(10);
			}
		});
		frame.getContentPane().add(btn10Generations);
		
		JButton btntxtnCustom = new JButton();
		btntxtnCustom.setBackground(Color.PINK);
		btntxtnCustom.setText("Custom G !");
		btntxtnCustom.setBounds(10, 140, 120, 25);
		btntxtnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int MaxGen;
				MaxGen = Integer.parseInt(CustomG.getText());;
				delayLifeGame(MaxGen);
			}
		});
		frame.getContentPane().add(btntxtnCustom);
		
		CustomG = new JTextField();
		CustomG.setBounds(10, 120, 120, 20);
		frame.getContentPane().add(CustomG);
		CustomG.setColumns(10);
	}
	
	private void delayLifeGame(int generations){
		   new Thread(new Runnable() {
		        @Override
		        public void run() {
		        	for (int i = 0; i < generations; i++){
						lifeGame.forwardTime();
						jTPCounter.setText(Integer.toString(lifeGame.getGenerationCount()));
			            try {
			                Thread.sleep(500);
			            } catch (InterruptedException ex) {
			            }
			        }
		        }
			    }).start();
	}
}
