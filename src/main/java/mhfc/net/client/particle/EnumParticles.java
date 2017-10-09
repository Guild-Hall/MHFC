package mhfc.net.client.particle;

import javax.annotation.Nullable;

import mhfc.net.client.particle.api.PWorkshop;
import mhfc.net.client.particle.api.PWorkshop.ParticleArgs;
import mhfc.net.client.particle.particles.Cloud;
import mhfc.net.client.particle.particles.Flake;
import mhfc.net.client.particle.particles.Ring;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public enum EnumParticles {
	FLAKE(new Flake.SFFactory()),
	RING(new Ring.RFactory()),
	CLOUD(new Cloud.CFactory());

	private PWorkshop<?, ?> factory;

	private EnumParticles(PWorkshop<?, ?> factory) {
		this.factory = factory;
	}

	public PWorkshop<?, ?> getFactory() {
		return factory;
	}

	public Particle create(World world, double x, double y, double z) {
		return create(world, x, y, z, null);
	}

	public Particle createWithLabel(World world, double x, double y, double z, String label) {
		return create(world, x, y, z, null);
	}

	public Particle create(World world, double x, double y, double z, @Nullable ParticleArgs<?> args) {
		return factory.create(world, x, y, z, args);
	}

	public Particle spawn(World world, double x, double y, double z) {
		return spawn(world, x, y, z, null);
	}

	;

	public Particle spawn(World world, double x, double y, double z, @Nullable ParticleArgs<?> args) {
		return factory.spawn(world, x, y, z, args);
	}
}
