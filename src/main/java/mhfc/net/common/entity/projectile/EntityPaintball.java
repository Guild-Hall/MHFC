package mhfc.net.common.entity.projectile;

import io.netty.buffer.ByteBuf;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.item.ItemColor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityPaintball extends EntityThrowable implements IEntityAdditionalSpawnData {

	private ItemColor color;

	public EntityPaintball(World world) {
		super(world);
	}

	public EntityPaintball(World world, ItemColor color) {
		this(world);
		this.color = color;
	}

	public EntityPaintball(World world, ItemColor color, EntityLivingBase thrower) {
		super(world, thrower);
		this.color = color;
	}

	public ItemColor getColor() {
		return color;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(color == null ? 0 : color.getMetadata());
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		color = ItemColor.byMetadata(additionalData.readInt());
	}

	@Override
	protected void onImpact(RayTraceResult pos) {
		if (pos.entityHit != null && pos.entityHit instanceof EntityLivingBase) {
			EntityLivingBase entityLivingHit = (EntityLivingBase) pos.entityHit;

			entityLivingHit.addPotionEffect(
					new PotionEffect(
							MHFCPotionRegistry.getRegistry().painted,
							2400,
							this.color.getMetadata(),
							true,
							true));
			entityLivingHit.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), 1);
		}
	}

}
