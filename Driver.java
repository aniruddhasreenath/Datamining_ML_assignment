
import java.io.*;

/**
 * Created by as17 on 11/15/16.
 */
public class Driver {

    public static String trainingFile = "";
    public static String testingFile = "";

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

        BufferedReader reader = new BufferedReader(new FileReader(trainingFile));

        String ln = reader.readLine();

        while(ln != null){



        }


    }



}
