package mhfc.net.common.util.parsing.syntax.literals;

import java.util.Formatter;

import mhfc.net.common.util.parsing.IValueHolder;

public class HolderLiteral implements IExpression {
	private IValueHolder value;

	public HolderLiteral(IValueHolder value) {
		this.value = value;
	}

	@Override
	public void prettyPrint(Formatter formatter) {
		formatter.format("%s", value.toString());
	}

	@Override
	public IValueHolder asValue() {
		return value;
	}

}
