package mhfc.net.common.util.parsing.valueholders;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

public class PrimitiveHolders {
	public static class BooleanHolder implements IValueHolder {
		private boolean val;

		public BooleanHolder(boolean val) {
			this.set(val);
		}

		public void set(boolean val) {
			this.val = val;
		}

		public boolean get() {
			return this.val;
		}

		@Override
		public Class<?> getContainedClass() {
			return boolean.class;
		}

		@Override
		public Holder snapshot() {
			return Holder.valueOf(val);
		}

		@Override
		public BooleanHolder snapshotClass() {
			return this;
		}

		@Override
		public boolean isClassSnapshot() {
			return true;
		}
	}

	public static class CharacterHolder implements IValueHolder {
		private char val;

		public CharacterHolder(char val) {
			this.set(val);
		}

		public void set(char val) {
			this.val = val;
		}

		public char get() {
			return this.val;
		}

		@Override
		public Class<?> getContainedClass() {
			return char.class;
		}

		@Override
		public Holder snapshot() {
			return Holder.valueOf(val);
		}

		@Override
		public CharacterHolder snapshotClass() {
			return this;
		}

		@Override
		public boolean isClassSnapshot() {
			return true;
		}
	}

	public static class ByteHolder implements IValueHolder {
		private byte val;

		public ByteHolder(byte val) {
			this.set(val);
		}

		public void set(byte val) {
			this.val = val;
		}

		public byte get() {
			return this.val;
		}

		@Override
		public Class<?> getContainedClass() {
			return byte.class;
		}

		@Override
		public Holder snapshot() {
			return Holder.valueOf(val);
		}

		@Override
		public ByteHolder snapshotClass() {
			return this;
		}

		@Override
		public boolean isClassSnapshot() {
			return true;
		}
	}

	public static class ShortHolder implements IValueHolder {
		private short val;

		public ShortHolder(short val) {
			this.set(val);
		}

		public void set(short val) {
			this.val = val;
		}

		public short get() {
			return this.val;
		}

		@Override
		public Class<?> getContainedClass() {
			return short.class;
		}

		@Override
		public Holder snapshot() {
			return Holder.valueOf(val);
		}

		@Override
		public ShortHolder snapshotClass() {
			return this;
		}

		@Override
		public boolean isClassSnapshot() {
			return true;
		}
	}

	public static class IntegerHolder implements IValueHolder {
		private int val;

		public IntegerHolder(int val) {
			this.set(val);
		}

		public void set(int val) {
			this.val = val;
		}

		public int get() {
			return this.val;
		}

		@Override
		public Class<?> getContainedClass() {
			return int.class;
		}

		@Override
		public Holder snapshot() {
			return Holder.valueOf(val);
		}

		@Override
		public IntegerHolder snapshotClass() {
			return this;
		}

		@Override
		public boolean isClassSnapshot() {
			return true;
		}
	}

	public static class LongHolder implements IValueHolder {
		private long val;

		public LongHolder(long val) {
			this.set(val);
		}

		public void set(long val) {
			this.val = val;
		}

		public long get() {
			return this.val;
		}

		@Override
		public Class<?> getContainedClass() {
			return long.class;
		}

		@Override
		public Holder snapshot() {
			return Holder.valueOf(val);
		}

		@Override
		public LongHolder snapshotClass() {
			return this;
		}

		@Override
		public boolean isClassSnapshot() {
			return true;
		}
	}

	public static class FloatHolder implements IValueHolder {
		private float val;

		public FloatHolder(float val) {
			this.set(val);
		}

		public void set(float val) {
			this.val = val;
		}

		public float get() {
			return this.val;
		}

		@Override
		public Class<?> getContainedClass() {
			return float.class;
		}

		@Override
		public Holder snapshot() {
			return Holder.valueOf(val);
		}

		@Override
		public FloatHolder snapshotClass() {
			return this;
		}

		@Override
		public boolean isClassSnapshot() {
			return true;
		}
	}

	public static class DoubleHolder implements IValueHolder {
		private double val;

		public DoubleHolder(double val) {
			this.set(val);
		}

		public void set(double val) {
			this.val = val;
		}

		public double get() {
			return this.val;
		}

		@Override
		public Class<?> getContainedClass() {
			return double.class;
		}

		@Override
		public Holder snapshot() {
			return Holder.valueOf(val);
		}

		@Override
		public DoubleHolder snapshotClass() {
			return this;
		}

		@Override
		public boolean isClassSnapshot() {
			return true;
		}
	}
}
