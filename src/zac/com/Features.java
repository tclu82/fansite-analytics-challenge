package zac.com;

import sun.util.resources.LocaleData;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by zac on 4/1/17.
 */
public class Features {


    /**
     * A map contains Host/IP name as key and its frequency as value for Feature 1.
     */
    Map<String, Integer> countHostOrIPFrequencyMap;
    /**
     * A map contains resource as key and its frequency as value for Feature 2.
     */
    Map<String, ResourceConsume> resourceUsedFrequency;

    /**
     * Constructor
     */
    Features() {


        countHostOrIPFrequencyMap = new HashMap<>();


        resourceUsedFrequency = new HashMap<>();




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
            File file = new File("src/log.txt");
            /** BufferReader has bigger buffer and more efficient when reading huge file. */
            BufferedReader br = new BufferedReader(new FileReader(file));
            /** A variable to contain each line. */
            String line1, line2;



            // Date

            line1 = br.readLine();
            line2 = br.readLine();

            String[] strs1 = line1.split(" ");
            String[] strs2 = line2.split(" ");



            String dateString1 = strs1[3].substring(1);
            String dateString2 = strs2[3].substring(1);

            System.out.println(dateString1);
            System.out.println(dateString2);


            DateFormat df = new SimpleDateFormat("dd/MMM/yyyy:hh:mm:ss");
            Date date1 = df.parse(dateString1);
            Date date2 = df.parse(dateString2);

            System.out.println("\nDate info:\n");

            System.out.println(date1.toString());
            System.out.println(date1.compareTo(date2));


//            java.util.Calendar cal = java.util.Calendar.getInstance();
//            String dateInString = new java.text.SimpleDateFormat("dd/EEE/yyyy:hh:mm:ss")
//                    .format(cal.getTime());
//            System.out.println(dateInString);





//            formatter.parse(dateString);

//            System.out.println(parsedDate.toString());



//            /** Read each line from BufferReader. */
//            while ((line = br.readLine()) != null) {
//                /** Split the line with " ". */
//                String[] strs = line.split(" ");


//                // Feature 1
//
//                String hostOrIP = strs[0];
//                /** If the key is not in the map, put the key and 1 as value. */
//                if (!countHostOrIPFrequencyMap.containsKey(hostOrIP))
//                    countHostOrIPFrequencyMap.put(hostOrIP, 1);
//                /** Else add 1 to its frequency. */
//                else
//                    countHostOrIPFrequencyMap.put(hostOrIP, countHostOrIPFrequencyMap.get(hostOrIP) + 1);
//
//
//                // Feature 2
//
//                String resourceName = strs[6];
//
//                String lastElement = strs[strs.length-1];
//
//                int resourceSize = Character.isDigit(lastElement.charAt(0)) ?
//                        Integer.parseInt(lastElement) : 0;
//
//
//                /** If the key is not in the map, put a new ResourceConsume object. */
//                if (!resourceUsedFrequency.containsKey(resourceName))
//                    resourceUsedFrequency.put(resourceName, new ResourceConsume(resourceName, resourceSize));
//
//                /** Add 1 to frequency. */
//                resourceUsedFrequency.get(resourceName).addFrequency();




                // Feature 3








                // Feature 4


//            }



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








    void executeFeature1() {

        Feature1 f1 = new Feature1(countHostOrIPFrequencyMap);

    }

    void executeFeature2() {

        Feature2 f2 = new Feature2(resourceUsedFrequency);

    }
}

