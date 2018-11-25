package mhfc.net.common.util.parsing.valueholders;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

import java.util.function.Supplier;

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
