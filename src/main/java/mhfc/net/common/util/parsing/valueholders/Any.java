package mhfc.net.common.util.parsing.valueholders;

import java.util.Objects;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.Holder.DefaultPolicies;
import mhfc.net.common.util.parsing.IValueHolder;

/**
 * Represents any object, including primitives.
 *
 * @author WorldSEnder
 *
 */
public final class Any implements IValueHolder {
	private static class FromHolderTag {
	}

	public static final FromHolderTag snapshot_tag = new FromHolderTag();

	private Holder holder;
	private FailPolicy onFail = DefaultPolicies.STRICT;

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

	public Any(IValueHolder holder, FromHolderTag ignored) {
		this.assign(holder, ignored);
	}

	public void setFailPolicy(FailPolicy newPolicy) {
		this.onFail = Objects.requireNonNull(newPolicy);
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
		this.setHolder(Holder.valueOf(o));
	}

	public <F> void assign(F o, Class<F> clazz) {
		this.setHolder(Holder.valueOf(o, clazz));
	}

	public void assign(IValueHolder holder, FromHolderTag ignored) {
		this.setHolder(holder.snapshot());
	}

	private void setHolder(Holder newHolder) {
		this.holder = newHolder;
	}

	@Override
	public Holder snapshotClass() {
		return this.holder;
	}

	@Override
	public Class<?> getContainedClass() {
		return this.holder.getContainedClass();
	}

	@Override
	public FailPolicy getDefaultPolicy() {
		return this.onFail;
	}
}
