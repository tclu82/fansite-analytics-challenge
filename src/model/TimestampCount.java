package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zac on 4/4/17.
 */
public class TimestampCount {
    String timestamp;
    String timeZone;
    Date date;
    int frequency;

    public TimestampCount(String timestamp, String timeZone) {
        this.timestamp = timestamp;
        this.timeZone = timeZone;
        this.frequency = 0;

        /** Initialize Date */
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss");
        try { this.date = dateFormat.parse(this.timestamp); }
        catch (ParseException e) { e.printStackTrace(); }
    }

    public void addFrequency() {
        frequency++;
    }
}
