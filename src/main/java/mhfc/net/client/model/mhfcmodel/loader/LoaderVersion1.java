package mhfc.net.client.model.mhfcmodel.loader;

import java.io.DataInputStream;

import mhfc.net.client.model.mhfcmodel.data.RawModelData;

public class LoaderVersion1 extends VersionizedModelLoader {
	public static final LoaderVersion1 instance = new LoaderVersion1();

	@Override
	public RawModelData loadFromInputStream(int version, DataInputStream is) {
		int nbrParts;

		return null;
	}
}
