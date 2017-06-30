package mhfc.net.common.ai.general.provider.adapters;

import java.util.Objects;

import mhfc.net.common.ai.general.provider.simple.IDamageCalculator;
import mhfc.net.common.ai.general.provider.simple.IDamageProvider;

public class DamageAdapter implements IDamageProvider {

	private IDamageCalculator damageCalculator;

	public DamageAdapter(IDamageCalculator calculator) {
		this.damageCalculator = Objects.requireNonNull(calculator);
	}

	@Override
	public IDamageCalculator getDamageCalculator() {
		return damageCalculator;
	}

}
