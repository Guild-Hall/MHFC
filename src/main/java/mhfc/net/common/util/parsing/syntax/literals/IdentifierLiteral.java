package mhfc.net.common.util.parsing.syntax.literals;

import java.util.Formatter;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.valueholders.ContextVariable;

public class IdentifierLiteral implements IExpression {
	private Context context;
	private String name;

	public IdentifierLiteral(Context context, String name) {
		this.context = context;
		this.name = name;
	}

	@Override
	public IValueHolder asValue() {
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
