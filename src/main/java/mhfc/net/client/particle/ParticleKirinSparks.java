package mhfc.net.client.particle;

import mhfc.net.common.index.ResourceInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleKirinSparks extends Particle {
	private static final ResourceLocation texture = new ResourceLocation(ResourceInterface.particle_kirinsfx_tex);
	float field_70569_a;

	public ParticleKirinSparks(
			World par1World,
			double par2,
			double par4,
			double par6,
			double par8,
			double par10,
			double par12) {
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		float f = 2.5F;
		this.motionX *= 0.10000000149011612D;
		this.motionY *= 0.10000000149011612D;
		this.motionZ *= 0.10000000149011612D;
		this.motionX += par8;
		this.motionY += par10;
		this.motionZ += par12;
		this.particleRed = this.particleGreen = this.particleBlue = 1.0F
				- (float) (Math.random() * 0.30000001192092896D);
		this.particleScale *= 0.75F;
		this.particleScale *= f;
		this.field_70569_a = this.particleScale;
		this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.3D));
		this.particleMaxAge = (int) (this.particleMaxAge * f);
	}

	@Override
	public void renderParticle(
			VertexBuffer worldRenderer,
			Entity viewEntity,
			float partialTicks,
			float rotX,
			float rotZ,
			float rotYZ,
			float rotXY,
			float rotXZ) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		float f6 = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;

		if (f6 < 0.0F) {
			f6 = 0.0F;
		}

		if (f6 > 1.0F) {
			f6 = 1.0F;
		}

		this.particleScale = this.field_70569_a * f6;
		super.renderParticle(worldRenderer, viewEntity, partialTicks, rotX, rotZ, rotYZ, rotXY, rotXZ);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		this.motionY -= 0.04D * this.particleGravity;
		this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);

		super.onUpdate();

		EntityPlayer entityplayer = this.world.getClosestPlayer(posX, posY, posZ, 2.0D, false);
		// TODO: wtf is that logic right here?
		if (entityplayer != null && this.posY > entityplayer.getEntityBoundingBox().minY) {
			this.posY += (entityplayer.getEntityBoundingBox().minY - this.posY) * 0.2D;
			this.motionY += (entityplayer.motionY - this.motionY) * 0.2D;
			this.setPosition(this.posX, this.posY, this.posZ);
		}
	}
}
