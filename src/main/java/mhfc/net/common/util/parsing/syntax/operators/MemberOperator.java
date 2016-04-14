package mhfc.net.common.util.parsing.syntax.operators;

import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.syntax.literals.IdentifierLiteral;
import mhfc.net.common.util.parsing.valueholders.MemberAccess;

/**
 * "ex.name"
 *
 * @author WorldSEnder
 *
 */
public class MemberOperator implements IBinaryOperator<IValueHolder, IdentifierLiteral, IValueHolder> {

	@Override
	public IValueHolder with(IValueHolder valueV, IdentifierLiteral name) {
		return MemberAccess.makeMemberAccess(valueV, name.getLiteral());
	}

}
