package mhfc.net.common.util.math;

public class Interval {
	public static final Interval ZERO_TO_ONE = new Interval(0, 1);
	public final double min;
	public final double max;

	public Interval(double one, double two) {
		if (one > two) {
			min = two;
			max = one;
		} else {
			min = one;
			max = two;
		}
	}

	public Interval intersection(Interval second) {
		if (this == second) {
			return this;
		}
		if (second == null || this.min > second.max || this.max < second.min) {
			return null;
		}
		double newMin = Math.max(this.min, second.min);
		double newMax = Math.min(this.max, second.max);
		return new Interval(newMin, newMax);
	}
}
