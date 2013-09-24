package edu.cmu.deiis.annotator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Answer;

/**
 * This Annotator Annotates the answers present in the document and assigned
 * isCorrent its value
 */
public class AnswerAnnotator extends JCasAnnotator_ImplBase {
	private Pattern correctAnswerPattern = Pattern.compile("(?m)\nA 1 .*$"); 
	private Pattern wrongAnswerPattern = Pattern.compile("(?m)\nA 0 .*$");

	public void process(JCas aJCas) {
		// Discussed Later
		// get document text
		String docText = aJCas.getDocumentText();
		// search for tokens
		Matcher matcher = correctAnswerPattern.matcher(docText);
		while (matcher.find()) {
			// found one - create annotation
			Answer annotation = new Answer(aJCas);
			annotation.setBegin(matcher.start()+5);
			annotation.setEnd(matcher.end());
			annotation.setIsCorrect(true);
			annotation.setCasProcessorId("JavaRegex");
			annotation.setConfidence(1);
			annotation.addToIndexes();
		}
		matcher = wrongAnswerPattern.matcher(docText);
		while (matcher.find()) {
			// found one - create annotation
			Answer annotation = new Answer(aJCas);
			annotation.setBegin(matcher.start()+5);
			annotation.setEnd(matcher.end());
			annotation.setIsCorrect(false);
			annotation.addToIndexes();
		}
	}

}
