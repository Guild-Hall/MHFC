package mhfc.net.common.util.parsing.syntax.literals;

import java.util.Formatter;

import mhfc.net.common.util.parsing.IValueHolder;

public interface IExpression {
	void prettyPrint(Formatter formatter);

	IValueHolder asValue(IValueHolder ctx);
}
