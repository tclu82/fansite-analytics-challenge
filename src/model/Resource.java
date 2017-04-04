package model;

import java.util.Date;

/**
 * Created by zac on 4/2/17.
 */
public class Resource {

    public String hostName;

    public String resourceName;

    public int frequency;

    public int resourceSize;

    public String dateString;

    public String timeZone;

    public Date date;




    public Resource(String hostName, String resourceName, int resourceSize, String dateString, String timeZone, Date theDate) {
        this.hostName = hostName;
        this.resourceName = resourceName;
        this.dateString = dateString;
        this.date = theDate;
        this.timeZone = timeZone;
        this.frequency = 0;
        this.resourceSize = resourceSize;
    }

    public Resource(Resource r2) {
        this.hostName = r2.hostName;
        this.resourceName = r2.resourceName;
        this.dateString = r2.dateString;
        this.date = r2.date;
        this.timeZone = r2.timeZone;
        this.frequency = r2.frequency;
        this.resourceSize = r2.resourceSize;
    }

    public void addFrequency() {
        frequency++;
    }
}
