package mhfc.net.common.ai.general;

import java.util.Random;

import mhfc.net.common.ai.general.WeightedPick.WeightedItem;
import mhfc.net.common.util.function.FloatSupplier;

public class WeightUtils {

	private WeightUtils() {}

	public static float random(Random rng, float maximum) {
		return rng.nextFloat() * maximum;
	}

	public static float withFallback(FloatSupplier primary, FloatSupplier... fallbacks) {
		float weight = primary.getAsFloat();
		for (FloatSupplier fallback : fallbacks) {
			if (!WeightedItem.isDontSelect(weight)) {
				return weight;
			}
			weight = fallback.getAsFloat();
		}
		return weight;
	}
}
