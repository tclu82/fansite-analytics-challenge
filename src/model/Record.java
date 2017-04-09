package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zac on 4/5/17.
 */
public class Record {

    private static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    /** All required fields for 1 record */
    public String host;

    public String originalTimestamp;

    public String timestamp;

    public String request;

    public String resource;

    public String replyCode;

    public String bytes;

    public int hostCount;

    public int resourceCount;

    public int busyCount;

    public int failCount;

    public Date date;

    /**
     * Constructor, initialize all fields
     *
     * @param host
     * @param timestamp
     * @param request
     * @param replyCode
     * @param bytes
     */
    public Record(String host, String timestamp, String request, String resource, String replyCode, String bytes) {

        this.host = host;

        this.originalTimestamp = timestamp;

        this.request = request;

        this.resource = resource;

        this.replyCode = replyCode;

        this.bytes = bytes;

        this.hostCount = 0;

        this.resourceCount = 0;

        this.busyCount = 0;

        this.failCount = 0;
        /** Modify timestamp */
        modifyMonthsFromLettersToDigits();

        /** Initialize Date */
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss");

        try
        {
            int index = timestamp.indexOf(' ');

            this.date = dateFormat.parse(this.timestamp.substring(1, index));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Replace "Jul" from originalTimestamp to "07" from Date later
     */
    private void modifyMonthsFromLettersToDigits()
    {
        Map<String, String> monthMap = new HashMap<>();

        for (int i=0; i<MONTHS.length; i++)
        {   /** 1-9 */
            if (i<=8)
            {
                monthMap.put(MONTHS[i], "0" + String.valueOf(i+1));
            }
            /** 10-12 */
            else
            {
                monthMap.put(MONTHS[i], String.valueOf(i+1));
            }
        }

        for (String monthString: monthMap.keySet())
        {   /** Find out which month then update to digits */
            if (originalTimestamp.contains(monthString))
            {
                this.timestamp = this.originalTimestamp.replace(monthString, monthMap.get(monthString));
            }
        }
    }

    /**
     * Update host count
     */
    public void addhostCount()
    {
        hostCount++;
    }

    /**
     * Update resource count
     */
    public void addResourceCount()
    {
        resourceCount++;
    }

    /**
     * Update busy count
     */
    public void addBusyCount()
    {
        busyCount++;
    }

    /**
     * Update failure count
     */
    public void addFailCount()
    {
        failCount++;
    }
}
