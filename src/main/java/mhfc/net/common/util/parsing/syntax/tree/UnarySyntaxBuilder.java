package mhfc.net.common.util.parsing.syntax.tree;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import mhfc.net.common.util.BitSetIterator;
import mhfc.net.common.util.parsing.syntax.ITerminalElement;

public class UnarySyntaxBuilder {
	/* package */ class ValueRegistration {
		public final Class<?> clazz;
		public final int valueID;
		public final BitSet equivalents;
		public final BitSet parents;

		public ValueRegistration(Class<?> clazz, int id, int parentID) {
			this.clazz = Objects.requireNonNull(clazz);
			this.valueID = id;
			this.equivalents = new BitSet();
			this.equivalents.set(id);
			this.parents = new BitSet();
			if (parentID != -1) {
				ValueRegistration parentReg = values.get(parentID);
				if (!parentReg.clazz.isAssignableFrom(clazz)) {
					throw new IllegalArgumentException(
							"Subclass " + clazz + " must be assignable to parent class " + parentReg.clazz);
				}
				this.parents.set(parentID);
				this.parents.or(parentReg.parents);
				for (int i : BitSetIterator.asIndexIterable(parents)) {
					values.get(i).equivalents.set(valueID);
				}
			}
		}
	}

	public enum ElementType {
		VALUE,
		PREFIX_OP,
		POSTFIX_OP;
	}

	/* package */ class OperatorRegistration {
		public final Class<?> clazz;
		public final int operatorID;
		public final boolean isPrefix;
		public final ElementType resultType;
		public final int resultID;

		// a bit is set at pos p if this operator has a lower precedence that
		// the operator with id p
		// Same means prefix > prefix
		public final BitSet comesAfterSameFixity;
		// Other means prefix > postfix
		public final BitSet comesAfterOtherFixity;
		// Same means prefix > prefix
		public final BitSet comesBeforeSameFixity;
		// Other means prefix > postfix
		public final BitSet comesBeforeOtherFixity;
		// The bitset of the ValueRegistration this op has been registered for
		public final BitSet acceptedValues;

		public OperatorRegistration(
				Class<?> clazz,
				int id,
				boolean isPrefix,
				int valueID,
				ElementType type,
				int resultID) {
			this.clazz = Objects.requireNonNull(clazz);
			this.operatorID = id;
			this.isPrefix = isPrefix;
			this.comesAfterOtherFixity = new BitSet();
			this.comesAfterSameFixity = new BitSet();
			this.comesBeforeOtherFixity = new BitSet();
			this.comesBeforeSameFixity = new BitSet();
			this.resultType = Objects.requireNonNull(type);
			this.resultID = resultID;
			ValueRegistration reg = values.get(valueID);
			// No copy
			this.acceptedValues = reg.equivalents;
		}

		private List<OperatorRegistration> otherRegistry() {
			return isPrefix ? postfixOps : prefixOps;
		}

		private List<OperatorRegistration> sameRegistry() {
			return isPrefix ? prefixOps : postfixOps;
		}

		public void addAfter(int otherID) {
			OperatorRegistration other = otherRegistry().get(otherID);

			comesAfterOtherFixity.set(otherID);
			comesAfterOtherFixity.or(other.comesAfterSameFixity);
			comesAfterSameFixity.or(other.comesAfterOtherFixity);
			for (int idx : BitSetIterator.asIndexIterable(comesBeforeSameFixity)) {
				sameRegistry().get(idx).comesAfterOtherFixity.set(otherID);
				sameRegistry().get(idx).comesAfterOtherFixity.or(other.comesAfterSameFixity);
				sameRegistry().get(idx).comesAfterSameFixity.or(other.comesAfterOtherFixity);
			}
			for (int idx : BitSetIterator.asIndexIterable(comesBeforeOtherFixity)) {
				otherRegistry().get(idx).comesAfterSameFixity.set(otherID);
				otherRegistry().get(idx).comesAfterSameFixity.or(other.comesAfterSameFixity);
				otherRegistry().get(idx).comesAfterOtherFixity.or(other.comesAfterOtherFixity);
			}

			other.comesBeforeOtherFixity.set(operatorID);
			other.comesBeforeOtherFixity.or(comesBeforeSameFixity);
			other.comesBeforeSameFixity.or(comesBeforeOtherFixity);
			for (int idx : BitSetIterator.asIndexIterable(other.comesAfterSameFixity)) {
				otherRegistry().get(idx).comesBeforeOtherFixity.set(operatorID);
				otherRegistry().get(idx).comesBeforeOtherFixity.or(comesBeforeSameFixity);
				otherRegistry().get(idx).comesBeforeSameFixity.or(comesBeforeOtherFixity);
			}
			for (int idx : BitSetIterator.asIndexIterable(other.comesAfterOtherFixity)) {
				sameRegistry().get(idx).comesBeforeSameFixity.set(operatorID);
				sameRegistry().get(idx).comesBeforeSameFixity.or(comesBeforeSameFixity);
				sameRegistry().get(idx).comesBeforeOtherFixity.or(comesBeforeOtherFixity);
			}
		}
	}

