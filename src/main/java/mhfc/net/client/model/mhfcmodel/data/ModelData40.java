package mhfc.net.client.model.mhfcmodel.data;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindBufferBase;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glVertexAttribIPointer;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;
import static org.lwjgl.opengl.GL32.glDrawElementsBaseVertex;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Matrix4f;

import mhfc.net.client.model.mhfcmodel.AnimationInformation;
import mhfc.net.client.model.mhfcmodel.Utils;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.Bone;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.ModelPart;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.TesselationPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;

public class ModelData40 implements IModelData {
	protected static Minecraft mc = Minecraft.getMinecraft();
	public static final int VS_POSITION_LOCATION = 0;
	public static final int VS_NORMAL_LOCATION = 1;
	public static final int VS_TEXCOORDS_LOCATION = 2;
	public static final int VS_TRANSFORM_INDICES_LOCATION = 3;
	public static final int VS_TRANSFORM_VALUES_LOCATION = 4;
	public static final int VS_ATTR_SIZE = 52;

	public static final int U_BONE_MATRIX_BIND = 0;
	public static final int U_BONE_MATRIX_SIZE = 16;
	public static final int U_BONE_TRANSFORM_BINDING = 1;
	public static final int U_BONE_TRANSFORM_SIZE = 16;

	public static class Part {
		/**
		 * The offset of this part's indices in the ELEMENT_BUFFER
		 */
		public final int startIndex;
		/**
		 * How many primitives should be drawn
		 */
		public final int indexCount;
		/**
		 * We use an offset. This means that the vertex data is offset in the
		 * attribute-buffer(s) but we don't have to care about calculating it
		 * for every index.
		 */
		public final int vertexOffset;

		public final ResourceLocation texLocation;

		public Part(int startIndex, int indexCount, int vertexOffset,
				ResourceLocation texLocation) {
			this.startIndex = startIndex;
			this.indexCount = indexCount;
			this.vertexOffset = vertexOffset;
			this.texLocation = texLocation;
		}

		public void render() {
			mc.renderEngine.bindTexture(texLocation);
			glDrawElementsBaseVertex(GL_TRIANGLES, indexCount,
					GL_UNSIGNED_SHORT, startIndex, vertexOffset);
		}
	}
	/**
	 * Is frozen
	 */
	public final ImmutableMap<String, Part> partMap;
	/**
	 * Immutable and ordered. This has to be ordered because the buffer has to
	 * be ordered.
	 */
	public final ImmutableSortedSet<Long> boneHashes;
	/**
	 * The model's Vertex Array Object. Stores vertex attribute buffers and
	 * client state.
	 */
	public final int vao;
	/**
	 * Uniform buffer for the bone matrices. Only gets set once as this
	 * describes the bones in binding pose.
	 */
	public final int boneMatricesBuff;
	/**
	 * Uniform buffer for the bone transforms. Gets set every frame to set
	 * current transformation and deformation.
	 */
	public final int boneTransformsBuff;

