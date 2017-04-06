package control;

import model.Record;

import java.io.*;
import java.util.*;

/**
 * Created by zac on 4/2/17.
 */
public class ReadFile {
    /** A Map contains Host/IP name as key and Record as value for Feature 1 */
    public Map<String, Record> hostMap;
    /** A Map contains resource as key and Record as value for Feature 2 */
    public Map<String, Record> resourceMap;
    /** A Map contains timestamp as key and Record as value for Feature 3*/
    public Map<String, Record> busyMap;
    /** A List contains all timestamp for Feature 3 */
    public List<String> timestampList;
    /** A List of Record for feature 4*/
    public List<Record> recordList;

    private static final String INPUT_FILE = "log_input/log.txt";

    /**
     * Constructor, initialize all fields
     */
    public ReadFile() {
        /** HashMap's get(key) is O(1) */
        hostMap = new HashMap<>();
        
        resourceMap = new HashMap<>();
        
        busyMap = new HashMap<>();
        /** ArrayList's get(index) is O(1) */
        timestampList = new ArrayList<>();

        recordList = new ArrayList<>();
        /** Run execution, throw exception if file not found. */
        readFile();
    }

    /**
     * Read the file, and build Records for all fields
     *
     * Run time: O(mn)
     * m is the line quantity of input file, and n is the average length of lines.
     */
    private void readFile() {
        /** Try to read the input file, and catch the file not found exception. */
        try {
            File file = new File(INPUT_FILE);
            /** BufferReader has bigger buffer and more efficient when reading huge file. */
            BufferedReader br = new BufferedReader(new FileReader(file));
            /** A variable to contain each line. */
            String line;
            /** Read each line from BufferReader. */
            while ((line = br.readLine()) != null)
            {   /** Split the line with " " */
                String[] stringArray = line.split(" ");

                String host = stringArray[0];
                /** Keep original timestamp for Feature 4*/
                String timestamp = stringArray[3] + " " + stringArray[4];
                /** StringBuilder use extra memory but faster than String */
                StringBuilder sb = new StringBuilder();

                for (int i = 5; i < stringArray.length - 2; i++)
                {
                    sb.append(stringArray[i] + " ");
                }
                String request = sb.toString().substring(0, sb.length() - 1);

                String resource = stringArray[6];

                String replyCode = stringArray[stringArray.length - 2];

                String lastElement = stringArray[stringArray.length - 1];

                String bytes = Character.isDigit(lastElement.charAt(0)) ? lastElement : "0";

                Record record = new Record(host, timestamp, request, resource, replyCode, bytes);


                // Feature 1

                /** If the key is not in the map, put the key and 1 as value. */
                if (!hostMap.containsKey(host))
                {
                    hostMap.put(host, record);
                }
                hostMap.get(host).addhostCount();


                // Feature 2

                /** If the key is not in the map, put a new ResourceConsume object. */
                if (!resourceMap.containsKey(resource))
                {
                    resourceMap.put(resource, record);
                }
                /** Add 1 to frequency. */
                resourceMap.get(resource).addResourceCount();


                // Feature 3

                /** Build the busyMap */
                if (!busyMap.containsKey(timestamp))
                {
                    busyMap.put(timestamp, record);
                }
                busyMap.get(timestamp).addBusyCount();
                /** Build the timestampList with no duplicate timestamp, O(1).
                 * it's faster than using TreeMap's keySet to get key O(lgn) */
                if (!timestampList.isEmpty() && !timestampList.get(timestampList.size()-1).equals(timestamp))
                {
                    timestampList.add(timestamp);
                }
                /** Add first timestamp */
                if (timestampList.isEmpty())
                {
                    timestampList.add(timestamp);
                }


                // Feature 4

                if (!record.replyCode.equals("200"))
                {
                    record.addFailCount();
                }
                if (!recordList.isEmpty())
                {
                    Record lastRecord = recordList.get(recordList.size()-1);
                    /** Current and last are the same host */
                    if (lastRecord.host.equals(record.host))
                    {   /** Last one is fail, either current */
                        if (lastRecord.failCount > 0 && record.failCount == 1)
                        {
                            record.failCount = lastRecord.failCount + 1;
                        }
                    }
                }
                recordList.add(record);
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
