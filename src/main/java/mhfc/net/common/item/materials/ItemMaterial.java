package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.AbstractSubTypedItem;
import mhfc.net.common.item.ItemColor;
import mhfc.net.common.item.materials.ItemMaterial.MaterialSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.item.Item;

public class ItemMaterial extends AbstractSubTypedItem<MaterialSubType> {
	public static enum MaterialSubType implements SubTypedItem.SubTypeEnum<Item> {

		/**
		 * In registering the Item Materials, i highly recommend that you register them to the buttom, unless you know
		 * the commulative logic for the Item ids. This is because they are runned with the loot_table.json rewards
		 * which is based on their submeta or sub ids. - Heltrato
		 **/

		ANUMIUM("anumium", ResourceInterface.item_base0_name, ItemColor.BLUE),
		MEGANUM("meganum", ResourceInterface.item_base1_name, ItemColor.GREEN),

		NORMAL("normal", ResourceInterface.item_armorsphere0_name, ItemColor.BLUE),
		PLUS("plus", ResourceInterface.item_armorsphere1_name, ItemColor.LIME),
		//4-16
		DRAGONITE("dragonite", ResourceInterface.item_ingot1_name, ItemColor.GREEN),
		ELTALITE("eltalite", ResourceInterface.item_ingot2_name, ItemColor.RED),
		MACHALITE("machalite", ResourceInterface.item_ingot3_name, ItemColor.CYAN),
		CARBALITE("carbalite", ResourceInterface.item_ingot4_name, ItemColor.PURPLE),
		DISKSTONE("diskstone", ResourceInterface.item_ingot5_name, ItemColor.GRAY),
		EARTHCRYSTAL("earthcrystal", ResourceInterface.item_ingot6_name, ItemColor.WHITE),
		FIRECELLSTONE("firecellstone", ResourceInterface.item_ingot7_name, ItemColor.ORANGE),
		NOVACRYSTAL("novacrystal", ResourceInterface.item_ingot8_name, ItemColor.WHITE),
		RAINBOWORE("rainbowore", ResourceInterface.item_ingot9_name, ItemColor.CANNONPINK),
		UNIONORE("unionore", ResourceInterface.item_ingot10_name, ItemColor.GRAY),
		FURUKURAITO("furukuraito", ResourceInterface.item_ingot11_name, ItemColor.LIME),
		ANTISEPTIC("antiseptic", ResourceInterface.item_ingot12_name, ItemColor.GREEN),
		MELLANJE("mellanje", ResourceInterface.item_ingot13_name, ItemColor.WHITE),
		//17-24
		BLUEMUSHROOM("bluemushroom", ResourceInterface.item_shroom1_name, ItemColor.BLUE),
		NITROSHROOM("nitroshroom", ResourceInterface.item_shroom2_name, ItemColor.RED),
		PARASHROOM("parashroom", ResourceInterface.item_shroom3_name, ItemColor.YELLOW),
		TOADSTOOL("toadstool", ResourceInterface.item_shroom4_name, ItemColor.PINK),
		EXCITESHROOM("exciteshroom", ResourceInterface.item_shroom5_name, ItemColor.PINK),
		DRAGONTOADSTOOL("dragontoadstool", ResourceInterface.item_shroom6_name, ItemColor.RED),
		SPICYMUSHROOM("spicymushroom", ResourceInterface.item_shroom7_name, ItemColor.BROWN),
		CHAOSMUSHROOM("chaosmushroom", ResourceInterface.item_shroom8_name, ItemColor.CANNONPINK),
		//25-42
		SMMONSTERBONE("smallmonsterbone", ResourceInterface.item_bone1_name, ItemColor.GREEN),
		MDMONSTERBONE("medmonsterbone", ResourceInterface.item_bone2_name, ItemColor.GREEN),
		LGMONSTERBONE("largemonsterbone", ResourceInterface.item_bone3_name, ItemColor.GREEN),
		MONSTERBONE("monsterboneplus", ResourceInterface.item_bone4_name, ItemColor.BROWN),
		HRDMONSTERBONE("hardmonsterbone", ResourceInterface.item_bone5_name, ItemColor.RED),
		HVYMONSTERBONE("heavymonsterbone", ResourceInterface.item_bone6_name, ItemColor.GRAY),
		ELDDRAGONBONE("elderdragonbone", ResourceInterface.item_bone7_name, ItemColor.BLUE),
		BGELDDRAGONBONE("bigelderdragonbone", ResourceInterface.item_bone8_name, ItemColor.BLUE),
		MYSTERYBONE("mysterybone", ResourceInterface.item_bone9_name, ItemColor.GREEN),
		UNKNOWNSKULL("unknownskull", ResourceInterface.item_bone10_name, ItemColor.GREEN),
		MASTERSKULL("masterskull", ResourceInterface.item_bone11_name, ItemColor.WHITE),
		BONE("bone", ResourceInterface.item_bone12_name, ItemColor.WHITE),
		ROBUSTWYVERNBONE("robustwyvernbone", ResourceInterface.item_bone13_name, ItemColor.PINK),
		BRUTEBONE("brutebone", ResourceInterface.item_bone14_name, ItemColor.WHITE),
		GIANTBONE("giantbone", ResourceInterface.item_bone15_name, ItemColor.WHITE),
		STOUTBONE("stoutbone", ResourceInterface.item_bone16_name, ItemColor.WHITE),
		MASSIVEBONE("massivebone", ResourceInterface.item_bone17_name, ItemColor.WHITE),
		JUMBOBONE("jumbobone", ResourceInterface.item_bone18_name, ItemColor.WHITE),
		//43 - 63
		INSECTHUSK("insecthusk", ResourceInterface.item_bug0_name, ItemColor.GRAY),
		YAMBUG("yambug", ResourceInterface.item_bug1_name, ItemColor.BLUE),
		BUGHOPPER("bughopper", ResourceInterface.item_bug2_name, ItemColor.WHITE),
		SNAKEBEELARVA("snakebeelarva", ResourceInterface.item_bug3_name, ItemColor.GREEN),
		GODBUG("godbug", ResourceInterface.item_bug4_name, ItemColor.WHITE),
		BITTERBUG("bitterbug", ResourceInterface.item_bug5_name, ItemColor.WHITE),
		FLASHBUG("flashbug", ResourceInterface.item_bug6_name, ItemColor.CYAN),
		THUNDERBUG("thunderbug", ResourceInterface.item_bug7_name, ItemColor.YELLOW),
		CARPENTERBUG("carpenterbug", ResourceInterface.item_bug8_name, ItemColor.YELLOW),
		KINGSCARAB("kingscarab", ResourceInterface.item_bug9_name, ItemColor.PINK),
		EMPERORCRICKET("emperorcricket", ResourceInterface.item_bug10_name, ItemColor.BLUE),
		KILLERBEETLE("killerbeetle", ResourceInterface.item_bug11_name, ItemColor.BROWN),
		HERCUDROME("hercudrome", ResourceInterface.item_bug12_name, ItemColor.GREEN),
		RARESCARAB("rarescarab", ResourceInterface.item_bug13_name, ItemColor.RED),
		PHANTOMBUTTERFLY("phantombutterfly", ResourceInterface.item_bug14_name, ItemColor.WHITE),
		RAINBOWINSECT("rainbowinsect", ResourceInterface.item_bug15_name, ItemColor.CYAN),
		GREATHORNFLY("greathornfly", ResourceInterface.item_bug16_name, ItemColor.YELLOW),
		GREATLADYBUG("greatladybug", ResourceInterface.item_bug17_name, ItemColor.GRAY),
		ROYALRHINO("royalrhino", ResourceInterface.item_bug18_name, ItemColor.GREEN),
		DIVINERHINO("divinerhino", ResourceInterface.item_bug19_name, ItemColor.GREEN),
		GLUEHOPPER("gluehopper", ResourceInterface.item_bug20_name, ItemColor.CYAN),
		//64-76
		SCREAMER("screamer", ResourceInterface.item_sac0_name, ItemColor.GRAY),
		POISON("poison", ResourceInterface.item_sac1_name, ItemColor.MAGNTA),
		TOXIN("toxin", ResourceInterface.item_sac2_name, ItemColor.MAGNTA),
		DEADLYPOISON("deadlypoison", ResourceInterface.item_sac3_name, ItemColor.MAGNTA),
		PARALYSIS("paralysis", ResourceInterface.item_sac4_name, ItemColor.LIME),
		SLEEP("sleep", ResourceInterface.item_sac5_name, ItemColor.CYAN),
		COMA("coma", ResourceInterface.item_sac6_name, ItemColor.CYAN),
		FLAME("flame", ResourceInterface.item_sac7_name, ItemColor.RED),
		INFERNO("inferno", ResourceInterface.item_sac8_name, ItemColor.RED),
		BLAZINGFIRE("blazingfire", ResourceInterface.item_sac9_name, ItemColor.RED),
		THUNDER("electro", ResourceInterface.item_sac10_name, ItemColor.YELLOW),
		ELECTRO("thunder", ResourceInterface.item_sac11_name, ItemColor.YELLOW),
		LIGHTNING("lightning", ResourceInterface.item_sac12_name, ItemColor.YELLOW),

