package mhfc.net.common.util.parsing;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import junit.framework.TestCase;
import mhfc.net.common.util.parsing.expressions.Arguments.PermissiveIterator;

@RunWith(value = BlockJUnit4ClassRunner.class)
public class ParsingTests extends TestCase {
	public static class A {
		public int diff = 1;

		public double plus(double a) {
			return a + diff;
		}
	}

	private static final double epsilon = 0e-10d;

	private Context ctx;

	public ParsingTests() {
		super("Parsing Tests");
	}

	@Override
	@Before
	public void setUp() throws Exception {
		ctx = new Context();
		ctx.putVar("a", Holder.valueOf(new A()));
		ctx.putVar("value1", Holder.valueOf(3.2d));
		ctx.putVar("value2", Holder.valueOf(2.3d));
		ctx.putFilter("add", (ar) -> {
			PermissiveIterator<IValueHolder> it = ar.iterator();
			return Holder.valueOf(it.next().asDouble() + it.next().asDouble());
		});
	}

	@Override
	@After
	public void tearDown() throws Exception {}

	@Test
	public void testSimpleExpr() {
		Holder h = ExpressionTranslator.parse("value1 | add : value2").resolveAgainst(ctx).snapshot();
		Assert.assertEquals(h.getType(), double.class);
		Assert.assertEquals(h.asDouble(), 5.5d, epsilon);
	}

	@Test
	public void testMemberMethod() {
		Holder h = ExpressionTranslator.parse("value1 | a.plus").resolveAgainst(ctx).snapshot();
		Assert.assertEquals(h.getType(), double.class);
		Assert.assertEquals(h.asDouble(), 4.2d, epsilon);
	}
}
