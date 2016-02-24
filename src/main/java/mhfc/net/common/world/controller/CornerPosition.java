package mhfc.net.common.world.controller;

public class CornerPosition {
	public final int posX;
	public final int posY;

	public CornerPosition(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public static CornerPosition totalMin(CornerPosition x, CornerPosition y) {
		if (x.posX <= y.posX && x.posY <= y.posY) {
			return x;
		}
		if (y.posX <= x.posX && y.posY <= x.posY) {
			return y;
		}
		return new CornerPosition(java.lang.Math.min(x.posX, y.posX), java.lang.Math.min(x.posY, y.posY));
	}

	public static CornerPosition totalMax(CornerPosition x, CornerPosition y) {
		if (x.posX >= y.posX && x.posY >= y.posY) {
			return x;
		}
		if (y.posX >= x.posX && y.posY >= x.posY) {
			return y;
		}
		return new CornerPosition(java.lang.Math.max(x.posX, y.posX), java.lang.Math.max(x.posY, y.posY));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + posX;
		result = prime * result + posY;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CornerPosition other = (CornerPosition) obj;
		if (posX != other.posX) {
			return false;
		}
		if (posY != other.posY) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "(" + this.posX + ", " + this.posY + ")";
	}
}
