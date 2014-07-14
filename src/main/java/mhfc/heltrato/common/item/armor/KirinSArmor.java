package mhfc.heltrato.common.item.armor;

import java.util.List;
import java.util.Random;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.core.registry.MHFCRegItem;
import mhfc.heltrato.common.core.registry.MHFCRegPotion;
import mhfc.heltrato.common.helper.MHFCArmorModelHelper;
import mhfc.heltrato.common.helper.MHFCParticleHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class KirinSArmor extends ItemArmor  {
	
	private Random rand;
	private int param;

	public KirinSArmor(ArmorMaterial armor, int par1, int par2) {
		super(armor, par1, par2);
		setCreativeTab(MHFCMain.mhfctabs);
		rand = new Random();
		param = par2;
	}
	
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("All Resistance H");
		par3List.add("Thunder + 40");
		par3List.add("Aura");
		if(param == 0){
			par3List.add("\u00a79Kirin S Class Helmet");
		}else
		if(param == 1){
			par3List.add("\u00a79Kirin S Class Chest");
		}
		if(param == 2){
			par3List.add("\u00a79Kirin S Class Leggings");
		}
		if(param == 3){
			par3List.add("\u00a79Kirin S Class Boots");
		}
	}
	
	@SideOnly(Side.CLIENT)
	  public void registerIcons(IIconRegister iconRegister) {
	     this.itemIcon = iconRegister.registerIcon("mhfc:mhf_" + this.armorType);
	  }
	

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
	String type) {
		if(stack.getItem() == MHFCRegItem.mhfcitemkirinShelm || stack.getItem() == MHFCRegItem.mhfcitemkirinSchest || stack.getItem() == MHFCRegItem.mhfcitemkirinSboots){
			return "mhfc:textures/armor/kirinS_layer_1.png";
		}if(stack.getItem() == MHFCRegItem.mhfcitemkirinSlegs){
			return "mhfc:textures/armor/kirinS_layer_2.png";
		}
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,
			ItemStack itemStack, int armorSlot) {

		ModelBiped armorModel = null;

		if(itemStack != null){
			int type = ((ItemArmor)itemStack.getItem()).armorType;

			if(type == 1 || type == 3 || type == 0){
				armorModel = MHFCArmorModelHelper.getArmorModel(2);
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
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		ItemStack boots = player.getCurrentArmor(0);
		ItemStack legs = player.getCurrentArmor(1);
		ItemStack chest = player.getCurrentArmor(2);
		ItemStack helmet = player.getCurrentArmor(3);
		int duration = 15;
		if(boots != null && legs != null && chest != null && helmet != null)
		{
		if(boots.getItem() == MHFCRegItem.mhfcitemkirinSboots && legs.getItem() == MHFCRegItem.mhfcitemkirinSlegs &&
		chest.getItem() == MHFCRegItem.mhfcitemkirinSchest && helmet.getItem() == MHFCRegItem.mhfcitemkirinShelm)
		{
			player.addPotionEffect(new PotionEffect(MHFCRegPotion.mhfcpotionkirinbless.id, duration++, 1));
			world.spawnParticle("cloud", player.posX + this.rand.nextFloat() * 2.0F - 1.0D, player.posY + this.rand.nextFloat() * 3.0F + 1.0D, player.posZ + this.rand.nextFloat() * 2.0F - 1.0D, 0.0D, 0.0D, 0.0D);
		}
		}
	}
	
}
	
	
	

