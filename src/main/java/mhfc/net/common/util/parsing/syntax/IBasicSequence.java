package mhfc.net.common.util.parsing.syntax;

import mhfc.net.common.util.parsing.syntax.tree.AST;
import net.minecraft.command.SyntaxErrorException;

public interface IBasicSequence {
	public static enum SiftResult {
		/**
		 * The current codepoint was accepted, continue
		 */
		ACCEPTED,
		/**
		 * This current codepoint does not match, reject all so-far-accepted
		 */
		REJCECTED,
		/**
		 * This sequence matches, push but accept current codepoint
		 */
		FINISHED,
		/**
		 * This sequence matches, push and reject current codepoint
		 */
		PAST_END;
	}

	SiftResult accepting(int cp);

	/**
	 * Marks that the end of the input stream has been reached. {@link SiftResult#ACCEPTED} is <b> NOT </b> allowed
	 * here. Either {@link SiftResult#PAST_END} or {@link SiftResult#REJCECTED}.
	 *
	 * @return
	 */
	SiftResult endOfStream();

	void reset();

	void pushOnto(AST ast) throws SyntaxErrorException;
}
