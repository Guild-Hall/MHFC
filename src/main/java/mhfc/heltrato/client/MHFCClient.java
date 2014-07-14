package mhfc.heltrato.client;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.MHFCCommon;
import mhfc.heltrato.common.core.MHFCReg;
import mhfc.heltrato.common.sound.MHFCSound;
import mhfc.heltrato.common.util.Capes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.Timer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class MHFCClient extends MHFCCommon
{
	
	private MHFCReg reg;
	private MinecraftForge forge;
	private static Timer timer;
	public void regStuff(){
		reg.init();
	}
	public void regTick(){}
	
	public void regCapes(){
		Capes.addDevCapes();
	}
	
	public void regSounds(){
		forge.EVENT_BUS.register(new MHFCSound());
	}
	
	public void regTimer(){
		timer = (Timer)ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), MHFCMain.fTimer);
	}
	
	public ModelBiped getArmorModel(int id){
		return null;
	}
	
	public float getPartialTick(){
		return timer.renderPartialTicks;
	}
	
	public boolean isClient(){
		return true;
	}
	
}
