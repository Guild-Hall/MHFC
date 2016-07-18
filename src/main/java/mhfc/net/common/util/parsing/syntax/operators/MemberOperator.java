package mhfc.net.common.util.parsing.syntax.operators;

import mhfc.net.common.util.parsing.syntax.literals.HolderLiteral;
import mhfc.net.common.util.parsing.syntax.literals.IExpression;
import mhfc.net.common.util.parsing.syntax.literals.IdentifierLiteral;
import mhfc.net.common.util.parsing.valueholders.MemberAccess;

/**
 * "ex.name"
 *
 * @author WorldSEnder
 *
 */
public class MemberOperator implements IBinaryOperator<IExpression, IdentifierLiteral, IExpression> {

	@Override
	public IExpression with(IExpression valueV, IdentifierLiteral name) {
		return new HolderLiteral(c -> MemberAccess.makeMemberAccess(valueV.asValue(c), name.getLiteral()));
	}

}
