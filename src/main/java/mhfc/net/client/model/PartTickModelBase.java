package mhfc.net.client.model;

import net.minecraft.client.model.ModelBase;

public abstract class PartTickModelBase extends ModelBase {
	protected float partialTick;

	public void setPartialTick(float newPartialTick) {
		if (checkValidPartial(newPartialTick))
			this.partialTick = newPartialTick;
	}

	private static boolean checkValidPartial(float newPartialTick) {
		return newPartialTick >= 0.0F && newPartialTick <= 1.0F;
	}

	public float getPartialTick() {
		return this.partialTick;
	}
}
