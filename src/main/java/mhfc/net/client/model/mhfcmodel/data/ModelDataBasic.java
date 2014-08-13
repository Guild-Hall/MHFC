package mhfc.net.client.model.mhfcmodel.data;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex4f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mhfc.net.client.model.mhfcmodel.Utils;
import mhfc.net.client.model.mhfcmodel.animation.IAnimation;
import mhfc.net.client.model.mhfcmodel.animation.IAnimation.BoneTransformation;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1.TesselationPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.google.common.base.Predicate;

public class ModelDataBasic implements IModelData {
	protected static Minecraft mc = Minecraft.getMinecraft();
	private static class Part {
		private static class Point {
			protected static class Vertex {
				private Vector4f pos;
				private Vector3f norm;
				private Vector2f uv;
				public Vertex(Vector4f pos, Vector3f norm, Vector2f uv) {
					this.pos = pos;
					this.norm = norm;
					this.uv = uv;
				}
				/**
				 * Issues glVertex and glNormal and glTexCoord
				 */
				public void render() {
					glNormal3f(norm.x, norm.y, norm.z);
					glTexCoord2f(uv.x, uv.y);
					glVertex4f(pos.x, pos.y, pos.z, pos.w);
				}
				/**
				 * Adds the other Vertex to this one. This will not touch this
				 * Vertex's UV-coordinates
				 *
				 * @param other
				 */
				public void add(Vertex other) {
					Vector4f.add(this.pos, other.pos, this.pos);
					Vector3f.add(this.norm, other.norm, this.norm);
				}
			}

			/**
			 * Bindings with a (absolute) strength of less than this won't get
			 * recognized
			 */
			public static final float epsilon = 0.0F;

			protected static boolean isApplicable(RawDataV1.BoneBinding binding) {
				return Math.abs(binding.bindingValue) > epsilon
						&& binding.boneIndex != 0xFF;
			}

			protected Vertex vert;
			protected Point(Vector3f pos, Vector3f norm, Vector2f uv) {
				this.vert = new Vertex(new Vector4f(pos.x, pos.y, pos.z, 1F),
						norm, uv);
			}
			/**
			 * Renders this point, already transformed
			 *
			 * @param bones
			 *            the models bones
			 */
			public void render(Bone[] bones, BoneTransformation[] currTransforms) {
				this.vert.render();
			}
			/**
			 * Constructs a bone from the {@link TesselationPoint} given. This
			 * is implemented in the factory style to efficiently handle bones
			 * without Bindings
			 *
			 * @param data
			 *            the point to construct from
			 * @return the constructed point
			 */
			public static Point from(RawDataV1.TesselationPoint data) {
				boolean isBound = false;
				for (RawDataV1.BoneBinding bind : data.boneBindings) {
					if (isApplicable(bind)) {
						isBound = true;
						break;
					}
				}
				if (isBound)
					return new BoundPoint(data.coords, data.normal,
							data.texCoords, data.boneBindings);
				return new Point(data.coords, data.normal, data.texCoords);
			}
		}

		private static class BoundPoint extends Point {
			private static class Binding {
				// Used as a buffer, doesn't requite to always create new
				// temporaries. Prevents parallelization, though
				private static Vector4f posBuff;
				private static Vector4f normBuff;
				static {
					posBuff = new Vector4f();
					normBuff = new Vector4f();
				}
				private byte boneIndex;
				private float strength;
				public Binding(byte index, float strengh) {
					this.boneIndex = index;
					this.strength = strengh;
				}
				public Vertex transform(Bone[] bones,
						BoneTransformation[] transforms, Vertex vert) {
					posBuff.set(vert.pos.x, vert.pos.y, vert.pos.z, 1.0F);
					normBuff.set(vert.norm.x, vert.norm.y, vert.norm.z, 0.0F);
					Bone boundTo = bones[this.boneIndex & 0xFF];
					Matrix4f boundMatrix = transforms[this.boneIndex & 0xFF]
							.asMatrix();
					// Transform matrix
					Matrix4f globalTransform = new Matrix4f();
					boundTo.toLocal(globalTransform, globalTransform);
					Matrix4f.mul(boundMatrix, globalTransform, globalTransform);
					boundTo.toGlobal(globalTransform, globalTransform);
					// Transform points with matrix
					Matrix4f.transform(globalTransform, posBuff, posBuff);
					// Inverted transposed for normal
					Matrix4f.invert(globalTransform, globalTransform);
					Matrix4f.transpose(globalTransform, globalTransform);
					// Transform normal
					Matrix4f.transform(globalTransform, normBuff, normBuff);
					// Final result
					Vector4f position = new Vector4f(posBuff);
					Vector3f normal = new Vector3f(normBuff.x, normBuff.y,
							normBuff.z);
					position.scale(this.strength);
					normal.scale(this.strength);
					return new Vertex(position, normal, new Vector2f(vert.uv));
				}
				public void normalize(float sum) {
					this.strength /= sum;
				}
			}

			private static List<Binding> normalizeAndCompress(
					RawDataV1.BoneBinding[] data) {
				float sum = 0.0F;
				List<Binding> collected = new ArrayList<>(4);
				for (RawDataV1.BoneBinding boneBind : data) {
					if (!isApplicable(boneBind))
						continue;
					collected.add(new Binding(boneBind.boneIndex,
							boneBind.bindingValue));
					sum += Math.abs(boneBind.bindingValue);
				}
				for (Binding bind : collected) {
					bind.normalize(sum);
				}
				return collected;
			}

			private List<Binding> binds;

