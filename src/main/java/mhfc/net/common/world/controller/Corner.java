package mhfc.net.common.world.controller;

import java.util.Objects;

public class Corner {
	public final CornerType type;
	public final CornerPosition pos;

	public Corner(CornerType type, int posX, int posZ) {
		this(type, new CornerPosition(posX, posZ));
	}

	public Corner(CornerType type, CornerPosition pos) {
		this.type = Objects.requireNonNull(type);
		this.pos = Objects.requireNonNull(pos);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Corner other = (Corner) obj;
		if (pos == null) {
			if (other.pos != null) {
				return false;
			}
		} else if (!pos.equals(other.pos)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("{type: %s, coords: %s}", this.type, this.pos);
	}
}
