package mhfc.net.client.model.mhfcmodel.loader;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import mhfc.net.client.model.mhfcmodel.Utils;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.Bone;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.BoneBinding;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.Header;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.ModelPart;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.TesselationPoint;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.Texture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelFormatException;

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
	private static final int NBR_BONEBINDINGS = 4;
	public static final LoaderVersion1 instance = new LoaderVersion1();

	@Override
	public RawDataV1 loadFromInputStream(int version, DataInputStream di)
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
		Set<String> partsNameSet = new TreeSet<>();
		for (int i = 0; i < nbrParts; ++i) {
			ModelPart newPart = readPartFrom(di, header);
			if (!partsNameSet.add(newPart.name))
				throw new ModelFormatException("Two parts with same name "
						+ newPart.name);
			parts[i] = newPart;
		}
		// Read bones
		Bone[] bones = new Bone[nbrBones];
		Set<String> boneNameSet = new TreeSet<>();
		for (int i = 0; i < nbrBones; ++i) {
			Bone newBone = readBoneFrom(di, header);
			if (!boneNameSet.add(newBone.name))
				throw new ModelFormatException("Two bones with same name "
						+ newBone.name);
			bones[i] = newBone;
		}
		// Read parents
		readBoneParents(di, bones); // Structure has to be tree-like
		// Apply data
		data.header = header;
		data.bones = bones;
		data.parts = parts;
		return data;
	}
	/**
	 * This method has to check for a tree-like structure of the bones. No bone
	 * can be parent of his parent or his parent's parent, etc.
	 *
	 * @param di
	 *            The {@link DataInputStream} to read indices from
	 * @param bones
	 *            the bone array with read bones
	 * @throws EOFException
	 *             if the file ends unexpectedly
	 * @throws IOException
	 *             if an IOException occurs on the {@link DataInputStream}
	 */
	protected void readBoneParents(DataInputStream di, Bone[] bones)
			throws EOFException, IOException {
		int nbrBones = bones.length;
		for (int i = 0; i < nbrBones; ++i) {
			int parentIndex = di.readUnsignedShort();
			if (parentIndex < 256) {
				if (parentIndex >= nbrBones)
					throw new ModelFormatException(
							String.format(
									"ParentIndex (%d) has to be smaller than nbrBones (%d).",
									parentIndex, nbrBones));
				bones[i].parent = bones[parentIndex];
			}
		}
	}

	protected Bone readBoneFrom(DataInputStream di, Header header)
			throws EOFException, IOException {
		Bone bone = new Bone();
		// Read name
		String name = Utils.readString(di);
		// Read quaternion
		Quat4f quat = Utils.readQuat(di);
		// Read offset
		Vector3f offset = Utils.readVector3f(di);
		// Apply attributes
		bone.name = name;
		bone.rotationQuat = quat;
		bone.offset = offset;
		return bone;
	}

	protected ModelPart readPartFrom(DataInputStream di, Header header)
			throws EOFException, IOException {
		ModelPart mp = new ModelPart();
		// Read "header"
		int nbrPoints = di.readUnsignedShort();
		int nbrIndices = di.readUnsignedShort() * 3;
		// Read name
		String name = Utils.readString(di);
		// Read Texture
		Texture texture = this.readTextureFrom(di, header);
		// Read points
		TesselationPoint[] vertexArray = new TesselationPoint[nbrPoints];
		for (int i = 0; i < nbrPoints; vertexArray[i++] = readPointFrom(di,
				header));
		// Read indices
		int[] indexArray = new int[nbrIndices];
		for (int i = 0; i < nbrIndices; ++i) {
			int candidate = di.readUnsignedShort();
			if (candidate >= nbrPoints)
				throw new ModelFormatException(
						String.format(
								"Vertexindex (%d) has to be smaller than nbrPoints (%d).",
								candidate, nbrPoints));
			indexArray[i] = candidate;
		}
		// Apply attributes
		mp.name = name;
		mp.texture = texture;
		mp.vertexArray = vertexArray;
		mp.indexArray = indexArray;
		return mp;
	}

	protected Texture readTextureFrom(DataInputStream di, Header header)
			throws EOFException, IOException {
		Texture tex = new Texture();
		tex.resLocation = new ResourceLocation(Utils.readString(di));
		return tex;
	}

	protected TesselationPoint readPointFrom(DataInputStream di, Header header)
			throws EOFException, IOException {
		TesselationPoint tessP = new TesselationPoint();
		// Read coords
		Vector3f coords = Utils.readVector3f(di);
		// Read normal
		Vector3f normal = Utils.readVector3f(di);
		if (normal.length() == 0)
			throw new ModelFormatException(
					"Normal vector can't have zerolength.");
		// Read texture coordinates
		Vector2f texCoords = Utils.readVector2f(di);
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

	protected BoneBinding readBoneBindingFrom(DataInputStream di, Header header)
			throws EOFException, IOException {
		BoneBinding binding = new BoneBinding();
		// Read index of bone this is bound to
		int bindIndex = di.readUnsignedByte();
		if (bindIndex >= header.nbrBones)
			throw new ModelFormatException("Can't bind to non-existant bone.");
		// Read strength of binding
		float bindingValue = di.readFloat();
		if (Math.abs(bindingValue) > 100.0F)
			throw new ModelFormatException(String.format(
					"Value for binding seems out of range: %f", bindingValue));
		// Apply attributes
		binding.boneIndex = bindIndex;
		binding.bindingValue = bindingValue;
		return binding;
	}
}
