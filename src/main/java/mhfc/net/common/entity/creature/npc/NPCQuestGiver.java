package mhfc.net.common.entity.creature.npc;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCContainerRegistry;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NPCQuestGiver extends EntityVillager {

	private int subID = 0;

	public NPCQuestGiver(World world) {
		super(world, 0);
		this.tasks.taskEntries.clear();
		this.setEntityBoundingBox(new AxisAlignedBB(0, 0, 0, 1.0f, 1.0f, 1.0f));
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0f);
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		player.openGui(MHFCMain.instance(), MHFCContainerRegistry.gui_questgiver_id, this.world, subID, 0, 0);
		return EnumActionResult.SUCCESS;
	}

	@Override
	protected void damageEntity(DamageSource source, float p_70665_2_) {
		// This should avoid taking any damage if the player is not in creative mode
		if (!(source.getEntity() instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) source.getEntity();
		if (!player.capabilities.isCreativeMode) {
			return;
		}
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
