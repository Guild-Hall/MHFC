package mhfc.net.common.quests.world;

import mhfc.net.common.world.area.IArea;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface EntityFactory extends BiFunction<World, IArea, Entity> {

	default EntityFactory andThenConfigure(Function<? super Entity, ? extends Entity> after) {
		Objects.requireNonNull(after);
		return (World w, IArea a) -> after.apply(apply(w, a));
	}

}
