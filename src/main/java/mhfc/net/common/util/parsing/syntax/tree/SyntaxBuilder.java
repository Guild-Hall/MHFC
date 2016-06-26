package mhfc.net.common.util.parsing.syntax.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mhfc.net.common.util.parsing.syntax.operators.BinaryOPWrapper;
import mhfc.net.common.util.parsing.syntax.operators.BinaryOPWrapper.BinaryWrapperResult;
import mhfc.net.common.util.parsing.syntax.operators.IBinaryOperator;
import mhfc.net.common.util.parsing.syntax.operators.IOperator;

public class SyntaxBuilder extends UnarySyntaxBuilder {
	private enum PublicOpType {
		UNARY_PREFIX,
		UNARY_POSTFIX,
		BINARY;
	}

	/* package */ class OperatorRemap {
		public final int publicID;
		public final PublicOpType type;
		public final Class<?> clazz;
		private final int prefixID;
		// Only used for binary
		private final int postfixID;

		public OperatorRemap(boolean isPrefix, int publicID, int realID, Class<?> clazz) {
			this.type = isPrefix ? PublicOpType.UNARY_PREFIX : PublicOpType.UNARY_POSTFIX;
			this.publicID = publicID;
			this.prefixID = isPrefix ? realID : -1;
			this.postfixID = isPrefix ? -1 : realID;
			this.clazz = clazz;
		}

		public OperatorRemap(int publicID, int realIDPre, int realIDPost, Class<?> clazz) {
			this.type = PublicOpType.BINARY;
			this.publicID = publicID;
			this.prefixID = realIDPre;
			this.postfixID = realIDPost;
			this.clazz = clazz;
		}

		public boolean isDominantPrefix() {
			return type == PublicOpType.UNARY_PREFIX;
		}

		public int getDominantID() {
			return isDominantPrefix() ? prefixID : postfixID;
		}

		public int getPrefixID() {
			return prefixID;
		}

		public int getPostfixID() {
			return postfixID;
		}
	}

	private List<OperatorRemap> remapPublicIDs;

	public SyntaxBuilder() {
		super();
		remapPublicIDs = new ArrayList<>();
	}

	/* package */ List<OperatorRemap> getRemapPublicIDs() {
		return Collections.unmodifiableList(remapPublicIDs);
	}

	public <O extends IOperator<?, ?>> int registerUnaryOperator(
			Class<O> classOP,
			boolean isPrefix,
			int valueID,
			int resultID) {
		return registerUnaryOperator(classOP, isPrefix, valueID, ElementType.VALUE, resultID);
	}

	/**
	 * Registers a unary operator. Note that no checks regarding the types of R and V is done.
	 *
	 * @param classOP
	 * @param isPrefix
	 * @param valueID
	 *            argument's value id
	 * @param resultID
	 *            result's value id
	 * @return
	 */
	public <O extends IOperator<?, ?>> int registerUnaryOperator(
			Class<O> classOP,
			boolean isPrefix,
			int valueID,
			ElementType type,
			int resultID) {
		if (type != ElementType.VALUE) {
			OperatorRemap remap = remapPublicIDs.get(resultID);
			resultID = remap.getDominantID();
		}
		int internal = registerOperator(classOP, isPrefix, valueID, type, resultID).operatorID;
		int external = remapPublicIDs.size();
		remapPublicIDs.add(new OperatorRemap(isPrefix, external, internal, classOP));
		return external;
	}

	public <O extends IBinaryOperator<?, ?, ?>> int registerBinaryOperator(
			Class<O> classOP,
			int valueLeftID,
			int valueRightID,
			int resultID,
			boolean isLeftAssociative) {
		OperatorRegistration intermediate = registerOperator(
				BinaryWrapperResult.class,
				true,
				valueRightID,
				ElementType.VALUE,
				resultID);
		OperatorRegistration initial = registerOperator(
				BinaryOPWrapper.class,
				false,
				valueLeftID,
				ElementType.PREFIX_OP,
				intermediate.operatorID);
		declarePrecedence(intermediate.operatorID, initial.operatorID, isLeftAssociative);
		int externalID = remapPublicIDs.size();
		remapPublicIDs.add(new OperatorRemap(externalID, intermediate.operatorID, initial.operatorID, classOP));
		return externalID;
	}

	/**
	 * Declares precedence of op1 over op2
	 *
	 * @param opID1
	 * @param opID2
	 */
	public void declarePrecedence(int opID1, int opID2) {
		OperatorRemap op1 = remapPublicIDs.get(opID1);
		OperatorRemap op2 = remapPublicIDs.get(opID2);

		int prefix1 = op1.getPrefixID();
		int prefix2 = op2.getPrefixID();

		int postfix1 = op1.getPostfixID();
		int postfix2 = op2.getPostfixID();
		if (prefix1 != -1 && postfix2 != -1) {
			declarePrecedence(prefix1, postfix2, true);
		}
		if (prefix2 != -1 && postfix1 != -1) {
			declarePrecedence(prefix2, postfix1, false);
		}
	}

	/**
	 * Declares precedence of binary operators in cases of e.g. "5 + 6 - 4". leftToRight means this evaluates as
	 * "(5 + 6) - 4", leftToRight false means this is evaluated as "5 + (6 - 4)"
	 *
	 * @param binaryOpID1
	 * @param binaryOpID2
	 * @param leftToRight
	 */
	public void declareSamePrecedence(int binaryOpID1, int binaryOpID2, boolean leftToRight) {
		OperatorRemap op1 = remapPublicIDs.get(binaryOpID1);
		OperatorRemap op2 = remapPublicIDs.get(binaryOpID2);

		int prefix1 = op1.getPrefixID();
		int prefix2 = op2.getPrefixID();

		int postfix1 = op1.getPostfixID();
		int postfix2 = op2.getPostfixID();
		if (prefix1 == -1 || postfix2 == -1 || prefix2 == -1 || postfix1 == -1) {
			throw new IllegalArgumentException("Both ops must be binary operators");
		}
		declarePrecedence(prefix1, postfix2, leftToRight);
		declarePrecedence(prefix2, postfix1, leftToRight);
	}

	@Override
	public SyntaxBuilder validate() {
		super.validate();
		return this;
	}

	public AST newParseTree() {
		return new AST(this);
	}
}
