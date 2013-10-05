package edu.cmu.deiis.cpe;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;


import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.Evaluation;
import edu.cmu.deiis.types.Question;

public class CasConsumer extends CasConsumer_ImplBase {

	private double totalPrecision;
	private double numberOfDoc;

	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		totalPrecision = 0;
		numberOfDoc = 0;
	}

	public void destroy() {
		double avgP = totalPrecision / numberOfDoc ;
		avgP = Math.round(avgP * 100) / 100.0d;
		System.out.println("Average Precision:" + avgP);
	}

	@Override
	public void processCas(CAS aCas) throws ResourceProcessException {
		// Read the Evaluator index and print out the precision
		JCas aJCas;
		try {
			aJCas = aCas.getJCas();
		} catch (CASException e) {
			throw new ResourceProcessException(e);
		}
		FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type);
		Iterator questionIter = questionIndex.iterator();
		
		// Print question
		Question question = (Question) questionIter.next();
		System.out.println(question.getCoveredText().trim());
		// Print Final Evaluation
		FSIndex evaluationIndex = aJCas.getAnnotationIndex(Evaluation.type);
		Iterator evaluationIter = evaluationIndex.iterator();
		FSArray answerScoreArray = null;
		AnswerScore answerScore=null;
		Evaluation evl  = null;
		if (evaluationIter.hasNext()) {
			evl = (Evaluation) evaluationIter.next();
			answerScoreArray = evl.getAnswerList();
			for (int idx=0; idx < answerScoreArray.size(); idx ++ ) {
				answerScore = (AnswerScore) answerScoreArray.get(idx);
				if (answerScore.getAnswer().getIsCorrect())
					System.out.println("+ " + answerScore.getScore() + " "
							+ answerScore.getAnswer().getCoveredText().trim());
				else
					System.out.println("- " + answerScore.getScore() + " "
							+ answerScore.getAnswer().getCoveredText().trim());
				
			}
			System.out.println("Precision at " + evl.getN() + ":"
					+ evl.getPrecision() + "\n" );
			totalPrecision += evl.getPrecision();
			numberOfDoc++;
		} else
			System.out.println("No Evaluation present");

		
	}

}
