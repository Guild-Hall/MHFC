package mhfc.net.common.util.parsing.proxies;

import java.util.Map;

import mhfc.net.common.util.Objects;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.syntax.special.ISpecialMember;

public class MapProxy implements ISpecialMember {
	private Map<String, ? extends IValueHolder>[] maps;

	/**
	 * @param mapReference
	 *            no defensive copy is made.
	 */
	@SafeVarargs
	public MapProxy(Map<String, ? extends IValueHolder>... mapReferences) {
		this.maps = Objects.requireNonNullDeep(mapReferences);
	}

	/**
	 * If the key is not found in the map, or it is mapped to <code>null</code>, then an empty Holder is returned.
	 * Otherwise, the result of {@link IValueHolder#snapshot()} on the mapped value is returned.
	 */
	@Override
	public Holder __getattr__(String member) throws Throwable {
		for (Map<String, ? extends IValueHolder> map : maps) {
			IValueHolder holder = map.get(member);
			if (holder == null) {
				continue;
			}
			return holder.snapshot();
		}
		return Holder.empty();
	}

}
