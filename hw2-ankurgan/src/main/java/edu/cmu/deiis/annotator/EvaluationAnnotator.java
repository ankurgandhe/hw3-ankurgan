package edu.cmu.deiis.annotator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.Question;

/**
 * Annotator to take question and all scored answers, and produce ranked list of
 * answers. It also produces the precision@N, where N is number of correct
 * answers
 */

public class EvaluationAnnotator extends JCasAnnotator_ImplBase {
	public void process(JCas aJCas) {
		// get document text
		
		// search for question
		FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type);
		Iterator questionIter = questionIndex.iterator();
		Question question = (Question) questionIter.next();
		System.out.println(question.getCoveredText().trim());

		// Iterate over all asnwers
		FSIndex answerScoreIndex = aJCas.getAnnotationIndex(AnswerScore.type);
		Iterator answerScoreIter = answerScoreIndex.iterator();
		AnswerScore answerScore = null;
		List<AnswerScore> AnswerList = new ArrayList<AnswerScore>();
		
		// Store number of correct answers
		int N = 0;
		while (answerScoreIter.hasNext()) {
			answerScore = (AnswerScore) answerScoreIter.next();
			AnswerList.add(answerScore);
			if (answerScore.getAnswer().getIsCorrect())
				N = N + 1;
		}
		// Sort AnswerList in reverse order 
		Collections.sort(AnswerList, new ScoreComparator());

		// store number of correct answers in topN
		int i = 0;
		float nCorrect = 0;
		for (AnswerScore a : AnswerList) {
			System.out.println(a.getScore() + " "
					+ a.getAnswer().getCoveredText().trim());
			if (i++ < N && a.getAnswer().getIsCorrect())
				nCorrect += 1;

		}
		System.out.println("Precision at " + N + ":" + nCorrect / N + "\n");
	}
}

class ScoreComparator implements Comparator<AnswerScore> {
	public int compare(AnswerScore o1, AnswerScore o2) {
		if (o1.getScore() < o2.getScore())
			return 1;
		else
			return -1;

	}
}