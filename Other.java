import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Other {

    public ArrayList<Sample> sampleSpace;

    private ArrayList<Sample> subSampleSpace;

    public ArrayList<Rule> learnedRules;

    public HashMap<String, ArrayList<String>> attributeValues;

    public HashMap<String, ArrayList<String>> uniqueValuesForAttributes;

    public ArrayList<String> uniqueLabels;

    public ArrayList<String> unusedAttributes;


    private double systemIndex;

    private int sizeOfsampleSpace;

    private int sizeOfSubSpace;

    public Other(ArrayList<Sample> samples){

        unusedAttributes = new ArrayList<String>();
        uniqueLabels = new ArrayList<String>();
        learnedRules = new ArrayList<Rule>();
        sampleSpace = new ArrayList<Sample>();
        subSampleSpace = new ArrayList<Sample>();
        attributeValues = new HashMap<String, ArrayList<String>>();
        uniqueValuesForAttributes = new HashMap<String, ArrayList<String>>();
        systemIndex = 0.0;

        sizeOfSubSpace = samples.size();
        sizeOfsampleSpace = samples.size();
        sampleSpace = samples;
        subSampleSpace = samples;
        unusedAttributes = samples.get(0).attributes;

        setAttributeValues();
        updateSystemIndex();
        Rule rule = new Rule();
        testPrint();
    }

    public void learnRule(Rule rule){

        learnedRules.add(rule);

    }


    public boolean containsOneClass(){

        boolean terminateCondition = true;

        if(subSampleSpace.isEmpty()){
            System.out.println("list sp is empty in method conatinsOneClass");
            return false;
        }

        String label = subSampleSpace.get(0).label;
        for(int i = 0; i < subSampleSpace.size(); i++){

            if(!subSampleSpace.get(i).label.equals(label)){
                terminateCondition = false;
            }

        }
        return terminateCondition;

    }


    public void reduceSubSampleSpace(String attr, String val){

        ArrayList<Sample> tempSpace = new ArrayList<Sample>();

        for(int i = 0; i < subSampleSpace.size(); i ++){

            //if you find an attribute with that value then add it to the temp list
            if(subSampleSpace.get(i).AtoV.get(attr).equals(val)){

                tempSpace.add(subSampleSpace.get(i));

            }

        }

        subSampleSpace.clear();

        for(int i = 0; i < tempSpace.size(); i++){

            subSampleSpace.add(tempSpace.get(i));
        }

    }

    public void updateUsedAttributes(String attri){

        for(int i = 0; i < unusedAttributes.size(); i++){

            if(unusedAttributes.get(i).equals(attri)){
                unusedAttributes.remove(i);
            }
        }

    }

    public String calculateMaxGain(ArrayList<String> potentialAttributes,
                                   HashMap<String , ArrayList<String>> valuesForAttributes, HashMap<String,
            ArrayList<String>> allVals, ArrayList<Sample> data, ArrayList<String> labels){

        //calculate the system index this is the gini index for the labels
        updateSystemIndex();

        //calculate the gini of all the attributes in the current state
        ArrayList<Double> ginis = new ArrayList<Double>();
        HashMap<Double, String> correspondingAttributes = new HashMap<Double, String>();

        for(int i = 0; i < potentialAttributes.size(); i++){

            ginis.add(gini(potentialAttributes.get(i), valuesForAttributes, allVals, data, labels ));

        }

        //find the maximum gini value and return that as the splitting attribute
        ArrayList<Double> gains = new ArrayList<Double>();
        double maxGain = 0.0;

        System.out.println();
        for(int k =0; k < ginis.size(); k++){

            gains.add((systemIndex - ginis.get(k)));
            correspondingAttributes.put(gains.get(k), potentialAttributes.get(k));
            System.out.println("GiniGain of attribute " + potentialAttributes.get(k) + " is: " + gains.get(k));

        }

        for(int m = 0; m < gains.size(); m++){

            if (maxGain < gains.get(m)){

                maxGain = gains.get(m);

            }

        }
        System.out.println("\n"+"Found max Gain at: " + maxGain + " Corresponding to: " + correspondingAttributes.get(maxGain));
        return correspondingAttributes.get(maxGain);

    }
    public  double gini(String attribute, HashMap<String , ArrayList<String>> valuesForAttributes, HashMap<String, ArrayList<String>> allVals, ArrayList<Sample> data, ArrayList<String> labels){

        double gini = 0.0;
        double tmp = 1.0;
        double tmp2 = 1.0;

        ArrayList<Double> sumOfProducts = new ArrayList<Double>();
        double prod =1.0;

        for(int i = 0; i < valuesForAttributes.get(attribute).size(); i++){

            tmp = calculateFrequencyOfValues(valuesForAttributes.get(attribute).get(i), allVals.get(attribute))
            / data.size();


            //iterate over all the unique values
                for (int k = 0; k < labels.size(); k++){

                    double freqOfVale = calculateFrequencyOfValues(valuesForAttributes.get(attribute).get(i), allVals.get(attribute));

                   double tmp3 = (matchTuples(attribute, valuesForAttributes.get(attribute).get(i), labels.get(k)) /  freqOfVale);

                    tmp2 = tmp2 * tmp3;

                }

                prod = tmp * tmp2;
                sumOfProducts.add(prod);
                tmp2 = 1.0;
        }


        for(int l = 0; l < sumOfProducts.size(); l++){

            gini = gini + sumOfProducts.get(l);

        }

        System.out.println("gini value of attribute " + attribute + " is: " + gini);
        return gini;

    }

    public double matchTuples(String attribute, String Val, String label){

        double count = 0.0;
        int indexOfValue = 0;

        for(Sample sample : subSampleSpace){

            indexOfValue = sample.attributes.indexOf(attribute);

           if(sample.values.get(indexOfValue).equals(Val) && sample.label.equals(label)){

               count = count + 1.0;

           }
        }

        return count;


    }

    public void setAttributeValues(){

        // assumes all the data entries have the same number of attributes
        int numberOfAttr = sampleSpace.get(1).attributes.size();
        String attributeVal = "";

        //set the values that the each attribute can take
        for(int i = 0; i < numberOfAttr; i ++){

            ArrayList<String> vals = new ArrayList<String>();
            attributeVal = sampleSpace.get(i).attributes.get(i);

            for(Sample smp : sampleSpace){

                vals.add(smp.values.get(i));

            }

            attributeValues.put(attributeVal, vals);
        }

        //remove duplicates and only keep unique vales for each attribute
        for(Map.Entry<String, ArrayList<String>> entry : attributeValues.entrySet()){
            uniqueValuesForAttributes.put(entry.getKey(), removeDuplicates(entry.getValue()));
        }





    }

    public ArrayList<String> removeDuplicates(ArrayList<String> tmp){

        ArrayList<String> values = new ArrayList<>(tmp);
        HashSet<String> uniqueVals = new HashSet<>();
        uniqueVals.addAll(values);
        values.clear();
        values.addAll(uniqueVals);

        return values;
    }


    public void updateSystemIndex(){

        double gini = 1.0;
        ArrayList<String> labels = new ArrayList<String>();
        for(Sample sam : subSampleSpace){
            labels.add(sam.label);
        }

        //find unique terms in the label
        uniqueLabels = removeDuplicates(labels);

        //iterate over the labels and calculate the gini vale for the system
        for(int i = 0; i < uniqueLabels.size(); i++){

            gini = gini * (calculateFrequencyOfValues(uniqueLabels.get(i),labels)/sizeOfSubSpace);

        }

        systemIndex = gini;

    }

    public double calculateFrequencyOfValues(String val, ArrayList<String> list){

        double freq = 0.0;

        for(int i = 0; i < list.size(); i++){

            if(val.equals(list.get(i))){
                freq = freq + 1.0;
            }

        }

        return freq;

    }

    public void testPrint(){

        //TODO prints uniqueAttributeValues hashmap
        /*for(Map.Entry<String, ArrayList<String>> entry : uniqueAttributeValues.entrySet()){

            for(String attr : entry.getValue()){

                System.out.println("KEY: "+entry.getKey() + " VALUE: " + attr);

            }

        }*/


        //TODO prints the sub sample space
        /*for(int i = 0; i < subSampleSpace.size(); i++){

            System.out.println(subSampleSpace.get(i));

        }*/


        //TODO prints the hashmap of all the variables and their values
        /*for(Map.Entry<String, ArrayList<String>> entry : attributeValues.entrySet()){
            System.out.println("Attribute: " + entry.getKey());
            System.out.println("Values: "+entry.getValue());
            System.out.println();
        }*/


        //TODO prints the unique values for the attributes
        /*for(Map.Entry<String, ArrayList<String>> entry : uniqueValuesForAttributes.entrySet()){
            System.out.println("Attribute: " + entry.getKey());
            System.out.println("Unique Values: "+entry.getValue());
            System.out.println();
        }*/


    }

}
