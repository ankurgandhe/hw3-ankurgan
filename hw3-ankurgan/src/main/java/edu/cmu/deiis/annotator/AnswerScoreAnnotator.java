package edu.cmu.deiis.annotator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.util.JCasUtil;

import org.cleartk.ne.type.NamedEntityMention;
import org.cleartk.token.type.Token;
import org.cleartk.syntax.dependency.type.DependencyNode;
import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.NGram;

import edu.cmu.deiis.types.Question;

/**
 * This Annotator takes the Ngrams, Questions and Answers and matches N-grams to
 * score the answers
 */
public class AnswerScoreAnnotator extends JCasAnnotator_ImplBase {

	public void process(JCas aJCas) {
		// Get annotation indexes
		// Answer Iterator and Lists for answer annotations
		FSIndex answerIndex = aJCas.getAnnotationIndex(Answer.type);
		Iterator answerIter = answerIndex.iterator();

		List<NGram> aNGramList = new ArrayList<NGram>();
		ArrayList<String> aNGramTextList = new ArrayList<String>();
		List<Token> aTokenList = new ArrayList<Token>();
		ArrayList<String> aLemmaTextList = new ArrayList<String>();
		List<NamedEntityMention> aNEList = new ArrayList<NamedEntityMention>(0);
		ArrayList<String> aNETextList = new ArrayList<String>();
		Answer answer;
		// Question
		FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type);
		Iterator questionIter = questionIndex.iterator();

		List<NGram> qNGramList = new ArrayList<NGram>();
		ArrayList<String> qNGramTextList = new ArrayList<String>();
		List<NamedEntityMention> qNEList = new ArrayList<NamedEntityMention>(0);
		ArrayList<String> qNETextList = new ArrayList<String>();
		List<Token> qTokenList = new ArrayList<Token>();
		ArrayList<String> qLemmaTextList = new ArrayList<String>();
		
		Question ques;
		// Scoring
		double score = 0;
		double nGramScore = 0;
		double neScore = 0;
		double lemmaScore = 0;
		double negPenalty = 0;

		// Named Entity from remote cleartk.
		boolean remoteAnnotationStatus = false;

		// Get n-grams in question. We use UIMAfit
		ques = (Question) questionIter.next();
		qNGramList = JCasUtil.selectCovered(NGram.class, ques);
		for (NGram qNGram : qNGramList) {
			qNGramTextList.add(qNGram.getCoveredText());
		}

		// Get named entities in the question.
		// Check the status of the remote annotation service call
		qNEList = JCasUtil.selectCovered(NamedEntityMention.class, ques);
		if (qNEList.size() > 0)
			remoteAnnotationStatus = true;

		if (remoteAnnotationStatus) {
			qNEList = JCasUtil.selectCovered(NamedEntityMention.class, ques);
			for (NamedEntityMention qNE : qNEList) {
				qNETextList.add(qNE.getCoveredText());
			}
			qTokenList = JCasUtil.selectCovered(Token.class, ques);
			for (Token qToken : qTokenList) {
				qLemmaTextList.add(qToken.getLemma());
			
					
			}
		}

		// Iterate over answers and get score
		while (answerIter.hasNext()) {
			answer = (Answer) answerIter.next();
			// Get NgramScore
			aNGramList = JCasUtil.selectCovered(NGram.class, answer);
			for (NGram aNGram : aNGramList) {
				aNGramTextList.add(aNGram.getCoveredText());
			}
			nGramScore = ScoreText(qNGramTextList, aNGramTextList);

			// Get Named Entity and Lemma Score
			if (remoteAnnotationStatus) {
				aNEList = JCasUtil.selectCovered(NamedEntityMention.class,
						answer);
				for (NamedEntityMention aNE : aNEList) {
					aNETextList.add(aNE.getCoveredText());
				}
				neScore = ScoreText(qNETextList, aNETextList);

				aTokenList = JCasUtil.selectCovered(Token.class, answer);
				for (Token aToken : aTokenList) {
					aLemmaTextList.add(aToken.getLemma());
					
				}
				lemmaScore = ScoreText(qLemmaTextList, aLemmaTextList);
				// Negation penalty
				for (String lemma : aLemmaTextList) {
					if (lemma.contains("not")) {
						negPenalty += 1;
					}
				}

			}

			// Calculate final Score
			if (remoteAnnotationStatus)
				score = 0.25 * lemmaScore + 0.5 * nGramScore + 0.25 * neScore
						- 0.05 * negPenalty;
			else
				score = nGramScore;

			score = Math.round(score * 100) / 100.0d;
			// Add to AnswerScore annotations
			AnswerScore annotation = new AnswerScore(aJCas);
			annotation.setBegin(answer.getBegin());
			annotation.setEnd(answer.getEnd());
			annotation.setScore(score);
			annotation.setAnswer(answer);
			annotation.setCasProcessorId("NGram+NE+LemmaMatch");
			annotation.setConfidence(0.8);
			annotation.addToIndexes();
			aNGramTextList.clear();
			aLemmaTextList.clear();
			negPenalty = 0;
		}

	}

	/*
	 * Function to match Question Annotation Text with Answer Annotation Text
	 */
	private double ScoreText(ArrayList<String> qAnnotation,
			ArrayList<String> aAnnotation) {
		double score = 0.0;
		ArrayList<String> annotationCovered = new ArrayList<String>();
		double number_of_matches = 0;
		for (String text : aAnnotation) {
			if (qAnnotation.contains(text) && !annotationCovered.contains(text)) {
				number_of_matches++;
				annotationCovered.add(text);
			}
		}
		score = number_of_matches / qAnnotation.size(); // number_of_matches
		if (score > 1)
			score = 1;

		return score;
	}

}