		//77-79
		REMOBRASKIN("remobraskin", ResourceInterface.item_remobra0_name, ItemColor.GRAY), //
		REMOBRASKULL("remobraskull", ResourceInterface.item_remobra1_name, ItemColor.GRAY), //
		REMOBRAWING("remobrawing", ResourceInterface.item_remobra2_name, ItemColor.GRAY),

		//80-85
		DEVILJHOSCALE("deviljhoscale", ResourceInterface.item_deviljho0_name, ItemColor.GREEN),
		DEVILJHOFANG("deviljhofang", ResourceInterface.item_deviljho1_name, ItemColor.GREEN),
		DEVILJHOHIDE("deviljhohide", ResourceInterface.item_deviljho2_name, ItemColor.GREEN),
		DEVILJHOTALON("deviljhotalon", ResourceInterface.item_deviljho3_name, ItemColor.GREEN),
		DEVILJHOSCALP("deviljhoscalp", ResourceInterface.item_deviljho4_name, ItemColor.GREEN),
		DEVILJHOTAIL("deviljhotail", ResourceInterface.item_deviljho5_name, ItemColor.GREEN),

		//86 - 91
		KIRINMANE("kirinmane", ResourceInterface.item_kirin0_name, ItemColor.GRAY),
		KIRINGEM("kiringem", ResourceInterface.item_kirin1_name, ItemColor.WHITE),
		KIRINTHUNDERTAIL("kirinthundertail", ResourceInterface.item_kirin2_name, ItemColor.WHITE),
		KIRINLIGHTCRYSTAL("kirincrystal_light", ResourceInterface.item_kirin3_name, ItemColor.GRAY),
		KIRINPURECRYSTAL("kirincrystal_pure", ResourceInterface.item_kirin4_name, ItemColor.WHITE),
		KIRINPLATINUMMANE("kirinplatinummane", ResourceInterface.item_kirin5_name, ItemColor.WHITE),

