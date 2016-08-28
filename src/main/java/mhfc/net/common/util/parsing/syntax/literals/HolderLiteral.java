package mhfc.net.common.util.parsing.syntax.literals;

import java.util.Formatter;
import java.util.Objects;
import java.util.function.Function;

import mhfc.net.common.util.parsing.IValueHolder;

public class HolderLiteral implements IExpression {
	private String descriptor;
	private Function<IValueHolder, IValueHolder> valueIndirect;

	public HolderLiteral(IValueHolder value) {
		this(c -> value, value.toString());
	}

	public HolderLiteral(Function<IValueHolder, IValueHolder> fromContext) {
		this(fromContext, "<indirect value>");
	}

	public HolderLiteral(Function<IValueHolder, IValueHolder> fromContext, String descriptor) {
		valueIndirect = Objects.requireNonNull(fromContext);
		this.descriptor = descriptor;
	}

	@Override
	public void prettyPrint(Formatter formatter) {
		formatter.format("%s", descriptor);
	}

	@Override
	public IValueHolder asValue(IValueHolder ctx) {
		return valueIndirect.apply(ctx);
	}

}
