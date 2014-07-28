package mhfc.net.client.model.mhfcmodel.data;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import net.minecraft.util.ResourceLocation;

public class RawDataV1 implements IRawData {

	public static class BoneBinding {
		/** To be interpreted as unsigned */
		public int boneIndex;
		public float bindingValue;
	}

	public static class Texture {
		public ResourceLocation resLocation;
	}

	public static class TesselationPoint {
		public Vector3f coords;
		public Vector3f normal;
		public Vector2f texCoords;
		public BoneBinding[] boneBindings;
	}

	public static class ModelPart {
		public String name;
		/** All available {@link TesselationPoint}s in this part of the model */
		public TesselationPoint[] vertexArray;
		/** The array to store the order of the {@link TesselationPoint}s */
		public int[] indexArray;
		/** The texture this part of the model uses */
		public Texture texture;
	}

	public static class Bone {
		public String name;
		public Quat4f rotationQuat;
		public Vector3f offset;
		public Bone parent;
	}

	public static class Header {
		/**
		 * Unsigned byte
		 */
		public int nbrParts;
		/**
		 * Unsigned byte
		 */
		public int nbrBones;
	}
	public Header header;
	/**
	 * A list of all parts
	 */
	public ModelPart[] parts;
	/**
	 * A list of all bones
	 */
	public Bone[] bones;
}
