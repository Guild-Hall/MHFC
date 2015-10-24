package mhfc.net.common.util.parsing;

import java.lang.ref.WeakReference;

public interface IListenableValue extends IValueHolder {

	public static interface IValueListener {
		boolean onChange(Holder newValue);
	}
	/**
	 * Registers in the collection of listeners that receive a
	 * {@link IValueListener#onChange(Holder)} callback when the value of this
	 * {@link IValueHolder} changes (given that it can change).<br>
	 * The values should be kept by {@link WeakReference}, so that the Listeners
	 * are *automatically* unregistered by the GC. Be sure to keep the Listener
	 * alive yourself if you want continuous updates.
	 *
	 * @param listener
	 *            the listener to register
	 */
	void register(IValueListener listener);
	/**
	 * Unregisters a listener. This is generally not possible during the
	 * callback. Instead, a return value of <code>false</code> signals that a
	 * handler wants to be unregistered after the callback. Use this method to
	 * unregister the listener from outside.
	 *
	 * @param listener
	 * @return
	 */
	boolean unregister(IValueListener listener);

}
