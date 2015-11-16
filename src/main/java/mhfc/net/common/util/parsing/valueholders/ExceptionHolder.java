package mhfc.net.common.util.parsing.valueholders;

import javax.annotation.Nullable;

import com.google.common.collect.ComputationException;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

public class ExceptionHolder implements IValueHolder {
	private final Throwable cause;

	public ExceptionHolder(@Nullable Throwable throwable) {
		this.cause = throwable;
	}

	private <T> T throwIt() {
		throw new ComputationException(cause);
	}

	@Override
	public Holder snapshot() {
		return throwIt();
	}

	@Override
	public IValueHolder snapshotClass() {
		return throwIt();
	}

	@Override
	public Class<?> getContainedClass() {
		return throwIt();
	}

}
