package mhfc.net.common.entity.quests;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class QuestGiver extends EntityLiving {

	public QuestGiver(World world) {
		super(world);
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
				100.0f);
	}

	@Override
	protected boolean interact(EntityPlayer player) {
		player.openGui(MHFCMain.instance, MHFCReference.gui_questgiver_id,
				this.worldObj, (int) this.posX, (int) this.posY,
				(int) this.posZ);
		return true;
	}

	@Override
	protected void damageEntity(DamageSource source, float p_70665_2_) {
		// This should avoid taking any damage
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

}
