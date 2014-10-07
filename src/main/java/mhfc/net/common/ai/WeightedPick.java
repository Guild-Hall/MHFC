package mhfc.net.common.ai;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
/**
 * This selects a Random Item from a list of items.<br>
 *
 * @author WorldSEnder
 *
 */
public class WeightedPick {
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
	 * Randomly picks one of the items in the list if no item returns
	 * <code>true</code> for {@link WeightedItem#forceSelection()}. If an item
	 * does, the first one that does so will be returned.<br>
	 * Each item's weight is determined by {@link WeightedItem#getWeight()}. If
	 * the weight is less than zero it is defaulted to zero. Every items has a
	 * chance of <code>w<sub>i</sub>/sum(w)</code> to be picked. This implies
	 * that items that return a weight of zero will never be picked.<br>
	 * If and only if all items return a weight of zero or the list given is
	 * <code>null</code> this method will return <code>null</code>. Else the
	 * picked item is returned.<br>
	 * Note: This method does not guard against items that return a weight of
	 * infinity. They will be selected almost every time.<br>
	 * This implementation IS threadsafe.
	 *
	 * @param list
	 *            the list to pick items from
	 * @return the picked item
	 */
	public static <T extends WeightedItem> T pickRandom(List<T> list) {
		if (list == null)
			return null;
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
			WeightedItem item = iterator.next();
			if (item.forceSelection()) {
				@SuppressWarnings("unchecked")
				T t = (T) item;
				return t;
			}
			double w = Math.max(0.0d, item.getWeight());
			if (w == 0.0d)
				continue;
			sum += w;
			items[i] = item;
			weights[i] = w;
			i++;
		}
		// If no items
		if (i == 0)
			return null;
		// Generate selection
		double value = rand.nextDouble() * sum;
		sum = 0.0d;
		// Select item from cache
		for (; i > 0;) {
			sum += weights[--i];
			if (sum < value)
				continue;
			@SuppressWarnings("unchecked")
			T t = (T) items[i];
			return t;
		}
		throw new IllegalStateException(
				"value is bigger than the sum of weightcache?");
	}

	public static interface WeightedItem {
		public float getWeight();
		public boolean forceSelection();
	}
}
