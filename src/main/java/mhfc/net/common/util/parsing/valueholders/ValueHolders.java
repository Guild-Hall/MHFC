package mhfc.net.common.util.parsing.valueholders;

import java.util.function.Supplier;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

public class ValueHolders {
	public static IValueHolder throwing(Supplier<RuntimeException> exception) {
		return new IValueHolder() {
			@Override
			public boolean isTypeFinal() {
				return true;
			}

			@Override
			public Holder snapshot() {
				throw exception.get();
			}

			@Override
			public Class<?> getType() {
				return IValueHolder.EMPTY_CLASS;
			}
		};
	}
}
