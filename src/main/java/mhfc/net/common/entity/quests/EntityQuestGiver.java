package mhfc.net.common.entity.quests;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityQuestGiver extends EntityVillager {

	private int subID = 0;

	public EntityQuestGiver(World world) {
		super(world, 0);
		this.tasks.taskEntries.clear();
		this.boundingBox.setBounds(0, 0, 0, 1.0f, 1.0f, 1.0f);
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0f);
	}

	@Override
	public boolean interact(EntityPlayer player) {
		player.openGui(MHFCMain.instance(), MHFCReference.gui_questgiver_id, this.worldObj, subID, 0, 0);
		return true;
	}

	@Override
	protected void damageEntity(DamageSource source, float p_70665_2_) {
		// This should avoid taking any damage if the player is not in creative mode
		if (!(source.getEntity() instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) source.getEntity();
		if (!player.capabilities.isCreativeMode)
			return;
		super.damageEntity(source, p_70665_2_);
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
}
