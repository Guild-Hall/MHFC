package mhfc.net.common.ai.nodes;

public interface IAINode<T> {
	/**
	 * The first invocation of an ai node.
	 *
	 * @return
	 */
	NodeResult<T> begin();
}
