package zac.com;

import java.io.*;
import java.util.*;

/**
 * Created by zac on 4/1/17.
 */
public class Features {


    /**
     * A map contains Host/IP name as key and its frequency as value for Feature 1.
     */
    Map<String, Integer> countHostOrIPFrequencyMap;
    /**
     * A map contains resource as key and its frequency as value for Feature 2.
     */
    Map<String, Integer> resourceUsedFrequency;

    /**
     * Constructor
     */
    Features() {

        countHostOrIPFrequencyMap = new HashMap<>();

        resourceUsedFrequency = new HashMap<>();




        /** Run execution, throw exception if file not found. */
        try {
            readFile();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found, please check your input \"log.txt\"");
        }
    }


    /**
     * Read the file, and count each Host/IP's frequency then put it into the countHostOrIPFrequencyMap
     * For the countHostOrIPFrequencyMap, key is Host/IP's name and the frequency is its value.
     * <p>
     * Run time: O(mn)
     * m is the line quantity of input file, and n is the average length of lines.
     *
     * @throws FileNotFoundException if log.txt not found.
     */
    private void readFile() throws FileNotFoundException {
        /** Try to read the input file, and catch the file not found exception. */
        try {
            File file = new File("/Users/zac/Desktop/InsightDataScience/src/log.txt");
            /** BufferReader has bigger buffer and more efficient when reading huge file. */
            BufferedReader br = new BufferedReader(new FileReader(file));
            /** A variable to contain each line. */
            String line;

            /** Read each line from BufferReader. */
            while ((line = br.readLine()) != null) {
                /** Split the line with " ". */
                String[] strs = line.split(" ");

                String hostOrIP = strs[0];

                String resource = strs[6];


                // Feature 1

                /** If the key is not in the map, put the key and 1 as value. */
                if (!countHostOrIPFrequencyMap.containsKey(hostOrIP))
                    countHostOrIPFrequencyMap.put(hostOrIP, 1);
                /** Else add 1 to its frequency. */
                else
                    countHostOrIPFrequencyMap.put(hostOrIP, countHostOrIPFrequencyMap.get(hostOrIP) + 1);

                // Feature 2

                /** If the key is not in the map, put the key and 1 as value. */
                if (!resourceUsedFrequency.containsKey(resource))
                    resourceUsedFrequency.put(resource, 1);
                /** Else add 1 to its frequency. */
                else
                    resourceUsedFrequency.put(resource, resourceUsedFrequency.get(resource) + 1);


            }
        } catch (IOException e) {
            System.out.println("File is not found.");
        }
    }

    void executeFeature1() throws FileNotFoundException {
        Feature1 f1 = new Feature1(countHostOrIPFrequencyMap);

        Deque<Map.Entry<String, Integer>> deque = f1.findTheTop10MostAcitveDescending();

        f1.executeFeature1();

    }




}

