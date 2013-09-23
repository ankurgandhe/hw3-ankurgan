package edu.cmu.deiis.annotator;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.NGram;
import edu.cmu.deiis.types.Question;

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
		FSIndex answerIndex = aJCas.getAnnotationIndex(Answer.type);
		Iterator answerIter = answerIndex.iterator();
		FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type);
		Iterator questionIter = questionIndex.iterator();
		FSIndex ngramIndex = aJCas.getAnnotationIndex(NGram.type);
		Iterator ngramIter = ngramIndex.iterator();
		NGram nG1=null; 
		
		Question ques = (Question) questionIter.next();
		ArrayList<String> qNGrams = new ArrayList<String>();
		// Get ngrams in question. nG1 stores the next n-gram
		nG1 = GetNGrams(qNGrams,nG1,ques.getEnd(),ngramIter);
		
		Answer answer;
		double score;
		ArrayList<String> aNGrams = new ArrayList<String>();
		while (answerIter.hasNext()) {
			answer = (Answer) answerIter.next();
			nG1 = GetNGrams(aNGrams,nG1,answer.getEnd(),ngramIter);
			score = ScoreAnswer(aNGrams, qNGrams);
			AnswerScore annotation = new AnswerScore(aJCas);
			annotation.setBegin(answer.getBegin());
			annotation.setEnd(answer.getEnd());
			annotation.setScore(score);
			annotation.setAnswer(answer);
			annotation.addToIndexes();
			aNGrams.clear();
		}
		
	}
	
	private NGram GetNGrams(ArrayList<String> nGrams , NGram nG1, int end,Iterator nGramIter){
		if (nG1!=null)
			if (nG1.getBegin() < end)
				nGrams.add(nG1.getCoveredText());
		while (nGramIter.hasNext()) {
			nG1 = (NGram) nGramIter.next();
			if (nG1.getBegin() > end){
				break;
			}
			nGrams.add(nG1.getCoveredText());
		}
		return nG1;
	}
	
	private double ScoreAnswer(ArrayList<String> qNGrams,ArrayList<String> aNGrams) {
		double score = 0.0;
		int number_of_matches = 0;
		for (String text : aNGrams){
			if (qNGrams.contains(text))
				number_of_matches++;
				
		}
		score = number_of_matches; ///aNGrams.size(); //
		return score;
	}
}
