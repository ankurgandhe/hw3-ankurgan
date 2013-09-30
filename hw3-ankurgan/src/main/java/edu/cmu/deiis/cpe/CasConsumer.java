package edu.cmu.deiis.cpe;

import java.util.Iterator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceProcessException;

import edu.cmu.deiis.types.Evaluation;

public class CasConsumer extends CasConsumer_ImplBase {

	@Override
	public void processCas(CAS aCas) throws ResourceProcessException {
		// Read the Evaluator index and print out the precision
		JCas aJCas;
		  try {
		    aJCas = aCas.getJCas();
		  } catch (CASException e) {
		    throw new ResourceProcessException(e);
		  }
		FSIndex evaluationIndex = aJCas.getAnnotationIndex(Evaluation.type);
		Iterator evaluationIter = evaluationIndex.iterator();
		if ( evaluationIter.hasNext()){
			Evaluation evl = (Evaluation) evaluationIter.next();
			System.out.println("Precision at " + evl.getN() + ":" + evl.getPrecision() + "\n");
		}
		else
			System.out.println("No Evaluation present");
		
	}

}
