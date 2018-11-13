package mhfc.net.client.render.block;

import java.util.Objects;

import com.github.worldsender.mcanm.client.IRenderPass;
import com.github.worldsender.mcanm.client.mcanmmodel.IModel;
import com.github.worldsender.mcanm.client.model.IRenderPassInformation;
import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;
import com.github.worldsender.mcanm.common.animation.IAnimation;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public abstract class RenderTileEntity<T extends TileEntity> extends TileEntitySpecialRenderer<T> {
	private class RenderPass implements IRenderPass {
		private IRenderPassInformation passInformation;

		public void setPassInformation(IRenderPassInformation passInformation) {
			this.passInformation = Objects.requireNonNull(passInformation);
		}

		@Override
		public void bindTexture(ResourceLocation resLoc) {
			RenderTileEntity.this.bindTexture(resLoc);
		}

		@Override
		public ResourceLocation getActualResourceLocation(String slot) {
			return passInformation.getActualResourceLocation(slot);
		}

		@Override
		public IAnimation getAnimation() {
			return passInformation.getAnimation();
		}

		@Override
		public float getFrame() {
			return passInformation.getFrame();
		}

		@Override
		public boolean shouldRenderPart(String part) {
			return passInformation.shouldRenderPart(part);
		}
	}

	private final IModel model;
	private final RenderPassInformation cachedPassInformation = new RenderPassInformation();
	private final RenderPass renderPass = this.new RenderPass();

	public RenderTileEntity(IModel model) {
		this.model = Objects.requireNonNull(model);
	}

	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage,float alpha) {
		cachedPassInformation.reset();
		renderPass
				.setPassInformation(preRenderCallback(cachedPassInformation, te, x, y, z, partialTicks, destroyStage));
		model.render(renderPass);
	}

	protected abstract IRenderPassInformation preRenderCallback(
			RenderPassInformation cache,
			T tile,
			double x,
			double y,
			double z,
			float partialTicks,
			int destroyStage);
}
