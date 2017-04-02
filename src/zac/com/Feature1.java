package zac.com;

import java.io.*;
import java.util.*;

/**
 * Created by zac on 3/31/17.
 *
 * This class perform Feature 1 to print out top 10 most active Host/IP in descending order.
 */
public class Feature1 {

    Map<String, Integer> countHostOrIPFrequencyMap;

//    Deque<Map.Entry<String, Integer>> deque;

    Feature1(Map<String, Integer> map) throws FileNotFoundException {

        countHostOrIPFrequencyMap = map;

        execute();

    }

    /**
     * Helper method of constructor, read the file and find out top 10 most active Host/IP
     *
     * @throws FileNotFoundException if log.txt not found
     */
    void execute() throws FileNotFoundException {

        PriorityQueue<Map.Entry<String, Integer>> pq
                = findTheTop10MostAcitveDescending(countHostOrIPFrequencyMap);


        Deque<Map.Entry<String, Integer>> deque = new ArrayDeque<>();

        while (!pq.isEmpty()) deque.push(pq.poll());


        /** Print out the result. */
        Writer writer = null;

        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("hosts.txt"), "utf-8"));
            /** Pop up all entries and write to output file. */
            while (!deque.isEmpty()) {
                Map.Entry<String, Integer> entry = deque.pop();
                writer.write(entry.getKey() + ", " + entry.getValue() + "\n");
            }
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
    private PriorityQueue<Map.Entry<String, Integer>> findTheTop10MostAcitveDescending(Map<String, Integer> map) {

        /** Use PriorityQueue with size 10 to find out top 10 most active Host/IP. */
        PriorityQueue<Map.Entry<String, Integer>> theTop10AescendingOrder
                = new PriorityQueue<>(10, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                /** Stores top 10 entries in ascending order. */
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            theTop10AescendingOrder.offer(entry);
            /** If the size of PriorityQueue is greater than 10, poll the smallest 1 and rearrange the order. */
            if (theTop10AescendingOrder.size() > 10)
                theTop10AescendingOrder.poll();
        }
        return theTop10AescendingOrder;
    }


}
