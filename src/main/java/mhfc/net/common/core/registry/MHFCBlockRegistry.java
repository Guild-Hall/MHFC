package mhfc.net.common.core.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import mhfc.net.common.block.BlockDiscstone;
import mhfc.net.common.block.BlockIceCrystal;
import mhfc.net.common.block.BlockLosGable;
import mhfc.net.common.block.BlockQuestBoard;
import mhfc.net.common.block.BlockWyverniaClay;
import mhfc.net.common.block.BlockWyverniaDirt;
import mhfc.net.common.block.BlockWyverniaFlower;
import mhfc.net.common.block.BlockWyverniaGrass;
import mhfc.net.common.block.BlockWyverniaOreBlock;
import mhfc.net.common.block.BlockWyverniaOres;
import mhfc.net.common.block.BlockWyverniaPlank;
import mhfc.net.common.block.BlockWyverniaQuickSand;
import mhfc.net.common.block.BlockWyverniaRock;
import mhfc.net.common.block.BlockWyverniaSand;
import mhfc.net.common.block.BlockWyverniaStone;
import mhfc.net.common.block.BlockWyverniaWood;
import mhfc.net.common.block.container.BlockBBQSpit;
import mhfc.net.common.block.container.BlockHunterBench;
import mhfc.net.common.block.container.BlockStunTrap;
import mhfc.net.common.item.block.ItemBlockBBQSpit;
import mhfc.net.common.item.block.ItemBlockBenchHunter;
import mhfc.net.common.item.block.ItemBlockQuestBoard;
import mhfc.net.common.item.block.ItemBlockStunTrap;
import mhfc.net.common.item.block.ItemBlockWyverniaFlower;
import mhfc.net.common.item.block.ItemBlockWyverniaOreBlock;
import mhfc.net.common.item.block.ItemBlockWyverniaOres;
import mhfc.net.common.item.block.ItemBlockWyverniaPlank;
import mhfc.net.common.item.block.ItemBlockWyverniaRock;
import mhfc.net.common.item.block.ItemBlockWyverniaWood;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class MHFCBlockRegistry {

	// private static Item item;
	// private static ItemBlockIngot ingot;
	public static final Block mhfcblockoreblocks;
	public static final Block mhfcblockore;

	public static final Block mhfcblocklosgable;
	public static final Block mhfcblockhunterbench;
	public static final Block mhfcblockstuntrap;
	public static final Block mhfcblockdirt;
	public static final Block mhfcblockgrass;
	public static final Block mhfcblockstone;
	public static final Block mhfcblockdiskstone;
	public static final Block mhfcblockicecrystal;
	public static final Block mhfcblockclay;
	public static final Block mhfcblocksand;
	public static final Block mhfcblockflowers;
	public static final Block mhfcblockbbqspit;
	public static final Block mhfcblockplanks;
	public static final Block mhfcblockrocks;
	public static final Block mhfcblockquicksand;
	public static final Block mhfcblockwood;
	public static final Block mhfcblockquestboard;

	static {
		// Initialize Blocks
		mhfcblocklosgable = registerBlock(new BlockLosGable());
		mhfcblockdirt = registerBlock(new BlockWyverniaDirt());
		mhfcblockgrass = registerBlock(new BlockWyverniaGrass());
		mhfcblockstone = registerBlock(new BlockWyverniaStone());
		mhfcblockicecrystal = registerBlock(new BlockIceCrystal());
		mhfcblocksand = registerBlock(new BlockWyverniaSand());
		mhfcblockquicksand = registerBlock(new BlockWyverniaQuickSand());
		mhfcblockclay = registerBlock(new BlockWyverniaClay());
		mhfcblockdiskstone = registerBlock(new BlockDiscstone());
		// Initialize Blocks with special items
		mhfcblockhunterbench = registerBlockWithItem(new BlockHunterBench(),
				ItemBlockBenchHunter.class);
		mhfcblockstuntrap = registerBlockWithItem(new BlockStunTrap(),
				ItemBlockStunTrap.class);
		mhfcblockbbqspit = registerBlockWithItem(new BlockBBQSpit(),
				ItemBlockBBQSpit.class);
		mhfcblockoreblocks = registerBlockWithItem(new BlockWyverniaOreBlock(),
				ItemBlockWyverniaOreBlock.class);
		mhfcblockore = registerBlockWithItem(new BlockWyverniaOres(),
				ItemBlockWyverniaOres.class);
		mhfcblockflowers = registerBlockWithItem(new BlockWyverniaFlower(),
				ItemBlockWyverniaFlower.class);
		mhfcblockplanks = registerBlockWithItem(new BlockWyverniaPlank(),
				ItemBlockWyverniaPlank.class);
		mhfcblockrocks = registerBlockWithItem(new BlockWyverniaRock(),
				ItemBlockWyverniaRock.class);
		mhfcblockwood = registerBlockWithItem(new BlockWyverniaWood(),
				ItemBlockWyverniaWood.class);
		mhfcblockquestboard = registerBlockWithItem(new BlockQuestBoard(),
				ItemBlockQuestBoard.class);
	}

	public static void init() {
	}

	private static Block registerBlock(Block block) {
		return registerBlock(block, block.getUnlocalizedName());
	}

	private static Block registerBlock(Block block, String name) {
		return GameRegistry.registerBlock(block, name);
	}

	private static Block registerBlockWithItem(Block block,
			Class<? extends ItemBlock> itemBlockClass) {
		return registerBlockWithItem(block, itemBlockClass,
				block.getUnlocalizedName());
	}

	private static Block registerBlockWithItem(Block block,
			Class<? extends ItemBlock> itemBlockClass, String name) {
		return GameRegistry.registerBlock(block, itemBlockClass, name);
	}

}
