package mhfc.net.common.entity.monster;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.github.worldsender.mcanm.client.model.mcanmmodel.animation.IAnimation;
import com.github.worldsender.mcanm.client.model.mcanmmodel.data.RenderPassInformation;
import com.github.worldsender.mcanm.client.model.util.AnimationLoader;
import com.github.worldsender.mcanm.client.renderer.IAnimatedObject;

// This is a really simple test Entity
public class EntityTest extends EntityLiving implements IAnimatedObject {
	// Load the animation from the stored file
	private static IAnimation snap = AnimationLoader
			.loadAnimation(new ResourceLocation(
					"mhfc:models/Rathalos/testanim.mhanm"));

	public EntityTest(World world) {
		super(world);
		this.boundingBox.setBounds(-0.5f, 0, -0.5f, 0.5f, 1f, 0.5f);
	}

	@Override
	public RenderPassInformation preRenderCallback(float subFrame,
			RenderPassInformation info) {
		return info.setAnimation(snap).setFrame(this.ticksExisted % 90);
	}
}
