package mhfc.net.common.ai.entity.deviljho;

import java.util.List;
import java.util.Random;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityDeviljho;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;

public class DeviljhoStomp extends AIAnimatedAction<EntityDeviljho> {
	private static final String ANIMATION = "mhfc:models/Deviljho/DeviljhoStomp.mcanm";
	private static final int LAST_FRAME = 55;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(60f, 50F, 9999999f);
	private static final double MAX_DIST = 9f;
	private static final float WEIGHT = 7;
	private boolean thrown = false;

	public DeviljhoStomp() {
		super(generateProvider());
	}

	private static IAnimatedActionProvider<EntityDeviljho> generateProvider() {
		IAnimationProvider animationProvider = new IAnimationProvider.AnimationAdapter(ANIMATION, LAST_FRAME);
		ISelectionPredicate<EntityDeviljho> selectionProvider = new ISelectionPredicate.DistanceAdapter<>(0, MAX_DIST);
		IWeightProvider<EntityDeviljho> weightProvider = new IWeightProvider.SimpleWeightAdapter<>(WEIGHT);
		return new AnimatedActionAdapter<EntityDeviljho>(animationProvider, selectionProvider, weightProvider);
	}

	private void updateStomp() {
		EntityDeviljho entity = this.getEntity();
		if (!entity.onGround || thrown || this.getCurrentFrame() < 26)
			return;
		List<Entity> list = entity.worldObj
				.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(6.0D, 1.0D, 6.0D));
		Random random = new Random();
		int a = MathHelper.floor_double(entity.posX);
		int b = MathHelper.floor_double(entity.posY);
		int c = MathHelper.floor_double(entity.posZ);
		Block block = entity.worldObj.getBlock(a, b - 1, c);
		if (block != Blocks.air) {
			block = Blocks.dirt;
		}
		for (int x = 0; x < 100; x++) {
			for (int z = 0; z < 100; z++) {
				entity.worldObj.spawnParticle(
						"blockcrack_" + Block.getIdFromBlock(block) + "_0",
						entity.posX - 5.0D + x,
						entity.posY + 0.5D,
						entity.posZ - 5.0D + z,
						random.nextGaussian(),
						random.nextGaussian(),
						random.nextGaussian());
			}
		}
		for (Entity entity1 : list) {
			if (!(entity1 instanceof EntityLivingBase)) {
				continue;
			}
			EntityLivingBase living = (EntityLivingBase) entity;
			damageCalc.accept(living);
			entity1.addVelocity(-0.2, 0.2, 0);
		}
		entity.playSound("mhfc:deviljho-bite", 1.0F, 1.0F);
		thrown = true;
	}

	@Override
	protected void update() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();

		updateStomp();

		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityDeviljho e = getEntity();
			e.moveForward(1, false);
		}

	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		thrown = false;
	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}

}
