package mhfc.net.common.weapon;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IWeaponCluster {
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity);

	public UUID getUUID();

	public ComponentMelee getMeleeComponent();
}
