import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.TreeSet;

import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel {
	private int rows;
	private int columns;
	private static int size = 30;
	public int WIDTH = 300;
	public int HEIGHT = 500;
	private Block[][] blocks; // holds elements
	public static final int INTERVAL = 500; // update for timer
	private int lines;
	private int score;
	private int speed;
	public Shape current;
	public boolean speedMode;

	public int getLines() { return lines; }

	private void setSize(int size) {
		columns = WIDTH / size;
		rows = HEIGHT / size;
		WIDTH = columns * size;
		HEIGHT = rows * size;
	}

	private static int randomInteger(int min, int max) {
		Random random = new Random();
		int randomNum = random.nextInt(max - min) + min;
		return randomNum;
	}

	// the state of the game logic
	public boolean playing = false; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)
	private JLabel linesLabel;
	private JLabel scoreLabel;
	private JLabel speedLabel;


	public Board(JLabel status, JLabel linesLabel, JLabel scoreLabel,
			JLabel speedLabel) {
		setSize(size);
		// set up game
		current = new Shape(0, 0, 0);
		current.blocks = new TreeSet<Block>();
		blocks = new Block[columns][rows];
		for (int i = 0; i < columns; i++)
			for (int j = 0; j < rows; j++)
				blocks[i][j] = new Block(i, j, size, true);
		playing = false;
		speedMode = true;
		lines = 0;
		score = 0;
		speed = 1;
		
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < speed; i++)
					tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// This key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually
		// moves the square.)


		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!playing) return;
				switch(e.getKeyCode()) {
				case(KeyEvent.VK_LEFT): { 
					if (current.canMoveLeft(blocks) && !current.isStopped())
						current.moveLeft();
					break;
				}
				case(KeyEvent.VK_RIGHT): {
					if (current.canMoveRight(blocks) && !current.isStopped())
						current.moveRight();
					break;
				}
				case(KeyEvent.VK_DOWN): {
					if (current.canMoveDown(blocks) && !current.isStopped())
						current.moveDown();
					break;
				}
				case(KeyEvent.VK_SPACE): {
					while (current.canMoveDown(blocks) && !current.isStopped())
						current.moveDown();
					score++;
					break;
				}
				case(KeyEvent.VK_UP): {
					if (!current.isStopped())
						current.rotateLeft(blocks);
					break;
				}
				case(KeyEvent.VK_Z): {
					if (!current.isStopped())
						current.rotateRight(blocks);
					break;
				}
				}
				while(current.aboveTop(blocks) && !current.isStopped())
					current.moveDown();	
				if (!current.canMoveDown(blocks)) current.stopShape();
				repaint();
			}
			
			public void keyReleased(KeyEvent e) {
				while(current.aboveTop(blocks) && !current.isStopped())
					current.moveDown();	
				repaint();
				}
		});
		this.status = status;
		this.linesLabel = linesLabel;
		this.scoreLabel = scoreLabel;
		this.speedLabel = speedLabel;
	}
	
	public Shape nextShape() {
		int shapeNum = randomInteger(0, 7);
		Shape temp = new Shape(0, 0, size);
		switch (shapeNum) {
		case(0): {
			temp = new Shape_O(0, 0, size);
			break;
		} case(1): {
			temp = new Shape_I(0, 0, size);
			break;
		} case(2): {
			temp = new Shape_S(0, 0, size);
			break;
		} case(3): {
			temp = new Shape_Z(0, 0, size);
			break;
		} case(4): {
			temp = new Shape_L(0, 0, size);
			break;
		} case(5): {
			temp = new Shape_J(0, 0, size);
			break;
		} case(6): {
			temp = new Shape_T(0, 0, size);
			break;
		} default:  {
			temp = new Shape(0, 0, size);
			break;
		}
		}
		temp.x = randomInteger(-temp.xRangeMin, columns - temp.xRangeMax);
		temp.y = temp.yShift;
		for (Block b: temp.blocks) {
			b.setX(b.getX() + temp.x);
			b.setY(b.getY() + temp.y);
		}
		return temp;
	}
	public boolean fullRow(int r) {
		for (int i = 0; i < columns; i++)
			if (blocks[i][r].isEmpty()) return false;
		return true;
	}

	public void removeRow(int r) {
		for (int i = rows - 1; i > 0; i--)
			if (i <= r) 
				for (int j = 0; j < columns; j++) {
					Block changeTo = blocks[j][i-1];
					changeTo.shiftDown();
					blocks[j][i] = changeTo;
				}
		for (int j = 0; j < columns; j++) {
			blocks[j][0] = new Block(j, 0, size, true);
		}
	}
	
	public boolean fullCol(int c) {
		return !blocks[c][0].isEmpty();
	}

	// (Re-)set the game to its initial state.
	public void reset() {
		current = nextShape();
		blocks = new Block[columns][rows];
		for (int i = 0; i < columns; i++)
			for (int j = 0; j < rows; j++)
				blocks[i][j] = new Block(i, j, size, true);
		playing = true;
		status.setText("Running...");
		linesLabel.setText("Lines: 0");
		scoreLabel.setText("Score: 0");
		speedLabel.setText("Speed: 1");
		lines = 0;
		score = 0;
		speed = 1;
		repaint();

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	void addCurrent(Shape s) {
		for (Block b: current.blocks)
			blocks[b.getX()][b.getY()] = b;
		current = s;
	}
	

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			if (current.isStopped()) {
				Shape newShape = nextShape();
				if (newShape.intersects(blocks)) {
					playing = false;
					status.setText("Press New Game to Begin");
					final JFrame lostFrame = new JFrame("");
					lostFrame.setLocation(400, 400);
					final JLabel lostText = new JLabel("You lost!", 
							SwingConstants.CENTER);
					lostFrame.add(lostText);
					lostFrame.setSize(new Dimension(100, 50));
					lostFrame.setVisible(true);
					return;
				}
				else {
					while (newShape.aboveTop(blocks)) {
						if (newShape.canMoveDown(blocks)) newShape.moveDown();
						else newShape.stopShape();
					}
					if (!newShape.isStopped()) addCurrent(newShape);
				}
			}
			else if (current.aboveTop(blocks)) {
				while (current.aboveTop(blocks)) {
					if (current.canMoveDown(blocks)) current.moveDown();
					else current = nextShape();
				}
				
			}
			else if (current.canMoveDown(blocks))
				current.moveDown();
			else {
				current.stopShape();
				score++;
			}
			int rowCount = 0;
			for (int i = 0; i < rows; i++)
				if (fullRow(i)) {
					removeRow(i);
					lines++;
					rowCount++;
					linesLabel.setText("Lines: " + lines);
				}
			switch(rowCount) {
			case(1): {score += 100; break;}
			case(2): {score += 300; break;}
			case(3): {score += 500; break;}
			case(4): {score += 800; break;}
			}
			scoreLabel.setText("Score: " + score);
			if (speedMode) {
				int newSpeed = score / 750 + 1;
				if (newSpeed != speed) {
					speed = newSpeed;
				}
				speedLabel.setText("Speed: " + speed);
			}
			else { 
				speedLabel.setText("Speed: 1");
			}
			repaint();  // update the display
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < columns; i++)
			for (int j = 0; j < rows; j++)
				blocks[i][j].draw(g);
		current.draw(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
}