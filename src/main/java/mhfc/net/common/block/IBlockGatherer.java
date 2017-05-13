package mhfc.net.common.block;

public interface IBlockGatherer {
	
	/**
	 * 
	 *  Im planning to make gathering blocks ( ore blocks, bug blocks etc.) to be no more metadata. This is
	 *  to make a one block for them and has the ability to get random items everytime a player tries to 
	 *  harvest/mine this block. Thus they can get random material item every random tick or chances. But of 
	 *  course this is to make the block indestructible and has a limited or count item to get mine.
	 *  
	 *   @Heltrato
	 * 
	 * 
	 * */
	
	
	float chanceRate();
	int numberOfMineableItems();
	
	
	

}
