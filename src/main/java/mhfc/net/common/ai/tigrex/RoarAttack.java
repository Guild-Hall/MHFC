package mhfc.net.common.ai.tigrex;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class RoarAttack extends ActionAdapter<EntityTigrex> {
	private static final int LAST_FRAME = 70;
	private Collection<EntityPlayer> affectedEntities;

	// Create a general AI for Roar.
	public RoarAttack() {
		setAnimation("mhfc:models/Tigrex/rawr.mcanm");
		setLastFrame(LAST_FRAME);
		affectedEntities = new HashSet<EntityPlayer>();
	}

	@Override
	public float getWeight() {
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if (target == null)
			return DONT_SELECT;
		return rng().nextFloat() * 1;
	}

	@Override
	public void beginExecution() {
		affectedEntities.clear();
		getEntity().playSound("mhfc:tigrex-roar", 1.0F, 1.0F);
	}

	@Override
	public void update() {
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if (this.getCurrentFrame() >= 18 && this.getCurrentFrame() <= 22)
			tigrex.getLookHelper().setLookPositionWithEntity(target, 30.0F,
				30.0F);

		@SuppressWarnings("unchecked")
		List<Entity> list = tigrex.worldObj
			.getEntitiesWithinAABBExcludingEntity(tigrex, tigrex.boundingBox
				.expand(16.0D, 8.0D, 16.0D));

		for (Entity entity : list) {// <---Loop start
			if (!affectedEntities.contains(entity)
				&& entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,
					80, 10));
				affectedEntities.add(player);
			}
		}
	}
}
