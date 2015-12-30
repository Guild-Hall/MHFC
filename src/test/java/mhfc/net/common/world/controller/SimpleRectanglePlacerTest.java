package mhfc.net.common.world.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import junit.framework.TestCase;
import mhfc.net.common.world.controller.SimpleRectanglePlacer.Corner;
import mhfc.net.common.world.controller.SimpleRectanglePlacer.CornerType;
import mhfc.net.common.world.controller.SimpleRectanglePlacer.RewindableListIterator;

@RunWith(value = BlockJUnit4ClassRunner.class)
public class SimpleRectanglePlacerTest extends TestCase {
	public SimpleRectanglePlacerTest() {
		super("SimpleRectanglePlacer");
	}

	@Before
	@Override
	protected void setUp() throws Exception {}

	@After
	@Override
	protected void tearDown() throws Exception {}

	@Test
	public void testAAA() {
		SimpleRectanglePlacer.CornerList list = new SimpleRectanglePlacer.CornerList();
		list.add(new Corner(CornerType.DOWN_LEFT, 0, 0));
		list.add(new Corner(CornerType.DOWN_LEFT, 1, 1));
		list.add(new Corner(CornerType.DOWN_LEFT, 2, 2));
		list.add(new Corner(CornerType.DOWN_LEFT, 3, 3));
		list.add(new Corner(CornerType.DOWN_LEFT, 4, 4));
		ListIterator<Corner> replacingIt = list.cyclicIterator(2);
		replacingIt.previous();
		replacingIt.remove();
		replacingIt.next();
		replacingIt.remove();
		replacingIt.next();
		replacingIt.remove();
		replacingIt.add(new Corner(CornerType.DOWN_LEFT, 10, 10));
		System.out.println(list);
		System.out.println("======================================");

		SimpleRectanglePlacer placer = new SimpleRectanglePlacer();
		Random rand = new Random();
		for (int i = 0; i < 25; i++) {
			placer.addRectangle(rand.nextInt(20) + 1, rand.nextInt(20) + 1);
		}
		System.out.println(placer);
		System.out.println("======================================");

		List<String> strings = new ArrayList<>();
		strings.add("1");
		strings.add("2");
		strings.add("3");
		strings.add("4");
		strings.add("5");
		strings.add("6");
		strings.add("7");
		strings.add("8");
		RewindableListIterator<String> it = new RewindableListIterator<>(
				strings.listIterator());
		it.mark();
		it.add("0");
		it.next();
		it.next();
		it.remove();
		it.next();
		it.remove();
		it.next();
		it.next();
		it.add("something");
		System.out.println(it.rewind().next());
		System.out.println("======================================");
	}
}
