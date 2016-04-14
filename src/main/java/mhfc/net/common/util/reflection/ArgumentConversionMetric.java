package mhfc.net.common.util.reflection;

/**
 * A class recording all conversions necessary to convert some value to some other value, e.g. in an assignement
 * expression.<br>
 * Works about the same as css Specificity. Used to decide overload.
 * https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.12.2
 *
 * @author WorldSEnder
 *
 */
public class ArgumentConversionMetric implements Comparable<ArgumentConversionMetric> {

	public ArgumentConversionMetric() {}

	@Override
	public int compareTo(ArgumentConversionMetric o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