		//92 - 96
		RATHALOSSHELL("rathalosshell", ResourceInterface.item_rathalos0_name, ItemColor.RED), //
		RATHALOSWEBBING("rathaloswebbing", ResourceInterface.item_rathalos1_name, ItemColor.RED), //
		RATHALOSMARROW("rathalosmarrow", ResourceInterface.item_rathalos2_name, ItemColor.RED), //
		RATHALOSWING("rathaloswing", ResourceInterface.item_rathalos3_name, ItemColor.RED), //
		RATHALOSPLATE("rathalosplate", ResourceInterface.item_rathalos4_name, ItemColor.RED),

		// Meta data is 97 - 102
		TIGREXSCALE("tigrexscale", ResourceInterface.item_tigrex0_name, ItemColor.YELLOW),
		TIGREXSHELL("tigrexshell", ResourceInterface.item_tigrex1_name, ItemColor.YELLOW),
		TIGREXCLAW("tigrexclaw", ResourceInterface.item_tigrex2_name, ItemColor.YELLOW),
		TIGREXFANG("tigrexfang", ResourceInterface.item_tigrex3_name, ItemColor.YELLOW),
		TIGREXSKULLSHELL("tigrexskullshell", ResourceInterface.item_tigrex4_name, ItemColor.YELLOW),
		TIGREXTAIL("tigrextail", ResourceInterface.item_tigrex5_name, ItemColor.YELLOW),

