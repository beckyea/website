import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class Shape {

	public Color color;
	public Set<Block> blocks;
	public int size;
	private boolean stopped;
	public int orientation;
	public int x; // leftmost coordinate
	public int y; // topmost coordinate
	public int xRangeMin;
	public int xRangeMax;
	public int yShift;


	public Shape(int x, int y, int size){
		stopped = false;
		this.size = size;
	}

	public void getOrientation() {
		Random random = new Random();
		orientation = random.nextInt(4);
	}

	public boolean aboveTop(Block[][] board) {
		for (Block b: blocks)
			if (b.getY() < 0) return true;
		return false;
	}
	
	public boolean intersects(Block[][] board) {
		for (Block b: blocks)
			if (b.intersects(board)) return true;
		return false;
	}
	
	public boolean canMoveDown(Block[][] board) {
		for (Block b: blocks)
			if (!b.canMoveDown(board)) return false;
		return true;
	}

	public boolean canMoveLeft(Block[][] board) {
		for (Block b: blocks)
			if (!b.canMoveLeft(board)) return false;
		return true;
	}

	public boolean canMoveRight(Block[][] board) {
		for (Block b: blocks)
			if (!b.canMoveRight(board)) return false;
		return true;
	}

	public void moveDown() {
		for (Block b: blocks)
			b.shiftDown();
		y++;
	}

	public void moveLeft() {
		for (Block b: blocks)
			b.shiftLeft();
		x--;
	}

	public void moveRight() {
		for (Block b: blocks)
			b.shiftRight();
		x++;
	}

	public void stopShape() {
		for (Block b: blocks)
			b.stopBlock();
		stopped = true;
	}

	public Set<Block> withOrientation(int i) {
		return blocks;
	}
	
	public void rotateLeft(Block[][] board) {
		orientation = (orientation + 1) % 4;
		blocks = withOrientation(orientation % 4);
		if (!intersects(board)) return;
		moveLeft();
		if (!intersects(board)) return;
		moveRight();
		moveRight();
		if (!intersects(board)) return;
		moveLeft();
		moveLeft();
		moveLeft();
		if (!intersects(board)) return;
		moveRight();
		moveRight();
		moveRight();
		moveRight();
		if (!intersects(board)) return;
		moveLeft();
		moveLeft();
		moveLeft();	
	}
	
	public void rotateRight(Block[][] board) {
		orientation = (orientation + 3) % 4;
		blocks = withOrientation(orientation % 4);
		if (!intersects(board)) return;
		moveLeft();
		if (!intersects(board)) return;
		moveRight();
		moveRight();
		if (!intersects(board)) return;
		moveLeft();
		moveLeft();
		moveLeft();
		if (!intersects(board)) return;
		moveRight();
		moveRight();
		moveRight();
		moveRight();
		if (!intersects(board)) return;
		moveLeft();
		moveLeft();
		moveLeft();
	}
	
	 public boolean isStopped() {return stopped;}


	/**
	 * Default draw method that provides how the object should be drawn 
	 * in the GUI. This method does not draw anything. Subclass should 
	 * override this method based on how their object should appear.
	 */
	public void draw(Graphics g) {
		for (Block b: blocks)
			b.draw(g);
	}

}
