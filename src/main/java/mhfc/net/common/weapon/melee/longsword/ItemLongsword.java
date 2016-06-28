package mhfc.net.common.weapon.melee.longsword;

import java.util.UUID;
import java.util.function.Consumer;

import com.google.common.collect.Multimap;

import mhfc.net.common.util.NBTUtils;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.melee.ItemWeaponMelee;
import mhfc.net.common.weapon.melee.longsword.LongswordWeaponStats.LongswordWeaponStatsBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLongsword extends ItemWeaponMelee<LongswordWeaponStats> {
	public static ItemLongsword build(Consumer<LongswordWeaponStatsBuilder> config) {
		LongswordWeaponStatsBuilder builder = new LongswordWeaponStatsBuilder();
		config.accept(builder);
		return new ItemLongsword(builder.build());
	}

	protected static final String NBT_SPIRIT = "mhfc:affinity";
	protected static final float MAX_SPIRIT = 250f;
	protected static final float TRIGGER_SPIRIT = 100f;
	protected static final float SPIRIT_DECREASE = -0.1f;

	public ItemLongsword(LongswordWeaponStats stats) {
		super(stats);
		setTextureName(MHFCReference.weapon_ls_default_icon);
	}

	protected float getAffinity(ItemStack stack) {
		return NBTUtils.getNBTChecked(stack).getFloat(NBT_SPIRIT);
	}

	protected float changeSpirit(ItemStack stack, float change) {
		float current = getAffinity(stack);
		current = Math.min(Math.max(current + change, 0f), MAX_SPIRIT);
		NBTUtils.getNBTChecked(stack).setFloat(NBT_SPIRIT, current);
		return current;
	}

	public boolean isAffinityTriggered(ItemStack stack) {
		return getAffinity(stack) > TRIGGER_SPIRIT;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity holder, int slot, boolean isHoldItem) {
		super.onUpdate(stack, world, holder, slot, isHoldItem);
		changeSpirit(stack, SPIRIT_DECREASE);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		changeSpirit(entityItem.getEntityItem(), SPIRIT_DECREASE);
		return super.onEntityItemUpdate(entityItem);
	}

	/**
	 * Gets the current affinity as a float in [0..1]
	 *
	 * @param stack
	 * @return
	 */
	public float getSpiritPercentage(ItemStack stack) {
		return Math.min(TRIGGER_SPIRIT, getAffinity(stack)) / TRIGGER_SPIRIT;
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return MHFCReference.weapon_longsword_name;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase holder, EntityLivingBase hit) {
		changeSpirit(stack, 30);
		return true;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack) {
		Multimap<String, AttributeModifier> attributes = super.getAttributeModifiers(stack);
		if (isAffinityTriggered(stack)) {
			AttributeModifier attackModifier = new AttributeModifier(
					UUID.fromString(MHFCReference.potion_longsworddamageup_uuid),
					"Spirit Gauge",
					1.2,
					1);
			attributes.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), attackModifier);
		}
		return attributes;
	}
}
