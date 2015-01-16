package mhfc.heltrato.common.core.registry;

import mhfc.heltrato.client.model.weapon.bow.ModelBAdventurer;
import mhfc.heltrato.client.render.weapon.bow.RenderBAdventurer;
import mhfc.heltrato.client.render.weapon.bow.RenderBHunter;
import mhfc.heltrato.client.render.weapon.bow.RenderBTigrexArrow;
import mhfc.heltrato.client.render.weapon.bowgun.RenderBGLShooterBarrel;
import mhfc.heltrato.client.render.weapon.bowgun.RenderBGLSpartacusFire;
import mhfc.heltrato.client.render.weapon.greatsword.RenderGSBone;
import mhfc.heltrato.client.render.weapon.greatsword.RenderGSDeviljhobroadsword;
import mhfc.heltrato.client.render.weapon.greatsword.RenderGSKirinThunderSword;
import mhfc.heltrato.client.render.weapon.greatsword.RenderGSRathalosFiresword;
import mhfc.heltrato.client.render.weapon.greatsword.RenderGSTigrex;
import mhfc.heltrato.client.render.weapon.hammer.RenderHDeviljho;
import mhfc.heltrato.client.render.weapon.hammer.RenderHKirinSpark;
import mhfc.heltrato.client.render.weapon.hammer.RenderHRathalos;
import mhfc.heltrato.client.render.weapon.hammer.RenderHTigrex;
import mhfc.heltrato.client.render.weapon.hammer.RenderHWar;
import mhfc.heltrato.client.render.weapon.hammer.RenderHWarSlammer;
import mhfc.heltrato.client.render.weapon.huntinghorn.RenderHHIvoryHorn;
import mhfc.heltrato.client.render.weapon.huntinghorn.RenderHHMetalBagpipe;
import mhfc.heltrato.client.render.weapon.huntinghorn.RenderHHTigrex;
import mhfc.heltrato.client.render.weapon.longsword.RenderLSDarkVipern;
import mhfc.heltrato.client.render.weapon.longsword.RenderLSIronKatana;
import mhfc.heltrato.common.util.lib.MHFCReference;
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
		reg.registerItemRenderer(MHFCRegItem.mhfcitembglshooterbarrel, new RenderBGLShooterBarrel());
		reg.registerItemRenderer(MHFCRegItem.mhfcitembglspartacusfire, new RenderBGLSpartacusFire());
		reg.registerItemRenderer(MHFCRegItem.mhfcitembtigrexarrow, new RenderBTigrexArrow());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhdevilsdue, new RenderHDeviljho());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemgsberserkerssword, new RenderGSDeviljhobroadsword());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemgsrathalosfiresword, new RenderGSRathalosFiresword());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhhivoryhorn, new RenderHHIvoryHorn());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhrathalos, new RenderHRathalos());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhhtigrex, new RenderHHTigrex());
		
		//new type of rendering
		reg.registerItemRenderer(MHFCRegItem.mhfcitembadventurer, new RenderBAdventurer(new ModelBAdventurer(), MHFCReference.weapon_bow_adventurer_tex));
		
		
		
		
	}
	
}
