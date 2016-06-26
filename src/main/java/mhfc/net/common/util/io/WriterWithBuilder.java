package mhfc.net.common.util.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class WriterWithBuilder<R, M, B> implements WriteInterface<R, M> {
	private final Supplier<B> builderSupplier;
	private final List<BiConsumer<B, M>> storeSteps;
	private final Function<B, R> builderToRaw;

	protected WriterWithBuilder(
			Supplier<B> builderSupplier,
			List<BiConsumer<B, M>> storeSteps,
			Function<B, R> builderToRaw) {
		super();
		this.builderSupplier = Objects.requireNonNull(builderSupplier);
		this.storeSteps = new ArrayList<>(storeSteps);
		this.builderToRaw = Objects.requireNonNull(builderToRaw);
	}

	@Override
	public R write(M model) {
		B builder = builderSupplier.get();
		for (BiConsumer<B, M> step : storeSteps) {
			step.accept(builder, model);
		}
		return builderToRaw.apply(builder);
	}

}
