package mhfc.net.common.weapon.melee.hammer;

import java.util.function.Consumer;

import com.google.common.collect.Multimap;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.weapon.melee.ItemWeaponMelee;
import mhfc.net.common.weapon.melee.hammer.HammerWeaponStats.HammerWeaponStatsBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemHammer extends ItemWeaponMelee<HammerWeaponStats> {
	public static ItemHammer build(Consumer<HammerWeaponStatsBuilder> config) {
		HammerWeaponStatsBuilder builder = new HammerWeaponStatsBuilder();
		config.accept(builder);
		return new ItemHammer(builder.build());
	}

	protected static final double motY = 0.2D;
	protected static final int stunDur = 140;
	protected static final int critDamageFlat = 20; // Hammer deals FLAT.

	public ItemHammer(HammerWeaponStats stats) {
		super(stats);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return ResourceInterface.weapon_hammer_name;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity holder, int slot, boolean isHold) {
		super.onUpdate(stack, world, holder, slot, isHold);
		if (!isHold) {
			return;
		}
		if (holder instanceof EntityPlayer) {
			EntityPlayer entity = (EntityPlayer) holder;
			entity.moveEntityWithHeading(entity.moveStrafing * -0.7F, entity.moveForward * -0.7F);
		}
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase player) {
		if (!isOffCooldown(stack)) {
			return false;
		}
		target.addPotionEffect(new PotionEffect(MHFCPotionRegistry.getRegistry().stun, stunDur, 5));
		triggerCooldown(stack);
		return true;
	}
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	{
	        @SuppressWarnings("deprecation")
			Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
	        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
	        {
	            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -1.3000000953674316D, 2));
	        }

	        return multimap;
	    
	    }
}
