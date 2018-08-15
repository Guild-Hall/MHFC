package mhfc.net.common.world.exploration;

import java.util.concurrent.CompletionStage;

import com.google.gson.JsonElement;

import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;

/**
 * An exploration manager uses a set of active areas that it manages in order to give player access to instances of
 * specific types of areas. It can make decisions about the number of players in an area at once or about the
 * availability in general.
 *
 * @author Katora
 *
 */
public interface IExplorationManager {

	public CompletionStage<IActiveArea> transferPlayerInto(IAreaType type, QuestFlair flair);

	/**
	 * Returns the enclosing instance for the player or null if the player is not in an active area
	 */
	public IActiveArea getActiveAreaOf();

	/**
	 * Handles the first spawn of a player into the map. Not the first spawn in this manager, just the very first spawn
	 * of a player (after joining the server).
	 *
	 */
	public void onPlayerJoined();

	public void onPlayerAdded();

	public void onPlayerRemove();

	/**
	 * Save the state of this exploration manager. If the exploration manager is not meant to be saved, return null
	 * 
	 * @return
	 */
	public JsonElement saveState();

	/**
	 * Respawn the player from the saved data. Passed in data may be null if no data is available
	 * 
	 * @param saveData
	 */
	public void respawn(JsonElement saveData);

}
