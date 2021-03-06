/*
   Copyright (C) 2005-2012, by the President and Fellows of Harvard College.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   Dataverse Network - A web application to share, preserve and analyze research data.
   Developed at the Institute for Quantitative Social Science, Harvard University.
   Version 3.0.
*/
package edu.harvard.iq.dvn.ingest.org.thedata.statdataio.metadata;

import static java.lang.System.*;
import java.util.*;
import java.util.logging.*;


/**
 * A helper class that combines value-label data in a data file with 
 * missing-value data and actual tabulation results and creates a 
 * unified value-label table.
 * 
 * @author Akio Sone
 */
public class MetadataHelper {

   private static Logger dbgLog =
       Logger.getLogger(MetadataHelper.class.getPackage().getName());

    /**
     * Returns the merged results from the given value-label data, 
     * missing-value data, and tabulation results.
     * 
     * @param valueLabeli a given variable's value-label table recorded
     * in the given data file.
     *
     * @param catStati a given variable's frequency table.
     *
     * @param missingValuei a given variabl's missing-value data.
     *
     * @return a unified value-label table as a <code>List</code> of 
     * <code>CategoricalStatistic</code> objects.
     */
    public static List<CategoricalStatistic> getMergedResult(
            Map<String, String> valueLabeli,
            Map<String, Integer> catStati,
            List<String> missingValuei,
            Integer nullValueCount
            ) {
      
      dbgLog.fine("MetadataHelper: Inside getMergedResult");
      dbgLog.fine("valueLabeli = " + valueLabeli);
      
        // protection block
        if (missingValuei == null){
            missingValuei = new ArrayList<String>();
        }
//        if (valueLabeli == null){
//
//        }
        //
        int caseTypeNumber = 0;
        Set <String> catStatiKeys = null;
        Set <String> valueLabeliKeys = null;
        Set <String> mvs = new TreeSet(missingValuei);
        
        dbgLog.finer("mvs="+mvs);
        
        if ((valueLabeli == null) && (catStati ==null)) {
          return null;
        }
        else if ((valueLabeli != null)&& (catStati !=null)) {
            // create duplicates
          catStatiKeys= new TreeSet(catStati.keySet());
          valueLabeliKeys= new TreeSet(valueLabeli.keySet());


          dbgLog.finer("valueLabeliKeys="+valueLabeliKeys);
          
          // get the set-operation case number
          caseTypeNumber = getRelationsBetweenTwoSets(catStatiKeys, valueLabeliKeys);
          dbgLog.finer("caseTypeNumber="+caseTypeNumber +"\n\n");
        }
        else if (valueLabeli == null) {
          catStatiKeys= new TreeSet(catStati.keySet());
          caseTypeNumber = 6;
        }
        else if (catStati == null) {
          valueLabeliKeys= new TreeSet(valueLabeli.keySet());
          caseTypeNumber = 7;
        }

        // merged result
        List<CategoricalStatistic> merged = new ArrayList<CategoricalStatistic>();
        Set<String> included = new TreeSet<String>();

        dbgLog.fine("key values = " + valueLabeliKeys);

        switch (caseTypeNumber){
            case 1:
                dbgLog.finer("case 1: no intersection");
                // V side
                for (String kv: valueLabeliKeys){

                  
                  
                    CategoricalStatistic cs = new CategoricalStatistic();
                    cs.setValue(kv);
                    cs.setLabel(valueLabeli.get(kv));
                    cs.setFrequency(0);

                    if (mvs.contains(kv)){
                        cs.setMissingValue(true);
                        included.add(kv);
                    }
                    merged.add(cs);
                }
                // C side
                for (String kv:catStatiKeys){
                    CategoricalStatistic cs = new CategoricalStatistic();
                    cs.setValue(kv);
                    cs.setLabel(null);
                    cs.setFrequency(catStati.get(kv));
                    if (mvs.contains(kv)){
                        cs.setMissingValue(true);
                        included.add(kv);
                    }
                    merged.add(cs);
                }
                dbgLog.finer("case 1: merged="+merged);
                break;
            case 2:
                dbgLog.finer("case 2: C intersect with V");
                // C & V part
                dbgLog.finer("C(initial)="+catStatiKeys);
                dbgLog.finer(catStati.values().toString());
                Set<String>diffKeysCat = new TreeSet(catStatiKeys);
                catStatiKeys.retainAll(valueLabeliKeys);
                dbgLog.finer("C and V="+catStatiKeys);
                for (String kv:catStatiKeys){
                    CategoricalStatistic cs = new CategoricalStatistic();
                    cs.setValue(kv);
                    cs.setLabel(valueLabeli.get(kv));
                    cs.setFrequency(catStati.get(kv));
                    if (mvs.contains(kv)){
                        cs.setMissingValue(true);
                        included.add(kv);
                    }
                    merged.add(cs);
                }
                dbgLog.finer(catStati.values().toString());
                dbgLog.finer("merged(C & V only)="+merged);
                // V-only part
                valueLabeliKeys.removeAll(catStatiKeys);
                dbgLog.finer("V only="+valueLabeliKeys);
                for (String kv: valueLabeliKeys){
                    CategoricalStatistic cs = new CategoricalStatistic();
                    cs.setValue(kv);
                    cs.setLabel(valueLabeli.get(kv));
                    cs.setFrequency(0);
                    if (mvs.contains(kv)){
                        cs.setMissingValue(true);
                        included.add(kv);
                    }
                    merged.add(cs);
                }
                dbgLog.finer("merged(V-only)="+merged);
                dbgLog.finer(catStati.values().toString());
                // C-only part
                diffKeysCat.removeAll(catStatiKeys);
                dbgLog.finer("C only="+diffKeysCat);
                dbgLog.finer(catStati.values().toString());
                for (String kv: diffKeysCat){
                    CategoricalStatistic cs = new CategoricalStatistic();
                    cs.setValue(kv);
                    cs.setLabel(null);
                    cs.setFrequency(catStati.get(kv));
                    if (mvs.contains(kv)){
                        cs.setMissingValue(true);
                        included.add(kv);
                    }
                    merged.add(cs);
                }
                dbgLog.finer("case 2: merged="+merged);
                break;
            case 3:
                dbgLog.finer("case 3: V inclues C");
                // V only part
                Set<String>diffKeysV = new TreeSet(valueLabeliKeys);
                diffKeysV.removeAll(catStatiKeys);
                for (String kv: diffKeysV){
                    CategoricalStatistic cs = new CategoricalStatistic();
                    cs.setValue(kv);
                    cs.setLabel(valueLabeli.get(kv));
                    cs.setFrequency(0);
                    if (mvs.contains(kv)){
                        cs.setMissingValue(true);
                        included.add(kv);
                    }
                    merged.add(cs);
                }
                dbgLog.finer("merged(V-only)="+merged);
                // V & C part (== C)
                for (String kv:catStatiKeys){
                    CategoricalStatistic cs = new CategoricalStatistic();
                    cs.setValue(kv);
                    cs.setLabel(valueLabeli.get(kv));
                    cs.setFrequency(catStati.get(kv));
                    if (mvs.contains(kv)){
                        cs.setMissingValue(true);
                        included.add(kv);
                    }

                    merged.add(cs);
                }
                dbgLog.finer("\ncase 3: merged="+merged);
                break;
            case 4:
                dbgLog.finer("case 4: C includes V");
                // C only part
                Set<String>diffKeysC = new TreeSet(catStatiKeys);
                diffKeysC.removeAll(valueLabeliKeys);
                for (String kv: diffKeysC){
                    CategoricalStatistic cs = new CategoricalStatistic();
                    cs.setValue(kv);
                    cs.setLabel(null);
                    cs.setFrequency(catStati.get(kv));
                    if (mvs.contains(kv)){
                        cs.setMissingValue(true);
                        included.add(kv);
                    }
                    merged.add(cs);
                }
                dbgLog.finer("merged(C-only)="+merged);
                // C & V part (== V)
                for (String kv:valueLabeliKeys){
                    CategoricalStatistic cs = new CategoricalStatistic();
                    cs.setValue(kv);
                    cs.setLabel(valueLabeli.get(kv));
                    cs.setFrequency(catStati.get(kv));
                    if (mvs.contains(kv)){
                        cs.setMissingValue(true);
                        included.add(kv);
                    }
                    merged.add(cs);
                }
                dbgLog.finer("\ncase 4: merged="+merged);
                break;
            case 5:
                dbgLog.finer("case 5: C == V");

                // V side
                for (String kv: valueLabeliKeys){

                    CategoricalStatistic cs = new CategoricalStatistic();
                    cs.setValue(kv);
                    cs.setLabel(valueLabeli.get(kv));
                    cs.setFrequency(catStati.get(kv));

                    if (mvs.contains(kv)){
                        cs.setMissingValue(true);
                        included.add(kv);
                    }
                    merged.add(cs);
                }

                dbgLog.finer("case 5: merged="+merged);
                break;
            case 6:
                // V is null == C only case
                dbgLog.finer("case 6: V is null");
                // C side
                for (String kv:catStatiKeys){
                    CategoricalStatistic cs = new CategoricalStatistic();
                    cs.setValue(kv);
                    cs.setLabel(null);
                    cs.setFrequency(catStati.get(kv));
                    if (mvs.contains(kv)){
                        cs.setMissingValue(true);
                        included.add(kv);
                    }

                    merged.add(cs);
                }
                break;
            case 7:
                // C is null == V only case
                dbgLog.finer("case 7: C is null");
                // V side
                for (String kv: valueLabeliKeys){

                    CategoricalStatistic cs = new CategoricalStatistic();
                    cs.setValue(kv);
                    cs.setLabel(valueLabeli.get(kv));
                    cs.setFrequency(0);

                    if (mvs.contains(kv)){
                        cs.setMissingValue(true);
                        included.add(kv);
                    }
                    merged.add(cs);
                }
                break;
            default:

        }  // end of switch
        // missing values

        mvs.removeAll(included);
        dbgLog.finer("not called missing values:"+mvs);
        if (!mvs.isEmpty()){
            for (String mv: mvs){
                CategoricalStatistic csmv = new CategoricalStatistic();
                csmv.setValue(mv);
                csmv.setLabel(null);
                csmv.setFrequency(0);
                csmv.setMissingValue(true);
                merged.add(csmv);
            }
        }
        
        if (nullValueCount != null && nullValueCount.intValue() > 0) {
            CategoricalStatistic nullValueAsMissing = new CategoricalStatistic();
            nullValueAsMissing.setValue(".");
            nullValueAsMissing.setLabel(null);
            nullValueAsMissing.setFrequency(nullValueCount.intValue());
            nullValueAsMissing.setMissingValue(true);
            
            merged.add(nullValueAsMissing);
        }
        
        dbgLog.finer("merged"+merged);
        return merged;
    }





