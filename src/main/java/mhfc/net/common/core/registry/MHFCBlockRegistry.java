package mhfc.net.common.core.registry;

import java.util.function.Function;

import mhfc.net.MHFCMain;
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
import mhfc.net.common.block.area.BlockExploreArea;
import mhfc.net.common.block.area.BlockRespawn;
import mhfc.net.common.block.container.BlockBBQSpit;
import mhfc.net.common.block.container.BlockHunterBench;
import mhfc.net.common.block.container.BlockStunTrap;
import mhfc.net.common.item.block.ItemBlockBBQSpit;
import mhfc.net.common.item.block.ItemBlockBenchHunter;
import mhfc.net.common.item.block.ItemBlockQuestBoard;
import mhfc.net.common.item.block.ItemBlockStunTrap;
import mhfc.net.common.item.block.ItemSubtypedBlock;
import mhfc.net.common.util.services.IServiceKey;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MHFCBlockRegistry {
	public static void staticInit() {}

	private static final IServiceKey<MHFCBlockRegistry> serviceAccess = RegistryWrapper
			.registerService("block registry", MHFCBlockRegistry::new, MHFCMain.preInitPhase);

	// private static Item item;
	// private static ItemBlockIngot ingot;
	public final Block mhfcblockoreblocks;
	public final Block mhfcblockore;

	public final Block mhfcblocklosgable;
	public final Block mhfcblockhunterbench;
	public final Block mhfcblockstuntrap;
	public final Block mhfcblockdirt;
	public final Block mhfcblockgrass;
	public final Block mhfcblockstone;
	public final Block mhfcblockdiskstone;
	public final Block mhfcblockicecrystal;
	public final Block mhfcblockclay;
	public final Block mhfcblocksand;
	public final Block mhfcblockflowers;
	public final Block mhfcblockbbqspit;
	public final Block mhfcblockplanks;
	public final Block mhfcblockrocks;
	public final Block mhfcblockquicksand;
	public final Block mhfcblockwood;
	public final Block mhfcblockquestboard;
	public final Block mhfcblockrespawn;
	public final Block mhfcblockexplorearea;

	private MHFCBlockRegistry() {
		mhfcblockdirt = registerBlock("dirt_wyvernian", new BlockWyverniaDirt());
		mhfcblockgrass = registerBlock("grass_wyvernian", new BlockWyverniaGrass());
		mhfcblockstone = registerBlock("stone_wyvernian", new BlockWyverniaStone());
		mhfcblocksand = registerBlock("sand_wyvernian", new BlockWyverniaSand());
		mhfcblockclay = registerBlock("clay_wyvernian", new BlockWyverniaClay());
		mhfcblockwood = registerBlockWithItem(
				"wood_wyvernian",
				new BlockWyverniaWood(),
				b -> ItemSubtypedBlock.createFor(b));
		mhfcblockplanks = registerBlockWithItem(
				"plank_wyvernian",
				new BlockWyverniaPlank(),
				b -> ItemSubtypedBlock.createFor(b));
		mhfcblockflowers = registerBlockWithItem(
				"flower_wyvernian",
				new BlockWyverniaFlower(),
				b -> ItemSubtypedBlock.createFor(b).setFull3D().setMaxStackSize(4));
		mhfcblockrocks = registerBlockWithItem(
				"rock_wyvernian",
				new BlockWyverniaRock(),
				b -> ItemSubtypedBlock.createFor(b).setMaxStackSize(20));
		mhfcblockore = registerBlockWithItem(
				"ore",
				new BlockWyverniaOres(),
				b -> ItemSubtypedBlock.createFor(b).setMaxStackSize(12));
		mhfcblockoreblocks = registerBlockWithItem(
				"ore_block",
				new BlockWyverniaOreBlock(),
				b -> ItemSubtypedBlock.createFor(b).setMaxStackSize(16));

		mhfcblocklosgable = registerBlock("los_gable", new BlockLosGable());
		mhfcblockicecrystal = registerBlock("icecrystal", new BlockIceCrystal());
		mhfcblockquicksand = registerBlock("quicksand", new BlockWyverniaQuickSand());
		mhfcblockdiskstone = registerBlock("diskstone", new BlockDiscstone());

		mhfcblockhunterbench = registerBlockWithItem("hunterbench", new BlockHunterBench(), ItemBlockBenchHunter::new);
		mhfcblockstuntrap = registerBlockWithItem("trap_stun", new BlockStunTrap(), ItemBlockStunTrap::new);
		mhfcblockbbqspit = registerBlockWithItem("bbq_spit", new BlockBBQSpit(), ItemBlockBBQSpit::new);
		mhfcblockquestboard = registerBlockWithItem("questboard", new BlockQuestBoard(), ItemBlockQuestBoard::new);
		mhfcblockrespawn = registerBlock("respawn_marker", new BlockRespawn());
		mhfcblockexplorearea = registerBlock("eploration_teleporter", new BlockExploreArea());

		MHFCMain.logger().info("Blocks registered");
	}

	private <T extends Block> T registerBlock(String registryName, T block) {
		return registerBlockWithItem(registryName, block, new ItemBlock(block));
	}

	private <T extends Block> T registerBlockWithItem(
			String blockRegistry,
			T block,
			Function<? super T, ? extends Item> item) {
		return registerBlockWithItem(blockRegistry, block, item.apply(block));
	}

	private <T extends Block> T registerBlockWithItem(String blockRegistry, T block, Item item) {
		item.setRegistryName(blockRegistry);
		block.setRegistryName(blockRegistry);
		GameRegistry.register(item);
		return GameRegistry.register(block);
	}

	public static MHFCBlockRegistry getRegistry() {
		return serviceAccess.getService();
	}
}
