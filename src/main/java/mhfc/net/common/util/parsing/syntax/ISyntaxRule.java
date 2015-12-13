package mhfc.net.common.util.parsing.syntax;

import java.util.Stack;

public interface ISyntaxRule {
	boolean reduce(Stack<Symbol> parsingStack);
}