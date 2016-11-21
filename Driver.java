
import java.io.*;
import java.util.ArrayList;

/**
 * Created by as17 on 11/15/16.
 */
public class Driver {

    public static String trainingFile = "";
    public static String testingFile = "";

    public static ArrayList<Sample> trainingSet = new ArrayList<Sample>();
    public static ArrayList<Sample> testSet = new ArrayList<Sample>();

    public static void main(String[] args) throws IOException {

        //check the number of params
        if(args[0].equals(null) || args[1].equals(null)){
            System.out.println("ERROR INCORRECT PARAMETERS");
            System.exit(0);
        }
        else{
            trainingFile = args[0];
            testingFile = args[1];
        }

        setTrainingSamples();
        setTestSamples();

        System.out.println("Training set size: " + trainingSet.size() + " Test set size: " + testSet.size());

    }

    public static void setTrainingSamples() throws IOException{

        BufferedReader reader = new BufferedReader(new FileReader(trainingFile));
        String ln = reader.readLine();


        String label = "";
        while(ln != null){

            ArrayList<String> values = new ArrayList<String>();
            ArrayList<String> attributes = new ArrayList<String>();

            //split the line
            String[] tmp;
            tmp = ln.split(" ");

            //iterate over the split items
            for(String str : tmp){
                if(str.contains(":")){
                    String[] tmp2;
                    tmp2 = str.split(":");

                    attributes.add(tmp2[0]);
                    values.add(tmp2[1]);
                }
                else{
                    label = str;
                }
            }
            //create a new sample
            Sample newSample = new Sample(label, attributes, values, true);

            // add this sample
            trainingSet.add(newSample);

            ln = reader.readLine();
        }
    }

    public static void setTestSamples() throws IOException{

        BufferedReader reader = new BufferedReader(new FileReader(testingFile));
        String ln = reader.readLine();


        String label = "";

        while(ln != null){

            ArrayList<String> values = new ArrayList<String>();
            ArrayList<String> attributes = new ArrayList<String>();

            //split the line
            String[] tmp;
            tmp = ln.split(" ");

            //iterate over the split items
            for(String str : tmp){
                if(str.contains(":")){
                    String[] tmp2;
                    tmp2 = str.split(":");

                    attributes.add(tmp2[0]);
                    values.add(tmp2[1]);
                }
                else{
                    label = str;
                }
            }
            //create a new sample
            Sample newSample = new Sample(label, attributes, values, false);

            // add this sample
            testSet.add(newSample);

            ln = reader.readLine();
        }
    }



}
