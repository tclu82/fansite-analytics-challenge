package model;

import control.ReadFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zac on 4/3/17.
 */
public class Feature4 extends Features {
    /** A constant time interval */
    private static final long TWENTY_SECONDS = 20 * 1000;

    /**
     * Constructor
     *
     * @param readFile
     */
    public Feature4(ReadFile readFile) {
        super(readFile);
    }

    /**
     * Find out if the failed record within the time interval and write to output file
     */
    @Override
    public void execute() {
        /** Records from readFile */
        List<Record> recordList = readFile.recordList;
        /** A list record all records needed to be blocked */
        List<Record> blockedList = new ArrayList<>();

        for (int i=0; i<recordList.size(); i++) {
            /** Check fail count 1, 2, 3 within 20 seconds */
            if (recordList.get(i).failCount == 3) {

                Date start = recordList.get(i-2).date;

                Date current = recordList.get(i).date;
                /** If the first 3 failed records happened within the time interval */
                if (current.getTime() - start.getTime() <= TWENTY_SECONDS) {
                    /** Add all of them into the blocked list */
                    for (int j=i-2; j<=i; j++) {
                        blockedList.add(recordList.get(j));
                    }
                }
            }
            /** Check fail count 4, 5, 6..... */
            else if (recordList.get(i).failCount > 3) {

                int j = i;
                /** Find out the first fail when failed more than 3 times in row */
                while (recordList.get(j).host.equals(recordList.get(i).host)) {

                    j--;
                }
                /** The first failed record*/
                Date start = recordList.get(j+1).date;

                Date current = recordList.get(i).date;

                if (current.getTime() - start.getTime() <= TWENTY_SECONDS) {

                    blockedList.add(recordList.get(i));
                }
            }
        }

        /** Print out the result. */
        Writer writer = null;

        /** Write to hosts.txt and catch the exceptions. */
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log_output/blocked.txt"),

                    "utf-8"));
            /** Pop up all entries and write to output file. */
            for (Record record: blockedList) {
                /** Write the record to blocked.txt */
                String output = String.format("%s - - %s %s %s %s\n", record.host, record.originalTimestamp,
                        record.request, record.replyCode, record.bytes);

                writer.write(output);
            }
        }
        /** */
        catch (IOException e) { e.printStackTrace(); }

        finally {

            try { writer.close(); }

            catch (Exception e) { e.printStackTrace(); }
        }
    }
}
