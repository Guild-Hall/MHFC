package mhfc.net.client.model.mhfcmodel.data;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;

import java.util.List;
import java.util.Map;

import mhfc.net.client.model.mhfcmodel.AnimationInformation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;

public class ModelData40 implements IModelData {
	public static final int VS_POSITION_LOCATION = 0;
	public static final int VS_NORMAL_LOCATION = 1;
	public static final int VS_TEXCOORDS_LOCATION = 2;
	public static final int VS_TRANSFORM_INDICES_LOCATION = 3;
	public static final int VS_TRANSFORM_VALUES_LOCATION = 4;
	public static final int VS_ATTR_SIZE = 64;

	public static final int UNIFORM_BONES_STATIC_BLOCK_BINDING = 0;
	public static final int UNIFORM_BONES_DYNAMIC_BLOCK_BINDING = 1;

	public static class Part {
		public int pointsBufferName;
		public int indexBufferName;
		public int indexCount;

		public void render() {
			glBindBuffer(GL_ARRAY_BUFFER, pointsBufferName);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferName);
			glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
		}
	}
	/**
	 * Is frozen
	 */
	public final ImmutableMap<String, Part> partMap;
	/**
	 * Immutable and ordered
	 */
	public final ImmutableSortedSet<Long> boneHashes;
	public final int boneStaticMatrixBufferName;

	public final int boneTransformBufferName;

	public ModelData40(RawDataV1 dataFrom) {
		Map<String, Part> buildingPartMap = null;
		List<Long> boneHashes = null;
		// TODO: actually read from data given
		boneStaticMatrixBufferName = glGenBuffers();
		glBindBuffer(GL_UNIFORM_BUFFER, boneStaticMatrixBufferName);
		// glBufferData(GL_UNIFORM_BUFFER, boneData, GL_STATIC_DRAW);
		boneTransformBufferName = 0;

		this.partMap = ImmutableMap.copyOf(buildingPartMap);
		this.boneHashes = ImmutableSortedSet.copyOf(boneHashes);
	}

	/**
	 * Sets vertex attributes and static buffers
	 */
	protected void prepareRender() {
		// Enable attributes
		glEnableVertexAttribArray(VS_POSITION_LOCATION);
		glEnableVertexAttribArray(VS_NORMAL_LOCATION);
		glEnableVertexAttribArray(VS_TEXCOORDS_LOCATION);
		glEnableVertexAttribArray(VS_TRANSFORM_INDICES_LOCATION);
		glEnableVertexAttribArray(VS_TRANSFORM_VALUES_LOCATION);
		// Set position
		glVertexAttribPointer(VS_POSITION_LOCATION, 3, GL_FLOAT, false,
				VS_ATTR_SIZE, 0);
		glVertexAttribPointer(VS_NORMAL_LOCATION, 3, GL_FLOAT, false,
				VS_ATTR_SIZE, 3 * 4);
		glVertexAttribPointer(VS_TEXCOORDS_LOCATION, 2, GL_FLOAT, false,
				VS_ATTR_SIZE, 6 * 4);
		glVertexAttribPointer(VS_TRANSFORM_INDICES_LOCATION, 4,
				GL_UNSIGNED_INT, false, VS_ATTR_SIZE, 8 * 4);
		glVertexAttribPointer(VS_TRANSFORM_VALUES_LOCATION, 4, GL_UNSIGNED_INT,
				false, VS_ATTR_SIZE, 12 * 4);
		// glBindBufferBase(GL_UNIFORM_BUFFER, bindingPoint, buffer);
	}
	/**
	 * Binds the transform from the currently present attackinformation
	 *
	 * @param info
	 *            the information to read from
	 */
	protected void bindBoneTransforms(AnimationInformation info) {
		// TODO: bind bone transform
	}
	/**
	 * Reverts actions of {@link #prepareRender()}
	 */
	protected void finishRender() {
		// Disable attributes
		glDisableVertexAttribArray(VS_POSITION_LOCATION);
		glDisableVertexAttribArray(VS_NORMAL_LOCATION);
		glDisableVertexAttribArray(VS_TEXCOORDS_LOCATION);
		glDisableVertexAttribArray(VS_TRANSFORM_INDICES_LOCATION);
		glDisableVertexAttribArray(VS_TRANSFORM_VALUES_LOCATION);
	}
	/**
	 * Renders all parts that don't get filtered out by the
	 * {@link AnimationInformation#shouldDisplayPart(String, float)} method
	 *
	 * @param info
	 */
	public void renderFiltered(AnimationInformation info, float subFrame) {
		this.prepareRender();
		this.bindBoneTransforms(info);

		// Filter parts to render
		// TODO: enable streaming approach once java 8 is enabled
		// Stream<String> partsStream =
		// this.modelData.partMap.keySet().stream();
		// partsStream
		// .filter((part) -> info.shouldDisplayPart(part, subFrame));
		for (String part : this.partMap.keySet()) {
			Part p = this.partMap.get(part);
			if (p == null)
				continue;
			if (info == null || info.shouldDisplayPart(part, subFrame))
				p.render();
		}
		// partsStream.forEach(this::renderPart);

		this.finishRender();
	}
}
