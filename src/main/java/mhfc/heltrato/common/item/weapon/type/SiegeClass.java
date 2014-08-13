package mhfc.heltrato.common.item.weapon.type;

import java.util.Random;

import mhfc.heltrato.MHFCMain;
import net.minecraft.item.ItemSword;

public class SiegeClass extends ItemSword {

	public float damageWeapon;
	public Random rand;

	public SiegeClass(ToolMaterial getType) {
		super(getType);
		setCreativeTab(MHFCMain.mhfctabs);
		rand = new Random();
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

}
