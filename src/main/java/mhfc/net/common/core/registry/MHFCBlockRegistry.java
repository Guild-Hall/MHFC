package mhfc.net.common.core.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.container.BlockBBQSpit;
import mhfc.net.common.block.container.BlockHunterBench;
import mhfc.net.common.block.container.BlockStunTrap;
import mhfc.net.common.block.decoration.BlockClay;
import mhfc.net.common.block.decoration.BlockDirt;
import mhfc.net.common.block.decoration.BlockDiscstone;
import mhfc.net.common.block.decoration.BlockFlower;
import mhfc.net.common.block.decoration.BlockGrass;
import mhfc.net.common.block.decoration.BlockIceCrystal;
import mhfc.net.common.block.decoration.BlockLeaves;
import mhfc.net.common.block.decoration.BlockOreBlock;
import mhfc.net.common.block.decoration.BlockOres;
import mhfc.net.common.block.decoration.BlockPlank;
import mhfc.net.common.block.decoration.BlockPlant;
import mhfc.net.common.block.decoration.BlockQuickSand;
import mhfc.net.common.block.decoration.BlockRock;
import mhfc.net.common.block.decoration.BlockSand;
import mhfc.net.common.block.decoration.BlockStone;
import mhfc.net.common.block.decoration.BlockWood;
import mhfc.net.common.block.quest.BlockExploreArea;
import mhfc.net.common.block.quest.BlockQuestBoard;
import mhfc.net.common.block.quest.BlockRespawn;
import mhfc.net.common.item.block.ItemBlockBBQSpit;
import mhfc.net.common.item.block.ItemBlockBenchHunter;
import mhfc.net.common.item.block.ItemBlockIceCrystal;
import mhfc.net.common.item.block.ItemBlockQuestBoard;
import mhfc.net.common.item.block.ItemBlockStunTrap;
import mhfc.net.common.item.block.ItemSubtypedBlock;
import mhfc.net.common.util.services.IServiceKey;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MHFCBlockRegistry {

	/**
	 * 
	 * WorldEdit important commands.
	 * 
	 * /br sphere < block id > < radius /br smooth
	 * 
	 * /butcher
	 * 
	 * /repl <block id>
	 * 
	 * /gmask 0 ( prevents overlapping blocks and only overlaps air blocks) / gmask (block) (brush tool only blends with
	 * the block inputted) ex. /gmask 235, all 235 will only be replaced.
	 * 
	 * //pos1 ( sets position 1 ) can affect air //pos2 ( sets position 2 ) can affect air
	 * 
	 * //rotate 90 ( rotate copy block by 90 degrees)
	 * 
	 * ~Heltrato
	 * 
	 * 
	 */
	public static void staticInit() {}

	private static final IServiceKey<MHFCBlockRegistry> serviceAccess = RegistryWrapper
			.registerService("block registry", MHFCBlockRegistry::new, MHFCMain.preInitPhase);

	// private static Item item;
	// private static ItemBlockIngot ingot;
	public final Block mhfcblockoreblocks;
	public final Block mhfcblockore;

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
	public final Block mhfcblockplant;
	public final Block mhfcblockbbqspit;
	public final Block mhfcblockplanks;
	public final Block mhfcblockrocks;
	public final Block mhfcblockquicksand;
	public final Block mhfcblockwood;
	public final Block mhfcblockleaves;
	public final Block mhfcblockquestboard;
	public final Block mhfcblockrespawn;
	public final Block mhfcblockexplorearea;

	private final List<Pair<Block, Item>> everyBlockWithItem = new ArrayList<>();
	private final List<Block> everyBlock = new ArrayList<>();
	private final List<Item> everyItem = new ArrayList<>();

	public final Collection<Pair<Block, Item>> allBlocksWithItem = Collections.unmodifiableList(everyBlockWithItem);
	public final Collection<Block> allBlocks = Collections.unmodifiableList(everyBlock);
	public final Collection<Item> allItems = Collections.unmodifiableList(everyItem);

	private MHFCBlockRegistry() {
		mhfcblockdirt = registerBlock("dirt", new BlockDirt());//235
		mhfcblockgrass = registerBlock("grass", new BlockGrass()); //236
		mhfcblockstone = registerBlock("stone", new BlockStone());//237
		mhfcblocksand = registerBlock("sand", new BlockSand()); //set 238
		mhfcblockclay = registerBlock("clay_block", new BlockClay()); //239

		// Itemblocks. eg Plank woods this are register wit registerBlockWithItem
		mhfcblockwood = registerBlockWithItem("log", new BlockWood(), b -> ItemSubtypedBlock.createFor(b)); //240
		mhfcblockplanks = registerBlockWithItem("plank", new BlockPlank(), b -> ItemSubtypedBlock.createFor(b));//241

		mhfcblockrocks = registerBlockWithItem(
				"rock",
				new BlockRock(),
				b -> ItemSubtypedBlock.createFor(b).setMaxStackSize(20)); //242
		mhfcblockore = registerBlockWithItem(
				"ore",
				new BlockOres(),
				b -> ItemSubtypedBlock.createFor(b).setMaxStackSize(12)); //243
		mhfcblockoreblocks = registerBlockWithItem(
				"block",
				new BlockOreBlock(),
				b -> ItemSubtypedBlock.createFor(b).setMaxStackSize(16)); //244
		mhfcblockquicksand = registerBlock("quicksand", new BlockQuickSand()); //245
		mhfcblockdiskstone = registerBlock("diskstone", new BlockDiscstone());//246
		mhfcblockflowers = registerBlockWithItem(
				"flower",
				new BlockFlower(),
				b -> ItemSubtypedBlock.createFor(b).setFull3D().setMaxStackSize(4)); //247
		mhfcblockplant = registerBlockWithItem(
				"plant",
				new BlockPlant(),
				b -> ItemSubtypedBlock.createFor(b).setFull3D().setMaxStackSize(4)); //248
		mhfcblockleaves = registerBlockWithItem("leaves", new BlockLeaves(), b -> ItemSubtypedBlock.createFor(b)); //249
		// Util
		mhfcblockicecrystal = registerBlockWithItem("icecrystal", new BlockIceCrystal(), ItemBlockIceCrystal::new);
		mhfcblockhunterbench = registerBlockWithItem("hunterbench", new BlockHunterBench(), ItemBlockBenchHunter::new);
		mhfcblockstuntrap = registerBlockWithItem("trap_stun", new BlockStunTrap(), ItemBlockStunTrap::new);
		mhfcblockbbqspit = registerBlockWithItem("bbq_spit", new BlockBBQSpit(), ItemBlockBBQSpit::new);
		mhfcblockquestboard = registerBlockWithItem("questboard", new BlockQuestBoard(), ItemBlockQuestBoard::new);
		mhfcblockrespawn = registerBlock("respawn_marker", new BlockRespawn());
		mhfcblockexplorearea = registerBlock("exploration_teleporter", new BlockExploreArea());

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
		block.setRegistryName(blockRegistry);
		item.setRegistryName(blockRegistry);
		MHFCMain.logger().debug(
				"Registered " + block + " with id " + block.getRegistryName() + " and item " + item.getRegistryName());
		everyBlock.add(block);
		everyItem.add(item);
		everyBlockWithItem.add(Pair.of(block, item));
		GameRegistry.register(item);
		return GameRegistry.register(block);
	}

	public static MHFCBlockRegistry getRegistry() {
		return serviceAccess.getService();
	}
}
