package mhfc.net.common.util.parsing.syntax.operators;

import java.util.Formatter;
import java.util.Objects;
import java.util.function.BooleanSupplier;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.proxies.MemberMethodProxy;
import mhfc.net.common.util.parsing.syntax.literals.FunctionCallLiteral;
import mhfc.net.common.util.parsing.syntax.literals.HolderLiteral;
import mhfc.net.common.util.parsing.syntax.literals.IExpression;
import mhfc.net.common.util.reflection.MethodHelper;
import mhfc.net.common.util.reflection.OverloadedMethod;

public class Operators {
	// ====== Unary operators

	// --- complement
	public static int bitwiseComplement(int arg) {
		return ~arg;
	}

	public static long bitwiseComplement(long arg) {
		return ~arg;
	}

	public static boolean logicalComplement(boolean arg) {
		return !arg;
	}

	// ====== Binary operators

	// --- multiply
	public static int multiply(int arg1, int arg2) {
		return arg1 * arg2;
	}

	public static long multiply(long arg1, long arg2) {
		return arg1 * arg2;
	}

	public static float multiply(float arg1, float arg2) {
		return arg1 * arg2;
	}

	public static double multiply(double arg1, double arg2) {
		return arg1 * arg2;
	}

	// --- divide
	public static int divide(int arg1, int arg2) {
		return arg1 / arg2;
	}

	public static long divide(long arg1, long arg2) {
		return arg1 / arg2;
	}

	public static float divide(float arg1, float arg2) {
		return arg1 / arg2;
	}

	public static double divide(double arg1, double arg2) {
		return arg1 / arg2;
	}

	// --- remainder
	public static int remainder(int arg1, int arg2) {
		return arg1 % arg2;
	}

	public static long remainder(long arg1, long arg2) {
		return arg1 % arg2;
	}

	public static float remainder(float arg1, float arg2) {
		return arg1 % arg2;
	}

	public static double remainder(double arg1, double arg2) {
		return arg1 % arg2;
	}

	// --- Add
	public static int add(int arg1, int arg2) {
		return arg1 + arg2;
	}

	public static long add(long arg1, long arg2) {
		return arg1 + arg2;
	}

	public static float add(float arg1, float arg2) {
		return arg1 + arg2;
	}

	public static double add(double arg1, double arg2) {
		return arg1 + arg2;
	}

	public static String add(String arg1, String arg2) {
		return arg1 + arg2;
	}

	// --- Substract
	public static int substract(int arg1, int arg2) {
		return arg1 - arg2;
	}

	public static long substract(long arg1, long arg2) {
		return arg1 - arg2;
	}

	public static float substract(float arg1, float arg2) {
		return arg1 - arg2;
	}

	public static double substract(double arg1, double arg2) {
		return arg1 - arg2;
	}

	// --- shift-left
	public static int leftshift(int arg1, int arg2) {
		return arg1 << arg2;
	}

	public static int leftshift(int arg1, long arg2) {
		return arg1 << arg2;
	}

	public static long leftshift(long arg1, int arg2) {
		return arg1 << arg2;
	}

	public static long leftshift(long arg1, long arg2) {
		return arg1 << arg2;
	}

	// --- signed shift right
	public static int rightshift(int arg1, int arg2) {
		return arg1 >> arg2;
	}

	public static int rightshift(int arg1, long arg2) {
		return arg1 >> arg2;
	}

	public static long rightshift(long arg1, int arg2) {
		return arg1 >> arg2;
	}

	public static long rightshift(long arg1, long arg2) {
		return arg1 >> arg2;
	}

	// --- unsigned shift right
	public static int rightshiftUnsigned(int arg1, int arg2) {
		return arg1 >>> arg2;
	}

	public static int rightshiftUnsigned(int arg1, long arg2) {
		return arg1 >>> arg2;
	}

	public static long rightshiftUnsigned(long arg1, int arg2) {
		return arg1 >>> arg2;
	}

	public static long rightshiftUnsigned(long arg1, long arg2) {
		return arg1 >>> arg2;
	}

	// ----- comparison
	// --- less
	public static boolean less(int arg1, int arg2) {
		return arg1 < arg2;
	}

	public static boolean less(long arg1, long arg2) {
		return arg1 < arg2;
	}

