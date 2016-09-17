package mhfc.net.client.core.registry;

import mhfc.net.common.core.registry.MHFCItemRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class MHFCWeaponRenderRegistry {
	public static void init() {

		MHFCItemRegistry itemRegistry = MHFCItemRegistry.getRegistry();

		registerItemRenderer(itemRegistry.weapon_gs_bone);
		registerItemRenderer(itemRegistry.weapon_gs_tigrex);
		registerItemRenderer(itemRegistry.weapon_gs_kirinthunders);
		registerItemRenderer(itemRegistry.weapon_gs_berserkers);
		registerItemRenderer(itemRegistry.weapon_gs_rathalosfire);
		registerItemRenderer(itemRegistry.weapon_gs_deadlyserpentblade);

		registerItemRenderer(itemRegistry.weapon_hm_tigrex);
		registerItemRenderer(itemRegistry.weapon_hm_kirinspark);
		registerItemRenderer(itemRegistry.weapon_hm_warhammer);
		registerItemRenderer(itemRegistry.weapon_hm_warhammerplus);
		registerItemRenderer(itemRegistry.weapon_hm_warslammer);
		registerItemRenderer(itemRegistry.weapon_hm_devilsdue);
		registerItemRenderer(itemRegistry.weapon_hm_rathalos);

		registerItemRenderer(itemRegistry.weapon_ls_ironkatana);
		registerItemRenderer(itemRegistry.weapon_ls_darkvipern);
		registerItemRenderer(itemRegistry.weapon_ls_ironkatanagrace);
		registerItemRenderer(itemRegistry.weapon_ls_ironkatanagospel);
		registerItemRenderer(itemRegistry.weapon_ls_eagercleaver);
		registerItemRenderer(itemRegistry.weapon_ls_devilslicer);
		registerItemRenderer(itemRegistry.weapon_ls_truedevilslicer);
		registerItemRenderer(itemRegistry.weapon_ls_saber);
		registerItemRenderer(itemRegistry.weapon_ls_liondancesaber);
		registerItemRenderer(itemRegistry.weapon_ls_lionkingsaber);
		registerItemRenderer(itemRegistry.weapon_ls_lionkaisersaber);
		registerItemRenderer(itemRegistry.weapon_ls_lionsroarsaber);
		registerItemRenderer(itemRegistry.weapon_ls_miragefinsword);
		registerItemRenderer(itemRegistry.weapon_ls_miragefinswordplus);
		registerItemRenderer(itemRegistry.weapon_ls_phantommirage);

		registerItemRenderer(itemRegistry.weapon_hh_metalbagpipe);
		registerItemRenderer(itemRegistry.weapon_hh_ivoryhorn);
		registerItemRenderer(itemRegistry.weapon_hh_tigrex);
		registerItemRenderer(itemRegistry.weapon_hh_greatbagpipe);
		registerItemRenderer(itemRegistry.weapon_hh_heavybagpipe);
		registerItemRenderer(itemRegistry.weapon_hh_heavybagpipeplus);
		registerItemRenderer(itemRegistry.weapon_hh_elitebagpipe);
		registerItemRenderer(itemRegistry.weapon_hh_wardrums);
		registerItemRenderer(itemRegistry.weapon_hh_wardrumsplus);
		registerItemRenderer(itemRegistry.weapon_hh_mogwarddrums);
		registerItemRenderer(itemRegistry.weapon_hh_darkthorntrumpet);
		registerItemRenderer(itemRegistry.weapon_hh_blackcasket);

		registerItemRenderer(itemRegistry.weapon_b_hunters);

		registerItemRenderer(itemRegistry.weapon_b_huntersstout);
		registerItemRenderer(itemRegistry.weapon_b_huntersproud);

		registerItemRenderer(itemRegistry.weapon_bgl_barrel);

		registerItemRenderer(itemRegistry.weapon_bgh_rath);
	}

	private static void registerItemRenderer(Item item) {
		String itemName = item.getRegistryName().getResourcePath();
		ModelResourceLocation customLocation = new ModelResourceLocation(
				"mhfc:models/item/" + itemName + ".mcmdl#inventory");
		ModelLoader.setCustomModelResourceLocation(item, 0, customLocation);
	}

}
