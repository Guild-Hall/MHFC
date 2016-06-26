package mhfc.net.common.util.parsing.syntax.special;

import mhfc.net.common.util.parsing.Holder;

/**
 * Represents a class that has a special way to retrieve members (apart from the ones that are already there)
 *
 * @author WorldSEnder
 *
 */
public interface ISpecialMember {
	/**
	 * Retrieves the value of the member name
	 *
	 * @param member
	 * @return
	 */
	Holder __getattr__(String member) throws Throwable;
}
