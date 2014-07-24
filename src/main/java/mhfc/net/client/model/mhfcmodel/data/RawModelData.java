package mhfc.net.client.model.mhfcmodel.data;

import java.util.HashMap;
import java.util.Map;

import mhfc.net.client.model.mhfcmodel.glcontext.IGLHelper;
import net.minecraft.util.ResourceLocation;

/**
 * Represents the model data right after being loaded. No optimizations or
 * translation has happened yet. This is the raw data return from the
 * appropriate loader. <br>
 * This data will then be translated into an {@link IModelData} by the
 * appropriate {@link IGLHelper} to be used to render the model.
 *
 * @author WorldSEnder
 *
 */
public abstract class RawModelData {
	public static class Vector3f {
		public float x;
		public float y;
		public float z;

		public Vector3f(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	public static class Vector2f {
		public float x;
		public float y;

		public Vector2f(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}

	public static class BoneBinding {
		public int boneIndex;
		public float bindingValue;

		public BoneBinding(int boneIndex, float bindingValue) {
			this.boneIndex = boneIndex;
			this.bindingValue = bindingValue;
		}
	}

	public static class Texture {
		public ResourceLocation resLocation;

		public Texture(ResourceLocation resLocation) {
			this.resLocation = resLocation;
		}
	}

	public static class TesselationPoint {
		public Vector3f coords;
		public Vector3f normal;
		public Vector2f texCoords;
		public BoneBinding[] boneBindings;

		public TesselationPoint(Vector3f coords, Vector3f normal,
				Vector2f texCoords) {
			this.coords = coords;
			this.normal = normal;
			this.texCoords = texCoords;
		}
	}

	public static class ModelPart {
		/** All available {@link TesselationPoint}s in this part of the model */
		public TesselationPoint[] vertexArray;
		/** The array to store the order of the {@link TesselationPoint}s */
		public int[] indexArray;
		/** The texture this part of the model uses */
		public Texture texture;

	}

	public final Map<String, ModelPart> partsByName = new HashMap<>();
	public String name;
}
