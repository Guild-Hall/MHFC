package mhfc.net.common.util.stringview;

public interface Viewable {
	void appendTo(StringBuilder builder);

	default Viewable concat(Viewable other) {
		return JoinedView.on("").concat(this).concat(other);
	}

	default Viewable appendStatic(String staticString) {
		return concat(new StaticString(staticString));
	}
}
