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

public class MHFCClient extends MHFCCommon {
	private static Timer timer;
	@Override
	public void regStuff() {
		MHFCReg.init();
	}
	@Override
	public void regTick() {}

	@Override
	public void regCapes() {
		MHFCCapes.addDevCapes();
	}

	@Override
	public void regSounds() {
		MinecraftForge.EVENT_BUS.register(new MHFCSound());
	}

	@Override
	public void regTimer() {
		timer = (Timer) ReflectionHelper.getPrivateValue(Minecraft.class,
				Minecraft.getMinecraft(), MHFCMain.fTimer);
	}

	@Override
	public ModelBiped getArmorModel(int id) {
		return null;
	}

	@Override
	@Deprecated
	/**
	 * This uses a really dirty hack and should not be used
	 * Access the partial tick through method-arguments, etc,
	 */
	public float getPartialTick() {
		return timer.renderPartialTicks;
	}

	@Override
	public boolean isClient() {
		return true;
	}
}
