package mhfc.net.common.core.registry;

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
import net.minecraftforge.client.MinecraftForgeClient;

public class MHFCRegRenderWeapon {
	public static void render() {

		MinecraftForgeClient.registerItemRenderer(MHFCRegItem.mhfcitemgsbone,
				new RenderGSBone());
		MinecraftForgeClient.registerItemRenderer(MHFCRegItem.MHFCItemHTigrex,
				new RenderHTigrex());
		MinecraftForgeClient.registerItemRenderer(MHFCRegItem.MHFCItemGSTigrex,
				new RenderGSTigrex());
		MinecraftForgeClient.registerItemRenderer(
				MHFCRegItem.mhfcitemlsironkatana, new RenderLSIronKatana());
		MinecraftForgeClient.registerItemRenderer(
				MHFCRegItem.mhfcitemgskirinthundersword,
				new RenderGSKirinThunderSword());
		MinecraftForgeClient.registerItemRenderer(
				MHFCRegItem.mhfcitemhkirinspark, new RenderHKirinSpark());
		MinecraftForgeClient.registerItemRenderer(
				MHFCRegItem.mhfcitemhwarhammer, new RenderHWar());
		MinecraftForgeClient.registerItemRenderer(
				MHFCRegItem.mhfcitemhwarhammerplus, new RenderHWar());
		MinecraftForgeClient.registerItemRenderer(
				MHFCRegItem.mhfcitemhwarslammer, new RenderHWarSlammer());
		MinecraftForgeClient.registerItemRenderer(MHFCRegItem.mhfcitembhunter,
				new RenderBHunter());
		MinecraftForgeClient.registerItemRenderer(
				MHFCRegItem.mhfcitemhhmetalbagpipe, new RenderHHMetalBagpipe());
		MinecraftForgeClient.registerItemRenderer(
				MHFCRegItem.mhfcitemlsdarkvipern, new RenderLSDarkVipern());

	}

}
