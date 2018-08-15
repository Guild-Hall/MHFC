package mhfc.net.common.worldedit;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;

import javax.xml.ws.Holder;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.function.operation.DelegateOperation;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.RunContext;
import com.sk89q.worldedit.regions.CuboidRegion;

public class RegionSplittingOperation implements Operation {

	private Queue<RegionSpliterator> regionsSplitter;
	private Function<CuboidRegion, Operation> generator;

	public RegionSplittingOperation(CuboidRegion region, Function<CuboidRegion, Operation> generator, int minSize) {
		this.regionsSplitter = new LinkedList<>();
		regionsSplitter.add(new RegionSpliterator(region, minSize));
		this.generator = generator;
	}

	@Override
	public Operation resume(RunContext run) throws WorldEditException {
		if (regionsSplitter.isEmpty()) {
			return null;
		}
		RegionSpliterator polled = regionsSplitter.poll();
		assert (polled != null);
		RegionSpliterator splitOff;
		// Force a split
		while ((splitOff = polled.trySplit()) != null) {
			regionsSplitter.add(splitOff);
		}
		final Holder<Operation> op = new Holder<>();
		if (polled.tryAdvance(e -> {
			op.value = generator.apply(e);
		})) {
			regionsSplitter.add(polled);
			return new DelegateOperation(this, op.value);
		}
		return this;
	}


	@Override
	public void cancel() {
		regionsSplitter.clear();
	}

	@Override
	public void addStatusMessages(List<String> messages) {
		// TODO Auto-generated method stub
		
	}

}
