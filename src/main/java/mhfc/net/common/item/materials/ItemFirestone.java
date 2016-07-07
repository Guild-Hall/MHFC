package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFirestone extends Item {

	public ItemFirestone() {
		maxStackSize = 12;
		setUnlocalizedName(MHFCReference.item_firestone_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister
				.registerIcon(MHFCReference.base_misc_ore);
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		switch (par7) {
			case 0 :
				--par5;
				break;
			case 1 :
				++par5;
				break;
			case 2 :
				--par6;
				break;
			case 3 :
				++par6;
				break;
			case 4 :
				--par4;
				break;
			default :
				++par4;
				break;
		}
		if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7,
				par1ItemStack)) {
			return false;
		}
		if (par3World.isAirBlock(par4, par5, par6)) {
			par3World.playSoundEffect(par4 + 0.5D, par5 + 0.5D, par6 + 0.5D,
					"fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
			par3World.setBlock(par4, par5, par6, Blocks.fire);
		}
		return true;

	}


	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return ItemColor.RED.getRGB();
	}
}