		// 103 - 106
		DELEXBFIN("delexbfin", ResourceInterface.item_delex0_name, ItemColor.GREEN),
		DELEXQFIN("delexqfin", ResourceInterface.item_delex1_name, ItemColor.GREEN),
		DELEXFANG("delexfang", ResourceInterface.item_delex2_name, ItemColor.GREEN),
		DELEXGUTS("delexguts", ResourceInterface.item_delex3_name, ItemColor.GREEN),

		// 107 - 112
		NARGASCALE("nargascale", ResourceInterface.item_nargacuga0_name, ItemColor.BLACK),
		NARGAPELT("nargapelt", ResourceInterface.item_nargacuga1_name, ItemColor.BLACK),
		NARGAFANG("nargafang", ResourceInterface.item_nargacuga2_name, ItemColor.BLACK),
		NARGATAIL("nargatail", ResourceInterface.item_nargacuga3_name, ItemColor.BLACK),
		NARGAWING("nargawing", ResourceInterface.item_nargacuga4_name, ItemColor.BLACK),
		NARGASTEM("nargastem", ResourceInterface.item_nargacuga5_name, ItemColor.BLACK),

		//113 - 120
		BARROTHSHELL("barrothshell", ResourceInterface.item_barroth0_name, ItemColor.BROWN),
		BARROTHRIDGE("barrothridge", ResourceInterface.item_barroth1_name, ItemColor.BROWN),
		BARROTHCLAW("barrothclaw", ResourceInterface.item_barroth2_name, ItemColor.BROWN),
		BARROTHTAIL("barrothtail", ResourceInterface.item_barroth3_name, ItemColor.BROWN),
		BARROTHSCALP("barrothscalp", ResourceInterface.item_barroth4_name, ItemColor.BROWN),
		BARROTHCARAPACE("barrothcarapace", ResourceInterface.item_barroth5_name, ItemColor.BROWN),
		BARROTHSTONE("barrothstone", ResourceInterface.item_barroth6_name, ItemColor.BROWN),
		BARROTHMUD("barrothmud", ResourceInterface.item_barroth7_name, ItemColor.BROWN);

		public final String registryName;
		public final String name;
		public final ItemColor color;

		private MaterialSubType(String registryName, String name, ItemColor color) {
			this.registryName = registryName;
			this.name = name;
			this.color = color;
		}

		@Override
		public String getName() {
			return this.registryName;
		}

		@Override
		public String getUnlocalizedName() {
			return this.name;
		}

		@Override
		public Item getBaseItem() {
			return MHFCItemRegistry.getRegistry().material;
		}
		
		@Override
		public ItemColor getColor() {
			return this.color;
		}
	}

	public ItemMaterial() {
		super(MaterialSubType.class);
		setTranslationKey(ResourceInterface.item_material_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(40);
		setHasSubtypes(true);
	}
}
