package mhfc.net.client.particle.api;

import mhfc.net.client.particle.EnumParticles;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class ParticleStitcher<T> {
	public static <T extends Particle & IParticleSpriteReceiver> ParticleStitcher create(
			Class<T> cls,
			ResourceLocation[] textures) {
		return new ParticleStitcher<T>(textures);
	}

	public static <T extends Particle & IParticleSpriteReceiver> ParticleStitcher create(
			Class<T> cls,
			ResourceLocation texture) {
		return create(cls, new ResourceLocation[] { texture });
	}

	private final ResourceLocation[] textures;
	private TextureAtlasSprite[] loadedSprites;

	private ParticleStitcher(ResourceLocation[] textures) {
		this.textures = textures;
	}

	public ResourceLocation[] getTextures() {
		return this.textures;
	}

	public void setSprites(TextureAtlasSprite[] sprites) {
		this.loadedSprites = sprites;
	}

	public TextureAtlasSprite[] getSprites() {
		return this.loadedSprites;
	}

	public static interface IParticleSpriteReceiver {
		default void setStitchedSprites(TextureAtlasSprite[] sprites) {
			((Particle) this).setParticleTexture(sprites[0]);
		}
	}

	public enum Stitcher {
		INSTANCE;

		@SubscribeEvent
		public void onTextureStitch(TextureStitchEvent.Pre event) {
			TextureMap map = event.getMap();
			EnumParticles[] particles = EnumParticles.values();
			for (EnumParticles particle : particles) {
				ParticleStitcher stitcher = particle.getFactory().getStitcher();
				if (stitcher != null) {
					ResourceLocation[] textures = stitcher.getTextures();
					TextureAtlasSprite[] sprites = new TextureAtlasSprite[textures.length];
					for (int i = 0; i < textures.length; i++) {
						sprites[i] = map.registerSprite(textures[i]);
					}
					stitcher.setSprites(sprites);
				}
			}
		}
	}
}