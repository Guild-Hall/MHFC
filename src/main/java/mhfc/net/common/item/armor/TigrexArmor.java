package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCRegItem;
import mhfc.net.common.core.registry.MHFCRegPotion;
import mhfc.net.common.helper.MHFCArmorModelHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TigrexArmor extends ItemArmor{
	
	private Item item;
	private ItemFood FOODS;

	public TigrexArmor(ArmorMaterial p_i45325_1_, int p_i45325_2_,	int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	 @SideOnly(Side.CLIENT)
	  public void registerIcons(IIconRegister iconRegister) {
	     this.itemIcon = iconRegister.registerIcon("mhfc:mhf_" + this.armorType);
	  }
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
	String type) {
		if(stack.getItem() == MHFCRegItem.mhfcitemtigrexhelm || stack.getItem() == MHFCRegItem.mhfcitemtigrexchest || stack.getItem() == MHFCRegItem.mhfcitemtigrexboots){
			return "mhfc:textures/armor/tigrex_layer_1.png";
		}if(stack.getItem() == MHFCRegItem.mhfcitemtigrexlegs){
			return "mhfc:textures/armor/tigrex_layer_2.png";
		}
		return null;
	}
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("Quick Eating L");
		par3List.add("+ 15 Fire");
		par3List.add("- 10 Thunder");
	}
	
	

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,
			ItemStack itemStack, int armorSlot) {

		ModelBiped armorModel = null;

		if(itemStack != null){
			int type = ((ItemArmor)itemStack.getItem()).armorType;

			if(type == 1 || type == 3 || type == 0){
				armorModel = MHFCArmorModelHelper.getArmorModel(0);
			}
			if(armorModel != null){
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
				armorModel.heldItemRight = entityLiving.getEquipmentInSlot(0) != null ? 1 :0;
				if(entityLiving instanceof EntityPlayer){
					armorModel.aimedBow =((EntityPlayer)entityLiving).getItemInUseDuration() > 2;
				}
				return armorModel;
			}	
		}
		return null;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		ItemStack boots = player.getCurrentArmor(0);
		ItemStack legs = player.getCurrentArmor(1);
		ItemStack chest = player.getCurrentArmor(2);
		ItemStack helmet = player.getCurrentArmor(3);
		ItemStack food = player.getCurrentEquippedItem();
		if(boots != null && legs != null && chest != null && helmet != null)
		{
		if(boots.getItem() == MHFCRegItem.mhfcitemtigrexboots && legs.getItem() == MHFCRegItem.mhfcitemtigrexlegs &&
		chest.getItem() == MHFCRegItem.mhfcitemtigrexchest && helmet.getItem() == MHFCRegItem.mhfcitemtigrexhelm)
		{
			if(food != null &&food.getItem() instanceof ItemFood){
				int i = food.getItem().getMaxItemUseDuration(food);
				int j = 16;
				i = i - j;
				}
			}
		}

	}
	
}