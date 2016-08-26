package mhfc.net.common.util.parsing.proxies;

import java.util.Map;
import java.util.Objects;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.syntax.special.ISpecialMember;

public class MapProxy implements ISpecialMember {
	private Map<String, ? extends IValueHolder> map;

	/**
	 * @param mapReference
	 *            no defensive copy is made.
	 */
	public MapProxy(Map<String, ? extends IValueHolder> mapReference) {
		this.map = Objects.requireNonNull(map);
	}

	/**
	 * If the key is not found in the map, or it is mapped to <code>null</code>, then an empty Holder is returned.
	 * Otherwise, the result of {@link IValueHolder#snapshot()} on the mapped value is returned.
	 */
	@Override
	public Holder __getattr__(String member) throws Throwable {
		IValueHolder holder = map.get(member);
		if (holder == null) {
			return Holder.empty();
		}
		return holder.snapshot();
	}

}
