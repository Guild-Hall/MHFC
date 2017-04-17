package mhfc.net.common.item.armor;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.ItemRarity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ArmorBase extends ItemArmor {
	public static EnumMap<EntityEquipmentSlot, String> makeDefaultSlotToTex(String upperTex, String lowerTex) {
		EnumMap<EntityEquipmentSlot, String> map = new EnumMap<>(EntityEquipmentSlot.class);

		map.put(EntityEquipmentSlot.CHEST, upperTex);
		map.put(EntityEquipmentSlot.HEAD, upperTex);
		map.put(EntityEquipmentSlot.FEET, upperTex);
		map.put(EntityEquipmentSlot.LEGS, lowerTex);

		return map;
	}
	
	public int layerIndex;

	protected final ItemRarity rarity;
	private final Map<EntityEquipmentSlot, String> slotToTex;

	public ArmorBase(ArmorMaterial armor, ItemRarity rarity, EntityEquipmentSlot armorType) {
		this(armor, rarity, armorType, null);
		this.layerIndex = this.renderIndex;
	}

	/**
	 *
	 * @param armor
	 * @param rarity
	 * @param armorType
	 * @param slotToTexture
	 *            if <code>null</code>, sub classes _must_ implement
	 *            {@link #getArmorTexture(ItemStack, Entity, EntityEquipmentSlot, String)}
	 */
	public ArmorBase(
			ArmorMaterial armor,
			ItemRarity rarity,
			EntityEquipmentSlot armorType,
			@Nullable Map<EntityEquipmentSlot, String> slotToTexture) {
		super(armor, 4, armorType);
		this.rarity = Objects.requireNonNull(rarity);
		this.slotToTex = slotToTexture;
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		// TODO add default armor information?!
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (slotToTex == null) {
			return super.getArmorTexture(stack, entity, slot, type);
		}
		return slotToTex.getOrDefault(slot, ResourceInterface.armor_null_tex);
	}

	@SideOnly(Side.CLIENT)
	protected abstract ModelBiped getBipedModel(EntityEquipmentSlot armorSlot);

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,ItemStack itemStack,EntityEquipmentSlot armorSlot,ModelBiped _default) {

		if (itemStack == null || !(itemStack.getItem() instanceof ItemArmor)) {
			return null;
		}
		EntityEquipmentSlot type = ((ItemArmor) itemStack.getItem()).armorType;
		ModelBiped armorModel = getBipedModel(armorSlot);
		if (armorModel == null) {
			return null;
		}
		
		switch (type) {
		case HEAD:
			armorModel = getBipedModel(armorSlot);
			break;
		case CHEST:
			armorModel = getBipedModel(armorSlot);
			break;
		case FEET:
			armorModel = getBipedModel(armorSlot);
			break;
		case LEGS:
			default:
				break;
		}

		armorModel.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
		armorModel.bipedHeadwear.showModel = armorSlot == EntityEquipmentSlot.HEAD;
		armorModel.bipedBody.showModel = (armorSlot == EntityEquipmentSlot.CHEST)|| (armorSlot == EntityEquipmentSlot.CHEST);
		armorModel.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
		armorModel.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
		armorModel.bipedRightLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS)
				|| (armorSlot == EntityEquipmentSlot.FEET);
		armorModel.bipedLeftLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS)
				|| (armorSlot == EntityEquipmentSlot.FEET);

		armorModel.isSneak = _default.isSneak;
		armorModel.isRiding = _default.isRiding;
		armorModel.isChild = _default.isChild;

		armorModel.rightArmPose = _default.rightArmPose;
		armorModel.leftArmPose = _default.leftArmPose;

		return armorModel;
	}

	/*private ModelBiped.ArmPose getPoseForItemStack(EnumHand activeHand, EntityLivingBase living) {
		ItemStack stackInHand = activeHand == EnumHand.MAIN_HAND
				? living.getHeldItemMainhand()
				: living.getHeldItemOffhand();
		int useTicks = living.getItemInUseCount();
		if (useTicks <= 0) {
			return ArmPose.EMPTY;
		}

		switch (stackInHand.getItemUseAction()) {
		case BLOCK:
			return ArmPose.BLOCK;
		case BOW:
			return ArmPose.BOW_AND_ARROW;
		case DRINK:
		case EAT:
			return ArmPose.ITEM;
		default:
			break;
		}

		return ArmPose.EMPTY;
	}*/

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		// The player needs to wear all armor pieces, so when we check on the helmet it's enough
		assert armor.getItem() == this;
		boolean shouldApplySetBonus = shouldApplySetBonus(player);
		if (shouldApplySetBonus) {
			applySetBonus(world, player);
		}
	}

	protected boolean shouldApplySetBonus(EntityPlayer player) {
		if (this.armorType != EntityEquipmentSlot.HEAD) {
			return false;
		}

		ItemStack helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);

		return isSetPiece(helmet) && isSetPiece(chest) && isSetPiece(legs) && isSetPiece(boots);
	}

	private boolean isSetPiece(ItemStack itemStack) {
		return itemStack != null && itemStack.getItem().getClass().equals(getClass());
	}

	protected void applySetBonus(World world, EntityPlayer player) {}

}
