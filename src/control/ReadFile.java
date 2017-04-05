package control;

import model.Resource;
import model.TimestampCount;

import java.io.*;
import java.util.*;

/**
 * Created by zac on 4/2/17.
 */
public class ReadFile {

    /**
     * A map contains Host/IP name as key and its frequency as value for Feature 1.
     */
    public Map<String, Resource> hostNameMap;


    /**
     * A map contains resource as key and its frequency as value for Feature 2.
     */
    public Map<String, Resource> resourceMap;



    public Map<String, TimestampCount> busyMap;
    public List<String> timestampList;



    /**
     * Constructor
     */
    public ReadFile() {


        hostNameMap = new HashMap<>();
        
        resourceMap = new HashMap<>();
        
        busyMap = new HashMap<>();

        timestampList = new ArrayList<>();



        /** Run execution, throw exception if file not found. */
        readFile();
    }


    /**
     * Read the file, and count each Host/IP's frequency then put it into the countHostOrIPFrequencyMap
     * For the countHostOrIPFrequencyMap, key is Host/IP's name and the frequency is its value.
     * <p>
     * Run time: O(mn)
     * m is the line quantity of input file, and n is the average length of lines.
     */
    private void readFile() {
        /** Try to read the input file, and catch the file not found exception. */
        try {
            File file = new File("log.txt");
            /** BufferReader has bigger buffer and more efficient when reading huge file. */
            BufferedReader br = new BufferedReader(new FileReader(file));
            /** A variable to contain each line. */
            String line;


            /** Read each line from BufferReader. */
            while ((line = br.readLine()) != null) {
                /** Split the line with " ". */
                String[] strs = line.split(" ");
                String hostName = strs[0];
                String resourceName = strs[6];
                String lastElement = strs[strs.length-1];
                int resourceSize = Character.isDigit(lastElement.charAt(0)) ? Integer.parseInt(lastElement) : 0;
                String dateOriginal = strs[3].substring(1);
                String timestamp = dateOriginal.replace("Jul", "07");
                String timeZone = strs[4].substring(0,strs[4].length()-1);


                // Feature 1

//                /** If the key is not in the map, put the key and 1 as value. */
//                if (!hostNameMap.containsKey(hostName)) {
//                    hostNameMap.put(hostName, new Resource(hostName, resourceName, resourceSize, timestamp, timeZone));
//                }
//
//                hostNameMap.get(hostName).addFrequency();


                // Feature 2

//                /** If the key is not in the map, put a new ResourceConsume object. */
//                if (!resourceMap.containsKey(resourceName))
//                    resourceMap.put(resourceName, new Resource(hostName, resourceName, resourceSize, timestamp, timeZone));
//
//                /** Add 1 to frequency. */
//                resourceMap.get(resourceName).addFrequency();


                // Feature 3

                /** Build the busyMap */
                if (!busyMap.containsKey(timestamp))
                    busyMap.put(timestamp, new TimestampCount(timestamp, timeZone));

                busyMap.get(timestamp).addFrequency();


                /** Build the timestampList */
                if (!timestampList.isEmpty() && !timestampList.get(timestampList.size()-1).equals(timestamp))
                    timestampList.add(timestamp);

                if (timestampList.isEmpty())
                    timestampList.add(timestamp);




                // Feature 4




            }




//            for (int i=0; i<5; i++)
//                System.out.println(timestampList.get(i));
//
//            for (int i=timestampList.size()-1; i>=timestampList.size()-5; i--)
//                System.out.println(timestampList.get(i));

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