			public BoundPoint(Vector3f pos, Vector3f norm, Vector2f uv,
					RawDataV1.BoneBinding[] readBinds) {
				super(pos, norm, uv);
				// This can be assumed to at least be size 1
				this.binds = normalizeAndCompress(readBinds);
			}

			@Override
			public void render(Bone[] bones, BoneTransformation[] currTransforms) {
				Iterator<Binding> bindIter = this.binds.listIterator();
				Vertex base = bindIter.next().transform(bones, currTransforms,
						this.vert); // Assumed not to throw
				while (bindIter.hasNext()) {
					Binding curr = bindIter.next();
					base.add(curr.transform(bones, currTransforms, this.vert));
				}
				base.render();
			}
		}

		private final Point[] pointsList;
		private final ResourceLocation resLocation;
		private final short[] indices; // Unsigned
		private final String name;

		public Part(RawDataV1.ModelPart data) {
			Point[] points = new Point[data.points.length];
			int idx = 0;
			for (RawDataV1.TesselationPoint point : data.points) {
				points[idx++] = Point.from(point);
			}
			this.pointsList = points;
			this.resLocation = new ResourceLocation(
					data.material.resLocationRaw);
			this.indices = data.indices;
			this.name = data.name;
		}

		public void render(Bone[] bones, BoneTransformation[] currTransforms) {
			mc.renderEngine.bindTexture(this.resLocation);
			glBegin(GL_TRIANGLES);
			for (short idx : this.indices) {
				this.pointsList[idx & 0xFFFF].render(bones, currTransforms);
			}
			glEnd();
		}

		public String getName() {
			return this.name;
		}
	}

	private static class Bone {
		private static class ParentedBone extends Bone {
			private Bone parent;
			private byte parentByte; // Never 0xFF

			protected ParentedBone(Matrix4f lTp, Matrix4f pTl, byte parent,
					String name) {
				super(lTp, pTl, name);
				this.parentByte = parent;
			}

			@Override
			public void setParent(Bone[] allBones) {
				this.parent = allBones[this.parentByte & 0xFF];
			}

			@Override
			public Matrix4f toLocal(Matrix4f src, Matrix4f dst) {
				// Handles dst == null
				dst = this.parent.toLocal(src, dst);
				return Matrix4f.mul(this.parentToLocal, dst, dst);
			}

			@Override
			public Matrix4f toGlobal(Matrix4f src, Matrix4f dst) {
				// Handles dst == null
				dst = Matrix4f.mul(localToParent, src, dst);
				return this.parent.toGlobal(dst, dst);
			}
		}
		protected Matrix4f localToParent;
		protected Matrix4f parentToLocal;
		private String name;

		protected Bone(Matrix4f lTp, Matrix4f pTl, String name) {
			this.localToParent = lTp;
			this.parentToLocal = pTl;
			this.name = name;
		}

		public void setParent(Bone[] allBones) {} // Nothing to do here

		public Matrix4f toLocal(Matrix4f src, Matrix4f dst) {
			return Matrix4f.mul(this.parentToLocal, src, dst);
		}

		public Matrix4f toGlobal(Matrix4f src, Matrix4f dst) {
			return Matrix4f.mul(this.localToParent, src, dst);
		}

		public String getName() {
			return this.name;
		}

		public static Bone fromData(RawDataV1.Bone data) {
			Matrix4f localToParent = Utils.fromRotTrans(data.rotation,
					data.offset, 1.0F);
			Matrix4f parentToLocal = Matrix4f.invert(localToParent, null);
			if (data.parent == 0xFF)
				return new ParentedBone(localToParent, parentToLocal,
						data.parent, data.name);
			return new Bone(localToParent, parentToLocal, data.name);

		}
	}
	private final Part[] parts; // May have Random order
	private final Bone[] bones; // In correct order

	public ModelDataBasic(RawDataV1 data) {
		Part[] parts = new Part[data.parts.size()];
		Bone[] bones = new Bone[data.bones.size()];
		Iterator<RawDataV1.ModelPart> partIt = data.parts.iterator();
		for (int i = 0; i < data.parts.size(); ++i) {
			parts[i] = new Part(partIt.next());
		}
		Iterator<RawDataV1.Bone> boneIt = data.bones.iterator();
		for (int i = 0; i < data.bones.size(); ++i) {
			bones[i] = Bone.fromData(boneIt.next());
		}
		for (Bone bone : bones) {
			bone.setParent(bones);
		}
		this.parts = parts;
		this.bones = bones;
	}

	private BoneTransformation[] getTransforms(IAnimation anim, int frame,
			float subFrame) {
		BoneTransformation[] transforms = new BoneTransformation[this.bones.length];
		for (int i = 0; i < this.bones.length; ++i) {
			if (anim == null) {
				transforms[i] = BoneTransformation.identity;
				continue;
			}
			Bone currBone = this.bones[i];
			BoneTransformation transform = anim.getCurrentTransformation(
					currBone.getName(), frame, subFrame);
			transforms[i] = transform != null
					? transform
					: BoneTransformation.identity;
		}
		return transforms;
	}

	@Override
	public void renderAll(IAnimation currAnimation, int frame, float subFrame) {
		BoneTransformation[] transforms = getTransforms(currAnimation, frame,
				subFrame);
		for (Part part : this.parts) {
			part.render(this.bones, transforms);
		}
	}

	@Override
	public void renderFiltered(Predicate<String> filter,
			IAnimation currAnimation, int frame, float subFrame) {
		BoneTransformation[] transforms = getTransforms(currAnimation, frame,
				subFrame);
		for (Part part : this.parts) {
			if (filter.apply(part.getName()))
				part.render(this.bones, transforms);
		}
	}
}
