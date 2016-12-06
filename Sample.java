import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by as17 on 11/18/16.
 */
public class Sample {

    public String label;

    public ArrayList<String> attributes;

    public ArrayList<String> values;

    public HashMap<String, String > AtoV;

    public boolean isTraining;

    public Sample(String lab, ArrayList<String> attr, ArrayList<String> vals, boolean training){


        attributes = new ArrayList<String>();
        values = new ArrayList<String>();
        AtoV = new HashMap<String, String>();

        isTraining = training;
        label = lab;
        attributes = attr;
        values = vals;

        populateHashMap();
    }

    public void populateHashMap(){
        for(int i = 0; i < attributes.size(); i++){

            AtoV.put(attributes.get(i),values.get(i));

        }
    }


    public String toString(){

        String main;

        main = "Label: " + label + " ";

        for(int i = 0; i < attributes.size(); i++){
            main = main + " " + attributes.get(i) + ":" + values.get(i);
        }

        if(isTraining){
            main = main + " (TRAINING)";

        }
        else{
            main = main +  " (TEST)";
        }

        return main;
    }





}
