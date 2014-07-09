package mhfc.net.common.eventhandler;

import mhfc.net.client.container.ContainerHunterBench;
import mhfc.net.client.gui.GuiHunterBench;
import mhfc.net.common.core.registry.MHFCRegBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class MHFCGuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		if(ID == 1)return new ContainerHunterBench(player.inventory, world, x, y, z);
		return null;
		
		}

	

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		if(ID == 1) return new GuiHunterBench(player.inventory, world, z, z, z);
		return null;
				
		}


}
