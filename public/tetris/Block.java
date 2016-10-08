import java.awt.Graphics;
import java.awt.Color;

/** A block used to form a Tetris shape. */

public class Block implements Comparable<Object>{

	private int coord_x;  // x coordinate in array - from left to right
	private int coord_y;  // y coordinate in array - from top to bottom
	private int size;     // object size (square, so same width & height)
	private Color color;
	private boolean stopped; // called if hits y limit
	private boolean empty; // if no block exists here

	// Constructor
	public Block(int x, int y, int s, boolean e) {
		this.coord_x = x;
		this.coord_y = y;
		this.size = s;
		this.empty = e;
	}

	public Block(int x, int y, int s, Color c, boolean e) {
		this.coord_x = x;
		this.coord_y = y;
		this.size = s;
		this.empty = e;
		this.color = c;
	}

	public void stopBlock() {this.stopped = true;}
	public boolean isStopped() {return stopped;}
	public boolean isEmpty() {return empty;}
	public void clear() {
		this.stopped = false;
		this.empty = true;
	}

	public void fillBlock(Color c) {
		this.color = c;
		this.empty = false;
	}
	public Color getColor() {return color;}
	public void setX(int x) {coord_x = x;}
	public int getX() {return coord_x;}
	public void setY(int y) {coord_y = y;}
	public int getY() {return coord_y;}
	public void setSize(int s) {size = s;}
	public int getSize() {return size;}
	public void shiftLeft() {coord_x--;}
	public void shiftRight() {coord_x++;}
	public void shiftDown() {coord_y++;}

	public boolean hitsLeftWall(Block[][] blocks) {
		return coord_x < 0;
	}
	public boolean hitsRightWall(Block[][] blocks) {
		return coord_y >= blocks.length;
	}


	// INTERACTIONS
	// Whether the block can move down
	public boolean canMoveDown(Block[][] blocks) {
		if (coord_y >= blocks[0].length - 1 || intersects(blocks)) 
			return false;
		return blocks[coord_x][coord_y+1].isEmpty() && !isStopped();	
	}

	// Whether the block can move left
	public boolean canMoveLeft(Block[][] blocks) {
		if (coord_x <= 0) return false;
		return blocks[coord_x-1][coord_y].isEmpty() && !isStopped();		
	}

	// Whether the block can move right
	public boolean canMoveRight(Block[][] blocks) {
		if (coord_x >= blocks.length - 1) return false;
		return blocks[coord_x+1][coord_y].isEmpty() && !isStopped();	
	}

	public boolean intersects(Block[][] blocks) {
		if (coord_x < 0 || coord_x >= blocks.length || 
				coord_y < 0 || coord_y >= blocks[0].length) 
			return true;
		return !blocks[coord_x][coord_y].isEmpty();
	}

	public void moveBlock(int x, int y) {
		coord_x = x;
		coord_y = y;
	}

	/**
	 * Default draw method that provides how the object should be drawn 
	 * in the GUI. This method does not draw anything. Subclass should 
	 * override this method based on how their object should appear.
	 * 
	 * @param g 
	 *	The Graphics context used for drawing the object.
	 * 	Remember graphics contexts that we used in OCaml, it gives the 
	 *  context in which the object should be drawn (a canvas, a frame, 
	 *  etc.)
	 */
	public void draw(Graphics g) {
		int x = coord_x * size;
		int y = coord_y * size;
		if (isEmpty()) {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, size, size);//g.clearRect(x, y, size, size); 
		}
		else {
			g.setColor(color);
			g.fillRect(x, y, size, size);
		}
		g.setColor(Color.WHITE);
		g.drawRect(x, y, size, size);
	}

	@Override
	public int compareTo(Object o) {
		return this.hashCode() - o.hashCode();
	}

}