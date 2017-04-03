package model;

import control.ReadFile;
import control.TopKPriorityQueue;

import java.io.*;
import java.util.*;

/**
 * Created by zac on 3/31/17.
 *
 * This class perform Feature 1 to print out top 10 most active Host/IP in descending order.
 */
public class Feature1 extends Features {

    public Feature1(ReadFile data) {
        super(data);
    }

    /**
     * Helper method of constructor, read the file and find out top 10 most active Host/IP
     */
    @Override
    public void execute() {

        List<Map.Entry<String, Integer>> list
                = findTheTop10MostActiveDescending(dataStruct.countHostOrIPFrequencyMap);

        /** Print out the result. */
        Writer writer = null;

        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("hosts.txt"), "utf-8"));

            /** Pop up all entries and write to output file. */
            for (Map.Entry<String, Integer> entry: list)
                writer.write(entry.getKey() + ", " + entry.getValue() + "\n");

        }
        catch (IOException ex) { System.out.println("IO exception: " + ex); }

        finally { try { writer.close(); } catch (Exception ex) { System.out.println("Output file can't be closed: " + ex); } }
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
    private List<Map.Entry<String, Integer>> findTheTop10MostActiveDescending(Map<String, Integer> map) {

        /** Use PriorityQueue with size 10 to find out top 10 most active Host/IP. */
        TopKPriorityQueue<Map.Entry<String, Integer>> theTop10DescendingOrder
                = new TopKPriorityQueue<>(10, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                /** Stores top 10 entries in ascending order. */
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, Integer> entry : map.entrySet())
            theTop10DescendingOrder.push(entry);

        return theTop10DescendingOrder.offers();
    }


}
