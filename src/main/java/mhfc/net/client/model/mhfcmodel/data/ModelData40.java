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
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindBufferBase;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glVertexAttribIPointer;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;
import static org.lwjgl.opengl.GL32.glDrawElementsBaseVertex;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mhfc.net.client.model.mhfcmodel.Utils;
import mhfc.net.client.model.mhfcmodel.animation.IAnimatedObject;
import mhfc.net.client.model.mhfcmodel.animation.IAnimation;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.Bone;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.ModelPart;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.TesselationPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.util.vector.Matrix4f;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSortedSet;

public class ModelData40 implements IModelData {
	protected static Minecraft mc = Minecraft.getMinecraft();
	// Shader vars
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

	protected static class Part {
		/**
		 * The offset of this part's indices in the ELEMENT_BUFFER
		 */
		private final int startIndex;
		/**
		 * How many primitives should be drawn
		 */
		private final int indexCount;
		/**
		 * We use an offset. This means that the vertex data is offset in the
		 * attribute-buffer(s) but we don't have to care about calculating it
		 * for every index.
		 */
		private final int vertexOffset;

		private final ResourceLocation texLocation;
		private final String name;

		public Part(int startIndex, int indexCount, int vertexOffset,
				ResourceLocation texLocation, String name) {
			this.startIndex = startIndex;
			this.indexCount = indexCount;
			this.vertexOffset = vertexOffset;
			this.texLocation = texLocation;
			this.name = name;
		}

		public void render() {
			mc.renderEngine.bindTexture(texLocation);
			glDrawElementsBaseVertex(GL_TRIANGLES, indexCount,
					GL_UNSIGNED_SHORT, startIndex, vertexOffset);
		}

		public String getName() {
			return this.name;
		}
	}
	/**
	 * Is frozen
	 */
	private Part[] parts;
	/**
	 * Immutable and ordered. This has to be ordered because the buffer has to
	 * be ordered.
	 */
	@SuppressWarnings("unused")
	private Set<String> boneNames;
	/**
	 * The model's Vertex Array Object. Stores vertex attribute buffers and
	 * client state.
	 */
	private int vao;
	/**
	 * Uniform buffer for the bone matrices. Only gets set once as this
	 * describes the bones in binding pose.
	 */
	private int boneMatricesBuff;
	/**
	 * Uniform buffer for the bone transforms. Gets set every frame to set
	 * current transformation and deformation.
	 */
	private int boneTransformsBuff;

	/**
	 * Constucts the data to render from the read raw data. The raw data is
	 * final and can be tweaked as wanted.
	 *
	 * @param dataFrom
	 */
	public ModelData40(RawDataV1 dataFrom) {
		// Setup local values
		Part[] parts = new Part[dataFrom.parts.length];
		List<String> boneNames = new ArrayList<>();
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
		for (int i = 0; i < dataFrom.parts.length;) {
			RawDataV1.ModelPart p = dataFrom.parts[i];
			Part part = new Part(totalIndices, p.indices.length, totalPoints,
					new ResourceLocation(p.material.resLocationRaw), p.name);
			parts[i++] = part;
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
		this.parts = parts;
		this.boneNames = ImmutableSortedSet.copyOf(boneNames);
	}
	/**
	 * Binds the transform from the currently present attack information and
	 * uniform buffers
	 *
	 * @param currAttack
	 *            the currently executed attack
	 */
	protected void bindUniforms(IAnimation currAttack, float subFrame) {
		glBindBufferBase(GL_UNIFORM_BUFFER, U_BONE_MATRIX_BIND,
				boneMatricesBuff);
		// TODO: buffer bone transform data, careful, boneMatricesBuff is
		// localToParent
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
	 * Renders all parts without filtering any
	 *
	 * @param attack
	 *            the currently executed attack to retrieve transforms from
	 */
	@Override
	public void renderAll(IAnimation animation, float frame) {
		this.bindUniforms(animation, frame);
		glBindVertexArray(this.vao);
		for (Part part : this.parts) {
			part.render();
		}
		glBindVertexArray(0);
		this.unbindUniforms();
	}
	/**
	 * Renders all parts that don't get filtered out by the
	 * {@link IAnimatedObject#getPartPredicate(float)} method
	 *
	 * @param info
	 *            used for filtering parts
	 * @param attack
	 *            the currently executed attack to retrieve transforms from
	 */
	@Override
	public void renderFiltered(Predicate<String> filter, IAnimation animation,
			float frame) {
		this.bindUniforms(animation, frame);
		glBindVertexArray(this.vao);
		// Filter parts to render
		// TODO: enable streaming approach once java 8 is enabled
		for (Part part : this.parts) {
			if (filter == null || filter.apply(part.getName()))
				part.render();
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
			// TODO: normalize binds in shader
			int i = 0;
			for (RawDataV1.BoneBinding bind : tp.boneBindings) {
				attrBuff.put(bind.boneIndex);
			}
			for (; i < RawDataV1.MAX_NBR_BONEBINDINGS; ++i) {
				attrBuff.put((byte) 0xFF);
			}
			i = 0;
			for (RawDataV1.BoneBinding bind : tp.boneBindings) {
				attrBuff.putFloat(bind.bindingValue);
			}
			for (; i < RawDataV1.MAX_NBR_BONEBINDINGS; ++i) {
				attrBuff.putFloat(0.0F);
			}
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
		Matrix4f localToWorld = Utils.fromRotTrans(bone.rotation, bone.offset,
				1.0F);
		Matrix4f worldToLocal = Matrix4f.invert(localToWorld, null);
		localToWorld.store(matrixBuffer);
		worldToLocal.store(matrixBuffer);
	}
	/**
	 * Releases currently held OpenGL-objects (that are not handled by the GC)
	 */
	@Override
	public void finalize() {
		glDeleteVertexArrays(this.vao);
		glDeleteBuffers(this.boneMatricesBuff);
		glDeleteBuffers(this.boneTransformsBuff);
	}
}
