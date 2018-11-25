package mhfc.net.client.particle.paint;

import io.netty.buffer.ByteBuf;
import mhfc.net.common.item.ItemColor;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.Color;

@SideOnly(Side.CLIENT)
public class ParticlePaint extends Particle implements IEntityAdditionalSpawnData {

	private static final int texMin = 160;
	private static final int texMax = 167;

	private ItemColor color;

	public ParticlePaint(World worldIn, ItemColor color, double posXIn, double posYIn, double posZIn) {
		super(
				worldIn,
				posXIn,
				posYIn,
				posZIn,
				worldIn.rand.nextFloat() * 0.25F,
				-1 * Math.abs(2.5F * worldIn.rand.nextFloat()),
				worldIn.rand.nextFloat() * 0.25F);
		setParticleTextureIndex((int) Math.floor(worldIn.rand.nextDouble() * (texMax - texMin)) + texMin);
		this.particleMaxAge = (int) (1.5F * (8.0F / (worldIn.rand.nextFloat() * 1.8F + 0.2F)));
		this.particleScale = 2.0F;
		this.color = color;

		if (color == null) {
			this.color = ItemColor.WHITE;
		}

		Color floatColor = this.color.getColor();
		//Note: This says set RBG color, but the order is actually RGB.
		//Typo on MC's end.
		setRBGColorF(floatColor.getRed() / 255.0F, floatColor.getGreen() / 255.0F, floatColor.getBlue() / 255.0F);
		setAlphaF(0.8F);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(color.getMetadata());
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		color = ItemColor.byMetadata(additionalData.readInt());
	}

}
