package mhfc.heltrato.common.item.armor;

import java.util.List;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.core.registry.MHFCRegItem;
import mhfc.heltrato.common.helper.MHFCArmorModelHelper;
import mhfc.heltrato.common.helper.system.MHFCColorHelper;
import mhfc.heltrato.common.util.Donators;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DragoonArmor extends ItemArmor {
	
	
	private static final String[] icons_array = {
			MHFCReference.armor_default_helm_icon,
			MHFCReference.armor_default_chest_icon,
			MHFCReference.armor_default_legs_icon,
			MHFCReference.armor_default_boots_icon};

	// private Random rand;
	// private int param;

	public DragoonArmor(ArmorMaterial p_i45325_1_, int p_i45325_2_,
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
		par3List.add(MHFCColorHelper.RED + "Exclusive " + "[ " + MHFCColorHelper.GOLD + " DONATORS  " + MHFCColorHelper.RED + "]");
		par3List.add("Health +1");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			String type) 
	{
		
		EntityPlayer player = (EntityPlayer)entity;
		
		if(entity instanceof EntityPlayer)
		if (stack.getItem() == MHFCRegItem.mhfcitemdragoonhelm
				|| stack.getItem() == MHFCRegItem.mhfcitemdragoonchest
				|| stack.getItem() == MHFCRegItem.mhfcitemdragoonboots) {
			for(int i = 0; i < Donators.dragoonHelm.length; i++)
			if(player.getDisplayName().equals(Donators.dragoonHelm[i]))
			return MHFCReference.armor_dragoon_tex1;
		}
		if(stack.getItem() == MHFCRegItem.mhfcitemdragoonlegs)
			for(int i = 0; i < Donators.dragoonHelm.length; i++)
			if(player.getDisplayName().equals(Donators.dragoonHelm[i]))
			return MHFCReference.armor_dragoon_tex2;
		return "mhfc:textures/armor/null.png";
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,
			ItemStack itemStack, int armorSlot) {

		ModelBiped armorModel = null;

		if (itemStack != null) {
			int type = ((ItemArmor) itemStack.getItem()).armorType;

			if (type == 1 || type == 3 || type == 0) {
				armorModel = MHFCArmorModelHelper.getArmorModel(5);
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
	 public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	   {
		  // The player needs to wear all armor pieces, so when we check on the helmet it's enough
		  if(this.armorType != 0)
		   return;
		  ItemStack boots = player.getCurrentArmor(0);
		  ItemStack legs = player.getCurrentArmor(1);
		  ItemStack chest = player.getCurrentArmor(2);
		  
		  // If all items are our items (except helmet, as this method is only called if helmet is equipped
		  for(int i = 0; i < Donators.dragoonHelm.length; i++)
		  if( chest != null && legs != null && boots != null && 
		    chest.getItem() == MHFCRegItem.mhfcitemdragoonchest &&
		    boots.getItem() == MHFCRegItem.mhfcitemdragoonboots &&
		    legs.getItem() == MHFCRegItem.mhfcitemdragoonlegs){
			  if(player.getDisplayName().equals(Donators.dragoonHelm[i])){
		  }else{
			  	float h = player.getHealth();
		        player.removePotionEffect(21);
		        player.addPotionEffect(new PotionEffect(21, 200, 1, true));
		        player.setHealth(h);
		  }
	    }
	  }
	    
	  public int getHealthBoost(int slot)
	    {
	      if (slot == 3) {
	        return 1;
	     }
	     if (slot == 2) {
	        return 2;
	     }
	      if (slot == 1) {
	        return 1;
	      }
	    return 1;
	    }
	  }

