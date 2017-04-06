package model;

import control.ReadFile;
import control.TopKPriorityQueue;

import java.io.*;
import java.util.*;

/**
 * Created by zac on 3/31/17.
 *
 * This class perform Feature 2 to print out top 10 sites consume the most bandwidth.
 */
public class Feature2 extends Features {
    /** An output path for feature 2 */
    private static final String FEATURE_2_OUTPUT = "log_output/resource.txt";

    /**
     * Constructor
     *
     * @param readFile
     */
    public Feature2(ReadFile readFile)
    {
        super(readFile);
    }

    /**
     * Write top 10 sites consume the most bandwidth to output file
     */
    @Override
    public void execute()
    {   /** resourceList contains the top 10 sites consume most resources in descending order */
        List<Record> resourceList = findTheTop10ConsumptionInDescending(readFile.resourceMap);
        /** Print out the result. */
        Writer writer = null;
        /** Write to hosts.txt and catch the exceptions. */
        try
        {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FEATURE_2_OUTPUT),
                    "utf-8"));
            /** write to output file. */
            for (Record record: resourceList)
            {
                writer.write(record.resource + "\n");
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
     * Find out top 10 consume most bandwidth sites
     *
     * Run time: O(n)
     * n is the number of resourceMap's entry set.
     * In this PriorityQueue, the size is constrained in 10, offer and poll are O(lg10) == O(1)
     *
     * @param resourceMap
     * @return An List of top 10 most active Record
     */
    private List<Record> findTheTop10ConsumptionInDescending(Map<String, Record> resourceMap) {
        /** Use PriorityQueue with size 10 to find out top 10 consume most bandwidth */
        TopKPriorityQueue<Record> theTop10DescendingOrder = new TopKPriorityQueue<>(10, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2)
            {
                int bandwidthConsume1 = o1.resourceCount * Integer.parseInt(o1.bytes);

                int bandwidthConsume2 = o2.resourceCount * Integer.parseInt(o2.bytes);
                /** Sorted in ascending order */
                return bandwidthConsume1 - bandwidthConsume2;
            }
        });

        for (Record record : resourceMap.values())
        {
            theTop10DescendingOrder.push(record);
        }
        /** Return descending order */
        return theTop10DescendingOrder.offers();
    }
}
