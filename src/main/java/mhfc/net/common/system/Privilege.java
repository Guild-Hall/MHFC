package mhfc.net.common.system;

import net.minecraft.entity.player.EntityPlayer;

/**
 * It's a privilege to be a donator
 *
 * @author WorldSEnder
 *
 */
public abstract class Privilege {
	/**
	 * Loads this privilege from wherever.
	 */
	public abstract void load();
	public abstract boolean hasPrivilege(EntityPlayer player);
}
