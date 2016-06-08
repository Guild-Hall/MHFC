package mhfc.net.common.util.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.model.ModelFormatException;

/**
 * {@link IOInterface} implementations for {@link NBTTagCompound} as raw data type.
 *
 * <p>
 * Usage: <code>
 * // TODO: insert usage
 * </code>
 *
 * @author WorldSEnder
 *
 */
public class NBTIO {
	private static <M, B> NBTIOOperation<M, B> combine(NBTReadOperation<B> read, NBTWriteOperation<M> write) {
		return new NBTIOOperation<M, B>() {
			@Override
			public Optional<RuntimeException> read(B builder, NBTTagCompound tag) {
				return read.read(builder, tag);
			}

			@Override
			public void write(NBTTagCompound builtTag, M model) {
				write.write(builtTag, model);
			}
		};
	}

	private static Optional<RuntimeException> illegalFormat(String message) {
		return Optional.of(new ModelFormatException(message));
	}

	private static Optional<RuntimeException> success() {
		return Optional.empty();
	}

	// === Interfaces
	public static interface NBTReadOperation<B> {
		// null if successful
		Optional<RuntimeException> read(B builder, NBTTagCompound tag);
	}

	public static interface NBTWriteOperation<M> {
		// always successful
		void write(NBTTagCompound builtTag, M model);
	}

	public static interface NBTIOOperation<M, B> extends NBTReadOperation<B>, NBTWriteOperation<M> {}

	// === Operations
	public static <B> NBTReadOperation<B> readInt(String nbtTagName, ObjIntConsumer<B> application) {
		return (b, tag) -> {
			if (!tag.hasKey(nbtTagName, net.minecraftforge.common.util.Constants.NBT.TAG_INT)) {
				return illegalFormat("no key " + nbtTagName + " of type int");
			}
			int i = tag.getInteger(nbtTagName);
			application.accept(b, i);
			return success();
		};
	}

	public static <M> NBTWriteOperation<M> writeInt(String nbtTagName, ToIntFunction<M> retrieve) {
		return (tag, model) -> {
			tag.setInteger(nbtTagName, retrieve.applyAsInt(model));
		};
	}

	public static <B, M> NBTIOOperation<M, B> readWriteInt(
			String nbtTagName,
			ObjIntConsumer<B> application,
			ToIntFunction<M> retrieve) {
		return combine(readInt(nbtTagName, application), writeInt(nbtTagName, retrieve));
	}

	public static <B, N> NBTReadOperation<B> readNested(
			String nbtTagName,
			BiConsumer<B, N> application,
			ReadInterface<NBTTagCompound, N> reader) {
		return (b, tag) -> {
			if (!tag.hasKey(nbtTagName, net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND)) {
				return illegalFormat("no key " + nbtTagName + " of type compound");
			}
			N nested = reader.read(tag.getCompoundTag(nbtTagName));
			application.accept(b, nested);
			return success();
		};
	}

	public static <M, N> NBTWriteOperation<M> writeNested(
			String nbtTagName,
			Function<M, N> retrieve,
			WriteInterface<NBTTagCompound, N> writer) {
		return (tag, model) -> {
			NBTTagCompound nestedTag = writer.write(retrieve.apply(model));
			tag.setTag(nbtTagName, nestedTag);
		};
	}

	public static <B, M, N> NBTIOOperation<M, B> readWriteNested(
			String nbtTagName,
			BiConsumer<B, N> application,
			Function<M, N> retrieve,
			IOInterface<NBTTagCompound, N> nestedIO) {
		return combine(readNested(nbtTagName, application, nestedIO), writeNested(nbtTagName, retrieve, nestedIO));
	}

	/**
	 * Tries all reads after one another from the first to the last. Can be used for version control. The newest is
	 * implemented first, but will fail if an older version is stored.
	 *
	 * @param reads
	 * @return
	 */
	@SafeVarargs
	public static <B> NBTReadOperation<B> cascadingRead(NBTReadOperation<B> first, NBTReadOperation<B>... reads) {
		Objects.requireNonNull(first);
		mhfc.net.common.util.Objects.requireNonNullDeep(reads);
		return (b, tag) -> {
			Optional<RuntimeException> lastExc = first.read(b, tag);
			if (!lastExc.isPresent()) {
				return success();
			}
			for (NBTReadOperation<B> r : reads) {
				Optional<RuntimeException> result = lastExc = r.read(b, tag);
				if (result.isPresent()) {
					continue;
				}
				return success();
			}
			return lastExc;
		};
	}

	@SafeVarargs
	public static <B, M> NBTIOOperation<M, B> cascadingReadWrite(
			NBTIOOperation<M, B> readWrite,
			NBTReadOperation<B>... reads) {
		//mhfc.net.common.util.Objects.requireNonNullDeep(reads);
		return combine(cascadingRead(readWrite, reads), readWrite);
	}

	// === Builder classes
	public static class NBTReaderBuilder<M, B> {
		private Supplier<B> builderSupply;
		private Function<B, M> finalOperation;
		private List<BiConsumer<B, NBTTagCompound>> buildSteps;

		private BiConsumer<B, NBTTagCompound> readSafe(NBTReadOperation<B> reader) {
			Objects.requireNonNull(reader);
			return (b, tag) -> {
				Optional<RuntimeException> onError = reader.read(b, tag);
				if (!onError.isPresent()) {
					return;
				}
				throw onError.get();
			};
		}

		public NBTReaderBuilder(Supplier<B> builderSupply, Function<B, M> finalOperation) {
			this.builderSupply = Objects.requireNonNull(builderSupply);
			this.finalOperation = Objects.requireNonNull(finalOperation);
			this.buildSteps = new ArrayList<>();
		}

		public NBTReaderBuilder<M, B> with(NBTReadOperation<B> operation) {
			this.buildSteps.add(readSafe(operation));
			return this;
		}

		public ReadInterface<NBTTagCompound, M> buildReader() {
			return new ReaderWithBuilder<>(builderSupply, buildSteps, finalOperation);
		}
	}

	public static class NBTWriterBuilder<M> {
		private List<BiConsumer<NBTTagCompound, M>> consumers;

		private BiConsumer<NBTTagCompound, M> writeSafe(NBTWriteOperation<M> writer) {
			return writer::write;
		}

		public NBTWriterBuilder() {
			this.consumers = new ArrayList<>();
		}

		public NBTWriterBuilder<M> with(NBTWriteOperation<M> operation) {
			this.consumers.add(writeSafe(operation));
			return this;
		}

		public WriteInterface<NBTTagCompound, M> buildWriter() {
			return new WriterWithBuilder<>(NBTTagCompound::new, consumers, Function.identity());
		}
	}

	public static class NBTIOBuilder<M, B> {
		private NBTReaderBuilder<M, B> readerBuild;
		private NBTWriterBuilder<M> writerBuild;

		public NBTIOBuilder(Supplier<B> builderSupply, Function<B, M> finalOperation) {
			readerBuild = new NBTReaderBuilder<>(builderSupply, finalOperation);
			writerBuild = new NBTWriterBuilder<>();
		}

		public NBTIOBuilder<M, B> with(NBTIOOperation<M, B> operation) {
			this.readerBuild.with(operation);
			this.writerBuild.with(operation);
			return this;
		}

		public IOInterface<NBTTagCompound, M> build() {
			return new CombinedReadWriter<>(readerBuild.buildReader(), writerBuild.buildWriter());
		}
	}
}
