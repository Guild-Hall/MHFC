package mhfc.net.common.util.parsing.valueholders;

import java.util.function.Supplier;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

public class ValueHolders {
	public static IValueHolder throwing(Supplier<RuntimeException> exception) {
		return new IValueHolder() {
			@Override
			public Holder snapshot() throws Throwable {
				throw exception.get();
			}

			@Override
			public String toString() {
				return "<waiting exception: " + exception + ">";
			}
		};
	}
}
