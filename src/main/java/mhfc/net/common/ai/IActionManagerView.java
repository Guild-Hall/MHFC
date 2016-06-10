package mhfc.net.common.ai;

import com.github.worldsender.mcanm.common.animation.IAnimation;

import mhfc.net.common.network.message.MessageAIAction;
import net.minecraft.entity.EntityLiving;

public interface IActionManagerView<EntityT extends EntityLiving & IManagedActions<EntityT>> {

	IAnimation getCurrentAnimation();

	int getCurrentFrame();

	IExecutableAction<? super EntityT> chooseAttack();

	void receiveUpdate(MessageAIAction<EntityT> message);

}
