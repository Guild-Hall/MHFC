package mhfc.net.common.util.parsing;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

import mhfc.net.common.util.parsing.proxies.StaticAccess;
import mhfc.net.common.util.parsing.syntax.special.ISpecialCallable;
import mhfc.net.common.util.parsing.syntax.special.ISpecialMember;
import mhfc.net.common.util.parsing.valueholders.Arguments;
import net.minecraft.command.SyntaxErrorException;

public class AngularMCTest {
	private static final int TEST_VALUE = 42;
	private Context ctx;
	private BoundExpressionTranslator translator;

	public class TestStruct implements ISpecialMember {
		public int property = TEST_VALUE;

		public int mutate(int in) {
			return in * 2;
		}

		public int mutate(int one, int two) {
			return one * two;
		}

		@Override
		public Holder __getattr__(String name) {
			if (name.equals("x")) {
				return Holder.valueOf(42);
			}
			return Holder.empty();
		}
	}

	public class Callable implements ISpecialCallable {
		@Override
		public Holder __call__(Arguments args) throws Throwable {
			return Holder.valueOf(args.getArgument(0).snapshot().asInt() * 2);
		}
	}

	@Before
	public void setUp() throws Exception {
		ctx = new Context();
		translator = ctx.getTranslator();
		ctx.putVar("callable", Holder.valueOf(new Callable()));
		ctx.putVar("testVar", Holder.valueOf(TEST_VALUE));
		ctx.putVar("true", Holder.valueOf(true));
		ctx.putVar("false", Holder.valueOf(false));
		ctx.putVar("structVar", Holder.valueOf(new TestStruct()));
		ctx.putVar("Math", Holder.valueOf(new StaticAccess(Math.class)));
	}

	@Test
	public void simpleExpr() {
		IValueHolder holder = translator.parse("100");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(100));
	}

	@Test
	public void simpleWithComment() {
		IValueHolder holder = translator.parse("100 /* a comment * / **/");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(100));
	}

	@Test(expected = SyntaxErrorException.class)
	public void failingTwoValues() {
		translator.parse("3 5");
	}

	@Test(expected = SyntaxErrorException.class)
	public void failingTwoOperators() {
		translator.parse("3 ++ 5");
	}

	@Test(expected = SyntaxErrorException.class)
	public void unrecognizedSymbol() {
		translator.parse("3 | 4 ° 5");
	}

	@Test
	public void contextVar() {
		IValueHolder holder = translator.parse("testVar");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(TEST_VALUE));
	}

	@Test
	public void memberAccess() {
		IValueHolder holder = translator.parse("structVar.property");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(TEST_VALUE));
	}

	@Test
	public void call() {
		IValueHolder holder = translator.parse("testVar | callable");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(2 * TEST_VALUE));
	}

	@Test
	public void multipleArguments() {
		IValueHolder holder = translator.parse("testVar | structVar.mutate : testVar");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(TEST_VALUE * TEST_VALUE));
	}

	@Test
	public void chainedCalls() {
		IValueHolder holder = translator.parse("testVar | structVar.mutate : testVar | structVar.mutate : testVar");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(TEST_VALUE * TEST_VALUE * TEST_VALUE));
	}

	@Test
	public void memberCall() {
		IValueHolder holder = translator.parse("testVar | structVar.mutate");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(2 * TEST_VALUE));
	}

	@Test
	public void getattribute() {
		IValueHolder holder = translator.parse("structVar.x");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(TEST_VALUE));
	}

	@Test
	public void bracketsEasy() {
		IValueHolder holder = translator.parse("(testVar | structVar.mutate)");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(2 * TEST_VALUE));
	}

	@Test
	public void bracketsMedium() {
		IValueHolder holder = translator.parse("testVar | structVar.mutate : (testVar | callable)");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(TEST_VALUE * 2 * TEST_VALUE));
	}

	@Test
	public void context() {
		IValueHolder holder = translator.parse("$.testVar");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(TEST_VALUE));
	}

	@Test
	public void staticAccess() {
		IValueHolder holder = translator.parse("12 | Math.max : 10");
		assertThat(Holder.snapshotSafely(holder).asDouble(), equalTo(12d));
	}

	@Test
	public void simpleAdd() {
		IValueHolder holder = translator.parse("testVar + testVar");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(TEST_VALUE + TEST_VALUE));
	}

	@Test
	public void moreOperators() {
		IValueHolder holder = translator.parse("(testVar / testVar + testVar * testVar) % testVar");
		assertThat(
				Holder.snapshotSafely(holder).asInt(),
				equalTo((TEST_VALUE / TEST_VALUE + TEST_VALUE * TEST_VALUE) % TEST_VALUE));
	}

	@Test
	public void complementOperator() {
		IValueHolder holder = translator.parse("~testVar");
		assertThat(Holder.snapshotSafely(holder).asInt(), equalTo(~TEST_VALUE));
	}

	@Test
	public void negateOperators() {
		IValueHolder holder = translator.parse("!true");
		assertThat(Holder.snapshotSafely(holder).asBool(), equalTo(false));
	}

	@Test
	public void comparison() {
		IValueHolder holder = translator.parse("testVar < testVar + 1");
		assertThat(Holder.snapshotSafely(holder).asBool(), equalTo(true));
	}
}
