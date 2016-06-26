package mhfc.net.common.util.parsing.syntax.tree;

import java.util.List;
import java.util.Objects;

import mhfc.net.common.util.parsing.syntax.operators.BinaryOPWrapper;
import mhfc.net.common.util.parsing.syntax.operators.IBinaryOperator;
import mhfc.net.common.util.parsing.syntax.operators.IOperator;

public class AST extends UnaryAST {
	// map userOp_id -> opid, highest bit = isPrefix
	private final int[] publicIDToRealOpID;
	// map value_id -> Class of value, for type safety
	private final Class<?>[] valueClasses;
	private final Class<?>[] publicClasses;

	/* package */ AST(SyntaxBuilder builder) {
		super(builder.validate());
		List<SyntaxBuilder.ValueRegistration> valueReg = builder.getValues();
		List<SyntaxBuilder.OperatorRemap> publicRemap = builder.getRemapPublicIDs();

		publicIDToRealOpID = new int[publicRemap.size()];
		publicClasses = new Class[publicRemap.size()];
		valueClasses = new Class[valueReg.size()];
		for (SyntaxBuilder.ValueRegistration val : valueReg) {
			valueClasses[val.valueID] = val.clazz;
		}
		for (SyntaxBuilder.OperatorRemap remap : publicRemap) {
			publicIDToRealOpID[remap.publicID] = toRemappedOP(remap.isDominantPrefix(), remap.getDominantID());
			publicClasses[remap.publicID] = remap.clazz;
		}
	}

	protected AST(AST structure) {
		super(structure);
		this.publicIDToRealOpID = structure.publicIDToRealOpID;
		this.valueClasses = structure.valueClasses;
		this.publicClasses = structure.publicClasses;
	}

	@Override
	public void pushValue(int valueId, Object val) throws IllegalStateException {
		super.pushValue(valueId, valueClasses[valueId].cast(val));
	}

	public void pushUnaryOperator(int publicID, IOperator<?, ?> operator) throws IllegalStateException {
		publicClasses[publicID].cast(operator);
		Objects.requireNonNull(operator);

		int op = publicIDToRealOpID[publicID];
		boolean isPrefix = isRemappedPrefixOP(op);
		int realID = fromRemappedOP(op);

		pushOperator(isPrefix, realID, operator);
	}

	public void pushBinaryOperator(int publicID, IBinaryOperator<?, ?, ?> operator) throws IllegalStateException {
		Class<?> clazz = publicClasses[publicID];
		Objects.requireNonNull(operator);
		clazz.cast(operator);

		int op = publicIDToRealOpID[publicID];
		boolean isPrefix = isRemappedPrefixOP(op);
		int realID = fromRemappedOP(op);
		pushOperator(isPrefix, realID, BinaryOPWrapper.wrap(operator));
	}

	private int toRemappedOP(boolean isPrefix, int realID) {
		return (realID & 0x7FFFFFFF) | (isPrefix ? 0x80000000 : 0);
	}

	private boolean isRemappedPrefixOP(int remapped) {
		return (remapped & 0x80000000) != 0;
	}

	private int fromRemappedOP(int remapped) {
		return (remapped & 0x7FFFFFFF);
	}

	/**
	 * Returns a new, fresh AST (with no values or operators pushed) but with the same syntax as this AST.<br>
	 * This is so that you can dispose of the {@link SyntaxBuilder} after finishing building it and still make fresh
	 * syntax trees.
	 *
	 * @return
	 */
	public AST makeFreshTree() {
		return new AST(this);
	}
}