	private List<ValueRegistration> values;

	private List<OperatorRegistration> prefixOps;
	private List<OperatorRegistration> postfixOps;
	private boolean validated;

	public UnarySyntaxBuilder() {
		values = new ArrayList<>();
		prefixOps = new ArrayList<>();
		postfixOps = new ArrayList<>();
		validated = false;
	}

	/**
	 * When something changes and has to be revalidated
	 */
	protected void invalidate() {
		validated = false;
	}

	/* package */ List<ValueRegistration> getValues() {
		return Collections.unmodifiableList(values);
	}

	/* package */ List<OperatorRegistration> getPrefixOps() {
		return Collections.unmodifiableList(prefixOps);
	}

	/* package */ List<OperatorRegistration> getPostfixOps() {
		return Collections.unmodifiableList(postfixOps);
	}

	/**
	 * Registers clazz as a value.
	 *
	 * @param clazz
	 * @return the id to use in {@link AST#pushValue(int, ITerminalElement)}.
	 */
	public int registerTerminal(Class<?> clazz) {
		invalidate();
		int nextID = values.size();
		values.add(new ValueRegistration(clazz, nextID, -1));
		return nextID;
	}

	/**
	 * Registers a clazz that can be a substitute for the parentID, like a child-class.<br>
	 * The class of parentID must be a superclass of clazz.
	 *
	 * @param clazz
	 * @param parentID
	 * @return
	 */
	public int registerTerminal(Class<?> clazz, int parentID) {
		invalidate();
		int nextID = values.size();
		values.add(new ValueRegistration(clazz, nextID, parentID));
		return nextID;
	}

	protected OperatorRegistration registerOperator(
			Class<?> classOP,
			boolean isPrefix,
			int valueID,
			ElementType resultType,
			int resultID) {
		invalidate();
		List<OperatorRegistration> opReg = isPrefix ? prefixOps : postfixOps;
		int nextID = opReg.size();
		OperatorRegistration reg = new OperatorRegistration(classOP, nextID, isPrefix, valueID, resultType, resultID);
		opReg.add(reg);
		return reg;
	}

	/**
	 * Declares that prefix binds stronger than postfix if forPre is true.
	 *
	 * @param opId1
	 * @param opId2
	 */
	protected void declarePrecedence(int prefix, int postfix, boolean forPre) {
		invalidate();
		if (forPre) {
			postfixOps.get(postfix).addAfter(prefix);
		} else {
			prefixOps.get(prefix).addAfter(postfix);
		}
	}

	protected void revalidate() {
		List<SyntaxBuilder.OperatorRegistration> preOpReg = getPrefixOps();
		List<SyntaxBuilder.OperatorRegistration> postOpReg = getPostfixOps();

		for (UnarySyntaxBuilder.OperatorRegistration opReg : preOpReg) {
			if (opReg.comesAfterSameFixity.get(opReg.operatorID) || opReg.comesBeforeSameFixity.get(opReg.operatorID)) {
				throw new IllegalStateException("Circular precedence");
			}
		}
		for (UnarySyntaxBuilder.OperatorRegistration opReg : postOpReg) {
			if (opReg.comesAfterSameFixity.get(opReg.operatorID) || opReg.comesBeforeSameFixity.get(opReg.operatorID)) {
				throw new IllegalStateException("Circular precedence");
			}
		}
		for (UnarySyntaxBuilder.OperatorRegistration preOp : preOpReg) {
			for (UnarySyntaxBuilder.OperatorRegistration postOp : postOpReg) {
				if (!preOp.acceptedValues.intersects(postOp.acceptedValues)) {
					continue;
				}
				boolean preOpAfter = preOp.comesAfterOtherFixity.get(postOp.operatorID);
				boolean postOpAfter = postOp.comesAfterOtherFixity.get(preOp.operatorID);
				if (preOpAfter == postOpAfter) {
					throw new IllegalStateException(
							"Some operators are not ordered: " + preOp.operatorID + " " + postOp.operatorID);
				}
			}
		}
	}

	public UnarySyntaxBuilder validate() {
		if (validated) {
			return this;
		}
		revalidate();
		validated = true;
		return this;
	}

}
