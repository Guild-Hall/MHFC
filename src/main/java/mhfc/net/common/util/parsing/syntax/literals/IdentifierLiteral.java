package mhfc.net.common.util.parsing.syntax.literals;

import java.util.Formatter;

import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.valueholders.MemberAccess;

public class IdentifierLiteral implements IExpression {
	private String name;

	public IdentifierLiteral(String name) {
		this.name = name;
	}

	@Override
	public IValueHolder asValue(IValueHolder context) {
		return MemberAccess.makeMemberAccess(context, name);
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
