package mhfc.net.client.particle.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import mhfc.net.client.particle.api.TextureSewer.IParticleSpriteReceiver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public abstract class PWorkshop<F extends PWorkshop<?, T>, T extends Particle> {

	public static class ImmutableParticleArgs {
		public final World world;
		public final double x, y, z, motionX, motionY, motionZ;
		public final float scale;
		public final int color;
		public final Object[] data;

		public ImmutableParticleArgs(World world, double x, double y, double z, ParticleArgs builder) {
			this.world = world;
			this.x = x;
			this.y = y;
			this.z = z;
			this.motionX = builder.motionX;
			this.motionY = builder.motionY;
			this.motionZ = builder.motionZ;
			this.scale = builder.scale;
			this.color = builder.color;
			this.data = builder.data;
			builder.reset();
		}
	}

	public static class ParticleArgs<T extends ParticleArgs> implements Consumer<ParticleArgs> {
		public static final class ArgumentDataBuilder<T extends ParticleArgs> {
			private int highestIndex = 0;
			private final Map<Integer, Object> setArgs;
			private final T args;

			private ArgumentDataBuilder(T args) {
				this.setArgs = new HashMap<Integer, Object>();
				this.args = args;
			}

			public ArgumentDataBuilder<T> setData(int index, Object... data) {
				if (index + data.length - 1 > this.highestIndex)
					this.highestIndex = index + data.length - 1;
				for (int i = 0; i < data.length; i++)
					this.setArgs.put(index + i, data[i]);
				return this;
			}

			public ArgumentDataBuilder<T> setEmpty(int index) {
				return this.setData(index, EMPTY_ARG);
			}

			public T buildData() {
				Object[] data = new Object[this.highestIndex + 1];
				for (int i = 0; i < data.length; i++) {
					data[i] = EMPTY_ARG;
				}
				Iterator<Entry<Integer, Object>> dataIT = this.setArgs.entrySet().iterator();
				Entry<Integer, Object> entry;
				while (dataIT.hasNext()) {
					entry = dataIT.next();
					data[entry.getKey()] = entry.getValue();
				}
				this.args.withData(data);
				return this.args;
			}
		}

		private static final ParticleArgs BUILDER = new ParticleArgs();

		private static final Object[] NO_DATA = new Object[0];

		private boolean motionSet = false;
		private double motionX, motionY, motionZ;
		private boolean scaleSet = false;
		private float scale;
		private boolean colorSet = false;
		private int color;
		private Object[] data;
		private boolean dataSet = false;

		private ParticleArgs() {
			this.reset();
		}

		public final void reset() {
			this.motionX = 0;
			this.motionY = 0;
			this.motionZ = 0;
			this.scale = 1;
			this.color = 0xFFFFFFFF;
			this.data = NO_DATA;
			this.dataSet = false;
			this.motionSet = false;
			this.scaleSet = false;
			this.colorSet = false;
		}

		public final T accept(Consumer<ParticleArgs> consumer) {
			consumer.accept(this);
			return (T) this;
		}

		public final T withMotion(double motionX, double motionY, double motionZ) {
			this.motionX = motionX;
			this.motionY = motionY;
			this.motionZ = motionZ;
			this.motionSet = true;
			return (T) this;
		}

		public final T withScale(float scale) {
			this.scale = scale;
			this.scaleSet = true;
			return (T) this;
		}

		public final T withColor(int color) {
			this.color = color;
			this.colorSet = true;
			return (T) this;
		}

		public final T withData(Object... data) {
			if (data == null)
				data = NO_DATA;
			this.data = data;
			this.dataSet = true;
			return (T) this;
		}

		public final ArgumentDataBuilder<T> withDataBuilder() {
			return new ArgumentDataBuilder<T>((T) this);
		}

		public final double getMotionX() {
			return this.motionX;
		}

		public final double getMotionY() {
			return this.motionY;
		}

		public final double getMotionZ() {
			return this.motionZ;
		}

		public final boolean isMotionSet() {
			return this.motionSet;
		}

		public final float getScale() {
			return this.scale;
		}

		public final boolean isScaleSet() {
			return this.scaleSet;
		}

		public final int getColor() {
			return this.color;
		}

		public final boolean isColorSet() {
			return this.colorSet;
		}

		public final Object[] getData() {
			return this.data;
		}

		public final boolean isDataSet() {
			return this.dataSet;
		}

		public static ParticleArgs get() {
			BUILDER.reset();
			return BUILDER;
		}

		public final ParticleArgs populateEmptyArgs(ParticleArgs container) {
			if (!container.isMotionSet()) {
				container.withMotion(this.getMotionX(), this.getMotionY(), this.getMotionZ());
			}
			if (!container.isColorSet()) {
				container.withColor(this.getColor());
			}
			if (!container.isScaleSet()) {
				container.withScale(this.getScale());
			}
			if (this.isDataSet()) {
				Object[] initialAdditionalArgs = container.getData();
				Object[] defaultArgs = this.getData();
				Object[] additionalArgs = container.getData();
				if (defaultArgs.length > initialAdditionalArgs.length) {
					container.withData(additionalArgs = new Object[defaultArgs.length]);
				}
				for (int i = 0; i < additionalArgs.length; i++) {
					if (i < initialAdditionalArgs.length) {
						if (initialAdditionalArgs[i] == EMPTY_ARG) {
							if (i >= defaultArgs.length)
								additionalArgs[i] = null;
							else
								additionalArgs[i] = defaultArgs[i];
						} else
							additionalArgs[i] = initialAdditionalArgs[i];
					} else if (i < defaultArgs.length) {
						additionalArgs[i] = defaultArgs[i];
					}
				}
			}
			return container;
		}

		@Override
		public final void accept(ParticleArgs defaultArgs) {
			defaultArgs.populateEmptyArgs(this);
		}
	}

	private final TextureSewer<T> stitcher;
	private final Class<T> type;
	private final ParticleArgs baseArgs;
	private final ParticleArgs defaultArgs;

	public PWorkshop(Class<T> type) {
		this(type, null);
	}

	public PWorkshop(Class<T> type, @Nullable TextureSewer<T> stitcher) {
		this.stitcher = stitcher;
		this.type = type;
		this.baseArgs = new ParticleArgs();
		this.setBaseArguments(this.baseArgs);
		this.defaultArgs = new ParticleArgs();
	}

	public final TextureSewer<? extends Particle> getStitcher() {
		return this.stitcher;
	}

	public final Class<T> getType() {
		return this.type;
	}

	public static final void setParticleColor(Particle particle, int color) {
		particle.setRBGColorF((color >> 16 & 0xff) / 255F, (color >> 8 & 0xff) / 255F, (color & 0xff) / 255F);
		particle.setAlphaF((color >> 24 & 0xff) / 255F);
	}

	protected final T getParticle(ImmutableParticleArgs args) {
		T particle = this.createParticle(args);
		if (this.getStitcher() != null) {
			((IParticleSpriteReceiver) particle).setStitchedSprites(this.getStitcher().getSprites());
		}
		setParticleColor(particle, args.color);
		return particle;
	}

	protected abstract T createParticle(ImmutableParticleArgs args);

	public static final Object EMPTY_ARG = new Object();

	protected void setBaseArguments(ParticleArgs args) {}

	protected void setDefaultArguments(World world, ParticleArgs args) {}

	public static final class BaseArgsBuilder<F extends PWorkshop, B extends BaseArgsBuilder, C extends Particle>
			extends
			ParticleArgs<B> {
		private final PWorkshop factory;

		private BaseArgsBuilder(PWorkshop factory) {
			super();
			this.factory = factory;
		}

		public final F buildBaseArgs() {
			ParticleArgs container = new ParticleArgs();
			this.populateEmptyArgs(container);
			this.factory.baseArgs.populateEmptyArgs(container);
			if (container.isMotionSet())
				this.factory.baseArgs
						.withMotion(container.getMotionX(), container.getMotionY(), container.getMotionZ());
			if (container.isColorSet())
				this.factory.baseArgs.withColor(container.getColor());
			if (container.isScaleSet())
				this.factory.baseArgs.withScale(container.getScale());
			if (container.isDataSet()) {
				this.factory.baseArgs.withData(container.getData());
			}
			return (F) this.factory;
		}
	}

	private final BaseArgsBuilder baseArgsBuilder = new BaseArgsBuilder(this);

	public BaseArgsBuilder<F, BaseArgsBuilder<F, ?, T>, T> getBaseArgsBuilder() {
		this.baseArgsBuilder.reset();
		return this.baseArgsBuilder;
	}

	public final T create(World world, double x, double y, double z, @Nullable ParticleArgs args) {
		if (args == null)
			args = ParticleArgs.get();
		this.defaultArgs.reset();
		this.setDefaultArguments(world, this.defaultArgs);
		boolean hasActualDefaults = this.defaultArgs.isColorSet() || this.defaultArgs.isMotionSet()
				|| this.defaultArgs.isScaleSet() || this.defaultArgs.isDataSet();
		if (hasActualDefaults) {
			args = this.baseArgs.populateEmptyArgs(this.defaultArgs).populateEmptyArgs(args);
		} else {
			args = this.baseArgs.populateEmptyArgs(args);
		}
		return this.getParticle(new ImmutableParticleArgs(world, x, y, z, args));
	}

	public final T spawn(World world, double x, double y, double z, @Nullable ParticleArgs args) {
		T particle = this.create(world, x, y, z, args);
		if (particle != null)
			Minecraft.getMinecraft().effectRenderer.addEffect(particle);
		return particle;
	}
}