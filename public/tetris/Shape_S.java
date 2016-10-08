import java.awt.*;
import java.util.Set;
import java.util.TreeSet;

public class Shape_S extends Shape {	
	
	public Shape_S(int x, int y, int s) {
		super(x, y, s);
		color = Color.GREEN;
		this.x = x;
		this.y = y;
		this.size = s;
		getOrientation();
		blocks = withOrientation(orientation);
	}
	
	@Override public Set<Block> withOrientation(int i) {
		Set<Block> blockSet = new TreeSet<Block>();
		switch(i) {
		case(0): {
			blockSet.add(new Block(x, y + 1, size, color, false));
			blockSet.add(new Block(x + 1, y + 1, size, color, false));
			blockSet.add(new Block(x + 1, y, size, color, false));
			blockSet.add(new Block(x + 2, y, size, color, false));
			xRangeMin = 0;
			xRangeMax = 2;
			yShift = 0;
			return blockSet;
		}
		case (1): {
			blockSet.add(new Block(x + 1, y - 1, size, color, false));
			blockSet.add(new Block(x + 1, y, size, color, false));
			blockSet.add(new Block(x + 2, y, size, color, false));
			blockSet.add(new Block(x + 2, y + 1, size, color, false));
			xRangeMin = 1;
			xRangeMax = 2;
			yShift = 1;
			return blockSet;
		}
		case(2): {
			blockSet.add(new Block(x, y + 1, size, color, false));
			blockSet.add(new Block(x + 1, y + 1, size, color, false));
			blockSet.add(new Block(x + 1, y, size, color, false));
			blockSet.add(new Block(x + 2, y, size, color, false));
			xRangeMin = 0;
			xRangeMax = 2;
			yShift = 0;
			return blockSet;
		}
		case (3): {
			blockSet.add(new Block(x + 1, y - 1, size, color, false));
			blockSet.add(new Block(x + 1, y, size, color, false));
			blockSet.add(new Block(x + 2, y, size, color, false));
			blockSet.add(new Block(x + 2, y + 1, size, color, false));
			xRangeMin = 1;
			xRangeMax = 2;
			yShift = 1;
			return blockSet;
		}
		default: return blockSet;
		}	
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
}