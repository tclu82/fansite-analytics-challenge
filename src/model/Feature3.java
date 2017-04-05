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


//        int i=0, j=0;
//
//        for (i=0; i<timestampList.size(); i++) {
//
//            TimestampCount currentTimestampCount = busyMap.get(timestampList.get(i));
//
//            Date currentDate = currentTimestampCount.date;
//
//            long timeDifferent = stop.getTime() - currentDate.getTime();
//
//
//            /** Find out resource in each hour */
//            if (timeDifferent <= ONE_HOUR && timeDifferent >= 0) {
//
////                System.out.println("here2");
////                System.out.println(timeDifferent);
//
//                int frequency = currentTimestampCount.frequency;
//
//                if (frequency > busiestCount) {
//                    busiestTimestamp = currentTimestampCount.timestamp;
//                    busiestCount = frequency;
//                }
//            }
//            /** Find out the max one within last 1 hour and add into the List. */
//            else {
//
////                list.add(new Resource(busyMap.get(maxName)));
////
////                stop.setTime(resource.date.getTime() + ONE_HOUR);
//////
////                maxName = resource.dateString;
////                maxCount = resource.frequency;
//            }

//        System.out.println(maxName + ", " + busyMap.get(maxName).frequency);
//        System.out.println(list.size());
//        System.out.println(list.get(0).dateString + ", " +list.get(0).frequency);
//
////        System.out.println("here5");
//
//
//        List<Resource> res = findTheTop10MostActiveDescending(list);


        /** Print out the result. */
        Writer writer = null;

        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("hours.txt"), "utf-8"));

            /** Pop up all entries and write to output file. */
            for (TimestampCount timestampCount: busiestList)
                writer.write(timestampCount.timestamp + ", " + timestampCount.frequency + "\n");
        }
        catch (IOException e) { e.printStackTrace(); }

        finally {

            try { writer.close(); }

            catch (Exception e) { e.printStackTrace(); }
        }
    }


    private List<Resource> findTheTop10MostActiveDescending(List<Resource> list) {

        TopKPriorityQueue<Resource> theTop10BusyDescending = new TopKPriorityQueue<>(10, new Comparator<Resource>() {
            @Override
            public int compare(Resource o1, Resource o2) {
                return o1.frequency-o2.frequency;
            }
        });

        for (Resource resource : list)
            theTop10BusyDescending.push(resource);

        return theTop10BusyDescending.offers();
    }




    private List<TimestampCount> busiestWindows(List<String> list, Map<String, TimestampCount> map, long time) {
        List<TimestampCount> result = new ArrayList<>();
        Date start = map.get(list.get(0)).date;
        Date stop = new Date(start.getTime() + time);
        int i=0;
        String maxTimestamp = list.get(0);
        int maxTimestampCount = map.get(maxTimestamp).frequency;

        for (i=0; map.get(list.get(i)).date.getTime()-start.getTime() <= time; i++) {

            int currentTimestampCount = map.get(list.get(i)).frequency;
            String currentTimestamp = map.get(list.get(i)).timestamp;

            if (currentTimestampCount > maxTimestampCount) {
                maxTimestampCount = currentTimestampCount;
                maxTimestamp = currentTimestamp;
            }
        }
        result.add(map.get(maxTimestamp));

        return result;
    }
}
