package mhfc.heltrato.common.item.armor;

import java.util.Random;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.helper.MHFCArmorModelHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ArmorMHFC extends ItemArmor implements ISpecialArmor{
	/**TODO
	 * 
	 * Soon this will be the basing of all mhfc armor
	 * 
	*/
	public Random rand;
	public static int param;
	public static int modelID;
	public static int armorHeart;

	public ArmorMHFC(ArmorMaterial armor, int renderIndex, int armorType) {
		super(armor, renderIndex, armorType);
		setCreativeTab(MHFCMain.mhfctabs);
		rand = new Random();
		param = armorType;
	}
	

	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon("mhfc:mhf_" + this.armorType);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,
			ItemStack itemStack, int armorSlot) {

		ModelBiped armorModel = null;

		if(itemStack != null){
			int type = ((ItemArmor)itemStack.getItem()).armorType;

			if(type == 1 || type == 3 || type == 0){
				armorModel = MHFCArmorModelHelper.getArmorModel(modelID);
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
	public ArmorProperties getProperties(EntityLivingBase player,
			ItemStack armor, DamageSource source, double damage, int slot) {
		return null;
	}



	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return armorHeart;
	}



	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
		
	}
	
	

	
	

}