	public static boolean less(float arg1, float arg2) {
		return arg1 < arg2;
	}

	public static boolean less(double arg1, double arg2) {
		return arg1 < arg2;
	}

	// --- less equal
	public static boolean lessEqual(int arg1, int arg2) {
		return arg1 <= arg2;
	}

	public static boolean lessEqual(long arg1, long arg2) {
		return arg1 <= arg2;
	}

	public static boolean lessEqual(float arg1, float arg2) {
		return arg1 <= arg2;
	}

	public static boolean lessEqual(double arg1, double arg2) {
		return arg1 <= arg2;
	}

	// --- greater
	public static boolean greater(int arg1, int arg2) {
		return arg1 > arg2;
	}

	public static boolean greater(long arg1, long arg2) {
		return arg1 > arg2;
	}

	public static boolean greater(float arg1, float arg2) {
		return arg1 > arg2;
	}

	public static boolean greater(double arg1, double arg2) {
		return arg1 > arg2;
	}

	// --- greater equal
	public static boolean greaterEqual(int arg1, int arg2) {
		return arg1 >= arg2;
	}

	public static boolean greaterEqual(long arg1, long arg2) {
		return arg1 >= arg2;
	}

	public static boolean greaterEqual(float arg1, float arg2) {
		return arg1 >= arg2;
	}

	public static boolean greaterEqual(double arg1, double arg2) {
		return arg1 >= arg2;
	}

	// --- equals
	public static boolean equal(int arg1, int arg2) {
		return arg1 == arg2;
	}

	public static boolean equal(long arg1, long arg2) {
		return arg1 == arg2;
	}

	public static boolean equal(float arg1, float arg2) {
		return arg1 == arg2;
	}

	public static boolean equal(double arg1, double arg2) {
		return arg1 == arg2;
	}

	public static boolean equal(boolean arg1, boolean arg2) {
		return arg1 == arg2;
	}

	public static boolean equal(Object arg1, Object arg2) {
		return Objects.equals(arg1, arg2);
	}

	// --- unequals
	public static boolean unequal(int arg1, int arg2) {
		return arg1 != arg2;
	}

	public static boolean unequal(long arg1, long arg2) {
		return arg1 != arg2;
	}

	public static boolean unequal(float arg1, float arg2) {
		return arg1 != arg2;
	}

	public static boolean unequal(double arg1, double arg2) {
		return arg1 != arg2;
	}

	public static boolean unequal(boolean arg1, boolean arg2) {
		return arg1 != arg2;
	}

	public static boolean unequal(Object arg1, Object arg2) {
		return !Objects.equals(arg1, arg2);
	}

	// --- bitwise AND
	public static int and(int arg1, int arg2) {
		return arg1 & arg2;
	}

	public static long and(long arg1, long arg2) {
		return arg1 & arg2;
	}

	public static boolean and(boolean arg1, boolean arg2) {
		return arg1 & arg2;
	}

	// --- bitwise OR
	public static int or(int arg1, int arg2) {
		return arg1 | arg2;
	}

	public static long or(long arg1, long arg2) {
		return arg1 | arg2;
	}

	public static boolean or(boolean arg1, boolean arg2) {
		return arg1 | arg2;
	}

	// --- bitwise XOR
	public static int xor(int arg1, int arg2) {
		return arg1 ^ arg2;
	}

	public static long xor(long arg1, long arg2) {
		return arg1 ^ arg2;
	}

	public static boolean xor(boolean arg1, boolean arg2) {
		return arg1 ^ arg2;
	}

	// --- conditional and
	public static ShortCurcuitResult conditionalAnd(boolean arg1) {
		return arg1 ? ShortCurcuitResult.RESULT_IS_SECOND : ShortCurcuitResult.RESULT_FALSE;
	}

	// --- conditional or
	public static ShortCurcuitResult conditionalOr(boolean arg1, BooleanSupplier arg2) {
		return arg1 ? ShortCurcuitResult.RESULT_TRUE : ShortCurcuitResult.RESULT_IS_SECOND;
	}

	// ===== factory functions
	private static IExpression makeMethodProxy(String name) {
		OverloadedMethod methods = MethodHelper.findStatic(Operators.class, name).get();
		Holder constant = Holder.valueOf(new MemberMethodProxy(methods));
		return new HolderLiteral(constant);
	}

