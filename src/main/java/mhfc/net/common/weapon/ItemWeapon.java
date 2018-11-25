package mhfc.net.common.weapon;

import com.google.common.collect.Multimap;
import mhfc.net.MHFCMain;
import mhfc.net.common.index.AttributeModifiers;
import mhfc.net.common.item.IItemSimpleModel;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.util.NBTUtils;
import mhfc.net.common.weapon.stats.CombatEffect;
import mhfc.net.common.weapon.stats.WeaponStats;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Our own version of {@link ItemSword} but without the destruction of strings for example.
 *
 * @author WorldSEnder
 *
 */
public abstract class ItemWeapon<W extends WeaponStats> extends Item implements IItemSimpleModel {
	protected static final String COOLDOWN_NBT = "mhfc:cooldown";

	protected static final UUID MOVEMENT_SLOW_UUID = UUID.fromString("be039fef-dd36-4042-8cae-ca8afff6479c");

	protected final W stats;
	private ModelResourceLocation resLocCache;

	public ItemWeapon(W stats) {
		this.stats = Objects.requireNonNull(stats);
		setFull3D();
		setCreativeTab(MHFCMain.mhfctabs);
		setTranslationKey(stats.getUnlocalizedName());
		setMaxStackSize(1);
	}

	@Override
	public ModelResourceLocation getModel() {
		if (resLocCache == null) {
			ResourceLocation resLoc = getRegistryName();
			String name = resLoc.getPath();
			resLocCache = new ModelResourceLocation(
					resLoc.getNamespace() + ":models/item/" + name + ".mcmdl#inventory");
		}
		return resLocCache;
	}

	protected boolean isOffCooldown(ItemStack stack) {
		return NBTUtils.getNBTChecked(stack).getInteger(COOLDOWN_NBT) == 0;
	}

	protected void triggerCooldown(ItemStack stack) {
		NBTUtils.getNBTChecked(stack).setInteger(COOLDOWN_NBT, stats.getCooldownTicks());
	}

	protected void decreaseCooldown(ItemStack stack) {
		NBTUtils.decreaseIntUnsigned(NBTUtils.getNBTChecked(stack), COOLDOWN_NBT);
	}

	protected double getMovementSpeedMultiplier(ItemStack stack) {
		return 0D;
	}

	/**
	 * Call this addInformation() in your Item
	 *
	 * @param iStack
	 *            stack to update
	 * @param info
	 *            cooldown text
	 */
	@SideOnly(Side.CLIENT)
	protected void addCooldownToInformation(ItemStack iStack, List<String> info) {
		int cooldown = NBTUtils.getNBTChecked(iStack).getInteger(COOLDOWN_NBT);
		info.add(cooldown > 0 ? "Cooldown Time: " + cooldown : "Cooldown Time: Ready to Use");
	}

	@SideOnly(Side.CLIENT)
	protected static void displayAttackDelay(ItemStack iStack, List<String> info) {
		int cooldown = NBTUtils.getNBTChecked(iStack).getInteger(COOLDOWN_NBT);
		info.add(cooldown > 0 ? "Attack Time: " + cooldown : "Attack Time: Ready");
	}

	public abstract String getWeaponClassUnlocalized();

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ColorSystem.gold + I18n.format(getWeaponClassUnlocalized() + ".name"));
		tooltip.add(ColorSystem.yellow + "Rarity: " + stats.getRarity().toString());
		for (CombatEffect effect : stats.getCombatEffects()) {
			String formattedAmount = String.format("%+.0f", effect.getAmount());
			tooltip.add(
					ColorSystem.light_purple + formattedAmount + " "
							+ I18n.format(effect.getType().getUnlocalizedName() + ".name"));
		}
		if (!flagIn.isAdvanced()) {
			return;
		}
		addCooldownToInformation(stack, tooltip);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity holder, int slot, boolean isHoldItem) {
		decreaseCooldown(stack);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		for (CombatEffect effect : stats.getCombatEffects()) {
			effect.getType().onEntitySwing(entityLiving, stack, itemRand);
		}
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		for (CombatEffect effect : stats.getCombatEffects()) {
			effect.applyTo(target, attacker);
		}
		return super.hitEntity(stack, target, attacker);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}

	public W getWeaponStats() {
		return stats;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 72000;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if (slot != EntityEquipmentSlot.MAINHAND) {
			return multimap;
		}
		AttributeModifier attackModifier = new AttributeModifier(
				Item.ATTACK_DAMAGE_MODIFIER,
				"Weapon Attack",
				stats.getAttack(),
				AttributeModifiers.ADDITIVE);
		multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), attackModifier);
		AttributeModifier moveSpeedModifier = new AttributeModifier(
				MOVEMENT_SLOW_UUID,
				"Weapon weight",
				getMovementSpeedMultiplier(stack),
				AttributeModifiers.MULTIPLICATIVE);
		multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), moveSpeedModifier);
		return multimap;
	}
}
