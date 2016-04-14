package mhfc.net.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompositeException extends IllegalArgumentException {
	/**
	 *
	 */
	private static final long serialVersionUID = 3901532003007263456L;
	private List<Throwable> exceptions = new ArrayList<Throwable>();

	public CompositeException(Collection<Throwable> exceptions) {
		super("Multiple exceptions");
		this.exceptions = new ArrayList<>(exceptions);
	}

	@Override
	public String toString() {
		return super.toString() + exceptions.toString();
	}
}
