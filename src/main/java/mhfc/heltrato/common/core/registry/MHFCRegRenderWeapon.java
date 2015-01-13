package mhfc.heltrato.common.core.registry;

import mhfc.heltrato.client.model.weapon.ModelBAdventurer;
import mhfc.heltrato.client.render.weapon.RenderBAdventurer;
import mhfc.heltrato.client.render.weapon.RenderBGLShooterBarrel;
import mhfc.heltrato.client.render.weapon.RenderBGLSpartacusFire;
import mhfc.heltrato.client.render.weapon.RenderBHunter;
import mhfc.heltrato.client.render.weapon.RenderBTigrexArrow;
import mhfc.heltrato.client.render.weapon.RenderGSBone;
import mhfc.heltrato.client.render.weapon.RenderGSDeviljhobroadsword;
import mhfc.heltrato.client.render.weapon.RenderGSKirinThunderSword;
import mhfc.heltrato.client.render.weapon.RenderGSRathalosFiresword;
import mhfc.heltrato.client.render.weapon.RenderGSTigrex;
import mhfc.heltrato.client.render.weapon.RenderHDeviljho;
import mhfc.heltrato.client.render.weapon.RenderHHMetalBagpipe;
import mhfc.heltrato.client.render.weapon.RenderHKirinSpark;
import mhfc.heltrato.client.render.weapon.RenderHTigrex;
import mhfc.heltrato.client.render.weapon.RenderHWar;
import mhfc.heltrato.client.render.weapon.RenderHWarSlammer;
import mhfc.heltrato.client.render.weapon.RenderLSDarkVipern;
import mhfc.heltrato.client.render.weapon.RenderLSIronKatana;
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
		reg.registerItemRenderer(MHFCRegItem.mhfcitemhdeviljho, new RenderHDeviljho());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemgsdeviljhobroadsword, new RenderGSDeviljhobroadsword());
		reg.registerItemRenderer(MHFCRegItem.mhfcitemgsrathalosfiresword, new RenderGSRathalosFiresword());
		
		//new type of rendering
		reg.registerItemRenderer(MHFCRegItem.mhfcitembadventurer, new RenderBAdventurer(new ModelBAdventurer(), MHFCReference.weapon_bow_adventurer_tex));
		
		
		
		
	}
	
}
