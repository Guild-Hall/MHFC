package mhfc.net.common.util.io;

public interface ReadInterface<R, M> {
	M read(R raw);
}
