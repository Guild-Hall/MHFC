package mhfc.net.client.particle.particles;

import org.lwjgl.opengl.GL11;

import mhfc.net.client.particle.ParticleFactory;
import mhfc.net.client.particle.ParticleTextureStitcher;
import mhfc.net.client.particle.ParticleTextureStitcher.IParticleSpriteReceiver;
import mhfc.net.client.render.RenderHelper;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleAnimated extends Particle implements IParticleSpriteReceiver {

	/**
	 * An attempt to apply advance Particle FX into Minecraft each setup are well documented
	 * @author Heltrato
	 */

	public static String fxTexture;
	public int cols, rows, numSprites;
	public float scale;
	public RenderHelper.RenderType renderType;
	public float maxScale;
	public float minScale;
	public float scaleFactor;
	public boolean softScaling;
	public boolean variations = false;
	public int variationFrame = 0;
	public double motionDamping = 0.9800000190734863D;
	public float randomness = 0.2F;
	public int random_rot = 0;
	public float rot_offset;
	public int delay = 0;

	public float R1;
	public float G1;
	public float B1;
	public float A1;
	public float R2;
	public float G2;
    public float B2;
    public float A2;
	public float dynamicColorStart;
	public DynamicColorType dynamicColorType = DynamicColorType.NONE;

	/** First Constructor is protected thus similar as set default */

	protected ParticleAnimated(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.canCollide = true;
		this.particleGravity = 0.0F;

	}

	/** The vanilla public constructor of this class. */

	public ParticleAnimated(
			World worldIn,
			double xCoordIn,
			double yCoordIn,
			double zCoordIn,
			double xSpeedIn,
			double ySpeedIn,
			double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		this.canCollide = false;
		this.particleGravity = 0.0F;

	}

	/** The modified public constructor of this class. Commonly use in all event instances */
	
	public ParticleAnimated(
			World world,
			double posX,
			double posY,
			double posZ,
			float scale,
			String fxTexture,
			int cols,
			int rows,
			int numSprites,
			RenderHelper.RenderType renderType) {
		super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		this.canCollide = false;
		this.particleGravity = 0.0F;
		ParticleAnimated.fxTexture = "textures/particle/" + fxTexture;
		this.cols = cols;
		this.rows = rows;
		this.numSprites = numSprites;
		this.particleScale = scale;
		this.renderType = renderType;
		this.particleRed = 1.0F;
		this.particleGreen = 1.0F;
		this.particleBlue = 1.0F;
		this.particleMaxAge = (10 + this.rand.nextInt(5));
		setMaxAge(20);
		setScaleIncrease(2.5F, 5.0F, 1.0F, true);
		setMotionDamping(0.9D);
		setRandomness(0.33F);
		setRandomRotation(true);

	}
	
	/**
	 * Returns on what effect layer should be.
	 */
	@Override
	public int getFXLayer() {
		return 2;
	}
	
	@Override
	public int getBrightnessForRender(float delta) {
		return 240 | super.getBrightnessForRender(delta) & 0xFF0000;
	}
	
	/**
	 * Updates the tick of the particle. Just a trial and error so fucking hard.
	 * 
	 */

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.delay <= 0) {
			if (this.particleAge++ >= this.particleMaxAge) {
				setExpired();
			}

			this.motionY -= 0.04D * this.particleGravity;
			move(this.motionX, this.motionY, this.motionZ);
			this.motionX *= this.motionDamping;
			this.motionY *= this.motionDamping;
			this.motionZ *= this.motionDamping;

			if (this.onGround) {
				this.motionX *= 0.699999988079071D;
				this.motionZ *= 0.699999988079071D;
			}

			if (this.dynamicColorType != DynamicColorType.NONE) {
				float prog = this.particleAge / this.particleMaxAge;
				if (prog >= this.dynamicColorStart) {
					prog = (prog - this.dynamicColorStart) * (1.0F / (1.0F - this.dynamicColorStart));
				} else {
					prog = 0.0F;
				}
				switch (this.dynamicColorType) {
				case LINEAR:
				default:
					break;
				case TENT:
					prog *= 2.0F;
					if (prog > 1.0F)
						prog = 2.0F - prog;
					break;
				case SINUS:
					prog = (float) Math.sin(6.283185307179586D * prog);
					break;
				case SMOOTH:
					prog = (float) Math.sin(3.141592653589793D * prog);
					break;
				case FAST:
					prog = (float) Math.sqrt(prog);
				}

				this.particleRed = (this.R1 * (1.0F - prog) + this.R2 * prog);
				this.particleGreen = (this.G1 * (1.0F - prog) + this.G2 * prog);
				this.particleBlue = (this.B1 * (1.0F - prog) + this.B2 * prog);
				this.particleAlpha = (this.A1 * (1.0F - prog) + this.A2 * prog);
			}
		} else {
			this.delay -= 1;
		}
	}

	public void renderParticle(
			VertexBuffer vertexbuffer,
			float partialTickTime,
			float rotX,
			float rotXZ,
			float rotZ,
			float rotYZ,
			float rotXY) {
		if (this.delay > 0) {
			return;
		}
		Tessellator tessellator = Tessellator.getInstance();
		float prog = this.particleAge / this.particleMaxAge;
		int currentFrame = 0;
		if (this.variations) {
			currentFrame = this.variationFrame;
		} else {
			currentFrame = (int) (this.numSprites * prog);
		}
		if (currentFrame < this.numSprites) {
			if (this.maxScale != 0.0F) {
				float p = (float) (this.softScaling ? Math.sqrt(prog) : prog);
				this.particleScale = Math.min(
						this.minScale + (this.maxScale - this.minScale) * p * (1.0F / this.scaleFactor),
						this.maxScale);
			}
			tessellator.draw();
			GL11.glPushAttrib(8192);
			if (this.renderType != RenderHelper.RenderType.SOLID) {
				GL11.glEnable(3042);
				GL11.glEnable(3008);
			}
			if (this.renderType == RenderHelper.RenderType.ALPHA) {
				GL11.glBlendFunc(770, 771);
			} else if (this.renderType == RenderHelper.RenderType.ADDITIVE) {
				GL11.glBlendFunc(770, 1);
			}
			if (this.renderType != RenderHelper.RenderType.ALPHA_SHADED) {
				RenderHelper.enableFXLighting();
			}
			Minecraft.getMinecraft().renderEngine
					.bindTexture(new ResourceLocation(ResourceInterface.main_modid, fxTexture));
			vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
			;
			float fscale = 0.1F * this.particleScale;
			float fPosX = (float) (this.prevPosX + (this.posX - this.prevPosX) * partialTickTime - interpPosX);
			float fPosY = (float) (this.prevPosY + (this.posY - this.prevPosY) * partialTickTime - interpPosY);
			float fPosZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * partialTickTime - interpPosZ);
			int col = currentFrame % this.cols;
			int row = currentFrame / this.cols;
			float u = 1.0F / this.cols;
			float v = 1.0F / this.rows;
			float U1 = col * u;
			float V1 = row * v;
			float U2 = (col + 1) * u;
			float V2 = (row + 1) * v;
			float vd;
			float ua;
			float va;
			float ub;
			float vb;
			float uc;
			float vc;
			float ud;
			switch (this.random_rot) {
			case 1:
				ua = U1;
				va = V2;
				ub = U2;
				vb = V2;
				uc = U2;
				vc = V1;
				ud = U1;
				vd = V1;
				break;
			case 2:
				ua = U1;
				va = V1;
				ub = U1;
				vb = V2;
				uc = U2;
				vc = V2;
				ud = U2;
				vd = V1;
				break;
			case 3:
				ua = U2;
				va = V1;
				ub = U1;
				vb = V1;
				uc = U1;
				vc = V2;
				ud = U2;
				vd = V2;
				break;
			case 0:
			default:
				ua = U2;
				va = V2;
				ub = U2;
				vb = V1;
				uc = U1;
				vc = V1;
				ud = U1;
				vd = V2;
			}
			GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
			GlStateManager.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);

			vertexbuffer.pos(
					fPosX - rotX * fscale - rotYZ * fscale,
					fPosY - rotXZ * fscale,
					fPosZ - rotZ * fscale - rotXY * fscale).tex(ua,
					va);
			vertexbuffer.pos(
					fPosX - rotX * fscale + rotYZ * fscale,
					fPosY + rotXZ * fscale,
					fPosZ - rotZ * fscale + rotXY * fscale).tex(ub,
					vb);
			vertexbuffer.pos(
					fPosX + rotX * fscale + rotYZ * fscale,
					fPosY + rotXZ * fscale,
					fPosZ + rotZ * fscale + rotXY * fscale).tex(
					uc,
					vc);
			vertexbuffer.pos(
					fPosX + rotX * fscale - rotYZ * fscale,
					fPosY - rotXZ * fscale,
					fPosZ + rotZ * fscale - rotXY * fscale).tex(
					ud,
							vd);

			tessellator.draw();

			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/particle/particles.png"));
			GL11.glPopAttrib();
			vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
			;

			if (this.renderType != RenderHelper.RenderType.ALPHA_SHADED) {
				RenderHelper.disableFXLighting();
			}
		}
	}

	@Override
	public void setMaxAge(int maxAge) {
		this.particleMaxAge = Math.round(getRandom(maxAge));
	}

	public void setGravity(float gravity) {
		this.particleGravity = gravity;
	}

	public void setScaleIncrease(float minScale, float maxScale, float scaleFactor, boolean softScaling) {
		this.minScale = getRandom(minScale);
		this.particleScale = minScale;
		this.maxScale = getRandom(maxScale);
		this.scaleFactor = scaleFactor;
		this.softScaling = softScaling;
	}

	public void setDynamicColor(
			float R1,
			float G1,
			float B1,
			float A1,
			float R2,
			float G2,
			float B2,
			float A2,
			DynamicColorType type) {
		this.R1 = (R1 / 255.0F);
		this.G1 = (G1 / 255.0F);
		this.B1 = (B1 / 255.0F);
		this.A1 = (A1 / 255.0F);
		this.R2 = (R2 / 255.0F);
		this.G2 = (G2 / 255.0F);
		this.B2 = (B2 / 255.0F);
		this.A2 = (A2 / 255.0F);
		this.dynamicColorType = type;
	}

	public void setRandomRotation(float rot_min, float rot_max) {
		this.rot_offset = ((rot_min + this.rand.nextFloat() * (rot_max - rot_min)) * 3.1415927F / 180.0F);
	}

	public void setRandomRotation(boolean randomRotation) {
		if (!randomRotation) {
			this.random_rot = 0;
		} else {
			this.random_rot = this.rand.nextInt(4);
		}
	}

	public void setRandomness(float randomness) {
		this.randomness = randomness;
	}

	public void setMotionDamping(double damping) {
		this.motionDamping = damping;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	private float getRandom(float value) {
		return value * (1.0F - this.randomness) + 2.0F * value * this.randomness * this.rand.nextFloat();
	}

	public static final class Factory extends ParticleFactory<Factory, ParticleAnimated> {
		@SuppressWarnings("unchecked")
		public Factory() {
			super(
					ParticleAnimated.class,
					ParticleTextureStitcher
							.create(
									ParticleAnimated.class,
									new ResourceLocation(
											ResourceInterface.main_modid,
											ParticleAnimated.fxTexture)));
		}

		@Override
		public ParticleAnimated createParticle(ImmutableParticleArgs args) {
			if (args.data.length == 2) {
				return new ParticleAnimated(
						args.world,
						args.x,
						args.y,
						args.z);
			} else {
				return new ParticleAnimated(
						args.world,
						args.x,
						args.y,
						args.z,
						(double) args.data[0],
						(double) args.data[1],
						(double) args.data[2]);
			}
		}
	}

	/**
	 * An addon enum that distinguish the color blending for the particle
	 * 
	 * 
	 */
	
	public static enum DynamicColorType {
		LINEAR,  TENT,  SINUS,  SMOOTH,  FAST,  NONE;
		private DynamicColorType() {} }
		public void setDynamicColorStart(float f) { this.dynamicColorStart = f; }

	public void setFramesAsVariations(boolean b) {
		this.variations = b;
		if (b)
			this.variationFrame = this.rand.nextInt(this.numSprites);
	}
	

	

}
