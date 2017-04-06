package model;

import control.ReadFile;
import control.TopKPriorityQueue;

import java.io.*;
import java.util.*;

/**
 * Created by zac on 4/2/17.
 */
public class Feature3 extends Features {
    /** An output path for feature 3 */
    private static final String FEATURE_3_OUTPUT = "log_output/hours.txt";

    public static final long ONE_HOUR = 1000 * 60 * 60;

    /**
     * Constructor
     *
     * @param readFile
     */
    public Feature3(ReadFile readFile)
    {
        super(readFile);
    }

    @Override
    public void execute() {
        /** A List of timestamp from parent class */
        List<String> timestampList = readFile.timestampList;
        /** A Map field from parent class */
        Map<String, Record> busyMap = readFile.busyMap;
        /** A Set of Recode contains top 10 busies sites */
        Set<Record> busiestSet = busiestWindows(timestampList, busyMap, ONE_HOUR);
        /** Sorted in descending order */
        List<Record> top10BusiestDescending = findTheTop10BusiestSitesInDescending(busiestSet);
        /** Print out the result. */
        Writer writer = null;
        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FEATURE_3_OUTPUT), "utf-8"));
            /** Pop up all entries and write to output file. */
            for (Record record: top10BusiestDescending)
            {
                writer.write(record.originalTimestamp + "," + record.busyCount + "\n");
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
     * This method find out the busies record in all windows
     *
     * @param list
     * @param map
     * @param time
     * @return Set<Record> A Set of Records represents the busies record in each window
     */
    private Set<Record> busiestWindows(List<String> list, Map<String, Record> map, long time)
    {
        Set<Record> busiestWindowsSet = new HashSet<>();

        String busiestTimestamp;
        /** Find out the first busiest one from the first window */
        busiestTimestamp = findBusiestRecordInSingleWindow(list, map, 0, time);

        busiestWindowsSet.add(map.get(busiestTimestamp));

        String lastTimestamp = list.get(list.size() - 1);

        long end = map.get(lastTimestamp).date.getTime();

        long stop = end - time;
        /** From the 2nd window to the stop window */
        for (int i=1; map.get(list.get(i)).date.getTime() <= stop; i++)
        {   /** If busiest timestamp is in last window, find new busiest one */
            if (list.get(i-1).equals(busiestTimestamp))
            {
                busiestTimestamp = findBusiestRecordInSingleWindow(list, map, i, time);
            }
            busiestWindowsSet.add(map.get(busiestTimestamp));
        }
        return busiestWindowsSet;
    }

    /**
     * Find busiest record in single window
     *
     * @param timestampList
     * @param busyMap
     * @param startIndex
     * @param time
     * @return String of timestamp represent the busiest record
     */
    private String findBusiestRecordInSingleWindow(List<String> timestampList, Map<String, Record> busyMap, int startIndex, long time) {
        /** Start time */
        long start = busyMap.get(timestampList.get(startIndex)).date.getTime();
        /** Stop time */
        long stop = new Date(start + time).getTime();
        /** Set the first timestamp is the busiest one */
        String maxTimestamp = timestampList.get(startIndex);

        int maxTimestampCount = busyMap.get(maxTimestamp).busyCount;
        /** Start from given index, stop at the designated time */
        for (int i=startIndex; stop - busyMap.get(timestampList.get(i)).date.getTime() <= time
                            && stop - busyMap.get(timestampList.get(i)).date.getTime() >= 0; i++)
        {   /** Find out current timestamp and current busy count */
            int currentTimestampCount = busyMap.get(timestampList.get(i)).busyCount;

            String currentTimestamp = busyMap.get(timestampList.get(i)).originalTimestamp;
            /** Update the busiest if busier one shows */
            if (currentTimestampCount > maxTimestampCount)
            {
                maxTimestampCount = currentTimestampCount;

                maxTimestamp = currentTimestamp;
            }
        }
        return maxTimestamp;
    }

    /**
     * Find out top 10 busies sites from input Set<Record>
     *
     * @param busiestSet
     * @return Top 10 busiest sites record set
     */
    private List<Record> findTheTop10BusiestSitesInDescending(Set<Record> busiestSet) {
        /** Use PriorityQueue with size 10 to find out top 10 busiest sites */
        TopKPriorityQueue<Record> theTop10BusyDescending = new TopKPriorityQueue<>(10, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2)
            {
                /** Sorted in ascending order */
                return o1.busyCount - o2.busyCount;
            }
        });

        for (Record record : busiestSet)
        {
            theTop10BusyDescending.push(record);
        }
        /** Return descending order */
        return theTop10BusyDescending.offers();
    }
}
