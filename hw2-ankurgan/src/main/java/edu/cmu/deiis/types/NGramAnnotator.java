package edu.cmu.deiis.types;

import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Token;
import edu.cmu.deiis.types.NGram;

/**
 * ngram annotator that detects tokens using Java regular expressions.
 */
public class NGramAnnotator extends JCasAnnotator_ImplBase {

	public void process(JCas aJCas) {
		// search for tokens
		FSIndex tokenIndex = aJCas.getAnnotationIndex(Token.type);
		Iterator tokenIter = tokenIndex.iterator();

		FSIndex answerIndex = aJCas.getAnnotationIndex(Answer.type);
		Iterator answerIter = answerIndex.iterator();

		FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type);
		Iterator questionIter = questionIndex.iterator();
		
		if ( !tokenIter.hasNext())
			return;
		
		Token t1 = (Token) tokenIter.next();
		Question q = (Question)questionIter.next();
		//Mark n-grams in the question
		t1 = MarkNgrams(aJCas,t1,tokenIter, q.getEnd());
		// Mark n-grams in the answers
		Answer ans;
		while (answerIter.hasNext()) {
			ans = (Answer)answerIter.next();
			t1 = MarkNgrams(aJCas,t1,tokenIter, ans.getEnd());
		}
		
	}

	public Token MarkNgrams(JCas aJCas,Token t1, Iterator tokenIter, int end) {
		Token tm1=null,t2;
		int counter=0;
		//Uni-gram
		NGram annotation = new NGram(aJCas);
		annotation.setBegin(t1.getBegin());
		annotation.setEnd(t1.getEnd());
		annotation.addToIndexes();
		
		
		while (tokenIter.hasNext()) {
			t2 = (Token) tokenIter.next();
			if (t2.getBegin() > end){
				t1=t2;
				break;
			}
			//Tri-gram
			if (tm1!=null){
				annotation = new NGram(aJCas);
				annotation.setBegin(tm1.getBegin());
				annotation.setEnd(t2.getEnd());
				annotation.addToIndexes();
			}
			//Bi-gram
			annotation = new NGram(aJCas);
			annotation.setBegin(t1.getBegin());
			annotation.setEnd(t2.getEnd());
			annotation.addToIndexes();
			//Uni-gram
			annotation = new NGram(aJCas);
			annotation.setBegin(t2.getBegin());
			annotation.setEnd(t2.getEnd());
			annotation.addToIndexes();
			tm1 = t1;
			t1 = t2;
			counter++;
		}
		System.out.println(counter);
		return t1;
	}
}
