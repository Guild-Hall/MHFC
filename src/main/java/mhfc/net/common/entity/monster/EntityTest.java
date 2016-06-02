package mhfc.net.common.entity.monster;

import java.util.Optional;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;
import com.github.worldsender.mcanm.client.renderer.IAnimatedObject;
import com.github.worldsender.mcanm.common.CommonLoader;
import com.github.worldsender.mcanm.common.animation.IAnimation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

// This is a really simple test Entity
public class EntityTest extends EntityLiving implements IAnimatedObject {
	// Load the animation from the stored file
	private static IAnimation snap = CommonLoader
			.loadAnimation(new ResourceLocation("mhfc:models/Rathalos/testanim.mhanm"));

	public EntityTest(World world) {
		super(world);
		this.boundingBox.setBounds(-0.5f, 0, -0.5f, 0.5f, 1f, 0.5f);
	}

	@Override
	public RenderPassInformation preRenderCallback(float subFrame, RenderPassInformation info) {
		return info.setAnimation(Optional.of(snap)).setFrame(this.ticksExisted % 90);
	}
}
