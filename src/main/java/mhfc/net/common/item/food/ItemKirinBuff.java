package mhfc.net.common.item.food;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.IItemColored;
import mhfc.net.common.item.ItemColor;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class ItemKirinBuff extends ItemFood implements IItemColored {
	private static final int healAmount = 6;
	private static final int saturation = 100;
	private static final boolean isDogsFood = false;

	public ItemKirinBuff() {
		super(healAmount, saturation, isDogsFood);
		setTranslationKey(ResourceInterface.armor_kirin_buff_name);
		setCreativeTab(MHFCMain.mhfctabs);
		setPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 15, 4), 1f);
		setAlwaysEdible();
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
