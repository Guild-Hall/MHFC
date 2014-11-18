package mhfc.net.common.quests.world;

import net.minecraft.entity.Entity;

public interface IQuestAreaSpawnController {

	/**
	 * Set whether default spawn are enabled or whether all spawns are
	 * controlled by the quest
	 * 
	 * @param defaultSpawnsEnabled
	 *            If false no monsters will spawn from default
	 */
	public void setDefaultSpawns(boolean defaultSpawnsEnabled);

	/**
	 * Same as {@link #setDefaultSpawns(boolean)} except it is only set for one
	 * specific type of monster. Whatever method is called later will overwrite
	 * all others before. Calling on a super type should set for all children of
	 * the type.
	 * 
	 * @param entityID
	 *            The monster to set the spawn for.
	 * @param defaultSpawnEnabled
	 */
	public void setDefaultSpawnFor(String entityID, boolean defaultSpawnEnabled);

	/**
	 * Spawn one monster from an entityID. For example Creeper for a creeper.
	 * The position is determined by the controller.
	 * 
	 * @param entityID
	 */
	public void spawnEntity(String entityID);

	/**
	 * Spawn an entity from the given ID at the given position only if it is
	 * inside the area.
	 * 
	 * @param entityID
	 */
	public void spawnEntity(String entityID, int x, int y, int z);

	/**
	 * Directly spawn an entity, position controlled by the controller.
	 * 
	 * @param entity
	 */
	public void spawnEntity(Entity entity);

	/**
	 * Directly spawn an entity at the position if the position is inside the
	 * controlled area.
	 * 
	 * @param entity
	 */
	public void spawnEntity(Entity entity, int x, int y, int z);

	/**
	 * Advises the controller to spawn entities of the given entity id until the
	 * max amount is reached
	 * 
	 * @param entityID
	 * @param maxAmount
	 */
	public void spawnEntityUpTo(String entityID, int maxAmount);

	/**
	 * Remove all MHFC or hostile monsters from the area and return the number
	 * removed.
	 * 
	 * @return How many monsters were removed
	 */
	public int clearArea();

	/**
	 * Clear the area from all monsters whose classes are instances of the class
	 * belonging the the given id.
	 * 
	 * @return How many monsters were removed
	 */
	public int clearArea(String entityClassID);
}
