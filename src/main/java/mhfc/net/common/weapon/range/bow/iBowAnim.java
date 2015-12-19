package mhfc.net.common.weapon.range.bow;


public interface iBowAnim {
	
	
	void renderBase();
	void restBow(boolean show);
	void pullSlow(boolean show);
	void pullHard(boolean show);
	
	int baseAnim(int frame);

}