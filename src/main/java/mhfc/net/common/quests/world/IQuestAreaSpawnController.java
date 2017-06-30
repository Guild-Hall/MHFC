package mhfc.net.common.quests.world;

import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public interface IQuestAreaSpawnController {

	/**
	 * Set whether default spawn are enabled or whether all spawns are controlled by the quest
	 *
	 * @param defaultSpawnsEnabled
	 *            If false no monsters will spawn from default
	 */
	public void setDefaultSpawnsEnabled(boolean defaultSpawnsEnabled);

	/**
	 * Directly spawn an entity, position controlled by the controller.
	 *
	 * @param entity
	 */
	public void spawnEntity(EntityFactory entity);

	/**
	 * Directly spawn an entity at the position if the position is inside the controlled area.
	 *
	 * @param entity
	 */
	public void spawnEntity(EntityFactory entity, double x, double y, double z);

	/**
	 * Enqueues the queue for spawning. The area will, in each tick, try to spawn the next entity from the queue. If it
	 * is not restricted it will do so and remove the element, else it will not consume it.
	 */
	public void enqueueSpawns(Queue<EntityFactory> qu);

	public void dequeueSpawns(Queue<EntityFactory> qu);

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
	public void setGenerationMaximum(ResourceLocation entityID, int maxAmount);

	public <T extends Entity> void setGenerationMaximum(Class<T> entityclass, int maxAmount);

	/**
	 * Clear the area from all monsters whose classes are instances of the class belonging the the given id.
	 *
	 * @return How many monsters were removed
	 */
	default public int clearAreaOf(ResourceLocation entityClassID) {
		Class<? extends Entity> clazz = EntityList.getClass(entityClassID);
		if (clazz == null) {
			return 0;
		}
		return clearAreaOf(clazz::isInstance);
	}

	/**
	 * Clear the area from all monsters who match the given predicate.
	 *
	 * @return How many monsters were removed
	 */
	public int clearAreaOf(Predicate<Entity> predicate);

	/**
	 * Tells the area spawn controller to look through the current spawn queues and take appropriate action.
	 */
	public void runSpawnCycle();

	public Set<Entity> getControlledEntities();

}
