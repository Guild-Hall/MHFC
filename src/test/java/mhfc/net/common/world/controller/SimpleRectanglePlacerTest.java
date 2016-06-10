package mhfc.net.common.world.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import junit.framework.TestCase;
import mhfc.net.common.util.CyclicIterator;
import mhfc.net.common.world.controller.SimpleRectanglePlacer.CornerList;
import mhfc.net.common.world.controller.SimpleRectanglePlacer.IRectanglePlacingFunction;

@RunWith(value = BlockJUnit4ClassRunner.class)
public class SimpleRectanglePlacerTest extends TestCase {
	private void basicABATestCase(IRectanglePlacingFunction func, int inSize, int outSize, List<Corner> expected) {
		List<Corner> out = new ArrayList<>(5);
		CornerPosition placedAt = func
				.tryPlace(at20outer, at00inner, at02outer, -1, minCorner, maxCorner, inSize, outSize, out);
		Assert.assertThat(placedAt, IsEqual.equalTo(zerozero));
		Assert.assertThat(out, IsEqual.equalTo(expected));
	}

	private void basicAATestCase(
			IRectanglePlacingFunction func,
			int inSize,
			int outSize,
			CornerPosition placement,
			List<Corner> expected) {
		List<Corner> out = new ArrayList<>(5);
		CornerPosition placedAt = func
				.tryPlace(at_20outer, at00outer, at02outer, -1, minCorner, maxCorner, inSize, outSize, out);
		Assert.assertThat(placedAt, IsEqual.equalTo(placement));
		Assert.assertThat(out, IsEqual.equalTo(expected));
	}

	private void basicBABTestCase(IRectanglePlacingFunction func, int inSize, int outSize) {
		List<Corner> out = new ArrayList<>(5);
		CornerPosition placedAt = func
				.tryPlace(at_20inner, at00outer, at02inner, -1, minCorner, maxCorner, inSize, outSize, out);
		Assert.assertThat(placedAt, IsEqual.equalTo(null));
	}

	private Corner at02outer, at02inner, at00outer, at00inner, at20outer, at_20outer, at_20inner;
	private CornerPosition minCorner, maxCorner, zerozero;

	private CornerPosition beforeAASmallPlacedAt;
	private List<Corner> beforeAASmallReplacement;
	private CornerPosition beforeAAEqualPlacedAt;
	private List<Corner> beforeAAEqualReplacement;
	private CornerPosition beforeAABiggerPlacedAt;
	private List<Corner> beforeAABiggerReplacement;

	private CornerPosition afterAASmallPlacedAt;
	private List<Corner> afterAASmallReplacement;
	private CornerPosition afterAAEqualPlacedAt;
	private List<Corner> afterAAEqualReplacement;
	private CornerPosition afterAABiggerPlacedAt;
	private List<Corner> afterAABiggerReplacement;

