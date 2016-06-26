package mhfc.net.common.world.controller;

import mhfc.net.common.util.ISavableToNBT;

public interface IRectanglePlacer extends ISavableToNBT {

	CornerPosition addRectangle(int sizeX, int sizeY);

}
