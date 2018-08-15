package mhfc.net.common.ai.general.provider.adapters;

import mhfc.net.common.ai.general.provider.simple.IRoarProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundEvent;

public class RoarAdapter implements IRoarProvider {

	private SoundEvent soundFile;
	private boolean shouldStun;

	public RoarAdapter(SoundEvent roarSoundFile, boolean shouldStun) {
		this.shouldStun = shouldStun;
		this.soundFile = roarSoundFile;
	}

	@Override
	public SoundEvent getRoarSoundLocation() {
		return soundFile;
	}

	@Override
	public boolean shouldAffect(EntityLivingBase target) {
		return shouldStun;
	}
}
