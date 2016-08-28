package mhfc.net.common.util.parsing.valueholders;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

/**
 * Represents any object, including primitives.
 *
 * @author WorldSEnder
 *
 */
public final class Any implements IValueHolder {
	private static class FromHolderTag {}

	public static final FromHolderTag snapshot_tag = new FromHolderTag();

	private Holder holder;

	public Any() {
		this.disengage();
	}

	public Any(boolean bool) {
		this.assign(bool);
	}

	public Any(char c) {
		this.assign(c);
	}

	public Any(byte b) {
		this.assign(b);
	}

	public Any(short s) {
		this.assign(s);
	}

	public Any(int i) {
		this.assign(i);
	}

	public Any(long l) {
		this.assign(l);
	}

	public Any(float f) {
		this.assign(f);
	}

	public Any(double d) {
		this.assign(d);
	}

	public Any(Object o) {
		this.assign(o);
	}

	public <F> Any(F o, Class<F> clazz) {
		this.assign(o, clazz);
	}

	public Any(Holder holder, FromHolderTag ignored) {
		this.assign(holder, ignored);
	}

	public boolean isEngaged() {
		return this.holder.isEngaged();
	}

	public void disengage() {
		this.holder = Holder.empty();
	}

	@Override
	public Holder snapshot() {
		return this.holder;
	}

	public void assign(boolean bool) {
		this.setHolder(Holder.valueOf(bool));
	}

	public void assign(char c) {
		this.setHolder(Holder.valueOf(c));
	}

	public void assign(byte b) {
		this.setHolder(Holder.valueOf(b));
	}

	public void assign(short s) {
		this.setHolder(Holder.valueOf(s));
	}

	public void assign(int i) {
		this.setHolder(Holder.valueOf(i));
	}

	public void assign(long l) {
		this.setHolder(Holder.valueOf(l));
	}

	public void assign(float f) {
		this.setHolder(Holder.valueOf(f));
	}

	public void assign(double d) {
		this.setHolder(Holder.valueOf(d));
	}

	public void assign(Object o) {
		this.setHolder(Holder.valueOrEmpty(o));
	}

	public <F> void assign(F o, Class<F> clazz) {
		this.setHolder(Holder.valueOf(o, clazz));
	}

	public void assign(Holder holder, FromHolderTag ignored) {
		this.setHolder(holder);
	}

	private void setHolder(Holder newHolder) {
		this.holder = newHolder;
	}

	public Class<?> getType() {
		return this.holder.getType();
	}

	@Override
	public String toString() {
		return this.holder.toString();
	}
}
