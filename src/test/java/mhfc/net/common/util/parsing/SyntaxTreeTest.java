package mhfc.net.common.util.parsing;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import mhfc.net.common.util.parsing.syntax.operators.IOperator;
import mhfc.net.common.util.parsing.syntax.tree.AST;
import mhfc.net.common.util.parsing.syntax.tree.SyntaxBuilder;

public class SyntaxTreeTest {
	private class BarOperator implements IOperator<Integer, Integer> {
		@Override
		public Integer with(Integer value) {
			return value * 2;
		}
	}

	private class FooOperator implements IOperator<Integer, Integer> {
		@Override
		public Integer with(Integer value) {
			return value + 3;
		}
	}

	@Before
	public void setUp() throws Exception {}

	@Test
	public void builderValidatePositive() {
		SyntaxBuilder syntax = new SyntaxBuilder();
		int resultID = syntax.registerTerminal(Integer.class);
		int barID = syntax.registerUnaryOperator(BarOperator.class, false, resultID, resultID);
		int fooID = syntax.registerUnaryOperator(FooOperator.class, true, resultID, resultID);
		syntax.declarePrecedence(fooID, barID);
		syntax.validate();
	}

	@Test(expected = IllegalStateException.class)
	public void builderValidateNegative() {
		SyntaxBuilder syntax = new SyntaxBuilder();
		int resultID = syntax.registerTerminal(Integer.class);
		syntax.registerUnaryOperator(BarOperator.class, false, resultID, resultID);
		syntax.registerUnaryOperator(FooOperator.class, true, resultID, resultID);
		syntax.validate();
	}

	@Test
	public void validateSimpleExpr() {
		SyntaxBuilder syntax = new SyntaxBuilder();
		int resultID = syntax.registerTerminal(Integer.class);
		int barID = syntax.registerUnaryOperator(BarOperator.class, false, resultID, resultID);
		int fooID = syntax.registerUnaryOperator(FooOperator.class, true, resultID, resultID);
		syntax.declarePrecedence(fooID, barID);
		syntax.validate();

		AST ast = syntax.newParseTree();
		ast.pushUnaryOperator(fooID, new FooOperator());
		ast.pushValue(resultID, 2);
		Object obj = ast.getOverallValue();
		Integer i = Integer.class.cast(obj);
		assertThat(i, IsEqual.equalTo(5));
	}

	@Test
	public void validateTwoOperatorExpr() {
		SyntaxBuilder syntax = new SyntaxBuilder();
		int resultID = syntax.registerTerminal(Integer.class);
		int barID = syntax.registerUnaryOperator(BarOperator.class, false, resultID, resultID);
		int fooID = syntax.registerUnaryOperator(FooOperator.class, true, resultID, resultID);
		syntax.declarePrecedence(fooID, barID);
		syntax.validate();

		AST ast = syntax.newParseTree();
		ast.pushUnaryOperator(fooID, new FooOperator());
		ast.pushValue(resultID, 2);
		ast.pushUnaryOperator(barID, new BarOperator());
		Object obj = ast.getOverallValue();
		Integer i = Integer.class.cast(obj);
		assertThat(i, IsEqual.equalTo(10));
	}
}
