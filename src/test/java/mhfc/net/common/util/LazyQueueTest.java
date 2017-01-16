package mhfc.net.common.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class LazyQueueTest {

	static List<Integer> original;
	static LazyQueue<Integer> queue;

	@BeforeClass
	public static void setUp() {
		original = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++)
			original.add(new Integer(i));
		queue = new LazyQueue<>(original);
	}

	@Test
	public void testConstruct() {
		List<Integer> trans = new ArrayList<>(queue);
		assertEquals(original, trans);
	}

	@Test
	public void testPeek() {
		assertEquals(new Integer(0), queue.peek());
	}

	@Test
	public void testInfinite() {
		Iterator<Integer> infiniteIterator = new Iterator<Integer>() {
			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public Integer next() {
				return new Integer(0);
			}
		};
		Integer zero = new Integer(0);
		LazyQueue<Integer> infiniteQueue = new LazyQueue<>(infiniteIterator);
		for (int i = 0; i < 1000; i++) {
			assertEquals(zero, infiniteQueue.remove());
		}
	}

}
