package mhfc.net.common.ai.entity.deviljho;

import java.util.List;
import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class DeviljhoStomp extends ActionAdapter<EntityDeviljho> {
	private static final int LAST_FRAME = 55;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(60f, 50F,9999999f);
	private static final double MAX_DIST = 9f;
	private static final float WEIGHT = 7;
	private boolean thrown = false;
	
	public DeviljhoStomp() {
		setAnimation("mhfc:models/Deviljho/DeviljhoStomp.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	public float getWeight() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3 toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DIST) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}

	private void updateThrow() {
		EntityDeviljho entity = this.getEntity();
		if (!entity.onGround || thrown || this.getCurrentFrame() < 26)
			return;
		List<Entity> list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(6.0D, 1.0D, 6.0D));
		Random random = new Random();
		int a = MathHelper.floor_double(entity.posX);
		int b = MathHelper.floor_double(entity.posY);
		int c = MathHelper.floor_double(entity.posZ);
		Block block = entity.worldObj.getBlock(a, b - 1, c);
		if (block != Blocks.air) {
			block = Blocks.dirt;
		}
		for (int x = 0; x < 10; x++) {
			for (int z = 0; z < 10; z++) {
				entity.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_0", entity.posX - 5.0D + x, entity.posY + 0.5D, entity.posZ - 5.0D + z, random.nextGaussian(), random.nextGaussian(), random.nextGaussian());
			}
		}
		for (Entity entity1 : list) {
			if(!(entity1 instanceof EntityLivingBase)) {
				continue;
			}
			EntityLivingBase living = (EntityLivingBase) entity;
				entity.attackEntityFrom(DamageSource.causeMobDamage(entity), 60.0F);
				entity.addVelocity(0.4, 0, 0);
			}
		entity.playSound("mhfc:deviljho-bite", 1.0F, 1.0F);
		thrown = true;	
	}
	
	@Override
	protected void update() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		
		updateThrow();

		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityDeviljho e = getEntity();
			e.moveForward(1, false);
		}
		
	}
	
	@Override
	public void beginExecution() {
		thrown = false;
	}
	
	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}

}