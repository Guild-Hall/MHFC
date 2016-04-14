package mhfc.net.common.util.parsing;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

import net.minecraft.command.SyntaxErrorException;

public class AngularMCTest {
	private static final int TEST_VALUE = 42;
	private Context ctx;
	private ExpressionTranslator translator;

	public class TestStruct {
		public int property = TEST_VALUE;

		public int mutate(int in) {
			return in * 2;
		}

		public int mutate(int one, int two) {
			return one * two;
		}
	}

	@Before
	public void setUp() throws Exception {
		ctx = new Context();
		translator = ctx.getTranslator();
		// FIXME: don't use 3
		ctx.putVar("callable", Holder.valueOf(3));
		ctx.putVar("testVar", Holder.valueOf(TEST_VALUE));
		// FIXME: Make a struct
		ctx.putVar("structVar", Holder.valueOf(new TestStruct()));
	}

	@Test
	public void simpleExpr() {
		IValueHolder holder = translator.parse("100").snapshot();
		assertThat(holder.snapshot().asInt(), equalTo(100));
	}

	@Test
	public void simpleWithComment() {
		IValueHolder holder = translator.parse("100 /* a comment * / **/").snapshot();
		assertThat(holder.snapshot().asInt(), equalTo(100));
	}

	@Test(expected = SyntaxErrorException.class)
	public void failingTwoValues() {
		translator.parse("3 5");
	}

	@Test(expected = SyntaxErrorException.class)
	public void failingTwoOperators() {
		translator.parse("3 ++ 5");
	}

	@Test
	public void contextVar() {
		IValueHolder holder = translator.parse("testVar");
		assertThat(holder.snapshot().asInt(), equalTo(TEST_VALUE));
	}

	@Test
	public void memberAccess() {
		IValueHolder holder = translator.parse("structVar.property");
		assertThat(holder.snapshot().asInt(), equalTo(TEST_VALUE));
	}

	@Test
	public void call() {
		IValueHolder holder = translator.parse("testVar | callable");
		assertThat(holder.snapshot().asInt(), equalTo(2 * TEST_VALUE));
	}

	@Test
	public void multipleArguments() {
		IValueHolder holder = translator.parse("testVar | structVar.mutate : testVar");
		assertThat(holder.snapshot().asInt(), equalTo(TEST_VALUE * TEST_VALUE));
	}

	@Test
	public void chainedCalls() {
		IValueHolder holder = translator.parse("testVar | structVar.mutate : testVar | structVar.mutate : testVar");
		assertThat(holder.snapshot().asInt(), equalTo(TEST_VALUE * TEST_VALUE * TEST_VALUE));
	}

	@Test
	public void memberCall() {
		IValueHolder holder = translator.parse("testVar | structVar.mutate");
		assertThat(holder.snapshot().asInt(), equalTo(2 * TEST_VALUE));
	}
}
