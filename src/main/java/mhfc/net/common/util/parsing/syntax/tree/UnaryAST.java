package mhfc.net.common.util.parsing.syntax.tree;

import java.util.List;
import java.util.Objects;
import java.util.Stack;

import mhfc.net.common.util.parsing.syntax.operators.IOperator;
import net.minecraft.command.SyntaxErrorException;

public class UnaryAST {
	private static abstract class Node {

		/**
		 * Add in depth first nature. Return true if no more upward nodes should be added.
		 *
		 * @param stack
		 * @return
		 */
		public boolean addDisputableNodes(Stack<IntermediateNode> stack) {
			return false;
		}

		public void addOpenPostfixOps(Stack<IntermediateNode> stack) {}

		public void addOpenPrefixOps(Stack<IntermediateNode> stack) {}
	}

	private static interface IValueNode {
		int getValueType();

		Object getVal();
	}

	private static interface IOperatorNode {
		boolean isPrefixOP();

		int getOpType();

		IOperator<?, ?> getOp();
	}

	/**
	 * A value provided directly by the parser.
	 */
	private static class ValueLeaf extends Node implements IValueNode {
		private final int valueType;
		private final Object value;

		public ValueLeaf(int valueType, Object value) {
			this.valueType = valueType;
			this.value = value;
		}

		@Override
		public int getValueType() {
			return valueType;
		}

		@Override
		public Object getVal() {
			return value;
		}

		@Override
		public String toString() {
			return Objects.toString(value);
		}
	}

	/**
	 * An operator provided directly by the parser.
	 */
	private static class OperatorLeaf extends Node implements IOperatorNode {
		private final int opType;
		private final boolean isPrefix;
		private final IOperator<?, ?> op;

		public OperatorLeaf(int opType, boolean isPrefix, IOperator<?, ?> op) {
			this.opType = opType;
			this.isPrefix = isPrefix;
			this.op = op;
		}

		@Override
		public boolean isPrefixOP() {
			return isPrefix;
		}

		@Override
		public int getOpType() {
			return opType;
		}

		@Override
		public IOperator<?, ?> getOp() {
			return op;
		}

		@Override
		public String toString() {
			return Objects.toString(op);
		}
	}

	private static abstract class IntermediateNode extends Node {
		private Node valueNode;
		private IValueNode valueNodeAsVal;

		private final Node opNode;
		private final IOperatorNode opNodeAsOp;

		public <T extends Node & IOperatorNode> IntermediateNode(T opNode) {
			this(opNode, null);
		}

		public <T extends Node & IOperatorNode, Q extends Node & IValueNode> IntermediateNode(T opNode, Q valNode) {
			this.opNode = Objects.requireNonNull(opNode);
			this.opNodeAsOp = opNode;
			setValueNode(valNode);
		}

		public Node getValueNode() {
			return valueNode;
		}

		public IValueNode getValue() {
			return valueNodeAsVal;
		}

		public <T extends Node & IValueNode> void setValueNode(T newNode) {
			this.valueNode = newNode;
			this.valueNodeAsVal = newNode;
		}

		public Node getOperatorNode() {
			return opNode;
		}

		public IOperatorNode getOperator() {
			return opNodeAsOp;
		}

		private boolean isPrefix() {
			return opNodeAsOp.isPrefixOP();
		}

		public Object compute() {
			if (this.valueNode == null) {
				throw new IllegalStateException("Unfinished tree");
			}
			return compute(this.opNodeAsOp.getOp(), this.valueNodeAsVal.getVal());
		}

		@SuppressWarnings("unchecked")
		private <T> Object compute(IOperator<T, ?> op, Object object) {
			return op.with((T) object);
		}

		/**
		 * Return the right most non-empty child node
		 *
		 * @return
		 */
		private Node getRightMostNode() {
			return isPrefix() ? getValue() != null ? getValueNode() : getOperatorNode() : getOperatorNode();
		}

		/**
		 * Return the left most non-empty child node
		 *
		 * @return
		 */
		private Node getLeftMostNode() {
			return isPrefix() ? getOperatorNode() : getValue() != null ? getValueNode() : getOperatorNode();
		}

		@Override
		public boolean addDisputableNodes(Stack<IntermediateNode> stack) {
			if (getValue() == null) {
				if (isPrefix()) {
					getOperatorNode().addDisputableNodes(stack);
				}
				// Cancel when an incomplete op is encountered
				return true;
			}
			// Traverse down the right path
			boolean childrenResult = getRightMostNode().addDisputableNodes(stack);
			if (!childrenResult && isPrefix()) {
				// Add to the bottom of the stack
				stack.add(0, this);
			}
			return childrenResult;
		}

