package mhfc.net.common.util.stringview;

public interface Viewable {
	void appendTo(StringBuilder builder);

	default Viewable append(Viewable other) {
		return new JoiningView("").append(this).append(other);
	}

	default Viewable appendStatic(String staticString) {
		return append(new StaticString(staticString));
	}
}
