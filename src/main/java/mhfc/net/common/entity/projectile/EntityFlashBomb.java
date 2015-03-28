package mhfc.net.common.entity.projectile;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.gui.MHFCGuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityFlashBomb extends EntityThrowable {

	public static final int EXPLOSIVE_TIMER = 40;
	public static final int EXPLOSION_DURATION = 60;
	public static final int PHASE_IN_DURATION = 5;

	public static final int FALL_OFF_BEGIN = 90;
	public static final int FALL_OFF_END = 200;

	private int ticksToExplode;
	private boolean exploded;
	private boolean done;
	private boolean registered;

	private float alphaMultiplier;

	public EntityFlashBomb(World par1World, double x, double y, double z) {
		super(par1World, x, y, z);
		ticksToExplode = EXPLOSIVE_TIMER;
		exploded = false;
		done = false;
		registered = false;
		alphaMultiplier = 1.0f;
	}

	public EntityFlashBomb(World par1World) {
		super(par1World);
		ticksToExplode = EXPLOSIVE_TIMER;
		exploded = false;
		done = false;
		registered = false;
		alphaMultiplier = 1.0f;
	}

	public EntityFlashBomb(World par1World, EntityLivingBase thrower) {
		super(par1World, thrower);
		ticksToExplode = EXPLOSIVE_TIMER;
		exploded = false;
		done = false;
		registered = false;
		alphaMultiplier = 1.0f;
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {
		explode();
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		ticksToExplode--;
		if (ticksToExplode == 0) {
			explode();
		}
		if (exploded && ticksToExplode < -EXPLOSION_DURATION) {
			finishExplosion();
		}
	}

	private void finishExplosion() {
		done = true;
		setDead();
	}

	@Override
	public void setDead() {
		if (registered) {
			MinecraftForge.EVENT_BUS.unregister(this);
			registered = false;
		}
		super.setDead();
	}

	private void explode() {
		if (exploded)
			return;
		if (this.worldObj.isRemote) { // Blind local player
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			float viewX = (float) -Math.sin(player.rotationYaw * Math.PI / 180), //
			viewZ = (float) Math.cos(player.rotationYaw * Math.PI / 180), //
			viewY = (float) Math.sin(player.rotationPitch * Math.PI / 180);
			Vector3f viewVec = new Vector3f(viewX, viewY, viewZ);
			Vector3f dirVec = new Vector3f((float) (posX - player.posX),
				(float) (posY - player.posY), (float) (posZ - player.posZ));

			alphaMultiplier = getAlphaMultiplier(viewVec, dirVec);

			MinecraftForge.EVENT_BUS.register(this);
			registered = true;
		}
		exploded = true;
		ticksToExplode = 0;
	}

	private float getAlphaMultiplier(Vector3f viewDirection,
		Vector3f entityDirection) {

		float distance = entityDirection.length();
		double angle = getAngle(viewDirection, entityDirection);

		if (angle > 0.5 || distance > FALL_OFF_END) // Don't blind if looking
													// away
			return 0;
		float distanceMult = 1;
		if (distance > FALL_OFF_BEGIN) {
			distanceMult -= (distance - FALL_OFF_BEGIN)
				/ (FALL_OFF_END - FALL_OFF_BEGIN);
		}
		if (angle < 0.25) // Blind fully if looking into it
			return distanceMult;
		angle -= 0.25;
		return (float) ((0.25 - angle) * (0.25 - angle)) * 16 * distanceMult;
		// Apply some sort of quadratic function if you look from the corner of
		// your eye
	}

	/**
	 * Gets the angle as value between 0 and 1
	 * 
	 * @return
	 */
	private float getAngle(Vector3f viewDirection, Vector3f entityDirection) {
		float dot = Vector3f.dot(viewDirection, entityDirection);
		float lengthProd = viewDirection.length() * entityDirection.length();
		dot = Math.abs(dot / lengthProd);
		return (float) (Math.acos(dot) / Math.PI);
	}

	@SubscribeEvent
	public void onGuiDraw(RenderGameOverlayEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();
		if (event.type == ElementType.HOTBAR && exploded && !done && mc != null) {
			float alpha = getAlphaFromExplosionDuration(-ticksToExplode)
				* alphaMultiplier;
			GL11.glColor4f(1, 1, 1, alpha);
			MHFCMain.logger.info("Draw with alpha " + alpha);
			int screenWidth = MHFCGuiUtil.minecraftWidth(mc), screenHeight = MHFCGuiUtil
				.minecraftWidth(mc);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex3f(0, 0, 50);
			GL11.glVertex3f(0, screenHeight, 50);
			GL11.glVertex3f(screenWidth, screenHeight, 50);
			GL11.glVertex3f(screenWidth, 0, 50);
			GL11.glEnd();

			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
	}

	private float getAlphaFromExplosionDuration(int dur) {
		if (dur < 0 || dur > EXPLOSION_DURATION)
			return 0;
		if (dur < PHASE_IN_DURATION) {
			return (dur + 1) / (float) PHASE_IN_DURATION;
		}
		float phaseOutDuration = EXPLOSION_DURATION - PHASE_IN_DURATION;
		dur -= PHASE_IN_DURATION;
		return (phaseOutDuration - dur) / phaseOutDuration;
	}
}
