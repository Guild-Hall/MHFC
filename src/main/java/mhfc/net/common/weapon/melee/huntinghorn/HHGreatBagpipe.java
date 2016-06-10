package mhfc.net.common.weapon.melee.huntinghorn;

import java.util.List;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.Cooldown;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HHGreatBagpipe extends HuntingHornClass {

	public HHGreatBagpipe() {
		super(MHFCWeaponMaterialHelper.HHGreatbagpipe, 400, 600);
		setUnlocalizedName(MHFCReference.weapon_hh_greatbagpipe_name);
		labelWeaponRarity(2);
		elementalType(false, false, false, false, false, false, false, false);
		enableCooldownDisplay = true;
	}
	
}