	private static IOperator<IExpression, IExpression> makeUnaryProxy(String name) {
		return r -> new FunctionCallLiteral(makeMethodProxy(name), r);
	}

	private static IBinaryOperator<IExpression, IExpression, IExpression> makeBinaryProxy(String name) {
		return (l, r) -> new FunctionCallLiteral(makeMethodProxy(name), l, r);
	}

	public static IOperator<IExpression, IExpression> makeBitwiseComplementOp() {
		return makeUnaryProxy("bitwiseComplement");
	}

	public static IOperator<IExpression, IExpression> makeLogicalComplementOp() {
		return makeUnaryProxy("logicalComplement");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeMultiplyOp() {
		return makeBinaryProxy("multiply");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeDivideOp() {
		return makeBinaryProxy("divide");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeModuloOp() {
		return makeBinaryProxy("remainder");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeAddOp() {
		return makeBinaryProxy("add");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeMinusOp() {
		return makeBinaryProxy("substract");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeLeftShiftOp() {
		return makeBinaryProxy("leftshift");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeRightShiftOp() {
		return makeBinaryProxy("rightshift");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeUnsignedRightShiftOp() {
		return makeBinaryProxy("rightshiftUnsigned");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeLessThanOp() {
		return makeBinaryProxy("less");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeLessOrEqOp() {
		return makeBinaryProxy("lessEqual");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeGreaterThanOp() {
		return makeBinaryProxy("greater");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeGreaterOrEqualOp() {
		return makeBinaryProxy("greaterEqual");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeEqualOp() {
		return makeBinaryProxy("equal");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeNotEqualOp() {
		return makeBinaryProxy("unequal");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeAndOp() {
		return makeBinaryProxy("and");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeOrOp() {
		return makeBinaryProxy("or");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeXorOp() {
		return makeBinaryProxy("xor");
	}

	private static enum ShortCurcuitResult {
		RESULT_IS_SECOND,
		RESULT_IS_NOT_SECOND,
		RESULT_TRUE,
		RESULT_FALSE;
	}

	@FunctionalInterface
	private static interface ShortCurcuitOperation {
		ShortCurcuitResult compute(boolean first);
	}

	private static class ShortCurcuitExpression implements IExpression {
		private final IExpression left;
		private final IExpression right;
		private final ShortCurcuitOperation op;
		private final String repr;

		public ShortCurcuitExpression(IExpression left, IExpression right, ShortCurcuitOperation op, String repr) {
			this.left = Objects.requireNonNull(left);
			this.right = Objects.requireNonNull(right);
			this.op = Objects.requireNonNull(op);
			this.repr = repr;
		}

		@Override
		public void prettyPrint(Formatter formatter) {
			left.prettyPrint(formatter);
			formatter.format(" %s ", repr);
			right.prettyPrint(formatter);
		}

		@Override
		public IValueHolder asValue(IValueHolder ctx) {
			IValueHolder leftHolder = left.asValue(ctx);
			IValueHolder rightHolder = right.asValue(ctx);
			return new IValueHolder() {
				@Override
				public Holder snapshot() throws Throwable {
					switch (op.compute(leftHolder.snapshot().asBool())) {
					case RESULT_FALSE:
						return Holder.FALSE;
					case RESULT_TRUE:
						return Holder.TRUE;
					case RESULT_IS_SECOND:
						return Holder.valueOf(rightHolder.snapshot().asBool());
					case RESULT_IS_NOT_SECOND:
						return Holder.valueOf(!rightHolder.snapshot().asBool());
					default:
						throw new IllegalStateException("Error, enum value is likely null");
					}
				}

				@Override
				public String toString() {
					return leftHolder.toString() + op.toString() + rightHolder;
				}
			};
		}
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeConditionalAndOp() {
		return (l, r) -> new ShortCurcuitExpression(l, r, Operators::conditionalAnd, "&&");
	}

	public static IBinaryOperator<IExpression, IExpression, IExpression> makeConditionalOrOp() {
		return (l, r) -> new ShortCurcuitExpression(l, r, Operators::conditionalAnd, "||");
	}
}
