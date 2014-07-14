package mhfc.heltrato.common.util.world;

import java.util.Random;

public class MHFCSpawn {
	public static int spawnrate;
	public static Random rand = new Random();
	
	public static boolean getRandomSpawnChance(){
		int i = Math.max(1, spawnrate);
		i = Math.min(30, i);
		return rand.nextInt(50 / i) == 0;
	  }
	
	/*To be add soon :D
	 * MHFCLightning l = new MHFCLightning(e.worldObj);
	MHFCLightning l1 = new MHFCLightning(e.worldObj);
	l.setLocationAndAngles(e.posX + 4, target.posY, e.posZ + 4, 0, 0);
	l1.setLocationAndAngles(e.posX + 5, target.posY, e.posZ + 5, 0, 0);
	e.worldObj.spawnEntityInWorld(l);
	e.worldObj.spawnEntityInWorld(l1);
	*/
}
