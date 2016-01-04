package mhfc.net.common.world.controller;

public enum CornerType {
	// Counter-clockwise
	DOWN_RIGHT(true, Direction.DOWN, Direction.RIGHT),
	RIGHT_UP(true, Direction.RIGHT, Direction.UP),
	UP_LEFT(true, Direction.UP, Direction.LEFT),
	LEFT_DOWN(true, Direction.LEFT, Direction.DOWN),
	DOWN_LEFT(false, Direction.DOWN, Direction.LEFT),
	LEFT_UP(false, Direction.LEFT, Direction.UP),
	UP_RIGHT(false, Direction.UP, Direction.RIGHT),
	RIGHT_DOWN(false, Direction.RIGHT, Direction.DOWN);

	public final boolean isOuter;
	public final Direction incoming, outgoing;

	private CornerType(boolean isOuter, Direction from, Direction to) {
		this.isOuter = isOuter;
		this.incoming = from;
		this.outgoing = to;
	}

	public boolean isOuter() {
		return isOuter;
	}

	public static CornerType fromIncoming(Direction dir, boolean outer) {
		switch (dir) {
		case RIGHT:
			return outer ? CornerType.RIGHT_UP : RIGHT_DOWN;
		case UP:
			return outer ? CornerType.UP_LEFT : UP_RIGHT;
		case LEFT:
			return outer ? LEFT_DOWN : LEFT_UP;
		case DOWN:
			return outer ? CornerType.DOWN_RIGHT : DOWN_LEFT;
		default:
			throw new IllegalArgumentException("Invalid direction");
		}
	}

	public CornerType followUp(boolean outer) {
		return fromIncoming(outgoing, outer);
	}

	/**
	 * branches in the other direction. if this is an outer corner, the returned corner will be innner..
	 * 
	 * @return
	 */
	public CornerType rebranch(boolean rebranchInc) {
		return rebranchInc ? fromIncoming(incoming.inverted(), !isOuter()) : fromIncoming(incoming, !isOuter());
	}

	@Override
	public String toString() {
		return (isOuter ? "." : "!") + super.toString();
	}
}
