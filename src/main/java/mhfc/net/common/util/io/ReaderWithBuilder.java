package mhfc.net.common.util.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReaderWithBuilder<R, M, B> implements ReadInterface<R, M> {
	private final Supplier<B> builderSupply;
	private final List<BiConsumer<B, R>> buildSteps;
	private final Function<B, M> builderToModel;

	protected ReaderWithBuilder(
			Supplier<B> builderSupply,
			List<BiConsumer<B, R>> buildSteps,
			Function<B, M> builderToModel) {
		super();
		this.builderSupply = Objects.requireNonNull(builderSupply);
		this.buildSteps = new ArrayList<>(buildSteps);
		this.builderToModel = Objects.requireNonNull(builderToModel);
	}

	@Override
	public M read(R raw) {
		B builder = builderSupply.get();
		for (BiConsumer<B, R> step : buildSteps) {
			step.accept(builder, raw);
		}
		return builderToModel.apply(builder);
	}

}
