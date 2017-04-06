package model;

import control.ReadFile;
import control.TopKPriorityQueue;

import java.io.*;
import java.util.*;

/**
 * Created by zac on 4/2/17.
 */
public class Feature3 extends Features {

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

        List<String> timestampList = readFile.timestampList;

        Map<String, Record> busyMap = readFile.busyMap;

        Set<Record> busiestSet = busiestWindows(timestampList, busyMap, ONE_HOUR);

        List<Record> top10BusiestDescending = findTheTop10MostActiveDescending(busiestSet);

        /** Print out the result. */
        Writer writer = null;

        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log_output/hours.txt"), "utf-8"));

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



    private Set<Record> busiestWindows(List<String> list, Map<String, Record> map, long time)
    {
        Set<Record> result = new HashSet<>();

        String maxTimestamp;

        maxTimestamp = findMaxTimestamp(list, map, 0, time);

        result.add(map.get(maxTimestamp));

        String lastTimestamp = list.get(list.size() - 1);

        long end = map.get(lastTimestamp).date.getTime();

        long stop = end - time;

        for (int i=1; map.get(list.get(i)).date.getTime() <= stop; i++)
        {
            if (list.get(i-1).equals(maxTimestamp))
            {
                maxTimestamp = findMaxTimestamp(list, map, i, time);
            }
            result.add(map.get(maxTimestamp));
        }
        return result;
    }






    private String findMaxTimestamp(List<String> timestampList, Map<String, Record> busyMap, int startIndex, long time) {

        long start = busyMap.get(timestampList.get(startIndex)).date.getTime();

        long stop = new Date(start + time).getTime();

        String maxTimestamp = timestampList.get(startIndex);

        int maxTimestampCount = busyMap.get(maxTimestamp).busyCount;

        for (int i=startIndex; stop - busyMap.get(timestampList.get(i)).date.getTime() <= time
                            && stop - busyMap.get(timestampList.get(i)).date.getTime() >= 0; i++)
        {

            int currentTimestampCount = busyMap.get(timestampList.get(i)).busyCount;

            String currentTimestamp = busyMap.get(timestampList.get(i)).originalTimestamp;

            if (currentTimestampCount > maxTimestampCount)
            {
                maxTimestampCount = currentTimestampCount;

                maxTimestamp = currentTimestamp;
            }
        }
        return maxTimestamp;
    }




    private List<Record> findTheTop10MostActiveDescending(Set<Record> set) {
        /** Use PriorityQueue with size 10 to find out top 10 busiest sites */
        TopKPriorityQueue<Record> theTop10BusyDescending = new TopKPriorityQueue<>(10, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2)
            {
                /** Sorted in ascending order */
                return o1.busyCount - o2.busyCount;
            }
        });

        for (Record record : set)
        {
            theTop10BusyDescending.push(record);
        }
        /** Return descending order */
        return theTop10BusyDescending.offers();
    }
}