	private List<Corner> ABASmallSmallReplacement;
	private List<Corner> ABASmallEqualReplacement;
	private List<Corner> ABASmallLargeReplacement;
	private List<Corner> ABAEqualSmallReplacement;
	private List<Corner> ABAEqualEqualReplacement;
	private List<Corner> ABAEqualLargeReplacement;
	private List<Corner> ABALargeSmallReplacement;
	private List<Corner> ABALargeEqualReplacement;
	private List<Corner> ABALargeLargeReplacement;

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
		at20outer = new Corner(CornerType.UP_LEFT, 2, 0);
		at_20outer = new Corner(CornerType.DOWN_RIGHT, -2, 0);
		at_20inner = new Corner(CornerType.UP_RIGHT, -2, 0);
		zerozero = new CornerPosition(0, 0);
		minCorner = zerozero;
		maxCorner = zerozero;
		// BeforeAASmaller
		Corner testAASmall_10inner = new Corner(CornerType.RIGHT_DOWN, -1, 0);
		Corner testAASmall_1_1outer = new Corner(CornerType.DOWN_RIGHT, -1, -1);
		Corner testAASmall0_1outer = new Corner(CornerType.RIGHT_UP, 0, -1);
		beforeAASmallPlacedAt = new CornerPosition(-1, -1);
		beforeAASmallReplacement = Arrays
				.asList(at_20outer, testAASmall_10inner, testAASmall_1_1outer, testAASmall0_1outer, at02outer);
		// BeforeAAEqual
		Corner testAAEqual_2_1outer = new Corner(CornerType.DOWN_RIGHT, -2, -1);
		Corner testAAEqual0_1outer = new Corner(CornerType.RIGHT_UP, 0, -1);
		beforeAAEqualPlacedAt = new CornerPosition(-2, -1);
		beforeAAEqualReplacement = Arrays.asList(testAAEqual_2_1outer, testAAEqual0_1outer, at02outer);
		// BeforeAABigger
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
		// AfterAASmaller
		Corner testAASmall10outer = new Corner(CornerType.RIGHT_UP, 1, 0);
		Corner testAASmall11outer = new Corner(CornerType.UP_LEFT, 1, 1);
		Corner testAASmall01inner = new Corner(CornerType.LEFT_UP, 0, 1);
		afterAASmallPlacedAt = new CornerPosition(0, 0);
		afterAASmallReplacement = Arrays
				.asList(at_20outer, testAASmall10outer, testAASmall11outer, testAASmall01inner, at02outer);
		// AfterAAEqual
		Corner testAAEqual10outer = new Corner(CornerType.RIGHT_UP, 1, 0);
		Corner testAAEqual12outer = new Corner(CornerType.UP_LEFT, 1, 2);
		afterAAEqualPlacedAt = new CornerPosition(0, 0);
		afterAAEqualReplacement = Arrays.asList(at_20outer, testAAEqual10outer, testAAEqual12outer);
		// AfterAABigger
		Corner testAABigger10outer = new Corner(CornerType.RIGHT_UP, 1, 0);
		Corner testAABigger13outer = new Corner(CornerType.UP_LEFT, 1, 3);
		Corner testAABigger03outer = new Corner(CornerType.LEFT_DOWN, 0, 3);
		Corner testAABigger02inner = new Corner(CornerType.DOWN_LEFT, 0, 2);
		afterAABiggerPlacedAt = new CornerPosition(0, 0);
		afterAABiggerReplacement = Arrays
				.asList(at_20outer, testAABigger10outer, testAABigger13outer, testAABigger03outer, testAABigger02inner);

		// BBBeforeSmall
		Corner testABASmall10inner = new Corner(CornerType.LEFT_UP, 1, 0);
		// BBBeforeLarger
		Corner testABALarge30outer = new Corner(CornerType.RIGHT_UP, 3, 0);
		Corner testABALarge20inner = new Corner(CornerType.UP_RIGHT, 2, 0);
		// BBAfterSmall
		Corner testABASmall01inner = new Corner(CornerType.LEFT_UP, 0, 1);
		// BBAfterLarger
		Corner testABALarge03outer = new Corner(CornerType.LEFT_DOWN, 0, 3);
		Corner testABALarge02inner = new Corner(CornerType.DOWN_LEFT, 0, 2);

		Corner testABA11outer = new Corner(CornerType.UP_LEFT, 1, 1);
		Corner testABA12outer = new Corner(CornerType.UP_LEFT, 1, 2);
		Corner testABA21outer = new Corner(CornerType.UP_LEFT, 2, 1);
		Corner testABA13outer = new Corner(CornerType.UP_LEFT, 1, 3);
		Corner testABA22outer = new Corner(CornerType.UP_LEFT, 2, 2);
		Corner testABA31outer = new Corner(CornerType.UP_LEFT, 3, 1);
		Corner testABA23outer = new Corner(CornerType.UP_LEFT, 2, 3);
		Corner testABA32outer = new Corner(CornerType.UP_LEFT, 3, 2);
		Corner testABA33outer = new Corner(CornerType.UP_LEFT, 3, 3);

		ABASmallSmallReplacement = Arrays
				.asList(at20outer, testABASmall10inner, testABA11outer, testABASmall01inner, at02outer);
		ABASmallEqualReplacement = Arrays.asList(at20outer, testABASmall10inner, testABA12outer);
		ABASmallLargeReplacement = Arrays
				.asList(at20outer, testABASmall10inner, testABA13outer, testABALarge03outer, testABALarge02inner);

		ABAEqualSmallReplacement = Arrays.asList(testABA21outer, testABASmall01inner, at02outer);
		ABAEqualEqualReplacement = Arrays.asList(testABA22outer);
		ABAEqualLargeReplacement = Arrays.asList(testABA23outer, testABALarge03outer, testABALarge02inner);

