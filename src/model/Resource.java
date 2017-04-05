package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zac on 4/2/17.
 */
public class Resource {

    public String hostName;

    public String resourceName;

    public int frequency;

    public int resourceSize;

    public String timestamp;

    public String timeZone;

    public Date date;


    public Resource(String hostName, String resourceName, int resourceSize, String timestamp, String timeZone) {
        this.hostName = hostName;
        this.resourceName = resourceName;
        this.timestamp = timestamp;
        this.timeZone = timeZone;
        this.frequency = 0;
        this.resourceSize = resourceSize;
        /** Initialize Date */
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss");
        try { this.date = dateFormat.parse(this.timestamp); }
        catch (ParseException e) { e.printStackTrace(); }
    }

    public Resource(Resource another) {
        this.hostName = another.hostName;
        this.resourceName = another.resourceName;
        this.timestamp = another.timestamp;
        this.date = another.date;
        this.timeZone = another.timeZone;
        this.frequency = another.frequency;
        this.resourceSize = another.resourceSize;
    }

    public void addFrequency() {
        frequency++;
    }
}
