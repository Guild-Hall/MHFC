package mhfc.net.common.core.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import mhfc.net.MHFCMain;
import mhfc.net.common.block.BlockQuestBoard;
import mhfc.net.common.block.BlockWyverniaOreBlock;
import mhfc.net.common.block.BlockWyverniaOres;
import mhfc.net.common.block.area.BlockExploreArea;
import mhfc.net.common.block.area.BlockRespawn;
import mhfc.net.common.block.container.BlockBBQSpit;
import mhfc.net.common.block.container.BlockHunterBench;
import mhfc.net.common.block.container.BlockStunTrap;
import mhfc.net.common.item.block.ItemBlockBBQSpit;
import mhfc.net.common.item.block.ItemBlockBenchHunter;
import mhfc.net.common.item.block.ItemBlockQuestBoard;
import mhfc.net.common.item.block.ItemBlockStunTrap;
import mhfc.net.common.item.block.ItemBlockWyverniaOreBlock;
import mhfc.net.common.item.block.ItemBlockWyverniaOres;
import mhfc.net.common.util.services.IServiceKey;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class MHFCBlockRegistry {
	public static void staticInit() {}

	private static final IServiceKey<MHFCBlockRegistry> serviceAccess = RegistryWrapper
			.registerService("block registry", MHFCBlockRegistry::new, MHFCMain.initPhase);

	// private static Item item;
	// private static ItemBlockIngot ingot;
	public final Block mhfcblockoreblocks;
	public final Block mhfcblockore;

	//public final Block mhfcblocklosgable;
	public final Block mhfcblockhunterbench;
	public final Block mhfcblockstuntrap;
	//public final Block mhfcblockdirt;
	//public final Block mhfcblockgrass;
	//public final Block mhfcblockstone;
	//public final Block mhfcblockdiskstone;
	//public final Block mhfcblockicecrystal;
	//public final Block mhfcblockclay;
	//public final Block mhfcblocksand;
	//public final Block mhfcblockflowers;
	public final Block mhfcblockbbqspit;
	//public final Block mhfcblockplanks;
	//public final Block mhfcblockrocks;
	//public final Block mhfcblockquicksand;
	//public final Block mhfcblockwood;
	public final Block mhfcblockquestboard;
	public final Block mhfcblockrespawn;
	public final Block mhfcblockexplorearea;

	private MHFCBlockRegistry() {
		// Initialize Blocks
	//	mhfcblocklosgable = registerBlock(new BlockLosGable());
	//	mhfcblockdirt = registerBlock(new BlockWyverniaDirt());
	//	mhfcblockgrass = registerBlock(new BlockWyverniaGrass());
	//	mhfcblockstone = registerBlock(new BlockWyverniaStone());
	//	mhfcblockicecrystal = registerBlock(new BlockIceCrystal());
	//	mhfcblocksand = registerBlock(new BlockWyverniaSand());
		
	//	mhfcblockclay = registerBlock(new BlockWyverniaClay());
	//	mhfcblockdiskstone = registerBlock(new BlockDiscstone());
		// Initialize Blocks with special items
		
	//	mhfcblockquicksand = registerBlock(new BlockWyverniaQuickSand());
		
	//	mhfcblockflowers = registerBlockWithItem(new BlockWyverniaFlower(), ItemBlockWyverniaFlower.class);
	//	mhfcblockplanks = registerBlockWithItem(new BlockWyverniaPlank(), ItemBlockWyverniaPlank.class);
	//	mhfcblockrocks = registerBlockWithItem(new BlockWyverniaRock(), ItemBlockWyverniaRock.class);
	//	mhfcblockwood = registerBlockWithItem(new BlockWyverniaWood(), ItemBlockWyverniaWood.class);
		
		mhfcblockhunterbench = registerBlockWithItem(new BlockHunterBench(), ItemBlockBenchHunter.class);
		mhfcblockstuntrap = registerBlockWithItem(new BlockStunTrap(), ItemBlockStunTrap.class);
		mhfcblockbbqspit = registerBlockWithItem(new BlockBBQSpit(), ItemBlockBBQSpit.class);
		mhfcblockoreblocks = registerBlockWithItem(new BlockWyverniaOreBlock(), ItemBlockWyverniaOreBlock.class);
		mhfcblockore = registerBlockWithItem(new BlockWyverniaOres(), ItemBlockWyverniaOres.class);
		mhfcblockquestboard = registerBlockWithItem(new BlockQuestBoard(), ItemBlockQuestBoard.class);
		mhfcblockrespawn = registerBlock(new BlockRespawn());
		mhfcblockexplorearea = registerBlock(new BlockExploreArea());

		MHFCMain.logger().info("Blocks registered");
	}

	private Block registerBlock(Block block) {
		return registerBlock(block, block.getUnlocalizedName());
	}

	private Block registerBlock(Block block, String name) {
		return GameRegistry.registerBlock(block, name);
	}

	private Block registerBlockWithItem(Block block, Class<? extends ItemBlock> itemBlockClass) {
		return registerBlockWithItem(block, itemBlockClass, block.getUnlocalizedName());
	}

	private Block registerBlockWithItem(Block block, Class<? extends ItemBlock> itemBlockClass, String name) {
		return GameRegistry.registerBlock(block, itemBlockClass, name);
	}

	public static MHFCBlockRegistry getRegistry() {
		return serviceAccess.getService();
	}
}
