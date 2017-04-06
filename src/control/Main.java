package control;

import model.*;

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
        /** Read the input file and pass to 4 features */
        ReadFile readFile = new ReadFile();

        Features f1 = new Feature1(readFile);
        f1.execute();

        Features f2 = new Feature2(readFile);
        f2.execute();

        Features f3 = new Feature3(readFile);
        f3.execute();

        Features f4 = new Feature4(readFile);
        f4.execute();
    }
}
