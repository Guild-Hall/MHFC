package mhfc.net.common.util.reflection;

import java.lang.invoke.MethodHandle;
import java.util.Objects;

/**
 * Helper struct to determine applicability
 */
public class MethodInfo {
	public final MethodHandle method;
	public final Class<?>[] argTypes;
	public final Class<?> varArgCompType;
	public final boolean isVarArgs;

	public MethodInfo(MethodHandle method) {
		this.method = Objects.requireNonNull(method);
		this.argTypes = method.type().parameterArray();
		this.isVarArgs = method.isVarargsCollector();
		this.varArgCompType = this.argTypes[this.argTypes.length - 1].getComponentType();
	}

	public Class<?> getVarArgType(int position) {
		if (position < 0) {
			throw new IllegalArgumentException("position must be greater 0");
		}
		if (!isVarArgs) {
			throw new IllegalStateException("Not a vararg info");
		}
		return position >= argTypes.length - 1 ? varArgCompType : argTypes[position];
	}

	@Override
	public String toString() {
		return method.toString();
	}
}
