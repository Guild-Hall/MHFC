package mhfc.net.common.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerDisconnectionFromClientEvent;
import mhfc.net.MHFCMain;

public class NetworkTracker {
	@SubscribeEvent
	public void onClientServerConnection(ClientConnectedToServerEvent event) {
		MHFCMain.logger().debug("Client connected to server " + event.manager.getSocketAddress().toString());
	}

	@SubscribeEvent
	public void onClientServerDisconnection(ClientDisconnectionFromServerEvent event) {
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
