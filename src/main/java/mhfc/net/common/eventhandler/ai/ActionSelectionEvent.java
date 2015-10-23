package mhfc.net.common.eventhandler.ai;

import mhfc.net.common.ai.IExecutableAction;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;

public class ActionSelectionEvent extends LivingEvent {
	public final IExecutableAction<?> chosenAction;

	public ActionSelectionEvent(IExecutableAction<?> chosenAction,
		EntityLivingBase entity) {
		super(entity);
		this.chosenAction = chosenAction;
	}
}
