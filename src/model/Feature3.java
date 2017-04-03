package model;

import control.ReadFile;
import control.TopKPriorityQueue;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zac on 4/2/17.
 */
public class Feature3 extends Features {

    public Feature3(ReadFile data) {
        super(data);
    }

    @Override
    public void execute() {
        List<Resource> resourceList = dataStruct.resourceList;
        List<ResourceWindow> windowList = new ArrayList<>();

        int i = 0, j = 0;

        for (i = 0; i < resourceList.size(); i++) {

            Resource resource1 = resourceList.get(i);

            while (true) {

                if (j >= resourceList.size()) {
                    j--;
                    break;
                }

                Resource resource2 = resourceList.get(j);

                long timeDifferent = resource2.date.getTime() - resource1.date.getTime();
                /** If greater 1 hour, start another new 1 hour window. */
                if (timeDifferent > 1000 * 60 * 60) {
                    j--;
                    break;
                }
                j++;
            }
            windowList.add(new ResourceWindow(resource1, j - i + 1));
        }


        List<ResourceWindow> list = findTheTop10MostActiveDescending(windowList);


        /** Print out the result. */
        Writer writer = null;

        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("hours.txt"), "utf-8"));
            /** Pop up all entries and write to output file. */
            for (ResourceWindow resourceWindow : list)
                writer.write(resourceWindow.resource.dateString + "," + resourceWindow.resouceCount + "\n");
        } catch (IOException ex) {
            System.out.println("IO exception: " + ex);
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                System.out.println("Output file can't be closed: " + ex);
            }
        }
    }


    private List<ResourceWindow> findTheTop10MostActiveDescending(List<ResourceWindow> list) {

        TopKPriorityQueue<ResourceWindow> theTop10BusyDescending = new TopKPriorityQueue<>(10, new Comparator<ResourceWindow>() {
            @Override
            public int compare(ResourceWindow o1, ResourceWindow o2) {
                return o1.resouceCount - o2.resouceCount;
            }
        });

        for (ResourceWindow resourceWindow : list)
            theTop10BusyDescending.push(resourceWindow);

        return theTop10BusyDescending.offers();
    }

    private class ResourceWindow {
        Resource resource;
        int resouceCount;

        private ResourceWindow(Resource resource, int resourceCount) {
            this.resource = resource;
            this.resouceCount = resourceCount;
        }
    }
}