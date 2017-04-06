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
    /** An output path for feature 1 */
    private static final String FEATURE_1_OUTPUT = "log_output/hosts.txt";

    /**
     * Constructor
     *
     * @param readFile
     */
    public Feature1(ReadFile readFile)
    {
        super(readFile);
    }

    /**
     * Helper method of constructor, read the file and find out top 10 most active Host/IP
     */
    @Override
    public void execute()
    {
        /** hostList contains the top 10 most active hosts */
        List<Record> hostList = findTheTop10MostActiveDescending(readFile.hostMap);
        /** Print out the result. */
        Writer writer = null;
        /** Write to hosts.txt and catch the exceptions. */
        try
        {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FEATURE_1_OUTPUT),
                    "utf-8"));
            /** Write all records to output file. */
            for (Record record: hostList)
            {
                writer.write(record.host + ", " + record.hostCount + "\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Find out top 10 most active entries
     *
     * Run time: O(n)
     * n is the number of hostMap's entry set.
     * In this PriorityQueue, the size is constrained in 10, offer and poll are O(lg10) == O(1)
     *
     * @param hostMap
     * @return An List of top 10 most active Record
     */
    private List<Record> findTheTop10MostActiveDescending(Map<String, Record> hostMap)
    {
        /** Use PriorityQueue with size 10 to find out top 10 most active Host/IP. */
        TopKPriorityQueue<Record> theTop10DescendingOrder = new TopKPriorityQueue<>(10, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2)
            {
                /** Sorted in ascending order */
                return o1.hostCount - o2.hostCount;
            }
        });

        for (Record record : hostMap.values())
        {
            theTop10DescendingOrder.push(record);
        }
        /** Return descending order */
        return theTop10DescendingOrder.offers();
    }
}