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

    public String replyCode;

    public String bytes;

    public int failCount;

    public Date date;

    public Record(String host, String timestamp, String request, String replyCode, String bytes) {
        this.host = host;
        this.originalTimestamp = timestamp;
        this.timestamp = this.originalTimestamp.replace("Jul", "07");
        this.request = request;
        this.replyCode = replyCode;
        this.bytes = bytes;
        this.failCount = 0;

        /** Initialize Date */
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss");
        try {
            int index = timestamp.indexOf('-');

//            System.out.println(timestamp.substring(1, index));

            this.date = dateFormat.parse(this.timestamp.substring(1, index));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void failDetect() {
        failCount++;
    }
}
