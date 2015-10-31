package mhfc.net.common.util.parsing.valueholders;

import java.util.concurrent.Callable;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

public interface ICallableValueHolder extends IValueHolder, Callable<Holder> {

}
