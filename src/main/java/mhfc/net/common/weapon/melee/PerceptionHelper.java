package mhfc.net.common.weapon.melee;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class PerceptionHelper {
	private static Minecraft mc = FMLClientHandler.instance().getClient();

	/**
	 * This method will return the entitly or tile the mouse is hovering over up to the distance provided. It is more or
	 * less a copy/paste of the default minecraft version.
	 *
	 * @return
	 */
	public static RayTraceResult getMouseOver(float frame, float dist) {
		Entity renderViewEntity = mc.getRenderViewEntity();
		if (renderViewEntity == null) {
			return null;
		}
		if (mc.world == null) {
			return null;
		}
		double var2 = dist;
		RayTraceResult mop = renderViewEntity.rayTrace(var2, frame);
		double calcdist = var2;
		Vec3d pos = renderViewEntity.getPositionVector();
		var2 = calcdist;
		if (mop != null) {
			calcdist = mop.hitVec.distanceTo(pos);
		}

		Vec3d lookvec = renderViewEntity.getLook(frame);
		Vec3d var8 = pos.addVector(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2);
		Entity pointedEntity = null;
		float var9 = 1.0F;
		List<Entity> list = mc.world.getEntitiesWithinAABBExcludingEntity(
				renderViewEntity,
				renderViewEntity.getEntityBoundingBox()
						.expand(lookvec.x * var2, lookvec.y * var2, lookvec.z * var2)
						.expand(var9, var9, var9));
		double d = calcdist;

		for (Entity entity : list) {
			if (entity.canBeCollidedWith()) {
				float bordersize = entity.getCollisionBorderSize();
				AxisAlignedBB aabb = entity.getEntityBoundingBox().expand(bordersize, bordersize, bordersize);
				RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);

				if (aabb.contains(pos)) {
					if (0.0D < d || d == 0.0D) {
						pointedEntity = entity;
						d = 0.0D;
					}
				} else if (mop0 != null) {
					double d1 = pos.distanceTo(mop0.hitVec);

					if (d1 < d || d == 0.0D) {
						pointedEntity = entity;
						d = d1;
					}
				}
			}
		}

		if (pointedEntity != null && (d < calcdist || mop == null)) {
			return new RayTraceResult(pointedEntity);
		}
		return mop;
	}

}
