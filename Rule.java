import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sreenath on 23/11/2016.
 */
public class Rule {

    public ArrayList<String> attributes;

    public ArrayList<String > values;

    public HashMap<String, String> association;

    public String inferedLabel;

    public Rule(ArrayList<String> attr, ArrayList<String> vals){
        attributes = new ArrayList<String>();
        values = new ArrayList<String>();
        association = new HashMap<String, String>();
        inferedLabel = "Cannot resolve label";

        attributes = attr;
        values = vals;

        for(int i = 0; i < values.size(); i ++){
            association.put(attributes.get(i), values.get(i));
        }

    }
    public Rule(){
        attributes = new ArrayList<String>();
        values = new ArrayList<String>();
        association = new HashMap<String, String>();
        inferedLabel = "Cannot resolve label";

        for(int i = 0; i < values.size(); i ++){
            association.put(attributes.get(i), values.get(i));
        }

    }

    public void addAttribute(String attribute){

        attributes.add(attribute);
    }


    public String toString(){

        String out = "";

        for(Map.Entry<String, String> entry : association.entrySet()){

            out = out + entry.getKey() + ":" + entry.getValue() + " ";

        }
        out = out + "-> " + inferedLabel;
        return out;

    }



}
