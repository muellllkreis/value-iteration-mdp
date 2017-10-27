import java.awt.*;
import java.util.*;

public class Field {
	Point pos;
	int value;

	public Field(Point pos, int value) {
		this.pos = pos;
		this.value = value;
	}

	public Point getPos() {
		return this.pos;
	}

	public int getValue() {
		return this.value;
	}
}