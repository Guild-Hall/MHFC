package mhfc.net.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.mojang.realmsclient.util.Pair;

public class MapGraph<N, E> {
	protected Set<N> nodes = new HashSet<>();
	protected List<N> nodesInOrder = new ArrayList<>();
	protected Map<Pair<N, N>, E> graphMap = new HashMap<>();

	public void addNode(N n) {
		if (nodes.add(n)) {
			nodesInOrder.add(n);
		}
	}

	public void addEdge(N begin, N end, E value) {
		if (!nodes.contains(begin) || !nodes.contains(end))
			throw new IllegalArgumentException("Can only insert edge for existing nodes");
		Pair<N, N> key = Pair.of(begin, end);
		graphMap.put(key, value);
	}

	public Set<N> getNodes() {
		return Collections.unmodifiableSet(nodes);
	}

	public E getValue(Pair<N, N> key) {
		return graphMap.get(key);
	}

	public Set<Pair<N, E>> getOutbound(N n) {
		return graphMap.entrySet().stream().filter((entry) -> entry.getKey().first() == n)
				.map((entry) -> Pair.of(entry.getKey().second(), entry.getValue())).collect(Collectors.toSet());
	}

	public int indexOf(N n) {
		return nodesInOrder.indexOf(n);
	}

	public N get(int index) {
		return nodesInOrder.get(index);
	}

}
