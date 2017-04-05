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
public class Feature2 extends Features {

    public Feature2(ReadFile data) {
        super(data);
    }



    /**
     * Helper method of constructor, read the file and find out top 10 most active Host/IP
     *
     * @throws FileNotFoundException if log.txt not found
     */
    @Override
    public void execute() {

        List<Resource> resourceList = findTheTop10MostActiveDescending(readFile.resourceMap);


        /** Print out the result. */
        Writer writer = null;

        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("resource.txt"), "utf-8"));
            /** write to output file. */
            for (Resource resource: resourceList)
                writer.write(resource.resourceName  + "\n");

        }
        catch (IOException e) { e.printStackTrace(); }

        finally { try { writer.close(); } catch (Exception e) { e.printStackTrace(); } }
    }



    /**
     * Find out top 10 most active entries from countHostOrIPFrequencyMap
     *
     * Run time: O(n)
     * n is the number of countHostOrIPFrequencyMap's entry set.
     * This PriorityQueue's size is limited to 10, so offer and poll will be constant.
     *
     * @return An Deque of Map.Entry<String, Integer> with top 10 most active
     */
    private List<Resource> findTheTop10MostActiveDescending(Map<String, Resource> map) {

        /** Use PriorityQueue with size 10 to find out top 10 most active Host/IP. */
        TopKPriorityQueue<Resource> theTop10DescendingOrder
                = new TopKPriorityQueue<>(10, new Comparator<Resource>() {
            @Override
            public int compare(Resource o1, Resource o2) {
                int bandwidthConsume1 = o1.frequency * o1.resourceSize;
                int bandwidthConsume2 = o2.frequency * o2.resourceSize;
                return bandwidthConsume1 - bandwidthConsume2;
            }
        });


        for (Resource resource : map.values())
            theTop10DescendingOrder.push(resource);

        return theTop10DescendingOrder.offers();
    }
}
