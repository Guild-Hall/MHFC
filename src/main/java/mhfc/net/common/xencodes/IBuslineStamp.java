package mhfc.net.common.xencodes;

public interface IBuslineStamp {
	
	/**
	 * IBuslineStamp verifies the current supplied hitbox through specific bytes
	 */
	
	
	
	public void readSTAMP(byte bufHitBox, int retail, boolean ifstraight);
	
	
	public void initSTAMP(byte bufreadBox, int retailed);
	
}
