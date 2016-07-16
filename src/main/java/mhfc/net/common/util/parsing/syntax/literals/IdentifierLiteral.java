package mhfc.net.common.util.parsing.syntax.literals;

import java.util.Formatter;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.valueholders.ContextVariable;

public class IdentifierLiteral implements IExpression {
	private String name;

	public IdentifierLiteral(String name) {
		this.name = name;
	}

	@Override
	public IValueHolder asValue(Context context) {
		return new ContextVariable(context, name);
	}

	public String getLiteral() {
		return name;
	}

	@Override
	public void prettyPrint(Formatter formatter) {
		formatter.format("%s", name);
	}

	@Override
	public String toString() {
		return "\"" + name + "\"";
	}

}
