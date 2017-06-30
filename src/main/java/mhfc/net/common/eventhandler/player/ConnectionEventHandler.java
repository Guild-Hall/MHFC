package mhfc.net.common.eventhandler.player;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.system.UpdateSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandResultStats.Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

@Mod.EventBusSubscriber(modid = ResourceInterface.main_modid)
public class ConnectionEventHandler {

	@SubscribeEvent
	public static void onEvent(final ClientConnectedToServerEvent cctse) {
		UpdateSystem.sendUpdateAsync(new ICommandSender() {

			@Override
			public MinecraftServer getServer() {
				return FMLCommonHandler.instance().getMinecraftServerInstance();
			}

			@Override
			public BlockPos getPosition() {
				throw new UnsupportedOperationException("No position known");
			}

			@Override
			public Vec3d getPositionVector() {
				return new Vec3d(getPosition());
			}

			@Override
			public World getEntityWorld() {
				throw new UnsupportedOperationException("No world at this point");
			}

			@Override
			public String getName() {
				return Minecraft.getMinecraft().getSession().getUsername();
			}

			@Override
			public ITextComponent getDisplayName() {
				return new TextComponentString(getName());
			}

			@Override
			public Entity getCommandSenderEntity() {
				throw new UnsupportedOperationException("No player at this point");
			}

			@Override
			public boolean canUseCommand(int commandLevel, String command) {
				throw new UnsupportedOperationException("No player at this point");
			}

			@Override
			public boolean sendCommandFeedback() {
				return false;
			}

			@Override
			public void setCommandStat(Type type, int amount) {
				// Ignore
			}

			@Override
			public void sendMessage(ITextComponent chat) {
				cctse.getHandler().handleChat(new SPacketChat(chat));
			}
		});
	}
}
