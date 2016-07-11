package mhfc.net.client.core.registry;

import mhfc.net.client.render.weapon.bow.RenderBHunterStout;
import mhfc.net.client.render.weapon.bow.RenderBHunters;
import mhfc.net.client.render.weapon.bow.RenderBHuntersProud;
import mhfc.net.client.render.weapon.bowgun.RenderBHRath;
import mhfc.net.client.render.weapon.bowgun.RenderBLBarrel;
import mhfc.net.client.render.weapon.greatsword.RenderGSBone;
import mhfc.net.client.render.weapon.greatsword.RenderGSDeadlySerpentblade;
import mhfc.net.client.render.weapon.greatsword.RenderGSDeviljhobroadsword;
import mhfc.net.client.render.weapon.greatsword.RenderGSKirinThunderSword;
import mhfc.net.client.render.weapon.greatsword.RenderGSRathalosFiresword;
import mhfc.net.client.render.weapon.greatsword.RenderGSTigrex;
import mhfc.net.client.render.weapon.hammer.RenderHDeviljho;
import mhfc.net.client.render.weapon.hammer.RenderHKirinSpark;
import mhfc.net.client.render.weapon.hammer.RenderHRathalos;
import mhfc.net.client.render.weapon.hammer.RenderHTigrex;
import mhfc.net.client.render.weapon.hammer.RenderHWar;
import mhfc.net.client.render.weapon.hammer.RenderHWarSlammer;
import mhfc.net.client.render.weapon.huntinghorn.RenderHHBlackCasket;
import mhfc.net.client.render.weapon.huntinghorn.RenderHHDarkThornTrumpet;
import mhfc.net.client.render.weapon.huntinghorn.RenderHHEliteBagpipe;
import mhfc.net.client.render.weapon.huntinghorn.RenderHHGreatBagpipe;
import mhfc.net.client.render.weapon.huntinghorn.RenderHHHeavyBagpipe;
import mhfc.net.client.render.weapon.huntinghorn.RenderHHIvoryHorn;
import mhfc.net.client.render.weapon.huntinghorn.RenderHHMetalBagpipe;
import mhfc.net.client.render.weapon.huntinghorn.RenderHHMogwardDrums;
import mhfc.net.client.render.weapon.huntinghorn.RenderHHTigrex;
import mhfc.net.client.render.weapon.huntinghorn.RenderHHWarDrums;
import mhfc.net.client.render.weapon.longsword.RenderLSDarkVipern;
import mhfc.net.client.render.weapon.longsword.RenderLSDevilSlicer;
import mhfc.net.client.render.weapon.longsword.RenderLSEagerCleaver;
import mhfc.net.client.render.weapon.longsword.RenderLSIKGospel;
import mhfc.net.client.render.weapon.longsword.RenderLSIKGrace;
import mhfc.net.client.render.weapon.longsword.RenderLSIronKatana;
import mhfc.net.client.render.weapon.longsword.RenderLSLionDanceSaber;
import mhfc.net.client.render.weapon.longsword.RenderLSLionKaiserSaber;
import mhfc.net.client.render.weapon.longsword.RenderLSLionKingSaber;
import mhfc.net.client.render.weapon.longsword.RenderLSLionsRoarSaber;
import mhfc.net.client.render.weapon.longsword.RenderLSMirageFinsword;
import mhfc.net.client.render.weapon.longsword.RenderLSMirageFinswordplus;
import mhfc.net.client.render.weapon.longsword.RenderLSPhantomMirage;
import mhfc.net.client.render.weapon.longsword.RenderLSSaber;
import mhfc.net.client.render.weapon.longsword.RenderLSTrueDevilSlicer;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class MHFCWeaponRenderRegistry {
	public static void init() {

		MHFCItemRegistry itemRegistry = MHFCItemRegistry.getRegistry();

		registerItemRenderer(itemRegistry.weapon_gs_bone, new RenderGSBone());
		registerItemRenderer(itemRegistry.weapon_gs_tigrex, new RenderGSTigrex());
		registerItemRenderer(itemRegistry.weapon_gs_kirinthunders, new RenderGSKirinThunderSword());
		registerItemRenderer(itemRegistry.weapon_gs_berserkers, new RenderGSDeviljhobroadsword());
		registerItemRenderer(itemRegistry.weapon_gs_rathalosfire, new RenderGSRathalosFiresword());
		registerItemRenderer(itemRegistry.weapon_gs_deadlyserpentblade, new RenderGSDeadlySerpentblade());

		registerItemRenderer(itemRegistry.weapon_hm_tigrex, new RenderHTigrex());
		registerItemRenderer(itemRegistry.weapon_hm_kirinspark, new RenderHKirinSpark());
		registerItemRenderer(itemRegistry.weapon_hm_warhammer, new RenderHWar());
		registerItemRenderer(itemRegistry.weapon_hm_warhammerplus, new RenderHWar());
		registerItemRenderer(itemRegistry.weapon_hm_warslammer, new RenderHWarSlammer());
		registerItemRenderer(itemRegistry.weapon_hm_devilsdue, new RenderHDeviljho());
		registerItemRenderer(itemRegistry.weapon_hm_rathalos, new RenderHRathalos());

		registerItemRenderer(itemRegistry.weapon_ls_ironkatana, new RenderLSIronKatana());
		registerItemRenderer(itemRegistry.weapon_ls_darkvipern, new RenderLSDarkVipern());
		registerItemRenderer(itemRegistry.weapon_ls_ironkatanagrace, new RenderLSIKGrace());
		registerItemRenderer(itemRegistry.weapon_ls_ironkatanagospel, new RenderLSIKGospel());
		registerItemRenderer(itemRegistry.weapon_ls_eagercleaver, new RenderLSEagerCleaver());
		registerItemRenderer(itemRegistry.weapon_ls_devilslicer, new RenderLSDevilSlicer());
		registerItemRenderer(itemRegistry.weapon_ls_truedevilslicer, new RenderLSTrueDevilSlicer());
		registerItemRenderer(itemRegistry.weapon_ls_saber, new RenderLSSaber());
		registerItemRenderer(itemRegistry.weapon_ls_liondancesaber, new RenderLSLionDanceSaber());
		registerItemRenderer(itemRegistry.weapon_ls_lionkingsaber, new RenderLSLionKingSaber());
		registerItemRenderer(itemRegistry.weapon_ls_lionkaisersaber, new RenderLSLionKaiserSaber());
		registerItemRenderer(itemRegistry.weapon_ls_lionsroarsaber, new RenderLSLionsRoarSaber());
		registerItemRenderer(itemRegistry.weapon_ls_miragefinsword, new RenderLSMirageFinsword());
		registerItemRenderer(itemRegistry.weapon_ls_miragefinswordplus, new RenderLSMirageFinswordplus());
		registerItemRenderer(itemRegistry.weapon_ls_phantommirage, new RenderLSPhantomMirage());

		registerItemRenderer(itemRegistry.weapon_hh_metalbagpipe, new RenderHHMetalBagpipe());
		registerItemRenderer(itemRegistry.weapon_hh_ivoryhorn, new RenderHHIvoryHorn());
		registerItemRenderer(itemRegistry.weapon_hh_tigrex, new RenderHHTigrex());
		registerItemRenderer(itemRegistry.weapon_hh_greatbagpipe, new RenderHHGreatBagpipe());
		registerItemRenderer(itemRegistry.weapon_hh_heavybagpipe, new RenderHHHeavyBagpipe());
		registerItemRenderer(itemRegistry.weapon_hh_heavybagpipeplus, new RenderHHHeavyBagpipe());
		registerItemRenderer(itemRegistry.weapon_hh_elitebagpipe, new RenderHHEliteBagpipe());
		registerItemRenderer(itemRegistry.weapon_hh_wardrums, new RenderHHWarDrums());
		registerItemRenderer(itemRegistry.weapon_hh_wardrumsplus, new RenderHHWarDrums());
		registerItemRenderer(itemRegistry.weapon_hh_mogwarddrums, new RenderHHMogwardDrums());
		registerItemRenderer(itemRegistry.weapon_hh_darkthorntrumpet, new RenderHHDarkThornTrumpet());
		registerItemRenderer(itemRegistry.weapon_hh_blackcasket, new RenderHHBlackCasket());

		registerItemRenderer(itemRegistry.weapon_b_hunters, new RenderBHunters());

		registerItemRenderer(itemRegistry.weapon_b_huntersstout, new RenderBHunterStout());
		registerItemRenderer(itemRegistry.weapon_b_huntersproud, new RenderBHuntersProud());

		registerItemRenderer(itemRegistry.weapon_bgl_barrel, new RenderBLBarrel());

		registerItemRenderer(itemRegistry.weapon_bgh_rath, new RenderBHRath());
	}

	private static void registerItemRenderer(Item item, IItemRenderer renderer) {
		MinecraftForgeClient.registerItemRenderer(item, renderer);
	}

}
