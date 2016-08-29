package mhfc.net.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerMap<T> extends HashMap<UUID, T> {
	/**
	 *
	 */
	private static final long serialVersionUID = 7089554763707019448L;

	public static UUID playerToKey(EntityPlayer player) {
		if (player == null) {
			return null;
		}
		return profileToKey(player.getGameProfile());
	}

	public static UUID profileToKey(GameProfile profile) {
		if (profile == null) {
			return null;
		}
		return profile.getId();
	}

	public PlayerMap() {
		super();
	}

	public boolean containsPlayer(EntityPlayer key) {
		return containsKey(playerToKey(key));
	}

	public boolean containsProfile(GameProfile key) {
		return containsKey(profileToKey(key));
	}

	public T getPlayer(EntityPlayer key) {
		return get(playerToKey(key));
	}

	public T getProfile(GameProfile key) {
		return get(profileToKey(key));
	}

	public T putPlayer(EntityPlayer key, T value) {
		return put(playerToKey(key), value);
	}

	public T putProfile(GameProfile key, T value) {
		return put(profileToKey(key), value);
	}

	public T removePlayer(EntityPlayer key) {
		return remove(playerToKey(key));
	}

	public T removeProfile(GameProfile key) {
		return remove(profileToKey(key));
	}

	public void putAllPlayers(Map<? extends EntityPlayer, ? extends T> m) {
		for (Map.Entry<? extends EntityPlayer, ? extends T> e : m.entrySet()) {
			putPlayer(e.getKey(), e.getValue());
		}
	}
}
