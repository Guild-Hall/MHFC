package mhfc.net.common.util.io;

public interface IConverter<A, AC, B, BC> {

	public A convertFrom(B value, BC context);

	public B convertTo(A value, AC context);

}
