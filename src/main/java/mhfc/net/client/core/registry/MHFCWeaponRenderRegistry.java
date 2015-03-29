package mhfc.net.client.core.registry;

import mhfc.net.client.render.weapon.bowgun.RenderBHRath;
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
import mhfc.net.client.render.weapon.longsword.RenderLSIronKatana;
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

		registerItemRenderer(MHFCItemRegistry.weapon_hh_metalbagpipe,
				new RenderHHMetalBagpipe());
		registerItemRenderer(MHFCItemRegistry.weapon_hh_ivoryhorn,
				new RenderHHIvoryHorn());
		registerItemRenderer(MHFCItemRegistry.weapon_hh_tigrex,
				new RenderHHTigrex());

		// registerItemRenderer(MHFCItemRegistry.weapon_bow_hunter,
		// new RenderBHunter());

		registerItemRenderer(MHFCItemRegistry.weapon_bgh_rath,
				new RenderBHRath());
	}
	private static void registerItemRenderer(Item item, IItemRenderer renderer) {
		MinecraftForgeClient.registerItemRenderer(item, renderer);
	}

}
