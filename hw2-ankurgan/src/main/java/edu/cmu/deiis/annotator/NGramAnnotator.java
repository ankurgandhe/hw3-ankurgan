package edu.cmu.deiis.annotator;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Token;
import edu.cmu.deiis.types.NGram;

/**
 * ngram annotator that detects 1-gram, 2-gram and 3-gram using Java regular
 * expressions.
 **/
public class NGramAnnotator extends JCasAnnotator_ImplBase {
	private Pattern sentencePattern = Pattern.compile(".*[\\n$]");

	public void process(JCas aJCas) {
		// Iterate over token
		FSIndex tokenIndex = aJCas.getAnnotationIndex(Token.type);
		Iterator tokenIter = tokenIndex.iterator();

		if (!tokenIter.hasNext())
			return;
		String docText = aJCas.getDocumentText();
		// search for tokens within a single sentence
		Matcher matcher = sentencePattern.matcher(docText);
		Token t1 = (Token) tokenIter.next();
		// Find all ngrams in this sentence
		while (matcher.find()) {
			t1 = MarkNgrams(aJCas, t1, tokenIter, matcher.end());
		}
	}

	/*
	 * Function to iterate over tokens and add 1-gram, 2-gram and 3-gram
	 * annotations
	 */
	public Token MarkNgrams(JCas aJCas, Token t1, Iterator tokenIter, int end) {
		Token tm1 = null, t2;

		// Uni-gram
		NGram annotation = new NGram(aJCas);
		annotation.setBegin(t1.getBegin());
		annotation.setEnd(t1.getEnd());
		annotation.addToIndexes();

		while (tokenIter.hasNext()) {
			t2 = (Token) tokenIter.next();
			if (t2.getBegin() > end) {
				t1 = t2;
				break;
			}
			// Tri-gram
			if (tm1 != null) {
				annotation = new NGram(aJCas);
				annotation.setBegin(tm1.getBegin());
				annotation.setEnd(t2.getEnd());
				annotation.addToIndexes();
			}
			// Bi-gram
			annotation = new NGram(aJCas);
			annotation.setBegin(t1.getBegin());
			annotation.setEnd(t2.getEnd());
			annotation.addToIndexes();
			// Uni-gram
			annotation = new NGram(aJCas);
			annotation.setBegin(t2.getBegin());
			annotation.setEnd(t2.getEnd());
			annotation.addToIndexes();
			tm1 = t1;
			t1 = t2;

		}
		return t1;
	}
}
