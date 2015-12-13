package mhfc.net.common.util.parsing.syntax;

import java.util.Stack;

public interface IBasicSequence {
	public static enum SiftResult {
		ACCEPTED, // The codepoint was accepted, don't push yet
		REJCECTED, // This sequence does not match, reject all so-far-accepted
					// codepoints
		FINISHED; // This sequence matches, push and reject last codepoint
	}

	SiftResult accepting(int cp);

	/**
	 * {@link SiftResult#ACCEPTED} is <b> NOT </b> allowed here. Either
	 * {@link SiftResult#FINISHED} or {@link SiftResult#REJCECTED}.
	 * 
	 * @return
	 */
	SiftResult endOfStream();

	void reset();

	void pushOnto(Stack<Symbol> tokenStack);
}
