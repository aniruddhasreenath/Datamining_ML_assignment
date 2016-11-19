import java.util.ArrayList;

/**
 * Created by as17 on 11/18/16.
 */
public class Sample {

    public int label;

    public ArrayList<Integer> attributes;

    public ArrayList<Integer> values;

    public boolean isTraining;

    public Sample(int lab, ArrayList<Integer> attr, ArrayList<Integer> vals, boolean training){


        attributes = new ArrayList<Integer>();
        values = new ArrayList<Integer>();

        isTraining = training;
        label = lab;
        attributes = attr;
        values = vals;
    }





}
