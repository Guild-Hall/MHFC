package mhfc.net.client.model.mhfcmodel.data;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class RawDataV1 implements IRawData {

	public static class BoneBinding {
		/** To be interpreted as unsigned */
		public byte boneIndex;
		public float bindingValue;
	}

	public static class Material {
		public String resLocationRaw;
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
		public TesselationPoint[] points;
		/**
		 * The array to store the order of the {@link TesselationPoint}s. To be
		 * interpreted as unsigned.
		 */
		public short[] indices;
		/** The material this part of the model uses */
		public Material material;
	}

	public static class Bone {
		public String name;
		public Quaternion rotation;
		public Vector3f offset;
		/** Parent of this bone as array index. A value of 0xFF means no parent */
		public byte parent;
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
	// Header as is is automatically part of the model
	// public Header header;
	/**
	 * A list of all parts
	 */
	public ModelPart[] parts;
	/**
	 * A list of all bones
	 */
	public Bone[] bones;
}
