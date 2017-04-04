package control;

import model.Resource;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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



    public TreeMap<String, Resource> busyMap;




    /**
     * Constructor
     */
    public ReadFile() {


        hostNameMap = new HashMap<>();


        resourceMap = new HashMap<>();


        busyMap = new TreeMap<>();



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
                String timeZone = strs[4].substring(0,strs[4].length()-1);
                String dateString = dateOriginal.replace("Jul", "07");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss");
                Date date = dateFormat.parse(dateString);

                // Feature 1

//                /** If the key is not in the map, put the key and 1 as value. */
//                if (!hostNameMap.containsKey(hostName)) {
//                    hostNameMap.put(hostName, new Resource(hostName, resourceName, resourceSize, dateString, timeZone, date));
//                }
//
//                hostNameMap.get(hostName).addFrequency();


                // Feature 2

//                /** If the key is not in the map, put a new ResourceConsume object. */
//                if (!resourceMap.containsKey(resourceName))
//                    resourceMap.put(resourceName, new Resource(hostName, resourceName, resourceSize, dateString, timeZone, date));
//
//                /** Add 1 to frequency. */
//                resourceMap.get(resourceName).addFrequency();


                // Feature 3


                if (!busyMap.containsKey(dateString))
                    busyMap.put(dateString, new Resource(hostName, resourceName, resourceSize, dateString, timeZone, date));

                busyMap.get(dateString).addFrequency();



                // Feature 4




            }



        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        catch (ParseException e) {
            e.printStackTrace();
        }


    }
}

