package edu.cmu.deiis.annotator;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Question;

/**
 * Question Annotator that detects question using Java regular expressions.
 **/
public class QuestionAnnotator extends JCasAnnotator_ImplBase {
	private Pattern QuestionPattern = Pattern.compile("(?m)^Q .*$");

	public void process(JCas aJCas) {
		// get document text
		String docText = aJCas.getDocumentText();
		// search for questions
		Matcher matcher = QuestionPattern.matcher(docText);
		while (matcher.find()) {
			// found one - create annotation
			Question annotation = new Question(aJCas);
			annotation.setBegin(matcher.start()+2);
			annotation.setEnd(matcher.end());
			annotation.addToIndexes();

		}

	}

}
