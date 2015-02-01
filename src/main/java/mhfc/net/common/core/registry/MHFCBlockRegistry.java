package mhfc.net.common.core.registry;

import mhfc.net.common.block.BlockCarbalite;
import mhfc.net.common.block.BlockDiscstone;
import mhfc.net.common.block.BlockDragonite;
import mhfc.net.common.block.BlockEltalite;
import mhfc.net.common.block.BlockIceCrystal;
import mhfc.net.common.block.BlockLosGable;
import mhfc.net.common.block.BlockMachalite;
import mhfc.net.common.block.BlockOreArmorSphere;
import mhfc.net.common.block.BlockOreArmorSpherePlus;
import mhfc.net.common.block.BlockOreCarbalite;
import mhfc.net.common.block.BlockOreDragonite;
import mhfc.net.common.block.BlockOreEltalite;
import mhfc.net.common.block.BlockOreMachalite;
import mhfc.net.common.block.BlockQuestBoard;
import mhfc.net.common.block.BlockWyverniaClay;
import mhfc.net.common.block.BlockWyverniaDirt;
import mhfc.net.common.block.BlockWyverniaGrass;
import mhfc.net.common.block.BlockWyverniaSand;
import mhfc.net.common.block.BlockWyverniaStone;
import mhfc.net.common.block.container.BlockArmorStand;
import mhfc.net.common.block.container.BlockArmorStandBase;
import mhfc.net.common.block.container.BlockBBQSpit;
import mhfc.net.common.block.container.BlockHunterBench;
import mhfc.net.common.block.container.BlockStunTrap;
import mhfc.net.common.item.block.ItemBlockBBQSpit;
import mhfc.net.common.item.block.ItemBlockBenchHunter;
import mhfc.net.common.item.block.ItemBlockQuestBoard;
import mhfc.net.common.item.block.ItemBlockStunTrap;
import mhfc.net.common.item.block.ItemBlockWyverniaDefault;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCBlockRegistry {

	// private static Item item;
	// private static ItemBlockIngot ingot;
	public static final Block mhfcblockcarbalite;
	public static final Block mhfcblockdragonite;
	public static final Block mhfcblockeltalite;
	public static final Block mhfcblockmachalite;

	public static final Block mhfcblocklosgable;
	public static final Block mhfcblockarmorstandbase;
	public static final Block mhfcblockarmorstand;
	public static final Block mhfcblockhunterbench;
	public static final Block mhfcblockstuntrap;
	public static final Block mhfcblockdirt;
	public static final Block mhfcblockgrass;
	public static final Block mhfcblockstone;
	public static final Block mhfcblockdiscstone;
	public static final Block mhfcblockicecrystal;
	public static final Block mhfcblockclay;
	public static final Block mhfcblocksand;
	public static final Block mhfcblockorearmorsphere;
	public static final Block mhfcblockorearmorsphereplus;
	public static final Block mhfcblockorecarbalite;
	public static final Block mhfcblockoredragonite;
	public static final Block mhfcblockoreeltalite;
	public static final Block mhfcblockoremachalite;
	public static final Block mhfcblockbbqspit;
	public static final Block mhfcblockquestboard;

	static {
		// Initialize Blocks
		mhfcblockcarbalite = registerBlock(new BlockCarbalite());
		mhfcblockdragonite = registerBlock(new BlockDragonite());
		mhfcblockeltalite = registerBlock(new BlockEltalite());
		mhfcblockmachalite = registerBlock(new BlockMachalite());
		mhfcblocklosgable = registerBlock(new BlockLosGable());
		mhfcblockdirt = registerBlock(new BlockWyverniaDirt());
		mhfcblockgrass = registerBlock(new BlockWyverniaGrass());
		mhfcblockstone = registerBlock(new BlockWyverniaStone());
		mhfcblockicecrystal = registerBlock(new BlockIceCrystal());
		mhfcblocksand = registerBlock(new BlockWyverniaSand());
		mhfcblockclay = registerBlock(new BlockWyverniaClay());
		mhfcblockdiscstone = registerBlock(new BlockDiscstone());
		mhfcblockorearmorsphere = registerBlock(new BlockOreArmorSphere());
		mhfcblockorearmorsphereplus = registerBlock(new BlockOreArmorSpherePlus());
		mhfcblockorecarbalite = registerBlock(new BlockOreCarbalite());
		mhfcblockoredragonite = registerBlock(new BlockOreDragonite());
		mhfcblockoreeltalite = registerBlock(new BlockOreEltalite());
		mhfcblockoremachalite = registerBlock(new BlockOreMachalite());
		mhfcblockarmorstandbase = registerBlock(new BlockArmorStandBase());
		// Initialize Blocks with special items
		mhfcblockhunterbench = registerBlockWithItem(new BlockHunterBench(),
				ItemBlockBenchHunter.class);
		mhfcblockarmorstand = registerBlockWithItem(new BlockArmorStand(),
				ItemBlockWyverniaDefault.class);
		mhfcblockstuntrap = registerBlockWithItem(new BlockStunTrap(),
				ItemBlockStunTrap.class);
		mhfcblockbbqspit = registerBlockWithItem(new BlockBBQSpit(),
				ItemBlockBBQSpit.class);
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
