// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {

	Color light_purple = new Color(200, 150, 250);
	public static final String logo_file = "images/logo.png";
	private static BufferedImage logo;	
	public static final String inst_file = "images/instructions.png";
	private static BufferedImage instImg;	

	public void run() {

		// Top-level frame in which game components live
		final JFrame frame = new JFrame("TETRIS");
		frame.setLocation(200, 200);

		final JPanel side_panel = new JPanel();
		side_panel.setLayout(new GridLayout(12,1));
		// Status panel
		frame.add(side_panel, BorderLayout.EAST);


		final JLabel status = new JLabel("Press New Game to Begin", 
				SwingConstants.CENTER);

		final JLabel linesLabel = new JLabel("Lines: 0", SwingConstants.CENTER);
		final JLabel scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
		final JLabel speedLabel = new JLabel("Speed: 1", SwingConstants.CENTER);

		// Main playing area
		final Board court = new Board(status, linesLabel, scoreLabel, 
				speedLabel);
		frame.add(court, BorderLayout.CENTER);
		court.setSize(court.WIDTH, court.HEIGHT);
		side_panel.setBackground(light_purple);
		court.setBackground(light_purple);
		side_panel.setBorder(BorderFactory.createMatteBorder(30, 20, 30, 20,
				light_purple));
		court.setPreferredSize(new Dimension(court.WIDTH, court.HEIGHT));
		court.setMinimumSize(new Dimension(court.WIDTH, court.HEIGHT));
		court.setMaximumSize(new Dimension(court.WIDTH, court.HEIGHT));
		frame.setMinimumSize(new Dimension(court.WIDTH + side_panel.getWidth(),
				court.HEIGHT));
		frame.setMaximumSize(new Dimension(2 * court.WIDTH,
				court.HEIGHT));
		


		final JButton pause = new JButton("Pause");
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.playing = !court.playing;
				if (!court.playing) {
					status.setText("Paused");
					pause.setText("Continue");
				}
				else {
					status.setText("Running...");
					pause.setText("Pause");
					court.requestFocusInWindow();
				}
			}
		});
		
		final JButton instructions = new JButton("Instructions");
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.playing = false;
				status.setText("Paused");
				pause.setText("Continue");
				final JFrame instFrame = new JFrame("Instructions");
				instFrame.setLocation(200, 200);
				try {
					if (instImg == null) {
						instImg = ImageIO.read(getClass().getResource(inst_file));
					}
				} catch (IOException e1) {
					System.out.println("Internal Error:" + e1.getMessage());
				}
				
				JLabel instLabel = new JLabel(new ImageIcon(instImg), 
						SwingConstants.CENTER);
				instFrame.setBackground(light_purple);
				instLabel.setBackground(light_purple);
				instFrame.add(instLabel);
				instFrame.pack();
				instFrame.setVisible(true);
				
			}
		});


		final JButton speedModeButton = new JButton("Speed Mode: On");
		speedModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.speedMode = !court.speedMode;
				if (!court.speedMode) {
					speedModeButton.setText("Speed Mode: Off");
					court.requestFocusInWindow();
				}
				else {
					speedModeButton.setText("Speed Mode: On");
					court.requestFocusInWindow();
				}
			}
		});

		// Reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.NORTH);

		final JButton reset = new JButton("New Game");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.reset();
			}
		});
		try {
			if (logo == null) {
				logo = ImageIO.read(getClass().getResource(logo_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
        JLabel title = new JLabel(new ImageIcon(logo), SwingConstants.CENTER);
        title.setMaximumSize(new Dimension(200, 50));
        
		side_panel.add(title);
		side_panel.add(new JLabel(""));
		side_panel.add(linesLabel);
		side_panel.add(scoreLabel);
		side_panel.add(speedLabel);
		side_panel.add(instructions);
		side_panel.add(reset);
		side_panel.add(pause);
		side_panel.add(speedModeButton);
		side_panel.add(status);

		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}