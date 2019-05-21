package floodIt;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.PrimitiveIterator.OfDouble;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FloodIt {

	private JFrame frame;
	private FloodItGrid floodIt;
	private JTextPane jTpCounter;
	private JTextField jTFSize;
	private JTextPane jTPNbColors;
	private int gameSize;
	private int nbColors;
	private JTextPane jTPmaxMoves;
	private JTextPane jTPmoves;
	private JTextPane jTPnbMaxMoves;
	private JComboBox<Object> jCBnbColors;
	private int jCBnbColorsIndex;
	private JButton[] jBColorToPlay;
	private JTextPane txtpnColorsToPlay;
	private JTextPane jTPGameSize;
	private JTextPane jTPNbGameSize;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FloodIt window = new FloodIt();
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
	public FloodIt() {
		gameSize = 10;
		nbColors = 6;
		floodIt = new FloodItGrid(gameSize, gameSize, nbColors);
		frame = new JFrame();
		frame.setBounds(100, 100, 698, 581);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		colorsToPlay();
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JButton btnNewButton = new JButton("Flood It !");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (!FloodIt.isInteger(jTFSize.getText())){
						throw new NotNumberException("Not a number");
					}
					gameSize = Integer.parseInt(jTFSize.getText());
				}
				catch(NotNumberException message){
				
				}
				nbColors = jCBnbColors.getSelectedIndex()+2;
				jCBnbColorsIndex = jCBnbColors.getSelectedIndex();
				frame.getContentPane().removeAll();				
				floodIt = new FloodItGrid(gameSize, gameSize, nbColors);
				colorsToPlay();
				initialize();
				frame.revalidate();
				frame.repaint();
			}
		});
		btnNewButton.setBackground(Color.ORANGE);
		btnNewButton.setBounds(5, 0, 110, 20);
		frame.getContentPane().add(btnNewButton);
		
		jTpCounter = new JTextPane();
		jTpCounter.setBounds(75, 105, 50, 20);
		frame.getContentPane().add(jTpCounter);
		
		jTFSize = new JTextField("Enter game size");
		jTFSize.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				jTFSize.setText("");
			}
		});
		jTFSize.setBounds(5, 20, 110, 20);
		frame.getContentPane().add(jTFSize);
		jTFSize.setColumns(10);
		
		jTPNbColors = new JTextPane();
		jTPNbColors.setText("Nb colors:");		
		jTPNbColors.setBounds(5, 60, 70, 20);
		frame.getContentPane().add(jTPNbColors);
		
		jTPmaxMoves = new JTextPane();
		jTPmaxMoves.setText("Max moves:");		
		jTPmaxMoves.setBounds(5, 85, 70, 20);
		frame.getContentPane().add(jTPmaxMoves);
		
		jTPmoves = new JTextPane();
		jTPmoves.setText("Moves:");
		jTPmoves.setBounds(5, 105, 70, 20);
		frame.getContentPane().add(jTPmoves);
		
		jTPNbGameSize = new JTextPane();
		jTPNbGameSize.setText(Integer.toString(floodIt.getWidth()));
		jTPNbGameSize.setBounds(75, 40, 30, 20);
		frame.getContentPane().add(jTPNbGameSize);
		
		jTPGameSize = new JTextPane();
		jTPGameSize.setText("Game size:");
		jTPGameSize.setBounds(5, 40, 70, 20);
		frame.getContentPane().add(jTPGameSize);
		
		jTPnbMaxMoves = new JTextPane();
		jTPnbMaxMoves.setText(Long.toString(Math.round(floodIt.getWidth()*2.375)));
		jTPnbMaxMoves.setBounds(80, 85, 50, 20);
		frame.getContentPane().add(jTPnbMaxMoves);
		
		jCBnbColors = new JComboBox<Object>();
		jCBnbColors.setToolTipText("Nb of Colors");
		jCBnbColors.setModel(new DefaultComboBoxModel<Object>(new String[] {"2", "3", "4", "5", "6", "7", "8"}));
		jCBnbColors.setSelectedIndex(jCBnbColorsIndex);
		jCBnbColors.setBounds(75, 60, 40, 20);
		frame.getContentPane().add(jCBnbColors);
		
		txtpnColorsToPlay = new JTextPane();
		txtpnColorsToPlay.setText("Colors to play :");
		txtpnColorsToPlay.setBounds(5, 130, 100, 20);
		frame.getContentPane().add(txtpnColorsToPlay);
		int floodItBoxesLength = floodIt.getFloodItBoxes().length;

		for (int i = 0; i < floodItBoxesLength; i++){
			final int j = i;
			int x = 200 + floodIt.getFloodItBox(i).getIndexW() * 40;
			int y = 45 + floodIt.getFloodItBox(i).getIndexH() * 40;	
			floodIt.getFloodItBox(i).setBounds(x, y, 40, 40);
			floodIt.getFloodItBox(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					floodIt.chooseColor(j);
					jTpCounter.setText(Integer.toString(floodIt.getPlayCount()));
					removeColorsToPlay();
					colorsToPlay();
				}
			});
			frame.getContentPane().add(floodIt.getFloodItBox(i));
		}
	}
	
	private void removeColorsToPlay(){
		int indexMax = jBColorToPlay.length;
		
		for (int index = 0; index < indexMax; index++){
			frame.getContentPane().remove(jBColorToPlay[index]);
		}
	}
	
	private void colorsToPlay(){
		int nbBoxColored;
		int xCoordinate;
		int yCoordinate;
		int jBColorToPlaySize;
		
		xCoordinate = 50;
		yCoordinate = 155;
		jBColorToPlaySize = 40;
		nbBoxColored = floodIt.getColorsToPlay().size();
		jBColorToPlay = new JButton[nbBoxColored];
		
		for (int index = 0; index < nbBoxColored; index++){
			jBColorToPlay[index]= new JButton();
			jBColorToPlay[index].setBounds(xCoordinate, yCoordinate + (index * 40), jBColorToPlaySize, jBColorToPlaySize);
			jBColorToPlay[index].setBackground(floodIt.getColorToPlay(index));
			frame.getContentPane().add(jBColorToPlay[index]);
		}
		frame.revalidate();
		frame.repaint();		
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
}
