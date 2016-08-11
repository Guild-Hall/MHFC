package mhfc.net.common.ai.general.provider.simple;

import java.util.Objects;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mhfc.net.common.ai.IActionRecorder;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.eventhandler.ai.ActionSelectionEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public interface ISelectionPredicate<EntityT extends EntityLiving> {

	public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target);

	public static class SelectionAdapter<EntityT extends EntityLiving> implements ISelectionPredicate<EntityT> {

		private DistanceAdapter<EntityT> distanceAdapter;
		private AngleAdapter<EntityT> angleAdapter;

		/**
		 * The angles are in degrees, negative values refer to the left side, positive to the right side
		 */
		public SelectionAdapter(float minAngle, float maxAngle, double minDistance, double maxDistance) {
			distanceAdapter = new DistanceAdapter<>(minDistance, maxDistance);
			angleAdapter = new AngleAdapter<>(minAngle, maxAngle);
		}

		@Override
		public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
			return distanceAdapter.shouldSelectAttack(attack, actor, target)
					&& angleAdapter.shouldSelectAttack(attack, actor, target);
		}
	}

	public static class CooldownAdapter<EntityT extends EntityLiving> implements ISelectionPredicate<EntityT> {

		private int cooldown;
		private int cooldownRemaining;
		private IExecutableAction<? super EntityT> attack;
		ISelectionPredicate<? super EntityT> originalPredicate;

		public CooldownAdapter(
				IExecutableAction<? super EntityT> attack,
				int cooldown,
				ISelectionPredicate<EntityT> originalPredicate) {
			this.attack = Objects.requireNonNull(attack);
			this.originalPredicate = Objects.requireNonNull(originalPredicate);
			this.cooldown = cooldown;
			this.cooldownRemaining = 0;
		}

		@Override
		public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
			if (cooldownRemaining > 0)
				cooldownRemaining--;
			return cooldownRemaining == 0;
		}

		@SubscribeEvent
		public void onSelectionSuccess(ActionSelectionEvent ase) {
			if (ase.chosenAction == attack)
				cooldownRemaining = cooldown;
		}
	}

	public static class DistanceAdapter<EntityT extends EntityLiving> implements ISelectionPredicate<EntityT> {

		private double minDistance, maxDistance;

		public DistanceAdapter(double minDistance, double maxDistance) {
			this.minDistance = minDistance;
			this.maxDistance = maxDistance;
		}

		@Override
		public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
			if (target == null)
				return false;
			double distance = actor.getDistanceToEntity(target);
			return distance >= minDistance && distance <= maxDistance;
		}
	}

	/**
	 * Selects only when the target entity is within a certain view angle. When minAngle is bigger than maxAngle, it
	 * wraps around once. Negative angle refer to the left side, positive to the right side.
	 * 
	 * @author Katora
	 *
	 * @param <EntityT>
	 */
	public static class AngleAdapter<EntityT extends EntityLiving> implements ISelectionPredicate<EntityT> {

		private float minAngle, maxAngle;

		public AngleAdapter(float minAngle, float maxAngle) {
			this.minAngle = minAngle;
			this.maxAngle = maxAngle;
		}

		@Override
		public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
			if (target == null)
				return false;
			float angle = AIUtils.getViewingAngle(actor, target);
			if (minAngle <= maxAngle)
				return angle >= minAngle && angle <= maxAngle;
			else
				return angle <= maxAngle || angle >= minAngle;
		}
	}

	public static class SelectIdleAdapter<EntityT extends EntityLiving> implements ISelectionPredicate<EntityT> {

		@Override
		public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
			return target == null;
		}
	}

	public static class SelectAlways<EntityT extends EntityLiving> implements ISelectionPredicate<EntityT> {

		@Override
		public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
			return true;
		}

	}

	public static class AllOfAdapter<EntityT extends EntityLiving> implements ISelectionPredicate<EntityT> {

		private ISelectionPredicate<EntityT>[] group;

		public AllOfAdapter(ISelectionPredicate<EntityT>[] toFulfil) {
			group = Objects.requireNonNull(toFulfil);
		}

		@Override
		public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
			boolean all = true;
			for (ISelectionPredicate<EntityT> pred : group) {
				all &= pred.shouldSelectAttack(attack, actor, target);
			}
			return all;
		}
	}

	public static class SpecificLastActionAdapter<EntityT extends EntityLiving & IActionRecorder<? super EntityT>>
			implements
			ISelectionPredicate<EntityT> {

		private IExecutableAction<? super EntityT> actionToTrack;

		public SpecificLastActionAdapter(IExecutableAction<? super EntityT> actionToTrack) {
			this.actionToTrack = actionToTrack;
		}

		@Override
		public boolean shouldSelectAttack(IExecutableAction<? super EntityT> attack, EntityT actor, Entity target) {
			return actor.getLastAction() == actionToTrack;
		}

	}

}
