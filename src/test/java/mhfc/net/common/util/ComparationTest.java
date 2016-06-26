package mhfc.net.common.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ComparationTest {
	@Test
	public void lessThan() {
		assertTrue(Comparation.comparing(2).isLessThan(4));
	}

	@Test
	public void equals() {
		assertTrue(Comparation.comparing(2).isEqualsThan(2));
	}

	@Test
	public void withResult() {
		assertTrue(Comparation.comparing(2).to(3).favorsRight());
	}
}
