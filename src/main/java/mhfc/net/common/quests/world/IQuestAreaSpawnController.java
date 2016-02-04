package mhfc.net.common.quests.world;

import java.util.Queue;
import java.util.Set;

import mhfc.net.common.quests.world.SpawnControllerAdapter.Spawnable;
import net.minecraft.entity.Entity;

public interface IQuestAreaSpawnController {

	/**
	 * Set whether default spawn are enabled or whether all spawns are controlled by the quest
	 * 
	 * @param defaultSpawnsEnabled
	 *            If false no monsters will spawn from default
	 */
	public void defaultSpawnsEnabled(boolean defaultSpawnsEnabled);

	/**
	 * Spawn one monster from an entityID. For example "Creeper" for a creeper. The position is determined by the
	 * controller.
	 * 
	 * @param entityID
	 */
	public void spawnEntity(String entityID);

	/**
	 * Spawn an entity from the given ID at the given position only if it is inside the area.
	 * 
	 * @param entityID
	 */
	public void spawnEntity(String entityID, double x, double y, double z);

	/**
	 * Directly spawn an entity, position controlled by the controller.
	 * 
	 * @param entity
	 */
	public void spawnEntity(Spawnable entity);

	/**
	 * Directly spawn an entity at the position if the position is inside the controlled area.
	 * 
	 * @param entity
	 */
	public void spawnEntity(Spawnable entity, double x, double y, double z);

	/**
	 * Enqueues the queue for spawning. The area will, in each tick, try to spawn the next entity from the queue. If it
	 * is not restricted it will do so and remove the element, else it will not consume it.
	 */
	public void enqueueSpawns(Queue<Spawnable> qu);

	public void dequeueSpawns(Queue<Spawnable> qu);

	public void clearQueues();

	/**
	 * Advises the controller to generate entities of the given entity id only until the max amount is reached. <br>
	 * This does not affect entities spawned directly through the {@link IQuestAreaSpawnController#spawnEntity(Entity)}
	 * and {@link IQuestAreaSpawnController#spawnEntity(String)} methods but only does spawned by
	 * {@link IQuestAreaSpawnController#enque()}
	 * 
	 * @param entityID
	 * @param maxAmount
	 */
	public void setGenerationMaximum(String entityID, int maxAmount);

	public <T extends Entity> void setGenerationMaximum(Class<T> entityclass, int maxAmount);

	/**
	 * Remove all MHFC or hostile monsters from the area and return the number removed.
	 * 
	 * @return How many monsters were removed
	 */
	public int clearArea();

	/**
	 * Clear the area from all monsters whose classes are instances of the class belonging the the given id.
	 * 
	 * @return How many monsters were removed
	 */
	public int clearAreaOf(String entityClassID);

	/**
	 * Tells the area spawn controller to look through the current spawn queues and take appropriate action.
	 */
	public void runSpawnCycle();

	public Set<Entity> getControlledEntities();

}
