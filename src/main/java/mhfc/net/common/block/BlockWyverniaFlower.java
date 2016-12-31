package mhfc.net.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.common.block.environment.BlockWyverniaDecor;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockWyverniaFlower extends BlockWyverniaDecor {
	public static enum WyverniaFlowerSubType implements SubTypedItem.SubTypeEnum<Block> {
		CARNCASE(MHFCReference.block_carncase_name, MHFCReference.block_carncase_tex), //
		FELRON(MHFCReference.block_felron_name, MHFCReference.block_felron_tex), //
		ORCTAL(MHFCReference.block_orctal_name, MHFCReference.block_orctal_tex), //
		PENO(MHFCReference.block_peno_name, MHFCReference.block_peno_tex), //
		SHRINE(MHFCReference.block_shrine_name, MHFCReference.block_shrine_tex), //
		SPINDEL(MHFCReference.block_spindel_name, MHFCReference.block_spindel_tex);

		public final String name;
		public final String texture;

		private WyverniaFlowerSubType(String name, String tex) {
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
			return null;//MHFCBlockRegistry.getRegistry().mhfcblockflowers;
		}
	}

	private final SubTypedItem<Block, WyverniaFlowerSubType> blockTrait;

	public BlockWyverniaFlower() {
		super(Material.plants);
		blockTrait = new SubTypedItem<>(WyverniaFlowerSubType.class);
		setBlockName(MHFCReference.block_wyverniaflower_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(0.0f);
		setStepSound(Block.soundTypeGrass);
		setTickRandomly(true);
		float f = 0.2F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
	}

	// TODO might have bugs.
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public int getRenderType() {
		return 1; // Magic number.
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void registerBlockIcons(IIconRegister registry) {
		blockTrait.registerIcons(registry);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, @SuppressWarnings("rawtypes") List list) {
		blockTrait.getSubItems(item, list);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta >= this.blockTrait.getIcons().length) {
			meta = 0;
		}
		return this.blockTrait.getIcons()[meta];
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public boolean isValidPosition(World world, int x, int y, int z, int metadata) {
		// TODO: getBlock()
		//Block block = world.getBlock(x, y - 1, z);
		return null != null;//block == MHFCBlockRegistry.getRegistry().mhfcblockdirt || block == MHFCBlockRegistry.getRegistry().mhfcblockgrass || block == Blocks.glass;
	}

}