		ABALargeSmallReplacement = Arrays
				.asList(testABALarge20inner, testABALarge30outer, testABA31outer, testABASmall01inner, at02outer);
		ABALargeEqualReplacement = Arrays.asList(testABALarge20inner, testABALarge30outer, testABA32outer);
		ABALargeLargeReplacement = Arrays.asList(
				testABALarge20inner,
				testABALarge30outer,
				testABA33outer,
				testABALarge03outer,
				testABALarge02inner);

	}

	@After
	@Override
	public void tearDown() throws Exception {}

	/**
	 * <pre>
	 * {@code
	 * --+    --+
	 * ..| -> ..|
	 * +-*    ++|
	 *         ++
	 * }
	 * </pre>
	 */
	@Test
	public void BeforeAASmaller() {
		basicAATestCase(SimpleRectanglePlacer.BeforeOuterCorner, 1, 1, beforeAASmallPlacedAt, beforeAASmallReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 * --+    --+
	 * ..| -> ..|
	 * +-*    |.|
	 *        +-+
	 * }
	 * </pre>
	 */
	@Test
	public void BeforeAAEqual() {
		basicAATestCase(SimpleRectanglePlacer.BeforeOuterCorner, 2, 1, beforeAAEqualPlacedAt, beforeAAEqualReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 * ---+    --+
	 *  ..| -> ..|
	 *  +-*   ++.|
	 *        +--+
	 * }
	 * </pre>
	 */
	@Test
	public void BeforeAABigger() {
		basicAATestCase(
				SimpleRectanglePlacer.BeforeOuterCorner,
				3,
				1,
				beforeAABiggerPlacedAt,
				beforeAABiggerReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 * --+    --+
	 * ..| -> ..++
	 * +-*    +--+
	 * }
	 * </pre>
	 */
	@Test
	public void AfterAASmaller() {
		basicAATestCase(SimpleRectanglePlacer.AfterOuterCorner, 1, 1, afterAASmallPlacedAt, afterAASmallReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 * --+    ---+
	 * ..| -> ...|
	 * +-*    +--+
	 * }
	 * </pre>
	 */
	@Test
	public void AfterAAEqual() {
		basicAATestCase(SimpleRectanglePlacer.AfterOuterCorner, 1, 2, afterAAEqualPlacedAt, afterAAEqualReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 *         ++
	 * --+    -+|
	 * ..| -> ..|
	 * +-*    +-+
	 * }
	 * </pre>
	 */
	@Test
	public void AfterAABigger() {
		basicAATestCase(SimpleRectanglePlacer.AfterOuterCorner, 1, 3, afterAABiggerPlacedAt, afterAABiggerReplacement);
	}

	@Test
	public void BeforeBASmaller() {
		basicBABTestCase(SimpleRectanglePlacer.BeforeOuterCorner, 1, 1);
	}

	@Test
	public void BeforeBAEqual() {
		basicBABTestCase(SimpleRectanglePlacer.BeforeOuterCorner, 2, 1);
	}

	@Test
	public void BeforeBALarger() {
		basicBABTestCase(SimpleRectanglePlacer.BeforeOuterCorner, 3, 1);
	}

	@Test
	public void AfterABSmaller() {
		basicBABTestCase(SimpleRectanglePlacer.AfterOuterCorner, 1, 1);
	}

	@Test
	public void AfterABEqual() {
		basicBABTestCase(SimpleRectanglePlacer.AfterOuterCorner, 1, 2);
	}

	@Test
	public void AfterABLarger() {
		basicBABTestCase(SimpleRectanglePlacer.AfterOuterCorner, 1, 3);
	}

	/**
	 * <pre>
	 * {@code
	 * +      +
	 * |   -> ++
	 * +-+    .++
	 * }
	 * </pre>
	 */
	@Test
	public void AtABASmallSmall() {
		basicABATestCase(SimpleRectanglePlacer.AtInnerCorner, 1, 1, ABASmallSmallReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 * +      -+
	 * |   -> .|
	 * +-+    .++
	 * }
	 * </pre>
	 */
	@Test
	public void AtABASmallEqual() {
		basicABATestCase(SimpleRectanglePlacer.AtInnerCorner, 1, 2, ABASmallEqualReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 *        ++
	 * +      +|
	 * |   -> .|
	 * +-+    .++
	 * }
	 * </pre>
	 */
	@Test
	public void AtABASmallLarge() {
		basicABATestCase(SimpleRectanglePlacer.AtInnerCorner, 1, 3, ABASmallLargeReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 * +      +
	 * |   -> +-+
	 * +-+    ..|
	 * }
	 * </pre>
	 */
	@Test
	public void AtABAEqualSmall() {
		basicABATestCase(SimpleRectanglePlacer.AtInnerCorner, 2, 1, ABAEqualSmallReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 * +      --+
	 * |   -> ..|
	 * +-+    ..|
	 * }
	 * </pre>
	 */
	@Test
	public void AtABAEqualEqual() {
		basicABATestCase(SimpleRectanglePlacer.AtInnerCorner, 2, 2, ABAEqualEqualReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 *        +-+
	 * +      +.|
	 * |   -> ..|
	 * +-+    ..|
	 * }
	 * </pre>
	 */
	@Test
	public void AtABAEqualLarge() {
		basicABATestCase(SimpleRectanglePlacer.AtInnerCorner, 2, 3, ABAEqualLargeReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 * +      +
	 * |   -> +--+
	 * +-+    ..++
	 * }
	 * </pre>
	 */
	@Test
	public void AtABALargeSmall() {
		basicABATestCase(SimpleRectanglePlacer.AtInnerCorner, 3, 1, ABALargeSmallReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 * +      ---+
	 * |   -> ...|
	 * +-+    ..++
	 * }
	 * </pre>
	 */
	@Test
	public void AtABALargeEqual() {
		basicABATestCase(SimpleRectanglePlacer.AtInnerCorner, 3, 2, ABALargeEqualReplacement);
	}

	/**
	 * <pre>
	 * {@code
	 *        +--+
	 * +      +..|
	 * |   -> ...|
	 * +-+    ..++
	 * }
	 * </pre>
	 */
	@Test
	public void AtABALargeLarge() {
		basicABATestCase(SimpleRectanglePlacer.AtInnerCorner, 3, 3, ABALargeLargeReplacement);
	}

	@Test
	public void simpleInvariantFix() {
		List<Corner> test = new ArrayList<>(
				Arrays.asList(
						new Corner(CornerType.RIGHT_DOWN, 1, 4),
						new Corner(CornerType.DOWN_RIGHT, 1, 2),
						new Corner(CornerType.RIGHT_DOWN, 2, 2),
						new Corner(CornerType.DOWN_RIGHT, 2, 0),
						//
						new Corner(CornerType.RIGHT_UP, 4, 0),
						new Corner(CornerType.UP_RIGHT, -4, 0),
						new Corner(CornerType.RIGHT_UP, 0, 0),
						new Corner(CornerType.UP_RIGHT, 0, 4)));
		SimpleRectanglePlacer.fixInvariant(new CyclicIterator<>(test, 7), true);
		Assert.assertThat(
				test,
				IsEqual.equalTo(
						Arrays.asList(new Corner(CornerType.RIGHT_UP, 4, 0), new Corner(CornerType.UP_RIGHT, -4, 0))));
	}

	private void checkNotBothInner(Corner one, Corner two) {
		TestCase.assertTrue(one.type.isOuter() || two.type.isOuter());
	}

	private void checkCornerList(CornerList list) {
		checkNotBothInner(list.get(0), list.get(list.size() - 1));
		Corner previous = list.get(0);
		Iterator<Corner> listIt = list.listIterator(1);
		while (listIt.hasNext()) {
			Corner curr = listIt.next();
			checkNotBothInner(previous, curr);
			previous = curr;
		}
	}

	@Test
	public void fullTest() {
		SimpleRectanglePlacer placer = new SimpleRectanglePlacer();
		Random rand = new Random();

		for (int i = 0; i < 25; i++) {
			int sizeX = rand.nextInt(10) + 1, sizeZ = rand.nextInt(10) + 1;
			placer.addRectangle(sizeX, sizeZ);
			//			System.out.print(sizeX + " " + sizeZ + "->");
			//			System.out.println(placedAt);
			//			System.out.println(placer);
			checkCornerList(placer.getCorners());
			int count = 0;
			for (Corner c : placer.getCorners()) {
				count += c.type.isOuter ? 1 : -1;
			}
			Assert.assertTrue(count == 4);
		}
	}
}
