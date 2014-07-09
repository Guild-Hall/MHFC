package mhfc.net.common.sound;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

/**
* This File is for the MHF Sounds
* 
*  This is not the official class for putting all the sounds from the mod. But instead these will be the clazz which all consist
*  
*  for the interfaces and gameplays of the mods. Such Events and stuff subscribes
* 
* 
* 
* DN: public<>? - easy access
* DN: ArrayList < music = variable for imports;
* DN: Resource Located <monsterhunter:(name.ogg)
* 
*/

public class MHFCSound {
	
	private final ArrayList<Object> music = Lists.newArrayList() ;
	private Random rand;
	private World world;
	private EntityPlayerMP EntityPlayer;
	private float bols;
	private float dobs;

	public MHFCSound()
	{
		rand = new Random();
	}
	
	public void getEventSound(int pitch, float strength, String type, String name, boolean get) {
		soundTemp(pitch, (int)strength, (int) dobs, bols, type);
	}
	
	public void soundTemp(int par1, int par3, int par5, float par7, String par8) {
		
	}
	
}
