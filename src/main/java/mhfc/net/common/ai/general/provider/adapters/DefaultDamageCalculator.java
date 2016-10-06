package mhfc.net.common.ai.general.provider.adapters;

import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.entity.type.EntityMHFCBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * A default implementation for {@link IDamageCalculator}. It decides
 *
 * @author WorldSEnder
 *
 */
public class DefaultDamageCalculator implements IDamageCalculator {
	private float player;
	private float wyvern;
	private float rest;

	public DefaultDamageCalculator(float player, float wyvern, float rest) {
		this.player = player;
		this.wyvern = wyvern;
		this.rest = rest;
	}

	@Override
	public float accept(Entity trgt) {
		if (trgt instanceof EntityPlayer) {
			return player;
		} else if (trgt instanceof EntityMHFCBase) {
			return wyvern;
		}
		return rest;
	}
}
