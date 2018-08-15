package mhfc.net.common.ai.general.provider.simple;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundEvent;

public interface IRoarProvider {

	SoundEvent getRoarSoundLocation();

	boolean shouldAffect(EntityLivingBase entity);

}
