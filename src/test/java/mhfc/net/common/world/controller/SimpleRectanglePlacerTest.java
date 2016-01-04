package mhfc.net.common.world.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.hamcrest.core.IsEqual.equalTo;

import junit.framework.TestCase;
import mhfc.net.common.util.RewindableListIterator;

@RunWith(value = BlockJUnit4ClassRunner.class)
public class SimpleRectanglePlacerTest extends TestCase {
	private Corner at02outer, at02inner, at00outer, at00inner, at20, atminus20outer, atminus20inner;
	private CornerPosition minCorner, maxCorner;

	private CornerPosition beforeAASmallPlacedAt;
	private List<Corner> beforeAASmallReplacement;
	private CornerPosition beforeAAEqualPlacedAt;
	private List<Corner> beforeAAEqualReplacement;
	private CornerPosition beforeAABiggerPlacedAt;
	private List<Corner> beforeAABiggerReplacement;

	public SimpleRectanglePlacerTest() {
		super("SimpleRectanglePlacer");
	}

	@Before
	@Override
	public void setUp() throws Exception {
		at02outer = new Corner(CornerType.UP_LEFT, 0, 2);
		at02inner = new Corner(CornerType.UP_RIGHT, 0, 2);
		at00outer = new Corner(CornerType.RIGHT_UP, 0, 0);
		at00inner = new Corner(CornerType.LEFT_UP, 0, 0);
		at20 = new Corner(CornerType.UP_LEFT, 2, 0);
		atminus20outer = new Corner(CornerType.DOWN_RIGHT, -2, 0);
		atminus20inner = new Corner(CornerType.UP_RIGHT, -2, 0);
		minCorner = new CornerPosition(0, 0);
		maxCorner = new CornerPosition(0, 0);
		// BeforeAASmaller
		Corner testAASmall_10inner = new Corner(CornerType.RIGHT_DOWN, -1, 0);
		Corner testAASmall_1_1outer = new Corner(CornerType.DOWN_RIGHT, -1, -1);
		Corner testAASmall0_1outer = new Corner(CornerType.RIGHT_UP, 0, -1);
		beforeAASmallPlacedAt = new CornerPosition(-1, -1);
		beforeAASmallReplacement = Arrays
				.asList(atminus20outer, testAASmall_10inner, testAASmall_1_1outer, testAASmall0_1outer, at02outer);
		// BeforeAAEqual
		Corner testAAEqual_2_1outer = new Corner(CornerType.DOWN_RIGHT, -2, -1);
		Corner testAAEqual0_1outer = new Corner(CornerType.RIGHT_UP, 0, -1);
		beforeAAEqualPlacedAt = new CornerPosition(-2, -1);
		beforeAAEqualReplacement = Arrays.asList(testAAEqual_2_1outer, testAAEqual0_1outer, at02outer);
		// BeforeAAEqual
		Corner testAABigger_20inner = new Corner(CornerType.DOWN_LEFT, -2, 0);
		Corner testAABigger_30outer = new Corner(CornerType.LEFT_DOWN, -3, 0);
		Corner testAABigger_31outer = new Corner(CornerType.DOWN_RIGHT, -3, -1);
		Corner testAABigger0_1outer = new Corner(CornerType.RIGHT_UP, 0, -1);
		beforeAABiggerPlacedAt = new CornerPosition(-3, -1);
		beforeAABiggerReplacement = Arrays.asList(
				testAABigger_20inner,
				testAABigger_30outer,
				testAABigger_31outer,
				testAABigger0_1outer,
				at02outer);
	}

	@After
	@Override
	public void tearDown() throws Exception {}

	/**
	 * <code>
	 * --+    --+
	 * ..| -> ..|
	 * +-*    ++|
	 *         ++
	 * </code>
	 */
	@Test
	public void BeforeAASmaller() {
		List<Corner> out = new ArrayList<>(5);
		CornerPosition placedAt = SimpleRectanglePlacer.BeforeOuterCorner
				.tryPlace(atminus20outer, at00outer, at02outer, -1, minCorner, maxCorner, 1, 1, out);
		Assert.assertThat(placedAt, equalTo(beforeAASmallPlacedAt));
		Assert.assertThat(out, equalTo(beforeAASmallReplacement));
	}

	/**
	 * <code>
	 * --+    --+
	 * ..| -> ..|
	 * +-*    |.|
	 *        +-+
	 * </code>
	 */
	@Test
	public void BeforeAAEqual() {
		List<Corner> out = new ArrayList<>(5);
		CornerPosition placedAt = SimpleRectanglePlacer.BeforeOuterCorner
				.tryPlace(atminus20outer, at00outer, at02outer, -1, minCorner, maxCorner, 2, 1, out);
		Assert.assertThat(placedAt, equalTo(beforeAAEqualPlacedAt));
		Assert.assertThat(out, equalTo(beforeAAEqualReplacement));
	}

	/**
	 * <code>
	 * ---+    --+
	 *  ..| -> ..|
	 *  +-*   ++.|
	 *        +--+
	 * </code>
	 */
	@Test
	public void BeforeAABigger() {
		List<Corner> out = new ArrayList<>(5);
		CornerPosition placedAt = SimpleRectanglePlacer.BeforeOuterCorner
				.tryPlace(atminus20outer, at00outer, at02outer, -1, minCorner, maxCorner, 3, 1, out);
		Assert.assertThat(placedAt, equalTo(beforeAABiggerPlacedAt));
		Assert.assertThat(out, equalTo(beforeAABiggerReplacement));
	}

	/**
	 * Don't do this, rather place at the inner corner <br>
	 * <code>
	 * --+    --+
	 * ..| /> ..|
	 * +-*    ++|
	 * |      |++
	 * </code>
	 */
	@Test
	public void BeforeBASmaller() {
		List<Corner> out = new ArrayList<>(5);
		CornerPosition placedAt = SimpleRectanglePlacer.BeforeOuterCorner
				.tryPlace(atminus20inner, at00outer, at02outer, -1, minCorner, maxCorner, 1, 1, out);
		Assert.assertThat(placedAt, equalTo(null));
	}

	@Test
	public void BeforeBASame() {
		List<Corner> out = new ArrayList<>(5);
		CornerPosition placedAt = SimpleRectanglePlacer.BeforeOuterCorner
				.tryPlace(atminus20inner, at00outer, at02outer, -1, minCorner, maxCorner, 2, 1, out);
		Assert.assertThat(placedAt, equalTo(null));
	}

	@Test
	public void BeforeBALarger() {
		List<Corner> out = new ArrayList<>(5);
		CornerPosition placedAt = SimpleRectanglePlacer.BeforeOuterCorner
				.tryPlace(atminus20inner, at00outer, at02outer, -1, minCorner, maxCorner, 3, 1, out);
		Assert.assertThat(placedAt, equalTo(null));
	}

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

		List<String> strings = new ArrayList<>();
		strings.add("1");
		strings.add("2");
		strings.add("3");
		strings.add("4");
		strings.add("5");
		strings.add("6");
		strings.add("7");
		strings.add("8");
		RewindableListIterator<String> it = new RewindableListIterator<>(strings.listIterator());
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
