package edu.cmu.deiis.types;

import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * Example annotator that detects meetings from the co-occurrence of a
 * RoomNumber, a Date, and two Times (start and end), within a specified
 * "window" size.
 */
public class AnswerScoreAnnotator extends JCasAnnotator_ImplBase {

	/**
	 * @see JCasAnnotator_ImplBase#process(JCas)
	 */
	public void process(JCas aJCas) {
		// get annotation indexes
		FSIndex tokenIndex = aJCas.getAnnotationIndex(Token.type);
		Iterator tokenIter = tokenIndex.iterator();

		FSIndex answerIndex = aJCas.getAnnotationIndex(Answer.type);
		Iterator answerIter = answerIndex.iterator();

		FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type);
		Iterator questionIter = questionIndex.iterator();

		FSIndex ngramIndex = aJCas.getAnnotationIndex(NGram.type);
		Iterator ngramIter = ngramIndex.iterator();

		Question ques = (Question) questionIter.next();
		
		Answer ans;
		float score;
		while (answerIter.hasNext()) {
			ans = (Answer) answerIter.next();
			score = ScoreAnswer(aJCas, ques, ans, tokenIter, ngramIter);
		}

	}

	private float ScoreAnswer(JCas aJCas, Question ques, Answer ans,
			Iterator tokenIter, Iterator ngramIter) {
		float score = 0;
		return score;
	}
}
