import java.awt.*;
import java.util.List;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.*;

public class World {
	public static World world = null;
	int rows;
	int cols;
	Point dest;
	Field[][] fields;
	Agent agent;
	final int MAX_ITERATIONS = 1000;

	public World(int rows, int cols, Point dest) {
		this.rows = rows;
		this.cols = cols;
		this.dest = dest;
		this.fields = new Field[rows][cols];
		this.initializeFields();
	}

	public void initializeFields() {
		int index = 0;
		for(int i = 0; i < cols; i++) {
			for(int j = 0; j < rows; j++) {
				if((i == dest.x) && (j == dest.y)) {
					this.fields[i][j] = new Field(new Point(i,j), 0, index);
					this.fields[i][j].isGoal = true;
				}
				else {
					this.fields[i][j] = new Field(new Point(i,j), -1, index);
				}
			index++;
			}
		}
	}

	public void printFields() {
		for(int i = 0; i < this.cols; i++) {
			for(int j = 0; j < this.rows; j++) {
					System.out.print(this.fields[i][j].getValue() + " ");
					//System.out.print(this.fields[i][j].getIndex() + " ");
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

	public Field getFieldByIndex(int index) {
		for(int i = 0; i < this.cols; i++) {
			for(int j = 0; j < this.rows; j++) {
				if(this.fields[i][j].getIndex() == index) {
					return this.fields[i][j];
				}
			}
		}
		return null;
	}

	public void testValIter() {
		Field current;
		TreeSet<Field> open = new TreeSet<>();
		Set<Field> visited = new HashSet<>();
		open.add(this.getField(dest));
		int currentReward = 0;

		while(!(open.size() == 0)) {
			Field[] temp = new Field[open.size()];
			for(int i = 0; i < temp.length; i++) {
				temp[i] = open.first();
				open.remove(open.first());
			}
			int tempsize = temp.length;
			for(int i = 0; i < temp.length; i++) {
				current = temp[i];
				if(visited.contains(current)) {
					continue;
				}
				visited.add(current);
				this.fields[this.getField(current.getPos()).getPos().x][this.getField(current.getPos()).getPos().y].setValue(currentReward);
				Queue<Field> neighbors = this.getNeighbors(current.getPos());
				while(neighbors.peek() != null) {
					Field field = neighbors.poll();
					if(visited.contains(field) || Arrays.asList(temp).contains(field)) {
						continue;
					}
					else {
						open.add(field);
					}
				}
			}
			temp = new Field[0];
			currentReward += -1;
		}
	}


	public void testValIterWind(int wind) {
		Field current;
		TreeSet<Field> open = new TreeSet<>();
		Set<Field> visited = new HashSet<>();
		open.add(this.getField(dest));
		int currentReward = 0;

		while(!(open.size() == 0)) {
			Field[] temp = new Field[open.size()];
			for(int i = 0; i < temp.length; i++) {
				temp[i] = open.first();
				open.remove(open.first());
			}
			int tempsize = temp.length;
			for(int i = 0; i < temp.length; i++) {
				current = temp[i];
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println(current.pos);
				System.out.println();
				this.getNeighborsWithWind(current.pos, -1).stream().forEach(x -> System.out.println(x.pos));
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				if(visited.contains(current)) {
					continue;
				}
				visited.add(current);
				this.fields[this.getField(current.getPos()).getPos().x][this.getField(current.getPos()).getPos().y].setValue(currentReward);
				Queue<Field> neighbors = this.getNeighborsWithWind2(current.getPos(), wind);
				while(neighbors.peek() != null) {
					Field field = neighbors.poll();
					if(visited.contains(field) || Arrays.asList(temp).contains(field)) {
						continue;
					}
					else {
						open.add(field);
					}
				}
			}
			temp = new Field[0];
			currentReward += -1;
		}
	}

public void testValIterWind2(int wind) {
		Field current;
		TreeSet<Field> open = new TreeSet<>();
		Set<Field> visited = new HashSet<>();
		open.add(this.getField(dest));
		int currentReward = 0;

		while(!(open.size() == 0)) {
			Field[] temp = new Field[open.size()];
			for(int i = 0; i < temp.length; i++) {
				temp[i] = open.first();
				open.remove(open.first());
			}
			int tempsize = temp.length;
			for(int i = 0; i < temp.length; i++) {
				current = temp[i];
				if(visited.contains(current)) {
					continue;
				}
				visited.add(current);
				this.fields[this.getField(current.getPos()).getPos().x][this.getField(current.getPos()).getPos().y].setValue(currentReward);
				Queue<Field> neighbors = this.getNeighborsWithWind2(current.getPos(), wind);
				while(neighbors.peek() != null) {
					Field field = neighbors.poll();
					if(visited.contains(field) || Arrays.asList(temp).contains(field)) {
						continue;
					}
					else {
						open.add(field);
					}
				}
			}
			temp = new Field[0];
			currentReward += -1;
		}
	}


	public Queue<Field> getNeighborsWithWind(Point current, int wind) {
		Queue<Field> neighbors = new LinkedList<>();
		Agent agent = new Agent(this, new Point(0,0));
		if(current.y == 6) {
			return agent.getRowSixNeighbors(current, wind);
		}
		for(Action a: Action.values()) {
			if(agent.peekAtFieldWithWind(current, a, wind) == null) {
				continue;
			}
			else {
				neighbors.add(agent.peekAtFieldWithWind(current, a, wind));
			}
		}
		return neighbors;
	}

	public Queue<Field> getNeighborsWithWind2(Point current, int wind) {
		Queue<Field> neighbors = new LinkedList<>();
		Agent agent = new Agent(this, new Point(0,0));
		if(current.y == 6) {
			return agent.getRowSixNeighbors(current, wind);
		}
		if((current.y > 2 && current.y < 6)) {
			for(int j = -1; j <= 1; j++) {
	            for(int i = -1; i <= 1; i++) {
	                Point candidate = new Point(current.x + i + wind, current.y + j);
	                if(candidate.x < 0 || candidate.y < 0 
	                    || candidate.x > this.rows-1 || candidate.y > this.cols-1) {
	                    continue;
	                }
	                Field newneighbor = this.getField(candidate);
	                if(neighbors.stream().anyMatch(f -> f.pos == newneighbor.pos)) {
	                	continue;
	                }
	                neighbors.add(newneighbor);
	            }
	        }			
		}
		if(current.y <= 2) {
			neighbors = this.getNeighbors(current);
		}

		return neighbors;
	}

	public Queue<Field> getNeighbors(Point current) {
        Queue<Field> neighbors = new LinkedList<>();
        for(int j = -1; j <= 1; j++) {
            for(int i = -1; i <= 1; i++) {
                Point candidate = new Point(current.x + i, current.y + j);
                if(candidate.x < 0 || candidate.y < 0 
                    || candidate.x > this.rows-1 || candidate.y > this.cols-1
                    || (current.x == candidate.x && current.y == candidate.y)) {
                    continue;
                }
                Field newneighbor = this.getField(candidate);
                neighbors.add(newneighbor);
            }
        }
        return neighbors;
    }

	public static void main(String[] args) {
		// make sure there is only one world instance
		if(world == null) {
			world = new World(7, 7, new Point(3,6));
		}
		world.agent = new Agent(world, new Point(3,0));
		//ßüüßßßßßßßworld.testValIter();
		world.testValIter();
		world.agent.printFields();
		world.agent.getOptimalSequenceFromPos(world.agent.pos);//.stream().forEach(x -> System.out.println(x));

		LinkedList<Field> visited = new LinkedList();
		visited.add(world.getField(world.agent.pos));
		world.agent.depthFirst(visited);

		System.out.println();

		for(int i = 6; i <= 48; i = i+7) {
			System.out.println("CURRENT: " + world.getFieldByIndex(i).pos);
			world.getNeighborsWithWind2(world.getFieldByIndex(i).pos, -1).stream().forEach(x -> System.out.println(x.pos));
		System.out.println();			
		}

		System.out.println("++++++++++++++++++++++++++++++++++++++++");

		for(int i = 5; i <= 47; i = i+7) {
			System.out.println("CURRENT: " + world.getFieldByIndex(i).pos);
			world.getNeighborsWithWind2(world.getFieldByIndex(i).pos, -1).stream().forEach(x -> System.out.println(x.pos));
		System.out.println();			
		}
	}
}