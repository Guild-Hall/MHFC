package mhfc.net.common.item.armor.community;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.helper.ArmorMaterialHelper;
import mhfc.net.common.helper.ArmorModelHelper;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.system.DonatorSystem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ST_Bionic extends ItemArmor {
	private static final String[] names = { MHFCReference.armor_bionic_helm_name, MHFCReference.armor_bionic_chest_name,
			MHFCReference.armor_bionic_legs_name, MHFCReference.armor_bionic_boots_name };

	private static final String[] icons = { MHFCReference.armor_default_helm_icon,
			MHFCReference.armor_default_chest_icon, MHFCReference.armor_default_legs_icon,
			MHFCReference.armor_default_boots_icon };

	public ST_Bionic(int type) {
		super(ArmorMaterialHelper.ArmorST_1_Bionic, 4, type);
		setCreativeTab(MHFCMain.mhfctabs);
		setUnlocalizedName(names[type]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(icons[this.armorType]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List,
			boolean par4) {
		par3List.add(
				ColorSystem.ENUMRED + "(DONATORS EXCLUSIVE) Designed from Sean Tang" + "[ " + ColorSystem.ENUMGOLD
						+ " ST - 1 'Bionic'   " + ColorSystem.ENUMRED + "]");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (!(entity instanceof EntityPlayer) || stack == null) {
			return MHFCReference.armor_null_tex;
		}

		EntityPlayer player = (EntityPlayer) entity;
		if (!DonatorSystem.checkbioNic(player)) {
			return MHFCReference.armor_null_tex;
		}

		if (stack.getItem() == MHFCItemRegistry.getRegistry().armor_bionic_helm
				|| stack.getItem() == MHFCItemRegistry.getRegistry().armor_bionic_chest
				|| stack.getItem() == MHFCItemRegistry.getRegistry().armor_bionic_boots) {
			return MHFCReference.armor_bionic_tex1;
		}
		if (stack.getItem() == MHFCItemRegistry.getRegistry().armor_bionic_legs) {
			return MHFCReference.armor_bionic_tex2;
		}
		return MHFCReference.armor_null_tex;

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
			armorModel = ArmorModelHelper.bionic;
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

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		// The player needs to wear all armor pieces, so when we check on the
		// helmet it's enough
		if (this.armorType != 0) {
			return;
		}
		ItemStack boots = player.getCurrentArmor(0);
		ItemStack legs = player.getCurrentArmor(1);
		ItemStack chest = player.getCurrentArmor(2);
		if (chest != null && legs != null && boots != null
				&& chest.getItem() == MHFCItemRegistry.getRegistry().armor_bionic_chest
				&& boots.getItem() == MHFCItemRegistry.getRegistry().armor_bionic_boots
				&& legs.getItem() == MHFCItemRegistry.getRegistry().armor_bionic_legs) {}
	}

}
