package edu.cmu.deiis.annotator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Token;

/**
 * Token Annotator that detects tokens using Java regular expressions.
 **/

public class TokenAnnotator extends JCasAnnotator_ImplBase {
	private Pattern tokenPattern = Pattern
			.compile("(?m)(?<!\\n|\n|^)\\b[a-zA-Z_]([a-zA-Z0-9_^])*\\b");

	public void process(JCas aJCas) {
		// get document text
		String docText = aJCas.getDocumentText();
		// search for tokens
		Matcher matcher = tokenPattern.matcher(docText);

		while (matcher.find()) {
			// found one - create annotation
			Token annotation = new Token(aJCas);
			annotation.setBegin(matcher.start());
			annotation.setEnd(matcher.end());
			annotation.addToIndexes();
		}

	}
}
