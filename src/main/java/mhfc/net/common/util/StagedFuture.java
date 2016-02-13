package mhfc.net.common.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

/**
 * Wrapper for when a {@link CompletableFuture} needs to be returned but only the consumer interface shall be exposed
 *
 * @author WorldSEnder
 *
 * @param <T>
 */
public class StagedFuture<T> {
	private Object holder;

	public <Q extends CompletionStage<T> & Future<T>> StagedFuture(Q future) {
		holder = future;
	}

	@SuppressWarnings("unchecked")
	public CompletionStage<T> asCompletionStage() {
		return (CompletionStage<T>) holder;
	}

	@SuppressWarnings("unchecked")
	public Future<T> asFuture() {
		return (Future<T>) holder;
	}

	public static <T> StagedFuture<T> wrap(CompletableFuture<T> future) {
		return new StagedFuture<>(future);
	}
}
