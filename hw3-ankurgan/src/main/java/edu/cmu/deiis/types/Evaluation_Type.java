
/* First created by JCasGen Sat Sep 28 14:42:59 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Stores the result of metric of evaluation for each document
 * Updated by JCasGen Mon Sep 30 15:25:36 EDT 2013
 * @generated */
public class Evaluation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Evaluation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Evaluation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Evaluation(addr, Evaluation_Type.this);
  			   Evaluation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Evaluation(addr, Evaluation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Evaluation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.deiis.types.Evaluation");
 
  /** @generated */
  final Feature casFeat_precision;
  /** @generated */
  final int     casFeatCode_precision;
  /** @generated */ 
  public float getPrecision(int addr) {
        if (featOkTst && casFeat_precision == null)
      jcas.throwFeatMissing("precision", "edu.cmu.deiis.types.Evaluation");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_precision);
  }
  /** @generated */    
  public void setPrecision(int addr, float v) {
        if (featOkTst && casFeat_precision == null)
      jcas.throwFeatMissing("precision", "edu.cmu.deiis.types.Evaluation");
    ll_cas.ll_setFloatValue(addr, casFeatCode_precision, v);}
    
  
 
  /** @generated */
  final Feature casFeat_n;
  /** @generated */
  final int     casFeatCode_n;
  /** @generated */ 
  public int getN(int addr) {
        if (featOkTst && casFeat_n == null)
      jcas.throwFeatMissing("n", "edu.cmu.deiis.types.Evaluation");
    return ll_cas.ll_getIntValue(addr, casFeatCode_n);
  }
  /** @generated */    
  public void setN(int addr, int v) {
        if (featOkTst && casFeat_n == null)
      jcas.throwFeatMissing("n", "edu.cmu.deiis.types.Evaluation");
    ll_cas.ll_setIntValue(addr, casFeatCode_n, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Evaluation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_precision = jcas.getRequiredFeatureDE(casType, "precision", "uima.cas.Float", featOkTst);
    casFeatCode_precision  = (null == casFeat_precision) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_precision).getCode();

 
    casFeat_n = jcas.getRequiredFeatureDE(casType, "n", "uima.cas.Integer", featOkTst);
    casFeatCode_n  = (null == casFeat_n) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_n).getCode();

  }
}



    