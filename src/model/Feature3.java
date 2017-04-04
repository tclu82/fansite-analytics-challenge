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
        TreeMap<String, Resource> busyMap = dataStruct.busyMap;

        System.out.println("key size: " + busyMap.keySet().size());

        System.out.println("value size: " + busyMap.values().size());

        Date start = busyMap.firstEntry().getValue().date;

        Date stop = new Date(start.getTime() + ONE_HOUR);

//        System.out.println(start);
//        System.out.println(stop);

        String maxName = busyMap.firstKey();

        int maxCount = 0;

        Date cur = start;

        List<Resource> list = new LinkedList<>();


//        System.out.println("here1");

        for (Resource resource: busyMap.values()) {

            long timeDifferent = stop.getTime() - resource.date.getTime();

            System.out.println(timeDifferent);


            if (timeDifferent < ONE_HOUR) {

//                System.out.println("here2");

                int frequency = resource.frequency;

                if (frequency > maxCount) {
                    maxName = resource.dateString;
                    maxCount = frequency;
                }
            }
            else {

                list.add(new Resource(busyMap.get(maxName)));

                break;


//                stop.setTime(resource.date.getTime() + ONE_HOUR);
//
//                maxName = resource.dateString;
//                maxCount = resource.frequency;
            }


        }


//        System.out.println("here5");


        List<Resource> res = findTheTop10MostActiveDescending(list);


        /** Print out the result. */
        Writer writer = null;

        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("hours.txt"), "utf-8"));


            /** Pop up all entries and write to output file. */
            for (Resource resource : res)
                writer.write(resource.dateString + " " + resource.timeZone + " " + resource.frequency + "\n");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            try {
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
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

//    private class ResourceWindow {
//        Resource resource;
//        int resouceCount;
//
//        private ResourceWindow(Resource resource, int resourceCount) {
//            this.resource = resource;
//            this.resouceCount = resourceCount;
//        }
//    }

}