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

        List<Resource> resourceList = findTheTop10MostActiveDescending(readFile.hostNameMap);

        /** Print out the result. */
        Writer writer = null;

        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log_output/hosts.txt"), "utf-8"));

            /** Pop up all entries and write to output file. */
            for (Resource resource: resourceList)
                writer.write(resource.hostName + ", " + resource.frequency + "\n");

        }
        catch (IOException ex) { System.out.println("IO exception: " + ex); }

        finally { try { writer.close(); } catch (Exception ex) { System.out.println("Output file can't be closed: " + ex); } }
    }




    /**
     * Find out top 10 most active entries from countHostOrIPFrequencyMap
     *
     * Run time: O(n)
     * n is the number of countHostOrIPFrequencyMap's entry set.
     * In this PriorityQueue I uses minimum heap, offer and poll are both O(nlgn).
     *
     * @return An Deque of Map.Entry<String, Integer> with top 10 most active
     */
    private List<Resource> findTheTop10MostActiveDescending(Map<String, Resource> map) {

        /** Use PriorityQueue with size 10 to find out top 10 most active Host/IP. */
        TopKPriorityQueue<Resource> theTop10DescendingOrder = new TopKPriorityQueue<>(10, new Comparator<Resource>() {
            @Override
            public int compare(Resource o1, Resource o2) {
                return o1.frequency - o2.frequency;
            }
        });


        for (Resource resource : map.values())
            theTop10DescendingOrder.push(resource);

        return theTop10DescendingOrder.offers();
    }
}
