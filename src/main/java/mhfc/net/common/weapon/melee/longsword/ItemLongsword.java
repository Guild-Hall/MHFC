package mhfc.net.common.weapon.melee.longsword;

import java.util.function.Consumer;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.util.NBTUtils;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.melee.ItemWeaponMelee;
import mhfc.net.common.weapon.melee.longsword.LongswordWeaponStats.LongswordWeaponStatsBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemLongsword extends ItemWeaponMelee<LongswordWeaponStats> {
	public static ItemLongsword build(Consumer<LongswordWeaponStatsBuilder> config) {
		LongswordWeaponStatsBuilder builder = new LongswordWeaponStatsBuilder();
		config.accept(builder);
		return new ItemLongsword(builder.build());
	}

	protected static final String NBT_AFFINITY = "mhfc:affinity";
	protected static final float MAX_AFFINITY = 250f;
	protected static final float TRIGGER_AFFINITY = 100f;

	public ItemLongsword(LongswordWeaponStats stats) {
		super(stats);
		setTextureName(MHFCReference.weapon_ls_default_icon);
	}

	protected float getAffinity(ItemStack stack) {
		return NBTUtils.getNBTChecked(stack).getFloat(NBT_AFFINITY);
	}

	protected float affectAffinity(ItemStack stack, float change) {
		float current = getAffinity(stack);
		current = Math.min(Math.max(current + change, 0f), MAX_AFFINITY);
		NBTUtils.getNBTChecked(stack).setFloat(NBT_AFFINITY, current);
		return current;
	}

	protected boolean isAffinityTriggered(ItemStack stack) {
		return getAffinity(stack) > TRIGGER_AFFINITY;
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return "longsword";
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase holder, EntityLivingBase hit) {
		affectAffinity(stack, 30);
		return true;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity holder, int slot, boolean isHold) {
		if (holder instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) holder;
			if (isAffinityTriggered(stack)) {
				player.addPotionEffect(new PotionEffect(MHFCPotionRegistry.longswordattackup.id, 30));
			}
		}
	}
}
