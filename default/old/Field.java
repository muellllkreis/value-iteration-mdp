import java.awt.*;
import java.util.List;
import java.util.*;

public class Field implements Comparable<Field> {
	Point pos;
	int index;
	int reward;
	int value;
	boolean isGoal = false;
	List<Action> optimals = new ArrayList<>();

	public Field(Point pos, int reward, int index) {
		this.pos = pos;
		this.reward = reward;
		this.value = 0;
		this.index = index;
	}

	public List<Action> getOptimals() {
		return this.optimals;
	}

	public void setOptimals(List<Action> optimals) {
		this.optimals = optimals;
	}

	public Point getPos() {
		return this.pos;
	}

	public int getValue() {
		return this.value;
	}

	public int getIndex() {
		return this.index;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getReward() {
		return this.reward;
	}

	@Override
    public boolean equals(Object o) {
        Field anotherField = (Field) o;
    return this.pos.equals(anotherField.pos);
    }

    @Override
    public int compareTo(Field anotherField) {
        return Integer.compare(this.index, anotherField.index);
    }
}