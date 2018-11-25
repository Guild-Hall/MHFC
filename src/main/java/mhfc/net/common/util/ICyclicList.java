package mhfc.net.common.util;

import mhfc.net.common.world.controller.Corner;

import java.util.List;

public interface ICyclicList<E> extends List<E> {
	CyclicIterator<Corner> cyclicIterator();

	CyclicIterator<Corner> cyclicIterator(int index);
}
