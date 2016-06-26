package mhfc.net.common.world.exploration;

import java.util.function.Consumer;

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

	public void transferPlayerInto(EntityPlayerMP player, IAreaType type, Consumer<IActiveArea> callback);

	/**
	 * Returns the enclosing instance for the player or null if the player is not managed by this exploration manager.
	 */
	public IActiveArea getActiveAreaOf(EntityPlayerMP player);

	/**
	 * Respawn the player if it is managed by this exploration manager. If it is not, throws
	 * {@link IllegalArgumentException}
	 * 
	 * @throws IllegalArgumentException
	 */
	public void respawn(EntityPlayerMP player) throws IllegalArgumentException;

	/**
	 * Handles the first spawn of a player into the map. Not the first spawn in this manager, just the very first spawn
	 * of a player.
	 * 
	 */
	public void initialAddPlayer(EntityPlayerMP player) throws IllegalArgumentException;

	public void onPlayerRemove(EntityPlayerMP player);

	public void onPlayerAdded(EntityPlayerMP player);

}
