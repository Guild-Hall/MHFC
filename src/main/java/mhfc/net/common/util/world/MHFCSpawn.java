package mhfc.net.common.util.world;

import java.util.Random;

public class MHFCSpawn {
	public static int spawnrate;
	public static Random rand = new Random();
	
	public static boolean getRandomSpawnChance(){
		int i = Math.max(1, spawnrate);
		i = Math.min(30, i);
		return rand.nextInt(50 / i) == 0;
	  }
	

}
