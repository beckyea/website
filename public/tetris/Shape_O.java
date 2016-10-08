import java.awt.*;
import java.util.TreeSet;

public class Shape_O extends Shape {
	
	public int x_coord; // leftmost coordinate
	public int y_coord; // topmost coordinate
	public Color c = Color.RED;
	
	
	public Shape_O(int x, int y, int s) {
		super(x, y, s);
		color = Color.RED;
		blocks = new TreeSet<Block>();
		blocks.add(new Block(x, y, s, color, false));
		blocks.add(new Block(x + 1, y, s, color, false));
		blocks.add(new Block(x, y + 1, s, color, false));
		blocks.add(new Block(x + 1, y + 1, s, color, false));
		xRangeMin = 0;
		xRangeMax = 1;
		yShift = 0;
	}
	
	public void setColor(Color color) {
		c = color;
	}
	
}