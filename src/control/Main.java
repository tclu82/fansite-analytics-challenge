package control;

import model.Feature1;
import model.Feature2;
import model.Feature3;
import model.Features;

/**
 * Main class
 */
public class Main {

    /**
     * Main method, execute 4 features
     *
     * @param args
     */
    public static void main(String[] args) {

        ReadFile readFile = new ReadFile();

        Features f1 = new Feature1(readFile);
        f1.execute();

        Features f2 = new Feature2(readFile);
        f2.execute();

//        Features f3 = new Feature3(readFile);
//        f3.execute();





    }
}
