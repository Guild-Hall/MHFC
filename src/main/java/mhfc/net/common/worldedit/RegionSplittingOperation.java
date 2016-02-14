package mhfc.net.common.worldedit;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.operation.RunContext;
import com.sk89q.worldedit.regions.CuboidRegion;

public class RegionSplittingOperation implements Operation {

	private Queue<RegionSpliterator> regionSplitter;
	private Function<CuboidRegion, Operation> generator;
	private int maxSize;

	public RegionSplittingOperation(CuboidRegion region, Function<CuboidRegion, Operation> generator, int maxSize) {
		this.regionSplitter = new LinkedList<>();
		this.regionSplitter.add(new RegionSpliterator(region));
		this.generator = generator;
		this.maxSize = maxSize;
	}

	@Override
	public Operation resume(RunContext run) throws WorldEditException {
		RegionSpliterator toSplit = regionSplitter.poll();
		while (maxSize < toSplit.estimateBlocksAtOnce()) {
			RegionSpliterator split = toSplit.trySplit();
			if (split == null)
				break;
			regionSplitter.add(split);
		}
		if (toSplit.tryAdvance((e) -> {
			Operation op = generator.apply(e);
			try {
				Operations.complete(op);
			} catch (Exception e1) {}
		})) {
			regionSplitter.add(toSplit);
		}
		return regionSplitter.isEmpty() ? null : this;
	}

	@Override
	public void cancel() {
		regionSplitter.clear();
	}

}
