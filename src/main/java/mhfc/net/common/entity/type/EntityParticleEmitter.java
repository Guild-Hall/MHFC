package mhfc.net.common.entity.type;

import mhfc.net.common.index.ResourceInterface;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public abstract class EntityParticleEmitter extends Entity implements IEntityAdditionalSpawnData {

	protected static final AxisAlignedBB boundingBox = new AxisAlignedBB(0, 0, 0, 0, 0, 0);

	public static enum DurationType {
		SHORT(30), //1.5 seconds
		MEDIUM(200), //10 seconds
		LONG(600), //30 seconds
		VERY_LONG(1200); //1 minute
		public final int ticks;

		private DurationType(int ticks) {
			this.ticks = ticks;
		}
	}

	public int maxLife;
	public static final int ABSOLUTE_MAX = ResourceInterface.max_duration_particle_emitter_in_ticks;

	public EntityParticleEmitter(World worldIn) {
		super(worldIn);
		this.setSize(0, 0);
	}

	@Override
	public boolean isEntityInvulnerable(DamageSource source) {
		return true;
	}

	@Override
	public AxisAlignedBB getEntityBoundingBox() {
		return boundingBox;
	}

	@Override
	public abstract void onUpdate();
}
