package mhfc.heltrato.common.core.registry;

import mhfc.heltrato.client.render.weapon.RenderBHunter;
import mhfc.heltrato.client.render.weapon.RenderGSBone;
import mhfc.heltrato.client.render.weapon.RenderGSKirinThunderSword;
import mhfc.heltrato.client.render.weapon.RenderGSTigrex;
import mhfc.heltrato.client.render.weapon.RenderHHMetalBagpipe;
import mhfc.heltrato.client.render.weapon.RenderHKirinSpark;
import mhfc.heltrato.client.render.weapon.RenderHTigrex;
import mhfc.heltrato.client.render.weapon.RenderHWar;
import mhfc.heltrato.client.render.weapon.RenderHWarSlammer;
import mhfc.heltrato.client.render.weapon.RenderLSDarkVipern;
import mhfc.heltrato.client.render.weapon.RenderLSIronKatana;
import net.minecraftforge.client.MinecraftForgeClient;

public class MHFCRegRenderWeapon {
	
	

	private static MinecraftForgeClient reg;
	
	public static void render(){
		
		reg.registerItemRenderer(MHFCRegItem.mhfcitemgsbone, new RenderGSBone());
		reg.registerItemRenderer(MHFCRegItem.MHFCItemHTigrex, new RenderHTigrex());
		reg.registerItemRenderer(MHFCRegItem.MHFCItemGSTigrex, new RenderGSTigrex());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemlsironkatana, new RenderLSIronKatana());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemgskirinthundersword, new RenderGSKirinThunderSword());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhkirinspark, new RenderHKirinSpark());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhwarhammer, new RenderHWar());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhwarhammerplus, new RenderHWar());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhwarslammer, new RenderHWarSlammer());
		reg.registerItemRenderer(MHFCRegItem.mhfcitembhunter, new RenderBHunter());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhhmetalbagpipe, new RenderHHMetalBagpipe());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemlsdarkvipern, new RenderLSDarkVipern());
		
		
		
		
	}
	
}
