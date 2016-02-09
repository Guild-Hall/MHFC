package mhfc.net.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockWyverniaMud extends BlockFalling {
	public static enum WyverniaMudSubType
			implements
				SubTypedItem.SubTypeEnum<Block> {
		QUICKSAND(MHFCReference.block_quicksand_name, MHFCReference.block_quicksand_tex), //
		ASH(MHFCReference.block_ashmud_name, MHFCReference.block_ashmud_tex), //
		DESERT(MHFCReference.block_desertmud_name, MHFCReference.block_desertmud_tex);

		public final String name;
		public final String texture;
		private WyverniaMudSubType(String name, String tex) {
			this.name = name;
			this.texture = tex;
		}
		@Override
		public String getName() {
			return name;
		}
		@Override
		public String getTexPath() {
			return texture;
		}
		@Override
		public Block getBaseItem() {
			return MHFCBlockRegistry.mhfcblockmuds;
		}
	}

	private final SubTypedItem<Block, WyverniaMudSubType> blockTrait;

	public BlockWyverniaMud() {
		super(Material.sand);
		blockTrait = new SubTypedItem<>(WyverniaMudSubType.class);
		setBlockName(MHFCReference.block_wyverniamud_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(1.1f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister regIcon) {
		blockTrait.registerIcons(regIcon);
	}

	@SuppressWarnings("unchecked")
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item block, CreativeTabs creativetabs,
			@SuppressWarnings("rawtypes") List list) {
		blockTrait.getSubItems(block, list);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return blockTrait.getIcons()[meta];
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (world.getBlockMetadata(x, y, z) == 0)
		{	
			if(entity instanceof EntityPlayer)
			entity.motionX *= 0.1D;
			entity.motionZ *= 0.1D;
		}
		else
		{
			if(entity instanceof EntityPlayer)
			entity.setInWeb();
		}
	}

}
