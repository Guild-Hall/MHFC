package mhfc.net.common.util.parsing;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class AngularMCTest {
	private Context ctx;
	private ExpressionTranslator translator;

	@Before
	public void setUp() throws Exception {
		ctx = new Context();
		translator = ctx.getTranslator();
	}

	@Test
	public void simpleExpr() {
		Holder holder = translator.parse("3").snapshot();
		assertThat(holder.asInt(), equalTo(3));
	}

	@Test(timeout = 200)
	public void failingExpr() {
		IValueHolder holder = translator.parse("3 | 5");
		System.out.println(holder);
	}
}