		@Override
		public void addOpenPostfixOps(Stack<IntermediateNode> stack) {
			if (!isPrefix() && getValue() == null) {
				stack.add(this);
			}
			getLeftMostNode().addOpenPostfixOps(stack);
		}

		@Override
		public void addOpenPrefixOps(Stack<IntermediateNode> stack) {
			if (isPrefix() && getValue() == null) {
				stack.add(this);
			}
			getRightMostNode().addOpenPrefixOps(stack);
		}
	}

	/**
	 * A value that is the result of an operator application
	 */
	private static class ValueIntermediate extends IntermediateNode implements IValueNode {
		private int resultType;

		public <T extends Node & IOperatorNode> ValueIntermediate(T opNode, int resultType) {
			super(opNode);
			this.resultType = resultType;
		}

		@Override
		public int getValueType() {
			return resultType;
		}

		@Override
		public Object getVal() {
			return compute();
		}

		@Override
		public String toString() {
			return getOperator() + "(" + getValue() + ")";
		}
	}

	/**
	 * An operator that is the result of an operator application.
	 */
	private static class OperatorIntermediate extends IntermediateNode implements IOperatorNode {
		private final int opType;
		private final boolean isPrefix;

		public <T extends Node & IOperatorNode> OperatorIntermediate(T opNode, boolean isPrefix, int resultOpType) {
			super(opNode);
			this.isPrefix = isPrefix;
			this.opType = resultOpType;
		}

		@Override
		public boolean isPrefixOP() {
			return isPrefix;
		}

		@Override
		public int getOpType() {
			return opType;
		}

		@Override
		public IOperator<?, ?> getOp() {
			return (IOperator<?, ?>) compute();
		}

		@Override
		public String toString() {
			return getOperator() + "(" + getValue() + ")";
		}
	}

	private class Tree {
		// Doesn't include the top of the tree
		private Stack<IntermediateNode> depthFirstDisputableValues;
		private Stack<IntermediateNode> depthFirstOpenPostfixOps;
		private Stack<IntermediateNode> depthFirstOpenPrefixOps;

		private Node topNode;
		private IValueNode topNodeAsVal;

		public Tree(boolean isPrefix, int opID, IOperator<?, ?> op) {
			this(makeNodesFor(isPrefix, opID, op));
		}

		public Tree(int valueID, Object value) {
			this(new ValueLeaf(valueID, value));
		}

		private <T extends Node & IValueNode> Tree(T topNode) {
			this.topNode = topNode;
			this.topNodeAsVal = topNode;
			depthFirstDisputableValues = new Stack<>();
			depthFirstOpenPostfixOps = new Stack<>();
			depthFirstOpenPrefixOps = new Stack<>();
			reparseDisputableValues();
			reparsePostfixOps();
			reparsePrefixOps();
		}

		private void reparseDisputableValues() {
			this.depthFirstDisputableValues.clear();
			this.topNode.addDisputableNodes(depthFirstDisputableValues);
		}

		private void reparsePostfixOps() {
			this.depthFirstOpenPostfixOps.clear();
			this.topNode.addOpenPostfixOps(depthFirstOpenPostfixOps);
		}

		private void reparsePrefixOps() {
			this.depthFirstOpenPrefixOps.clear();
			this.topNode.addOpenPrefixOps(depthFirstOpenPrefixOps);
		}

		private boolean tryApplyTreeTo(IntermediateNode target) {
			// Merge o
			if (!accepts(target, topNodeAsVal)) {
				return false;
			}
			target.setValueNode((Node & IValueNode) topNode);
			topNode = null;
			topNodeAsVal = null;
			return true;
		}

		private boolean tryOvertakeValueNode(IntermediateNode disputable) {
			if (depthFirstOpenPostfixOps.isEmpty()) {
				return false;
			}
			IntermediateNode openOP = depthFirstOpenPostfixOps.peek();
			if (!accepts(openOP, disputable.getValue())) {
				return false;

			}
			if (isPrefixPrefered(disputable, openOP)) {
				return false;
			}
			openOP.setValueNode((Node & IValueNode) disputable.getValue());
			disputable.setValueNode(null);
			depthFirstOpenPostfixOps.pop();
			return true;
		}

