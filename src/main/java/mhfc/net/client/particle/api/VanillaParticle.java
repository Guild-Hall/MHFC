package mhfc.net.client.particle.api;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;

public final class VanillaParticle<T extends Particle> extends ParticleFactory<VanillaParticle<T>, T> {
	private final IParticleFactory factory;

	private VanillaParticle(Class<T> type, IParticleFactory factory) {
		super(type);
		this.factory = factory;
	}

	public static <T extends Particle> VanillaParticle<T> create(Class<T> type, IParticleFactory factory) {
		return new VanillaParticle<T>(type, factory);
	}

	@Override
	protected T createParticle(ImmutableParticleArgs args) {
		Object[] data = args.data;
		if (data.length > 1) {
			int[] additionalArgs = ArrayUtils
					.toPrimitive(Arrays.copyOfRange(data, 1, data.length - 1, Integer[].class));
			return (T) this.factory.createParticle(
					(int) data[0],
					args.world,
					args.x,
					args.y,
					args.z,
					args.motionX,
					args.motionY,
					args.motionZ,
					additionalArgs);
		} else {
			return (T) this.factory.createParticle(
					(int) data[0],
					args.world,
					args.x,
					args.y,
					args.z,
					args.motionX,
					args.motionY,
					args.motionZ);
		}
	}

	@Override
	protected void setBaseArguments(ParticleArgs args) {
		args.withData(0);
	}
}
