package mhfc.net.client;

import mhfc.net.MHFCMain;
import mhfc.net.common.MHFCCommon;
import mhfc.net.common.core.MHFCReg;
import mhfc.net.common.sound.MHFCSound;
import mhfc.net.common.util.MHFCCapes;
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
		MHFCCapes.addDevCapes();
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

	@Deprecated
	/**
	 * This uses a really dirty hack and should not be used
	 * Access the partial tick through method-arguments, etc,
	 */
	public float getPartialTick(){
		return timer.renderPartialTicks;
	}

	public boolean isClient(){
		return true;
	}
}
