package mhfc.heltrato.common.item.armor;

import java.util.List;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.core.registry.MHFCRegItem;
import mhfc.heltrato.common.helper.MHFCArmorModelHelper;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class VelocipreyArmor extends ItemArmor {
	private static final String[] icons_array = {
			MHFCReference.armor_velociprey_helm_icon,
			MHFCReference.armor_velociprey_chest_icon,
			MHFCReference.armor_velociprey_legs_icon,
			MHFCReference.armor_velociprey_boots_icon};

	// private Random rand;
	// private int param;

	public VelocipreyArmor(ArmorMaterial p_i45325_1_, int p_i45325_2_,
			int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
		setCreativeTab(MHFCMain.mhfctabs);
		// rand = new Random();
		// param = p_i45325_3_;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(icons_array[this.armorType]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			String type) {
		if (stack.getItem() == MHFCRegItem.mhfcitemvelocipreyhelm
				|| stack.getItem() == MHFCRegItem.mhfcitemvelocipreychest
				|| stack.getItem() == MHFCRegItem.mhfcitemvelocipreyboots) {
			return MHFCReference.armor_velociprey_tex1;
		}
		if (stack.getItem() == MHFCRegItem.mhfcitemkirinlegs) {
			return MHFCReference.armor_velociprey_tex2;
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,
			ItemStack itemStack, int armorSlot) {

		ModelBiped armorModel = null;

		if (itemStack != null) {
			int type = ((ItemArmor) itemStack.getItem()).armorType;

			if (type == 1 || type == 3 || type == 0) {
				armorModel = MHFCArmorModelHelper.getArmorModel(6);
			}
			if (armorModel != null) {
				armorModel.bipedHead.showModel = armorSlot == 0;
				armorModel.bipedHeadwear.showModel = armorSlot == 0;
				armorModel.bipedBody.showModel = armorSlot == 1
						|| armorSlot == 2;
				armorModel.bipedRightArm.showModel = armorSlot == 1;
				armorModel.bipedLeftArm.showModel = armorSlot == 1;
				armorModel.bipedRightLeg.showModel = armorSlot == 2
						|| armorSlot == 3;
				armorModel.bipedLeftLeg.showModel = armorSlot == 2
						|| armorSlot == 3;

				armorModel.isSneak = entityLiving.isSneaking();
				armorModel.isRiding = entityLiving.isRiding();
				armorModel.isChild = entityLiving.isChild();
				armorModel.heldItemRight = 0;
				armorModel.aimedBow = false;
				EntityPlayer player = (EntityPlayer)entityLiving;
				ItemStack held_item = player.getEquipmentInSlot(0);
				if (held_item != null){
				armorModel.heldItemRight = 1;
				if (player.getItemInUseCount() > 0){
				EnumAction enumaction = held_item.getItemUseAction();
				if (enumaction == EnumAction.bow){
				armorModel.aimedBow = true;
				}else if (enumaction == EnumAction.block){
				armorModel.heldItemRight = 3;
				}
				}
				}
			}
		}
				return armorModel;
	}
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		ItemStack boots = player.getCurrentArmor(0);
		ItemStack legs = player.getCurrentArmor(1);
		ItemStack chest = player.getCurrentArmor(2);
		ItemStack helmet = player.getCurrentArmor(3);

		if (helmet != null && boots != null && legs != null && chest != null && helmet != null) {
			if (boots.getItem() == MHFCRegItem.mhfcitemvelocipreyboots
					&& legs.getItem() == MHFCRegItem.mhfcitemvelocipreylegs
					&& chest.getItem() == MHFCRegItem.mhfcitemvelocipreychest
					&& helmet.getItem() == MHFCRegItem.mhfcitemvelocipreyhelm) {
			}
		}
	}

}
