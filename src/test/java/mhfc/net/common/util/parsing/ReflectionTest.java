package mhfc.net.common.util.parsing;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import mhfc.net.common.util.reflection.FieldHelper;
import mhfc.net.common.util.reflection.MethodHelper;
import mhfc.net.common.util.reflection.OverloadedMethod;

public class ReflectionTest {
	public class TestClass {
		public String member = "Found";

		public String method(String argument) {
			return "Found String";
		}

		public int method(float argument) {
			return 42;
		}
	}

	@Test
	public void findMember() {
		Optional<Field> f = FieldHelper.findMatching(TestClass.class, "member");
		Assert.assertTrue(f.isPresent());
		Assert.assertThat(f.get().getType(), IsEqual.equalTo(String.class));
	}

	@Test
	public void findMethod() {
		Optional<OverloadedMethod> methods = MethodHelper.findMatching(TestClass.class, "method");
		Assert.assertTrue(methods.isPresent());
		OverloadedMethod ms = methods.get();

		Optional<Method> method = ms.disambiguate(String.class);
		Assert.assertTrue(method.isPresent());
		Method m = method.get();
		Assert.assertThat(m.getReturnType(), IsEqual.equalTo(String.class));
	}
}
