package mhfc.net.common.util;

import java.util.List;

import mhfc.net.common.world.controller.Corner;

public interface ICyclicList<E> extends List<E> {
	CyclicIterator<Corner> cyclicIterator();

	CyclicIterator<Corner> cyclicIterator(int index);
}
