package mhfc.net.common.ai;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;

public interface IActionRecorder<EntityT extends EntityLivingBase> {

	/**
	 * Returns as many actions as the tracker can remember (should be at least
	 * one and the last action in the list should be the same as from
	 * {@link IActionRecorder#getLastAction()}. The list can have null elements.
	 */
	public List<IExecutableAction<? super EntityT>> getActionHistory();

	/**
	 * Returns the last action that the tracker entity has recorded. <b>DON'T
	 * modify the action as it may still be in active use.</b> Null if there is
	 * no such action.
	 * 
	 * @return The action that was carried out last
	 */
	public IExecutableAction<? super EntityT> getLastAction();

	public static class RecorderAdapter<EntityT extends EntityLivingBase>
		implements
			IActionRecorder<EntityT> {

		private List<IExecutableAction<? super EntityT>> actionHistory;
		private int maximum;

		public RecorderAdapter(int historyLength) {
			maximum = historyLength;
			actionHistory = new ArrayList<IExecutableAction<? super EntityT>>();
		}

		public void addAction(IExecutableAction<? super EntityT> action) {
			actionHistory.add(action);
			int length = actionHistory.size();
			if (length > maximum)
				actionHistory.subList(0, length - maximum).clear();
		}

		@Override
		public List<IExecutableAction<? super EntityT>> getActionHistory() {
			return actionHistory;
		}

		@Override
		public IExecutableAction<? super EntityT> getLastAction() {
			int size = actionHistory.size();
			return size > 0 ? actionHistory.get(size - 1) : null;
		}

	}

}
