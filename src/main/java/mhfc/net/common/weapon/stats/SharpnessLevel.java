package mhfc.net.common.weapon.stats;

/**
 * Sharpness for weapons.
 *
 * @author WorldSEnder
 *
 */
public enum SharpnessLevel {
	// Order is important
	Red(0.5f, 0.25f, 255, 0, 0),
	Orange(0.75f, 0.5f, 255, 146, 0),
	Yellow(1f, 0.75f, 255, 255, 0),
	Green(1.05f, 1f, 0, 211, 0),
	Blue(1.2f, 1.0625f, 0, 0, 255),
	White(1.32f, 1.125f, 255, 255, 255),
	Purple(1.45f, 1.2f, 107, 0, 255);

	public final float rawDamageMultiplier;
	public final float elementalDamageMultiplier;
	public final int color;

	private SharpnessLevel(float rawDamageMult, float elementalMultiplier, int color) {
		this.rawDamageMultiplier = rawDamageMult;
		this.elementalDamageMultiplier = elementalMultiplier;
		this.color = color;
	}

	private SharpnessLevel(float damageMult, float elementalMultiplier, int r, int g, int b) {
		this(damageMult, elementalMultiplier, r << 16 | g << 8 | b);
	}
}
