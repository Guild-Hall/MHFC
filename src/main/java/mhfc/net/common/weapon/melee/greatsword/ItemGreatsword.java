package mhfc.net.common.weapon.melee.greatsword;

import java.util.function.Consumer;

import com.google.common.collect.Multimap;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.weapon.melee.ItemWeaponMelee;
import mhfc.net.common.weapon.melee.greatsword.GreatswordWeaponStats.GreatswordWeaponStatsBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGreatsword extends ItemWeaponMelee<GreatswordWeaponStats> {
	public static ItemGreatsword build(Consumer<GreatswordWeaponStatsBuilder> config) {
		GreatswordWeaponStatsBuilder builder = new GreatswordWeaponStatsBuilder();
		config.accept(builder);
		return new ItemGreatsword(builder.build());
	}

	public ItemGreatsword(GreatswordWeaponStats stats) {
		super(stats);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return ResourceInterface.weapon_greatsword_name;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity holder, int slot, boolean isHold) {
		super.onUpdate(stack, world, holder, slot, isHold);
		if (!isHold) {
			return;
		}
		if (holder instanceof EntityPlayer) {
			EntityPlayer entity = (EntityPlayer) holder;
			entity.moveEntityWithHeading(entity.moveStrafing * -0.5f, entity.moveForward * -0.5f);
			//if(stack instanceof) TODO: Add some High class GS that will never required strafing delay.
		}

	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		@SuppressWarnings("deprecation")
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(
					SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -1.9000000953674316D, 2));
		}

		return multimap;

	}

}
