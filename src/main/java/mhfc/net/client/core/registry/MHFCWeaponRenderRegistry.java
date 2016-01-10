package mhfc.net.client.core.registry;

import mhfc.net.client.render.weapon.bow.RenderBHunterStout;
import mhfc.net.client.render.weapon.bow.RenderBHunters;
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
import mhfc.net.client.render.weapon.huntinghorn.RenderHHIvoryHorn;
import mhfc.net.client.render.weapon.huntinghorn.RenderHHMetalBagpipe;
import mhfc.net.client.render.weapon.huntinghorn.RenderHHTigrex;
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

		registerItemRenderer(MHFCItemRegistry.weapon_gs_bone,
				new RenderGSBone());
		registerItemRenderer(MHFCItemRegistry.weapon_gs_tigrex,
				new RenderGSTigrex());
		registerItemRenderer(MHFCItemRegistry.weapon_gs_kirinthunders,
				new RenderGSKirinThunderSword());
		registerItemRenderer(MHFCItemRegistry.weapon_gs_berserkers,
				new RenderGSDeviljhobroadsword());
		registerItemRenderer(MHFCItemRegistry.weapon_gs_rathalosfire,
				new RenderGSRathalosFiresword());
		registerItemRenderer(MHFCItemRegistry.weapon_gs_deadlyserpentblade,
				new RenderGSDeadlySerpentblade());

		registerItemRenderer(MHFCItemRegistry.weapon_hm_tigrex,
				new RenderHTigrex());
		registerItemRenderer(MHFCItemRegistry.weapon_hm_kirinspark,
				new RenderHKirinSpark());
		registerItemRenderer(MHFCItemRegistry.weapon_hm_warhammer,
				new RenderHWar());
		registerItemRenderer(MHFCItemRegistry.weapon_hm_warhammerplus,
				new RenderHWar());
		registerItemRenderer(MHFCItemRegistry.weapon_hm_warslammer,
				new RenderHWarSlammer());
		registerItemRenderer(MHFCItemRegistry.weapon_hm_devilsdue,
				new RenderHDeviljho());
		registerItemRenderer(MHFCItemRegistry.weapon_hm_rathalos,
				new RenderHRathalos());

		registerItemRenderer(MHFCItemRegistry.weapon_ls_ironkatana,
				new RenderLSIronKatana());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_darkvipern,
				new RenderLSDarkVipern());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_ironkatanagrace,
				new RenderLSIKGrace());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_ironkatanagospel,
				new RenderLSIKGospel());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_eagercleaver,
				new RenderLSEagerCleaver());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_devilslicer,
				new RenderLSDevilSlicer());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_truedevilslicer,
				new RenderLSTrueDevilSlicer());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_saber, 
				new RenderLSSaber());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_liondancesaber, 
				new RenderLSLionDanceSaber());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_lionkingsaber, 
				new RenderLSLionKingSaber());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_lionkaisersaber, 
				new RenderLSLionKaiserSaber());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_lionsroarsaber, 
				new RenderLSLionsRoarSaber());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_miragefinsword, 
				new RenderLSMirageFinsword());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_miragefinswordplus, 
				new RenderLSMirageFinswordplus());
		registerItemRenderer(MHFCItemRegistry.weapon_ls_phantommirage, 
				new RenderLSPhantomMirage());

		registerItemRenderer(MHFCItemRegistry.weapon_hh_metalbagpipe,
				new RenderHHMetalBagpipe());
		registerItemRenderer(MHFCItemRegistry.weapon_hh_ivoryhorn,
				new RenderHHIvoryHorn());
		registerItemRenderer(MHFCItemRegistry.weapon_hh_tigrex,
				new RenderHHTigrex());

		registerItemRenderer(MHFCItemRegistry.weapon_bow_hunters,
				new RenderBHunters());
		
		registerItemRenderer(MHFCItemRegistry.weapon_bow_huntersstout,
				new RenderBHunterStout());

		registerItemRenderer(MHFCItemRegistry.weapon_bgl_barrel,
				new RenderBLBarrel());
		
		registerItemRenderer(MHFCItemRegistry.weapon_bgh_rath,
				new RenderBHRath());
	}
	private static void registerItemRenderer(Item item, IItemRenderer renderer) {
		MinecraftForgeClient.registerItemRenderer(item, renderer);
	}

}
