import java.awt.*;
import java.util.*;

public class World {
	public static World world = null;
	int rows;
	int cols;
	Point dest;
	Field[][] fields;
	Agent agent;

	public World(int rows, int cols, Point dest) {
		this.rows = rows;
		this.cols = cols;
		this.dest = dest;
		this.fields = new Field[rows][cols];
		this.initializeFields();
		//this.printFields();
	}

	public void initializeFields() {
		for(int i = 0; i < cols; i++) {
			for(int j = 0; j < rows; j++) {
				if((i == dest.x) && (j == dest.y)) {
					this.fields[i][j] = new Field(new Point(i,j), 0);
				}
				else {
					this.fields[i][j] = new Field(new Point(i,j), -1);
				}
			}
		}
	}

	public void printFields() {
		for(int i = 0; i < this.cols; i++) {
			for(int j = 0; j < this.rows; j++) {
					System.out.print(this.fields[i][j].getValue() + " ");
			}
			System.out.println();
		}
	}

	public Field getField(Point pos) {
		return this.fields[pos.x][pos.y];
	}

	public int getCols() {
		return this.cols;
	}

	public int getRows() {
		return this.rows;
	}

	public boolean isOutOfBounds(Point pos) {
		return (pos.x >= rows || pos.x < 0 || pos.y >= cols || pos.y < 0) ? true : false;
	}

	public static void main(String[] args) {
		// make sure there is only one world instance
		if(world == null) {
			world = new World(7, 7, new Point(3,6));
		}
		world.agent = new Agent(world, new Point(3,0));
		world.agent.printFields();
		world.agent.move(Action.E);
		world.agent.move(Action.E);
		world.agent.move(Action.E);
		world.agent.printFields();
	}
}