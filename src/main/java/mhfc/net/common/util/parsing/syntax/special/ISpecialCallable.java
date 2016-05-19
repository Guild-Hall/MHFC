package mhfc.net.common.util.parsing.syntax.special;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.valueholders.Arguments;

/**
 * Represents a callable class.
 *
 * @author WorldSEnder
 *
 */
public interface ISpecialCallable {
	/**
	 * Invokes this object with the arguments args.
	 *
	 * @param args
	 * @return
	 */
	Holder __call__(Arguments args) throws Throwable;
}
