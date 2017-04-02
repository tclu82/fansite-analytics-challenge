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
            File file = new File("src/log.txt");
            /** BufferReader has bigger buffer and more efficient when reading huge file. */
            BufferedReader br = new BufferedReader(new FileReader(file));
            /** A variable to contain each line. */
            String line;


            /** Read each line from BufferReader. */
            while ((line = br.readLine()) != null) {
                /** Split the line with " ". */
                String[] strs = line.split(" ");


                // Feature 1

                String hostOrIP = strs[0];
                /** If the key is not in the map, put the key and 1 as value. */
                if (!countHostOrIPFrequencyMap.containsKey(hostOrIP))
                    countHostOrIPFrequencyMap.put(hostOrIP, 1);
                /** Else add 1 to its frequency. */
                else
                    countHostOrIPFrequencyMap.put(hostOrIP, countHostOrIPFrequencyMap.get(hostOrIP) + 1);




                // Feature 2

                String resourceName = strs[6];

                String lastElement = strs[strs.length-1];

                int resourceSize = Character.isDigit(lastElement.charAt(0)) ?
                        Integer.parseInt(lastElement) : 0;

//                ResourceConsume resourceConsume = new ResourceConsume(resourceName, resourceSize);

                /** If the key is not in the map, put the key and 1 as value. */
                if (!resourceUsedFrequency.containsKey(resourceName))
                    resourceUsedFrequency.put(resourceName, resourceSize);
                /** Else add 1 to its frequency. */
                else
                    resourceUsedFrequency.put(resourceName, resourceUsedFrequency.get(resourceName) + resourceSize);

            }


        } catch (IOException e) {
            System.out.println("File is not found.");
        }
    }





    void executeFeature1() throws FileNotFoundException {

        Feature1 f1 = new Feature1(countHostOrIPFrequencyMap);

    }

    void executeFeature2() throws FileNotFoundException {

//        PriorityQueue<Map.Entry<String, Integer>> pq
//                = findTheTop10MostAcitveDescending(resourceUsedFrequency);

        Feature2 f2 = new Feature2(resourceUsedFrequency);

    }



//    /**
//     * Find out top 10 most active entries from countHostOrIPFrequencyMap
//     *
//     * Run time: O(nlgn)
//     * n is the number of countHostOrIPFrequencyMap's entry set.
//     * In this PriorityQueue I uses minimum heap, offer and poll are both O(nlgn).
//     *
//     * @return An Deque of Map.Entry<String, Integer> with top 10 most active
//     */
//    private PriorityQueue<Map.Entry<String, Integer>> findTheTop10MostAcitveDescending(Map<String, Integer> map) {
//
//        /** Use PriorityQueue with size 10 to find out top 10 most active Host/IP. */
//        PriorityQueue<Map.Entry<String, Integer>> theTop10AescendingOrder
//                = new PriorityQueue<>(10, new Comparator<Map.Entry<String, Integer>>() {
//            @Override
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                /** Stores top 10 entries in ascending order. */
//                return o1.getValue().compareTo(o2.getValue());
//            }
//        });
//
//        for (Map.Entry<String, Integer> entry : map.entrySet()) {
//            theTop10AescendingOrder.offer(entry);
//            /** If the size of PriorityQueue is greater than 10, poll the smallest 1 and rearrange the order. */
//            if (theTop10AescendingOrder.size() > 10)
//                theTop10AescendingOrder.poll();
//        }
//        return theTop10AescendingOrder;
//    }


//    /**
//     * Inner class for Feature 2
//     */
//    class ResourceConsume {
//        String resourceName;
//        int resourceSize;
//
//        ResourceConsume(String name, int size) {
//            this.resourceName = name;
//            this.resourceSize = size;
//        }
//    }

}

