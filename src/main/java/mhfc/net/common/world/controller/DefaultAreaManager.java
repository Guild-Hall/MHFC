package mhfc.net.common.world.controller;

import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.world.World;

public class DefaultAreaManager extends AreaManagerAdapter {
	private World world;
	private WorldProxies.WorldProxy proxied;

	public DefaultAreaManager(World world) {
		this.world = Objects.requireNonNull(world);
		this.proxied = new WorldProxies.WorldProxy(world);
	}

	public World getWorld() {
		return this.world;
	}

	@Override
	protected IWorldProxy getProxiedWorld() {
		return this.proxied;
	}

	@Override
	protected Pair<Integer, Integer> getEmptyPlaceFor(int sizeX, int sizeZ) {
		// TODO: implement a basic version of a 2D-spot mananger
		return null;
	}

}
