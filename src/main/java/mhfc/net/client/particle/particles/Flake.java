package mhfc.net.client.particle.particles;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;

import mhfc.net.client.particle.api.ParticleFactory;
import mhfc.net.client.particle.api.ParticleStitcher;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Flake extends Particle implements ParticleStitcher.IParticleSpriteReceiver {
	private int whichTex;
	private int swirlTick;
	private float spread;
	boolean swirls;

	public Flake(
			World world,
			double x,
			double y,
			double z,
			double vX,
			double vY,
			double vZ,
			double duration,
			double swirls) {
		super(world, x, y, z);
		particleScale = 3;
		whichTex = rand.nextInt(8);
		motionX = vX;
		motionY = vY;
		motionZ = vZ;
		particleMaxAge = (int) duration;
		swirlTick = rand.nextInt(120);
		spread = rand.nextFloat();
		this.swirls = swirls == 1;
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	public boolean isTransparent() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (swirls) {
			Vec3d motionVec = new Vec3d(motionX, motionY, motionZ).normalize();
			float yaw = (float) Math.atan2(motionVec.xCoord, motionVec.zCoord);
			float xzDistance = (float) motionVec.lengthVector();
			float pitch = (float) Math.atan2(motionVec.yCoord, xzDistance);
			float swirlRadius = 4f * (particleAge / (float) particleMaxAge) * spread;
			Point3d point = new Point3d(
					swirlRadius * Math.cos(swirlTick * 0.2),
					swirlRadius * Math.sin(swirlTick * 0.2),
					0);
			Matrix4d boxRotateX = new Matrix4d();
			Matrix4d boxRotateY = new Matrix4d();
			boxRotateX.rotX(pitch);
			boxRotateY.rotY(yaw);
			boxRotateX.transform(point);
			boxRotateY.transform(point);
			posX += point.x;
			posY += point.y;
			posZ += point.z;
			//            posY += swirlRadius * Math.cos(swirlTick * 0.2) * (Math.sqrt(1 - y * y)) * (Math.sqrt(1 - z * z));
		}

		if (particleAge >= particleMaxAge) {
			setExpired();
		}
		particleAge++;
		swirlTick++;
	}

	@Override
	public void renderParticle(
			VertexBuffer buffer,
			Entity entityIn,
			float partialTicks,
			float rotationX,
			float rotationZ,
			float rotationYZ,
			float rotationXY,
			float rotationXZ) {
		float var = (particleAge + partialTicks) / particleMaxAge;
		particleAlpha = (float) (1 - Math.exp(10 * (var - 1)));
		if (particleAlpha < 0.1)
			particleAlpha = 0.1f;

		float f = this.particleTextureIndexX / 16.0F;
		float f1 = f + 0.0624375F;
		float f2 = this.particleTextureIndexY / 16.0F;
		float f3 = f2 + 0.0624375F;
		float f4 = 0.1F * this.particleScale;

		if (this.particleTexture != null) {
			int row = (int) (whichTex / 4f);
			int column = whichTex % 4;
			float uRange = particleTexture.getMaxU() - particleTexture.getMinU();
			float spriteWidth = uRange / 4f;
			float pixelWidth = uRange / 32f;
			f = particleTexture.getMinU() + (column * spriteWidth);
			f1 = particleTexture.getMinU() + (spriteWidth * (column + 1)) - pixelWidth;
			f2 = particleTexture.getMinV() + (row * spriteWidth);
			f3 = particleTexture.getMinV() + (spriteWidth * (row + 1)) - pixelWidth;
		}

		float f5 = (float) (this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
		float f6 = (float) (this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
		float f7 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
		int i = this.getBrightnessForRender(partialTicks);
		int j = i >> 16 & 65535;
		int k = i & 65535;
		Vec3d[] avec3d = new Vec3d[] {
				new Vec3d(-rotationX * f4 - rotationXY * f4, -rotationZ * f4, -rotationYZ * f4 - rotationXZ * f4),
				new Vec3d(-rotationX * f4 + rotationXY * f4, rotationZ * f4, -rotationYZ * f4 + rotationXZ * f4),
				new Vec3d(rotationX * f4 + rotationXY * f4, rotationZ * f4, rotationYZ * f4 + rotationXZ * f4),
				new Vec3d(rotationX * f4 - rotationXY * f4, -rotationZ * f4, rotationYZ * f4 - rotationXZ * f4) };

		if (this.particleAngle != 0.0F) {
			float f8 = this.particleAngle + (this.particleAngle - this.prevParticleAngle) * partialTicks;
			float f9 = MathHelper.cos(f8 * 0.5F);
			float f10 = MathHelper.sin(f8 * 0.5F) * (float) cameraViewDir.xCoord;
			float f11 = MathHelper.sin(f8 * 0.5F) * (float) cameraViewDir.yCoord;
			float f12 = MathHelper.sin(f8 * 0.5F) * (float) cameraViewDir.zCoord;
			Vec3d vec3d = new Vec3d(f10, f11, f12);

			for (int l = 0; l < 4; ++l) {
				avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d))
						.add(avec3d[l].scale(f9 * f9 - vec3d.dotProduct(vec3d)))
						.add(vec3d.crossProduct(avec3d[l]).scale(2.0F * f9));
			}
		}

		buffer.pos(f5 + avec3d[0].xCoord, f6 + avec3d[0].yCoord, f7 + avec3d[0].zCoord).tex(f1, f3)
				.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k)
				.endVertex();
		buffer.pos(f5 + avec3d[1].xCoord, f6 + avec3d[1].yCoord, f7 + avec3d[1].zCoord).tex(f1, f2)
				.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k)
				.endVertex();
		buffer.pos(f5 + avec3d[2].xCoord, f6 + avec3d[2].yCoord, f7 + avec3d[2].zCoord).tex(f, f2)
				.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k)
				.endVertex();
		buffer.pos(f5 + avec3d[3].xCoord, f6 + avec3d[3].yCoord, f7 + avec3d[3].zCoord).tex(f, f3)
				.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k)
				.endVertex();
	}

	public static final class SFFactory
			extends
			ParticleFactory<Flake.SFFactory, Flake> {
		@SuppressWarnings("unchecked")
		public SFFactory() {
			super(
					Flake.class,
					ParticleStitcher.create(
							Flake.class,
							new ResourceLocation(ResourceInterface.main_modid, "particle/snowflake")));
		}

		@Override
		public Flake createParticle(ImmutableParticleArgs args) {
			if (args.data.length >= 5) {
				return new Flake(
						args.world,
						args.x,
						args.y,
						args.z,
						(double) args.data[0],
						(double) args.data[1],
						(double) args.data[2],
						(double) args.data[3],
						(double) args.data[4]);
			}
			if (args.data.length == 4) {
				return new Flake(
						args.world,
						args.x,
						args.y,
						args.z,
						(double) args.data[0],
						(double) args.data[1],
						(double) args.data[2],
						(double) args.data[3],
						0);
			} else if (args.data.length == 3) {
				return new Flake(
						args.world,
						args.x,
						args.y,
						args.z,
						(double) args.data[0],
						(double) args.data[1],
						(double) args.data[2],
						40,
						0);
			}
			return new Flake(args.world, args.x, args.y, args.z, 0, 0, 0, 40, 0);
		}
	}
}
