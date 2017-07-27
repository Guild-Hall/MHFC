package mhfc.net.client.particle;

import javax.annotation.Nullable;

import mhfc.net.client.particle.ParticleFactory.ParticleArgs;
import mhfc.net.client.particle.particles.Cloud;
import mhfc.net.client.particle.particles.Flake;
import mhfc.net.client.particle.particles.Ring;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public enum EnumParticles {
	FLAKE(new Flake.SFFactory()),
	RING(new Ring.RFactory()),
	CLOUD(new Cloud.CFactory());

	private ParticleFactory<?, ?> factory;

	private EnumParticles(ParticleFactory<?, ?> factory) {
		this.factory = factory;
	}

	public ParticleFactory<?, ?> getFactory() {
		return factory;
	}

	/**
	 * Creates a new instance of this particle
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Particle create(World world, double x, double y, double z) {
		return create(world, x, y, z, null);
	}

	/**
	 * Creates a new instance of this particle
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param args
	 * @return
	 */
	public Particle create(World world, double x, double y, double z, @Nullable ParticleArgs<?> args) {
		return factory.create(world, x, y, z, args);
	}

	/**
	 * Spawns this particle
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Particle spawn(World world, double x, double y, double z) {
		return spawn(world, x, y, z, null);
	}

	/**
	 * Spawns this particle
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param args
	 * @return
	 */
	public Particle spawn(World world, double x, double y, double z, @Nullable ParticleArgs<?> args) {
		return factory.spawn(world, x, y, z, args);
	}
}
