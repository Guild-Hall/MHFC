package mhfc.net.client.core.registry;

import mhfc.net.client.render.weapon.RenderBHunter;
import mhfc.net.client.render.weapon.RenderGSBone;
import mhfc.net.client.render.weapon.RenderGSKirinThunderSword;
import mhfc.net.client.render.weapon.RenderGSTigrex;
import mhfc.net.client.render.weapon.RenderHHMetalBagpipe;
import mhfc.net.client.render.weapon.RenderHKirinSpark;
import mhfc.net.client.render.weapon.RenderHTigrex;
import mhfc.net.client.render.weapon.RenderHWar;
import mhfc.net.client.render.weapon.RenderHWarSlammer;
import mhfc.net.client.render.weapon.RenderLSDarkVipern;
import mhfc.net.client.render.weapon.RenderLSIronKatana;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import net.minecraftforge.client.MinecraftForgeClient;

public class MHFCWeaponRenderRegistry {
	public static void init() {

		MinecraftForgeClient.registerItemRenderer(MHFCItemRegistry.mhfcitemgsbone,
				new RenderGSBone());
		MinecraftForgeClient.registerItemRenderer(MHFCItemRegistry.MHFCItemHTigrex,
				new RenderHTigrex());
		MinecraftForgeClient.registerItemRenderer(MHFCItemRegistry.MHFCItemGSTigrex,
				new RenderGSTigrex());
		MinecraftForgeClient.registerItemRenderer(
				MHFCItemRegistry.mhfcitemlsironkatana, new RenderLSIronKatana());
		MinecraftForgeClient.registerItemRenderer(
				MHFCItemRegistry.mhfcitemgskirinthundersword,
				new RenderGSKirinThunderSword());
		MinecraftForgeClient.registerItemRenderer(
				MHFCItemRegistry.mhfcitemhkirinspark, new RenderHKirinSpark());
		MinecraftForgeClient.registerItemRenderer(
				MHFCItemRegistry.mhfcitemhwarhammer, new RenderHWar());
		MinecraftForgeClient.registerItemRenderer(
				MHFCItemRegistry.mhfcitemhwarhammerplus, new RenderHWar());
		MinecraftForgeClient.registerItemRenderer(
				MHFCItemRegistry.mhfcitemhwarslammer, new RenderHWarSlammer());
		MinecraftForgeClient.registerItemRenderer(MHFCItemRegistry.mhfcitembhunter,
				new RenderBHunter());
		MinecraftForgeClient.registerItemRenderer(
				MHFCItemRegistry.mhfcitemhhmetalbagpipe, new RenderHHMetalBagpipe());
		MinecraftForgeClient.registerItemRenderer(
				MHFCItemRegistry.mhfcitemlsdarkvipern, new RenderLSDarkVipern());

	}

}
