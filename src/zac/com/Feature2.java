package zac.com;

import java.io.*;
import java.util.*;

/**
 * Created by zac on 4/1/17.
 */
public class Feature2 {
    /** A map contains Host/IP name as key and its frequency as value. */
    Map<String, Integer> countHostOrIPFrequencyMap;

    /**
     * Constructor
     */
    Feature2() {
        countHostOrIPFrequencyMap = new HashMap<>();
        /** Run execution, throw exception if file not found. */
        try { executeFeature1(); }

        catch (FileNotFoundException e) {
            System.out.println("File Not Found, please check your input \"log.txt\"");
        }
    }

    /**
     * Helper method of constructor, read the file and find out top 10 most active Host/IP
     *
     * @throws FileNotFoundException if log.txt not found
     */
    private void executeFeature1() throws FileNotFoundException {
        readFile();
        Deque<Map.Entry<String, Integer>> theTop10MostAcitveDescending = findTheTop10MostAcitveDescending();

        /** Print out the result. */
        Writer writer = null;

        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("hosts.txt"), "utf-8"));

            while (!theTop10MostAcitveDescending.isEmpty()) {
                Map.Entry<String, Integer> entry = theTop10MostAcitveDescending.pop();
                writer.write(entry.getKey() + ", " + entry.getValue() + "\n");
            }
        }
        catch (IOException ex) { System.out.println("IO exception: " + ex); }

        finally { try { writer.close(); } catch (Exception ex) { System.out.println("Output file can't be closed: " + ex); } }
    }

    /**
     * Read the file, and count each Host/IP's frequency then put it into the countHostOrIPFrequencyMap
     * For the countHostOrIPFrequencyMap, key is Host/IP's name and the frequency is its value.
     *
     * Run time: O(mn)
     * m is the line quantity of input file, and n is the average length of lines.
     *
     * @throws FileNotFoundException if log.txt not found.
     */
    private void readFile() throws FileNotFoundException {
        /** Try to read the input file, and catch the file not found exception. */
        try {
            File file = new File("../log.txt");
            /** BufferReader has bigger buffer and more efficient when reading huge file. */
            BufferedReader br = new BufferedReader(new FileReader(file));
            /** A variable to contain each line. */
            String line;

            /** Read each line from BufferReader. */
            while((line = br.readLine()) != null) {
                /** Split the line with " ". */
                String[] strs = line.split(" ");
                String key = strs[0];

                /** If the key is not in the map, put the key and 1 as value. */
                if (!countHostOrIPFrequencyMap.containsKey(key))
                    countHostOrIPFrequencyMap.put(key, 1);
                /** Else add 1 to its frequency. */
                else
                    countHostOrIPFrequencyMap.put(key, countHostOrIPFrequencyMap.get(key) + 1);
            }
        }
        catch (IOException e) { System.out.println("File is not found."); }
    }

    /**
     * Find out top 10 most active entries from countHostOrIPFrequencyMap
     *
     * Run time: O(nlgn)
     * n is the number of countHostOrIPFrequencyMap's entry set.
     * In this PriorityQueue I uses minimum heap, offer and poll are both O(nlgn).
     *
     * @return An Deque of Map.Entry<String, Integer> with top 10 most active
     */
    private Deque<Map.Entry<String, Integer>> findTheTop10MostAcitveDescending() {
        /** Use PriorityQueue with size 10 to find out top 10 most active Host/IP. */
        PriorityQueue<Map.Entry<String, Integer>> theTop10AescendingOrder
                = new PriorityQueue<>(10, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                /** Stores top 10 entries in ascending order. */
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, Integer> entry: countHostOrIPFrequencyMap.entrySet()) {
            theTop10AescendingOrder.offer(entry);
            /** If the size of PriorityQueue is greater than 10, poll the smallest 1 and rearrange the order. */
            if (theTop10AescendingOrder.size() > 10)
                theTop10AescendingOrder.poll();
        }
        /** A stack for reverse PriorityQueue's order. */
        Deque<Map.Entry<String, Integer>> theTop10DescendingOrder = new ArrayDeque<>();
        /** Poll up from PriorityQueue and push into Descending order. */
        while (!theTop10AescendingOrder.isEmpty())
            theTop10DescendingOrder.push(theTop10AescendingOrder.poll());

        return theTop10DescendingOrder;
    }
}
