package control;

import model.Resource;
import model.ResourceConsume;

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
    public Map<String, Integer> countHostOrIPFrequencyMap;
    /**
     * A map contains resource as key and its frequency as value for Feature 2.
     */
    public Map<String, ResourceConsume> resourceUsedFrequency;


    public List<Resource> resourceList;


    /**
     * Constructor
     */
    public ReadFile() {


        countHostOrIPFrequencyMap = new HashMap<>();


        resourceUsedFrequency = new HashMap<>();


        resourceList = new ArrayList<>();



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


                // Feature 1

                String hostOrIP = strs[0];
                /** If the key is not in the map, put the key and 1 as value. */
                if (!countHostOrIPFrequencyMap.containsKey(hostOrIP))
                    countHostOrIPFrequencyMap.put(hostOrIP, 1);
                /** Else add 1 to its frequency. */
                else
                    countHostOrIPFrequencyMap.put(hostOrIP, countHostOrIPFrequencyMap.get(hostOrIP) + 1);


                // Feature 2

                String resourceName = strs[6];

                String lastElement = strs[strs.length-1];

                int resourceSize = Character.isDigit(lastElement.charAt(0)) ?
                        Integer.parseInt(lastElement) : 0;


                /** If the key is not in the map, put a new ResourceConsume object. */
                if (!resourceUsedFrequency.containsKey(resourceName))
                    resourceUsedFrequency.put(resourceName, new ResourceConsume(resourceName, resourceSize));

                /** Add 1 to frequency. */
                resourceUsedFrequency.get(resourceName).addFrequency();


                // Feature 3

                String dateOriginal = strs[3].substring(1);
                String dateString = dateOriginal.replace("Jul", "07");

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss");
                Date date = dateFormat.parse(dateString);

                Resource resource = new Resource(dateString, date);

                resourceList.add(resource);



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