	/**
	 * Constucts the data to render from the read raw data. The raw data is
	 * final and can be tweaked as wanted.
	 *
	 * @param dataFrom
	 */
	public ModelData40(RawDataV1 dataFrom) {
		// Setup local values
		Map<String, Part> buildingPartMap = new HashMap<>();
		List<Long> boneHashes = new ArrayList<>();
		// We need 3 new buffers: attribute buffer (interleaved), bonematrices,
		// bonetransform
		IntBuffer bufferBuffer = Utils.directIntBuffer(4);
		glGenBuffers(bufferBuffer);
		int attrBuff = glGenBuffers();
		int indicesBuff = glGenBuffers();
		int boneMatricesBuff = glGenBuffers();
		int boneTransformBuff = glGenBuffers();
		int vao = glGenVertexArrays();
		// -- read data from passed data
		int totalPoints = 0;
		int totalIndices = 0;
		for (ModelPart p : dataFrom.parts) {
			Part part = new Part(totalIndices, p.indices.length, totalPoints,
					new ResourceLocation(p.material.resLocationRaw));
			buildingPartMap.put(p.name, part);
			totalIndices += p.indices.length;
			totalPoints += p.points.length;
		}
		// Read points
		ByteBuffer attrBuffer = Utils.directByteBuffer(totalPoints
				* VS_ATTR_SIZE);
		ShortBuffer indicesBuffer = Utils.directShortBuffer(totalIndices);
		FloatBuffer boneMatricesBuffer = Utils
				.directFloatBuffer(dataFrom.bones.length * U_BONE_MATRIX_SIZE);
		for (ModelPart part : dataFrom.parts) {
			putPartInto(part, attrBuffer, indicesBuffer);
		}
		for (Bone bone : dataFrom.bones) {
			putBoneInto(bone, boneMatricesBuffer);
		}
		// Prepare buffers
		attrBuffer.flip();
		indicesBuffer.flip();
		boneMatricesBuffer.flip();
		// matrices!!!
		glBindBuffer(GL_UNIFORM_BUFFER, boneMatricesBuff);
		glBufferData(GL_UNIFORM_BUFFER, boneMatricesBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBuff);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		// Bind boneTransformsBuff every draw
		glBindBuffer(GL_ARRAY_BUFFER, attrBuff);
		glBufferData(GL_ARRAY_BUFFER, attrBuffer, GL_STATIC_DRAW);
		// setup vao
		glBindVertexArray(vao);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBuff);
		glEnableVertexAttribArray(VS_POSITION_LOCATION);
		glEnableVertexAttribArray(VS_NORMAL_LOCATION);
		glEnableVertexAttribArray(VS_TEXCOORDS_LOCATION);
		glEnableVertexAttribArray(VS_TRANSFORM_INDICES_LOCATION);
		glEnableVertexAttribArray(VS_TRANSFORM_VALUES_LOCATION);
		// Set position
		glVertexAttribPointer(VS_POSITION_LOCATION, 3, GL_FLOAT, false, 52, 0);
		glVertexAttribPointer(VS_NORMAL_LOCATION, 3, GL_FLOAT, false, 52, 12);
		glVertexAttribPointer(VS_TEXCOORDS_LOCATION, 2, GL_FLOAT, false, 52, 24);
		glVertexAttribIPointer(VS_TRANSFORM_INDICES_LOCATION, 4,
				GL_UNSIGNED_BYTE, 52, 32);
		glVertexAttribPointer(VS_TRANSFORM_VALUES_LOCATION, 4, GL_FLOAT, false,
				52, 36);
		// Unbind
		glBindVertexArray(0);
		glBindBuffer(GL_UNIFORM_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		// Actually initialize the class fields
		this.vao = vao;
		this.boneMatricesBuff = boneMatricesBuff;
		this.boneTransformsBuff = boneTransformBuff;
		this.partMap = ImmutableMap.copyOf(buildingPartMap);
		this.boneHashes = ImmutableSortedSet.copyOf(boneHashes);
	}
	/**
	 * Binds the transform from the currently present attackinformation and
	 * uniform buffers
	 *
	 * @param info
	 *            the information to read from
	 */
	protected void bindUniforms(AnimationInformation info) {
		glBindBufferBase(GL_UNIFORM_BUFFER, U_BONE_MATRIX_BIND,
				boneMatricesBuff);
		// TODO: buffer bone transform data
		glBindBufferBase(GL_UNIFORM_BUFFER, U_BONE_TRANSFORM_BINDING,
				boneTransformsBuff);
	}
	/**
	 * Reverts actions of {@link #prepareRender()}
	 */
	protected void unbindUniforms() {
		glBindBufferBase(GL_UNIFORM_BUFFER, U_BONE_MATRIX_BIND, 0);
		glBindBufferBase(GL_UNIFORM_BUFFER, U_BONE_TRANSFORM_BINDING, 0);
	}
	/**
	 * Renders all parts that don't get filtered out by the
	 * {@link AnimationInformation#shouldDisplayPart(String, float)} method
	 *
	 * @param info
	 */
	public void renderFiltered(AnimationInformation info, float subFrame) {
		this.bindUniforms(info);
		glBindVertexArray(this.vao);
		// Filter parts to render
		// TODO: enable streaming approach once java 8 is enabled
		for (String part : this.partMap.keySet()) {
			Part p = this.partMap.get(part);
			if (p == null)
				continue;
			if (info == null || info.shouldDisplayPart(part, subFrame))
				p.render();
		}
		glBindVertexArray(0);
		this.unbindUniforms();
	}
	/**
	 * Fills the AttributeBuffer and the IndexBuffer with the content of the
	 * {@link ModelPart}.
	 *
	 * The AttributeBuffer will be advanced exactly {@value #VS_ATTR_SIZE} *
	 * pointCount and the IndexBuffer will be advanced exactly 2 * indexCount
	 * bytes aka. indexCount shorts
	 *
	 * @param part
	 *            the part to write into the buffers
	 * @param attrBuff
	 *            the AttributeBuffer
	 * @param indicesBuffer
	 *            the IndexBuffer
	 */
	protected static void putPartInto(ModelPart part, ByteBuffer attrBuff,
			ShortBuffer indicesBuffer) {
		for (TesselationPoint tp : part.points) {
			attrBuff.putFloat(tp.coords.x); // 4
			attrBuff.putFloat(tp.coords.y); // 8
			attrBuff.putFloat(tp.coords.z); // 12
			attrBuff.putFloat(tp.normal.x); // 16
			attrBuff.putFloat(tp.normal.y); // 20
			attrBuff.putFloat(tp.normal.z); // 24
			attrBuff.putFloat(tp.texCoords.x); // 28
			attrBuff.putFloat(tp.texCoords.y); // 32
			final int bindsPerPoint = 4;
			for (int i = 0; i < bindsPerPoint; attrBuff
					.put(tp.boneBindings[i++].boneIndex)); // 36
			for (int i = 0; i < bindsPerPoint; attrBuff
					.putFloat(tp.boneBindings[i++].bindingValue)); // 52
		}
		indicesBuffer.put(part.indices);
	}
	/**
	 * Places the bone's data at the current position in the buffer and advances
	 * the buffer
	 *
	 * @param bone
	 *            the bone which matrix should be placed in the matrix
	 * @param matrixBuffer
	 *            the buffer to write data to
	 */
	protected static void putBoneInto(Bone bone, FloatBuffer matrixBuffer) {
		Matrix4f localToWorld = new Matrix4f(bone.rotation, bone.offset, 1.0F);
		float[] col = new float[4];
		for (int i = 0; i < 4; ++i) {
			localToWorld.getColumn(i, col);
			matrixBuffer.put(col);
		}
	}
}
