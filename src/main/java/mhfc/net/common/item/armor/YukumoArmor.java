package mhfc.net.common.item.armor;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.helper.MHFCArmorMaterialHelper;
import mhfc.net.common.helper.MHFCArmorModelHelper;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class YukumoArmor extends ItemArmor {
	private static final String[] names = { MHFCReference.armor_yukumo_helm_name, MHFCReference.armor_yukumo_chest_name,
			MHFCReference.armor_yukumo_legs_name, MHFCReference.armor_yukumo_boots_name };

	private static final String[] icons = { MHFCReference.armor_default_helm_icon,
			MHFCReference.armor_default_chest_icon, MHFCReference.armor_default_legs_icon,
			MHFCReference.armor_default_boots_icon };

	public YukumoArmor(int type) {
		super(MHFCArmorMaterialHelper.ArmorYukumo, 4, type);
		setCreativeTab(MHFCMain.mhfctabs);
		setUnlocalizedName(names[type]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(icons[this.armorType]);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add("+5 Thunder");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (stack.getItem() == MHFCItemRegistry.getRegistry().armor_yukumo_helm
				|| stack.getItem() == MHFCItemRegistry.getRegistry().armor_yukumo_chest
				|| stack.getItem() == MHFCItemRegistry.getRegistry().armor_yukumo_boots) {
			return MHFCReference.armor_yukumo_tex1;
		}
		if (stack.getItem() == MHFCItemRegistry.getRegistry().armor_yukumo_legs) {
			return MHFCReference.armor_yukumo_tex2;
		}
		return null;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {

		ModelBiped armorModel = null;

		if (itemStack == null || !(itemStack.getItem() instanceof ItemArmor)) {
			return null;
		}

		int type = ((ItemArmor) itemStack.getItem()).armorType;

		if (type == 1 || type == 3 || type == 0) {
			armorModel = MHFCArmorModelHelper.yukumo;
		}
		if (armorModel == null) {
			return null;
		}
		armorModel.bipedHead.showModel = armorSlot == 0;
		armorModel.bipedHeadwear.showModel = armorSlot == 0;
		armorModel.bipedBody.showModel = armorSlot == 1 || armorSlot == 2;
		armorModel.bipedRightArm.showModel = armorSlot == 1;
		armorModel.bipedLeftArm.showModel = armorSlot == 1;
		armorModel.bipedRightLeg.showModel = armorSlot == 2 || armorSlot == 3;
		armorModel.bipedLeftLeg.showModel = armorSlot == 2 || armorSlot == 3;

		armorModel.isSneak = entityLiving.isSneaking();
		armorModel.isRiding = entityLiving.isRiding();
		armorModel.isChild = entityLiving.isChild();
		armorModel.heldItemRight = 0;
		armorModel.aimedBow = false;
		ItemStack held_item = entityLiving.getEquipmentInSlot(0);
		if (held_item != null) {
			armorModel.heldItemRight = 1;
			if (entityLiving instanceof EntityPlayer && ((EntityPlayer) entityLiving).getItemInUseCount() > 0) {
				EnumAction enumaction = held_item.getItemUseAction();
				if (enumaction == EnumAction.bow) {
					armorModel.aimedBow = true;
				} else if (enumaction == EnumAction.block) {
					armorModel.heldItemRight = 3;
				}
			}
		}
		return armorModel;
	}
}
