

/* First created by JCasGen Wed Sep 11 13:44:28 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



import org.apache.uima.jcas.cas.FSList;


/** 
 * Updated by JCasGen Mon Sep 23 17:32:53 EDT 2013
 * XML source: C:/Users/gandhe/git/hw2-ankurgan/hw2-ankurgan/src/main/resources/descriptors/deiis_types.xml
 * @generated */
public class Question extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Question.class);
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
  protected Question() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Question(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Question(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Question(JCas jcas, int begin, int end) {
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
  //* Feature: tokenList

  /** getter for tokenList - gets Stores the tokens covering in the question annotation
   * @generated */
  public FSList getTokenList() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_tokenList == null)
      jcasType.jcas.throwFeatMissing("tokenList", "edu.cmu.deiis.types.Question");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_tokenList)));}
    
  /** setter for tokenList - sets Stores the tokens covering in the question annotation 
   * @generated */
  public void setTokenList(FSList v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_tokenList == null)
      jcasType.jcas.throwFeatMissing("tokenList", "edu.cmu.deiis.types.Question");
    jcasType.ll_cas.ll_setRefValue(addr, ((Question_Type)jcasType).casFeatCode_tokenList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: ngramList

  /** getter for ngramList - gets Stores the n-grams covering in the question annotation
   * @generated */
  public FSList getNgramList() {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_ngramList == null)
      jcasType.jcas.throwFeatMissing("ngramList", "edu.cmu.deiis.types.Question");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Question_Type)jcasType).casFeatCode_ngramList)));}
    
  /** setter for ngramList - sets Stores the n-grams covering in the question annotation 
   * @generated */
  public void setNgramList(FSList v) {
    if (Question_Type.featOkTst && ((Question_Type)jcasType).casFeat_ngramList == null)
      jcasType.jcas.throwFeatMissing("ngramList", "edu.cmu.deiis.types.Question");
    jcasType.ll_cas.ll_setRefValue(addr, ((Question_Type)jcasType).casFeatCode_ngramList, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    