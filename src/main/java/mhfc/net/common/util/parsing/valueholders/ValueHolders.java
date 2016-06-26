package mhfc.net.common.util.parsing.valueholders;

import java.util.function.Supplier;

import mhfc.net.common.util.parsing.IValueHolder;

public class ValueHolders {
	public static IValueHolder throwing(Supplier<RuntimeException> exception) {
		return () -> {
			throw exception.get();
		};
	}
}
