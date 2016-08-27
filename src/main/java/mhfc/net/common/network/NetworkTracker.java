package mhfc.net.common.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerDisconnectionFromClientEvent;
import mhfc.net.MHFCMain;
import mhfc.net.common.util.services.IPhaseAccess;
import mhfc.net.common.util.services.IPhaseKey;
import mhfc.net.common.util.services.Services;

public class NetworkTracker {
	private static IPhaseAccess<ClientConnectedToServerEvent, ClientDisconnectionFromServerEvent> clientConnectedAccess = Services.instance
			.<ClientConnectedToServerEvent, ClientDisconnectionFromServerEvent>registerPhase("client connected");
	public static final IPhaseKey<ClientConnectedToServerEvent, ClientDisconnectionFromServerEvent> clientConnectedPhase = clientConnectedAccess;

	public static final NetworkTracker instance = new NetworkTracker();

	private NetworkTracker() {}

	@SubscribeEvent
	public void onClientServerConnection(ClientConnectedToServerEvent event) {
		MHFCMain.logger().debug("Client connected to server " + event.manager.getSocketAddress().toString());
		MHFCMain.logger().debug("Entering client connected phase ");
		clientConnectedAccess.enterPhase(event);
	}

	@SubscribeEvent
	public void onClientServerDisconnection(ClientDisconnectionFromServerEvent event) {
		clientConnectedAccess.exitPhase(event);
		MHFCMain.logger().debug("Client disconnected from server " + event.manager.getSocketAddress().toString());
	}

	@SubscribeEvent
	public void onServerClientConnection(ServerConnectionFromClientEvent event) {
		MHFCMain.logger().debug("Server connected to client " + event.manager.getSocketAddress().toString());
	}

	@SubscribeEvent
	public void onServerClientDisconnection(ServerDisconnectionFromClientEvent event) {
		MHFCMain.logger().debug("Server disconnected from client " + event.manager.getSocketAddress().toString());
	}
}
