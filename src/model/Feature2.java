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

        List<Map.Entry<String, ResourceConsume>> list = findTheTop10MostActiveDescending(dataStruct.resourceUsedFrequency);


        /** Print out the result. */
        Writer writer = null;

        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("resource.txt"), "utf-8"));
            /** write to output file. */
            for (Map.Entry<String, ResourceConsume> entry: list)
                writer.write(entry.getKey()  + "\n");

        }
        catch (IOException ex) { System.out.println("IO exception: " + ex); }

        finally { try { writer.close(); } catch (Exception ex) { System.out.println("Output file can't be closed: " + ex); } }
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
    private List<Map.Entry<String, ResourceConsume>> findTheTop10MostActiveDescending(Map<String, ResourceConsume> map) {

        /** Use PriorityQueue with size 10 to find out top 10 most active Host/IP. */
        TopKPriorityQueue<Map.Entry<String, ResourceConsume>> theTop10DescendingOrder
                = new TopKPriorityQueue<>(10, new Comparator<Map.Entry<String, ResourceConsume>>() {
            @Override
            public int compare(Map.Entry<String, ResourceConsume> o1, Map.Entry<String, ResourceConsume> o2) {
                int bancwidthConsumption1 = o1.getValue().frequency * o1.getValue().resourceSize;
                int bancwidthConsumption2 = o2.getValue().frequency * o2.getValue().resourceSize;
                return bancwidthConsumption1-bancwidthConsumption2;
            }
        });


        for (Map.Entry<String, ResourceConsume> entry : map.entrySet())
            theTop10DescendingOrder.push(entry);

        return theTop10DescendingOrder.offers();
    }
}
