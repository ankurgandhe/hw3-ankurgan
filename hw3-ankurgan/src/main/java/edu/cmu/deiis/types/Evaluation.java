

/* First created by JCasGen Sat Sep 28 14:42:59 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Stores the result of metric of evaluation for each document
 * Updated by JCasGen Sat Sep 28 14:42:59 EDT 2013
 * XML source: C:/Users/gandhe/git/hw3-ankurgan/hw3-ankurgan/src/main/resources/descriptors/deiis_types.xml
 * @generated */
public class Evaluation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Evaluation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Evaluation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Evaluation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Evaluation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Evaluation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: precision

  /** getter for precision - gets Stores the precision@N for the given document, where N is number of correct answers
   * @generated */
  public float getPrecision() {
    if (Evaluation_Type.featOkTst && ((Evaluation_Type)jcasType).casFeat_precision == null)
      jcasType.jcas.throwFeatMissing("precision", "edu.cmu.deiis.types.Evaluation");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((Evaluation_Type)jcasType).casFeatCode_precision);}
    
  /** setter for precision - sets Stores the precision@N for the given document, where N is number of correct answers 
   * @generated */
  public void setPrecision(float v) {
    if (Evaluation_Type.featOkTst && ((Evaluation_Type)jcasType).casFeat_precision == null)
      jcasType.jcas.throwFeatMissing("precision", "edu.cmu.deiis.types.Evaluation");
    jcasType.ll_cas.ll_setFloatValue(addr, ((Evaluation_Type)jcasType).casFeatCode_precision, v);}    
  }

    