package mhfc.net.common.entity.mob;

import mhfc.net.client.model.mhfcmodel.animation.IAnimatedObject;
import mhfc.net.client.model.mhfcmodel.animation.IAnimation;
import mhfc.net.client.model.mhfcmodel.animation.stored.AnimationRegistry;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

// This is a really simple test Entity
public class EntityTest extends EntityLiving implements IAnimatedObject {
	// Load the animation from the stored file
	private static IAnimation snap = AnimationRegistry
			.loadAnimation(new ResourceLocation(
					"mhfc:models/Rathalos/testanim.mhanm"));

	public EntityTest(World world) {
		super(world);
		this.boundingBox.setBounds(-0.5f, 0, -0.5f, 0.5f, 1f, 0.5f);
	}

	@Override
	public IAnimation getCurrentAnimation() {
		// Return the current animation, depends on the current attack/ AI
		return snap;
	}

	@Override
	public int getCurrentFrame() {
		// Returns the frame in the animation (time since attack started?)
		return this.ticksExisted % 50;
	}

	@Override
	public Scale getScale() {
		return NO_SCALE;
	}

	@Override
	public Predicate<String> getPartPredicate(float subFrame) {
		// Can decide not to show some parts
		return RENDER_ALL;
	}
}
