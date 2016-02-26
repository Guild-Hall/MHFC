package mhfc.net.common.weapon.range.bowgun;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.helper.MHFCWeaponClassingHelper;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.util.Cooldown;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BowgunClass extends Item  {
	
	protected float getDamage = 12;
	protected boolean poisontype, firetype, enableCooldownDisplay;
	protected String des1, des2, des3; // <--- Shorten the handles
	protected int attackdelay, rarity, meta, getcooldown;
	public BowgunClass() {
		setCreativeTab(MHFCMain.mhfctabs);
		setFull3D();
		maxStackSize = 1;
	
	}
	
	@Override
	public void onUpdate(ItemStack iStack, World world, Entity entity, int i,
			boolean flag) {
		if (!world.isRemote) {
			Cooldown.onUpdate(iStack, getcooldown);
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	
	public void elementalType(boolean poison, boolean fire) {
		poisontype = poison;
		firetype = fire;
	}
	
	@Deprecated
	// will rework soon
	public void getWeaponDescription(String title) {
		des1 = title;
	}

	public void getWeaponDescriptionWithMeta(String second, int rarerty,
			int metaData) {
		des2 = second;
		rarity = rarerty;
		meta = metaData;
	}

	public void getWeaponDescription(String second, int rareity) {
		des2 = second;
		rarity = rareity;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		par3List.add(ColorSystem.gold + des1);
		par3List.add(ColorSystem.dark_green + des2);
		par3List.add(ColorSystem.yellow + "Rarity: " + rarity);
		if (enableCooldownDisplay)
			Cooldown.displayAttackDelay(par1ItemStack, par3List, getcooldown);
	}

}