    /**
     * Returns the relationship between the recorded value-label table
     * and actual tabulation in <code>integer</code> ranging from 0 to 7.
     * 
     * @param setC  the set of values of the tallied frequency table
     *
     * @param setV  the set of values of the value-label table recoded in the
     * data file
     * 
     * @return an <code>integer</code> value between 0 and 7.
     */
    public static int getRelationsBetweenTwoSets(Set<String> setC, Set<String> setV){
        int relation = 0;
        Set<String> newC = new TreeSet<String>(setC);
        Set<String> newV = new TreeSet<String>(setV);
        dbgLog.finer("newC:before="+newC);
        dbgLog.finer("newV:before="+newV);
        // check the intersection
        boolean Rintersection = newC.retainAll(newV);
        // if no intersection case, newC becomes empty
        dbgLog.finer("newC:after="+newC);
        dbgLog.finer("newV:after="+newV);
        if (newC.size()==0){
            // no intersection element: case #1
            dbgLog.finer("no intersections between the two: case #1");
            dbgLog.finer("newC="+newC);
            dbgLog.finer("newV="+newV);
            relation = 1;
        } else {
            // some intersection exists
            dbgLog.finer("some intersections between the two sets");
            dbgLog.finer("difference=("+ (setC.size() - newC.size())+") newC="+newC);
            if (newC.containsAll(newV) && newC.containsAll(setC)){
                // here newC is the intersection; SetC is used because newC is modified
                dbgLog.finer(" C == V: case #5 :");
                dbgLog.finer("newC="+newC);
                relation=5;
            } else {
                dbgLog.finer(" C != V case:");
                dbgLog.finer("C contains V or V contains C or have the intersection");
                dbgLog.finer("setC="+setC);
                dbgLog.finer("newV="+newV);
                if (setC.containsAll(newV)){
                    dbgLog.finer("newC contains newV: case # 4");
                    dbgLog.finer("setC="+setC);
                    dbgLog.finer("newV="+newV);
                    relation = 4;
                } else if (newV.containsAll(setC)){
                    dbgLog.finer("newV contains newC: case # 3");
                    dbgLog.finer("newV="+newV);
                    dbgLog.finer("setC="+setC);
                    relation = 3;
                } else {
                    Set<String> setC2 = new TreeSet<String>(setC);
                    setC2.removeAll(newC);
                    newV.removeAll(newC);
                    if (!setC2.isEmpty() && !newV.isEmpty()){
                        dbgLog.finer("C and V partially intersect: case #2");
                        dbgLog.finer("setC="+setC2);
                        dbgLog.finer("newV="+newV);
                        relation = 2;
                    }

                }
            }
        }

        dbgLog.fine("relation="+relation);

        return relation;
    }

}

