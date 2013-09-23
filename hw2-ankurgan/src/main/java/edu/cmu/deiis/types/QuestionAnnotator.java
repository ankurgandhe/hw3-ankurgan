package edu.cmu.deiis.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Question;

public class QuestionAnnotator extends JCasAnnotator_ImplBase {
	private Pattern QuestionPattern = Pattern.compile("(?m)^Q .*$"); 
	 
	public void process(JCas aJCas) {
		// Discussed Later
		// get document text
		String docText = aJCas.getDocumentText();
		// search for tokens
		Matcher matcher = QuestionPattern.matcher(docText);
		while (matcher.find()) {
			// found one - create annotation
			Question annotation = new Question(aJCas);
			annotation.setBegin(matcher.start());
			annotation.setEnd(matcher.end());
			annotation.addToIndexes();
		}
		
	}

}
