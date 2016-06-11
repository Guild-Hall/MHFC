package mhfc.net.common.item.food;

import mhfc.net.MHFCMain;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class ItemKirinBuff extends ItemFood {
	private static final int healAmount = 6;
	private static final int saturation = 100;
	private static final boolean isDogsFood = false;

	public ItemKirinBuff() {
		super(healAmount, saturation, isDogsFood);
		setUnlocalizedName(MHFCReference.armor_kirin_buff_name);
		setCreativeTab(MHFCMain.mhfctabs);
		setPotionEffect(Potion.resistance.id, 15, 4, 100);
	}

	@Override
	public void registerIcons(IIconRegister itemReg) {
		this.itemIcon = itemReg
				.registerIcon(MHFCReference.base_tool_potion);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderLayer) {
		return ItemColor.LIBLUE.getRGB();
	}
	
	

}