		private boolean tryOvertakeWholeTree(Tree other) {
			if (depthFirstOpenPostfixOps.empty()) {
				return false;
			}
			IntermediateNode openOP = depthFirstOpenPostfixOps.peek();
			if (!accepts(openOP, other.topNodeAsVal)) {
				return false;
			}
			openOP.setValueNode((Node & IValueNode) other.topNode);
			other.topNode = null;
			other.topNodeAsVal = null;
			depthFirstOpenPostfixOps.pop();
			return true;
		}

		/**
		 * Will merge left <- this. Returns true if something changed. Leaves the tree with topNode == null iff the
		 * merge is complete. Aka, one of them should be deleted.
		 *
		 * @param left
		 * @return
		 */
		public boolean mergeInto(Tree left) throws SyntaxErrorException {
			if (depthFirstOpenPostfixOps.isEmpty()) {
				// Merge our value
				if (left.depthFirstOpenPrefixOps.isEmpty()) {
					// Both trees represent values
					return false;
				}
				IntermediateNode openOP = left.depthFirstOpenPrefixOps.pop();
				if (tryApplyTreeTo(openOP)) {
					left.reparseDisputableValues();
					left.reparsePrefixOps();
					return true;
				}
			}
			while (!left.depthFirstDisputableValues.empty()) {
				IntermediateNode node = left.depthFirstDisputableValues.pop();
				if (tryOvertakeValueNode(node)) {
					left.reparseDisputableValues();
					left.reparsePrefixOps();
					this.reparseDisputableValues();
					this.reparsePostfixOps();
					return true;
				}
			}
			// Try to swallow left's top node
			if (tryOvertakeWholeTree(left)) {
				this.reparseDisputableValues();
				this.reparsePostfixOps();
				this.reparsePrefixOps();
				return true;
			}
			throw new SyntaxErrorException("Impossible sequence encountered " + left + " " + this);
		}

		@Override
		public String toString() {
			return "Tree { top: " + this.topNode + ", open: " + this.depthFirstOpenPrefixOps + "|"
					+ depthFirstOpenPostfixOps + ", disputable: " + this.depthFirstDisputableValues + "}";
		}
	}

	private static interface NodeFunction {
		<T extends Node & IOperatorNode> ValueIntermediate make(T op);
	}

	// map prefix_id, postfix_id -> boolean, true if prefix has higher fixity
	// final
	private final boolean[][] isPrefixFixityHigher;
	// map prefix_id, value_id -> boolean, true if applicable
	// final
	private final boolean[][] isPrefixApplicable;
	// map postfix_id, value_id -> boolean, true if applicable
	// final
	private final boolean[][] isPostfixApplicable;
	// map prefix_id -> Supplier
	private final NodeFunction[] prefixOpTreeBuilder;
	private final NodeFunction[] postfixOPTreeBuilder;

	private Stack<Tree> partialTrees;

	/* package */ UnaryAST(UnarySyntaxBuilder builder) {
		builder.validate();
		List<SyntaxBuilder.OperatorRegistration> preOpReg = builder.getPrefixOps();
		List<SyntaxBuilder.OperatorRegistration> postOpReg = builder.getPostfixOps();
		List<SyntaxBuilder.ValueRegistration> valueReg = builder.getValues();

		isPrefixFixityHigher = new boolean[preOpReg.size()][postOpReg.size()];
		isPrefixApplicable = new boolean[preOpReg.size()][valueReg.size()];
		isPostfixApplicable = new boolean[postOpReg.size()][valueReg.size()];
		prefixOpTreeBuilder = new NodeFunction[preOpReg.size()];
		postfixOPTreeBuilder = new NodeFunction[postOpReg.size()];
		for (UnarySyntaxBuilder.OperatorRegistration preOp : preOpReg) {
			for (int i = 0; i < postOpReg.size(); i++) {
				boolean preOpAfter = preOp.comesAfterOtherFixity.get(i);
				isPrefixFixityHigher[preOp.operatorID][i] = !preOpAfter;
			}
			for (int i = 0; i < valueReg.size(); i++) {
				isPrefixApplicable[preOp.operatorID][i] = preOp.acceptedValues.get(i);
			}
			prefixOpTreeBuilder[preOp.operatorID] = makeNodeGenerator(preOp);
		}
		for (UnarySyntaxBuilder.OperatorRegistration postOp : postOpReg) {
			for (int i = 0; i < valueReg.size(); i++) {
				isPostfixApplicable[postOp.operatorID][i] = postOp.acceptedValues.get(i);
			}
			postfixOPTreeBuilder[postOp.operatorID] = makeNodeGenerator(postOp);
		}
		partialTrees = new Stack<>();
	}

