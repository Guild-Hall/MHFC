package mhfc.net.common.index;

public enum AttributeModifiers {

	;
	/**
	 * An additive attribute modifier: When added to an attribute with current value v and amount a, the result is a
	 * value of v + a
	 */
	public static final int ADDITIVE = 0;
	/**
	 * An additive multiplicative modifier: When added to an attribute with a current value v, a base value b and amount
	 * a, the result is a value of v + b * a
	 */
	public static final int ADDITIVE_MULT = 1;
	/**
	 * A multiplicative modifier: When added to an attribute with a current value v, a base value b and amount a, the
	 * result is a value of v * (1 + a)
	 */
	public static final int MULTIPLICATIVE = 2;



}
