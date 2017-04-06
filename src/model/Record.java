package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zac on 4/5/17.
 */
public class Record {

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
        this.timestamp = this.originalTimestamp.replace("Jul", "07");
        this.request = request;
        this.resource = resource;
        this.replyCode = replyCode;
        this.bytes = bytes;
        this.hostCount = 0;
        this.resourceCount = 0;
        this.busyCount = 0;
        this.failCount = 0;

        /** Initialize Date */
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss");
        try {
            int index = timestamp.indexOf(' ');

            this.date = dateFormat.parse(this.timestamp.substring(1, index));
        }
        catch (ParseException e) {

            e.printStackTrace();
        }
    }

    /**
     * Update host count
     */
    public void addhostCount()
    {
        hostCount++;
    }

    public void addResourceCount()
    {
        resourceCount++;
    }

    public void addBusyCount()
    {
        busyCount++;
    }

    public void addFailCount()
    {
        failCount++;
    }

}
