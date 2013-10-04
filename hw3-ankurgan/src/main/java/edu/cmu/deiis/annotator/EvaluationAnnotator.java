package edu.cmu.deiis.annotator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Evaluation;

/**
 * Annotator to take question and all scored answers, and produce ranked list of
 * answers. It also produces the precision@N, where N is number of correct
 * answers
 */

public class EvaluationAnnotator extends JCasAnnotator_ImplBase {

	private double totalPrecision;
	private double numberOfDoc;

	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
		totalPrecision = 0;
		numberOfDoc = 0;
	}

	public void destroy() {
		//System.out.println("Average Precision:" + totalPrecision / numberOfDoc);
	}

	public void process(JCas aJCas) {

		// search for question
		FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type);
		Iterator questionIter = questionIndex.iterator();
		Question question = (Question) questionIter.next();
		//System.out.println(question.getCoveredText().trim());

		// Iterate over all answers
		FSIndex answerScoreIndex = aJCas.getAnnotationIndex(AnswerScore.type);
		Iterator answerScoreIter = answerScoreIndex.iterator();
		AnswerScore answerScore = null;
		List<AnswerScore> AnswerList = new ArrayList<AnswerScore>();

		// Store number of correct answers
		int N = 0;
		// Get all answers in a list
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
			/*if (a.getAnswer().getIsCorrect())
				System.out.println("+ " + a.getScore() + " "
						+ a.getAnswer().getCoveredText().trim());
			else
				System.out.println("- " + a.getScore() + " "
						+ a.getAnswer().getCoveredText().trim());
			*/
			if (i++ < N && a.getAnswer().getIsCorrect())
				nCorrect += 1;

		}
		float precision = nCorrect / N;
		//System.out.println("Precision at " + N + ":" + precision + "\n");
		
		totalPrecision += precision;
		numberOfDoc++;
		// Store result in Evaluation annotation
		Evaluation evaluate = new Evaluation(aJCas);
		evaluate.setPrecision(precision);
		evaluate.setN(N);
		evaluate.setBegin(question.getBegin());
		evaluate.setEnd(answerScore.getEnd());
		evaluate.addToIndexes();
		
	}
}

/*
 * Class that implements comparator for AnswerScore.
 */
class ScoreComparator implements Comparator<AnswerScore> {
	public int compare(AnswerScore o1, AnswerScore o2) {
		if (o1.getScore() <= o2.getScore())
			return 1;
		else
			return -1;

	}
}