	/**
	 * Clones the parse structure, but *not* the current state of the AST, i.e. all operators and precedence
	 *
	 * @param structure
	 */
	protected UnaryAST(UnaryAST structure) {
		this.isPrefixFixityHigher = structure.isPrefixFixityHigher;
		this.isPrefixApplicable = structure.isPrefixApplicable;
		this.isPostfixApplicable = structure.isPostfixApplicable;
		this.prefixOpTreeBuilder = structure.prefixOpTreeBuilder;
		this.postfixOPTreeBuilder = structure.postfixOPTreeBuilder;
		this.partialTrees = new Stack<>();
	}

	private NodeFunction makeNodeGenerator(SyntaxBuilder.OperatorRegistration operator) {
		switch (operator.resultType) {
		case VALUE:
			return new NodeFunction() {
				@Override
				public <T extends Node & IOperatorNode> ValueIntermediate make(T op) {
					return new ValueIntermediate(op, operator.resultID);
				}
			};
		case PREFIX_OP:
			return new NodeFunction() {
				@Override
				public <T extends Node & IOperatorNode> ValueIntermediate make(T op) {
					return prefixOpTreeBuilder[operator.resultID]
							.make(new OperatorIntermediate(op, true, operator.resultID));
				}
			};
		case POSTFIX_OP:
			return new NodeFunction() {
				@Override
				public <T extends Node & IOperatorNode> ValueIntermediate make(T op) {
					return postfixOPTreeBuilder[operator.resultID]
							.make(new OperatorIntermediate(op, false, operator.resultID));
				}
			};
		}
		throw new IllegalArgumentException("is resultType null?");
	}

	public Object getOverallValue() throws SyntaxErrorException {
		if (partialTrees.size() != 1) {
			throw new SyntaxErrorException("Not exactly a single tree remaining: " + partialTrees);
		}
		try {
			Tree tree = partialTrees.peek();
			return tree.topNodeAsVal.getVal();
		} catch (IllegalStateException ise) {
			throw new SyntaxErrorException("Unfinished operator" + partialTrees);
		}
	}

	/**
	 *
	 * @param valueId
	 * @param val
	 * @throws SyntaxErrorException
	 *             if the tree can't be fulfilled. For example when the first thing getting pushed is a postfix operator
	 */
	protected void pushValue(int valueId, Object val) throws SyntaxErrorException {
		// valueClasses[valueId].cast(val);
		partialTrees.push(new Tree(valueId, val));
		remergeTrees();
	}

	protected void pushOperator(boolean isPrefix, int opId, IOperator<?, ?> op) throws SyntaxErrorException {
		// (isPrefix ? prefixClasses : postfixClasses)[opId].cast(op);
		Objects.requireNonNull(op);
		partialTrees.push(new Tree(isPrefix, opId, op));
		remergeTrees();
	}

	private void remergeTrees() {
		boolean somethingHappened = true;
		while (somethingHappened && partialTrees.size() >= 2) {
			Tree one = partialTrees.pop();
			Tree two = partialTrees.pop();
			somethingHappened = one.mergeInto(two);
			if (two.topNode != null) {
				partialTrees.push(two);
			}
			if (one.topNode != null) {
				partialTrees.push(one);
			}
		}
	}

	private boolean accepts(IntermediateNode op, IValueNode valueType) {
		if (op.isPrefix()) {
			return isPrefixApplicable[op.getOperator().getOpType()][valueType.getValueType()];
		}
		return isPostfixApplicable[op.getOperator().getOpType()][valueType.getValueType()];
	}

	private boolean isPrefixPrefered(IntermediateNode prefixOP, IntermediateNode postfixOP) {
		if (!prefixOP.isPrefix() || postfixOP.isPrefix()) {
			throw new IllegalArgumentException(
					"Messed up! Two operators of the same type are fighting for a value" + prefixOP + postfixOP);
		}
		return isPrefixFixityHigher[prefixOP.getOperator().getOpType()][postfixOP.getOperator().getOpType()];
	}

	private ValueIntermediate makeNodesFor(boolean isPrefix, int opID, IOperator<?, ?> op) {
		return (isPrefix ? prefixOpTreeBuilder : postfixOPTreeBuilder)[opID].make(new OperatorLeaf(opID, isPrefix, op));
	}
}
