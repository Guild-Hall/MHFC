package mhfc.net.common.util.parsing.valueholders;

import java.lang.invoke.MethodHandle;
import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.exceptions.MethodNotFoundException;
import mhfc.net.common.util.reflection.MethodHelper;
import mhfc.net.common.util.reflection.OverloadedMethod;

public class FunctionCall implements IValueHolder {
	private static interface ICall {
		boolean isTypeFinal();

		Class<?> getType();

		Holder call(Object instance, Arguments args) throws Throwable;
	}

	private static class MethodProxy implements ICall {
		private final MethodHandle method;
		private final Supplier<RuntimeException> error;

		public MethodProxy(MethodHandle method) {
			assert method.type().parameterArray().length == 2;
			assert method.type().parameterArray()[1].isAssignableFrom(Arguments.class);
			if (!Holder.class.isAssignableFrom(method.type().returnType())) {
				error = () -> new IllegalArgumentException("__call__ must return Holder");
			} else {
				error = null;
			}
			this.method = method;
		}

		@Override
		public Class<?> getType() {
			return IValueHolder.EMPTY_CLASS;
		}

		@Override
		public boolean isTypeFinal() {
			return error != null;
		}

		@Override
		public Holder call(Object instance, Arguments args) throws Throwable {
			if (error != null) {
				throw error.get();
			}
			return Holder.class.cast(method.invokeWithArguments(instance, args));
		}
	}

	private static class NoCallFound implements ICall {
		private final Class<?> clazz;

		public NoCallFound(Class<?> clazz) {
			this.clazz = clazz;
		}

		@Override
		public Holder call(Object instance, Arguments args) {
			throw new MethodNotFoundException(
					instance + " not callable: method 'Holder " + clazz.getName()
							+ ".__call__(Arguments)' hasn't been found");
		}

		@Override
		public Class<?> getType() {
			return IValueHolder.EMPTY_CLASS;
		}

		@Override
		public boolean isTypeFinal() {
			return true;
		}
	}

	private static LoadingCache<Class<?>, ICall> callCache;

	static {
		callCache = CacheBuilder.newBuilder().maximumSize(1000).build(new CacheLoader<Class<?>, ICall>() {
			@Override
			public ICall load(Class<?> key) {
				return computeCall(key);
			}
		});
	}

	private static ICall resolveCall(Class<?> clazz) {
		return callCache.getUnchecked(clazz);
	}

	private static ICall computeCall(Class<?> clazz) {
		Optional<OverloadedMethod> specialMethod = MethodHelper.find(clazz, "__call__");
		Optional<MethodHandle> call = specialMethod.flatMap(s -> s.disambiguate(clazz, Arguments.class));
		if (call.isPresent()) {
			return new MethodProxy(call.get());
		}
		return new NoCallFound(clazz);
	}

	private IValueHolder holder;
	private Arguments args;

	private FunctionCall(IValueHolder holder, Arguments args) {
		this.holder = holder;
		this.args = args;
	}

	@Override
	public Holder snapshot() throws Throwable {
		Holder instance = holder.snapshot();
		return resolveCall(instance.getType()).call(instance.boxed(), args);
	}

	public static IValueHolder makeFunctionCall(IValueHolder callee, IValueHolder... arguments) {
		Arguments args = new Arguments(arguments);
		return new FunctionCall(callee, args);
	}

	@Override
	public String toString() {
		return holder.toString() + "(" + args.toString() + ")";
	}
}
