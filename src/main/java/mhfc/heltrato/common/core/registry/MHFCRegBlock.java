package mhfc.heltrato.common.core.registry;

import mhfc.heltrato.common.block.BlockDiscstone;
import mhfc.heltrato.common.block.BlockIceCrystal;
import mhfc.heltrato.common.block.BlockLosGable;
import mhfc.heltrato.common.block.BlockWyverniaClay;
import mhfc.heltrato.common.block.BlockWyverniaDirt;
import mhfc.heltrato.common.block.BlockWyverniaFlower;
import mhfc.heltrato.common.block.BlockWyverniaGrass;
import mhfc.heltrato.common.block.BlockWyverniaOreBlock;
import mhfc.heltrato.common.block.BlockWyverniaOres;
import mhfc.heltrato.common.block.BlockWyverniaPlank;
import mhfc.heltrato.common.block.BlockWyverniaRock;
import mhfc.heltrato.common.block.BlockWyverniaSand;
import mhfc.heltrato.common.block.BlockWyverniaStone;
import mhfc.heltrato.common.block.BlockWyverniaWood;
import mhfc.heltrato.common.block.container.BlockBBQSpit;
import mhfc.heltrato.common.block.container.BlockHunterBench;
import mhfc.heltrato.common.block.container.BlockStunTrap;
import mhfc.heltrato.common.configuration.MHFCConfig;
import mhfc.heltrato.common.item.block.ItemBlockBBQSpit;
import mhfc.heltrato.common.item.block.ItemBlockBenchHunter;
import mhfc.heltrato.common.item.block.ItemBlockIngot;
import mhfc.heltrato.common.item.block.ItemBlockStunTrap;
import mhfc.heltrato.common.item.block.ItemBlockWyverniaDefault;
import mhfc.heltrato.common.item.block.ItemBlockWyverniaFlower;
import mhfc.heltrato.common.item.block.ItemBlockWyverniaOreBlock;
import mhfc.heltrato.common.item.block.ItemBlockWyverniaOres;
import mhfc.heltrato.common.item.block.ItemBlockWyverniaPlank;
import mhfc.heltrato.common.item.block.ItemBlockWyverniaRock;
import mhfc.heltrato.common.item.block.ItemBlockWyverniaWood;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.registry.GameRegistry;

public class MHFCRegBlock {
	
	private static GameRegistry blockreg;
	private static MHFCConfig id;
	private static Item item;
	private static ItemBlockIngot ingot;
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
	public static Block mhfcblockbbqspit;
	public static Block mhfcblockores;
	public static Block mhfcblockoreblocks;
	public static Block mhfcblockflowers;
	public static Block mhfcblockplanks;
	public static Block mhfcblockrocks;
	public static Block mhfcblockwood;
	
	public static void init()
	{
		// Initiation Blocks
		mhfcblocklosgable = new BlockLosGable();
		mhfcblockoreblocks = new BlockWyverniaOreBlock();
		mhfcblockhunterbench = new BlockHunterBench();
		mhfcblockstuntrap = new BlockStunTrap();
		mhfcblockdirt = new BlockWyverniaDirt();
		mhfcblockgrass = new BlockWyverniaGrass();
		mhfcblockstone = new BlockWyverniaStone();
		mhfcblockicecrystal = new BlockIceCrystal();
		mhfcblocksand = new BlockWyverniaSand();
		mhfcblockclay = new BlockWyverniaClay();
		mhfcblockdiscstone = new BlockDiscstone();
		mhfcblockores = new BlockWyverniaOres();
		mhfcblockbbqspit = new BlockBBQSpit();
		mhfcblockflowers = new BlockWyverniaFlower();
		mhfcblockplanks = new BlockWyverniaPlank();
		mhfcblockrocks = new BlockWyverniaRock();
		mhfcblockwood = new BlockWyverniaWood();
	
		
	//------------------------Game Registry ------------------------------//
		regBlocks();
		regItemBlocks();
	}
	
	private static void regBlocks()
	{
		getBlockID(mhfcblockgrass);
		getBlockID(mhfcblockdirt);
		getBlockID(mhfcblockstone);
		getBlockID(mhfcblockclay);
		getBlockID(mhfcblocksand);
		getBlockID(mhfcblockdiscstone);
		getBlockID(mhfcblockicecrystal);
		getBlockID(mhfcblocklosgable);
	}
	
	private static void regItemBlocks()
	{
		getItemBlockID(mhfcblockplanks, ItemBlockWyverniaPlank.class);
		getItemBlockID(mhfcblockrocks, ItemBlockWyverniaRock.class);
		getItemBlockID(mhfcblockores, ItemBlockWyverniaOres.class);
		getItemBlockID(mhfcblockwood, ItemBlockWyverniaWood.class);
		getItemBlockID(mhfcblockoreblocks, ItemBlockWyverniaOreBlock.class);
		getItemBlockID(mhfcblockstuntrap, ItemBlockStunTrap.class);
		getItemBlockID(mhfcblockhunterbench, ItemBlockBenchHunter.class);
		getItemBlockID(mhfcblockbbqspit, ItemBlockBBQSpit.class);
		getItemBlockID(mhfcblockflowers, ItemBlockWyverniaFlower.class);
		
		
	}
	
	private static void getBlockID(Block block)
	{
		blockreg.registerBlock(block, block.getUnlocalizedName());
	    
	}
	
	private static void getItemBlockID(Block block, Class<? extends ItemBlock> itemBlockClass)
    {
		blockreg.registerBlock(block, itemBlockClass, block.getUnlocalizedName());
        
    }
	
	
}
