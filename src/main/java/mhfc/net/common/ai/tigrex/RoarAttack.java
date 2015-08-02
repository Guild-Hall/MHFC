package mhfc.net.common.ai.tigrex;

import java.util.List;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class RoarAttack extends ActionAdapter<EntityTigrex> {
	private static final int LAST_FRAME = 70;

	
	// Create a general AI for Roar.
	public RoarAttack() {
		setAnimation("mhfc:models/Tigrex/rawr.mcanm");
		setLastFrame(LAST_FRAME);
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
	public void update() {
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if(this.getCurrentFrame() >= 18 && this.getCurrentFrame() <= 22 )
			tigrex.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
			tigrex.playSound("mhfc:tigrex-roar", 1.0F, 1.0F);
		// TODO stun all hunters in the near range if possible 
			List list = tigrex.worldObj.getEntitiesWithinAABBExcludingEntity(tigrex, tigrex.boundingBox.expand(16.0D, 8.0D, 16.0D));
			for(int i = 0; i < list.size(); i++){//<---Loop start
				Entity entity = (Entity)list.get(i);
				if(entity instanceof EntityPlayer) {
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80, 10));
				}
			}
	}
}
