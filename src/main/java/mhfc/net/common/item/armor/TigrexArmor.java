package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCRegItem;
import mhfc.net.common.helper.MHFCArmorModelHelper;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TigrexArmor extends ItemArmor {
	private static final String[] icons_array = {
			MHFCReference.armor_tigrex_helm_icon,
			MHFCReference.armor_tigrex_chest_icon,
			MHFCReference.armor_tigrex_legs_icon,
			MHFCReference.armor_tigrex_boots_icon};

	// private Item item;
	// private ItemFood FOODS;
	private int param;

	public TigrexArmor(ArmorMaterial p_i45325_1_, int p_i45325_2_,
			int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
		setCreativeTab(MHFCMain.mhfctabs);
		param = p_i45325_2_;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(icons_array[this.armorType]);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			String type) {
		if (stack.getItem() == MHFCRegItem.mhfcitemtigrexhelm
				|| stack.getItem() == MHFCRegItem.mhfcitemtigrexchest
				|| stack.getItem() == MHFCRegItem.mhfcitemtigrexboots) {
			return MHFCReference.armor_tigrex_tex1;
		}
		if (stack.getItem() == MHFCRegItem.mhfcitemtigrexlegs) {
			return MHFCReference.armor_tigrex_tex2;
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		par3List.add("Quick Eating L");
		par3List.add("+ 15 Fire");
		par3List.add("- 10 Thunder");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,
			ItemStack itemStack, int armorSlot) {

		ModelBiped armorModel = null;

		if (itemStack != null) {
			int type = ((ItemArmor) itemStack.getItem()).armorType;

			if (type == 1 || type == 3 || type == 0) {
				armorModel = MHFCArmorModelHelper.getArmorModel(0);
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
	public void onArmorTick(World world, EntityPlayer player,ItemStack itemstack) {
		 if(this.armorType != 0)
			   return;
			  ItemStack boots = player.getCurrentArmor(0);
			  ItemStack legs = player.getCurrentArmor(1);
			  ItemStack chest = player.getCurrentArmor(2);
		ItemStack food = player.getCurrentEquippedItem();
		  if( chest != null && legs != null && boots != null && 
				    chest.getItem() == MHFCRegItem.mhfcitemtigrexchest &&
				    boots.getItem() == MHFCRegItem.mhfcitemtigrexboots &&
				    legs.getItem() == MHFCRegItem.mhfcitemtigrexlegs){
				if (food != null && food.getItem() instanceof ItemFood) {
					int i = food.getItem().getMaxItemUseDuration(food);
					int j = 16;
					i = i - j;
				}
			}
		}

	}

