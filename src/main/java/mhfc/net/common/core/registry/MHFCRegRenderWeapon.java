package mhfc.net.common.core.registry;

import mhfc.net.client.render.weapon.bowgun.RenderBHRath;
import mhfc.net.client.render.weapon.greatsword.RenderGSBone;
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
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhhmetalbagpipe, new RenderHHMetalBagpipe());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemlsdarkvipern, new RenderLSDarkVipern());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhdevilsdue, new RenderHDeviljho());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemgsberserkerssword, new RenderGSDeviljhobroadsword());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemgsrathalosfiresword, new RenderGSRathalosFiresword());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhhivoryhorn, new RenderHHIvoryHorn());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhrathalos, new RenderHRathalos());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhhtigrex, new RenderHHTigrex());
		reg.registerItemRenderer(MHFCRegItem.mhfcitembghrath, new RenderBHRath());
		
		//new type of rendering
		
		
		
		
	}
	
}
