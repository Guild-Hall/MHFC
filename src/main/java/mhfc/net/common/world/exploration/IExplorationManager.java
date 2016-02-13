package mhfc.net.common.world.exploration;

import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * An exploration manager uses a set of active areas that it manages in order to give player access to instances of
 * specific types of areas. It can make decisions about the number of players in an area at once or about the
 * availability in general.
 * 
 * @author Katora
 *
 */
public interface IExplorationManager {

	public void transferPlayerInto(EntityPlayerMP player, IAreaType type);

	/**
	 * Returns the enclosing instance for the player or null if the player is not managed by this exploration manager.
	 */
	public IActiveArea getActiveAreaFor(EntityPlayerMP player);

}
