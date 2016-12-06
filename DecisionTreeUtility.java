import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by sreenath on 5/12/2016.
 */
public class DecisionTreeUtility {



    public static String findBestSplittingAttribute(ArrayList<Sample> data, ArrayList<String> potentialAttributes){

        double gini_D = 0.0;
        double temp_D = 0.0;
        double giniGain = 0.0;

        System.out.println("Finding best splitting attribute" + "\n");

        String bestSplittingAttribute = "";

        ArrayList<Double> gains = new ArrayList<Double>();

        ArrayList<Double> ginis = new ArrayList<Double>();

        for(int i = 0; i < potentialAttributes.size(); i++){

            //get the first attribute
            String attribute = potentialAttributes.get(i);

            //get all the values for this attribute
            ArrayList<String> values = new ArrayList<String>(getAllValuesForAttribute(data, attribute));

            //get the unique attribute values
            ArrayList<String> uniqueValues = new ArrayList<String>(getUniqueItemsInList(values));

            //iterate through all the values of that attribute and compute its respective gini values
            for(int j = 0; j < uniqueValues.size(); j++){

                String value = uniqueValues.get(i);

                //split the data at this value
                ArrayList<Sample> splitData = new ArrayList<Sample>(getSubDataSet(attribute, value, data));

                //multiply the freq of this value with the gini of the split data
                gini_D = gini_D + ( (calculateFrequency(value,values)/splitData.size())  *  gini(splitData) );

            }

            giniGain = gini(data) - gini_D;

            gains.add(giniGain);
        }

        double maxGain = Double.MIN_VALUE;
        int index = 0;

        for(int i = 0; i < gains.size(); i++){

            if(maxGain < gains.get(i)){
                maxGain = gains.get(i);
                index = i;
            }

        }

        //set the best splitting attribute
        bestSplittingAttribute = potentialAttributes.get(index);

        System.out.println("Best Attribute: " + bestSplittingAttribute);
        return bestSplittingAttribute;

    }

    public static double gini(ArrayList<Sample> data){

        double gini = 0.0;

        ArrayList<String> labels = new ArrayList<String>();

        //extract all the predictors in the given data set
        for(int i = 0; i < data.size(); i++){

            labels.add(data.get(i).label);

        }

        //get the unique labels

        ArrayList<String> uniqueLabels = new ArrayList<String>(getUniqueItemsInList(labels));

        for(int i = 0; i < uniqueLabels.size(); i++){

            double tmp = calculateFrequency(uniqueLabels.get(i), labels)/(double)data.size();
            gini = gini - (tmp * tmp);

        }

        gini = gini + 1;

        return gini;


    }

    public static double calculateFrequency(String val, ArrayList<String> list){

        double freq = 0.0;

        for(int i = 0; i < list.size(); i++){

            if(val.equals(list.get(i))){
                freq = freq + 1.0;
            }

        }
        return freq;
    }

    public static ArrayList<String> getUniqueItemsInList(ArrayList<String> list){

        ArrayList<String> values = new ArrayList<>(list);
        HashSet<String> uniqueVals = new HashSet<>();
        uniqueVals.addAll(values);
        values.clear();
        values.addAll(uniqueVals);

        return values;
    }

    public static ArrayList<Sample> getSubDataSet(String attribute, String value, ArrayList<Sample> sampleSpace){

        ArrayList<Sample> subSampleSpace = new ArrayList<Sample>();

        for(int i = 0; i < sampleSpace.size(); i ++){

            //if you find an attribute with that value then add it to the temp list
            if(sampleSpace.get(i).AtoV.get(attribute).equals(value)){

                subSampleSpace.add(sampleSpace.get(i));

            }

        }

        return subSampleSpace;
    }

    public static ArrayList<String> getAllValuesForAttribute(ArrayList<Sample> data, String attribute){

        ArrayList<String> values = new ArrayList<String>();

        for(int i = 0; i < data.size(); i++){

            values.add(data.get(i).AtoV.get(attribute));

        }

        return values;

    }

}
