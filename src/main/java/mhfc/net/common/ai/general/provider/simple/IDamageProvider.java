package mhfc.net.common.ai.general.provider.simple;

import java.util.Objects;

import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;

public interface IDamageProvider {

	public IDamageCalculator getDamageCalculator();

	public static class DamageAdapter implements IDamageProvider {
		IDamageCalculator calculator;

		public DamageAdapter(IDamageCalculator calculator) {
			this.calculator = Objects.requireNonNull(calculator);
		}

		@Override
		public IDamageCalculator getDamageCalculator() {
			return calculator;
		}
	}
}
