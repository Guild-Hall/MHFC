package mhfc.net.common.eventhandler.player;

import mhfc.net.common.system.UpdateSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

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
			public ITextComponent getDisplayName() {
				return new TextComponentString(getCommandSenderName());
			}

			@Override
			public boolean canCommandSenderUseCommand(int commandLevel, String command) {
				throw new UnsupportedOperationException("No player at this point");
			}

			@Override
			public void addChatMessage(ITextComponent chat) {
				cctse.getHandler().handleChat(new SPacketChat(chat));
			}
		});
	}
}
