package mhfc.net.common.eventhandler.player;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import mhfc.net.common.system.UpdateSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class ConnectionEventHandler {

	public static final ConnectionEventHandler instance = new ConnectionEventHandler();

	@SubscribeEvent
	public void onEvent(final ClientConnectedToServerEvent cctse) {
		UpdateSystem.sendUpdateAsync(new ICommandSender() {

			@Override
			public ChunkCoordinates getPlayerCoordinates() {
				throw new UnsupportedOperationException("No player at this point");
			}

			@Override
			public World getEntityWorld() {
				throw new UnsupportedOperationException("No world at this point");
			}

			@Override
			public String getCommandSenderName() {
				return Minecraft.getMinecraft().getSession().getUsername();
			}

			@Override
			public IChatComponent func_145748_c_() {
				return new ChatComponentText(getCommandSenderName());
			}

			@Override
			public boolean canCommandSenderUseCommand(int a, String b) {
				throw new UnsupportedOperationException("No player at this point");
			}

			@Override
			public void addChatMessage(IChatComponent chat) {
				cctse.handler.handleChat(new S02PacketChat(chat));
			}
		});
	}
}
