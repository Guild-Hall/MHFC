package mhfc.net.common.util.io;

public class CombinedReadWriter<R, M> implements IOInterface<R, M> {
	private final ReadInterface<R, M> reader;
	private final WriteInterface<R, M> writer;
	
	public CombinedReadWriter(ReadInterface<R, M> reader, WriteInterface<R, M> writer) {
		super();
		this.reader = reader;
		this.writer = writer;
	}
	
	@Override
	public M read(R raw) {
		return this.reader.read(raw);
	}
	
	@Override
	public R write(M model) {
		return this.writer.write(model);
	}
}
