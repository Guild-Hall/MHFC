package mhfc.net.common.ai.general;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import mhfc.net.MHFCMain;

/**
 * This selects a Random Item from a list of items.<br>
 * This class is written to be thread-safe.<br>
 * BEWARE: Re-entering any picking method (recursive calls) of this class (from the same thread) will result in a crash.
 *
 * @author WorldSEnder
 *
 */
public class WeightedPick {
	/**
	 * Making the whole thing relatively thread-safe. ONLY PROBLEM: re-entry will result in a crash.
	 */
	private static final ThreadLocal<Integer> size;
	private static final ThreadLocal<WeightedItem[]> itemcache;
	private static final ThreadLocal<double[]> weightcache;
	private static final Random rand = new Random();
	static {
		size = new ThreadLocal<Integer>() {
			@Override
			protected Integer initialValue() {
				return 0x10;
			}
		};
		itemcache = new ThreadLocal<WeightedItem[]>() {
			@Override
			protected WeightedItem[] initialValue() {
				return new WeightedItem[size.get()];
			}
		};
		weightcache = new ThreadLocal<double[]>() {
			@Override
			protected double[] initialValue() {
				return new double[size.get()];
			}
		};
	}

	private static void rescale(int minSize) {
		renewCache(minSize); // Maybe another size??!
	}

	private static void renewCache(int newSize) {
		size.set(newSize);
		itemcache.set(new WeightedItem[size.get()]);
		weightcache.set(new double[size.get()]);
	}

	/**
	 * Randomly picks one of the items in the list if no item returns <code>true</code> for
	 * {@link WeightedItem#forceSelection()}. If an item does, the first one that does so will be returned.<br>
	 * Each item's weight is determined by {@link WeightedItem#getWeight()}. If the weight is less than zero it is
	 * defaulted to zero. Every items has a chance of <code>w<sub>i</sub>/sum(w)</code> to be picked. This implies that
	 * items that return a weight of zero will never be picked.<br>
	 * If and only if all items return a weight of zero or the list given is <code>null</code> this method will return
	 * <code>null</code>. Else the picked item is returned.<br>
	 * Note: This method does not guard against items that return a weight of infinity. They will be selected almost
	 * every time.<br>
	 * This implementation IS threadsafe.
	 *
	 * @param list
	 *            the list to pick items from
	 * @return the picked item
	 */
	public static <T extends WeightedItem> T pickRandom(Collection<T> list) {
		if (list == null) {
			MHFCMain.logger().debug("List supplied to random pick null. Is some IAttackManager invalid?");
			return null;
		}
		WeightedItem[] items = itemcache.get();
		double[] weights = weightcache.get();
		// Check cache size
		int listsize = list.size();
		if (listsize > size.get()) {
			rescale(listsize);
		}
		// Collect items in cache and sum
		double sum = 0.0D;
		int i = 0;
		for (Iterator<T> iterator = list.iterator(); iterator.hasNext();) {
			T item = iterator.next();
			if (item.forceSelection()) {
				return item;
			}
			double w = Math.max(0.0d, item.getWeight());
			if (w <= 0.0d)
				continue;
			sum += w;
			items[i] = item;
			weights[i] = w;
			i++;
		}
		int index = pickRandomIndex(weights, i, sum);
		if (index < 0) {
			return null;
		}
		@SuppressWarnings("unchecked")
		T t = (T) items[index];
		return t;
	}

	private static int pickRandomIndex(double weights[], int count, double sum) {
		// If no items
		if (count <= 0) {
			return -1;
		}
		// Generate selection
		double value = rand.nextDouble() * sum;
		sum = 0.0d;
		// Select item from cache
		for (; count > 0;) {
			sum += weights[--count];
			if (sum < value)
				continue;
			return count;
		}
		return -1;
	}

	public static interface WeightedItem {
		/**
		 * Returns the (positive) weight of this item. The chance of this item being selected is
		 * <code>(weight/sum)</code> where <code>sum</code> is the sum of all weights of all items to be picked out of.
		 * <br>
		 * Returning a negative number or zero ensures that this item will not be selected.
		 *
		 * @return the weight to be selected
		 */
		public float getWeight();

		/**
		 * Return <code>true</code> to instantly select this item before chances have been taken for other items.<br>
		 * This ensures that any item return <code>true</code> here will be selected if it is the first item to do so.
		 *
		 * @return <code>true</code> if this item should always be selected
		 */
		public boolean forceSelection();
	}
}
