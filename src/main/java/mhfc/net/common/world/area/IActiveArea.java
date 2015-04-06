package mhfc.net.common.world.area;
/**
 * Represents an {@link IArea} in use. It can be dismissed via
 * {@link #dismiss()}.
 *
 * @author WorldSEnder
 *
 */
public interface IActiveArea extends AutoCloseable {
	/**
	 * Gets the area of the active area
	 *
	 * @return the underlying area
	 */
	IArea getArea();
	/**
	 * Dismisses the area. All following calls to this area should fail - and
	 * may throw an {@link IllegalStateException}.
	 *
	 * @return <code>true</code> if the area was active before, aka there was no
	 *         call to {@link #dismiss()} before
	 */
	boolean dismiss();
}
