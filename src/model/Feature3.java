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



    public Feature3(ReadFile data) {
        super(data);
    }

    @Override
    public void execute() {

        List<String> timestampList = readFile.timestampList;

        Map<String, TimestampCount> busyMap = readFile.busyMap;

        List<TimestampCount> busiestList = busiestWindows(timestampList, busyMap, ONE_HOUR);

        Set<TimestampCount> set = new HashSet<>();

        set.addAll(busiestList);
        
        List<TimestampCount> top10BusiestDescending = findTheTop10MostActiveDescending(set);

        /** Print out the result. */
        Writer writer = null;

        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("hours.txt"), "utf-8"));

            /** Pop up all entries and write to output file. */
            for (TimestampCount timestampCount: top10BusiestDescending)
                writer.write(timestampCount.timestamp + " " + timestampCount.timeZone
                                    + "," + timestampCount.frequency + "\n");
        }
        catch (IOException e) { e.printStackTrace(); }

        finally {

            try { writer.close(); }

            catch (Exception e) { e.printStackTrace(); }
        }
    }



    private List<TimestampCount> busiestWindows(List<String> list, Map<String, TimestampCount> map, long time) {
        List<TimestampCount> result = new ArrayList<>();

        String maxTimestamp;
        maxTimestamp = findMaxTimestamp(list, map, 0, time);
        result.add(map.get(maxTimestamp));

        String lastTimestamp = list.get(list.size() - 1);
        long end = map.get(lastTimestamp).date.getTime();
        long stop = end - time;

        for (int i=1; map.get(list.get(i)).date.getTime() <= stop; i++) {

            maxTimestamp = findMaxTimestamp(list, map, i, time);

            result.add(map.get(maxTimestamp));
        }


        return result;
    }

    private String findMaxTimestamp(List<String> list, Map<String, TimestampCount> map, int startIndex, long time) {

        long start = map.get(list.get(startIndex)).date.getTime();

        long stop = new Date(start + time).getTime();

        String maxTimestamp = list.get(startIndex);

        int maxTimestampCount = map.get(maxTimestamp).frequency;

        for (int i=startIndex; stop - map.get(list.get(i)).date.getTime() <= time
                            && stop - map.get(list.get(i)).date.getTime() >= 0; i++) {

            int currentTimestampCount = map.get(list.get(i)).frequency;

            String currentTimestamp = map.get(list.get(i)).timestamp;

            if (currentTimestampCount > maxTimestampCount) {
                maxTimestampCount = currentTimestampCount;
                maxTimestamp = currentTimestamp;
            }
        }
        return maxTimestamp;
    }



    private List<TimestampCount> findTheTop10MostActiveDescending(Set<TimestampCount> set) {

        TopKPriorityQueue<TimestampCount> theTop10BusyDescending = new TopKPriorityQueue<>(10, new Comparator<TimestampCount>() {
            @Override
            public int compare(TimestampCount o1, TimestampCount o2) {
                return o1.frequency - o2.frequency;
            }
        });

        for (TimestampCount timestampCount : set)
            theTop10BusyDescending.push(timestampCount);

        return theTop10BusyDescending.offers();
    }
}
