package mhfc.net.common;

import mhfc.net.MHFCMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.Timer;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class MHFCCommon {
	
	public void regStuff(){}
	public void regTick(){}
	public void regSounds(){}
	public void regTimer(){}
	
	public ModelBiped getArmorModel(int id){
		return null;
	}
	
	public float getPartialTick(){
		return 0.0F;
	}
	
	public boolean isClient(){
		return false;
	}
	
	public void regCapes(){
		
	}
}
