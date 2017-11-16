import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class Agent {
	World world;
	Point pos;
	int totalReward = 0;

	public Agent(World world, Point startingPos) {
		this.world = world;
		this.pos = startingPos;
	}

	public boolean move(Action action, boolean print) {
		Point before = this.pos;
		switch(action) {
			case N:
				if(!this.world.isOutOfBounds(new Point(this.pos.x - 1, this.pos.y))) {
					this.pos = new Point(this.pos.x - 1, this.pos.y);
					this.totalReward -= world.getField(this.pos).getValue();
				}
				else {
					return false;
				}
				break;
			case NE:
				if(!this.world.isOutOfBounds(new Point(this.pos.x - 1, this.pos.y)) && !this.world.isOutOfBounds(new Point(this.pos.x - 1, this.pos.y + 1))) {
					this.pos = new Point(this.pos.x - 1, this.pos.y + 1);
					this.totalReward -= world.getField(this.pos).getValue();
				}
				else if(this.world.isOutOfBounds(new Point(this.pos.x - 1, this.pos.y)) && !this.world.isOutOfBounds(new Point(this.pos.x, this.pos.y + 1))) {
					this.pos = new Point(this.pos.x, this.pos.y + 1);
					this.totalReward -= world.getField(this.pos).getValue();
				}
				else {
					return false;
				}
				break;
			case E:
				if(!this.world.isOutOfBounds(new Point(this.pos.x, this.pos.y + 1))) {
					this.pos = new Point(this.pos.x, this.pos.y + 1);
					this.totalReward -= world.getField(this.pos).getValue();
				}
				else {
					return false;
				}
				break;
			case SE:
				if(!this.world.isOutOfBounds(new Point(this.pos.x + 1, this.pos.y)) && !this.world.isOutOfBounds(new Point(this.pos.x + 1, this.pos.y + 1))) {
					this.pos = new Point(this.pos.x + 1, this.pos.y + 1);
					this.totalReward -= world.getField(this.pos).getValue();
				}
				else if(this.world.isOutOfBounds(new Point(this.pos.x + 1, this.pos.y)) && !this.world.isOutOfBounds(new Point(this.pos.x, this.pos.y + 1))) {
					this.pos = new Point(this.pos.x, this.pos.y + 1);
					this.totalReward -= world.getField(this.pos).getValue();
				}
				else {
					return false;
				}
				break;
			case S:
				if(!this.world.isOutOfBounds(new Point(this.pos.x + 1, this.pos.y))) {
					this.pos = new Point(this.pos.x + 1, this.pos.y);
					this.totalReward -= world.getField(this.pos).getValue();
				}
				else {
					return false;
				}
				break;
			case SW:
				if(!this.world.isOutOfBounds(new Point(this.pos.x + 1, this.pos.y)) && !this.world.isOutOfBounds(new Point(this.pos.x + 1, this.pos.y - 1))) {
					this.pos = new Point(this.pos.x + 1, this.pos.y - 1);
					this.totalReward -= world.getField(this.pos).getValue();
				}
				else if(this.world.isOutOfBounds(new Point(this.pos.x + 1, this.pos.y)) && !this.world.isOutOfBounds(new Point(this.pos.x, this.pos.y - 1))) {
					this.pos = new Point(this.pos.x, this.pos.y - 1);
					this.totalReward -= world.getField(this.pos).getValue();
				}
				else {
					return false;
				}
				break;
			case W:
				if(!this.world.isOutOfBounds(new Point(this.pos.x, this.pos.y - 1))) {
					this.pos = new Point(this.pos.x, this.pos.y - 1);
					this.totalReward -= world.getField(this.pos).getValue();
				}
				else {
					return false;
				}
				break;
			case NW:
				if(!this.world.isOutOfBounds(new Point(this.pos.x - 1, this.pos.y)) && !this.world.isOutOfBounds(new Point(this.pos.x - 1, this.pos.y - 1))) {
					this.pos = new Point(this.pos.x - 1, this.pos.y - 1);
					this.totalReward -= world.getField(this.pos).getValue();
				}
				else if(this.world.isOutOfBounds(new Point(this.pos.x - 1, this.pos.y)) && !this.world.isOutOfBounds(new Point(this.pos.x, this.pos.y - 1))) {
					this.pos = new Point(this.pos.x, this.pos.y - 1);
					this.totalReward -= world.getField(this.pos).getValue();
				}
				else {
					return false;
				}
				break;
			case STAY:
				break;
			default:
				return false;
		}
		if(print) {
			System.out.println("Move " + action + " from (" + before.x + "," + before.y + ") to (" + pos.x + "," + pos.y + ")");
			System.out.println();
			this.printFields();
		}
	return true;
	}

	public Field peekAtFieldWithWind(Point curr, Action action, int wind) {
		if(!(curr.y > 2 && curr.y < 6)) {
			wind = 0;
		}

		//System.out.println("WIND: " + wind);
 
		switch(action) {
			case N:
				if(!this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y))) {
					//System.out.println("PRINT: " + this.world.getField(new Point(curr.x - 1 + wind, curr.y)).pos);
					return this.world.getField(new Point(curr.x - 1 + wind, curr.y));
				}
				else {
					return null;
				}
			case NE:
				if(!this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y + 1))) {
					return this.world.getField(new Point(curr.x - 1 + wind, curr.y + 1));
				}
				else {
					return this.peekAtField(curr, action);
				}
			case E:
				if(!this.world.isOutOfBounds(new Point(curr.x + wind, curr.y + 1))) {
					return this.world.getField(new Point(curr.x + wind, curr.y + 1));
				}
				else {
					return null;
				}
			case SE:
				if(!this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y + 1))) {
					return this.world.getField(new Point(curr.x + 1 + wind, curr.y + 1));
				}
				else {
					return this.peekAtField(curr, action);
				}
			case S:
				if(!this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y))) {
					return this.world.getField(new Point(curr.x + 1 + wind, curr.y));
				}
				else {
					return null;
				}
			case SW:
				if(!this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y - 1))) {
					return this.world.getField(new Point(curr.x + 1 + wind, curr.y - 1));
				}
				else {
					return this.peekAtField(curr, action);
				}
			case W:
				if(!this.world.isOutOfBounds(new Point(curr.x + wind, curr.y - 1))) {
					return this.world.getField(new Point(curr.x + wind, curr.y - 1));
				}
				else {
					return null;
				}
			case NW:
				if(!this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y - 1))) {
					return this.world.getField(new Point(curr.x - 1 + wind, curr.y - 1));
				}
				else {
					return this.peekAtField(curr, action);
				}
			case STAY:
				if(!this.world.isOutOfBounds(new Point(curr.x + wind, curr.y))) {
					return this.world.getField(new Point(curr.x + wind, curr.y));
				}
				else {
					return this.world.getField(new Point(curr.x, curr.y));	
				}
			default:
				return null;
		}
	}

	public Queue<Field> getRowSixNeighbors(Point curr, int wind) {
        Queue<Field> neighbors = new LinkedList<>();
		if(curr.x < 4) {
			for(int i = 5; i <= 47; i = i+7) {
				Field check = this.world.getFieldByIndex(i);
				//System.out.println(check.pos);
				Queue<Field> temp = this.world.getNeighborsWithWind(check.pos, wind);//.stream().forEach(x -> System.out.print(x.pos + " "));
				//System.out.println();
				if(this.world.getNeighborsWithWind(check.pos, wind).contains(this.world.getField(curr))) {
						neighbors.add(check);
				}
			}
			if(!this.world.isOutOfBounds(new Point(curr.x - 1, curr.y))) {
				neighbors.add(this.world.getField(new Point(curr.x - 1, curr.y)));
			}
			if(!this.world.isOutOfBounds(new Point(curr.x + 1, curr.y))) {
				neighbors.add(this.world.getField(new Point(curr.x + 1, curr.y)));
			}
		}
		else {
			neighbors = this.world.getNeighbors(curr);
		}
		//System.out.println(neighbors.size());
		return neighbors;
	}

	public Field peekAtField(Point curr, Action action) {
		switch(action) {
			case N:
				if(!this.world.isOutOfBounds(new Point(curr.x - 1, curr.y))) {
					return this.world.getField(new Point(curr.x - 1, curr.y));
				}
				else {
					return null;
				}
			case NE:
				if(!this.world.isOutOfBounds(new Point(curr.x - 1, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x - 1, curr.y + 1))) {
					return this.world.getField(new Point(curr.x - 1, curr.y + 1));
				}
				else if(this.world.isOutOfBounds(new Point(curr.x - 1, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x, curr.y + 1))) {
					return this.world.getField(new Point(curr.x, curr.y + 1));
				}
				else {
					return null;
				}
			case E:
				if(!this.world.isOutOfBounds(new Point(curr.x, curr.y + 1))) {
					return this.world.getField(new Point(curr.x, curr.y + 1));
				}
				else {
					return null;
				}
			case SE:
				if(!this.world.isOutOfBounds(new Point(curr.x + 1, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + 1, curr.y + 1))) {
					return this.world.getField(new Point(curr.x + 1, curr.y + 1));
				}
				else if(this.world.isOutOfBounds(new Point(curr.x + 1, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x, curr.y + 1))) {
					return this.world.getField(new Point(curr.x, curr.y + 1));
				}
				else {
					return null;
				}
			case S:
				if(!this.world.isOutOfBounds(new Point(curr.x + 1, curr.y))) {
					return this.world.getField(new Point(curr.x + 1, curr.y));
				}
				else {
					return null;
				}
			case SW:
				if(!this.world.isOutOfBounds(new Point(curr.x + 1, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + 1, curr.y - 1))) {
					return this.world.getField(new Point(curr.x + 1, curr.y - 1));
				}
				else if(this.world.isOutOfBounds(new Point(curr.x + 1, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x, curr.y - 1))) {
					return this.world.getField(new Point(curr.x, curr.y - 1));
				}
				else {
					return null;
				}
			case W:
				if(!this.world.isOutOfBounds(new Point(curr.x, curr.y - 1))) {
					return this.world.getField(new Point(curr.x, curr.y - 1));
				}
				else {
					return null;
				}
			case NW:
				if(!this.world.isOutOfBounds(new Point(curr.x - 1, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x - 1, curr.y - 1))) {
					return this.world.getField(new Point(curr.x - 1, curr.y - 1));
				}
				else if(this.world.isOutOfBounds(new Point(curr.x - 1, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x, curr.y - 1))) {
					return this.world.getField(new Point(curr.x, curr.y - 1));
				}
				else {
					return null;
				}
			case STAY:
				return this.world.getField(new Point(curr.x, curr.y));
			default:
				return null;
		}
	}


	public void printFields() {
		for(int i = 0; i < this.world.getCols(); i++) {
			for(int j = 0; j < this.world.getRows(); j++) {
					if(this.pos.x == i && this.pos.y == j) {
						System.out.print(" A ");
						continue;
					}
					System.out.print(this.world.getField(new Point(i,j)).getValue() + " ");
					//System.out.print(this.world.getField(new Point(i,j)).getIndex() + " ");
					//System.out.print(this.world.getField(new Point(i,j)).isGoal + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public List<Action> getOptimalSequenceFromPos(Point position) {
		Field currentPos = this.world.getField(position);
		List<Action> preferred = new ArrayList<>();
		int maxreward = Integer.MIN_VALUE;

		for(Action a: Action.values()) {
			if(this.peekAtField(currentPos.pos, a) == null) {
				continue;
			}
			int reward = this.peekAtField(currentPos.pos, a).getValue();
			if(reward >= maxreward) {
				maxreward = reward;
			}
		}
		for(Action a: Action.values()) {
			if(this.peekAtField(currentPos.pos, a) == null) {
				continue;
			}
			if(this.peekAtField(currentPos.pos, a).getValue() == maxreward) {
				preferred.add(a);
			}
		}
		currentPos.setOptimals(preferred);
		//currentPos.getOptimals().stream().forEach(x -> System.out.print(x + " "));
		return preferred;
	}

	public void printSequence(List<Action> sequence) {
		sequence.stream().forEach(x -> System.out.print(x + " "));
		System.out.println();
	}

	public List<Field> optimalNeighbors(Field field) {
		this.getOptimalSequenceFromPos(field.getPos()); 
		List<Field> neighbors = new ArrayList<>();
		for(Action a: field.getOptimals()) {
			neighbors.add(this.peekAtField(field.pos, a));
		}
		return neighbors;
	}

	public Action whichDirection(Field origin, Field destination) {
		//System.out.println(origin.pos +" VS. " + destination.pos); 
		if(origin.pos.x - 1 == destination.pos.x && origin.pos.y == destination.pos.y) {
			return Action.N;
		}
		else if(origin.pos.x - 1 == destination.pos.x && origin.pos.y + 1 == destination.pos.y) {
			return Action.NE;
		}
		else if(origin.pos.x == destination.pos.x && origin.pos.y + 1 == destination.pos.y) {
			return Action.E;
		}
		else if(origin.pos.x + 1 == destination.pos.x && origin.pos.y + 1 == destination.pos.y) {
			return Action.SE;
		}
		else if(origin.pos.x + 1 == destination.pos.x && origin.pos.y == destination.pos.y) {
			return Action.S;
		}
		else if(origin.pos.x + 1 == destination.pos.x && origin.pos.y - 1 == destination.pos.y) {
			return Action.SW;
		}
		else if(origin.pos.x == destination.pos.x && origin.pos.y - 1 == destination.pos.y) {
			return Action.W;
		}
		else if(origin.pos.x - 1 == destination.pos.x && origin.pos.y - 1 == destination.pos.y) {
			return Action.NW;
		}
		else {
			return Action.STAY;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void depthFirst(LinkedList<Field> visited) {
        List<Field> nodes = this.optimalNeighbors(visited.getLast());
        
        // examine adjacent nodes
        for (Field field : nodes) {
            if (visited.contains(field)) {
                continue;
            }
            if (field.isGoal) {
                visited.add(field);
                printPath(visited);
                visited.removeLast();
                break;
            }
        }
        for (Field field : nodes) {
            if (visited.contains(field) || field.isGoal) {
                continue;
            }
            visited.addLast(field);
            depthFirst(visited);
            visited.removeLast();
        }
    }

    private void printPath(LinkedList<Field> visited) {
        for (Field field : visited) {
        	if(visited.indexOf(field) + 1 < visited.size()) {
            System.out.print(this.whichDirection(field, visited.get(visited.indexOf(field) + 1)));
            //System.out.print(field.index);
            System.out.print(" ");        		
        	}
        }
        System.out.println();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}


	// public Field peekAtFieldWithWind2(Point curr, Action action, int wind) {
	// 	// if(!(curr.y > 2 && curr.y < 6)) {
	// 	// 	wind = 0;
	// 	// }

	// 	if(curr.y < 2) {
	// 		wind = 0;
	// 	}
	// 	else if(curr.y == 6) {
	// 		switch(action) {
	// 			case N:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y))) {
	// 					//System.out.println("PRINT: " + this.world.getField(new Point(curr.x - 1 + wind, curr.y)).pos);
	// 					return this.world.getField(new Point(curr.x - 1 + wind, curr.y));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case NE:
	// 				return null;
	// 			case E:
	// 				return null;
	// 			case SE:
	// 				return null;
	// 			case S:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y))) {
	// 					return this.world.getField(new Point(curr.x + 1 + wind, curr.y));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case SW:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x + 2, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + 2, curr.y - 1))) {
	// 					return this.world.getField(new Point(curr.x + 2, curr.y - 1));
	// 				}
	// 				else if(this.world.isOutOfBounds(new Point(curr.x + 2, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + 1, curr.y - 1))) {
	// 					return this.world.getField(new Point(curr.x + 1, curr.y - 1));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case W:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x + 1, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + 1, curr.y - 1))) {
	// 					return this.world.getField(new Point(curr.x + 1, curr.y - 1));
	// 				}
	// 				else if(this.world.isOutOfBounds(new Point(curr.x, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x, curr.y - 1))) {
	// 					return this.world.getField(new Point(curr.x, curr.y - 1));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case NW:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x, curr.y - 1))) {
	// 					return this.world.getField(new Point(curr.x, curr.y - 1));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case STAY:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x + wind, curr.y))) {
	// 					return this.world.getField(new Point(curr.x + wind, curr.y));
	// 				}
	// 				else {
	// 					return this.world.getField(new Point(curr.x, curr.y));	
	// 				}
	// 			default:
	// 				return null;
	// 		}
	// 	}
	// 	else {
	// 		switch(action) {
	// 			case N:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y))) {
	// 					//System.out.println("PRINT: " + this.world.getField(new Point(curr.x - 1 + wind, curr.y)).pos);
	// 					return this.world.getField(new Point(curr.x - 1 + wind, curr.y));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case NE:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y + 1))) {
	// 					return this.world.getField(new Point(curr.x - 1 + wind, curr.y + 1));
	// 				}
	// 				else if(this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + wind, curr.y + 1))) {
	// 					return this.world.getField(new Point(curr.x, curr.y + 1));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case E:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x + wind, curr.y + 1))) {
	// 					return this.world.getField(new Point(curr.x + wind, curr.y + 1));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case SE:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y + 1))) {
	// 					return this.world.getField(new Point(curr.x + 1 + wind, curr.y + 1));
	// 				}
	// 				else if(this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + wind, curr.y + 1))) {
	// 					return this.world.getField(new Point(curr.x, curr.y + 1));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case S:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y))) {
	// 					return this.world.getField(new Point(curr.x + 1 + wind, curr.y));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case SW:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y - 1))) {
	// 					return this.world.getField(new Point(curr.x + 1 + wind, curr.y - 1));
	// 				}
	// 				else if(this.world.isOutOfBounds(new Point(curr.x + 1 + wind, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + wind, curr.y - 1))) {
	// 					return this.world.getField(new Point(curr.x, curr.y - 1));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case W:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x + wind, curr.y - 1))) {
	// 					return this.world.getField(new Point(curr.x + wind, curr.y - 1));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case NW:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y - 1))) {
	// 					return this.world.getField(new Point(curr.x - 1 + wind, curr.y - 1));
	// 				}
	// 				else if(this.world.isOutOfBounds(new Point(curr.x - 1 + wind, curr.y)) && !this.world.isOutOfBounds(new Point(curr.x + wind, curr.y - 1))) {
	// 					return this.world.getField(new Point(curr.x, curr.y - 1));
	// 				}
	// 				else {
	// 					return null;
	// 				}
	// 			case STAY:
	// 				if(!this.world.isOutOfBounds(new Point(curr.x + wind, curr.y))) {
	// 					return this.world.getField(new Point(curr.x + wind, curr.y));
	// 				}
	// 				else {
	// 					return this.world.getField(new Point(curr.x, curr.y));	
	// 				}
	// 			default:
	// 				return null;
	// 		}
	// 	}
		
	// }

	// public void findPaths(Field currentField) {
	// 	System.out.println("HALLO");
	// 	currentPath.push(currentField);
	// 	visited.add(currentField.index);

	// 	if(currentField.isGoal) {
	// 		visited.remove(currentField.index);
	// 	}

	// 	if(currentField.isGoal && currentPath.size() != 1) {
	// 		currentPath.stream().forEach(x -> System.out.print(x));
	// 		System.out.println();
	// 	} else {
	// 		System.out.println("else: " + currentField.pos);
	// 		System.out.println(this.optimalNeighbors(currentField).size());
	// 		for(Field f: this.optimalNeighbors(currentField)) {
	// 			System.out.println("for");
	// 			if(!visited.contains(f.index)) {
	// 				this.findPaths(f);
	// 			}
	// 		}
	// 	currentPath.pop();
	// 	visited.remove(currentField.index);
	// 	}
	// }

	// public void findAllOptimalPaths(Set<Field> visited, Field v, List<Action> path, Action a) {
	// 	visited.add(v);
	// 	if(a != null) {
	// 		path.add(a);			
	// 	}
	// 	if(v.isGoal) {
	// 		path.stream().forEach(x -> System.out.println(x));
	// 	}
	// 	else {
	// 		for(int i = 0; i < this.optimalNeighbors(v).size(); i++) {
	// 			Field w = this.optimalNeighbors(v).get(i);
	// 			if(!visited.contains(w)) {
	// 				findAllOptimalPaths(visited, w, path, w.optimals.get(i));
	// 			}
	// 		}
	// 		// for(Field w : this.optimalNeighbors(v)) {
	// 		// 	if(!visited.contains(w)) {
	// 		// 		findAllOptimalPaths(visited, w, path);
	// 		// 	}
	// 		// }
	// 	}
	// 	path.remove(path.size());
	// 	visited.remove(v);
	// }

		// public void optimalHelper() {
	// 	Set<Field> visited = new HashSet<>();
	// 	Field v = this.world.getField(this.pos);
	// 	List<Action> path = new LinkedList<>();
	// 	this.findAllOptimalPaths(visited, v, path, null);
	// }


	// public List<Action> moveToGoal() {
	// 	List<Action> sequence = new ArrayList<>();
	// 	while(!this.world.getField(pos).isGoal) {
	// 		Action action = this.getOptimalSequenceFromPos().get(0);
	// 		this.move(action, true);
	// 		sequence.add(action);
	// 		try {
	// 			Thread.sleep(1000);
	// 		} 
	// 		catch(InterruptedException ex) {
	// 		    Thread.currentThread().interrupt();
	// 		}
	// 	}
	// return sequence;
	// }

	// public List<Action> simulate(int alt) {
	// 	Point start = this.pos;	
	// 	List<Action> sequence = new ArrayList<>();
	// 	while(!this.world.getField(pos).isGoal) {
	// 		List<Action> actions = this.getOptimalSequenceFromPos();
	// 		Action action = null;
	// 		if(actions.size() > 1) {
	// 			action = this.getOptimalSequenceFromPos().get(alt);
	// 		}
	// 		else {
	// 			action = this.getOptimalSequenceFromPos().get(0);
	// 		}
	// 		this.move(action, false);
	// 		sequence.add(action);
	// 	}
	// 	this.pos = start;
	// 	return sequence;
	// }




	// public void findPaths(Field currentField, Deque<Action> currentPath, Set<Field> visited, int depth) {
	// 	//System.out.println("DEPTH: " + depth);	
	// 	this.getOptimalSequenceFromPos(currentField.pos);
	// 	System.out.println("POSITION: " + currentField.pos);	
	// 	// for(Action a : currentField.getOptimals()) {
	// 	// 	System.out.println(this.peekAtField(currentField.pos, a).pos);
	// 	// }
	// 	//System.out.println("VISITED");
	// 	//visited.stream().forEach(x -> System.out.print(x.getPos() + " "));
	// 	for(Action a : currentField.getOptimals()) {
	// 		if(this.peekAtField(currentField.pos, a).isGoal) {
	// 			System.out.println("GOAL");
	// 			currentPath.add(a);
	// 	 		currentPath.stream().forEach(x -> System.out.print(x + " "));
	// 	 		System.out.println();				
	// 		}
	// 		else {
	// 			if(!visited.contains(this.peekAtField(currentField.pos, a))) {
	// 				currentPath.add(a);
	// 				visited.add(currentField);
	// 				//System.out.println("if");
	// 				//System.out.println(currentPath);
	// 				int newdepth = depth+1;
	// 				this.findPaths(this.peekAtField(currentField.pos, a), currentPath, visited, newdepth);	
	// 			}
	// 			else {
	// 				//System.out.println("else");
	// 				//System.out.println();
	// 				continue;
	// 			}	
	// 			currentPath.pop();
	// 			//visited.add(currentField);
	// 		}
	// 	}
	// }


	// public void findPathHelper() {
	// 	this.findPaths(this.world.getField(this.pos), new ArrayDeque<Action>(), new HashSet<Field>(), 0);
	// 	//System.out.println(this.world.getField(this.pos).optimals);
	// }