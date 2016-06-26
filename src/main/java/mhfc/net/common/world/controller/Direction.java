package mhfc.net.common.world.controller;

public enum Direction {
	RIGHT, //
	UP, //
	LEFT, //
	DOWN;

	public boolean isHorizontal() {
		switch (this) {
		case RIGHT:
		case LEFT:
			return true;
		case UP:
		case DOWN:
			return false;
		default:
			throw new IllegalArgumentException("Invalid this");
		}
	}

	public int edgeLength(CornerPosition from, CornerPosition to) {
		switch (this) {
		case RIGHT:
			return to.posX - from.posX;
		case UP:
			return to.posY - from.posY;
		case LEFT:
			return from.posX - to.posX;
		case DOWN:
			return from.posY - to.posY;
		default:
			throw new IllegalArgumentException("Invalid this");
		}
	}

	public Direction inverted() {
		switch (this) {
		case RIGHT:
			return Direction.LEFT;
		case UP:
			return Direction.DOWN;
		case LEFT:
			return Direction.RIGHT;
		case DOWN:
			return Direction.UP;
		default:
			throw new IllegalArgumentException("Invalid this");
		}
	}

	public CornerPosition add(CornerPosition start, int edgeLength) {
		switch (this) {
		case RIGHT:
			return new CornerPosition(start.posX + edgeLength, start.posY);
		case UP:
			return new CornerPosition(start.posX, start.posY + edgeLength);
		case LEFT:
			return new CornerPosition(start.posX - edgeLength, start.posY);
		case DOWN:
			return new CornerPosition(start.posX, start.posY - edgeLength);
		default:
			throw new IllegalArgumentException("Invalid this");
		}
	}

	public int compare(CornerPosition x, CornerPosition y) {
		switch (this) {
		case RIGHT:
			return Integer.compare(x.posX, y.posX);
		case UP:
			return Integer.compare(x.posY, y.posY);
		case LEFT:
			return Integer.compare(y.posX, x.posX);
		case DOWN:
			return Integer.compare(y.posY, x.posY);
		default:
			throw new IllegalArgumentException("Invalid this");
		}
	}
}
