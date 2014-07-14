package mhfc.heltrato.client.model;

import java.util.HashMap;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.interfaces.iMHFC;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MHFCAnimator {
	
	public MHFCAnimator(ModelBase model) {
		tempTick = 0;
		correctAnim = false;
		mainModel = model;
		transformMap = new HashMap<ModelRenderer, MHFCTransform>();
		prevTransformMap = new HashMap<ModelRenderer, MHFCTransform>();
	}
	
	public iMHFC getEntity() {
		return animEntity;
	}
	
	public void update(iMHFC entity) {
		tempTick = prevTempTick = 0;
		correctAnim = false;
		animEntity = entity;
		transformMap.clear();
		prevTransformMap.clear();
		for(int i = 0; i < mainModel.boxList.size(); i++) {
			ModelRenderer box = (ModelRenderer)mainModel.boxList.get(i);
			box.rotateAngleX = 0F;
			box.rotateAngleY = 0F;
			box.rotateAngleZ = 0F;
		}
	}
	
	public boolean setAnim(int animID) {
		tempTick = prevTempTick = 0;
		correctAnim = animEntity.getAnimID() == animID;
		return correctAnim;
	}
	
	public void startPhase(int duration) {
		if(!correctAnim) return;
		prevTempTick = tempTick;
		tempTick += duration;
	}
	
	public void setStationaryPhase(int duration) {
		startPhase(duration);
		endPhase(true);
	}
	
	public void resetPhase(int duration) {
		startPhase(duration);
		endPhase();
	}
	
	public void rotate(ModelRenderer box, float x, float y, float z) {
		if(!correctAnim) return;
		if(!transformMap.containsKey(box)) transformMap.put(box, new MHFCTransform(x, y, z));
		else transformMap.get(box).addRot(x, y, z);
	}
	
	public void move(ModelRenderer box, float x, float y, float z) {
		if(!correctAnim) return;
		if(!transformMap.containsKey(box)) transformMap.put(box, new MHFCTransform(x, y, z, 0F, 0F, 0F));
		else transformMap.get(box).addOffset(x, y, z);
	}
	
	public void endPhase() {
		endPhase(false);
	}
	
	private void endPhase(boolean stationary) {
		if(!correctAnim) return;
		int animTick = animEntity.getAnimTick();
		if(animTick >= prevTempTick && animTick < tempTick) {
			if(stationary) {
				for(ModelRenderer box : prevTransformMap.keySet()) {
					MHFCTransform transform = prevTransformMap.get(box);
					box.rotateAngleX += transform.rotX;
					box.rotateAngleY += transform.rotY;
					box.rotateAngleZ += transform.rotZ;
					box.rotationPointX += transform.offsetX;
					box.rotationPointY += transform.offsetY;
					box.rotationPointZ += transform.offsetZ;
				}
			} else {
				float tick = (animTick - prevTempTick + MHFCMain.proxy.getPartialTick()) / (tempTick - prevTempTick);
				float inc = MathHelper.sin(tick * PI / 2F), dec = 1F - inc;
				for(ModelRenderer box : prevTransformMap.keySet()) {
					MHFCTransform transform = prevTransformMap.get(box);
					box.rotateAngleX += dec * transform.rotX;
					box.rotateAngleY += dec * transform.rotY;
					box.rotateAngleZ += dec * transform.rotZ;
					box.rotationPointX += dec * transform.offsetX;
					box.rotationPointY += dec * transform.offsetY;
					box.rotationPointZ += dec * transform.offsetZ;
				}
				for(ModelRenderer box : transformMap.keySet()) {
					MHFCTransform transform = transformMap.get(box);
					box.rotateAngleX += inc * transform.rotX;
					box.rotateAngleY += inc * transform.rotY;
					box.rotateAngleZ += inc * transform.rotZ;
					box.rotationPointX += inc * transform.offsetX;
					box.rotationPointY += inc * transform.offsetY;
					box.rotationPointZ += inc * transform.offsetZ;
				}
			}
		}
		if(!stationary) {
			prevTransformMap.clear();
			prevTransformMap.putAll(transformMap);
			transformMap.clear();
		}
	}
	
	private int tempTick, prevTempTick;
	private boolean correctAnim;
	private ModelBase mainModel;
	private iMHFC animEntity;
	private HashMap<ModelRenderer, MHFCTransform> transformMap, prevTransformMap;
	
	public static final float PI = (float)Math.PI;
}
