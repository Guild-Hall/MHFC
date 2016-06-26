package mhfc.net.common.util;

public interface IRewindable {
	void mark();

	void clearMark();

	IRewindable rewind();
}
