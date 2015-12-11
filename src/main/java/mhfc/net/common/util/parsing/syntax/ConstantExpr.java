package mhfc.net.common.util.parsing.syntax;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.expressions.Constant;
import mhfc.net.common.util.parsing.syntax.BasicSequences.FloatMatch;
import mhfc.net.common.util.parsing.syntax.BasicSequences.IntMatch;
import mhfc.net.common.util.parsing.syntax.BasicSequences.StringMatch;
import mhfc.net.common.util.parsing.syntax.ISyntaxRule.ISyntaxRuleInstance;

public class ConstantExpr implements ISyntaxRuleInstance {
	public static ISyntaxRule<ConstantExpr> factory = ConstantExpr::new;

	private final IntMatch intMatcher = IntMatch.factory.newInstance();
	private final FloatMatch fpMatcher = FloatMatch.factory.newInstance();
	private final StringMatch stringMatcher = StringMatch.factory.newInstance();
	private Holder value;

	@Override
	public int match(CharSequence toMatch) {
		value = null; // Reset
		int matched;
		matched = stringMatcher.match(toMatch);
		if (matched > 0) {
			value = Holder.valueOf(stringMatcher.getRaw().get());
			return matched;
		}
		matched = intMatcher.match(toMatch);
		if (matched > 0) {
			try {
				int constantValue = Integer.parseInt(intMatcher.getRaw().get());
				value = Holder.valueOf(constantValue);
			} catch (NumberFormatException mfe) {
				// Int too large to fit
				value = Holder.failedComputation(mfe);
			}
			return matched;
		}
		matched = fpMatcher.match(toMatch);
		if (matched > 0) {
			float fpValue = Float.parseFloat(fpMatcher.getRaw().get());
			value = Holder.valueOf(fpValue);
			return matched;
		}
		return 0;
	}

	public Constant representation() {
		if (this.value == null)
			throw new IllegalStateException("Didn't match anything");
		return new Constant(this.value);
	}
}
