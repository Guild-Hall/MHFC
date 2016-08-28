package mhfc.net.common.util.stringview;

public interface Viewable {
	void appendTo(StringBuilder builder);

	default Viewable append(Viewable other) {
		return JoiningView.on("").append(this).append(other);
	}

	default Viewable appendStatic(String staticString) {
		return append(new StaticString(staticString));
	}
}
