package mhfc.net.common.weapon.range.bowgun.heavy;

import java.util.function.Consumer;

import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.range.bowgun.BowgunWeaponStats;
import mhfc.net.common.weapon.range.bowgun.BowgunWeaponStats.BowgunWeaponStatsBuilder;
import mhfc.net.common.weapon.range.bowgun.ItemBowgun;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHeavyBowgun extends ItemBowgun {
	public static ItemHeavyBowgun build(Consumer<BowgunWeaponStatsBuilder> config) {
		BowgunWeaponStatsBuilder builder = new BowgunWeaponStatsBuilder();
		config.accept(builder);
		return new ItemHeavyBowgun(builder.build());
	}

	public ItemHeavyBowgun(BowgunWeaponStats stats) {
		super(stats);
		setTextureName(MHFCReference.weapon_bgl_barrel_icon);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return MHFCReference.weapon_heavybowgun_name;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack iStack, World world, EntityPlayer player) {
		return iStack;
		/*
		if (Cooldown.canUse(iStack, getcooldown)) {
		if(player.capabilities.isCreativeMode ) {
			if(!world.isRemote)world.playSoundAtEntity(player, "mhfc:bowgun-shot", 1.0F, 1.0F);
			EntityThrowable entity = new EntityBullet(world, player, getDamage );
			entity.posX += (this.rand.nextDouble()-this.rand.nextDouble())/2;
			entity.posY += (this.rand.nextDouble()-this.rand.nextDouble())/2;
			entity.posZ += (this.rand.nextDouble()-this.rand.nextDouble())/2;
			if(!world.isRemote) {
				world.spawnEntityInWorld(entity);
			}
		}
		}
		return iStack;*/
	}

}
