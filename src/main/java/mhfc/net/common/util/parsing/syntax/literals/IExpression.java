package mhfc.net.common.util.parsing.syntax.literals;

import mhfc.net.common.util.parsing.IValueHolder;

import java.util.Formatter;

public interface IExpression {
	void prettyPrint(Formatter formatter);

	IValueHolder asValue(IValueHolder ctx);
}
