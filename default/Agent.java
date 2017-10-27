import java.awt.*;
import java.util.*;

public class Agent {
	World world;
	Point pos;
	int totalReward = 0;

	public Agent(World world, Point startingPos) {
		this.world = world;
		this.pos = startingPos;
	}

	public boolean move(Action action) {
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
	return true;
	}

	public void printFields() {
		for(int i = 0; i < this.world.getCols(); i++) {
			for(int j = 0; j < this.world.getRows(); j++) {
					if(this.pos.x == i && this.pos.y == j) {
						System.out.print("A  ");
						continue;
					}
					System.out.print(this.world.getField(new Point(i,j)).getValue() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}