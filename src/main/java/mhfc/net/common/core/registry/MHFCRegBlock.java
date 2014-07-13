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
import mhfc.net.common.item.block.ItemBlockStunTrap;
import mhfc.net.common.item.block.ItemBlockWyverniaDefault;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCRegBlock {

	// private static MHFCConfig id;
	// private static Item item;
	// private static ItemBlockIngot ingot;
	public static Block mhfcblockcarbalite;
	public static Block mhfcblockdragonite;
	public static Block mhfcblockeltalite;
	public static Block mhfcblockmachalite;

	public static Block mhfcblocklosgable;
	public static Block mhfcblockarmorstandbase;
	public static Block mhfcblockarmorstand;
	public static Block mhfcblockhunterbench;
	public static Block mhfcblockstuntrap;
	public static Block mhfcblockdirt;
	public static Block mhfcblockgrass;
	public static Block mhfcblockstone;
	public static Block mhfcblockdiscstone;
	public static Block mhfcblockicecrystal;
	public static Block mhfcblockclay;
	public static Block mhfcblocksand;
	public static Block mhfcblockorearmorsphere;
	public static Block mhfcblockorearmorsphereplus;
	public static Block mhfcblockorecarbalite;
	public static Block mhfcblockoredragonite;
	public static Block mhfcblockoreeltalite;
	public static Block mhfcblockoremachalite;
	public static Block mhfcblockbbqspit;

	public static void init() {
		// Initiation Blocks
		mhfcblockcarbalite = new BlockCarbalite();
		mhfcblockdragonite = new BlockDragonite();
		mhfcblockeltalite = new BlockEltalite();
		mhfcblockmachalite = new BlockMachalite();
		mhfcblocklosgable = new BlockLosGable();
		mhfcblockarmorstandbase = new BlockArmorStandBase();
		mhfcblockarmorstand = new BlockArmorStand();
		mhfcblockhunterbench = new BlockHunterBench();
		mhfcblockstuntrap = new BlockStunTrap();
		mhfcblockdirt = new BlockWyverniaDirt();
		mhfcblockgrass = new BlockWyverniaGrass();
		mhfcblockstone = new BlockWyverniaStone();
		mhfcblockicecrystal = new BlockIceCrystal();
		mhfcblocksand = new BlockWyverniaSand();
		mhfcblockclay = new BlockWyverniaClay();
		mhfcblockdiscstone = new BlockDiscstone();
		mhfcblockorearmorsphere = new BlockOreArmorSphere();
		mhfcblockorearmorsphereplus = new BlockOreArmorSpherePlus();
		mhfcblockorecarbalite = new BlockOreCarbalite();
		mhfcblockoredragonite = new BlockOreDragonite();
		mhfcblockoreeltalite = new BlockOreEltalite();
		mhfcblockoremachalite = new BlockOreMachalite();
		mhfcblockbbqspit = new BlockBBQSpit();
		// ------------------------Game Registry
		// ------------------------------//
		regBlocks();
		regItemBlocks();
	}

	private static void regBlocks() {
		getBlockID(mhfcblockgrass);
		getBlockID(mhfcblockdirt);
		getBlockID(mhfcblockstone);
		getBlockID(mhfcblockclay);
		getBlockID(mhfcblocksand);
		getBlockID(mhfcblockdiscstone);
		getBlockID(mhfcblockicecrystal);
		getBlockID(mhfcblockcarbalite);
		getBlockID(mhfcblockdragonite);
		getBlockID(mhfcblockeltalite);
		getBlockID(mhfcblockmachalite);
		getBlockID(mhfcblockorearmorsphere);
		getBlockID(mhfcblockorearmorsphereplus);
		getBlockID(mhfcblockorecarbalite);
		getBlockID(mhfcblockoredragonite);
		getBlockID(mhfcblockoreeltalite);
		getBlockID(mhfcblockoremachalite);
		getBlockID(mhfcblocklosgable);
		getBlockID(mhfcblockarmorstandbase);
	}

	private static void regItemBlocks() {

		getItemBlockID(mhfcblockarmorstand, ItemBlockWyverniaDefault.class);
		getItemBlockID(mhfcblockstuntrap, ItemBlockStunTrap.class);
		getItemBlockID(mhfcblockhunterbench, ItemBlockBenchHunter.class);
		getItemBlockID(mhfcblockbbqspit, ItemBlockBBQSpit.class);
	}

	private static void getBlockID(Block block) {
		GameRegistry.registerBlock(block, block.getUnlocalizedName());

	}

	private static void getItemBlockID(Block block,
			Class<? extends ItemBlock> itemBlockClass) {
		GameRegistry.registerBlock(block, itemBlockClass,
				block.getUnlocalizedName());

	}

}
