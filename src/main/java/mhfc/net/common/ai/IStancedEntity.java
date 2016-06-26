package mhfc.net.common.ai;

import mhfc.net.common.ai.IStancedEntity.IStance;
import net.minecraft.entity.EntityLiving;

public interface IStancedEntity<EntityT extends EntityLiving & IStancedEntity<EntityT, StanceT>, StanceT extends Enum<StanceT> & IStance<EntityT>> {
	public static interface IStance<EntityT> {
		void onAttackStart(IExecutableAction<? super EntityT> attack,
			EntityT entity);

		void onAttackEnd(IExecutableAction<? super EntityT> attack,
			EntityT entity);

		void onStanceStart();

		void onStanceEnd();
	}

	public void setStance(StanceT stance);

	public StanceT getStance();
}
