package mhfc.net.client.model.mhfcmodel.loader;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import mhfc.net.client.model.mhfcmodel.data.RawDataV1;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.Bone;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.BoneBinding;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.Header;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.ModelPart;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.TesselationPoint;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.Texture;
import net.minecraft.util.ResourceLocation;

/**
 * Loads a file with version signature 1. This implementation follows the all or
 * nothing principle, so no corrupt data will ever be accessible.<br>
 * To find a full specification of the file format please visit<br>
 * // TODO: file specs
 *
 * @author WorldSEnder
 *
 */
public class LoaderVersion1 extends VersionizedModelLoader {
	// TODO: range check on various locations
	private static final int NBR_BONEBINDINGS = 4;
	public static final LoaderVersion1 instance = new LoaderVersion1();

	@Override
	public RawDataV1 loadFromInputStream(int version, DataInput di)
			throws IOException {
		RawDataV1 data = new RawDataV1();
		// Read the header
		Header header = new Header();
		int nbrParts = di.readUnsignedByte();
		int nbrBones = di.readUnsignedByte();
		header.nbrBones = nbrBones;
		header.nbrParts = nbrParts;
		// Read parts
		ModelPart[] parts = new ModelPart[nbrParts];
		for (int i = 0; i < nbrParts; parts[i++] = readPartFrom(di, header));
		// Read bones
		Bone[] bones = new Bone[nbrBones];
		for (int i = 0; i < nbrBones; bones[i++] = readBoneFrom(di, header));
		// Read parents
		for (int i = 0; i < nbrBones; ++i) {
			int parentIndex = di.readShort();
			if (parentIndex >= 0) {
				bones[i].parent = bones[parentIndex];
			}
		}
		// Apply data
		data.header = header;
		data.bones = bones;
		data.parts = parts;
		return data;
	}

	protected Bone readBoneFrom(DataInput di, Header header)
			throws EOFException, IOException {
		Bone bone = new Bone();
		// Read name
		String name = this.readString(di);
		// Read quaternion
		Quat4f quat = this.readQuat(di);
		// Read translation
		Vector3f translation = this.readVector3f(di);
		// Apply attributes
		bone.name = name;
		bone.rotationQuat = quat;
		bone.translation = translation;
		return bone;
	}

	protected ModelPart readPartFrom(DataInput di, Header header)
			throws EOFException, IOException {
		ModelPart mp = new ModelPart();
		// Read "header"
		int nbrPoints = di.readUnsignedShort();
		int nbrIndices = di.readUnsignedShort();
		// Read name
		String name = this.readString(di);
		// Read Texture
		Texture texture = this.readTextureFrom(di, header);
		// Read points
		TesselationPoint[] vertexArray = new TesselationPoint[nbrPoints];
		for (int i = 0; i < nbrPoints; vertexArray[i++] = readPointFrom(di,
				header));
		// Read indices
		int[] indexArray = new int[nbrIndices];
		for (int i = 0; i < nbrIndices; indexArray[i++] = di
				.readUnsignedShort());
		// Apply attributes
		mp.name = name;
		mp.texture = texture;
		mp.vertexArray = vertexArray;
		mp.indexArray = indexArray;
		return mp;
	}
	protected Texture readTextureFrom(DataInput di, Header header)
			throws EOFException, IOException {
		Texture tex = new Texture();
		tex.resLocation = new ResourceLocation(this.readString(di));
		return tex;
	}

	protected TesselationPoint readPointFrom(DataInput di, Header header)
			throws EOFException, IOException {
		TesselationPoint tessP = new TesselationPoint();
		// Read coords
		Vector3f coords = this.readVector3f(di);
		// Read normal
		Vector3f normal = this.readVector3f(di);
		// Read texture coordinates
		Vector2f texCoords = this.readVector2f(di);
		// Read bindings
		BoneBinding[] bindings = new BoneBinding[NBR_BONEBINDINGS];
		for (int i = 0; i < NBR_BONEBINDINGS; bindings[i++] = readBoneBindingFrom(
				di, header));
		// Apply attributes
		tessP.coords = coords;
		tessP.normal = normal;
		tessP.texCoords = texCoords;
		tessP.boneBindings = bindings;
		return tessP;
	}

	protected BoneBinding readBoneBindingFrom(DataInput di, Header header)
			throws EOFException, IOException {
		BoneBinding binding = new BoneBinding();
		// Read index of bone this is bound to
		int bindIndex = di.readUnsignedByte();
		// Read strength of binding
		float bindingValue = di.readFloat();
		// Apply attributes
		binding.boneIndex = bindIndex;
		binding.bindingValue = bindingValue;
		return binding;
	}
}
