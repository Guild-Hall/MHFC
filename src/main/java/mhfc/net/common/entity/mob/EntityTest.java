package mhfc.net.common.entity.mob;

import mhfc.net.client.model.mhfcmodel.animation.IAnimatedObject;
import mhfc.net.client.model.mhfcmodel.animation.IAnimation;
import mhfc.net.client.model.mhfcmodel.animation.stored.AnimationRegistry;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

public class EntityTest extends EntityLiving implements IAnimatedObject {
	private static IAnimation animation = AnimationRegistry
			.loadAnimation(new ResourceLocation(
					"mhfc:models/Rathalos/testanim.mhanm"));

	public EntityTest(World world) {
		super(world);
		this.boundingBox.setBounds(-0.5f, 0, -0.5f, 0.5f, 1f, 0.5f);
	}

	@Override
	public IAnimation getCurrentAnimation() {
		return animation;
	}

	@Override
	public int getCurrentFrame() {
		return this.ticksExisted % 100;
	}

	@Override
	public Predicate<String> getPartPredicate(float subFrame) {
		return RENDER_ALL;
	}
}
