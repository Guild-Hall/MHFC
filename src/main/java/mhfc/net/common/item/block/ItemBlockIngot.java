package mhfc.net.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockIngot extends ItemBlock {

	private static final String[] blockofTypes = new String[]{"blockof0",
			"blockof1", "blockof2"};

	// private BlockIngots ingot;

	public ItemBlockIngot(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int met) {
		return met & 4;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		int meta = itemStack.getItemDamage();
		if (meta < 0 || meta >= blockofTypes.length) {
			meta = 0;
		}

		return super.getUnlocalizedName() + "." + blockofTypes[meta];
	}

}
